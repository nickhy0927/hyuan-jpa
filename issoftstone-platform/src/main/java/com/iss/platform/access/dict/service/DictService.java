package com.iss.platform.access.dict.service;

import java.util.List;

import com.iss.orm.service.CustomService;
import com.iss.platform.access.dict.entity.Dict;

public interface DictService extends CustomService<Dict, String> {

	List<Dict> queryDictByParentDictType(String dictType);
	
	/**
	 * 查询顶级类型
	 * @return
	 */
	List<Dict> queryDictByParentNull();
}
