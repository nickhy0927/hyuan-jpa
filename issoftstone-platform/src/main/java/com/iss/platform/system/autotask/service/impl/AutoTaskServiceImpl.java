package com.iss.platform.system.autotask.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.system.autotask.entity.AutoTask;
import com.iss.platform.system.autotask.service.AutoTaskService;
import org.springframework.stereotype.Service;

@Service
public class AutoTaskServiceImpl extends BaseCustomService<AutoTask, String> implements AutoTaskService {
}
