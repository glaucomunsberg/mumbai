package com.plataformaparaformal.Mumbai.services;

import java.util.Date;

public class Scene {

	public final int id;
	public final String description;
	public final Date dtOccurrence;
	public final boolean publicContribution;
    public int activityGroupId;

    public Scene(int id,String description,Date dtOccurrence, boolean publicContribution){
        this.id = id;
        this.description = description;
        this.dtOccurrence = dtOccurrence;
        this.publicContribution = publicContribution;
    }
}
