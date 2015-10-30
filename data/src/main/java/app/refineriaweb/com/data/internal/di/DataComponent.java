package app.refineriaweb.com.data.internal.di;

import app.refineriaweb.com.data.net.Endpoints;
import dagger.Component;

@Component(modules = DataModule.class)
public interface DataComponent {
    Endpoints endpoints();
}