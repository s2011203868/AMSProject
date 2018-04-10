package com.purple.ams.ssm.pojo;

import java.io.Serializable;

public class SysDuty extends SysRole implements Serializable{

    // @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    private static final long serialVersionUID = 1L;
    private String deptid;
    private String deptname;
    public String getDeptid() {
        return deptid;
    }
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    public String getDeptname() {
        return deptname;
    }
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    public SysDuty(String roleid, String rolename, int status, String uri, String target, String order, String level,
            String parentid, String hasRole, String deptid, String deptname) {
        super(roleid, rolename, status, uri, target, order, level, parentid, hasRole);
        this.deptid = deptid;
        this.deptname = deptname;
    }
    public SysDuty() {
        super();
        // TODO Auto-generated constructor stub
    }
    
}
