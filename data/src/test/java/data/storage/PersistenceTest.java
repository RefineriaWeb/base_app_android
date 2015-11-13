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
    private Persistence persistence;
    private final static String KEY = "store";
    private final static String VALUE = "dummy";
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before public void setUp() {
        persistence = new Persistence(() -> temporaryFolder.getRoot());
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

    @Test public void when_A_Valid_Pair_Is_Supplied_And_Delete_Then_Do_Not_Retrieve_It() {
        persistence.save(KEY, VALUE);
        persistence.delete(KEY);

        String result = persistence.retrieve(KEY, String.class);
        assertThat(result, is(nullValue()));
    }
}