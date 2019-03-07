package com.iss.orm.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iss.common.exception.DaoException;

/**
 * @author Administrator
 */
public interface CustomRepostiory<T, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> paramMap) throws DaoException;

	List<T> queryByCriteria(Criteria paramCriteria) throws DaoException;

	List<T> queryByCriteria(Criteria paramCriteria, int paramInt) throws DaoException;

	List<T> queryByCriteria(Criteria paramCriteria, Sort paramSort) throws DaoException;

	Page<T> queryByPage(Pageable paramPageable) throws DaoException;

	Page<T> queryPageByMap(Map<String, Object> paramMap, Pageable paramPageable) throws DaoException;

	Page<T> queryPageByCriteria(Criteria paramCriteria, Pageable paramPageable) throws DaoException;

	Criteria createCriteria(Map<String, Object> paramMap) throws DaoException;

	Disjunction createdDisjunction(Map<String, Object> paramMap) throws DaoException;

	int nativeSqlUpdate(String paramString, Object... paramVarArgs) throws DaoException;

	int nativeSqlUpdate(String paramString, Map<String, ?> paramMap);

	T saveEntity(T paramT);

	void insertInBatch(List<T> paramList);

	List<T> findByEntityList(Map<String, Object> paramMap);
	
	List<T> queryByMap(Map<String, Object> paramMap,Sort sort);

}
