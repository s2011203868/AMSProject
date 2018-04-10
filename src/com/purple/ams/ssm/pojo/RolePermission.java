package com.purple.ams.ssm.pojo;

import java.io.Serializable;
/**
 * 
 * @ClassName: RolePermission 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:51:36
 */
public class RolePermission implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private String id;
	private String sys_role_id;
	private String sys_permission_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSys_role_id() {
		return sys_role_id;
	}
	public void setSys_role_id(String sys_role_id) {
		this.sys_role_id = sys_role_id;
	}
	public String getSys_permission_id() {
		return sys_permission_id;
	}
	public void setSys_permission_id(String sys_permission_id) {
		this.sys_permission_id = sys_permission_id;
	}
	public RolePermission(String id, String sys_role_id, String sys_permission_id) {
		super();
		this.id = id;
		this.sys_role_id = sys_role_id;
		this.sys_permission_id = sys_permission_id;
	}
	public RolePermission() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
