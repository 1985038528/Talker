package com.panxianhao.talker.presenter.present.user;

import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.Base.BasePresenter;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.api.user.UserUpdateModel;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.network.APIRetrofit;
import com.panxianhao.talker.presenter.view.user.UpdateInfoView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Louis on 2018/6/3 16:11.
 */
public class UpdateInfoPresenter extends BasePresenter<UpdateInfoView> {

    public UpdateInfoPresenter(UpdateInfoView view) {
        super(view);
    }

    public void updatePortrait(String mPortraitPath) {
        File file = new File(mPortraitPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
        APIRetrofit.getInstance().getApiService().uploadPortrait(part).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Application.showToast("上传头像成功");
                    mView.updatePortriate(response.body());
//                    Application.showToast(response.body());
                } else {
                    Application.showToast("上传头像失败");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void update(String mPortraitPath, String desc, boolean isMan) {
        addSubscription(mApiService.userUpdate(new UserUpdateModel("", mPortraitPath, desc,
                isMan ? User.SEX_MAN : User.SEX_WOMAN)), new Subscriber<RspModel<UserCard>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showError();
                Application.showToast("网络错误");
            }

            @Override
            public void onNext(RspModel<UserCard> response) {
                if (response.success()) {
                    UserCard userCard = response.getResult();
                    User user = userCard.build();
                    user.save();
                    mView.updateSucceed();
                } else {
                    Application.showToast("上传失败");
                }

            }
        });
    }
}
