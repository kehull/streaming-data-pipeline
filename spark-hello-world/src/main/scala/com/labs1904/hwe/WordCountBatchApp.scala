package com.labs1904.hwe

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object WordCountBatchApp {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "WordCountBatchApp"

  def main(args: Array[String]): Unit = {
    logger.info(s"$jobName starting...")
    try {
      val spark = SparkSession.builder()
        .appName(jobName)
        .config("spark.sql.shuffle.partitions", "3")
        .master("local[*]")
        .getOrCreate()
      import spark.implicits._

      val sentences = spark.read.csv("C:\\Users\\kelly\\OneDrive\\Documents\\HoursWithExperts\\streaming-data-pipeline\\spark-hello-world\\src\\main\\resources\\sentences.txt").as[String]

      // get the string of sentences parsed from the csv
      val counts = sentences
        // make all words lowercase, remove extra punctuation, and split the string into a dataset of strings
        .flatMap(sentence => splitSentenceIntoWords(sentence))
        // group columns by word and add an aggregate column counting each word
        .groupBy(col("value")).count()
        // sort by the column "count" in desc order
        .sort(col("count").desc)
      counts.show()

    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  // TODO: implement this function
  // HINT: you may have done this before in Scala practice...
  def splitSentenceIntoWords(sentence: String): Array[String] = {
    sentence
      // convert all text to lowercase, otherwise "the" and "The" will be two separate values
      .toLowerCase()
      // replace all exteraneous punctuation with nothing, otherwise "are" and "are." will be two separate values. Keep apostrophes and hyphens.
      .replaceAll("""[\p{Punct}&&[^"'"-]]""", "")
      // split the string on every space into an array
      .split(" ")
      // drop all empty values from the array
      .filter(_.nonEmpty)
  }
}
