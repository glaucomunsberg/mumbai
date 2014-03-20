package com.plataformaparaformal.Mumbai.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.plataformaparaformal.Mumbai.EditParaformalidadeActivity;

public class Config {

    private static volatile Config instance = null;

    public int versionNum;


    public String baseURL;
	public String hash;
    public String versionName;
	public String versionCompilation;
	public String versionDevelopBy;
	public String helpURL;
	public String homePagaURL;
	public String passwordAPI;
	public String baseURLAPI;

	public int portAPI;

	public boolean syncAutomatic;
	public boolean notificationOnScree;
	public boolean seeFullImage;

    private Config(){
        syncAutomatic = true;
        notificationOnScree = true;
        seeFullImage = false;
        versionNum = 1;
        versionName = "0.3";
        versionCompilation = "02/02/02 a90eb18Fdd0";
        versionDevelopBy = "Developeria";
        portAPI = 3000;

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

}
