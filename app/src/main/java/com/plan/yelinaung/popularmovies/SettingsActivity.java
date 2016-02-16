package com.plan.yelinaung.popularmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener {

  @Override public boolean onPreferenceChange(Preference preference, Object o) {
    if (preference instanceof ListPreference) {
      ListPreference listPreference = (ListPreference) preference;
      int index = listPreference.findIndexOfValue(o.toString());
      if (index >= 0) {
        listPreference.setSummary(listPreference.getEntries()[index]);
      }
    }
    return true;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
    Preference preference = findPreference(getResources().getString(R.string.key_sort_pref));
    preference.setOnPreferenceChangeListener(this);
    onPreferenceChange(preference,
        PreferenceManager.getDefaultSharedPreferences(this).getString(preference.getKey(), ""));
  }
}
