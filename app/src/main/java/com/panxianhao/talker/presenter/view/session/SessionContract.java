package com.panxianhao.talker.presenter.view.session;

import com.panxianhao.talker.Base.BaseContract;
import com.panxianhao.talker.data.model.db.Session;

/**
 * Created by Louis on 2018/6/7 16:17.
 */
public interface SessionContract {
    interface Presenter extends BaseContract.Presenter {

    }
    interface View extends BaseContract.RecyclerView<Presenter, Session> {
        void showLoading();
    }
}
