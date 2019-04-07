package com.hackdivas.smartworkplace

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import com.hackdivas.smartworkplace.ml.MLTrainLR

object MLTrainLRApp {

  def main(configs: Array[String]) = {

    // Create the application context
    val context = SmartWorkplaceContext(Array("application.json", "spark-setting.conf","ml-settings.conf") ++ configs)

    // Create ML Train application
    val trainML = MLTrainLR.create(context)
    trainML.run
  }
}
