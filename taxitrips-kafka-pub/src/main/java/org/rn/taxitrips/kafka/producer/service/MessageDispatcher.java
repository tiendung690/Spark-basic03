package org.rn.taxitrips.kafka.producer.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.kafka.producer.config.KafkaTripEndProducerProperties;
import org.rn.taxitrips.kafka.producer.config.KafkaTripStartProducerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
