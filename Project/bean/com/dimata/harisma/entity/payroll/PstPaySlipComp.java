/*
 * PstPaySlipComp.java
 *
 * Created on April 26, 2007, 2:43 PM
 */
package com.dimata.harisma.entity.payroll;

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

/* package harisma */
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.system.entity.system.*;

/**
 *
 * @author  yunny
 */
public class PstPaySlipComp extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAY_SLIP_COMP = "pay_slip_comp";//"PAY_SLIP_COMP";
    public static final int FLD_PAY_SLIP_COMP_ID = 0;
    public static final int FLD_PAY_SLIP_ID = 1;
    public static final int FLD_COMP_CODE = 2;
    public static final int FLD_COMP_VALUE = 3;
    public static final String[] fieldNames = {
        "PAY_SLIP_COMP_ID",
        "PAY_SLIP_ID",
        "COMP_CODE",
        "COMP_VALUE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_FK + TYPE_LONG,
        TYPE_FK + TYPE_STRING,
        TYPE_FLOAT
    };

    /** Creates a new instance of PstPaySlipComp */
    public PstPaySlipComp() {
    }

    public PstPaySlipComp(int i) throws DBException {
        super(new PstPaySlipComp());
    }

    public PstPaySlipComp(String sOid) throws DBException {
        super(new PstPaySlipComp(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySlipComp(long lOid) throws DBException {
        super(new PstPaySlipComp(0));
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
        return new PstPaySlipComp().getClass().getName();
    }

    public String getTableName() {
        return TBL_PAY_SLIP_COMP;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public long fetchExc(Entity ent) throws Exception {
        PaySlipComp paySlipComp = fetchExc(ent.getOID());
        ent = (Entity) paySlipComp;
        return paySlipComp.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((PaySlipComp) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((PaySlipComp) ent);
    }

    public static PaySlipComp fetchExc(long oid) throws DBException {
        try {

            PaySlipComp paySlipComp = new PaySlipComp();
            PstPaySlipComp pstPaySlipComp = new PstPaySlipComp(oid);
            paySlipComp.setOID(oid);
            paySlipComp.setPaySlipId(pstPaySlipComp.getlong(FLD_PAY_SLIP_ID));
            paySlipComp.setCompCode(pstPaySlipComp.getString(FLD_COMP_CODE));
            paySlipComp.setCompValue(pstPaySlipComp.getdouble(FLD_COMP_VALUE));
            return paySlipComp;

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipComp(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(PaySlipComp paySlipComp) throws DBException {
        try {
            PstPaySlipComp pstPaySlipComp = new PstPaySlipComp(0);

            pstPaySlipComp.setLong(FLD_PAY_SLIP_ID, paySlipComp.getPaySlipId());
            pstPaySlipComp.setString(FLD_COMP_CODE, paySlipComp.getCompCode());
            pstPaySlipComp.setDouble(FLD_COMP_VALUE, paySlipComp.getCompValue());

            pstPaySlipComp.insert();
            paySlipComp.setOID(pstPaySlipComp.getlong(FLD_PAY_SLIP_COMP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipComp(0), DBException.UNKNOWN);
        }
        return paySlipComp.getOID();
    }

    public static long updateExc(PaySlipComp paySlipComp) throws DBException {
        try {
            if (paySlipComp.getOID() != 0) {
                PstPaySlipComp pstPaySlipComp = new PstPaySlipComp(paySlipComp.getOID());
                pstPaySlipComp.setLong(FLD_PAY_SLIP_ID, paySlipComp.getPaySlipId());
                pstPaySlipComp.setString(FLD_COMP_CODE, paySlipComp.getCompCode());
                pstPaySlipComp.setDouble(FLD_COMP_VALUE, paySlipComp.getCompValue());

                pstPaySlipComp.update();
                return paySlipComp.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstTaxType(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstPaySlipComp pstPaySlipComp = new PstPaySlipComp(oid);
            pstPaySlipComp.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySlipComp(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector listAll() {
        return list(0, 1000, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SLIP_COMP;
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


            // System.out.println("sql PstPaySlipComp.list........"+sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PaySlipComp paySlipComp = new PaySlipComp();
                resultToObject(rs, paySlipComp);
                lists.add(paySlipComp);
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
     * Description : mendapatkan one of data paySlipComp
     * Date : 2015-04-20
     * Author : Hendra Putu
    */
    public static long getPaySlipCompIdData(String whereClause) {
        long oid = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SLIP_COMP;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                oid = rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID]);
            }
            rs.close();
            return oid;


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return oid;
    }

    public static void resultToObject(ResultSet rs, PaySlipComp paySlipComp) {
        try {
            paySlipComp.setOID(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID]));
            paySlipComp.setPaySlipId(rs.getLong(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]));
            paySlipComp.setCompCode(rs.getString(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]));
            paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long paySlipCompId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SLIP_COMP + " WHERE " +
                    PstTaxType.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID] + " = '" + paySlipCompId + "'";

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
            String sql = "SELECT COUNT(" + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_COMP_ID] + ") FROM " + TBL_PAY_SLIP_COMP;
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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    PaySlipComp paySlipComp = (PaySlipComp) list.get(ls);
                    if (oid == paySlipComp.getOID()) {
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
    /*
     */

    public static int getCompValue(long paySlipId, String compCode) {
        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId +
                    " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL compValue"+sql);

            int compValue = 0;
            while (rs.next()) {
                compValue = rs.getInt(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    /* This method used to get paySlipCompId
     * @param = paySlipId
     * @param = comp_code
     * created Yunny
     */

    public static long getPaySlipId(long paySlipId, String compCode) {
        DBResultSet dbrs = null;
        try {


            String sql = "SELECT PAY_SLIP_COMP_ID FROM " + TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId +
                    " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "= '" + compCode.trim() + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            System.out.println("SQL getPaySlipId" + sql);

            long paySlipCompId = 0;
            while (rs.next()) {
                paySlipCompId = rs.getLong(1);
            }

            rs.close();
            return paySlipCompId;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getCompValueDouble(long paySlipId, String compCode) {
        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId +
                    " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            System.out.println("SQL PstPaySlipComp.getCompValueDouble.." + sql);

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getReportCompValue(long paySlipId, String compCode) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId +
                    " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL PstPaySlipComp.getReportCompValue.."+sql);

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSummSalary(long paySlipId, int compType) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(COMP_VALUE) FROM " + TBL_PAY_SLIP_COMP + " AS SLIP " +
                    "INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON SLIP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " WHERE SLIP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + "=" + paySlipId +
                    " AND COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] + "=" + compType;

            System.out.println("SQL PstPaySlipComp.getSummSalary.." + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSummEkspor(long periodId) {
        DBResultSet dbrs = null;
        String codeEksp = PstSystemProperty.getValueByName("CODE_EKSP");
        try {
            String sql = "SELECT SUM(COMP_VALUE) FROM " + TBL_PAY_SLIP_COMP + " AS COMP " +
                    "INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeEksp + "'";

            System.out.println("SQL PstPaySlipComp.getSummEkspor.." + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /*
     */
    public static int getCompValueEmployee(long employeeId, long periodId, String compCode) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] +
                    " FROM " + TBL_PAY_SLIP_COMP + " AS slip" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as pay" +
                    " ON slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    "= pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " WHERE slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    "=" + employeeId +
                    " AND pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    "=" + periodId;


            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            System.out.println("SQL compValueFix" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //

            int compValue = 0;
            while (rs.next()) {
                compValue = rs.getInt(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getCompValueEmployeeDouble(long employeeId, long periodId, String compCode) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] +
                    " FROM " + TBL_PAY_SLIP_COMP + " AS slip" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as pay" +
                    " ON slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    "= pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " WHERE slip." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    "=" + employeeId +
                    " AND pay." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    "=" + periodId;


            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            //System.out.println("SQL compValueFix" + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    /*
     *  This method used to update value of salary component
     *  Created By Yunny
     */
    public static void updateCompValue(long paySlipId, String compCode, double compValue) {
        DBResultSet dbrs = null;
        //boolean result = false;
        //String barcode = (barcodeNumber.equals(null)) ? "null" : barcodeNumber;

        try {
            String sql = "";

            sql = " UPDATE " + TBL_PAY_SLIP_COMP + " SET " +
                    PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + " =  " + compValue +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] + " = " + paySlipId +
                    " AND " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + " = '" + compCode + "'";
            ;

            //dbrs = DBHandler.execQueryResult(sql);
            int status = DBHandler.execUpdate(sql);
            //ResultSet rs = dbrs.getResultSet();
            System.out.println("\tupdateCompValue : " + sql);
        //while(rs.next()) { result = true; }

        //rs.close();
        } catch (Exception e) {
            System.err.println("\tupdateCompValue error : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        //return result;
        }
    }

    public static double getCompFormValue(String formula) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        try {
            String sql = "SELECT (" + formula + ")";
            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL getCompFormValue"+sql);
            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }

            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static double getCompFormValue(String formula, String tmpMessage) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        try {
            String sql = "SELECT (" + formula + ")";
            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL getCompFormValue"+sql);
            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }

            rs.close();
            return compValueX;
        } catch (Exception e) {
            tmpMessage = "Formula : "+ formula;
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    

    /**
     * this method used to get componentCode
     *  @param : payComp
     * created by Yunny
     */
    public static String getCodeComponent(String payComp) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT COMP_CODE FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + payComp + "'";

            //System.out.println("sql PstPaySlipComp.getCodeComponent  "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    /**
     * this method used to get componentCode
     *  @param : payComp
     * created by Kartika
     */
    public static int countCodeComponent(String payComp) {
        DBResultSet dbrs = null;
        int  result = 0;
        try {
            String sql = "SELECT COUNT(COMP_CODE) FROM " + PstPaySlipComp.TBL_PAY_SLIP_COMP +
                    " WHERE " + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + payComp + "'";

            //System.out.println("sql PstPaySlipComp.getCodeComponent  "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }    
    
    /**
     * this method used to get bankAccount
     *  @param : employeeId
     * created by Yunny
     */
    public static String getStrValue(String formula) {
        DBResultSet dbrs = null;
        String result = "";
        try {
            String sql = "SELECT (" + formula + ")";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            //  System.out.println("sql getBankAccNr  "+sql);
            while (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return result;
    }

    public static double getSumValue(String compCode, long oidPeriod, String levelCode, long departmentId, long sectionId) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        try {
            String sql = "SELECT SUM(COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " +
                    PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS COMP" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP " +
                    " ON COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    " = " + oidPeriod +
                    (!levelCode.trim().equals("0")?" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] +
                    " = '" + levelCode + "'" :" ")+
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            String whereClause = "";

            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + departmentId + " AND ";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId + " AND ";
            }




            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            //sql = sql + " WHERE " + whereClause;
            }
            //System.out.println("SQL PstPaySlipComp.getSumValue..."+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }
            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSumValue(String compCode, long oidPeriod, String levelCode, long departmentId, long sectionId, int transfered) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT SUM(COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " +
                    PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS COMP" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP " +
                    " ON COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    " = " + oidPeriod +
                    (!levelCode.trim().equals("0")?" AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] +
                    " = '" + levelCode + "'" : " ")+
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            String whereClause = "";
            if (transfered < 2) {
                if (transfered == PstPayEmpLevel.TRANSFERED_YES) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " != " + oidCash + " AND ";
                }

                if (transfered == PstPayEmpLevel.TRANSFERED_NO) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " = " + oidCash + " AND ";
                }
            }


            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + departmentId + " AND ";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId + " AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            //sql = sql + " WHERE " + whereClause;
            }
            //System.out.println("SQL PstPaySlipComp.getSumValue..."+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }
            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSumComponentValue(String compCode, long oidPeriod, String levelCode, Vector vectDept, long sectionId, int transfered) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT SUM(COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " +
                    PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS COMP" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP " +
                    " ON COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    " = " + oidPeriod +
                    (!levelCode.trim().equals("0")? " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] +
                    " = '" + levelCode + "'" : " ")+
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            String whereClause = "";
            if (transfered < 2) {
                if (transfered == PstPayEmpLevel.TRANSFERED_YES) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " != " + oidCash + " AND ";
                }

                if (transfered == PstPayEmpLevel.TRANSFERED_NO) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " = " + oidCash + " AND ";
                }
            }

            if (vectDept != null && vectDept.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < vectDept.size(); x++) {
                    Department dept = (Department) vectDept.get(x);
                    sql = sql + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dept.getOID() + " OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (sectionId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +
                        " = " + sectionId + " AND ";
            }


            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            //sql = sql + " WHERE " + whereClause;
            }
            //System.out.println("SQL PstPaySlipComp.getSumValue..."+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }
            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    public static double getSumComponentAllowance(Vector levSelect, String compCode, long oidPeriod, long departmentId, int transfered) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT SUM(COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " +
                    PstPaySlipComp.TBL_PAY_SLIP_COMP + " AS COMP" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP " +
                    " ON COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE COMP." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] +
                    " = '" + compCode + "'" +
                    " AND SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] +
                    " = " + oidPeriod +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN;

            String whereClause = "";
            if (transfered < 2) {
                if (transfered == PstPayEmpLevel.TRANSFERED_YES) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " != " + oidCash + " AND ";
                }

                if (transfered == PstPayEmpLevel.TRANSFERED_NO) {
                    whereClause = whereClause + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] +
                            " = " + oidCash + " AND ";
                }
            }


            if (departmentId != 0) {
                whereClause = whereClause + " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +
                        " = " + departmentId + " AND ";
            }

            if (levSelect != null && levSelect.size() > 0) {
                sql = sql + " AND (";
                for (int x = 0; x < levSelect.size(); x++) {
                    SalaryLevel s = (SalaryLevel) levSelect.get(x);
                    sql = sql + " LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE] + "='" + s.getLevelCode() + "' OR";
                }
                sql = sql.substring(0, sql.length() - 2);
                sql = sql + ")";
            }

            if (whereClause != null && whereClause.length() > 0) {
                whereClause = " AND " + whereClause.substring(0, whereClause.length() - 4);
                sql = sql + whereClause;
            //sql = sql + " WHERE " + whereClause;
            }
            System.out.println("SQL PstPaySlipComp.getSumComponentAllowance..." + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }
            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

//method for get Max Value Jabatan    
    public static int getMaxJab(String levelCode) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_VARIABEL_VALUE] +
                    " FROM " + PstPayTaxVariabel.TBL_PAY_TAX_VARIABEL +
                    " WHERE " + (!levelCode.trim().equals("0")? PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_LEVEL_CODE] + " = '" + levelCode + "'" : " ")+
                    " AND " + PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_JENIS_VARIABEL] + " = " + PstPayTaxVariabel.BIAYA_TUNJ_POT_JABATAN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            System.out.println("data maks datanya berapa?????:::::" + sql);

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

    //method for get Persen Variabel    
    public static double getPersenJab(String levelCode) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_PERSEN_VARIABEL] +
                    " FROM " + PstPayTaxVariabel.TBL_PAY_TAX_VARIABEL +
                    " WHERE " + (!levelCode.trim().equals("0")? PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_LEVEL_CODE] + " = '" + levelCode + "'" :" ")+
                    " AND " + PstPayTaxVariabel.fieldNames[PstPayTaxVariabel.FLD_JENIS_VARIABEL] + " = " + PstPayTaxVariabel.BIAYA_TUNJ_POT_JABATAN;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            System.out.println("data persennya berapa?????:::::" + sql);

            double count = 0;
            while (rs.next()) {
                count = rs.getDouble(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     *strated count for tax..............
     *by emerliana
     */
    public static int formatNumber(double value) {
        int result = 0;
        String strVal = "";
        if (value != 0) {
            strVal = Formater.formatNumber(value, "###");
        }
        if (strVal != null && strVal.length() > 0) {
            result = Integer.parseInt(strVal);
        }
        return result;
    }

    public static double formatNumberDouble(double value) {
        double result = 0;
        String strVal = "";
        if (value != 0) {
            strVal = Formater.formatNumber(value, "###");
        }
        if (strVal != null && strVal.length() > 0) {
            result = Double.parseDouble(strVal);
        }
        return result;
    }

    //method for get jumlah bulan bekerja selama 1 thn
 /*public static int getMonthWorkly(Date commencingDate){
    Date dtNow = new Date();
    int month = commencingDate.getMonth();
    int monthNow = dtNow.getMonth();
    int totMonth = monthNow - month;
    }
    //method for get potongan biaya jabatan
    public static int getPotJabatan(int totGajiKotor, String levelCode){
    double potJabatan = (getPersenJab(levelCode)*totGajiKotor)/100;
    //String strPotJab = String.valueOf(potJabatanX);
    //int potJabatan = Integer.parseInt(strPotJab);
    if(potJabatan>getMaxJab(levelCode)){
    potJabatan = getMaxJab(levelCode);
    }
    return formatNumber(potJabatan);
    }
    //method for get biaya pensiun
    public static int getIpensiun(int totGajiKotor){
    int iPensiun = (475*totGajiKotor)/10000;        
    return formatNumber(iPensiun);
    }
    public static int getTotGjThn(int totGajiKotor,int potJabatan){
    int totGjThn = (totGajiKotor-potJabatan)*12;        
    return totGjThn;
    }
    public synchronized static void hitungPph(int totBenefit, int totDeduction, String levelCode, Date commencingDate){
    int countMonth = getMonthWorkly(commencingDate);
    int totGajiKotor = totBenefit - totDeduction;
    int potJabatan = getPotJabatan(totGajiKotor,levelCode);
    int totGajiBersihThn = getTotGjThn(totGajiKotor,potJabatan);
    }*/
    /*
     *  This method used to update pay slip comp value 
     *  Created By Yunny
     */
    public static void updateValueComp(double compValue, String whereClause) {

        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql = " UPDATE " + PstPaySlipComp.TBL_PAY_SLIP_COMP + " SET " +
                    PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + " = " + compValue;

            sql = sql + " WHERE " + whereClause;
            //System.out.println("updateValueComp : " + sql);
            int status = DBHandler.execUpdate(sql);
        } catch (Exception e) {
            System.err.println("\tupdateNote : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        //return result;
        }
    }

    /* This method to used to get sum of salary 
     * @param periodId
     * Created By Yunny
     */
    public static double getSummSalary(long periodId) {
        DBResultSet dbrs = null;
        long oidCash = 0;
        oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEVEL" +
                    " ON LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM] +
                    " IN (" + PstPayComponent.GAJI+","+PstPayComponent.TUNJANGAN+","+PstPayComponent.BONUS_THR+") " +                    
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash;

            System.out.println("SQL PstPaySlipComp.getSummSalary.." + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /* This method to used to get sum of overtime 
     * @param periodId
     * Created By Yunny
     */
    public static double getSummOvt(long periodId) {
        DBResultSet dbrs = null;
        String codeOvt = PstSystemProperty.getValueByName("CODE_OVT");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEVEL" +
                    " ON LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeOvt + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND LEVEL." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash;

            System.out.println("SQL PstPaySlipComp.getSummOvt..." + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * this method used to get sum overtime
     * @ param periodId
     * created by Yunny
     */
    public static Vector getVectOvt(long periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String codeOvt = PstSystemProperty.getValueByName("CODE_OVT");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    /*" INNER JOIN "+PstSalaryLevelDetail.TBL_PAY_LEVEL_COM+" AS LEV"+
                    " ON PAY."+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+
                    " = LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_COMP_CODE]+*/
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeOvt + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    //" AND LEV."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.YES_TAKE+
                    " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            System.out.println("sql PstPaySlipComp.getVectOvt  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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
     * this method used to get 
     * created by Yunny
     */
    public static Vector getVectEkspAll(long periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String codeEksp = PstSystemProperty.getValueByName("CODE_EKSP");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeEksp + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            System.out.println("sql PstPaySlipComp.getVectEkspAll  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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

    /* This method to used to get sum of Ekspor Allowance 
     * @param periodId
     * Created By Yunny
     */
    public static double getSummEksAllowance(long periodId) {
        DBResultSet dbrs = null;
        String codeEksp = PstSystemProperty.getValueByName("CODE_EKSP");
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeEksp + "'";

            //System.out.println("SQL getSummEksAllowance"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * Get vector of compoenent salary   
     * @param periodId
     * @param compCode : code of salary component
     * @param bankOid  : bank oid, bisa null jika tidak diperlukan perbedaan bank oid
     * @param compareStringBankOID  : logica comparasi field BANK_ID dgn bankOid , contoh : = atau <> atau !=  
     * @return
     */
    public static Vector getVectSalaryComp(long periodId, String compCode, Long bankOid, String compareStringBankOID) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + compCode + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    (bankOid != null ? " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + compareStringBankOID + bankOid.toString() : "") +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            System.out.println("sql PstPaySlipComp.getVectSalaryComp  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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

    
    
    public static double sumCompSalary(long periodId, String compCode) {	
        Vector vctCompSal = getVectSalaryComp(periodId, compCode, null, "");
        double sumComp = 0;
        if (vctCompSal != null && vctCompSal.size() > 0) {
            for (int i = 0; i < vctCompSal.size(); i++) {
                Vector temp = (Vector) vctCompSal.get(i);
                PaySlipComp paySlipComp = (PaySlipComp) temp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(), compCode);
                if (cekTakeHomePay == PstSalaryLevelDetail.YES_TAKE) {
                    //System.out.println(sumMealAllowance+ "   " +paySlipComp.getCompValue() );
                    sumComp = sumComp + paySlipComp.getCompValue();                    
                }
            }
        }	
		return sumComp;
}

    
    
    
    /* This method to used to get sum of Kasbon 
     * @param periodId
     * Created By Yunny
     */
    public static double getSummKasbon(long periodId) {
        DBResultSet dbrs = null;
        String codeKsb = PstSystemProperty.getValueByName("CODE_KSB");
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeKsb + "'";

            //System.out.println("SQL getSummKasbon"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * this method used to get 
     * created by Yunny
     */
    public static Vector getVectKasbon(long periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String codeKsb = PstSystemProperty.getValueByName("CODE_KSB");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + codeKsb + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            System.out.println("sql PstPaySlipComp.getVectKasbon  " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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

    /* This method to used to get sum of Leave Deduction
     * @param periodId
     * Created By Yunny
     */
    public static double getLeaveDeduction(long periodId) {
        DBResultSet dbrs = null;
        String leaveCode = PstSystemProperty.getValueByName("LEAVE_CODE");
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + leaveCode + "'";

            //System.out.println("SQL getLeaveDeduction"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * this method used to get 
     * created by Yunny
     */
    public static Vector getVectSumLeaveDeduction(long periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String leaveCode = PstSystemProperty.getValueByName("LEAVE_CODE");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "='" + leaveCode + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            // System.out.println("sql PstPaySlipComp.getVectSumLeaveDeduction  "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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

    /* This method to used to get sum of The Others Deduction
     * @param periodId
     * Created By Yunny
     */
    public static double getOtherDeduction(long periodId) {
        DBResultSet dbrs = null;
        String ksbCode = PstSystemProperty.getValueByName("CODE_KSB");
        String leaveCode = PstSystemProperty.getValueByName("CODE_CUTI");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {

            String sql = "SELECT SUM(pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE] + ") FROM " + TBL_PAY_SLIP_COMP + " AS PAY" +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " AS SLIP" +
                    " ON PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstPayComponent.TBL_PAY_COMPONENT + " AS COMP" +
                    " ON PAY." + PstPaySlip.fieldNames[PstPaySlip.FLD_COMP_CODE] +
                    " = COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS LEV" +
                    " ON SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " = LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND COMP." + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + "=" + PstPayComponent.TYPE_DEDUCTION +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "!='" + ksbCode + "'" +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "!='" + leaveCode + "'" +
                    " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash;

            //System.out.println("SQL PstPaySlipComp.getOtherDeduction"+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            double compValue = 0;
            while (rs.next()) {
                compValue = rs.getDouble(1);
            }

            rs.close();
            return compValue;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    /**
     * this method used to get 
     * created by Yunny
     */
    public static Vector getVectSumOther(long periodId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String ksbCode = PstSystemProperty.getValueByName("CODE_KSB");
        String leaveCode = PstSystemProperty.getValueByName("CODE_CUTI");
        long oidCash = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")));
        try {
            String sql = "SELECT pay.COMP_VALUE,EMPLEV.LEVEL_CODE FROM " + TBL_PAY_SLIP_COMP + " as pay " +
                    " INNER JOIN " + PstPaySlip.TBL_PAY_SLIP + " as slip" +
                    " ON pay." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID] +
                    " = slip." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID] +
                    " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                    " = SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] +
                    " INNER JOIN " + PstPayEmpLevel.TBL_PAY_EMP_LEVEL + " AS EMPLEV" +
                    " ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" +
                    " EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID] +
                    " WHERE SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID] + "=" + periodId +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "!='" + ksbCode + "'" +
                    " AND PAY." + PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE] + "!='" + leaveCode + "'" +
                    " AND EMP." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN +
                    " AND EMPLEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_BANK_ID] + "!=" + oidCash +
                    " GROUP BY SLIP." + PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID];

            // System.out.println("sql PstPaySlipComp.getVectSumOther  "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL    "+compType+"-"+sql);
            //System.out.println("masuk:::::::::::: "+rs);
            while (rs.next()) {
                Vector vect = new Vector(1, 1);

                PaySlipComp paySlipComp = new PaySlipComp();
                paySlipComp.setCompValue(rs.getDouble(PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_VALUE]));
                //resultToObject(rs, paySlipComp);
                vect.add(paySlipComp);

                PayEmpLevel payEmpLevel = new PayEmpLevel();
                payEmpLevel.setLevelCode(rs.getString(PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]));
                vect.add(payEmpLevel);

                lists.add(vect);

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

    public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);

        /**
        PaySlipComp paySlipComp = new PaySlipComp();
        paySlipComp.setPaySlipId(1254124);
        paySlipComp.setCompCode("TRG");
        try{
        PstPaySlipComp.insertExc(paySlipComp);
        }catch(Exception e){
        System.out.println("Err"+e.toString());
        }
         **/
         
        Vector summMealAll = PstPaySlipComp.getVectSalaryComp(504404358898906770L, "TJ.MAKAN", null, "");
//double sumEksAllowance = PstPaySlipComp.getSummEksAllowance(oidPeriod);
        double sumMealAllowance = 0;
        if (summMealAll != null && summMealAll.size() > 0) {
            for (int i = 0; i < summMealAll.size(); i++) {
                Vector temp = (Vector) summMealAll.get(i);
                PaySlipComp paySlipComp = (PaySlipComp) temp.get(0);
                PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(1);
                int cekTakeHomePay = PstSalaryLevelDetail.getTakeHomePay(payEmpLevel.getLevelCode(), "TJ.MAKAN");
                if (cekTakeHomePay == PstSalaryLevelDetail.YES_TAKE) {
                    System.out.println(sumMealAllowance+ "   " +paySlipComp.getCompValue() );
                    sumMealAllowance = sumMealAllowance + paySlipComp.getCompValue();                    
                }
            }
        }
    }
    
  public static int deleteByPaySlipId(long paySlipId){
        try {
            String sql = " DELETE FROM " + TBL_PAY_SLIP_COMP
                    + " WHERE " + fieldNames[FLD_PAY_SLIP_ID]
                    + " = " + paySlipId;
            int status = DBHandler.execUpdate(sql);
            return 0;
        } catch (Exception exc) {
            System.out.println("error delete fam member by employee " + exc.toString());
        }
        return -1;
  }
}
