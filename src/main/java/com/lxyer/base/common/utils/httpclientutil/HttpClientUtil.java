package com.lxyer.base.common.utils.httpclientutil;


import com.lxyer.base.common.MyException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 王金龙
 *
 * @version 1.3
 * @since 1.0
 */
public class HttpClientUtil {
    private static final String CHARSET = "utf-8";
    private static int TIMEOUT = 40000;
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * GET 请求
     *
     * @param url URL地址
     * @return JSON字符串
     * @throws IOException
     * @throws ParseException
     */
    public static String get(String url) throws IOException, ParseException {
        //时钟
        long startTime = System.currentTimeMillis();
        //url 添加reqSeq 请求时间戳
        url = addReqSeq(url);
        //获取 httpClient
        //返回 信息
        String ret = null;
        try (CloseableHttpClient httpClient = CloseableHttpClientPool.getHttpclient()) {
            //创建 httpGet请求
            final HttpGet httpGet = new HttpGet(url);
            //添加request参数
            httpGet.setHeader("User-agent", "WEB");
            httpGet.setHeader("x-tymh-client-ip", RemoteIpUtil.getRemoteIp());
            // 创建一个自定义的响应句柄
            final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, CHARSET) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            //调用接口
            ret = httpClient.execute(httpGet, responseHandler);
            return ret;
        } finally {
            logger.info("running time {} ms, get url: {} , return :{}",
                    System.currentTimeMillis() - startTime, url, ret);
        }
    }

    /**
     * POST请求
     *
     * @param url  URL地址
     * @param json 入参的JSON字符串
     * @return 接口返回的JSON字符串
     * @throws IOException
     * @throws ParseException
     */
    public static String post(String url, String json) throws Exception {
        return post(url, json, ContentType.APPLICATION_JSON);
    }

    /**
     * PUT请求
     *
     * @param url      URL地址
     * @param json     入参的JSON字符串
     * @param userName 用户名密码
     * @param password 用户密码
     * @return 接口返回的JSON字符串
     * @throws IOException
     * @throws ParseException
     */
    public static String putBasicAuth(String url, String json, String userName, String password) throws Throwable {
        return putBasicAuth(url, json, ContentType.APPLICATION_JSON, userName, password);
    }


    /**
     * POST 请求 适用于表单提交
     *
     * @param url  请求地址
     * @param form 表单数据(格式<code>key1=value1&key2=value2</code>)
     * @return 接口返回的字符串
     * @throws IOException
     * @throws ParseException
     * @since 1.5
     */
    public static String postForm(String url, String form) throws Throwable {
        return post(url, form, ContentType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * POST请求 适用于自定义提交类型
     *
     * @param url         URL地址
     * @param str         入参的请求参数
     * @param contentType 请求类型
     * @return 接口返回的字符串
     * @throws IOException
     * @throws ParseException
     * @since 1.5
     */
    public static String post(String url, String str, ContentType contentType) throws Exception {
        //时钟
        long startTime = System.currentTimeMillis();
        url = addReqSeq(url);
        String ret = null;
        try (CloseableHttpClient httpClient = CloseableHttpClientPool.getHttpclient()) {
            final HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-agent", "WEB");
            httpPost.setHeader("x-tymh-client-ip", RemoteIpUtil.getRemoteIp());
            StringEntity entity = new StringEntity(str, contentType);
            httpPost.setEntity(entity);
            final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 600) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, CHARSET) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            ret = httpClient.execute(httpPost, responseHandler);
            return ret;
        } finally {
            logger.info("running time {} ms, post url: {} ,param : {}, return : {}",
                    System.currentTimeMillis() - startTime, url, str, ret);
        }
    }



    /**
     * 发送post请求
     *
     * @param url
     * @param header
     * @param body
     * @return
     */
    public static String doPost(String url, Map<String, String> header, String body) {
        String result = "";
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 设置 url
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // 设置 header
            for (String key : header.keySet()) {
                connection.setRequestProperty(key, header.get(key));
            }
            // 设置请求 body
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //设置连接超时和读取超时时间
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            try {
                out = new PrintWriter(connection.getOutputStream());
                // 保存body
                out.print(body);
                // 发送body
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                // 获取响应body
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
//			return null;
        }
        return result;
    }

    /**
     * PUT请求 适用于自定义提交类型
     *
     * @param url         URL地址
     * @param str         入参的请求参数
     * @param contentType 请求类型
     * @return 接口返回的字符串
     * @throws IOException
     * @throws ParseException
     * @since 1.5
     */
    public static String putBasicAuth(String url, String str, ContentType contentType, String userName, String password) throws Throwable {
        //时钟
        long startTime = System.currentTimeMillis();
        url = addReqSeq(url);
        String ret = null;
        try (CloseableHttpClient httpClient = CloseableHttpClientPool.getHttpclient()) {
            final HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader("User-agent", "WEB");
            httpPut.setHeader("x-tymh-client-ip", RemoteIpUtil.getRemoteIp());
            StringEntity entity = new StringEntity(str, contentType);
            httpPut.setEntity(entity);
            final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, CHARSET) : null;
                    } else {
                        throw new MyException(String.valueOf(status));
                    }
                }
            };
            ret = httpClient.execute(httpPut, responseHandler);
            return ret;
        } finally {
            logger.info("running time {} ms, put url: {} ,param : {}, return : {}",
                    System.currentTimeMillis() - startTime, url, str, ret);
        }
    }


    /**
     * 每个请求后面增加 reqSeq时间戳
     *
     * @param url URL 地址
     * @return 加上时间戳的URL
     */
    private static String addReqSeq(String url) {
        String reqSeq = String.valueOf((new Date()).getTime());
        if (url.indexOf("?") > 0) {
            url = url + "&reqSeq=" + reqSeq;
        } else {
            url = url + "?reqSeq=" + reqSeq;
        }
        return url;
    }

    /**
     * GET 请求 适用于一组参数，参数编码UTF-8<br/>
     * 例如<br/>
     * String ret = get(url,"phonenum","13333333333")
     *
     * @param url        接口地址
     * @param paramName  参数名
     * @param paramValue 参数值
     * @return 接口返回值
     * @throws IOException
     * @throws ParseException
     * @throws URISyntaxException
     * @since 1.1
     */
    public static String get(String url, String paramName, String paramValue) throws IOException, ParseException,
            URISyntaxException {
        URI uri = new URIBuilder(url).setCharset(Charset.forName(CHARSET)).setParameter(paramName, paramValue).build();
        return get(uri.toString());
    }

    /**
     * GET 请求 适用于多组参数，参数编码UTF-8<br/>
     * 例如<br/>
     * List&lt;NameValuePair&gt; list = new ArrayList&lt;&gt;(); <br/>
     * list.add(new BasicNameValuePair("phonenum","13333333333"));<br/>
     * list.add(new BasicNameValuePair("username","test"));<br/>
     * String ret = get(url,list);
     *
     * @param url  接口地址
     * @param nvps 参数集合
     * @return 接口返回值
     * @throws IOException
     * @throws ParseException
     * @throws URISyntaxException
     * @see org.apache.http.message.BasicNameValuePair
     * @see NameValuePair
     * @since 1.1
     */
    public static String get(String url, List<NameValuePair> nvps) throws IOException, ParseException,
            URISyntaxException {
        URI uri = new URIBuilder(url).setCharset(Charset.forName(CHARSET)).setParameters(nvps).build();
        return get(uri.toString());
    }

}
