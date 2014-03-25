package com.plataformaparaformal.Mumbai;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.plataformaparaformal.Mumbai.util.Mumbai;
import com.plataformaparaformal.Mumbai.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.*;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PrincipalActivity extends Activity{

    private static final Mumbai mumbai = Mumbai.getInstance();
    private Toast toast;
    private GoogleMap map;
    private LocationClient locationClient;
    private AlertDialog.Builder alertDialog;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);

        toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
        mumbai.config.principalToast = toast;

        alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.create();
        mumbai.config.principalAlertDialog = alertDialog;

        LatLng pelotas = new LatLng(-34.6158527,-54.4332985);
        map = ( (MapFragment) getFragmentManager().findFragmentById(R.id.fullscreen_map)).getMap();
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pelotas,13));
        map.addMarker(new MarkerOptions().title("Pelotas").snippet("Princesa do Sul").position(pelotas));



        mumbai.api.isOnAir();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

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
