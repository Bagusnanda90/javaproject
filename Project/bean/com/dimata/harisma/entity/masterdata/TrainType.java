
package com.dimata.harisma.entity.masterdata;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class TrainType extends Entity {
    
    private String typeName     =   "";
    private String typeDesc     =   "";
    private String typeCode     =   "";
    
     
    public TrainType() {        
    }
    

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }  

    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
}
