package org.rn.taxitrips.kafka.producer.web;

import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.kafka.producer.service.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleKafkaMessageController {

	@Autowired
	private MessageDispatcher messageDispatcher;

	@GetMapping("/generateWork")
	public boolean sendMessage(TripStart tripStart) {
		return this.messageDispatcher.dispatchTripStart(tripStart);
	}
}
