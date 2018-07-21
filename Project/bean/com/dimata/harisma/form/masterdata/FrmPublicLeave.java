/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;
import com.dimata.harisma.entity.masterdata.PublicLeave;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmPublicLeave extends FRMHandler implements I_FRMInterface, I_FRMType{
     private PublicLeave publicLeave;

    public static final String FRM_NAME_PUBLIC_LEAVE = "FRM_NAME_PUBLIC_LEAVE";

    public static final int FRM_FIELD_PUBLIC_LEAVE_ID = 0;
    public static final int FRM_FIELD_PUBLIC_HOLIDAY_ID = 1;
    public static final int FRM_FIELD_PUBLIC_DATE_FROM = 2;
    public static final int FRM_FIELD_PUBLIC_DATE_TO = 3;
    public static final int FRM_FIELD_LEAVE_TYPE = 4;
    public static final int FRM_FIELD_PUBLIC_TYPE_CATEGORY=5;
    public static final int FRM_FIELD_PUBLIC_FLAG_SCH=6;
    
    
    public static String[] fieldNames = {
        "FRM_FIELD_PUBLIC_LEAVE_ID",
        "FRM_FIELD_PUBLIC_HOLIDAY_ID",
        "FRM_FIELD_PUBLIC_DATE_FROM",
        "FRM_FIELD_PUBLIC_DATE_TO",
        "FRM_FIELD_LEAVE_TYPE",
         "FRM_FIELD_PUBLIC_TYPE_CATEGORY",
         "FRM_FIELD_PUBLIC_FLAG_SCH"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };


    public FrmPublicLeave() {
    }

    public FrmPublicLeave(PublicLeave objPublicLeave) {
        this.publicLeave = objPublicLeave;
    }

    public FrmPublicLeave(HttpServletRequest request, PublicLeave objPublicLeave) {
        super(new FrmPublicLeave(objPublicLeave), request);
        this.publicLeave = objPublicLeave;
    }


    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_PUBLIC_LEAVE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public PublicLeave getEntityObject(){
        return publicLeave;
    }

    public void requestEntityObject(PublicLeave objPublicLeave){
        try{
            this.requestParam();
            objPublicLeave.setPublicHolidayId(getLong(FRM_FIELD_PUBLIC_HOLIDAY_ID));
            objPublicLeave.setDateLeaveFrom(getDate(FRM_FIELD_PUBLIC_DATE_FROM));
            objPublicLeave.setDateLeaveTo(getDate(FRM_FIELD_PUBLIC_DATE_TO));
            objPublicLeave.setTypeLeave(getLong(FRM_FIELD_LEAVE_TYPE));
            objPublicLeave.setEmpCat(getLong(FRM_FIELD_PUBLIC_TYPE_CATEGORY));
            objPublicLeave.setFlagSch(getInt(FRM_FIELD_PUBLIC_FLAG_SCH));
            
        }
        catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
    }
}
