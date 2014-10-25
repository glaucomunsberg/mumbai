package plataformaparaformal.mumbai.services;

import android.app.AlertDialog;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import plataformaparaformal.mumbai.util.SocialNetwork;

public class Config {

    private static volatile Config instance = null;
    public static Calendar lastUpdate = Calendar.getInstance();
    private static File file;
    ObjectOutputStream toSave;
    ObjectInputStream saved;

    public final int versionNum = 3;
    private final String LOG_TAG = "Config.java";
	public final String portAPI = "3000";
	public final String hash = "";
    public final String versionName = "0.3";
	public final String versionCompilation  = "02/02/02 a90eb18Fdd0";
	public final String versionDevelopBy = "Glauco Munsberg";
	public final String urlHelpCenter = "http://www.plataformaparaformal.com.br/Ajuda";
	public final String urlHomePage = "http://www.plataformaparaformal.com.br";
	public final String passwordAPI = "";
	public final String urlBaseAPI = "192.168.1.110";
    public final String dirFiles = "mumbai/";
    private final String configFile = "mumbai.conf";
    private final String budapestFile = "budapest.bin";
    public Toast principalToast;
    public AlertDialog.Builder principalAlertDialog;
	public boolean syncAutomatic;
	public boolean notificationOnScree;
	public boolean seeFullImage;
    public boolean isOnAir;
    public boolean isTheFirstTime;
    public boolean isTheCameraPositionFixed;
    public boolean SyncOnServerSometime;
    public boolean DataToSend;

