package com.panxianhao.talker.frags.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.PersonalActivity;
import com.panxianhao.talker.adapter.ContactAdapter;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.presenter.present.contact.ContactPresenter;
import com.panxianhao.talker.presenter.view.contact.BaseView;
import com.panxianhao.talker.presenter.view.contact.ContactView;
import com.panxianhao.talker.view.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactFragment extends BaseFragement implements ContactView {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    private ContactPresenter presenter;
    private ContactAdapter contactAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new ContactPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.dodgerblue));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getContacts(true);
            }
        });
        contactAdapter = new ContactAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(contactAdapter);
        contactAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                MessageActivity.show(getContext(), adapter.getItem(position));
            }
        });
        contactAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalActivity.show(getActivity(), ((User) adapter.getItem(position)).getId());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoading();
        presenter.getContacts(false);
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
        swipeRefresh.setRefreshing(false);
        loadingLayout.showEmpty();
    }

    @Override
    public void addData(List<User> users, boolean isRefresh) {
        if (isRefresh) {
            contactAdapter.setNewData(users);
            swipeRefresh.setRefreshing(false);
        } else {
            contactAdapter.setNewData(users);
        }
        loadingLayout.showContent();
    }
}
