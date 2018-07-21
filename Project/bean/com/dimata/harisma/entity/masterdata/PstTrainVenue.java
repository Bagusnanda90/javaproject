
package com.dimata.harisma.entity.masterdata; 

// import java
import java.sql.*;
import java.util.*;

// import dimata
import com.dimata.util.lang.*;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/**
 * 
 * @author bayu
 */

public class PstTrainVenue extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final String TBL_TRAIN_VENUE       =   "hr_training_venue";
    
        public static final int FLD_TRAIN_VENUE_ID       =   0;
        public static final int FLD_TRAIN_VENUE_NAME     =   1;
        public static final int FLD_TRAIN_VENUE_DESC     =   2;

        public static String[] fieldNames =
        {
            "VENUE_ID",
            "NAME",
            "DESCRIPTION"
        };
        
        public static int[] fieldTypes =
        {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_STRING,
            TYPE_STRING
        };


	public PstTrainVenue(){
	}

	public PstTrainVenue(int i) throws DBException { 
            super(new PstTrainVenue()); 
	}

	public PstTrainVenue(String sOid) throws DBException { 
            super(new PstTrainVenue(0));
            
            if (!locate(sOid)) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            } 
            else {
                return;
            } 
        }

	public PstTrainVenue(long lOid) throws DBException { 
            super(new PstTrainVenue(0));
            String sOid = "0";
            
            try {
                sOid = String.valueOf(lOid);
            } 
            catch (Exception e) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            }
            
            if (!locate(sOid)) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            } 
            else {
                return;
            } 
	} 

        
	public int getFieldSize(){ 
            return fieldNames.length; 
	}

	public String getTableName(){ 
            return TBL_TRAIN_VENUE;
	}

	public String[] getFieldNames(){ 
            return fieldNames; 
	}

	public int[] getFieldTypes(){ 
            return fieldTypes; 
	}

	public String getPersistentName(){ 
            return new PstTrainVenue().getClass().getName(); 
	}

        
	public long fetchExc(Entity ent) throws Exception{ 
            TrainVenue trainVenue = fetchExc(ent.getOID());
        
            ent = (Entity)trainVenue;
            return ent.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
            return insertExc((TrainVenue)ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
            return updateExc((TrainVenue) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
	}

        
	public static TrainVenue fetchExc(long oid) throws DBException {
            try{ 
                TrainVenue trainVenue = new TrainVenue();
                PstTrainVenue pstTrainVenue = new PstTrainVenue(oid);

                trainVenue.setOID(pstTrainVenue.getlong(PstTrainVenue.FLD_TRAIN_VENUE_ID));
                trainVenue.setVenueName(pstTrainVenue.getString(PstTrainVenue.FLD_TRAIN_VENUE_NAME));
                trainVenue.setVenueDesc(pstTrainVenue.getString(PstTrainVenue.FLD_TRAIN_VENUE_DESC));

                return trainVenue;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainVenue(0),DBException.UNKNOWN); 
            } 
        }

        public static long insertExc(TrainVenue trainVenue) throws DBException {
            try{ 
                PstTrainVenue pstTrainVenue = new PstTrainVenue(0);

                pstTrainVenue.setString(FLD_TRAIN_VENUE_NAME, trainVenue.getVenueName());
                pstTrainVenue.setString(FLD_TRAIN_VENUE_DESC, trainVenue.getVenueDesc());

                pstTrainVenue.insert();            
                trainVenue.setOID(pstTrainVenue.getlong(PstTrainVenue.FLD_TRAIN_VENUE_ID));

                return trainVenue.getOID();
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainVenue(0),DBException.UNKNOWN); 
            } 
        }

        public static long updateExc(TrainVenue trainVenue) throws DBException {
            try{ 
               if(trainVenue.getOID() != 0) {
                   PstTrainVenue pstTrainVenue = new PstTrainVenue(trainVenue.getOID());

                   pstTrainVenue.setString(FLD_TRAIN_VENUE_NAME, trainVenue.getVenueName());
                   pstTrainVenue.setString(FLD_TRAIN_VENUE_DESC, trainVenue.getVenueDesc());

                   pstTrainVenue.update();

                   return trainVenue.getOID();
               }           
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainVenue(0),DBException.UNKNOWN); 
            } 

            return 0;
        }

        public static long deleteExc(long oid) throws DBException {
            try{ 
               PstTrainVenue pstTrainVenue = new PstTrainVenue(oid);
               pstTrainVenue.delete();

               return oid;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainVenue(0),DBException.UNKNOWN); 
            } 
        }

        
	public static Vector listAll(){ 
            return list(0, 0, "", ""); 
	}

	public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT * FROM " + TBL_TRAIN_VENUE; 
                
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
                    TrainVenue trainVenue = new TrainVenue();
                    resultToObject(rs, trainVenue);
                    lists.add(trainVenue);
                }
                
                rs.close();                
                return lists;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                DBResultSet.close(dbrs);
            }            
            
            return new Vector();
	}

	public static void resultToObject(ResultSet rs, TrainVenue trainVenue){
            try{
                trainVenue.setOID(rs.getLong(PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_ID]));
                trainVenue.setVenueName(rs.getString(PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_NAME]));
                trainVenue.setVenueDesc(rs.getString(PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_DESC]));
            }
            catch(Exception e){ 
                e.printStackTrace();
            }
	}

	public static boolean checkOID(long trainTypeId){            
            boolean result = false;
            DBResultSet dbrs = null;
            
            try{
                String sql = "SELECT * FROM " + TBL_TRAIN_VENUE + " WHERE " + 
                             PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_ID] + 
                             " = " + trainTypeId;

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) 
                    result = true; 
                
                rs.close();                
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                DBResultSet.close(dbrs);                
            }
            
            return result;
	}

	public static int getCount(String whereClause){
            int count = 0;
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT COUNT(" + PstTrainVenue.fieldNames[PstTrainVenue.FLD_TRAIN_VENUE_ID] + 
                             ") FROM " + TBL_TRAIN_VENUE;
                
                if(whereClause != null && whereClause.length() > 0)
                    sql = sql + " WHERE " + whereClause;

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) 
                    count = rs.getInt(1);

                rs.close();                
                return count;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                DBResultSet.close(dbrs);
            }
            
            return count;
	}

	public static int findLimitStart(long oid, int recordToGet, String whereClause, String order){
            boolean found = false;
            int size = getCount(whereClause);
            int start = 0;            
            
            for(int i=0; (i < size) && !found ; i=i+recordToGet) {                 
                 start = i;
                 Vector list = list(i,recordToGet, whereClause, order); 
                 
                 if(list.size() > 0){
                     
                      for(int ls=0; ls<list.size(); ls++){ 
                           TrainVenue trainVenue = (TrainVenue)list.get(ls);
                           
                           if(oid == trainVenue.getOID())
                                  found=true;
                      }
                      
                 }
            }
            
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
	}
        
}
