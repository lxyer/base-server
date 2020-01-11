package com.lxyer.base.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.lxyer.base.common.base.RestResponse;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lxyer
 * @Description: 调用接口打印性能日志以及接口报错之后记录错误日志
 * @Date: 2019/9/20
 * @Time: 15:16
 */
@Component
@Aspect
public class InterfaceRequestErrrorAndPerformanceLog {

    public static final Logger logger = LoggerFactory.getLogger(InterfaceRequestErrrorAndPerformanceLog.class);


//    @Resource
//    private RabbitMQService rabbitMQService;
//    @Resource
//    private InterfaceErrorService interfaceErrorService;

    @Pointcut("execution(* com.deepsec.notarization.modules.*.*.controller.*Controller.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            //处理入参特殊字符和sql注入攻击
            checkRequestParam(pjp);
            Long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            long start = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uri = request.getRequestURI();
            Object result = pjp.proceed();
            long end = System.currentTimeMillis();
            List<Object> list = Arrays.asList(pjp.getArgs());
            List<Object> objectList = list.stream().filter(i->i instanceof MultipartFile).collect(Collectors.toList());
            if (objectList.size()==0) {
                logger.info("\n" +
                        "\n***************************  start " + sdf.format(start) + " *************************************************" +
                        "\n请求接口:" + uri +
                        "\n请求参数:" + JSON.toJSONString(pjp.getArgs(), true) +
                        "\n请求结束  " + sdf.format(end) + "耗时 " + (end - start) + "ms" +
                        "\n返回值类型:" + pjp.getSignature() +
                        "\n返回值数据:" + JSON.toJSONString(result, true) +
                        "\n***************************    end    *************************\n");
            }

            //当接口请求时间大于3秒时，标记为异常调用时间，并记录入库
//            if(consumeTime > 3000){
            //todo
//            }
            return result;
        } catch (Exception throwable) {
            handlerException(pjp, throwable);
        }
        return null;
    }

    /**
     * @Author: lxyer
     * @Description: 处理接口调用异常
     * @Date: 15:13 2019/9/18
     */
    private RestResponse handlerException(ProceedingJoinPoint pjp, Throwable e) {
        RestResponse response = new RestResponse();
        Object apiResponse;
        if (e.getClass().isAssignableFrom(MyException.class)) {
//            apiResponse = e.getMessage();
            logger.error("\nMyException{\n方法：" + pjp.getSignature() + ",\n参数：" + JSON.toJSONString(pjp.getArgs(), true) + ",\n异常：" + e.getMessage() + "\n}", e);
        } else if (e instanceof RuntimeException) {
            logger.error("\nRuntimeException{\n方法：" + pjp.getSignature() + ",\n参数：" + JSON.toJSONString(pjp.getArgs(), true) + ",\n异常：" + e.getMessage() + "\n}", e);
//            apiResponse = e.getMessage();
        } else {
            logger.error("\n异常{\n方法：" + pjp.getSignature() + ",\n参数：" + JSON.toJSONString(pjp.getArgs(), true) + ",\n异常：" + e.getMessage() + "\n}", e);
//            apiResponse = e.getMessage();
        }
        return response;
    }

    /**
     * @Author: lxyer
     * @Description: 处理入参特殊字符和sql注入攻击
     * @Date: 15:37 2019/10/25
     */
    private void checkRequestParam(ProceedingJoinPoint pjp) throws Exception {
        String str = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            logger.info("访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
            throw new Exception("输入参数存在SQL注入风险");
        }
        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
            logger.info("访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
            throw new Exception("输入参数含有非法字符!");
        }
    }


}

