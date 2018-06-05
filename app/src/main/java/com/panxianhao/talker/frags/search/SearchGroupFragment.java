package com.panxianhao.talker.frags.search;

import com.panxianhao.talker.Base.BaseFragement;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.view.search.SearchFragementView;

import java.util.List;

public class SearchGroupFragment extends BaseFragement implements SearchFragementView {
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }
}
