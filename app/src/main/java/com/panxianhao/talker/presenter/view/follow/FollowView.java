package com.panxianhao.talker.presenter.view.follow;

/**
 * Created by Louis on 2018/6/3 21:14.
 */
public interface FollowView {
    void onSuccess(boolean isFollow);

    void onLoading();

    void onError();
}
