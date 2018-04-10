package com.purple.ams.ssm.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @ClassName: SysPermission 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:51:47
 */
public class SysPermission implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = -1909765191236731922L;
	private String id;
	private String name;
	private String pertype;
	private String url;
	private String perCode;
	private String parentid;
	private String parentids;
	private String order;
	private int status;
	private String target;
	private String level;
	//private List<SysPermission> children = new ArrayList<SysPermission>();
	
	


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
	public String getPertype() {
		return pertype;
	}
	public void setPertype(String pertype) {
		this.pertype = pertype;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPerCode() {
		return perCode;
	}
	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getParentids() {
		return parentids;
	}
	public void setParentids(String parentids) {
		this.parentids = parentids;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	/*public List<SysPermission> getChildren() {
		return children;
	}
	public void setChildren(List<SysPermission> children) {
		this.children = children;
	}*/
	

	
	public SysPermission() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SysPermission [id=" + id + ", name=" + name + ", pertype=" + pertype + ", url=" + url + ", perCode="
				+ perCode + ", parentid=" + parentid + ", parentids=" + parentids + ", order=" + order + ", status="
				+ status + ", target=" + target + ", level=" + level + "]";
	}
}
