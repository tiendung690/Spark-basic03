package com.codspire.dataanalysis.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codspire.dataanalysis.taxitrips.domain.TripEnd;

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
public class TripEndConsumer implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(TripEndConsumer.class);

	private final KafkaConsumer<String, TripEnd> tripEndConsumer;

	public TripEndConsumer(KafkaConsumer<String, TripEnd> tripEndConsumer) {
		this.tripEndConsumer = tripEndConsumer;
	}

	public void run() {
		try {
			while (true) {
				ConsumerRecords<String, TripEnd> records = this.tripEndConsumer.poll(100);
				for (ConsumerRecord<String, TripEnd> record : records) {
					log.info(record.toString());
					log.info("---------------TripEndConsumer-----------------");
					log.info(
							"consuming from topic = {}, partition = {}, ts = {}, ts-type = {},  offset = {}, key = {}, value = {}",
							record.topic(), record.partition(), record.timestamp(), record.timestampType(),
							record.offset(), record.key(), record.value());
					log.info("---------------TripEndConsumer-----------------");
				}
			}
		} finally {
			this.tripEndConsumer.close();
		}
	}
}
