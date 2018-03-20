package com.baozi.moom.net;

/**
 * author：baozi
 * time: 2018/3/20 16:46
 * email：baoziguoguo@foxmail.com
 */
public interface OnEasyHttpRequestListener {
    void onSuccess(int id, String response);
    void onFail(int id, String errorMessage);
}
