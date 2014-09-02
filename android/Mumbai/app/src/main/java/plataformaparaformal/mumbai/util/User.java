package plataformaparaformal.mumbai.util;


import plataformaparaformal.mumbai.services.Paraformalidade;

import java.util.ArrayList;

public class User {

    private static volatile User instance = null;

    private int id;
    private double lastLat;
    private double lastLng;

    private SocialNetwork userType;

    private boolean userLogged;
    private boolean userLoggedOnServidor;
    private boolean haveParaformalidadeToSync;

    private String userId;
    private String userName;
    private String userDtBorn;
    private String userEmail;

    private ArrayList<TypeBase> userScenes;
    private ArrayList<Paraformalidade> userParaformalidades;

    private User(){
        haveParaformalidadeToSync = false;
        userLogged = false;
        userType = SocialNetwork.account_none;
        userScenes = new ArrayList<TypeBase>();
        userParaformalidades = new ArrayList<Paraformalidade>();
        userLoggedOnServidor = false;
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
        this.lastLat = lastLat;
        this.lastLng = lastLng;
    }

    public void setUserInformation(String name,String userId,String born,SocialNetwork network, String userEmail){
        this.userDtBorn = born;
        this.userName = name;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userType = network;
        this.userLogged = true;
    }

    public SocialNetwork getUserType(){
        return userType;
    }

	public boolean isLogged() {
		return this.userLogged;
	}

    public boolean isUserLoggedOnServidor(){
        return userLoggedOnServidor;
    }
}
