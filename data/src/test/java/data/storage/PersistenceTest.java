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

package data.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


public class PersistenceTest {
    private Persistence persistenceUT;
    private final static String KEY = "store";
    private final static String VALUE = "dummy";
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before public void setUp() {
        persistenceUT = new Persistence(() -> temporaryFolder.getRoot());
    }

    @Test public void when_A_Valid_Pair_Is_Supplied_Then_Get_True() {
        boolean result = persistenceUT.save(KEY, VALUE);
        assertThat(result, is(true));
    }

    @Test public void when_An_Invalid_Pair_Is_Supplied_Then_Get_False() {
        boolean result = persistenceUT.save(null, null);
        assertThat(result, is(not(true)));
    }

    @Test public void when_A_Valid_Pair_Is_Supplied_And_Retrieve_Then_Get_It() {
        persistenceUT.save(KEY, VALUE);

        String result = persistenceUT.retrieve(KEY, String.class);
        assertThat(result, is(VALUE));
    }

    @Test public void when_A_Valid_Pair_Is_Supplied_And_Delete_Then_Do_Not_Retrieve_It() {
        persistenceUT.save(KEY, VALUE);
        persistenceUT.delete(KEY);

        String result = persistenceUT.retrieve(KEY, String.class);
        assertThat(result, is(nullValue()));
    }
}