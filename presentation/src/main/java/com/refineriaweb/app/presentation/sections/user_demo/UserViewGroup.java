package com.refineriaweb.app.presentation.sections.user_demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.refineriaweb.app.R;
import com.refineriaweb.app.presentation.utilities.recyclerview_adapter.ViewWrapper;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;

@EViewGroup(R.layout.user_view_group) public class UserViewGroup extends FrameLayout implements ViewWrapper.Binder<UserDemoEntity>{
    @ViewById protected ImageView iv_avatar;
    @ViewById protected TextView tv_name;

    public UserViewGroup(Context context) {
        super(context);
    }

    public UserViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public void bind(UserDemoEntity user) {
        Glide.with(getContext()).load(user.getAvatarUrl()).into(iv_avatar);
        tv_name.setText(user.getId() + ":" + user.getLogin());
    }
}
