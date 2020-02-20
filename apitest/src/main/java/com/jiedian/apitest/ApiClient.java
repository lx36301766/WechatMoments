package com.jiedian.apitest;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by ycx on 2017/10/27.
 */

public class ApiClient {

    private static final String TAG = "ApiClient";

    public static final int CONNECT_TIME_OUT = 20 * 1000;//连接超时时间
    public static final int READ_TIME_OUT = 20 * 1000;
    public static final int WRITE_TIME_OUT = 20 * 1000;

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private String baseUrl;

    private static class RetrofitHolder {
        static ApiClient instance = new ApiClient(false);
    }

    public static ApiClient getInstance() {
        return RetrofitHolder.instance;
    }

    private static class RetrofitNationalHolder {
        static ApiClient instance = new ApiClient(true);
    }

    public static ApiClient getNationalInstance() {
        return RetrofitNationalHolder.instance;
    }

    private ApiClient(boolean national) {
        baseUrl = "https://sharechargapi.jmstatic.com/";
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new UrlInterceptor())
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(buildSslSocketFactory())
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .build();

        }
        return okHttpClient;
    }

    public SSLSocketFactory buildSslSocketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        SSLSocketFactory sslSocketFactory = null;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception var5) {
        }
        return sslSocketFactory;
    }

    public class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}
