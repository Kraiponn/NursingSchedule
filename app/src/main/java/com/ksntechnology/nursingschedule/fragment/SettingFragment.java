package com.ksntechnology.nursingschedule.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksntechnology.nursingschedule.R;

public class SettingFragment extends PreferenceFragment {

    private SharedPreferences mSharedPreff;

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initInstance() {
        addPreferencesFromResource(R.xml.preferences);
        mSharedPreff =
                PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        onPreferenceValueChanged();
        getCurrentPreferences();
    }

    private void getCurrentPreferences() {
        boolean systemMode = mSharedPreff.getBoolean(
                "check_system_mode", false);
        Preference prefSystemMode = findPreference("check_system_mode");
        if (systemMode == true) {
            prefSystemMode.setSummary("Online Mode");
        } else {
            prefSystemMode.setSummary("Offline Mode");
        }

        boolean checkUpdate = mSharedPreff.getBoolean(
                "check_update", false);
        Preference prefUpdate = findPreference("check_update");
        if (checkUpdate == true) {
            prefUpdate.setSummary("Update when has a new update");
        } else {
            prefUpdate.setSummary("Manual update");
        }

        String fontSize = mSharedPreff.getString(
                "font_size", "18");
        Preference prefFontSize = findPreference("font_size");
        prefFontSize.setSummary(fontSize);
    }

    private void onPreferenceValueChanged() {
        final SharedPreferences.Editor editor = mSharedPreff.edit();

        mSharedPreff.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                          String key) {
                        boolean systemMode = sharedPreferences.getBoolean(
                                "check_system_mode", false);
                        Preference prefSystemMode = findPreference("check_system_mode");
                        if (systemMode == true) {
                            prefSystemMode.setSummary("Online Mode");
                            editor.putBoolean("check_system_mode", true);
                            //Log.d("Update", "system mode true");
                        } else {
                            prefSystemMode.setSummary("Offline Mode");
                            editor.putBoolean("check_system_mode", false);
                            //Log.d("Update", "system mode false");
                        }

                        boolean checkUpdate = sharedPreferences.getBoolean(
                                "check_update", false);
                        Preference prefUpdate = findPreference("check_update");
                        if (checkUpdate == true) {
                            prefUpdate.setSummary("Update when has a new update");
                            editor.putBoolean("check_update", true);
                            //Log.d("Update", "Check update true");
                        } else {
                            prefUpdate.setSummary("Manual update");
                            //Log.d("Update", "Check update false");
                        }

                        String fontSize = sharedPreferences.getString(
                                "font_size", "18");
                        Preference prefFontSize = findPreference("font_size");
                        prefFontSize.setSummary(fontSize);
                        editor.putString("font_size", fontSize);
                        //Log.d("Update", "Font size " + fontSize);

                        editor.commit();

                        //getCurrentPreferences();
                    }
                }
        );
    }
}
