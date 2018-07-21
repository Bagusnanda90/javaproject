/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

/**
 *
 * @author Tu Roy
 */

import com.dimata.harisma.entity.attendance.LeaveApplicationSummary;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;

import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.entity.search.SrcSpecialLeaveSummaryAttd;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.qdep.entity.I_DocStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;
import com.dimata.util.Formater;
import com.dimata.util.DateCalc;
import com.dimata.util.LogicParser;
import java.util.Hashtable;


public class PstSpecialUnpaidLeaveTaken extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
    public static final String TBL_SPECIAL_UNPAID_LEAVE_TAKEN = "hr_special_unpaid_leave_taken";
    
    public static final int FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID = 0;
    public static final int FLD_LEAVE_APLICATION_ID = 1;
    public static final int FLD_SCHEDULED_ID = 2;
    public static final int FLD_EMPLOYEE_ID = 3;
    public static final int FLD_TAKEN_DATE = 4;
    public static final int FLD_TAKEN_QTY = 5;
    public static final int FLD_TAKEN_STATUS = 6;
    public static final int FLD_TAKEN_FROM_STATUS = 7;
    public static final int FLD_TAKEN_FINNISH_DATE = 8;
    
    public static final String[] fieldNames = {
        "SPECIAL_UNPAID_LEAVE_TAKEN_ID",
        "LEAVE_APPLICATION_ID",
        "SCHEDULED_ID",
        "EMPLOYEE_ID",
        "TAKEN_DATE",
        "TAKEN_QTY",
        "TAKEN_STATUS",
        "TAKEN_FROM_STATUS",
        "TAKEN_FINNISH_DATE"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE
    };  
    
    public static int TAKEN_STATUS_NOT_YET_PROCESS = 0;
    public static int TAKEN_STATUS_PROCESS = 1;
    
    public static int TAKEN_FROM_STATUS_USER = 0;
    public static int TAKEN_FROM_STATIS_SYSTEM = 1;
    
    
    public static int LEAVE_STATUS_SPECIAL = 0;
    public static int LEAVE_STATUS_UNPAID = 1;
    
    public static final String[] fieldStatus = {
        "Special Leave",
        "Unpaid Leave"
    };
    
    public static final String[] fieldTakenStatus = {
        "NOT YET PROCESS",
        "PROCESS"
    };
    
    
    public static final String[] fieldTakenFromStatus = {
        "USER",
        "SYSTEM"
    };
    
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
    public PstSpecialUnpaidLeaveTaken(){        
    }
    
    public PstSpecialUnpaidLeaveTaken(int i) throws DBException{
        super(new PstSpecialUnpaidLeaveTaken());
    }
    
    public PstSpecialUnpaidLeaveTaken(String sOid) throws DBException {
        super(new PstSpecialUnpaidLeaveTaken(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstSpecialUnpaidLeaveTaken(long lOid) throws DBException {
        super(new PstSpecialUnpaidLeaveTaken(0));
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
        return TBL_SPECIAL_UNPAID_LEAVE_TAKEN;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    public String getPersistentName() {
        return new PstSpecialUnpaidLeaveTaken().getClass().getName();
    }
     public long fetchExc(Entity ent) throws Exception {
        SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = fetchExc(ent.getOID());
        return objSpecialUnpaidLeaveTaken.getOID();
    }
    
     public static SpecialUnpaidLeaveTaken fetchExc(long oid) throws DBException {
        try {
            SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
            PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(oid);
            objSpecialUnpaidLeaveTaken.setOID(oid);

            objSpecialUnpaidLeaveTaken.setLeaveApplicationId(objPstSpecialUnpaidLeaveTaken.getlong(FLD_LEAVE_APLICATION_ID));
            objSpecialUnpaidLeaveTaken.setScheduledId(objPstSpecialUnpaidLeaveTaken.getlong(FLD_SCHEDULED_ID));
            objSpecialUnpaidLeaveTaken.setEmployeeId(objPstSpecialUnpaidLeaveTaken.getlong(FLD_EMPLOYEE_ID));
            objSpecialUnpaidLeaveTaken.setTakenDate(objPstSpecialUnpaidLeaveTaken.getDate(FLD_TAKEN_DATE));            
            //objSpecialUnpaidLeaveTaken.setTakenQty(objPstSpecialUnpaidLeaveTaken.getInt(FLD_TAKEN_QTY));
            objSpecialUnpaidLeaveTaken.setTakenStatus(objPstSpecialUnpaidLeaveTaken.getInt(FLD_TAKEN_STATUS));
            objSpecialUnpaidLeaveTaken.setTakenFromStatus(objPstSpecialUnpaidLeaveTaken.getInt(FLD_TAKEN_FROM_STATUS));
            objSpecialUnpaidLeaveTaken.setTakenFinnishDate(objPstSpecialUnpaidLeaveTaken.getDate(FLD_TAKEN_FINNISH_DATE));
            objSpecialUnpaidLeaveTaken.setTakenQty(objPstSpecialUnpaidLeaveTaken.getfloat(FLD_TAKEN_QTY));                        
            return objSpecialUnpaidLeaveTaken;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSpecialUnpaidLeaveTaken(0), DBException.UNKNOWN);
        }
    }
     
    public long updateExc(Entity ent) throws Exception {
        return updateExc((SpecialUnpaidLeaveTaken) ent);
    } 
    
    public static long updateExc(SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken) throws DBException {
        try {
            if (objSpecialUnpaidLeaveTaken.getOID() != 0) {
                PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(objSpecialUnpaidLeaveTaken.getOID());
                objPstSpecialUnpaidLeaveTaken.setLong(FLD_LEAVE_APLICATION_ID, objSpecialUnpaidLeaveTaken.getLeaveApplicationId());
                objPstSpecialUnpaidLeaveTaken.setLong(FLD_SCHEDULED_ID,objSpecialUnpaidLeaveTaken.getScheduledId());
                objPstSpecialUnpaidLeaveTaken.setLong(FLD_EMPLOYEE_ID, objSpecialUnpaidLeaveTaken.getEmployeeId());
                objPstSpecialUnpaidLeaveTaken.setDate(FLD_TAKEN_DATE, objSpecialUnpaidLeaveTaken.getTakenDate());
                objPstSpecialUnpaidLeaveTaken.setFloat(FLD_TAKEN_QTY, objSpecialUnpaidLeaveTaken.getTakenQty());
                objPstSpecialUnpaidLeaveTaken.setInt(FLD_TAKEN_STATUS, objSpecialUnpaidLeaveTaken.getTakenStatus());
                objPstSpecialUnpaidLeaveTaken.setInt(FLD_TAKEN_FROM_STATUS, objSpecialUnpaidLeaveTaken.getTakenFromStatus());
                objPstSpecialUnpaidLeaveTaken.setDate(FLD_TAKEN_FINNISH_DATE, objSpecialUnpaidLeaveTaken.getTakenFinnishDate());
                
                objPstSpecialUnpaidLeaveTaken.update();
                return objSpecialUnpaidLeaveTaken.getOID();
               
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSpecialUnpaidLeaveTaken(0), DBException.UNKNOWN);            
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(oid);
            objPstSpecialUnpaidLeaveTaken.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSpecialUnpaidLeaveTaken(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static int deleteByLeaveAppID(long leaveAppOid) throws DBException {
        try {
            String where =" "+ fieldNames[FLD_LEAVE_APLICATION_ID]+" = "+leaveAppOid;
            PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(0);
            objPstSpecialUnpaidLeaveTaken.deleteRecords(0, where);
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSpecialUnpaidLeaveTaken(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((SpecialUnpaidLeaveTaken) ent);
    }
    
   public static long insertExc(SpecialUnpaidLeaveTaken objUnpaidLeavetaken) throws DBException {
        try {
            PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeavetaken = new PstSpecialUnpaidLeaveTaken(0);

            objPstSpecialUnpaidLeavetaken.setLong(FLD_LEAVE_APLICATION_ID, objUnpaidLeavetaken.getLeaveApplicationId());
            objPstSpecialUnpaidLeavetaken.setLong(FLD_SCHEDULED_ID, objUnpaidLeavetaken.getScheduledId());
            objPstSpecialUnpaidLeavetaken.setLong(FLD_EMPLOYEE_ID, objUnpaidLeavetaken.getEmployeeId());            
            objPstSpecialUnpaidLeavetaken.setDate(FLD_TAKEN_DATE, objUnpaidLeavetaken.getTakenDate());            
            objPstSpecialUnpaidLeavetaken.setFloat(FLD_TAKEN_QTY, objUnpaidLeavetaken.getTakenQty());
            objPstSpecialUnpaidLeavetaken.setInt(FLD_TAKEN_STATUS, objUnpaidLeavetaken.getTakenStatus());
            objPstSpecialUnpaidLeavetaken.setInt(FLD_TAKEN_FROM_STATUS, objUnpaidLeavetaken.getTakenFromStatus());           
            objPstSpecialUnpaidLeavetaken.setDate(FLD_TAKEN_FINNISH_DATE, objUnpaidLeavetaken.getTakenFinnishDate());           

            objPstSpecialUnpaidLeavetaken.insert();
            objUnpaidLeavetaken.setOID(objPstSpecialUnpaidLeavetaken.getlong(FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSpecialUnpaidLeaveTaken(0), DBException.UNKNOWN);
        }
        return objUnpaidLeavetaken.getOID();
    }
    
   
   public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SpecialUnpaidLeaveTaken SpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                resultToObject(rs, SpecialUnpaidLeaveTaken);
                lists.add(SpecialUnpaidLeaveTaken);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
   
   /**
    * create by satrya 2013-11-15
    * @param limitStart
    * @param recordToGet
    * @param whereClause
    * @param order
    * @return 
    */
    public static Vector listJointWithLeaveApplication(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN +" AS SU"
                    +" INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION 
                    +" AS LA ON SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]
                    +"= LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID];
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SpecialUnpaidLeaveTaken SpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                resultToObject(rs, SpecialUnpaidLeaveTaken);
                lists.add(SpecialUnpaidLeaveTaken);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    
    /**
    * create by priska 2015-07-23
    * @param limitStart
    * @param recordToGet
    * @param whereClause
    * @param order
    * @return 
    */
    public static boolean UnpaidLeaveToday(long empId, Date scheduleDate) {
        DBResultSet dbrs = null;
                boolean status = false;
                String jam="";
                String menit = "";
                String detik = "";
                        
                if (scheduleDate != null){
                jam = String.valueOf(scheduleDate.getHours());
                if (jam.length() < 2){
                    jam = "0"+jam;
                }
                menit = String.valueOf(scheduleDate.getMinutes());
                if (menit.length() < 2){
                    menit = "0"+menit;
                }
                detik = String.valueOf(scheduleDate.getSeconds());
                if (detik.length() < 2){
                    detik = "0"+detik;
                }
                }
		try {
			String sql = "SELECT hla."+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ", has."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] +" FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " hla ";
                                sql = sql + " INNER JOIN "+TBL_SPECIAL_UNPAID_LEAVE_TAKEN+" has ON has.`LEAVE_APPLICATION_ID` = hla.`LEAVE_APPLICATION_ID` " ;
                                sql = sql + " WHERE `hla`.`DOC_STATUS` >=2  AND hla.`EMPLOYEE_ID` = " + empId + " AND (\"" +  Formater.formatDate(scheduleDate, "yyyy-MM-dd") + "\" BETWEEN DATE_FORMAT(has.`TAKEN_DATE`,'%Y-%m-%d') AND DATE_FORMAT(has.`TAKEN_FINNISH_DATE`,'%Y-%m-%d') )";

                       
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			
			while(rs.next()) { 
                            
                            long scheduleId = rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]);
                            int statusLeave = SessLeaveApplication.LeaveStatus(scheduleId);
                            // 2 = STATUS_LEAVE_UNPAID_LEAVE
                            if(statusLeave == 2){
                                status = true;
                            }
                        }

			rs.close();
			return status;
		}catch(Exception e) {
			return status;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
    
    
   //update by satrya 2012-12-23
   /**
    * 
    * @return 
    */
      public static Vector listSpecialSymbole() {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
            + " ,HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]
            /*+ " , HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]*/
            +" FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS HPS "
            + " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
            + " AS SS ON (HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]
            + " = SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+")"
            + " GROUP BY SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
            + " ORDER BY HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID];
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                specialUnpaidLeaveTaken.setSymbole(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                specialUnpaidLeaveTaken.setEmployeeId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]));
                //specialUnpaidLeaveTaken.setScheduledId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]));
                lists.add(specialUnpaidLeaveTaken);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
      
   //create by satrya ramayu  
      /**
       * Keterangan : mencari hashtable special Symbole
       * @return 
       */
   public static Hashtable<String, Integer> getHashSpecialSymbole(){
		 DBResultSet dbrs = null;
        
       Hashtable<String, Integer> specialSymbole= new Hashtable();
                int idx=-1;
        	try {
			 String sql = "SELECT SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                        /*+ " HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]
                        + " HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]*/
                        +" FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS HPS "
                        + " INNER JOIN "+PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL
                        + " AS SS ON (HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]
                        + " = SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]+")"
                        + " GROUP BY SS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                        + " ORDER BY HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID];
                     
			dbrs = DBHandler.execQueryResult(sql); 
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
                           idx= idx + 1;
                         specialSymbole.put(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]), idx);
                            
                        }
                     
			rs.close();
			return specialSymbole;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Hashtable();
    	}
      
   /**
    * Untuk mencari summary dari special Leave
    * create by satrya 2014-02-10
    * @param srcSpecialLeaveSummaryAttd
    * @param selectDateFrom
    * @param selectedDateTo
    * @return 
    */
   public static Hashtable listTotTakenBySrcLeaveManagemnt(SrcSpecialLeaveSummaryAttd srcSpecialLeaveSummaryAttd,Date selectDateFrom,Date selectedDateTo) {
         if (srcSpecialLeaveSummaryAttd == null || selectedDateTo==null && selectDateFrom==null) {
            return new Hashtable();
        }
String whareClause=" AND (1=1)";
    if (srcSpecialLeaveSummaryAttd.getEmpCompanyId() != 0) {
        whareClause = whareClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + srcSpecialLeaveSummaryAttd.getEmpCompanyId();
    }
    if (srcSpecialLeaveSummaryAttd.getEmpDivisionId() != 0) {
        whareClause = whareClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + srcSpecialLeaveSummaryAttd.getEmpDivisionId();
    }
    if (srcSpecialLeaveSummaryAttd.getEmpDeptId() != 0) {
        whareClause = whareClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcSpecialLeaveSummaryAttd.getEmpDeptId();
    }
    if (srcSpecialLeaveSummaryAttd.getEmpSection() != 0) {
        whareClause = whareClause + " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcSpecialLeaveSummaryAttd.getEmpSection();
    }
   if (srcSpecialLeaveSummaryAttd.getEmpNum() != null && srcSpecialLeaveSummaryAttd.getEmpNum().length() > 0) {
            Vector vectName = logicParser(srcSpecialLeaveSummaryAttd.getEmpNum());
            whareClause = whareClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whareClause = whareClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whareClause = whareClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whareClause = whareClause + str.trim();
                    }
                }
                whareClause = whareClause + ")";
            }
        }
        if (srcSpecialLeaveSummaryAttd.getEmpName() != null && srcSpecialLeaveSummaryAttd.getEmpName().length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(srcSpecialLeaveSummaryAttd.getEmpName());
            whareClause = whareClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whareClause = whareClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whareClause = whareClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whareClause = whareClause + str.trim();
                    }
                }
                whareClause = whareClause + ")";
            }
        }//update by satrya 2013-08-14
         
        
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID] + " AS EMP"
                    +",HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+" AS TK "
                    +",HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" AS TF "
                    +",HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY]+" AS TAKEN_QTY "
                    // update by satrya 2014-02-10 + " , SUM(HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY]+") AS SUMQTY" 
                    // update by satrya 2014-02-10 + " , COUNT(HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+") AS COUNTSYM" 
                    +",HS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL"
                    //+ " , HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID] 
                    +" FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN
                    + " AS HPS INNER JOIN "+PstEmployee.TBL_HR_EMPLOYEE
                    + " AS EMP ON(HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]
                    + " = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS HS " 
                    + " ON (HS."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + " = HPS."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+")"
                    + " INNER JOIN "+ PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA ON (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]+"=HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+")"
                    + " WHERE "
                    + ("\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd 23:59:59")+"\" > HPS."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ " AND HPS."
       + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd 00:00:00")+"\"")
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+I_DocStatus.DOCUMENT_STATUS_CANCELLED + " AND "
//                    +(srcLeaveManagement.getEmpNum() ==null || srcLeaveManagement.getEmpNum().length() < 1 ? "" : ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + srcLeaveManagement.getEmpNum() + "' AND "))+
//                    (srcLeaveManagement.getEmpName() ==null || srcLeaveManagement.getEmpName().length() < 1 ? " (1=1) AND " : ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " like '%" +
//                    srcLeaveManagement.getEmpName() + "%' AND ")) + (srcLeaveManagement.getEmpDeptId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcLeaveManagement.getEmpDeptId() + " AND ") : "(1=1) AND ")
//                    + (srcLeaveManagement.getEmpSectionId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcLeaveManagement.getEmpSectionId() + " AND ") : " (1=1) AND ") 
//                    + (srcLeaveManagement.getEmpLevelId() != 0 ? ("EMP." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID] + " = " + srcLeaveManagement.getEmpLevelId() + " AND ") : " (1=1) AND ") 
     
                    + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "='" + PstEmployee.NO_RESIGN + "' " 
                    + whareClause
                    /*+ " GROUP BY  EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+","
                          + " HPS. " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]*/
                    + " ORDER BY HPS."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID];
            dbrs = DBHandler.execQueryResult(sql);
            
            ResultSet rs = dbrs.getResultSet();
            long tmpEmployeeId=0;
            LeaveApplicationSummary leaveApplicationSummary = new LeaveApplicationSummary();
            while (rs.next()) {
               /* SummarySpecialUnpaidLeaveTaken summarySpecialUnpaidLeaveTaken = new SummarySpecialUnpaidLeaveTaken();
                summarySpecialUnpaidLeaveTaken.setEmployeeId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]));
                summarySpecialUnpaidLeaveTaken.setTotTakenQty(rs.getFloat("SUMQTY"));
                summarySpecialUnpaidLeaveTaken.setCountScheduleId(rs.getInt("COUNTSYM"));
                //summarySpecialUnpaidLeaveTaken.setScheduleSpecialId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]));
                summarySpecialUnpaidLeaveTaken.setSymbole(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                lists.add(summarySpecialUnpaidLeaveTaken);*/
                 if(tmpEmployeeId !=rs.getLong("EMP")){
                    tmpEmployeeId = rs.getLong("EMP");
                    leaveApplicationSummary = new LeaveApplicationSummary();
                     Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                    
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                        if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                    leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                    leaveApplicationSummary.addJumlahTaken(tDate);
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                else{
                    //Date tmpCutiStart=null;
                    Date tmpSelectedTo = selectedDateTo==null?null:Formater.reFormatDate(selectedDateTo, "yyyy-MM-dd 00:00:00");
                    double tDate=0;
                        Date dtTaken =DBHandler.convertDate(rs.getDate("TK"),rs.getTime("TK"));
                        Date dtFinish=DBHandler.convertDate(rs.getDate("TF"),rs.getTime("TF"));
                        
                         if (dtTaken!=null && dtFinish!=null && tmpSelectedTo!=null && tmpSelectedTo.getTime() - Formater.reFormatDate(dtFinish, "yyyy-MM-dd 00:00:00").getTime() < 0) {
                           long diffStartToFinish  = tmpSelectedTo.getTime() - Formater.reFormatDate(dtTaken, "yyyy-MM-dd 00:00:00").getTime();
                           tDate = Math.abs(Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)))+1;
                        } else {
                            tDate = rs.getDouble("TAKEN_QTY");
                        }
                    leaveApplicationSummary.addSymbol(rs.getString("LEAVE_SYMBOL"));
                    leaveApplicationSummary.addJumlahTaken(tDate);
                    leaveApplicationSummary.setEmployeeId(rs.getLong("EMP"));
                }
                      //lists.add(overtimeDetail);
                      lists.put(leaveApplicationSummary.getEmployeeId(), leaveApplicationSummary);
                 
            }
            rs.close();
          
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return lists;
        }
        
    }
    public static void resultToObject(ResultSet rs, SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken) {
        try {
            objSpecialUnpaidLeaveTaken.setOID(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]));
            objSpecialUnpaidLeaveTaken.setLeaveApplicationId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]));
            objSpecialUnpaidLeaveTaken.setScheduledId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]));
            objSpecialUnpaidLeaveTaken.setEmployeeId(rs.getLong(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]));
            //objSpecialUnpaidLeaveTaken.setTakenDate(rs.getDate(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]));            
            objSpecialUnpaidLeaveTaken.setTakenStatus(rs.getInt(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_STATUS]));
            objSpecialUnpaidLeaveTaken.setTakenFromStatus(rs.getInt(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FROM_STATUS]));
            //objSpecialUnpaidLeaveTaken.setTakenFinnishDate(rs.getDate(PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]));
            
            objSpecialUnpaidLeaveTaken.setTakenDate(
                    rs.getTime(fieldNames[FLD_TAKEN_DATE]) != null
                    ? PstEmpSchedule.convertDate(rs.getDate(fieldNames[FLD_TAKEN_DATE]),
                    rs.getTime(fieldNames[FLD_TAKEN_DATE]))
                    : rs.getDate(fieldNames[FLD_TAKEN_DATE]));
            objSpecialUnpaidLeaveTaken.setTakenFinnishDate(
                    rs.getTime(fieldNames[FLD_TAKEN_FINNISH_DATE]) != null
                    ? PstEmpSchedule.convertDate(rs.getDate(fieldNames[FLD_TAKEN_FINNISH_DATE]),
                    rs.getTime(fieldNames[FLD_TAKEN_FINNISH_DATE]))
                    : rs.getDate(fieldNames[FLD_TAKEN_FINNISH_DATE]));            
            objSpecialUnpaidLeaveTaken.setTakenQty(rs.getFloat(fieldNames[FLD_TAKEN_QTY]));                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Vector getSpecialUnpaidLeaveTakenList(String sql){
        DBResultSet dbrs = null;
        Vector lists = new Vector();
        try{
            
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()){
                SpecialUnpaidLeaveTaken SpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
                resultToObject(rs, SpecialUnpaidLeaveTaken);
                lists.add(SpecialUnpaidLeaveTaken);
            }
            
            rs.close();
            return lists;
        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        }
        return null;        
    }
    
    public static int getCountRecordSpcUnpLeaveTkn(String whereClause){
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT("+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID]+
                    ") FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN+ " AS SP"
                   + " INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION
                   + " AS LA ON LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                   + " =SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID];
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            dbrs = DBHandler.execQueryResult(sql);
            System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            int contr = 0;
            
            while(rs.next()){
                contr = rs.getInt(1);
            }
            
            return contr;
            
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        
        return 0;
    }
    
