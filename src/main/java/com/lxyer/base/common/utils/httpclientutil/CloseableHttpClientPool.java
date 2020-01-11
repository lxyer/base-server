package com.lxyer.base.common.utils.httpclientutil;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 王金龙
 */
public class CloseableHttpClientPool {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CloseableHttpClientPool.class);
    /**
     * 服务端
     */
    private static CloseableHttpClient httpclient;

    /**
     * 总连接数
     */
    private static final int CONNECTION_MAX_TOTAL = 128;

    /**
     * 重试次数
     */
    private static final int RETRY_COUNT = 3;

    /**
     * 生存周期
     */
    private static final int KEEP_TO_ALIVE_TIME = 60;

    /**
     * 从连接池中获取连接的超时时间
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    /**
     * 与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
     */
    private static final int CONNECTION_TIMEOUT = 1000;

    /**
     * socket读数据超时时间：从服务器获取响应数据的超时时间
     */
    private static final int SOCKET_TIMEOUT = 40000;

    static {
        // 构建httpclient请求
        try {
            httpclient = closeableHttpClient();
        } catch (Throwable throwable) {
            logger.error(String.format("CloseableHttpClientPool Init error "), throwable);
        }
    }

    /**
     * 在调用SSL之前需要重写验证方法，取消检测SSL
     * 创建ConnectionManager，添加Connection配置信息
     *
     * @return HttpClient 支持https
     */
    private static CloseableHttpClient closeableHttpClient() throws Throwable {
        // 创建Http请求的相关配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(Boolean.TRUE)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        // CloseableHttpClient 按代码顺序解释
        // 创建连接池
        // 设置重试方案
        // 保持长连接配置，需要在头添加Keep-Alive
        // 指定默认RequestConfig实例，如果未在客户端执行上下文中设置，将用于请求执行
        // 使HTTP客户端的实例主动地使用后台线程从连接池中删除空闲连接。
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager())
                .setRetryHandler(httpRequestRetryHandler())
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                .evictExpiredConnections()
                .evictIdleConnections(5L, TimeUnit.SECONDS)
                .setConnectionManagerShared(true)
                .build();

        return closeableHttpClient;
    }

    /**
     * 构建连接池
     *
     * @return 返回连接池
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext ctx = createIgnoreVerifySSL();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);

        // SSL
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", socketFactory).build();

        // 创建ConnectionManager，添加Connection配置信息
        // 长连接保持60秒
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, null, null, null, KEEP_TO_ALIVE_TIME, TimeUnit.SECONDS);
        // 总连接数
        connectionManager.setMaxTotal(CONNECTION_MAX_TOTAL);
        // 同路由的并发数(每个主机的并发)
        connectionManager.setDefaultMaxPerRoute(CONNECTION_MAX_TOTAL);

        return connectionManager;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 少数固定客户端，长时间极高频次的访问服务器，启用keep-alive非常合适
     * <p>
     * 方法添加{@link CloseableHttpClientPool#closeableHttpClient }在{@link HttpClients#custom()} 下面设置setKeepAliveStrategy
     *
     * @return ConnectionKeepAliveStrategy
     */
    private static ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        // 创建长连接
        ConnectionKeepAliveStrategy kaStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                // 如果没有约定，则默认定义时长为60s
                return 60 * 1000;
            }
        };

        return kaStrategy;
    }

    /**
     * 创建重试
     *
     * @return 重试构造
     */
    public static HttpRequestRetryHandler httpRequestRetryHandler() {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(
                    IOException exception,
                    int executionCount,
                    HttpContext context) {

                if (executionCount >= RETRY_COUNT) {
                    // 超过重试次数
                    return false;
                } else if (exception instanceof ConnectionPoolTimeoutException) {
                    // 从连接池中获取连接的超时
                    return false;
                } else if (exception instanceof ConnectTimeoutException) {
                    // 与服务器连接超时时间三次握手超时
                    return true;
                } else if (exception instanceof SocketTimeoutException) {
                    // 从服务器获取响应数据的超时
                    return false;
                } else if (exception instanceof UnknownHostException) {
                    // 未知的主机名
                    return false;
                } else if (exception instanceof SSLException) {
                    // SSL 握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };

        return myRetryHandler;
    }

    public static CloseableHttpClient getHttpclient() {
        return httpclient;
    }
}
