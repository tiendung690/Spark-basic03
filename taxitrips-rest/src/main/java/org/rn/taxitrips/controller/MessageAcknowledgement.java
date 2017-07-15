package org.rn.taxitrips.controller;

import com.google.common.base.MoreObjects;

public class MessageAcknowledgement {

	private String id;
	private String received;
	private String response;

	public MessageAcknowledgement() {

	}

	public MessageAcknowledgement(String id, String received, String response) {
		this.id = id;
		this.received = received;
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("received", received).add("response", response)
				.toString();
	}
}
