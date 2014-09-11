package plataformaparaformal.mumbai;

import plataformaparaformal.mumbai.services.Mumbai;
import plataformaparaformal.mumbai.services.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

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
public class Settings extends PreferenceFragment  {
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static Mumbai mumbai = Mumbai.getInstance();
    private static final User user = User.getInstance();
    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        /**
         * Setting the preference of the sistem and the some informations
         */
        CheckBoxPreference ckeck = (CheckBoxPreference) findPreference("settings_sync");
        ckeck.setChecked(mumbai.config.syncAutomatic);
        if(!mumbai.config.syncAutomatic){
            String lastUpdate = getResources().getString(R.string.settings_syncSummLastUpdate);
            lastUpdate += mumbai.config.lastUpdate.get(Calendar.DAY_OF_MONTH)+"/";
            lastUpdate += mumbai.config.lastUpdate.get(Calendar.MONTH)+"/";
            lastUpdate += mumbai.config.lastUpdate.get(Calendar.YEAR);
            ckeck.setSummary(lastUpdate);
        }
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
        editTextPreference = (EditTextPreference) findPreference("settings_user");
        editTextPreference.setSummary((mumbai.user.getUserAuroraId()==0?"":String.format("%d",mumbai.user.getUserAuroraId())));
        editTextPreference.setEnabled(false);

        if(mumbai.user.isUserLoggedOnSocialNetwork()){
            PreferenceScreen preferenceScreen1 = (PreferenceScreen) findPreference("settings_accountLogged");
            preferenceScreen1.setTitle(R.string.settings_account);
            preferenceScreen1.setSummary(mumbai.user.getUserType().getAccount());
            switch(mumbai.user.getUserType()){
                case account_facebook:
                    preferenceScreen = (PreferenceScreen) findPreference("setting_accountFacebook");
                    preferenceScreen.setSummary(R.string.settings_accountBound);
                    preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            Intent intent = new Intent(getActivity(), AccountFacebook.class);
                            startActivity(intent);
                            return true;
                        }
                    });
                    break;
                case account_google:
                    preferenceScreen = (PreferenceScreen) findPreference("setting_accountGoogle");
                    preferenceScreen.setSummary(R.string.settings_accountBound);
                    break;
                case account_twitter:
                    preferenceScreen = (PreferenceScreen) findPreference("setting_accountTwitter");
                    preferenceScreen.setSummary(R.string.settings_accountBound);
                    break;
                case account_none:
                    break;
            }

        }

        preferenceScreen = (PreferenceScreen) findPreference("setting_accountGoogle");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), AccountGoogle.class);
                startActivity(intent);
                return true;
            }
        });

        preferenceScreen = (PreferenceScreen) findPreference("setting_accountFacebook");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), AccountFacebook.class);
                startActivity(intent);
                return true;
            }
        });

        preferenceScreen = (PreferenceScreen) findPreference("setting_accountTwitter");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), AccountTwitter.class);
                startActivity(intent);
                return true;
            }
        });

        preferenceScreen = (PreferenceScreen) findPreference("settings_helperAboutPlataforma");
        preferenceScreen.onDismiss(null);
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(mumbai.config.urlHomePage));
                    startActivity(i);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(preference.getContext(), "Not open", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        preferenceScreen = (PreferenceScreen) findPreference("settings_helperCenter");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(mumbai.config.urlHelpCenter));
                    startActivity(i);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(preference.getContext(), "Not open", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setView(View view){
        this.view = view;
    }

    @Override
    public void onResume(){
        CheckBoxPreference ckeck = (CheckBoxPreference) findPreference("settings_sync");
        ckeck.setChecked(mumbai.config.syncAutomatic);

        ckeck = (CheckBoxPreference) findPreference("setting_notification");
        ckeck.setChecked(mumbai.config.notificationOnScree);

        ckeck = (CheckBoxPreference) findPreference("settings_imgHighResolution");
        ckeck.setChecked(mumbai.config.seeFullImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mumbai != null){
            if(mumbai.config.saveConfig()){
                mumbai.config.principalToast.setText(R.string.alert_settings_saveSuccess);
            }else{
                mumbai.config.principalToast.setText(R.string.alert_settings_notSaveSucess);
            };
            mumbai.config.principalToast.show();
        }
    }
}
