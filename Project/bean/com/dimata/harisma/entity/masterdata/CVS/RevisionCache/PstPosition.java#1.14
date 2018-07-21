
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package  harisma */
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.outsource.PstOutSourceEvaluation;
import com.dimata.harisma.entity.outsource.PstOutSourceEvaluationProvider;
import com.dimata.harisma.entity.outsource.PstOutSourcePlan;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanDetail;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.SrcObject;
import com.dimata.util.Formater;

public class PstPosition extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{

    public static final String SESS_HR_POSITION = "SESS_HR_POSITION";
    public static final String TBL_HR_POSITION = "hr_position";
    public static final int FLD_POSITION_ID = 0;
    public static final int FLD_POSITION = 1;
    public static final int FLD_DESCRIPTION = 2;
    public static final int FLD_POSITION_LEVEL = 3;
    public static final int FLD_DISABLED_APP_UNDER_SUPERVISOR = 4;
    public static final int FLD_DISABLED_APP_DEPT_SCOPE = 5;
    public static final int FLD_DISABLED_APP_DIV_SCOPE = 6;
    public static final int FLD_ALL_DEPARTMENT = 7;    
    public static final int FLD_DEDLINE_SCH_BEFORE = 8;
    public static final int FLD_DEDLINE_SCH_AFTER = 9;
    public static final int FLD_DEDLINE_SCH_LEAVE_BEFORE = 10;
    public static final int FLD_DEDLINE_SCH_LEAVE_AFTER = 11;
    //Gede_8Maret2012{
    public static final int FLD_HEAD_TITLE = 12;
    //update by satrya 2012-10-19
    public static final int FLD_POSITION_LEVEL_PAYROL = 13;
    
    //update by satrya 2014-03-06
    public static final int FLD_POSITION_KODE = 14;
    //update by satrya 2014-04-18
    public static final int FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT=15;
    /* update by Hendra Putu 2015-09-09 */
    public static final int FLD_VALID_STATUS = 16;
    public static final int FLD_VALID_START = 17;
    public static final int FLD_VALID_END = 18;
    public static final int FLD_LEVEL_ID = 19;

    //}
    public static final String[] fieldNames = {
        "POSITION_ID",
        "POSITION",
        "DESCRIPTION",
        "POSITION_LEVEL",
        "DISABLED_APP_UNDER_SUPERVISOR",
        "DISABLED_APP_DEPT_SCOPE",
        "DISABLED_APP_DIV_SCOPE",
        "ALL_DEPARTMENT",
        "DEDLINE_SCH_BEFORE",
        "DEDLINE_SCH_AFTER",
        "DEDLINE_SCH_LEAVE_BEFORE",
        "DEDLINE_SCH_LEAVE_AFTER",
        //Gede_8Maret2012{
        "HEAD_TITLE",
        //update by satrya 2012-10-19
        "POSITION_LEVEL_PAYROL",
        "POSITION_KODE",
        "FLAG_POSITION_SHOW_IN_PAYROLL_INPUT",
        /*UPDATE BY HENDRA 20150909*/
        "VALID_STATUS",
        "VALID_START",
        "VALID_END",
        "LEVEL_ID"
       //}
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,       
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        //Gede_8Maret2012{
        TYPE_INT,
        //update by satrya 2012-10-19
        TYPE_INT,
        TYPE_STRING,
        //update by satrya 2014-04-18
        TYPE_INT,
        /*UPDATE BY HENDRA 20150909*/
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG
        //}
    };
    
    public static final int LEVEL_GENERAL = 0;
    public static final int LEVEL_SECRETARY = 1;
    public static final int LEVEL_SUPERVISOR = 2;
    public static final int LEVEL_ASST_MANAGER = 3; 
    public static final int LEVEL_MANAGER = 4;  // before 3
    public static final int LEVEL_ASST_DIRECTOR = 5;
    public static final int LEVEL_DIRECTOR = 6;
    public static final int LEVEL_GENERAL_MANAGER = 7; // before 4
        
