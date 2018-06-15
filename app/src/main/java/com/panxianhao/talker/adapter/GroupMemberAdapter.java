package com.panxianhao.talker.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.model.card.UserCard;

/**
 * Created by Louis on 2018/6/12 11:26.
 */
public class GroupMemberAdapter extends BaseQuickAdapter<UserCard,BaseViewHolder> {
    public GroupMemberAdapter() {
        super(R.layout.cell_group_create_contact);
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserCard item) {
        helper.getView(R.id.cb_select).setVisibility(View.GONE);
        Glide.with(mContext)
                .load(item.getPortrait())
                .centerCrop()
                .into((ImageView) helper.getView(R.id.im_portrait));
        helper.setText(R.id.txt_name, item.getName());
    }
}
