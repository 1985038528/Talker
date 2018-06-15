package com.panxianhao.talker.presenter.view.user;

/**
 * Created by Louis on 2018/6/3 16:09.
 */
public interface UpdateInfoView {
    void showLoading();

    void showError();

    void updateSucceed();

    void updatePortriate(String portriate);
}
