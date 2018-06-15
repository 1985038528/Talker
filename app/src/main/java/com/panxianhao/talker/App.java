package com.panxianhao.talker;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.activities.AccountActivity;
import com.panxianhao.talker.data.Factory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setup();
        PushManager.getInstance().initialize(this);
    }

    public static void showAccount(Context context) {
        AccountActivity.show(context);
    }
}
