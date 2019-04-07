# Smart Workplace

The project focuses on utitlizing sensor data such as fitbit, apple watch, smart belts, etc for employee health and wellness, their safety and prevention, in turn reducing the employee hazard costs for the businesses. The project aims to provide real-time alerting to employees in case of abnormalities and stores data of employees' health in fixing premiums for the organization. We using Kafka and Spark Structured Streaming to process the data and provide real-time alerts.

# Configuration Setup

We use the following configurations,

-Apache Spark 2.2.0
-Apache Kafka 0.10.2.11
-Scala 2.11.11
The maven dependencies are enlisted in pom.xml.
The configurational properties for Spark, Kafka and ML models are placed in the resources directory.

# Data
We use Kaggle's Fall detection data  (https://www.kaggle.com/pitasr/falldata) collected from wearable motion sensor units fitted to the subject's body. The data has been modified to two classes- abnormality (1) and no abnormality (0) and subsampled to prevent class imbalance. The data for train and test are located in data folder.
### Data description
TIME  - monitoring time                                                         
SL	- Sugar Level
EEG	- EEG monitoring rate
BP	- Blood pressure
HR - Heart Rate
CIRCLUATION	- Blood circulation
target

# Running the code on local
1. All the configurations from the property files, application.json, kafka-settings.conf, log4j.properties, ml-settings.conf and spark-settings.conf, twilio.conf are passed as config properties to Spark Session. The Spark Context alongwith with all the configuration properties form the SmartWorkplace Context. This SmartWorkplace Context is called at the start of every application and a Spark Session is started. This enables to load in all the config properties once and read at any time during the application run.

2. We developed static training models using Logistic regression and Gradient Boosted Trees classifiers. The classes for these models are MLTrainLR and MLTrainGBT and these can be run using their respective apps in com.hackdivas.workplace package. The data is loaded in based on the path in ml-stettings.conf file and the models are stored in the ouput paths mentioned in the property file.

3. The test data is streamed in using Spark streaming and loading into a custom Kafka sink that contains the ML models. The user has the flexibility to choose the models by selecting the model names in ml-settings.conf file. Users with abnormalities are sent real-time text alerts and the data is stored as files for further processing and analysis. The predictions can be obtained by running using MLTestApp.

# Deployment and running on cluster
The jar is built using Maven and can be run on the cluster using spark-submit.
