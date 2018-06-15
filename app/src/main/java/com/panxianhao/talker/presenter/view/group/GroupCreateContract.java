package com.panxianhao.talker.presenter.view.group;

import java.util.List;

/**
 * Created by Louis on 2018/6/8 15:22.
 */
public interface GroupCreateContract {
    interface View {
        void onCreateSucceed();

        void onFollowLoaded(List<ViewModel> models);
    }

    class ViewModel {
        public String Id;
        public String Name;
        public String Portrait;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPortrait() {
            return Portrait;
        }

        public void setPortrait(String portrait) {
            Portrait = portrait;
        }

        public boolean isSelected;
    }
}

