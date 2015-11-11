package app.refineriaweb.com.domain.foundation;

import app.refineriaweb.com.domain.foundation.schedulers.ObserveOn;
import app.refineriaweb.com.domain.foundation.schedulers.SubscribeOn;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class Agent<R extends Repository> implements Disposable {
    protected final R repository;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    public Agent(R repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.repository = repository;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public <T> void execute(Observable<T> observable, Subscriber<T> subscriber) {
        subscription.unsubscribe();

        subscription = observable
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(subscriber);
    }

    private void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override public void dispose() {
        unsubscribe();
    }
}
