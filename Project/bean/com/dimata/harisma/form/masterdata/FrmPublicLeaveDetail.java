/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;
import com.dimata.harisma.entity.masterdata.PublicLeaveDetail;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Dimata 007
 */
public class FrmPublicLeaveDetail extends FRMHandler implements I_FRMInterface, I_FRMType{
     private PublicLeaveDetail publicLeaveDetail;
     private Vector publicLeaves = new Vector();
    public static final String FRM_NAME_PUBLIC_LEAVE_DETAIL = "FRM_NAME_PUBLIC_LEAVE_DETAIL";

    public static final int FRM_FIELD_PUBLIC_LEAVE_DETAIL_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_TYPE_LEAVE = 2;
    public static final int FRM_FIELD_PUBLIC_LEAVE_ID = 3;
    public static final int FRM_FIELD_PUBLIC_HOLIDAY_ID = 4;
    public static final int FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE = 5;
    public static final int FRM_FIELD_PUBLIC_LEAVE_DETAIL_DATE_FROM = 6;
    public static final int FRM_FIELD_PUBLIC_LEAVE_DETAIL_DATE_TO = 7;
    
    
    public static String[] fieldNames = {
        "FRM_FIELD_PUBLIC_LEAVE_DETAIL_ID",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_TYPE_LEAVE",
        "FRM_FIELD_PUBLIC_LEAVE_ID",
        "FRM_FIELD_PUBLIC_HOLIDAY_ID",
        "FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE",
        "FRM_FIELD_PUBLIC_LEAVE_DETAIL_DATE_FROM",
        "FRM_FIELD_PUBLIC_LEAVE_DETAIL_DATE_TO",
        "FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG ,
        TYPE_LONG ,
        TYPE_LONG ,
        TYPE_LONG ,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    };


    public FrmPublicLeaveDetail() {
    }

    public FrmPublicLeaveDetail(PublicLeaveDetail objPublicLeaveDetail) { 
        this.publicLeaveDetail = objPublicLeaveDetail;
    }

    public FrmPublicLeaveDetail(HttpServletRequest request, PublicLeaveDetail objPublicLeaveDetail) {
        super(new FrmPublicLeaveDetail(objPublicLeaveDetail), request);
        this.publicLeaveDetail = objPublicLeaveDetail;
    }


    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_PUBLIC_LEAVE_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public PublicLeaveDetail getEntityObject(){
        return publicLeaveDetail;
    }

    public void requestEntityObject(PublicLeaveDetail objPublicLeaveDetail){
        try{
            this.requestParam();
            objPublicLeaveDetail.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            objPublicLeaveDetail.setPublicHolidayId(getLong(FRM_FIELD_PUBLIC_HOLIDAY_ID));
            objPublicLeaveDetail.setPublicLeaveId(getLong(FRM_FIELD_PUBLIC_LEAVE_ID));
            objPublicLeaveDetail.setTypeLeaveId(getLong(FRM_FIELD_TYPE_LEAVE));
            objPublicLeaveDetail.setNote(getString(FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE));
            
           
        }
        catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
    }
    
public Vector<PublicLeaveDetail> requestEntityObjectMultiple() {
        try {
            this.requestParam();

     String[] employeeId = null;
    employeeId = this.getParamsStringValues("empoid");
          
    if(employeeId != null && employeeId.length > 0){        
    
       // String[] data_executed = null;		
        //data_executed = this.getParamsStringValues("executed");
         String[] empCategory = null;
            empCategory = this.getParamsStringValues("empcat");
        for(int i=0; i<employeeId.length; i++){
            //int ix = this.getParamInt("executed"+i);
            long empId = 0;
            empId = Long.parseLong(employeeId[i]);
            if(empId!=0){
                //long empId = 0;
                long empCatgId=0;
                try{
                    //empId = Long.parseLong(employeeId[i]);
                    empCatgId = Long.parseLong(empCategory[i]);
                    PublicLeaveDetail publicLeaveDetailX = new PublicLeaveDetail();
                    publicLeaveDetailX.setEmployeeId(empId);
                    publicLeaveDetailX.setEmpCategoryId(empCatgId);
                    publicLeaveDetailX.setNote((getString(FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE)));
                    getPublicLeaves().add(publicLeaveDetailX);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }    
                 
            }    
        }        
    }
        } catch(Exception exc){
            System.out.println("Exception rs to frmpublicdetailLeave"+exc);
        }
        return getPublicLeaves();
    }

    /**
     * @return the publicLeaves
     */
    public Vector getPublicLeaves() {
        return publicLeaves;
    }

    /**
     * @param publicLeaves the publicLeaves to set
     */
    public void setPublicLeaves(Vector publicLeaves) {
        this.publicLeaves = publicLeaves;
    }

}
