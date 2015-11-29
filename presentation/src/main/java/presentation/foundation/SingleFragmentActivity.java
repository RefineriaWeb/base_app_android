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

package presentation.foundation;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import java.io.Serializable;

import base.app.android.R;

/**
 * Created by victor on 23/11/15.
 */
@EActivity(R.layout.host_single_fragment_activity)
public class SingleFragmentActivity extends BaseToolbarActivity {

    @Override protected void initViews() {
        super.initViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            throw new IllegalStateException("When using HostCommonActivity you need to supply " +
                    "a valid type BaseFragment class by extra argument in the intent" +
                    "with key " + Behaviour.FRAGMENT_CLASS_KEY);
        }

        Serializable serializable = bundle.getSerializable(Behaviour.FRAGMENT_CLASS_KEY);

        try {
            Class<BaseFragment> clazz = (Class<BaseFragment>) serializable;
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, clazz.newInstance()).commit();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public interface Behaviour {
        String FRAGMENT_CLASS_KEY = "fragment_class_key";
    }
}
