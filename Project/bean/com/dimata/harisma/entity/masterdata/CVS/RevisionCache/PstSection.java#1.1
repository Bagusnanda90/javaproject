
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.masterdata;

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
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.sesssection.SessSection;

public class PstSection extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String SESS_HR_SECTION = "SESS_HR_SECTION";
    public static final String TBL_HR_SECTION = "hr_section";//"HR_SECTION";
    public static final int FLD_SECTION_ID = 0;
    public static final int FLD_DEPARTMENT_ID = 1;
    public static final int FLD_SECTION = 2;
    public static final int FLD_DESCRIPTION = 3;
    public static final int FLD_SECTION_LINK_TO = 4;

	public static final String[] fieldNames = {
        "SECTION_ID",
        "DEPARTMENT_ID",
        "SECTION",
        "DESCRIPTION",
        "SECTION_LINK_TO"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public PstSection() {
    }

    public PstSection(int i) throws DBException {
        super(new PstSection());
    }

    public PstSection(String sOid) throws DBException {
        super(new PstSection(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstSection(long lOid) throws DBException {
        super(new PstSection(0));
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
        return TBL_HR_SECTION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstSection().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Section section = fetchExc(ent.getOID());
        ent = (Entity) section;
        return section.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Section) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Section) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Section fetchExc(long oid) throws DBException {
        try {
            Section section = new Section();
            PstSection pstSection = new PstSection(oid);
            section.setOID(oid);

            section.setDepartmentId(pstSection.getlong(FLD_DEPARTMENT_ID));
            section.setSection(pstSection.getString(FLD_SECTION));
            section.setDescription(pstSection.getString(FLD_DESCRIPTION));
            section.setSectionLinkTo(pstSection.getString(FLD_SECTION_LINK_TO));
            return section;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSection(0), DBException.UNKNOWN);
        }
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

    public static long insertExc(Section section) throws DBException {
        try {
            PstSection pstSection = new PstSection(0);

            pstSection.setLong(FLD_DEPARTMENT_ID, section.getDepartmentId());
            pstSection.setString(FLD_SECTION, section.getSection());
            pstSection.setString(FLD_DESCRIPTION, section.getDescription());
            pstSection.setString(FLD_SECTION_LINK_TO, section.getSectionLinkTo());
            pstSection.insert();
            section.setOID(pstSection.getlong(FLD_SECTION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSection(0), DBException.UNKNOWN);
        }
        return section.getOID();
    }

    public static long updateExc(Section section) throws DBException {
        try {
            if (section.getOID() != 0) {
                PstSection pstSection = new PstSection(section.getOID());
                pstSection.setLong(FLD_DEPARTMENT_ID, section.getDepartmentId());
                pstSection.setString(FLD_SECTION, section.getSection());
                pstSection.setString(FLD_DESCRIPTION, section.getDescription());
                pstSection.setString(FLD_SECTION_LINK_TO, section.getSectionLinkTo());
                pstSection.update();
                return section.getOID();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSection(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstSection pstSection = new PstSection(oid);
            pstSection.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstSection(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_SECTION;
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
//                        System.out.println("sql section : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Section section = new Section();
                resultToObject(rs, section);
                lists.add(section);
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
     * create by satrya 2013-08-13 untuk mencrasi semua section
     *
     * @return
     */
    public static Hashtable hashlistSection() {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SECTION;


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Section section = new Section();
                resultToObject(rs, section);
                if (section != null && section.getDepartmentId() != 0) {
                    lists.put(section.getOID(), section);
                }

            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    /**
     * Create by satrya 2013-02-19
     *
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */
    public static Vector listByEmployee(int limitStart, int recordToGet, long oidDepartement, long oidSection, String empNumb, String fullName, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            if (oidDepartement != 0) {
                String sql = " SELECT * FROM " + PstEmployee.TBL_HR_EMPLOYEE + " HE "
                        + " LEFT JOIN " + TBL_HR_SECTION + " HS "
                        + " ON HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = HS." + PstSection.fieldNames[PstSection.FLD_SECTION_ID];


                sql = sql + " WHERE  HS." + fieldNames[FLD_DEPARTMENT_ID] + " = " + oidDepartement;


                //update by satrya 2013-04-02
                if (empNumb != null && empNumb != "") {
                    Vector vectNum = logicParser(empNumb);
                    sql = sql + " AND ";
                    if (vectNum != null && vectNum.size() > 0) {
                        sql = sql + " (";
                        for (int i = 0; i < vectNum.size(); i++) {
                            String str = (String) vectNum.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                sql = sql + str.trim();
                            }
                        }
                        sql = sql + ")";
                    }

                }
                //update by satrya 2012-07-16
                if (fullName != null && fullName != "") {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                    Vector vectFullName = logicParser(fullName);
                    sql = sql + " AND ";
                    if (vectFullName != null && vectFullName.size() > 0) {
                        sql = sql + " ( ";
                        for (int i = 0; i < vectFullName.size(); i++) {
                            String str = (String) vectFullName.get(i);
                            if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                                sql = sql + " " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                        + " LIKE '%" + str.trim() + "%' ";
                            } else {
                                sql = sql + str.trim();
                            }
                        }
                        sql = sql + " ) ";
                    }

                }
//			if(whereClause != null && whereClause.length() > 0)
//				sql = sql + " WHERE " + whereClause;

                if (oidSection != 0) {
                    sql = sql + " AND HS." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSection;
                }

                sql = sql + " GROUP BY HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID];

                if (order != null && order.length() > 0) {
                    sql = sql + " ORDER BY " + order;
                }



                if (limitStart == 0 && recordToGet == 0) {
                    sql = sql + "";
                } else {
                    sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                }
//                        System.out.println("sql section : " + sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while (rs.next()) {
                    Section section = new Section();
                    resultToObject(rs, section);
                    lists.add(section);
                }
                rs.close();
            }
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    private static void resultToObject(ResultSet rs, Section section) {
        try {
            section.setOID(rs.getLong(PstSection.fieldNames[PstSection.FLD_SECTION_ID]));
            section.setDepartmentId(rs.getLong(PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]));
            section.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
            section.setDescription(rs.getString(PstSection.fieldNames[PstSection.FLD_DESCRIPTION]));
            section.setSectionLinkTo(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION_LINK_TO]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long sectionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SECTION + " WHERE "
                    + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + " = " + sectionId;

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
            String sql = "SELECT COUNT(" + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + ") FROM " + TBL_HR_SECTION;
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
    public static int findLimitStart(long oid, int recordToGet, String whereClause, String order) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    Section section = (Section) list.get(ls);
                    if (oid == section.getOID()) {
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

    public static boolean checkDepartment(long departmentId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_SECTION + " WHERE "
                    + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + departmentId;

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

    public static boolean checkMaster(long oid) {
        if (PstEmployee.checkSection(oid)) {
            return true;
        } else {
            if (PstCareerPath.checkSection(oid)) {
                return true;
            } else {
                return false;
            }
        }
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

    //Gede_1Maret2012{
    //untuk structure employee
    public static Vector list2(int limitStart, int recordToGet, String whereClauseSec, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT sec.* FROM " + PstSection.TBL_HR_SECTION
                    + " sec INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE
                    + " emp ON sec." + PstSection.fieldNames[PstSection.FLD_SECTION_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION
                    + " pos ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + "=pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID];

            if (whereClauseSec != null && whereClauseSec.length() > 0) {
                sql = sql + " WHERE " + whereClauseSec;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql Section " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Section section = new Section();
                resultToObject(rs, section);
                lists.add(section);
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
    //}

    public static Vector listSection(long oidDepartment) {
        Vector section_value = new Vector(1, 1);
        Vector section_key = new Vector(1, 1);
        Vector listSec = PstSection.list(0, 0, PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment, " SECTION ");
        for (int i = 0; i < listSec.size(); i++) {
            Section sec = (Section) listSec.get(i);
            section_key.add(sec.getSection());
            section_value.add(String.valueOf(sec.getOID()));
        }
         Vector listDepartment = new Vector();
        listDepartment.add(section_value);
        listDepartment.add(section_key);
        return listDepartment;
    }
    
    
    
    /**
     * list Department hashtable
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @return 
     */
     public static Hashtable hashListSection(int limitStart, int recordToGet, String whereClause) {
        DBResultSet dbrs = null;
        Hashtable hashListSection= new Hashtable();
        try {
            String sql = "SELECT * FROM " + TBL_HR_SECTION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
                sql = sql + " ORDER BY " + fieldNames[FLD_DEPARTMENT_ID]+","+fieldNames[FLD_SECTION]+" ASC ";
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            //System.out.println("sql xxxxx " + sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            SessSection sessSection = new SessSection();
            Section section = new Section();
            long prevDeptId=0;
            while (rs.next()) {
                
               section = new Section();
                long oidDeptId = rs.getLong(fieldNames[FLD_DEPARTMENT_ID]);
                if(oidDeptId!=0 && prevDeptId!=oidDeptId){
                      sessSection = new SessSection();
                    hashListSection.put(""+oidDeptId, sessSection);                  
                }
                
                 resultToObject(rs, section);
                prevDeptId=section.getDepartmentId();
                sessSection.addObjSection(section);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashListSection;
        }
    }
    
}
