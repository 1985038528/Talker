package com.panxianhao.talker.frags.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.MesssageActivity;
import com.panxianhao.talker.activities.PersonalActivity;
import com.panxianhao.talker.adapter.SearchUserAdapter;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.present.search.SearchPresenter;
import com.panxianhao.talker.presenter.view.search.SearchUserView;
import com.panxianhao.talker.view.LoadingLayout;

import java.util.List;

import butterknife.BindView;

//Todo 实现关注和取消关注失败的重试
public class SearchUserFragment extends BaseFragement implements SearchUserView {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    private SearchUserAdapter searchUserAdapter;
    private SearchPresenter presenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new SearchPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        searchUserAdapter = new SearchUserAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(searchUserAdapter);
        searchUserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.im_portrait) {
                    PersonalActivity.show(getContext(), ((UserCard) adapter.getItem(position)).getId());
                }
            }
        });
        searchUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (((UserCard) adapter.getItem(position)).isFollow()) {
                    MesssageActivity.show(getContext(), ((UserCard) adapter.getItem(position)).build());
                } else {
                    Application.showToast("请先添加为好友");
                }

            }
        });
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

    @Override
    public void OnSuccess(List<UserCard> userCards) {
        loadingLayout.showContent();
        searchUserAdapter.setNewData(userCards);
    }

    @Override
    public void search(String content) {
        presenter.searchUser(content);
    }
}
