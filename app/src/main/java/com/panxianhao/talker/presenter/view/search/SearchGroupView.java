package com.panxianhao.talker.presenter.view.search;

import com.panxianhao.talker.data.model.card.UserCard;

import java.util.List;

/**
 * Created by Louis on 2018/6/3 21:07.
 */
public interface SearchGroupView extends SearchFragementView {
    void OnSuccess(List<UserCard> userCards);
}
