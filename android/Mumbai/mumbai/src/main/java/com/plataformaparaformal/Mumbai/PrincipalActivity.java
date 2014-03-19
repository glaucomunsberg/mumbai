package com.plataformaparaformal.Mumbai;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.plataformaparaformal.Mumbai.util.Mumbai;
import com.plataformaparaformal.Mumbai.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PrincipalActivity extends Activity {

    private static final Mumbai mumbai = Mumbai.getInstance();

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        try {
            PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            mumbai.config.versionNum = packInfo.versionCode;
            mumbai.config.versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        GoogleMap map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.fullscreen_map)).getMap();
        LatLng pelotas = new LatLng(-33.867,151.206);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pelotas,13));
        map.addMarker(new MarkerOptions().title("Pelotas").snippet("Princesa do Sul").position(pelotas));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch(item.getItemId()){
            case R.id.action_menu:
                myIntent = new Intent(this, SettingsActivity.class);
                startActivity(myIntent);
                break;
            case R.id.action_create:
                myIntent = new Intent(this, EditParaformalidadeActivity.class);
                startActivity(myIntent);
                break;
            case R.id.action_location:
                myIntent = new Intent(this,SeeParaformalidadeActivity.class);
                startActivity(myIntent);
                break;
            case R.id.action_search:
                myIntent = new Intent(this,SearchActivity.class);
                startActivity(myIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}
