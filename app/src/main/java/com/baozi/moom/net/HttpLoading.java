package com.baozi.moom.net;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author：baozi
 * time: 2018/3/20 18:01
 * email：baoziguoguo@foxmail.com
 */
public class HttpLoading {

    private static volatile HttpLoading httpLoading = null;
    private int default_Id = 520;

    public static HttpLoading getInstance() {
        HttpLoading httpLoadingDefault = httpLoading;
        if (httpLoadingDefault == null) {
            synchronized (HttpLoading.class) {
                httpLoadingDefault = httpLoading;
                if (httpLoadingDefault == null) {
                    httpLoadingDefault = new HttpLoading();
                    httpLoading = httpLoadingDefault;
                }
            }
        }
        return httpLoadingDefault;
    }

    /**
     * 登录
     */
    public void getUserLogin(Context context, int id, String username, String password, String img_code, OnEasyHttpRequestListener onEasyHttpRequestListener) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("password", password);
            if (!TextUtils.isEmpty(img_code)) {
                jsonParams.put("img_code", img_code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EasyHttpManager.getInstance().jsonRequest(context, "passport/service.php?c=account", "signin", id,
                jsonParams, false, true ,true, onEasyHttpRequestListener);
    }

    /**
     * 获取用户基础信息
     */
    public void getUserProfile(Context context, int id, OnEasyHttpRequestListener onEasyHttpRequestListener) {
        EasyHttpManager.getInstance().jsonRequest(context, "passport/service.php?c=account", "profile", id,
                null, false, true ,true, onEasyHttpRequestListener);
    }
}
