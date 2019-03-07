package com.iss.platform.system.database.dao;

import org.springframework.stereotype.Repository;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.system.database.entity.DataBase;

@Repository
public interface DataBaseDao extends CustomRepostiory<DataBase, String> {

}
