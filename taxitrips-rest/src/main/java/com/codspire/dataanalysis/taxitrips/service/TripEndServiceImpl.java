package com.codspire.dataanalysis.taxitrips.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codspire.dataanalysis.taxitrips.controller.MessageAcknowledgement;
import com.codspire.dataanalysis.taxitrips.domain.TripEnd;
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