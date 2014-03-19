package com.plataformaparaformal.Mumbai.util;

import com.plataformaparaformal.Mumbai.services.API;
import com.plataformaparaformal.Mumbai.services.User;
import com.plataformaparaformal.Mumbai.services.Scene;
import com.plataformaparaformal.Mumbai.services.Paraformalidade;

public class Mumbai {

    private static volatile Mumbai instance = null;

	public static API api = API.getInstance();
	public static User user = User.getInstance();
	public static Config config = Config.getInstance();

	private Scene scene;

	private Paraformalidade paraformalidade;

    private Mumbai(){

    }

    public static Mumbai getInstance(){
        if(instance == null){
            synchronized (Config.class){
                if(instance == null){
                    instance = new Mumbai();
                }
            }
        }
        return instance;
    }

	public void viewParaformalidade() {

	}

	public void filterParaformalidade() {

	}

	public void seeParaformalidadesOfUser() {

	}

	public void seeInformationOfParaformalidade() {

	}

	public void shareParaformalidade() {

	}

	public void paraformalidadeEdit() {

	}

	public void OwnParaformalidadeEdit() {

	}

	public void viewAccount() {

	}

	public void paraformalidadeCreate() {

	}

	public void doLogIn() {

	}

	public void doSignIn() {

	}

}
