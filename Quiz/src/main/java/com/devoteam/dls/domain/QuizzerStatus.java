package com.devoteam.dls.domain;

public enum QuizzerStatus {
	INACTIVE(0), ACTIVE(1), DEACTIVATED(2);
	
	private int status;
	
	private QuizzerStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public static QuizzerStatus findByStatus(int status) {
		for (QuizzerStatus quizzerStatus : values()) {
			if (quizzerStatus.getStatus() == status) {
				return quizzerStatus;
			}
		}
		return null;
	}
}
