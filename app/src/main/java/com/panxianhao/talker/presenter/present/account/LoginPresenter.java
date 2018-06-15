package com.panxianhao.talker.presenter.present.account;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.api.account.AccountRspModel;
import com.panxianhao.talker.data.model.api.account.LoginModel;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.presenter.view.account.LoginView;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/3 15:05.
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    public void Login(LoginModel loginModel) {
        addSubscription(mApiService.accountLogin(loginModel), new Subscriber<RspModel<AccountRspModel>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Application.showToast("网络错误");
            }

            @Override
            public void onNext(RspModel<AccountRspModel> response) {
                if (response.success()) {
                    AccountRspModel accountRspModel = response.getResult();
                    User user = accountRspModel.getUser();
                    user.save();
                    Account.login(accountRspModel);
                    if (accountRspModel.isBind()) {
                        Account.setBind(true);
                        mView.loginSuccess();
                    } else {
                        bindpush(Account.getPushId());
                    }
                } else {
                    Application.showToast("登录失败");
                }
            }
        });
    }

    public void bindpush(String pushID) {
        addSubscription(mApiService.accountBind(pushID), new Subscriber<RspModel<AccountRspModel>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Application.showToast("网络错误");
            }

            @Override
            public void onNext(RspModel<AccountRspModel> response) {
                if (response.success()) {
                    AccountRspModel accountRspModel = response.getResult();
                    User user = accountRspModel.getUser();
                    user.save();
                    Account.login(accountRspModel);
                    if (accountRspModel.isBind()) {
                        Account.setBind(true);
                        mView.loginSuccess();
                    } else {
                        bindpush(Account.getPushId());
                    }
                } else {
                    Application.showToast("登录失败");
                }
            }
        });
    }

}
