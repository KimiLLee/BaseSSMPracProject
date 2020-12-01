package com.ischoolbar.programmer.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.RoleService;
import com.ischoolbar.programmer.service.admin.UserService;


/**
 * �û����������
 * @author Administrator
 *
 */
@RequestMapping("/admin/user")
@Controller
public class UserController {
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	
	/**
	 * �û��б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		model.addObject("roleList", roleService.findList(queryMap));
		model.setViewName("user/list");
		return model;
	}
	
	/**
	 * ��ȡ�û��б�
	 * @param page
	 * @param username
	 * @param roleId
	 * @param sex
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name="username",required = false,defaultValue = "") String username,
			@RequestParam(name="roleId",required = false) Long roleId,
			@RequestParam(name="sex",required = false) Integer sex){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", username);
		queryMap.put("roleId", roleId);
		queryMap.put("sex", sex);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));
		ret.put("total", userService.getTotal(queryMap));
		return ret;
	}
	
	
	/**
	 * ����û�
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "����ȷ��д�û���Ϣ");
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
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������ɫ");
			return ret;
		}
		if(isExist(user.getUsername(), 0l)){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѵ���");
			return ret;
		}
		if(userService.add(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "�û����ʧ�ܣ�����ϵ����Ա");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�û���ӳɹ�");
		return ret;
	}
	
	/**
	 * �༭�û�
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "����ȷ��д�û���Ϣ");
			return ret;
		}
		if(StringUtil.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "����д�û���");
			return ret;
		}
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������ɫ");
			return ret;
		}
		if(isExist(user.getUsername(), user.getId())){
			ret.put("type", "error");
			ret.put("msg", "���û����Ѵ���");
			return ret;
		}
		if(userService.edit(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "�û��޸�ʧ�ܣ�����ϵ����Ա");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�û��޸ĳɹ�");
		return ret;
	}
	
	/**
	 * ����ɾ���û�
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String ids){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtil.isEmpty(ids)) {
			ret.put("type", "error");
			ret.put("msg", "����ѡ��Ҫɾ��������");
			return ret;
		}
		if(ids.contains(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		if(userService.delete(ids) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "�û�ɾ��ʧ�ܣ�����ϵ����Ա");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�û�ɾ���ɹ�");
		return ret;
	}
	
	/**
	 * �ϴ�ͷ��ͼƬ
	 * @param photo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(photo == null) {
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫ�ϴ���ͼƬ");
			return ret;
		}
		if(photo.getSize() > 1024*1024*1024) {
			ret.put("type", "error");
			ret.put("msg", "ͼƬ��С���ܳ���10M");
			return ret;
		}
		//��ȡ�ļ���չ��
		String suffix = photo.getOriginalFilename().substring(
				photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
		if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "ͼƬ��ʽҪ��jpg,jpeg,gif,png����");
			return ret;
		}
		//��ȡ����·��
		String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
		//�жϴ�·���Ƿ����
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()) {
			//�����ڸ�Ŀ¼������
			savePathFile.mkdir();
			
		}
		String filename = new Date().getTime()+"."+suffix;
		try {
			//�����ļ���ָ��Ŀ¼
			photo.transferTo(new File(savePath + filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "�����ļ��쳣");
			e.printStackTrace();
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ͷ���ϴ��ɹ�");
		ret.put("filepath", request.getServletContext().getContextPath() + "/resources/upload/" + filename);
		return ret;
//		ͷ��ʵ���ϴ���E:\eclipseworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\BaseProjectSSM\resources
		
	}

	/**
	 * �ж��û����Ƿ����
	 * @param username
	 * @param id
	 * @return
	 */
	private boolean isExist(String username, Long id) {
		User user = userService.findByUsername(username);
		if(user == null) {
			return false;
		}
		if(user.getId().longValue() == id.longValue()) {
			return false;
		}
		return true;
	}
}
