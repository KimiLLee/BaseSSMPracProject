package com.ischoolbar.programmer.util;
/**
 * �˵������Ĺ��÷���
 * @author Administrator
 *
 */

import java.util.ArrayList;
import java.util.List;

import com.ischoolbar.programmer.entity.admin.Menu;

public class MenuUtil {
	

	/**
	 * �Ӹ����˵��з���ȫ�������˵�
	 * @param menulist
	 * @return
	 */
	public static List<Menu> getAllTopMenu(List<Menu> menulist){
		List<Menu> ret = new ArrayList<Menu>();
		for(Menu menu:menulist) {
			if(menu.get_parentId() == 0) {
				ret.add(menu);
			}
		}
		return ret;
	}
	
	/**
	 * ��ȡȫ�������˵�
	 * @param menulist
	 * @return
	 */
	public static List<Menu> getAllSecondMenu(List<Menu> menulist){
		List<Menu> ret = new ArrayList<Menu>();
		List<Menu> allTopMenu = getAllTopMenu(menulist);
		for(Menu menu:menulist) {
			for(Menu topMenu:allTopMenu) {
				if(menu.get_parentId() == topMenu.getId()) {
					ret.add(menu);
					break;
				}
			}
			
		}
		return ret;
	}
	
	/**
	 * ��ȡ��ťurl
	 * @param menuList
	 * @param secondMenuId
	 * @return
	 */
	public static List<Menu> getAllThirdMenu(List<Menu> menuList,Long secondMenuId){
		List<Menu> ret = new ArrayList<Menu>();
		for(Menu menu:menuList){
			if(menu.getParentId() == secondMenuId){
				ret.add(menu);
			}
		}
		return ret;
	}
}
