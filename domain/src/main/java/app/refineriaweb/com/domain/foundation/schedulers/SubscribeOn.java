package app.refineriaweb.com.domain.foundation.schedulers;

import rx.Scheduler;

public interface SubscribeOn {
    Scheduler getScheduler();
}