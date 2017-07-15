# chicago-taxi-trips-streaming-analysis
Streaming analytics using Spark Structured Streaming, Kafka with mocked Taxi Trip Events
```bash
git clone https://github.com/codspire/chicago-taxi-trips-streaming-analysis.git
cd chicago-taxi-trips-streaming-analysis/
chmod +x mvnw
./mvnw clean package

[ec2-user@ip-172-31-85-222 ~]$ sudo yum install collectd-zookeeper

wget http://apache.cs.utah.edu/zookeeper/zookeeper-3.4.10/zookeeper-3.4.10.tar.gz

cp conf/zoo_sample.cfg conf/zoo.cfg

sudo ./bin/zkServer.sh start

wget http://apache.cs.utah.edu/kafka/0.11.0.0/kafka_2.11-0.11.0.0.tgz

https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-14-04

kafka_2.11-0.11.0.0.tgz
zookeeper-3.4.10.tar.gz
[ec2-user@ip-172-31-85-222 tmp]$ python -V
Python 3.5.1

[ec2-user@ip-172-31-85-222 tmp]$ pip -V
pip 9.0.1 from /usr/local/lib/python3.5/site-packages (python 3.5)

[ec2-user@ip-172-31-85-222 tmp]$ spark-submit --version
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.1.1
      /_/

Using Scala version 2.11.8, Java HotSpot(TM) 64-Bit Server VM, 1.8.0_131
Branch
Compiled by user jenkins on 2017-04-25T23:51:10Z

[ec2-user@ip-172-31-85-222 tmp]$ java -version
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)

```
```bash

cd /home/ec2-user/applications/chicago-taxi-trips-streaming-analysis
# REST Services
java -jar taxitrips-rest/target/taxitrips-rest-0.0.1-SNAPSHOT.jar

# Kafka Consumer
java -jar taxitrips-kafka-sub/target/taxitrips-kafka-sub-0.0.1-SNAPSHOT.jar

# Mock Events

$ cd /home/ec2-user/applications/chicago-taxi-trips-streaming-analysis/taxitrips-events-generator/src/main/resources/python

# Trip End Events

$ python tripend-events2.py --endpoint http://ec2-34-204-199-175.compute-1.amazonaws.com:9090/tripend --datafile ../test-data-v2-aa.csv --delay 0.3

# Trip Start Mock Events
$ python tripstart-events2.py --endpoint http://ec2-34-204-199-175.compute-1.amazonaws.com:9090/tripstart --datafile ../test-data-v2-aa.csv --delay 0.2

# Spark Streaming SQL

$ spark-submit --packages org.apache.spark:spark-core_2.11:1.5.2,org.apache.spark:spark-streaming_2.11:1.5.2,org.apache.spark:spark-sql-kafka-0-10_2.11:2.1.0,org.apache.spark:spark-streaming-kafka_2.11:1.5.2,org.apache.kafka:kafka_2.11:0.11.0.0,org.apache.kafka:kafka-clients:0.11.0.0 ./spark-streaming-analytics.py --kafka_servers localhost:9092

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