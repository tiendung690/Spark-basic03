package org.rn.taxitrips.service;

import org.rn.taxitrips.controller.MessageAcknowledgement;
import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.domain.TripStart;

public interface TripStartService {
	public MessageAcknowledgement saveTripStart(TripStart tripStart);

	public TripStart getTripStartById(String tripID);
}
