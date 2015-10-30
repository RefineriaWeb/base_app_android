package app.refineriaweb.com.domain.presenters.subscribers;

import rx.Subscriber;

public abstract class UseCaseSubscriber<T> extends Subscriber<T> {
    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {}

    @Override public void onNext(T t) {}
}
