package com.baozi.moom;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

public class MainActivity extends AppCompatActivity {

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

        EasyHttp.post("passport/service.php?c=account").baseUrl("https://dev.wanglibao.com/").upJson("{\"id\":-1009929645,\"method\":\"signin\",\"params\":[{\"username\":\"12345678901\",\"password\":\"111111\"}],\"jsonrpc\":\"2.0\"}")
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Log.d("ApiException" , e.toString());
                    }

                    @Override
                    public void onSuccess(String response) {
                        Log.d("response----------->" , response);
                        cookie();
                    }
                });

    }

    private void cookie(){
        IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("登录中...");
                return dialog;
            }
        };
        EasyHttp.post("passport/service.php?c=account").baseUrl("https://dev.wanglibao.com/").upJson("{\"id\":-1290520513,\"method\":\"profile\",\"params\":[],\"jsonrpc\":\"2.0\"}")
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Log.d("ApiException" , e.toString());
                    }

                    @Override
                    public void onSuccess(String response) {
                        Log.d("response----------->" , response);
                    }
                });
    }

}