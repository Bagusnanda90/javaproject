
package com.dimata.harisma.form.masterdata;

// import java
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

// import qdep
import com.dimata.qdep.form.*;

// import harisma
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class FrmRace extends FRMHandler implements I_FRMInterface, I_FRMType {

    public static final String FRM_NAME_RACE        =   "FRM_RACE";
    
    public static final int FRM_FIELD_RACE_ID       =   0;
    public static final int FRM_FIELD_RACE_NAME     =   1;
    
    
    public static String[] fieldNames = 
    {
        "FRM_FLD_RACE_ID",
        "FRM_FLD_RACE_NAME"
    };
    
    public static int[] fieldTypes = 
    {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED
    };
    
    private Race race;
    
    
    public FrmRace() {
    }
    
    public FrmRace(Race race) {
        this.race = race;
    }
    
    public FrmRace(HttpServletRequest request, Race race) {
        super(new FrmRace(race), request);
        this.race = race;
    }

    
    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_RACE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    
    public Race getEntityObject() {
        return race;
    }
    
    public void requestEntityObject(Race race) {
        try {
            this.requestParam();
            race.setRaceName(this.getString(FRM_FIELD_RACE_NAME));
        }
        catch(Exception e) {}
    }
    
}
