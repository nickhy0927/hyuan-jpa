package com.iss.platform.system.exceptionlog.service;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.system.exceptionlog.entity.ExceptionLog;

@Service
public class ExceptionLogServiceImpl extends BaseCustomService<ExceptionLog, String> implements ExceptionLogService {

}
