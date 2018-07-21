/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Hendra McHen | 20150210
 */
public class PstPaySimulationStructure extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_PAY_SIMULATION_STRUCT = "pay_simulation_struct";
    public static final int FLD_SIMULATION_STRUCT_ID = 0;
    public static final int FLD_PAY_SIMULATION_ID = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static final int FLD_SAL_LEVEL_ID = 6;
    public static final int FLD_LEVEL_CODE = 7;
    public static final int FLD_COMPONENT_ID = 8;
    public static final int FLD_COMP_CODE = 9;
    public static final int FLD_EMP_COUNT_IN_PRD = 10;
    public static final int FLD_SALARY_AMOUNT = 11;
    public static final int FLD_NEW_EMP_ADD = 12;
    public static final int FLD_SALARY_AMOUNT_ADD = 13;
    public static final int FLD_POSITION_ID = 14;

    public static String[] fieldNames = {
        "SIMULATION_STRUCT_ID", //0
        "PAY_SIMULATION_ID",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID", //5
        "SAL_LEVEL_ID",
        "LEVEL_CODE",
        "COMPONENT_ID",
        "COMP_CODE",
        "EMP_COUNT_IN_PRD", //10
        "SALARY_AMOUNT",
        "NEW_EMP_ADD",
        "SALARY_AMOUNT_ADD",
        "POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //0
        TYPE_LONG + TYPE_FK,
        TYPE_LONG + TYPE_FK,
        TYPE_LONG + TYPE_FK,
        TYPE_LONG + TYPE_FK,
        TYPE_LONG + TYPE_FK, //5
        TYPE_LONG + TYPE_FK,
        TYPE_STRING + TYPE_FK,
        TYPE_LONG + TYPE_FK,
        TYPE_STRING + TYPE_FK,
        TYPE_INT, //10
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public PstPaySimulationStructure() {
    }

    public PstPaySimulationStructure(int i) throws DBException {
        super(new PstPaySimulationStructure());
    }

    public PstPaySimulationStructure(String sOid) throws DBException {
        super(new PstPaySimulationStructure(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPaySimulationStructure(long lOid) throws DBException {
        super(new PstPaySimulationStructure(0));
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
        return TBL_PAY_SIMULATION_STRUCT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPaySimulationStructure().getClass().getName();
    }

    public static PaySimulationStructure fetchExc(long oid) throws DBException {
        try {
            PaySimulationStructure entPaySimulationStruct = new PaySimulationStructure();
            PstPaySimulationStructure pstPaySimulationStruct = new PstPaySimulationStructure(oid);
            entPaySimulationStruct.setOID(oid);
            entPaySimulationStruct.setPaySimulationId(pstPaySimulationStruct.getLong(FLD_PAY_SIMULATION_ID));
            entPaySimulationStruct.setCompanyId(pstPaySimulationStruct.getLong(FLD_COMPANY_ID));
            entPaySimulationStruct.setComponentCode(pstPaySimulationStruct.getString(FLD_COMP_CODE));
            entPaySimulationStruct.setComponentId(pstPaySimulationStruct.getLong(FLD_COMPONENT_ID));
            entPaySimulationStruct.setDepartmentId(pstPaySimulationStruct.getLong(FLD_DEPARTMENT_ID));
            entPaySimulationStruct.setDivisionId(pstPaySimulationStruct.getlong(FLD_DIVISION_ID));
            entPaySimulationStruct.setEmployeeCount(pstPaySimulationStruct.getInt(FLD_EMP_COUNT_IN_PRD));
            entPaySimulationStruct.setLevelCode(pstPaySimulationStruct.getString(FLD_LEVEL_CODE));
            entPaySimulationStruct.setNewEmployeeAdd(pstPaySimulationStruct.getInt(FLD_NEW_EMP_ADD));
            entPaySimulationStruct.setSalLevelId(pstPaySimulationStruct.getLong(FLD_SAL_LEVEL_ID));
            entPaySimulationStruct.setSalaryAmount(pstPaySimulationStruct.getdouble(FLD_SALARY_AMOUNT));
            entPaySimulationStruct.setSalaryAmountAdd(pstPaySimulationStruct.getdouble(FLD_SALARY_AMOUNT_ADD));
            entPaySimulationStruct.setSectionId(pstPaySimulationStruct.getLong(FLD_SECTION_ID));
            entPaySimulationStruct.setPositionId(pstPaySimulationStruct.getLong(FLD_POSITION_ID));

            return entPaySimulationStruct;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationStructure(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PaySimulationStructure entPaySimulationStruct = fetchExc(entity.getOID());
        entity = (Entity) entPaySimulationStruct;
        return entPaySimulationStruct.getOID();
    }

    public static synchronized long updateExc(PaySimulationStructure entPaySimulationStruct) throws DBException {
        try {
            if (entPaySimulationStruct.getOID() != 0) {
                PstPaySimulationStructure pstPaySimulationStruct = new PstPaySimulationStructure(entPaySimulationStruct.getOID());
                pstPaySimulationStruct.setLong(FLD_PAY_SIMULATION_ID, entPaySimulationStruct.getPaySimulationId());
                pstPaySimulationStruct.setLong(FLD_COMPANY_ID, entPaySimulationStruct.getCompanyId());
                pstPaySimulationStruct.setString(FLD_COMP_CODE, entPaySimulationStruct.getComponentCode());
                pstPaySimulationStruct.setLong(FLD_COMPONENT_ID, entPaySimulationStruct.getComponentId());
                pstPaySimulationStruct.setLong(FLD_DEPARTMENT_ID, entPaySimulationStruct.getDepartmentId());
                pstPaySimulationStruct.setLong(FLD_DIVISION_ID, entPaySimulationStruct.getDivisionId());
                pstPaySimulationStruct.setInt(FLD_EMP_COUNT_IN_PRD, entPaySimulationStruct.getEmployeeCount());
                pstPaySimulationStruct.setString(FLD_LEVEL_CODE, entPaySimulationStruct.getLevelCode());
                pstPaySimulationStruct.setInt(FLD_NEW_EMP_ADD, entPaySimulationStruct.getNewEmployeeAdd());
                pstPaySimulationStruct.setLong(FLD_SAL_LEVEL_ID, entPaySimulationStruct.getSalLevelId());
                pstPaySimulationStruct.setDouble(FLD_SALARY_AMOUNT, entPaySimulationStruct.getSalaryAmount());
                pstPaySimulationStruct.setDouble(FLD_SALARY_AMOUNT_ADD, entPaySimulationStruct.getSalaryAmountAdd());
                pstPaySimulationStruct.setLong(FLD_SECTION_ID, entPaySimulationStruct.getSectionId());
                pstPaySimulationStruct.setLong(FLD_POSITION_ID, entPaySimulationStruct.getPositionId());
                pstPaySimulationStruct.update();
                return entPaySimulationStruct.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationStructure(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PaySimulationStructure) entity);
    }
    
    public static int updateExc(Vector<PaySimulationStructure> vPaySimStructure){
        if(vPaySimStructure==null || vPaySimStructure.size()<1){
            return 0;
        }
        else{
            int savedCount=0;
            for(int idx=0;idx < vPaySimStructure.size();idx++){
                try{
                    PaySimulationStructure paySimStructure = vPaySimStructure.get(idx);
                    updateExc(paySimStructure);
                    savedCount++;
                }catch(Exception exc){
                    System.out.println(exc); 
                }
            }
        return savedCount;
    }// return 0;
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPaySimulationStructure pstPaySimulation = new PstPaySimulationStructure(oid);
            pstPaySimulation.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationStructure(0), DBException.UNKNOWN);
        }
        return oid;
    }

     public static synchronized long deleteExcByPaySimOid(long oidPaySim) throws DBException {
        try {
            String delStr = "DELETE FROM " + TBL_PAY_SIMULATION_STRUCT+ " WHERE "+ fieldNames[FLD_PAY_SIMULATION_ID]+"="+oidPaySim;
            PstPaySimulation.delete(delStr);
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationStructure(0), DBException.UNKNOWN);
        }
        return oidPaySim;
    }
    
    
    
    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PaySimulationStructure entPaySimulationStruct) throws DBException {
        try {
            PstPaySimulationStructure pstPaySimulationStruct = new PstPaySimulationStructure(0);
            pstPaySimulationStruct.setLong(FLD_PAY_SIMULATION_ID, entPaySimulationStruct.getPaySimulationId());
            pstPaySimulationStruct.setLong(FLD_COMPANY_ID, entPaySimulationStruct.getCompanyId());
            pstPaySimulationStruct.setString(FLD_COMP_CODE, entPaySimulationStruct.getComponentCode());
            pstPaySimulationStruct.setLong(FLD_COMPONENT_ID, entPaySimulationStruct.getComponentId());
            pstPaySimulationStruct.setLong(FLD_DEPARTMENT_ID, entPaySimulationStruct.getDepartmentId());
            pstPaySimulationStruct.setLong(FLD_DIVISION_ID, entPaySimulationStruct.getDivisionId());
            pstPaySimulationStruct.setInt(FLD_EMP_COUNT_IN_PRD, entPaySimulationStruct.getEmployeeCount());
            pstPaySimulationStruct.setString(FLD_LEVEL_CODE, entPaySimulationStruct.getLevelCode());
            pstPaySimulationStruct.setInt(FLD_NEW_EMP_ADD, entPaySimulationStruct.getNewEmployeeAdd());
            pstPaySimulationStruct.setLong(FLD_SAL_LEVEL_ID, entPaySimulationStruct.getSalLevelId());
            pstPaySimulationStruct.setDouble(FLD_SALARY_AMOUNT, entPaySimulationStruct.getSalaryAmount());
            pstPaySimulationStruct.setDouble(FLD_SALARY_AMOUNT_ADD, entPaySimulationStruct.getSalaryAmountAdd());
            pstPaySimulationStruct.setLong(FLD_SECTION_ID, entPaySimulationStruct.getSectionId());
            pstPaySimulationStruct.setLong(FLD_POSITION_ID, entPaySimulationStruct.getPositionId());
            pstPaySimulationStruct.insert();
            entPaySimulationStruct.setOID(pstPaySimulationStruct.getLong(FLD_SIMULATION_STRUCT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPaySimulationStructure(0), DBException.UNKNOWN);
        }
        return entPaySimulationStruct.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PaySimulationStructure) entity);
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT e.*, c.COMPANY, d.`DIVISION`, p.DEPARTMENT, s.`SECTION`, t.`POSITION` "
                    + " FROM " + TBL_PAY_SIMULATION_STRUCT + " e "
                    + " INNER JOIN pay_general c ON c.`GEN_ID` = e.`COMPANY_ID` "
                    + " INNER JOIN hr_division d ON d.`DIVISION_ID` = e.`DIVISION_ID` "
                    + " INNER JOIN hr_department p ON p.`DEPARTMENT_ID` = e.`DEPARTMENT_ID` "
                    + " INNER JOIN hr_position t ON t.`POSITION_ID` = e.`POSITION_ID` "
                    + " LEFT JOIN hr_section s ON s.`SECTION_ID` = e.`SECTION_ID` ";
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
                PaySimulationStructure paySimulationStructure = new PaySimulationStructure();
                resultToObject(rs, paySimulationStructure);
                paySimulationStructure.setCompany(rs.getString("COMPANY"));
                paySimulationStructure.setDivision(rs.getString("DIVISION"));
                paySimulationStructure.setDepartment(rs.getString("DEPARTMENT"));
                paySimulationStructure.setSection(rs.getString("SECTION"));
                paySimulationStructure.setPosition(rs.getString("POSITION"));
                lists.add(paySimulationStructure);
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

    public static Vector generatePaySimStruct(PaySimulation paySimulation) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        if (paySimulation == null) {
            return null;
        }
        Hashtable mapPayment = getSumPayrollMapEmpID(paySimulation);
        try {
            String strCategory = "";
            if (paySimulation.getEmployeeCategoryIds() != null && paySimulation.getEmployeeCategoryIds().size() > 0) {
                for (int idx = 0; idx < paySimulation.getEmployeeCategoryIds().size(); idx++) {
                    Long lO = (Long) paySimulation.getEmployeeCategoryIds().get(idx);
                    strCategory = strCategory + lO.toString() + (idx < (paySimulation.getEmployeeCategoryIds().size() - 1) ? "," : "");
                }
            }
            Vector vPaySimStruct = PstPaySimulationStructure.listByPaySimulation(paySimulation); int sizeOfStruct = vPaySimStruct==null? 0 : vPaySimStruct.size();
            PayPeriod payPeriode = null;
            try {
                payPeriode = PstPayPeriod.fetchExc(paySimulation.getSourcePayPeriodId());
            } catch (Exception exc) {
            }

            String sql = " SELECT  c.COMPANY, c.`GEN_ID` COMPANY_ID, d.`DIVISION`, d.`DIVISION_ID`, p.DEPARTMENT, p.DEPARTMENT_ID, s.`SECTION`, s.`SECTION_ID`,  t.`POSITION`, t.`POSITION_ID`,"
                    + " lv.`LEVEL_CODE`,sl.`SAL_LEVEL_ID`, COUNT(e.`EMPLOYEE_ID`) " + fieldNames[FLD_EMP_COUNT_IN_PRD] + ", MAX(e.`EMPLOYEE_ID`) EMPLOYEE_ID FROM hr_employee e "
                    + " INNER JOIN pay_general c ON c.`GEN_ID` = e.`COMPANY_ID` "
                    + " INNER JOIN hr_division d ON d.`DIVISION_ID` = e.`DIVISION_ID` "
                    + " INNER JOIN hr_department p ON p.`DEPARTMENT_ID` = e.`DEPARTMENT_ID` "
                    + " INNER JOIN hr_position t ON t.`POSITION_ID` = e.`POSITION_ID` "
                    + " LEFT JOIN hr_section s ON s.`SECTION_ID` = e.`SECTION_ID` "
                    + " INNER JOIN `pay_emp_level` lv ON lv.`EMPLOYEE_ID` = e.`EMPLOYEE_ID` "
                    + " LEFT JOIN pay_sal_level sl ON sl.`LEVEL_CODE` = lv.`LEVEL_CODE` "
                    + " WHERE " + ((strCategory != null && strCategory.length() > 0) ? "e.`EMP_CATEGORY_ID` IN (" + strCategory + ") AND " : "") + " lv.`END_DATE` > \""
                    + Formater.formatDate((payPeriode != null && payPeriode.getEndDate() != null) ? payPeriode.getEndDate() : new Date(), "yyyy-MM-dd") + "\" "
                    + " GROUP BY  lv.`LEVEL_CODE`, e.`SECTION_ID`, p.`DEPARTMENT` "
                    + " ORDER BY c.`COMPANY` , d.`DIVISION`, p.DEPARTMENT, s.`SECTION`, t.`POSITION`, lv.`LEVEL_CODE` ";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                PaySimulationStructure paySimulationStructure = new PaySimulationStructure();
                paySimulationStructure.setPaySimulationId(paySimulation.getOID());
                paySimulationStructure.setCompanyId(rs.getLong(fieldNames[FLD_COMPANY_ID]));
                paySimulationStructure.setDepartmentId(rs.getLong(fieldNames[FLD_DEPARTMENT_ID]));
                paySimulationStructure.setDivisionId(rs.getLong(fieldNames[FLD_DIVISION_ID]));
                paySimulationStructure.setEmployeeCount(rs.getInt(fieldNames[FLD_EMP_COUNT_IN_PRD]));
                paySimulationStructure.setLevelCode(rs.getString(fieldNames[FLD_LEVEL_CODE]));
                paySimulationStructure.setSalLevelId(rs.getLong(fieldNames[FLD_SAL_LEVEL_ID]));
                paySimulationStructure.setSectionId(rs.getLong(fieldNames[FLD_SECTION_ID]));
                paySimulationStructure.setCompany(rs.getString("COMPANY"));
                paySimulationStructure.setDivision(rs.getString("DIVISION"));
                paySimulationStructure.setDepartment(rs.getString("DEPARTMENT"));
                paySimulationStructure.setSection(rs.getString("SECTION"));
                paySimulationStructure.setPosition(rs.getString("POSITION"));
                paySimulationStructure.setPositionId(rs.getLong("POSITION_ID"));
                paySimulationStructure.setSampleEmployeeId(rs.getLong("EMPLOYEE_ID"));

                if (mapPayment != null) {
                    Double payAmount = (Double) mapPayment.get("" + paySimulationStructure.getSampleEmployeeId());
                    paySimulationStructure.setSalaryAmount(payAmount != null ? payAmount.doubleValue() : 0.0);
                }
                try {
                    Vector existPaySim = sizeOfStruct < 1 ? null: listByPaySimulationStructureData(paySimulationStructure);
                    if(existPaySim==null || existPaySim.size()<1){
                      insertExc(paySimulationStructure);
                    }else{
                        double addSalary  = 0;
                        int addEmployee=0;
                        for(int idx=0;idx < existPaySim.size();idx++){
                            PaySimulationStructure payStrk = (PaySimulationStructure) existPaySim.get(idx);
                            addSalary = addSalary + payStrk.getSalaryAmountAdd();
                            addEmployee = addEmployee + payStrk.getNewEmployeeAdd();
                            PstPaySimulationStructure.deleteExc(payStrk.getOID());
                        }
                        paySimulationStructure.setNewEmployeeAdd(addEmployee);
                        paySimulationStructure.setSalaryAmountAdd(addSalary);
                        insertExc(paySimulationStructure);
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
                lists.add(paySimulationStructure);
            }
            paySimulation.setPaySimulationStruct(lists);
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Hashtable<String, Double> getSumPayrollMapEmpID(PaySimulation paySimulation) {

        Hashtable<String, Double> lists = new Hashtable();
        DBResultSet dbrs = null;
        if (paySimulation == null) {
            return null;
        }
        try {
            String strPayComp = "";
            if (paySimulation.getPayrollComponents() != null && paySimulation.getPayrollComponents().size() > 0) {
                for (int idx = 0; idx < paySimulation.getPayrollComponents().size(); idx++) {
                    String strComp = (String) paySimulation.getPayrollComponents().get(idx);
                    strPayComp = strPayComp + strComp + (idx < (paySimulation.getPayrollComponents().size() - 1) ? "," : "");
                }
            }

            String sql = "SELECT ps.`EMPLOYEE_ID`, SUM(pc.COMP_VALUE) AS SUM_PAY FROM `pay_slip` ps "
                    + " INNER JOIN `pay_slip_comp` pc ON pc.`PAY_SLIP_ID` = ps.`PAY_SLIP_ID` "
                    + " INNER JOIN `pay_component` c ON c.`COMP_CODE` = pc.`COMP_CODE` "
                    + " WHERE PERIOD_ID=" + paySimulation.getSourcePayPeriodId() + " AND c.`COMPONENT_ID` IN (" + strPayComp + ") GROUP BY EMPLOYEE_ID";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                lists.put("" + rs.getLong("EMPLOYEE_ID"), new Double(rs.getDouble("SUM_PAY")));
            }
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return null;

    }

    public static void resultToObject(ResultSet rs, PaySimulationStructure entPaySimulationStruct) {
        try {
            entPaySimulationStruct.setOID(rs.getLong(fieldNames[FLD_SIMULATION_STRUCT_ID]));
            entPaySimulationStruct.setPaySimulationId(rs.getLong(fieldNames[FLD_PAY_SIMULATION_ID]));
            entPaySimulationStruct.setCompanyId(rs.getLong(fieldNames[FLD_COMPANY_ID]));
            entPaySimulationStruct.setComponentCode(rs.getString(fieldNames[FLD_COMP_CODE]));
            entPaySimulationStruct.setComponentId(rs.getLong(fieldNames[FLD_COMPONENT_ID]));
            entPaySimulationStruct.setDepartmentId(rs.getLong(fieldNames[FLD_DEPARTMENT_ID]));
            entPaySimulationStruct.setDivisionId(rs.getLong(fieldNames[FLD_DIVISION_ID]));
            entPaySimulationStruct.setEmployeeCount(rs.getInt(fieldNames[FLD_EMP_COUNT_IN_PRD]));
            entPaySimulationStruct.setLevelCode(rs.getString(fieldNames[FLD_LEVEL_CODE]));
            entPaySimulationStruct.setNewEmployeeAdd(rs.getInt(fieldNames[FLD_NEW_EMP_ADD]));
            entPaySimulationStruct.setSalLevelId(rs.getLong(fieldNames[FLD_SAL_LEVEL_ID]));
            entPaySimulationStruct.setSalaryAmount(rs.getDouble(fieldNames[FLD_SALARY_AMOUNT]));
            entPaySimulationStruct.setSalaryAmountAdd(rs.getDouble(fieldNames[FLD_SALARY_AMOUNT_ADD]));
            entPaySimulationStruct.setSectionId(rs.getLong(fieldNames[FLD_SECTION_ID]));
            entPaySimulationStruct.setPositionId(rs.getLong(fieldNames[FLD_POSITION_ID]));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean checkOID(long entPaySimulationId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_PAY_SIMULATION_STRUCT + " WHERE "
                    + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SIMULATION_STRUCT_ID] + " = " + entPaySimulationId;

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
            String sql = "SELECT COUNT(" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SIMULATION_STRUCT_ID] + ") FROM " + TBL_PAY_SIMULATION_STRUCT;
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
                    PaySimulationStructure entPaySimulationStruct = (PaySimulationStructure) list.get(ls);
                    if (oid == entPaySimulationStruct.getOID()) {
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

    public static String getKeyMap(PaySimulationStructure paySimStruct) {
        if (paySimStruct == null || paySimStruct.getOID() == 0) {
            return null;
        }
        return "" + paySimStruct.getCompanyId() + "_" + paySimStruct.getDivisionId()
                + "_" + paySimStruct.getDepartmentId() + "_" + paySimStruct.getSectionId() + "_" + paySimStruct.getPositionId() + "_" + paySimStruct.getLevelCode();
    }

    public static Hashtable<String, PaySimulationStructure> getMapByPaySimulation(PaySimulation paySimulation) {
        if (paySimulation == null || paySimulation.getOID() == 0) {
            return null;
        }
        try {
            String where = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_PAY_SIMULATION_ID] + "=" + paySimulation.getOID();
            String order = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_COMPANY_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DIVISION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DEPARTMENT_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SECTION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_POSITION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_LEVEL_CODE];

            Vector vPaySimStruct = PstPaySimulationStructure.list(0, 10000, where, order);

            if (vPaySimStruct != null && vPaySimStruct.size() > 0) {
                Hashtable<String, PaySimulationStructure> maps = new Hashtable();
                for (int idx = 0; idx < vPaySimStruct.size(); idx++) {
                    PaySimulationStructure paySimStruct = (PaySimulationStructure) vPaySimStruct.get(idx);
                    maps.put(getKeyMap(paySimStruct), paySimStruct);
                }
                return maps;
            }
        } catch (Exception exc) {
            System.out.println("getMapByPaySimulation >>> " + exc);
        }
        return null;
    }

    public static Vector<PaySimulationStructure> listByPaySimulation(PaySimulation paySimulation) {
        if (paySimulation == null || paySimulation.getOID() == 0) {
            return null;
        }
        try {
            String where = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_PAY_SIMULATION_ID] + "=" + paySimulation.getOID();
            String order = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_COMPANY_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DIVISION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DEPARTMENT_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SECTION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_POSITION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_LEVEL_CODE];

            Vector vPaySimStruct = PstPaySimulationStructure.list(0, 10000, where, order);

            return vPaySimStruct;
        } catch (Exception exc) {
            System.out.println("getMapByPaySimulation >>> " + exc);
        }
        return null;
    }
    

    public static Vector<PaySimulationStructure> listByPaySimulationStructureData(PaySimulationStructure paySimulationStruct) {
        if (paySimulationStruct == null || paySimulationStruct.getOID() == 0) {
            return null;
        }
        try {
            String where = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_PAY_SIMULATION_ID] + "=" + paySimulationStruct.getPaySimulationId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_COMPANY_ID] + "=" + paySimulationStruct.getComponentId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DIVISION_ID] + "=" + paySimulationStruct.getDivisionId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DEPARTMENT_ID] + "=" + paySimulationStruct.getDepartmentId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SECTION_ID] + "=" + paySimulationStruct.getSectionId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_POSITION_ID] + "=" + paySimulationStruct.getPositionId()
                    +" AND " + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_LEVEL_CODE] + "=\"" + paySimulationStruct.getLevelCode()+"\"";
                    
            String order = "" + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_COMPANY_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DIVISION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_DEPARTMENT_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_SECTION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_POSITION_ID]
                    + "," + PstPaySimulationStructure.fieldNames[PstPaySimulationStructure.FLD_LEVEL_CODE];

            Vector vPaySimStruct = PstPaySimulationStructure.list(0, 10000, where, order);

            return vPaySimStruct;
        } catch (Exception exc) {
            System.out.println("getMapByPaySimulation >>> " + exc);
        }
        return null;
    }    
    

}
