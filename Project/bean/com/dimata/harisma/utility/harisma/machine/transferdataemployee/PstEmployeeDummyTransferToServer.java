/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.*;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.session.aplikasidesktop.attendance.SessDestopApplication;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstEmployeeDummyTransferToServer extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TRANSFER_EMPLOYEE_OUTLET = "transfer_employee_outlet";//"HR_EDUCATION";
    public static final int FLD_TRANSFER_EMPLOYEE_ID = 0;
    public static final int FLD_EMPLOYEE_NUMBER = 1;
    public static final int FLD_EMPLOYEE_PIN = 2;
    public static final int FLD_EMPLOYEE_FULL_NAME = 3;
    public static final int FLD_STATUS_EMPLOYEE = 4;
    public static final int FLD_POSITION_ID = 5;
    public static final int FLD_SCHEDULE_ID = 6;
    public static final int FLD_DATE_CREATE_TRANSFER = 7;
    public static final String[] fieldNames = {
        "TRANSFER_EMPLOYEE_ID",
        "EMPLOYEE_NUM",
        "EMP_PIN",
        "EMPLOYEE_FULL_NAME",
        "STATUS_EMPLOYEE",
        "POSITION_ID",
        "SCHEDULE_ID",
        "DATE_CREATE_TRANSFER"
    };
    public static final int[] fieldTypes = {
        //TYPE_LONG + TYPE_PK + TYPE_ID,
        //TYPE_STRING,
        TYPE_LONG + TYPE_PK + TYPE_ID,// "KONFIGURASI_ID",
        TYPE_STRING,//"URL_IMAGES",
        TYPE_STRING,//"URL_IMAGES",
        TYPE_STRING,//"URL_IMAGES_BUTTON_ICON",
        TYPE_INT,//"URL_LETAK_MYSQL_EXE",
        TYPE_LONG,//"USERNAME_MYSQL",
        TYPE_LONG,//"PASSWORD_MYSQL",
        TYPE_DATE
    };

    public PstEmployeeDummyTransferToServer() {
    }

    public PstEmployeeDummyTransferToServer(int i) throws DBException {
        super(new PstEmployeeDummyTransferToServer());
    }

    public PstEmployeeDummyTransferToServer(String sOid) throws DBException {
        super(new PstEmployeeDummyTransferToServer(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmployeeDummyTransferToServer(long lOid) throws DBException {
        super(new PstEmployeeDummyTransferToServer(0));
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
        return TBL_TRANSFER_EMPLOYEE_OUTLET;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

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

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmployeeDummyTransferToServer().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        EmployeeDummyTransfer employeeDummyTransfer = fetchExc(ent.getOID());
        ent = (Entity) employeeDummyTransfer;
        return employeeDummyTransfer.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((EmployeeDummyTransfer) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((EmployeeDummyTransfer) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static EmployeeDummyTransfer fetchExc(long oid) throws DBException {
        try {
            EmployeeDummyTransfer employeeDummyTransfer = new EmployeeDummyTransfer();
            PstEmployeeDummyTransferToServer pstEmployeeDummyTransfer = new PstEmployeeDummyTransferToServer(oid);
            employeeDummyTransfer.setOID(oid);

            employeeDummyTransfer.setEmployeeFullName(pstEmployeeDummyTransfer.getString(FLD_EMPLOYEE_FULL_NAME));
            employeeDummyTransfer.setEmployeePin(pstEmployeeDummyTransfer.getString(FLD_EMPLOYEE_PIN));
            employeeDummyTransfer.setEmployeeNum(pstEmployeeDummyTransfer.getString(FLD_EMPLOYEE_NUMBER));
            employeeDummyTransfer.setPositionId(pstEmployeeDummyTransfer.getlong(FLD_POSITION_ID));
            employeeDummyTransfer.setScheduleId(pstEmployeeDummyTransfer.getlong(FLD_SCHEDULE_ID));
            employeeDummyTransfer.setStatusEmployee(pstEmployeeDummyTransfer.getInt(FLD_STATUS_EMPLOYEE));
            employeeDummyTransfer.setDtTransferAdd(pstEmployeeDummyTransfer.getDate(FLD_DATE_CREATE_TRANSFER));

            return employeeDummyTransfer;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDummyTransferToServer(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(EmployeeDummyTransfer employeeDummyTransfer) throws DBException {
        try {
            PstEmployeeDummyTransferToServer pstEmployeeDummyTransfer = new PstEmployeeDummyTransferToServer(0);

            pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_FULL_NAME, employeeDummyTransfer.getEmployeeFullName());
            pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_PIN, employeeDummyTransfer.getEmployeePin());
            pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_NUMBER, employeeDummyTransfer.getEmployeeNum());
            pstEmployeeDummyTransfer.setLong(FLD_POSITION_ID, employeeDummyTransfer.getPositionId());
            pstEmployeeDummyTransfer.setLong(FLD_SCHEDULE_ID, employeeDummyTransfer.getScheduleId());
            pstEmployeeDummyTransfer.setInt(FLD_STATUS_EMPLOYEE, employeeDummyTransfer.getStatusEmployee());
             pstEmployeeDummyTransfer.setDate(FLD_DATE_CREATE_TRANSFER, employeeDummyTransfer.getDtTransferAdd());


            pstEmployeeDummyTransfer.insert(employeeDummyTransfer.getOID());
            //employeeDummyTransfer.setOID(pstEmployeeDummyTransfer.getlong(FLD_TRANSFER_EMPLOYEE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDummyTransferToServer(0), DBException.UNKNOWN);
        }
        return employeeDummyTransfer.getOID();
    }

    public static long updateExc(EmployeeDummyTransfer employeeDummyTransfer) throws DBException {
        try {
            if (employeeDummyTransfer.getOID() != 0) {
                PstEmployeeDummyTransferToServer pstEmployeeDummyTransfer = new PstEmployeeDummyTransferToServer(employeeDummyTransfer.getOID());

                pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_FULL_NAME, employeeDummyTransfer.getEmployeeFullName());
                pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_PIN, employeeDummyTransfer.getEmployeePin());
                pstEmployeeDummyTransfer.setString(FLD_EMPLOYEE_NUMBER, employeeDummyTransfer.getEmployeeNum());
                pstEmployeeDummyTransfer.setLong(FLD_POSITION_ID, employeeDummyTransfer.getPositionId());
                pstEmployeeDummyTransfer.setLong(FLD_SCHEDULE_ID, employeeDummyTransfer.getScheduleId());
                pstEmployeeDummyTransfer.setInt(FLD_STATUS_EMPLOYEE, employeeDummyTransfer.getStatusEmployee());
                    pstEmployeeDummyTransfer.setDate(FLD_DATE_CREATE_TRANSFER, employeeDummyTransfer.getDtTransferAdd());
               

                pstEmployeeDummyTransfer.update();
                return employeeDummyTransfer.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDummyTransferToServer(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmployeeDummyTransferToServer pstEmployeeDummyTransfer = new PstEmployeeDummyTransferToServer(oid);
            pstEmployeeDummyTransfer.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmployeeDummyTransferToServer(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET;
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
                EmployeeDummyTransfer employeeDummyTransfer = new EmployeeDummyTransfer();
                resultToObject(rs, employeeDummyTransfer);
                lists.add(employeeDummyTransfer);
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

    public static void resultToObject(ResultSet rs, EmployeeDummyTransfer employeeDummyTransfer) {
        try {
            employeeDummyTransfer.setOID(rs.getLong(fieldNames[FLD_TRANSFER_EMPLOYEE_ID]));

            employeeDummyTransfer.setEmployeeFullName(rs.getString(fieldNames[FLD_EMPLOYEE_FULL_NAME]));
            employeeDummyTransfer.setEmployeePin(rs.getString(fieldNames[FLD_EMPLOYEE_PIN]));
            employeeDummyTransfer.setEmployeeNum(rs.getString(fieldNames[FLD_EMPLOYEE_NUMBER]));
            employeeDummyTransfer.setPositionId(rs.getLong(fieldNames[FLD_POSITION_ID]));
            employeeDummyTransfer.setScheduleId(rs.getLong(fieldNames[FLD_SCHEDULE_ID]));
            employeeDummyTransfer.setStatusEmployee(rs.getInt(fieldNames[FLD_STATUS_EMPLOYEE]));
            employeeDummyTransfer.setDtTransferAdd(rs.getDate(fieldNames[FLD_DATE_CREATE_TRANSFER]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long employeeDummyTransferId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET + " WHERE "
                    + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_TRANSFER_EMPLOYEE_ID] + " = " + employeeDummyTransferId;

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

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_TRANSFER_EMPLOYEE_ID] + ") FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET;
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

    public static long getOid(String whereClause, int limitStart, int recordToGet) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_TRANSFER_EMPLOYEE_ID] + " FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long count = 0;
            while (rs.next()) {
                count = rs.getLong(1);
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
                    EmployeeDummyTransfer employeeDummyTransfer = (EmployeeDummyTransfer) list.get(ls);
                    if (oid == employeeDummyTransfer.getOID()) {
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
    /* This method used to find command where current data */

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
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

    public static boolean checkMaster(long oid) {
        if (PstEmployeeDummyTransferToServer.checkMaster(oid)) {
            return true;
        } else {
            return false;
        }
    }

    public static int getIdxNameOfTableBySelectedDate(Date selectedDate) {
        if (selectedDate != null) {
            return selectedDate.getDate();
        } else {
            return 0;
        }
    }
/**
 * 
 * @param empNum
 * @param fullName
 * @param position
 * @param schedule
 * @param statusEmp
 * @return 
 */
    public static Vector getDataEmployeeInDesktop(String empNum, String fullName, String position, String schedule, int statusEmp) {
        
        String whereClause = "(1=1)";
        if (empNum != null && empNum.length() > 0) {
            Vector vectName = logicParser(empNum);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_NUMBER]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (fullName != null && fullName.length() > 0) {
            Vector vectName = logicParser(fullName);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }

        if (position != null && position.length() > 0) {
            Vector vectName = logicParser(position);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (schedule != null && schedule.length() > 0) {
            Vector vectName = logicParser(schedule);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (statusEmp != 3) {
           whereClause = whereClause + " AND "+fieldNames[FLD_STATUS_EMPLOYEE]+"="+statusEmp;
        }
        whereClause = whereClause + " AND "+fieldNames[FLD_DATE_CREATE_TRANSFER] + " between \"" +Formater.formatDate(new Date(), "yyyy-MM-dd 00:00") +"\" AND \"" +Formater.formatDate(new Date(), "yyyy-MM-dd 23:59")+"\"";
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
                
                String sql = "SELECT teo.*,"
                        + " pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION] + ",sch." + PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE] 
                        + " ,sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_TIME_IN]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_TIME_OUT]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_BREAK_IN]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_BREAK_OUT]
                        + " ,sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SYMBOL]
                        + " FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET + " AS teo "
                        + " INNER JOIN " + PstPositionDestopTransfer.TBL_HR_POSITION + " AS pos ON pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION_ID] + "=teo." + fieldNames[FLD_POSITION_ID]
                        + " INNER JOIN " + PstScheduleDestopTransfer.TBL_HR_SCHEDULE_SYMBOL + " AS sch ON sch." + PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE_ID] + "=teo." + fieldNames[FLD_SCHEDULE_ID];
                sql = sql + " WHERE "+whereClause;
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {

                    EmployeeDummyTransfer employeeDummyTransfer = new EmployeeDummyTransfer();
                    employeeDummyTransfer.setEmployeeFullName(rs.getString("teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_FULL_NAME]));
                    employeeDummyTransfer.setEmployeeNum(rs.getString("teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_NUMBER]));
                    employeeDummyTransfer.setEmployeePin(rs.getString("teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_PIN]));
                    employeeDummyTransfer.setPositionId(rs.getLong("teo." + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_POSITION_ID]));
                    employeeDummyTransfer.setPositionName(rs.getString("pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION]));
                    employeeDummyTransfer.setSchBreakIn(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_BREAK_IN]));
                    employeeDummyTransfer.setSchBreakOut(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_BREAK_OUT]));
                    employeeDummyTransfer.setSchTimeIn(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]));
                    employeeDummyTransfer.setSchTimeOut(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]));
                    employeeDummyTransfer.setScheduleId(rs.getLong(PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_SCHEDULE_ID]));
                    employeeDummyTransfer.setScheduleName(rs.getString(PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SYMBOL]));
                    employeeDummyTransfer.setOID(rs.getLong("teo."+PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_TRANSFER_EMPLOYEE_ID]));
                    employeeDummyTransfer.setStatusEmployee(rs.getInt("teo."+PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_STATUS_EMPLOYEE]));
                    
                    result.add(employeeDummyTransfer);
                }
                rs.close();

        } catch (Exception e) {
            System.out.println("get check schedule cross Exception : " + e.toString());

        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    /**
     * untuk list di dekstop
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listEmployeeDesktop(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
                  String sql = "SELECT emp.*," 
                        + " pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION] + ",sch." + PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE] 
                        + " ,sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_TIME_IN]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_TIME_OUT]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_BREAK_IN]+",sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_BREAK_OUT]
                        + " ,sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SYMBOL]
                        + " ,sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE]
                        + " FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET + " AS emp "
                        + " INNER JOIN " + PstPositionDestopTransfer.TBL_HR_POSITION + " AS pos ON pos." + PstPositionDestopTransfer.fieldNames[PstPositionDestopTransfer.FLD_POSITION_ID] + "=emp." + fieldNames[FLD_POSITION_ID]
                        + " INNER JOIN " + PstScheduleDestopTransfer.TBL_HR_SCHEDULE_SYMBOL + " AS sch ON sch." + PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE_ID] + "=emp." + fieldNames[FLD_SCHEDULE_ID];

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
            //System.out.println("list employee list of employee  " + sql);
            while (rs.next()) {
                SessDestopApplication sessDestopApplication = new SessDestopApplication();
                sessDestopApplication.setEmpNumber(rs.getString("emp."+PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_NUMBER]));
                sessDestopApplication.setNamaEmployee(rs.getString("emp."+PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_FULL_NAME]));
                sessDestopApplication.setPositionName(rs.getString("pos."+PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                sessDestopApplication.setBarcodeNumber(rs.getString("emp."+PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_EMPLOYEE_NUMBER]));
                sessDestopApplication.setSchBreakIn(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_BREAK_IN]));
                sessDestopApplication.setSchBreakOut(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_BREAK_OUT]));
                sessDestopApplication.setSchTimeIn(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]));
                sessDestopApplication.setSchTimeOut(rs.getTime("sch." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]));
                sessDestopApplication.setScheduleSymbolId(rs.getLong(PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_SCHEDULE_ID]));
                sessDestopApplication.setSymbol(rs.getString(PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SYMBOL]));
                sessDestopApplication.setKetSymbol(rs.getString("sch."+PstScheduleDestopTransfer.fieldNames[PstScheduleDestopTransfer.FLD_SCHEDULE]));
                
                   
                lists.add(sessDestopApplication);
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
    
    public static Hashtable<String,Boolean> hashEmpEmpDummyAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashEmpEmpDummyAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_TRANSFER_EMPLOYEE_ID]+" FROM " + TBL_TRANSFER_EMPLOYEE_OUTLET;
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
                hashEmpEmpDummyAda.put(""+rs.getLong(fieldNames[FLD_TRANSFER_EMPLOYEE_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashEmpEmpDummyAda;
        }
        
    }
}
