package app.refineriaweb.com.domain.sections.user_demo;

import app.refineriaweb.com.domain.foundation.View;

public interface UserView extends View {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void showUser(UserDemoEntity userDemoEntity);
}
