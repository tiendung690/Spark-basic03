package org.rn.taxitrips.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rn.taxitrips.domain.TripStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripStartConsumer implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(TripStartConsumer.class);

	private final KafkaConsumer<String, TripStart> tripStartConsumer;

	public TripStartConsumer(KafkaConsumer<String, TripStart> tripStartConsumer) {
		this.tripStartConsumer = tripStartConsumer;
	}

	public void run() {
		try {
			while (true) {
				ConsumerRecords<String, TripStart> records = this.tripStartConsumer.poll(100);
				for (ConsumerRecord<String, TripStart> record : records) {
					log.info(record.toString());
					log.info("---------------TripStartConsumer-----------------");
					log.info(
							"consuming from topic = {}, partition = {}, ts = {}, ts-type = {},  offset = {}, key = {}, value = {}",
							record.topic(), record.partition(), record.timestamp(), record.timestampType(),
							record.offset(), record.key(), record.value());
					log.info("---------------TripStartConsumer-----------------");
				}
			}
		} finally {
			this.tripStartConsumer.close();
		}
	}
}
