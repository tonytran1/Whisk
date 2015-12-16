package com.example.team3.whisk;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**     File name: UserSettingActivity.java
 *
 *      Helper class for timer to save user settings for the timer activity.
 *
 *      @author Team 3
 *      @version 1.00
 */

public class UserSettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
