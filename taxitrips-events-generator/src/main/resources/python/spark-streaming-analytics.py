'''
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * 
 *
 * @author Rakesh Nagar
 * @since 1.0
 */
 
** spark-submit command ** 
spark-submit --packages org.apache.spark:spark-core_2.11:1.5.2,org.apache.spark:spark-streaming_2.11:1.5.2,org.apache.spark:spark-sql-kafka-0-10_2.11:2.1.0,org.apache.spark:spark-streaming-kafka_2.11:1.5.2,org.apache.kafka:kafka_2.11:0.11.0.0,org.apache.kafka:kafka-clients:0.11.0.0 D:\Rakesh\Projects\Github\taxi-trip-data-end-to-end\taxi-trips\src\main\resources\spark-streaming-analytics.py
'''
import argparse
from datetime import datetime
import json
from pyspark.sql import SparkSession
from pyspark.sql.functions import *
from pyspark.sql.functions import lit
from pyspark.sql.types import *
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils


spark = SparkSession.builder.appName("StreamingSQL").getOrCreate()
KAFKA_SERVERS = ""
TRIPSTART_TOPIC = ""
TRIPEND_TOPIC = ""

def inittripstart():    
    tripstartrows = spark.readStream.format("kafka")\
        .option("kafka.bootstrap.servers", KAFKA_SERVERS)\
        .option("subscribe", TRIPSTART_TOPIC)\
        .option("startingOffsets", "latest")\
        .load()

    tripstartrows.printSchema()

    tripstartschema = StructType().add("tripID", StringType()) \
        .add("taxiID", StringType()) \
        .add("companyName", StringType()) \
        .add("pickupCensusTract", LongType()) \
        .add("pickupCommunityAreaCode", IntegerType()) \
        .add("pickupCentroidLatitude", DoubleType()) \
        .add("pickupCentroidLongitude", DoubleType()) \
        .add("pickupCentroidLocation", StringType()) \
        .add("communityAreaCode", IntegerType())

    parsedRows = tripstartrows.select(col("timestamp"), col("key").cast("string"), from_json(col("value").cast("string"), tripstartschema).alias("data"))
    parsedRows.printSchema()
    
    parsedRows = parsedRows.select(col("timestamp"), \
                                col("key"), \
                                col("data.tripID"), \
                                col("data.taxiID"), \
                                col("data.companyName"), \
                                col("data.pickupCensusTract"), \
                                col("data.pickupCommunityAreaCode"), \
                                col("data.pickupCentroidLatitude"), \
                                col("data.pickupCentroidLongitude"), \
                                col("data.pickupCentroidLocation"), \
                                col("data.communityAreaCode"))
    
    parsedRows.printSchema()
                                    
    return parsedRows

def inittripend():
    tripendrows = spark.readStream.format("kafka")\
        .option("kafka.bootstrap.servers", KAFKA_SERVERS)\
        .option("subscribe", TRIPEND_TOPIC)\
        .option("startingOffsets", "latest")\
        .load()

    tripendrows.printSchema()

    tripendschema = StructType().add("tripID", StringType()) \
        .add("taxiID", StringType()) \
        .add("companyName", StringType()) \
        .add("tripSeconds", IntegerType()) \
        .add("tripEndTime", StringType()) \
        .add("dropoffCensusTract", LongType()) \
        .add("dropoffCommunityArea", IntegerType()) \
        .add("fare", DoubleType()) \
        .add("tips", DoubleType()) \
        .add("tolls", DoubleType()) \
        .add("extras", DoubleType()) \
        .add("tripTotal", DoubleType()) \
        .add("paymentType", StringType()) \
        .add("dropoffCentroidLatitude", DoubleType()) \
        .add("dropoffCentroidLongitude", DoubleType()) \
        .add("dropoffCentroidLocation", StringType())        

    parsedRows = tripendrows.select(col("timestamp"), \
                                    col("key").cast("string"), \
                                    from_json(col("value").cast("string"), tripendschema).alias("data"))    
    parsedRows.printSchema()
    
    parsedRows = parsedRows.select(col("timestamp"), \
                                    col("key"), \
                                    col("data.tripID"), \
                                    col("data.taxiID"), \
                                    col("data.companyName"), \
                                    col("data.tripSeconds"), \
                                    col("data.tripEndTime"), \
                                    col("data.dropoffCensusTract"), \
                                    col("data.dropoffCommunityArea"), \
                                    col("data.fare"), \
                                    col("data.tips"), \
                                    col("data.tolls"), \
                                    col("data.extras"), \
                                    col("data.tripTotal"), \
                                    col("data.paymentType"), \
                                    col("data.dropoffCentroidLatitude"), \
                                    col("data.dropoffCentroidLongitude"), \
                                    col("data.dropoffCentroidLocation"))
    parsedRows.printSchema()
                                    
    return parsedRows

