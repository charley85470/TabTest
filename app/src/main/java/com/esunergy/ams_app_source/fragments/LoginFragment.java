package com.esunergy.ams_app_source.fragments;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.Response;
import com.esunergy.ams_app_source.models.active.LoginInfo;
import com.esunergy.ams_app_source.utils.DialogUtil;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * 登入頁面
 */
public class LoginFragment extends BaseConnectionFragment implements View.OnTouchListener {

    private String PAGE_TAG = "LoginFragment";

    private Context ctx;
    private View topLayoutView;
    private LoginInfo loginInfo;

    private Button btn_login, btn_cancel;
    private TextInputLayout input_layout_account, input_layout_password;
    private EditText et_account, et_password;

    protected Dialog mCheckVersionDialog;
    private InitFragmentView initFragmentView;

    public LoginFragment() {
        // Required empty public constructor
        initFragmentView = new InitFragmentView(getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_login, container, false);

        btn_login = topLayoutView.findViewById(R.id.btn_login);
        btn_cancel = topLayoutView.findViewById(R.id.btn_cancel);
        input_layout_account = topLayoutView.findViewById(R.id.input_layout_account);
        input_layout_password = topLayoutView.findViewById(R.id.input_layout_password);
        et_account = topLayoutView.findViewById(R.id.et_account);
        et_password = topLayoutView.findViewById(R.id.et_password);

        return topLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginEmpty()) {
                    //checkVersion
//                    checkVersion();

                    showProgressDialog();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("User",et_account.getText().toString());
                    jsonObject.addProperty("Password",et_password.getText().toString());
                    String jsonString = gson.toJson(jsonObject);
                    mConnectionManager.sendPost(ConnectionService.login, jsonString, LoginFragment.this, false);
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewReset();
            }
        });

        mCheckVersionDialog = DialogUtil.createCheckVersionProgressDialog(ctx);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        dismissProgressDialog();
        switch (service) {

            case login:
                Response response = gson.fromJson(result, Response.class);
                LogUtil.LOGI(PAGE_TAG, "LoginInfo = " + response);
                if (response.statusCode == 200) {
                    try {
                        loginInfo = gson.fromJson(response.data.toString(), LoginInfo.class);
//                        loginInfo.user = et_account.getText().toString();
//                        loginInfo.menuMode = MenuFragment.MENU_MODE_STEP1_BeforeDownload;
                        loginInfo.save();

                        LogUtil.LOGI(PAGE_TAG, "LoginInfo = " + loginInfo);
                        Constants.setLogin(loginInfo.user, loginInfo.userNameE, loginInfo.userCompany);
                        initFragmentView.initLoginView();
                        //toMenuFragment();
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    loginViewReset();
                    Toast.makeText(getActivity(), getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //檢查帳號密碼輸入空白，輸入空白則setInputTextLayout Error
    private boolean validateLoginEmpty() {
        int accountErrorRes = 0;
        int passwordErrorRes = 0;

        if (TextUtils.isEmpty(et_account.getText())) {
            accountErrorRes = R.string.hint_account;
        }

        if (TextUtils.isEmpty(et_password.getText())){
            passwordErrorRes = R.string.hint_password;
        }

        setAccountError(accountErrorRes != 0 ? getResources().getString(accountErrorRes) : null);
        setPasswordError(passwordErrorRes != 0 ? getResources().getString(passwordErrorRes) : null);

        return accountErrorRes == 0 && passwordErrorRes == 0;
    }

    //setAccountInputTextLayout Error
    private void setAccountError(String accountErrorString) {
        if (accountErrorString != null) {
            input_layout_account.setError(accountErrorString);
            input_layout_account.setErrorEnabled(true);
        } else {
            input_layout_account.setError("");
            input_layout_account.setErrorEnabled(false);
        }

    }

    //setPasswordInputTextLayout Error
    private void setPasswordError(String passwordErrorString) {
        if (passwordErrorString != null) {
            input_layout_password.setError(passwordErrorString);
            input_layout_password.setErrorEnabled(true);
        } else {
            input_layout_password.setError("");
            input_layout_password.setErrorEnabled(false);
        }
    }

    //EditText內容與TextInputLayout內容重新設定
    private void loginViewReset() {
        setAccountError(null);
        setPasswordError(null);
        et_account.setText("");
        et_password.setText("");
    }
}
