package ${packages}.${domainObjectName?lower_case}.dao;

import com.iss.orm.repository.CustomRepostiory;
import ${packages}.${domainObjectName?lower_case}.entity.${domainObjectName};

public interface ${domainObjectName}Dao extends CustomRepostiory<${domainObjectName},String> {
}