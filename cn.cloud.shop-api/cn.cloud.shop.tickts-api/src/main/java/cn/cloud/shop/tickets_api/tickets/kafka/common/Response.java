package cn.cloud.shop.tickets_api.tickets.kafka.common;

public class Response {
	
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	

}
