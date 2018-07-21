
package com.dimata.harisma.entity.employee;

// import java
import java.sql.*;
import java.util.*;
import java.util.Date;

// import dimata
import com.dimata.util.lang.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class PstTrainingSchedule extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
        public static final String TBL_TRAIN_SCHEDULE   =   "hr_training_schedule";
    
        public static final int FLD_TRAIN_SCHEDULE_ID   =   0;
        public static final int FLD_TRAIN_PLAN_ID       =   1;
        public static final int FLD_TRAIN_DATE          =   2;
        public static final int FLD_START_TIME          =   3;
        public static final int FLD_END_TIME            =   4;
        public static final int FLD_TRAIN_VENUE_ID      =   5;
        public static final int FLD_TRAIN_END_DATE      =   6;
        public static final int FLD_TOTAL_HOUR          =   7;
        

        public static String[] fieldNames =
        {
            "TRAINING_SCHEDULE_ID",
            "TRAINING_PLAN_ID",
            "TRAIN_DATE",
            "START_TIME",
            "END_TIME",
            "VENUE_ID",
            "TRAIN_END_DATE",
            "TOTAL_HOUR"
        };
        
        public static int[] fieldTypes =
        {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_LONG,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_LONG,
            TYPE_DATE,
            TYPE_INT
        };


	public PstTrainingSchedule(){
	}

	public PstTrainingSchedule(int i) throws DBException { 
            super(new PstTrainingSchedule()); 
	}

	public PstTrainingSchedule(String sOid) throws DBException { 
            super(new PstTrainingSchedule(0));
            
            if (!locate(sOid)) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            } 
            else {
                return;
            } 
        }

	public PstTrainingSchedule(long lOid) throws DBException { 
            super(new PstTrainingSchedule(0));
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
            return TBL_TRAIN_SCHEDULE;
	}

	public String[] getFieldNames(){ 
            return fieldNames; 
	}

	public int[] getFieldTypes(){ 
            return fieldTypes; 
	}

	public String getPersistentName(){ 
            return new PstTrainingSchedule().getClass().getName(); 
	}

        
	public long fetchExc(Entity ent) throws Exception{ 
            TrainingSchedule trainSchedule = fetchExc(ent.getOID());
        
            ent = (Entity)trainSchedule;
            return ent.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
            return insertExc((TrainingSchedule)ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
            return updateExc((TrainingSchedule) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
	}

        
	public static TrainingSchedule fetchExc(long oid) throws DBException {
            try{ 
                TrainingSchedule trainSchedule = new TrainingSchedule();
                PstTrainingSchedule pstTrainingSchedule = new PstTrainingSchedule(oid);

                trainSchedule.setOID(pstTrainingSchedule.getlong(PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID));
                trainSchedule.setTrainPlanId(pstTrainingSchedule.getlong(PstTrainingSchedule.FLD_TRAIN_PLAN_ID));
                trainSchedule.setTrainDate(pstTrainingSchedule.getDate(PstTrainingSchedule.FLD_TRAIN_DATE));
                trainSchedule.setTrainEndDate(pstTrainingSchedule.getDate(PstTrainingSchedule.FLD_TRAIN_END_DATE));
                trainSchedule.setStartTime(pstTrainingSchedule.getDate(PstTrainingSchedule.FLD_START_TIME));
                trainSchedule.setEndTime(pstTrainingSchedule.getDate(pstTrainingSchedule.FLD_END_TIME));
                trainSchedule.setTrainVenueId(pstTrainingSchedule.getlong(pstTrainingSchedule.FLD_TRAIN_VENUE_ID));
                trainSchedule.setTotalHour(pstTrainingSchedule.getInt(pstTrainingSchedule.FLD_TOTAL_HOUR));
                
                return trainSchedule;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingSchedule(0),DBException.UNKNOWN); 
            } 
        }

        public static long insertExc(TrainingSchedule trainSchedule) throws DBException {
            try{ 
                PstTrainingSchedule pstTrainingSchedule = new PstTrainingSchedule(0);

                pstTrainingSchedule.setLong(FLD_TRAIN_PLAN_ID, trainSchedule.getTrainPlanId());
                pstTrainingSchedule.setDate(FLD_TRAIN_DATE, trainSchedule.getTrainDate());
                pstTrainingSchedule.setDate(FLD_TRAIN_END_DATE, trainSchedule.getTrainEndDate());
                pstTrainingSchedule.setDate(FLD_START_TIME, trainSchedule.getStartTime());
                pstTrainingSchedule.setDate(FLD_END_TIME, trainSchedule.getEndTime());
                pstTrainingSchedule.setLong(FLD_TRAIN_VENUE_ID, trainSchedule.getTrainVenueId());
                pstTrainingSchedule.setLong(FLD_TOTAL_HOUR, trainSchedule.getTotalHour());

                pstTrainingSchedule.insert();            
                trainSchedule.setOID(pstTrainingSchedule.getlong(PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID));

                return trainSchedule.getOID();
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingSchedule(0),DBException.UNKNOWN); 
            } 
        }

        public static long updateExc(TrainingSchedule trainSchedule) throws DBException {
            try{ 
               if(trainSchedule.getOID() != 0) {
                   PstTrainingSchedule pstTrainingSchedule = new PstTrainingSchedule(trainSchedule.getOID());

                   pstTrainingSchedule.setLong(FLD_TRAIN_PLAN_ID, trainSchedule.getTrainPlanId());
                   pstTrainingSchedule.setDate(FLD_TRAIN_DATE, trainSchedule.getTrainDate());
                   pstTrainingSchedule.setDate(FLD_TRAIN_END_DATE, trainSchedule.getTrainEndDate());
                   pstTrainingSchedule.setDate(FLD_START_TIME, trainSchedule.getStartTime());
                   pstTrainingSchedule.setDate(FLD_END_TIME, trainSchedule.getEndTime());
                   pstTrainingSchedule.setLong(FLD_TRAIN_VENUE_ID, trainSchedule.getTrainVenueId());
                   pstTrainingSchedule.setLong(FLD_TOTAL_HOUR, trainSchedule.getTotalHour());

                   pstTrainingSchedule.update();

                   return trainSchedule.getOID();
               }           
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingSchedule(0),DBException.UNKNOWN); 
            } 

            return 0;
        }

        public static long deleteExc(long oid) throws DBException {
            try{ 
               PstTrainingSchedule pstTrainingSchedule = new PstTrainingSchedule(oid);
               pstTrainingSchedule.delete();

               return oid;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingSchedule(0),DBException.UNKNOWN); 
            } 
        }

        
	public static Vector listAll(){ 
            return list(0, 0, "", ""); 
	}

	public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT * FROM " + TBL_TRAIN_SCHEDULE; 
                
                if(whereClause != null && whereClause.length() > 0)
                    sql = sql + " WHERE " + whereClause;
                
                if(order != null && order.length() > 0)
                    sql = sql + " ORDER BY " + order;
			
                if(limitStart == 0 && recordToGet == 0)
                    sql = sql + ""; 
                else 
                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ; 
                
                
                System.out.println(">>>>> List Schedule = " + sql);
			
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();               
                                
                while(rs.next()) {
                    TrainingSchedule trainSchedule = new TrainingSchedule();
                    resultToObject(rs, trainSchedule);
                    lists.add(trainSchedule);
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

	public static void resultToObject(ResultSet rs, TrainingSchedule trainSchedule){
            try{
                trainSchedule.setOID(rs.getLong(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID]));
                trainSchedule.setTrainPlanId(rs.getLong(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID]));
                trainSchedule.setTrainDate(rs.getDate(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE]));
                trainSchedule.setTrainEndDate(rs.getDate(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_END_DATE]));
                trainSchedule.setStartTime(rs.getTime(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_START_TIME]));
                trainSchedule.setEndTime(rs.getTime(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_END_TIME]));
                trainSchedule.setTrainVenueId(rs.getLong(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_VENUE_ID]));
                trainSchedule.setTotalHour(rs.getInt(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TOTAL_HOUR]));
            }
            catch(Exception e){ 
                e.printStackTrace();
            }
	}

	public static boolean checkOID(long trainTypeId){            
            boolean result = false;
            DBResultSet dbrs = null;
            
            try{
                String sql = "SELECT * FROM " + TBL_TRAIN_SCHEDULE + " WHERE " + 
                             PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID] + 
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
                String sql = "SELECT COUNT(" + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_SCHEDULE_ID] + 
                             ") FROM " + TBL_TRAIN_SCHEDULE;
                
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
                           TrainingSchedule trainSchedule = (TrainingSchedule)list.get(ls);
                           
                           if(oid == trainSchedule.getOID())
                                  found=true;
                      }
                      
                 }
            }
            
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
	}
        
        public static Vector listTrainingSchedule(long oidPlan){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
                
		try {                       
                        String sql = " SELECT TS.* FROM " + PstTrainingSchedule.TBL_TRAIN_SCHEDULE + " TS" +
                                     " INNER JOIN " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TP" +
                                     " ON TS." + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +
                                     " = TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " WHERE " + PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +
                                     " = " + oidPlan;
                        
                        //System.out.println("schedule >>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
            
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				TrainingSchedule schedule = new TrainingSchedule();
				resultToObject(rs, schedule);
				lists.add(schedule);
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
                
                return new Vector();
	}
    
    
}
