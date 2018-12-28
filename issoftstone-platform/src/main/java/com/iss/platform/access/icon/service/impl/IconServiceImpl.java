package com.iss.platform.access.icon.service.impl;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.DefaultCustomService;
import com.iss.platform.access.icon.entity.Icon;
import com.iss.platform.access.icon.service.IconService;

@Service
public class IconServiceImpl extends DefaultCustomService<Icon, String> implements IconService {

}
