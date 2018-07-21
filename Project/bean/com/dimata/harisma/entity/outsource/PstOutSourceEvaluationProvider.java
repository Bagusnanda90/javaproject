/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.harisma.entity.masterdata.PstDivision;
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Hashtable;
import java.util.Vector;

public class PstOutSourceEvaluationProvider extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_OUT_SOURCE_EVAL_PROVIDER = "hr_outsrc_eval_provider";
   public static final int FLD_OUT_SOURCE_EVAL_PROVIDER_ID = 0;
   public static final int FLD_OUT_SOURCE_EVAL_ID = 1;
   public static final int FLD_POSITION_ID = 2;
   public static final int FLD_PROVIDER_ID = 3;
   public static final int FLD_NUMBER_OF_PERSON = 4;
   public static final int FLD_EVAL_POINT = 5;
   public static final int FLD_JUSTIFICATION = 6;
   public static final int FLD_SUGGESTION = 7;

   public static String[] fieldNames = {
      "OUTSRC_EVAL_PROVIDER_ID",
      "OUTSOURCE_EVAL_ID",
      "POSITION_ID",
      "PROVIDER_ID",
      "NUMBER_OF_PERSON",
      "EVAL_POINT",
      "JUSTIFICATION",
      "SUGGESTION"
   };

   public static int[] fieldTypes = {
      TYPE_LONG+TYPE_PK+TYPE_ID,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_INT,
      TYPE_INT,
      TYPE_STRING,
      TYPE_STRING
   };
    public PstOutSourceEvaluationProvider() {
    }

    public PstOutSourceEvaluationProvider(int i) throws DBException {
        super(new PstOutSourceEvaluationProvider());
    }

    public PstOutSourceEvaluationProvider(String sOid) throws DBException {
        super(new PstOutSourceEvaluationProvider(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutSourceEvaluationProvider(long lOid) throws DBException {
        super(new PstOutSourceEvaluationProvider(0));
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
        return TBL_OUT_SOURCE_EVAL_PROVIDER;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutSourceEvaluationProvider().getClass().getName();
    }

    public static OutSourceEvaluationProvider fetchExc(long oid) throws DBException {
        try {
         OutSourceEvaluationProvider entOutSourceEvalProvider = new OutSourceEvaluationProvider();
         PstOutSourceEvaluationProvider pstOutSourceEvalProvider = new PstOutSourceEvaluationProvider(oid);
         entOutSourceEvalProvider.setOID(oid);
         entOutSourceEvalProvider.setOutsourceEvalId(pstOutSourceEvalProvider.getLong(FLD_OUT_SOURCE_EVAL_ID));
         entOutSourceEvalProvider.setPositionId(pstOutSourceEvalProvider.getLong(FLD_POSITION_ID));
         entOutSourceEvalProvider.setProviderId(pstOutSourceEvalProvider.getLong(FLD_PROVIDER_ID));
         entOutSourceEvalProvider.setNumberOfPerson(pstOutSourceEvalProvider.getInt(FLD_NUMBER_OF_PERSON));
         entOutSourceEvalProvider.setEvalPoint(pstOutSourceEvalProvider.getInt(FLD_EVAL_POINT));
         entOutSourceEvalProvider.setJustification(pstOutSourceEvalProvider.getString(FLD_JUSTIFICATION));
         entOutSourceEvalProvider.setSuggestion(pstOutSourceEvalProvider.getString(FLD_SUGGESTION));
         return entOutSourceEvalProvider;
            
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluationProvider(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutSourceEvaluationProvider entOutSourceEvaluationProvider = fetchExc(entity.getOID());
        entity = (Entity) entOutSourceEvaluationProvider;
        return entOutSourceEvaluationProvider.getOID();
    }

    public static synchronized long updateExc(OutSourceEvaluationProvider entOutSourceEvaluationProvider) throws DBException {
        try {
            if (entOutSourceEvaluationProvider.getOID() != 0) {
                PstOutSourceEvaluationProvider pstOutSourceEvalProvider = new PstOutSourceEvaluationProvider(entOutSourceEvaluationProvider.getOID());
            pstOutSourceEvalProvider.setLong(FLD_OUT_SOURCE_EVAL_ID, entOutSourceEvaluationProvider.getOutsourceEvalId());
            pstOutSourceEvalProvider.setLong(FLD_POSITION_ID, entOutSourceEvaluationProvider.getPositionId());
            pstOutSourceEvalProvider.setLong(FLD_PROVIDER_ID, entOutSourceEvaluationProvider.getProviderId());
            pstOutSourceEvalProvider.setInt(FLD_NUMBER_OF_PERSON, entOutSourceEvaluationProvider.getNumberOfPerson());
            pstOutSourceEvalProvider.setInt(FLD_EVAL_POINT, entOutSourceEvaluationProvider.getEvalPoint());
            pstOutSourceEvalProvider.setString(FLD_JUSTIFICATION, entOutSourceEvaluationProvider.getJustification());
            pstOutSourceEvalProvider.setString(FLD_SUGGESTION, entOutSourceEvaluationProvider.getSuggestion());
            pstOutSourceEvalProvider.update();
                pstOutSourceEvalProvider.update();
            return entOutSourceEvaluationProvider.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluationProvider(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutSourceEvaluationProvider) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutSourceEvaluationProvider pstOutSourceEvaluationProvider = new PstOutSourceEvaluationProvider(oid);
            pstOutSourceEvaluationProvider.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluationProvider(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutSourceEvaluationProvider entOutSourceEvalProvider) throws DBException {
        try {
            PstOutSourceEvaluationProvider pstOutSourceEvalProvider = new PstOutSourceEvaluationProvider(0);
            pstOutSourceEvalProvider.setLong(FLD_OUT_SOURCE_EVAL_ID, entOutSourceEvalProvider.getOutsourceEvalId());
            pstOutSourceEvalProvider.setLong(FLD_POSITION_ID, entOutSourceEvalProvider.getPositionId());
            pstOutSourceEvalProvider.setLong(FLD_PROVIDER_ID, entOutSourceEvalProvider.getProviderId());
            pstOutSourceEvalProvider.setInt(FLD_NUMBER_OF_PERSON, entOutSourceEvalProvider.getNumberOfPerson());
            pstOutSourceEvalProvider.setInt(FLD_EVAL_POINT, entOutSourceEvalProvider.getEvalPoint());
            pstOutSourceEvalProvider.setString(FLD_JUSTIFICATION, entOutSourceEvalProvider.getJustification());
            pstOutSourceEvalProvider.setString(FLD_SUGGESTION, entOutSourceEvalProvider.getSuggestion());
            pstOutSourceEvalProvider.insert();
            entOutSourceEvalProvider.setOID(pstOutSourceEvalProvider.getlong(FLD_OUT_SOURCE_EVAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutSourceEvaluationProvider(0), DBException.UNKNOWN);
        }
        return entOutSourceEvalProvider.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutSourceEvaluationProvider) entity);
    }

    public static void resultToObject(ResultSet rs, OutSourceEvaluationProvider entOutSourceEvalProvider) {
        try {
            entOutSourceEvalProvider.setOID(rs.getLong(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_PROVIDER_ID]));
            entOutSourceEvalProvider.setOutsourceEvalId(rs.getLong(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_ID]));
            entOutSourceEvalProvider.setPositionId(rs.getLong(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_POSITION_ID]));
            entOutSourceEvalProvider.setProviderId(rs.getLong(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_PROVIDER_ID]));
            entOutSourceEvalProvider.setNumberOfPerson(rs.getInt(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_NUMBER_OF_PERSON]));
            entOutSourceEvalProvider.setEvalPoint(rs.getInt(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_EVAL_POINT]));
            entOutSourceEvalProvider.setJustification(rs.getString(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_JUSTIFICATION]));
            entOutSourceEvalProvider.setSuggestion(rs.getString(PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_SUGGESTION]));   
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
            String sql = "SELECT * FROM " + TBL_OUT_SOURCE_EVAL_PROVIDER;
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
                OutSourceEvaluationProvider entOutSourceEvaluationProvider = new OutSourceEvaluationProvider();
                resultToObject(rs, entOutSourceEvaluationProvider);
                lists.add(entOutSourceEvaluationProvider);
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
    
   public static Hashtable listOutSourceEvalProviderPerDiv(int limitStart, int recordToGet, String whereClause, String order,long divisiId, SrcObject srcObject) {
        Hashtable lists = new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_OUT_SOURCE_EVAL_PROVIDER + " as osep"+
            " INNER JOIN "+ PstOutSourceEvaluation.TBL_OUTSOURCEEVALUATION+" AS ose ON (osep."+ PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_ID]+" = ose."+ PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_OUTSOURCE_EVAL_ID] + " AND ose."+ PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DIVISION_ID] +"= "+divisiId+" )";
            
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (srcObject != null ) {
                if (srcObject.getPayPeriodId().length() > 0 ){
                    String periodeIn =  srcObject.getPayPeriodId().substring(0,srcObject.getPayPeriodId().length() - 1);
                    sql = sql + " AND ose." + PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_PERIOD_ID] + " IN ( " + periodeIn + " ) " ;
                }
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
                OutSourceEvaluationProvider entOutSourceEvaluationProvider = new OutSourceEvaluationProvider();
                resultToObject(rs, entOutSourceEvaluationProvider);
                lists.put(""+entOutSourceEvaluationProvider.getPositionId(),entOutSourceEvaluationProvider);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Hashtable();
    }   
    
    
 
    public static boolean checkOID(long entOutSourceEvaluationProviderId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_OUT_SOURCE_EVAL_PROVIDER + " WHERE "
                    + PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_PROVIDER_ID] + " = " + entOutSourceEvaluationProviderId;
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
            String sql = "SELECT COUNT(" + PstOutSourceEvaluationProvider.fieldNames[PstOutSourceEvaluationProvider.FLD_OUT_SOURCE_EVAL_PROVIDER_ID] + ") FROM " + TBL_OUT_SOURCE_EVAL_PROVIDER;
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
                    OutSourceEvaluationProvider entOutSourceEvaluationProvider = (OutSourceEvaluationProvider) list.get(ls);
                    if (oid == entOutSourceEvaluationProvider.getOID()) {
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
}
