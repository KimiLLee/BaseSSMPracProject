package com.ischoolbar.programmer.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.admin.Authority;
import com.ischoolbar.programmer.entity.admin.Menu;
import com.ischoolbar.programmer.entity.admin.Role;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.service.admin.AuthorityService;
import com.ischoolbar.programmer.service.admin.LogService;
import com.ischoolbar.programmer.service.admin.MenuService;
import com.ischoolbar.programmer.service.admin.RoleService;
import com.ischoolbar.programmer.service.admin.UserService;
import com.ischoolbar.programmer.util.CpachaUtil;
import com.ischoolbar.programmer.util.MenuUtil;
import com.mysql.jdbc.StringUtils;

/**
 * ϵͳ�����������
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private AuthorityService authorityService;
	@Autowired private MenuService menuService;
	@Autowired private LogService logService;
	/**
	 * ϵͳ��¼����ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request) {
		List<Menu> userMenus = (List<Menu>) request.getSession().getAttribute("userMenus");
		model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
		model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
		model.setViewName("system/index");
		return model;
	}
	
	/**
	 * ϵͳ��¼��ӭҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model) {
		model.setViewName("system/welcome");
		return model;
	}
	
	/*
	 * ��¼ҳ��
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		
		model.setViewName("system/login");
		
		return model;
	}
	
	/**
	 * ��¼���ύ���������
	 * @param user
	 * @param cpacha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginAct(User user, String cpacha, HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "����д�û���Ϣ");
			return ret;
		}
		if(StringUtil.isEmpty(cpacha)) {
			ret.put("type", "error");
			ret.put("msg", "����д��֤��");
			return ret;
		}
		if(StringUtil.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "����д�û���");
			return ret;
		}
		if(StringUtil.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "����д����");
			return ret;
		}
		
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		
		if(loginCpacha == null) {
			ret.put("type", "error");
			ret.put("msg", "�Ự��ʱ����ˢ��ҳ��");
			return ret;
		}
		if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "��֤�����");
			logService.add("�û�{" + user.getUsername() + "}������֤�����");
			return ret;
		}
		User findByUsername = userService.findByUsername(user.getUsername());
		if(findByUsername == null) {
			ret.put("type", "error");
			ret.put("msg", "�û���������");
			return ret;
		}
		if(!user.getPassword().equals(findByUsername.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "�������");
			logService.add("�û�{" + user.getUsername() + "}�����������");
			return ret;
		}
		//��ѯ�û���ɫȨ��
		Role role = roleService.find(findByUsername.getRoleId());
		//���ݽ�ɫ��ȡȨ���б�
		List<Authority> authorityList = authorityService.findListByRoleId(role.getId());
		String menuIds = "";
		for(Authority authority:authorityList) {
			menuIds += authority.getMenuId() + ",";
		}
		if(!StringUtils.isNullOrEmpty(menuIds)) {
			menuIds = menuIds.substring(0, menuIds.length() - 1);
		}
		List<Menu> userMenus = menuService.findListByIds(menuIds);
		//��ɫ��Ϣ���˵���Ϣ�ŵ�session
		request.getSession().setAttribute("admin", findByUsername);
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", userMenus);
		
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ�");
		logService.add("{" + role.getName()+"}���û�{" + user.getUsername() + "}��½�ɹ�");
		return ret;
	}
	
	/**
	 * �ǳ�
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		session.setAttribute("role", null);
		request.getSession().setAttribute("userMenus", null);
		return "redirect:login";
	}
	
	/**
	 * �޸�����ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit_password", method = RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model) {
		
		model.setViewName("system/edit_password");
		
		return model;
	}
	
	@RequestMapping(value = "/edit_password", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPasswordAct(String newpassword,String oldpassword, HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmptyOrWhitespaceOnly(newpassword)) {
			ret.put("type", "error");
			ret.put("msg", "����д������");
			return ret;
		}
		User user = (User)request.getSession().getAttribute("admin");
		if(!user.getPassword().equals(oldpassword)){
			ret.put("type", "error");
			ret.put("msg", "ԭ�����������");
			return ret;
		}
		user.setPassword(newpassword);
		if(userService.editPassword(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "�����޸�ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�����޸ĳɹ�");
		logService.add("�û�{" + user.getUsername() + "}���޸�����");
		return ret;
	}
	
	
	
	/**
	 * ��ϵͳ��֤����ɴ˷�������
	 * @param vcodeLen
	 * @param width
	 * @param height
	 * @param cpachaType ������֤�����ͣ������ַ���
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/get_cpacha", method = RequestMethod.GET)
	public void generateCpacha(
			@RequestParam(name = "vl", required = false, defaultValue = "4") Integer vcodeLen,
			@RequestParam(name = "w", required = false, defaultValue = "110") Integer width,
			@RequestParam(name = "h", required = false, defaultValue = "30") Integer height,
			@RequestParam(name = "type", required = true, defaultValue = "loginCpacha") String cpachaType,
			HttpServletRequest request, 
			HttpServletResponse response) {
		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute(cpachaType, generatorVCode);
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
