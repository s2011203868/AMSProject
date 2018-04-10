package com.purple.ams.ssm.pojo;

import java.io.Serializable;

public class SysPermissionTree implements Serializable{

    // @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    private static final long serialVersionUID = 1L;
    private String id;
    private String pId;
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public SysPermissionTree(String id, String pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    public SysPermissionTree() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
}
