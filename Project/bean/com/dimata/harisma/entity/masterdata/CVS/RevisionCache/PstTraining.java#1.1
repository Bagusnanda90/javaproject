
package com.dimata.harisma.entity.masterdata; 

/* package java */ 
import java.io.*;
import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;


public class PstTraining extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

        public static final  String SESS_HR_TRAINING    = "SESS_HR_TRAINING"; 
	public static final  String TBL_HR_TRAINING     = "hr_training";

	public static final  int FLD_TRAINING_ID        = 0;
	public static final  int FLD_NAME               = 1;
	public static final  int FLD_DESCRIPTION        = 2;        
        // added for Hard Rock
        public static final  int FLD_TYPE               = 3;

        
	public static final  String[] fieldNames = {
		"TRAINING_ID",
		"NAME",
		"DESCRIPTION",
                "TYPE"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_STRING,
                TYPE_LONG
	 }; 
        
       
        public static final int ORDER_TRAINING_NAME = 0;
        public static final int ORDER_DEPARTMENT = 1;
  

        public static final int[] orderValue = {0, 1};

        public static final String[] orderKey = {"Training Name", "Department"};

        public static Vector getOrderValue() {
                Vector order = new Vector();
                for (int i = 0; i < orderValue.length; i++) {
                    order.add(String.valueOf(orderValue[i]));
                }
                return order;
        }

        public static Vector getOrderKey() {
                Vector order = new Vector();
                for (int i = 0; i < orderKey.length; i++) {
                    order.add(orderKey[i]);
                }
                return order;
        }

	public PstTraining(){
	}

	public PstTraining(int i) throws DBException { 
		super(new PstTraining()); 
	}

	public PstTraining(String sOid) throws DBException { 
		super(new PstTraining(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstTraining(long lOid) throws DBException { 
		super(new PstTraining(0)); 
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		}catch(Exception e) { 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	} 

	public int getFieldSize(){ 
		return fieldNames.length; 
	}

	public String getTableName(){ 
		return TBL_HR_TRAINING;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstTraining().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		Training training = fetchExc(ent.getOID()); 
		ent = (Entity)training; 
		return training.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((Training) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((Training) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static Training fetchExc(long oid) throws DBException{ 
		try{ 
			Training training = new Training();
			PstTraining pstTraining = new PstTraining(oid); 
			training.setOID(oid);

			training.setName(pstTraining.getString(FLD_NAME));
			training.setDescription(pstTraining.getString(FLD_DESCRIPTION));
                        // added for Hard Rock
                        training.setType(pstTraining.getlong(FLD_TYPE));

			return training; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTraining(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(Training training) throws DBException{ 
		try{ 
			PstTraining pstTraining = new PstTraining(0);

			pstTraining.setString(FLD_NAME, training.getName());
			pstTraining.setString(FLD_DESCRIPTION, training.getDescription());
                        // added for Hard Rock
                        pstTraining.setLong(FLD_TYPE, training.getType());

			pstTraining.insert(); 
			training.setOID(pstTraining.getlong(FLD_TRAINING_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTraining(0),DBException.UNKNOWN); 
		}
		return training.getOID();
	}

	public static long updateExc(Training training) throws DBException{ 
		try{ 
			if(training.getOID() != 0){ 
				PstTraining pstTraining = new PstTraining(training.getOID());

				pstTraining.setString(FLD_NAME, training.getName());
				pstTraining.setString(FLD_DESCRIPTION, training.getDescription());
                                // added for Hard Rock
                                pstTraining.setLong(FLD_TYPE, training.getType());

				pstTraining.update(); 
				return training.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTraining(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstTraining pstTraining = new PstTraining(oid);
			pstTraining.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstTraining(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 500, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_TRAINING; 
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
				Training training = new Training();
				resultToObject(rs, training);
				lists.add(training);
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

	public static void resultToObject(ResultSet rs, Training training){
		try{
			training.setOID(rs.getLong(PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]));
			training.setName(rs.getString(PstTraining.fieldNames[PstTraining.FLD_NAME]));
			training.setDescription(rs.getString(PstTraining.fieldNames[PstTraining.FLD_DESCRIPTION]));
                        // added for Hard Rock
                        training.setType(rs.getLong(PstTraining.fieldNames[PstTraining.FLD_TYPE]));
		}
                catch(Exception e){ }
	}

	public static boolean checkOID(long trainingId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_TRAINING + " WHERE " + 
                                      PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID] + " = " + trainingId;

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
			String sql = "SELECT COUNT("+ PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID] + ") FROM " + TBL_HR_TRAINING;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   Training training = (Training)list.get(ls);
				   if(oid == training.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

        //
        // additional methods
        //
        
        public static String getTrainingDepartment(long oidTraining, Vector vctDepartment){
            String where = PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID]+"="+oidTraining;
            Vector vct = PstTrainingDept.list(0,0,where, null);

            String departments = "";
            if(vct!=null && vct.size()>0 && vctDepartment!=null && vctDepartment.size()>0){
                if(vct.size() == vctDepartment.size()){
                    return "All Department ( General Training )";
                }
                else{
                    for(int i=0; i<vct.size(); i++){
                        TrainingDept td = (TrainingDept)vct.get(i);
                        String dep = checkDepartmentOID(td.getDepartmentId(), vctDepartment);
                        
                        if(dep.length()>0){
                                departments = departments + dep +", ";
                        }
                    }

                    if(departments.length()>0){
                            departments = departments.substring(0, (departments.length()-2));
                    }

                    return departments;
                }
            }

            return "--- no department ---";
        }

        public static String checkDepartmentOID(long oid, Vector vct){
                    if(vct!=null && vct.size()>0){
                            for(int i=0; i<vct.size(); i++){
                                    Department dp = (Department)vct.get(i);
                                    if(dp.getOID() == oid){
                                            return dp.getDepartment();
                                    }
                            }
                    }
                    return "";
            }


        public static Vector searchTrainingMaster(String trainingName,String description, Vector vectDep,int start, int recordToGet, int orderBy, int trainingType)
        {
            DBResultSet dbrs = null;
            Vector result = new Vector(1,1);
            //System.out.println("order BY..."+orderBy);
            if (vectDep == null)
                return new Vector(1,1);
            try {
                String sql = " SELECT TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                             ", TR."+PstTraining.fieldNames[PstTraining.FLD_NAME]+
                             ", TR."+PstTraining.fieldNames[PstTraining.FLD_DESCRIPTION]+
                             ", TR."+PstTraining.fieldNames[PstTraining.FLD_TYPE]+
                             " FROM "+PstTraining.TBL_HR_TRAINING+ " AS TR"+
                             " INNER JOIN "+PstTrainingDept.TBL_HR_TRAINING_DEPT+ " AS TRDEP"+
                             " ON TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                             " = TRDEP."+PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID]+
                             " INNER JOIN "+PstDepartment.TBL_HR_DEPARTMENT+" AS DEPT"+
                             " ON TRDEP."+PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+
                             " = DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+
                             " WHERE TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+" > 0";


                String whereClause = "";

                if((trainingName!= null)&& (trainingName.length()>0)){
                   whereClause = whereClause + "  TR."+PstTraining.fieldNames[PstTraining.FLD_NAME]+
                      " LIKE '%"+trainingName.trim()+"%' AND ";           
                }

                 if((description!= null)&& (description.length()>0)){
                   whereClause = whereClause + "  TR."+PstTraining.fieldNames[PstTraining.FLD_DESCRIPTION]+
                      " LIKE '%"+description.trim()+"%' AND ";           
                }


                if(vectDep!=null && vectDep.size()>0){
                     sql = sql + " AND (";
                     for(int x=0; x<vectDep.size(); x++){
                        Department dept = (Department)vectDep.get(x);
                        sql = sql + " TRDEP."+PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+"='"+dept.getOID()+"' OR";
                     }
                    sql = sql.substring(0, sql.length()-2);
                    sql = sql + ")";
                 }

                 /* added by Bayu
                  *
                  * if training privilege is departmental
                  * don't display records belong to general training
                  * (having 2 or more departments) */

                 if(trainingType == 2) {
                    whereClause += " TRDEP." + PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] +
                                   " NOT IN (SELECT " + PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + 
                                   " FROM " + PstTrainingDept.TBL_HR_TRAINING_DEPT + 
                                   " GROUP BY " + PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + 
                                   " HAVING COUNT(" + PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID] + ") > 1) AND ";
                 }

                /*if(whereClause != null && whereClause.length()>0){
                    sql = sql + " AND " + whereClause + " 1 = 1 ";
                }*/            


                if(whereClause != null && whereClause.length()>0){
                    whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                    sql = sql + whereClause;
                     //sql = sql + " WHERE " + whereClause;
                   }

                sql = sql + " GROUP BY TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID];
                if(orderBy==PstTraining.ORDER_TRAINING_NAME){
                    sql = sql + " ORDER BY TR."+PstTraining.fieldNames[PstTraining.FLD_NAME];
                }

                if(orderBy==PstTraining.ORDER_DEPARTMENT){
                    sql = sql + " ORDER BY DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
                }
                  sql = sql + " LIMIT " + start + "," + recordToGet;


                System.out.println("\t SQL trainingSearch : " + sql);

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {
                    Vector vect = new Vector(1,1);

                    Training  training = new Training();
                    TrainingDept trainingDept = new TrainingDept();

                    training.setOID(rs.getLong(PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]));
                    training.setName(rs.getString(PstTraining.fieldNames[PstTraining.FLD_NAME]));
                    training.setDescription(rs.getString(PstTraining.fieldNames[PstTraining.FLD_DESCRIPTION]));
                    training.setType(rs.getLong(PstTraining.fieldNames[PstTraining.FLD_TYPE]));
                    vect.add(training);



                    result.add(vect);
                }
                rs.close();
                return result;
            } catch (Exception e) {
                System.out.println("\t Exception on search Training History : " + e);
            }
            finally {
                DBResultSet.close(dbrs);
            }
            return new Vector(1,1);

            }


        public static int countTrainingMaster(String trainingName,String description, Vector vectDep)
        {
            DBResultSet dbrs = null;
            Vector result = new Vector(1,1);
            if (vectDep == null)
                return 0;
            try {
                String sql = " SELECT COUNT(DISTINCT TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+")"+
                             " FROM "+PstTraining.TBL_HR_TRAINING+ " AS TR"+
                             " INNER JOIN "+PstTrainingDept.TBL_HR_TRAINING_DEPT+ " AS TRDEP"+
                             " ON TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+
                             " = TRDEP."+PstTrainingDept.fieldNames[PstTrainingDept.FLD_TRAINING_ID]+
                             " WHERE TR."+PstTraining.fieldNames[PstTraining.FLD_TRAINING_ID]+" > 0";


                String whereClause = "";

                if((trainingName!= null)&& (trainingName.length()>0)){
                   whereClause = whereClause + "  TR."+PstTraining.fieldNames[PstTraining.FLD_NAME]+
                      " LIKE '%"+trainingName.trim()+"%' AND ";           
                }

                 if((description!= null)&& (description.length()>0)){
                   whereClause = whereClause + "  TR."+PstTraining.fieldNames[PstTraining.FLD_DESCRIPTION]+
                      " LIKE '%"+description.trim()+"%' AND ";           
                }


                if(vectDep!=null && vectDep.size()>0){
                     sql = sql + " AND (";
                     for(int x=0; x<vectDep.size(); x++){
                        Department dept = (Department)vectDep.get(x);
                        sql = sql + " TRDEP."+PstTrainingDept.fieldNames[PstTrainingDept.FLD_DEPARTMENT_ID]+"='"+dept.getOID()+"' OR";
                     }
                    sql = sql.substring(0, sql.length()-2);
                    sql = sql + ")";
                 }

                /*if(whereClause != null && whereClause.length()>0){
                    sql = sql + " AND " + whereClause + " 1 = 1 ";
                }*/

                  if(whereClause != null && whereClause.length()>0){
                    whereClause = " AND "+ whereClause.substring(0,whereClause.length()-4);
                    sql = sql + whereClause;
                     //sql = sql + " WHERE " + whereClause;
                   }

                //sql = sql + " GROUP BY TR."+PstTraining.fieldNames[PstTraining.FLD_NAME];
               // sql = sql + " ORDER BY TR."+PstTraining.fieldNames[PstTraining.FLD_NAME];

                //  sql = sql + " LIMIT " + start + "," + recordToGet;


                System.out.println("\t SQL countTrainingSearch : " + sql);

                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                int count = 0;
                while(rs.next()) {
                    count = rs.getInt(1);
                }
                rs.close();
                return count;
            } catch (Exception e) {
                System.out.println("\t Exception on search Training History : " + e);
            }
            finally {
                DBResultSet.close(dbrs);
            }
            return 0;

            }
}
