package com.anycommon.logger.util;


import cn.hutool.http.Header;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author: wangkai
 * @date: 2018/11/24
 * @instructions:
 */
public class CookieUtils {

    /**
     * cookie过期时间 一个月, 未使用
     */
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;
    /**
     * 保存路径, 网站根路径
     */
    private static final String COOKIE_PATH = "/";

    /**
     * 保存
     *
     * @param response
     * @param key
     * @param value
     * @param ifRemember
     */
    public static void set(HttpServletResponse response, String key, String value, boolean ifRemember) {
        int age = ifRemember ? COOKIE_MAX_AGE : -1;
        set(response, key, value, null, COOKIE_PATH, age, true);
    }

    /**
     * 保存
     *
     * @param request
     * @param response
     * @param key
     * @param value
     */
    public static void set(HttpServletRequest request, HttpServletResponse response, String key, String value, boolean ifRemember) {
        String domain = null;
        String origin = request.getHeader(Header.ORIGIN.name());
        if (StringUtils.isNotBlank(origin)) {
             domain = origin.replaceAll("http://", "").replaceAll("https://", "");
             if (domain.contains(":")) {
                 domain = domain.split(":")[0];
             }
        }

        set(response, key, value, domain, COOKIE_PATH, COOKIE_MAX_AGE, false);
    }

    /**
     * 保存
     *
     * @param response
     * @param key
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        /* 不设置cookie过期时间,用户登录信息过期存redis处理 */
        //cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
//        response.setHeader("Set-Cookie", "key=value; SameSite=None; Secure");
        response.addCookie(cookie);
    }

    /**
     * 查询value
     *
     * @param request
     * @param key
     * @return
     */
    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 查询Cookie
     *
     * @param request
     * @param key
     */
    private static Cookie get(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
