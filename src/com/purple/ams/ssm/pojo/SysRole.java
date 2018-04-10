package com.purple.ams.ssm.pojo;

import java.io.Serializable;
/**
 * 
 * @ClassName: SysRole 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:52:00
 */
public class SysRole implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private String roleid;
	private String rolename;
	private int status;
	private String uri;
	private String target;
	private String order;
	private String level;
	private String parentid;
	private String hasRole;
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHasRole() {
		return hasRole;
	}
	public void setHasRole(String hasRole) {
		this.hasRole = hasRole;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public SysRole(String roleid, String rolename, int status, String uri, String target, String order, String level,
			String parentid, String hasRole) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
		this.status = status;
		this.uri = uri;
		this.target = target;
		this.order = order;
		this.level = level;
		this.parentid = parentid;
		this.hasRole = hasRole;
	}
	public SysRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hasRole == null) ? 0 : hasRole.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((parentid == null) ? 0 : parentid.hashCode());
		result = prime * result + ((roleid == null) ? 0 : roleid.hashCode());
		result = prime * result + ((rolename == null) ? 0 : rolename.hashCode());
		result = prime * result + status;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRole other = (SysRole) obj;
		if (hasRole == null) {
			if (other.hasRole != null)
				return false;
		} else if (!hasRole.equals(other.hasRole))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (parentid == null) {
			if (other.parentid != null)
				return false;
		} else if (!parentid.equals(other.parentid))
			return false;
		if (roleid == null) {
			if (other.roleid != null)
				return false;
		} else if (!roleid.equals(other.roleid))
			return false;
		if (rolename == null) {
			if (other.rolename != null)
				return false;
		} else if (!rolename.equals(other.rolename))
			return false;
		if (status != other.status)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

}
