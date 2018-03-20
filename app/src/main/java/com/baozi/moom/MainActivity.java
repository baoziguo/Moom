package com.baozi.moom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baozi.moom.net.HttpLoading;
import com.baozi.moom.net.OnEasyHttpRequestListener;
import com.zhouyou.http.subsciber.IProgressDialog;

public class MainActivity extends AppCompatActivity implements OnEasyHttpRequestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("登录中...");
                return dialog;
            }
        };

//        //==========接口请求参数
//        JSONObject jsonParams = new JSONObject();
//        try {
//            jsonParams.put("username", "12345678901");
//            jsonParams.put("password", "111111");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //==============底下封装起来
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id", -1009929645);
//            jsonObject.put("method", "signin");
//            jsonObject.put("params", new JSONArray().put(0, jsonParams));
//            jsonObject.put("jsonrpc", "2.0");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //请求json串"{\"id\":-1009929645,\"method\":\"signin\",\"params\":[{\"username\":\"12345678901\",\"password\":\"111111\"}],\"jsonrpc\":\"2.0\"}"
//        EasyHttp.post("passport/service.php?c=account").baseUrl("https://dev.wanglibao.com/").upJson(jsonObject.toString())
//                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
//                    @Override
//                    public void onError(ApiException e) {
//                        super.onError(e);
//                        Log.d("ApiException" , e.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(String response) {
//                        Log.d("response----------->" , response);
//                        cookie();
//                    }
//                });

        HttpLoading.getInstance().getUserLogin(this, 1, "12345678901", "111111", "", this);
    }


    @Override
    public void onSuccess(int id, String response) {
        if (id == 1){//登录结果
            HttpLoading.getInstance().getUserProfile(this, 2, this);
        }else if (id == 2){//用户信息结果

        }
    }

    @Override
    public void onFail(int id, String errorMessage) {
    }
}
