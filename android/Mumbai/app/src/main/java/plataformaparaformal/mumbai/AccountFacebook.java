package plataformaparaformal.mumbai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

import plataformaparaformal.mumbai.services.Mumbai;
import plataformaparaformal.mumbai.util.SocialNetwork;

public class AccountFacebook extends Activity {

    private String TAG = "AccountFacebook";
    private Mumbai mumbai = Mumbai.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_facebook);



        LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
        authButton.setOnErrorListener(new LoginButton.OnErrorListener() {
            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "Error " + error.getMessage());
            }
        });

        authButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        authButton.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    Log.i(TAG,"Access Token"+ session.getAccessToken());
                    Request.newMeRequest(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {

                            if (user != null) {

                                Log.i(TAG, "User NAME " + user.getName());
                                Log.i(TAG, "User ID " + user.getId());
                                Log.i(TAG, "Email " + user.asMap().get("email"));

                                Log.i(TAG, "Genero " + user.asMap().get("gender"));
                                //Log.i(TAG, "aniversario " + user.getBirthday());

                                mumbai.user.setUserInformation(user.getName(),user.getId(),user.getBirthday(), SocialNetwork.account_facebook,user.asMap().get("email").toString(),user.asMap().get("gender").toString());
                                mumbai.api.setPersonBySocialConnection();
                            }
                        }
                    }).executeAsync();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_facebook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
