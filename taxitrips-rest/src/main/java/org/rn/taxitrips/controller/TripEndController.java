package org.rn.taxitrips.controller;

import org.rn.taxitrips.domain.TripEnd;
import org.rn.taxitrips.service.TripEndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tripend")
public class TripEndController {

	@Autowired
	// @Qualifier("hystrixPongClient")
	private TripEndService tripEndService;

	@RequestMapping(method = RequestMethod.POST)
	public MessageAcknowledgement saveTripEnd(@RequestBody TripEnd tripEnd) {
		return this.tripEndService.saveTripEnd(tripEnd);
	}

	@RequestMapping(method = RequestMethod.GET)
	public TripEnd sendMessage(@RequestParam String tripID) {
		return this.tripEndService.getTripEndById(tripID);
	}
}
