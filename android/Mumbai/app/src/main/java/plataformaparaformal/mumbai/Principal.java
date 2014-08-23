package plataformaparaformal.mumbai;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import plataformaparaformal.mumbai.services.Mumbai;

public class Principal extends Fragment {

    private static final Mumbai mumbai = Mumbai.getInstance();
    private Toast toast;
    private GoogleMap map;
    private AlertDialog.Builder alertDialog;
    private static View view;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState)
    {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_principal, container, false);
        } catch (InflateException e) {

        }

        if(mumbai.config.getIsTheFirstTime()){
            mumbai.config.setIsTheFirstTime(false);

            toast = Toast.makeText(this.getActivity(),"",Toast.LENGTH_SHORT);

            /**
             * Starting the menu_mumbai system
             */


            mumbai.config.principalToast = toast;
            alertDialog = new AlertDialog.Builder(this.getActivity());
            alertDialog.create();
            mumbai.config.principalAlertDialog = alertDialog;


            /**
             * Starting the plataforma system
             */
            mumbai.api.isOnAir();

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            mumbai.map = map;
            LatLng pelotas = new LatLng(-31.7267873,-52.3346216);
            mumbai.map.addMarker(new MarkerOptions().title("Pelotas").snippet("Princesa do Sul").position(pelotas));
            mumbai.map.setMyLocationEnabled(true);
            mumbai.map.moveCamera(CameraUpdateFactory.newLatLngZoom(pelotas,13));

            /**
             * Set location as the atual
             */
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mumbai.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));
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

        return view;

    }


}