/* 
 * Form Name  	:  FrmPresence.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.attendance;

/* java package */ 
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.form.*;
import java.io.*; 
import java.sql.ResultSet;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 

public class FrmPresence extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Presence presence;
        private Vector cekBox=new Vector();
	public static final String FRM_NAME_PRESENCE		=  "FRM_NAME_PRESENCE" ;

	public static final int FRM_FIELD_PRESENCE_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_PRESENCE_DATETIME		=  2 ;
	public static final int FRM_FIELD_STATUS			=  3 ;
	public static final int FRM_FIELD_ANALYZED			=  4 ;
        //update by satrya 2012-08-08
        public static final int FRM_FIELD_SCHEDULE_DATETIME		=  5 ;
        //update byd devin 2014-03-21
        public static final int FRM_FIELD_PRESENCE_DATETIME_END		=  6 ;
        public static final int FRM_FIELD_PRESENCE_TIME		=  7 ;
        public static final int FRM_FIELD_MANUAL_PRESENCE	=  8 ;
        
	public static String[] fieldNames = {
		"FRM_FIELD_PRESENCE_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_PRESENCE_DATETIME",  "FRM_FIELD_STATUS",  "FRM_FIELD_ANALYZED", "SCHEDULE_DATETIME","FRM_FIELD_PRESENCE_DATETIME_END","FRM_FIELD_PRESENCE_TIME","FRM_FIELD_MANUAL_PRESENCE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG ,     
		TYPE_DATE ,  TYPE_INT, TYPE_INT, TYPE_DATE,TYPE_DATE,TYPE_DATE,TYPE_INT
	} ;

	public FrmPresence(){
	}
	public FrmPresence(Presence presence){
		this.presence = presence;
	}

	public FrmPresence(HttpServletRequest request, Presence presence){
		super(new FrmPresence(presence), request);
		this.presence = presence;
	}

	public String getFormName() { return FRM_NAME_PRESENCE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Presence getEntityObject(){ return presence; }

	public void requestEntityObject(Presence presence) {
		try{
			this.requestParam();
			presence.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                      
			presence.setPresenceDatetime(getDate(FRM_FIELD_PRESENCE_DATETIME));
			presence.setStatus(getInt(FRM_FIELD_STATUS));
			presence.setAnalyzed(getInt(FRM_FIELD_ANALYZED));
			presence.setManualPresence(getInt(FRM_FIELD_MANUAL_PRESENCE));
                        //update by satrya 2012-10-01
                        //update by satrya 2012-12-05
                       /* if(getDate(FRM_FIELD_SCHEDULE_DATETIME)==null){
                          if(presence.getPresenceDatetime()!=null){
                              Date dtX = (presence.getPresenceDatetime());
                              dtX.setHours(0);
                              dtX.setMinutes(0);
                            dtX.setSeconds(0);
                             presence.setScheduleDatetime(dtX);
                          }else{
                            Date dT = new Date(); 
                            dT.setHours(0);
                            dT.setMinutes(0);
                            dT.setSeconds(0);
                            presence.setScheduleDatetime(dT);
                          }
                            
                        }else{
                        presence.setScheduleDatetime(getDate(FRM_FIELD_SCHEDULE_DATETIME));
                        }*/
                         presence.setScheduleDatetime(getDate(FRM_FIELD_SCHEDULE_DATETIME));
                      
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
        
        public Vector<Employee> manualInputPresenceMultiple() {
        try {
            this.requestParam();

     String[] employeeId = null;
    employeeId = this.getParamsStringValues("cek");
          
    if(employeeId != null && employeeId.length > 0){        
    
       // String[] data_executed = null;		
        //data_executed = this.getParamsStringValues("executed");
        
        for(int i=0; i<employeeId.length; i++){
            //int ix = this.getParamInt("executed"+i);
            long empId = 0;
            if(employeeId[i]!=null){
             empId = Long.parseLong(employeeId[i]);
}
           
            if(empId!=0){
                //long empId = 0;
               // long oidEmployee=oidEmployee(empId);
                try{
                    
                    //empId = Long.parseLong(employeeId[i]);
                    Presence presen =new Presence();
                    presen.setEmployeeId(empId);
                      
			presen.setPresenceDatetime(getDate(FRM_FIELD_PRESENCE_DATETIME));
			presen.setStatus(getInt(FRM_FIELD_STATUS));
			presen.setAnalyzed(getInt(FRM_FIELD_ANALYZED));
                        presen.setPresenceDatetimeEnd(getDate(FRM_FIELD_PRESENCE_DATETIME_END));
                        presen.setManualPresence(getInt(FRM_FIELD_MANUAL_PRESENCE));
                        //update by satrya 2012-10-01
                        //update by satrya 2012-12-05
                       /* if(getDate(FRM_FIELD_SCHEDULE_DATETIME)==null){
                          if(presence.getPresenceDatetime()!=null){
                              Date dtX = (presence.getPresenceDatetime());
                              dtX.setHours(0);
                              dtX.setMinutes(0);
                            dtX.setSeconds(0);
                             presence.setScheduleDatetime(dtX);
                          }else{
                            Date dT = new Date(); 
                            dT.setHours(0);
                            dT.setMinutes(0);
                            dT.setSeconds(0);
                            presence.setScheduleDatetime(dT);
                          }
                            
                        }else{
                        presence.setScheduleDatetime(getDate(FRM_FIELD_SCHEDULE_DATETIME));
                        }*/
                         presen.setScheduleDatetime(getDate(FRM_FIELD_SCHEDULE_DATETIME));
                      
                   
                    getCekBox().add(presen);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }    
                 
            }    
        }        
    }
        } catch(Exception exc){
            System.out.println("Exception rs to frmpublicdetailLeave"+exc);
        }
        return getCekBox();
    }

    /**
     * @return the publicLeaves
     */
    public Vector getCekBox() {
        return cekBox;
    }

    /**
     * @param publicLeaves the publicLeaves to set
     */
    public void setCekBox(Vector cekBox) {
        this.cekBox = cekBox;
    }
    
    public static long oidEmployee(long employeeNum){ 
        long result=0;
        DBResultSet dbrs=null;
        try{
            String sql =" SELECT " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " WHERE " +
                     PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +  " = " + employeeNum;
            dbrs=DBHandler.execQueryResult(sql);
            ResultSet rs =dbrs.getResultSet();
            while(rs.next()){
                result=rs.getLong(1);
            }
        }catch(Exception exc){
            
        }finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }
}
