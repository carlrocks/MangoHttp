package com.carlrocks.http.okhttp;

import com.carlrocks.http.okhttp.callback.FileCallBack;
import com.carlrocks.http.okhttp.https.HttpsUtils;
import com.carlrocks.http.okhttp.json.RequestCallBack;
import com.carlrocks.http.okhttp.utils.MangoLog;

import okhttp3.*;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import java.io.InputStream;


/**
 * Created by mangohttp on 2018/5/4.
 * })
 */

public abstract class MangoHttp {

    /**
     * post request
     *
     * @param url
     * @param parameters
     * @param callBack
     * @param <T>
     */
    public static <T> void post(String url, final RequestHeaderParameters headers, final NetRequestParameters parameters, final RequestCallBack<T> callBack) {
        MangoHttpUtils.post().url(url).tag(url).addRequestHeaders(headers).addRequestParams(parameters.getParams()).build().execute(callBack);
    }

    /**
     * get request
     *
     * @param url
     * @param parameters
     * @param callBack
     * @param <T>
     */
    public static <T> void get(String url, final RequestHeaderParameters headers, final NetRequestParameters parameters, final RequestCallBack<T> callBack) {
        MangoHttpUtils.get().url(url).tag(url).addRequestHeaders(headers).addRequestParams(parameters.getParams()).build().execute(callBack);
    }

    /**
     * json格式请求
     * @param url
     * @param mediaType
     * @param headers
     * @param content
     * @param callBack
     * @param <T>
     */
    public static <T> void postContent(String url, MediaType mediaType, final RequestHeaderParameters headers, String content, final RequestCallBack<T> callBack) {
        MangoHttpUtils.postString().url(url).tag(url).addRequestHeaders(headers).mediaType(mediaType).content(content).build().execute(callBack);
    }

    public static void addInterceptor(Interceptor encryptDecryptInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(encryptDecryptInterceptor).build();
        MangoHttpUtils.initClient(client);
    }

    /**
     * Donwload File
     *
     * @param url
     * @param callBack
     */
    public static void donwload(String url, final FileCallBack callBack) {
        MangoHttpUtils.get().url(url).build().execute(callBack);
    }

    public static void initSslSocktFactory(InputStream[] certificates, InputStream bksFile, String password) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        MangoHttpUtils.initClient(okHttpClient);
    }

    public static void initSslSocktFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                //其他配置
                .build();
        MangoHttpUtils.initClient(okHttpClient);
    }

    public static void openLog(boolean open) {
        MangoLog.DEBUG = open;
    }
}
