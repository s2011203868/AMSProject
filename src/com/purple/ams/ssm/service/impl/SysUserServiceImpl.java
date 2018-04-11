package com.purple.ams.ssm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.purple.ams.ssm.mapper.SysUserMapper;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.pojo.ExceptionRecord;
import com.purple.ams.ssm.pojo.ExecuteRecord;
import com.purple.ams.ssm.pojo.OnlineUser;
import com.purple.ams.ssm.pojo.RolePermission;
import com.purple.ams.ssm.pojo.SysDept;
import com.purple.ams.ssm.pojo.SysDuty;
import com.purple.ams.ssm.pojo.SysPermission;
import com.purple.ams.ssm.pojo.SysPermissionAuthorization;
import com.purple.ams.ssm.pojo.SysPermissionTree;
import com.purple.ams.ssm.pojo.SysRole;
import com.purple.ams.ssm.pojo.SysUser;
import com.purple.ams.ssm.service.SysUserService;
import com.purple.ams.ssm.shiro.realm.AMSRealm;
import com.purple.ams.ssm.util.ExecuteResult;
import com.purple.ams.ssm.util.FunctionUtil;
import com.purple.ams.ssm.util.RemoveNullKeyValue;

import net.sf.json.JSONArray;
/**
 * 
 * @ClassName: SysUserServiceImpl 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:45:07
 */

