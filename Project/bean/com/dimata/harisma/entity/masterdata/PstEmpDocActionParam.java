///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dimata.harisma.entity.masterdata;
//
//import com.dimata.qdep.db.DBException;
//import com.dimata.qdep.db.DBHandler;
//import com.dimata.qdep.db.DBResultSet;
//import com.dimata.qdep.db.I_DBInterface;
//import com.dimata.qdep.db.I_DBType;
//import com.dimata.qdep.entity.Entity;
//import com.dimata.qdep.entity.I_PersintentExc;
//import com.dimata.util.lang.I_Language;
//import java.sql.ResultSet;
//import java.util.Hashtable;
//import java.util.Vector;
//
///**
// *
// * @author GUSWIK
// */
//public class PstEmpDocActionParam extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
//
//   public static final String TBL_HR_EMP_DOC_ACTION_PARAM = "hr_emp_doc_action_param";
//   public static final int FLD_EMP_DOC_ACTION_PARAM_ID = 0;
//   public static final int FLD_ACTION_NAME = 1;
//   public static final int FLD_ACTION_TITLE = 2;
//   public static final int FLD_ACTION_PARAMETER = 3;
//   public static final int FLD_DOC_ACTION_ID = 4;
//   
//    public static final String[] fieldNames = {
//      "DOC_ACTION_PARAM_ID",
//      "ACTION_PARAMETER",
//      "OBJ_NAME",
//      "OBJ_ATTRIBUTE",
//      "DOC_ACTION_ID"
//    };
//    public static final int[] fieldTypes = {
//      TYPE_LONG + TYPE_PK + TYPE_ID,
//      TYPE_STRING,
//      TYPE_STRING,
//      TYPE_STRING,
//      TYPE_LONG
//    };
//
//   public PstDocMasterActionParam() {
//   }
//
//    public PstDocMasterActionParam(int i) throws DBException {
//        super(new PstDocMasterActionParam());
//    }
//
//    public PstDocMasterActionParam(String sOid) throws DBException {
//        super(new PstDocMasterActionParam(0));
//        if (!locate(sOid)) {
//            throw new DBException(this, DBException.RECORD_NOT_FOUND);
//        } else {
//            return;
//        }
//    }
//
//    public PstDocMasterActionParam(long lOid) throws DBException {
//        super(new PstDocMasterActionParam(0));
//        String sOid = "0";
//        try {
//            sOid = String.valueOf(lOid);
//        } catch (Exception e) {
//            throw new DBException(this, DBException.RECORD_NOT_FOUND);
//        }
//        if (!locate(sOid)) {
//            throw new DBException(this, DBException.RECORD_NOT_FOUND);
//        } else {
//            return;
//        }
//    }
//
//    public int getFieldSize() {
//        return fieldNames.length;
//    }
//
//    public String getTableName() {
//        return TBL_HR_EMP_DOC_ACTION_PARAM;
//    }
//
//    public String[] getFieldNames() {
//        return fieldNames;
//    }
//
//    public int[] getFieldTypes() {
//        return fieldTypes;
//    }
//
//    public String getPersistentName() {
//        return new PstDocMasterActionParam().getClass().getName();
//    }
//
//    public long fetchExc(Entity ent) throws Exception {
//        DocMasterActionParam docActionParam = fetchExc(ent.getOID());
//        ent = (Entity) docActionParam;
//        return docActionParam.getOID();
//    }
//
//    public long insertExc(Entity ent) throws Exception {
//        return insertExc((DocMasterActionParam) ent);
//    }
//
//    public long updateExc(Entity ent) throws Exception {
//        return updateExc((DocMasterActionParam) ent);
//    }
//
//    public long deleteExc(Entity ent) throws Exception {
//        if (ent == null) {
//            throw new DBException(this, DBException.RECORD_NOT_FOUND);
//        }
//        return deleteExc(ent.getOID());
//    }
//
//    public static DocMasterActionParam fetchExc(long oid) throws DBException {
//        try {
//            DocMasterActionParam docActionParam = new DocMasterActionParam();
//            PstDocMasterActionParam pstDocMasterActionParam = new PstDocMasterActionParam(oid);
//            docActionParam.setOID(oid);
//            
//            docActionParam.setDocActionParamId(pstDocMasterActionParam.getLong(FLD_DOC_ACTION_PARAM_ID));         
//            docActionParam.setActionParameter(pstDocMasterActionParam.getString(FLD_ACTION_PARAMETER));
//            docActionParam.setObjectName(pstDocMasterActionParam.getString(FLD_OBJECT_NAME));
//            docActionParam.setObjectAtribut(pstDocMasterActionParam.getString(FLD_OBJECT_ATTRIBUTE));
//            docActionParam.setDocActionId(pstDocMasterActionParam.getLong(FLD_DOC_ACTION_ID));
//            
//            return docActionParam;
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstDocMasterActionParam(0), DBException.UNKNOWN);
//        }
//    }
//
//    public static long insertExc(DocMasterActionParam docActionParam) throws DBException {
//        try {
//            PstDocMasterActionParam pstDocMasterActionParam = new PstDocMasterActionParam(0);
//
//           // pstDocMasterActionParam.setLong(FLD_DOC_ACTION_PARAM_ID, docActionParam.getDocActionParamId());
//            pstDocMasterActionParam.setString(FLD_ACTION_PARAMETER, docActionParam.getActionParameter());
//            pstDocMasterActionParam.setString(FLD_OBJECT_NAME, docActionParam.getObjectName());
//            pstDocMasterActionParam.setString(FLD_OBJECT_ATTRIBUTE, docActionParam.getObjectAtribut());
//            pstDocMasterActionParam.setLong(FLD_DOC_ACTION_ID, docActionParam.getDocActionId());
//            
//            pstDocMasterActionParam.insert();
//            docActionParam.setOID(pstDocMasterActionParam.getlong(FLD_DOC_ACTION_ID));
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstDocMasterActionParam(0), DBException.UNKNOWN);
//        }
//        return docActionParam.getOID();
//    }
//
//    public static long updateExc(DocMasterActionParam docActionParam) throws DBException {
//        try {
//            if (docActionParam.getOID() != 0) {
//                PstDocMasterActionParam pstDocMasterActionParam = new PstDocMasterActionParam(docActionParam.getOID());
//
//                //pstDocMasterActionParam.setLong(FLD_DOC_ACTION_PARAM_ID, docActionParam.getDocActionParamId());
//                pstDocMasterActionParam.setLong(FLD_DOC_ACTION_ID, docActionParam.getDocActionId());
//                pstDocMasterActionParam.setString(FLD_ACTION_PARAMETER, docActionParam.getActionParameter());
//                pstDocMasterActionParam.setString(FLD_OBJECT_NAME, docActionParam.getObjectName());
//                pstDocMasterActionParam.setString(FLD_OBJECT_ATTRIBUTE, docActionParam.getObjectAtribut());
//            
//                pstDocMasterActionParam.update();
//                return docActionParam.getOID();
//
//            }
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstDocMasterActionParam(0), DBException.UNKNOWN);
//        }
//        return 0;
//    }
//
//    public static long deleteExc(long oid) throws DBException {
//        try {
//            PstDocMasterActionParam pstDocMasterActionParam = new PstDocMasterActionParam(oid);
//            pstDocMasterActionParam.delete();
//        } catch (DBException dbe) {
//            throw dbe;
//        } catch (Exception e) {
//            throw new DBException(new PstDocMasterActionParam(0), DBException.UNKNOWN);
//        }
//        return oid;
//    }
//
//    public static Vector listAll() {
//        return list(0, 500, "", "");
//    }
//
//    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
//        Vector lists = new Vector();
//        DBResultSet dbrs = null;
//        try {
//            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_ACTION_PARAM;
//            if (whereClause != null && whereClause.length() > 0) {
//                sql = sql + " WHERE " + whereClause;
//            }
//            if (order != null && order.length() > 0) {
//                sql = sql + " ORDER BY " + order;
//            }
//            if (limitStart == 0 && recordToGet == 0) {
//                sql = sql + "";
//            } else {
//                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
//            }
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//                DocMasterActionParam docActionParam = new DocMasterActionParam();
//                resultToObject(rs, docActionParam);
//                lists.add(docActionParam);
//            }
//            rs.close();
//            return lists;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//        return new Vector();
//    }
//    
//    
//    public static Hashtable hList(int limitStart, int recordToGet, String whereClause, String order) {
//        Hashtable lists = new Hashtable();
//        DBResultSet dbrs = null;
//        try {
//            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_ACTION_PARAM;
//            if (whereClause != null && whereClause.length() > 0) {
//                sql = sql + " WHERE " + whereClause;
//            }
//            if (order != null && order.length() > 0) {
//                sql = sql + " ORDER BY " + order;
//            }
//            if (limitStart == 0 && recordToGet == 0) {
//                sql = sql + "";
//            } else {
//                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
//            }
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//                DocMasterActionParam docActionParam = new DocMasterActionParam();
//                resultToObject(rs, docActionParam);
//                lists.put(docActionParam.getActionParameter(),docActionParam);
//            }
//            rs.close();
//            return lists;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//        return new Hashtable();
//    }
//    
//    
//      public static void resultToObject(ResultSet rs, DocMasterActionParam docActionParam) {
//        try {
//  
//               docActionParam.setOID(rs.getLong(PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_PARAM_ID]));
//               docActionParam.setDocActionId(rs.getLong(PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_ID]));
//               docActionParam.setActionParameter(rs.getString(PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_ACTION_PARAMETER]));
//               docActionParam.setObjectAtribut(rs.getString(PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_OBJECT_ATTRIBUTE]));
//               docActionParam.setObjectName(rs.getString(PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_OBJECT_NAME]));
//               
//        } catch (Exception e) {
//        }
//    }
//      
//      
//    public static long deletewhereActionId( long actionId) {
//        DBResultSet dbrs = null;
//        long resulthasil =0;
//        try {
//            String sql = "DELETE  FROM " + TBL_HR_EMP_DOC_ACTION_PARAM + " WHERE "
//                    + PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_ID] + " = " + actionId ;
//            
//            DBHandler.execSqlInsert(sql);
//        } catch (Exception e) {
//            System.out.println("err : " + e.toString());
//            
//        } finally {
//            DBResultSet.close(dbrs);
//            return resulthasil;
//        }
//    }
//    
//    public static boolean checkOID(long docActionParamId) {
//        DBResultSet dbrs = null;
//        boolean result = false;
//        try {
//            String sql = "SELECT * FROM " + TBL_HR_EMP_DOC_ACTION_PARAM + " WHERE "
//                    + PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_PARAM_ID] + " = " + docActionParamId;
//
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//
//            while (rs.next()) {
//                result = true;
//            }
//            rs.close();
//        } catch (Exception e) {
//            System.out.println("err : " + e.toString());
//        } finally {
//            DBResultSet.close(dbrs);
//            return result;
//        }
//    }
//
//    public static int getCount(String whereClause) {
//        DBResultSet dbrs = null;
//        try {
//            String sql = "SELECT COUNT(" + PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_PARAM_ID] + ") FROM " + TBL_HR_EMP_DOC_ACTION_PARAM;
//            if (whereClause != null && whereClause.length() > 0) {
//                sql = sql + " WHERE " + whereClause;
//            }
//
//            dbrs = DBHandler.execQueryResult(sql);
//            ResultSet rs = dbrs.getResultSet();
//
//            int count = 0;
//            while (rs.next()) {
//                count = rs.getInt(1);
//            }
//
//            rs.close();
//            return count;
//        } catch (Exception e) {
//            return 0;
//        } finally {
//            DBResultSet.close(dbrs);
//        }
//    }
//
//
//    /* This method used to find current data */
//    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
//        String order = "";
//        int size = getCount(whereClause);
//        int start = 0;
//        boolean found = false;
//        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
//            Vector list = list(i, recordToGet, whereClause, order);
//            start = i;
//            if (list.size() > 0) {
//                for (int ls = 0; ls < list.size(); ls++) {
//                    DocMasterActionParam docActionParam = (DocMasterActionParam) list.get(ls);
//                    if (oid == docActionParam.getOID()) {
//                        found = true;
//                    }
//                }
//            }
//        }
//        if ((start >= size) && (size > 0)) {
//            start = start - recordToGet;
//        }
//
//        return start;
//    }
//
//  
//}
