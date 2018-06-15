package com.panxianhao.talker.adapter;

import android.view.View;

import com.panxianhao.talker.R;
import com.panxianhao.talker.face.Face;
import com.panxianhao.talker.view.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by Louis on 2018/6/12 16:54.
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {
    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
