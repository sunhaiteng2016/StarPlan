package com.boniu.starplan.http;


import android.app.Application;


import com.boniu.starplan.BuildConfig;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.oaid.UuidCreator;
import com.boniu.starplan.ui.ApplicationUtils;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.SystemInfoUtils;
import com.google.gson.Gson;

import java.io.File;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.cookie.CookieStore;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl;
import rxhttp.wrapper.ssl.X509TrustManagerImpl;

/**
 * User: ljx
 * Date: 2019-11-26
 * Time: 20:44
 */
public class RxHttpManager {

   /* @Converter(name = "FastJsonConverter")
    public static IConverter fastJsonConverter = FastJsonConverter.create();
    @Converter(name = "XmlConverter")
    public static IConverter xmlConverter = XmlConverter.create();*/


    public static void init(Application context) {
        File file = new File(context.getExternalCacheDir(), "RxHttpCookie");
        X509TrustManager trustAllCert = new X509TrustManagerImpl();
        SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieStore(file))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
//            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//            .addInterceptor(new RedirectInterceptor())
                //.addInterceptor(new TokenInterceptor())
                .build();
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttp.init(client, BuildConfig.DEBUG);

        //设置缓存策略，非必须
        File cacheFile = new File(context.getExternalCacheDir(), "RxHttpCache");
        RxHttpPlugins.setCache(cacheFile, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        RxHttpPlugins.setExcludeCacheKeys("time"); //设置一些key，不参与cacheKey的组拼

        //设置数据解密/解码器，非必须
//        RxHttp.setResultDecoder(s -> s);

        //设置全局的转换器，非必须
//        RxHttp.setConverter(FastJsonConverter.create());

        //设置公共参数，非必须
        RxHttp.setOnParamAssembly(p -> {
            /*根据不同请求添加不同参数，子线程执行，每次发送请求前都会被回调
            如果希望部分请求不回调这里，发请求前调用Param.setAssemblyEnabled(false)即可
             */
            String accountId = ApplicationUtils.newInstance().accountId == null ? SPUtils.getInstance().getString(ComParamContact.Common.TOKEN_KEY) : ApplicationUtils.newInstance().accountId;
            String brand = SystemInfoUtils.getBrandName();
            String channel = SystemInfoUtils.getAppSource(context, "UMENG_CHANNEL");
            String deviceModel = SystemInfoUtils.getProductName();
            String deviceType = "Android";
            String uuid = UuidCreator.getInstance(context).getDeviceId();
            String version = SystemInfoUtils.getAppVersionName(context);
            Map<String, String> map = new HashMap<>();
                map.put("accountId", accountId);
            map.put("brand", brand);
            map.put("channel", channel);
            map.put("deviceModel", deviceModel);
            map.put("deviceType", deviceType);
            map.put("uuid", uuid);
            map.put("version", version);
            String headerStr = new Gson().toJson(map);
            return p.addHeader("session", AESUtil.encrypt(headerStr, AESUtil.KEY)); //添加公共请求头
        });
    }
}
