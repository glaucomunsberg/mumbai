package plataformaparaformal.mumbai.util;

import java.io.Serializable;

/**
 * Created by glaucomunsberg on 8/14/14.
 */
public class TypeBase implements Serializable {
    public String description;
    public int id;
    public TypeBase(String description,int id){
        this.description = description;
        this.id = id;
    }
}
