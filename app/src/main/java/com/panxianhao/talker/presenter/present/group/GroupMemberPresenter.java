package com.panxianhao.talker.presenter.present.group;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.view.group.GroupMembersContract;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Louis on 2018/6/12 10:12.
 */
public class GroupMemberPresenter extends BasePresenter<GroupMembersContract.View> {

    public GroupMemberPresenter(GroupMembersContract.View view) {
        super(view);
    }

    public void getMembers(String groupId) {
        addSubscription(mApiService.groupMembers(groupId), new Subscriber<RspModel<List<UserCard>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Application.showToast("网络异常");
                    }

                    @Override
                    public void onNext(RspModel<List<UserCard>> listRspModel) {
                        mView.showMembers(listRspModel.getResult());
                    }
                }
        );
    }
}