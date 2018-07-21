/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.service.presence;

import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.AlAutomaticStart;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.qdep.db.*;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Roy Andika
 */

public class RunAutomaticEntileAl implements Runnable{
    
    public void RunAutomaticEntileAl(){}

    /*
     * @Desc    : Untuk melakukan pemberian entitle AL secara automatic setiap bulan/period
     */
    public void run(){

        try{

            I_Leave leaveConfig = null;

            try{
                leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            }catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }

        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }

    }

    /**
     * @Desc    Untuk mendapatkan list employee yang akan mendapatkan entitle perbulan
     * @return
     */
    public static Vector getListEmployeeMustClosePeriod(I_Leave leaveConfig){

        Date datePeriodMonth = leaveConfig.getPeriodMonth();
        Date datePeriodYear = getPeriodYear(leaveConfig);
                
        DBResultSet dbrs = null;
        
        try{

            String sql = "SELECT ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STOCK_ID]+" as alStockId "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" as employeeId "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+" as alStatus "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+" as entitleDate "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_PREV_BALANCE]+" as prevBalance "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLED]+" as entitle "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_RECORD_DATE]+" as alQty "+
                    ", ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_QTY_USED]+" as qtyUsed "+
                    ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" as commencingDt "+
                    ", EMP."+PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" as level "+
                    ", EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+" as empCat "+
                    " FROM "+PstEmployee.TBL_HR_EMPLOYEE+" EMP INNER JOIN "+PstAlStockManagement.TBL_AL_STOCK_MANAGEMENT+" ALM ON "+
                    " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" = EMP."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+
                    " INNER JOIN "+PstLevel.TBL_HR_LEVEL+" LEV ON EMP."+PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]+
                    " = LEV."+PstLevel.fieldNames[PstLevel.FLD_LEVEL_ID]+" INNER JOIN "+PstEmpCategory.TBL_HR_EMP_CATEGORY+" EMPCAT ON EMP."+
                    PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+" = EMPCAT."+PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+
                    " WHERE ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"="+PstAlStockManagement.AL_STS_AKTIF+
                    " AND ALM."+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_AL_STATUS]+"='"+Formater.formatDate(datePeriodYear, "yyyy-MM-dd")+"'";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            Vector result = new Vector();

            while(rs.next()){

                AlAutomaticStart alAutomaticStart = new AlAutomaticStart();
                
                alAutomaticStart.setAlStockId(rs.getLong("alStockId"));
                alAutomaticStart.setEmployeeId(rs.getLong("employeeId"));
                alAutomaticStart.setAlStatus(rs.getInt("alStatus"));
                alAutomaticStart.setEntitleDate(rs.getDate("entitleDate"));
                alAutomaticStart.setPrevBalance(rs.getInt("prevBalance"));
                alAutomaticStart.setEntitle(rs.getInt("entitle"));
                alAutomaticStart.setAlQty(rs.getInt("alQty"));
                alAutomaticStart.setQtyUsed(rs.getInt("qtyUsed"));
                alAutomaticStart.setCommencingDate(rs.getDate("commencingDt"));
                alAutomaticStart.setLevel(rs.getString("level"));
                alAutomaticStart.setEmpCategory(rs.getString("empCat"));
                result.add(alAutomaticStart);
            }

            return result;
            
        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }finally{
            DBResultSet.close(dbrs);
        }

        return null;
    }

    public static Date getPeriodYear(I_Leave leaveConfig){

        Date current = new Date();

        try{
                        
            int tmpYear = current.getYear() + 1900;

            int year    = tmpYear - 1;
            int month   = leaveConfig.getMonthPeriod();
            int dt      = leaveConfig.getDatePeriod();

            
            String tmpPeriodYear = ""+year+"-"+month+"-"+dt;
            return Formater.formatDate(tmpPeriodYear,"yyyy-MM-dd");

        }catch(Exception E){
            System.out.println("[exception] "+E.toString());
        }

        return null;

    }

}
