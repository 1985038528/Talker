package com.panxianhao.talker.frags.account;


import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.MainActivity;
import com.panxianhao.talker.data.model.api.account.LoginModel;
import com.panxianhao.talker.presenter.present.account.LoginPresenter;
import com.panxianhao.talker.presenter.view.account.LoginView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragement implements LoginView {
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private AccountTrigger mAccountTrigger;
    LoginPresenter presenter;
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
        presenter = new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick() {
        mAccountTrigger.triggerView();
    }

    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String Phone = mPhone.getText().toString();
        String Password = mPassword.getText().toString();
        if (TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Password)) {
            Application.showToast("账户或密码不能为空");
        } else {
            presenter.Login(new LoginModel(Phone, Password));
        }
    }
}
