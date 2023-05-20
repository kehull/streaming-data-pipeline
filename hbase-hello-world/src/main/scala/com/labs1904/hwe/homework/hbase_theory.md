# Overview

By now you've seen some different Big Data frameworks such as Kafka and Spark. Now we'll be focusing in on HBase. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like


### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What is a NoSQL database? 
A NoSQL databases are non-structured, meaning you can build them without having a set schema in mind. They are not tabular (tabular = columns and rows). NoSQL databases allow flexible schemas, meaning the fields and datatypes can differ from document to document. They can scale horizontally across multiple nodes more easily than a relational database because the data doesn't have to be joined across nodes.

#### In your own words, what is Apache HBase? 
Apache HBase is an open-source, NoSQL, distributed big data store written on top of HDFS that provides random, real-time read and write access to its data. It can handle smaller databases of unstructured data, but works better with structured or semi-structured data in bigger databases. It is a good option for real-time analysis of large datasets

#### What are some strengths and limitations of HBase? 
* [HBase By Examples](https://sparkbyexamples.com/apache-hbase-tutorial/)
HBase allows real-time analytics of large datasets. It also allows dynamic addition/removal of columns as needed. It's easily scalable and allows for random read and write operations. It's very fault tolerant and supports parallel processing.

That said, HBase queries are much more challenging than SQL queries (though you can run Hive on top of it to run SQL-like queries), and it's not possible to join multiple tables. It's computationally expensive, challenging to filter, limited in how it can be sorted, and doesn't support compound keys. HBase introduces a single point of failure and doesn't support transactions.

#### Explain the following concepts: 
* Rowkey
A rowkey is the unique identifier for a row of data in an HBase table. The rowkey points to both the node of the cluster the data is stored on, as well as the actual row within the dataset on that node. Unlike normal UUIDs in other types of databases, which are usually integers or strings, an HBase rowkey is a byte array. Rows in an HBase database are stored in lexicographic order, like they would be if they were entries in a paper dictionary. This is not usually the case with other types of database UUIDs. The because of this sorting, rowkeys can have a significant impact on query efficiency. A rowkey is often made up of more than one column.
* Column Qualifier
Column qualifier = column name/column header. They are sometimes also called column keys.
* Column Family
A column family is a logical grouping of columns within a database.


#### What are the differences between Get and Put commands in HBase? 
* [HBase commands](https://www.tutorialspoint.com/hbase/hbase_create_data.htm)
Put is how you insert data, get is how you read data.


#### What is the HBase Scan command for? 
* [HBase Scan](https://www.tutorialspoint.com/hbase/hbase_scan.htm)
Unlike Get, which only returns one line of data, Scan returns multiple lines of data. Get is generally more efficient because it simply finds the rowkey in question. Scan doesn't require a rowkey

#### What was the most interesting aspect of HBase when went through all the questions? 