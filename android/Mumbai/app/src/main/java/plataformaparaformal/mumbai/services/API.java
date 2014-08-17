package plataformaparaformal.mumbai.services;

import android.os.AsyncTask;

import plataformaparaformal.mumbai.R;
import plataformaparaformal.mumbai.util.SocialNetwork;
import plataformaparaformal.mumbai.util.User;

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

    private API(){


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


	public boolean isOnAir() {
        String serverURL = "http://"+config.urlBaseAPI+":"+config.portAPI+"/api/isOnAir";
        new Operator(this,"isOnAir").execute(serverURL);
		return false;
	}

	public boolean sendData() {
		return false;
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
                config.principalToast.setText(R.string.api_is_on_air);
                config.principalToast.show();
            }else{
                config.isOnAir = false;
                config.principalToast.setText(R.string.api_is_no_on_air);
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
            config.principalToast.setText(R.string.api_what);
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