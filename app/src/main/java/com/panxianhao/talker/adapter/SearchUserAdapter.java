package com.panxianhao.talker.adapter;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.panxianhao.talker.Base.Application;
import com.panxianhao.talker.R;
import com.panxianhao.talker.data.Account;
import com.panxianhao.talker.data.model.card.UserCard;
import com.panxianhao.talker.presenter.present.follow.FollowPresenter;
import com.panxianhao.talker.presenter.view.follow.FollowView;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

/**
 * Created by Louis on 2018/6/3 20:32.
 */
//TODO 关注取消关注后在ContactFragement回调
public class SearchUserAdapter extends BaseQuickAdapter<UserCard, SearchUserAdapter.CellViewHolder> {
    public SearchUserAdapter() {
        super(R.layout.cell_search_list);
    }

    @Override
    protected void convert(CellViewHolder helper, UserCard item) {
        helper.setText(R.id.tex_name, item.getName());
        helper.setImageResource(R.id.im_follow, item.isFollow() ? R.drawable.ic_done : R.drawable.ic_add);
        helper.setBackgroundRes(R.id.im_follow, item.isFollow() ? R.color.textSecond : R.color.colorAccent);
        Glide.with(mContext)
                .load(item.getPortrait())
                .placeholder(R.drawable.default_portrait)
                .centerCrop()
                .dontAnimate()
                .into((ImageView) helper.getView(R.id.im_portrait));
        helper.setUserID(item, mData.indexOf(item));
        helper.addOnClickListener(R.id.im_portrait);

    }

    class CellViewHolder extends BaseViewHolder implements FollowView {
        ImageView FollowImage = getView(R.id.im_follow);
        FollowPresenter presenter;
        UserCard userCard;
        int position;

        public CellViewHolder(View view) {
            super(view);
            presenter = new FollowPresenter(this);
            FollowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userCard.getId().equals(Account.getUserId())) {
                        Application.showToast("这是自己不能操作");
                    } else {
                        if (!userCard.isFollow()) {
                            presenter.Follow(userCard.getId());
                        } else {
                            presenter.UnFollow(userCard.getId());
                        }
                    }

                }
            });
        }

        @Override
        public void onSuccess(boolean isFollow) {
            ((LoadingDrawable) FollowImage.getDrawable()).stop();
            if (isFollow) {
                FollowImage.setImageResource(R.drawable.ic_done);
                FollowImage.setBackgroundResource(R.color.textSecond);

            } else {
                FollowImage.setImageResource(R.drawable.ic_add);
                FollowImage.setBackgroundResource(R.color.colorAccent);
            }
            mData.get(position).setFollow(isFollow);
            notifyDataSetChanged();
        }

        @Override
        public void onLoading() {
            LoadingDrawable drawable = new LoadingCircleDrawable((int) Ui.dipToPx(Resources.getSystem(), 22)
                    , (int) Ui.dipToPx(Resources.getSystem(), 30));
            drawable.setBackgroundColor(0);
            drawable.setForegroundColor(new int[]{R.color.white_alpha_208});
            FollowImage.setImageDrawable(drawable);
            drawable.start();
        }

        @Override
        public void onError() {
            if (FollowImage.getDrawable() instanceof LoadingDrawable) {
                LoadingDrawable drawable = (LoadingDrawable) FollowImage.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        public void setUserID(UserCard userCard, int position) {
            this.userCard = userCard;
            this.position = position;
        }
    }
}
