package presentation.sections;

import base.app.android.R;
import data.sections.Locale;
import presentation.foundation.BaseApp;

public class LocaleData implements Locale {
    private final BaseApp baseApp;

    public LocaleData(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public String genericError() {
        return baseApp.getString(R.string.generic_error);
    }
}
