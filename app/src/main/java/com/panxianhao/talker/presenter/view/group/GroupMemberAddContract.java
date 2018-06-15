package com.panxianhao.talker.presenter.view.group;

import com.panxianhao.talker.Base.BaseContract;

/**
 * Created by Louis on 2018/6/12 10:45.
 */
public interface GroupMemberAddContract {
    interface Presenter extends BaseContract.Presenter {
        void submit();

        void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected);
    }
    interface View extends BaseContract.RecyclerView<Presenter, GroupCreateContract.ViewModel> {
        void onAddedSucceed();

        String getGroupId();
    }
}
