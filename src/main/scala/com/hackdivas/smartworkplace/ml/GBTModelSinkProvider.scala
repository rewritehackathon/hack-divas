package com.hackdivas.smartworkplace.ml

import org.apache.spark.sql.DataFrame

/** Define the GBT Model pipeline model in the sink
  * It is a sink which makes use of MlModel class that takes in the best model from the respective path saved
  * after training
  */
class GBTModelSinkProvider extends MLSinkProvider {
  override def process(df: DataFrame) {
    val ModelObject = new MLModel("GBT")
    ModelObject.transform(df)
  }
}