/*
 * PstCanteenVisitation.java
 * @author  rusdianta
 * Created on January 13, 2005, 4:08 PM
 */

package com.dimata.harisma.entity.canteen;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.*;
import com.dimata.util.lang.I_Language;
import com.dimata.harisma.entity.canteen.CanteenVisitation;

public class PstCanteenVisitation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_CANTEEN_VISITATION = "hr_canteen_visitation";//"HR_CANTEEN_VISITATION";
    public static final int FLD_VISITATION_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_VISITATION_TIME = 2;
    public static final int FLD_STATUS = 3;
    public static final int FLD_ANALYZED = 4;
    public static final int FLD_TRANSFERRED = 5;
    public static final int FLD_NUM_OF_VISITATION = 6;
    
    public static final String fieldNames[] = {
        "VISITATION_ID",
        "EMPLOYEE_ID",
        "VISITATION_TIME",
        "STATUS",
        "ANALYZED",
        "TRANSFERRED",
        "NUM_OF_VISITATION"
    };
    
    public static final int fieldTypes[] = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG + TYPE_FK,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };
    
    public static final int NOT_TRANSFERRED = 0;
    public static final int TRANSFERRED = 1;
    public static final String transferredStatus[] = {
        "Canteen visitation have not transferred",
        "Canteen visitation have transferred"
    };
    
    /** Creates a new instance of PstCanteenVisitation */
    public PstCanteenVisitation() {
    }
    
    public PstCanteenVisitation(int i) throws DBException {
        super(new PstCanteenVisitation());
    }
    
    public PstCanteenVisitation(String soid) throws DBException {
        super(new PstCanteenVisitation(0));
        if (!locate(soid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstCanteenVisitation(long oid) throws DBException {
        super(new PstCanteenVisitation(0));
        String soid = "0";
        try {
            soid = String.valueOf(oid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(soid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstCanteenVisitation().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_CANTEEN_VISITATION;
    }
    
    /* --- delete process --- */
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstCanteenVisitation pstCanteenVisitation = new PstCanteenVisitation(oid);
            pstCanteenVisitation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCanteenVisitation(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        if (ent == null)
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        return deleteExc(ent.getOID());
    }
    
    /* --- fetch process --- */
    
    public static CanteenVisitation fetchExc(long oid) throws DBException {
        try {
            CanteenVisitation canteenVisitation = new CanteenVisitation();
            PstCanteenVisitation pstCanteenVisitation = new PstCanteenVisitation(oid);
            canteenVisitation.setOID(oid);
            canteenVisitation.setEmployeeId(pstCanteenVisitation.getlong(FLD_EMPLOYEE_ID));
            canteenVisitation.setVisitationTime(pstCanteenVisitation.getDate(FLD_VISITATION_TIME));
            canteenVisitation.setStatus(pstCanteenVisitation.getInt(FLD_STATUS));
            canteenVisitation.setAnalyzed(pstCanteenVisitation.getInt(FLD_ANALYZED));
            canteenVisitation.setTransferred(pstCanteenVisitation.getInt(FLD_TRANSFERRED));                        
            canteenVisitation.setNumOfVisitation(pstCanteenVisitation.getInt(FLD_NUM_OF_VISITATION));                        
            return canteenVisitation;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCanteenVisitation(0), DBException.UNKNOWN);            
        }
    }
    
    public long fetchExc(Entity ent) throws Exception {
        CanteenVisitation canteenVisitation = fetchExc(ent.getOID());
        ent = (Entity) canteenVisitation;
        return canteenVisitation.getOID();
    }
    
    /* --- insert process --- */
    
    public static long insertExc(CanteenVisitation canteenVisitation) throws DBException {
        try {
            PstCanteenVisitation pstCanteenVisitation = new PstCanteenVisitation(0);
            pstCanteenVisitation.setLong(FLD_EMPLOYEE_ID, canteenVisitation.getEmployeeId());
            pstCanteenVisitation.setDate(FLD_VISITATION_TIME, canteenVisitation.getVisitationTime());
            pstCanteenVisitation.setInt(FLD_STATUS, canteenVisitation.getStatus());
            pstCanteenVisitation.setInt(FLD_ANALYZED, canteenVisitation.getAnalyzed());
            pstCanteenVisitation.setInt(FLD_TRANSFERRED, canteenVisitation.getTransferred());
            pstCanteenVisitation.setInt(FLD_NUM_OF_VISITATION, canteenVisitation.getNumOfVisitation());
            pstCanteenVisitation.insert();
            canteenVisitation.setOID(pstCanteenVisitation.getlong(FLD_VISITATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCanteenVisitation(0), DBException.UNKNOWN);
        }
        return canteenVisitation.getOID();
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((CanteenVisitation) ent);
    }
    
    /* --- update processing --- */
    
    public static long updateExc(CanteenVisitation canteenVisitation) throws DBException {
        try {
            if (canteenVisitation.getOID() != 0) {
                PstCanteenVisitation pstCanteenVisitation = new PstCanteenVisitation(canteenVisitation.getOID());
                pstCanteenVisitation.setLong(FLD_EMPLOYEE_ID, canteenVisitation.getEmployeeId());
                pstCanteenVisitation.setDate(FLD_VISITATION_TIME, canteenVisitation.getVisitationTime());
                pstCanteenVisitation.setInt(FLD_STATUS, canteenVisitation.getStatus());
                pstCanteenVisitation.setInt(FLD_ANALYZED, canteenVisitation.getAnalyzed());
                pstCanteenVisitation.setInt(FLD_TRANSFERRED, canteenVisitation.getTransferred());
                pstCanteenVisitation.setInt(FLD_NUM_OF_VISITATION, canteenVisitation.getNumOfVisitation());
                pstCanteenVisitation.update();
                return canteenVisitation.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCanteenVisitation(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((CanteenVisitation) ent);
    } 
    
    public static void resultToObject(ResultSet rs,
                                      CanteenVisitation canteenVisitation)
    {
        try {
            canteenVisitation.setOID(rs.getLong(fieldNames[FLD_VISITATION_ID]));
            canteenVisitation.setEmployeeId(rs.getLong(fieldNames[FLD_EMPLOYEE_ID]));
            java.sql.Date date = rs.getDate(fieldNames[FLD_VISITATION_TIME]);
            java.sql.Time time = rs.getTime(fieldNames[FLD_VISITATION_TIME]);
            java.util.Date visitationTime = new java.util.Date(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());            
            canteenVisitation.setVisitationTime(visitationTime);
            canteenVisitation.setStatus(rs.getInt(fieldNames[FLD_STATUS]));
            canteenVisitation.setAnalyzed(rs.getInt(fieldNames[FLD_ANALYZED]));
            canteenVisitation.setTransferred(rs.getInt(fieldNames[FLD_TRANSFERRED]));
            canteenVisitation.setNumOfVisitation(rs.getInt(fieldNames[FLD_NUM_OF_VISITATION]));
        } catch (Exception e) {
            
        }
    }
    
    public static Vector list(int limitStart,
                              int recordToGet,
                              String whereClause,
                              String order)
    {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String SQL = "SELECT * FROM " + TBL_CANTEEN_VISITATION;
            if (whereClause.length() > 0)
                SQL += " WHERE " + whereClause;
            if (order.length() > 0)
                SQL += " ORDER BY " + order;
            if (limitStart > 0 || recordToGet > 0)
                SQL += " LIMIT " + limitStart + ", " + recordToGet;
            dbrs = DBHandler.execQueryResult(SQL);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CanteenVisitation canteenVisitation = new CanteenVisitation();
                resultToObject(rs, canteenVisitation);
                lists.add(canteenVisitation);
            }
            rs.close();
            return lists;
        } catch (Exception ERROR) {
            System.out.println(ERROR.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }
    
    public static boolean checkOID(long visitationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String SQL = "SELECT * FROM " + TBL_CANTEEN_VISITATION + " WHERE " + fieldNames[FLD_VISITATION_ID] + " = " + visitationId;
            dbrs = DBHandler.execQueryResult(SQL);
            ResultSet rs = dbrs.getResultSet();
            if (rs.next())
                result = true;
            rs.close();
        } catch (Exception e) {
            System.out.println("ERORR : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);           
        }      
        return result;
    }
    
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String SQL = "SELECT COUNT(" + fieldNames[FLD_VISITATION_ID] + ") FROM " + TBL_CANTEEN_VISITATION;
            if (whereClause.length() > 0)
                SQL += " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(SQL);
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) 
                count = rs.getInt(1);
            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }        
    }
    
    public static int findLimitStart(long oid,
                                     int recordToGet,
                                     String whereClause,
                                     String orderClause)
    {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int  i = 0; (i < size) && !found; i += recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            for (int ls = 0; ls < list.size(); ls++) {
                CanteenVisitation canteenVisitation = (CanteenVisitation) list.get(ls);
                if (oid == canteenVisitation.getOID()) {
                    found = true;
                    break;
                }
            }
        }
        if ((start >= size) && (size > 0))
            start -= recordToGet;
        return start;
    }
    
    public static int findLimitCommand(int start,
                                       int recordToGet,
                                       int vectSize)
    {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize += recordToGet - mdl;
        if (start == 0)
            cmd = Command.FIRST;
        else {
            if (start == (vectSize - recordToGet))
                cmd = Command.LAST;
            else {
                start += recordToGet;
                if (start <= (vectSize - recordToGet))
                    cmd = Command.NEXT;
                else {
                    start -= recordToGet;
                    if (start > 0)
                        cmd = Command.PREV;                       
                }
            }
                
        }
        return cmd;    
    }
    
    public static CanteenVisitation getLatestVisitation(long employeeOid) {
        String whereClause = fieldNames[FLD_EMPLOYEE_ID] + " = " + employeeOid;
        String orderClause = fieldNames[FLD_VISITATION_TIME] + " DESC";
        Vector results = list(0, 1, whereClause, orderClause);
        if (results.size() > 0) {
            CanteenVisitation output = (CanteenVisitation) results.get(0);
            return output;
        }
        return null;
    }
    
    /*public static void main(String args[]) {
        long employeeOid = 504404240100960513L;
        CanteenVisitation canteenVisitation = getLatestVisitation(employeeOid);
        if (canteenVisitation == null)
            System.out.println("There is no record found. Please check it ...");
        else {
            System.out.println("Nilai dari Visitation Id   = " + canteenVisitation.getVisitationId());
            System.out.println("Nilai dari Employee Id     = " + canteenVisitation.getEmployeeId());
            System.out.println("Nilai dari Visitation Time = " + canteenVisitation.getVisitationTime());
            System.out.println("Nilai dari Status          = " + canteenVisitation.getStatus());
            System.out.println("Nilai dari Analyzed        = " + canteenVisitation.getAnalyzed());
            System.out.println("Nilai dari Transferred     = " + canteenVisitation.getTransferred());
            //Date date = canteenVisitation.getVisitationTime();
            //Time time = canteenVisitation.getVisitationTime();
            
            //System.out.println("Nilai dari Second          = " + time.getSeconds());
            //System.out.println("Nilai dari Minute          = " + time.getMinutes());
            //System.out.println("Nilai dari Hour            = " + time.getHours());
            //System.out.println("Nilai dari Date            = " + date.getDate());
            //System.out.println("Nilai dari Month           = " + date.getMonth());
            //System.out.println("Nilai dari Year            = " + date.getYear());
            
           // Date abc = new Date();
            //System.out.println("Nilai dari abc Date = " + abc);
            
            //Date xyz = new Date();
            //xyz.setDate(1);
            //xyz.setMonth(2);
            //xyz.setYear(105);
            //xyz.setHours(10);
            //xyz.setMinutes(11);
            //xyz.setSeconds(12);
            
            //System.out.println("Nilai dari XYZ aadalah : " + xyz);
            
            Date a1 = new Date(105, 0, 1, 1, 0, 0);
            Date a2 = new Date(105, 0, 1, 1, 1, 0);
            System.out.println("Nilai dari a1 = " + a1.getTime());
            System.out.println("Nilai dari a2 = " + a2.getTime());
            System.out.println("The difference is " + (a2.getTime() - a1.getTime()));
            
        }
            
    }*/ 
}
