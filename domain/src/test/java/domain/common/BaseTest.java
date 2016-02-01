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

package domain.common;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import domain.foundation.schedulers.ObserveOn;
import domain.foundation.schedulers.SubscribeOn;
import domain.sections.UI;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public abstract class BaseTest {
    protected final static int WAIT = 50;
    @Mock protected UI UIMock;
    @Mock protected ObserveOn observeOnMock;
    @Mock protected SubscribeOn subscribeOnMock;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(observeOnMock.getScheduler()).thenReturn(Schedulers.newThread());
        when(subscribeOnMock.getScheduler()).thenReturn(Schedulers.newThread());
    }
}