package com.devoteam.dls.dao;

import java.sql.Timestamp;

public class Sender {   
	
	
	
	private String senderId;
	private String senderName;
	private Timestamp timestamp;

	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Sender pirnt [senderId=" + senderId + ", senderName=" + senderName + ", timestamp=" + timestamp + "]";
	}
}
