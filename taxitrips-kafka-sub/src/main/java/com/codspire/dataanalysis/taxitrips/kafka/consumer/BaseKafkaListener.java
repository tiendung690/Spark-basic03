package com.codspire.dataanalysis.taxitrips.kafka.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

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

//@Component
public abstract class BaseKafkaListener implements SmartLifecycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseKafkaListener.class);

	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	// private final KafkaConsumer<String, WorkUnit> kafkaWorkUnitsConsumer;

	private volatile boolean running = false;

	// public KafkaListener(KafkaConsumer<String, WorkUnit>
	// kafkaWorkUnitsConsumer) {
	// this.kafkaWorkUnitsConsumer = kafkaWorkUnitsConsumer;
	// }

	// @Override
	// public void start() {
	// WorkUnitsConsumer workUnitsConsumer = new
	// WorkUnitsConsumer(this.kafkaWorkUnitsConsumer);
	// executorService.submit(workUnitsConsumer);
	// this.running = true;
	// }

	protected void submitRequest(Runnable runnable) {
		executorService.submit(runnable);
		this.running = true;
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {

	}

	@Override
	public int getPhase() {
		return 0;
	}
}