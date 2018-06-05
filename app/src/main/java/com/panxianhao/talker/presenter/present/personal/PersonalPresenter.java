package com.panxianhao.talker.presenter.present.personal;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.presenter.view.personal.PersonalView;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/5 8:47.
 */
public class PersonalPresenter extends BasePresenter<PersonalView> {
    public PersonalPresenter(PersonalView view) {
        super(view);
    }

    public void getInfo(String userID) {
        addSubscription(mApiService.userFind(userID), new Subscriber<RspModel<UserCard>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //TODO 这里会抛异常,原因未知
            }

            @Override
            public void onNext(RspModel<UserCard> userCardRspModel) {
                if (userCardRspModel.success()) {
                    User user = userCardRspModel.getResult().build();
                    mView.setInfo(user);
                } else {
                    Application.showToast("拉取用户信息失败");
                }
            }
        });
    }

    public void follow(String userID) {
        addSubscription(mApiService.userFollow(userID), new Subscriber<RspModel<UserCard>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Application.showToast("关注失败");
            }

            @Override
            public void onNext(RspModel<UserCard> RspModel) {
                if (RspModel.success()) {
                    mView.setFollowState(true);
                } else {
                    Application.showToast("关注失败");
                }
            }
        });
    }

    public void unFollow(String userID) {
        addSubscription(mApiService.userUnFollow(userID), new Subscriber<RspModel<UserCard>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Application.showToast("失败");
            }

            @Override
            public void onNext(RspModel<UserCard> RspModel) {
                if (RspModel.success()) {
                    mView.setFollowState(false);
                } else {
                    Application.showToast("取消关注失败");
                }
            }
        });
    }
}
