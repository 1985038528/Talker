package com.panxianhao.talker.presenter.present.message;

import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.message.MessageGroupRepository;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.GroupCard;
import com.panxianhao.talker.data.model.db.Message;
import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.network.APIService;
import com.panxianhao.talker.presenter.view.message.ChatContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Louis on 2018/6/9 17:41.
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();
        getGroup(mReceiverId);
    }

    public void getGroup(String groupid) {
        final APIService service = APIRetrofit.getInstance().getApiService();
        service.groupFind(groupid).enqueue(new Callback<RspModel<GroupCard>>() {
            @Override
            public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                GroupCard card = response.body().getResult();
                getView().showAdminOption(Account.getUserId().equalsIgnoreCase(card.getOwnerId()));
                getView().onInit(card.build(null));
            }

            @Override
            public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {

            }
        });
    }
}