package org.rn.taxitrips.service;

import org.rn.taxitrips.controller.MessageAcknowledgement;
import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.kafka.producer.service.MessageDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripEndServiceImpl implements TripEndService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TripEndServiceImpl.class);

	@Autowired
	private MessageDispatcher messageDispatcher;

	@Override
	public MessageAcknowledgement saveTripEnd(TripEnd tripEnd) {

		LOGGER.debug(tripEnd.toString());

		boolean status = messageDispatcher.dispatchTripEnd(tripEnd);

		MessageAcknowledgement acknowledgement = new MessageAcknowledgement(tripEnd.getTripID(), tripEnd.toString(),
				"Received");

		System.out.println(tripEnd);

		return acknowledgement;
	}
	
	@Override
	public TripEnd getTripEndById(String tripID) {
		TripEnd tripEnd = new TripEnd();
		tripEnd.setTripID(tripID);

		LOGGER.debug(tripEnd.toString());

		return tripEnd;
	}
}