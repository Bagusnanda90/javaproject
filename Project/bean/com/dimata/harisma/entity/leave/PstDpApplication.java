/*
 * PstDpApplication.java
 *
 * Created on October 21, 2004, 12:05 PM
 */

package com.dimata.harisma.entity.leave;

import com.dimata.qdep.db.*; 
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language; 
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.ScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class PstDpApplication  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_APPLICATION = "hr_dp_application";//"HR_DP_APPLICATION";
    
    public static final int FLD_DP_APPLICATION_ID = 0;    
    public static final int FLD_SUBMISSION_DATE = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_DP_ID = 3;
    public static final int FLD_TAKEN_DATE = 4;
    public static final int FLD_APPROVAL_ID = 5;
    public static final int FLD_DOC_STATUS = 6;
    public static final int FLD_BALANCE = 7;
    public static final int FLD_NEW_BALANCE = 8;
    
    public static final String[] fieldNames = {
        "DP_APPLICATION_ID",
        "SUBMISSION_DATE",
        "EMPLOYEE_ID",
        "DP_ID",
        "TAKEN_DATE",
        "APPROVAL_ID",
        "DOC_STATUS",
        "BALANCE",
        "NEW_BALANCE"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,        
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };
    
    public static final int FLD_DOC_STATUS_VALID = 0;
    public static final int FLD_DOC_STATUS_INVALID = 1;    
    public static final int FLD_DOC_STATUS_INCOMPLATE = 2;
    
   //public static final int FLD_STATUS_EXPIRED = 0;
   //public static final int FLD_STATUS_EXTENDED = 1;
    
    public static final String[] fieldStatusNames = {
        "Valid",
        "Invalid",
        "Incomplate"
    };    
    
    public static final int FLD_NOT_APPROVED = 0;
    public static final int FLD_APPROVE_BY_DEPT_HEAD = 1;    
    public static final String[] fieldApprovalStatusNames = {
        "Not Approved",
        "Approve by Department Head"        
    };    
    
    public PstDpApplication() {
    }
    
    public PstDpApplication(int i) throws DBException {
        super(new PstDpApplication());
    }
    
    public PstDpApplication(String sOid) throws DBException {
        super(new PstDpApplication(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpApplication(long lOid) throws DBException {
        super(new PstDpApplication(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_DP_APPLICATION;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDpApplication().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DpApplication objDpApplication = fetchExc(ent.getOID());
        return objDpApplication.getOID();
    }
    
    public static DpApplication fetchExc(long oid) throws DBException {
        try {
            DpApplication objDpApplication = new DpApplication();
            PstDpApplication objPstDpApplication = new PstDpApplication(oid);
            objDpApplication.setOID(oid);
            
            objDpApplication.setSubmissionDate(objPstDpApplication.getDate(FLD_SUBMISSION_DATE));            
            objDpApplication.setEmployeeId(objPstDpApplication.getlong(FLD_EMPLOYEE_ID));
            objDpApplication.setDpId(objPstDpApplication.getlong(FLD_DP_ID));
            objDpApplication.setTakenDate(objPstDpApplication.getDate(FLD_TAKEN_DATE));            
            objDpApplication.setApprovalId(objPstDpApplication.getlong(FLD_APPROVAL_ID));            
            objDpApplication.setDocStatus(objPstDpApplication.getInt(FLD_DOC_STATUS));      
            objDpApplication.setBalance(objPstDpApplication.getInt(FLD_BALANCE));      
            objDpApplication.setNewBalance(objPstDpApplication.getInt(FLD_NEW_BALANCE));      
            
            return objDpApplication;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpApplication(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpApplication) ent);
    }
    
    public static long updateExc(DpApplication objDpApplication) throws DBException {
        try {
            if (objDpApplication.getOID() != 0) {
                PstDpApplication objPstDpApplication = new PstDpApplication(objDpApplication.getOID());
                
                objPstDpApplication.setDate(FLD_SUBMISSION_DATE, objDpApplication.getSubmissionDate());
                objPstDpApplication.setLong(FLD_EMPLOYEE_ID, objDpApplication.getEmployeeId());
                objPstDpApplication.setLong(FLD_DP_ID, objDpApplication.getDpId());                
                objPstDpApplication.setDate(FLD_TAKEN_DATE, objDpApplication.getTakenDate());
                objPstDpApplication.setLong(FLD_APPROVAL_ID, objDpApplication.getApprovalId());                
                objPstDpApplication.setInt(FLD_DOC_STATUS, objDpApplication.getDocStatus());                
                objPstDpApplication.setInt(FLD_BALANCE, objDpApplication.getBalance());                
                objPstDpApplication.setInt(FLD_NEW_BALANCE, objDpApplication.getNewBalance());                
                
                objPstDpApplication.update();
                return objDpApplication.getOID();                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpApplication(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDpApplication objPstDpApplication = new PstDpApplication(oid);
            objPstDpApplication.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpApplication(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DpApplication)ent);
    }
    
    public static long insertExc(DpApplication objDpApplication) throws DBException {
        try {
            PstDpApplication objPstDpApplication = new PstDpApplication(0);
            
            objPstDpApplication.setDate(FLD_SUBMISSION_DATE, objDpApplication.getSubmissionDate());            
            objPstDpApplication.setLong(FLD_EMPLOYEE_ID, objDpApplication.getEmployeeId());
            objPstDpApplication.setLong(FLD_DP_ID, objDpApplication.getDpId());
            objPstDpApplication.setDate(FLD_TAKEN_DATE, objDpApplication.getTakenDate());            
            objPstDpApplication.setLong(FLD_APPROVAL_ID, objDpApplication.getApprovalId());
            objPstDpApplication.setInt(FLD_DOC_STATUS, objDpApplication.getDocStatus());                
            objPstDpApplication.setInt(FLD_BALANCE, objDpApplication.getBalance());                
            objPstDpApplication.setInt(FLD_NEW_BALANCE, objDpApplication.getNewBalance());                
            
            objPstDpApplication.insert();
            objDpApplication.setOID(objPstDpApplication.getlong(FLD_DP_APPLICATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpApplication(0), DBException.UNKNOWN);
        }
        return objDpApplication.getOID();
    }
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DP_APPLICATION;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;            
//            System.out.println("sql : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpApplication objDpApplication = new DpApplication();
                resultToObject(rs, objDpApplication);
                lists.add(objDpApplication);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();   
    }
    
    private static void resultToObject(ResultSet rs, DpApplication objDpApplication) {
        try {
            objDpApplication.setOID(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_DP_APPLICATION_ID]));
            objDpApplication.setSubmissionDate(rs.getDate(PstDpApplication.fieldNames[PstDpApplication.FLD_SUBMISSION_DATE]));
            objDpApplication.setEmployeeId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID]));
            objDpApplication.setDpId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID]));
            objDpApplication.setTakenDate(rs.getDate(PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE]));
            objDpApplication.setApprovalId(rs.getLong(PstDpApplication.fieldNames[PstDpApplication.FLD_APPROVAL_ID]));
            objDpApplication.setDocStatus(rs.getInt(PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS]));
            objDpApplication.setBalance(rs.getInt(PstDpApplication.fieldNames[PstDpApplication.FLD_BALANCE]));
            objDpApplication.setNewBalance(rs.getInt(PstDpApplication.fieldNames[PstDpApplication.FLD_BALANCE]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+ PstDpApplication.fieldNames[PstDpApplication.FLD_DP_APPLICATION_ID] + ") FROM " + TBL_DP_APPLICATION;
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;  
            
//            System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }
            
            rs.close();
            return count;
        }catch(Exception e) {
            return 0;
        }finally {
            DBResultSet.close(dbrs);
        }
    }
    
    /**
     * @param dpTakenDate
     * @param employeeId
     * @return
     */    
    public static DpApplication getDpApplicationBySchedule(Date dpTakenDate, long employeeId)
    {
        DpApplication objDpApplication = new DpApplication();
        
        String strTakenDate = Formater.formatDate(dpTakenDate, "yyyy-MM-dd");
        String whereClause = PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId + 
                             " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + 
                             " = \"" + strTakenDate + "\"" +
                             " AND (" + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + 
                             " = " + PstDpApplication.FLD_DOC_STATUS_VALID + 
                             " OR " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + 
                             " = " + PstDpApplication.FLD_DOC_STATUS_INCOMPLATE + ")"; 
        
        Vector vectDpAplication = PstDpApplication.list(0, 0, whereClause, "");
        if(vectDpAplication!=null && vectDpAplication.size()>0)
        {
            int maxDpApplication = vectDpAplication.size();
            for(int i=0; i<maxDpApplication; i++)
            {
                objDpApplication = (DpApplication) vectDpAplication.get(i);
                break;
            }
        }
        
        return objDpApplication; 
    }
    

    /**
     * @param dpTakenDate
     * @param employeeId
     * @return
     */    
    public static long getDpApplicationIdBySchedule(Date dpTakenDate, long employeeId)
    {
        long result = 0;
        
        String strTakenDate = Formater.formatDate(dpTakenDate, "yyyy-MM-dd");
        String whereClause = PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID] + 
                             " = " + employeeId + 
                             " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + 
                             " = \"" + strTakenDate + "\"" +
                             " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + 
                             " = " + PstDpApplication.FLD_DOC_STATUS_VALID; 
        
        Vector vectDpAplication = PstDpApplication.list(0, 0, whereClause, "");
        if(vectDpAplication!=null && vectDpAplication.size()>0)
        {
            int maxDpApplication = vectDpAplication.size();
            for(int i=0; i<maxDpApplication; i++)
            {                
                DpApplication objDpApplication = (DpApplication) vectDpAplication.get(i);
                result = objDpApplication.getOID();
                break;
            }
        }
        
        return result; 
    }    
    
    
    /**
     * @param employeeOid
     * @param submissionDate
     * @param takenDate
     * @param dpStockOid
     * @return
     */    
    public static long generateDpApplication(long employeeOid, Date submissionDate, Date takenDate, long dpStockOid)
    {
        long result = 0;
        
        DpApplication objDpApplication = new DpApplication();
        objDpApplication.setEmployeeId(employeeOid);
        objDpApplication.setSubmissionDate(submissionDate);        
        objDpApplication.setTakenDate(takenDate);
        objDpApplication.setDpId(dpStockOid);        
        objDpApplication.setDocStatus(PstDpApplication.FLD_DOC_STATUS_VALID);               
        objDpApplication.setApprovalId(0);
        objDpApplication.setBalance(0);
        objDpApplication.setNewBalance(0);
        
        try
        {
            result = PstDpApplication.insertExc(objDpApplication);
        }
        catch(Exception e)
        {
            System.out.println("Exc when generate DpApplication : " + e.toString());
        }
        
        return result;
    }


    /**
     * @param objEmpSchedule
     * @return
     */    
    public static int generateDpApplication(EmpSchedule objEmpSchedule)
    {
        int result = 0;
        
        
        // generate tanggal awal periode
        Date startPeriodDate = null;
        try
        {            
            Period objPeriod = PstPeriod.fetchExc(objEmpSchedule.getPeriodId());
            startPeriodDate = objPeriod.getStartDate();
        }
        catch(Exception e)
        {
            System.out.println("Exc : " + e.toString());
        }
        
        // get list DP stock for this employee
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                             " = " + objEmpSchedule.getEmployeeId() + 
                             " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                             " = " + PstDpStockManagement.DP_STS_AKTIF;
        String orderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector listOfDPStock = new Vector(1,1);
        Vector vectTemp = PstDpStockManagement.list(0, 0, whereClause, orderBy);
        if(vectTemp!=null && vectTemp.size()>0)
        {
            int maxDpStock = vectTemp.size();
            for(int i=0; i<maxDpStock; i++)
            {
                DpStockManagement objDpStockManagement = (DpStockManagement) vectTemp.get(i);
                if(objDpStockManagement.getQtyResidue() > 0)
                {
                    float dpResidue = objDpStockManagement.getQtyResidue();
                    for(int j=0; j<dpResidue; j++)
                    {
                        objDpStockManagement.setiDpQty(1);
                        listOfDPStock.add(objDpStockManagement);
                    }
                }
            }
        }
        
        // proses schedule dari tanggal 01 - 31
        long[] arrObjEmpSchedule = 
        {
          objEmpSchedule.getD1(),  
          objEmpSchedule.getD2(),
          objEmpSchedule.getD3(),
          objEmpSchedule.getD4(),
          objEmpSchedule.getD5(),
          objEmpSchedule.getD6(),
          objEmpSchedule.getD7(),
          objEmpSchedule.getD8(),
          objEmpSchedule.getD9(),
          objEmpSchedule.getD10(),
          objEmpSchedule.getD11(),
          objEmpSchedule.getD12(),
          objEmpSchedule.getD13(),
          objEmpSchedule.getD14(),
          objEmpSchedule.getD15(),
          objEmpSchedule.getD16(),
          objEmpSchedule.getD17(),
          objEmpSchedule.getD18(),
          objEmpSchedule.getD19(),
          objEmpSchedule.getD20(),
          objEmpSchedule.getD21(),
          objEmpSchedule.getD22(),
          objEmpSchedule.getD23(),
          objEmpSchedule.getD24(),
          objEmpSchedule.getD25(),
          objEmpSchedule.getD26(),
          objEmpSchedule.getD27(),
          objEmpSchedule.getD28(),
          objEmpSchedule.getD29(),
          objEmpSchedule.getD30(),
          objEmpSchedule.getD31()
        };
        
        int maxSchedule = arrObjEmpSchedule.length;
        for(int i=0; i<maxSchedule; i++)
        {                    
            long scheduleOid = arrObjEmpSchedule[i];
            int scheduleCategory = PstScheduleSymbol.getCategoryType(scheduleOid);

            if(scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)    
            {                              
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i+1);
                long dpStockOid = 0;
                if(listOfDPStock!=null && listOfDPStock.size()>0)
                {
                    DpStockManagement objDpStock = (DpStockManagement) listOfDPStock.get(0);
                    dpStockOid = objDpStock.getOID();
                }
                
                DpApplication objDpApplication = new DpApplication();
                objDpApplication.setEmployeeId(objEmpSchedule.getEmployeeId());
                objDpApplication.setSubmissionDate(new Date());        
                objDpApplication.setTakenDate(takenDate);
                objDpApplication.setDpId(dpStockOid);                        
                objDpApplication.setDocStatus(dpStockOid != 0 ? PstDpApplication.FLD_DOC_STATUS_VALID : PstDpApplication.FLD_DOC_STATUS_INCOMPLATE);               
                objDpApplication.setApprovalId(0);               
                objDpApplication.setBalance(0);               
                objDpApplication.setNewBalance(0);               

                try
                {
                    long oidResult = PstDpApplication.insertExc(objDpApplication);
                    result++;
                }
                catch(Exception e)
                {
                    System.out.println("Exc when generate DpApplication : " + e.toString());
                }
                
                // hapus data DpStockOID dari vector pada index ke 0
                if(listOfDPStock!=null && listOfDPStock.size()>0)
                {
                    listOfDPStock.remove(0);
                }
            }
        }
        
        return result;
    }
    

    /**
     * @param objEmpSchedule
     * @return
     */       
    public static int updateDpApplication(EmpSchedule objEmpSchedule)
    {
        int result = 0;        
        
        // generate tanggal awal periode
        Date startPeriodDate = null;
        try
        {            
            Period objPeriod = PstPeriod.fetchExc(objEmpSchedule.getPeriodId());
            startPeriodDate = objPeriod.getStartDate();
        }
        catch(Exception e)
        {
            System.out.println("Exc : " + e.toString());
        }
        
        
        // mencari stock DP terakhir yang diambil oleh dokumen DP Application sebelum "current period"
        // data ini akan digunakan utk menghitung jumlah stock Dp yang semestinya bisa diambil pada "current period"
        long oidDpStockOnLastDpAppDoc = 0;
        int usedQtyOfThisDPStock = 0;        
        String selectedDate = "\"" + Formater.formatDate(startPeriodDate, "yyyy-MM-dd") + "\"";
        String whLastDpStockUsed = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + objEmpSchedule.getEmployeeId() +
                                   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + " < " + selectedDate +         
                                   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID] + " <> 0" + 
                                   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + " = " + PstDpApplication.FLD_DOC_STATUS_VALID;
        String ordLastDpStockUsed = PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + " DESC";
        Vector vectDpAppLast = PstDpApplication.list(0, 0, whLastDpStockUsed, ordLastDpStockUsed);
        if(vectDpAppLast!=null && vectDpAppLast.size()>0)
        {
            DpApplication objDpApplication = (DpApplication) vectDpAppLast.get(0);
            oidDpStockOnLastDpAppDoc = objDpApplication.getDpId();

            String whCls = PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID] + "=" + oidDpStockOnLastDpAppDoc + 
                           " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + " = " + PstDpApplication.FLD_DOC_STATUS_VALID;
            if(oidDpStockOnLastDpAppDoc!=0)
            {
                usedQtyOfThisDPStock = PstDpApplication.getCount(whCls);
            }	            
        }        
        
        
        // get list/perhitungan DP stock for this employee pada "current period"
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + objEmpSchedule.getEmployeeId() + 
                             " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +                             
                             " IN (" + PstDpStockManagement.DP_STS_AKTIF + "," + PstDpStockManagement.DP_STS_TAKEN + "," + PstDpStockManagement.DP_STS_EXPIRED + ")";        
        String orderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector listOfDPStock = new Vector(1,1);
        Vector vectTemp = PstDpStockManagement.list(0, 0, whereClause, orderBy);        
        if(vectTemp!=null && vectTemp.size()>0)
        {
            // masukkan masing-masing object DpStockManagement ke vector dengan ketentuan, jika QtyDP lebih dari 1, 
            // maka akan dibuatkan object yang sejenis sejumlah QtyDP tersebut 
            int maxTemp = vectTemp.size();
            boolean sameDpOidHasFound = false;
            for(int i=0; i<maxTemp; i++)
            {
                DpStockManagement objDpStockManagement = (DpStockManagement) vectTemp.get(i);		
                if( (oidDpStockOnLastDpAppDoc == 0) || (oidDpStockOnLastDpAppDoc == objDpStockManagement.getOID()) || sameDpOidHasFound)
                {
                    if(objDpStockManagement.getiDpQty() > 0)
                    {			
                        sameDpOidHasFound = true;
                        float dpQty = objDpStockManagement.getiDpQty();
                        for(int j=0; j<dpQty; j++)
                        {
                            objDpStockManagement.setiDpQty(1);
                            listOfDPStock.add(objDpStockManagement);
                        }
                    }
                }			
            }            
            
            // perhitungan utk mendapatkan DP Stock yang seharusnya harus diambil (FIFO)	
            if(listOfDPStock!=null && listOfDPStock.size()>0)
            {
                int lstDpSize = listOfDPStock.size();
                int maxRemoveTop = (usedQtyOfThisDPStock > lstDpSize) ? lstDpSize : usedQtyOfThisDPStock;		
                for(int it=0; it<maxRemoveTop; it++)
                {
                    listOfDPStock.remove(0);
                }		
            }
        }       
                
        
        // proses schedule dari tanggal 01 - 31
        long[] arrObjEmpSchedule = 
        {
          objEmpSchedule.getD1(),  
          objEmpSchedule.getD2(),
          objEmpSchedule.getD3(),
          objEmpSchedule.getD4(),
          objEmpSchedule.getD5(),
          objEmpSchedule.getD6(),
          objEmpSchedule.getD7(),
          objEmpSchedule.getD8(),
          objEmpSchedule.getD9(),
          objEmpSchedule.getD10(),
          objEmpSchedule.getD11(),
          objEmpSchedule.getD12(),
          objEmpSchedule.getD13(),
          objEmpSchedule.getD14(),
          objEmpSchedule.getD15(),
          objEmpSchedule.getD16(),
          objEmpSchedule.getD17(),
          objEmpSchedule.getD18(),
          objEmpSchedule.getD19(),
          objEmpSchedule.getD20(),
          objEmpSchedule.getD21(),
          objEmpSchedule.getD22(),
          objEmpSchedule.getD23(),
          objEmpSchedule.getD24(),
          objEmpSchedule.getD25(),
          objEmpSchedule.getD26(),
          objEmpSchedule.getD27(),
          objEmpSchedule.getD28(),
          objEmpSchedule.getD29(),
          objEmpSchedule.getD30(),
          objEmpSchedule.getD31()
        };
        
        
        // proses filter terhadap Doc DP Application yang masing-masing harus di-invalid, di-update atau di-generate
        int maxSchedule = arrObjEmpSchedule.length;
        for(int i=0; i<maxSchedule; i++)
        {                    
            long scheduleOid = arrObjEmpSchedule[i];
            int scheduleCategory = PstScheduleSymbol.getCategoryType(scheduleOid);

            Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i+1);
            DpApplication objDpApplication = getDpApplicationBySchedule(takenDate, objEmpSchedule.getEmployeeId());                

            // jika ada document DP Application, maka lakukan 'update', 'set invalid'
            if(objDpApplication.getOID()!=0)
            {
                // jika schedule hr ini adalah category DP, maka lakukan 'update'
                if(scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)    
                {
                    // update existing DP Application 
                    long dpStockOid = 0;
                    if(listOfDPStock!=null && listOfDPStock.size()>0)
                    {
                        DpStockManagement objDpStock = (DpStockManagement) listOfDPStock.get(0);
                        dpStockOid = objDpStock.getOID();
                    }                                

                    long lUpdatedDpApp = updateExistingDpApplication(objDpApplication, dpStockOid);

                    // hapus data DpStockOID dari vector pada index ke 0
                    if(listOfDPStock!=null && listOfDPStock.size()>0)
                    {
                        listOfDPStock.remove(0);
                    }                      
                }
                
                // jika schedule hr ini bukan category DP
                else
                {
                    try
                    {
                        // set status old DP Application to 'invalid'
                        long lInvalidDpApp = setDpApplicationToInvalid(objDpApplication);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exc when 'set invalid' : " + e.toString());
                    }                    
                }
            }
             
            
            // jika tidak ada document DP Application, maka lakukan 'generate'
            else
            {
                // jika schedule hr ini adalah category DP
                if(scheduleCategory == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)    
                {                
                    // generate new DP Application 
                    long dpStockOid = 0;
                    if(listOfDPStock!=null && listOfDPStock.size()>0)
                    {
                        DpStockManagement objDpStock = (DpStockManagement) listOfDPStock.get(0);
                        dpStockOid = objDpStock.getOID();
                    }                                

                    long lNewDpApp = generateNewDpApplication(objEmpSchedule.getEmployeeId(), new Date(), takenDate, dpStockOid);

                    // hapus data DpStockOID dari vector pada index ke 0
                    if(listOfDPStock!=null && listOfDPStock.size()>0)
                    {
                        listOfDPStock.remove(0);
                    }             
                }
            }            
        }
        
        return result;
    }

    
    /**
     * set status old DP Application to 'invalid'
     * @param objDpApplication
     * @return
     */    
    public static long setDpApplicationToInvalid(DpApplication objDpApplication)
    {
        long result = 0;        
        
        try
        {
            objDpApplication.setDocStatus(PstDpApplication.FLD_DOC_STATUS_INVALID);
            result = PstDpApplication.updateExc(objDpApplication);        
        }
        catch(Exception e)
        {
            System.out.println("Exc when setDpApplicationToInvalid : " + e.toString());
        }
        
        return result;
    }

    
    /**
     * set status old DP Application to 'invalid'
     * @param objEmpSchedule
     * @return
     */    
    public static int setDpApplicationToInvalid(EmpSchedule objEmpSchedule)
    {
        int iResult = 0;        
        
        // get period duration of specified schedule
        long lSchedulePeriodOid = objEmpSchedule.getPeriodId();
        Date dStartDatePeriod = null;
        Date dEndDatePeriod = null;
        try
        {
            Period objPeriod = PstPeriod.fetchExc(lSchedulePeriodOid);
            dStartDatePeriod = objPeriod.getStartDate();
            dEndDatePeriod = objPeriod.getEndDate();
        }
        catch(Exception e)
        {
            System.out.println("Exc when fetch period object : " + e.toString());
        }
        
        
        // get list Dp Application document during period duration
        if(dStartDatePeriod!=null && dEndDatePeriod!=null)
        {
            String strStartDatePeriod = Formater.formatDate(dStartDatePeriod,"yyyy-MM-dd");
            String strEndDatePeriod = Formater.formatDate(dEndDatePeriod,"yyyy-MM-dd");
            String whereClause = PstDpApplication.fieldNames[PstDpApplication.FLD_EMPLOYEE_ID] + 
                                 " = " + objEmpSchedule.getEmployeeId() + 
                                 " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + 
                                 " BETWEEN \"" + strStartDatePeriod + "\"" + 
                                 " AND \"" + strEndDatePeriod + "\"";            
            Vector vListDpApplicationDoc = PstDpApplication.list(0, 0, whereClause, "");
            if(vListDpApplicationDoc!=null && vListDpApplicationDoc.size()>0) 
            {
                int iListDpApplicationDocCount = vListDpApplicationDoc.size();
                for(int i=0; i<iListDpApplicationDocCount; i++)
                {
                    DpApplication objDpApplication = (DpApplication) vListDpApplicationDoc.get(i);
                    long lResult = setDpApplicationToInvalid(objDpApplication);
                    iResult++;
                }
            }
        }        
        
        return iResult;
    }    
    
    /**
     * generate new Dp Application with 'new' DP stock OID
     * @param objDpApplication
     * @return
     */    
    public static long generateNewDpApplication(long employeeId, Date submissionDate, Date takenDate, long dpStockOid)
    {
        long result = 0;        
        
        try
        {
            DpApplication objDpApplicationNew = new DpApplication();
            objDpApplicationNew.setEmployeeId(employeeId);
            objDpApplicationNew.setSubmissionDate(submissionDate);
            objDpApplicationNew.setTakenDate(takenDate);
            objDpApplicationNew.setDpId(dpStockOid);                                
            objDpApplicationNew.setApprovalId(0);
            objDpApplicationNew.setDocStatus(PstDpApplication.FLD_DOC_STATUS_VALID);                            
            objDpApplicationNew.setBalance(0);                            
            objDpApplicationNew.setNewBalance(0);                            
            result = PstDpApplication.insertExc(objDpApplicationNew);
        }
        catch(Exception e)
        {
            System.out.println("Exc when generateNewDpApplication : " + e.toString());
        }
        
        return result;
    }

    
    /**
     * update existing Dp Application with 'new' DP stock OID
     * @param objDpApplication
     * @return
     */    
    public static long updateExistingDpApplication(DpApplication objDpApplication, long dpStockOid)
    {
        long result = 0;        
        
        try
        {
            objDpApplication.setDpId(dpStockOid);                                
            result = PstDpApplication.updateExc(objDpApplication);
        }
        catch(Exception e)
        {
            System.out.println("Exc when updateExistingDpApplication : " + e.toString());
        }
        
        return result;
    }    
    
    
    
    public static void main(String[] args)
    {
        /*
        Date takenDate = new Date(104, 9, 30);
        long empOid = 504404240100958350L;
        DpApplication objDpApplication = getDpApplicationBySchedule(takenDate, empOid);
        System.out.println("objDpApplication.getOID() : "+objDpApplication.getOID());
        System.out.println("objDpApplication.getEmployeeId() : "+objDpApplication.getEmployeeId());
        System.out.println("objDpApplication.getDpId() : "+objDpApplication.getDpId());
        System.out.println("objDpApplication.getSubmissionDate() : "+objDpApplication.getSubmissionDate());
        System.out.println("objDpApplication.getTakenDate() : "+objDpApplication.getTakenDate());
        System.out.println("objDpApplication.getApprovalId() : "+objDpApplication.getApprovalId());
        */
        
        EmpSchedule objEmpSchedule = new EmpSchedule();
        try
        {
            objEmpSchedule = PstEmpSchedule.fetchExc(504404267663073989L);
            int result = updateDpApplication(objEmpSchedule);
        }
        catch(Exception e)
        {
            System.out.println("Exc when fetch schedule : " + e.toString());
        }        
    }
    
}
