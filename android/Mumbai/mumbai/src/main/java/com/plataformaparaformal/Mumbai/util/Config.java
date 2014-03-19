package com.plataformaparaformal.Mumbai.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.plataformaparaformal.Mumbai.EditParaformalidadeActivity;

public class Config {

    private static volatile Config instance = null;

    public int versionNum;

    public String versionName;
    public String baseURL;
	public String hash;
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
