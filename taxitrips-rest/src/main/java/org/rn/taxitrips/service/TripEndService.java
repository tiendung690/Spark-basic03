package org.rn.taxitrips.service;

import org.rn.taxitrips.controller.MessageAcknowledgement;
import org.rn.taxitrips.domain.TripEnd;

public interface TripEndService {
	public MessageAcknowledgement saveTripEnd(TripEnd tripEnd);
	
	public TripEnd getTripEndById(String tripId);
}
