package com.panxianhao.talker.presenter.view.message;

import com.panxianhao.talker.Base.BaseContract;
import com.panxianhao.talker.data.model.db.Group;
import com.panxianhao.talker.data.model.db.Message;
import com.panxianhao.talker.data.model.db.User;

public interface ChatContract {
    interface Presenter extends BaseContract.Presenter {
        void pushText(String content);

        void pushAudio(String path,long time);

        void pushImages(String[] paths);

        boolean rePush(Message message);
    }

    // 界面的基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        // 初始化的Model
        void onInit(InitModel model);

        void smoothToBotton();
    }

    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {
        void showAdminOption(boolean isAdmin);
    }
}
