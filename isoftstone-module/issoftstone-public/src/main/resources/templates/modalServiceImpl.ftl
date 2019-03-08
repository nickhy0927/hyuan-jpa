package ${packages}.${domainObjectName?lower_case}.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import ${packages}.${domainObjectName?lower_case}.entity.${domainObjectName};
import ${packages}.${domainObjectName?lower_case}.service.${domainObjectName}Service;
import org.springframework.stereotype.Service;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Service
public class ${domainObjectName}ServiceImpl extends BaseCustomService<${domainObjectName}, String> implements ${domainObjectName}Service {
}
