/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 * Description : Date :
 *
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.employee.PstEmployee;
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
import java.util.Vector;

public class PstBenefitPeriodEmp extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_BENEFIT_PERIOD_EMP = "pay_benefit_period_emp";
    public static final int FLD_BENEFIT_PERIOD_EMP_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_AMOUNT_PART_1 = 2;
    public static final int FLD_AMOUNT_PART_2 = 3;
    public static final int FLD_LEVEL_CODE = 4;
    public static final int FLD_LEVEL_POINT = 5;
    public static final int FLD_BENEFIT_PERIOD_ID = 6;
    public static String[] fieldNames = {
        "BENEFIT_PERIOD_EMP_ID",
        "EMPLOYEE_ID",
        "AMOUNT_PART_1",
        "AMOUNT_PART_2",
        "LEVEL_CODE",
        "LEVEL_POINT",
        "BENEFIT_PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };

    public PstBenefitPeriodEmp() {
    }

    public PstBenefitPeriodEmp(int i) throws DBException {
        super(new PstBenefitPeriodEmp());
    }

    public PstBenefitPeriodEmp(String sOid) throws DBException {
        super(new PstBenefitPeriodEmp(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstBenefitPeriodEmp(long lOid) throws DBException {
        super(new PstBenefitPeriodEmp(0));
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
        return TBL_BENEFIT_PERIOD_EMP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstBenefitPeriodEmp().getClass().getName();
    }

    public static BenefitPeriodEmp fetchExc(long oid) throws DBException {
        try {
            BenefitPeriodEmp entBenefitPeriodEmp = new BenefitPeriodEmp();
            PstBenefitPeriodEmp pstBenefitPeriodEmp = new PstBenefitPeriodEmp(oid);
            entBenefitPeriodEmp.setOID(oid);
            entBenefitPeriodEmp.setEmployeeId(pstBenefitPeriodEmp.getLong(FLD_EMPLOYEE_ID));
            entBenefitPeriodEmp.setAmountPart1(pstBenefitPeriodEmp.getdouble(FLD_AMOUNT_PART_1));
            entBenefitPeriodEmp.setAmountPart2(pstBenefitPeriodEmp.getdouble(FLD_AMOUNT_PART_2));
            entBenefitPeriodEmp.setLevelCode(pstBenefitPeriodEmp.getString(FLD_LEVEL_CODE));
            entBenefitPeriodEmp.setLevelPoint(pstBenefitPeriodEmp.getInt(FLD_LEVEL_POINT));
            entBenefitPeriodEmp.setBenefitPeriodId(pstBenefitPeriodEmp.getLong(FLD_BENEFIT_PERIOD_ID));
            return entBenefitPeriodEmp;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodEmp(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        BenefitPeriodEmp entBenefitPeriodEmp = fetchExc(entity.getOID());
        entity = (Entity) entBenefitPeriodEmp;
        return entBenefitPeriodEmp.getOID();
    }

    public static synchronized long updateExc(BenefitPeriodEmp entBenefitPeriodEmp) throws DBException {
        try {
            if (entBenefitPeriodEmp.getOID() != 0) {
                PstBenefitPeriodEmp pstBenefitPeriodEmp = new PstBenefitPeriodEmp(entBenefitPeriodEmp.getOID());
                pstBenefitPeriodEmp.setLong(FLD_EMPLOYEE_ID, entBenefitPeriodEmp.getEmployeeId());
                pstBenefitPeriodEmp.setDouble(FLD_AMOUNT_PART_1, entBenefitPeriodEmp.getAmountPart1());
                pstBenefitPeriodEmp.setDouble(FLD_AMOUNT_PART_2, entBenefitPeriodEmp.getAmountPart2());
                pstBenefitPeriodEmp.setString(FLD_LEVEL_CODE, entBenefitPeriodEmp.getLevelCode());
                pstBenefitPeriodEmp.setInt(FLD_LEVEL_POINT, entBenefitPeriodEmp.getLevelPoint());
                pstBenefitPeriodEmp.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodEmp.getBenefitPeriodId());
                pstBenefitPeriodEmp.update();
                return entBenefitPeriodEmp.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodEmp(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((BenefitPeriodEmp) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstBenefitPeriodEmp pstBenefitPeriodEmp = new PstBenefitPeriodEmp(oid);
            pstBenefitPeriodEmp.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodEmp(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(BenefitPeriodEmp entBenefitPeriodEmp) throws DBException {
        try {
            PstBenefitPeriodEmp pstBenefitPeriodEmp = new PstBenefitPeriodEmp(0);
            pstBenefitPeriodEmp.setLong(FLD_EMPLOYEE_ID, entBenefitPeriodEmp.getEmployeeId());
            pstBenefitPeriodEmp.setDouble(FLD_AMOUNT_PART_1, entBenefitPeriodEmp.getAmountPart1());
            pstBenefitPeriodEmp.setDouble(FLD_AMOUNT_PART_2, entBenefitPeriodEmp.getAmountPart2());
            pstBenefitPeriodEmp.setString(FLD_LEVEL_CODE, entBenefitPeriodEmp.getLevelCode());
            pstBenefitPeriodEmp.setInt(FLD_LEVEL_POINT, entBenefitPeriodEmp.getLevelPoint());
            pstBenefitPeriodEmp.setLong(FLD_BENEFIT_PERIOD_ID, entBenefitPeriodEmp.getBenefitPeriodId());
            pstBenefitPeriodEmp.insert();
            entBenefitPeriodEmp.setOID(pstBenefitPeriodEmp.getLong(FLD_BENEFIT_PERIOD_EMP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstBenefitPeriodEmp(0), DBException.UNKNOWN);
        }
        return entBenefitPeriodEmp.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((BenefitPeriodEmp) entity);
    }

    public static void resultToObject(ResultSet rs, BenefitPeriodEmp entBenefitPeriodEmp) {
        try {
            entBenefitPeriodEmp.setOID(rs.getLong(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_BENEFIT_PERIOD_EMP_ID]));
            entBenefitPeriodEmp.setEmployeeId(rs.getLong(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_EMPLOYEE_ID]));
            entBenefitPeriodEmp.setAmountPart1(rs.getDouble(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_AMOUNT_PART_1]));
            entBenefitPeriodEmp.setAmountPart2(rs.getDouble(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_AMOUNT_PART_2]));
            entBenefitPeriodEmp.setLevelCode(rs.getString(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_LEVEL_CODE]));
            entBenefitPeriodEmp.setLevelPoint(rs.getInt(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_LEVEL_POINT]));
            entBenefitPeriodEmp.setBenefitPeriodId(rs.getLong(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_BENEFIT_PERIOD_ID]));
        } catch (Exception e) {
        }
    }
    
    public static Vector list(int limitStart, int recordToGet, long periodId, String empNum, String empName) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT pay_period.PERIOD, hr_employee.EMPLOYEE_NUM, hr_employee.FULL_NAME, pay_benefit_period_emp.LEVEL_CODE, ";
            sql += " pay_benefit_period_emp.LEVEL_POINT, ";
            sql += " pay_benefit_period_emp.AMOUNT_PART_1, ";
            sql += " pay_benefit_period_emp.AMOUNT_PART_2 FROM pay_benefit_period_emp ";
            sql += " INNER JOIN hr_employee ON pay_benefit_period_emp.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_benefit_period ON pay_benefit_period_emp.BENEFIT_PERIOD_ID=pay_benefit_period.BENEFIT_PERIOD_ID ";
            sql += " INNER JOIN pay_period ON pay_benefit_period.PERIOD_ID=pay_period.PERIOD_ID ";
            sql += " WHERE 1=1 ";
            /* AND pay_period.PERIOD_ID=504404524367701516 AND hr_employee.EMPLOYEE_NUM='5013' AND hr_employee.FULL_NAME='KUS RIJANTO' */
            if (periodId > 0) {
                sql = sql + " AND pay_period.PERIOD_ID=" + periodId;
            }
            
            if (empNum != null && empNum.length() > 0) {
                sql = sql + " AND hr_employee.EMPLOYEE_NUM='" + empNum + "' ";
            }
            
            if (empName != null && empName.length() > 0) {
                sql = sql + " AND hr_employee.FULL_NAME LIKE '%" + empName +"%' ";
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SrcBenefitEmp benefitEmp = new SrcBenefitEmp();
                benefitEmp.setPeriod(rs.getString(PstPayPeriod.fieldNames[PstPayPeriod.FLD_PERIOD]));
                benefitEmp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                benefitEmp.setEmployeeName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                benefitEmp.setLevelCode(rs.getString(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_LEVEL_CODE]));
                benefitEmp.setLevelPoint(rs.getInt(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_LEVEL_POINT]));
                benefitEmp.setFlatService(rs.getDouble(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_AMOUNT_PART_1]));
                benefitEmp.setServicePoint(rs.getDouble(PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_AMOUNT_PART_2]));
                lists.add(benefitEmp);
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
    
    public static boolean checkOID(long entBenefitEmpId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_BENEFIT_PERIOD_EMP + " WHERE "
                    + PstBenefitPeriodEmp.fieldNames[PstBenefitPeriodEmp.FLD_BENEFIT_PERIOD_EMP_ID] + " = " + entBenefitEmpId;

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

    public static int getCount(long periodId, String empNum, String empName) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(pay_benefit_period_emp.BENEFIT_PERIOD_EMP_ID) AS total_emp ";
            sql += " FROM pay_benefit_period_emp ";
            sql += " INNER JOIN hr_employee ON pay_benefit_period_emp.EMPLOYEE_ID=hr_employee.EMPLOYEE_ID ";
            sql += " INNER JOIN pay_benefit_period ON pay_benefit_period_emp.BENEFIT_PERIOD_ID=pay_benefit_period.BENEFIT_PERIOD_ID ";
            sql += " INNER JOIN pay_period ON pay_benefit_period.PERIOD_ID=pay_period.PERIOD_ID ";
            sql += " WHERE 1=1 ";
            /* AND pay_period.PERIOD_ID=504404524367701516 AND hr_employee.EMPLOYEE_NUM='5013' AND hr_employee.FULL_NAME='KUS RIJANTO' */
            if (periodId > 0) {
                sql = sql + " AND pay_period.PERIOD_ID=" + periodId;
            }
            
            if (empNum != null && empNum.length() > 0) {
                sql = sql + " AND hr_employee.EMPLOYEE_NUM=" + empNum;
            }
            
            if (empName != null && empName.length() > 0) {
                sql = sql + " AND hr_employee.FULL_NAME=" + empName;
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
    public static int findLimitStart(long oid, int recordToGet, long periodId, String empNum, String empName) {
        int size = getCount(periodId, empNum, empName);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, periodId, empNum, empName);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    BenefitPeriodEmp entBenefitEmp = (BenefitPeriodEmp) list.get(ls);
                    if (oid == entBenefitEmp.getOID()) {
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
}
