/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.harisma.form.outsource.*;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class PstOutsrcCostProvDetail extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_OUTSRC_COST_PROV_DETAIL = "hr_outsrc_cost_prov_detail";
    public static final int FLD_OUTSRC_COST_PROV_DETLD_ID = 0;
    public static final int FLD_OUTSRC_COST_PROVIDER_ID = 1;
    public static final int FLD_OUTSRC_COST_ID = 2;
    public static final int FLD_COST_VAL = 3;
    public static final int FLD_NOTE = 4;
    public static String[] fieldNames = {
        "OUTSRC_COST_PROV_DETLD_ID",
        "OUTSRC_COST_PROVIDER_ID",
        "OUTSRC_COST_ID",
        "COST_VAL",
        "NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING
    };

    public PstOutsrcCostProvDetail() {
    }

    public PstOutsrcCostProvDetail(int i) throws DBException {
        super(new PstOutsrcCostProvDetail());
    }

    public PstOutsrcCostProvDetail(String sOid) throws DBException {
        super(new PstOutsrcCostProvDetail(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstOutsrcCostProvDetail(long lOid) throws DBException {
        super(new PstOutsrcCostProvDetail(0));
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
        return TBL_HR_OUTSRC_COST_PROV_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstOutsrcCostProvDetail().getClass().getName();
    }

    public static OutsrcCostProvDetail fetchExc(long oid) throws DBException {
        try {
            OutsrcCostProvDetail entOutsrcCostProvDetail = new OutsrcCostProvDetail();
            PstOutsrcCostProvDetail pstOutsrcCostProvDetail = new PstOutsrcCostProvDetail(oid);
            entOutsrcCostProvDetail.setOID(oid);
            entOutsrcCostProvDetail.setOutsrcCostProviderId(pstOutsrcCostProvDetail.getLong(FLD_OUTSRC_COST_PROVIDER_ID));
            entOutsrcCostProvDetail.setOutsrcCostId(pstOutsrcCostProvDetail.getLong(FLD_OUTSRC_COST_ID));
            entOutsrcCostProvDetail.setCostVal(pstOutsrcCostProvDetail.getdouble(FLD_COST_VAL));
            entOutsrcCostProvDetail.setNote(pstOutsrcCostProvDetail.getString(FLD_NOTE));
            return entOutsrcCostProvDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutsrcCostProvDetail(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        OutsrcCostProvDetail entOutsrcCostProvDetail = fetchExc(entity.getOID());
        entity = (Entity) entOutsrcCostProvDetail;
        return entOutsrcCostProvDetail.getOID();
    }

    public static synchronized long updateExc(OutsrcCostProvDetail entOutsrcCostProvDetail) throws DBException {
        try {
            if (entOutsrcCostProvDetail.getOID() != 0) {
                PstOutsrcCostProvDetail pstOutsrcCostProvDetail = new PstOutsrcCostProvDetail(entOutsrcCostProvDetail.getOID());
                pstOutsrcCostProvDetail.setLong(FLD_OUTSRC_COST_PROVIDER_ID, entOutsrcCostProvDetail.getOutsrcCostProviderId());
                pstOutsrcCostProvDetail.setLong(FLD_OUTSRC_COST_ID, entOutsrcCostProvDetail.getOutsrcCostId());
                pstOutsrcCostProvDetail.setFloat(FLD_COST_VAL, entOutsrcCostProvDetail.getCostVal());
                pstOutsrcCostProvDetail.setString(FLD_NOTE, entOutsrcCostProvDetail.getNote());
                pstOutsrcCostProvDetail.update();
                return entOutsrcCostProvDetail.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutsrcCostProvDetail(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((OutsrcCostProvDetail) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstOutsrcCostProvDetail pstOutsrcCostProvDetail = new PstOutsrcCostProvDetail(oid);
            pstOutsrcCostProvDetail.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutsrcCostProvDetail(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(OutsrcCostProvDetail entOutsrcCostProvDetail) throws DBException {
        try {
            PstOutsrcCostProvDetail pstOutsrcCostProvDetail = new PstOutsrcCostProvDetail(0);
            pstOutsrcCostProvDetail.setLong(FLD_OUTSRC_COST_PROVIDER_ID, entOutsrcCostProvDetail.getOutsrcCostProviderId());
            pstOutsrcCostProvDetail.setLong(FLD_OUTSRC_COST_ID, entOutsrcCostProvDetail.getOutsrcCostId());
            pstOutsrcCostProvDetail.setFloat(FLD_COST_VAL, entOutsrcCostProvDetail.getCostVal());
            pstOutsrcCostProvDetail.setString(FLD_NOTE, entOutsrcCostProvDetail.getNote());
            pstOutsrcCostProvDetail.insert();
            entOutsrcCostProvDetail.setOID(pstOutsrcCostProvDetail.getlong(FLD_OUTSRC_COST_PROV_DETLD_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstOutsrcCostProvDetail(0), DBException.UNKNOWN);
        }
        return entOutsrcCostProvDetail.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((OutsrcCostProvDetail) entity);
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_OUTSRC_COST_PROV_DETAIL;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if (limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            
           // System.out.println(":::::::::::::::::: "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                OutsrcCostProvDetail outsrcCostProvDetail = new OutsrcCostProvDetail();
                resultToObject(rs, outsrcCostProvDetail);
                lists.add(outsrcCostProvDetail);
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

    public static void resultToObject(ResultSet rs, OutsrcCostProvDetail entOutsrcCostProvDetail) {
        try {
            entOutsrcCostProvDetail.setOutsrcCostProviderId(rs.getLong(PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_PROVIDER_ID]));
            entOutsrcCostProvDetail.setOutsrcCostId(rs.getLong(PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_ID]));
            entOutsrcCostProvDetail.setCostVal(rs.getFloat(PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_COST_VAL]));
            entOutsrcCostProvDetail.setNote(rs.getString(PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_NOTE]));
        } catch (Exception e) {
        }
    }
    
    public static long getOid(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+ PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_PROV_DETLD_ID] + " FROM " + TBL_HR_OUTSRC_COST_PROV_DETAIL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            long oid = 0;
            while (rs.next()) {
                oid = rs.getLong(1);
            }
            rs.close();
            return oid;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static double getcostvalue(int limitStart, int recordToGet, String whereClause, String order,long divisiId, SrcObject srcObject, long position) {
        double value = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(oscpd."+PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_COST_VAL]+") FROM " + TBL_HR_OUTSRC_COST_PROV_DETAIL + " as oscpd"+
            " INNER JOIN "+ PstOutsrcCostProv.TBL_HR_OUTSRC_COST_PROV + " AS oscp ON (oscpd."+ PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_PROVIDER_ID]+" = oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_OUTSRC_COST_PROVIDER_ID] + " AND oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_POSITION_ID] +"= "+position+" )"+
            " INNER JOIN "+ PstOutSourceCost.TBL_OUTSOURCE_COST+" AS osc ON (osc."+ PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_OUTSOURCE_COST_ID]+" = oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_OUTSOURCE_COST_ID] + " AND osc." + PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_DIVISION_ID] + " = " + divisiId + "  ) ";
            
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (srcObject != null ) {
                if (srcObject.getPayPeriodId().length() > 0 ){
                    String periodeIn =  srcObject.getPayPeriodId().substring(0,srcObject.getPayPeriodId().length() - 1);
                    sql = sql + " AND osc." + PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_PERIOD_ID] + " IN ( " + periodeIn + " ) " ;
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
                value = rs.getDouble(1);
            }
            rs.close();
            return value;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }   
    
    public static double getcostvalueALL(int limitStart, int recordToGet, String whereClause, String order, SrcObject srcObject, long position) {
        double value = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT SUM(oscpd."+PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_COST_VAL]+") FROM " + TBL_HR_OUTSRC_COST_PROV_DETAIL + " as oscpd"+
            " INNER JOIN "+ PstOutsrcCostProv.TBL_HR_OUTSRC_COST_PROV + " AS oscp ON (oscpd."+ PstOutsrcCostProvDetail.fieldNames[PstOutsrcCostProvDetail.FLD_OUTSRC_COST_PROVIDER_ID]+" = oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_OUTSRC_COST_PROVIDER_ID] + " AND oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_POSITION_ID] +"= "+position+" )"+
            " INNER JOIN "+ PstOutSourceCost.TBL_OUTSOURCE_COST+" AS osc ON (osc."+ PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_OUTSOURCE_COST_ID]+" = oscp."+ PstOutsrcCostProv.fieldNames[PstOutsrcCostProv.FLD_OUTSOURCE_COST_ID] + " ) ";
            
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (srcObject != null ) {
                if (srcObject.getPayPeriodId().length() > 0 ){
                    String periodeIn =  srcObject.getPayPeriodId().substring(0,srcObject.getPayPeriodId().length() - 1);
                    sql = sql + " AND osc." + PstOutSourceCost.fieldNames[PstOutSourceCost.FLD_PERIOD_ID] + " IN ( " + periodeIn + " ) " ;
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
                value = rs.getDouble(1);
            }
            rs.close();
            return value;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }  
    
    /**
     * List dalam hastable cost yg di realisasi , keynya dinamik tergantung nilai parameter yang di berikan ke fungsi ini;
     * long divisionId, long positionId, long masterCostId, int docSatus
     * @param startDate
     * @param endDate
     * @param divisionId
     * @param positionId
     * @param masterCostId
     * @return 
     */
    public static Hashtable<String , OutSourceCostProvDetailAvrg> listCostDetailByDivPos(Date startDate, Date endDate, long divisionId, long positionId, long masterCostId, int docSatus ){
        Hashtable<String , OutSourceCostProvDetailAvrg> lists = new Hashtable<String , OutSourceCostProvDetailAvrg>();
        DBResultSet dbrs = null;
        try{
          if(startDate==null){
              startDate = new  Date(0, 0, 0);
          }
          if(endDate == null){
              endDate = new Date();
          }
          String sql = " SELECT  cm.`DIVISION_ID`, cp.`POSITION_ID`,  cd.`OUTSRC_COST_ID`, "
                  +" SUM(cp.NUMBER_OF_PERSON) AS PAX, SUM(cd.COST_VAL) AS COST FROM `hr_outsrc_cost_prov_detail` cd "
                  +" INNER JOIN `hr_outsrc_cost_prov` cp ON cp.`OUTSRC_COST_PROVIDER_ID` = cd.`OUTSRC_COST_PROVIDER_ID` "
                  +" INNER JOIN `hr_outsource_cost` cm ON  cm.OUTSOURCE_COST_ID = cp.OUTSOURCE_COST_ID "
                  +" INNER JOIN  `hr_period` hp ON cm.`PERIOD_ID` = hp.`PERIOD_ID` "
                  +" WHERE  " +   (masterCostId!=0 ? (" cd.`OUTSRC_COST_ID` = "+masterCostId+" AND") : ""  )
                  +" hp.`END_DATE` > \""+ Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00")+"\" AND  \""+ Formater.formatDate(endDate, "yyyy-MM-dd 00:00:00")+"\" > hp.`END_DATE` "
                  + ( divisionId!=0 ? (" AND cm.`DIVISION_ID`= "+divisionId+""):"") 
                  + ( positionId!=0 ? (" AND cp.`POSITION_ID`= "+positionId+""):"") 
                  + ( masterCostId!=0 ? (" AND cd.`OUTSRC_COST_ID`= "+masterCostId+""):"") 
                  + ( docSatus!=0 ? (" AND cm.`STATUS_DOC`= "+docSatus+""):"") 
                  +" GROUP BY  cm.`DIVISION_ID`, cp.`POSITION_ID`, cd.`OUTSRC_COST_ID` ";
          
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int keyMapTemp=1;
            while (rs.next()) {
                OutSourceCostProvDetailAvrg outsrcCostAvrg = new OutSourceCostProvDetailAvrg();
                outsrcCostAvrg.setDivisionId(rs.getLong("DIVISION_ID"));
                outsrcCostAvrg.setPositionId(rs.getLong("POSITION_ID"));
                outsrcCostAvrg.setMasterCostId(rs.getLong("OUTSRC_COST_ID"));
                outsrcCostAvrg.setPax(rs.getInt("PAX"));
                if(outsrcCostAvrg.getPax()!=0){
                   outsrcCostAvrg.setAverageCost(rs.getFloat("COST")/outsrcCostAvrg.getPax());
                } else{
                   outsrcCostAvrg.setAverageCost(rs.getFloat("COST"));  
                }
                String keyMap =""+( divisionId==0 ?(""+outsrcCostAvrg.getDivisionId()):"")+ (positionId==0?("_"+outsrcCostAvrg.getPositionId()):"")
                        +(masterCostId==0 ? ("_"+outsrcCostAvrg.getMasterCostId()) :"");
                if(keyMap.length()<1){
                    keyMap=""+ keyMapTemp;
                }else{
                    if(keyMap.startsWith("-")){
                        keyMap = keyMap.substring(1);
                    }
                }
                lists.put(keyMap,outsrcCostAvrg );
                keyMapTemp++;
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
}
