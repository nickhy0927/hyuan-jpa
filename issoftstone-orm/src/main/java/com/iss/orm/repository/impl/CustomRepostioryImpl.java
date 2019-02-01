package com.iss.orm.repository.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.common.exception.DaoException;
import com.iss.common.utils.IdEntity;
import com.iss.orm.repository.CustomRepostiory;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomRepostioryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements CustomRepostiory<T, ID> {

	private Class<T> entityClass;
	private EntityManager entityManager;

	public CustomRepostioryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		this.entityClass = domainClass;
	}

	@Override
	public List<T> queryByMap(Map<String, Object> map) throws DaoException {
		return createCriteria(map).list();
	}

	@Override
	public List<T> queryByCriteria(org.hibernate.Criteria criteria) throws DaoException {
		return criteria.list();
	}

	@Override
	public List<T> queryByMap(Map<String, Object> map, Sort sort) throws DaoException {
		Criteria criteria = createCriteria(map);
		Iterator<Sort.Order> it;
		if (sort != null) {
			for (it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	@Override
	public List<T> queryByCriteria(Criteria criteria, Sort sort) throws DaoException {
		Iterator<Sort.Order> it;
		if (sort != null) {
			for (it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	@Override
	public Page<T> queryPageByMap(Map<String, Object> map, Pageable pageable) throws DaoException {
		Criteria criteria = createCriteria(map);
		long total = countCriteriaResult(criteria);
		criteria.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		Iterator<Sort.Order> it;
		if (pageable.getSort() != null) {
			for (it = pageable.getSort().iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		Page<T> page = new PageImpl(criteria.list(), pageable, total);
		return page;
	}

	@Override
	public Page<T> queryPageByCriteria(Criteria criteria, Pageable pageable) throws DaoException {
		long total = countCriteriaResult(criteria);
		criteria.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		Iterator<Sort.Order> it;
		if (pageable.getSort() != null) {
			for (it = pageable.getSort().iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		Page<T> page = new PageImpl(criteria.list(), pageable, total);
		return page;
	}

	@Override
	public Criteria createCriteria(Map<String, Object> map) throws DaoException {
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Criteria c = getSession().createCriteria(this.entityClass);
		for (Map.Entry<String, Object> entry : set) {
			Object obj = entry.getValue();
			if ((obj != null) && (StringUtils.isNotEmpty(obj.toString()))) {
				Criterion criterion = mapEntryToCriterion((String) entry.getKey(), entry.getValue());
				c.add(criterion);
			}
		}
		return c;
	}

	@Override
	public Disjunction createdDisjunction(Map<String, Object> map) throws DaoException {
		Set<Map.Entry<String, Object>> set = map.entrySet();
		Disjunction or = Restrictions.disjunction();
		for (Map.Entry<String, Object> entry : set) {
			Object obj = entry.getValue();
			if ((obj != null) && (StringUtils.isNotEmpty(obj.toString()))) {
				Criterion criterion = mapEntryToCriterion((String) entry.getKey(), entry.getValue());
				or.add(criterion);
			}
		}
		return or;
	}

	private Criterion mapEntryToCriterion(String key, Object value) {
		String[] k = key.split("_");
		if (k.length < 2) {
			return Restrictions.eq(k[0], value);
		}
		if (StringUtils.equals("eq", k[1])) {
			return Restrictions.eq(k[0], value);
		}
		if (StringUtils.equals("ne", k[1])) {
			return Restrictions.ne(k[0], value);
		}
		if (StringUtils.equals("lt", k[1])) {
			return Restrictions.lt(k[0], value);
		}
		if (StringUtils.equals("le", k[1])) {
			return Restrictions.le(k[0], value);
		}
		if (StringUtils.equals("gt", k[1])) {
			return Restrictions.gt(k[0], value);
		}
		if (StringUtils.equals("ge", k[1])) {
			return Restrictions.ge(k[0], value);
		}
		if (StringUtils.equals("li", k[1])) {
			return Restrictions.like(k[0], "%" + value + "%");
		}
		if (StringUtils.equals("nl", k[1])) {
			return Restrictions.not(Restrictions.like(k[0], "%" + value + "%"));
		}
		if (StringUtils.equals("in", k[1])) {
			return Restrictions.in(k[0], (List) value);
		}
		if (StringUtils.equals("ni", k[1])) {
			return Restrictions.not(Restrictions.in(k[0], (List) value));
		}
		return Restrictions.eq(key, value);
	}

	protected long countCriteriaResult(Criteria c) {
		return c.list().size();
	}

	@Override
	public int nativeSqlUpdate(String sql, Object... values) throws DaoException {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int nativeSqlUpdate(String sql, Map<String, ?> values) throws DaoException {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (values != null) {
			query.setProperties(values);
		}
		return query.executeUpdate();
	}

	@Override
	public T saveEntity(T entity) throws DaoException {
		if ((entity instanceof IdEntity) ) {
			try {
				IdEntity idEntity = (IdEntity) entity;
				if (StringUtils.isNotEmpty(idEntity.getId())) {
					((IdEntity) entity).setUpdateTime(new Date());
					String id = idEntity.getId();
					T t = findOne((ID) id);
					Field[] fields = t.getClass().getDeclaredFields();
					Field[] eFields = entity.getClass().getDeclaredFields();
					for (Field field : fields) {
						for (Field f : eFields) {
							if (StringUtils.equals(field.getName(), f.getName())) {
								field.setAccessible(true);
								f.setAccessible(true);
								Object object = f.get(entity);
								if (object != null) {
									field.set(t, object);
								}
							}
						}
					}
					Field[] entityFields = entity.getClass().getSuperclass().getDeclaredFields();
					Field[] tFields = t.getClass().getSuperclass().getDeclaredFields();
					for (Field tf : tFields) {
						for (Field field : entityFields) {
							if (StringUtils.equals(field.getName(), tf.getName())) {
								tf.setAccessible(true);
								field.setAccessible(true);
								Object object = field.get(entity);
								if (object != null) {
									tf.set(t, object);
								}
							}
						}
					}
					return saveAndFlush(t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return saveAndFlush(entity);
	}

	public List<T> queryByMap(Map<String, Object> map, int limit) throws DaoException {
		Criteria criteria = createCriteria(map);
		criteria.setFirstResult(0).setMaxResults(limit);
		return criteria.list();
	}

	public List<T> queryByMap(Map<String, Object> map, int limit, Sort sort) throws DaoException {
		Criteria criteria = createCriteria(map);
		criteria.setFirstResult(0).setMaxResults(limit);
		if (sort != null) {
			for (Iterator<Sort.Order> it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	public List<T> queryByMap(Map<String, Object> map, int offset, int limit) throws DaoException {
		Criteria criteria = createCriteria(map);
		criteria.setFirstResult(offset).setMaxResults(limit);
		return criteria.list();
	}

	public List<T> queryByMap(Map<String, Object> map, int offset, int limit, Sort sort) throws DaoException {
		Criteria criteria = createCriteria(map);
		criteria.setFirstResult(offset).setMaxResults(limit);
		Iterator<Sort.Order> it;
		if (sort != null) {
			for (it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	@Override
	public List<T> queryByCriteria(Criteria criteria, int limit) throws DaoException {
		return criteria.setFirstResult(0).setMaxResults(limit).list();
	}

	public List<T> queryByCriteria(Criteria criteria, int limit, Sort sort) throws DaoException {
		criteria.setFirstResult(0).setMaxResults(limit);
		Iterator<Sort.Order> it;
		if (sort != null) {
			for (it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	public List<T> queryByCriteria(Criteria criteria, int offset, int limit) throws DaoException {
		return criteria.setFirstResult(offset).setMaxResults(limit).list();
	}

	public List<T> queryByCriteria(Criteria criteria, int offset, int limit, Sort sort) throws DaoException {
		criteria.setFirstResult(offset).setMaxResults(limit);
		Iterator<Sort.Order> it;
		if (sort != null) {
			for (it = sort.iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		return criteria.list();
	}

	private Session getSession() {
		return (Session) this.entityManager.getDelegate();
	}

	@Override
	public void insertInBatch(List<T> entitys) throws DaoException {
		for (int i = 0; i < entitys.size(); i++) {
			this.entityManager.persist(entitys.get(i));
			if (i % 50 == 0) {
				this.entityManager.flush();
				this.entityManager.clear();
			}
		}
	}

	@Override
	public Page<T> queryByPage(Pageable pageable) throws DaoException {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		long total = countCriteriaResult(criteria);
		criteria.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		Iterator<Sort.Order> it;
		if (pageable.getSort() != null) {
			for (it = pageable.getSort().iterator(); it.hasNext();) {
				Sort.Order order = (Sort.Order) it.next();
				if (order.getDirection().equals(Sort.Direction.DESC)) {
					criteria.addOrder(Order.desc(order.getProperty()));
				} else {
					criteria.addOrder(Order.asc(order.getProperty()));
				}
			}
		}
		Page<T> page = new PageImpl<T>(criteria.list(), pageable, total);
		return page;
	}

	@Override
	public List<T> findByEntityList(Map<String, Object> paramMap) throws DaoException {
		Criteria criteria = createCriteria(paramMap);
		return queryByCriteria(criteria);
	}

}
