package com.iss.platform.system.optlog.service.impl;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.DefaultCustomService;
import com.iss.platform.system.optlog.entity.OptLog;
import com.iss.platform.system.optlog.service.OptLogService;

@Service
public class OptLogServiceImpl extends DefaultCustomService<OptLog, String> implements OptLogService {

}
