package com.plataformaparaformal.Mumbai.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.plataformaparaformal.Mumbai.EditParaformalidadeActivity;

public class Config {

    	private static volatile Config instance = null;

    	public final int versionNum = 3;
	public final int portAPI = 3000;
	public final String hash = "";
    	public final String versionName = "0.3";
	public final String versionCompilation  = "02/02/02 a90eb18Fdd0";
	public final String versionDevelopBy = "Developeria";
	public final String urlHelpCenter = "http://www.plataformaparaformal.com.br/Ajuda";
	public final String urlHomePage = "http://www.plataformaparaformal.com.br";
	public final String passwordAPI = "";
	public final String urlBaseAPI = "";

	public boolean syncAutomatic;
	public boolean notificationOnScree;
	public boolean seeFullImage;

    private Config(){
        syncAutomatic = true;
        notificationOnScree = true;
        seeFullImage = false;
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
