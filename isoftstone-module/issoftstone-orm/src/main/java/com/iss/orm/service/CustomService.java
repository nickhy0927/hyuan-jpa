package com.iss.orm.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.iss.common.exception.ServiceException;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;

/**
 * @author Administrator
 */
public interface CustomService<E, ID extends Serializable> {

	/**
	 *
	 * @param id
	 * @throws ServiceException
	 */
	void delete(ID id) throws ServiceException;

	void delete(Iterable<E> paramIterable) throws ServiceException;

	void deleteAll() throws ServiceException;

	void deleteBatch(ID[] ids) throws ServiceException;

	List<E> findAll() throws ServiceException;

	E get(ID id) throws ServiceException;

	List<E> queryByMap(Map<String, Object> paramMap) throws ServiceException;

	List<E> queryByMap(Map<String, Object> paramMap, Sort sort) throws ServiceException;

	PagerInfo<E> queryPageByMap(Map<String, Object> map, PageSupport support) throws ServiceException;

	E saveEntity(E entity) throws ServiceException;
	
	Iterable<E> saveBatch(Iterable<E> paramIterable) throws ServiceException;
}