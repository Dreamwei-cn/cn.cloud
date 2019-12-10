package cn.cloud.api.order.entity;

public class Order {
	private String id;
	private String messageString;
	private String messageIdString;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessageString() {
		return messageString;
	}
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}
	public String getMessageIdString() {
		return messageIdString;
	}
	public void setMessageIdString(String messageIdString) {
		this.messageIdString = messageIdString;
	}


}
