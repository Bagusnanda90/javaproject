/*
 * LongLeaveMonthly.java
 *
 * Created on December 19, 2004, 6:03 PM
 */

package com.dimata.harisma.session.attendance;

// import core java package
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;

// import dimata package
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;

// import qdep package
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.DBHandler;

// import project package
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.attendance.LLStockReporting;  
import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.attendance.PstLLStockManagement;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;

/**
 *
 * @author  Administrator
 * @version 
 */
public class LongLeaveMonthly {

    /**
     * @param text
     * @return
     */    
    private static Vector logicParser(String text)
    {
        Vector vector = LogicParser.textSentence(text);
        for(int i =0;i < vector.size();i++)
        {
            String code =(String)vector.get(i);
            if(((vector.get(vector.size()-1)).equals(LogicParser.SIGN))&&
              ((vector.get(vector.size()-1)).equals(LogicParser.ENGLISH)))
            { 
                vector.remove(vector.size()-1);
            }
        }
        return vector;
    }

    
    
    // ------------------ Start LL Reporting --------------------
    /**
     * @param srcLeaveManagement
     * @return
     * @created by Edhy
     */
    synchronized public static Vector listLLStockReport(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_ll_stock_temp";//HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            // empty table LL stock temporary table
            int status = emptyTemp();
            
            // add data into AL stock temporary table (to clear lat year, entitle this year, earned ytd, taken mtd and taken ytd)
            status = addEntitle1ToTemp(srcLeaveManagement);            
            status = addEntitle2ToTemp(srcLeaveManagement);                        
            status = addTakenMtdToTemp(srcLeaveManagement);            
            status = addTakenYtdToTemp(srcLeaveManagement);            
            
            String sQL = "SELECT PAYROLL, NAME, COMM_DATE, SUM(ENTITLE1), SUM(ENTITLE2), SUM(TAKEN_MTD), SUM(TAKEN_YTD) "+
                         " FROM " + tblDpReportName +
                         " GROUP BY PAYROLL";
            
            System.out.println("listLLStockReport SQL : " + sQL);
            dbrs = DBHandler.execQueryResult(sQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                LLStockReporting objLLStockReporting = new LLStockReporting();
                
                objLLStockReporting.setPayroll(rs.getString(1));
                objLLStockReporting.setName(rs.getString(2));
                objLLStockReporting.setCommDate(rs.getDate(3));
                objLLStockReporting.setEntitle1(rs.getInt(4));
                objLLStockReporting.setEntitle2(rs.getInt(5));                                
                objLLStockReporting.setTakenMtd(rs.getInt(6));
                objLLStockReporting.setTakenYtd(rs.getInt(7));
                
                result.add(objLLStockReporting);
            }
            
            // empty table LL stock temporary table
            status = emptyTemp();
            rs.close();
        }
        catch(Exception e) {
            System.out.println("Exc when listLLStockReport : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
 
    
    /**
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int emptyTemp() {
        String tblDpReportName = "hr_ll_stock_temp";//HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "DELETE FROM " + tblDpReportName;
            
            System.out.println("emptyTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc emptyTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }         
    
    /**
     * get stock LL entitle per employee based on parameter in search object
     * and then insert them into temporary table called "HR_LL_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addEntitle1ToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_ll_stock_temp";//"HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
            ", SUM(LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+")" +
            ", 0" + // entitle 2   
            ", 0" + // entitle 3   
            ", 0" + // taken mtd
            ", 0" + // taken ytd
            " FROM "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT+" AS LL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonLLWhereClause(srcLeaveManagement); 
            String strEntCond  = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] + " = 1";            

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strEntCond!=null && strEntCond.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strEntCond;
                }
                else 
                {
                    whereClause = strEntCond;
                }
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addEntitle1ToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEntitle1ToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    /**
     * get stock LL entitle per employee based on parameter in search object
     * and then insert them into temporary table called "HR_LL_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addEntitle2ToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_ll_stock_temp";//"HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
            ", 0" + // entitle 1
            ", SUM(LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_LL_QTY]+")" +                        
            ", 0" + // entitle 1
            ", 0" + // taken mtd
            ", 0" + // taken ytd
            " FROM "+PstLLStockManagement.TBL_LL_STOCK_MANAGEMENT+" AS LL" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON LL."+PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID]+
            " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            
            String strCommCondition = genCommonLLWhereClause(srcLeaveManagement); 
            String strEntCond  = PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_ENTITLED] + " = 2";            

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strEntCond!=null && strEntCond.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strEntCond;
                }
                else 
                {
                    whereClause = strEntCond;
                }
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY LL." + PstLLStockManagement.fieldNames[PstLLStockManagement.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addEntitle2ToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addEntitle2ToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    /**
     * get taken MTD LL this year per employee based on parameter in search object
     * and then insert them into temporary table called "HR_AL_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addTakenMtdToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_ll_stock_temp";//"HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
            ", 0" + // entitle1
            ", 0" + // entitle2 
            ", 0" + // entitle3 
            ", SUM(TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]+")" +
            ", 0" + // taken ytd
            " FROM "+PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS TAKEN" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON TAKEN." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]+
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];                        
            
            String strCommCondition = genCommonLLWhereClause(srcLeaveManagement);                              
            
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);
                
                Date startDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+
                " BETWEEN \""+Formater.formatDate(startDtSelected,"yyyy-MM-dd")+"\"" + 
                " AND \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";                 
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }    
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addTakenMtdToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addTakenMtdToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    /**
     * get taken MTD LL this year per employee based on parameter in search object
     * and then insert them into temporary table called "HR_AL_STOCK_TEMP"
     *
     * @param srcLeaveManagement
     * created by Edhy
     */    
    synchronized public static int addTakenYtdToTemp(SrcLeaveManagement srcLeaveManagement) {
        String tblDpReportName = "hr_ll_stock_temp";;//"HR_LL_STOCK_TEMP";
        DBResultSet dbrs = null;
        int status = 0;
        try {
            String sQL = "INSERT INTO " + tblDpReportName +
            " SELECT EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
            ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+
            ", 0" + // entitle1
            ", 0" + // entitle2        
            ", 0" + // entitle3 
            ", 0" + // taken mtd
            ", SUM(TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_QTY]+")" +
            " FROM "+PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS TAKEN" +
            " INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP" +
            " ON TAKEN." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID]+
            " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];                        
            
            String strCommCondition = genCommonLLWhereClause(srcLeaveManagement);                              
            
            String strLeavePeriodCondition = "";
            if(srcLeaveManagement.getLeavePeriod() != null && !srcLeaveManagement.isPeriodChecked()) {
                Date selectedDate = srcLeaveManagement.getLeavePeriod();                                
                java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                cal.setTime(selectedDate);
                
                Date startDtSelected = new Date(selectedDate.getYear(), 0, 1);
                Date endDtSelected = new Date(selectedDate.getYear(), selectedDate.getMonth(), cal.getActualMaximum(cal.DAY_OF_MONTH));
                strLeavePeriodCondition = " TAKEN."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+
                " <= \""+Formater.formatDate(endDtSelected,"yyyy-MM-dd")+"\"";                 
            }

            String whereClause = "";
            if(strCommCondition!=null && strCommCondition.length()>0) {
                whereClause = strCommCondition;
            }            
            
            if(strLeavePeriodCondition!=null && strLeavePeriodCondition.length()>0) {
                if(whereClause!=null && whereClause.length()>0) {
                    whereClause = whereClause + " AND " + strLeavePeriodCondition;
                }
                else {
                    whereClause = strLeavePeriodCondition;
                }
            }
            
            if(whereClause != null && whereClause.length()>0) {
                sQL = sQL + " WHERE " + whereClause;
            }
            
            sQL = sQL + " GROUP BY TAKEN." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID];
            
            
            System.out.println("addTakenYtdToTemp SQL : " + sQL);
            status = DBHandler.execUpdate(sQL);
        }
        catch(Exception e) {
            System.out.println("Exc addTakenYtdToTemp : "+e.toString());
        }
        finally {
            DBResultSet.close(dbrs);
            return status;
        }
    }    
    
    
    /**
     * @param srcLeaveManagement
     * @return  
     */    
    public static String genCommonLLWhereClause(SrcLeaveManagement srcLeaveManagement)
    {
        String strNameCondition = "";
        if((srcLeaveManagement.getEmpName()!= null)&& (srcLeaveManagement.getEmpName().length()>0)) {
            Vector vectName = logicParser(srcLeaveManagement.getEmpName());
            if(vectName != null && vectName.size()>0) {
                strNameCondition = " (";
                for(int i = 0; i <vectName.size();i++) {
                    String str = (String)vectName.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNameCondition = strNameCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                        " LIKE '%"+str.trim()+"%' ";
                    }
                    else {
                        strNameCondition = strNameCondition + str.trim();
                    }
                }
                strNameCondition = strNameCondition + ")";
            }
        }

        String strNumCondition = "";
        if((srcLeaveManagement.getEmpNum()!= null)&& (srcLeaveManagement.getEmpNum().length()>0)) {
            Vector vectNum = logicParser(srcLeaveManagement.getEmpNum());
            if(vectNum != null && vectNum.size()>0) {
                strNumCondition = " (";
                for(int i = 0; i <vectNum.size();i++) {
                    String str = (String)vectNum.get(i);
                    if(!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        strNumCondition = strNumCondition + " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                        " LIKE '"+str.trim()+"%' ";
                    }
                    else {
                        strNumCondition = strNumCondition + str.trim();
                    }
                }
                strNumCondition = strNumCondition + ")";
            }
        }

        String strCategoryCondition = "";
        if(srcLeaveManagement.getEmpCatId() != 0) {
            strCategoryCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+
            " = "+srcLeaveManagement.getEmpCatId();
        }

        String strDepartmentCondition = "";
        if(srcLeaveManagement.getEmpDeptId() != 0) {
            strDepartmentCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+
            " = "+srcLeaveManagement.getEmpDeptId();
        }

        String strSectionCondition = "";
        if(srcLeaveManagement.getEmpSectionId() != 0) {
            strSectionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpSectionId();
        }

        String strPositionCondition = "";
        if(srcLeaveManagement.getEmpPosId() != 0) {
            strPositionCondition = " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+
            " = "+srcLeaveManagement.getEmpPosId();
        }

        String whereClause = "";
        if(strNameCondition!=null && strNameCondition.length()>0) {
            whereClause = strNameCondition;
        }

        if(strNumCondition!=null && strNumCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strNumCondition;
            }
            else {
                whereClause = strNumCondition;
            }
        }

        if(strCategoryCondition!=null && strCategoryCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strCategoryCondition;
            }
            else {
                whereClause = strCategoryCondition;
            }
        }


        if(strDepartmentCondition!=null && strDepartmentCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strDepartmentCondition;
            }
            else {
                whereClause = strDepartmentCondition;
            }
        }

        if(strSectionCondition!=null && strSectionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strSectionCondition;
            }
            else {
                whereClause = strSectionCondition;
            }
        }

        if(strPositionCondition!=null && strPositionCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strPositionCondition;
            }
            else {
                whereClause = strPositionCondition;
            }
        }
        
        return whereClause;
    }
    // ------------------ End AL reporting -------------------
    
    
    
    public static void main(String args[])
    {
        SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();
        srcLeaveManagement.setPeriodChecked(false);
        srcLeaveManagement.setLeavePeriod(new Date());
        
        Vector vectResult = listLLStockReport(srcLeaveManagement);
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxResult = vectResult.size();
            for(int i=0; i<maxResult; i++)
            {
                LLStockReporting objLLStockReporting = (LLStockReporting) vectResult.get(i);
                System.out.println("Payroll   : " + objLLStockReporting.getPayroll());
                System.out.println("Name      : " + objLLStockReporting.getName());
                System.out.println("Date      : " + objLLStockReporting.getCommDate());
                System.out.println("Entitle1  : " + objLLStockReporting.getEntitle1());
                System.out.println("Entitle2  : " + objLLStockReporting.getEntitle2());                
                System.out.println("Tk Mtd    : " + objLLStockReporting.getTakenMtd());
                System.out.println("Tk Ytd    : " + objLLStockReporting.getTakenYtd());
                System.out.println("");
            }
        }
        else
        {
            System.out.println("Result null ...");
        }
        
    }
}
