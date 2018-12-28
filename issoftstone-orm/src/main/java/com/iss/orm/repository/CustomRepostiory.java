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

public abstract interface CustomRepostiory<T, ID extends Serializable> extends JpaRepository<T, ID> {

	abstract List<T> queryByMap(Map<String, Object> paramMap);

	abstract List<T> queryByCriteria(Criteria paramCriteria);

	abstract List<T> queryByCriteria(Criteria paramCriteria, int paramInt);

	abstract List<T> queryByCriteria(Criteria paramCriteria, Sort paramSort);

	abstract Page<T> queryByPage(Pageable paramPageable);

	abstract Page<T> queryPageByMap(Map<String, Object> paramMap, Pageable paramPageable);

	abstract Page<T> queryPageByCriteria(Criteria paramCriteria, Pageable paramPageable);

	abstract Criteria createCriteria(Map<String, Object> paramMap);

	abstract Disjunction createdDisjunction(Map<String, Object> paramMap);

	abstract int nativeSqlUpdate(String paramString, Object... paramVarArgs);

	abstract int nativeSqlUpdate(String paramString, Map<String, ?> paramMap);

	abstract T saveEntity(T paramT);

	abstract void insertInBatch(List<T> paramList);

	abstract List<T> findByEntityList(Map<String, Object> paramMap);
	
	abstract List<T> queryByMap(Map<String, Object> paramMap,Sort sort);

}
