package app.refineriaweb.com.domain.sections.user_demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.Disposable;
import app.refineriaweb.com.domain.foundation.Presenter;
import app.refineriaweb.com.domain.foundation.DefaultPresenterSubscriber;

public class UserDemoPresenter extends Presenter<UserView> implements Disposable {
    private final GetUserDemoUseCase getUserDemo;
    private final GetCachedUserDemoUseCase getCachedUserDemoUseCase;

    @Inject public UserDemoPresenter(GetUserDemoUseCase getUserDemo,
                                     GetCachedUserDemoUseCase getCachedUserDemoUseCase) {
        this.getUserDemo = getUserDemo;
        this.getCachedUserDemoUseCase = getCachedUserDemoUseCase;
    }

    @Override public void attachView(UserView view) {
        super.attachView(view);
        getCachedUserDemoUseCase.getCachedUser(new UserDemoSubscriber());
    }

    public void getUserByUserName(String username) {
        getUserDemo.getUser(username, new UserDemoSubscriber());
    }

    private class UserDemoSubscriber extends DefaultPresenterSubscriber<UserDemoEntity> {
        @Override public void onStart() {
            view.showProgress();
        }

        @Override public void onError(Throwable e) {
            view.hideProgress();
            view.showError(e.getMessage());
        }

        @Override public void onNext(UserDemoEntity user) {
            view.hideProgress();
            view.showUser(user);
        }
    }

    @Override public void dispose() {
        getUserDemo.dispose();
        getCachedUserDemoUseCase.dispose();
    }
}
