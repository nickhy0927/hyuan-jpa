package com.iss.orm.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.iss.common.exception.ServiceException;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PageSupport.Sortable;
import com.iss.common.utils.PagerInfo;
import com.iss.orm.repository.CustomRepostiory;
import com.iss.orm.service.CustomService;

@Transactional(readOnly = true)
public abstract class BaseCustomService<E, ID extends Serializable> implements CustomService<E, ID> {

	@Autowired
	private CustomRepostiory<E, ID> dao;

	@Override
	@Transactional
	public E saveEntity(E entity) throws ServiceException {
		return this.dao.saveEntity(entity);
	}
	
	@Override
	@Transactional
	public void delete(ID id) throws ServiceException {
		E t = get(id);
		if (t != null) {
			this.dao.delete(id);
		}
	}

	
	@Override
	@Transactional
	public void delete(Iterable<E> entities) throws ServiceException {
		this.dao.delete(entities);
	}

	@Override
	@Transactional
	public void deleteAll() throws ServiceException {
		this.dao.deleteAll();
	}

	
	@Override
	@Transactional
	public void deleteBatch(ID[] ids) throws ServiceException {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				ID id = ids[i];
				delete(id);
			}
		}
	}

	
	@Override
	public List<E> findAll() throws ServiceException {
		return dao.findAll();
	}

	
	@Override
	public E get(ID id) throws ServiceException {
		return this.dao.findOne(id);
	}

	
	@Override
	public List<E> queryByMap(Map<String, Object> map) throws ServiceException {
		return this.dao.queryByMap(map);
	}

	
	@Override
	public List<E> queryByMap(Map<String, Object> paramMap, Sort sort) throws ServiceException {
		return dao.queryByMap(paramMap, sort);
	}

	
	@Override
	public Page<E> queryPageByMap(Map<String, Object> map, Pageable pageable) throws ServiceException {
		return this.dao.queryPageByMap(map, pageable);
	}

	
	@Override
	public PagerInfo<E> queryPageByMap(Map<String, Object> map, PageSupport support) throws ServiceException {
		support.setTotalRecord(queryByMap(map).size());
		Sort sort = new Sort(Sort.Direction.ASC, support.getOrder());
		if (StringUtils.equals(support.getSort().toUpperCase(), Sortable.DESC.toString().toUpperCase())) {
			sort = new Sort(Sort.Direction.DESC, support.getOrder());
		}
		PageRequest pageable = new PageRequest(support.getPage() - 1, support.getLimit(), sort);
		Page<E> pageInfo = queryPageByMap(map, pageable);
		PagerInfo<E> pagerInfo = new PagerInfo<E>(support, pageInfo.getContent());
		return pagerInfo;
	}
	
}