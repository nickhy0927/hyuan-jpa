package com.iss.platform.access.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.access.dict.dao.DictDao;
import com.iss.platform.access.dict.entity.Dict;
import com.iss.platform.access.dict.service.DictService;

@Service
public class DictServiceImpl extends BaseCustomService<Dict, String> implements DictService {

	@Autowired
	private DictDao dictDao;

	@Override
	public List<Dict> queryDictByParentDictType(String dictType) {
		return dictDao.queryDictByParentDictType(dictType);
	}
	
	@Override
	public List<Dict> queryDictByParentNull() {
		return dictDao.queryDictByParentNull();
	}

}
