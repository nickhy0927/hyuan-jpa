package com.iss.platform.access.dict.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.dict.entity.Dict;

@Repository
public interface DictDao extends CustomRepostiory<Dict, String> {

	@Query("select d from Dict d left join fetch d.dict p where p.dictType = ?1 and p.enable = 1 and p.status = 0 and d.enable = 1 and d.status = 0")
	List<Dict> queryDictByParentDictType(String dictType);
	
	@Query("select d from Dict d where d.dict.id is null")
	List<Dict> queryDictByParentNull();
}