public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private AMSRealm amsReam;
	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * 查看数据库中是否有本机机器码
	 */
	@Override
	public int findhasMacheineCode(String dESMac) {
		int n = sysUserMapper.findHasMacheineCode(dESMac);
		return n;
	}
	
	

	/**
	 * 保存机器码
	 */
	@Override
	public int saveMacheineCode(String dESMac) {
		int n = sysUserMapper.saveMacheineCode(dESMac);
		return n;
	}
	
	
	@Override
	public int setLastLoginTime(String nowTime,String userid) throws Exception{
		int n = 0;
		n = sysUserMapper.setLastLoginTime(nowTime,userid);
		return n;
	}
	
	@Override
	public SysUser findUserByAccount(String account) throws Exception {
		
		SysUser sysUser = sysUserMapper.findByUserAccount(account);
		
		return sysUser;
	}

	@Override
	public List<SysPermission> findUserMenusByAccount(String account)throws Exception{
		
		List<SysPermission> list = sysUserMapper.finUserMenusByAccount(account);
		return list;
	}
	
	/**
	 * 查询用户所拥有的权限
	 */
	@Override
	public List<SysPermission> findPermissionsByUser(String userid) throws Exception {
		List<SysPermission> list = sysUserMapper.findPermissionsByUser(userid);
		return list;
	}

	@Override
	public List<SysRole> finUserRolesByAccount(String account)throws Exception {
		
		List<SysRole> list = sysUserMapper.finUserRolesByAccount(account);
		return list;
	}

	@Override
	public String findLeftMenu(String account, String menuid) throws Exception{

		List<SysPermission> list = sysUserMapper.findLeftMenu(account,menuid);
		
		StringBuffer sb = new StringBuffer("[");
		sb.append("{\"name\":").append("\"菜单选项\"").append(",").append("\"children\":[");
		for(SysPermission permission : list){
			
			String perid = permission.getId();
			List<SysPermission> nextList =sysUserMapper.findLeftMenu(account, perid);
			if(nextList.size() == 0){
				sb.append(ExecuteResult.leftMenuJson(permission)).append(",");
			}
			if(nextList.size() != 0){
				sb.append("{\"name\":\""+permission.getName()+"\",\"children\":[");
				nextList.forEach( next -> sb.append(ExecuteResult.leftMenuJson(next)).append(",") );
				sb.append("]}").append(",");
			}
		}
		
		sb.append("]}]");
		String menujson = sb.toString();
		menujson = menujson.replaceAll(",]", "]");
		return menujson;
	}

	@Override
	public String getRoleList() throws Exception{
		List<SysDuty> roleList = sysUserMapper.getRoleList();
		for(int i = 0 ;i < roleList.size() ;i++){
			if("super".equals(roleList.get(i).getRoleid())){
				roleList.remove(i);
			}
		}
		String result = JSONArray.fromObject(roleList).toString();
		String json = "{\"totalRow\":1 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":10, \"list\":"+result+"}";
		return json;
	}

	@Override
	public int rolelistAddSave(String roleid,String rolename, String status,String duty) throws Exception{
		
		String uri = "system/findpermissionbyroleid.action?roleid="+roleid;
		String target = "#layout01";
		String level = "1";
		String parentid = "super";
		int count = sysUserMapper.findRoleCount();
		int order = count -1 ;
		
		int s = Integer.parseInt(status);
		int n = sysUserMapper.rolelistAddSave(rolename,s,roleid,uri,target,level,parentid,order,duty);
		return n;
	}

	@Override
	public int rolelisteditSave(String roleid, String rolename, String status) throws Exception{
		int s = Integer.parseInt(status);
		int n = sysUserMapper.rolelisteditSave(roleid,rolename,s);
		return n;
	}

	@Override
	public String getRoleDistributeList(String pageSize, String pageCurrent)throws Exception {
		int total = sysUserMapper.getRoleDistributeTotal();
		int pagesize = Integer.parseInt(pageSize);
		int totalPage = total/pagesize == 0 ? total/pagesize : (total/pagesize)+1;
		int begin = (Integer.parseInt(pageCurrent)-1)*(Integer.parseInt(pageSize));
		List<ActiveUser> roleList = sysUserMapper.getRoleDistributeList(begin,pagesize);
		for(ActiveUser activeUser : roleList){
			List<SysRole> roles = this.finUserRolesByAccount(activeUser.getAccount());
			roles.forEach(sysRole -> {
				if(activeUser.getRole() == null || activeUser.getRole() == ""){
					activeUser.setRole(sysRole.getRolename());
				}else{
					activeUser.setRole(activeUser.getRole()+","+sysRole.getRolename());
				}
			});
		}
		
		String res = JSONArray.fromObject(roleList).toString();
		String json = "{\"totalRow\":"+total+" ,\"pageNumber\":"+pageCurrent+",\"firstPage\":true,\"lastPage\":false,\"totalPage\":"+totalPage+",\"pageSize\":"+pagesize+", \"list\":"+res+"}";
		return json;
	}

	@Override
	public List<SysRole> finAllRoles()throws Exception {
		List<SysRole> allRole = sysUserMapper.finAllRoles();
		return allRole;
	}

	@Override
	public int deleteOldRoleByAccount(String account)throws Exception {
		int n = sysUserMapper.deleteOldRoleByAccount(account);
		return n;
	}
	
    @Override
    public int deleteOldDeptByUserid(String userid) throws Exception {
        int n = sysUserMapper.deleteOldDeptByUserId(userid);
        return n;
    }
	

	@Override
	public int saveUserRole(String uuid, String userid, String role)throws Exception {
		int n = sysUserMapper.saveUserRole(uuid,userid,role);
		return n;
	}

	@Override
	public String getSysUserList(String pageSize, String pageCurrent)throws Exception {
		
		int total = sysUserMapper.getSysUserTotal();
		int pagesize = Integer.parseInt(pageSize);
		int totalPage = total/pagesize == 0 ? total/pagesize : (total/pagesize)+1;
		int begin = (Integer.parseInt(pageCurrent)-1)*(Integer.parseInt(pageSize));
		List<SysUser> userList = sysUserMapper.getSysUserList(begin,pagesize);
		
		for(SysUser sysUser : userList){
		    sysUser.setBegintime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysUser.getCreateTime()));
            sysUser.setLasttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysUser.getLastLogintime()));
            List<SysDept> depts = sysUserMapper.getSysDeptByUserid(sysUser.getUserid());
            StringBuffer deptids = new StringBuffer();
            StringBuffer deptnames = new StringBuffer();
            for(SysDept sysDept : depts){
                deptids.append(sysDept.getId()).append(",");
                deptnames.append(sysDept.getName()).append(",");
            }
            if(deptids.length()>0){
                sysUser.setDeptid(deptids.toString().substring(0,deptids.toString().length()-1));
            }
            if(deptnames.length()>0){
                sysUser.setDept(deptnames.toString().substring(0,deptnames.toString().length()-1));
            }
            List<SysRole> roles = sysUserMapper.finUserRolesByAccount(sysUser.getAccount());
            StringBuffer roleids = new StringBuffer();
            StringBuffer rolenames = new StringBuffer();
            for(SysRole sysRole : roles){
                roleids.append(sysRole.getRoleid()).append(",");
                rolenames.append(sysRole.getRolename()).append(",");
            }
            if(roleids.length()>0){
                sysUser.setDutyid(roleids.toString().substring(0,roleids.toString().length()-1));
            }
            if(rolenames.length()>0){
                sysUser.setDuty(rolenames.toString().substring(0,rolenames.toString().length()-1));
            }
		}
		
		String res = JSONArray.fromObject(userList.toArray()).toString();
		String json = "{\"totalRow\":"+total+" ,\"pageNumber\":"+pageCurrent+",\"firstPage\":true,\"lastPage\":false,\"totalPage\":"+totalPage+",\"pageSize\":"+pagesize+", \"list\":"+res+"}";
		return json;
	}

	@Override
	public int changePassword(String changedPassword, String userid)throws Exception {
		int n = sysUserMapper.changePassword(changedPassword,userid);
		return n;
	}

	@Override
	public int checkAccount(String account)throws Exception {
		int n = sysUserMapper.checkAccount(account);
		return n;
	}

	@Override
	public int createSysUser(String userid,String account, String md5Password, String username, String nowTime, String salt,
			String status)throws Exception {
		int n = sysUserMapper.createSysUser(userid,account,md5Password,username,nowTime,salt,status);
		return n;
	}

	@Override
	public int EditUserStatus(String userid, String status)throws Exception {
		int n = sysUserMapper.EditUserStatus(userid,status);
		return n;
	}

	@Override
	public void addExceptionInfo(String uri, String account, String realIp, String executeTime, String exceptionType,
			String exceptionCause) {
		sysUserMapper.addExceptionInfo(uri,account,realIp,executeTime,exceptionType,exceptionCause);
		
	}

	@Override
	public String findSystemExceptionRecord(String pageSize, String pageCurrent) throws Exception {
		int total = sysUserMapper.getExceptionRecordTotal();
		int pagesize = Integer.parseInt(pageSize);
		int totalPage = total/pagesize == 0 ? total/pagesize : (total/pagesize)+1;
		int begin = (Integer.parseInt(pageCurrent)-1)*(Integer.parseInt(pageSize));
		List<ExceptionRecord> ExceptionList = sysUserMapper.findSystemExceptionRecord(begin,pagesize);
		ExceptionList.forEach(exceptionRecord -> exceptionRecord.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exceptionRecord.getExecutetime())));
		String res = JSONArray.fromObject(ExceptionList.toArray()).toString();
		String json = "{\"totalRow\":"+total+" ,\"pageNumber\":"+pageCurrent+",\"firstPage\":true,\"lastPage\":false,\"totalPage\":"+totalPage+",\"pageSize\":"+pagesize+", \"list\":"+res+"}";
		return json;
	}

	@Override
	public List<ExceptionRecord> querySystemExceptionRecord(String pageSize, String pageCurrent) throws Exception {
		int pagesize = Integer.parseInt(pageSize);
		int begin = (Integer.parseInt(pageCurrent)-1)*(Integer.parseInt(pageSize));
		List<ExceptionRecord> ExceptionList = sysUserMapper.findSystemExceptionRecord(begin,pagesize);
		ExceptionList.forEach(exceptionRecord -> exceptionRecord.setDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exceptionRecord.getExecutetime())) );
		return ExceptionList;
	}

	@Override
	public void addExecuteRecord(String description, String url, String time, String realIp, String account,
			String executeTime) {
		sysUserMapper.addExecuteRecordInfo(description,url,time,realIp,account,executeTime);
		
	}

	@Override
	public String findSystemExecuteRecord(String pageSize, String pageCurrent) throws Exception {
		int total = sysUserMapper.getExecuteRecordTotal();
		int pagesize = Integer.parseInt(pageSize);
		int totalPage = total/pagesize == 0 ? total/pagesize : (total/pagesize)+1;
		int begin = (Integer.parseInt(pageCurrent)-1)*(Integer.parseInt(pageSize));
		List<ExecuteRecord> ExecuteList = sysUserMapper.findSystemExecuteRecord(begin,pagesize);
		String res = JSONArray.fromObject(ExecuteList.toArray()).toString();
		String json = "{\"totalRow\":"+total+" ,\"pageNumber\":"+pageCurrent+",\"firstPage\":true,\"lastPage\":false,\"totalPage\":"+totalPage+",\"pageSize\":"+pagesize+", \"list\":"+res+"}";
		return json;
	}

	@Override
	public int correlationPermissions(String roleid, String permissionid) throws Exception {
		String id = FunctionUtil.getUUID();
		int n = sysUserMapper.correlationPermissions(id,roleid,permissionid);
		amsReam.clearCachedAuthorization();
		return n;
	}


	@Override
	public String findPermissionList() throws Exception {
		List<SysPermission> permissions = sysUserMapper.findPermissionList();
		String res = JSONArray.fromObject(permissions.toArray()).toString();
		return res;
	}

	@Override
	public int permissionEidtSave(String id, int status) throws Exception {
		int n = sysUserMapper.permissionEditSave(id,status);
		return n;
	}

	@Override
	public String findRoleList() throws Exception {
		List<SysDuty> roleList = sysUserMapper.getRoleList();
		String result = JSONArray.fromObject(roleList.toArray()).toString();
		String json ="{\"totalRow\":200 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+result+"}";
		return json;
	}

	/**
	 * 查询岗位所拥有的的权限
	 */
	@Override
	public String findIsPermissionAuthorization(String roleid) throws Exception {
		
		List<SysPermissionAuthorization> permissionAuthorizations = sysUserMapper.findIsPermissionAuthorization();
		
		List<RolePermission> hasPermissions = sysUserMapper.findHasPermissionsByRole(roleid);
		permissionAuthorizations.forEach(permissionAuthorization -> {
			if("super".equals(permissionAuthorization.getId())){
				permissionAuthorization.setIsAuthorization(1);
			}else{
				permissionAuthorization.setIsAuthorization(2);
			}
			hasPermissions.forEach(permission -> {
				if(permission.getSys_permission_id().equals(permissionAuthorization.getId())){
					permissionAuthorization.setIsAuthorization(1);
			}});
		});
		String res = JSONArray.fromObject(permissionAuthorizations.toArray()).toString();
		String json ="{\"totalRow\":200 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+res+"}";
		return json;
	}
	
	/**
	 * 查询部门所拥有的权限
	 */
    @Override
    public String findDeptIsPermissionAuthorization(String deptid) throws Exception {
        List<String> roles = sysUserMapper.findRolesByDeptid(deptid);
        List<SysPermissionAuthorization> permissionAuthorizations = sysUserMapper.findIsPermissionAuthorization();
        permissionAuthorizations.forEach(permissionAuthorization -> {
            if("super".equals(permissionAuthorization.getId())){
                permissionAuthorization.setIsAuthorization(1);
            }else{
                permissionAuthorization.setIsAuthorization(2);
            }
        });
        for(String roleid : roles){
            List<RolePermission> hasPermissions = sysUserMapper.findHasPermissionsByRole(roleid);
            permissionAuthorizations.forEach(permissionAuthorization -> {
                hasPermissions.forEach(permission -> {
                    if(permission.getSys_permission_id().equals(permissionAuthorization.getId())){
                        permissionAuthorization.setIsAuthorization(1);
                }});
            });
        }
        String res = JSONArray.fromObject(permissionAuthorizations.toArray()).toString();
        String json ="{\"totalRow\":200 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+res+"}";
        return json;
    }


	@Override
	public int removePermissionForRole(String roleid, String permissionid)throws Exception {

		int n = sysUserMapper.removePermissionForRole(roleid,permissionid);
		amsReam.clearCachedAuthorization();
		return n;
	}

	/**
	 * 修改完资源后删除相应关系
	 */
	@Override
	public int deleteRolePermission(String id) throws Exception {
		int n = sysUserMapper.deleteRolePermission(id);
		amsReam.clearCachedAuthorization();
		return n;
	}



    @Override
    public int deleteUserRoleByRoleid(String roleid) throws Exception {
       int count = sysUserMapper.deleteUserRoleByRoleid(roleid);
        return count;
    }


    /**
     * 获取当前在线用户
     */
    @Override
    public String findOnlineUser() throws Exception {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<OnlineUser> list = new ArrayList<OnlineUser>();
        String accounts = "";
        if(sessions.size()>0){
            for(Session session:sessions){

                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setSessionid((String) session.getId());
                onlineUser.setHosts(session.getHost());
                onlineUser.setStatus(1);
                onlineUser.setLastTime(FunctionUtil.dateToStr(session.getLastAccessTime()));
                PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if(principalCollection != null){
                    ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
                    //没有这个用户名则添加
                    if(!accounts.contains(activeUser.getAccount())){
                        onlineUser.setAccount(activeUser.getAccount());
                        accounts += activeUser.getAccount();
                        list.add(onlineUser);
                    }else{
                        //有这个用户名则比较最后操作时间
                        if(activeUser.getAccount()!=null){
                            for(int n = 0 ; n<list.size() ; n++){
                                if(activeUser.getAccount().equals(list.get(n).getAccount())){
                                    Date oldUsertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(list.get(n).getLastTime());
                                    Date newUsertime = session.getLastAccessTime();
                                    Long oldtime = oldUsertime.getTime();
                                    Long newtime = newUsertime.getTime();
                                    if(oldtime < newtime){
                                        list.remove(list.get(n));
                                        onlineUser.setAccount(activeUser.getAccount());
                                        list.add(onlineUser);
                                    }else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }else{
                   continue;
                }
            }
        }
        
        for(int i = 0 ; i<list.size() ; i++){
            if(list.get(i).getAccount() == null || "".equals(list.get(i).getAccount())){
                list.remove(list.get(i));
            }
        }
        String res = JSONArray.fromObject(list.toArray()).toString();
        String json ="{\"totalRow\":2000 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+res+"}";
        return json;
    }



	@Override
	public String findDeptList() throws Exception {
		List<SysDept> list = sysUserMapper.findDeptList();
		String result = JSONArray.fromObject(list.toArray()).toString();
		String json ="{\"totalRow\":200 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+result+"}";
		return json;
	}

	@Override
    public String findAllDept() throws Exception {
        List<SysDept> list = sysUserMapper.findAllDept();
        String result = JSONArray.fromObject(list.toArray()).toString();
        String json ="{\"totalRow\":200 ,\"pageNumber\":1,\"firstPage\":true,\"lastPage\":false,\"totalPage\":1,\"pageSize\":200, \"list\":"+result+"}";
        return json;
    }


    @Override
    public int addDept(SysDept sysDept) throws Exception {
       int n = sysUserMapper.addDept(sysDept);
        return n;
    }



    @Override
    public int editDept(SysDept sysDept) throws Exception {
        int n = sysUserMapper.editDept(sysDept);
        return n;
    }


    /**
     * 获取资源树
     */
    @Override
    public String getPermissionTree() throws Exception {
        List<SysPermissionTree> tree = sysUserMapper.getPermissionTree();
        String result = JSONArray.fromObject(tree.toArray()).toString();
        return result;
    }



    @Override
    public int initPermissionForRole(String id,String roleid,String permission) throws Exception {
        int n = sysUserMapper.initPermissionForRole(id,roleid,permission);
        return n;
    }



    @Override
    public Map<String, String> findAllDeptDuty() throws Exception {
        List<SysUser> list = sysUserMapper.findAllDeptDuty();
        Map<String, String> map = new HashMap<String,String>();
        for(SysUser sysUser : list){
            if(sysUser.getDept() == null){
                sysUser.setDept("");
            }
            if(sysUser.getDuty() == null){
                sysUser.setDuty("");
            }
            map.put(sysUser.getDeptid()+";"+sysUser.getDutyid(), sysUser.getDept()+sysUser.getDuty());
        }
        return map;
    }



    @Override
    public int addDeptForUser(String id, String userid, String deptid) throws Exception {
        int n = sysUserMapper.addDeptForUser(id,userid,deptid);
        return n;
    }



    @Override
    public String findDeptDutyByUserid(String userid) throws Exception {
        List<SysUser> list = sysUserMapper.findDeptDutyByUserid(userid);
        StringBuffer sBuffer = new StringBuffer();
        String result = "";
        if(list.size()>0){
            for(SysUser sysUser : list){
                sBuffer.append(sysUser.getDeptid()+";"+sysUser.getDutyid()).append(",");
            }
            if(sBuffer.length()>0){
                result = sBuffer.toString().substring(0,sBuffer.toString().length()-1);
            }
        }
        return result;
    }

    /**
     * 修改岗位状态
     */

    @Override
    public int updateRoleStatus(String roleid, String status) throws Exception {
        int n = sysUserMapper.updateRoleStatus(roleid,status);
        return n;
    }


    /**
     * 删除部门下相关用户
     */
    @Override
    public int deleteOldUserByDeptId(String deptid) throws Exception {
        int n = sysUserMapper.deleteOldUserByDeptId(deptid);
        return n;
    }


    @Override
    public int finddeptStatusByRoleid(String roleid) throws Exception {
        int n = sysUserMapper.finddeptStatusByRoleid(roleid);
        return n;
    }



  
}
