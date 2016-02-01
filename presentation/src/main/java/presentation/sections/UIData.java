package presentation.sections;

import base.app.android.R;
import data.sections.UI;
import presentation.foundation.BaseApp;

public class UIData implements UI {
    private final BaseApp baseApp;

    public UIData(BaseApp baseApp) {
        this.baseApp = baseApp;
    }

    @Override public String genericError() {
        return baseApp.getString(R.string.generic_error);
    }
}
