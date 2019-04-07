package com.hackdivas.smartworkplace.ml

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.types.{DoubleType, StructField, StructType}
import org.slf4j.LoggerFactory.getLogger

class MLTrainLR(context: SmartWorkplaceContext) {

  @transient val logger = getLogger(this.getClass)
  val spark = context.spark
  val config = context.config.config

  def run = {
    logger.debug("Running Pravaha app")
    //for ((k,v) <- context.config.config) logger.info("Key is " + k + " and value is " + v)

    // Path of training data
    val filePath = config("ml.train.path")
    val modelPath = config("ml.train.save.model.path.LR")

    //Schema of the data
    val schema = StructType(
      Array(StructField("TIME", DoubleType),
        StructField("SL", DoubleType),
        StructField("EEG", DoubleType),
        StructField("BP", DoubleType),
        StructField("HR", DoubleType),
        StructField("CIRCLUATION", DoubleType),
        StructField("target", DoubleType)
      ))

    // Read the raw data
    val df_raw = spark.read
      .option("header", "true")
      .schema(schema)
      .csv(filePath)

    /**
      * Steps for pipeline:
      * Replacing all null values with 0
      * Filtering columns with categorical data and converting them into numeric using StringIndexer
      * Selecting the feature set
      * Using GBT classifier for prediction
      * Creating pipeline with the above steps
      */

    //Remove null values
    val df = df_raw.na.fill(0)

    // Create the feature vector
    val vectorAssembler = new VectorAssembler()
      .setInputCols(Array("TIME", "SL", "BP", "HR"))
      .setOutputCol("features")

    //Logistic Regression Classifier for prediction
    val lr = new LogisticRegression()
      .setLabelCol("target")
      .setFeaturesCol("features")

    // Create the pipeline with the stages
    val pipeline = new Pipeline().setStages(Array(vectorAssembler, lr))

    // Split the data into training and validation sets (30% held out for testing).
    val Array(trainingData, valData) = df.randomSplit(Array(0.7, 0.3))

    // Create the model following the pipeline
    val pipelineModel = pipeline.fit(trainingData)

    // Make predictions on validation data.
    val predictions = pipelineModel.transform(valData)

    // Select (prediction, Survival) and compute validation error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("target")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println("Validation Accuracy = " + accuracy)
    println("Validation Error = " + (1.0 - accuracy))

    // Save the model
    pipelineModel.write.overwrite.save(modelPath)

  }
}

object MLTrainLR {

  def create[T <: SmartWorkplaceContext](context: T) = {
    new MLTrainLR(context)
  }
}



