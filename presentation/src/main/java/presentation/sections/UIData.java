package presentation.sections;

import base.app.android.R;
import data.sections.UI;
import presentation.foundation.BaseApp;
import rx.Observable;

public class UIData implements UI {
    private final BaseApp baseApp;

    public UIData(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public Observable<String> genericError() {
        return Observable.just(baseApp.getString(R.string.generic_error));
    }
}
