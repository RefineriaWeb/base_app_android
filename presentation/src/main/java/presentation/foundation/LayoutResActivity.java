package presentation.foundation;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the layout resource for every Activity which extends from BaseFragmentActivity
 * @see BaseFragmentActivity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LayoutResActivity {
    @LayoutRes int value();
}
