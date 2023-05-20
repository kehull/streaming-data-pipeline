package com.labs1904.hwe.homework

import org.apache.log4j.Logger
import org.apache.spark.sql.{Dataset, SparkSession}

object HelloWorldBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "HelloWorldBatchApp"

  def main(args: Array[String]): Unit = {
    try {
      logger.info(s"$jobName starting...")
      //TODO: What is a spark session - Why do we need this line?
      // A spark session is the container that holds everything needed to work with Spark, like configuration settings, data sources, and Spark application code. This line of code calls on the Builder API so we can configure our session.
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3")
        //TODO- What is local[*] doing here?
        //This line tells Spark to run on the local machine, using all available cores for parallel processing. This allows the user to develop and test applications on a single machine rather than needing access to a distributed cluster. You wouldn't use this setting for production code.
        .master("local[*]")
        //TODO- What does Get or Create do?
        //.getOrCreate() tells Spark to check if the session already exists, and only creates a new one of this session doesn't already exist.
        .getOrCreate()

      import spark.implicits._
      val sentences: Dataset[String] = spark.read.csv("C:\\Users\\kelly\\OneDrive\\Documents\\HoursWithExperts\\streaming-data-pipeline\\spark-hello-world\\src\\main\\resources\\sentences.txt").as[String]
      // print out the names and types of each column in the dataset
      sentences.printSchema
      // display some data in the console, useful for debugging
      //TODO- Make sure this runs successfully
      sentences.show(truncate = false)
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }
}
