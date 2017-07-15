package org.rn.taxitrips.controller;

import org.rn.taxitrips.domain.TripStart;
import org.rn.taxitrips.service.TripStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tripstart")
public class TripStartController {

	@Autowired
	// @Qualifier("hystrixPongClient")
	private TripStartService tripStartService;

	@RequestMapping(method = RequestMethod.POST)
	public MessageAcknowledgement saveTripStart(@RequestBody TripStart tripStart) {
		return this.tripStartService.saveTripStart(tripStart);
	}

	@RequestMapping(method = RequestMethod.GET)
	public TripStart sendMessage(@RequestParam String tripID) {
		return this.tripStartService.getTripStartById(tripID);
	}
}
