
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.entity.employee.assessment;

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

public class PstAssessmentFormItem extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_ASS_FORM_ITEM = "hr_ass_form_item";//
    public static final int FLD_ASS_FORM_ITEM_ID = 0;
    public static final int FLD_ASS_FORM_SECTION_ID = 1;
    public static final int FLD_TITLE = 2;
    public static final int FLD_TITLE_L2 = 3;
    public static final int FLD_ITEM_POIN_1 = 4;
    public static final int FLD_ITEM_POIN_2 = 5;
    public static final int FLD_ITEM_POIN_3 = 6;
    public static final int FLD_ITEM_POIN_4 = 7;
    public static final int FLD_ITEM_POIN_5 = 8;
    public static final int FLD_ITEM_POIN_6 = 9;
    public static final int FLD_TYPE = 10;
    public static final int FLD_ORDER_NUMBER = 11;
    public static final int FLD_NUMBER = 12;
    public static final int FLD_PAGE = 13;
    public static final int FLD_HEIGHT = 14;
    public static final int FLD_KPI_LIST_ID = 15;
    public static final int FLD_WEIGHT_POINT = 16;
    public static final int FLD_KPI_TARGET = 17;
    public static final int FLD_KPI_UNIT = 18;
    public static final int FLD_KPI_NOTE = 19;
    public static final String[] fieldNames = {
        "ASS_FORM_ITEM_ID",//0
        "ASS_FORM_SECTION_ID",
        "TITLE",
        "TITLE_L2",
        "ITEM_POIN_1",
        "ITEM_POIN_2",
        "ITEM_POIN_3",
        "ITEM_POIN_4",
        "ITEM_POIN_5",
        "ITEM_POIN_6",//9
        "TYPE",
        "ORDER_NUMBER",
        "NUMBER",
        "PAGE",
        "HEIGHT",
        "KPI_LIST_ID",
        "WEIGHT_POINT",
        "KPI_TARGET",
        "KPI_UNIT",
        "KPI_NOTE" //19
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
    };
    public static final int ITEM_TYPE_SPACE = 0;
    public static final int ITEM_TYPE_COL_2_WITHOUT_TEXT = 1;
    public static final int ITEM_TYPE_COL_1_WITH_TEXT = 2;
    public static final int ITEM_TYPE_COL_2_HEADER = 3;
    public static final int ITEM_TYPE_TEXT = 4;
    public static final int ITEM_TYPE_TEXT_BOLD = 5;
    public static final int ITEM_TYPE_SELECT_1_WITH_RANGE = 6;
    public static final int ITEM_TYPE_SELECT_2_WITHOUT_RANGE = 7;
    public static final int ITEM_TYPE_SELECT_HEADER = 8;
    public static final int ITEM_TYPE_INPUT_WITH_DOT = 9;
    public static final int ITEM_TYPE_INPUT_CHECK = 10;
    public static final int ITEM_TYPE_INPUT_CHECK_HEADER = 11;
    public static final int ITEM_TYPE_2_COL_ASS_COMM = 12;
    public static final int ITEM_TYPE_2_COL_OVERALL_COMM = 13;
    public static final int ITEM_TYPE_INPUT_ASS_COMM = 14;
    public static final int ITEM_TYPE_INPUT_EMP_COMM = 15;
    public static final String[] fieldTypesName = {
        "TYPE SPACE",
        "TYPE 2 COLUMNS WITHOUT TEXT",
        "TYPE 1 COLUMNS WITH TEXT",
        "TYPE 2 COLUMNS HEADER",
        "TYPE TEXT ONLY",
        "TYPE TEXT ONLY BOLD",
        "TYPE MULTY SELECT WITH RANGE",
        "TYPE MULTY SELECT WITHOUT RANGE",
        "TYPE MULTY SELECT HEADER",
        "TYPE INPUT WITH DOT",
        "TYPE INPUT CHECK",
        "TYPE INPUT CHECK HEADER",
        "TYPE 2 COL ASSESSOR COMMENTS",
        "TYPE 2 COL OVERALL COMMENTS",
        "TYPE INPUT ASSESSOR COMMENTS",
        "TYPE INPUT EMPLOYEE COMMENTS",};

    /*      public static final int TYPE_DATA_STRING            = 0;
     public static final int TYPE_DATA_DATE              = 1;
        
     public static final  String[] fieldTypeDataName = {
     "INPUT TEXT",
     "INPUT DATE"
     }; */
    public PstAssessmentFormItem() {
    }

    public PstAssessmentFormItem(int i) throws DBException {
        super(new PstAssessmentFormItem());
    }

    public PstAssessmentFormItem(String sOid) throws DBException {
        super(new PstAssessmentFormItem(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstAssessmentFormItem(long lOid) throws DBException {
        super(new PstAssessmentFormItem(0));
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
        return TBL_HR_ASS_FORM_ITEM;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstAssessmentFormItem().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        AssessmentFormItem assessmentFormItem = fetchExc(ent.getOID());
        ent = (Entity) assessmentFormItem;
        return assessmentFormItem.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((AssessmentFormItem) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((AssessmentFormItem) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static AssessmentFormItem fetchExc(long oid) throws DBException {
        try {
            AssessmentFormItem assessmentFormItem = new AssessmentFormItem();
            PstAssessmentFormItem pstAssessmentFormItem = new PstAssessmentFormItem(oid);
            assessmentFormItem.setOID(oid);

            assessmentFormItem.setAssFormSection(pstAssessmentFormItem.getlong(FLD_ASS_FORM_SECTION_ID));
            assessmentFormItem.setTitle(pstAssessmentFormItem.getString(FLD_TITLE));
            assessmentFormItem.setTitle_L2(pstAssessmentFormItem.getString(FLD_TITLE_L2));
            assessmentFormItem.setItemPoin1(pstAssessmentFormItem.getString(FLD_ITEM_POIN_1));
            assessmentFormItem.setItemPoin2(pstAssessmentFormItem.getString(FLD_ITEM_POIN_2));
            assessmentFormItem.setItemPoin3(pstAssessmentFormItem.getString(FLD_ITEM_POIN_3));
            assessmentFormItem.setItemPoin4(pstAssessmentFormItem.getString(FLD_ITEM_POIN_4));
            assessmentFormItem.setItemPoin5(pstAssessmentFormItem.getString(FLD_ITEM_POIN_5));
            assessmentFormItem.setItemPoin6(pstAssessmentFormItem.getString(FLD_ITEM_POIN_6));
            assessmentFormItem.setType(pstAssessmentFormItem.getInt(FLD_TYPE));
            assessmentFormItem.setOrderNumber(pstAssessmentFormItem.getInt(FLD_ORDER_NUMBER));
            assessmentFormItem.setNumber(pstAssessmentFormItem.getInt(FLD_NUMBER));
            assessmentFormItem.setPage(pstAssessmentFormItem.getInt(FLD_PAGE));
            assessmentFormItem.setHeight(pstAssessmentFormItem.getInt(FLD_HEIGHT));
            assessmentFormItem.setKpiListId(pstAssessmentFormItem.getLong(FLD_KPI_LIST_ID));
            assessmentFormItem.setWeightPoint(pstAssessmentFormItem.getfloat(FLD_WEIGHT_POINT));
            assessmentFormItem.setKpiTarget(pstAssessmentFormItem.getfloat(FLD_KPI_TARGET));
            assessmentFormItem.setKpiUnit(pstAssessmentFormItem.getString(FLD_KPI_UNIT));
            assessmentFormItem.setKpiNote(pstAssessmentFormItem.getString(FLD_KPI_NOTE));

            return assessmentFormItem;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAssessmentFormItem(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(AssessmentFormItem assessmentFormItem) throws DBException {
        try {
            PstAssessmentFormItem pstAssessmentFormItem = new PstAssessmentFormItem(0);

            pstAssessmentFormItem.setLong(FLD_ASS_FORM_SECTION_ID, assessmentFormItem.getAssFormSection());
            pstAssessmentFormItem.setString(FLD_TITLE, assessmentFormItem.getTitle());
            pstAssessmentFormItem.setString(FLD_TITLE_L2, assessmentFormItem.getTitle_L2());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_1, assessmentFormItem.getItemPoin1());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_2, assessmentFormItem.getItemPoin2());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_3, assessmentFormItem.getItemPoin3());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_4, assessmentFormItem.getItemPoin4());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_5, assessmentFormItem.getItemPoin5());
            pstAssessmentFormItem.setString(FLD_ITEM_POIN_6, assessmentFormItem.getItemPoin6());
            pstAssessmentFormItem.setInt(FLD_TYPE, assessmentFormItem.getType());
            pstAssessmentFormItem.setInt(FLD_ORDER_NUMBER, assessmentFormItem.getOrderNumber());
            pstAssessmentFormItem.setInt(FLD_NUMBER, assessmentFormItem.getNumber());
            pstAssessmentFormItem.setInt(FLD_PAGE, assessmentFormItem.getPage());
            pstAssessmentFormItem.setInt(FLD_HEIGHT, assessmentFormItem.getHeight());
            pstAssessmentFormItem.setLong(FLD_KPI_LIST_ID, assessmentFormItem.getKpiListId());
            pstAssessmentFormItem.setFloat(FLD_WEIGHT_POINT, assessmentFormItem.getWeightPoint());
            pstAssessmentFormItem.setFloat(FLD_KPI_TARGET, assessmentFormItem.getKpiTarget());
            pstAssessmentFormItem.setString(FLD_KPI_UNIT, assessmentFormItem.getKpiUnit());
            pstAssessmentFormItem.setString(FLD_KPI_NOTE, assessmentFormItem.getKpiNote());
            pstAssessmentFormItem.insert();
            assessmentFormItem.setOID(pstAssessmentFormItem.getlong(FLD_ASS_FORM_ITEM_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAssessmentFormItem(0), DBException.UNKNOWN);
        }
        return assessmentFormItem.getOID();
    }

    public static long updateExc(AssessmentFormItem assessmentFormItem) throws DBException {
        try {
            if (assessmentFormItem.getOID() != 0) {
                PstAssessmentFormItem pstAssessmentFormItem = new PstAssessmentFormItem(assessmentFormItem.getOID());

                pstAssessmentFormItem.setLong(FLD_ASS_FORM_SECTION_ID, assessmentFormItem.getAssFormSection());
                pstAssessmentFormItem.setString(FLD_TITLE, assessmentFormItem.getTitle());
                pstAssessmentFormItem.setString(FLD_TITLE_L2, assessmentFormItem.getTitle_L2());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_1, assessmentFormItem.getItemPoin1());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_2, assessmentFormItem.getItemPoin2());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_3, assessmentFormItem.getItemPoin3());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_4, assessmentFormItem.getItemPoin4());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_5, assessmentFormItem.getItemPoin5());
                pstAssessmentFormItem.setString(FLD_ITEM_POIN_6, assessmentFormItem.getItemPoin6());
                pstAssessmentFormItem.setInt(FLD_TYPE, assessmentFormItem.getType());
                pstAssessmentFormItem.setInt(FLD_ORDER_NUMBER, assessmentFormItem.getOrderNumber());
                pstAssessmentFormItem.setInt(FLD_NUMBER, assessmentFormItem.getNumber());
                pstAssessmentFormItem.setInt(FLD_PAGE, assessmentFormItem.getPage());
                pstAssessmentFormItem.setInt(FLD_HEIGHT, assessmentFormItem.getHeight());
                pstAssessmentFormItem.setLong(FLD_KPI_LIST_ID, assessmentFormItem.getKpiListId());
                pstAssessmentFormItem.setFloat(FLD_WEIGHT_POINT, assessmentFormItem.getWeightPoint());
                pstAssessmentFormItem.setFloat(FLD_KPI_TARGET, assessmentFormItem.getKpiTarget());
                pstAssessmentFormItem.setString(FLD_KPI_UNIT, assessmentFormItem.getKpiUnit());
                pstAssessmentFormItem.setString(FLD_KPI_NOTE, assessmentFormItem.getKpiNote());
                pstAssessmentFormItem.update();
                return assessmentFormItem.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAssessmentFormItem(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstAssessmentFormItem pstAssessmentFormItem = new PstAssessmentFormItem(oid);
            pstAssessmentFormItem.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAssessmentFormItem(0), DBException.UNKNOWN);
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
            String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_ITEM;
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
                AssessmentFormItem assessmentFormItem = new AssessmentFormItem();
                resultToObject(rs, assessmentFormItem);
                lists.add(assessmentFormItem);
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

    public static void resultToObject(ResultSet rs, AssessmentFormItem assessmentFormItem) {
        try {
            assessmentFormItem.setOID(rs.getLong(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_ITEM_ID]));
            assessmentFormItem.setAssFormSection(rs.getLong(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]));
            assessmentFormItem.setTitle(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TITLE]));
            assessmentFormItem.setTitle_L2(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TITLE_L2]));
            assessmentFormItem.setItemPoin1(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_1]));
            assessmentFormItem.setItemPoin2(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_2]));
            assessmentFormItem.setItemPoin3(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_3]));
            assessmentFormItem.setItemPoin4(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_4]));
            assessmentFormItem.setItemPoin5(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_5]));
            assessmentFormItem.setItemPoin6(rs.getString(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ITEM_POIN_6]));
            assessmentFormItem.setType(rs.getInt(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]));
            assessmentFormItem.setOrderNumber(rs.getInt(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]));
            assessmentFormItem.setNumber(rs.getInt(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_NUMBER]));
            assessmentFormItem.setPage(rs.getInt(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]));
            assessmentFormItem.setHeight(rs.getInt(PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_HEIGHT]));
            assessmentFormItem.setKpiListId(rs.getLong(fieldNames[FLD_KPI_LIST_ID]));
            assessmentFormItem.setWeightPoint(rs.getFloat(fieldNames[FLD_WEIGHT_POINT]));
            assessmentFormItem.setKpiTarget(rs.getFloat(fieldNames[FLD_KPI_TARGET]));
            assessmentFormItem.setKpiUnit(rs.getString(fieldNames[FLD_KPI_UNIT]));
            assessmentFormItem.setKpiNote(rs.getString(fieldNames[FLD_KPI_NOTE]));
        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long assessmentFormItemId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_ITEM + " WHERE "
                    + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_ITEM_ID]
                    + " = " + assessmentFormItemId;

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
            String sql = "SELECT COUNT(" + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_ITEM_ID]
                    + ") FROM " + TBL_HR_ASS_FORM_ITEM;
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
                    AssessmentFormItem assessmentFormItem = (AssessmentFormItem) list.get(ls);
                    if (oid == assessmentFormItem.getOID()) {
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
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                    }
                }
            }
        }

        return cmd;
    }

    public static Vector listItem(long mainOid, int page) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT I.* FROM " + TBL_HR_ASS_FORM_ITEM
                    + " AS I INNER JOIN " + PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    + " AS S WHERE S." + PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    + " = " + mainOid
                    + " AND I." + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    + " = " + page
                    + " ORDER BY I." + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    + "," + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER];
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AssessmentFormItem assessmentFormItem = new AssessmentFormItem();
                resultToObject(rs, assessmentFormItem);
                lists.add(assessmentFormItem);
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

    //Mencari item terbanyak pada suatu main dejenis 12 (2 Col Comments Assessor)
    public static Vector getAssessorCommentTitle() {
        Vector vTitle = new Vector(1, 1);
        Vector vAssMain = new Vector(1, 1);
        vAssMain = PstAssessmentFormMain.list(0, 0, "", "");
        int maxPrev = 0;
        for (int i = 0; i < vAssMain.size(); i++) {
            DBResultSet dbrs = null;
            try {
                String query = "SELECT ITEM.* FROM "
                        + PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM + " AS ITEM "
                        + " INNER JOIN " + PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION + " AS SECTION "
                        + " ON ITEM." + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                        + " = SECTION." + PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                        + " INNER JOIN " + PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN + " AS MAIN "
                        + " ON SECTION." + PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                        + " = MAIN." + PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                        + " WHERE ITEM." + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]
                        + " = " + PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM;
                Vector vTemp = new Vector(1, 1);
                dbrs = DBHandler.execQueryResult(query);
                ResultSet rs = dbrs.getResultSet();
                int countSec = 0;
                while (rs.next()) {
                    countSec += 1;
                    AssessmentFormItem assessmentFormItem = new AssessmentFormItem();
                    resultToObject(rs, assessmentFormItem);
                    vTemp.add(assessmentFormItem);
                }
                if (countSec > maxPrev) {
                    maxPrev = countSec;
                    vTitle = vTemp;
                }
                rs.close();
            } catch (Exception e) {
                return new Vector(1, 1);
            } finally {
                DBResultSet.close(dbrs);
            }
        }
        return vTitle;
    }
}
