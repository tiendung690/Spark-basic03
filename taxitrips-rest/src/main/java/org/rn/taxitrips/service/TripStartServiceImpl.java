package org.rn.taxitrips.service;

import org.rn.taxitrips.controller.MessageAcknowledgement;
import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.kafka.producer.service.MessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripStartServiceImpl implements TripStartService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TripStartServiceImpl.class);

	@Autowired
	private MessageDispatcher messageDispatcher;

	@Override
	public MessageAcknowledgement saveTripStart(TripStart tripStart) {

		LOGGER.debug(tripStart.toString());

		boolean status = messageDispatcher.dispatchTripStart(tripStart);

		MessageAcknowledgement acknowledgement = new MessageAcknowledgement(tripStart.getTripID(), tripStart.toString(),
				"Published=" + status);

		System.out.println(tripStart);

		return acknowledgement;
	}

	@Override
	public TripStart getTripStartById(String tripID) {
		TripStart tripStart = new TripStart();
		tripStart.setTripID(tripID);

		LOGGER.debug(tripStart.toString());
		System.out.println(tripStart.toString());

		return tripStart;
	}
}