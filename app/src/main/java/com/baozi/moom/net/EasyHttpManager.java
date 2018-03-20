package com.baozi.moom.net;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baozi.moom.util.ToastUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;

public class EasyHttpManager {

    /**
     * 单例
     */
    private static volatile EasyHttpManager mEasyHttpManager;

    /**
     * 获取单例
     *
     * @return EasyHttpManager 的单例对象
     */
    public static EasyHttpManager getInstance() {
        if (mEasyHttpManager == null) {
            synchronized (EasyHttpManager.class) {
                if (mEasyHttpManager == null)
                    mEasyHttpManager = new EasyHttpManager();
            }
        }
        return mEasyHttpManager;
    }

    /**
     * 发起网络请求
     * @param context                    上下文内容
     * @param url                        请求地址
     * @param method                     请求方法
     * @param id                         请求id
     * @param jsonParams                 请求参数
     * @param syncRequest                true表示同步，false异步
     * @param onEasyHttpRequestListener  网络请求监听器
     * @param isShowDialog               是否显示加载框
     * @param isCancellable              返回取消请求
     */
    public void jsonRequest(final Context context, String url, String method, final int id, JSONObject jsonParams, boolean syncRequest,
                            boolean isShowDialog, boolean isCancellable, final OnEasyHttpRequestListener onEasyHttpRequestListener) {

        final IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("疯狂加载中...");
                return dialog;
            }
        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", UUID.randomUUID());
            jsonObject.put("method", method);
            jsonObject.put("params", jsonParams == null ? new JSONArray() : new JSONArray().put(0, jsonParams));
            jsonObject.put("jsonrpc", "2.0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EasyHttp.post(url).baseUrl("https://dev.wanglibao.com/").upJson(jsonObject.toString()).syncRequest(syncRequest)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, isShowDialog, isCancellable) {

                    @Override
                    public void onSuccess(String response) {
                        Log.d("response----------->", response);
                        onEasyHttpRequestListener.onSuccess(id, response);
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        switch (e.getCode()) {
                            case ApiException.ERROR.PARSE_ERROR:
                                ToastUtils.showShortToast("数据异常，请重试");
                                break;
                            case ApiException.ERROR.NETWORD_ERROR:
                                ToastUtils.showShortToast("网络异常，请检查网络后重试");
                                break;
                            case ApiException.ERROR.TIMEOUT_ERROR:
                                ToastUtils.showShortToast("连接超时，请检查网络后重试");
                                break;
                            case ApiException.ERROR.REQUEST_CANCEL:
                                ToastUtils.showShortToast("请求取消，请重试");
                                break;
                            default:
                                if (!TextUtils.isEmpty(e.toString())) {
                                    ToastUtils.showShortToast(e.getMessage());
                                }
                                break;
                        }
                        onEasyHttpRequestListener.onFail(id, e.getMessage());
                    }
                });
    }

}
