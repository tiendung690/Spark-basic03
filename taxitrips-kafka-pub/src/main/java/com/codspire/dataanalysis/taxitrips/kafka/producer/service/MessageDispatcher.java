package com.codspire.dataanalysis.taxitrips.kafka.producer.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codspire.dataanalysis.taxitrips.domain.TripEnd;
import com.codspire.dataanalysis.taxitrips.domain.TripStart;
import com.codspire.dataanalysis.taxitrips.kafka.producer.config.KafkaTripEndProducerProperties;
import com.codspire.dataanalysis.taxitrips.kafka.producer.config.KafkaTripStartProducerProperties;

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

@Service
public class MessageDispatcher {

	@Autowired
	private KafkaProducer<String, TripStart> tripStartProducer;

	@Autowired
	private KafkaTripStartProducerProperties kafkaTripStartProducerProperties;

	@Autowired
	private KafkaProducer<String, TripEnd> tripEndProducer;

	@Autowired
	private KafkaTripEndProducerProperties kafkaTripEndProducerProperties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDispatcher.class);

	public boolean dispatchTripStart(TripStart tripStart) {
		ProducerRecord<String, TripStart> record = new ProducerRecord<>(kafkaTripStartProducerProperties.getTopic(),
				tripStart.getTripID(), tripStart);
		try {
			RecordMetadata recordMetadata = this.tripStartProducer.send(record).get();
			
			LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}", recordMetadata.topic(),
					recordMetadata.partition(), recordMetadata.offset(), tripStart);
			
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean dispatchTripEnd(TripEnd tripEnd) {
		ProducerRecord<String, TripEnd> record = new ProducerRecord<>(kafkaTripEndProducerProperties.getTopic(),
				tripEnd.getTripID(), tripEnd);
		try {
			RecordMetadata recordMetadata = this.tripEndProducer.send(record).get();
			
			LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}", recordMetadata.topic(),
					recordMetadata.partition(), recordMetadata.offset(), tripEnd);
			
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
