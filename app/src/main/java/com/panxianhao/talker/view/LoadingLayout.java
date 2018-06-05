package com.panxianhao.talker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.panxianhao.talker.R;

public class LoadingLayout extends FrameLayout {
    private int emptyView, errorView, loadingView;
    public LoadingLayout(Context context) {
        this(context, null);
    }


    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            emptyView = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_layout); //空数据页面
            errorView = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.fail_layout);//网络失败页面
            loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_layout);//加载页面
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(emptyView, this, true);
            inflater.inflate(errorView, this, true);
            inflater.inflate(loadingView, this, true);
            initViews();
        } finally {
            a.recycle();
        }

    }

    //初始化视图
    private void initViews() {
//        nodata_bt = (Button) findViewById(R.id.communal_nodata_btn);
//        communal_failload_layout = (LinearLayout) findViewById(R.id.communal_failload_layout);
//        nodata_text = (TextView) findViewById(R.id.communal_nodata_message_text);
//        circle = (ImageView) findViewById(R.id.communal_failload_tryagain_img);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }
    }
    //显示空数据页面
    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示网络失败页面
    public void showError() {

        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示加载Loading页面
    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    //显示想要加载的内容
    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }
}
