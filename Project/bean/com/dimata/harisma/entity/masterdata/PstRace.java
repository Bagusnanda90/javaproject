
package com.dimata.harisma.entity.masterdata;

// import java
import java.io.*;
import java.sql.*;
import java.util.*;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class PstRace extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc {
    
    public static final String TBL_RACE       =   "hr_race";
    
    public static final int FLD_RACE_ID       =   0;
    public static final int FLD_RACE_NAME     =   1;
    
    public static String[] fieldNames = 
    {
        "RACE_ID",
        "RACE_NAME"
    };
    
    public static int[] fieldTypes = 
    {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING
    };
            
    
    public PstRace() {
    }
    
    public PstRace(int i) throws DBException {
        super(new PstRace());
    }
    
    public PstRace(String sOid) throws DBException {
        super(new PstRace(0));
        
        if(!locate(sOid)) 
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstRace(long lOid) throws DBException {
        super(new PstRace(0));
        
        String sOid = String.valueOf(lOid);
        
        if(!locate(sOid)) 
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
            

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_RACE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {   
        Race race = fetchExc(ent.getOID());
        ent = (Entity)race;
        return race.getOID();
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Race)ent);
    }

    public long deleteExc(Entity ent) throws Exception {
       if(ent == null) {
          throw new DBException(this, DBException.RECORD_NOT_FOUND);
       }
       
       return deleteExc(ent.getOID());
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Race)ent);
    }
    
    
    public static Race fetchExc(long oid) throws Exception {
        try {
            PstRace pstRace = new PstRace(oid);
            Race race = new Race();
            
            race.setOID(pstRace.getlong(FLD_RACE_ID));
            race.setRaceName(pstRace.getString(FLD_RACE_NAME));
            
            return race;
        }
        catch(DBException e) {
            throw e;
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static long insertExc(Race race) throws Exception {
        try {
            PstRace pstRace = new PstRace(0);
            
            pstRace.setString(FLD_RACE_NAME, race.getRaceName());            
            pstRace.insert();
            
            race.setOID(pstRace.getlong(FLD_RACE_ID));
            
            return race.getOID();
        }
        catch(DBException e) {
            throw e;
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static long updateExc(Race race) throws Exception {
        try {
            if(race.getOID() != 0) {
                PstRace pstRace = new PstRace(race.getOID());

                pstRace.setString(FLD_RACE_NAME, race.getRaceName());                
                pstRace.update();
                
                return race.getOID();
            }
            
            return 0;
        }
        catch(DBException e) {
            throw e;
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static long deleteExc(long oid) throws Exception {
        try {
            PstRace pstRace = new PstRace(oid);
            pstRace.delete();
            
            return oid;
        }
        catch(DBException e) {
            throw e;
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static Vector listAll(){ 
        return list(0, 500, "",""); 
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT * FROM " + TBL_RACE; 
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            if(limitStart == 0 && recordToGet == 0)
                sql = sql + "";
            else
                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
         
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while(rs.next()) {
                Race race = new Race();
                resultToObject(rs, race);
                lists.add(race);
            }
            rs.close();
            
            return lists;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            DBResultSet.close(dbrs);
        }
        
        return new Vector();
    }

    private static void resultToObject(ResultSet rs, Race race){
        try{        
           race.setOID(rs.getLong(PstRace.fieldNames[PstRace.FLD_RACE_ID]));
           race.setRaceName(rs.getString(PstRace.fieldNames[PstRace.FLD_RACE_NAME]));
        }
        catch(Exception e){ }
    }

    public static boolean checkOID(long raceId){
        DBResultSet dbrs = null;
        boolean result = false;
        
        try{
            String sql = "SELECT * FROM " + TBL_RACE + " WHERE " + 
                         PstRace.fieldNames[PstRace.FLD_RACE_ID] + " = " + raceId;
                    
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) { result = true; }
            rs.close();
        }
        catch(Exception e){
            System.out.println("err : "+e.toString());
        }
        finally{
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause){
        DBResultSet dbrs = null;
        
        try {
            String sql = "SELECT COUNT("+ PstRace.fieldNames[PstRace.FLD_RACE_ID] + ") FROM " + TBL_RACE;
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }

            rs.close();
            return count;
        }
        catch(Exception e) {
            return 0;
        }
        finally {
            DBResultSet.close(dbrs);
        }
    }

    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause){
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found =false;
        
        for(int i=0; (i < size) && !found ; i=i+recordToGet){
             Vector list = list(i,recordToGet, whereClause, order); 
             start = i;
             
             if(list.size()>0) {                 
                for(int ls=0;ls<list.size();ls++){ 
                   Race race = (Race)list.get(ls);
                   
                   if(oid == race.getOID())
                      found=true;
                }
            }
        }
        
        if((start >= size) && (size > 0))
            start = start - recordToGet;

        return start;
    }


    /*public static boolean checkMaster(long oid)
    {
    	if(PstEmployee.checkReligion(oid) || PstRecrApplication.checkReligion(oid))
            return true;
    	else
            return false;
    }*/
    
}
