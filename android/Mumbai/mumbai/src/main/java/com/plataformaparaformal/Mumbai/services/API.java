package com.plataformaparaformal.Mumbai.services;

import com.plataformaparaformal.Mumbai.util.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class API {

    private static volatile API instance = null;

    private static Config config;
    private static User user;

    public ArrayList<Sense> senses;
    public ArrayList<EnvironmentalConditions> environmentalConditionses;
    public ArrayList<Climate> climates;
    public ArrayList<EquipmentInstalation> equipmentInstalations;
    public ArrayList<EquipmentMobility> equipmentMobilities;
    public ArrayList<EquipmentScale> equipmentScales;
    public ArrayList<NumberBody> numberBodies;
    public ArrayList<PositionBody> positionBodies;
    public ArrayList<LocalizationSpace> localizationSpaces;
    public ArrayList<RegisteredActivity> registeredActivities;
    public ArrayList<Shift> shifts;
    public ArrayList<Scene> scenes;

    private API(){

        senses      = new ArrayList<Sense>();
        shifts      = new ArrayList<Shift>();
        scenes      = new ArrayList<Scene>();
        climates    = new ArrayList<Climate>();
        equipmentScales = new ArrayList<EquipmentScale>();
        numberBodies    = new ArrayList<NumberBody>();
        positionBodies  = new ArrayList<PositionBody>();
        equipmentInstalations   = new ArrayList<EquipmentInstalation>();
        equipmentMobilities     = new ArrayList<EquipmentMobility>();
        localizationSpaces      = new ArrayList<LocalizationSpace>();
        registeredActivities    = new ArrayList<RegisteredActivity>();
        environmentalConditionses   = new ArrayList<EnvironmentalConditions>();

    }

    public static API getInstance(){
        if(instance == null){
            synchronized (Config.class){
                if(instance == null){
                    instance = new API();
                }
            }
        }
        return instance;
    }



	public boolean updateData() {
		return false;
	}

	public Scene getCena(int id) {
		return null;
	}

	public ArrayList getCenas(Arrays registeredActivity, Arrays shift, Arrays registeredAmount, Arrays positionBody, Arrays numberBody, Arrays senses, Arrays equipmentMobility, Arrays equipmenteScale, Arrays equipmenteInstalation) {
		return null;
	}

	public boolean getUser(String id, String name, SocialNetwork typeAccount, String userId, String email) {
		return false;
	}

	public boolean setNewUser(String userId, String email, SocialNetwork typeAccount, String name, String lastName, String dtBorun) {
		return false;
	}

	public boolean isOnAir() {
		return false;
	}

	public boolean sendData() {
		return false;
	}

    public ArrayList<EquipmentInstalation> getEquipmentInstalations(int id){
        return new ArrayList<EquipmentInstalation>();
    }

    public ArrayList<Sense> getSenses(int id){
        return new ArrayList<Sense>();
    }

    public ArrayList<EnvironmentalConditions> getEnvironmentalConditionses(int id){
        return new ArrayList<EnvironmentalConditions>();
    }

    public ArrayList<Climate> getClimates(int id){
        return new ArrayList<Climate>();
    }

    public ArrayList<Author> getAuthors(int id){
        return new ArrayList<Author>();
    }
}
