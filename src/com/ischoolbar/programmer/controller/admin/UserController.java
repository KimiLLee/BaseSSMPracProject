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
 * 用户管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/user")
@Controller
public class UserController {
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	
	/**
	 * 用户列表页面
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
	 * 获取用户列表
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
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "请正确填写用户信息");
			return ret;
		}
		if(StringUtil.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "请填写用户名");
			return ret;
		}
		if(StringUtil.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "请填写密码");
			return ret;
		}
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属角色");
			return ret;
		}
		if(isExist(user.getUsername(), 0l)){
			ret.put("type", "error");
			ret.put("msg", "该用户名已存在");
			return ret;
		}
		if(userService.add(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "用户添加失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户添加成功");
		return ret;
	}
	
	/**
	 * 编辑用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "请正确填写用户信息");
			return ret;
		}
		if(StringUtil.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "请填写用户名");
			return ret;
		}
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属角色");
			return ret;
		}
		if(isExist(user.getUsername(), user.getId())){
			ret.put("type", "error");
			ret.put("msg", "该用户名已存在");
			return ret;
		}
		if(userService.edit(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "用户修改失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户修改成功");
		return ret;
	}
	
	/**
	 * 批量删除用户
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String ids){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtil.isEmpty(ids)) {
			ret.put("type", "error");
			ret.put("msg", "请填选择要删除的数据");
			return ret;
		}
		if(ids.contains(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		if(userService.delete(ids) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "用户删除失败，请联系管理员");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户删除成功");
		return ret;
	}
	
	/**
	 * 上传头像图片
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
			ret.put("msg", "请选择要上传的图片");
			return ret;
		}
		if(photo.getSize() > 1024*1024*1024) {
			ret.put("type", "error");
			ret.put("msg", "图片大小不能超过10M");
			return ret;
		}
		//提取文件扩展名
		String suffix = photo.getOriginalFilename().substring(
				photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
		if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "图片格式要求jpg,jpeg,gif,png四种");
			return ret;
		}
		//获取保存路径
		String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
		//判断此路径是否存在
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()) {
			//不存在该目录，创建
			savePathFile.mkdir();
			
		}
		String filename = new Date().getTime()+"."+suffix;
		try {
			//保存文件至指定目录
			photo.transferTo(new File(savePath + filename));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "保存文件异常");
			e.printStackTrace();
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "头像上传成功");
		ret.put("filepath", request.getServletContext().getContextPath() + "/resources/upload/" + filename);
		return ret;
//		头像被实际上传到E:\eclipseworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\BaseProjectSSM\resources
		
	}

	/**
	 * 判断用户名是否存在
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
