/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.payroll;

/**
 * Description :
 * Date :
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class PstCustomRptMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CUSTOM_RPT_MAIN = "hr_custom_rpt_main";
    public static final int FLD_RPT_MAIN_ID = 0;
    public static final int FLD_RPT_MAIN_TITLE = 1;
    public static final int FLD_RPT_MAIN_DESC = 2;
    public static final int FLD_RPT_MAIN_DATE_CREATE = 3;
    public static final int FLD_RPT_MAIN_CREATED_BY = 4;
    public static final int FLD_RPT_MAIN_PRIV_LEVEL = 5;
    public static final int FLD_RPT_MAIN_PRIV_POS = 6;
    public static String[] fieldNames = {
        "RPT_MAIN_ID",
        "RPT_MAIN_TITLE",
        "RPT_MAIN_DESC",
        "RPT_MAIN_DATE_CREATE",
        "RPT_MAIN_CREATED_BY",
        "RPT_MAIN_PRIV_LEVEL",
        "RPT_MAIN_PRIV_POS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstCustomRptMain() {
    }

    public PstCustomRptMain(int i) throws DBException {
        super(new PstCustomRptMain());
    }

    public PstCustomRptMain(String sOid) throws DBException {
        super(new PstCustomRptMain(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCustomRptMain(long lOid) throws DBException {
        super(new PstCustomRptMain(0));
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
        return TBL_CUSTOM_RPT_MAIN;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCustomRptMain().getClass().getName();
    }

    public static CustomRptMain fetchExc(long oid) throws DBException {
        try {
            CustomRptMain entCustomRptMain = new CustomRptMain();
            PstCustomRptMain pstCustomRptMain = new PstCustomRptMain(oid);
            entCustomRptMain.setOID(oid);
            entCustomRptMain.setRptMainTitle(pstCustomRptMain.getString(FLD_RPT_MAIN_TITLE));
            entCustomRptMain.setRptMainDesc(pstCustomRptMain.getString(FLD_RPT_MAIN_DESC));
            entCustomRptMain.setRptMainDateCreate(pstCustomRptMain.getDate(FLD_RPT_MAIN_DATE_CREATE));
            entCustomRptMain.setRptMainCreatedBy(pstCustomRptMain.getLong(FLD_RPT_MAIN_CREATED_BY));
            entCustomRptMain.setRptMainPrivLevel(pstCustomRptMain.getLong(FLD_RPT_MAIN_PRIV_LEVEL));
            entCustomRptMain.setRptMainPrivPos(pstCustomRptMain.getLong(FLD_RPT_MAIN_PRIV_POS));
            return entCustomRptMain;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptMain(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CustomRptMain entCustomRptMain = fetchExc(entity.getOID());
        entity = (Entity) entCustomRptMain;
        return entCustomRptMain.getOID();
    }

    public static synchronized long updateExc(CustomRptMain entCustomRptMain) throws DBException {
        try {
            if (entCustomRptMain.getOID() != 0) {
                PstCustomRptMain pstCustomRptMain = new PstCustomRptMain(entCustomRptMain.getOID());
                pstCustomRptMain.setString(FLD_RPT_MAIN_TITLE, entCustomRptMain.getRptMainTitle());
                pstCustomRptMain.setString(FLD_RPT_MAIN_DESC, entCustomRptMain.getRptMainDesc());
                pstCustomRptMain.setDate(FLD_RPT_MAIN_DATE_CREATE, entCustomRptMain.getRptMainDateCreate());
                pstCustomRptMain.setLong(FLD_RPT_MAIN_CREATED_BY, entCustomRptMain.getRptMainCreatedBy());
                pstCustomRptMain.setLong(FLD_RPT_MAIN_PRIV_LEVEL, entCustomRptMain.getRptMainPrivLevel());
                pstCustomRptMain.setLong(FLD_RPT_MAIN_PRIV_POS, entCustomRptMain.getRptMainPrivPos());
                pstCustomRptMain.update();
                return entCustomRptMain.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptMain(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((CustomRptMain) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstCustomRptMain pstCustomRptMain = new PstCustomRptMain(oid);
            pstCustomRptMain.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptMain(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(CustomRptMain entCustomRptMain) throws DBException {
        try {
            PstCustomRptMain pstCustomRptMain = new PstCustomRptMain(0);
            pstCustomRptMain.setString(FLD_RPT_MAIN_TITLE, entCustomRptMain.getRptMainTitle());
            pstCustomRptMain.setString(FLD_RPT_MAIN_DESC, entCustomRptMain.getRptMainDesc());
            pstCustomRptMain.setDate(FLD_RPT_MAIN_DATE_CREATE, entCustomRptMain.getRptMainDateCreate());
            pstCustomRptMain.setLong(FLD_RPT_MAIN_CREATED_BY, entCustomRptMain.getRptMainCreatedBy());
            pstCustomRptMain.setLong(FLD_RPT_MAIN_PRIV_LEVEL, entCustomRptMain.getRptMainPrivLevel());
            pstCustomRptMain.setLong(FLD_RPT_MAIN_PRIV_POS, entCustomRptMain.getRptMainPrivPos());
            pstCustomRptMain.insert();
            entCustomRptMain.setOID(pstCustomRptMain.getLong(FLD_RPT_MAIN_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomRptMain(0), DBException.UNKNOWN);
        }
        return entCustomRptMain.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((CustomRptMain) entity);
    }

    public static void resultToObject(ResultSet rs, CustomRptMain entCustomRptMain) {
        try {
            entCustomRptMain.setOID(rs.getLong(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_ID]));
            entCustomRptMain.setRptMainTitle(rs.getString(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_TITLE]));
            entCustomRptMain.setRptMainDesc(rs.getString(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DESC]));
            entCustomRptMain.setRptMainDateCreate(rs.getDate(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_DATE_CREATE]));
            entCustomRptMain.setRptMainCreatedBy(rs.getLong(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_CREATED_BY]));
            entCustomRptMain.setRptMainPrivLevel(rs.getLong(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_PRIV_LEVEL]));
            entCustomRptMain.setRptMainPrivPos(rs.getLong(PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_PRIV_POS]));
        } catch (Exception e) {
        }
    }
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_CUSTOM_RPT_MAIN;
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
                CustomRptMain customRptMain = new CustomRptMain();
                resultToObject(rs, customRptMain);
                lists.add(customRptMain);
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
    
    public static boolean checkOID(long entMainId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CUSTOM_RPT_MAIN + " WHERE "
                    + PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_ID] + " = " + entMainId;

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
            String sql = "SELECT COUNT(" + PstCustomRptMain.fieldNames[PstCustomRptMain.FLD_RPT_MAIN_ID] + ") FROM " + TBL_CUSTOM_RPT_MAIN;
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
                    CustomRptMain entCustomRpt = (CustomRptMain) list.get(ls);
                    if (oid == entCustomRpt.getOID()) {
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
    
    /**
     * Description : List Leave Entitle Report
     * Date : 2015-06-30
     */
    public static Vector listLeaveEntitleReport(long periodId, long departmentId) {
        Vector lists = new Vector();
        double total = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT hr_department.DEPARTMENT, hr_section.SECTION, pay_slip.PAY_SLIP_ID FROM hr_department ";
                  sql += "INNER JOIN hr_section ON hr_department.DEPARTMENT_ID=hr_section.DEPARTMENT_ID ";
                  sql += "INNER JOIN pay_slip ON hr_section.SECTION=pay_slip.SECTION ";
            if (departmentId == 0){
                sql += "WHERE pay_slip.PERIOD_ID="+periodId;
            } else {
                sql += "WHERE hr_department.DEPARTMENT_ID="+departmentId+" AND pay_slip.PERIOD_ID="+periodId;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                LeaveEntitleReport en = new LeaveEntitleReport();
                en.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                en.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                total = getBaseAmount(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                en.setBaseAmount(total);
                en.setHeadCount(0);
                en.setApplyAmount(0);
                lists.add(en);                
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
    
    public static double getBaseAmount(long paySlipId){
        double total = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(COMP_VALUE) AS total FROM pay_slip_comp ";
            sql += "WHERE PAY_SLIP_ID="+paySlipId+" AND COMP_CODE IN('ALW01','ALW04','ALW05')";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                total = rs.getDouble("total");
            }
            rs.close();
            return total;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return total;
    }
    
    /**
     * Description : Taken Leave Report
     * Date : 2015-07-02
     */
    public static Vector listTakenLeaveReport(long periodId, long departmentId) {
        Vector lists = new Vector();
        double total = 0;
        int totalTakenLeave = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay_slip.EMPLOYEE_ID, hr_department.DEPARTMENT, hr_section.SECTION, pay_slip.PAY_SLIP_ID, ";
                  sql += "pay_period.START_DATE, pay_period.END_DATE FROM hr_department ";
                  sql += "INNER JOIN hr_section ON hr_department.DEPARTMENT_ID=hr_section.DEPARTMENT_ID ";
                  sql += "INNER JOIN pay_slip ON hr_section.SECTION=pay_slip.SECTION ";
                  sql += "INNER JOIN pay_period ON pay_period.PERIOD_ID=pay_slip.PERIOD_ID ";
            if (departmentId == 0){
                sql += "WHERE pay_slip.PERIOD_ID="+periodId;
            } else {
                sql += "WHERE hr_department.DEPARTMENT_ID="+departmentId+" AND pay_slip.PERIOD_ID="+periodId;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long empId = 0;
            Date startDate = null;
            Date endDate = null;
            while (rs.next()) {                
                TakenLeaveReport takenLeave = new TakenLeaveReport();
                takenLeave.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                takenLeave.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                empId = rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]);
                startDate = rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE]);
                endDate = rs.getDate(PstPayPeriod.fieldNames[PstPayPeriod.FLD_END_DATE]);
                totalTakenLeave = getTakenQty(empId, startDate, endDate);
                total = getBaseAmount(rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]));
                takenLeave.setBaseAmount(total);
                takenLeave.setHeadCount(0);
                takenLeave.setLeaveTaken(totalTakenLeave);
                takenLeave.setApplyAmount(0);
                takenLeave.setTotalApplyAmount(0);
                lists.add(takenLeave);
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
    
    public static String getLastOfDate(Date dateParam){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(dateParam);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String tanggal = dateFormat.format(c.getTime());
        return tanggal;
    }
    
    public static String dateToString(Date dateParam){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(dateParam);
        String tanggal = dateFormat.format(c.getTime());
        return tanggal;
    }
    
    public static String intValueToString(int value){
        String nilai = String.valueOf(value);
        if (nilai.length()==1){
            nilai = "0"+value;
        }
        return nilai;
    }
    
    public static String[] getArrPeriod(Date startDate, Date endDate){
        String[] arrPeriod = new String[31];
        String[] year = new String[31];
        String[] month = new String[31];
        String[] day = new String[31];
        /* ubah date menjadi string */
        String mulaiDate = dateToString(startDate);
        /* Pecah date menjadi tgl, bln, thn */
        String mulaiThn = mulaiDate.substring(0, 4);
        String mulaiBln = mulaiDate.substring(5, 7);
        String mulaiTgl = mulaiDate.substring(8, 10);
        
        int intMulaiTgl = Integer.valueOf(mulaiTgl);
        
        String middleDate = getLastOfDate(startDate);
        String middleDay = middleDate.substring(8, 10);
        int intMiddleDay = Integer.valueOf(middleDay);
        int inc = 0;
        for(int i=intMulaiTgl; i<=intMiddleDay; i++){
            year[inc] = mulaiThn;
            month[inc] = mulaiBln;
            day[inc] = intValueToString(i);
            inc++;
        }
        
        String akhirDate = dateToString(endDate);
        /* Pecah date menjadi tgl, bln, thn */
        String akhirThn = akhirDate.substring(0, 4);
        String akhirBln = akhirDate.substring(5, 7);
        String akhirTgl = akhirDate.substring(8, 10);
        for(int j=1; j<=Integer.valueOf(akhirTgl); j++){
            year[inc] = akhirThn;
            month[inc] = akhirBln;
            day[inc] = intValueToString(j);
            inc++;
        }
        String data = "";
        for(int k=0; k<31; k++){
            arrPeriod[k] = year[k]+"-"+month[k]+"-"+day[k];
        }
        return arrPeriod;
    }
    
    public static String[] getArrTaken(Date startDate, Date endDate){
        String[] arrTaken = null;
        
        /* ubah date menjadi string */
        String mulaiDate = dateToString(startDate);
        /* Pecah date menjadi tgl, bln, thn */
        String mulaiThn = mulaiDate.substring(0, 4);
        String mulaiBln = mulaiDate.substring(5, 7);
        String mulaiTgl = mulaiDate.substring(8, 10);
        
        String akhirDate = dateToString(endDate);
        /* Pecah date menjadi tgl, bln, thn */
        String akhirThn = akhirDate.substring(0, 4);
        String akhirBln = akhirDate.substring(5, 7);
        String akhirTgl = akhirDate.substring(8, 10);
        /*2015-03-18 2015-03-20 | 2015-03-01 2015-04-02 | 2015-12-30 2016-01-04 */
        if (mulaiThn.equals(akhirThn)){
            if (mulaiBln.equals(akhirBln)){
                if(Integer.valueOf(mulaiTgl)<Integer.valueOf(akhirTgl)){
                    int nTaken = 0;
                    
                    for(int i=Integer.valueOf(mulaiTgl); i<=Integer.valueOf(akhirTgl); i++){
                        nTaken++;
                    }
                    
                    arrTaken = new String[nTaken];
                    nTaken = 0;
                    for(int j=Integer.valueOf(mulaiTgl); j<=Integer.valueOf(akhirTgl); j++){
                        arrTaken[nTaken] = mulaiThn+"-"+mulaiBln+"-"+intValueToString(j);
                        nTaken++;
                    }
                }
            } else { // monthStart < monthEnd
                // cari last day
                String last = getLastOfDate(startDate);
                String tglLast = last.substring(8, 10);
                int nTaken = 0;
                for(int i=Integer.valueOf(mulaiTgl); i<=Integer.valueOf(tglLast); i++){
                    nTaken++;
                }
                for(int j=1; j<=Integer.valueOf(akhirTgl); j++){
                    nTaken++;
                }
                arrTaken = new String[nTaken];
                nTaken = 0;
                for(int k=Integer.valueOf(mulaiTgl); k<=Integer.valueOf(tglLast); k++){
                    arrTaken[nTaken] = mulaiThn+"-"+mulaiBln+"-"+intValueToString(k);
                    nTaken++;
                }
                for(int l=1; l<=Integer.valueOf(akhirTgl); l++){
                    arrTaken[nTaken] = mulaiThn+"-"+mulaiBln+"-"+intValueToString(l);
                    nTaken++;
                }
            }
        } else {
            // 2015-12-01 2016-01-04
            // cari last day
            String last = getLastOfDate(startDate);
            String tglLast = last.substring(8, 10);
            int nTaken = 0;
            for(int i=Integer.valueOf(mulaiTgl); i<=Integer.valueOf(tglLast); i++){
                nTaken++;
            }
            for(int j=1; j<=Integer.valueOf(akhirTgl); j++){
                nTaken++;
            }
            arrTaken = new String[nTaken];
            nTaken = 0;
            for(int k=Integer.valueOf(mulaiTgl); k<=Integer.valueOf(tglLast); k++){
                arrTaken[nTaken] = mulaiThn+"-"+mulaiBln+"-"+intValueToString(k);
                nTaken++;
            }
            for(int l=1; l<=Integer.valueOf(akhirTgl); l++){
                arrTaken[nTaken] = akhirThn+"-"+akhirBln+"-"+intValueToString(l);
                nTaken++;
            }
        }
        return arrTaken;
    }
    
    public static int getTakenQty(long employeeId, Date startDate, Date endDate){
        int total = 0;
        String[] dataPeriod = getArrPeriod(startDate, endDate);
        Date startTakenDate = null;
        Date endTakenDate = null;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM hr_al_stock_taken WHERE EMPLOYEE_ID="+employeeId;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //total = rs.getDouble("total");
                startTakenDate = rs.getDate("TAKEN_DATE");
                endTakenDate = rs.getDate("TAKEN_FINNISH_DATE");
                String[] arrTaken = getArrTaken(startTakenDate, endTakenDate);
                for(int i=0; i<arrTaken.length; i++){
                    for(int j=0; j<dataPeriod.length; j++){
                        if (arrTaken[i].equals(dataPeriod[j])){
                            total++;
                        }
                    }
                }
            }
            rs.close();
            return total;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return total;
    }
}
