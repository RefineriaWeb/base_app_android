package app.refineriaweb.com.domain.foundation.schedulers;

import rx.Scheduler;

public interface ObserveOn {
    Scheduler getScheduler();
}
