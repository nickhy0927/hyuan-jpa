package com.iss.platform.access.role.service.impl;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.DefaultCustomService;
import com.iss.platform.access.role.entity.Role;
import com.iss.platform.access.role.service.RoleService;

/**
 * Created by Curtain on 2015/9/21.
 */
@Service
public class RoleServiceImpl extends DefaultCustomService<Role, String> implements RoleService {
}
