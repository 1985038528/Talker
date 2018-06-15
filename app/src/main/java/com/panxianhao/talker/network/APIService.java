package com.panxianhao.talker.network;

import com.panxianhao.talker.data.model.api.GroupCreateModel;
import com.panxianhao.talker.data.model.api.RspModel;
import com.panxianhao.talker.data.model.api.account.AccountRspModel;
import com.panxianhao.talker.data.model.api.account.LoginModel;
import com.panxianhao.talker.data.model.api.account.RegisterModel;
import com.panxianhao.talker.data.model.api.message.MsgCreateModel;
import com.panxianhao.talker.data.model.api.user.UserUpdateModel;
import com.panxianhao.talker.data.model.card.GroupCard;
import com.panxianhao.talker.data.model.card.MessageCard;
import com.panxianhao.talker.data.model.card.UserCard;

import java.util.List;

import okhttp3.MultipartBody;
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
    Call<String> uploadPortrait(@Part MultipartBody.Part imgs);

    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch1(@Path("name") String name);

    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind1(@Path("userId") String userId);

    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);

    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    @GET("group/list/{date}")
    Observable<RspModel<List<GroupCard>>> getgroups(@Path(value = "date", encoded = true) String date);

    @GET("group/{groupId}")
    Call<RspModel<GroupCard>> groupFind(@Path("groupId") String groupId);

    // 我的群的成员列表
    @GET("group/{groupId}/member")
    Observable<RspModel<List<UserCard>>> groupMembers(@Path("groupId") String groupId);

}
