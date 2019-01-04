package com.iss.orm.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.orm.repository.impl.CustomRepostioryImpl;

public class DefaultRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends
		JpaRepositoryFactoryBean<R, T, I> {
	
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new DynamicQueryRepositoryFactory<>(entityManager);
	}

	private static class DynamicQueryRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {
		private EntityManager entityManager;

		public DynamicQueryRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}
		
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new CustomRepostioryImpl<>(metadata.getDomainType(), this.entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return CustomRepostiory.class;
		}
	}
}
