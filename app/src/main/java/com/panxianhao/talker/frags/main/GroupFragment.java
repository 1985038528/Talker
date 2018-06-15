package com.panxianhao.talker.frags.main;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.MesssageActivity;
import com.panxianhao.talker.adapter.GroupAdapter;
import com.panxianhao.talker.data.model.db.Group;
import com.panxianhao.talker.presenter.present.group.GroupPresenter;
import com.panxianhao.talker.presenter.view.group.GroupView;
import com.panxianhao.talker.view.LoadingLayout;

import java.util.List;

import butterknife.BindView;

public class GroupFragment extends BaseFragement implements GroupView {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private GroupAdapter mAdapter;
    private GroupPresenter presenter;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();

        presenter = new GroupPresenter(this);
        presenter.getGroups(false);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mAdapter = new GroupAdapter();
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.dodgerblue));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getGroups(true);
            }
        });
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MesssageActivity.show(getContext(), ((Group) adapter.getItem(position)));
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public void addData(List<Group> groups, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.setNewData(groups);
            swipeRefresh.setRefreshing(false);
        } else {
            mAdapter.setNewData(groups);
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }
        loadingLayout.showContent();
    }

    @Override
    public void showLoading() {
        loadingLayout.showLoading();
    }

    @Override
    public void showError() {
        loadingLayout.showError();
    }

    @Override
    public void showEmpty() {
        loadingLayout.showEmpty();
    }
}
