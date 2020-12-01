package com.ischoolbar.programmer.interceptor.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Menu;
import com.ischoolbar.programmer.util.MenuUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;
/**
 **后台登录拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestURIString = request.getRequestURI();
		Object admin = request.getSession().getAttribute("admin");
		
		if(admin == null) {
			String header = request.getHeader("X-Requested-With");
			//判断是否是ajax请求
			if("XMLHttpRequest".equals(header)) {
				//是ajax请求
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("typr", "error");
				ret.put("msg", "登录会话超时或未登录，请重新登录");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			//普通链接跳转，重定向到登录页面
			response.sendRedirect(request.getServletContext().getContextPath() + "/system/login");
			return false;
		}
		//获取菜单ID
		String mid = request.getParameter("_mid");
		if(!StringUtils.isEmptyOrWhitespaceOnly(mid)) {
			List<Menu> allThirdMenu = MenuUtil.getAllThirdMenu((List<Menu>) request.getSession().getAttribute("userMenus"), Long.valueOf(mid));
			request.setAttribute("thirdMenuList", allThirdMenu);
		}
		return true;
	}

}
