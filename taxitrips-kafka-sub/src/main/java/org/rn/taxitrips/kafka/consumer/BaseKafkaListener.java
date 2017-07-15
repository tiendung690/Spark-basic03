package org.rn.taxitrips.kafka.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

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