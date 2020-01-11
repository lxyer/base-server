package com.lxyer.base.common.utils.httpclientutil;

import com.alibaba.fastjson.JSON;
import com.lxyer.base.common.MyException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;

/**
 * 王金龙
 */
public abstract class BaseUni {
    private static final Logger logger = LoggerFactory.getLogger(BaseUni.class);


    /**
     * 执行get请求<br/>
     * GET 请求 适用于参数已经拼接于url中
     *
     * @param url      接口地址
     * @param dataType 接口返回的数据模型
     * @param <T>      泛型
     * @return 结果集
     * @throws IOException
     * @throws InstantiationError
     * @throws IllegalAccessError
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @since 1.1
     */
    protected <T> T excuteGet(String url, Class<T> dataType) throws MyException {
        try {

            //调用接口
            String ret = HttpClientUtil.get(url);
            // 反序列化接口出参
            return JSON.parseObject(ret, dataType);
        } catch (Exception ioe) {
            //调用接口超时
            logger.error(String.format("调用接口失败, url:%s ", url), ioe);
            throw new MyException("调用接口失败");
        }
    }


    /**
     * 执行post请求<br/>
     * 适用-有返回类型（基本类型或者引用类型）-类入参
     *
     * @param url    接口地址
     * @param params 请求入参，序列化成的json的字符串(实体)
     * @param <T>    泛型
     * @return 结果集
     * @throws IOException
     * @throws InstantiationError
     * @throws IllegalAccessError
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected <T> T excutePost(String url, String params, Class<T> dataType) throws Exception {
        try {
            // 调用接口
            String ret = HttpClientUtil.post(url, params);

            // 反序列化接口出参
            return JSON.parseObject(ret, dataType);
        } catch (ConnectTimeoutException ioe) {
            //调用接口超时
            logger.error("调用接口超时, url:{} , params: {}", url, params, ioe);
            throw new MyException("404调用接口超时");
        } catch (ClientProtocolException ioe) {
            //返回非200的http状态码
            logger.error("返回非200的http状态码, url:{} , params: {}", url, params, ioe);
            throw new MyException("400" + ioe.getMessage());
        } catch (ParseException pe) {
            //http response 流解析异常
            logger.error("流解析异常, url:{} , params: {}", url, params, pe);
            throw new MyException("400" + pe.getMessage());
        }
    }


}
