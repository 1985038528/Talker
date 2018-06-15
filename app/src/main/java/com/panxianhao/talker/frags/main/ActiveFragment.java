package com.panxianhao.talker.frags.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panxianhao.talker.R;
import com.panxianhao.talker.activities.MesssageActivity;
import com.panxianhao.talker.data.model.db.Session;
import com.panxianhao.talker.face.Face;
import com.panxianhao.talker.frags.message.PresenterFragment;
import com.panxianhao.talker.presenter.present.sesssion.SessionPresenter;
import com.panxianhao.talker.presenter.view.session.SessionContract;
import com.panxianhao.talker.utils.DateTimeUtil;
import com.panxianhao.talker.view.LoadingLayout;
import com.panxianhao.talker.view.PortraitView;
import com.panxianhao.talker.view.recycler.RecyclerAdapter;

import net.qiujuer.genius.ui.Ui;

import butterknife.BindView;

public class ActiveFragment extends PresenterFragment<SessionContract.Presenter>
        implements SessionContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    private RecyclerAdapter<Session> mAdapter;

    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void FirstinitData() {
        super.FirstinitData();
        mPresenter.start();
        showLoading();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Session>() {
            @Override
            protected int getItemViewType(int position, Session session) {
                return R.layout.cell_chat_list;
            }

            @Override
            protected ViewHolder<Session> onCreateViewHolder(View root, int viewType) {
                return new ActiveFragment.ViewHolder(root);
            }
        });

        // 点击事件监听
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Session>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Session session) {
                MesssageActivity.show(getContext(), session);
            }
        });

    }

    @Override
    protected SessionContract.Presenter initPresenter() {
        return new SessionPresenter(this);
    }

    @Override
    public void showError(int str) {
        loadingLayout.showError();
    }

    @Override
    public void showLoading() {
        loadingLayout.showLoading();
    }

    @Override
    public RecyclerAdapter<Session> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mAdapter.getItemCount() > 0) {
            loadingLayout.showContent();
        } else {
            loadingLayout.showEmpty();
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Session> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.txt_content)
        TextView mContent;

        @BindView(R.id.txt_time)
        TextView mTime;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Session session) {
            mPortraitView.setup(Glide.with(ActiveFragment.this), session.getPicture());
            mName.setText(session.getTitle());
            String str = TextUtils.isEmpty(session.getContent()) ? "" : session.getContent();
            Spannable spannable = new SpannableString(str);
            Face.decode(mContent, spannable, (int) Ui.dipToPx(getResources(), 20));
            mContent.setText(spannable);
            mTime.setText(DateTimeUtil.getSampleDate(session.getModifyAt()));
        }
    }
}
