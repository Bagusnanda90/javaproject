
package com.dimata.harisma.entity.employee;

// import java
import java.sql.*;
import java.util.*;
import java.util.Date;

// import dimata
import com.dimata.util.lang.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// import harisma
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class PstTrainingAttendancePlan extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_TRAIN_ATTEND_PLAN    =   "hr_training_attendance_plan";
    
        public static final int FLD_TRAIN_ATTEND_ID     =   0;
        public static final int FLD_TRAIN_PLAN_ID       =   1;
        public static final int FLD_EMP_ID              =   2;
        public static final int FLD_DURATION            =   3;
    
        public static String[] fieldNames =
        {
            "TRAINING_ATTENDANCE_ID",
            "TRAINING_PLAN_ID",
            "EMPLOYEE_ID",
            "DURATION"
        };
        
        public static int[] fieldTypes =
        {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_LONG,            
            TYPE_LONG,
            TYPE_INT
        };


	public PstTrainingAttendancePlan(){
	}

	public PstTrainingAttendancePlan(int i) throws DBException { 
            super(new PstTrainingAttendancePlan()); 
	}

	public PstTrainingAttendancePlan(String sOid) throws DBException { 
            super(new PstTrainingAttendancePlan(0));
            
            if (!locate(sOid)) {
                throw new DBException(this, DBException.RECORD_NOT_FOUND);
            } 
            else {
                return;
            } 
        }

	public PstTrainingAttendancePlan(long lOid) throws DBException { 
            super(new PstTrainingAttendancePlan(0));
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
            return TBL_TRAIN_ATTEND_PLAN;
	}

	public String[] getFieldNames(){ 
            return fieldNames; 
	}

	public int[] getFieldTypes(){ 
            return fieldTypes; 
	}

	public String getPersistentName(){ 
            return new PstTrainingAttendancePlan().getClass().getName(); 
	}

        
	public long fetchExc(Entity ent) throws Exception{ 
            TrainingAttendancePlan attendancePlan = fetchExc(ent.getOID());
        
            ent = (Entity)attendancePlan;
            return ent.getOID();
	}

	public long insertExc(Entity ent) throws Exception{ 
            return insertExc((TrainingAttendancePlan)ent);
	}

	public long updateExc(Entity ent) throws Exception{ 
            return updateExc((TrainingAttendancePlan) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
            if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
            } 
            return deleteExc(ent.getOID()); 
	}

        
	public static TrainingAttendancePlan fetchExc(long oid) throws DBException {
            try{ 
                TrainingAttendancePlan attendancePlan = new TrainingAttendancePlan();
                PstTrainingAttendancePlan pstAttendancePlan = new PstTrainingAttendancePlan(oid);

                attendancePlan.setOID(pstAttendancePlan.getlong(PstTrainingAttendancePlan.FLD_TRAIN_ATTEND_ID));
                attendancePlan.setTrainPlanid(pstAttendancePlan.getlong(PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID));
                attendancePlan.setEmployeeId(pstAttendancePlan.getlong(PstTrainingAttendancePlan.FLD_EMP_ID));
                attendancePlan.setDuration(pstAttendancePlan.getInt(PstTrainingAttendancePlan.FLD_DURATION));
                               
                return attendancePlan;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingAttendancePlan(0),DBException.UNKNOWN); 
            } 
        }

        public static long insertExc(TrainingAttendancePlan attendancePlan) throws DBException {
            try{ 
                PstTrainingAttendancePlan pstAttendancePlan = new PstTrainingAttendancePlan(0);

                pstAttendancePlan.setLong(FLD_TRAIN_PLAN_ID, attendancePlan.getTrainPlanid());
                pstAttendancePlan.setLong(FLD_EMP_ID, attendancePlan.getEmployeeId());
                pstAttendancePlan.setInt(FLD_DURATION, attendancePlan.getDuration());
              
                pstAttendancePlan.insert();            
                attendancePlan.setOID(pstAttendancePlan.getlong(PstTrainingAttendancePlan.FLD_TRAIN_ATTEND_ID));

                return attendancePlan.getOID();
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingAttendancePlan(0),DBException.UNKNOWN); 
            } 
        }

        public static long updateExc(TrainingAttendancePlan attendancePlan) throws DBException {
            try{ 
               if(attendancePlan.getOID() != 0) {
                   PstTrainingAttendancePlan pstAttendancePlan = new PstTrainingAttendancePlan(attendancePlan.getOID());

                   pstAttendancePlan.setLong(FLD_TRAIN_PLAN_ID, attendancePlan.getTrainPlanid());
                   pstAttendancePlan.setLong(FLD_EMP_ID, attendancePlan.getEmployeeId());
                   pstAttendancePlan.setInt(FLD_DURATION, attendancePlan.getDuration());

                   pstAttendancePlan.update();

                   return attendancePlan.getOID();
               }           
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingAttendancePlan(0),DBException.UNKNOWN); 
            } 

            return 0;
        }

        public static long deleteExc(long oid) throws DBException {
            try{ 
               PstTrainingAttendancePlan pstAttendancePlan = new PstTrainingAttendancePlan(oid);
               pstAttendancePlan.delete();

               return oid;
            }
            catch(DBException dbe){ 
                throw dbe; 
            }
            catch(Exception e){ 
                throw new DBException(new PstTrainingAttendancePlan(0),DBException.UNKNOWN); 
            } 
        }

        
	public static Vector listAll(){ 
            return list(0, 0, "", ""); 
	}

	public static Vector list(int limitStart, int recordToGet, String whereClause, String order){
            Vector lists = new Vector(); 
            DBResultSet dbrs = null;
            
            try {
                String sql = "SELECT * FROM " + TBL_TRAIN_ATTEND_PLAN; 
                
                if(whereClause != null && whereClause.length() > 0)
                    sql = sql + " WHERE " + whereClause;
                
                if(order != null && order.length() > 0)
                    sql = sql + " ORDER BY " + order;
			
                if(limitStart == 0 && recordToGet == 0)
                    sql = sql + ""; 
                else 
                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ; 
                
                System.out.println("LIST COMMAND = " + sql);
			
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                while(rs.next()) {
                    TrainingAttendancePlan attendancePlan = new TrainingAttendancePlan();
                    resultToObject(rs, attendancePlan);
                    lists.add(attendancePlan);
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
        
        public static Vector listEmployeeByPlan(long oidPlan, long oidDept){
		Vector lists = new Vector();
            	DBResultSet dbrs = null;
                
		try {
                    String sql = " SELECT atd.* FROM " + TBL_TRAIN_ATTEND_PLAN + " as atd" +
                                 " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp" +
                                 " on atd." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID]+ "=" +
                                 " emp. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                                
                                 " WHERE atd." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oidPlan + 
                                 " AND emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + oidDept +
                                 " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]; 
                    
                    
                    System.out.println(">>>>> List Attendance = " + sql);
                    
                    dbrs = DBHandler.execQueryResult(sql);                 
                    ResultSet rs = dbrs.getResultSet();
                    
                    while(rs.next()) {
                        TrainingAttendancePlan attendance = new TrainingAttendancePlan();
                        resultToObject(rs, attendance);
                        lists.add(attendance);
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
        
        public static Vector listEmployeeByPlan(long oidPlan){
		Vector lists = new Vector();
            	DBResultSet dbrs = null;
                
		try {
                    String sql = " SELECT atd.* FROM " + TBL_TRAIN_ATTEND_PLAN + " as atd" +
                                 " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " as emp" +
                                 " on atd." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID]+ "=" +
                                 " emp. " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                                
                                 " WHERE atd." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" +
                                 oidPlan + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]; 
                    
                    
                    System.out.println(">>>>> List Attendance = " + sql);
                    
                    dbrs = DBHandler.execQueryResult(sql);                 
                    ResultSet rs = dbrs.getResultSet();
                    
                    while(rs.next()) {
                        TrainingAttendancePlan attendance = new TrainingAttendancePlan();
                        resultToObject(rs, attendance);
                        lists.add(attendance);
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

	public static void resultToObject(ResultSet rs, TrainingAttendancePlan attendancePlan){
            try{
                attendancePlan.setOID(rs.getLong(PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_ATTEND_ID]));
                attendancePlan.setTrainPlanid(rs.getLong(PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID]));
                attendancePlan.setEmployeeId(rs.getLong(PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID]));
                attendancePlan.setDuration(rs.getInt(PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_DURATION]));
            }
            catch(Exception e){ 
                e.printStackTrace();
            }
	}

	public static boolean checkOID(long trainTypeId){            
            boolean result = false;
            DBResultSet dbrs = null;
            
            try{
                String sql = "SELECT * FROM " + TBL_TRAIN_ATTEND_PLAN + " WHERE " + 
                             PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_ATTEND_ID] + 
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
                String sql = "SELECT COUNT(" + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_ATTEND_ID] + 
                             ") FROM " + TBL_TRAIN_ATTEND_PLAN;
                
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
                           TrainingAttendancePlan attendancePlan = (TrainingAttendancePlan)list.get(ls);
                           
                           if(oid == attendancePlan.getOID())
                                  found=true;
                      }
                      
                 }
            }
            
            if((start >= size) && (size > 0))
                start = start - recordToGet;

            return start;
	}
        
        public static Vector getAttendanceByPlan(long oidPlan) {
                Vector lists = new Vector(); 
		DBResultSet dbrs = null;
                
                try {                      
                        String sql = " SELECT TA.* FROM " + PstTrainingAttendancePlan.TBL_TRAIN_ATTEND_PLAN+ " TA" +
                                     " INNER JOIN " + PstTrainingActivityPlan.TBL_HR_TRAINING_ACTIVITY_PLAN + " TP" +
                                     " ON TA." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] +
                                     " = TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE+ " EMP" +
                                     " ON TA." + PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID] +
                                     " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                                     " WHERE TP." + PstTrainingActivityPlan.fieldNames[PstTrainingActivityPlan.FLD_TRAINING_ACTIVITY_PLAN_ID] +
                                     " = " + oidPlan +
                                     " ORDER BY EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME];

                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+sql);
                        dbrs = DBHandler.execQueryResult(sql);
            
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				TrainingAttendancePlan attendance = new TrainingAttendancePlan();
				resultToObject(rs, attendance);
                                lists.add(attendance);                       
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
