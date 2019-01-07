package com.iss.oauth.password;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationFilter {

    // 是否开启验证码功能
    private boolean isOpenValidateCode = true;

    public static final String VALIDATE_CODE = "verifyCode";

    public UsernamePasswordAuthentication() {
        SimpleUrlAuthenticationFailureHandler failedHandler = (SimpleUrlAuthenticationFailureHandler) getFailureHandler();
        failedHandler.setDefaultFailureUrl("/login.jsp?error=2");
        this.setAuthenticationManager(getAuthenticationManager());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String verification = request.getParameter("code");
        String captcha = request.getSession().getAttribute(VALIDATE_CODE).toString();
        if (!captcha.contentEquals(verification)) {
            try {
                throw new Exception("captcha code not matched!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (!requiresAuthentication(req, res)) {
            chain.doFilter(request, response);
            return;
        }
        if (isOpenValidateCode) {
            if (!checkValidateCode(req, res)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }

    protected boolean checkValidateCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String sessionValidateCode = obtainSessionValidateCode(session);
        // 让上一次的验证码失效
        session.setAttribute(VALIDATE_CODE, null);
        String validateCodeParameter = obtainValidateCodeParameter(request);
        if (StringUtils.isEmpty(validateCodeParameter)
                || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
            unsuccessfulAuthentication(request, response, new InsufficientAuthenticationException("输入的验证码不正确"));
            return false;
        }
        // 保存一些session信息
        session.setAttribute(VALIDATE_CODE, validateCodeParameter);
        return true;
    }

    private String obtainValidateCodeParameter(HttpServletRequest request) {
        Object obj = request.getParameter(VALIDATE_CODE);
        return null == obj ? "" : obj.toString();
    }

    protected String obtainSessionValidateCode(HttpSession session) {
        Object obj = session.getAttribute(VALIDATE_CODE);
        return null == obj ? "" : obj.toString();
    }
}
