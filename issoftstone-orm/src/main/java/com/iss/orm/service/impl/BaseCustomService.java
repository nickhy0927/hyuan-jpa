package com.iss.orm.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.iss.common.exception.DaoException;
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
	@Transactional(readOnly = false)
	public E saveEntity(E entity) throws ServiceException {
		try {
			return this.dao.saveEntity(entity);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("保存信息失败", e);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void saveBatch(List<E> entities) throws ServiceException {
		try {
			this.dao.save(entities);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("批量保存信息失败", e);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(ID id) throws ServiceException {
		try {
			E t = get(id);
			if (t != null) {
				this.dao.delete(id);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("删除信息失败", e);
		}
	}

	
	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<E> entities) throws ServiceException {
		try {
			this.dao.delete(entities);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("批量删除信息失败", e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() throws ServiceException {
		try {
			this.dao.deleteAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("删除所有数据信息失败", e);
		}
	}

	
	@Override
	@Transactional(readOnly = false)
	public void deleteBatch(ID[] ids) throws ServiceException {
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					ID id = ids[i];
					delete(id);
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("删除所有数据信息失败", e);
		}
	}

	
	@Override
	public List<E> findAll() throws ServiceException {
		try {
			return dao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("查询所有数据信息失败", e);
		}
	}

	
	@Override
	public E get(ID id) throws ServiceException {
		try {
			return this.dao.findOne(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("查询一条数据信息失败", e);
		}
	}

	
	@Override
	public List<E> queryByMap(Map<String, Object> map) throws ServiceException {
		try {
			return this.dao.queryByMap(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("查询数据集合信息失败", e);
		}
	}

	
	@Override
	public List<E> queryByMap(Map<String, Object> paramMap, Sort sort) throws ServiceException {
		try {
			return dao.queryByMap(paramMap, sort);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("查询数据集合信息失败", e);
		}
	}

	@Override
	public PagerInfo<E> queryPageByMap(Map<String, Object> map, PageSupport support) throws ServiceException {
		try {
			support.setTotalRecord(queryByMap(map).size());
			Sort sort = new Sort(Sort.Direction.ASC, support.getOrder());
			if (StringUtils.equals(support.getSort().toUpperCase(), Sortable.DESC.toString().toUpperCase())) {
				sort = new Sort(Sort.Direction.DESC, support.getOrder());
			}
			PageRequest pageable = new PageRequest(support.getPage() - 1, support.getLimit(), sort);
			Page<E> pageInfo = this.dao.queryPageByMap(map, pageable);
			PagerInfo<E> pagerInfo = new PagerInfo<E>(support, pageInfo.getContent());
			return pagerInfo;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("查询数据分页信息失败", e);
		}
	}
	
}