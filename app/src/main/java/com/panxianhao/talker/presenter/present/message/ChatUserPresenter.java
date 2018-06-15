package com.panxianhao.talker.presenter.present.message;

import com.panxianhao.talker.data.message.MessageRepository;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.data.model.db.Message;
import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.network.APIService;
import com.panxianhao.talker.presenter.view.message.ChatContract;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {

    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);
    }

    @Override
    public void start() {
        super.start();
        APIService service = APIRetrofit.getInstance().getApiService();
        service.userFind(mReceiverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RspModel<UserCard>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(RspModel<UserCard> RspModel) {
                                   UserCard card = RspModel.getResult();
                                   getView().onInit(card.build());
                               }
                           }
                );

    }
}
