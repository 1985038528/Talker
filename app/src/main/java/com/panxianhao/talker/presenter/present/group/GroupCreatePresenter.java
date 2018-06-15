package com.panxianhao.talker.presenter.present.group;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.GroupCreateModel;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.card.GroupCard;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.presenter.view.group.GroupCreateContract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Louis on 2018/6/8 15:36.
 */
public class GroupCreatePresenter extends BasePresenter<GroupCreateContract.View> {
    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    public void setFollow() {
        addSubscription(mApiService.userContacts(), new Subscriber<RspModel<List<UserCard>>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RspModel<List<UserCard>> listRspModel) {
                List<GroupCreateContract.ViewModel> models = new ArrayList<>();
                if (listRspModel.success()) {
                    if (listRspModel.getResult().size() == 0) {
                        Application.showToast("您还没有好友");
                        return;
                    }
                    for (UserCard userCard : listRspModel.getResult()) {
                        GroupCreateContract.ViewModel temp = new GroupCreateContract.ViewModel();
                        temp.setId(userCard.getId());
                        temp.setName(userCard.getName());
                        temp.setPortrait(userCard.getPortrait());
                        models.add(temp);
                    }
                    mView.onFollowLoaded(models);
                } else {
                    Application.showToast("加载失败");
                }
            }
        });
    }

    public void create(final String name, final String desc, String mPortraitPath) {
        File file = new File(mPortraitPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
        APIRetrofit.getInstance().getApiService().uploadPortrait(part).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Application.showToast("上传头像成功");

                } else {
                    Application.showToast("上传头像失败");
                }
                APIRetrofit.getInstance().getApiService().groupCreate(new GroupCreateModel(name, desc, response.body(), users)).enqueue(new Callback<RspModel<GroupCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<GroupCard>> call, Response<RspModel<GroupCard>> response) {
                        if (response.body().success()) {
                           mView.onCreateSucceed();
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<GroupCard>> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected) {
            users.add(model.getId());
        } else {
            users.remove(model.getId());
        }
    }
}
