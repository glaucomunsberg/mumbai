package plataformaparaformal.mumbai.services;

import com.google.android.gms.maps.GoogleMap;

import plataformaparaformal.mumbai.util.User;

public class Mumbai {

    private static volatile Mumbai instance = null;

	public static API api;
	public static User user;
	public static Config config;
    public static GoogleMap map;
    public static Budapest budapest;
    private boolean isTheFirstTime;

	private Paraformalidade paraformalidade;

    private Mumbai(){
        api = API.getInstance();
        user = User.getInstance();
        config = Config.getInstance();
        budapest = Budapest.getInstance();
        map = null;
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



}
