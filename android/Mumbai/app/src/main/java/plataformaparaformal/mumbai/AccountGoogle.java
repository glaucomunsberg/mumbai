package plataformaparaformal.mumbai;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnAccessRevokedListener;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AccountGoogle extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener, OnClickListener,PlusClient.OnPeopleLoadedListener {

    private static final String TAG = "SignInTestActivity";

    // A magic number we will use to know that our sign-in error
    // resolution activity has completed.
    private static final int OUR_REQUEST_CODE = 49404;

    // The core Google+ client.
    private PlusClient mPlusClient;

    // A flag to stop multiple dialogues appearing for the user.
    private boolean mResolveOnFail;

    // We can store the connection result from a failed connect()
    // attempt in order to make the application feel a bit more
    // responsive for the user.
    private ConnectionResult mConnectionResult;

    // A progress dialog to display when the user is connecting in
    // case there is a delay in any of the dialogs being ready.
    private ProgressDialog mConnectionProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_google);
        // We pass through this for all three arguments, specifying the:
        // 1. Context
        // 2. Object to call onConnected and onDisconnected on
        // 3. Object to call onConnectionFailed on

        mPlusClient =
                new PlusClient.Builder(this, this, this).setActions(
                        "http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
                        .setScopes(Scopes.PLUS_LOGIN) // Space separated list of scopes
                        .build();

        // We use mResolveOnFail as a flag to say whether we should trigger
        // the resolution of a connectionFailed ConnectionResult.
        mResolveOnFail = false;

        // Connect our sign in, sign out and disconnect buttons.
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure the ProgressDialog that will be shown if there is a
        // delay in presenting the user with the next sign in step.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "Start");
        // Every time we start we want to try to connect. If it
        // succeeds we'll get an onConnected() callback. If it
        // fails we'll get onConnectionFailed(), with a result!
        mPlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "Stop");
        // It can be a little costly to keep the connection open
        // to Google Play Services, so each time our activity is
        // stopped we should disconnect.
        mPlusClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.v(TAG, "ConnectionFailed");
        // Most of the time, the connection will fail with a
        // user resolvable result. We can store that in our
        // mConnectionResult property ready for to be used
        // when the user clicks the sign-in button.
        if (result.hasResolution()) {
            mConnectionResult = result;
            if (mResolveOnFail) {
                // This is a local helper function that starts
                // the resolution of the problem, which may be
                // showing the user an account chooser or similar.
                startResolution();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Yay! We can get the oAuth 2.0 access token we are using.
        Log.v(TAG, "Connected. Yay!");

        // Turn off the flag, so if the user signs out they'll have to
        // tap to sign in again.
        mResolveOnFail = false;

        // Hide the progress dialog if its showing.
        mConnectionProgressDialog.dismiss();

        // Retrieve the oAuth 2.0 access token.
        final Context context = this.getApplicationContext();
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                String scope = "oauth2:" + Scopes.PLUS_LOGIN;
                try {
                    // We can retrieve the token to check via
                    // tokeninfo or to pass to a service-side
                    // application.
                    String token = GoogleAuthUtil.getToken(context,
                            mPlusClient.getAccountName(), scope);
                } catch (UserRecoverableAuthException e) {
                    // This error is recoverable, so we could fix this
                    // by displaying the intent to the user.
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GoogleAuthException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute((Void) null);

        mPlusClient.loadPeople(this, "me");
        String accountName = mPlusClient.getAccountName();
        Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnected() {
        // Bye!
        Log.v(TAG, "Disconnected. Bye!");
    }

    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        Log.v(TAG, "ActivityResult: " + requestCode);
        if (requestCode == OUR_REQUEST_CODE && responseCode == RESULT_OK) {
            // If we have a successful result, we will want to be able to
            // resolve any further errors, so turn on resolution with our
            // flag.
            mResolveOnFail = true;
            // If we have a successful result, lets call connect() again. If
            // there are any more errors to resolve we'll get our
            // onConnectionFailed, but if not, we'll get onConnected.
            mPlusClient.connect();
        } else if (requestCode == OUR_REQUEST_CODE && responseCode != RESULT_OK) {
            // If we've got an error we can't resolve, we're no
            // longer in the midst of signing in, so we can stop
            // the progress spinner.
            mConnectionProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
            if (mConnectionResult == null) {
                mConnectionProgressDialog.show();
            } else {
                try {
                    mConnectionResult.startResolutionForResult(this, OUR_REQUEST_CODE);
                } catch (SendIntentException e) {
                    // Tente se conectar novamente.
                    mConnectionResult = null;
                    mPlusClient.connect();
                }
            }
        }
    }

    /**
     * A helper method to flip the mResolveOnFail flag and start the resolution
     * of the ConnenctionResult from the failed connect() call.
     */
    private void startResolution() {
        try {
            // Don't start another resolution now until we have a
            // result from the activity we're about to start.
            mResolveOnFail = false;
            // If we can resolve the error, then call start resolution
            // and pass it an integer tag we can use to track. This means
            // that when we get the onActivityResult callback we'll know
            // its from being started here.
            mConnectionResult.startResolutionForResult(this, OUR_REQUEST_CODE);
        } catch (SendIntentException e) {
            // Any problems, just try to connect() again so we get a new
            // ConnectionResult.
            Log.v(TAG, e.getLocalizedMessage());
            mPlusClient.connect();
        }
    }

    @Override
    public void onPeopleLoaded(ConnectionResult connectionResult, PersonBuffer persons, String s) {
        //if (status.getErrorCode() == ConnectionResult.SUCCESS) {
         //   Log.d(TAG, "Display Name: " + person.getDisplayName());
        //}
    }
}