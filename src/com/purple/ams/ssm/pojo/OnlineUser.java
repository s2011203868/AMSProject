package com.purple.ams.ssm.pojo;

import java.io.Serializable;

public class OnlineUser implements Serializable{

    // @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    private static final long serialVersionUID = 1L;
    private String sessionid;
    private String account;
    private String lastTime;
    private String hosts;
    private int status;
    public String getSessionid() {
        return sessionid;
    }
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getLastTime() {
        return lastTime;
    }
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
    public String getHosts() {
        return hosts;
    }
    public void setHosts(String hosts) {
        this.hosts = hosts;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public OnlineUser(String sessionid, String account, String lastTime, String hosts, int status) {
        super();
        this.sessionid = sessionid;
        this.account = account;
        this.lastTime = lastTime;
        this.hosts = hosts;
        this.status = status;
    }
    public OnlineUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((hosts == null) ? 0 : hosts.hashCode());
        result = prime * result + ((lastTime == null) ? 0 : lastTime.hashCode());
        result = prime * result + ((sessionid == null) ? 0 : sessionid.hashCode());
        result = prime * result + status;
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
        OnlineUser other = (OnlineUser) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (hosts == null) {
            if (other.hosts != null)
                return false;
        } else if (!hosts.equals(other.hosts))
            return false;
        if (lastTime == null) {
            if (other.lastTime != null)
                return false;
        } else if (!lastTime.equals(other.lastTime))
            return false;
        if (sessionid == null) {
            if (other.sessionid != null)
                return false;
        } else if (!sessionid.equals(other.sessionid))
            return false;
        if (status != other.status)
            return false;
        return true;
    }
    
    
}
