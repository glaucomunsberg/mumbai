package com.plataformaparaformal.Mumbai.util;

import android.app.AlertDialog;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Config {

    private static volatile Config instance = null;
    public static Calendar lastUpdate = Calendar.getInstance();
    private static File file;

    public final int versionNum = 3;
    private final String LOG_TAG = "Config.java";
	public final String portAPI = "3000";
	public final String hash = "";
    public final String versionName = "0.3";
	public final String versionCompilation  = "02/02/02 a90eb18Fdd0";
	public final String versionDevelopBy = "Developeria";
	public final String urlHelpCenter = "http://www.plataformaparaformal.com.br/Ajuda";
	public final String urlHomePage = "http://www.plataformaparaformal.com.br";
	public final String passwordAPI = "";
	public final String urlBaseAPI = "192.168.1.104";
    public final String dirFiles = "mumbai/";
    private final String configFile = "mumbai.conf";
    public Toast principalToast;
    public AlertDialog.Builder principalAlertDialog;
	public boolean syncAutomatic;
	public boolean notificationOnScree;
	public boolean seeFullImage;
    public boolean isOnAir;

    private Config(){

        Log.e(LOG_TAG, "Carregando configuracoes");
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file=new File(Environment.getExternalStorageDirectory(),dirFiles);
            file.mkdirs();
            file = new File(Environment.getExternalStorageDirectory(),dirFiles + configFile);
            if(file.isFile()){
                loadConfgOnDevaice();
            }else{
                try {
                    file = new File(Environment.getExternalStorageDirectory(), dirFiles+configFile);
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG,e.getLocalizedMessage());
                }
                try {
                    FileWriter config = new FileWriter("/sdcard/"+dirFiles+configFile);
                    config.append("Sync=true\nNotificationOnScreen=true\nSeeFullImage=false\nLastUpdate=2014/03/30\n");
                    config.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG,e.getLocalizedMessage());
                }
            }
        }
        else{
            loadConfigDefault();
        }

    }

    public static Config getInstance(){
        if(instance == null){
            synchronized (Config.class){
                if(instance == null){
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    private void loadConfigDefault(){
        syncAutomatic = true;
        notificationOnScree = true;
        seeFullImage = false;
        lastUpdate = Calendar.getInstance();
        lastUpdate.set(2014, 03, 26);
    }

    private void loadConfgOnDevaice(){
        BufferedReader fileConfig;
        try {
            fileConfig = new BufferedReader(new FileReader("/sdcard/"+dirFiles+configFile));
            String line;
            String lineBoolean;
            int lineOfFile=0;
            while((line = fileConfig.readLine()) != null){

                lineBoolean = line.substring(line.indexOf('=')+1);

                switch(lineOfFile){
                    case 0:
                        if(lineBoolean.equals("true")){
                            syncAutomatic = true;
                        }
                        break;
                    case 1:
                        if(lineBoolean.equals("true")){
                            notificationOnScree = true;
                        }
                        break;
                    case 2:
                        if(lineBoolean.equals("true")){
                            seeFullImage = true;
                        }
                        break;
                    case 3:
                        String date[] = lineBoolean.split("/");
                        Log.e(LOG_TAG,date[0]);
                        Log.e(LOG_TAG,date[1]);
                        Log.e(LOG_TAG, date[2]);
                        lastUpdate.set(2014, 03, 26);
                }
                lineOfFile++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Não achou o arquivo");
            loadConfigDefault();
        } catch (IOException e){
            e.printStackTrace();
            Log.e(LOG_TAG,"Config Devaice: Não achou arquivo");
            loadConfigDefault();
        }
    }

    public boolean saveConfig(){
        try {
            FileWriter config = new FileWriter("/sdcard/"+dirFiles+configFile);
            if(syncAutomatic){
                config.append("Sync=true\n");
            }else{
                config.append("Sync=false\n");
            }
            config.flush();
            if(notificationOnScree){
                config.append("NotificationOnScreen=true\n");
            }else{
                config.append("NotificationOnScreen=false\n");
            }
            if(seeFullImage){
                config.append("SeeFullImage=true\n");
            }else{
                config.append("SeeFullImage=false\n");
            }
            config.flush();
            config.append("LastUpdate="+Integer.toString(lastUpdate.get(Calendar.YEAR))+"/"+Integer.toString(lastUpdate.get(Calendar.MONTH))+"/"+Integer.toString(lastUpdate.get(Calendar.DAY_OF_MONTH)));
            config.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Erro ao salvar config");
            loadConfigDefault();
            return false;
        }
        return true;
    }
}
