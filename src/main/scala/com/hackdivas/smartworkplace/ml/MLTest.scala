package com.hackdivas.smartworkplace.ml

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.slf4j.LoggerFactory.getLogger
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{DataStreamWriter, Trigger}

class MLTest(context: SmartWorkplaceContext) {

  @transient val logger = getLogger(this.getClass)
  val spark = context.spark
  val config = context.config.config

  def run = {
    logger.debug("Running Pravaha app")
    //    for ((k,v) <- context.config.config) logger.info("Key is " + k + " and value is " + v)

    val testFilePath = config("ml.test.path")

    // Define the streaming test data schema
    //Schema of the data
    val schema = StructType(
      Array(StructField("TIME", DoubleType),
        StructField("SL", DoubleType),
        StructField("EEG", DoubleType),
        StructField("BP", DoubleType),
        StructField("HR", DoubleType),
        StructField("CIRCLUATION", DoubleType)
      ))

    //Read test data streams from kafka
    import spark.implicits._
    val inputStreamDf = spark
      .readStream
      .option("header", "true")
      .schema(schema)
      .csv(testFilePath)

    //Write to console
    inputStreamDf.writeStream.format("console").start//.awaitTermination()

    // Write streams to custom ML Sink
    createMLPredictionStream(inputStreamDf).start.awaitTermination
  }

  /**
    * Create the input stream based DataFrame for ML on the configuration options in the config file
    * @return
    */
  def createKafkaInputStream : DataFrame = {

    // Retrieve all the configurations which refer to the inputStream prefixed by "input." in kafka-settings.conf file
    // Apply then as the options to the DataStreamReader and then create the dataframe and return it
    val inputStreamDf = spark
      .readStream
      .format("kafka")
      .options({
        val inputOptionsMap = config.filter(each => each._1.toLowerCase.startsWith("input.option."))
          .map{case (k,v) => (k.replace("input.option.",""),v)}
        inputOptionsMap
      })
      .load

    inputStreamDf
  }

  /**
    * Apply all the configurations to the output and then write to specified writer
    * Writing to custom ML Sink. Format contains the custom ML sink which is the selected model sink provider class
    */
  def createMLPredictionStream(streamFrame: DataFrame) : DataStreamWriter[Row] = {

    val modelName = config("ml.test.output.model")
    println("Model used for classification: "+ modelName)

    //Select sinks based on the model name
    val sinkProvider = modelName match {
      case "GBT"  => "GBTModelSinkProvider"
      case "LogisticRegression"  => "LogRegModelSinkProvider"
      case _  => throw new IllegalArgumentException("Invalid model")
    }

    // Read in the format from the ml-setttings.conf file
    val format = "com.hackdivas.smartworkplace.ml."+sinkProvider
    val outputMode = config.getOrElse("ml.test.outputMode", "append")
    val trigger = Trigger.ProcessingTime(config.getOrElse("ml.test.output.trigger.checkpoint-time", "0"))
    val outputStreamDf = streamFrame
      .writeStream
      .format(format)
      .options({
        val outputOptionsMap = config.filter(each => each._1.toLowerCase.startsWith("ml.test.output.option."))
          .map{case (k,v) => (k.replace("ml.test.output.option.",""),v)}
        outputOptionsMap
      })
      .outputMode(outputMode)
      .trigger(trigger)

    outputStreamDf
  }

}

object MLTest {

  def create[T <: SmartWorkplaceContext](context: T) = {
    new MLTest(context)
  }
}







