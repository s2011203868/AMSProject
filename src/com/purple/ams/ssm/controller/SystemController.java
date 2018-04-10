package com.purple.ams.ssm.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.purple.ams.ssm.constant.OtherConstants;
import com.purple.ams.ssm.constant.ReturnJsonConstantCollection;
import com.purple.ams.ssm.mapper.SysUserMapper;
import com.purple.ams.ssm.pojo.ActiveUser;
import com.purple.ams.ssm.pojo.ExceptionRecord;
import com.purple.ams.ssm.pojo.SysDept;
import com.purple.ams.ssm.pojo.SysPermission;
import com.purple.ams.ssm.pojo.SysRole;
import com.purple.ams.ssm.pojo.SysUser;
import com.purple.ams.ssm.service.SysUserService;
import com.purple.ams.ssm.shiro.realm.AMSRealm;
import com.purple.ams.ssm.util.DownLoadUtil;
import com.purple.ams.ssm.util.ExecuteResult;
import com.purple.ams.ssm.util.FunctionUtil;
import com.purple.ams.ssm.util.MacAddressApi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @ClassName: SystemController 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:49:44
 */
@Controller
public class SystemController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	
	@RequestMapping("changepassword")
	public String changePassword(Model model) {

		return "system/changepassword";
	}

	@RequestMapping("changepasswordsave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	public String changePasswordSave(Model model, HttpServletRequest request,
			@RequestParam(value = "userid") String userid, @RequestParam(value = "account") String account,
			@RequestParam(value = "oldpassword") String oldpassword,
			@RequestParam(value = "password") String password) throws Exception {
		int n = 0;
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		String salt = activeUser.getSalt();
		String oldpasswordMD5 = FunctionUtil.md5Str(oldpassword, salt, 1);
		// 81255cb0dca1a5f304328a70ac85dcbd
		if (!oldpasswordMD5.equals(activeUser.getPassword())) {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "原密码不正确！");
		}

		Md5Hash md5Hash2 = new Md5Hash(password, salt, 1);
		// 获取加密后的数值
		String changedPassword = md5Hash2.toString();
		n = sysUserService.changePassword(changedPassword, userid);
		if (n > 0) {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "密码修改成功！");
		}
		return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "密码修改失败！");
	}
	
	/**
	 * 
	* @Title: originalPassword  
	* @Description: 初始化密码
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("originalpassword")
	@ResponseBody
	@RequiresPermissions("system:originalpassword")
	public String originalPassword(@RequestParam(value="account")String account,@RequestParam(value="userid")String userid)throws Exception{
	    SysUser sysUser = sysUserService.findUserByAccount(account);
	    String salt = sysUser.getSalt();
	    Md5Hash md5Hash = new Md5Hash(OtherConstants.INITPASSWORD, salt, 1);
        // 获取加密后的数值
        String originalPassword = md5Hash.toString();
        int n = sysUserService.changePassword(originalPassword, userid);
        if (n > 0) {
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "密码初始化成功！");
        }
        return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "密码初始化失败！");
	}

	/**
	 * @Title: roleList @Description: 跳转到角色列表页 @param @param
	 * model @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping("system/postmanage")
	public String roleList(Model model) {
		return "system/rolelist";
	}

	@RequestMapping("findDutyList")
	@ResponseBody
	public String getRoleList() throws Exception {
		String json = "";
		json = sysUserService.getRoleList();
		return json;
	}

	@RequestMapping("editDuty")
	@Transactional(propagation=Propagation.REQUIRED)
	public String roleListEdit(Model model, @RequestParam(value = "roleid") String roleid,
			@RequestParam(value = "rolename", defaultValue = "") String rolename,
			@RequestParam(value = "status", defaultValue = "1") String status,
			@RequestParam(value="deptid")String deptid) throws Exception {

	    List<SysDept> deptList = sysUserMapper.findDeptList();
        model.addAttribute("deptList",deptList);
	    model.addAttribute("deptid", deptid);
		model.addAttribute("roleid", roleid);
		model.addAttribute("rolename", rolename);
		model.addAttribute("status", status);
		return "system/rolelistedit";
	}
	
	@RequestMapping("addNewDuty")
	public String addNewDuty(Model model) throws Exception{
	    List<SysDept> deptList = sysUserMapper.findDeptList();
	    model.addAttribute("deptList",deptList);
        return "system/rolelistadd";
	}

	@RequestMapping("rolelistAddSave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:addrole")
	public String rolelistAddSave(@RequestParam(value = "rolename") String rolename,
			@RequestParam(value = "status") String status,@RequestParam(value="duty")String duty,
			@RequestParam(value="permissions",defaultValue="")String permissions) throws Exception {

	    
		int n = 0;
		int m = 0;
		String res = "";
		String roleid = UUID.randomUUID().toString().replaceAll("-","").trim();
		n = sysUserService.rolelistAddSave(roleid,rolename, status,duty);
		
		if(permissions != null){
            String [] permission = permissions.split(",");
            for(String per : permission){
                String id = UUID.randomUUID().toString().replaceAll("-","").trim();
                m += sysUserService.initPermissionForRole(id,roleid,per);
            }
           
        }
		
		if (n > 0 && m>=0) {
			res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "添加成功！");
		} else {
			res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "添加失败！");
		}
		return res;
	}

	@RequestMapping("rolelisteditSave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:editrole")
	public String rolelisteditSave(HttpServletResponse response, @RequestParam(value = "roleid") String roleid,
			@RequestParam(value = "rolename") String rolename, @RequestParam(value = "status") String status) throws Exception {
		
		String res = "";
		
		InputStream is = SystemController.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(is);
		
		if(roleid.equals(properties.getProperty("adminroleid"))){
			res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "系统管理员角色无法修改！");
		}else{
			int n = 0;
			n = sysUserService.rolelisteditSave(roleid, rolename, status);
			if("2".equals(status)){
			  sysUserService.deleteUserRoleByRoleid(roleid);
			}
			if (n > 0) {
				response.setIntHeader("Refresh", 1);
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
			} else {
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
			}
		}
		return res;
	}

	/**
	 * @Title: roleDistribute @Description: 跳转到角色分配页 @param @param
	 * model @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping("system/roledistribute")
	public String roleDistribute(Model model) {
		return "system/roledistributelist";
	}

	@RequestMapping("getRoleDistributeList")
	@ResponseBody
	public String getRoleDistributeList(Model model,
			@RequestParam(value = "pageSize", defaultValue = "10") String pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = "1") String pageCurrent) throws Exception {

		String json = "";
		json = sysUserService.getRoleDistributeList(pageSize, pageCurrent);

		return json;
	}

	@RequestMapping("selectrole")
	public String selectRole(Model model, @RequestParam(value = "account") String account,
			@RequestParam(value = "userid") String userid,
			@RequestParam(value = "status", defaultValue = "1") String status) throws Exception {
		if ("1".equals(status)) {
			List<SysRole> allRole = null;
			List<SysRole> hasRoleList = null;
			allRole = sysUserService.finAllRoles();
			for(int i = 0 ; i<allRole.size();i++){
				if("super".equals(allRole.get(i).getRoleid())){
					allRole.remove(allRole.get(i));
				}
			}
			hasRoleList = sysUserService.finUserRolesByAccount(account);
			for (SysRole sysRole : hasRoleList) {
				if (allRole.contains(sysRole)) {
					allRole.remove(sysRole);
					sysRole.setHasRole("1");
					allRole.add(sysRole);
				}
			}
			model.addAttribute("AllRole", allRole);
			model.addAttribute("account", account);
			model.addAttribute("userid", userid);
			model.addAttribute("status", status);
			return "system/selectrole";
		} else {
			List<SysRole> allRole = null;
			allRole = sysUserService.finAllRoles();
			for(int i = 0 ; i<allRole.size();i++){
				if("super".equals(allRole.get(i).getRoleid())){
					allRole.remove(allRole.get(i));
				}else{
					allRole.get(i).setStatus(3);
				}
			}
			model.addAttribute("AllRole", allRole);
			model.addAttribute("account", account);
			model.addAttribute("userid", userid);
			model.addAttribute("status", status);
			return "system/selectrole";
		}
	}

	@RequestMapping("selectRoleSave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:selectrole")
	public String selectRoleSave(Model model, @RequestParam(value = "account") String account,
			@RequestParam(value = "role",defaultValue="") String role,
			@RequestParam(value = "userid") String userid,@RequestParam(value="status")String status) throws Exception {
		

		String res = "";
		if ("2".equals(status)) {
			res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "用户已注销！");
			return res;
		}else if("".equals(role)){
			int n = 0;
			n = sysUserService.deleteOldRoleByAccount(account);
			if (n>0) {
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
			} else {
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
			}
			return res;
		} else {
			int n = 0;
			n = sysUserService.deleteOldRoleByAccount(account);
			String[] roles = role.split(",");
			int count = 0;
			if (n >= 0) {
				for (String ro : roles) {
					String uuid = UUID.randomUUID().toString().replaceAll("-", "").trim();
					count += sysUserService.saveUserRole(uuid, userid, ro);
				}
			}
			if (count == roles.length) {
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
			} else {
				res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
			}
			return res;
		}
	}

	/**
	 * 
	 * @Title: userlist @Description: 请求去system/userlist.jsp页面 @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping("system/userlist")
	public String userlist() {
		return "system/userlist";
	}

	@RequestMapping("getSysUserList")
	@ResponseBody
	public String getSysUserList(Model model, @RequestParam(value = "pageSize", defaultValue = "10") String pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = "1") String pageCurrent) throws Exception {
		String json = "";
		json = sysUserService.getSysUserList(pageSize, pageCurrent);
		return json;
	}

	/**
	 * @Title: userListEdit @Description: TODO判断是用户添加还是编辑 @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping("userlistedit")
	public String userListEdit(Model model, @RequestParam(value = "userid", defaultValue = "") String userid,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "status", defaultValue = "1") String status) throws Exception{

		if ("".equals(userid) || userid == null) {
			
			Map<String, String> map = sysUserService.findAllDeptDuty();
			model.addAttribute("status", status);
			model.addAttribute("deptdutymap", map);
			return "system/userlistadd";
		} else {
		    Map<String, String> map = sysUserService.findAllDeptDuty();
			model.addAttribute("userid", userid);
			model.addAttribute("username", username);
			model.addAttribute("account", account);
			model.addAttribute("deptdutymap", map);
			model.addAttribute("status", status);
			String deptDutys = sysUserService.findDeptDutyByUserid(userid);
			if(deptDutys !=null && !"".equals(deptDutys)){
			    model.addAttribute("deptDutys", deptDutys);
			}
			return "system/userlistedit";
		}
	}

	@RequestMapping("checkAccount")
	@ResponseBody
	public String checkAccount(@RequestParam(value = "account") String account) throws Exception {

		int n = 0;
		n = sysUserService.checkAccount(account);

		if (n > 0) {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "用户名已存在！");
		} else {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "用户名不存在！");
		}
	}

	@RequestMapping("userlistAddSave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:createuser")
	public String userlistAddSave(@RequestParam(value = "account") String account,
			@RequestParam(value = "password") String password, @RequestParam(value = "status") String status,
			@RequestParam(value = "username") String username,@RequestParam(value="deptduty")String deptduty) throws Exception {

		String nowTime = FunctionUtil.dateToStr(new Date());
		String salt = FunctionUtil.randomSixCharStr();
		String md5Password = FunctionUtil.md5Str(password, salt, 1);
		
		
		int n = 0;
		int m = 0;
		String userid = FunctionUtil.getUUID();
		n = sysUserService.createSysUser(userid,account, md5Password, username, nowTime, salt, status);
		
		if(deptduty.contains(",")){
            String [] deptdutys = deptduty.split(","); 
            for(String deptdutyids : deptdutys){
                String [] ids = deptdutyids.split(";");
                if(ids[0] !=null && ids[0] != "" && ids[0] !="null"){
                    String id = FunctionUtil.getUUID();
                    m += sysUserService.addDeptForUser(id,userid,ids[0]);
                }
                if(ids[1] !=null && ids[1] != "" && ids[1] !="null"){
                    String id = FunctionUtil.getUUID();
                    m += sysUserService.saveUserRole(id,userid,ids[1]);
                }
            }
        }else{
            String [] ids = deptduty.split(";");
            if(ids[0] !=null && ids[0] != "" && ids[0] !="null"){
                String id = FunctionUtil.getUUID();
                String deptid = ids[0];
                m += sysUserService.addDeptForUser(id,userid,deptid);
            }
            if(ids[1] !=null && ids[1] != "" && ids[1] !="null"){
                String id = FunctionUtil.getUUID();
                String dutyid = ids[1];
                m += sysUserService.saveUserRole(id,userid,dutyid);
            }
        }
		if (n > 0 && m>=0) {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "创建系统用户成功！");
		} else {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "创建系统用户失败！");
		}
	}

	@RequestMapping("userlistEditSave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:edituser")
	public String userlistEditSave(@RequestParam(value = "userid") String userid,
			@RequestParam(value = "deptduty") String deptduty,@RequestParam(value="account")String account,
			@RequestParam(value="status")String status) throws Exception {

	    String res = "";
	    if ("2".equals(status)) {
            res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "用户已注销！");
            return res;
        }else if("".equals(deptduty)){
            int n = 0;
            int m = 0;
            //删除对应的岗位
            n = sysUserService.deleteOldRoleByAccount(account);
            //删除对应的部门
            m = sysUserService.deleteOldDeptByUserid(userid);
            if (n>=0 && m>=0) {
                res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
            } else {
                res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
            }
            return res;
        }else{
            int n = 0 ;
            int m = 0;
            int x = 0;
            //删除对应的岗位
            n = sysUserService.deleteOldRoleByAccount(account);
            //删除对应的部门
            x = sysUserService.deleteOldDeptByUserid(userid);
            
            if(deptduty.contains(",")){
                String [] deptdutys = deptduty.split(","); 
                for(String deptdutyids : deptdutys){
                    
                    String [] ids = deptdutyids.split(";");
                    if(ids[0] !=null && ids[0] != "" && ids[0] !="null"){
                        String id = FunctionUtil.getUUID();
                        m += sysUserService.addDeptForUser(id,userid,ids[0]);
                    }
                    
                    if(ids[1] !=null && ids[1] != "" && ids[1] !="null"){
                        String id = FunctionUtil.getUUID();
                        m += sysUserService.saveUserRole(id,userid,ids[1]);
                    }
                }
            }else{
                
                String [] ids = deptduty.split(";");
                if(ids[0] !=null && ids[0] != "" && ids[0] !="null"){
                    String id = FunctionUtil.getUUID();
                    m += sysUserService.addDeptForUser(id,userid,ids[0]);
                }
                
                if(ids[1] !=null && ids[1] != "" && ids[1] !="null"){
                    String id = FunctionUtil.getUUID();
                    m += sysUserService.saveUserRole(id,userid,ids[1]);
                }
            }
            
            if (n>=0 && m>=0 && x>=0) {
                res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
            } else {
                res = ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
            }
            return res;
        }
	}
	
	@RequestMapping("EditUserStatus")
	@ResponseBody
	public String editUserStatus(@RequestParam(value="json")String json) throws Exception{
	    JSONArray array = JSONArray.fromObject(json);
	    JSONObject obj = array.getJSONObject(0);
	    String userid = obj.getString("userid");
	    String status = obj.getString("status");
	    int n = sysUserService.EditUserStatus(userid, status);
	    if (n>=0) {
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功！");
        } else {
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败！");
        }
	}
	
	/**
	* @Title: OnlionUserList  
	* @Description: 在线用户列表
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/onlineuser")
	public String OnlineUserList(){
        return "system/onlineuser";
	}
	
	@RequestMapping("findOnlineUser")
	@ResponseBody
	public String findOnlineUser() throws Exception{
	    String json = sysUserService.findOnlineUser();
        return json;
	}
	
	/**
	 * 
	* @Title: exceptionRecordList  
	* @Description: TODO请求去异常日志页面
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/exceptionrecord")
	public String exceptionRecordList(){
		return "system/systemexceptionrecord";
	}
	
	@RequestMapping("findSystemExceptionRecord")
	@ResponseBody
	public String findSystemExceptionRecord(Model model, @RequestParam(value = "pageSize", defaultValue = "10") String pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = "1") String pageCurrent) throws Exception {
		String json = "";
		json = sysUserService.findSystemExceptionRecord(pageSize, pageCurrent);
		return json;
	}
	
	
	@RequestMapping("systemexceptionrecordexport")
	@ResponseBody
	@RequiresPermissions("system:exportexception")
	public String systemExceptionRecordExport(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="pageCurrent")String pageCurrent,@RequestParam(value="pageSize")String pageSize) throws Exception{
		
	    InputStream is = SystemController.class.getClassLoader().getResourceAsStream("tempfilepath.properties");
		Properties properties = new Properties();
		properties.load(is);
		List<ExceptionRecord> recordList = sysUserService.querySystemExceptionRecord(pageSize, pageCurrent);
		DownLoadUtil.createExcel(recordList);
		DownLoadUtil.downLoadExceptionRecordFile(request, response);
		DownLoadUtil.deleteFile(properties.getProperty("exceptionexporttempfilepath"));
		return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "导出成功");
	}
	
	/**
	* @Title: goToOperateRecord  
	* @Description: 返回到operateRecord页面
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/operaterecord")
	public String goToOperateRecord(){
		return "system/systemexecuterecord";
	}
	
	@RequestMapping("findSystemExecuteRecord")
	@ResponseBody
	public String findSystemExecuteRecord(Model model, @RequestParam(value = "pageSize", defaultValue = "10") String pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = "1") String pageCurrent)throws Exception{
		String json = sysUserService.findSystemExecuteRecord(pageSize,pageCurrent);
		return json;
	}
	
	/**
	 * @throws Exception 
	* @Title: goDruidHtml  
	* @Description: 性能监控页面 
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/druid")
	public String goDruidHtml(Model model) throws Exception{
		return "system/druidPage";
	}
	
	
	/**
	* @Title: permissionManagerList  
	* @Description: 资源管理列表页
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/permissionlist")
	public String permissionManagerList(){
		
		return "system/permissionManagerList";
	}
	
	
	@RequestMapping("findPermissionList")
	@ResponseBody
	public String findPermissionList() throws Exception{
		String json = sysUserService.findPermissionList();
		return json;
	}
	/**
	* @Title: goPermissionEditPage  
	* @Description: 去资源修改页面
	* @param @return
	* @param @throws Exception    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("goPermissionEditPage")
	public String goPermissionEditPage(Model model,SysPermission permission)throws Exception{
		model.addAttribute("SysPermission", permission);
		return "system/PermissionEditPage";
	}
	
	@RequestMapping("permissioneditsave")
	@ResponseBody
	@Transactional(propagation=Propagation.REQUIRED)
	@RequiresPermissions("system:editpermission")
	public String permissioneditsave(SysPermission permission)throws Exception{
		String id = permission.getId();
		int status = permission.getStatus();
		int n = sysUserService.permissionEidtSave(id,status);
		//将资源状态修改完之后删除与此资源相关角色关联关系
		int count = sysUserService.deleteRolePermission(id);
		
		if (n > 0 && count >=0) {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改成功!");
		} else {
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改失败!");
		}
	}
	
	/**
	* @Title: roleAuthorization  
	* @Description: 角色授权页面
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/roleauthorization")
	public String roleAuthorization(){
		return "system/roleauthorization";
	}
	
	@RequestMapping("findroleList")
	@ResponseBody
	public String findRoleList() throws Exception{
		String json = sysUserService.findRoleList();
		return json;
	}
	
	@RequestMapping("permissionlisttree")
	public String permissionlisttree(Model model,@RequestParam(value="roleid")String roleid){
		model.addAttribute("roleid",roleid);
		return "system/permissionlisttree";
	}
	
	/**
	 * @throws Exception 
	* @Title: findIsPermissionAuthorization  
	* @Description: 查询岗位授权信息 
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("findIsPermissionAuthorization")
	@ResponseBody
	@RequiresPermissions("system:querypermissionbyrole")
	public String findIsPermissionAuthorization(Model model,@RequestParam(value="roleid")String roleid) throws Exception{
		
		String json = sysUserService.findIsPermissionAuthorization(roleid);
		return json;
	}
	
	/**
	* @Title: findDeptIsPermissionAuthorization  
	* @Description: 查询部门授权信息 
	* @param @param deptid
	* @param @return
	* @param @throws Exception    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("findDeptIsPermissionAuthorization")
	@ResponseBody
	public String findDeptIsPermissionAuthorization(@RequestParam(value="deptid") String deptid) throws Exception{
	    String json = sysUserService.findDeptIsPermissionAuthorization(deptid);
        return json;
	}
	
	
	/**
	 * @throws Exception 
	* @Title: addPermissionForRole  
	* @Description: 为岗位授予权限
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("addPermissionForRole")
	@ResponseBody
	@RequiresPermissions("system:addpermissionforrole")
	public String addPermissionForRole(@RequestParam(value="roleid")String roleid,
			@RequestParam(value="permissionid")String permissionid) throws Exception{
		
	    int n = 0;
	    
	    if(permissionid.contains(",")){
	        String [] pers = permissionid.split(",");
	        for(int i = 0 ; i< pers.length ;i++){
	           n += sysUserService.correlationPermissions(roleid, pers[i]);
	        }
	    }else{
	        n = sysUserService.correlationPermissions(roleid, permissionid);
	    }
	    
		
		if(n>0){
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "赋权成功!");
		}else{
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "赋权失败!");
		}
	}
	
	/**
	 * @throws Exception 
	* @Title: addPermissionForDept  
	* @Description: 为部门授予权限  
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("addPermissionForDept")
	@ResponseBody
	public String addPermissionForDept(@RequestParam(value="deptid")String deptid,
	        @RequestParam(value="permissionid")String permissionid) throws Exception{
	    
	    int n = 0;
	    List<String> roles = sysUserMapper.findRolesByDeptid(deptid);
        if(permissionid.contains(",")){
            String [] pers = permissionid.split(",");
            for(int i = 0 ; i< pers.length ;i++){
                for(String roleid : roles){
                    n += sysUserService.correlationPermissions(roleid, pers[i]);
                }
            }
        }else{
            for(String roleid : roles){
                n = sysUserService.correlationPermissions(roleid, permissionid);
            }
        }
        
        
        if(n>0){
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "赋权成功!");
        }else{
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "赋权失败!");
        }
	    
	}
	
	/**
	 * @throws Exception 
	* @Title: removePermissionForRole  
	* @Description: 为角色取消授权
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("removePermissionForRole")
	@ResponseBody
	@RequiresPermissions("system:removepermissionforrole")
	public String removePermissionForRole(@RequestParam(value="roleid")String roleid,
			@RequestParam(value="permissionid")String permissionid) throws Exception{
	    int n = 0;
	    
	    if(permissionid.contains(",")){
	        String [] pers = permissionid.split(",");
            for(int i = 0 ; i< pers.length ;i++){
               n += sysUserService.removePermissionForRole(roleid, pers[i]);
            }
	    }else{
	        n = sysUserService.removePermissionForRole(roleid,permissionid);
	    }
		if(n>0){
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "取消授权成功!");
		}else{
			return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "取消授权失败!");
		}
	}
	
	
	/**
	 * @throws Exception 
	* @Title: removePermissionForDept  
	* @Description: 部门取消权限  
	* @param @param deptid
	* @param @param permissionid
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("removePermissionForDept")
	@ResponseBody
	public String removePermissionForDept(@RequestParam(value="deptid")String deptid,
            @RequestParam(value="permissionid")String permissionid) throws Exception{
	    int n = 0;
	    List<String> roles = sysUserMapper.findRolesByDeptid(deptid);
        if(permissionid.contains(",")){
            String [] pers = permissionid.split(",");
            for(int i = 0 ; i< pers.length ;i++){
                for(String roleid : roles){
                    n += sysUserService.removePermissionForRole(roleid, pers[i]);
                }
            }
        }else{
            for(String roleid : roles){
                n = sysUserService.removePermissionForRole(roleid,permissionid);
            }
        }
        if(n>0){
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "取消授权成功!");
        }else{
            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "取消授权失败!");
        }
	}
	
	/**
	* @Title: deptManage  
	* @Description: 跳转部门管理页面 
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping("system/deptemanage")
	public String deptManage(){
		return "system/deptManage";
	}
	
	
	@RequestMapping("finddeptList")
	@ResponseBody
	public String findDeptList() throws Exception{
		String json = sysUserService.findDeptList();
		return json;
		
	}
	
	@RequestMapping("setingdeptpermission")
	public String setingdeptpermission(Model model,@RequestParam(value="deptid")String deptid){
	    model.addAttribute("deptid", deptid);
        return "system/deptPermissionTree";
	    
	}
	
	@RequestMapping("editdept")
	@ResponseBody
	public String editDept(@RequestParam(value="json")String json) throws Exception{
	   // [{"name":"123","status":"1","level":2,"parentid":"c1a2b276be4841adb53095d85a78c9d9","order":0,"addFlag":true}]

	    JSONArray array = JSONArray.fromObject(json);
	    JSONObject object = array.getJSONObject(0);
	    Object addFlag = object.get("addFlag");
	    if(addFlag != null){
	       String id = FunctionUtil.getUUID();
	       String name = object.getString("name");
	       String status = object.getString("status");
	       int level = object.getInt("level");
	       String parentid = object.getString("parentid");
	       int order = object.getInt("order");
	       SysDept sysDept = new SysDept();
	       sysDept.setId(id);
	       sysDept.setName(name);
	       sysDept.setStatus(Integer.parseInt(status));
	       sysDept.setLevel(level+"");
	       sysDept.setParentid(parentid);
	       sysDept.setOrder(order+"");
	       int n = sysUserService.addDept(sysDept);
	       if(n>0){
	            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "添加部门成功!");
	        }else{
	            return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "添加部门失败!");
	        }
	    }else{
	        String id = object.getString("id");
	        String name = object.getString("name");
	        String status = object.getString("status");
	        SysDept sysDept = new SysDept();
	        sysDept.setId(id);
	        sysDept.setName(name);
	        sysDept.setStatus(Integer.parseInt(status));
	        int n = sysUserService.editDept(sysDept);
	        if(n>0){
                return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_OKSTATUS, "修改部门成功!");
            }else{
                return ExecuteResult.jsonReturn(ReturnJsonConstantCollection.RETURN_JSON_ERRORSTATUS, "修改部门失败!");
            }
	    }
	}
	
	
	/**
	* @Title: getPermissionTree  
	* @Description: 获取资源树 
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	
	@RequestMapping("getPermissionTree")
	@ResponseBody
    public String getPermissionTree() throws Exception{
	    
	    String json = sysUserService.getPermissionTree();
        return json;
    }
	
}
