package com.purple.ams.ssm.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
 * @ClassName: SysUser 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:52:06
 */
public class SysUser implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	private String userid;
	private String account;
	private String password;
	private String username;
	private Date createTime;
	private String begintime;
	private String salt;
	private Date lastLogintime;
	private String lasttime;
	private String remark;
	private int status;
	private String deptid;
	private String dept;
	private String dutyid;
	private String duty;
	

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
	public Date getCreateTime() {
		return new java.util.Date(createTime.getTime());
	}
	public void setCreateTime(Date createTime) {
		this.createTime =createTime;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Date getLastLogintime() {
		return new java.util.Date(lastLogintime.getTime()) ;
	}
	public void setLastLogintime(Date lastLogintime) {
		this.lastLogintime = lastLogintime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
    public String getDeptid() {
        return deptid;
    }
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public String getDutyid() {
        return dutyid;
    }
    public void setDutyid(String dutyid) {
        this.dutyid = dutyid;
    }
    public String getDuty() {
        return duty;
    }
    public void setDuty(String duty) {
        this.duty = duty;
    }
  
    public SysUser(String userid, String account, String password, String username, Date createTime, String begintime,
            String salt, Date lastLogintime, String lasttime, String remark, int status, String deptid, String dept,
            String dutyid, String duty) {
        super();
        this.userid = userid;
        this.account = account;
        this.password = password;
        this.username = username;
        this.createTime = createTime;
        this.begintime = begintime;
        this.salt = salt;
        this.lastLogintime = lastLogintime;
        this.lasttime = lasttime;
        this.remark = remark;
        this.status = status;
        this.deptid = deptid;
        this.dept = dept;
        this.dutyid = dutyid;
        this.duty = duty;
    }
    public SysUser() {
        super();
        // TODO Auto-generated constructor stub
    }
	
}
