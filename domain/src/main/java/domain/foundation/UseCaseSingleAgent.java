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

package domain.foundation;

/**
 * Base class for any UseCase which required only one Agent.
 * The use case asks for defined operations to the Agent, which is responsible for execute them.
 * @param <A> The Agent used for this UseCase.
 */

public abstract class UseCaseSingleAgent<A extends Agent, D> extends UseCase<D> {
    protected final A agent;

    public UseCaseSingleAgent(A agent) {
        this.agent = agent;
    }

    @Override public void dispose() {
        agent.dispose();
    }
}
