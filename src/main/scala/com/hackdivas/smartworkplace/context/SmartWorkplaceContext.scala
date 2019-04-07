package com.hackdivas.smartworkplace.context

import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory.getLogger

import scala.collection.JavaConversions._

class SmartWorkplaceContext(configs: Array[String]) extends Serializable {

  @transient val logger = getLogger(this.getClass)

  val config = loadConfiguration

  // This needs to be transient. We dont want to serialize the sparkSession to the tasks on the executors
  @transient lazy val spark: SparkSession = {
    val sparkConf = new SparkConf()  // .set("spark.sql.warehouse.dir", "./spark-warehouse")
    SparkSession.builder().appName("SmartWorkplace").config(sparkConf).getOrCreate()
  }

  def loadConfiguration = {

    // Load up the spark configuration (By default the file name is spark-setting.conf
    // Load any other configuration (By default the file name is application.json)
    val defaultConfig = ConfigFactory.parseResources("application.json")
      .withFallback(ConfigFactory.parseResources("spark-setting.conf"))

    // TODO: Check this code to ensure that properties passed in from the command line override whats in the default
    // config files.
    val configArray = configs.map(configFile => {
      logger.info("Loading configuration file named " + configFile)
      ConfigFactory.parseResources(configFile)
    }) ++ Array(defaultConfig)

    // Merge all the loaded configurations into one config object
    val mergedConfig = configArray.reduceLeft(_.withFallback(_)).withFallback(defaultConfig)

    // Extract spark settings from config and insert them into the system properties. This is necessary
    // for Spark to function.
    val sparkConfig = mergedConfig.entrySet().filter(name => name.getKey.startsWith("spark")).foreach(each => {
      System.getProperties.setProperty(each.getKey, each.getValue.unwrapped().toString)
    })

    // Extract other app configurations if any
    val appConfigTypesafe = mergedConfig.entrySet
    val appConfig = (for (config <- appConfigTypesafe) yield {
      (config.getKey, config.getValue.unwrapped().toString)
    }).toMap[String,String]

    new SmartWorkplaceConfig(appConfig)

  }
}

/**
  * Companion object to the context object
  */
object SmartWorkplaceContext {

  def create(configs : Array[String]) = {
    // Read in the configuration file for configuration
    new SmartWorkplaceContext(configs)
  }

  def apply(configs: Array[String]): SmartWorkplaceContext = new SmartWorkplaceContext(configs)
}
