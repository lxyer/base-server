package com.lxyer.base.modules.businesslog.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxyer.base.common.utils.HttpContextUtils;
import com.lxyer.base.common.utils.IPUtils;
import com.lxyer.base.modules.businesslog.annotation.BusinessLog;
import com.lxyer.base.modules.businesslog.entity.BusinessLogEntity;
import com.lxyer.base.modules.businesslog.service.BusinessLogService;

@Aspect
@Component
public class BusinessLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(BusinessLogAspect.class);

    @Autowired
    BusinessLogService businessLogService;


    @Pointcut("@annotation(com.deepsec.notarization.modules.businesslog.annotation.BusinessLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessLogEntity businessLog = new BusinessLogEntity();
        BusinessLog syslog = method.getAnnotation(BusinessLog.class);
        if (syslog != null) {
            // 注解上的描述
            businessLog.setRemark(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        businessLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {

            //businessLog.setBusinessId(params);

            String params = JSON.toJSONString(args);
            businessLog.setParams(params);

            String param = JSON.toJSONString(args[0]);
            JSONObject jsonObject = JSON.parseObject(param);
            if (jsonObject.containsKey("businessId")) {
                // 业务id
                businessLog.setBusinessId(Long.valueOf(jsonObject.get("businessId") + ""));
            }
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        businessLog.setIp(IPUtils.getIpAddr(request));
        // 创建时间
        businessLog.setCreatedTime(new Date());
        // 执行时间
        businessLog.setTime(time);

        // 保存系统日志
        businessLogService.save(businessLog);
    }
}
