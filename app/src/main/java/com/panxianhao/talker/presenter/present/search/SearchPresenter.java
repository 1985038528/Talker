package com.panxianhao.talker.presenter.present.search;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.view.search.SearchUserView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/3 20:04.
 */
public class SearchPresenter extends BasePresenter<SearchUserView> {

    public SearchPresenter(SearchUserView view) {
        super(view);
    }

    public void searchUser(String name) {
        mView.showLoading();
        addSubscription(mApiService.userSearch(name), new Subscriber<RspModel<List<UserCard>>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError();
            }

            @Override
            public void onNext(RspModel<List<UserCard>> listRspModel) {
                if (listRspModel.success()) {
                    if (listRspModel.getResult().size() == 0) {
                        mView.showEmpty();
                        return;
                    }
                    mView.OnSuccess(listRspModel.getResult());
                } else {
                    Application.showToast("加载失败");
                }
            }
        });
    }

    public void searchGroup() {

    }
}
