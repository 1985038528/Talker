package com.panxianhao.talker.presenter.present.message;

import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.helper.MessageHelper;
import com.panxianhao.talker.data.message.MessageDataSource;
import com.panxianhao.talker.data.model.api.message.MsgCreateModel;
import com.panxianhao.talker.data.model.db.Message;
import com.panxianhao.talker.data.utils.DiffDataCallBack;
import com.panxianhao.talker.presenter.view.message.ChatContract;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Message, Message, MessageDataSource, View>
        implements ChatContract.Presenter {

    protected String mReceiverId;
    protected int mReceiverType;


    public ChatPresenter(MessageDataSource source, View view,
                         String receiverId, int receiverType) {
        super(source, view);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void pushText(String content) {
        // 构建一个新的消息
        MsgCreateModel model = new MsgCreateModel.Builder()
                .receiver(mReceiverId, mReceiverType)
                .content(content, Message.TYPE_STR)
                .build();
        // 进行网络发送
        MessageHelper.push(model);
    }

    @Override
    public void pushAudio(String path,long time) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        MsgCreateModel model = new MsgCreateModel.Builder()
                .receiver(mReceiverId, mReceiverType)
                .content(path, Message.TYPE_AUDIO)
                .attach(String.valueOf(time))
                .build();
        MessageHelper.push(model);
    }

    @Override
    public void pushImages(String[] paths) {
        if (paths == null || paths.length == 0)
            return;
        for (String path : paths) {
            MsgCreateModel model = new MsgCreateModel.Builder()
                    .receiver(mReceiverId, mReceiverType)
                    .content(path, Message.TYPE_PIC)
                    .build();

            MessageHelper.push(model);
        }
    }

    @Override
    public boolean rePush(Message message) {
        if (Account.getUserId().equalsIgnoreCase(message.getSender().getId())
                && message.getStatus() == Message.STATUS_FAILED) {
            message.setStatus(Message.STATUS_CREATED);
            MsgCreateModel model = MsgCreateModel.buildWithMessage(message);
            MessageHelper.push(model);
            return true;
        }

        return false;
    }

    @Override
    public void onDataLoaded(List<Message> messages) {
        ChatContract.View view = getView();
        if (view == null)
            return;
        List<Message> old = view.getRecyclerAdapter().getItems();
        DiffDataCallBack<Message> callback = new DiffDataCallBack<>(old, messages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, messages);
        view.smoothToBotton();
    }
}
