package com.purple.ams.ssm.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: ActiveUser 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:50:51
 */
public class ActiveUser implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private String userid;
	private String account;
	private String password;
	private String username;
	private String role;
	private int status;
	private String salt;
	private List<SysPermission> menus;
	private List<SysPermission> permissions;
	private String dept;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<SysPermission> getMenus() {
		return menus;
	}
	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}
	public List<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	
	
	public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }

    
	public ActiveUser(String userid, String account, String password, String username, String role, int status,
            String salt, List<SysPermission> menus, List<SysPermission> permissions, String dept) {
        super();
        this.userid = userid;
        this.account = account;
        this.password = password;
        this.username = username;
        this.role = role;
        this.status = status;
        this.salt = salt;
        this.menus = menus;
        this.permissions = permissions;
        this.dept = dept;
    }
    public ActiveUser() {
		super();
		// TODO Auto-generated constructor stub
	}
}
