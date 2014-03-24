package com.plataformaparaformal.Mumbai.services;

import android.os.AsyncTask;

import com.plataformaparaformal.Mumbai.util.Config;
import com.plataformaparaformal.Mumbai.util.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class API implements AsyncResponse {

    private static volatile API instance = null;

    private static final Config config = Config.getInstance();
    private static User user;

    public ArrayList<Sense> senses;
    public ArrayList<EnvironmentalConditions> environmentalConditionses;
    public ArrayList<Climate> climates;
    public ArrayList<EquipmentInstalation> equipmentInstalations;
    public ArrayList<EquipmentMobility> equipmentMobilities;
    public ArrayList<EquipmentScale> equipmentScales;
    public ArrayList<NumberBody> numberBodies;
    public ArrayList<PositionBody> positionBodies;
    public ArrayList<LocalizationSpace> localizationSpaces;
    public ArrayList<RegisteredActivity> registeredActivities;
    public ArrayList<Shift> shifts;
    public ArrayList<Scene> scenes;

    private API(){

        senses      = new ArrayList<Sense>();
        shifts      = new ArrayList<Shift>();
        scenes      = new ArrayList<Scene>();
        climates    = new ArrayList<Climate>();
        equipmentScales = new ArrayList<EquipmentScale>();
        numberBodies    = new ArrayList<NumberBody>();
        positionBodies  = new ArrayList<PositionBody>();
        equipmentInstalations   = new ArrayList<EquipmentInstalation>();
        equipmentMobilities     = new ArrayList<EquipmentMobility>();
        localizationSpaces      = new ArrayList<LocalizationSpace>();
        registeredActivities    = new ArrayList<RegisteredActivity>();
        environmentalConditionses   = new ArrayList<EnvironmentalConditions>();

    }

    public static API getInstance(){
        if(instance == null){
            synchronized (Config.class){
                if(instance == null){
                    instance = new API();
                }
            }
        }
        return instance;
    }

	public boolean updateData() {
		return false;
	}

	public Scene getCena(int id) {
		return null;
	}

	public ArrayList getCenas(Arrays registeredActivity, Arrays shift, Arrays registeredAmount, Arrays positionBody, Arrays numberBody, Arrays senses, Arrays equipmentMobility, Arrays equipmenteScale, Arrays equipmenteInstalation) {
		return null;
	}

	public boolean getUser(String id, String name, SocialNetwork typeAccount, String userId, String email) {
		return false;
	}

	public boolean setNewUser(String userId, String email, SocialNetwork typeAccount, String name, String lastName, String dtBorun) {
		return false;
	}

	public boolean isOnAir() {
        String serverURL = "http://"+config.urlBaseAPI+":"+config.portAPI+"/api/isOnAir";
        new Operator(this,"isOnAir").execute(serverURL);
		return false;
	}

	public boolean sendData() {
		return false;
	}

    public ArrayList<EquipmentInstalation> getEquipmentInstalations(int id){
        return new ArrayList<EquipmentInstalation>();
    }

    public ArrayList<Sense> getSenses(int id){
        return new ArrayList<Sense>();
    }

    public ArrayList<EnvironmentalConditions> getEnvironmentalConditionses(int id){
        return new ArrayList<EnvironmentalConditions>();
    }

    public ArrayList<Climate> getClimates(int id){
        return new ArrayList<Climate>();
    }

    public ArrayList<Author> getAuthors(int id){
        return new ArrayList<Author>();
    }

    @Override
    public void processFinish(JSONObject outPut) {
        config.principalToast.setText(outPut.toString());
        config.principalToast.show();
        String method = null;
        int status = 0;
        try{
            status = outPut.getInt("status");
            method = outPut.getString("actionResponse");
        }catch (JSONException e){
            e.printStackTrace();
        }

        if("isOnAir".equals(method)){
            if(status == 200){
                config.isOnAir = true;
                config.principalToast.setText("Está no Ar");
                config.principalToast.show();
            }else{
                config.isOnAir = false;
                config.principalToast.setText("Não está no Ar");
                config.principalToast.show();
            }
        }

    }
}

class Operator extends AsyncTask<String, Void,Void>{

    private final HttpClient client = new DefaultHttpClient();
    private final Config config = Config.getInstance();
    private String AsyncContent;
    private String AsyncError = null;
    private String AsyncMethodType =null;
    public AsyncResponse delegate;

    public Operator(AsyncResponse async,String tipo){
        AsyncError = null;
        AsyncContent = null;
        AsyncMethodType = tipo;
        delegate    = async;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(config.principalToast != null){
            config.principalToast.setText("Por favor aguarde...");
            config.principalToast.show();
        }
    }

    @Override
    protected Void doInBackground(String... urls) {
        HttpGet httpGet = new HttpGet( urls[0]);
        try {
            HttpResponse httpResponse = client.execute(httpGet);
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line="";
            StringBuffer stringBuffer = new StringBuffer();
            while( (line = rd.readLine() ) != null ){
                stringBuffer.append(line);
            }
            AsyncError = null;
            AsyncContent = stringBuffer.toString();
        } catch (IOException e) {
            this.AsyncError = e.getMessage();
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void unused){

        if(AsyncError != null){
            if(config.principalToast != null){
                try{
                    JSONObject error = new JSONObject();
                    error.put("status","500");
                    error.put("actionResponse",AsyncMethodType);
                    delegate.processFinish(error);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }else{
            JSONObject jsonResponse = new JSONObject();
            try{
                jsonResponse = new JSONObject(AsyncContent);
            }catch (JSONException e){
                e.printStackTrace();
            }
            delegate.processFinish(jsonResponse);
        }
    }
}

interface AsyncResponse{
    void processFinish(JSONObject outPut);
}