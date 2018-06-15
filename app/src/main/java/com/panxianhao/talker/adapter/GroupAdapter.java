package com.panxianhao.talker.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.model.db.Group;

/**
 * Created by Louis on 2018/6/9 10:17.
 */
public class GroupAdapter extends BaseQuickAdapter<Group, BaseViewHolder> {
    public GroupAdapter() {
        super(R.layout.cell_group_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Group item) {
        helper.setText(R.id.txt_name, item.getName());
        helper.setText(R.id.txt_desc, item.getDesc());
        Glide.with(mContext)
                .load(item.getPicture())
                .centerCrop()
                .into((ImageView) helper.getView(R.id.im_portrait));
    }
}
