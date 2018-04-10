package com.purple.ams.ssm.pojo;

import java.io.Serializable;
/**
 * @ClassName: Message 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:51:30
 */
public class Message implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    private static final long serialVersionUID = 1L;
    private String statusCode;
	private String message;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(String statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
