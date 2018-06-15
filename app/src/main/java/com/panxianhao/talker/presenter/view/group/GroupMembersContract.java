package com.panxianhao.talker.presenter.view.group;

import com.panxianhao.talker.data.model.card.UserCard;

import java.util.List;

/**
 * Created by Louis on 2018/6/12 10:06.
 */
public interface GroupMembersContract {
    interface View  {
        void showMembers(List<UserCard> data);
    }
}

