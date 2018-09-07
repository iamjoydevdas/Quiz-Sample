package com.devoteam.dls.dao;

public class Receiver {
	private String receiverId;
	private String receiverName;
	public String getReceiverId() {  
		
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	@Override
	public String toString() {
		return "Receiver  Print [receiverId=" + receiverId + ", receiverName=" + receiverName + "]";
	}

}
