package com.iss.platform.access.permission.dao;

import org.springframework.stereotype.Repository;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.permission.entity.Permission;

@Repository
public interface PermissionDao extends CustomRepostiory<Permission, String> {

}
