package app.refineriaweb.com.domain.foundation.lce;

import rx.Subscriber;

public abstract class LcePresenterSubscriber<D, V extends LceView<D>> extends Subscriber<D> {
    private final V lceView;

    public LcePresenterSubscriber(V lceView) {
        this.lceView = lceView;
    }

    @Override public void onStart() {
        lceView.showProgress();
    }

    @Override public void onError(Throwable e) {
        lceView.hideProgress();
        lceView.showError(e.getMessage());
    }

    @Override public void onNext(D data) {
        lceView.hideProgress();
        lceView.showData(data);
    }

    @Override public void onCompleted() {}
}
