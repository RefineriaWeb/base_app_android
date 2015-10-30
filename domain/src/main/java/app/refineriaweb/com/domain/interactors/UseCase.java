package app.refineriaweb.com.domain.interactors;

import app.refineriaweb.com.domain.repositories.Repository;
import app.refineriaweb.com.domain.schedulers.ObserveOn;
import app.refineriaweb.com.domain.schedulers.SubscribeOn;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<Response, Repo extends Repository> {
    protected final Repo repository;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    protected UseCase(Repo repository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.repository = repository;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void execute(Subscriber<Response> subscriber) {
        subscription = buildUseCaseObservable()
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract Observable<Response> buildUseCaseObservable();
}
