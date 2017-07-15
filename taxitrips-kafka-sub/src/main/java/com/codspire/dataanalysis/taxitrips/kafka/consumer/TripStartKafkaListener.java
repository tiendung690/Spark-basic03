package com.codspire.dataanalysis.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import com.codspire.dataanalysis.taxitrips.domain.TripStart;

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

@Component
public class TripStartKafkaListener extends BaseKafkaListener implements SmartLifecycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripStartKafkaListener.class);
	private final KafkaConsumer<String, TripStart> kafkaTripStartConsumer;

	public TripStartKafkaListener(KafkaConsumer<String, TripStart> kafkaTripStartConsumer) {
		this.kafkaTripStartConsumer = kafkaTripStartConsumer;
	}

	@Override
	public void start() {
		TripStartConsumer tripStartConsumer = new TripStartConsumer(this.kafkaTripStartConsumer);
		submitRequest(tripStartConsumer);
	}
}
