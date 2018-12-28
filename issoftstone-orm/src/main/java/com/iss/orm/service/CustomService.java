package com.iss.orm.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.iss.common.exception.ServiceException;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;

public abstract interface CustomService<E, ID extends Serializable> {

	abstract void delete(ID id) throws ServiceException;

	abstract void delete(Iterable<E> paramIterable) throws ServiceException;

	abstract void deleteAll() throws ServiceException;

	abstract void deleteBatch(ID[] ids) throws ServiceException;

	abstract List<E> findAll() throws ServiceException;

	abstract E get(ID id) throws ServiceException;

	abstract List<E> queryByMap(Map<String, Object> paramMap) throws ServiceException;

	abstract List<E> queryByMap(Map<String, Object> paramMap, Sort sort) throws ServiceException;

	abstract Page<E> queryPageByMap(Map<String, Object> paramMap, Pageable paramPageable) throws ServiceException;

	abstract PagerInfo<E> queryPageByMap(Map<String, Object> map, PageSupport support) throws ServiceException;

	abstract E save(E entity) throws ServiceException;
}