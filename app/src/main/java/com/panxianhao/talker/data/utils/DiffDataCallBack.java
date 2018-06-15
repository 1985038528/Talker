package com.panxianhao.talker.data.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Louis on 2018/6/2 14:01.
 */
public class DiffDataCallBack<T extends DiffDataCallBack.UiDataDiffer<T>> extends DiffUtil.Callback {
    private List<T> mOldData, mNewData;

    public DiffDataCallBack(List<T> mOldData, List<T> mNewData) {
        this.mOldData = mOldData;
        this.mNewData = mNewData;
    }

    @Override
    public int getOldListSize() {
        return mOldData.size();
    }

    @Override
    public int getNewListSize() {
        return mNewData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T bold = mOldData.get(oldItemPosition);
        T bnew = mNewData.get(newItemPosition);
        return bnew.isSame(bold);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T bold = mOldData.get(oldItemPosition);
        T bnew = mNewData.get(newItemPosition);
        return bnew.isUiContentSame(bold);
    }

    public interface UiDataDiffer<T> {
        boolean isSame(T old);

        boolean isUiContentSame(T old);
    }
}
