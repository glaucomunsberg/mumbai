package com.plataformaparaformal.Mumbai.util;


import com.plataformaparaformal.Mumbai.services.Paraformalidade;
import com.plataformaparaformal.Mumbai.services.Scene;
import com.plataformaparaformal.Mumbai.services.SocialNetwork;

import java.util.ArrayList;

public class User {

    private static volatile User instance = null;

    public int id;
    public int lastLat;
    public int lastLng;

    public SocialNetwork userType;

	public boolean logged;

    public String userId;
	public String name;
	public String dtBorn;
	public String email;

    private ArrayList<Scene> userScenes;
    private ArrayList<Paraformalidade> userParaformalidades;

    private User(){
        logged = false;
        userType = SocialNetwork.account_none;
        userScenes = new ArrayList<Scene>();
        userParaformalidades = new ArrayList<Paraformalidade>();
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
		return false;
	}

	public boolean isLogged() {
		return false;
	}

}
