package org.rn.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rn.taxitrips.domain.TripEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class TripEndKafkaListener extends BaseKafkaListener implements SmartLifecycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(TripEndKafkaListener.class);
	private final KafkaConsumer<String, TripEnd> kafkaTripEndConsumer;

	public TripEndKafkaListener(KafkaConsumer<String, TripEnd> kafkaTripEndConsumer) {
		this.kafkaTripEndConsumer = kafkaTripEndConsumer;
	}

	@Override
	public void start() {
		TripEndConsumer tripEndConsumer = new TripEndConsumer(this.kafkaTripEndConsumer);
		submitRequest(tripEndConsumer);
	}
}