public static float getSumQtySpcUnpLeaveTkn(String whereClause){
        
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM("+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_QTY]+
                    ") FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SP"
                   + " INNER JOIN "+PstLeaveApplication.TBL_LEAVE_APPLICATION
                   + " AS LA ON LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                   + " =SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID];
            //") FROM " + TBL_SPECIAL_UNPAID_LEAVE_TAKEN ;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("SQL "+sql);
            ResultSet rs = dbrs.getResultSet();
            float sum = 0;
            
            while(rs.next()){
                sum = rs.getFloat(1);
            }
            
            return sum;
            
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        
        return 0f;
    }    
    

     /**
     * Keterangan: untuk mencari taken Special Leave
     * create by satrya 2014-01-06
     * @param leaveApplicationId
     * @param employeeId
     * @return 
     */
    public static Vector getSpTaken(long leaveApplicationId,long employeeId) {
        Vector result = new Vector(1, 1);
        if(leaveApplicationId==0 || employeeId==0){
            return result;
        }
        DBResultSet dbrs = null;
        String stSQL = "SELECT * "
                + " FROM " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN
                + " WHERE " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]
                + " = " + employeeId
                + " AND " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]
                + " = "+leaveApplicationId
                + " ORDER BY " + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE];
        try {

            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveStockTaken = new SpecialUnpaidLeaveTaken();
                resultToObject(rs, objSpecialUnpaidLeaveStockTaken);
                result.add(objSpecialUnpaidLeaveStockTaken);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Exc when getAlPayable : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static boolean getHaveTakenExecuteForm(Date dateFrom, long empId) {
        DBResultSet dbrs = null;
        boolean ada = false;
        try {
            String sql = "SELECT hsult.* FROM `hr_special_unpaid_leave_taken` hsult INNER JOIN `hr_leave_application` hla ON hla.`LEAVE_APPLICATION_ID` = `hsult`.`LEAVE_APPLICATION_ID`"; 
                   sql += "WHERE `hsult`.`EMPLOYEE_ID` = "+empId+" AND ( \""+ Formater.formatDate(dateFrom, "yyyy-MM-dd HH:mm:ss") +"\" BETWEEN `hsult`.`TAKEN_DATE` AND `hsult`.`TAKEN_FINNISH_DATE`  )  AND `hla`.`DOC_STATUS` > 2 ";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ada = true;
            }

            rs.close();
            return ada;
        } catch (Exception e) {
            return ada;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static boolean getHaveTakenExecuteForm2(Date dateFrom, Date dateTo, long empId, String oidSchedule) {
        DBResultSet dbrs = null;
        boolean ada = false;
        if ((oidSchedule.equals("Not initialized")) || oidSchedule.length() == 0 ){
            return ada;
        }
        String sql="";
        try {
             sql  = "SELECT hsult.* FROM `hr_special_unpaid_leave_taken` hsult INNER JOIN `hr_leave_application` hla ON hla.`LEAVE_APPLICATION_ID` = `hsult`.`LEAVE_APPLICATION_ID`"; 
                   sql += "WHERE `hsult`.`EMPLOYEE_ID` = "+empId+" AND ( \""+ Formater.formatDate(dateFrom, "yyyy-MM-dd") +"\"  BETWEEN LEFT(`hsult`.`TAKEN_DATE`,11)  AND LEFT(`hsult`.`TAKEN_FINNISH_DATE`,11) )  AND `hla`.`DOC_STATUS` > 2 ";
            if (oidSchedule.length() > 0 && !(oidSchedule.equals("Not initialized")) ){
                   sql += "AND hsult.`SCHEDULED_ID` = "+oidSchedule+"";
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ada = true;
            }

            rs.close();
            return ada;
        } catch (Exception e) {
            return ada;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
/**
     * menghitung DP qunatity ( dalam unit workday ( 8 jam kerja ) dari seorang karyawan pada rentang periode waktu tertentu
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return 
     */
    public static float getSpUnQty(long employeeId, Date startDate, Date endDate, long[] scheduleIds) {
        float qty = 0.0f;
        if(scheduleIds==null || scheduleIds.length<1){
            return 0;
        }
        String inScheId = " (";
        for(int il=0; il< scheduleIds.length-1;il++){
            inScheId = inScheId + scheduleIds[il]+",";
        }
        inScheId = inScheId + scheduleIds[scheduleIds.length-1]+") ";
        
        String where = fieldNames[FLD_SCHEDULED_ID] + " IN "+ inScheId + " AND  (" + 
                fieldNames[FLD_EMPLOYEE_ID] + "=\""+ employeeId + "\") AND  ((" + fieldNames[FLD_TAKEN_DATE] + " BETWEEN \""
                + Formater.formatDate(startDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\") OR "
                + " (" + fieldNames[FLD_TAKEN_FINNISH_DATE] + " BETWEEN \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "00:00:00\" AND \""
                + Formater.formatDate(endDate, "yyyy-MM-dd ") + "23:59:59\"))";

        Vector list = list(0, 100, where, fieldNames[FLD_TAKEN_DATE]);
        if (list != null && list.size() > 0) {
            for (int idx = 0; idx < list.size(); idx++) {
                SpecialUnpaidLeaveTaken suStockTaken = (SpecialUnpaidLeaveTaken) list.get(idx);
                if ((DateCalc.dayDifference(suStockTaken.getTakenDate(), startDate) >= 0)
                        && (DateCalc.dayDifference(suStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                    // taken date and taken finish di dalam start and end date
                    if (suStockTaken.getTakenQty() == (float) Math.floor(suStockTaken.getTakenQty())) {
                        // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                        qty = qty + DateCalc.dayDifference(startDate, endDate)+1;
                    } else {
                        // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                        qty = qty + DateCalc.dayDifference(startDate, endDate);
                        qty = qty + suStockTaken.getTakenQty() - (float) Math.floor(suStockTaken.getTakenQty());
                    }
                } else {
                    if ((DateCalc.dayDifference(suStockTaken.getTakenDate(), startDate) >= 0)
                            && (DateCalc.dayDifference(suStockTaken.getTakenFinnishDate(), endDate) > 0)) {
                        // taken date and taken finish di dalam start and end date
                        if (suStockTaken.getTakenQty() == (float) Math.floor(suStockTaken.getTakenQty())) {
                            // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                            qty = qty + DateCalc.dayDifference(startDate, suStockTaken.getTakenFinnishDate())+1;
                        } else {
                            // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                            qty = qty + DateCalc.dayDifference(startDate, suStockTaken.getTakenFinnishDate());
                            qty = qty + suStockTaken.getTakenQty() - (float) Math.floor(suStockTaken.getTakenQty());
                        }
                    } else {
                        if ((DateCalc.dayDifference(suStockTaken.getTakenDate(), startDate) < 0)
                                && (DateCalc.dayDifference(suStockTaken.getTakenFinnishDate(), endDate) <= 0)) {
                            // taken date and taken finish di dalam start and end date
                            if (suStockTaken.getTakenQty() == (float) Math.floor(suStockTaken.getTakenQty())) {
                                // quantity stock taken bulat ( artinya tidak ada pecahan 8 jam kerja
                                qty = qty + DateCalc.dayDifference(suStockTaken.getTakenDate(), endDate)+1;
                            } else {
                                // quantity tidak bulat, artinya hari terakhir pecahan  8 jam;
                                qty = qty + DateCalc.dayDifference(suStockTaken.getTakenDate(), endDate);
                                qty = qty + suStockTaken.getTakenQty() - (float) Math.floor(suStockTaken.getTakenQty());
                            }
                        } else {
                            // startdate  and enddate  beyond takendate and takenfinishdate
                            qty = qty + suStockTaken.getTakenQty();                            
                        }
                    }
                }
            }
        }
        return qty;
    }         
    
}
