package plataformaparaformal.mumbai.services;

import java.util.ArrayList;

import plataformaparaformal.mumbai.util.TypeBase;

/**
 * Created by glaucomunsberg on 8/14/14.
 */
public class Budapest {

    private static volatile Budapest instance = null;

    private Config config = Config.getInstance();

    public ArrayList<TypeBase> authors;
    public ArrayList<TypeBase> scenes;

    public ArrayList<TypeBase> climates;
    public ArrayList<TypeBase> environmentalConditions;
    public ArrayList<TypeBase> equipmentInstalations;
    public ArrayList<TypeBase> equipmentMobilities;
    public ArrayList<TypeBase> equipmentScale;
    public ArrayList<TypeBase> localizationSpaces;
    public ArrayList<TypeBase> numberBodies;
    public ArrayList<TypeBase> positionBodies;
    public ArrayList<TypeBase> registeredActivities;
    public ArrayList<TypeBase> registeredAmounts;
    public ArrayList<TypeBase> senses;
    public ArrayList<TypeBase> shifts;

    private Budapest(){
        if(config.isOnAir){
            //get from server
        }else{
            if(config.SyncOnServerSometime){
                //get from file
            }else{
                senses = new ArrayList<TypeBase>();
                senses.add(new TypeBase("Tato",1));
                senses.add(new TypeBase("Cheiro",2));
                senses.add(new TypeBase("Sons",3));
                senses.add(new TypeBase("Cor/Textura",4));

                registeredAmounts = new ArrayList<TypeBase>();
                registeredAmounts.add(new TypeBase("Não selecionado",1));
                registeredAmounts.add(new TypeBase("Uníco",2));
                registeredAmounts.add(new TypeBase("Grupo",3));

                numberBodies = new ArrayList<TypeBase>();
                numberBodies.add(new TypeBase("Único",2));
                numberBodies.add(new TypeBase("Grupo",3));
                numberBodies.add(new TypeBase("Nenhum",4));

                positionBodies = new ArrayList<TypeBase>();
                positionBodies.add(new TypeBase("Sentado",2));
                positionBodies.add(new TypeBase("Em pé",3));
                positionBodies.add(new TypeBase("Nenhum",4));

                localizationSpaces = new ArrayList<TypeBase>();
                localizationSpaces.add(new TypeBase("Vazio",2));
                localizationSpaces.add(new TypeBase("Esquina",3));
                localizationSpaces.add(new TypeBase("Praça",4));
                localizationSpaces.add(new TypeBase("Calçada",5));

                equipmentScale = new ArrayList<TypeBase>();
                equipmentScale.add(new TypeBase("Pequeno",2));
                equipmentScale.add(new TypeBase("Medio",3));
                equipmentScale.add(new TypeBase("Grande",4));

                equipmentInstalations = new ArrayList<TypeBase>();
                equipmentInstalations.add(new TypeBase("Elétrico",1));
                equipmentInstalations.add(new TypeBase("Hidráulica",2));
                equipmentInstalations.add(new TypeBase("Sanitária",3));
                equipmentInstalations.add(new TypeBase("Telefone",4));


                equipmentMobilities = new ArrayList<TypeBase>();
                equipmentMobilities.add(new TypeBase("Ambulante",2));
                equipmentMobilities.add(new TypeBase("Fixo",3));
                equipmentMobilities.add(new TypeBase("Móvel",4));

                climates = new ArrayList<TypeBase>();
                climates.add(new TypeBase("Sol",1));
                climates.add(new TypeBase("Chuva",2));
                climates.add(new TypeBase("Nublado",3));
                climates.add(new TypeBase("Vento",4));
                climates.add(new TypeBase("Quente",5));
                climates.add(new TypeBase("Ameno",6));
                climates.add(new TypeBase("Frio",7));

                shifts = new ArrayList<TypeBase>();
                shifts.add(new TypeBase("Manhã",2));
                shifts.add(new TypeBase("Tarde",3));
                shifts.add(new TypeBase("Noite",4));
                shifts.add(new TypeBase("Madrugada",5));

                registeredActivities = new ArrayList<TypeBase>();
                registeredActivities.add(new TypeBase("Cultura/Arte",2));
                registeredActivities.add(new TypeBase("Moradia",3));
                registeredActivities.add(new TypeBase("Comércio",4));
                registeredActivities.add(new TypeBase("Subsistência",5));

                environmentalConditions = new ArrayList<TypeBase>();
                environmentalConditions.add(new TypeBase("Sobra",2));
                environmentalConditions.add(new TypeBase("Movimento",3));
                environmentalConditions.add(new TypeBase("Natureza",4));
                environmentalConditions.add(new TypeBase("Piso para Apoio",5));
                environmentalConditions.add(new TypeBase("Parede para Apoio",6));

            }

        }
    }

    public static Budapest getInstance(){
        if(instance == null){
            synchronized (Config.class){
                if(instance == null){
                    instance = new Budapest();
                }
            }
        }
        return instance;
    }

    public int[] getStringFromArrayId(ArrayList<TypeBase> list){

        int[] temp = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            temp[i] = list.get(i).id;
        }

        return temp;
    }

    public String[] getStringFromArrayDescription(ArrayList<TypeBase> list){

        String[] temp = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
             temp[i] = list.get(i).description;
        }

        return temp;
    }

    public String getDescriptionFromId(ArrayList<TypeBase> list, int id){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).id == id){
                return list.get(i).description;
            }
        }
        return "";
    }

    public int getIdFromDescription(ArrayList<TypeBase> list, String description){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).description.equals(description)){
                return list.get(i).id;
            }
        }
        return -1;
    }





}
