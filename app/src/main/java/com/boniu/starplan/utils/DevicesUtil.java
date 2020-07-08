package com.boniu.starplan.utils;

import com.boniu.starplan.ui.ApplicationUtils;

/**
 * @author: hgb
 * @createTime: 2019/9/27
 * @description:
 * @changed by:
 */
public class DevicesUtil {


    /**
     * 获取唯一标识idfa
     *
     * @return
     */
    /**
     * 获取Oaid
     *
     * @return oaid或错误码
     */
    public static String getOaid() {
        String idfa;
        if (ApplicationUtils.isSupportOaid()) {
            idfa = ApplicationUtils.getOaid();
        } else {
            idfa = "";
        }
        return idfa;
    }


}
