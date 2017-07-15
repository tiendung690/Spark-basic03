package com.codspire.dataanalysis.taxitrips.kafka.consumer.config;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codspire.dataanalysis.taxitrips.domain.TripEnd;
import com.codspire.dataanalysis.taxitrips.domain.TripStart;
import com.codspire.dataanalysis.taxitrips.kafka.serializer.JsonDeserializer;

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
public class ConsumerConfig {

	/**
	 * auto.offset.reset
	 * 	earliest: automatically reset the offset to the earliest offset
	 * 	latest: automatically reset the offset to the latest offset
	 * 	none: throw exception to the consumer if no previous offset is found for the consumer's group
	 *  
	 * @param kafkaTripStartConsumerProperties
	 * @return
	 */
	@Bean
	public KafkaConsumer<String, TripStart> tripStartConsumer(
			KafkaTripStartConsumerProperties kafkaTripStartConsumerProperties) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaTripStartConsumerProperties.getBootstrap());
		props.put("group.id", kafkaTripStartConsumerProperties.getGroup());
		props.put("auto.offset.reset", "earliest");
		// props.put("key.deserializer",
		// "org.apache.kafka.common.serialization.StringDeserializer");
		// props.put("value.deserializer",
		// "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, TripStart> consumer = new KafkaConsumer<>(props, stringKeyDeserializer(),
				tripStartJsonValueDeserializer());
		consumer.subscribe(Collections.singletonList(kafkaTripStartConsumerProperties.getTopic()));
		return consumer;
	}

	/**
	 * auto.offset.reset
	 * 	earliest: automatically reset the offset to the earliest offset
	 * 	latest: automatically reset the offset to the latest offset
	 * 	none: throw exception to the consumer if no previous offset is found for the consumer's group
	 * 
	 * @param kafkaTripEndConsumerProperties
	 * @return
	 */
	@Bean
	public KafkaConsumer<String, TripEnd> tripEndConsumer(
			KafkaTripEndConsumerProperties kafkaTripEndConsumerProperties) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaTripEndConsumerProperties.getBootstrap());
		props.put("group.id", kafkaTripEndConsumerProperties.getGroup());
		props.put("auto.offset.reset", "earliest");
		// props.put("key.deserializer",
		// "org.apache.kafka.common.serialization.StringDeserializer");
		// props.put("value.deserializer",
		// "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, TripEnd> consumer = new KafkaConsumer<>(props, stringKeyDeserializer(),
				tripEndJsonValueDeserializer());
		consumer.subscribe(Collections.singletonList(kafkaTripEndConsumerProperties.getTopic()));
		return consumer;
	}

	@Bean
	public Deserializer stringKeyDeserializer() {
		return new StringDeserializer();
	}

	@Bean
	public Deserializer tripStartJsonValueDeserializer() {
		return new JsonDeserializer(TripStart.class);
	}

	@Bean
	public Deserializer tripEndJsonValueDeserializer() {
		return new JsonDeserializer(TripEnd.class);
	}
}
