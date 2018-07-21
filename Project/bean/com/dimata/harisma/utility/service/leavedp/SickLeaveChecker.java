/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.service.leavedp;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;

/**
 *
 * @author artha
 */
public class SickLeaveChecker {
    public synchronized  static void proccesSickLeaveNotPresent(long symbolId, long scheduleId, Date date){
        String strLeaveSymbol = "";
        long leaveId = 0;
        try{
            strLeaveSymbol = PstSystemProperty.getValueByName("SICK_LEAVE_SYMBOL");
            leaveId = PstScheduleSymbol.getScheduleId(strLeaveSymbol);
            
            if(leaveId==symbolId){
                updateStatus(scheduleId,date);
            }
        }catch(Exception ex){}
    }
    
    private static long updateStatus(long scheduleId, Date date){
        int result = 0;
        try {
                String sql = "UPDATE "+PstEmpSchedule.TBL_HR_EMP_SCHEDULE
                        +" SET "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1-1+date.getDate()]
                        +" = "+PstEmpSchedule.REASON_ABSENCE_SICKNESS
                        +" WHERE "+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]
                        +" = "+scheduleId
                        ;
                System.out.println("SickLeaveChecker.updateStatus :::: "+sql);
                result = DBHandler.execUpdate(sql);
                return result;
        }catch(Exception e) {
                return 0;
        }finally {
                return result;
        }
    }
}