    public static final String[] strPositionLevelNames = {
        "Staff", //1
        "Secretary", //5
        "Supervisor", //4
        "Asst. Manager",//7
        "Manager", //8
        "Asst. Director",//9
        "Director",//10
        "VP/General Manager",//11
        "Operator", //3
        "Officer", //2
        "Junior Manager", //6
        "--None--"
    };
    
    public static final String[] strPositionLevelNamesWithRank = {
        "Staff", //1
        "Operator", //2
        "Officer", //3
        "Supervisor", //4
        "Secretary", //5
        "Junior Manager", //6
        "Asst. Manager",//7
        "Manager", //8
        "Asst. Director",//9
        "Director",//10
        "VP/General Manager",//11
    };
    
    public static final String[] strPositionLevelValue = {
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11"
    };

    public static final int[] strPositionLevelInt = {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10
    };
    
    public static final int VALID_ACTIVE = 1;
    public static final int VALID_HISTORY = 2;
    
    public static final String[] validStatusValue = {
        "ACTIVE","HISTORY"
    };


    //Gede_8Maret2012
    //Head title{
    public static final int HEAD_TITLE_NONE = 0;
    public static final int HEAD_TITLE_SECTION = 1;
    public static final int HEAD_TITLE_DEPARTMENT = 2;
    public static final int HEAD_TITLE_DIVISION = 3;
    public static final int HEAD_TITLE_COMPANY = 4;
    public static final int HEAD_TITLE_BOARD = 5;

    public static final String[] strHeadTitle = {
        "None",
        "Section Head",
        "Department Head",
        "Division Head",
        "Company Head",
        "Board of Director",

    };
    public static final int[] strHeadTitleInt = {
        0,
        1,
        2,
        3,
        4,
        5,
    };

    //}
    /**
     * create by satrya 2014-04-18
     */
    public static final int NO_SHOW_PAY_INPUT = 0;
     public static final int YES_SHOW_PAY_INPUT = 1;
     public static final String[] strShowPayInput = {
        "No",
        "Yes"
    };
    
    public static final int DISABLED_APP_UNDER_SUPERVISOR_FALSE = 0;
    public static final int DISABLED_APP_UNDER_SUPERVISOR_TRUE = 1;
    public static final int DISABLED_APP_DEPT_SCOPE_FALSE = 0;
    public static final int DISABLED_APP_DEPT_SCOPE_TRUE = 1;
    public static final int DISABLED_APP_DIV_SCOPE_FALSE = 0;
    public static final int DISABLED_APP_DIV_SCOPE_TRUE = 1;
    public static final int ALL_DEPARTMENT_FALSE = 0;
    public static final int ALL_DEPARTMENT_TRUE = 1;
    
    /* configurasi untuk update schedule ( batas update schedule ) */
    public static final int UPDATE_SCHEDULE_BEFORE_TIME           = 0;
    public static final int UPDATE_SCHEDULE_AFTER_TIME            = 1;
    public static final int UPDATE_SCHEDULE_LEAVE_BEFORE_TIME     = 2;
    public static final int UPDATE_SCHEDULE_LEAVE_AFTER_TIME      = 3;    
    
    public static final int HOUR_UNLIMITED  = 8640; /* 360 hari x 24 jam */
    public static final int NO_HOUR         = -1;   /* value tidak bisa merubah schedule */

    public PstPosition() {
    }

    public PstPosition(int i) throws DBException {
        super(new PstPosition());
    }

