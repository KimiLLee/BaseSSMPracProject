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
 **��̨��¼������
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
			//�ж��Ƿ���ajax����
			if("XMLHttpRequest".equals(header)) {
				//��ajax����
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("typr", "error");
				ret.put("msg", "��¼�Ự��ʱ��δ��¼�������µ�¼");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			//��ͨ������ת���ض��򵽵�¼ҳ��
			response.sendRedirect(request.getServletContext().getContextPath() + "/system/login");
			return false;
		}
		//��ȡ�˵�ID
		String mid = request.getParameter("_mid");
		if(!StringUtils.isEmptyOrWhitespaceOnly(mid)) {
			List<Menu> allThirdMenu = MenuUtil.getAllThirdMenu((List<Menu>) request.getSession().getAttribute("userMenus"), Long.valueOf(mid));
			request.setAttribute("thirdMenuList", allThirdMenu);
		}
		return true;
	}

}