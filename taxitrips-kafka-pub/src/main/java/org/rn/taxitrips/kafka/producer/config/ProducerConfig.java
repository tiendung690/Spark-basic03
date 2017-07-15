package org.rn.taxitrips.kafka.producer.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.kafka.serializer.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
