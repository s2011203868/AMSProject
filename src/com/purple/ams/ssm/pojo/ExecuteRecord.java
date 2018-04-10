package com.purple.ams.ssm.pojo;

import java.io.Serializable;
/**
 * 
 * @ClassName: ExecuteRecord 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:51:06
 */
public class ExecuteRecord implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    private static final long serialVersionUID = 1L;
    private int id;
	private String description;
	private String uri;
	private String ipaddress;
	private String usetime;
	private String executor;
	private String executetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public String getExecutetime() {
		return executetime;
	}
	public void setExecutetime(String executetime) {
		this.executetime = executetime;
	}
	public ExecuteRecord(int id, String description, String uri, String ipaddress, String usetime, String executor,
			String executetime) {
		super();
		this.id = id;
		this.description = description;
		this.uri = uri;
		this.ipaddress = ipaddress;
		this.usetime = usetime;
		this.executor = executor;
		this.executetime = executetime;
	}
	public ExecuteRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
}
