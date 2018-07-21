/**
 * User: wardana
 * Date: Apr 8, 2004
 * Time: 9:24:23 AM
 * Version: 1.0
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.harisma.entity.masterdata.PublicHolidays;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class FrmPublicHolidays extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PublicHolidays objPublicHolidays;
    private Vector vlist = new Vector();
    public static final String FRM_NAME_HOLIDAYS = "FRM_NAME_HOLIDAYS";

    public static final int FRM_FIELD_HOLIDAY_ID = 0;
    public static final int FRM_FIELD_HOLIDAY_DATE = 1;
    public static final int FRM_FIELD_HOLIDAY_DESC = 2;
    public static final int FRM_FIELD_HOLIDAY_STS = 3;
    public static final int FRM_FIELD_HOLIDAY_DATE_TO = 4;
    
    public static String[] fieldNames = {
        "FRM_FIELD_HOLIDAY_ID",
        "FRM_FIELD_HOLIDAY_DATE",
        "FRM_FIELD_HOLIDAY_DESC",
        "FRM_FIELD_HOLIDAY_STS",
        "FRM_FIELD_HOLIDAY_DATE_TO",
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED
    };

    public FrmPublicHolidays() {
    }

    public FrmPublicHolidays(PublicHolidays objPublicHolidays) {
        this.objPublicHolidays = objPublicHolidays;
    }

    public FrmPublicHolidays(HttpServletRequest request, PublicHolidays objPublicHolidays) {
        super(new FrmPublicHolidays(objPublicHolidays), request);
        this.objPublicHolidays = objPublicHolidays;
    }


    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_HOLIDAYS;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public PublicHolidays getEntityObject(){
        return objPublicHolidays;
    }

    public void requestEntityObject(PublicHolidays objPublicHolidays){
        try{
            this.requestParam();
            objPublicHolidays.setDtHolidayDate(getDate(FRM_FIELD_HOLIDAY_DATE));
            objPublicHolidays.setStDesc(getString(FRM_FIELD_HOLIDAY_DESC));
            objPublicHolidays.setiHolidaySts(getLong(FRM_FIELD_HOLIDAY_STS));
            objPublicHolidays.setDtHolidayDateTo(getDate(FRM_FIELD_HOLIDAY_DATE_TO));
        }
        catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
    }
    
     public void requestMultipleEntityObjectGenerate(Vector listPublicHolidays){
        try{
            this.requestParam();
            
                for (int iFrom = 0; iFrom < listPublicHolidays.size(); iFrom++) {
                    PublicHolidays publicHolidays = (PublicHolidays) listPublicHolidays.get(iFrom);
                        String selectedPublic = this.getParamString("holiday_"+publicHolidays.getOID());
                        if (selectedPublic.equals("1")){
                            vlist.add(publicHolidays);
                        }
                }
        }
        catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
    }
    /**
     * @return the vlistEntriOpname
     */
    public Vector getVList() {
        return vlist;
    }
}
