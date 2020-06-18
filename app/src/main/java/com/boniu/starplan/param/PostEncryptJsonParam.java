package com.boniu.starplan.param;

import com.boniu.starplan.utils.AESUtil;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;
import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.param.JsonParam;
import rxhttp.wrapper.param.Method;

@Param(methodName = "postEncryptJson")
public class PostEncryptJsonParam extends JsonParam {
    /**
     * @param url    请求路径
     * @param method Method#POST  Method#PUT  Method#DELETE  Method#PATCH
     */
    public PostEncryptJsonParam(String url, Method method) {
        super(url, method);
    }

    /**
     * Post方法
     *
     * @param url
     */
    public PostEncryptJsonParam(String url) {
        super(url, Method.POST);
    }

    @Override
    public RequestBody getRequestBody() {
        Map<String, Object> params = getParams();
        String paramStr = new Gson().toJson(params);
        String encryptStr = AESUtil.encrypt(paramStr, AESUtil.KEY);  //根据上面拿到的参数，自行实现解密逻辑
        return RequestBody.create(MEDIA_TYPE_JSON, encryptStr);  //发送加密后的字符串
    }

}
