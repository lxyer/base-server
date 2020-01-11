//
// Source code recreated from a .class debtorfile by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxyer.base.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class IPUtils {
    private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

    public IPUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;

        try {
            String ips = request.getHeader("x-forwarded-for");
            if (ips != null && ips.length() > 0) {
                ip = ips.split(",")[0];
            }

            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }

            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception var3) {
            logger.error("IPUtils ERROR ", var3);
        }

        return ip;
    }

    public static String getIp() {
        String ip = "8.8.8.8";

        try {
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            ip = getIpAddr(request);
        } catch (Exception var2) {
            logger.error("IPUtils获取ip失败:" + var2);
        }

        return ip;
    }
}
