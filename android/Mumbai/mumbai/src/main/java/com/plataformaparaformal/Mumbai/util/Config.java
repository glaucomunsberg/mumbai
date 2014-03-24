package com.plataformaparaformal.Mumbai.util;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.plataformaparaformal.Mumbai.EditParaformalidadeActivity;

public class Config {

    private static volatile Config instance = null;

    public final int versionNum = 3;
	public final String portAPI = "3000";
	public final String hash = "";
    public final String versionName = "0.3";
	public final String versionCompilation  = "02/02/02 a90eb18Fdd0";
	public final String versionDevelopBy = "Developeria";
	public final String urlHelpCenter = "http://www.plataformaparaformal.com.br/Ajuda";
	public final String urlHomePage = "http://www.plataformaparaformal.com.br";
	public final String passwordAPI = "";
	public final String urlBaseAPI = "192.168.1.104";

    public Toast principalToast;
    public AlertDialog.Builder principalAlertDialog;
	public boolean syncAutomatic;
	public boolean notificationOnScree;
	public boolean seeFullImage;
    public boolean isOnAir;
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
