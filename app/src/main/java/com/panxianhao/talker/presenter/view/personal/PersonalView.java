package com.panxianhao.talker.presenter.view.personal;

import com.panxianhao.talker.data.model.db.User;

/**
 * Created by Louis on 2018/6/5 8:25.
 */
public interface PersonalView {
    void setInfo(User user);
    void setFollowState(boolean isFollow);
}