def continuousTimeWindowCount(rows, queryName):
    windowedCounts = rows.withWatermark("timestamp", "30 minutes").\
        groupBy(
            window(rows.timestamp, "30 seconds", "30 seconds"),
            rows.companyName
        ).count().orderBy("window", ascending=False)

    windowedCounts.printSchema()

# # Writing Stream to Kafka is not supported in Spark 2.1, it is expected to be available in 2.2   
#     query = windowedCounts.select(to_json(struct("companyName", "window")).alias("key"), col("count").cast("string").alias("value")) \
#                 .writeStream \
#                 .format("kafka") \
#                 .option("kafka.bootstrap.servers", KAFKA_SERVERS)\
#                 .option("topic", "trip-summary-stats") \
#                 .option("checkpointLocation", "c:\\temp\\streaming\\checkpoint\\") \
#                 .outputMode("complete") \
#                 .start()
  
    query = windowedCounts \
        .withColumn("eventType", lit(queryName)) \
        .writeStream \
        .queryName(queryName) \
        .outputMode("complete") \
        .option('truncate', 'false') \
        .option('numRows', '10') \
        .format("console") \
        .start()
            
#     query.awaitTermination()

def continuousCount(rows, queryName):
    count = rows.groupBy("companyName").count().orderBy("count", ascending=False)
#     count = rows.select("timestamp", "companyName").groupBy("timestamp", "companyName").count().alias("count").orderBy("count", ascending=False)
    
#     count = count.withColumn("timestamp", lit(datetime.now())) 
    count.printSchema()

    query = count \
        .withColumn("eventType", lit(queryName)) \
        .writeStream \
        .trigger(processingTime='120 seconds') \
        .queryName(queryName) \
        .outputMode("complete") \
        .option('truncate', 'false') \
        .format("console") \
        .start()
    
#     query.awaitTermination()

# # Not Supported:: Inner join between two streaming DataFrames/Datasets is not supported
# # Not Supported:: Full outer joins with streaming DataFrames/Datasets are not supported
# https://www.mail-archive.com/commits@spark.apache.org/msg16135.html
def processCompletedTrips(tripstartDF, tripendDF):    
#     https://forums.databricks.com/questions/876/is-there-a-better-method-to-join-two-dataframes-an.html
# # Inner Join <- Not Supported
    completedTripsDF = tripstartDF.join(tripendDF, tripstartDF.tripID == tripendDF.tripID).drop(tripendDF.key).drop(tripendDF.timestamp).drop(tripendDF.companyName)
# # Outer Join <- Not Supported
    completedTripsDF2 = tripstartDF.join(tripendDF, tripstartDF.tripID == tripendDF.tripID, "outer").drop(tripendDF.key).drop(tripendDF.timestamp).drop(tripendDF.companyName)
    completedTripsDF.printSchema()
    
    return completedTripsDF
    
def parseArguments():
    parser = argparse.ArgumentParser()
    parser.add_argument('--kafka_servers', default="ubuntu-lab:9092")
    parser.add_argument('--tripstart_topic', default="tripstart")
    parser.add_argument('--tripend_topic', default="tripend")
    
    args = parser.parse_args()
    print(args)
    
    global KAFKA_SERVERS
    global TRIPSTART_TOPIC
    global TRIPEND_TOPIC
    
    KAFKA_SERVERS = args.kafka_servers
    TRIPSTART_TOPIC = args.tripstart_topic
    TRIPEND_TOPIC = args.tripend_topic


##################################################################################################################

parseArguments()

tripstartDF = inittripstart()
tripendDF = inittripend()

continuousCount(tripstartDF, "tripstart events")
continuousCount(tripendDF, "tripend events")
# continuousTimeWindowCount(tripstartDF, "tripstart events")
# continuousTimeWindowCount(tripendDF, "tripend events")

# # Stream joins are not supported
# completedTripsDF = processCompletedTrips(tripstartDF, tripendDF)
# continuousTimeWindowCount(completedTripsDF, "completedTripsDF - continuousTimeWindowCount")

# block until any one of them terminates
spark.streams.awaitAnyTermination() 
