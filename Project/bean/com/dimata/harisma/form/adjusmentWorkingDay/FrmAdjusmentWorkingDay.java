/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.adjusmentWorkingDay;


import com.dimata.harisma.entity.adjusmentworkingday.AdjusmentWorkingDay;
import com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment;
import com.dimata.harisma.entity.koefisionposition.KoefisionPosition;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmAdjusmentWorkingDay  extends FRMHandler implements I_FRMInterface, 
        I_FRMType  {
      private AdjusmentWorkingDay AdjusmentWorkingDay;
    public static final String FRM_ADJUSMENT_WORKING_DAY= "FRM_ADJUSMENT_WORKING_DAY";
    public static final int FRM_FLD_ADJUSMENT_WORKING_DAY_ID=0;
    public static final int FRM_FLD_EMPLOYEE_ID=1;
    public static final int FRM_FLD_LOCATION_ID=2;
    public static final int FRM_FLD_SISTEM_WORK_HOURS=3;
    public static final int FRM_FLD_ADJUSMENT_WORKING_DAY=4;
           
           
    public static String[] fieldNames ={
        "FRM_FLD_ADJUSMENT_WORKING_DAY_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_LOCATION_ID",
        "FRM_FLD_SISTEM_WORK_HOURS",
        "FRM_FLD_ADJUSMENT_WORKING_DAY"
        
       
       
    };
    public static int[] fieldTypes = {
          TYPE_LONG,
          TYPE_LONG,
          TYPE_LONG,
          TYPE_FLOAT,
          TYPE_FLOAT
          
           };
public FrmAdjusmentWorkingDay(){
    }
    public FrmAdjusmentWorkingDay(AdjusmentWorkingDay adjusmentWorkingDay){
        this.AdjusmentWorkingDay = adjusmentWorkingDay;
    }
    public FrmAdjusmentWorkingDay(HttpServletRequest request, AdjusmentWorkingDay adjusmentWorkingDay){
        super(new FrmAdjusmentWorkingDay(adjusmentWorkingDay),request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.AdjusmentWorkingDay = adjusmentWorkingDay;
    }
    public String getFormName() { return FRM_ADJUSMENT_WORKING_DAY; }
    public int[] getFieldTypes() { return fieldTypes; }
    public String[] getFieldNames() { return fieldNames; }
    public int getFieldSize() { return fieldNames.length; }
    public AdjusmentWorkingDay getEntityObject(){ return AdjusmentWorkingDay; }

    public void requestEntityObject(AdjusmentWorkingDay  adjusmentWorkingDay) { //melakukan 
        ///pemanggilan terhadap Employee
            try{
		this.requestParam();
                    adjusmentWorkingDay.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
                    adjusmentWorkingDay.setLocationId(getLong(FRM_FLD_LOCATION_ID)); 
                    adjusmentWorkingDay.setSistemWorkHours(getDouble(FRM_FLD_SISTEM_WORK_HOURS));
                    adjusmentWorkingDay.setAdjusmentWorkingDay(getDouble(FRM_FLD_ADJUSMENT_WORKING_DAY)); 
                   
                      
                   
                    //set Nama_Employee
                    
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
    
}
