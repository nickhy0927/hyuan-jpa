package com.iss.platform.access.menu.service.impl;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.DefaultCustomService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.service.MenuService;

/**
 * Created by Curtain on 2015/9/15.
 */
@Service
public class MenuServiceImpl extends DefaultCustomService<Menu, String> implements MenuService {
}
