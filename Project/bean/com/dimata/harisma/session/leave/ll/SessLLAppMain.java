/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave.ll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.ll.LLAppMain;
import com.dimata.harisma.entity.leave.ll.PstLLAppMain;
import com.dimata.harisma.entity.search.SrcLeaveApplication;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessLLAppMain {
    /**
     * Mencari ll app main dari employee pada suatu tanggal
     * @param employeeId : OID employeee
     * @param data : tanggal acuan
     * @return LLAppMain
     * @author  Artha
     */
    public static LLAppMain getLLAppMain(long employeeId, Date date){
        LLAppMain llAppMain = new LLAppMain();
        String whereClause = PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                +" = "+employeeId
                +" AND "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]
                +" <= '"+Formater.formatDate(date, "yyyy-MM-dd")+"'"
                +" AND "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                +" >= '"+Formater.formatDate(date, "yyyy-MM-dd")+"'"
                ;
        Vector vLLAppMain = new Vector(1,1);
        vLLAppMain = PstLLAppMain.list(0, 0, whereClause, null);
        if(vLLAppMain.size()>0){
            llAppMain = (LLAppMain)vLLAppMain.get(0);
        }
        return llAppMain;
    }
    
    /**
     * Mencari ll app main dari employee pada suatu tanggal
     * @param data : tanggal acuan
     * @return LLAppMain
     * @author  Artha
     */
    public static Vector listLLAppMain(Date date){
        Vector vLLAppMain = new Vector(1,1);
        String whereClause =PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]
                +" <= '"+Formater.formatDate(date, "yyyy-MM-dd")+"'"
                +" AND "+PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                +" >= '"+Formater.formatDate(date, "yyyy-MM-dd")+"'"
                ;
        vLLAppMain = PstLLAppMain.list(0, 0, whereClause, null);
        return vLLAppMain;
    }
    
    /***
     * Mencari Dp app main
     */
    public static synchronized Vector listLLAppMain(int start, int recordToGet,SrcLeaveApplication srcLeave){
        Vector vList = new Vector(1,1);
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAIN."+ PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_BALANCE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_ID]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_ID]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_DOC_STATUS]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_REQUEST_QTY]
                    +" ,MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_TAKEN_QTY]
                    +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    +" ,EMP1."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED1"
                    +" ,EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED2"
                    +" ,EMP3."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED3"
                    + " FROM "+PstLLAppMain.TBL_LL_APP_MAIN+" AS MAIN "
                    +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                    +" ON MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                    +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP1 "
                    +" ON MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID]
                    +" = EMP1."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP2 "
                    +" ON MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_ID]
                    +" = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP3 "
                    +" ON MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_ID]
                    +" = EMP3."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    ;
            
            String whereClause = "";
            //Cari berdasarkan nama employee
            if(srcLeave.getFullName()!=null && srcLeave.getFullName().length()>0){
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        +" LIKE '%"+srcLeave.getFullName()+"%'";
            }
            //Cari berdasarkan number payroll
            if(srcLeave.getEmpNum()!=null && srcLeave.getEmpNum().length()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        +" LIKE '%"+srcLeave.getEmpNum()+"%'";
            }
            //Cari berdasarkan department
            if(srcLeave.getDepartmentId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        +" = "+srcLeave.getDepartmentId();
            }
            //Cari berdasarkan section
            if(srcLeave.getSectionId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        +" = "+srcLeave.getSectionId();
            }
            //Cari berdasarkan position
            if(srcLeave.getPositionId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        +" = "+srcLeave.getPositionId();
            }
            //Cari berdasarkan submission date
            String strSubmission = "";
            if ( !srcLeave.isSubmission() ) 
            {
                if( (srcLeave.getSubmissionDate()!=null) && (srcLeave.getSubmissionDate()!=null) )
                {
                    String strSubmissionDate = Formater.formatDate(srcLeave.getSubmissionDate(),"yyyy-MM-dd");
                    if(whereClause.length()>0){whereClause+= " AND ";}                                        
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE] +
                                    " = \"" + strSubmissionDate + "\"";                    
                }
            }
            
            //Cari berdasarkan taken date
            String strTaken = "";
            if ( !srcLeave.isTaken() ) 
            {
                if( (srcLeave.getTakenDate()!=null) && (srcLeave.getTakenDate()!=null) )
                {
                    String strTakenDate = Formater.formatDate(srcLeave.getTakenDate(),"yyyy-MM-dd");
                    if(whereClause.length()>0){whereClause+= " AND ";}                                        
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]
                               +" <= \"" + strTakenDate + "\""
                               +" AND MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                               +" >= \"" + strTakenDate + "\""
                               ;                    
                }
            }
            //Cari berdasarkan status dokument
            if ( srcLeave.getStatus() > -1 ) 
            {                
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " MAIN."+ PstLLAppMain.fieldNames[PstLLAppMain.FLD_DOC_STATUS]+
                            " = " + srcLeave.getStatus();                    
            }
            //Cari berdasarkanapakah sudah di approve apa belum
            switch(srcLeave.getApprovalStatus())
            {
                case 0 : 
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID] + " = 0";
                    break;
                    
                case 1 :
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID] + " > 0";                    
                    break;
                    
                default : 
                    break;    
            } 
            
            //Manambahkan where clause
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
            //Untuk order dan group data
            sql += " GROUP BY MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]
                    +" ORDER BY MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE];
            
            //Menambahkan limit
            if(!(start==0 && recordToGet==0)){
                sql += " LIMIT "+start+","+recordToGet;
            }
            
          //  System.out.println("\tSessLLAppMain.listLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { 
            //Dp App Main
                LLAppMain objLLAppMain = new LLAppMain();
                objLLAppMain.setOID(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]));
                objLLAppMain.setEmployeeId(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]));
                objLLAppMain.setSubmissionDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE]));
                objLLAppMain.setBalance(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_BALANCE]));
                objLLAppMain.setApprovalId(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID]));
                objLLAppMain.setApproval2Id(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_ID]));
                objLLAppMain.setApproval3Id(rs.getLong(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_ID]));
                objLLAppMain.setApprovalDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_DATE]));
                objLLAppMain.setApproval2Date(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL2_DATE]));
                objLLAppMain.setApproval3Date(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL3_DATE]));
                objLLAppMain.setDocumentStatus(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_DOC_STATUS]));
                objLLAppMain.setStartDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]));
                objLLAppMain.setEndDate(rs.getDate(PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]));
                objLLAppMain.setRequestQty(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_REQUEST_QTY]));
                objLLAppMain.setTakenQty(rs.getInt(PstLLAppMain.fieldNames[PstLLAppMain.FLD_TAKEN_QTY]));
                                
            //Employee
                Employee objEmp = new Employee();
                objEmp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                objEmp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                
            //Approved 1
                Employee objApp1 = new Employee();
                objApp1.setFullName(rs.getString("APPROVED1"));
                
            //Approved 2
                Employee objApp2 = new Employee();
                objApp2.setFullName(rs.getString("APPROVED2"));
                
            //Approved 3
                Employee objApp3 = new Employee();
                objApp3.setFullName(rs.getString("APPROVED3"));
            
                Vector vTemp = new Vector(1,1);
                vTemp.add(objLLAppMain);
                vTemp.add(objEmp);
                vTemp.add(objApp1);
                vTemp.add(objApp2);
                vTemp.add(objApp3);
                
                vList.add(vTemp);
                
            }
            
            rs.close();
            return vList;
        }catch(Exception e) {
            return new Vector(1,1);
        }finally {
            DBResultSet.close(dbrs);
            return vList;
        }
    }
    
    /***
     * Mencari Dp app main
     */
    public static synchronized int countLLAppMain(SrcLeaveApplication srcLeave){
        Vector vList = new Vector(1,1);
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(MAIN."+ PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]+")"
                    + " FROM "+PstLLAppMain.TBL_LL_APP_MAIN+" AS MAIN "
                    +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                    +" ON MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_EMPLOYEE_ID]
                    +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    ;
          String whereClause = "";
            //Cari berdasarkan nama employee
            if(srcLeave.getFullName()!=null && srcLeave.getFullName().length()>0){
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        +" LIKE '%"+srcLeave.getFullName()+"%'";
            }
            //Cari berdasarkan number payroll
            if(srcLeave.getEmpNum()!=null && srcLeave.getEmpNum().length()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        +" LIKE '%"+srcLeave.getEmpNum()+"%'";
            }
            //Cari berdasarkan department
            if(srcLeave.getDepartmentId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                        +" = "+srcLeave.getDepartmentId();
            }
            //Cari berdasarkan section
            if(srcLeave.getSectionId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        +" = "+srcLeave.getSectionId();
            }
            //Cari berdasarkan position
            if(srcLeave.getPositionId()>0){
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " EMP."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        +" = "+srcLeave.getPositionId();
            }
            //Cari berdasarkan submission date
            String strSubmission = "";
            if ( !srcLeave.isSubmission() ) 
            {
                if( (srcLeave.getSubmissionDate()!=null) && (srcLeave.getSubmissionDate()!=null) )
                {
                    String strSubmissionDate = Formater.formatDate(srcLeave.getSubmissionDate(),"yyyy-MM-dd");
                    if(whereClause.length()>0){whereClause+= " AND ";}                                        
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE] +
                                    " = \"" + strSubmissionDate + "\"";                    
                }
            }
            
             //Cari berdasarkan taken date
            String strTaken = "";
            if ( !srcLeave.isTaken() ) 
            {
                if( (srcLeave.getTakenDate()!=null) && (srcLeave.getTakenDate()!=null) )
                {
                    String strTakenDate = Formater.formatDate(srcLeave.getTakenDate(),"yyyy-MM-dd");
                    if(whereClause.length()>0){whereClause+= " AND ";}                                        
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_START_DATE]
                               +" <= \"" + strTakenDate + "\""
                               +" AND MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_END_DATE]
                               +" >= \"" + strTakenDate + "\""
                               ;                    
                }
            }
            //Cari berdasarkan status dokument
            if ( srcLeave.getStatus() > -1 ) 
            {                
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " MAIN."+ PstLLAppMain.fieldNames[PstLLAppMain.FLD_DOC_STATUS]+
                            " = " + srcLeave.getStatus();                    
            }
            //Cari berdasarkanapakah sudah di approve apa belum
            switch(srcLeave.getApprovalStatus())
            {
                case 0 : 
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID] + " = 0";
                    break;
                    
                case 1 :
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstLLAppMain.fieldNames[PstLLAppMain.FLD_APPROVAL_ID] + " > 0";                    
                    break;
                    
                default : 
                    break;    
            } 
            
            //Manambahkan where clause
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
            //Untuk order dan group data
            sql += " GROUP BY MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_LL_APP_ID]
                    +" ORDER BY MAIN."+PstLLAppMain.fieldNames[PstLLAppMain.FLD_SUBMISSION_DATE];
            
            //Menambahkan limit
            
        //    System.out.println("\tSessLLAppMain.countLLAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { 
            //Dp App Main
                count =  rs.getInt(1);
                
            }
            
            rs.close();
            return count;
        }catch(Exception e) {
            return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
    }
    
    
}
