package com.hackdivas.smartworkplace

import com.hackdivas.smartworkplace.context.SmartWorkplaceContext
import com.hackdivas.smartworkplace.ml.MLTest

object MLTestApp{

  def main(configs: Array[String]) = {

    // Create the application context
    val context = SmartWorkplaceContext(Array("application.json", "spark-setting.conf","ml-settings.conf", "kafka-settings.conf") ++ configs)

    // Create ML Test streaming application
    val testML = MLTest.create(context)
    testML.run

  }
}

