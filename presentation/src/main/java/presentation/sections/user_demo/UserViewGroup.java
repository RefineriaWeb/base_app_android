/*
 * Copyright 2015 RefineriaWeb
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package presentation.sections.user_demo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import base.app.android.R;
import domain.sections.user_demo.UserDemoEntity;
import presentation.utilities.recyclerview_adapter.ViewWrapper;

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
