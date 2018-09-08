package com.devoteam.dls.domain;

public class SessionQuestions {
	private Long questionId;
	private String senderAnswer;
	private String receiverAnswer;
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getSenderAnswer() {
		return senderAnswer;
	}
	public void setSenderAnswer(String senderAnswer) {
		this.senderAnswer = senderAnswer;
	}
	public String getReceiverAnswer() {
		return receiverAnswer;
	}
	public void setReceiverAnswer(String receiverAnswer) {
		this.receiverAnswer = receiverAnswer;
	}
	
}
