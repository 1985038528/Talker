package com.panxianhao.talker.presenter.present.group;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.GroupCard;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.data.model.db.Group;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.presenter.view.group.GroupView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Louis on 2018/6/9 9:52.
 */
public class GroupPresenter extends BasePresenter<GroupView> {

    public GroupPresenter(GroupView view) {
        super(view);
    }

    public void getGroups(final boolean isRefresh) {
        addSubscription(mApiService.getgroups(""), new Subscriber<RspModel<List<GroupCard>>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final RspModel<List<GroupCard>> listRspModel) {
                List<Group> groups = new ArrayList<>();
                if (listRspModel.success()) {
                    List<GroupCard> groupCards = listRspModel.getResult();
                    if (groupCards.size() == 0) {
                        mView.showEmpty();
                    } else {
                        for (GroupCard groupCard : groupCards) {
                            groups.add(groupCard.build(null));
                        }
                        mView.addData(groups, isRefresh);
                    }
                } else {
                    Application.showToast("加载失败");
                }
            }
        });
    }
}
