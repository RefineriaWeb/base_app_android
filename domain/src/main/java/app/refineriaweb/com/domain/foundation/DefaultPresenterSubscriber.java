package app.refineriaweb.com.domain.foundation;

import rx.Subscriber;

public abstract class DefaultPresenterSubscriber<T> extends Subscriber<T> {
    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {}

    @Override public void onNext(T t) {}
}
