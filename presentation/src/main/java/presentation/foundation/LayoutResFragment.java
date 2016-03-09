package presentation.foundation;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets the layout resource for every Fragment which extends from BasePresenterFragment
 * @see BasePresenterFragment
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LayoutResFragment {
    @LayoutRes int value();
}
