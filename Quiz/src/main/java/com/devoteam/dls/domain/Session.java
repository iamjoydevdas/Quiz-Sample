package com.devoteam.dls.domain;

import java.util.List;

public class Session {
	private Long sessionId;
	private int quizType;
	private String winner;
	private List<SessionQuestions> questions;
	
	public List<SessionQuestions> getQuestions() {
		return questions;
	}
	public void setQuestions(List<SessionQuestions> questions) {
		this.questions = questions;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public int getQuizType() {
		return quizType;
	}
	public void setQuizType(int quizType) {
		this.quizType = quizType;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}

}
