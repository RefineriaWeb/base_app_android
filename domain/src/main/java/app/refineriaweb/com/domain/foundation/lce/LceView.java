package app.refineriaweb.com.domain.foundation.lce;

import app.refineriaweb.com.domain.foundation.BaseView;

public interface LceView<D> extends BaseView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void showData(D data);
}
