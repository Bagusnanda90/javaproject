/*
 * PstLeaveStockTaken.java
 *
 * Created on July 23, 2004, 3:38 PM
 */

package com.dimata.harisma.entity.attendance;

// import core java package
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;

// import dimata package
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;

// import harisma package
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;

/**
 *
 * @author  gedhy
 */
public class PstLeaveStockTaken  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_LEAVE_STOCK_TAKEN = "hr_leave_stock_taken";

    public static final int FLD_LEAVE_STOCK_TAKEN_ID = 0;
    public static final int FLD_LEAVE_STOCK_ID = 1;
    public static final int FLD_IDX_LEAVE_TAKEN = 2;
    public static final int FLD_EMP_SCHEDULE_ID = 3;
    public static final int FLD_IDX_DATE_SHCEDULE = 4;
    public static final int FLD_LEAVE_TYPE = 5;

    public static final String[] fieldNames = {
        "LEAVE_STOCK_TAKEN_ID",  
        "LEAVE_STOCK_ID",
        "IDX_LEAVE_TAKEN",
        "EMP_SCHEDULE_ID",
        "IDX_DATE_SCHEDULE",
        "LEAVE_TYPE"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT
    };

    public static int LEAVE_TYPE_DP = 0; // TYPE LEAVE DP
    public static int LEAVE_TYPE_AL = 1; // TYPE LEAVE AL
    public static int LEAVE_TYPE_LL = 2; // TYPE LEAVE LL    

    public static final String[] fieldStatus = {
        "TYPE LEAVE DP",
        "TYPE LEAVE AL",        
        "TYPE LEAVE LL"
    };

    public PstLeaveStockTaken() {
    }

    public PstLeaveStockTaken(int i) throws DBException {
        super(new PstLeaveStockTaken());
    }

    public PstLeaveStockTaken(String sOid) throws DBException {
        super(new PstLeaveStockTaken(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstLeaveStockTaken(long lOid) throws DBException {
        super(new PstLeaveStockTaken(0));
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
        return TBL_LEAVE_STOCK_TAKEN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveStockTaken().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveStockTaken leaveStockTaken = fetchExc(ent.getOID());
        return leaveStockTaken.getOID();
    }

    public static LeaveStockTaken fetchExc(long oid) throws DBException {
        try {
            LeaveStockTaken leaveStockTaken = new LeaveStockTaken();
            PstLeaveStockTaken objPstLeaveStockTaken = new PstLeaveStockTaken(oid);
            leaveStockTaken.setOID(oid);

            leaveStockTaken.setLeaveStockId(objPstLeaveStockTaken.getlong(FLD_LEAVE_STOCK_ID));
            leaveStockTaken.setIdxLeaveTaken(objPstLeaveStockTaken.getInt(FLD_IDX_LEAVE_TAKEN));
            leaveStockTaken.setEmpScheduleId(objPstLeaveStockTaken.getlong(FLD_EMP_SCHEDULE_ID));
            leaveStockTaken.setIdxDateSchedule(objPstLeaveStockTaken.getInt(FLD_IDX_DATE_SHCEDULE));
            leaveStockTaken.setLeaveType(objPstLeaveStockTaken.getInt(FLD_LEAVE_TYPE));

            return leaveStockTaken;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveStockTaken(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveStockTaken) ent);
    }

    public static long updateExc(LeaveStockTaken objLeaveStockTaken) throws DBException {
        try {
            if (objLeaveStockTaken.getOID() != 0) {
                PstLeaveStockTaken objPstLeaveStockTaken = new PstLeaveStockTaken(objLeaveStockTaken.getOID());

                objPstLeaveStockTaken.setLong(FLD_LEAVE_STOCK_ID, objLeaveStockTaken.getLeaveStockId());
                objPstLeaveStockTaken.setInt(FLD_IDX_LEAVE_TAKEN, objLeaveStockTaken.getIdxLeaveTaken());
                objPstLeaveStockTaken.setLong(FLD_EMP_SCHEDULE_ID, objLeaveStockTaken.getEmpScheduleId());
                objPstLeaveStockTaken.setInt(FLD_IDX_DATE_SHCEDULE, objLeaveStockTaken.getIdxDateSchedule());
                objPstLeaveStockTaken.setInt(FLD_LEAVE_TYPE, objLeaveStockTaken.getLeaveType());

                objPstLeaveStockTaken.update();
                return objLeaveStockTaken.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveStockTaken(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstLeaveStockTaken objPstLeaveStockTaken = new PstLeaveStockTaken(oid);
            objPstLeaveStockTaken.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveStockTaken(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveStockTaken) ent);
    }

    public static long insertExc(LeaveStockTaken objLeaveStockTaken) throws DBException {
        try {
            PstLeaveStockTaken objPstLeaveStockTaken = new PstLeaveStockTaken(0);

            objPstLeaveStockTaken.setLong(FLD_LEAVE_STOCK_ID, objLeaveStockTaken.getLeaveStockId());
            objPstLeaveStockTaken.setInt(FLD_IDX_LEAVE_TAKEN, objLeaveStockTaken.getIdxLeaveTaken());
            objPstLeaveStockTaken.setLong(FLD_EMP_SCHEDULE_ID, objLeaveStockTaken.getEmpScheduleId());
            objPstLeaveStockTaken.setInt(FLD_IDX_DATE_SHCEDULE, objLeaveStockTaken.getIdxDateSchedule());
            objPstLeaveStockTaken.setInt(FLD_LEAVE_TYPE, objLeaveStockTaken.getLeaveType());

            objPstLeaveStockTaken.insert();
            objLeaveStockTaken.setOID(objPstLeaveStockTaken.getlong(FLD_LEAVE_STOCK_TAKEN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveStockTaken(0), DBException.UNKNOWN);
        }
        return objLeaveStockTaken.getOID();
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_LEAVE_STOCK_TAKEN;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LeaveStockTaken leaveStockTaken = new LeaveStockTaken();
                resultToObject(rs, leaveStockTaken);
                lists.add(leaveStockTaken);
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

    private static void resultToObject(ResultSet rs, LeaveStockTaken leaveStockTaken) {
        try {
            leaveStockTaken.setOID(rs.getLong(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_STOCK_TAKEN_ID]));
            leaveStockTaken.setLeaveStockId(rs.getLong(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_STOCK_ID]));
            leaveStockTaken.setIdxLeaveTaken(rs.getInt(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_IDX_LEAVE_TAKEN]));
            leaveStockTaken.setEmpScheduleId(rs.getLong(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_EMP_SCHEDULE_ID]));
            leaveStockTaken.setIdxDateSchedule(rs.getInt(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_IDX_DATE_SHCEDULE]));
            leaveStockTaken.setLeaveType(rs.getInt(PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_TYPE]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param leaveStockId
     * @param idxLeaveTaken
     * @param empScheduleId
     * @param idxDateSchedule
     * @param leaveType
     * @return
     * @create by Edhy
     */    
    public long insertLeaveStockTaken(long leaveStockId, int idxLeaveTaken, long empScheduleId, int idxDateSchedule, int leaveType)
    {
        long result = 0;
        
        LeaveStockTaken leaveStockTaken = new LeaveStockTaken();
        leaveStockTaken.setLeaveStockId(leaveStockId);
        leaveStockTaken.setIdxLeaveTaken(idxLeaveTaken);
        leaveStockTaken.setEmpScheduleId(empScheduleId);
        leaveStockTaken.setIdxDateSchedule(idxDateSchedule);
        leaveStockTaken.setLeaveType(leaveType);
        try
        {
            result = PstLeaveStockTaken.insertExc(leaveStockTaken);
        }
        catch(Exception e)
        {
            System.out.println("Exc when insertDpStockTaken : " + e.toString());
        }
        finally
        {
            return result;
        }
    }    
    
    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static Vector getLeavePayable(int leaveType, long employeeId)
    {  
        Vector result = new Vector(1,1);        
        DBResultSet dbrs = null;        
        String stSQL = " SELECT TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_STOCK_TAKEN_ID] +
                       ", TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_STOCK_ID] +
                       ", TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_IDX_LEAVE_TAKEN] +
                       ", TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_EMP_SCHEDULE_ID] +
                       ", TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_IDX_DATE_SHCEDULE] +
                       ", TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_TYPE] +
                       " FROM " + PstLeaveStockTaken.TBL_LEAVE_STOCK_TAKEN + " AS TAK " +
                       " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS SCH " + 
                       " ON TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_EMP_SCHEDULE_ID] + 
                       " = SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + 
                       " WHERE SCH." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_TYPE] +
                       " = " + leaveType +
                       " AND TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_LEAVE_STOCK_ID] +
                       " = 0" + 
                       " ORDER BY TAK." + PstLeaveStockTaken.fieldNames[PstLeaveStockTaken.FLD_IDX_DATE_SHCEDULE];
        try
        {
//            System.out.println("SQL Leave Stock : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                LeaveStockTaken objLeaveStockTaken = new LeaveStockTaken();
                objLeaveStockTaken.setOID(rs.getLong(1));
                objLeaveStockTaken.setLeaveStockId(rs.getLong(2));
                objLeaveStockTaken.setIdxLeaveTaken(rs.getInt(3));
                objLeaveStockTaken.setEmpScheduleId(rs.getLong(4));
                objLeaveStockTaken.setIdxDateSchedule(rs.getInt(5));
                objLeaveStockTaken.setLeaveType(rs.getInt(6));
                
                result.add(objLeaveStockTaken);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("Exc when getLeavePayable : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return result;
        }        
    }    
    
    
    public static void main(String[] args)
    {
        Vector result = getLeavePayable(PstLeaveStockTaken.LEAVE_TYPE_DP, 504404240100964269L);
        if(result!=null && result.size()>0)
        {
            int maxResult = result.size();
            for(int i=0; i<maxResult; i++)
            {
                LeaveStockTaken objLeaveStockTaken = (LeaveStockTaken) result.get(i);
                System.out.println(objLeaveStockTaken.getOID());
                System.out.println(objLeaveStockTaken.getLeaveStockId());
                System.out.println(objLeaveStockTaken.getIdxLeaveTaken());
                System.out.println(objLeaveStockTaken.getEmpScheduleId());
                System.out.println(objLeaveStockTaken.getIdxDateSchedule());
                System.out.println(objLeaveStockTaken.getLeaveType());
                System.out.println("");
            }
        }
    }
    
}
