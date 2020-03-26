package com.commonsl.service.impl;

import com.commonsl.dao.MenuDao;
import com.commonsl.model.Entity;
import com.commonsl.model.Menu;
import com.commonsl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Integer, Menu> implements MenuService {
	
	@Autowired
	private MenuDao dao;

	@Override
	public List<Menu> getMenuListByAdmin(int type) {

		Entity.MenuCriteria  criteria = new Entity.MenuCriteria();
		criteria.setType(Entity.Value.eq(type));
		List<Menu> list = selectList(criteria);
		List<Menu> roots = getChilds(list, 0);//获取主菜单
		dg(list,roots); //向主菜单导入子菜单
		return roots;
	}

	// 递归所有菜单
	public void dg(List<Menu> allMenus,List<Menu> list){
		for(Menu menu : list){
			int id = menu.getId();
			List<Menu> childs = getChilds(allMenus, id);
			if(!childs.isEmpty()){
				menu.setList(childs);
				dg(allMenus,childs);
			}
		}
	}

	// 根据parentId 获取所有子节点
	public List<Menu> getChilds(List<Menu> list , int parentId){
		List<Menu> childs = new ArrayList<Menu>();
		for(Menu menu : list){
			int _parentId = menu.getParetId();
			if(parentId ==_parentId){
				childs.add(menu);
			}
		}
		return childs;
	}

}