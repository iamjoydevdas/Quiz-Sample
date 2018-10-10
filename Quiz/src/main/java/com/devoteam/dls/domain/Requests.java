package com.devoteam.dls.domain;

import java.util.Date;

public class Requests {
	private String sender;
	private String receiver;
	private Date requestTime;
	private Boolean accepted;
	private Session session;
	private int challengeType;
	
	public int getChallengeType() {
		return challengeType;
	}
	public void setChallengeType(int challengeType) {
		this.challengeType = challengeType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Boolean getAccepted() {
		return accepted;
	}
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
}
