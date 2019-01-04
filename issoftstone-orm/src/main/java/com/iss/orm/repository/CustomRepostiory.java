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

/**
 * @author Administrator
 */
public interface CustomRepostiory<T, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> paramMap);

	List<T> queryByCriteria(Criteria paramCriteria);

	List<T> queryByCriteria(Criteria paramCriteria, int paramInt);

	List<T> queryByCriteria(Criteria paramCriteria, Sort paramSort);

	Page<T> queryByPage(Pageable paramPageable);

	Page<T> queryPageByMap(Map<String, Object> paramMap, Pageable paramPageable);

	Page<T> queryPageByCriteria(Criteria paramCriteria, Pageable paramPageable);

	Criteria createCriteria(Map<String, Object> paramMap);

	Disjunction createdDisjunction(Map<String, Object> paramMap);

	int nativeSqlUpdate(String paramString, Object... paramVarArgs);

	int nativeSqlUpdate(String paramString, Map<String, ?> paramMap);

	T saveEntity(T paramT);

	void insertInBatch(List<T> paramList);

	List<T> findByEntityList(Map<String, Object> paramMap);
	
	List<T> queryByMap(Map<String, Object> paramMap,Sort sort);

}
