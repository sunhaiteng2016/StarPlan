package com.boniu.starplan.param;

import com.boniu.starplan.utils.AESUtil;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.RequestBody;
import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.entity.KeyValuePair;
import rxhttp.wrapper.param.FormParam;
import rxhttp.wrapper.param.Method;

//表单提交
@Param(methodName = "postEncryptForm")
public class PostEncryptFormParam extends FormParam {

    /**
     * @param url    请求路径
     * @param method Method#POST  Method#PUT  Method#DELETE  Method#PATCH
     */
    public PostEncryptFormParam(String url, Method method) {
        super(url, method);
    }

    /**
     * Post方法
     *
     * @param url
     */
    public PostEncryptFormParam(String url) {
        super(url, Method.POST);
    }

    @Override
    public RequestBody getRequestBody() {
        List<KeyValuePair> keyValuePairs = getKeyValuePairs();
        String paramStr = new Gson().toJson(keyValuePairs);
        /*String encryptStr = AESUtil.encrypt(paramStr, "123456");
        addHeader("encryptStr", encryptStr);*/
        return super.getRequestBody();
    }
}
