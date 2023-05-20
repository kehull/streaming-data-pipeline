package com.labs1904.spark

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, Put, Scan}
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.util.Bytes

/**
 * Spark Structured Streaming app
 *
 */

case class Review(marketplace: String, //0
                   customer_id: String, //1
                   review_id: String, //2
                   product_id: String, //3
                   product_parent: String, //4
                   product_title: String, //5
                   product_category: String, //6
                   star_rating: String, //7
                   helpful_votes: String, //8
                   total_votes: String, //9
                   vine: String, //10
                   verified_purchase: String, //11
                   review_headline: String, //12
                   review_body: String, //13
                   review_date: String //14
                 )

object StreamingPipeline {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "StreamingPipeline"

  val hdfsUrl = "CHANGEME"
  val bootstrapServers = "b-3-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-2-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-1-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196"
  val username = "1904labs"
  val password = "1904labs"
  val hdfsUsername = "khull"

  //Use this for Windows
  val trustStore: String = "src\\main\\resources\\kafka.client.truststore.jks"
  //Use this for Mac
  //val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  def main(args: Array[String]): Unit = {
    try {
      // create spark session
      val spark = SparkSession.builder()
        .config("spark.sql.shuffle.partitions", "3")
        .appName(jobName)
        .master("local[*]")
        .getOrCreate()

      import spark.implicits._

      // read values from stream
      val ds = spark
        .readStream
        .format("kafka")
        //create consumer
        .option("kafka.bootstrap.servers", bootstrapServers)
        //subscribe to reviews topic
        .option("subscribe", "reviews")
        .option("startingOffsets", "earliest")
        .option("maxOffsetsPerTrigger", "20")
        .option("startingOffsets","earliest")
        .option("kafka.security.protocol", "SASL_SSL")
        .option("kafka.sasl.mechanism", "SCRAM-SHA-512")
        .option("kafka.ssl.truststore.location", trustStore)
        .option("kafka.sasl.jaas.config", getScramAuthString(username, password))
        .load()
        .selectExpr("CAST(value AS STRING)").as[String]

      // map ingested data to Review case class
      val reviewRow = ds.map(review_str => {
        val reviewElem = review_str.split("\t")
        Review(reviewElem(0),reviewElem(1), reviewElem(2), reviewElem(3), reviewElem(4), reviewElem(5), reviewElem(6), reviewElem(7), reviewElem(8), reviewElem(9), reviewElem(10), reviewElem(11), reviewElem(12), reviewElem(13), reviewElem(14))
      })
//       map HBase connection to partitions
      val ids = reviewRow.select("customer_id").collect.map(_.getString(0)).toList.toDS //collects reviewRow dataframe as an array of rows, applies "map" to extract the string value of the first column of each row, converts the array to a list and then a dataset named "ids"
      val customers = ids.mapPartitions(partition => { //applies mapPartitions transformation to the 'ids' Dataset; maps each partition of the Dataset to a new collection of values by applying a function to each partition
        val conf = HBaseConfiguration.create() // create HBase configuration
        conf.set("hbase.zookeeper.quorum", "cdh01.hourswith.expert:2181,cdh02.hourswith.expert:2181,cdh03.hourswith.expert:2181") //set ZooKeeper
        val connection = ConnectionFactory.createConnection(conf) // establish HBase connection
        val table = connection.getTable(TableName.valueOf("khull:users")) // get my table

        val iter = partition.map(id => { // id = each element of the partition. call "map" on the partition and return as value "iter"
          val idBytes = Bytes.toBytes(id) //convert "id" to bytes instead of string
          val get = new Get(idBytes).addFamily(Bytes.toBytes("f1")) //create new "Get" object to retrieve data from HBase table. "idBytes" represents the row identifier of each row. "addFamily" is used on the "Get" object to specify the column family to retrieve data from.
          val result = table.get(get) // retrieves the data from the HBase table using the "get" object created in the previous line and returns an object called "Result" containing teh retrieved row data.
//          logger.debug(result)

          (idBytes, Bytes.toString(result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("mail")))) // create tuple containing the idBytes value (row identifier), then retrieve a value from the "result" object using that key, based on the column family (f1) and column qualifier (mail). The value is then converted to a string
          }).toList.iterator //close the map function and transform the resulting collection into a List, then iterate over its elements.


        connection.close() // close the connection

        iter // return the results of the previous code block
      })

//       Write output to console
      val query = reviewRow.writeStream
        .outputMode(OutputMode.Append())
        .format("console")
        .option("truncate", false)
        .trigger(Trigger.ProcessingTime("5 seconds"))
        .start()

//      customers.show()

      // Write output to HDFS
//      val query = result.writeStream
//        .outputMode(OutputMode.Append())
//        .format("json")
//        .option("path", s"/user/${hdfsUsername}/reviews_json")
//        .option("checkpointLocation", s"/user/${hdfsUsername}/reviews_checkpoint")
//        .trigger(Trigger.ProcessingTime("5 seconds"))
//        .start()
      query.awaitTermination()
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  def getScramAuthString(username: String, password: String) = {
    s"""org.apache.kafka.common.security.scram.ScramLoginModule required
   username=\"$username\"
   password=\"$password\";"""
  }
}
