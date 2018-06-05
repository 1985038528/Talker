package com.panxianhao.talker.presenter.present.contact;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.presenter.view.contact.BaseView;
import com.panxianhao.talker.presenter.view.contact.ContactView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/3 16:45.
 */
public class ContactPresenter extends BasePresenter<ContactView> {
    public ContactPresenter(ContactView view) {
        super(view);
    }

    public void getContacts(final boolean isRefresh) {
        addSubscription(mApiService.userContacts(), new Subscriber<RspModel<List<UserCard>>>() {

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
                    final List<User> users = new ArrayList<>();
                    for (UserCard userCard : listRspModel.getResult()) {
                        users.add(userCard.build());
                    }
                    mView.addData(users,isRefresh);
                } else {
                    Application.showToast("加载失败");
                }
            }
        });
    }
}
