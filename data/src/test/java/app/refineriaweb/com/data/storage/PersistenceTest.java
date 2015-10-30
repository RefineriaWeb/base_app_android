package app.refineriaweb.com.data.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import app.refineriaweb.com.data.BuildConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PersistenceTest {
    private Persistence persistence;
    private final static String KEY = "store";
    private final static String VALUE = "dummy";

    @Before public void setUp() {
        persistence = new Persistence(RuntimeEnvironment.application);
    }

    @Test public void when_A_Valid_Pair_Is_Supplied_Then_Get_True() {
        boolean result = persistence.save(KEY, VALUE);
        assertThat(result, is(true));
    }

    @Test public void when_An_Invalid_Pair_Is_Supplied_Then_Get_False() {
        boolean result = persistence.save(null, null);
        assertThat(result, is(not(true)));
    }

    @Test public void when_A_Valid_Pair_Is_Supplied_And_Retrieve_Then_Get_It() {
        persistence.save(KEY, VALUE);

        String result = persistence.retrieve(KEY, String.class);
        assertThat(result, is(VALUE));
    }
}