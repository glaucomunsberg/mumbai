package com.plataformaparaformal.Mumbai.services;

/**
 * Created by glaucoroberto on 3/23/14.
 */
public enum ServerMethods {
    paraformalidade(0),sense(1),shift(2),positionBody(3),numberBody(4),equipmentScale(5),equipmentMobility(6),equipmentInstalation(7),registeredActivity(8),climate(9),localizationSpace(10),registeredAmount(11),scene(12),environmentalCondition(13),isOnAir(14);
    private int value;

    private ServerMethods(int value){
        this.value = value;
    }

    public String getAccount(){
        switch (this){
            case paraformalidade:
                return "paraformalidade";
            case sense:
                return "sense";
            case shift:
                return "shift";
            case numberBody:
                return "numberBody";
            case positionBody:
                return "positionBody";
            case equipmentScale:
                return "equipmentScale";
            case equipmentMobility:
                return "equipmentMobility";
            case equipmentInstalation:
                return "equipmentInstalation";
            case registeredActivity:
                return "registeredActivity";
            case climate:
                return "climate";
            case localizationSpace:
                return "localizationSpace";
            case registeredAmount:
                return "registeredAmount";
            case scene:
                return "scene";
            case environmentalCondition:
                return "environmentalCondition";
            case isOnAir:
                return "isOnAir";
            default:
                return "";
        }
    }
}
