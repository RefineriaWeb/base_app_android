package domain.sections.user_demo.common;

import domain.foundation.BaseView;
import domain.sections.user_demo.entities.UserDemoEntity;
import rx.Observable;
import rx.Subscription;

/**
 * Created by victor on 01/02/16.
 */
public interface UserView extends BaseView {
    Subscription showUser(Observable<UserDemoEntity> user);
}
