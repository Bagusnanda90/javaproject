
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Kartika
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Input Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.employee;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.harisma.entity.masterdata.*;

/* package  harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*;

public class PstDefaultSchedule extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_DEFAULT_SCHEDULE = "hr_dflt_schedule";//"HR_FAMILY_MEMBER";
    public static final int FLD_DFLT_SCHEDULE_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_SCHEDULE_1 = 2;
    public static final int FLD_SCHEDULE_2 = 3;
    public static final int FLD_DAY_INDEX = 4;
    public static final String[] fieldNames = {
        "DFLT_SCHEDULE_ID",
        "EMPLOYEE_ID",
        "SCH_ID_1",
        "SCH_ID_2",
        "DAY_IDX"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    /*
    public static final int REL_WIFE			= 0;
    public static final int REL_HUSBAND			= 1;
    public static final int REL_CHILDREN		= 2;

    public static String[] relationValue = {"Wife","Husband","Children"};

    public static Vector getRelation(){
    Vector result = new Vector(1,1);
    for(int i=0;i<relationValue.length;i++){
    result.add(relationValue[i]);
    }
    return result;
    }*/
    public PstDefaultSchedule() {
    }

    public PstDefaultSchedule(int i) throws DBException {
        super(new PstDefaultSchedule());
    }

    public PstDefaultSchedule(String sOid) throws DBException {
        super(new PstDefaultSchedule(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstDefaultSchedule(long lOid) throws DBException {
        super(new PstDefaultSchedule(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HR_DEFAULT_SCHEDULE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstDefaultSchedule().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        DefaultSchedule defaultSchedule = fetchExc(ent.getOID());
        ent = (Entity) defaultSchedule;
        return defaultSchedule.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((DefaultSchedule) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((DefaultSchedule) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static DefaultSchedule fetchExc(long oid) throws DBException {
        try {
            DefaultSchedule defaultSchedule = new DefaultSchedule();
            PstDefaultSchedule pstDefaultSchedule = new PstDefaultSchedule(oid);
            defaultSchedule.setOID(oid);

            defaultSchedule.setEmployeeId(pstDefaultSchedule.getlong(FLD_EMPLOYEE_ID));
            defaultSchedule.setSchedule1(pstDefaultSchedule.getlong(FLD_SCHEDULE_1));
            defaultSchedule.setSchedule2(pstDefaultSchedule.getlong(FLD_SCHEDULE_2));
            defaultSchedule.setDayIndex(pstDefaultSchedule.getInt(FLD_DAY_INDEX));
            return defaultSchedule;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDefaultSchedule(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(DefaultSchedule defaultSchedule) throws DBException {
        try {
            PstDefaultSchedule pstDefaultSchedule = new PstDefaultSchedule(0);

            pstDefaultSchedule.setLong(FLD_EMPLOYEE_ID, defaultSchedule.getEmployeeId());
            pstDefaultSchedule.setLong(FLD_SCHEDULE_1, defaultSchedule.getSchedule1());
            pstDefaultSchedule.setLong(FLD_SCHEDULE_2, defaultSchedule.getSchedule2());
            pstDefaultSchedule.setInt(FLD_DAY_INDEX, defaultSchedule.getDayIndex());            
            pstDefaultSchedule.insert();
            defaultSchedule.setOID(pstDefaultSchedule.getlong(FLD_DFLT_SCHEDULE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDefaultSchedule(0), DBException.UNKNOWN);
        }
        return defaultSchedule.getOID();
    }

    public static long updateExc(DefaultSchedule defaultSchedule) throws DBException {
        try {
            if (defaultSchedule.getOID() != 0) {
                PstDefaultSchedule pstDefaultSchedule = new PstDefaultSchedule(defaultSchedule.getOID());

                pstDefaultSchedule.setLong(FLD_EMPLOYEE_ID, defaultSchedule.getEmployeeId());
                pstDefaultSchedule.setLong(FLD_SCHEDULE_1, defaultSchedule.getSchedule1());
                pstDefaultSchedule.setLong(FLD_SCHEDULE_2, defaultSchedule.getSchedule2());
                pstDefaultSchedule.setInt(FLD_DAY_INDEX, defaultSchedule.getDayIndex());

                pstDefaultSchedule.update();
                return defaultSchedule.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDefaultSchedule(0), DBException.UNKNOWN);
        }
        return 0;
    }
    private FamRelation famRelation;  

    public static long deleteExc(long oid) throws DBException {
        try {
            PstDefaultSchedule pstDefaultSchedule = new PstDefaultSchedule(oid);
            pstDefaultSchedule.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDefaultSchedule(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DEFAULT_SCHEDULE + " AS DF ";                    
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DefaultSchedule defaultSchedule = new DefaultSchedule();
                resultToObject(rs, defaultSchedule);
                lists.add(defaultSchedule);
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
 * mencari tblHashDfltSch
 * create by satrya 2013-06-27
 * @return 
 */
    public static Hashtable getHashTblDfltSch() {
        //Vector lists = new Vector();
        Hashtable hashDlftSch = new Hashtable();
       // DfltScheduleTable dfltScheduleTable = new DfltScheduleTable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_DEFAULT_SCHEDULE + " AS DF ";                    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DefaultSchedule defaultSchedule = new DefaultSchedule();
                resultToObject(rs, defaultSchedule);
                //lists.add(defaultSchedule);
                //dfltScheduleTable.addDfltSchTable(, );
                hashDlftSch.put(defaultSchedule.getEmployeeId(), defaultSchedule);
            }
            rs.close();
            //return dfltScheduleTable;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashDlftSch;
    }
    
    
    private static void resultToObject(ResultSet rs, DefaultSchedule defaultSchedule) {
        try {
            defaultSchedule.setOID(rs.getLong(PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DFLT_SCHEDULE_ID]));
            defaultSchedule.setEmployeeId(rs.getLong(PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]));
            defaultSchedule.setSchedule1(rs.getLong(PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_SCHEDULE_1]));
            defaultSchedule.setSchedule2(rs.getLong(PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_SCHEDULE_2]));
            defaultSchedule.setDayIndex(rs.getInt(PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX]));

        } catch (Exception e) {
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DFLT_SCHEDULE_ID] + ") FROM " + TBL_HR_DEFAULT_SCHEDULE;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }


    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    DefaultSchedule defaultSchedule = (DefaultSchedule) list.get(ls);
                    if (oid == defaultSchedule.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }

        return cmd;
    }

    public static long deleteByEmployee(long emplOID) {
        try {
            String sql = " DELETE FROM " + PstDefaultSchedule.TBL_HR_DEFAULT_SCHEDULE
                    + " WHERE " + PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]
                    + " = " + emplOID;

            int status = DBHandler.execUpdate(sql);
        } catch (Exception exc) {
            System.out.println("error delete fam member by employee " + exc.toString());
        }

        return emplOID;
    }
    
    public static DefaultSchedule getDefaultSchedule(int dayIdex, Vector dftSchedules){
        if(dftSchedules==null){
            return new DefaultSchedule() ;
        }
        for(int idx=0;idx < dftSchedules.size(); idx++){
            DefaultSchedule dfltSch = (DefaultSchedule) dftSchedules.get(idx);
             if(dayIdex==dfltSch.getDayIndex()){
                 return dfltSch;
             }
        }
        return new DefaultSchedule();
    }
    
    public static DefaultSchedule getDefaultSchedule(int dayIdex, long employeeOID){
         String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employeeOID;
         String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
         Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);
         if(dftSchedules==null){
            return null;
        }
        for(int idx=0;idx < dftSchedules.size(); idx++){
            DefaultSchedule dfltSch = (DefaultSchedule) dftSchedules.get(idx);
             if(dayIdex==dfltSch.getDayIndex()){
                 return dfltSch;
             }
        }
        return null;
    }
    

}
