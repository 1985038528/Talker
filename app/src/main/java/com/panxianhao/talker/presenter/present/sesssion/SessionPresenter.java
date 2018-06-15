package com.panxianhao.talker.presenter.present.sesssion;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.util.DiffUtil;

import com.panxianhao.talker.data.message.SessionDataSource;
import com.panxianhao.talker.data.message.SessionRepository;
import com.panxianhao.talker.data.model.db.Session;
import com.panxianhao.talker.data.utils.DiffDataCallBack;
import com.panxianhao.talker.presenter.present.message.BaseSourcePresenter;
import com.panxianhao.talker.presenter.view.session.SessionContract;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Louis on 2018/6/7 16:30.
 */
public class SessionPresenter extends BaseSourcePresenter<Session, Session,
        SessionDataSource, SessionContract.View> implements SessionContract.Presenter {

    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view == null)
            return;

        // 差异对比
        List<Session> old = view.getRecyclerAdapter().getItems();
        DiffDataCallBack<Session> callback = new DiffDataCallBack<>(old, sessions);
        sessions.sort(new Comparator<Session>() {
            @Override
            public int compare(Session session, Session t1) {
                if (session.getModifyAt().after(t1.getModifyAt())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        // 刷新界面
        refreshData(result, sessions);
    }
}

