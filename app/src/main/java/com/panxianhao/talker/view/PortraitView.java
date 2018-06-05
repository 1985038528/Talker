package com.panxianhao.talker.view;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.Author;

import de.hdodenhof.circleimageview.CircleImageView;

public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //传user
    public void setup(RequestManager manager, Author author) {
        if (author == null) {
            return;
        }
        setup(manager, author.getPortrait());
    }

    public void setup(RequestManager manager, String url) {
        setup(manager, R.drawable.default_portrait, url);
    }

    public void setup(RequestManager manager, int resId, String url) {
        if (url == null) {
            url = "";
        }
        manager.load(url)
                .placeholder(resId)
                .centerCrop()
                .dontAnimate()
                .into(this);
    }
}