    private Config(){

        Log.e(LOG_TAG, "Carregando configuracoes");
        isTheCameraPositionFixed = false;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file=new File(Environment.getExternalStorageDirectory(),dirFiles);
            file.mkdirs();
            file = new File(Environment.getExternalStorageDirectory(),dirFiles + configFile);
            if(file.isFile()){
                loadConfigOnDevice();
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
                    config.append("Sync=true\nNotificationOnScreen=true\nSeeFullImage=false\nSyncOnServerSometime=false\nDataToSend=false\nLastUpdate=2014/03/30\n");
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
        isTheFirstTime = true;
        File arqBudapest = new File("/sdcard/"+dirFiles+budapestFile);
        if( arqBudapest.isFile() && arqBudapest != null )
        {
            Budapest.getInstance().loadDataDefault();
        }
        else
        {
            try {
                saved = new ObjectInputStream( new FileInputStream( "/sdcard/"+dirFiles+budapestFile));
            } catch (IOException e) {
                Budapest.getInstance().loadDataDefault();

            }
            Budapest budapestSaved = null;
            if(saved != null){
                try {
                    budapestSaved = (Budapest) saved.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Budapest.getInstance().loadDataDefault();
                } catch (IOException e) {
                    e.printStackTrace();
                    Budapest.getInstance().loadDataDefault();
                }
                if(budapestSaved != null)
                    Budapest.getInstance().loadDataFromFile(budapestSaved);
            }
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
        lastUpdate.set(2014, lastUpdate.MAY, 26);
    }

    private void loadConfigOnDevice(){
        BufferedReader fileConfig;
        try {
            fileConfig = new BufferedReader(new FileReader("/sdcard/"+dirFiles+configFile));
            String line;
            String lineBoolean;
            int infoAuroraId;
            String infoName=null,infoEmail=null,infoGener =null,infoSocialConnected = null,infoSocialConnectedId = "";
            boolean isInfoAboutLogged=false;
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
                        if(lineBoolean.equals("true")){
                            SyncOnServerSometime = true;
                        }
                        break;
                    case 4:
                        if(lineBoolean.equals("true")){
                            DataToSend = true;
                        }
                        break;
                    case 5:
                        String date[] = lineBoolean.split("/");
                        Log.e(LOG_TAG,date[0]);
                        Log.e(LOG_TAG,date[1]);
                        Log.e(LOG_TAG, date[2]);
                        int mes = Integer.parseInt(date[1]);
                        switch (mes){
                            case 1:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.JANUARY, Integer.parseInt(date[2]));
                                break;
                            case 2:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.FEBRUARY, Integer.parseInt(date[2]));
                                break;
                            case 3:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.MAY, Integer.parseInt(date[2]));
                                break;
                            case 4:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.MARCH, Integer.parseInt(date[2]));
                                break;
                            case 5:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.APRIL, Integer.parseInt(date[2]));
                                break;
                            case 6:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.JUNE, Integer.parseInt(date[2]));
                                break;
                            case 7:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.JULY, Integer.parseInt(date[2]));
                                break;
                            case 8:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.AUGUST, Integer.parseInt(date[2]));
                                break;
                            case 9:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.SEPTEMBER, Integer.parseInt(date[2]));
                                break;
                            case 10:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.OCTOBER, Integer.parseInt(date[2]));
                                break;
                            case 11:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.NOVEMBER, Integer.parseInt(date[2]));
                                break;
                            case 12:
                                lastUpdate.set(Integer.parseInt(date[0]), lastUpdate.DECEMBER, Integer.parseInt(date[2]));
                                break;

                        }
                        break;
                    case 6:
                        if(lineBoolean.equals("true")){
                            isInfoAboutLogged = true;
                        }else{
                            isInfoAboutLogged = false;
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        if(isInfoAboutLogged){
                            infoAuroraId = Integer.parseInt(lineBoolean);
                            User.getInstance().setUserAuroraId(infoAuroraId);
                        }
                        break;
                    case 9:
                        if(isInfoAboutLogged){
                            infoName = lineBoolean;
                        }
                        break;
                    case 10:
                        if(isInfoAboutLogged){
                            infoEmail = lineBoolean;
                        }
                        break;
                    case 11:
                        if(isInfoAboutLogged){
                            infoGener = lineBoolean;
                        }
                        break;
                    case 12:
                        if(isInfoAboutLogged){
                            infoSocialConnected = lineBoolean;
                        }
                        break;
                    case 13:
                        infoSocialConnectedId = "";
                        if(isInfoAboutLogged){
                            infoSocialConnectedId = lineBoolean;
                            SocialNetwork socialNetwork = SocialNetwork.account_none;
                            if(infoSocialConnected.equals("F")){
                                socialNetwork = SocialNetwork.account_facebook;
                            }
                            if(infoSocialConnected.equals("N")){
                                socialNetwork = SocialNetwork.account_none;
                            }
                            if(infoSocialConnected.equals("G")){
                                socialNetwork = SocialNetwork.account_google;
                            }
                            if(infoSocialConnected.equals("T")){
                                socialNetwork = SocialNetwork.account_twitter;
                            }
                            if(infoGener.equals("M")){
                                infoGener = "male";
                            }
                            User.getInstance().setUserInformation(infoName,infoSocialConnectedId,null,socialNetwork,infoEmail,infoGener);
                        }
                        break;

                }
                lineOfFile++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Não achou o arquivo");
            loadConfigDefault();
        } catch (IOException e){
            e.printStackTrace();
            Log.e(LOG_TAG,"Config Device: Não achou arquivo");
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
            if(SyncOnServerSometime){
                config.append("SyncOnServerSometime=true\n");
            }else{
                config.append("SyncOnServerSometime=false\n");
            }
            if(DataToSend){
                config.append("DataToSend=true\n");
            }else{
                config.append("DataToSend=false\n");
            }

            config.append("LastUpdate="+Integer.toString(lastUpdate.get(Calendar.YEAR))+"/"+Integer.toString(lastUpdate.get(Calendar.MONTH))+"/"+Integer.toString(lastUpdate.get(Calendar.DAY_OF_MONTH))+"\n");

            if(User.getInstance().isUserLoggedOnSocialNetwork()){
                config.append("userLoggedOnSocialNetwork=true\n");
            }else{
                config.append("userLoggedOnSocialNetwork=false\n");
            }

            if(User.getInstance().isUserLoggedOnServidor()){
                config.append("userLoggedOnServer=true\n");
            }else{
                config.append("userLoggedOnServer=false\n");
            }

            if(User.getInstance().isUserLoggedOnSocialNetwork()){
                config.append("userAuroraId="+User.getInstance().getUserAuroraId()+"\n");
                config.append("userNome="+User.getInstance().getUserName()+"\n");
                config.append("userEmail="+User.getInstance().getUserEmail()+"\n");
                config.append("userGenero="+User.getInstance().getUserGender()+"\n");
                switch (User.getInstance().getUserType()){
                    case account_facebook:
                        config.append("userSocialNetwork=F\n");
                        break;
                    case account_google:
                        config.append("userSocialNetwork=G\n");
                        break;
                    case account_twitter:
                        config.append("userSocialNetwork=T\n");
                        break;
                    case account_none:
                        config.append("userSocialNetwork=N\n");
                        break;
                    default:
                        config.append("userSocialNetwork=aaa\n");
                        break;
                }
                config.append("userSocialNetworkId=" + User.getInstance().getUserSocialId() + "\n");
            }
            config.flush();
            this.saveBudapest();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Erro ao salvar config");
            loadConfigDefault();
            return false;
        }
        return true;
    }

    public void setIsTheFirstTime(boolean isOrNot){
        isTheFirstTime = isOrNot;
    }

    public boolean getIsTheFirstTime(){
        return this.isTheFirstTime;
    }

    public void saveBudapest(){
        try {
            toSave = new ObjectOutputStream( new FileOutputStream("/sdcard/"+dirFiles+budapestFile) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            toSave.writeObject(Budapest.getInstance());
            toSave.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
