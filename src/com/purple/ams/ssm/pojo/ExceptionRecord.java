package com.purple.ams.ssm.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @ClassName: ExceptionRecord 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:50:58
 */
public class ExceptionRecord implements Serializable {

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private int id;
	private String exceptiontype;
	private String uri;
	private String ipaddress;
	private String executor;
	private Date executetime;
	private String datetime;
	private String exceptioncause;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getExceptiontype() {
		return exceptiontype;
	}
	public void setExceptiontype(String exceptiontype) {
		this.exceptiontype = exceptiontype;
	}
	public String getExceptioncause() {
		return exceptioncause;
	}
	public void setExceptioncause(String exceptioncause) {
		this.exceptioncause = exceptioncause;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}


	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public Date getExecutetime() {
		return executetime;
	}
	public void setExecutetime(Date executetime) {
		this.executetime = executetime;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public ExceptionRecord(int id, String exceptiontype, String uri, String ipaddress, String executor,
			Date executetime, String datetime, String exceptioncause) {
		super();
		this.id = id;
		this.exceptiontype = exceptiontype;
		this.uri = uri;
		this.ipaddress = ipaddress;
		this.executor = executor;
		this.executetime = executetime;
		this.datetime = datetime;
		this.exceptioncause = exceptioncause;
	}
	public ExceptionRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
