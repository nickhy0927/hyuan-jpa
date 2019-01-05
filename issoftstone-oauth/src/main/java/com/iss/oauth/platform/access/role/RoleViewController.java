package com.iss.oauth.platform.access.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iss.common.anno.AccessAuthority;

/**
 * @author Hyuan
 */
@Controller
public class RoleViewController {

    @RequestMapping(value = "/platform/access/role/create.do")
    public String create() {
        return "platform/access/role/create";
    }

    @AccessAuthority(alias = "role-list-do")
    @RequestMapping(value = "/platform/access/role/list.do")
    public String list() {
        return "platform/access/role/list";
    }
    
    public static void main(String[] args) {
		System.out.println(2%2);
	}
}
