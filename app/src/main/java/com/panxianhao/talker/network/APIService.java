package com.panxianhao.talker.network;

import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.api.account.AccountRspModel;
import com.panxianhao.talker.data.model.api.account.LoginModel;
import com.panxianhao.talker.data.model.api.account.RegisterModel;
import com.panxianhao.talker.data.model.api.user.UserUpdateModel;
import com.panxianhao.talker.data.model.card.UserCard;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface APIService {

    @POST("account/register")
    Observable<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    @POST("account/login")
    Observable<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    @POST("account/bind/{pushId}")
    Observable<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    @PUT("user")
    Observable<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    @GET("user/search/{name}")
    Observable<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    @PUT("user/follow/{userId}")
    Observable<RspModel<UserCard>> userFollow(@Path("userId") String userId);

    @PUT("user/unfollow/{userId}")
    Observable<RspModel<Boolean>> userUnFollow(@Path("userId") String userId);

    @GET("user/contact")
    Observable<RspModel<List<UserCard>>> userContacts();

    @GET("user/{userId}")
    Observable<RspModel<UserCard>> userFind(@Path("userId") String userId);

    @Multipart
    @POST("FServlet")
    Call<ResponseBody> uploadPortrait(@Part MultipartBody.Part imgs);
}
