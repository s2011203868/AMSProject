package com.purple.ams.ssm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.purple.ams.ssm.service.SysUserService;
/**
 * @ClassName: MenuController 
 * @Description: TODO
 * @author: PurpleSoft@一禅
 * @date: 2018年4月3日 上午9:49:37
 */
@Controller
public class MenuController {

	@Autowired
	private SysUserService sysUserService;
	
	
	@RequestMapping(value = "menu")
	@ResponseBody
	public String query(Model model,HttpServletRequest request,@RequestParam String account , @RequestParam String menuid) throws Exception{

		String menuJson = "";
		menuJson = sysUserService.findLeftMenu(account,menuid);
		
		return menuJson;
	}
}
