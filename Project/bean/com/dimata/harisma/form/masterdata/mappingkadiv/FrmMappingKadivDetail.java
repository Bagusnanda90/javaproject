/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmLeaveConfigurationMain
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata.mappingkadiv;

/**
 *
 * @author Wiweka
 */

/* java package */

import java.util.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.mappingkadiv.MappingKadivDetail;

public class FrmMappingKadivDetail extends FRMHandler implements I_FRMInterface, I_FRMType{
     private MappingKadivDetail mappingKadivDetail;
     private Vector listGroupMappingKadivOutlet = new Vector();
     private String message="";
    public static final String FRM_NAME_MAPPING_KADIV_DETAIL = "FRM_NAME_MAPPING_KADIV_DETAIL";

    public static final int FRM_FIELD_MAPPING_KADIV_MAIN_ID = 0;
    public static final int FRM_FIELD_LOCATION_ID = 1;

    public static String[] fieldNames = {
        "FRM_FIELD_MAPPING_KADIV_MAIN_ID", "FRM_FIELD_LOCATION_ID",
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
    };

    public FrmMappingKadivDetail() {
    }

    public FrmMappingKadivDetail(MappingKadivDetail mappingKadivDetail) {
        this.mappingKadivDetail = mappingKadivDetail;
    }

    public FrmMappingKadivDetail(HttpServletRequest request, MappingKadivDetail mappingKadivDetail) {
        super(new FrmMappingKadivDetail(mappingKadivDetail), request);
        this.mappingKadivDetail = mappingKadivDetail;
    }

    public String getFormName() {
        return FRM_NAME_MAPPING_KADIV_DETAIL;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public MappingKadivDetail getEntityObject() {
        return mappingKadivDetail;
    }

    public void requestEntityObjectMultiple(MappingKadivDetail mappingKadivDetail) {
        try {
            this.requestParam();
            mappingKadivDetail.setLocationIds(this.getParamsStringValues(fieldNames[FRM_FIELD_LOCATION_ID]));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public void requestEntityObject(MappingKadivDetail mappingKadivDetail) {
        try {
            this.requestParam();
            mappingKadivDetail.setLocationId(getLong(FRM_FIELD_LOCATION_ID));            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    public  Vector getGroupMappingKadiv(long oidMappingKadivMain,MappingKadivDetail mappingKadivDetail){
        try{
            //Vector listResult = new Vector();
           // String sValsDept[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_DEPARTEMENT_ID]);
           // String sValsSec[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_SECTION_ID]);
            //artinya jika sizenya tidak sama maka sistem akan memberikan 
            if( mappingKadivDetail==null || (mappingKadivDetail.getLocationIds()==null) || (mappingKadivDetail.getLocationIds().length<1)){
                this.setMessage("selec department is null");
                return getListGroupMappingKadiv();
            }
            
            for(int i=0; i < mappingKadivDetail.getLocationIds().length ; i++){
                try{
                   MappingKadivDetail objmappingKadivDetail = new MappingKadivDetail();
                   mappingKadivDetail.setMappingkadivMainId(oidMappingKadivMain);
                   long oidLocation = new Long(Long.parseLong(mappingKadivDetail.getLocationIds()[i]));
                   objmappingKadivDetail.setLocationId(oidLocation);
                   getListGroupMappingKadiv().add(objmappingKadivDetail); 
                }catch (Exception exc){
                    System.out.println("EXC : getVectorLong i="+i+" - "+exc);
                }
            }
            
            return getListGroupMappingKadiv();
            
        }catch(Exception e){
        }
        
        return new Vector(1,1);
        
    }

    /**
     * @return the listGroupMappingKadivOutlet
     */
    public Vector getListGroupMappingKadiv() {
        return listGroupMappingKadivOutlet;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
   

}
