package com.panxianhao.talker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.panxianhao.talker.Base.BaseActivity;
import com.panxianhao.talker.R;
import com.panxianhao.talker.adapter.GroupMemberAdapter;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.present.group.GroupMemberPresenter;
import com.panxianhao.talker.presenter.view.group.GroupMembersContract;

import java.util.List;

import butterknife.BindView;

public class GroupMemberActivity extends BaseActivity implements GroupMembersContract.View {
    private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private static final String KEY_GROUP_ADMIN = "KEY_GROUP_ADMIN";


    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private String mGroupId;
    private GroupMemberAdapter mAdapter;

    private GroupMemberPresenter presenter;

    public static void show(Context context, String groupId) {
        show(context, groupId, false);
    }

    public static void showAdmin(Context context, String groupId) {
        show(context, groupId, true);
    }

    private static void show(Context context, String groupId, boolean isAdmin) {
        if (TextUtils.isEmpty(groupId))
            return;
        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_GROUP_ADMIN, isAdmin);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mGroupId = bundle.getString(KEY_GROUP_ID);
        return !TextUtils.isEmpty(mGroupId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.title_member_list);
        initToolbar((Toolbar) findViewById(R.id.toolbar));
        mAdapter = new GroupMemberAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new GroupMemberPresenter(this);
        presenter.getMembers(mGroupId);
    }
    @Override
    public void showMembers(List<UserCard> data) {
        mAdapter.addData(data);
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
        }
    }
}
