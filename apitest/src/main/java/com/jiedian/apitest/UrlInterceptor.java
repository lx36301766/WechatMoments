//package com.jiedian.apitest;
//
//import com.jm.jiediancabinet.baselib.util.StringUtils;
//
//import java.io.IOException;
//
//import okhttp3.HttpUrl;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * Created by ycx on 18/6/1.
// */
//
//public class UrlInterceptor implements Interceptor {
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        //获取request
//        Request request = chain.request();
//        //从request中获取原有的HttpUrl实例oldHttpUrl
//        final HttpUrl oldHttpUrl = request.url();
//        String urlType = request.header("url_type");
//
//        //获取request的创建者builder
//        Request.Builder builder = request.newBuilder();
//
//        if (!StringUtils.isBlank(urlType)) {
//            HttpUrl newHttpUrl = getNewHttpUrl(UrlManager.getInstance().getHost(urlType), oldHttpUrl);
//            return chain.proceed(builder.url(newHttpUrl).build());
//        } else {
//            return chain.proceed(request);
//        }
//    }
//
//    /**
//     * 获取新的HttpUrl
//     *
//     * @return
//     */
//    private HttpUrl getNewHttpUrl(String newUrl, HttpUrl oldHttpUrl) {
//        HttpUrl newBaseUrl = HttpUrl.parse(newUrl);
//        //重建新的HttpUrl，修改需要修改的url部分
//        return oldHttpUrl
//                .newBuilder()
//                .scheme(newBaseUrl.scheme())//更换网络协议
//                .host(newBaseUrl.host())//更换主机名
//                .port(newBaseUrl.port())//更换端口
//                .build();
//    }
//}
