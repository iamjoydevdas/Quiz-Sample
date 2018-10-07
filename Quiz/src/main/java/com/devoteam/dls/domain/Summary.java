package com.devoteam.dls.domain;

public class Summary implements AnswerCheck {
	private int sessionId;
	private String question;
	private String answer;
	private boolean senderAnswer;
	private boolean receiverAnswer;
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isSenderAnswer() {
		return senderAnswer;
	}
	public void setSenderAnswer(boolean senderAnswer) {
		this.senderAnswer = senderAnswer;
	}
	public boolean isReceiverAnswer() {
		return receiverAnswer;
	}
	public void setReceiverAnswer(boolean receiverAnswer) {
		this.receiverAnswer = receiverAnswer;
	}
	@Override
	public String toString() {
		return "Summary [sessionId=" + sessionId + ", question=" + question + ", answer=" + answer + ", senderAnswer="
				+ senderAnswer + ", receiverAnswer=" + receiverAnswer + "]";
	}
	@Override
	public String checkAnswerValue(boolean v) {
		// TODO Auto-generated method stub
		return null;
	}
}
