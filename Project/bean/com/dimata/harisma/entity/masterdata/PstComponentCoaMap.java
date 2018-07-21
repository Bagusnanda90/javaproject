/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PaySlipComp;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlipComp;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class PstComponentCoaMap extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_COMPONENT_COA_MAP = "pay_component_coa_map";
    public static final int FLD_COMPONENT_COA_MAP_ID = 0;
    public static final int FLD_FORMULA = 1;
    public static final int FLD_GEN_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static final int FLD_ID_PERKIRAAN = 6;

    public static String[] fieldNames = {
        "COMPONENT_COA_MAP_ID",
        "FORMULA",
        "GEN_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "ID_PERKIRAAN"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstComponentCoaMap() {
    }

    public PstComponentCoaMap(int i) throws DBException {
        super(new PstComponentCoaMap());
    }

    public PstComponentCoaMap(String sOid) throws DBException {
        super(new PstComponentCoaMap(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstComponentCoaMap(long lOid) throws DBException {
        super(new PstComponentCoaMap(0));
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
        return TBL_COMPONENT_COA_MAP;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstComponentCoaMap().getClass().getName();
    }

    public static ComponentCoaMap fetchExc(long oid) throws DBException {
        try {
            ComponentCoaMap entComponentCoaMap = new ComponentCoaMap();
            PstComponentCoaMap pstComponentCoaMap = new PstComponentCoaMap(oid);
            entComponentCoaMap.setOID(oid);
            entComponentCoaMap.setFormula(pstComponentCoaMap.getString(FLD_FORMULA));
            entComponentCoaMap.setGenId(pstComponentCoaMap.getLong(FLD_GEN_ID));
            entComponentCoaMap.setDivisionId(pstComponentCoaMap.getLong(FLD_DIVISION_ID));
            entComponentCoaMap.setDepartmentId(pstComponentCoaMap.getLong(FLD_DEPARTMENT_ID));
            entComponentCoaMap.setSectionId(pstComponentCoaMap.getLong(FLD_SECTION_ID));
            entComponentCoaMap.setIdPerkiraan(pstComponentCoaMap.getLong(FLD_ID_PERKIRAAN));
            return entComponentCoaMap;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCoaMap(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        ComponentCoaMap entComponentCoaMap = fetchExc(entity.getOID());
        entity = (Entity) entComponentCoaMap;
        return entComponentCoaMap.getOID();
    }

    public static synchronized long updateExc(ComponentCoaMap entComponentCoaMap) throws DBException {
        try {
            if (entComponentCoaMap.getOID() != 0) {
                PstComponentCoaMap pstComponentCoaMap = new PstComponentCoaMap(entComponentCoaMap.getOID());
                pstComponentCoaMap.setString(FLD_FORMULA, entComponentCoaMap.getFormula());
                pstComponentCoaMap.setLong(FLD_GEN_ID, entComponentCoaMap.getGenId());
                pstComponentCoaMap.setLong(FLD_DIVISION_ID, entComponentCoaMap.getDivisionId());
                pstComponentCoaMap.setLong(FLD_DEPARTMENT_ID, entComponentCoaMap.getDepartmentId());
                pstComponentCoaMap.setLong(FLD_SECTION_ID, entComponentCoaMap.getSectionId());
                pstComponentCoaMap.setLong(FLD_ID_PERKIRAAN, entComponentCoaMap.getIdPerkiraan());
                pstComponentCoaMap.update();
                return entComponentCoaMap.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCoaMap(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((ComponentCoaMap) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstComponentCoaMap pstComponentCoaMap = new PstComponentCoaMap(oid);
            pstComponentCoaMap.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCoaMap(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(ComponentCoaMap entComponentCoaMap) throws DBException {
        try {
            PstComponentCoaMap pstComponentCoaMap = new PstComponentCoaMap(0);
            pstComponentCoaMap.setString(FLD_FORMULA, entComponentCoaMap.getFormula());
            pstComponentCoaMap.setLong(FLD_GEN_ID, entComponentCoaMap.getGenId());
            pstComponentCoaMap.setLong(FLD_DIVISION_ID, entComponentCoaMap.getDivisionId());
            pstComponentCoaMap.setLong(FLD_DEPARTMENT_ID, entComponentCoaMap.getDepartmentId());
            pstComponentCoaMap.setLong(FLD_SECTION_ID, entComponentCoaMap.getSectionId());
            pstComponentCoaMap.setLong(FLD_ID_PERKIRAAN, entComponentCoaMap.getIdPerkiraan());
            pstComponentCoaMap.insert();
            entComponentCoaMap.setOID(pstComponentCoaMap.getLong(FLD_COMPONENT_COA_MAP_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstComponentCoaMap(0), DBException.UNKNOWN);
        }
        return entComponentCoaMap.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((ComponentCoaMap) entity);
    }

    public static void resultToObject(ResultSet rs, ComponentCoaMap entComponentCoaMap) {
        try {
            entComponentCoaMap.setOID(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_COMPONENT_COA_MAP_ID]));
            entComponentCoaMap.setFormula(rs.getString(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_FORMULA]));
            entComponentCoaMap.setGenId(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_GEN_ID]));
            entComponentCoaMap.setDivisionId(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_DIVISION_ID]));
            entComponentCoaMap.setDepartmentId(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_DEPARTMENT_ID]));
            entComponentCoaMap.setSectionId(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_SECTION_ID]));
            entComponentCoaMap.setIdPerkiraan(rs.getLong(PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_ID_PERKIRAAN]));
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
            String sql = "SELECT * FROM " + TBL_COMPONENT_COA_MAP;
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
                ComponentCoaMap entComponentCoaMap = new ComponentCoaMap();
                resultToObject(rs, entComponentCoaMap);
                lists.add(entComponentCoaMap);
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

    public static boolean checkOID(long entComponentCoaMapId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_COMPONENT_COA_MAP + " WHERE "
                    + PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_COMPONENT_COA_MAP_ID] + " = " + entComponentCoaMapId;
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
            String sql = "SELECT COUNT(" + PstComponentCoaMap.fieldNames[PstComponentCoaMap.FLD_COMPONENT_COA_MAP_ID] + ") FROM " + TBL_COMPONENT_COA_MAP;
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
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    ComponentCoaMap entComponentCoaMap = (ComponentCoaMap) list.get(ls);
                    if (oid == entComponentCoaMap.getOID()) {
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
    
    public static long getCoA(long idPerkiraan, long idPeriod) {
        
        String whereCoaMap = " ID_PERKIRAAN="+idPerkiraan+" ";
        Vector listCoaMap = list(0, 0, whereCoaMap, "");
        double total = 0;
        String sql = "";
        if (listCoaMap != null && listCoaMap.size()>0){
            String[] wherePaySlip = new String[listCoaMap.size()];
            for (int i=0; i<listCoaMap.size(); i++){
                ComponentCoaMap compCoaMap = (ComponentCoaMap)listCoaMap.get(i);
                sql = "SELECT pay_slip.PAY_SLIP_ID, DIVISION, DEPARTMENT, SECTION FROM pay_slip ";
                sql += "WHERE pay_slip.PERIOD_ID="+idPeriod;
                if (compCoaMap.getDivisionId() != 0){
                    wherePaySlip[i] = " AND DIVISION='"+getDivisionName(compCoaMap.getDivisionId())+"' ";
                } else {
                    wherePaySlip[i] = " ";
                }
                if (compCoaMap.getDepartmentId() != 0){
                    wherePaySlip[i] += " AND DEPARTMENT='"+getDepartmentName(compCoaMap.getDepartmentId())+"' ";
                } else {
                    wherePaySlip[i] += " ";
                }
                if (compCoaMap.getSectionId() != 0){
                    wherePaySlip[i] += " AND SECTION='"+getSectionName(compCoaMap.getSectionId())+"' ";
                } else {
                    wherePaySlip[i] += " ";
                }
                sql = sql + wherePaySlip[i] +" ";
                
                if (compCoaMap.getFormula().equals("TAKE_HOME_PAY")){
                    total = total + prosesPaySlipVer2(sql, "TI,TD");
                } else {
                    total = total + prosesPaySlip(sql, compCoaMap.getFormula());
                }
                
            }
        }
        return convertLong(total);
    }
    
    public static double prosesPaySlip(String sql, String formula){
        double total = 0;
        DBResultSet dbrs = null;
        try {
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long paySlipId = 0;
            String comp = "";
            
            for (String retval : formula.split(",")) {
                comp = comp + "'" + retval + "',";
            }

            comp = comp + "'0'";
            while (rs.next()) {
                String query = "";
                paySlipId = rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]);
                query = "SELECT SUM(COMP_VALUE) as total  FROM pay_slip_comp ";
                query +="WHERE PAY_SLIP_ID="+paySlipId+" AND COMP_CODE IN("+comp+")";
                total = total + prosesPaySlipComp(query);
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
    
    public static double prosesPaySlipVer2(String sql, String formula){
        double total = 0;
        DBResultSet dbrs = null;
        try {
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long paySlipId = 0;
            String comp = "";
            
            for (String retval : formula.split(",")) {
                comp = comp + "'" + retval + "',";
            }

            comp = comp + "'0'";
            while (rs.next()) {
                String query = "";
                paySlipId = rs.getLong(PstPaySlip.fieldNames[PstPaySlip.FLD_PAY_SLIP_ID]);
                query = "SELECT pay_slip_comp.COMP_VALUE FROM pay_slip_comp ";
                query +="WHERE PAY_SLIP_ID="+paySlipId+" AND COMP_CODE IN("+comp+")";
                total = total + prosesPaySlipCompVer2(query);

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
    
    public static double prosesPaySlipComp(String sql){
        double total = 0;
        DBResultSet dbrs = null;
        try {
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
    
    public static double prosesPaySlipCompVer2(String sql){
        double total = 0;
        double[] nilai = new double[2];
        int inc = 0;
        DBResultSet dbrs = null;
        try {
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                nilai[inc] = rs.getDouble("COMP_VALUE");
                inc++;
            }
            rs.close();
            return nilai[1]-nilai[0];
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return total;
    }
    
    public static String getCompanyName(long oid) {
        String str = "-";
        try {
            Company company = PstCompany.fetchExc(oid);
            str = company.getCompany();
        } catch(Exception e){
            str = "-";
            System.out.println("getCompanyName()=>"+e.toString());
        }
        return str;
    }
    
    public static String getDivisionName(long oid) {
        String str = "-";
        try {
            Division div = PstDivision.fetchExc(oid);
            str = div.getDivision();
        } catch (Exception ex) {
            System.out.println("getDivisionName()=>" + ex.toString());
        }
        return str;
    }

    public static String getDepartmentName(long oid) {
        String str = "-";
        try {
            Department depart = PstDepartment.fetchExc(oid);
            str = depart.getDepartment();
        } catch (Exception ex) {
            System.out.println("getDepartmentName()=>" + ex.toString());
        }
        return str;
    }

    public static String getSectionName(long oid) {
        String str = "-";
        try {
            Section section = PstSection.fetchExc(oid);
            str = section.getSection();
        } catch (Exception ex) {
            System.out.println("getSectionName()=>" + ex.toString());
        }
        return str;
    }
    
    /* Convert Long */
    public static long convertLong(double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(0, RoundingMode.HALF_DOWN);
        return bDecimal.longValue();
    }
    
    /* 
     * Get pay Slip Component
     * Update : 2016-07-04
     * Author : Hendra Putu
     */
    public static Vector getPaySlipComponent(long idPeriod, long divisionId) {
        String whereClause = "";
        /* Get data pay slip */
        /* dengan parameter : idPeriod, company, division */
        String divisionName = getDivisionName(divisionId);
        whereClause  = PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+"="+idPeriod;

        if (divisionId != 0){
            whereClause += " AND " + PstPaySlip.fieldNames[PstPaySlip.FLD_DIVISION]+"='"+divisionName+"' ";
}
        Vector listPaySlip = PstPaySlip.list(0, 0, whereClause, "");
        String whereIn = "";
        if (listPaySlip != null && listPaySlip.size()>0){
            for(int i=0; i<listPaySlip.size(); i++){
                PaySlip paySlip = (PaySlip)listPaySlip.get(i);
                whereIn = whereIn + paySlip.getOID()+",";
            }
        }
        Vector listPaySlipComp = new Vector();
        if (whereIn.length()>0){
            whereIn = whereIn.substring(0, whereIn.length()-1);
            whereClause = PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+" IN ("+whereIn+")";
            listPaySlipComp = PstPaySlipComp.list(0, 0, whereClause, "");
        }

        return listPaySlipComp;
    }
    /*
     * Simulasi:
     * Chart of Account = Gaji Pokok
     * 1) Mapping ::
     *      PT.BPD | Not Division | BNF01
     *      Maka : akan mencari data pada semua division
     * 
     * 2) Mapping ::
     *      PT.BPD | Division SDM | BNF01
     *      Maka : akan mencari data pada Division SDM saja
     * 
     * 3) Mapping ::
     *      PT.BPD | Division SDM | BNF01
     *      PT.BPD | -            | BNF02
     *      Maka : akan mencari data pada semua division
     * 
     * Cara mengecek division, dapatkan value nya melalui parameter pencarian Jurnal Report
     */
    
    public static double getValueCoa(long oidPerkiraan, long oidPeriod, long oidDivision){
        double total = 0;
        boolean check = false;
        long oidMBT = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("MASA_BEBAS_TUGAS")));
        Vector listData = new Vector();
        String components = "";
        String formula1 = "";
        String formula2 = "";
        String whereClause = fieldNames[FLD_ID_PERKIRAAN]+"="+oidPerkiraan;
        listData = list(0, 0, whereClause, ""); 
        /* mendapatkan nilai coa mapping */
        if (listData != null && listData.size()>0){
            for(int i=0; i<listData.size(); i++){
                ComponentCoaMap coaMap = (ComponentCoaMap)listData.get(i);
                if (coaMap.getDivisionId() != 0){
                    if (coaMap.getDivisionId() == oidDivision){
                        formula1 = coaMap.getFormula();
                    }
                    check = true;
                    break; /* jika sudah ditumukan, maka hentikan looping */
                } else {
                    formula2 = coaMap.getFormula();
                    check = false;
                }
            }
        } else {
            total = 0;
        }
        /* prepare data pay slip component */
        String whereIn = "";
        String divisionName = getDivisionName(oidDivision);
        /* cek apakah divisi regular apa divisi masa bebas tugas */
        if (oidDivision == oidMBT){
            /*
            whereClause = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+oidMBT;
            Vector empList = PstEmployee.list(0, 0, whereClause, "");
            String whereEmp = "";
            for (int i=0; i<empList.size(); i++){
                Employee emp = (Employee)empList.get(i);
                whereEmp = whereEmp + emp.getOID() + ",";
            }*/
            Vector listCareerEmp = PstCareerPath.listCareerUnionDatabank("");
            /* Get period */
            PayPeriod payPeriod = new PayPeriod();
            String periodFrom = "";
            String periodTo = "";
            int intPeriodFrom = 0;
            int intPeriodTo = 0;
            try {
                payPeriod = PstPayPeriod.fetchExc(oidPeriod);
                periodFrom = ""+payPeriod.getStartDate();
                periodTo = ""+payPeriod.getEndDate();
                intPeriodFrom = PstCareerPath.getConvertDateToInt(periodFrom);
                intPeriodTo = PstCareerPath.getConvertDateToInt(periodTo);
            } catch(Exception e){
                System.out.println(e.toString());
            }
            String whereEmp = "";
            
            if (listCareerEmp != null && listCareerEmp.size()>0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (int i=0; i<listCareerEmp.size(); i++){
                    CareerPath empCareer = (CareerPath)listCareerEmp.get(i);
                    String workFrom = "0000-00-00";
                    String workTo = "0000-00-00";
                    if (empCareer.getWorkFrom() != null){
                        workFrom = sdf.format(empCareer.getWorkFrom());
                    }
                    if (empCareer.getWorkTo() != null){
                        workTo = sdf.format(empCareer.getWorkTo());
                    }
                     
                    
                    int intWorkFrom = PstCareerPath.getConvertDateToInt(workFrom);
                    int intWorkTo = PstCareerPath.getConvertDateToInt(workTo);
                    boolean ketemu = PstCareerPath.checkDataByPeriod(intWorkFrom, intWorkTo, intPeriodFrom, intPeriodTo);
                    if (ketemu){
                        if (empCareer.getDivisionId() == oidDivision){
                            whereEmp = whereEmp + empCareer.getEmployeeId() + ",";
                        }
                    }
                }
                whereEmp = whereEmp.substring(0, whereEmp.length()-1);
            }

            whereClause  = PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+"="+oidPeriod;
            whereClause += " AND "+ PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID]+" IN("+whereEmp+")";
        } else {
            /* get pay slip data */
            whereClause  = PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+"="+oidPeriod;
            whereClause += " AND "+ PstPaySlip.fieldNames[PstPaySlip.FLD_DIVISION]+"='"+divisionName+"'";
            
        }
        
        Vector paySlipList = PstPaySlip.list(0, 0, whereClause, "");
        if (paySlipList != null && paySlipList.size()>0){
            for (int i=0; i<paySlipList.size(); i++){
                PaySlip paySlip = (PaySlip)paySlipList.get(i);
                whereIn = whereIn + paySlip.getOID() + ",";
            }
            whereIn = whereIn.substring(0, whereIn.length()-1);
        }
        
        if (check == true){
            if (formula1.length()>0){
                for (String comp : formula1.split(",")) {
                    components = components + "'"+comp+"',";
                }
                components = components.substring(0, components.length()-1);
                total = getPaySlipCompTotal(oidPeriod, oidDivision, components, whereIn);
            } else {
                total = 0;
            }
        } else { /* berlaku untuk semua */
            if (formula2.length()>0){
                for (String comp : formula2.split(",")) {
                    components = components + "'"+comp+"',"; 
                }
                components = components.substring(0, components.length()-1);
                total = getPaySlipCompTotal(oidPeriod, oidDivision, components, whereIn);
            } else {
                total = 0;
            }
        }

        return total;
    }
    
    /*
     * Check Perkiraan pada Coa Mapping
     * Update 2016-08-22
     */
    public static boolean isCoaMapping(long oidPerkiraan, long oidDivision){
        boolean ketemu = false;
        String whereClause = fieldNames[FLD_ID_PERKIRAAN]+"="+oidPerkiraan;
        Vector listData = list(0, 0, whereClause, ""); 
        if (listData != null && listData.size()>0){
            for (int i=0; i<listData.size(); i++){
                ComponentCoaMap coaMap = (ComponentCoaMap)listData.get(i);
                if (coaMap.getDivisionId() != 0){
                    if (coaMap.getDivisionId() == oidDivision){
                        ketemu = true;
                    }
                } else {
                    ketemu = true;
                }
            }
        }
        return ketemu;
    }

    /* Update 2016-08-25 */
    public static Vector getMBTinCareer(String dateFrom, String dateTo, long divisionId) {
        Vector employeeList = new Vector();
        String[] arrDFrom = dateFrom.split("-");
        String[] arrDTo = dateTo.split("-");
        
        boolean ketemu = false;
        int intPeriodFrom = Integer.valueOf(arrDFrom[0] + arrDFrom[1] + arrDFrom[2]);
        int intPeriodTo = Integer.valueOf(arrDTo[0] + arrDTo[1] + arrDTo[2]);
        String strBiner = "";
        int[] biner = new int[8];
        String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID]+"="+divisionId;
        whereClause += " AND " + PstCareerPath.fieldNames[PstCareerPath.FLD_HISTORY_GROUP] + "=" + PstCareerPath.RIWAYAT_JABATAN;
        String orderBy = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
        Vector listCareer = PstCareerPath.list(0, 0, whereClause, orderBy);
        if (listCareer != null && listCareer.size() > 0) {
            for (int c = 0; c < listCareer.size(); c++) {
                CareerPath career = (CareerPath) listCareer.get(c);
                String workFrom = "" + career.getWorkFrom();
                String workTo = "" + career.getWorkTo();
                String[] arrWorkFrom = workFrom.split("-");
                String[] arrWorkTo = workTo.split("-");
                int intWorkFrom = Integer.valueOf(arrWorkFrom[0] + arrWorkFrom[1] + arrWorkFrom[2]);
                int intWorkTo = Integer.valueOf(arrWorkTo[0] + arrWorkTo[1] + arrWorkTo[2]);
                for (int b = 0; b < biner.length; b++) {
                    biner[b] = 0;
                }
                strBiner = "";
                if (intWorkFrom >= intPeriodFrom) {
                    biner[0] = 1;
                } else { /* intWorkFrom < intPeriodFrom */
                    biner[1] = 1;
                }
                if (intWorkFrom >= intPeriodTo) {
                    biner[2] = 1;
                } else { /* intWorkFrom < intPeriodTo */
                    biner[3] = 1;
                }

                if (intWorkTo >= intPeriodFrom) {
                    biner[4] = 1;
                } else { /* intWorkTo < intPeriodFrom */
                    biner[5] = 1;
                }
                if (intWorkTo >= intPeriodTo) {
                    biner[6] = 1;
                } else { /* intWorkTo < intPeriodTo */
                    biner[7] = 1;
                }

                for (int b = 0; b < biner.length; b++) {
                    strBiner = strBiner + biner[b];
                }
                if (strBiner.equals("10011001")) {
                    /*
                     * Pf ===================== Pt
                     *      Sd =========== Ed
                     */
                    employeeList.add("(C1) "+career.getEmployeeId()+ "WF: "+career.getWorkFrom()+ "; WT: "+career.getWorkTo());
                    ketemu = true;
                    break;
                }
                if (strBiner.equals("01011010")) {
                    /*
                     *      Pf ======= Pt
                     * Sd ================== Ed
                     */
                    employeeList.add("(C2) "+career.getEmployeeId()+ "WF: "+career.getWorkFrom()+ "; WT: "+career.getWorkTo());
                    ketemu = true;
                    break;
                }
                if (strBiner.equals("10011010")) {
                    /* 
                     * Pf ================== Pt
                     *          Sd ================ Ed
                     */
                    employeeList.add("(C3) "+career.getEmployeeId()+ "WF: "+career.getWorkFrom()+ "; WT: "+career.getWorkTo());
                    ketemu = true;
                    break;
                }
                if (strBiner.equals("01011001")) {
                    /*
                     *          Pf ============ Pt
                     * Sd ============= Ed
                     */
                    employeeList.add("(C4) "+career.getEmployeeId()+ "WF: "+career.getWorkFrom()+ "; WT: "+career.getWorkTo());
                    ketemu = true;
                    break;
                }
                if (strBiner.equals("01010101")) {
                    /*
                     *              Pf ========== Pt
                     * Sd ===== Ed
                     */
                    //output = "<div>Kondisi ke-5:</div>";
                    //output += "<div>Cari ke databank</div>";
                    String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=" + career.getEmployeeId();
                    Vector dataEmp = PstEmployee.list(0, 0, where, "");
                    if (dataEmp != null && dataEmp.size() > 0) {
                        Employee emp = (Employee) dataEmp.get(0);
                        if (divisionId == emp.getDivisionId()) {
                            employeeList.add("(E1) "+emp.getOID());
                            ketemu = true;
                        }
                    }

                }
                if (strBiner.equals("10101010")) {
                    /* 
                     * Pf ========== Pt
                     *                  Sd ========= Ed
                     */
                    ///output  = "<div>Kondisi ke-6:</div>";
                }
            }
        } 
        if (ketemu == false){
            String where = PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+ "="+divisionId;
            Vector dataEmp = PstEmployee.list(0, 0, where, "");
            if (dataEmp != null && dataEmp.size() > 0) {
                for (int e=0; e<dataEmp.size(); e++){
                    Employee emp = (Employee) dataEmp.get(e);
                    if (divisionId != 0) {
                        if (divisionId == emp.getDivisionId()) {
                            employeeList.add("(E2) "+emp.getOID());
                        }
                    }
                }
            }
        }
        /*
        if (employeeList != null && employeeList.size()>0){
            for(int i=0; i<employeeList.size(); i++){
                Long employeeId = (Long)employeeList.get(i);
            }
        }*/
        return listCareer;
    }
    
    public static int getPaySlipCount(long idPeriod, long divisionId) {
        String whereClause = "";
        /* Get data pay slip */
        /* dengan parameter : idPeriod, company, division */
        String divisionName = getDivisionName(divisionId);
        whereClause  = PstPaySlip.fieldNames[PstPaySlip.FLD_PERIOD_ID]+"="+idPeriod;

        if (divisionId != 0){
            whereClause += " AND " + PstPaySlip.fieldNames[PstPaySlip.FLD_DIVISION]+"='"+divisionName+"' ";
        }
        Vector listPaySlip = PstPaySlip.list(0, 0, whereClause, "");
        
        return listPaySlip.size();
    }
    
    public static double getPaySlipCompTotal(long periodId, long divisionId, String compCodes, String whereIn){
        String whereClause = "";
        
        whereClause = PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" IN("+compCodes+") AND ";
        whereClause += PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+" IN("+whereIn+")";
        
        double total = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(pay_slip_comp.COMP_VALUE) AS total_value ";
            sql += " FROM pay_slip_comp WHERE "+whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
  
            while (rs.next()) {
                total = rs.getDouble("total_value");
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
