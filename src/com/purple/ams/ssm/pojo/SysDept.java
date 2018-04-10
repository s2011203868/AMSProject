package com.purple.ams.ssm.pojo;

import java.io.Serializable;

public class SysDept implements Serializable {

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String level;
	private String order;
	private String parentid;
	private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public SysDept(String id, String name, String level, String order, String parentid, int status) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.order = order;
		this.parentid = parentid;
		this.status = status;
	}
	public SysDept() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
