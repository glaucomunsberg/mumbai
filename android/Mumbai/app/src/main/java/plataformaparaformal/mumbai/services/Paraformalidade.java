package plataformaparaformal.mumbai.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import plataformaparaformal.mumbai.util.TypeBase;

public class Paraformalidade {

    private static API api = API.getInstance();
    private static Calendar calendar = Calendar.getInstance();

    public int id;

	public String imageURL;
    public String shiftOccurrence;
    public String registeredAmount;
    public String geoLatitude;
    public String geoLongitude;
	public String description;
	public String link;
	public String registeredActivity;
	public String localizationSpace;
	public String numberBody;
	public String positionBody;
	public String equipmentScale;
	public String equipmentMobility;

	public ArrayList<TypeBase> equipmentInstalations;
	public ArrayList<TypeBase> senses;
	public ArrayList<TypeBase> environmentalConditions;
	public ArrayList<TypeBase> climates;

    public Date dtRegistration;

    public boolean isSyncronizedDetails;

	private ArrayList<TypeBase> authors;

    public Paraformalidade(int id,String geoLatitude,String geoLongitude,String description,String link,String registeredActivity, String imageURL,String shiftOccurrence,String registeredAmount,String localizationSpace,String numberBody,String positionBody,String equipmentScale, String equipmentMobility, String dtRegistration){

        this.id = id;
        this.geoLatitude = geoLatitude;
        this.geoLongitude = geoLongitude;
        this.description = description;
        this.link = link;
        this.registeredActivity = registeredActivity;
        this.imageURL = imageURL;
        this.shiftOccurrence=shiftOccurrence;
        this.registeredAmount=registeredAmount;
        this.localizationSpace = localizationSpace;
        this.numberBody = numberBody;
        this.positionBody = positionBody;
        this.equipmentScale = equipmentScale;
        this.equipmentMobility = equipmentMobility;

        try{
            calendar.set(Calendar.YEAR, 2014);
            calendar.set(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            this.dtRegistration = calendar.getTime();
        }catch (Exception e){

        }


    }

    public void syncronizeDetails(){
        if(!isSyncronizedDetails){
            try{

                isSyncronizedDetails = true;
            }catch (Exception e){
                isSyncronizedDetails = false;
            }


        }
    }



}
