package com.hackdivas.smartworkplace

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import com.hackdivas.smartworkplace.ml.MLTrainGBT

object MLTrainGBTApp {

  def main(configs: Array[String]) = {

    // Create the application context
    val context = SmartWorkplaceContext(Array("application.json", "spark-setting.conf","ml-settings.conf") ++ configs)

    // Create ML Train application
    val trainML = MLTrainGBT.create(context)
    trainML.run
  }
}