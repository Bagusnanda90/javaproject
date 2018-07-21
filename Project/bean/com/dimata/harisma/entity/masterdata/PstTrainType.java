
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

public class PstTrainType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final String TBL_TRAIN_TYPE       =   "hr_training_type";
    
        public static final int FLD_TRAIN_TYPE_ID       =   0;
        public static final int FLD_TRAIN_TYPE_NAME     =   1;
        public static final int FLD_TRAIN_TYPE_DESC     =   2;
        public static final int FLD_TRAIN_TYPE_CODE     =   3;

        public static String[] fieldNames =
        {
            "TYPE_ID",
            "NAME",
            "DESCRIPTION",
            "CODE"
        };
        
        public static int[] fieldTypes =
        {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING
        };


	public PstTrainType(){
	}

	public PstTrainType(int i) throws DBException { 
            super(new PstTrainType()); 
	}

	public PstTrainType(String sOid) throws DBException { 
            super(new PstTrainType(0));
            
            if (!locate(sOid)) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            } 
            else {
                return;
            } 
        }

	public PstTrainType(long lOid) throws DBException { 
            super(new PstTrainType(0));
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
            return TBL_TRAIN_TYPE;
	}

	public String[] getFieldNames(){ 
            return fieldNames; 
	}

	public int[] getFieldTypes(){ 
            return fieldTypes; 
	}

	public String getPersistentName(){ 
            return new PstTrainType().getClass().getName(); 
	}

        
	public long fetchExc(Entity ent) throws Exception{ 
            TrainType trainType = fetchExc(ent.getOID());
        
            ent = (Entity)trainType;
            return ent.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
            return insertExc((TrainType)ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
            return updateExc((TrainType) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
	}

        
	public static TrainType fetchExc(long oid) throws DBException {
            try{ 
                TrainType trainType = new TrainType();
                PstTrainType pstTrainType = new PstTrainType(oid);

                trainType.setOID(pstTrainType.getlong(PstTrainType.FLD_TRAIN_TYPE_ID));
                trainType.setTypeName(pstTrainType.getString(PstTrainType.FLD_TRAIN_TYPE_NAME));
                trainType.setTypeDesc(pstTrainType.getString(PstTrainType.FLD_TRAIN_TYPE_DESC));
                trainType.setTypeCode(pstTrainType.getString(PstTrainType.FLD_TRAIN_TYPE_CODE));

                return trainType;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainType(0),DBException.UNKNOWN); 
            } 
        }

        public static long insertExc(TrainType trainType) throws DBException {
            try{ 
                PstTrainType pstTrainType = new PstTrainType(0);

                pstTrainType.setString(FLD_TRAIN_TYPE_NAME, trainType.getTypeName());
                pstTrainType.setString(FLD_TRAIN_TYPE_DESC, trainType.getTypeDesc());
                pstTrainType.setString(FLD_TRAIN_TYPE_CODE, trainType.getTypeCode());

                pstTrainType.insert();            
                trainType.setOID(pstTrainType.getlong(PstTrainType.FLD_TRAIN_TYPE_ID));

                return trainType.getOID();
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainType(0),DBException.UNKNOWN); 
            } 
        }

        public static long updateExc(TrainType trainType) throws DBException {
            try{ 
               if(trainType.getOID() != 0) {
                   PstTrainType pstTrainType = new PstTrainType(trainType.getOID());

                   pstTrainType.setString(FLD_TRAIN_TYPE_NAME, trainType.getTypeName());
                   pstTrainType.setString(FLD_TRAIN_TYPE_DESC, trainType.getTypeDesc());
                   pstTrainType.setString(FLD_TRAIN_TYPE_CODE, trainType.getTypeCode());

                   pstTrainType.update();

                   return trainType.getOID();
               }           
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainType(0),DBException.UNKNOWN); 
            } 

            return 0;
        }

        public static long deleteExc(long oid) throws DBException {
            try{ 
               PstTrainType pstTrainType = new PstTrainType(oid);
               pstTrainType.delete();

               return oid;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainType(0),DBException.UNKNOWN); 
            } 
        }

        
	public static Vector listAll(){ 
            return list(0, 0, "", ""); 
	}

	public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT * FROM " + TBL_TRAIN_TYPE; 
                
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
                    TrainType trainType = new TrainType();
                    resultToObject(rs, trainType);
                    lists.add(trainType);
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

	public static void resultToObject(ResultSet rs, TrainType trainType){
            try{
                trainType.setOID(rs.getLong(PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_ID]));
                trainType.setTypeName(rs.getString(PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_NAME]));
                trainType.setTypeDesc(rs.getString(PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_DESC]));
                trainType.setTypeCode(rs.getString(PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_CODE]));
            }
            catch(Exception e){ 
                e.printStackTrace();
            }
	}

	public static boolean checkOID(long trainTypeId){            
            boolean result = false;
            DBResultSet dbrs = null;
            
            try{
                String sql = "SELECT * FROM " + TBL_TRAIN_TYPE + " WHERE " + 
                             PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_ID] + 
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
                String sql = "SELECT COUNT(" + PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_ID] + 
                             ") FROM " + TBL_TRAIN_TYPE;
                
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
                           TrainType trainType = (TrainType)list.get(ls);
                           
                           if(oid == trainType.getOID())
                                  found=true;
                      }
                      
                 }
            }
            
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
	}
        
}
