package com.example.team3.whisk;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by myatminmaung on 11/17/15.
 */
public class UserSettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
