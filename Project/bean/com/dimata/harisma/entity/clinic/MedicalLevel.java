
package com.dimata.harisma.entity.clinic;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author bayu
 */

public class MedicalLevel extends Entity {

    private int sortNumber=0;
    private String levelName = "";
    private String levelClass = "";

    public int getSortNumber(){
        return this.sortNumber;
    }
    
    public void setSortNumber(int sortNumber){
        this.sortNumber=sortNumber;
    }
    
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
    
        
    public String getLevelClass() {
        return levelClass;
    }

    public void setLevelClass(String levelClass) {
        this.levelClass = levelClass;
    }
    
    
}
