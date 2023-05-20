package com.labs1904.hwe

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Delete, Get, Put, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.logging.log4j.{LogManager, Logger}

import java.util
import scala.collection.JavaConverters._

object MyApp {
  lazy val logger: Logger = LogManager.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("MyApp starting...")
    var connection: Connection = null
    try {
      val conf = HBaseConfiguration.create()
      conf.set("hbase.zookeeper.quorum", "hbase01.labs1904.com:2181")
      connection = ConnectionFactory.createConnection(conf)
      // Example code... change me

      //challenge 1
      val table = connection.getTable(TableName.valueOf("khull:users"))
      val get = new Get(Bytes.toBytes("10000001"))
      val result = table.get(get)
      val message = Bytes.toString(result.getFamilyMap(Bytes.toBytes("f1")).get(Bytes.toBytes("mail")))
      println(result)
      println(message)
      logger.debug(result)
      logger.debug(message)

      //challenge 2
      val put = new Put(Bytes.toBytes("99"))
      put.addColumn(
        Bytes.toBytes("f1"),
        Bytes.toBytes("username"),
        Bytes.toBytes("DE-HWE")
      )

      put.addColumn(
        Bytes.toBytes("f1"),
        Bytes.toBytes("name"),
        Bytes.toBytes("The Panther")
      )

      put.addColumn(
        Bytes.toBytes("f1"),
        Bytes.toBytes("sex"),
        Bytes.toBytes("F")
      )

      put.addColumn(
        Bytes.toBytes("f1"),
        Bytes.toBytes("favorite_color"),
        Bytes.toBytes("pink")
      )
      table.put(put)

      val get2 = new Get(Bytes.toBytes("99"))
      val result2 = table.get(get2)
      val username = Bytes.toString(result2.getFamilyMap(Bytes.toBytes("f1")).get(Bytes.toBytes("username")))
      val name = Bytes.toString(result2.getFamilyMap(Bytes.toBytes("f1")).get(Bytes.toBytes("name")))
      val sex = Bytes.toString(result2.getFamilyMap(Bytes.toBytes("f1")).get(Bytes.toBytes("sex")))
      val favorite_color = Bytes.toString(result2.getFamilyMap(Bytes.toBytes("f1")).get(Bytes.toBytes("favorite_color")))
      println(result2)
      println(username)
      println(name)
      println(sex)
      println(favorite_color)
//      logger.debug(result2)
//      logger.debug(username)

      //challenge 3
//      var scan = table.getScanner(new Scan())
//      scan.asScala.foreach(counterPrint => {
//        println(counterPrint)
//      })


      //challenge 4
      val delete: Delete = new Delete(Bytes.toBytes("99"))
      table.delete(delete)

      //challenge 5
      println("\nScan Example:")
      val scan = table.getScanner(new Scan())
      scan.asScala.foreach(result => {
        println(result)
      })

    } catch {
      case e: Exception => logger.error("Error in main", e)
    } finally {
      if (connection != null) connection.close()
    }
  }
}