    public PstPosition(String sOid) throws DBException {
        super(new PstPosition(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPosition(long lOid) throws DBException {
        super(new PstPosition(0));
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
        return TBL_HR_POSITION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPosition().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Position position = fetchExc(ent.getOID());
        ent = (Entity) position;
        return position.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Position) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Position) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Position fetchExc(long oid) throws DBException {
        try {
            Position position = new Position();
            PstPosition pstPosition = new PstPosition(oid);
            position.setOID(oid);

            position.setPosition(pstPosition.getString(FLD_POSITION));
            position.setDescription(pstPosition.getString(FLD_DESCRIPTION));
            position.setPositionLevel(pstPosition.getInt(FLD_POSITION_LEVEL));

            position.setDisabledAppUnderSupervisor(pstPosition.getInt(FLD_DISABLED_APP_UNDER_SUPERVISOR));
            position.setDisabledAppDeptScope(pstPosition.getInt(FLD_DISABLED_APP_DEPT_SCOPE));
            position.setDisabedAppDivisionScope(pstPosition.getInt(FLD_DISABLED_APP_DIV_SCOPE));
            position.setAllDepartment(pstPosition.getInt(FLD_ALL_DEPARTMENT));

            //Gede_8Maret2012{
            position.setHeadTitle(pstPosition.getInt(FLD_HEAD_TITLE));
            //update by satrya 2012-10-19
            position.setPositionLevelPayrol(pstPosition.getInt(FLD_POSITION_LEVEL_PAYROL));
            
            //update by satrya 2014-03-06
            position.setKodePosition(pstPosition.getString(FLD_POSITION_KODE));
             //update by satrya 2014-04-18
            position.setFlagShowPayInput(pstPosition.getInt(FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT));
            
            position.setValidStatus(pstPosition.getInt(FLD_VALID_STATUS));
            position.setValidStart(pstPosition.getDate(FLD_VALID_START));
            position.setValidEnd(pstPosition.getDate(FLD_VALID_END));
            position.setLevelId(pstPosition.getLong(FLD_LEVEL_ID));
            //}

            position.setDeadlineScheduleBefore(pstPosition.getInt(FLD_DEDLINE_SCH_BEFORE));
            position.setDeadlineScheduleAfter(pstPosition.getInt(FLD_DEDLINE_SCH_AFTER));
            position.setDeadlineScheduleLeaveBefore(pstPosition.getInt(FLD_DEDLINE_SCH_LEAVE_BEFORE));
            position.setDeadlineScheduleLeaveAfter(pstPosition.getInt(FLD_DEDLINE_SCH_LEAVE_AFTER));


             
            return position;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPosition(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Position position) throws DBException {
        try {
            PstPosition pstPosition = new PstPosition(0);

            pstPosition.setString(FLD_POSITION, position.getPosition());
            pstPosition.setString(FLD_DESCRIPTION, position.getDescription());
            pstPosition.setInt(FLD_POSITION_LEVEL, position.getPositionLevel());

            pstPosition.setInt(FLD_DISABLED_APP_UNDER_SUPERVISOR, position.getDisabledAppUnderSupervisor());
            pstPosition.setInt(FLD_DISABLED_APP_DEPT_SCOPE, position.getDisabledAppDeptScope());

            pstPosition.setInt(FLD_DISABLED_APP_DIV_SCOPE, position.getDisabedAppDivisionScope());
            pstPosition.setInt(FLD_ALL_DEPARTMENT, position.getAllDepartment());

            //Gede_8Maret2012{
            pstPosition.setInt(FLD_HEAD_TITLE, position.getHeadTitle());
            //update by satrya 2012-10-19
            pstPosition.setInt(FLD_POSITION_LEVEL_PAYROL, position.getPositionLevelPayrol());
            
            //update by satrya 2014-03-06
            pstPosition.setString(FLD_POSITION_KODE, position.getKodePosition());
             //update by satrya 2014-04-18
            pstPosition.setInt(FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT, position.getFlagShowPayInput());
            //}
            pstPosition.setInt(FLD_VALID_STATUS, position.getValidStatus());
            pstPosition.setDate(FLD_VALID_START, position.getValidStart());
            pstPosition.setDate(FLD_VALID_END, position.getValidEnd());
            pstPosition.setLong(FLD_LEVEL_ID, position.getLevelId());

            pstPosition.setInt(FLD_DEDLINE_SCH_BEFORE, position.getDeadlineScheduleBefore());
            pstPosition.setInt(FLD_DEDLINE_SCH_AFTER, position.getDeadlineScheduleAfter());
            pstPosition.setInt(FLD_DEDLINE_SCH_LEAVE_BEFORE, position.getDeadlineScheduleLeaveBefore());
            pstPosition.setInt(FLD_DEDLINE_SCH_LEAVE_AFTER, position.getDeadlineScheduleLeaveAfter());

            pstPosition.insert();
            position.setOID(pstPosition.getlong(FLD_POSITION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPosition(0), DBException.UNKNOWN);
        }
        return position.getOID();
    }

    public static long updateExc(Position position) throws DBException {
        try {
            if (position.getOID() != 0) {
                PstPosition pstPosition = new PstPosition(position.getOID());

                pstPosition.setString(FLD_POSITION, position.getPosition());
                pstPosition.setString(FLD_DESCRIPTION, position.getDescription());
                pstPosition.setInt(FLD_POSITION_LEVEL, position.getPositionLevel());

                pstPosition.setInt(FLD_DISABLED_APP_UNDER_SUPERVISOR, position.getDisabledAppUnderSupervisor());
                pstPosition.setInt(FLD_DISABLED_APP_DEPT_SCOPE, position.getDisabledAppDeptScope());

                pstPosition.setInt(FLD_DISABLED_APP_DIV_SCOPE, position.getDisabedAppDivisionScope());
                pstPosition.setInt(FLD_ALL_DEPARTMENT, position.getAllDepartment());

                //Gede_8Maret2012{
                pstPosition.setInt(FLD_HEAD_TITLE, position.getHeadTitle());
                pstPosition.setInt(FLD_POSITION_LEVEL_PAYROL, position.getPositionLevelPayrol());
                //update by satrya 2014-03-06
                pstPosition.setString(FLD_POSITION_KODE, position.getKodePosition());
                
                 //update by satrya 2014-04-18
                pstPosition.setInt(FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT, position.getFlagShowPayInput());
                //}
                pstPosition.setInt(FLD_VALID_STATUS, position.getValidStatus());
                pstPosition.setDate(FLD_VALID_START, position.getValidStart());
                pstPosition.setDate(FLD_VALID_END, position.getValidEnd());
                pstPosition.setLong(FLD_LEVEL_ID, position.getLevelId());

                pstPosition.setInt(FLD_DEDLINE_SCH_BEFORE, position.getDeadlineScheduleBefore());
                pstPosition.setInt(FLD_DEDLINE_SCH_AFTER, position.getDeadlineScheduleAfter());
                pstPosition.setInt(FLD_DEDLINE_SCH_LEAVE_BEFORE, position.getDeadlineScheduleLeaveBefore());
                pstPosition.setInt(FLD_DEDLINE_SCH_LEAVE_AFTER, position.getDeadlineScheduleLeaveAfter());
                
                pstPosition.update();
                return position.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPosition(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPosition pstPosition = new PstPosition(oid);
            pstPosition.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPosition(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_POSITION;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.add(position);
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

    public static Hashtable<String, Position> listMap(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable<String, Position> lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.put(""+position.getOID(), position);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    
    public static Hashtable listMapPositionName(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.put(position.getOID(), position.getPosition());
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }
    
    /**
     * create by devin 2014-04-09
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
     public static Hashtable listt(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable listt = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION;
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
           // System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                listt.put(""+position.getOID(), position.getPosition());
            }
           

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return listt;
        }
      
    }
     
    
    //update by satrya 2012-10-08
    /**
     * Keterangan : mencari level position dari tabel postion berdasarkan employe ID
     * @param empId
     * @return 
     */
    public static int iGetPositionLevel(long empId) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HE INNER JOIN "
                    + TBL_HR_POSITION + " AS HP ON(HE."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+")" + " WHERE HE."
                    +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " = " + empId;
            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int ilistLevel=0;
            while (rs.next()) {
                ilistLevel = rs.getInt(1);
            }
            rs.close();
            return ilistLevel;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
        
    }

    /**
     * Keterangan: untuk mengetahui posisi apa karywawan tsb
     * create by satrya 2014-01-31
     * @return 
     */
    public static Hashtable hashGetPositionLevel() {
        Hashtable hashPositionLevel = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]
                    +", HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HE INNER JOIN "
                    + TBL_HR_POSITION + " AS HP ON(HE."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+")"; 
            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                
                hashPositionLevel.put(rs.getLong("HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), rs.getInt("HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]));
            }
            rs.close();
            return hashPositionLevel;
        } catch (Exception e) {
            //return hashPositionLevel;
            System.out.println("Exc"+e);
        } finally {
            DBResultSet.close(dbrs);
            return hashPositionLevel;
        }
        
    }
    /**
     * create by satrya 2014-03-11
     * keterangan: untuk mengambil objeck Postion
     * @return 
     */
     public static Hashtable hashGetPosition() {
        Hashtable hashPositionLevel = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT HP.*"
                    +", HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    +" FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS HE INNER JOIN "
                    + TBL_HR_POSITION + " AS HP ON(HE."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " = HP."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+")"; 
            //System.out.println("sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                hashPositionLevel.put(""+rs.getLong("HE."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]),position);
            }
            rs.close();
            return hashPositionLevel;
        } catch (Exception e) {
            //return hashPositionLevel;
            System.out.println("Exc"+e);
        } finally {
            DBResultSet.close(dbrs);
            return hashPositionLevel;
        }
        
    }
    public static void resultToObject(ResultSet rs, Position position){
        
        try {
            
            position.setOID(rs.getLong(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
            position.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
            position.setDescription(rs.getString(PstPosition.fieldNames[PstPosition.FLD_DESCRIPTION]));
            position.setPositionLevel(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL]));

            position.setDisabledAppUnderSupervisor(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_UNDER_SUPERVISOR]));
            position.setDisabledAppDeptScope(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DEPT_SCOPE]));
            position.setDisabedAppDivisionScope(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DISABLED_APP_DIV_SCOPE]));
            position.setAllDepartment(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_ALL_DEPARTMENT]));
            
            //Gede_8Maret2012{
            position.setHeadTitle(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE]));
            //}
            ///update by satrya 2012-10-19
            position.setPositionLevelPayrol(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL_PAYROL]));

            //update by satrya 2014-03-06
            position.setKodePosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION_KODE]));
            //update by satrya 2014-04-18
            position.setFlagShowPayInput(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT]));
            
            position.setValidStatus(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_VALID_STATUS]));
            position.setValidStart(rs.getDate(PstPosition.fieldNames[PstPosition.FLD_VALID_START]));
            position.setValidEnd(rs.getDate(PstPosition.fieldNames[PstPosition.FLD_VALID_END]));
            position.setLevelId(rs.getLong(PstPosition.fieldNames[PstPosition.FLD_LEVEL_ID]));
            
            position.setDeadlineScheduleBefore(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DEDLINE_SCH_BEFORE]));
            position.setDeadlineScheduleAfter(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DEDLINE_SCH_AFTER]));
            position.setDeadlineScheduleLeaveBefore(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DEDLINE_SCH_LEAVE_BEFORE]));
            position.setDeadlineScheduleLeaveAfter(rs.getInt(PstPosition.fieldNames[PstPosition.FLD_DEDLINE_SCH_LEAVE_AFTER]));

        } catch (Exception e) {
            System.out.println("Exception "+e.toString());
        }
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION + " WHERE " +
                    PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = " + positionId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    
     public static Vector listJoinOutSourceEval(int limitStart, int recordToGet, String order, SrcObject srcObject) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            
            String sql = " SELECT DISTINCT(pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"),pos.*  FROM "+TBL_HR_POSITION+" AS pos "+
                         " INNER JOIN "+ PstOutSourceEvaluationProvider.TBL_OUT_SOURCE_EVAL_PROVIDER+" AS osep ON pos."+ PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" = osep."+ PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_POSITION_ID]+
                         " INNER JOIN "+ PstOutSourceEvaluation.TBL_OUTSOURCEEVALUATION+" AS ose ON osep."+ PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_ID] +" = ose."+ PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_OUTSOURCE_EVAL_ID];
          
            
            if (srcObject != null ) {
                sql = sql + " WHERE 1=1 " ;
                
                if (srcObject.getDivisiId() > 0 ){
                    sql = sql + " AND ose." + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DIVISION_ID] + " = " + srcObject.getDivisiId() ;
                }
                 if (srcObject.getPayPeriodId().length() > 0 ){
                    String periodeIn =  srcObject.getPayPeriodId().substring(0,srcObject.getPayPeriodId().length() - 1);
                    sql = sql + " AND ose." + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_PERIOD_ID] + " IN ( " + periodeIn + " ) " ;
                }
                if (srcObject.getProviderId() > 0 ){
                    sql = sql + " AND osep." + PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_PROVIDER_ID] + " = " + srcObject.getProviderId() ;
                }
            }
            
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.add(position);
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
     
     
     public static Vector<Position> listPositionOutsourcePlan(Date checkDate, int statusDocPlan) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
         
        String strDate = "\"" + Formater.formatDate(checkDate, "yyyy-MM-dd") + "\"";
       
        
        try {
            
            String sql = " SELECT pos.*  FROM "+TBL_HR_POSITION+" AS pos "+
                         " INNER JOIN "+ PstOutSourcePlanDetail.TBL_OUTSOURCEPLANDETAIL+" AS ospd ON pos."+ PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" = ospd."+ PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_POSITIONID]+
                         " INNER JOIN "+ PstOutSourcePlan.TBL_OUTSOURCEPLAN+" AS osp ON ospd."+ PstOutSourcePlanDetail.fieldNames[PstOutSourcePlanDetail.FLD_OUTSOURCEPLANID]+" = osp."+ PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_OUTSOURCEPLANID];
        
            sql = sql + " WHERE " + PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_STATUS] + " = " + statusDocPlan +
                        " AND   (" + strDate +  " BETWEEN " + PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_FROM] +  " AND " + PstOutSourcePlan.fieldNames[PstOutSourcePlan.FLD_VALID_TO]+")" ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.add(position);
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

    public static Vector listJoinOutSourcePlanLoc(int limitStart, int recordToGet, String order, SrcObject srcObject) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            
            String sql = " SELECT DISTINCT(pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+"),pos.*  FROM "+TBL_HR_POSITION+" AS pos "+
                         " INNER JOIN "+ PstOutSourcePlanLocation.TBL_OUTSOURCEPLANLOCATION+" AS ospl ON pos."+ PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]+" = ospl."+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_POSITION_ID];
            
            if (srcObject != null ) {
                sql = sql + " WHERE 1=1 " ;
                
                if (srcObject.getDivisiId() > 0 ){
                    sql = sql + " AND ospl." + PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_DIVISIONID] + " = " + srcObject.getDivisiId() ;
                }
                
            }
            
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                lists.add(position);
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
    
    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + ") FROM " + TBL_HR_POSITION;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Position position = (Position) list.get(ls);
                    if (oid == position.getOID()) {
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

    public static boolean checkMaster(long oid) {
        if (PstEmployee.checkPosition(oid)) {
            return true;
        } else {
            if (PstCareerPath.checkPosition(oid)) {
                return true;
            } else {
                return false;
            }
        }
    }

    //Gede_5Maret2012
    //ambil position {
    public static String getPositionName(String posId) {
        String position = "";
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + " FROM " + PstPosition.TBL_HR_POSITION + " WHERE " + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + "=" + posId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql1:"+sql);
            while (rs.next()) {
                position = rs.getString(1);
            }

            rs.close();
            //return relation;
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return position;
    }
    
    
    public static Long getPositionId(String posname) {
        long position = 0;
        DBResultSet dbrs = null;
        String sql = "";
        try {
            sql = "SELECT " + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + " FROM " + PstPosition.TBL_HR_POSITION + " WHERE " + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + "=" + posname;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //System.out.println("sql1:"+sql);
            while (rs.next()) {
                position = rs.getLong(1);
            }

            rs.close();
            //return relation;
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            DBResultSet.close(dbrs);
        }
        return position;
    }
    
    public static Vector getPositionByStructure(long divisionId, long departmentId){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "";
            
            sql  = "SELECT DISTINCT "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];
            sql += " FROM "+PstEmployee.TBL_HR_EMPLOYEE;
            sql += " INNER JOIN "+PstDivision.TBL_HR_DIVISION;
            sql += " ON "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID];
            sql += " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID];
            sql += " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT;
            sql += " ON "+PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            sql += " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID];
            sql += " INNER JOIN "+PstPosition.TBL_HR_POSITION;
            sql += " ON "+PstPosition.TBL_HR_POSITION+"."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];
            sql += " = "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID];
            sql += " WHERE "+PstDivision.TBL_HR_DIVISION+"."+PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+divisionId+" AND ";
            sql += PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+departmentId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                lists.add(rs.getLong(PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
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

}

