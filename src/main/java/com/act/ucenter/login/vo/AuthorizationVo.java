/**   
 * @Title: AuthorizationVo.java 
 * @Package com.act.web.module.common.vo 
 * @Description: (routes 权限) 
 * @author   fmj
 * @date 2017-3-30 下午8:01:25 
 * @version V1.0   
 */
package com.act.ucenter.login.vo;

public class AuthorizationVo {
	// 访问地址 menu_url menuUrl
	private String path;
	
	// 菜单类容 menu_parameter menuParameter
	private Object component;
	
	// 菜单名称menu_name_ch menuNameCh
	private String name;
	
	// 图标样式menu_image menuImage
	private String iconCls;
	
	// 是否隐藏
	private Boolean hidden;
	
	// 子菜单
	private Object[] children;

	public AuthorizationVo() {
	}
	
	/**
	 * 
	* <p>Description: 初始化菜单权限</p> 
	* @param path 
	* 			menuUrl/menu_url 菜单路径
 	* @param component
 	* 			menuParameter/menu_parameter 菜单内容
	* @param name
	* 			menuNameCh/menu_name_ch 菜单名称
	* @param iconCls
	* 			menu_image/menuImage 菜单图片
	* @param hidden
	 */
	public AuthorizationVo(String path, Object component, String name,
			String iconCls, Boolean hidden) {
		this.path = path;
		this.component = component;
		this.name = name;
		this.iconCls = iconCls;
		this.hidden = hidden;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public void setComponent(String component) {
		this.component = component;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Object[] getChildren() {
		return children;
	}

	public void setChildren(Object[] children) {
		this.children = children;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}


}
