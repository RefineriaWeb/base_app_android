package app.refineriaweb.com.domain.views.demo;

import app.refineriaweb.com.domain.entities.UserDemo;
import app.refineriaweb.com.domain.views.View;

public interface GetUserView extends View {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void showUser(UserDemo userDemo);
}
