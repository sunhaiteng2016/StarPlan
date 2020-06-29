package com.boniu.starplan.base;


import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.utils.SPUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.annotation.Parser;

@Parser(name = "Response")
public class ResponseParser<T> extends AbstractParser<T> {

    //注意，以下两个构造方法是必须的
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, mType); //获取泛型类型
        Response<T> data = convert(response, type);
        String t = data.getResult(); //获取data字段
        if (null == t) {
            t = "";
        }
        if (data.getReturnCode() == -400) {
            //清空token
            SPUtils.getInstance().put(ComParamContact.Common.TOKEN_KEY, "");
            SPUtils.getInstance().put(ComParamContact.Login.MOBILE, "");
            ARouter.getInstance().build("/ui/MainActivity").navigation();
        }
        if (data.getReturnCode() != 0 && data.getReturnCode() != -100) {//这里假设code不等于0，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getReturnCode()), data.getErrorMsg(), response);
        }

        return (T) t;
    }
}
