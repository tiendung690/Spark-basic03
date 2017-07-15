package org.rn.taxitrips.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TripEnd {
	private String tripID;
	private String taxiID;
	private String companyName;
	private Integer tripSeconds;
	private Timestamp tripEndTime;
	private Long dropoffCensusTract;
	private Integer dropoffCommunityArea;

	private Double fare;
	private Double tips;
	private Double tolls;
	private Double extras;
	private Double tripTotal;

	private String paymentType;

	private Double dropoffCentroidLatitude;
	private Double dropoffCentroidLongitude;
	private String dropoffCentroidLocation;

	public TripEnd() {
	}

	@JsonCreator
	public TripEnd(@JsonProperty("tripID") String tripID, 
			@JsonProperty("taxiID") String taxiID, 
			@JsonProperty("companyName") String companyName, 
			@JsonProperty("tripSeconds") Integer tripSeconds,
			@JsonProperty("tripEndTime") Timestamp tripEndTime, 
			@JsonProperty("dropoffCensusTract") Long dropoffCensusTract,
			@JsonProperty("dropoffCommunityArea") Integer dropoffCommunityArea, 
			@JsonProperty("fare") Double fare, 
			@JsonProperty("tips") Double tips, 
			@JsonProperty("tolls") Double tolls, 
			@JsonProperty("extras") Double extras, 
			@JsonProperty("tripTotal") Double tripTotal,
			@JsonProperty("paymentType") String paymentType, 
			@JsonProperty("dropoffCentroidLatitude") Double dropoffCentroidLatitude, 
			@JsonProperty("dropoffCentroidLongitude") Double dropoffCentroidLongitude,
			@JsonProperty("dropoffCentroidLocation") String dropoffCentroidLocation) {
		super();
		this.tripID = tripID;
		this.taxiID = taxiID;
		this.companyName = companyName;
		this.tripSeconds = tripSeconds;
		this.tripEndTime = tripEndTime;
		this.dropoffCensusTract = dropoffCensusTract;
		this.dropoffCommunityArea = dropoffCommunityArea;
		this.fare = fare;
		this.tips = tips;
		this.tolls = tolls;
		this.extras = extras;
		this.tripTotal = tripTotal;
		this.paymentType = paymentType;
		this.dropoffCentroidLatitude = dropoffCentroidLatitude;
		this.dropoffCentroidLongitude = dropoffCentroidLongitude;
		this.dropoffCentroidLocation = dropoffCentroidLocation;
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

	public Timestamp getTripEndTime() {
		return tripEndTime;
	}

	public void setTripEndTime(Timestamp tripEndTime) {
		this.tripEndTime = tripEndTime;
	}

	public Long getDropoffCensusTract() {
		return dropoffCensusTract;
	}

	public void setDropoffCensusTract(Long dropoffCensusTract) {
		this.dropoffCensusTract = dropoffCensusTract;
	}

	public Integer getDropoffCommunityArea() {
		return dropoffCommunityArea;
	}

	public void setDropoffCommunityArea(Integer dropoffCommunityArea) {
		this.dropoffCommunityArea = dropoffCommunityArea;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getTips() {
		return tips;
	}

	public void setTips(Double tips) {
		this.tips = tips;
	}

	public Double getTolls() {
		return tolls;
	}

	public void setTolls(Double tolls) {
		this.tolls = tolls;
	}

	public Double getExtras() {
		return extras;
	}

	public void setExtras(Double extras) {
		this.extras = extras;
	}

	public Double getTripTotal() {
		return tripTotal;
	}

	public void setTripTotal(Double tripTotal) {
		this.tripTotal = tripTotal;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getDropoffCentroidLatitude() {
		return dropoffCentroidLatitude;
	}

	public void setDropoffCentroidLatitude(Double dropoffCentroidLatitude) {
		this.dropoffCentroidLatitude = dropoffCentroidLatitude;
	}

	public Double getDropoffCentroidLongitude() {
		return dropoffCentroidLongitude;
	}

	public void setDropoffCentroidLongitude(Double dropoffCentroidLongitude) {
		this.dropoffCentroidLongitude = dropoffCentroidLongitude;
	}

	public String getDropoffCentroidLocation() {
		return dropoffCentroidLocation;
	}

	public void setDropoffCentroidLocation(String dropoffCentroidLocation) {
		this.dropoffCentroidLocation = dropoffCentroidLocation;
	}

	
	public Integer getTripSeconds() {
		return tripSeconds;
	}

	public void setTripSeconds(Integer tripSeconds) {
		this.tripSeconds = tripSeconds;
	}

	@Override
	public String toString() {
		return "TripEnd [tripID=" + tripID + ", taxiID=" + taxiID + ", companyName=" + companyName + ", tripSeconds="
				+ tripSeconds + ", tripEndTime=" + tripEndTime + ", dropoffCensusTract=" + dropoffCensusTract
				+ ", dropoffCommunityArea=" + dropoffCommunityArea + ", fare=" + fare + ", tips=" + tips + ", tolls="
				+ tolls + ", extras=" + extras + ", tripTotal=" + tripTotal + ", paymentType=" + paymentType
				+ ", dropoffCentroidLatitude=" + dropoffCentroidLatitude + ", dropoffCentroidLongitude="
				+ dropoffCentroidLongitude + ", dropoffCentroidLocation=" + dropoffCentroidLocation + "]";
	}
}
