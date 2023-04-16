# Overview

Kafka has many moving pieces, but also has a ton of helpful resources to learn available online. In this homework, your
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
#### What problem does Kafka help solve? Use a specific use case in your answer 
* Helpful resource: [Confluent Motivations and Use Cases](https://youtu.be/BsojaA1XnpM)  

Kafka answers the modern shift from state-focused systems to event-driven system. Instead of viewing data as "the current state of things", like a daily newsletter, we now deal with real-time feeds accessible online at any time and place, like social media feeds, RSS feeds, etc. Instead of just serving as a file system storying past events, Kafka is a platform that addresses many needs of event-driven architecture that focuses on real-time processing. Kafka stores logs in a scalable, replicated, and fault-tolerant way, and allows us to integrate those logs with internal systems such as ioT, mobile apps, SaaS apps, edge, microservices, databases, data lakes, machine learning, and cloud serices. Kafka operates on a global scale in real-time. Because data is store as a stream of events, we run computations on it differently. Instead of running a query, pulling a report, and then working with that, we'll use stream processors that work on the data one event at a time and/or in aggregate. Some of the biggest and most complex companies in the world- from old industries like banks and airlines, to much newer companies like social media and gig work apps- use Kafka. Oer 35% of Fortune 500 companies use Kafka for mission-critial work.  

USE CASES:  
* Fraud detection at banks now happens in near real-time instead of at the end of a day
* Cars are increasingly ioT devices that depend on things such as cameras, radar sensors, crash sensors, ultrasonic sensors, traffic alerts, anomaly detection, hazard alerts, and personalization. All of this data must be computed in real time over a streaming platform.  
* Real-time e-commerce is full of events such as searches, items going into carts, etc. Event-streaming platforms enable cool functionality to happen faster.
* Customer 360 in general
* Banking is one of those industries that has old batch-processing legacy systems, but they're slowly making changes to streaming data
* Healthcare relies increasingly on ioT medical devices. One example is a hospital in Georgia that used Kafka and Confluent KSQL to process data from pediatric intracranial pressure monitors.
* Online gaming is fundamentally event-driven- every click and button press is an event. Regardless of how the core game engine works, you need intelligence around those events.
* Government
* Financial services outside of banking



#### What is Kafka?
* Helpful resource: [Kafka in 6 minutes](https://youtu.be/Ch5VhJzaoaI)   
Kafka is the answer the problems faced by distribution and scaling of messaging systems.

#### Describe each of the following with an example of how they all fit together: 
 * **Topic**

A topic is a group of partitions handling the same type of data.
 * **Producer** 

The producer is the process that reads the data events as they happen and populates them to a queue, or "produces" them to a queue.
 * **Consumer** 

Consumers are downstream channels that read data from partitions by subscribing to the topic to which those partitions belong- this could be a single topic, a list of topics, or even a regex that matches several topics. Consumers only have to specify the topic name and one broker to connect to, and Kafka will automatically pull the data from the right brokers, because connecting to on broker connects you to the entire cluster. You cannot have more consumers than partitions- otherwise, some will be inactive. Consumers in the same consumer group cannot read from the same partitions as each other. In a topic with three partitions, you could have three consumers, each reading from one partition; you could have one consumer reading from all three partitions; or you could have two consumers, where one reads from two partitions and one reads from the remaining partition. If you had three partitions and four consumers, at least one consumer would be inactive, because it cannot read from the same partition as another consumer in its consumer group. When messages are available on the topics that a consumer is subscribed to, they're returned as a collection called (in Java) "consumerRecords". consumerRecords contains individual instances of messages in the form of instances of an object called consumerRecord, which is the key-value pair of a single record. Consumers are very lightweight because Kafka only needs to maintain the most recent offset read by a consumer.
 * **Broker**  

A broker is a server holding one or more partitions. Each broker is identified with an integer ID. An individual broker is also called a "bootstrap broker". A good starting number of brokers in Kafka is 3, but some massive clusters have 100 brokers due to horizontal scaling.
 * **Partition**

A partition is a distributed queue. The total number of partitions is called a "partition count". A partition key is used to decide which partition gets which record. If no field is specified as the partition key, Kafka will assign a random partition. Messages are read in order within a partition, but in parallel across partitions.
 * **Offset** 

An offset is an individual record within a partition. Offsets are numbered starting from zero and increasing by one as each record is added. When a consumer has processed data received from Kafka, it commits the offsets. This way, if a consumer process dies, it will be able to read back from where it left off.

#### Describe Kafka Producers and Consumers
Producers capture and queue data; consumers read data from the queue.

#### How are consumers and consumer groups different in Kafka? 
* Helpful resource: [Consumers](https://youtu.be/lAdG16KaHLs)
* Helpful resource: [Confluent Consumer Overview](https://youtu.be/Z9g4jMQwog0)    

A consumer is the actual reader of the data; a consumer group is a grouping of consumers. If there is only one consumer for a topic, that topic will always subscribe to all partitions within that topic. Messages from each partition will always be read in the order from that partition; messages across partitions will be roughly in order. If there is a second instance of that same consumer, Kafka automatically re-balances the cluster and attempts to distribute partitions fairly across the consumers within the consumer group. That rebalancing process repeats every time you add a consumer group instance. This makes each consumer horizontally and elastically scalable. You can deploy, at most, as many consumers as you have partitions.  
One way you might see consumer groups utilized, is when an application has one consumer group for the mobile version and another for the desktop version of that application, reading the same data from the same partitions. These groups will usually read different offsets at different times, but within the same consumer group will still move in order.

#### How are Kafka offsets different than partitions? 
A partition is a queue within a topic. An offset is a particular record within a partition, identified with a unique ID. The first offset in a partition has a unique ID of 0, the second has an ID of 1, so on and so forth.

#### How is data assigned to a specific partition in Kafka? 
If a partition key is defined, it's assigned based on the partition key. If no partition key is assigned, Kafka assigns partitions at random.

#### Describe immutability - Is data on a Kafka topic immutable? 
Kafka records are immutable. The records themselves cannot be changed, updated, or moved around. Records can be appended to a partition, and they can be deleted via retention time, but this is not handled by the producer. If you were to read a record and "make a change" to it, that change would actually be an entirely new record appended to the end of the partition.  
*Source: https://stackoverflow.com/questions/58224758/what-does-it-really-mean-by-kafka-partitions-are-immutable*

#### How is data replicated across brokers in kafka? If you have a replication factor of 3 and 3 brokers, explain how data is spread across brokers
* Helpful resource [Brokers and Replication factors](https://youtu.be/ZOU7PJWZU9w)  

Replication is handled using a topic replication factor- for example, a topic replication factor of three means that there is one "leader" partition on a given broker, then one backup partition on each of the remaining brokers. If one or two brokers go down, the third broker can hold down the fort until service is returned. You should always have a replication factor >1, usually 2 or 3. At any time, only one broker can be the leader for a given partition, and only that leader can send/recieve data. The remaining replicates simply sync data from the leader. Each partition has one leader and multiple ISR (in-sync replica).

#### What was the most fascinating aspect of Kafka to you while learning? 
I was initially very intimidated by Kafka, so I was glad to find that once it was broken down and all the terms defined that I could easily understand the way that it works. I found replication very interesting, since it was the part that was least intuitive to me.
