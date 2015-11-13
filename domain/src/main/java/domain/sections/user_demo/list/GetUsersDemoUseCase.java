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

package domain.sections.user_demo.list;

import java.util.List;

import javax.inject.Inject;

import domain.foundation.UseCase;
import rx.Subscriber;

public class GetUsersDemoUseCase extends UseCase<domain.sections.user_demo.UserDemoAgent> {

    @Inject public GetUsersDemoUseCase(domain.sections.user_demo.UserDemoAgent agent) {
        super(agent);
    }

    public void getUsers(Subscriber<List<domain.sections.user_demo.UserDemoEntity>> subscriber) {
        agent.getUsers(subscriber);
    }

}
