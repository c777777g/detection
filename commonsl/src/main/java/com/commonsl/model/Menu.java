package com.commonsl.model;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Entity {
	
	private static final long serialVersionUID = 1L;

	public List<Menu> getList() {
		return list;
	}

	protected List<Menu> list = new ArrayList<Menu>();
	
	/**  */
	protected Integer id;
	
	/** 图标样式 */
	protected String icon;
	
	/** 菜单名 */
	protected String name;
	
	/** 所属id  0为主菜单 */
	protected Integer paretId;
	
	/** 管理员菜单与普通用户菜单 */
	protected Integer type;
	
	/** 选择后提交 url */
	protected String url;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParetId() {
		return paretId;
	}
	
	public void setParetId(Integer paretId) {
		this.paretId = paretId;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setList(List<Menu> list) {
		this.list = list;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("icon = ").append(icon).append(", ");
		builder.append("name = ").append(name).append(", ");
		builder.append("paretId = ").append(paretId).append(", ");
		builder.append("type = ").append(type).append(", ");
		builder.append("url = ").append(url);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}