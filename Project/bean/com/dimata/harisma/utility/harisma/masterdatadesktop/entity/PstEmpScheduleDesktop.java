/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.SessEmpScheduleAttendance;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.utility.harisma.machine.ActionDataManagementEmployee;
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.PstEmployeeOutletDesktop;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya ramayu
 */
public class PstEmpScheduleDesktop extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String SESS_HR_SCHEDULE_SYMBOL = "SESS_HR_SCHEDULE_SYMBOL";
    public static final String TBL_HR_SCHEDULE_SYMBOL = "hr_schedule_symbol";//"HR_SCHEDULE_SYMBOL";
    public static final int FLD_SCHEDULE_ID = 0;
    public static final int FLD_SCHEDULE_CATEGORY_ID = 1;
    public static final int FLD_SCHEDULE = 2;
    public static final int FLD_SYMBOL = 3;
    public static final int FLD_TIME_IN = 4;
    public static final int FLD_TIME_OUT = 5;
    //Tambahan untuk special leave pada hardrock
    public static final int FLD_MAX_ENTITLE = 6;
    public static final int FLD_PERIODE = 7;
    public static final int FLD_PERIODE_TYPE = 8;
    public static final int FLD_MIN_SERVICE = 9;
    public static final int FLD_BREAK_OUT = 10;
    public static final int FLD_BREAK_IN = 11;
    public static final String[] fieldNames = {
        "SCHEDULE_ID",
        "SCHEDULE_CATEGORY_ID",
        "SCHEDULE",
        "SYMBOL",
        "TIME_IN",
        "TIME_OUT",
        //Tambahan untuk special leave pada hardrock
        "MAX_ENTITLE",
        "PERIODE",
        "PERIODE_TYPE",
        "MIN_SERVICE",
        "BREAK_OUT",
        "BREAK_IN"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };
    public static final int PERIODE_TYPE_TIME_AT_ALL = 0;
    public static final int PERIODE_TYPE_MONTH = 1;
    public static final int PERIODE_TYPE_YEAR = 2;
    public static final String[] fieldNamesPeriodeType = {
        "Time At All",
        "Month",
        "Year"
    };

    public PstEmpScheduleDesktop() {
    }

    public PstEmpScheduleDesktop(int i) throws DBException {
        super(new PstEmpScheduleDesktop());
    }

    public PstEmpScheduleDesktop(String sOid) throws DBException {
        super(new PstEmpScheduleDesktop(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstEmpScheduleDesktop(long lOid) throws DBException {
        super(new PstEmpScheduleDesktop(0));
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
        return TBL_HR_SCHEDULE_SYMBOL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstEmpScheduleDesktop().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        ScheduleSymbol schedulesymbol = fetchExc(ent.getOID());
        ent = (Entity) schedulesymbol;
        return schedulesymbol.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((ScheduleSymbol) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((ScheduleSymbol) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static ScheduleSymbol fetchExc(long oid) throws DBException {
        try {
            ScheduleSymbol schedulesymbol = new ScheduleSymbol();
            PstEmpScheduleDesktop PstEmpScheduleDesktop = new PstEmpScheduleDesktop(oid);
            schedulesymbol.setOID(oid);

            schedulesymbol.setScheduleCategoryId(PstEmpScheduleDesktop.getlong(FLD_SCHEDULE_CATEGORY_ID));
            schedulesymbol.setSchedule(PstEmpScheduleDesktop.getString(FLD_SCHEDULE));
            schedulesymbol.setSymbol(PstEmpScheduleDesktop.getString(FLD_SYMBOL));
            schedulesymbol.setTimeIn(PstEmpScheduleDesktop.getDate(FLD_TIME_IN));
            schedulesymbol.setTimeOut(PstEmpScheduleDesktop.getDate(FLD_TIME_OUT));

            schedulesymbol.setMaxEntitle(PstEmpScheduleDesktop.getInt(FLD_MAX_ENTITLE));
            schedulesymbol.setPeriode(PstEmpScheduleDesktop.getInt(FLD_PERIODE));
            schedulesymbol.setPeriodeType(PstEmpScheduleDesktop.getInt(FLD_PERIODE_TYPE));
            schedulesymbol.setMinService(PstEmpScheduleDesktop.getInt(FLD_MIN_SERVICE));
            schedulesymbol.setBreakOut(PstEmpScheduleDesktop.getDate(FLD_BREAK_OUT));
            schedulesymbol.setBreakIn(PstEmpScheduleDesktop.getDate(FLD_BREAK_IN));

            return schedulesymbol;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDesktop(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(ScheduleSymbol schedulesymbol) throws DBException {
        try {
            PstEmpScheduleDesktop PstEmpScheduleDesktop = new PstEmpScheduleDesktop(0);

            PstEmpScheduleDesktop.setLong(FLD_SCHEDULE_CATEGORY_ID, schedulesymbol.getScheduleCategoryId());
            PstEmpScheduleDesktop.setString(FLD_SCHEDULE, schedulesymbol.getSchedule());
            PstEmpScheduleDesktop.setString(FLD_SYMBOL, schedulesymbol.getSymbol());
            PstEmpScheduleDesktop.setDate(FLD_TIME_IN, schedulesymbol.getTimeIn());
            PstEmpScheduleDesktop.setDate(FLD_TIME_OUT, schedulesymbol.getTimeOut());

            PstEmpScheduleDesktop.setInt(FLD_MAX_ENTITLE, schedulesymbol.getMaxEntitle());
            PstEmpScheduleDesktop.setInt(FLD_PERIODE, schedulesymbol.getPeriode());
            PstEmpScheduleDesktop.setInt(FLD_PERIODE_TYPE, schedulesymbol.getPeriodeType());
            PstEmpScheduleDesktop.setInt(FLD_MIN_SERVICE, schedulesymbol.getMinService());
            PstEmpScheduleDesktop.setDate(FLD_BREAK_OUT, schedulesymbol.getBreakOut());
            PstEmpScheduleDesktop.setDate(FLD_BREAK_IN, schedulesymbol.getBreakIn());

            PstEmpScheduleDesktop.insert();
            schedulesymbol.setOID(PstEmpScheduleDesktop.getlong(FLD_SCHEDULE_ID));
        } catch (DBException dbe) {
            System.out.println("Exception Save Sysmbol" + dbe);
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDesktop(0), DBException.UNKNOWN);
        }
        return schedulesymbol.getOID();
    }

    public static long updateExc(ScheduleSymbol schedulesymbol) throws DBException {
        try {
            if (schedulesymbol.getOID() != 0) {
                PstEmpScheduleDesktop PstEmpScheduleDesktop = new PstEmpScheduleDesktop(schedulesymbol.getOID());

                PstEmpScheduleDesktop.setLong(FLD_SCHEDULE_CATEGORY_ID, schedulesymbol.getScheduleCategoryId());
                PstEmpScheduleDesktop.setString(FLD_SCHEDULE, schedulesymbol.getSchedule());
                PstEmpScheduleDesktop.setString(FLD_SYMBOL, schedulesymbol.getSymbol());
                PstEmpScheduleDesktop.setDate(FLD_TIME_IN, schedulesymbol.getTimeIn());
                PstEmpScheduleDesktop.setDate(FLD_TIME_OUT, schedulesymbol.getTimeOut());

                PstEmpScheduleDesktop.setInt(FLD_MAX_ENTITLE, schedulesymbol.getMaxEntitle());
                PstEmpScheduleDesktop.setInt(FLD_PERIODE, schedulesymbol.getPeriode());
                PstEmpScheduleDesktop.setInt(FLD_PERIODE_TYPE, schedulesymbol.getPeriodeType());
                PstEmpScheduleDesktop.setInt(FLD_MIN_SERVICE, schedulesymbol.getMinService());
                PstEmpScheduleDesktop.setDate(FLD_BREAK_OUT, schedulesymbol.getBreakOut());
                PstEmpScheduleDesktop.setDate(FLD_BREAK_IN, schedulesymbol.getBreakIn());

                PstEmpScheduleDesktop.update();
                return schedulesymbol.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDesktop(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstEmpScheduleDesktop PstEmpScheduleDesktop = new PstEmpScheduleDesktop(oid);
            PstEmpScheduleDesktop.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstEmpScheduleDesktop(0), DBException.UNKNOWN);
        }
        return oid;
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

    public static int getIdxNameOfTableBySelectedDate(Date selectedDate) {
        if (selectedDate != null) {
            return selectedDate.getDate();
        } else {
            return 0;
        }
    }

    /**
     * untuk mencari IN dan Out create by satrya 2014-02-18
     *
     * @param periodId
     * @param empId
     * @param scheduleId1st
     * @param scheduleId2nd
     */
    public static Vector getINandOUT(long periodId, String scheduleId1st, String scheduleId2nd, String employeeIdNotAktiv) {

        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            if (scheduleId1st != null && scheduleId1st.length() > 0) {
                //Date dtNew = new Date();
                int idxFieldName = getIdxNameOfTableBySelectedDate(new Date());

                String fieldIn = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1];
                String fieldIn2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String fieldOut = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1];
                String fieldOut2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String empSchedules = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                String empSchedules2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                String sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + ",empsch." + fieldIn
                        + ",empsch." + fieldOut + ",empsch." + fieldIn2nd
                        + ",empsch." + fieldOut2nd
                        + ",empsch." + empSchedules + ",empsch." + empSchedules2nd
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                        + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstEmpScheduleDesktop.TBL_HR_SCHEDULE_SYMBOL + " AS sym ON sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SCHEDULE_ID] + "=empsch." + empSchedules
                        + " WHERE empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId
                        + " AND (empsch." + fieldIn + " IS NULL OR empsch." + fieldOut
                        + " IS NULL OR empsch." + fieldIn2nd + " IS NULL OR empsch." + fieldOut2nd + " IS NULL)"
                        + " AND (empsch." + empSchedules + " IN(" + scheduleId1st + ") "
                        //sementara di hidden + " AND (empsch."+empSchedules + " IN("+scheduleId1st+") "
                        + (scheduleId2nd == null || scheduleId2nd.length() == 0 ? " OR empsch." + empSchedules2nd + " IN(" + scheduleId2nd + ")" : "") + ")";
                if (employeeIdNotAktiv != null && employeeIdNotAktiv.length() > 0) {
                    sql = sql + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " NOT IN(" + employeeIdNotAktiv + ")";
                }

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {
                    Date In = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    Date Out = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]));
                    Date In2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    Date Out2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    SessEmpScheduleAttendance sessEmpSchedule = new SessEmpScheduleAttendance();
                    //int idxTgl=Integer.parseInt(empSchedules.replace("D", ""));

                    sessEmpSchedule.setTimeIn(In);
                    sessEmpSchedule.setTimeIn2Nd(In2nd);
                    sessEmpSchedule.setTimeOut(Out);
                    sessEmpSchedule.setTimeOut2Nd(Out2nd);
                    sessEmpSchedule.setEmployeeId(rs.getLong("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                    sessEmpSchedule.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    sessEmpSchedule.setSymbol(rs.getString("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]));
                    sessEmpSchedule.setSchTimeIn(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]));
                    sessEmpSchedule.setSchTimeOut(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]));
                    sessEmpSchedule.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                     sessEmpSchedule.setTanggalSchedulenya(new Date());
                    result.add(sessEmpSchedule);
                }
            }

        } catch (Exception e) {
            System.out.println("get check schedule cross Exception : " + e.toString());

        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static Vector getINandOUTCrossDay(Date tanggal, long periodId, String scheduleId1st, String scheduleId2nd, String employeeIdCross) {

        DBResultSet dbrs = null;
        Vector result = new Vector();
        if (tanggal == null || (employeeIdCross != null && employeeIdCross.length() == 0)) {
            return result;
        }
        try {
            if (scheduleId1st != null && scheduleId1st.length() > 0) {
                //Date dtNew = new Date();
                int idxFieldName = getIdxNameOfTableBySelectedDate(tanggal);

                String fieldIn = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1];
                String fieldIn2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String fieldOut = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1];
                String fieldOut2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String empSchedules = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                String empSchedules2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                String sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + ",empsch." + fieldIn
                        + ",empsch." + fieldOut + ",empsch." + fieldIn2nd
                        + ",empsch." + fieldOut2nd
                        + ",empsch." + empSchedules + ",empsch." + empSchedules2nd
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                        + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstEmpScheduleDesktop.TBL_HR_SCHEDULE_SYMBOL + " AS sym ON sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SCHEDULE_ID] + "=empsch." + empSchedules
                        + " WHERE empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId
                        + " AND (empsch." + fieldIn + " IS NULL OR empsch." + fieldOut
                        + " IS NULL OR empsch." + fieldIn2nd + " IS NULL OR empsch." + fieldOut2nd + " IS NULL)";
                        //+ " AND (empsch." + empSchedules + " IN(" + scheduleId1st + ") "
                        //sementara di hidden + " AND (empsch."+empSchedules + " IN("+scheduleId1st+") "
                        //+ (scheduleId2nd == null || scheduleId2nd.length() == 0 ? " OR empsch." + empSchedules2nd + " IN(" + scheduleId2nd + ")" : "") + ")";
                if (employeeIdCross != null && employeeIdCross.length() > 0) {
                    sql = sql + " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " IN(" + employeeIdCross + ")";
                }

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {
                    Date In = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    Date Out = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]));
                    Date In2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    Date Out2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                    SessEmpScheduleAttendance sessEmpSchedule = new SessEmpScheduleAttendance();
                    //int idxTgl=Integer.parseInt(empSchedules.replace("D", ""));

                    sessEmpSchedule.setTimeIn(In);
                    sessEmpSchedule.setTimeIn2Nd(In2nd);
                    sessEmpSchedule.setTimeOut(Out);
                    sessEmpSchedule.setTimeOut2Nd(Out2nd);
                    sessEmpSchedule.setEmployeeId(rs.getLong("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                    sessEmpSchedule.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    sessEmpSchedule.setSymbol(rs.getString("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]));
                    sessEmpSchedule.setSchTimeIn(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]));
                    sessEmpSchedule.setSchTimeOut(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]));
                    sessEmpSchedule.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                    sessEmpSchedule.setTanggalSchedulenya(tanggal);
                    result.add(sessEmpSchedule);
                }
            }

        } catch (Exception e) {
            System.out.println("get check schedule cross Exception : " + e.toString());

        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    //update by satrya 2014-11-09

    
    /**
     * fungsi: cek schedule cross day
     *
     * @param whereClause
     * @param order
     * @return
     */
    public static Hashtable hashCheckScheduleCrossDay(int tambahanJam, Date tanggal, String whereClause, String order) {
        if (tanggal == null) {
            return null;
        }
        Hashtable hashCeckCrossDay = new Hashtable();
        DBResultSet dbrs = null;
        int idxFieldName = getIdxNameOfTableBySelectedDate(tanggal);
//                if((new Date().getHours()>7 && new Date().getMinutes()<=59)){
//                    //idxFieldName = getIdxNameOfTableBySelectedDate(new Date());
//                    //cek" jika ada schedule yg jam smpe jam 7 pagi
//                    return null;
//                }else{
//                    //artinya ini sudah lewat hari, jadi perlu mencari hari berikutnya
//                    idxFieldName = getIdxNameOfTableBySelectedDate(tanggal);
//                }
//        Date dtStart = new Date();
        Date dtEnd = new Date();
//        //dtStart.setHours(dtStart.getHours()-tambahanBolehABs);
//        dtStart = new Date(dtStart.getYear(), dtStart.getMonth(), (dtStart.getDate()), (dtStart.getHours() - tambahanJam), dtStart.getMinutes(), dtStart.getSeconds());
        dtEnd = new Date(dtEnd.getYear(), dtEnd.getMonth(), (dtEnd.getDate()), (dtEnd.getHours() + tambahanJam), dtEnd.getMinutes(), dtEnd.getSeconds());
        
        try {
            String sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                    + ",sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + ",sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]
                    + ",sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]
                    + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                    + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sym ON sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + "=empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            //sql = sql + " LIMIT " + 0 + ","+ 1 ;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
//            int minScheduleTimeOut = 8;
//            int maxWaktuygAda = 23;
            while (rs.next()) {
               // ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                
                Date timeIn = rs.getTime("sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]);
                Date timeOut = rs.getTime("sym." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]);
                //mengecek apakah kemarin ada cross day
                if(timeIn!=null && timeOut!=null && timeIn.getHours()>timeOut.getHours() && (timeOut!=null && ( timeOut.getHours()>=new Date().getHours() ||timeOut.getHours() >= dtEnd.getHours()))){
                    hashCeckCrossDay.put("" + rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), true);
                }

//                if (tambahanJam != 0) {
//                    timeOut.setHours(timeOut.getHours() + tambahanJam);
//                }
//                if (timeOut != null && timeIn != null && (((timeOut.getHours() == maxWaktuygAda || timeOut.getHours() < minScheduleTimeOut) && timeIn.getHours() != maxWaktuygAda) || timeIn.getHours() > timeOut.getHours())) {
//                    hashCeckCrossDay.put("" + rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]), true);
//                }
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashCeckCrossDay;
    }

    private static void resultToObject(ResultSet rs, EmpSchedule empschedule) {
        try {
            empschedule.setOID(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
            empschedule.setPeriodId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]));
            empschedule.setEmployeeId(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));

            empschedule.setD1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1]));
            empschedule.setD2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2]));
            empschedule.setD3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D3]));
            empschedule.setD4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D4]));
            empschedule.setD5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D5]));
            empschedule.setD6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D6]));
            empschedule.setD7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D7]));
            empschedule.setD8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D8]));
            empschedule.setD9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D9]));
            empschedule.setD10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D10]));
            empschedule.setD11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D11]));
            empschedule.setD12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D12]));
            empschedule.setD13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D13]));
            empschedule.setD14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D14]));
            empschedule.setD15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D15]));
            empschedule.setD16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D16]));
            empschedule.setD17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D17]));
            empschedule.setD18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D18]));
            empschedule.setD19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D19]));
            empschedule.setD20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D20]));
            empschedule.setD21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D21]));
            empschedule.setD22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D22]));
            empschedule.setD23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D23]));
            empschedule.setD24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D24]));
            empschedule.setD25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D25]));
            empschedule.setD26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D26]));
            empschedule.setD27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D27]));
            empschedule.setD28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D28]));
            empschedule.setD29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D29]));
            empschedule.setD30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D30]));
            empschedule.setD31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D31]));
            empschedule.setD2nd1(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND1]));
            empschedule.setD2nd2(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND2]));
            empschedule.setD2nd3(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND3]));
            empschedule.setD2nd4(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND4]));
            empschedule.setD2nd5(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND5]));
            empschedule.setD2nd6(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND6]));
            empschedule.setD2nd7(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND7]));
            empschedule.setD2nd8(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND8]));
            empschedule.setD2nd9(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND9]));
            empschedule.setD2nd10(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND10]));
            empschedule.setD2nd11(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND11]));
            empschedule.setD2nd12(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND12]));
            empschedule.setD2nd13(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND13]));
            empschedule.setD2nd14(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND14]));
            empschedule.setD2nd15(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND15]));
            empschedule.setD2nd16(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND16]));
            empschedule.setD2nd17(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND17]));
            empschedule.setD2nd18(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND18]));
            empschedule.setD2nd19(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND19]));
            empschedule.setD2nd20(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND20]));
            empschedule.setD2nd21(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND21]));
            empschedule.setD2nd22(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND22]));
            empschedule.setD2nd23(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND23]));
            empschedule.setD2nd24(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND24]));
            empschedule.setD2nd25(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND25]));
            empschedule.setD2nd26(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND26]));
            empschedule.setD2nd27(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND27]));
            empschedule.setD2nd28(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND28]));
            empschedule.setD2nd29(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND29]));
            empschedule.setD2nd30(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND30]));
            empschedule.setD2nd31(rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D2ND31]));

            empschedule.setStatus1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]));
            empschedule.setStatus2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2]));
            empschedule.setStatus3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS3]));
            empschedule.setStatus4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS4]));
            empschedule.setStatus5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS5]));
            empschedule.setStatus6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS6]));
            empschedule.setStatus7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS7]));
            empschedule.setStatus8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS8]));
            empschedule.setStatus9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS9]));
            empschedule.setStatus10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS10]));
            empschedule.setStatus11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS11]));
            empschedule.setStatus12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS12]));
            empschedule.setStatus13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS13]));
            empschedule.setStatus14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS14]));
            empschedule.setStatus15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS15]));
            empschedule.setStatus16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS16]));
            empschedule.setStatus17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS17]));
            empschedule.setStatus18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS18]));
            empschedule.setStatus19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS19]));
            empschedule.setStatus20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS20]));
            empschedule.setStatus21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS21]));
            empschedule.setStatus22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS22]));
            empschedule.setStatus23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS23]));
            empschedule.setStatus24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS24]));
            empschedule.setStatus25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS25]));
            empschedule.setStatus26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS26]));
            empschedule.setStatus27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS27]));
            empschedule.setStatus28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS28]));
            empschedule.setStatus29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS29]));
            empschedule.setStatus30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS30]));
            empschedule.setStatus31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS31]));
            empschedule.setStatus2nd1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND1]));
            empschedule.setStatus2nd2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND2]));
            empschedule.setStatus2nd3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND3]));
            empschedule.setStatus2nd4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND4]));
            empschedule.setStatus2nd5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND5]));
            empschedule.setStatus2nd6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND6]));
            empschedule.setStatus2nd7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND7]));
            empschedule.setStatus2nd8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND8]));
            empschedule.setStatus2nd9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND9]));
            empschedule.setStatus2nd10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND10]));
            empschedule.setStatus2nd11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND11]));
            empschedule.setStatus2nd12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND12]));
            empschedule.setStatus2nd13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND13]));
            empschedule.setStatus2nd14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND14]));
            empschedule.setStatus2nd15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND15]));
            empschedule.setStatus2nd16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND16]));
            empschedule.setStatus2nd17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND17]));
            empschedule.setStatus2nd18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND18]));
            empschedule.setStatus2nd19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND19]));
            empschedule.setStatus2nd20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND20]));
            empschedule.setStatus2nd21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND21]));
            empschedule.setStatus2nd22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND22]));
            empschedule.setStatus2nd23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND23]));
            empschedule.setStatus2nd24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND24]));
            empschedule.setStatus2nd25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND25]));
            empschedule.setStatus2nd26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND26]));
            empschedule.setStatus2nd27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND27]));
            empschedule.setStatus2nd28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND28]));
            empschedule.setStatus2nd29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND29]));
            empschedule.setStatus2nd30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND30]));
            empschedule.setStatus2nd31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS2ND31]));

            empschedule.setReason1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON1]));
            empschedule.setReason2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2]));
            empschedule.setReason3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON3]));
            empschedule.setReason4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON4]));
            empschedule.setReason5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON5]));
            empschedule.setReason6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON6]));
            empschedule.setReason7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON7]));
            empschedule.setReason8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON8]));
            empschedule.setReason9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON9]));
            empschedule.setReason10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON10]));
            empschedule.setReason11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON11]));
            empschedule.setReason12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON12]));
            empschedule.setReason13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON13]));
            empschedule.setReason14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON14]));
            empschedule.setReason15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON15]));
            empschedule.setReason16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON16]));
            empschedule.setReason17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON17]));
            empschedule.setReason18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON18]));
            empschedule.setReason19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON19]));
            empschedule.setReason20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON20]));
            empschedule.setReason21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON21]));
            empschedule.setReason22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON22]));
            empschedule.setReason23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON23]));
            empschedule.setReason24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON24]));
            empschedule.setReason25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON25]));
            empschedule.setReason26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON26]));
            empschedule.setReason27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON27]));
            empschedule.setReason28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON28]));
            empschedule.setReason29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON29]));
            empschedule.setReason30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON30]));
            empschedule.setReason31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON31]));
            empschedule.setReason2nd1(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND1]));
            empschedule.setReason2nd2(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND2]));
            empschedule.setReason2nd3(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND3]));
            empschedule.setReason2nd4(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND4]));
            empschedule.setReason2nd5(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND5]));
            empschedule.setReason2nd6(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND6]));
            empschedule.setReason2nd7(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND7]));
            empschedule.setReason2nd8(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND8]));
            empschedule.setReason2nd9(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND9]));
            empschedule.setReason2nd10(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND10]));
            empschedule.setReason2nd11(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND11]));
            empschedule.setReason2nd12(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND12]));
            empschedule.setReason2nd13(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND13]));
            empschedule.setReason2nd14(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND14]));
            empschedule.setReason2nd15(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND15]));
            empschedule.setReason2nd16(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND16]));
            empschedule.setReason2nd17(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND17]));
            empschedule.setReason2nd18(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND18]));
            empschedule.setReason2nd19(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND19]));
            empschedule.setReason2nd20(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND20]));
            empschedule.setReason2nd21(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND21]));
            empschedule.setReason2nd22(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND22]));
            empschedule.setReason2nd23(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND23]));
            empschedule.setReason2nd24(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND24]));
            empschedule.setReason2nd25(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND25]));
            empschedule.setReason2nd26(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND26]));
            empschedule.setReason2nd27(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND27]));
            empschedule.setReason2nd28(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND28]));
            empschedule.setReason2nd29(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND29]));
            empschedule.setReason2nd30(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND30]));
            empschedule.setReason2nd31(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_REASON2ND31]));

            empschedule.setNote1(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE1]));
            empschedule.setNote2(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2]));
            empschedule.setNote3(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE3]));
            empschedule.setNote4(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE4]));
            empschedule.setNote5(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE5]));
            empschedule.setNote6(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE6]));
            empschedule.setNote7(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE7]));
            empschedule.setNote8(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE8]));
            empschedule.setNote9(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE9]));
            empschedule.setNote10(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE10]));
            empschedule.setNote11(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE11]));
            empschedule.setNote12(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE12]));
            empschedule.setNote13(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE13]));
            empschedule.setNote14(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE14]));
            empschedule.setNote15(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE15]));
            empschedule.setNote16(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE16]));
            empschedule.setNote17(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE17]));
            empschedule.setNote18(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE18]));
            empschedule.setNote19(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE19]));
            empschedule.setNote20(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE20]));
            empschedule.setNote21(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE21]));
            empschedule.setNote22(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE22]));
            empschedule.setNote23(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE23]));
            empschedule.setNote24(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE24]));
            empschedule.setNote25(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE25]));
            empschedule.setNote26(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE26]));
            empschedule.setNote27(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE27]));
            empschedule.setNote28(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE28]));
            empschedule.setNote29(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE29]));
            empschedule.setNote30(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE30]));
            empschedule.setNote31(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE31]));
            empschedule.setNote2nd1(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND1]));
            empschedule.setNote2nd2(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND2]));
            empschedule.setNote2nd3(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND3]));
            empschedule.setNote2nd4(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND4]));
            empschedule.setNote2nd5(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND5]));
            empschedule.setNote2nd6(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND6]));
            empschedule.setNote2nd7(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND7]));
            empschedule.setNote2nd8(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND8]));
            empschedule.setNote2nd9(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND9]));
            empschedule.setNote2nd10(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND10]));
            empschedule.setNote2nd11(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND11]));
            empschedule.setNote2nd12(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND12]));
            empschedule.setNote2nd13(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND13]));
            empschedule.setNote2nd14(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND14]));
            empschedule.setNote2nd15(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND15]));
            empschedule.setNote2nd16(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND16]));
            empschedule.setNote2nd17(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND17]));
            empschedule.setNote2nd18(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND18]));
            empschedule.setNote2nd19(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND19]));
            empschedule.setNote2nd20(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND20]));
            empschedule.setNote2nd21(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND21]));
            empschedule.setNote2nd22(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND22]));
            empschedule.setNote2nd23(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND23]));
            empschedule.setNote2nd24(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND24]));
            empschedule.setNote2nd25(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND25]));
            empschedule.setNote2nd26(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND26]));
            empschedule.setNote2nd27(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND27]));
            empschedule.setNote2nd28(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND28]));
            empschedule.setNote2nd29(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND29]));
            empschedule.setNote2nd30(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND30]));
            empschedule.setNote2nd31(rs.getString(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_NOTE2ND31]));

            empschedule.setIn1(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN1])) : null);
            empschedule.setIn2(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2])) : null);
            empschedule.setIn3(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN3]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN3]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN3])) : null);
            empschedule.setIn4(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN4]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN4]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN4])) : null);
            empschedule.setIn5(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN5]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN5]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN5])) : null);
            empschedule.setIn6(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN6]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN6]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN6])) : null);
            empschedule.setIn7(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN7]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN7]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN7])) : null);
            empschedule.setIn8(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN8]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN8]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN8])) : null);
            empschedule.setIn9(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN9]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN9]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN9])) : null);
            empschedule.setIn10(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN10]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN10]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN10])) : null);
            empschedule.setIn11(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN11]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN11]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN11])) : null);
            empschedule.setIn12(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN12]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN12]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN12])) : null);
            empschedule.setIn13(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN13]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN13]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN13])) : null);
            empschedule.setIn14(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN14]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN14]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN14])) : null);
            empschedule.setIn15(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN15]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN15]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN15])) : null);
            empschedule.setIn16(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN16]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN16]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN16])) : null);
            empschedule.setIn17(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN17]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN17]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN17])) : null);
            empschedule.setIn18(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN18]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN18]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN18])) : null);
            empschedule.setIn19(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN19]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN19]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN19])) : null);
            empschedule.setIn20(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN20]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN20]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN20])) : null);
            empschedule.setIn21(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN21]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN21]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN21])) : null);
            empschedule.setIn22(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN22]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN22]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN22])) : null);
            empschedule.setIn23(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN23]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN23]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN23])) : null);
            empschedule.setIn24(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN24]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN24]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN24])) : null);
            empschedule.setIn25(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN25]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN25]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN25])) : null);
            empschedule.setIn26(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN26]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN26]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN26])) : null);
            empschedule.setIn27(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN27]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN27]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN27])) : null);
            empschedule.setIn28(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN28]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN28]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN28])) : null);
            empschedule.setIn29(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN29]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN29]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN29])) : null);
            empschedule.setIn30(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN30]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN30]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN30])) : null);
            empschedule.setIn31(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN31]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN31]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN31])) : null);
            empschedule.setIn2nd1(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND1]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND1])) : null);
            empschedule.setIn2nd2(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND2]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND2]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND2])) : null);
            empschedule.setIn2nd3(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND3]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND3]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND3])) : null);
            empschedule.setIn2nd4(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND4]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND4]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND4])) : null);
            empschedule.setIn2nd5(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND5]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND5]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND5])) : null);
            empschedule.setIn2nd6(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND6]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND6]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND6])) : null);
            empschedule.setIn2nd7(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND7]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND7]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND7])) : null);
            empschedule.setIn2nd8(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND8]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND8]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND8])) : null);
            empschedule.setIn2nd9(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND9]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND9]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND9])) : null);
            empschedule.setIn2nd10(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND10]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND10]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND10])) : null);
            empschedule.setIn2nd11(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND11]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND11]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND11])) : null);
            empschedule.setIn2nd12(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND12]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND12]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND12])) : null);
            empschedule.setIn2nd13(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND13]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND13]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND13])) : null);
            empschedule.setIn2nd14(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND14]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND14]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND14])) : null);
            empschedule.setIn2nd15(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND15]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND15]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND15])) : null);
            empschedule.setIn2nd16(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND16]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND16]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND16])) : null);
            empschedule.setIn2nd17(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND17]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND17]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND17])) : null);
            empschedule.setIn2nd18(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND18]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND18]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND18])) : null);
            empschedule.setIn2nd19(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND19]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND19]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND19])) : null);
            empschedule.setIn2nd20(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND20]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND20]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND20])) : null);
            empschedule.setIn2nd21(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND21]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND21]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND21])) : null);
            empschedule.setIn2nd22(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND22]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND22]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND22])) : null);
            empschedule.setIn2nd23(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND23]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND23]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND23])) : null);
            empschedule.setIn2nd24(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND24]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND24]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND24])) : null);
            empschedule.setIn2nd25(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND25]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND25]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND25])) : null);
            empschedule.setIn2nd26(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND26]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND26]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND26])) : null);
            empschedule.setIn2nd27(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND27]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND27]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND27])) : null);
            empschedule.setIn2nd28(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND28]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND28]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND28])) : null);
            empschedule.setIn2nd29(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND29]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND29]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND29])) : null);
            empschedule.setIn2nd30(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND30]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND30]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND30])) : null);
            empschedule.setIn2nd31(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND31]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND31]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_IN2ND31])) : null);

            empschedule.setOut1(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT1])) : null);
            empschedule.setOut2(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2])) : null);
            empschedule.setOut3(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT3]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT3]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT3])) : null);
            empschedule.setOut4(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT4]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT4]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT4])) : null);
            empschedule.setOut5(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT5]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT5]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT5])) : null);
            empschedule.setOut6(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT6]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT6]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT6])) : null);
            empschedule.setOut7(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT7]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT7]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT7])) : null);
            empschedule.setOut8(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT8]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT8]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT8])) : null);
            empschedule.setOut9(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT9]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT9]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT9])) : null);
            empschedule.setOut10(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT10]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT10]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT10])) : null);
            empschedule.setOut11(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT11]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT11]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT11])) : null);
            empschedule.setOut12(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT12]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT12]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT12])) : null);
            empschedule.setOut13(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT13]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT13]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT13])) : null);
            empschedule.setOut14(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT14]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT14]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT14])) : null);
            empschedule.setOut15(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT15]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT15]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT15])) : null);
            empschedule.setOut16(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT16]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT16]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT16])) : null);
            empschedule.setOut17(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT17]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT17]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT17])) : null);
            empschedule.setOut18(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT18]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT18]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT18])) : null);
            empschedule.setOut19(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT19]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT19]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT19])) : null);
            empschedule.setOut20(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT20]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT20]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT20])) : null);
            empschedule.setOut21(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT21]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT21]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT21])) : null);
            empschedule.setOut22(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT22]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT22]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT22])) : null);
            empschedule.setOut23(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT23]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT23]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT23])) : null);
            empschedule.setOut24(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT24]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT24]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT24])) : null);
            empschedule.setOut25(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT25]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT25]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT25])) : null);
            empschedule.setOut26(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT26]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT26]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT26])) : null);
            empschedule.setOut27(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT27]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT27]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT27])) : null);
            empschedule.setOut28(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT28]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT28]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT28])) : null);
            empschedule.setOut29(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT29]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT29]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT29])) : null);
            empschedule.setOut30(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT30]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT30]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT30])) : null);
            empschedule.setOut31(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT31]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT31]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT31])) : null);
            empschedule.setOut2nd1(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND1]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND1])) : null);
            empschedule.setOut2nd2(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND2]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND2]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND2])) : null);
            empschedule.setOut2nd3(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND3]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND3]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND3])) : null);
            empschedule.setOut2nd4(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND4]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND4]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND4])) : null);
            empschedule.setOut2nd5(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND5]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND5]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND5])) : null);
            empschedule.setOut2nd6(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND6]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND6]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND6])) : null);
            empschedule.setOut2nd7(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND7]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND7]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND7])) : null);
            empschedule.setOut2nd8(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND8]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND8]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND8])) : null);
            empschedule.setOut2nd9(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND9]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND9]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND9])) : null);
            empschedule.setOut2nd10(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND10]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND10]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND10])) : null);
            empschedule.setOut2nd11(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND11]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND11]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND11])) : null);
            empschedule.setOut2nd12(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND12]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND12]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND12])) : null);
            empschedule.setOut2nd13(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND13]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND13]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND13])) : null);
            empschedule.setOut2nd14(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND14]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND14]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND14])) : null);
            empschedule.setOut2nd15(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND15]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND15]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND15])) : null);
            empschedule.setOut2nd16(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND16]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND16]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND16])) : null);
            empschedule.setOut2nd17(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND17]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND17]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND17])) : null);
            empschedule.setOut2nd18(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND18]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND18]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND18])) : null);
            empschedule.setOut2nd19(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND19]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND19]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND19])) : null);
            empschedule.setOut2nd20(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND20]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND20]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND20])) : null);
            empschedule.setOut2nd21(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND21]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND21]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND21])) : null);
            empschedule.setOut2nd22(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND22]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND22]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND22])) : null);
            empschedule.setOut2nd23(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND23]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND23]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND23])) : null);
            empschedule.setOut2nd24(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND24]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND24]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND24])) : null);
            empschedule.setOut2nd25(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND25]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND25]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND25])) : null);
            empschedule.setOut2nd26(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND26]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND26]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND26])) : null);
            empschedule.setOut2nd27(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND27]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND27]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND27])) : null);
            empschedule.setOut2nd28(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND28]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND28]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND28])) : null);
            empschedule.setOut2nd29(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND29]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND29]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND29])) : null);
            empschedule.setOut2nd30(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND30]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND30]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND30])) : null);
            empschedule.setOut2nd31(rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND31]) != null ? PstEmpSchedule.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND31]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_OUT2ND31])) : null);

            empschedule.setScheduleType(rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE]));

        } catch (Exception e) {
        }
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE;
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
            //System.out.println("Listku....."+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                EmpSchedule empschedule = new EmpSchedule();
                resultToObject(rs, empschedule);
                lists.add(empschedule);

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
     * get schedule data of selected date per selected employee
     *
     * @param selectedDate
     * @param employeeId
     * @return
     * @created by Edhy
     */
    public static ScheduleSymbol getDailyFirstSchedule(Date selectedDate, long employeeId) {
        ScheduleSymbol objScheduleSymbol = new ScheduleSymbol();

        long periodId = PstPeriodDesktop.getPeriodeIdBetween(selectedDate);
        String whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                + " = " + periodId
                + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                + " = " + employeeId;
        Vector vectEmpSchedule = PstEmpScheduleDesktop.list(0, 0, whereClause, "");
        if (vectEmpSchedule != null && vectEmpSchedule.size() > 0) {
            int maxEmpSchedule = vectEmpSchedule.size();
            EmpSchedule objEmpSchedule = new EmpSchedule();
            for (int i = 0; i < maxEmpSchedule; i++) {
                objEmpSchedule = (EmpSchedule) vectEmpSchedule.get(i);
                break;
            }

            long scheduleSymbolOid = 0;
            if (objEmpSchedule.getOID() != 0) {
                int idxSelectedDate = selectedDate.getDate();
                switch (idxSelectedDate) {
                    case 1:
                        scheduleSymbolOid = objEmpSchedule.getD1();
                        break;

                    case 2:
                        scheduleSymbolOid = objEmpSchedule.getD2();
                        break;

                    case 3:
                        scheduleSymbolOid = objEmpSchedule.getD3();
                        break;

                    case 4:
                        scheduleSymbolOid = objEmpSchedule.getD4();
                        break;

                    case 5:
                        scheduleSymbolOid = objEmpSchedule.getD5();
                        break;

                    case 6:
                        scheduleSymbolOid = objEmpSchedule.getD6();
                        break;

                    case 7:
                        scheduleSymbolOid = objEmpSchedule.getD7();
                        break;

                    case 8:
                        scheduleSymbolOid = objEmpSchedule.getD8();
                        break;

                    case 9:
                        scheduleSymbolOid = objEmpSchedule.getD9();
                        break;

                    case 10:
                        scheduleSymbolOid = objEmpSchedule.getD10();
                        break;

                    case 11:
                        scheduleSymbolOid = objEmpSchedule.getD11();
                        break;

                    case 12:
                        scheduleSymbolOid = objEmpSchedule.getD12();
                        break;

                    case 13:
                        scheduleSymbolOid = objEmpSchedule.getD13();
                        break;

                    case 14:
                        scheduleSymbolOid = objEmpSchedule.getD14();
                        break;

                    case 15:
                        scheduleSymbolOid = objEmpSchedule.getD15();
                        break;

                    case 16:
                        scheduleSymbolOid = objEmpSchedule.getD16();
                        break;

                    case 17:
                        scheduleSymbolOid = objEmpSchedule.getD17();
                        break;

                    case 18:
                        scheduleSymbolOid = objEmpSchedule.getD18();
                        break;

                    case 19:
                        scheduleSymbolOid = objEmpSchedule.getD19();
                        break;

                    case 20:
                        scheduleSymbolOid = objEmpSchedule.getD20();
                        break;

                    case 21:
                        scheduleSymbolOid = objEmpSchedule.getD21();
                        break;

                    case 22:
                        scheduleSymbolOid = objEmpSchedule.getD22();
                        break;

                    case 23:
                        scheduleSymbolOid = objEmpSchedule.getD23();
                        break;

                    case 24:
                        scheduleSymbolOid = objEmpSchedule.getD24();
                        break;

                    case 25:
                        scheduleSymbolOid = objEmpSchedule.getD25();
                        break;

                    case 26:
                        scheduleSymbolOid = objEmpSchedule.getD26();
                        break;

                    case 27:
                        scheduleSymbolOid = objEmpSchedule.getD27();
                        break;

                    case 28:
                        scheduleSymbolOid = objEmpSchedule.getD28();
                        break;

                    case 29:
                        scheduleSymbolOid = objEmpSchedule.getD29();
                        break;

                    case 30:
                        scheduleSymbolOid = objEmpSchedule.getD30();
                        break;

                    case 31:
                        scheduleSymbolOid = objEmpSchedule.getD31();
                        break;
                }
            }


            try {
                objScheduleSymbol = PstEmpScheduleDesktop.fetchExc(scheduleSymbolOid);
            } catch (Exception e) {
                System.out.println("Exc when fetch Schedule Symbol : " + e.toString());
            }
        }

        return objScheduleSymbol;
    }

    /**
     * keterangan: untuk menampilkan data employee di aplication desktop create
     * by satrya 2014-02-20
     *
     * @param periodId
     * @param scheduleId1st
     * @param scheduleId2nd
     * @param empNum
     * @param fullName
     * @param position
     * @param schedule
     * @param statusEmp
     * @return
     */
    public static Vector getDataEmployeeInDesktop(long periodId, String scheduleId1st, String scheduleId2nd, String empNum, String fullName, String position, String schedule, int statusEmp) {
        if (periodId == 0 || scheduleId1st == null || scheduleId1st.length() == 0) {
            return new Vector();
        }
        String oidPosition = PstKonfigurasiOutletSetting.oidPosition();
        Hashtable hashOidPosition = new Hashtable();
        if (oidPosition != null && oidPosition.length() > 0) {
            String sOIdPosition[] = oidPosition.split(",");
            if (sOIdPosition != null && sOIdPosition.length > 0) {
                for (int x = 0; x < sOIdPosition.length; x++) {
                    hashOidPosition.put("" + sOIdPosition[x], true);
                }
            }
        }
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
                        whereClause = whereClause + " emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
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
                        whereClause = whereClause + " emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
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
                        whereClause = whereClause + " pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
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
                        whereClause = whereClause + " sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (statusEmp != 0) {
            //masih di tanyakan
        }
        DBResultSet dbrs = null;
        Vector result = new Vector();
        try {
            if (scheduleId1st != null && scheduleId1st.length() > 0) {
                int idxFieldName = getIdxNameOfTableBySelectedDate(new Date());
                String fieldIn = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1];
                String fieldIn2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String fieldOut = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1];
                String fieldOut2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1];//"IN" + date.getDate();

                String empSchedules = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                String empSchedules2nd = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldName - 1];
                Date dtStart = new Date();
                dtStart.setHours(0);
                dtStart.setMinutes(0);
                dtStart.setSeconds(0);
                Date dtEnd = new Date();
                dtEnd.setHours(23);
                dtEnd.setMinutes(59);
                String sql = "SELECT IF(\"" + Formater.formatDate(dtEnd, "yyyy-MM-dd HH:mm") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                        + " AND empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO]
                        + " >= \"" + Formater.formatDate(dtStart, "yyyy-MM-dd HH:mm") + "\" ||  emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " IN (" + oidPosition + "),\"" + ActionDataManagementEmployee.STATUS_AKTIV + "\",\"" + ActionDataManagementEmployee.STATUS_NON_AKTIV + "\") AS stsemp"
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMG]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + ",empsch." + fieldIn
                        + ",empsch." + fieldOut + ",empsch." + fieldIn2nd
                        + ",empsch." + fieldOut2nd
                        + ",empsch." + empSchedules + ",empsch." + empSchedules2nd
                        + ",empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]
                        + ",sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]
                        + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                        + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch "
                        + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        + " INNER JOIN " + PstEmpScheduleDesktop.TBL_HR_SCHEDULE_SYMBOL + " AS sym ON sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SCHEDULE_ID] + "=empsch." + empSchedules
                        + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        //update by satrya 2014-09-18
                        + " AND "
                        + "\"" + Formater.formatDate(dtEnd, "yyyy-MM-dd 23:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + ">=\"" + Formater.formatDate(dtStart, "yyyy-MM-dd 00:00") + "\""
                        + " WHERE empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=" + periodId
                        //+ " OR (empsch."+empSchedules + " IN("+scheduleId1st+") "
                        + " AND (empsch." + empSchedules + " IN(" + scheduleId1st + ") "
                        //sementara saja + " OR (empsch."+empSchedules + " IN("+scheduleId1st+") "
                        + (scheduleId2nd == null || scheduleId2nd.length() == 0 ? " OR empsch." + empSchedules2nd + " IN(" + scheduleId2nd + ")" : "") + ")"
                        + (whereClause != null && whereClause.length() > 0 ? " AND " + whereClause : "")
                        + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                Hashtable hashEMployeeIdExistToday = new Hashtable();
                while (rs.next()) {
                    long employeeId = rs.getLong("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]);
                    long oidPositionQuery = rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]);//berguna untuk oidKadiv;
                    if ((statusEmp == rs.getInt("stsemp") || statusEmp == 3) && (hashEMployeeIdExistToday.size() == 0 || (hashEMployeeIdExistToday != null && hashEMployeeIdExistToday.size() > 0 && hashEMployeeIdExistToday.containsKey("" + employeeId) == false))) {
                        hashEMployeeIdExistToday.put("" + employeeId, false);
                        Date In = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                        Date Out = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1]));
                        Date In2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                        Date Out2nd = DBHandler.convertDate(rs.getDate(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_OUT + PstEmpSchedule.INTERVAL_INDEX_HALF_CALENDAR + idxFieldName - 1]), rs.getTime(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1]));
                        SessEmpScheduleAttendance sessEmpSchedule = new SessEmpScheduleAttendance();
                        //int idxTgl=Integer.parseInt(empSchedules.replace("D", ""));

                        sessEmpSchedule.setTimeIn(In);
                        sessEmpSchedule.setTimeIn2Nd(In2nd);
                        sessEmpSchedule.setTimeOut(Out);
                        sessEmpSchedule.setTimeOut2Nd(Out2nd);
                        sessEmpSchedule.setEmployeeId(rs.getLong("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]));
                        sessEmpSchedule.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                        sessEmpSchedule.setSymbol(rs.getString("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_SYMBOL]));
                        sessEmpSchedule.setSchTimeIn(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_IN]));
                        sessEmpSchedule.setSchTimeOut(rs.getTime("sym." + PstEmpScheduleDesktop.fieldNames[PstEmpScheduleDesktop.FLD_TIME_OUT]));
                        sessEmpSchedule.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));
                        sessEmpSchedule.setEmployeeNum(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                        sessEmpSchedule.setPosition(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                        sessEmpSchedule.setAlamat(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                        sessEmpSchedule.setAlamatPermanent(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                        sessEmpSchedule.setAlamatEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]));
                        sessEmpSchedule.setHandphone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                        sessEmpSchedule.setPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                        sessEmpSchedule.setPhoneEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMG]));
                        //update by satrya 2014-09-19 if(oidPosition==oidPositionQuery){
                        if (hashOidPosition != null && hashOidPosition.size() > 0 && hashOidPosition.containsKey("" + oidPositionQuery)) {
                            sessEmpSchedule.setStatusEmpTrans(ActionDataManagementEmployee.STATUS_AKTIV);
                        } else {
                            sessEmpSchedule.setStatusEmpTrans(rs.getInt("stsemp"));
                        }

                        result.add(sessEmpSchedule);
                    }



                }
            }

        } catch (Exception e) {
            System.out.println("get check schedule cross Exception : " + e.toString());

        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
}
