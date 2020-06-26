package  com.example.tpfinal.ui.login;

import android.os.Bundle;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;


    LoggedInUserView(String displayName) {

        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
