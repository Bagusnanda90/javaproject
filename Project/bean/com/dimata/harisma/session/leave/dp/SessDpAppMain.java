/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave.dp;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.PstDPUpload;
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.leave.dp.PstDpAppDetail;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.harisma.entity.search.SrcLeaveApplication;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessDpAppMain {
    /***
     * Mencari Dp app main
     */
    public static synchronized Vector listDpAppMain(int start, int recordToGet,SrcLeaveApplication srcLeave){
        Vector vList = new Vector(1,1);
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAIN."+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_BALANCE]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_DATE]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_DATE]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_DATE]
                    +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]
                    +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    +" ,EMP1."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED1"
                    +" ,EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED2"
                    +" ,EMP3."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" AS APPROVED3"
                    + " FROM "+PstDpAppMain.TBL_DP_APP_MAIN+" AS MAIN "
                    +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                    +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP1 "
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]
                    +" = EMP1."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP2 "
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]
                    +" = EMP2."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP3 "
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]
                    +" = EMP3."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstDpAppDetail.TBL_DP_APP_DETAIL+" AS DETAIL"
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    +" = DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
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
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE] +
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
                    whereClause += " DETAIL." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE] +
                               " = \"" + strTakenDate + "\"";                    
                }
            }
            //Cari berdasarkan status dokument
            if ( srcLeave.getStatus() > -1 ) 
            {                
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " MAIN."+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]+
                            " = " + srcLeave.getStatus();                    
            }
            //Cari berdasarkanapakah sudah di approve apa belum
            switch(srcLeave.getApprovalStatus())
            {
                case 0 : 
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID] + " = 0";
                    break;
                    
                case 1 :
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID] + " > 0";                    
                    break;
                    
                default : 
                    break;    
            } 
            
            //Manambahkan where clause
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
            //Untuk order dan group data
            sql += " GROUP BY MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    +" ORDER BY MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE];
            
            //Menambahkan limit
            if(!(start==0 && recordToGet==0)){
                sql += " LIMIT "+start+","+recordToGet;
            }
            
//            System.out.println("\tSessDpAppMain.listDpAppMain : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { 
            //Dp App Main
                DpAppMain objDpAppMain = new DpAppMain();
                objDpAppMain.setOID(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]));
                objDpAppMain.setEmployeeId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]));
                objDpAppMain.setSubmissionDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]));
                objDpAppMain.setBalance(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_BALANCE]));
                objDpAppMain.setApprovalId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]));
                objDpAppMain.setApproval2Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]));
                objDpAppMain.setApproval3Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]));
                objDpAppMain.setApprovalDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_DATE]));
                objDpAppMain.setApproval2Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_DATE]));
                objDpAppMain.setApproval3Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_DATE]));
                objDpAppMain.setDocumentStatus(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]));
                
            //List Dp app detail
                Vector vDpAppDetail = new Vector(1,1);
                vDpAppDetail = SessDpAppDetail.listDpAppDetail(objDpAppMain.getOID());
                
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
                vTemp.add(objDpAppMain);
                vTemp.add(vDpAppDetail);
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
    public static synchronized int countDpAppMain(SrcLeaveApplication srcLeave){
        Vector vList = new Vector(1,1);
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(MAIN."+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]+")"
                    + " FROM "+PstDpAppMain.TBL_DP_APP_MAIN+" AS MAIN "
                    +" INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE+" AS EMP "
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                    +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" LEFT JOIN "+PstDpAppDetail.TBL_DP_APP_DETAIL+" AS DETAIL"
                    +" ON MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    +" = DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
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
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE] +
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
                    whereClause += " DETAIL." + PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE] +
                               " = \"" + strTakenDate + "\"";                    
                }
            }
            //Cari berdasarkan status dokument
            if ( srcLeave.getStatus() > -1 ) 
            {                
                if(whereClause.length()>0){whereClause+= " AND ";}
                whereClause += " MAIN."+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]+
                            " = " + srcLeave.getStatus();                    
            }
            //Cari berdasarkanapakah sudah di approve apa belum
            switch(srcLeave.getApprovalStatus())
            {
                case 0 : 
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID] + " = 0";
                    break;
                    
                case 1 :
                    if(whereClause.length()>0){whereClause+= " AND ";}
                    whereClause += " MAIN." + PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID] + " > 0";                    
                    break;
                    
                default : 
                    break;    
            } 
            
            //Manambahkan where clause
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
            //Untuk order dan group data
            sql += " GROUP BY MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                    +" ORDER BY MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE];
            
            //Menambahkan limit
            
        //    System.out.println("\tSessDpAppMain.countDpAppMain : " + sql);
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
