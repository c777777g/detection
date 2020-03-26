package com.commonsl.dao.impl;

import com.commonsl.dao.BaseDao;
import com.commonsl.model.Entity;
import com.commonsl.model.Entity.Criteria;
import com.commonsl.model.Entity.PrimaryKey;
import com.commonsl.model.Entity.SimpleCriteria;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<K extends Serializable, E extends Entity> implements BaseDao<K, E> {

	private Class<E> entityClass;

	@Autowired
	@Qualifier("sqlSession")
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public K save(E entity) {
		return sqlSessionTemplate.insert(statement("save"), entity) == 1 ? (K) PrimaryKey.of(entity).getValue() : null;
	}

	@Override
	public int delete(K id) {
		return delete(new SimpleCriteria().eq(PrimaryKey.of(entityClass).getName(), id));
	}

	@Override
	public int delete(Criteria criteria) {
		return sqlSessionTemplate.delete(statement("delete"), criteria.toMapParameter());
	}

	@Override
	public int update(E entity) {
		return update(entity, new SimpleCriteria().eq(PrimaryKey.of(entity).getName(), PrimaryKey.of(entity).getValue()));
	}

	@Override
	public int update(E entity, Criteria criteria) {
		Map<String, Object> param = criteria.toMapParameter();
		param.put("entity", entity);
		return sqlSessionTemplate.update(statement("update"), param);
	}

	@Override
	public E selectOne(K id) {
		return selectOne(new SimpleCriteria().eq(PrimaryKey.of(entityClass).getName(), id));
	}

	@Override
	public E selectOne(Criteria criteria) {
		List<E> list = selectList(criteria);
		if (list == null) {
			return null;
		}
		int size = list.size();
		if (size == 0) {
			return null;
		}
		if (size == 1) {
			return list.get(0);
		}
		if (size > 1) {
			throw new TooManyResultsException("Expected 1 result (or null) to be returned, but found " + size);
		}
		return null;
	}

	@Override
	public List<E> selectAll() {
		return selectList(null);
	}

	@Override
	public List<E> selectList(Criteria criteria) {
		return sqlSessionTemplate.selectList(statement("selectList"), criteria == null ? null : criteria.toMapParameter());
	}

	@Override
	public long selectCount(Criteria criteria) {
		return sqlSessionTemplate.selectOne(statement("selectCount"), criteria == null ? null : criteria.toMapParameter());
	}
	
	protected String statement(String id) {
		return String.format("com.commonsl.dao.%sDao.%s", entityClass.getSimpleName(), id);
	}
	
	{
		entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

}