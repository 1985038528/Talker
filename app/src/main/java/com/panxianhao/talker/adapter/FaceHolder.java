package com.panxianhao.talker.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.panxianhao.talker.R;
import com.panxianhao.talker.face.Face;
import com.panxianhao.talker.view.recycler.RecyclerAdapter;

import butterknife.BindView;

/**
 * Created by Louis on 2018/6/12 16:54.
 */
public class FaceHolder extends RecyclerAdapter.ViewHolder<Face.Bean> {
    @BindView(R.id.im_face)
    ImageView mFace;

    public FaceHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Face.Bean bean) {
        if (bean != null
                && ((bean.preview instanceof Integer)
                || bean.preview instanceof String))
            Glide.with(itemView.getContext())
                    .load(bean.preview)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.default_face)
                    .into(mFace);
    }
}
