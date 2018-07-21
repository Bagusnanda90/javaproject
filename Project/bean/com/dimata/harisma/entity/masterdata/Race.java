
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author bayu
 */

public class Race extends Entity {
    
    private String raceName = "";
    

    public Race() {
    }
    
    
    public String getRaceName() {
        return raceName;
    }
    
    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }
    
}
