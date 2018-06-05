package com.panxianhao.talker.presenter.present.follow;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.view.follow.FollowView;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/3 21:16.
 */
public class FollowPresenter extends BasePresenter<FollowView> {
    public FollowPresenter(FollowView view) {
        super(view);
    }

    public void Follow(String userID) {
        mView.onLoading();
        addSubscription(mApiService.userFollow(userID), new Subscriber<RspModel<UserCard>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.onError();
                Application.showToast("关注失败");
            }

            @Override
            public void onNext(RspModel<UserCard> RspModel) {
                if (RspModel.success()) {
                    mView.onSuccess(true);
                } else {
                    mView.onError();
                    Application.showToast("关注失败");
                }
            }
        });
    }

    public void UnFollow(String userID) {
        mView.onLoading();
        addSubscription(mApiService.userUnFollow(userID),new Subscriber<RspModel<UserCard>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.onError();
                Application.showToast("取消关注失败");
            }

            @Override
            public void onNext(RspModel<UserCard> RspModel) {
                if (RspModel.success()) {
                    mView.onSuccess(false);
                } else {
                    mView.onError();
                    Application.showToast("取消关注失败");
                }
            }
        });
    }
}
