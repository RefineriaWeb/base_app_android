package app.refineriaweb.com.domain.sections.user_demo.list;

import java.util.List;

import javax.inject.Inject;

import app.refineriaweb.com.domain.foundation.DefaultPresenterSubscriber;
import app.refineriaweb.com.domain.foundation.Presenter;
import app.refineriaweb.com.domain.foundation.lce.LcePresenterSubscriber;
import app.refineriaweb.com.domain.sections.Wireframe;
import app.refineriaweb.com.domain.sections.user_demo.UserDemoEntity;

public class UsersDemoPresenter extends Presenter<UsersView> {
    private final GetUsersDemoUseCase getUsersDemoUseCase;
    private final SaveUserDemoSelectedListUseCase saveUserDemoSelectedListUseCase;

    @Inject public UsersDemoPresenter(Wireframe wireframe, GetUsersDemoUseCase getUsersDemoUseCase, SaveUserDemoSelectedListUseCase saveUserDemoSelectedListUseCase) {
        super(wireframe);
        this.getUsersDemoUseCase = getUsersDemoUseCase;
        this.saveUserDemoSelectedListUseCase = saveUserDemoSelectedListUseCase;
    }

    @Override public void attachView(UsersView view) {
        super.attachView(view);
        getUsersDemoUseCase.getUsers(new LcePresenterSubscriber<List<UserDemoEntity>, UsersView>(view) {});
    }

    public void goToDetail(UserDemoEntity user) {
        saveUserDemoSelectedListUseCase.saveUser(user, new DefaultPresenterSubscriber() {
            @Override public void onCompleted() {
                wireframe.userScreen();
            }
        });
    }

    @Override public void dispose() {
        getUsersDemoUseCase.dispose();
    }
}
