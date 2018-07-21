/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.Reason;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author RAMA
 */
public class AttendanceConfigRamayanaMM implements I_Atendance{

     public int getConfigurasiInOut() {
       return I_Atendance.CONFIGURASI_I_SEARCH_BY_SCHEDULE;
    }
    public int getConfigurasiInsentif() {
         return I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF;
    }

    public int getConfigurasiReportScheduleDaily() {
        return I_Atendance.CONFIGURASI_I_REPORT_DAILY_SCHEDULE_AND_CEK_BOX_ADA_DIBELAKANG; 
    }

    public boolean getConfigurasiWorkingDays(long syspropScheduleOff,long oidScheduleOff,int statusAbs) {
        // update by satrya 2014-02-11 return true;
        if(syspropScheduleOff==oidScheduleOff){
            return false;
        }else{
            return true;
        }
    }

    public int getConfigurationBirthDay() {
        return I_Atendance.CONFIGURASI_I_VIEW_BIRTHDAY_A_WEEK;
    }
    
      public boolean getConfigurationShowPositionCode() {
        return true; 
    }

    public Hashtable getReasonIdKhusus() {
         Hashtable hashReasonId = new Hashtable();
        //long oidReason=504404505462416248l;
         Vector listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT]+"="+PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON]+ " ASC ");
        try{
            if(listReason!=null && listReason.size()>0){
                for(int idxRea=0;idxRea<listReason.size();idxRea++){
                    Reason reason = (Reason)listReason.get(idxRea);
                     hashReasonId.put(reason.getOID(), reason);
                }
            }
            //reason = PstReason.fetchExc(oidReason);
           
        }catch(Exception exception){
            System.out.println("error getReasonId"+exception);
        }
     
          return hashReasonId;
    }

    public int getConfigurationShowNoRekening() {
       return I_Atendance.CONFIGURASI_I_VIEW_SHOW_NO_REKENING;
    }

    public int getConfigurationOutletMiniMarket() {
        return I_Atendance.CONFIGURASI_I_VIEW_SHOW_ALL_CONFIGURATION_MINIMART;
    }
    
     public int getConfigurasiInputEmpNum() {
         return I_Atendance.CONFIGURATION_II_GENERATE_AUTOMATIC_EMPLOYEE_NUMBER;
    }

     

    
}
