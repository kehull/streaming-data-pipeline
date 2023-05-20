# Overview

Similar to the work you did for Kafka, this is your crash course into Spark through different questions. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like
* Spark By Examples is a great resources to start with - [Spark By Examples](https://sparkbyexamples.com/)

### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What problem does Spark help solve? Use a specific use case in your answer 
* Helpful resource: [Apache Spark Use Cases](https://www.toptal.com/spark/introduction-to-apache-spark)
* [Overivew of Apache Spark](https://www.youtube.com/watch?v=znBa13Earms&t=42s)

Spark overcomes the problems of Hadoop MapReduce, allowing programs to run extremely fast, and also allowing users to write code much faster, as tasks require fewer lines of code to carry out. Spark also allows users to program in their preferred language. Spark also allows users to test individual lines of code without needing to code and execute an entire job. Spark allows for more advanced analytics via SQL, R, ML functions, and graph processing. Spark can be used to rapidly feed data in to ML algorithms, like those used in financial fraud detection, to generate real-time results.

#### What is Apache Spark?
* Helpful resource: [Spark Overview](https://www.youtube.com/watch?v=ymtq8yjmD9I) 

Apaches Spark is a framework for processing large-scale data and analytics workloads. Spark is founded on the Resilient Distributed Dataset. 

#### What is distributed data processing? How does it relate to Apache Spark?  
[Apache Spark for Beginners](https://medium.com/@aristo_alex/apache-spark-for-beginners-d3b3791e259e)

Apaches Spark is founded on RDDs, or Resilient Distributed Datasets, which can be created from nearly any kind of dataset you can think of.

RDDs allow for standard MapReduce functions, but also datasets, filtering, and aggregation. The RDD hides complexity from users, who don't have to define where specific files are sent, how resources are used, or how to retrieve files.

#### On the physical side of a spark cluster, you have a driver and executors. Define each and give an example of how they work together to process data

When processing an RDD, when a program executes, it starts with a driver that creates a SparkContext, which is an orchestrator that considers the tasks to complete and how to perform them. It generates a plan, then uses a cluster manager to coordinate all the executors to schedule and run the task. 

The scheduler is called a DAG (Directed, Acyclic Graph) scheduler, which assigns tasks out to the worker nodes. Then, the executors on the worker nodes are dynamically launched by the cluster manager, which run a task and return the result to the driver. This makes up Core Spark.

#### Define each and explain how they are different from each other 
* RDD (Resilient Distributed Dataset)

An RDD is a programming abstraction that represents a collection of read-only objects split across a computing cluster in which processing is done fully in memory. RDDs represent immutable, partitioned collections of elements that can be operated on in parallel.

* DataFrame

A dataframe is a structure that can store two-dimensional data in rows and columns, like a standard spreadsheet or SQL table.

* DataSet

A dataset is a collection of partitioned data with primitive values or values of values (such as tuples or other objects that represent records of the data you work with)

#### What is a spark transformation?
[Spark By Examples-Transformations](https://sparkbyexamples.com/apache-spark-rdd/spark-rdd-transformations/)
A Spark transformation is an operation performed on an RDD that spawns one or more new RDDs. RDDs are immutable, so transforming an RDD inherently generates a new one.

#### What is a spark action? How do actions differ from transformations? 

A Spark action is an operation that returns a value. Actions trigger transoformations.

#### What is a partition in spark? Why would you ever need to repartition? 
[Spark Partitioning](https://sparkbyexamples.com/spark/spark-repartition-vs-coalesce/)

A Spark partition is a subset of a larger distributed dataset stored on multiple machines in a cluster. This is a fundamental concept that enables distributed data processing and parallelism, which allows you to handle large-scale datasets efficiently. Actions and transformations can be performed on individual partitions in parallel across multiple nodes of a cluster.

Repartitioning a dataset can address uneven data distribution (data skew). It can also allow for better resource utilization- for example, if Spark partitions a small dataset across too many nodes, coalescing can set up a more reasonable number of partitions. Also, if you have two or more columns of data that are closely related to each other- for example the join keys on two datasets that you intend to join- it can be beneficial to "colocate" that data, or ensure that data ends up on the same partition, as shuffling the data between partition can be time-consuming if the data isn't already on the same partition.

#### What was the most fascinating aspect of Spark to you while learning? 
During office hours, I mentioned to Matt that I was still wrapping my head around writing recursive functions on purpose, because the concept didn't make a lot of sense to me. He explained that loops don't work in Spark because of parallelism, and that concept made a lot more sense!