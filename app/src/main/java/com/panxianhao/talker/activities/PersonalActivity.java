package com.panxianhao.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panxianhao.talker.Base.BaseActivity;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.model.db.User;
import com.panxianhao.talker.presenter.present.personal.PersonalPresenter;
import com.panxianhao.talker.presenter.view.personal.PersonalView;
import com.panxianhao.talker.view.PortraitView;

import net.qiujuer.genius.res.Resource;

import butterknife.BindView;

// TODO: 2018/6/5 在个人资料界面关注取消关注后ContactFragement回调 
public class PersonalActivity extends BaseActivity implements PersonalView {
    private static final String BOUND_KEY_ID = "BOUND_KEY_ID";
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.txt_name)
    TextView mName;
    @BindView(R.id.txt_desc)
    TextView mDesc;
    @BindView(R.id.txt_follows)
    TextView mFollows;
    @BindView(R.id.txt_following)
    TextView mFollowing;
    @BindView(R.id.btn_say_hello)
    Button mSayHello;
    private User user;
    private MenuItem mFollowItem;
    private PersonalPresenter presenter;
    private String userID;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    public static void show(Context context, String userId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(BOUND_KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userID = bundle.getString(BOUND_KEY_ID);
        return !TextUtils.isEmpty(userID);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar((Toolbar) findViewById(R.id.toolbar));
        presenter = new PersonalPresenter(this);
        presenter.getInfo(userID);
    }

    @Override
    public void setInfo(User user) {
        this.user = user;
        mName.setText(user.getName());
        mDesc.setText(user.getDesc());
        mFollows.setText(String.format(getString(R.string.label_follows), String.valueOf(user.getFollows())));
        mFollowing.setText(String.format(getString(R.string.label_following), String.valueOf(user.getFollowing())));
        mSayHello.setVisibility(user.isFollow() && !user.getId().equalsIgnoreCase(Account.getUserId())
                ? View.VISIBLE : View.GONE);
        mPortrait.setup(Glide.with(this), user);
        Drawable drawable = user.isFollow() ? getResources()
                .getDrawable(R.drawable.ic_favorite) :
                getResources().getDrawable(R.drawable.ic_favorite_border);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Resource.Color.WHITE);
        mFollowItem.setIcon(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.personal, menu);
        mFollowItem = menu.findItem(R.id.action_follow);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_follow) {
            if (user.isFollow()) {
                presenter.unFollow(userID);
            } else {
                presenter.follow(userID);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFollowState(boolean isFollow) {
        user.setFollow(isFollow);
        Drawable drawable = user.isFollow() ? getResources()
                .getDrawable(R.drawable.ic_favorite) :
                getResources().getDrawable(R.drawable.ic_favorite_border);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Resource.Color.WHITE);
        mFollowItem.setIcon(drawable);
    }

    public void initToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
