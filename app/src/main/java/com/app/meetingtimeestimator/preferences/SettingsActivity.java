package com.app.meetingtimeestimator.preferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.app.meetingtimeestimator.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
            bindPreferenceSummaryToValue(findPreference("default_daily_hours"));
            bindPreferenceSummaryToValue(findPreference("max_x_axis_events"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return super.onOptionsItemSelected(item);
        }

        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                if (preference instanceof EditTextPreference) {
                    if (preference.getKey().equals("default_daily_hours")) {
                        ((EditTextPreference) preference).setText(stringValue);
                        preference.setSummary(stringValue);
                    }
                    if (preference.getKey().equals("max_x_axis_events")) {
                        ((EditTextPreference) preference).setText(stringValue);
                        preference.setSummary(stringValue);
                    }
                } else if (preference instanceof CheckBoxPreference) {
                    if (preference.getKey().equals("default_acceptance_criteria")) {
                        preference.setDefaultValue(false);
                    }
                }
                return true;
            }
        };
    }

}
