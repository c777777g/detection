package com.commonsl.service.impl;

import java.io.Serializable;
import java.util.List;

import com.commonsl.dao.BaseDao;
import com.commonsl.model.Entity;
import com.commonsl.model.Entity.Criteria;
import com.commonsl.model.Entity.Pagination;
import com.commonsl.model.Entity.SimpleCriteria;
import com.commonsl.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<K extends Serializable, E extends Entity> implements BaseService<K, E> {
	
	@Autowired
	private BaseDao<K, E> dao;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public K save(E entity) {
		return dao.save(entity);
	}

	@Override
	public int delete(K id) {
		return dao.delete(id);
	}

	@Override
	public int delete(Criteria criteria) {
		return dao.delete(criteria);
	}

	@Override
	public int update(E entity) {
		return dao.update(entity);
	}

	@Override
	public int update(E entity, Criteria criteria) {
		return dao.update(entity, criteria);
	}

	@Override
	public E selectOne(K id) {
		return dao.selectOne(id);
	}

	@Override
	public E selectOne(Criteria criteria) {
		return dao.selectOne(criteria);
	}

	@Override
	public List<E> selectAll() {
		return dao.selectAll();
	}

	@Override
	public List<E> selectList(Criteria criteria) {
		return dao.selectList(criteria);
	}

	@Override
	public long selectCount(Criteria criteria) {
		return dao.selectCount(criteria);
	}

	@Override
	public Pagination selectPage(Criteria criteria, Pagination pagination) {
		if (pagination == null) {
			pagination = new Pagination();
		}
		if (pagination.getLimit() == 0) {
			pagination.setLimit(10);
		}
		if (pagination.getSort() != null) {
			if (pagination.getOrder() == null) {
				pagination.setOrder("ASC");
			}
			((SimpleCriteria) criteria).orderBy(pagination.getSort(), pagination.getOrder());
		}
		if (pagination.getField() != null) {
			String searchValue = pagination.getSearch();
			if (searchValue != null && !searchValue.contains("%")) {
				searchValue = "%" + searchValue + "%";
			}
			((SimpleCriteria) criteria).like(pagination.getField(), searchValue);
		}
		if (pagination.getPage() != 0) {
			pagination.setOffset((pagination.getPage() - 1) * pagination.getLimit());
		}
		((SimpleCriteria) criteria).limit(pagination.getOffset(), pagination.getLimit());
		pagination.setRows(dao.selectList(criteria));
		pagination.setTotal(dao.selectCount(criteria));
		boolean division = pagination.getTotal() % pagination.getLimit() == 0;
		int pages = (int) (pagination.getTotal() / pagination.getLimit());
		pagination.setPages(division ? pages : pages + 1);
		if (pagination.getPage() != 0) {
			pagination.setPrev(pagination.getPage() == 1 ? 1 : pagination.getPage() - 1);
			pagination.setNext(pagination.getPage() == pagination.getPages() ? pagination.getPage() : pagination.getPage() + 1);
		}
		return pagination;
	}

}