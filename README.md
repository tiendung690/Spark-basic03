[TOC]

## Overview
This is a sample application to showcase streaming data analytics on the City Of Chicago Taxi Trips dataset using Spark Structured Streaming, Kafka and Spring Boot.

Below is the high level data flow architecture:

![](design.png)

1. The application exposes two REST endpoints (/tripstart & /tripend) to collect trip start and trip end events
2. The events are pushed to Kafka topics
3. The Spark Streaming pulls the events from Kafka topics and performs rolling aggregates as the events arrive
4. There is a python helper script that creates mock events from a data file and submits them to the REST endpoints

## City of Chicago Taxi Trips Dataset
https://data.cityofchicago.org/Transportation/Taxi-Trips-Dashboard/spcw-brbq
https://data.cityofchicago.org/Transportation/Taxi-Trips/wrvz-psew

## Software Inventory
|Software|Version|
|--------|--------|
|Java|1.8.0_131|
|Kafka|2.11-0.11.0.0|
|ZooKeeper|3.4.10|
|Spark|2.1.1, Using Scala version 2.11.8|
|Python|3.5.1|
|pip|9.0.1|

Installations tips:
https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-14-04
https://gist.github.com/codspire/ee4a46ec054f962d9ef028b27fcb2635

## Build The Project
#### Code Checkout
```bash
$ git clone https://github.com/codspire/chicago-taxi-trips-streaming-analysis.git
```
#### Update Properties
Update the `bootstrap` property with your Kafka `host:port` in the below files:
1. chicago-taxi-trips-streaming-analysis/taxitrips-kafka-sub/src/main/resources/application.yml
2. chicago-taxi-trips-streaming-analysis/taxitrips-rest/src/main/resources/application.yml

#### Build
```bash
$ cd chicago-taxi-trips-streaming-analysis
$ chmod +x mvnw
$ ./mvnw clean package
```
## Start The Application
1. cd to the root folder (i.e. `chicago-taxi-trips-streaming-analysis`)
2. Make sure Kafka and ZooKeeper are already running

#### Start REST Services
```bash
java -jar taxitrips-rest/target/taxitrips-rest-0.0.1-SNAPSHOT.jar
```
#### Start Kafka Consumer (Optional)
Optional step, this will print the Kafka messages to the console for debugging purpose
```bash
java -jar taxitrips-kafka-sub/target/taxitrips-kafka-sub-0.0.1-SNAPSHOT.jar
```
## Publish Mock Events

cd to `chicago-taxi-trips-streaming-analysis/taxitrips-events-generator/src/main/resources/python`

**Note:**
* Replace `<hostname>` placeholder with your hostname
* Use `--datafile` to provide the mock data file path (available under `taxitrips-events-generator/src/main/resources` folder, e.g. test-data-v2-aa.csv, test-data-v2-ab.csv...)
* Use `--delay` to specify the wait time in seconds between REST request submissions (e.g. 0.2 means 3 events per second)

#### Trip Start Mock Events
```bash
$ python tripstart-events2.py \
--endpoint http://<hostname>:9090/tripstart \
--datafile ../test-data-v2-aa.csv \
--delay 0.2
```
#### Trip End Mock Events
```bash
$ python tripend-events2.py \
--endpoint http://<hostname>:9090/tripend \
--datafile ../test-data-v2-aa.csv \
--delay 0.2
```
## Start Spark Streaming Query
cd to `chicago-taxi-trips-streaming-analysis/taxitrips-events-generator/src/main/resources/python`
```bash
$ spark-submit --packages org.apache.spark:spark-core_2.11:1.5.2,\
org.apache.spark:spark-streaming_2.11:1.5.2,\
org.apache.spark:spark-sql-kafka-0-10_2.11:2.1.0,\
org.apache.spark:spark-streaming-kafka_2.11:1.5.2,\
org.apache.kafka:kafka_2.11:0.11.0.0,\
org.apache.kafka:kafka-clients:0.11.0.0 \
./spark-streaming-analytics.py --kafka_servers localhost:9092
```
#### Spark Streaming Query Results
Spark console should show the SQL results for events stream. The current query checks for count of trips started and ended every 30 secs grouped by Cab Company.

Sample Outputs:
```bash
+---------------------------------------------+---------------------------------+-----+----------------+
|window                                       |companyName                      |count|eventType       |
+---------------------------------------------+---------------------------------+-----+----------------+
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Top Cab Affiliation              |3    |tripstart events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Northwest Management LLC         |3    |tripstart events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Dispatch Taxi Affiliation        |25   |tripstart events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|KOAM Taxi Association            |7    |tripstart events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Taxi Affiliation Services        |40   |tripstart events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Choice Taxi Association          |11   |tripstart events|
|[2017-07-15 20:24:00.0,2017-07-15 20:24:30.0]|Northwest Management LLC         |12   |tripstart events|
|[2017-07-15 20:24:00.0,2017-07-15 20:24:30.0]|Choice Taxi Association          |21   |tripstart events|
|[2017-07-15 20:24:00.0,2017-07-15 20:24:30.0]|KOAM Taxi Association            |9    |tripstart events|
|[2017-07-15 20:24:00.0,2017-07-15 20:24:30.0]|Blue Ribbon Taxi Association Inc.|4    |tripstart events|
+---------------------------------------------+---------------------------------+-----+----------------+
only showing top 10 rows
```

```bash
+---------------------------------------------+---------------------------------+-----+--------------+
|window                                       |companyName                      |count|eventType     |
+---------------------------------------------+---------------------------------+-----+--------------+
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Top Cab Affiliation              |2    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|5129 - Mengisti Taxi             |1    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Northwest Management LLC         |3    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Dispatch Taxi Affiliation        |16   |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Blue Ribbon Taxi Association Inc.|2    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|KOAM Taxi Association            |2    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Taxi Affiliation Services        |22   |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|3201 - C&D Cab Co Inc            |1    |tripend events|
|[2017-07-15 20:24:30.0,2017-07-15 20:25:00.0]|Choice Taxi Association          |10   |tripend events|
|[2017-07-15 20:24:00.0,2017-07-15 20:24:30.0]|Northwest Management LLC         |4    |tripend events|
+---------------------------------------------+---------------------------------+-----+--------------+
only showing top 10 rows
```
## What's Next?
1. The streaming results can be written to a persistent store (e.g. S3, HBase etc.) or ElasticSearch cluster which can be used to visualize the analytics in real-time.
2. There are few limitations in Spark version 2.1.1 ([read here](https://www.mail-archive.com/commits@spark.apache.org/msg16135.html)). Another limitation is that there is no kafka format to write streaming datasets to Kafka (i.e. a Kafka sink) and it has to be handled using "foreach operator". The will probably be addressed in Spark 2.2 version ([read here](https://stackoverflow.com/questions/42996293/how-to-write-streaming-dataset-to-kafka))
