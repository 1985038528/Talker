package com.panxianhao.talker.Base;

import android.support.annotation.StringRes;

import com.panxianhao.talker.view.recycler.RecyclerAdapter;

/**
 * MVP模式中公共的基本契约
 */
public interface BaseContract {
    interface View<T extends Presenter> {
        void showError(@StringRes int str);

        void showLoading();

        void setPresenter(T presenter);
    }

    interface Presenter {
        void start();

        void destroy();
    }

    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        void onAdapterDataChanged();
    }
}
