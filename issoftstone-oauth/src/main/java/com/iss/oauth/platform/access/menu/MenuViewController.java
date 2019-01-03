package com.iss.oauth.platform.access.menu;

import com.iss.common.anno.AccessAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuViewController {

    @AccessAuthority(alias = "menu-list")
    @RequestMapping(value = "/platform/access/menu/list.do", method = RequestMethod.GET)
    public String list() {
        return "platform/access/menu/list";
    }
}
