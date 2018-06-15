package com.panxianhao.talker.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.igexin.sdk.PushConsts;
import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.Factory;

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;

        Bundle bundle = intent.getExtras();

        // 判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID: {
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                onClientInit(bundle.getString("clientid"));
                break;
            }
            case PushConsts.GET_MSG_DATA: {
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:" + message);
                    onMessageArrived(message);
                }
                break;
            }
            default:
                Log.i(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }

    private void onClientInit(String cid) {
        Log.d("MessageReceiver", "" + cid);
        Account.setPushId(cid);
        if (Account.isLogin()) {
//            AccountHelper.bindPush(null);
        }
    }

    private void onMessageArrived(String message) {
        Factory.dispatchPush(message);
    }
}
