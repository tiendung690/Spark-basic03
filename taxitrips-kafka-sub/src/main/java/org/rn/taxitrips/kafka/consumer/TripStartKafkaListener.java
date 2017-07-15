package org.rn.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rn.taxitrips.domain.TripStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

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
