package com.codspire.dataanalysis.taxitrips.kafka.producer.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codspire.dataanalysis.taxitrips.domain.TripEnd;
import com.codspire.dataanalysis.taxitrips.domain.TripStart;
import com.codspire.dataanalysis.taxitrips.kafka.serializer.JsonSerializer;

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

@Configuration
public class ProducerConfig {

	@Bean
	public KafkaProducer<String, TripStart> tripStartProducer(
			KafkaTripStartProducerProperties kafkaTripStartProducerProperties) {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", kafkaTripStartProducerProperties.getBootstrap());

		// kafkaProps.put("key.serializer",
		// "org.apache.kafka.common.serialization.StringSerializer");
		// kafkaProps.put("value.serializer",
		// "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, TripStart> producer = new KafkaProducer<>(kafkaProps, stringKeySerializer(),
				tripStartJsonSerializer());
		return producer;
	}

	@Bean
	public KafkaProducer<String, TripEnd> tripEndProducer(
			KafkaTripEndProducerProperties kafkaTripEndProducerProperties) {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", kafkaTripEndProducerProperties.getBootstrap());

		// kafkaProps.put("key.serializer",
		// "org.apache.kafka.common.serialization.StringSerializer");
		// kafkaProps.put("value.serializer",
		// "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, TripEnd> producer = new KafkaProducer<>(kafkaProps, stringKeySerializer(),
				tripStartJsonSerializer());
		return producer;
	}

	@Bean
	public Serializer stringKeySerializer() {
		return new StringSerializer();
	}

	@Bean
	public Serializer tripStartJsonSerializer() {
		return new JsonSerializer();
	}
}
