package app.refineriaweb.com.domain.sections.user_demo.detail;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.Presenter;
import app.refineriaweb.com.domain.foundation.lce.LcePresenterSubscriber;
import app.refineriaweb.com.domain.sections.Wireframe;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;

public class UserDemoPresenter extends Presenter<UserView> {
    private final GetUserDemoUseCase getUserDemo;
    private final GetSelectedDemoUserListUseCase getSelectedDemoUserListUseCase;

    @Inject public UserDemoPresenter(Wireframe wireframe, GetUserDemoUseCase getUserDemo,
                                     GetSelectedDemoUserListUseCase getSelectedDemoUserListUseCase) {
        super(wireframe);
        this.getUserDemo = getUserDemo;
        this.getSelectedDemoUserListUseCase = getSelectedDemoUserListUseCase;
    }

    @Override public void attachView(UserView view) {
        super.attachView(view);
        getSelectedDemoUserListUseCase.getCachedUser(new LcePresenterSubscriber<UserDemoEntity, UserView>(view) {});
    }

    public void getUserByUserName(String username) {
        getUserDemo.getUser(username, new LcePresenterSubscriber<UserDemoEntity, UserView>(view) {
        });
    }

    @Override public void dispose() {
        getUserDemo.dispose();
        getSelectedDemoUserListUseCase.dispose();
    }
}
