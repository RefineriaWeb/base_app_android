package app.refineriaweb.com.domain.foundation;

public abstract class UseCase <T extends Agent> implements Disposable {
    protected final T agent;

    public UseCase(T agent) {
        this.agent = agent;
    }

    @Override public void dispose() {
        agent.dispose();
    }
}
