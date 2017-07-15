package org.rn.taxitrips.kafka.consumer.config;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.kafka.serializer.JsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
