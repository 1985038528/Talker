package com.panxianhao.talker.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.model.db.User;

/**
 * Created by Louis on 2018/6/3 17:39.
 */
public class ContactAdapter extends BaseQuickAdapter<User,BaseViewHolder> {
    public ContactAdapter() {
        super(R.layout.cell_contact_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.txt_name,item.getName());
        helper.setText(R.id.txt_desc, item.getDesc());
        Glide.with(mContext)
                .load(item.getPortrait())
                .placeholder(R.drawable.default_portrait)
                .centerCrop()
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.im_portrait));
        helper.addOnClickListener(R.id.im_portrait);
    }
}
