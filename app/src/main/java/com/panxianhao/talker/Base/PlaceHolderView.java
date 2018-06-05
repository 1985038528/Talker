package com.panxianhao.talker.Base;

import android.support.annotation.StringRes;

public interface PlaceHolderView {

    void triggerEmpty();

    void triggerNetError();

    void triggerError(@StringRes int strRes);

    void triggerLoading();

    void triggerOk();

    void triggerOkOrEmpty(boolean isOk);
}
