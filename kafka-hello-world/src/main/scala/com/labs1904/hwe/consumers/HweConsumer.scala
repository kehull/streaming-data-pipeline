package com.labs1904.hwe.consumers

import com.labs1904.hwe.producers.SimpleProducer
import com.labs1904.hwe.util.Util
import com.labs1904.hwe.util.Util.{getScramAuthString, mapNumberToWord}
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import java.util.{Arrays, Properties, UUID}
// Create a case class named RawUser that matches the data's structure.
case class RawUser(id: Int, userName: String, governmentName: String, email: String, dob: String)
//Create a new case class named EnrichedUser that adds 2 new columns:
//numberAsWord - convert the first field (which is numeric) into a string using the Util.mapNumberToWord function
//hweDeveloper - hard-code your name here. (This is so you can tell your data apart from everyone else's)
case class EnrichedUser(id: Int, userName: String, governmentName: String, email: String, dob: String, numberAsWord: String, hweDeveloper: String)
object HweConsumer {
  val BootstrapServer : String = "b-3-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-2-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196,b-1-public.hwekafkacluster.6d7yau.c16.kafka.us-east-1.amazonaws.com:9196"
  val consumerTopic: String = "question-1"
  val producerTopic: String = "question-1-output"
  val username: String = "1904labs"
  val password: String = "1904labs"
  //Use this for Windows
  val trustStore: String = "src\\main\\resources\\kafka.client.truststore.jks"
  //Use this for Mac
  //val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  implicit val formats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {

    // Create the KafkaConsumer
    val consumerProperties = SimpleConsumer.getProperties(BootstrapServer)
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProperties)

    // Create the KafkaProducer
    val producerProperties = SimpleProducer.getProperties(BootstrapServer)
    val producer = new KafkaProducer[String, String](producerProperties)

    // Subscribe to the topic
    consumer.subscribe(Arrays.asList(consumerTopic))

    while ( {
      true
    }) {
      // poll for new data
      val duration: Duration = Duration.ofMillis(100)
      val records: ConsumerRecords[String, String] = consumer.poll(duration)

      records.forEach((record: ConsumerRecord[String, String]) => {
        // Retrieve the message from each record
        val message = record.value()
        println(s"Message Received: $message")
        // TODO: Add business logic here!

        //Split the TSV by tabs /t, and populate an instance of the scala case class that you created.
        val messageArray = message.split("\\t")
        val userData = RawUser(messageArray(0).toInt, messageArray(1), messageArray(2),messageArray(3), messageArray(4))
        println(s"RawData class: $userData")

        //For each record processed, populate an instance of EnrichedUser
        //numberAsWord - convert the first field (which is numeric) into a string using the Util.mapNumberToWord function
        //hweDeveloper - hard-code your name here. (This is so you can tell your data apart from everyone else's)
//        mapNumberToWord(messageArray0) << This is what I would use otherwise, but the ID number is coming through as a string natively
        val enrichedUserData = EnrichedUser(messageArray(0).toInt, messageArray(1), messageArray(2),messageArray(3), messageArray(4), mapNumberToWord(messageArray(0).toInt), "Kelly")
        println(s"EnrichedUser: $enrichedUserData")

//        Convert your instance of EnrichedUser into a comma -separated string of values

//        Write the comma - separated string to a topic named question - 1 - output
//        Terminate your HweConsumer process and use SimpleConsumer to
//        try and find your data !
        val sendToTopic = enrichedUserData.productIterator.mkString(",")
        val finalRecord = new ProducerRecord[String, String]("question-1-output", sendToTopic)

        producer.send(finalRecord)
        println("Record sent!")
      })
    }
    producer.close()
    println("Producer closed!")
  }
}

