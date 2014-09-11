package plataformaparaformal.mumbai;

import plataformaparaformal.mumbai.services.Mumbai;
import plataformaparaformal.mumbai.util.AlertDialogManager;
import plataformaparaformal.mumbai.util.ConnectionDetector;
import plataformaparaformal.mumbai.util.SocialNetwork;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountTwitter extends Activity {

    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     * */
    static String TWITTER_CONSUMER_KEY = "gist";
    static String TWITTER_CONSUMER_SECRET = "gist";

    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "gist";
    static final String PREF_KEY_OAUTH_SECRET = "gist";
    static final String PREF_KEY_TWITTER_LOGIN = "gist";

    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    // Login button
    private Button btnLoginTwitter;
    private Button btnSaveUser;
    private Button btnLogoutTwitter;

    private TextView lblUserName;
    private TextView lblUserDescription;
    private TextView lblBorn;
    private TextView lblEmail;
    private TextView lblGender;

    private EditText txtUserEmail;
    private DatePicker txtUserDataBorn;
    private Spinner txtUserGender;

    private ActionBar actionBar;

    // Twitter
    private static Twitter twitter;
    long userID;
    private static RequestToken requestToken;

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

    // Internet Connection detector
    private ConnectionDetector cd;
    private AlertDialogManager alert = new AlertDialogManager();

    private Mumbai mumbai = Mumbai.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_twitter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        actionBar = this.getActionBar();
        actionBar.setTitle(R.string.settings_accountTwitter);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(AccountTwitter.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Check if twitter keys are set
        if (TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0) {
            // Internet Connection is not present
            alert.showAlertDialog(AccountTwitter.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
            // stop executing code by return
            return;
        }

        // All UI elements
        btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
        btnSaveUser = (Button) findViewById(R.id.btnSaveUser);
        btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);

        txtUserDataBorn = (DatePicker) findViewById(R.id.txtUserBorn);
        txtUserDataBorn.setCalendarViewShown(false);
        txtUserGender = (Spinner) findViewById(R.id.txtUserGender);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.gender, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtUserGender.setAdapter(adapter);
        lblUserName = (TextView) findViewById(R.id.lblUserName);
        lblBorn = (TextView) findViewById(R.id.lblBorn);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblGender = (TextView) findViewById(R.id.lblGender);
        lblUserDescription = (TextView) findViewById(R.id.lblDescription);
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
        /**
          * Twitter login button click event will call loginToTwitter() function
          * */
        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                public void onClick(View arg0) {
                        // Call login twitter function
                        loginToTwitter();
                    }
            });
        /** This if conditions is tested once is
          * redirected from twitter page. Parse the uri to get oAuth
          * Verifier
          * */

        /**
         * Button click event for logout from twitter
         * */
        btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Call logout twitter function
                logoutFromTwitter();
            }
        });

        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAccountTwitterOnAurora();
            }
        });

        if (!isTwitterLoggedInAlready()) {
                Uri uri = getIntent().getData();
                if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                        // oAuth verifier
                        String verifier = uri
                                .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
                        try {
                                // Get the access token
                                AccessToken accessToken = twitter.getOAuthAccessToken(
                                                requestToken, verifier);
                                // Shared Preferences
                                Editor e = mSharedPreferences.edit();
                                // After getting access token, access token secret
                                // store them in application preferences
                                e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                                e.putString(PREF_KEY_OAUTH_SECRET,
                                                accessToken.getTokenSecret());
                                // Store login status - true
                                e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                                e.commit(); // save changes
                                Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
                                // Hide login button
                                btnLoginTwitter.setVisibility(View.GONE);
                                // Show Update Twitter
                                txtUserDataBorn.setVisibility(View.VISIBLE);
                                txtUserGender.setVisibility(View.VISIBLE);
                                txtUserEmail.setVisibility(View.VISIBLE);
                                lblGender.setVisibility(View.VISIBLE);
                                lblEmail.setVisibility(View.VISIBLE);
                                lblBorn.setVisibility(View.VISIBLE);
                                lblUserDescription.setVisibility(View.VISIBLE);
                                btnSaveUser.setVisibility(View.VISIBLE);
                                btnLogoutTwitter.setVisibility(View.VISIBLE);

                                // Getting user details from twitter
                                // For now i am getting his name only
                                userID = accessToken.getUserId();
                                User user = twitter.showUser(userID);
                                String username = user.getName();
                                // Displaying in xml ui
                                lblUserName.setText(username);
                                lblUserDescription.setText(R.string.account_complete_twitter);
                            } catch (Exception e) {
                                // Check log for login errors
                                Log.e("Twitter Login Error", "> " + e.getMessage());
                            }
                    }
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_twitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_send) {
            setAccountTwitterOnAurora();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    R.string.account_logged_twitter, Toast.LENGTH_LONG).show();
            btnLogoutTwitter.setVisibility(View.VISIBLE);
            btnLoginTwitter.setVisibility(View.GONE);
        }
    }

    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    /**
     * Function to logout from twitter
     * It will just clear the application shared preferences
     * */
    private void logoutFromTwitter() {
        // Clear the shared preferences
        Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.commit();

        // After this take the appropriate action
        // I am showing the hiding/showing buttons again
        // You might not needed this code
        btnLogoutTwitter.setVisibility(View.GONE);
        btnSaveUser.setVisibility(View.GONE);
        txtUserEmail.setVisibility(View.GONE);
        txtUserDataBorn.setVisibility(View.GONE);
        txtUserGender.setVisibility(View.GONE);
        lblUserName.setText("");
        lblGender.setVisibility(View.GONE);
        lblEmail.setVisibility(View.GONE);
        lblBorn.setVisibility(View.GONE);
        lblUserName.setVisibility(View.GONE);
        lblUserDescription.setVisibility(View.GONE);


        btnLoginTwitter.setVisibility(View.VISIBLE);
    }

    private void setAccountTwitterOnAurora(){
        boolean checked = false;
        if(!isTwitterLoggedInAlready()){
            mumbai.config.principalToast.setText(getResources().getString(R.string.account_not_logged_twitter));
            mumbai.config.principalToast.show();
            checked = false;
        }else{
            checked = true;
        }

        if(txtUserEmail.getText().toString().matches("")){
            mumbai.config.principalToast.setText(getResources().getString(R.string.account_without_email_twitter));
            mumbai.config.principalToast.show();
            checked = false;
        }else{
            checked = true;
        }

        if(checked){
            Log.i("TWITTER", "User NAME " + lblUserName.getText());
            Log.i("TWITTER", "User EMAIL " + txtUserEmail.getText());
            String burn = txtUserDataBorn.getDayOfMonth()+"/"+txtUserDataBorn.getDayOfMonth()+"/"+txtUserDataBorn.getYear();
            Log.i("TWITTER", "User BORN " + burn);
            String gender = null;
            switch((int)txtUserGender.getSelectedItemId()){
                case 0:
                    gender = "M";
                    break;
                case 1:
                    gender = "F";
                    break;
                case 2:
                    gender = "N";
                    break;
            }
            Log.i("TWITTER", "User GENDER " + gender);
            mumbai.user.setUserInformation(lblUserName.getText().toString(),String.format("%d",userID),burn, SocialNetwork.account_twitter,txtUserEmail.getText().toString(),gender);
            mumbai.api.setPersonBySocialConnection();
            mumbai.user.setNetworkUsed(SocialNetwork.account_twitter);
        }

    }
}
