package com.commonsl.service;

import com.commonsl.model.Menu;

import java.util.List;

public interface MenuService extends BaseService<Integer, Menu> {
    List<Menu> getMenuListByAdmin(int type);
}