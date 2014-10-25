package plataformaparaformal.mumbai.services;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import plataformaparaformal.mumbai.R;

public class Mumbai {

    private static volatile Mumbai instance = null;

	public static API api;
	public static User user;
	public static Config config;
    public static GoogleMap map;
    public static Budapest budapest;
    private boolean isTheFirstTime;

	private Paraformalidade paraformalidade;

    private Mumbai(){
        api = API.getInstance();
        user = User.getInstance();
        config = Config.getInstance();
        budapest = Budapest.getInstance();
        map = null;
    }



    public static Mumbai getInstance(){
        if(instance == null){
            synchronized (Mumbai.class){
                if(instance == null){
                    instance = new Mumbai();
                }
            }
        }
        return instance;
    }

    public boolean setNewScene(int id, String description, String lat_scene,String lng_scene){
        Double lat = Double.parseDouble(lat_scene);
        Double lng = Double.parseDouble(lng_scene);
        LatLng tmp = new LatLng(lat,lng);
        this.map.addMarker(new MarkerOptions().title(String.valueOf(R.string.scene_paraformalidade)).snippet(description).position(tmp));
        this.map.setMyLocationEnabled(true);
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, 13));
        return true;
    }

}
