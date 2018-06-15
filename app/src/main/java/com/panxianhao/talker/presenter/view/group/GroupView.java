package com.panxianhao.talker.presenter.view.group;

import com.panxianhao.talker.data.model.db.Group;
import com.panxianhao.talker.presenter.view.contact.BaseView;

import java.util.List;

/**
 * Created by Louis on 2018/6/3 16:42.
 */
public interface GroupView extends BaseView{
    void addData(List<Group> users, boolean isRefresh);
}
