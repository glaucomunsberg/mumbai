package com.plataformaparaformal.Mumbai;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;


import com.plataformaparaformal.Mumbai.services.SocialNetwork;
import com.plataformaparaformal.Mumbai.util.Mumbai;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

    private static final Mumbai mumbai = Mumbai.getInstance();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.addPreferencesFromResource(R.xml.activity_settings);
        getActionBar().setTitle(R.string.settings_title);

        /**
         * Setting the preference of the sistem and the some informations
         */
        CheckBoxPreference ckeck = (CheckBoxPreference) findPreference("settings_sync");
        ckeck.setChecked(mumbai.config.syncAutomatic);
        ckeck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(o instanceof Boolean){
                    mumbai.config.syncAutomatic = (Boolean) o;
                }
                return true;
            }
        });
        ckeck = (CheckBoxPreference) findPreference("setting_notification");
        ckeck.setChecked(mumbai.config.notificationOnScree);
        ckeck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(o instanceof Boolean){
                    mumbai.config.notificationOnScree = (Boolean) o;
                }
                return true;
            }
        });
        ckeck = (CheckBoxPreference) findPreference("settings_imgHighResolution");
        ckeck.setChecked(mumbai.config.seeFullImage);
        ckeck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(o instanceof Boolean){
                    mumbai.config.seeFullImage = (Boolean) o;
                }
                return true;
            }
        });

        PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("settings_screenVersion");
        preferenceScreen.setSummary(mumbai.config.versionName);
        EditTextPreference editTextPreference = (EditTextPreference) findPreference("settings_version");
        editTextPreference.setSummary(mumbai.config.versionName);
        editTextPreference.setEnabled(false);
        editTextPreference = (EditTextPreference) findPreference("settings_developer");
        editTextPreference.setSummary(mumbai.config.versionDevelopBy);
        editTextPreference.setEnabled(false);
        editTextPreference = (EditTextPreference) findPreference("settings_compilation");
        editTextPreference.setSummary(mumbai.config.versionCompilation);
        editTextPreference.setEnabled(false);

        if(!mumbai.user.logged){
            PreferenceScreen preferenceScreen1 = (PreferenceScreen) findPreference("settings_accountLogged");
            preferenceScreen1.setTitle(R.string.settings_account);
            preferenceScreen1.setSummary(mumbai.user.userType.getAccount());
            switch(mumbai.user.userType){
                case account_facebook:
                    editTextPreference = (EditTextPreference) findPreference("setting_accountFacebook");
                    editTextPreference.setSummary(R.string.settings_accountBound);
                    break;
                case account_google:
                    editTextPreference = (EditTextPreference) findPreference("setting_accountGoogle");
                    editTextPreference.setSummary(R.string.settings_accountBound);
                    break;
                case account_twitter:
                    editTextPreference = (EditTextPreference) findPreference("settings_accountTwitter");
                    editTextPreference.setSummary(R.string.settings_accountBound);
                    break;
                case account_none:
                    break;
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
