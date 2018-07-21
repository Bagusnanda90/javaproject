
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

/* package java */
import com.dimata.harisma.entity.employee.*;
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
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.masterdata.PstGroupRank;
import com.dimata.harisma.form.employee.appraisal.FrmAppraisalMain;

public class PstAssessmentFormMainDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_ASS_FORM_MAIN_DETAIL = "hr_ass_form_main_detail";//
    public static final int FLD_ASS_FORM_MAIN_ID = 0;
    public static final int FLD_GROUP_RANK_ID = 1;
    public static final String[] fieldNames = {
        "ASS_FORM_MAIN_ID",
        "GROUP_RANK_ID"
    };
    public static final int[] fieldTypes = {
        TYPE_PK + TYPE_FK + TYPE_LONG,
        TYPE_PK + TYPE_FK + TYPE_LONG
    };

    public PstAssessmentFormMainDetail() {
    }

    public PstAssessmentFormMainDetail(int i) throws DBException {
        super(new PstAssessmentFormMainDetail());
    }

    public PstAssessmentFormMainDetail(String sOid) throws DBException {
        super(new PstAssessmentFormMainDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstAssessmentFormMainDetail(long lOid) throws DBException {
        super(new PstAssessmentFormMainDetail(0));
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
        return TBL_HR_ASS_FORM_MAIN_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstAssessmentFormMainDetail().getClass().getName();
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
        return 0;
    }

    public static long insert(AssessmentFormMainDetail entObj) {
        try {
            PstAssessmentFormMainDetail pstObj = new PstAssessmentFormMainDetail(0);


            pstObj.setLong(FLD_ASS_FORM_MAIN_ID, entObj.getAssMainDetail());
            pstObj.setLong(FLD_GROUP_RANK_ID, entObj.getGroupRankId());

            pstObj.insert();
            return entObj.getAssMainDetail();
        } catch (DBException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static long deleteDetailAssFormMain(long oid) {
        PstAssessmentFormMainDetail pstObj = new PstAssessmentFormMainDetail();
        DBResultSet dbrs = null;
        try {
            String sql = "DELETE FROM " + pstObj.getTableName()
                    + " WHERE " + PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_ASS_FORM_MAIN_ID]
                    + " = '" + oid + "'";

            int status = DBHandler.execUpdate(sql);
            return oid;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }

        return 0;
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_MAIN_DETAIL;
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
                AssessmentFormMainDetail assessmentFormMainDetail = new AssessmentFormMainDetail();
                assessmentFormMainDetail.setAssMainDetail(rs.getLong(fieldNames[FLD_ASS_FORM_MAIN_ID]));
                assessmentFormMainDetail.setGroupRankId(rs.getLong(fieldNames[FLD_GROUP_RANK_ID]));
                lists.add(assessmentFormMainDetail);
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

    public static String sGroupNameRank(String whereClause, String order) {
        String sGroupRankName = "";
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT gr." + PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]
                    + " FROM " + PstAssessmentFormMainDetail.TBL_HR_ASS_FORM_MAIN_DETAIL + " AS afm "
                    + " INNER JOIN " + PstGroupRank.TBL_HR_GROUP_RANK + " AS gr ON gr." + PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + "=afm." + PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_GROUP_RANK_ID];
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                sGroupRankName = sGroupRankName + rs.getString("gr." + PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]) + ",";
            }
            rs.close();
            if (sGroupRankName != null && sGroupRankName.length() > 0) {
                sGroupRankName = sGroupRankName.substring(0, sGroupRankName.length() - 1);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return sGroupRankName;
        }

    }

    public static boolean setDetailGroupRankId(long assFormMainId, Vector vGroupRank) {

        // do delete
        if (PstAssessmentFormMainDetail.deleteDetailAssFormMain(assFormMainId) == 0) {
            return false;
        }

        if (vGroupRank == null || vGroupRank.size() == 0) {
            return true;
        }

        // than insert
        for (int i = 0; i < vGroupRank.size(); i++) {
            AssessmentFormMainDetail assessmentFormMainDetail = (AssessmentFormMainDetail) vGroupRank.get(i);
            if (PstAssessmentFormMainDetail.insert(assessmentFormMainDetail) == 0) {
                return false;
            }
        }
        return true;
    }

}
