package com.panxianhao.talker.presenter.view.contact;

import com.panxianhao.talker.data.model.db.User;

import java.util.List;

/**
 * Created by Louis on 2018/6/3 16:42.
 */
public interface ContactView extends BaseView{
    void addData(List<User> users,boolean isRefresh);
}
