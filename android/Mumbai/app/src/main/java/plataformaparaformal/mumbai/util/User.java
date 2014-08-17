package plataformaparaformal.mumbai.util;


import plataformaparaformal.mumbai.services.Paraformalidade;

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

    private ArrayList<TypeBase> userScenes;
    private ArrayList<Paraformalidade> userParaformalidades;

    private User(){
        logged = false;
        userType = SocialNetwork.account_none;
        userScenes = new ArrayList<TypeBase>();
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
