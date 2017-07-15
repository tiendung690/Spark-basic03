package com.codspire.dataanalysis.taxitrips.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codspire.dataanalysis.taxitrips.controller.MessageAcknowledgement;
import com.codspire.dataanalysis.taxitrips.domain.TripStart;
import com.codspire.dataanalysis.taxitrips.kafka.producer.service.MessageDispatcher;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * 
 *
 * @author Rakesh Nagar
 * @since 1.0
 */
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