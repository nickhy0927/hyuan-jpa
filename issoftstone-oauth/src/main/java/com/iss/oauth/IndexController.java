package com.iss.oauth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.SysContants;
import com.iss.interceptor.OperateLog;
import com.iss.interceptor.OperateType;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

@Controller
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 进入首页
     * @return
     */
    @AccessAuthority(alias = "index")
    @RequestMapping(value = "index.do", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @ResponseBody
    @AccessAuthority(alias = "queryUserInfo")
    @OperateLog(method = "queryUserInfo", optType = OperateType.OptType.QUERY, message = "查询用户信息", service = UserService.class)
    @RequestMapping(value = "/queryUserInfo.json", method = RequestMethod.GET)
    public MessageObject<User> queryUserInfo() {
        Map<String, Object> paramMap = Maps.newConcurrentMap();
        User u = new User();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        u.setBrithday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        u.setEmail("h_y_12@163.com");
        u.setEnable(String.valueOf(1));
        u.setLocked(String.valueOf(0));
        u.setLoginName("admin");
        u.setSalt(salt);
        u.setStatus(SysContants.IsDelete.NO);
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        u.setPassword(encoder.encodePassword("123456", salt));
        u.setNickName("系统管理员");
        MessageObject<User> messageObject = MessageObject.getDefaultInstance();
        try {
            List<User> users = userService.queryByMap(paramMap);
            messageObject.ok("查询用户列表成功", users);
        } catch (ServiceException e) {
            e.printStackTrace();
            messageObject.error("查询用户列表失败");
        }
        return messageObject;
    }
}
