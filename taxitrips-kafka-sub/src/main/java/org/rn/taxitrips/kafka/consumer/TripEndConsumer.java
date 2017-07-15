package org.rn.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rn.taxitrips.domain.TripEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
