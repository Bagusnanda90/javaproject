
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.utility.service.bkpdtoutlet;

/* package java */
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.*;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;


/**
 * Ari_20111002 Menambah Company, Division, Level dan EmpCategory
 *
 * @author Wiweka
 */
public class PstBakupPositionDestopTransfer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

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
    public static final int FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT = 15;
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
        "FLAG_POSITION_SHOW_IN_PAYROLL_INPUT"
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
        TYPE_INT
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
        "Staff",
        "Secretary",
        "Supervisor",
        "Asst. Manager",
        "Manager",
        "Asst. Director",
        "Director",
        "VP/General Manager"
    };
    public static final String[] strPositionLevelValue = {
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7"
    };
    public static final int[] strPositionLevelInt = {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7
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
        "Board of Director",};
    public static final int[] strHeadTitleInt = {
        0,
        1,
        2,
        3,
        4,
        5,};
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
    public static final int UPDATE_SCHEDULE_BEFORE_TIME = 0;
    public static final int UPDATE_SCHEDULE_AFTER_TIME = 1;
    public static final int UPDATE_SCHEDULE_LEAVE_BEFORE_TIME = 2;
    public static final int UPDATE_SCHEDULE_LEAVE_AFTER_TIME = 3;
    public static final int HOUR_UNLIMITED = 8640; /* 360 hari x 24 jam */

    public static final int NO_HOUR = -1;   /* value tidak bisa merubah schedule */


    public PstBakupPositionDestopTransfer() {
    }

    public PstBakupPositionDestopTransfer(int i) throws DBException {
        super(new PstBakupPositionDestopTransfer());
    }

    public PstBakupPositionDestopTransfer(String sOid) throws DBException {
        super(new PstBakupPositionDestopTransfer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBakupPositionDestopTransfer(long lOid) throws DBException {
        super(new PstBakupPositionDestopTransfer(0));
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
        return new PstBakupPositionDestopTransfer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static String insertExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        String sql="";
        try {
            PstBakupPositionDestopTransfer PstBakupPositionDestopTransfer = new PstBakupPositionDestopTransfer(0);

            PstBakupPositionDestopTransfer.setString(FLD_POSITION, tabelEmployeeOutletTransferData.getPositionName());

            sql =PstBakupPositionDestopTransfer.SyntacInsert(tabelEmployeeOutletTransferData.getPositionId());
        }catch (Exception e) {
            //throw new DBException(new PstBakupPositionDestopTransfer(0), DBException.UNKNOWN);
            System.out.println("Exc"+e);
        }
        return sql;
    }

    public static long updateExc(TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData) throws DBException {
        try {
            if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                PstBakupPositionDestopTransfer PstBakupPositionDestopTransfer = new PstBakupPositionDestopTransfer(tabelEmployeeOutletTransferData.getPositionId());


                PstBakupPositionDestopTransfer.setString(FLD_POSITION, tabelEmployeeOutletTransferData.getPositionName());


                PstBakupPositionDestopTransfer.update();
                return tabelEmployeeOutletTransferData.getPositionId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupPositionDestopTransfer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstBakupPositionDestopTransfer pstCareerPath = new PstBakupPositionDestopTransfer(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBakupPositionDestopTransfer(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION + " WHERE "
                    + PstBakupPositionDestopTransfer.fieldNames[PstBakupPositionDestopTransfer.FLD_POSITION_ID] + " = " + positionId;

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

    public static Hashtable<String, Boolean> hashPositionSdhAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashPositionSdhAda = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + fieldNames[FLD_POSITION_ID] + " FROM " + TBL_HR_POSITION;
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
                hashPositionSdhAda.put("" + rs.getLong(fieldNames[FLD_POSITION_ID]), true);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashPositionSdhAda;
        }
    }

    public static void resultToObject(ResultSet rs, Position position) {

        try {

            position.setOID(rs.getLong(fieldNames[FLD_POSITION_ID]));
            position.setPosition(rs.getString(fieldNames[FLD_POSITION]));
            position.setDescription(rs.getString(fieldNames[FLD_DESCRIPTION]));
            position.setPositionLevel(rs.getInt(fieldNames[FLD_POSITION_LEVEL]));

            position.setDisabledAppUnderSupervisor(rs.getInt(fieldNames[FLD_DISABLED_APP_UNDER_SUPERVISOR]));
            position.setDisabledAppDeptScope(rs.getInt(fieldNames[FLD_DISABLED_APP_DEPT_SCOPE]));
            position.setDisabedAppDivisionScope(rs.getInt(fieldNames[FLD_DISABLED_APP_DIV_SCOPE]));
            position.setAllDepartment(rs.getInt(fieldNames[FLD_ALL_DEPARTMENT]));
            position.setHeadTitle(rs.getInt(fieldNames[FLD_HEAD_TITLE]));
            position.setPositionLevelPayrol(rs.getInt(fieldNames[FLD_POSITION_LEVEL_PAYROL]));
            position.setKodePosition(rs.getString(fieldNames[FLD_POSITION_KODE]));
            position.setFlagShowPayInput(rs.getInt(fieldNames[FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT]));
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
    }

    
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector list = new Vector();
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
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Position position = new Position();
                resultToObject(rs, position);
                list.add(position);
            }
            rs.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return list;
        }

    }
}
