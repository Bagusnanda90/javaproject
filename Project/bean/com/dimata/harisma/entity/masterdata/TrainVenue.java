
package com.dimata.harisma.entity.masterdata;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */
 
public class TrainVenue extends Entity {
    
    private String venueName     =   "";
    private String venueDesc     =   "";
    
    
    public TrainVenue() {        
    }
    

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
    
    public String getVenueDesc() {
        return venueDesc;
    }

    public void setVenueDesc(String venueDesc) {
        this.venueDesc = venueDesc;
    }
        
}
