package plataformaparaformal.mumbai.util;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import plataformaparaformal.mumbai.R;
import plataformaparaformal.mumbai.services.Mumbai;
import plataformaparaformal.mumbai.util.Paraformalidade;

/**
 * Created by glaucomunsberg on 10/25/14.
 */
public class Scene implements Serializable {


    private String TAG = "SCENE";
    static final Mumbai mumbai = Mumbai.getInstance();
    public int id;
    public String geo_lat;
    public String geo_lng;
    public String description;
    ArrayList<Paraformalidade> paraformalidades;

    public Scene(int id, String description,String geo_lat, String geo_lng){

        paraformalidades    = new ArrayList<Paraformalidade>();
        this.id             = id;
        this.geo_lat        = geo_lat;
        this.geo_lng        = geo_lng;
        this.description    = description;

    }

    public boolean setParaformalidades(int id,String geoLatitude,String geoLongitude,String description,int sceneId,String link,int registeredActivityId, String imageURL,int shiftOccurrenceId,int registeredAmountId,int localizationSpaceId,int numberBodyId,int positionBodyId,int equipmentScaleId, int equipmentMobilityId, String dtRegistration){

        if(this.id !=sceneId ){
            Log.i(TAG, String.valueOf(R.string.paraformalidade_scene_diferent));
            return false;
        }

        for (int i = 0; i < paraformalidades.size(); i++) {
            if(paraformalidades.get(i).id == id ){
                return false;
            }
        }

        paraformalidades.add(new Paraformalidade(id,geoLatitude,geoLongitude,description,link,registeredActivityId,imageURL,shiftOccurrenceId,registeredAmountId,localizationSpaceId,numberBodyId,positionBodyId,equipmentScaleId,equipmentMobilityId,dtRegistration));

        return true;
    }

}
