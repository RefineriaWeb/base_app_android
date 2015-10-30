package app.refineriaweb.com.domain.presenters.demo;

import javax.inject.Inject;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.interactors.demo.GetCachedUserDemoUseCase;
import app.refineriaweb.com.domain.interactors.demo.GetUserDemoUseCase;
import app.refineriaweb.com.domain.presenters.Presenter;
import app.refineriaweb.com.domain.presenters.subscribers.UseCaseSubscriber;
import app.refineriaweb.com.domain.views.demo.GetUserView;

public class UserDemoPresenter extends Presenter<GetUserView> {
    private final GetUserDemoUseCase getUserDemoUseCase;
    private final GetCachedUserDemoUseCase getCachedUserDemoUseCase;

    @Inject public UserDemoPresenter(GetUserDemoUseCase getUserDemoUseCase,
                                     GetCachedUserDemoUseCase getCachedUserDemoUseCase) {
        this.getUserDemoUseCase = getUserDemoUseCase;
        this.getCachedUserDemoUseCase = getCachedUserDemoUseCase;
    }

    @Override public void attachView(GetUserView view) {
        super.attachView(view);
        getCachedUserDemoUseCase.execute(new UseCaseUserSubscriber());
    }

    public void getUserByUserName(String username) {
        getUserDemoUseCase.setUserName(username);
        getUserDemoUseCase.execute(new UseCaseUserSubscriber());
    }

    private class UseCaseUserSubscriber extends UseCaseSubscriber<UserDemo> {
        @Override public void onStart() {
            view.showProgress();
        }

        @Override public void onError(Throwable e) {
            view.hideProgress();
            view.showError(e.getMessage());
        }

        @Override public void onNext(UserDemo user) {
            view.hideProgress();
            view.showUser(user);
        }
    }

    @Override public void destroy() {
        getUserDemoUseCase.unsubscribe();
    }
}
