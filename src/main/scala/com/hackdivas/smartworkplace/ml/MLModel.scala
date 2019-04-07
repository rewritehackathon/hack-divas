package com.hackdivas.smartworkplace.ml

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.{DataFrame, SaveMode}
import com.twilio.Twilio
import com.twilio.`type`.PhoneNumber
import com.twilio.rest.api.v2010.account.Message

class MLModel(modelName: String) {

  //Extracting saved model path based on model name
  val context = SmartWorkplaceContext(Array("ml-settings.conf", "spark-setting.conf", "twilio.conf"))
  val spark = context.spark
  val accountsid = context.config.config("account_sid")
  val authToken = context.config.config("auth_token")
  val fromNumber = context.config.config("from_number")

  //Get the pre-trained model based on model selected in the config
  private val modelPath = modelName match {
    case "GBT"  => context.config.config("ml.train.save.model.path.GBT")
    case "LR"  => context.config.config("ml.train.save.model.path.LR")
    case _  => throw new IllegalArgumentException("Model path for the given model not found")
  }

  // Load the pre-trained model
  private val model = PipelineModel.read.load(modelPath)

  def transform(StreamDf: DataFrame) = {
    // Replace nan values with 0
    val StreamDf_clean = StreamDf.na.fill(0)

    // Run the model on streaming data
    val result = model.transform(StreamDf_clean)

    // Display the results
    import spark.implicits._
    result.select("TIME", "SL", "EEG","BP", "HR", "Prediction").show()
    val highRiskEmployee = result.select("*").where(result("Prediction") === 1).first

    //Send text message
    Twilio.init(accountsid, authToken)
    val from = new PhoneNumber(fromNumber)
    val to = new PhoneNumber("+14803075225")
    val body = "You should meet the doctor immediately. Your vitals are - BP: " + highRiskEmployee.getDouble(3) +
    ", Heart Rate: " + highRiskEmployee.getDouble(4)

    val message = Message.creator(to, from, body).create()
    println(s"Message sent to $to with ID ${message.getSid}")

    //Save to file
    val format = context.config.config.getOrElse("ml.test.output.format", "json")
    val savePath = context.config.config("ml.test.output.path")+"/"+modelName
    val mode = context.config.config.getOrElse("ml.test.output.mode", "overwrite")
    result.select("TIME", "SL", "EEG","BP", "HR", "rawPrediction",  "Probability", "Prediction")
      .write
      .mode(mode)
      .format(format)
      .save(savePath)
  }

}
