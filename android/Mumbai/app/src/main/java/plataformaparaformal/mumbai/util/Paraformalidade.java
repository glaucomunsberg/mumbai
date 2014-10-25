package plataformaparaformal.mumbai.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import plataformaparaformal.mumbai.services.API;
import plataformaparaformal.mumbai.util.TypeBase;

public class Paraformalidade implements Serializable {

    private static API api = API.getInstance();
    private static Calendar calendar = Calendar.getInstance();

    public int id;

	public String imageURL;
    public int shiftOccurrenceId;
    public int registeredAmountId;
    public String geoLatitude;
    public String geoLongitude;
	public String description;
	public String link;
	public int registeredActivityId;
	public int localizationSpaceId;
	public int numberBodyId;
	public int positionBodyId;
	public int equipmentScaleId;
	public int equipmentMobilityId;

	public ArrayList<TypeBase> equipmentInstalations;
	public ArrayList<TypeBase> senses;
	public ArrayList<TypeBase> environmentalConditions;
	public ArrayList<TypeBase> climates;

    public Date dtRegistration;

    public boolean isSyncronizedDetails;

	private ArrayList<TypeBase> authors;

    public Paraformalidade(int id,String geoLatitude,String geoLongitude,String description,String link,int registeredActivityId, String imageURL,int shiftOccurrenceId,int registeredAmountId,int localizationSpaceId,int numberBodyId,int positionBodyId,int equipmentScaleId, int equipmentMobilityId, String dtRegistration){

        this.id = id;
        this.geoLatitude = geoLatitude;
        this.geoLongitude = geoLongitude;
        this.description = description;
        this.link = link;
        this.registeredActivityId = registeredActivityId;
        this.imageURL = imageURL;
        this.shiftOccurrenceId=shiftOccurrenceId;
        this.registeredAmountId=registeredAmountId;
        this.localizationSpaceId = localizationSpaceId;
        this.numberBodyId = numberBodyId;
        this.positionBodyId = positionBodyId;
        this.equipmentScaleId = equipmentScaleId;
        this.equipmentMobilityId = equipmentMobilityId;

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
