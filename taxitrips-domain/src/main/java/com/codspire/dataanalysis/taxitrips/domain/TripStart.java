package com.codspire.dataanalysis.taxitrips.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class TripStart {
	private String tripID;
	private String taxiID;
	private String companyName;
	private Timestamp tripStartTime;
	private Long pickupCensusTract;
	private Integer pickupCommunityAreaCode;
	private Double pickupCentroidLatitude;
	private Double pickupCentroidLongitude;
	private String pickupCentroidLocation;
	private Integer communityAreaCode;

	public TripStart() {
	}
	
	@JsonCreator
	public TripStart(@JsonProperty("tripID") String tripID, 
			@JsonProperty("taxiID") String taxiID,
			@JsonProperty("companyName") String companyName, 
			@JsonProperty("tripStartTime") Timestamp tripStartTime, 
			@JsonProperty("pickupCensusTract") Long pickupCensusTract,
			@JsonProperty("pickupCommunityAreaCode") Integer pickupCommunityAreaCode,
			@JsonProperty("communityAreaCode") Integer communityAreaCode,
			@JsonProperty("pickupCentroidLatitude") Double pickupCentroidLatitude,
			@JsonProperty("pickupCentroidLongitude") Double pickupCentroidLongitude,
			@JsonProperty("pickupCentroidLocation") String pickupCentroidLocation) {
		super();
		this.tripID = tripID;
		this.taxiID = taxiID;
		this.companyName = companyName;
		this.tripStartTime = tripStartTime;
		this.pickupCensusTract = pickupCensusTract;
		this.pickupCommunityAreaCode = pickupCommunityAreaCode;
		this.pickupCentroidLatitude = pickupCentroidLatitude;
		this.pickupCentroidLongitude = pickupCentroidLongitude;
		this.pickupCentroidLocation = pickupCentroidLocation;
		this.communityAreaCode = communityAreaCode;
	}
	
	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public String getTaxiID() {
		return taxiID;
	}

	public void setTaxiID(String taxiID) {
		this.taxiID = taxiID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Timestamp getTripStartTime() {
		return tripStartTime;
	}

	public void setTripStartTime(Timestamp tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public Long getPickupCensusTract() {
		return pickupCensusTract;
	}

	public void setPickupCensusTract(Long pickupCensusTract) {
		this.pickupCensusTract = pickupCensusTract;
	}

	public Integer getPickupCommunityAreaCode() {
		return pickupCommunityAreaCode;
	}

	public void setPickupCommunityAreaCode(Integer pickupCommunityAreaCode) {
		this.pickupCommunityAreaCode = pickupCommunityAreaCode;
	}

	public Double getPickupCentroidLatitude() {
		return pickupCentroidLatitude;
	}

	public void setPickupCentroidLatitude(Double pickupCentroidLatitude) {
		this.pickupCentroidLatitude = pickupCentroidLatitude;
	}

	public Double getPickupCentroidLongitude() {
		return pickupCentroidLongitude;
	}

	public void setPickupCentroidLongitude(Double pickupCentroidLongitude) {
		this.pickupCentroidLongitude = pickupCentroidLongitude;
	}

	public String getPickupCentroidLocation() {
		return pickupCentroidLocation;
	}

	public void setPickupCentroidLocation(String pickupCentroidLocation) {
		this.pickupCentroidLocation = pickupCentroidLocation;
	}

	public Integer getCommunityAreaCode() {
		return communityAreaCode;
	}

	public void setCommunityAreaCode(Integer communityAreaCode) {
		this.communityAreaCode = communityAreaCode;
	}

	@Override
	public String toString() {
		return "TripStart {tripID=" + tripID + ", taxiID=" + taxiID + ", companyName=" + companyName
				+ ", tripStartTime=" + tripStartTime + ", pickupCensusTract=" + pickupCensusTract
				+ ", pickupCommunityAreaCode=" + pickupCommunityAreaCode + ", pickupCentroidLatitude="
				+ pickupCentroidLatitude + ", pickupCentroidLongitude=" + pickupCentroidLongitude
				+ ", pickupCentroidLocation=" + pickupCentroidLocation + ", communityAreaCode=" + communityAreaCode
				+ "}";
	}
}
