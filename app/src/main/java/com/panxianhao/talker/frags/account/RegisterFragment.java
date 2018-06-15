package com.panxianhao.talker.frags.account;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.MainActivity;
import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.model.api.account.RegisterModel;
import com.panxianhao.talker.presenter.present.account.RegisterPresenter;
import com.panxianhao.talker.presenter.view.account.RegisterView;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragement implements RegisterView {
    private AccountTrigger mAccountTrigger;
    private RegisterPresenter presenter;
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;
    @BindView(R.id.loading)
    Loading mLoading;
    @BindView(R.id.btn_submit)
    Button mSubmit;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
        presenter = new RegisterPresenter(this);
    }

    @OnClick({R.id.txt_go_login, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_go_login:
                mAccountTrigger.triggerView();
                break;
            case R.id.btn_submit:
                String Name = mName.getText().toString();
                String Phone = mPhone.getText().toString();
                String Password = mPassword.getText().toString();
                if (TextUtils.isEmpty(Name)) {
                    Application.showToast("姓名为空，请输入姓名");
                } else if (TextUtils.isEmpty(Phone)) {
                    Application.showToast("手机号为空");
                } else if (TextUtils.isEmpty(Password)) {
                    Application.showToast("密码为空");
                } else {
                    presenter.Register(new RegisterModel(Phone, Password, Name, Account.getPushId()));
                }
                break;
        }
    }

    @Override
    public void RegisterSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        mLoading.start();
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void showError() {
        mLoading.stop();
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);
        mSubmit.setEnabled(true);
    }
}
