package plataformaparaformal.mumbai.services;


import android.util.Log;

import plataformaparaformal.mumbai.util.SocialNetwork;
import plataformaparaformal.mumbai.util.TypeBase;

import java.util.ArrayList;

public class User {

    private static volatile User instance = null;

    private int userAuroraId;
    private double userLastLat;
    private double userLastLng;

    private SocialNetwork userType;


    private boolean haveParaformalidadeToSync;

    private String userSocialId;
    private String userName;
    private String userDtBorn;
    private String userEmail;
    private String userGender;

    private ArrayList<TypeBase> userScenes;
    private ArrayList<Paraformalidade> userParaformalidades;

    private User(){
        haveParaformalidadeToSync = false;
        userSocialId = "";
        userType = SocialNetwork.account_none;
        userScenes = new ArrayList<TypeBase>();
        userParaformalidades = new ArrayList<Paraformalidade>();
        userAuroraId = 0;
        userGender = "M";
    }

    public static User getInstance(){
        if(instance == null){
            synchronized (User.class){
                if(instance == null){
                    instance = new User();
                }
            }
        }
        return instance;
    }

	public boolean haveParaformalidadeToSend() {
		return haveParaformalidadeToSync;
	}

    public void setLastLocalization(double lastLat,double lastLng){
        this.userLastLat = lastLat;
        this.userLastLng = lastLng;
    }

    public void setUserInformation(String name,String userId,String born,SocialNetwork network, String userEmail,String gender){

        this.userDtBorn = born;
        this.userName = name;
        this.userSocialId = userId;
        this.userEmail = userEmail;
        this.userType = network;


        if(userType == SocialNetwork.account_facebook){
            if(gender.equals("male")){
                this.userGender = "M";
            }else{
                this.userGender = "F";
            }
        }
    }

    public String getUserSocialId(){
       return userSocialId;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserGender(){
        return userGender;
    }

    public SocialNetwork getUserType(){
        return userType;
    }

    public int getUserAuroraId(){
        return userAuroraId;
    }

    public boolean isUserLoggedOnSocialNetwork(){
        if(this.userSocialId.equals("")){
            return false;
        }else{
            return true;
        }
    }


    public void setUserAuroraId(int idAurora){

        this.userAuroraId = idAurora;
    }

    public boolean isUserLoggedOnServidor(){
        if(this.userAuroraId == 0){
            return false;
        }else{
            return true;
        }
    }
}
