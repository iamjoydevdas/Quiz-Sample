package com.devoteam.dls.domain;

public class OnlineQuizzers extends Quizzer{
	private OnlineStatus onlineStatus;
	private Requests requests;

	public Requests getRequests() {
		return requests;
	}

	public void setRequests(Requests requests) {
		this.requests = requests;
	}

	public OnlineStatus getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(OnlineStatus onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

}
