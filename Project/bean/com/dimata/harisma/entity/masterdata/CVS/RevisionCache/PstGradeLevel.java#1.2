
/* Created on   :  [date] [time] AM/PM 
 * 
 * @author      :  [authorName] 
 * @version     :  [version] 
 */

/*******************************************************************
 * Class Description    : [project description ... ] 
 * Imput Parameters     : [input parameter ...] 
 * Output               : [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata;

/* package java */ 
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

public class PstGradeLevel extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

        public static final  String TBL_HR_GRADE_LEVEL = "hr_grade_level";//"HR_LEVEL";

        public static final  int FLD_GRADE_LEVEL_ID = 0;
        public static final  int FLD_GRADE_CODE = 1;
       

        public static final  String[] fieldNames = {
                "GRADE_LEVEL_ID",
                "GRADE_CODE"              
         }; 

        public static final  int[] fieldTypes = {
                TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_STRING,
         }; 

        public PstGradeLevel(){
        }

        public PstGradeLevel(int i) throws DBException { 
                super(new PstGradeLevel()); 
        }

        public PstGradeLevel(String sOid) throws DBException { 
                super(new PstGradeLevel(0)); 
                if(!locate(sOid)) 
                        throw new DBException(this,DBException.RECORD_NOT_FOUND); 
                else 
                        return; 
        }

        public PstGradeLevel(long lOid) throws DBException { 
                super(new PstGradeLevel(0)); 
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
                return TBL_HR_GRADE_LEVEL;
        }

        public String[] getFieldNames(){ 
                return fieldNames; 
        }

        public int[] getFieldTypes(){ 
                return fieldTypes; 
        }

        public String getPersistentName(){ 
                return new PstGradeLevel().getClass().getName(); 
        }

        public long fetchExc(Entity ent) throws Exception{ 
                GradeLevel gradeLevel = fetchExc(ent.getOID()); 
                ent = (Entity)gradeLevel; 
                return gradeLevel.getOID(); 
        }

        public long insertExc(Entity ent) throws Exception{ 
                return insertExc((GradeLevel) ent); 
        }

        public long updateExc(Entity ent) throws Exception{ 
                return updateExc((GradeLevel) ent); 
        }

        public long deleteExc(Entity ent) throws Exception{ 
                if(ent==null){ 
                        throw new DBException(this,DBException.RECORD_NOT_FOUND); 
                } 
                return deleteExc(ent.getOID()); 
        }

        public static GradeLevel fetchExc(long oid) throws DBException{ 
                try{ 
                        GradeLevel gradeLevel = new GradeLevel();
                        PstGradeLevel pstLevel = new PstGradeLevel(oid); 
                        gradeLevel.setOID(oid);

                        gradeLevel.setCodeLevel(pstLevel.getString(FLD_GRADE_CODE));
                       

                        return gradeLevel; 
                }catch(DBException dbe){ 
                        throw dbe; 
                }catch(Exception e){ 
                        throw new DBException(new PstGradeLevel(0),DBException.UNKNOWN); 
                } 
        }

        public static long insertExc(GradeLevel gradeLevel) throws DBException{ 
                try{ 
                        PstGradeLevel pstLevel = new PstGradeLevel(0);

                        pstLevel.setString(FLD_GRADE_CODE, gradeLevel.getCodeLevel());
                        
                        pstLevel.insert(); 
                        gradeLevel.setOID(pstLevel.getlong(FLD_GRADE_LEVEL_ID));
                }catch(DBException dbe){ 
                        throw dbe; 
                }catch(Exception e){ 
                        throw new DBException(new PstGradeLevel(0),DBException.UNKNOWN); 
                }
                return gradeLevel.getOID();
        }

        public static long updateExc(GradeLevel gradeLevel) throws DBException{ 
                try{ 
                        if(gradeLevel.getOID() != 0){ 
                                PstGradeLevel pstLevel = new PstGradeLevel(gradeLevel.getOID());
                
                                pstLevel.setString(FLD_GRADE_CODE, gradeLevel.getCodeLevel());

                                pstLevel.update(); 
                                return gradeLevel.getOID();

                        }
                }catch(DBException dbe){ 
                        throw dbe; 
                }catch(Exception e){ 
                        throw new DBException(new PstGradeLevel(0),DBException.UNKNOWN); 
                }
                return 0;
        }

        public static long deleteExc(long oid) throws DBException{ 
                try{ 
                        PstGradeLevel pstLevel = new PstGradeLevel(oid);
                        pstLevel.delete();
                }catch(DBException dbe){ 
                        throw dbe; 
                }catch(Exception e){ 
                        throw new DBException(new PstGradeLevel(0),DBException.UNKNOWN); 
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
                        String sql = "SELECT * FROM " + TBL_HR_GRADE_LEVEL; 
                        if(whereClause != null && whereClause.length() > 0)
                                sql = sql + " WHERE " + whereClause;
                       // if(order != null && order.length() > 0)
                                sql = sql + " ORDER BY " + "GRADE_CODE" ;
                        if(limitStart == 0 && recordToGet == 0)
                                sql = sql + "";
                        else
                                sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();
                        while(rs.next()) {
                                GradeLevel gradeLevel = new GradeLevel();
                                resultToObject(rs, gradeLevel);
                                lists.add(gradeLevel);
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
/**
 * create by satrya 2013-07-04
 * @return 
 */
public static Hashtable hashlistLevel(){
       Hashtable hashListLeavel = new Hashtable();
        DBResultSet dbrs = null;
        try {
                String sql = "SELECT * FROM " + TBL_HR_GRADE_LEVEL; 
               
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                        GradeLevel gradeLevel = new GradeLevel();
                        resultToObject(rs, gradeLevel);
                       hashListLeavel.put(gradeLevel.getOID(), gradeLevel);
                }
                rs.close();
               

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
   return hashListLeavel;
}
        public static void resultToObject(ResultSet rs, GradeLevel gradeLevel){
                try{
                        gradeLevel.setOID(rs.getLong(PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_LEVEL_ID]));
                        gradeLevel.setCodeLevel(rs.getString(PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]));

                }catch(Exception e){ }
        }

        public static boolean checkOID(long gradeId){
                DBResultSet dbrs = null;
                boolean result = false;
                try{
                        String sql = "SELECT * FROM " + TBL_HR_GRADE_LEVEL + " WHERE " + 
                                                PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_LEVEL_ID] + " = '" + gradeId +"'";

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();

                        while(rs.next()) { result = true; }
                        rs.close();
                }catch(Exception e){
                        System.out.println("err : "+e.toString());
                }finally{
                        DBResultSet.close(dbrs);
                        return result;
                }
        }

        public static int getCount(String whereClause){
                DBResultSet dbrs = null;
                try {
                        String sql = "SELECT COUNT("+ PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_LEVEL_ID] + ") FROM " + TBL_HR_GRADE_LEVEL;
                        if(whereClause != null && whereClause.length() > 0)
                                sql = sql + " WHERE " + whereClause;

                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();

                        int count = 0;
                        while(rs.next()) { count = rs.getInt(1); }

                        rs.close();
                        return count;
                }catch(Exception e) {
                        return 0;
                }finally {
                        DBResultSet.close(dbrs);
                }
        }


        /* This method used to find current data */
        public static int findLimitStart( long oid, int recordToGet, String whereClause,String order){
                int size = getCount(whereClause);
                int start = 0;
                boolean found =false;
                for(int i=0; (i < size) && !found ; i=i+recordToGet){
                         Vector list =  list(i,recordToGet, whereClause, order); 
                         start = i;
                         if(list.size()>0){
                          for(int ls=0;ls<list.size();ls++){ 
                                   GradeLevel gradeLevel = (GradeLevel)list.get(ls);
                                   if(oid == gradeLevel.getOID())
                                          found=true;
                          }
                  }
                }
                if((start >= size) && (size > 0))
                    start = start - recordToGet;

                return start;
        }


        public static int findLimitCommand(int start, int recordToGet, int vectSize)
    {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
        if(start == 0)
            cmd =  Command.FIRST;
        else{
            if(start == (vectSize-recordToGet))
                cmd = Command.LAST;
            else{
                start = start + recordToGet;
                if(start <= (vectSize - recordToGet)){
                        cmd = Command.NEXT;
                    System.out.println("next.......................");
                }else{
                    start = start - recordToGet;
                             if(start > 0){
                         cmd = Command.PREV;
                         System.out.println("prev.......................");
                             }
                }
            }
        }

        return cmd;
    }




    public static boolean checkMaster(long oid){
        if(PstEmployee.checkLevel(oid))
            return true;
        else
            return false;
    }
}