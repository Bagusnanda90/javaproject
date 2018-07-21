/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.adjusmentworkingday;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.LogicParser;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Devin
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Devin
 */
public class PstAdjusmentWorkingDay extends  DBHandler  implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
   
    
    public static final String TBL_HR_ADJUSMENT_WORKING_DAY="hr_adjusment_working_day";
    public static final int FLD_ADJUSMENT_WORKING_DAY_ID=0;
    public static final int FLD_EMPLOYEE_ID=1;
    public static final int FLD_LOCATION_ID=2;
   public static final int FLD_SISTEM_WORK_HOURS=3;
    public static final int FLD_ADJUSMENT_WORKING_DAY=4;
    
   
    
      
    public static String[] fieldNames = {
        "ADJUSMENT_WORKING_DAY_ID",
        "EMPLOYEE_ID",
        "LOCATION_ID",
        "SISTEM_WORK_HOURS",
        "ADJUSMENT_WORKING_DAY"
      
       
            
    };
   
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT
       
        
       
    };
  //update by devin 2014-04-10
 private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }

    public PstAdjusmentWorkingDay() {
    }

    public PstAdjusmentWorkingDay(int i) throws DBException {

        super(new PstAdjusmentWorkingDay());  


    }

    public PstAdjusmentWorkingDay(String sOid) throws DBException {

        super(new PstAdjusmentWorkingDay(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstAdjusmentWorkingDay(long lOid) throws DBException {

        super(new PstAdjusmentWorkingDay(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_ADJUSMENT_WORKING_DAY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
       return fieldTypes;
    }

    public String getPersistentName() {
        return new PstAdjusmentWorkingDay().getClass().getName();
    }

       /**
     * Keterangan: untuk mengambil data dari 
     * database berdasarkan oid oidEmployee dan kemudaian di set objecknya
     * @param oid : oidEmployee
     * @return
     * @throws DBException 
     */
    public static AdjusmentWorkingDay fetchExc(long oid) throws DBException {

        try {
            AdjusmentWorkingDay adjusmentWorkingDay = new AdjusmentWorkingDay();
            PstAdjusmentWorkingDay pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(oid);
            adjusmentWorkingDay.setOID(oid);
            adjusmentWorkingDay.setEmployeeId(pstAdjusmentWorkingDay.getLong(FLD_EMPLOYEE_ID));
            adjusmentWorkingDay.setLocationId(pstAdjusmentWorkingDay.getLong(FLD_LOCATION_ID));
            adjusmentWorkingDay.setSistemWorkHours(pstAdjusmentWorkingDay.getdouble(FLD_SISTEM_WORK_HOURS));
            adjusmentWorkingDay.setAdjusmentWorkingDay(pstAdjusmentWorkingDay.getdouble(FLD_ADJUSMENT_WORKING_DAY_ID));
           
            
            return adjusmentWorkingDay;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAdjusmentWorkingDay(0), DBException.UNKNOWN);
        }
    }
    public long fetchExc(Entity entity) throws Exception {
        AdjusmentWorkingDay adjusmentWorkingDay = fetchExc(entity.getOID());
        entity = (Entity)adjusmentWorkingDay;
        return adjusmentWorkingDay.getOID();
    }
    
 /**
  * Keterangan: fungsi untuk update data to database
  * create by  satrya 2013-09-27
  * @param ConfigRewardAndPunishment
  * @return
  * @throws DBException 
  */
    public static synchronized long updateExc(AdjusmentWorkingDay adjusmentWorkingDay) throws DBException {
        try {
            if (adjusmentWorkingDay.getOID() != 0) {
                PstAdjusmentWorkingDay pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(adjusmentWorkingDay.getOID()) {};
                pstAdjusmentWorkingDay.setLong(FLD_EMPLOYEE_ID, adjusmentWorkingDay.getEmployeeId());
                pstAdjusmentWorkingDay.setLong(FLD_LOCATION_ID, adjusmentWorkingDay.getLocationId());
                pstAdjusmentWorkingDay.setDouble(FLD_SISTEM_WORK_HOURS, adjusmentWorkingDay.getSistemWorkHours());
                pstAdjusmentWorkingDay.setDouble(FLD_ADJUSMENT_WORKING_DAY, adjusmentWorkingDay.getAdjusmentWorkingDay());
                  
               

              
                
                pstAdjusmentWorkingDay.update();

                return adjusmentWorkingDay.getOID();
            }
        } 
        catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAdjusmentWorkingDay(0), DBException.UNKNOWN);
        }

        return 0;

    }

    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((AdjusmentWorkingDay)entity);
    }

    
 /**
 * Keterangan: delete data employee
 * @param oid
 * @return
 * @throws DBException 
 */
    public static Vector innerJoinList(int limitStart,int recordToGet,String employeeNum,String fullName,long oidRegency,long oidLocation,int command){
       Vector result =new Vector();
       DBResultSet dbrs=null;
       
       try{
           String sql="SELECT adj. *,emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+", loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+", loc."+PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID] + ", emp." +PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+ " FROM " 
                   + PstAdjusmentWorkingDay.TBL_HR_ADJUSMENT_WORKING_DAY + " AS adj INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON adj."+PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_EMPLOYEE_ID]+"= emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+
                   " INNER JOIN "+ PstLocation.TBL_P2_LOCATION+" AS loc ON loc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"= adj."+PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_LOCATION_ID] + " where (1=1) ";
                  
           //update by devin 2014-04-08
            if (employeeNum != null&& employeeNum.length()>0 && command!=Command.GOTO) {
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                        + " = " + "\"" + empNumId.trim() + "\"";//penambahan trima
                Vector vectNum = logicParser(employeeNum);
                sql = sql + " AND ";
                if (vectNum != null && vectNum.size() > 0) {
                    sql = sql + " (";
                    for (int i = 0; i < vectNum.size(); i++) {
                        String str = (String) vectNum.get(i); 
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
           }
           }
                    sql = sql + ")";
           }
                
            }
            //update by satrya 2012-07-16
            if (fullName != null &&  fullName.length()>0 && command!=Command.GOTO) { 
//                sql = sql + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                        + " LIKE " + "\"%" + fullName.trim() + "%\"";//penambahan trim
                Vector vectFullName = logicParser(fullName);
                  sql = sql + " AND ";
                if (vectFullName != null && vectFullName.size() > 0) {
                    sql = sql + " ( ";
                    for (int i = 0; i < vectFullName.size(); i++) {
                        String str = (String) vectFullName.get(i);
                        if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                            sql = sql + "emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                    + " LIKE '%" + str.trim() + "%' ";
                        } else {
                            sql = sql + str.trim();
                        }
                    }
                    sql = sql + " ) ";
                }
                 
            }
            if(oidRegency!=0 && command!=Command.GOTO){
           sql = sql + " AND loc."+PstLocation.fieldNames[PstLocation.FLD_SUB_REGENCY_ID]+" = '"+oidRegency+"'";
           }
             if(oidLocation!=0 && command!=Command.GOTO){
           sql = sql + " AND loc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+" = '"+oidLocation+"'";
           }
           if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        
           dbrs=DBHandler.execQueryResult(sql);
           ResultSet rs = dbrs.getResultSet();
           while(rs.next()){
               AdjusmentWorkingDay adjusmentWorkingDay = new AdjusmentWorkingDay();
               resultToObject(rs, adjusmentWorkingDay);
               adjusmentWorkingDay.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
               adjusmentWorkingDay.setLocationName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
               adjusmentWorkingDay.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM])); 
               result.add(adjusmentWorkingDay);
               
           }
       }catch(Exception exc){
           
       }finally{
           DBResultSet.close(dbrs);
           return result;
       }
        
    }
    
    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstAdjusmentWorkingDay pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(oid) {};

            pstAdjusmentWorkingDay.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstAdjusmentWorkingDay(0) {}, DBException.UNKNOWN);

        }

        return oid;

    }
    public long deleteExc(Entity entity) throws Exception {
        if(entity==null){
            throw  new DBException(this,DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }
    
    
/**
     * Ketrangan: fungsi untuk melakukan insert to database
     * @param ConfigRewardAndPunishment
     * @return
     * @throws DBException 
     */
    public static synchronized long insertExc(AdjusmentWorkingDay adjusmentWorkingDay) 
            throws DBException {
    try {

            PstAdjusmentWorkingDay pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(0);
            
            pstAdjusmentWorkingDay.setLong(FLD_EMPLOYEE_ID, adjusmentWorkingDay.getEmployeeId());
            pstAdjusmentWorkingDay.setLong(FLD_LOCATION_ID, adjusmentWorkingDay.getLocationId());
            pstAdjusmentWorkingDay.setDouble(FLD_SISTEM_WORK_HOURS, adjusmentWorkingDay.getSistemWorkHours());
            pstAdjusmentWorkingDay.setDouble(FLD_ADJUSMENT_WORKING_DAY, adjusmentWorkingDay.getAdjusmentWorkingDay());
           
            
            pstAdjusmentWorkingDay.insert();
            
            adjusmentWorkingDay.setOID(pstAdjusmentWorkingDay.getlong(FLD_ADJUSMENT_WORKING_DAY_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstAdjusmentWorkingDay(0) {}, DBException.UNKNOWN);
        }
        return adjusmentWorkingDay.getOID();
    }
    public long insertExc(Entity entity) throws Exception {
         return insertExc((AdjusmentWorkingDay)entity);
    }
    
     public static void resultToObject(ResultSet rs, AdjusmentWorkingDay adjusmentWorkingDay) {

        try {

adjusmentWorkingDay.setOID(rs.getLong(PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_ADJUSMENT_WORKING_DAY_ID]));
adjusmentWorkingDay.setEmployeeId(rs.getLong(PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_EMPLOYEE_ID]));
adjusmentWorkingDay.setLocationId(rs.getLong(PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_LOCATION_ID]));
adjusmentWorkingDay.setSistemWorkHours(rs.getDouble(PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_SISTEM_WORK_HOURS]));
adjusmentWorkingDay.setAdjusmentWorkingDay(rs.getDouble(PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_ADJUSMENT_WORKING_DAY]));


//set OID employee dari FLD_EMPLOYEE_ID


        } catch (Exception e) {
        }

    }
    /**
     * KETERANGAN: Fungsi untuk melakukan list table employee , berdasarkan parameter di bawah
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_HR_ADJUSMENT_WORKING_DAY;

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
                AdjusmentWorkingDay adjusmentWorkingDay = new AdjusmentWorkingDay();
                resultToObject(rs, adjusmentWorkingDay);
                lists.add(adjusmentWorkingDay);
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
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }
    
    public static boolean checkOID(long mSId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_ADJUSMENT_WORKING_DAY + " WHERE "
                    + PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_ADJUSMENT_WORKING_DAY_ID] + " = " + mSId;
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
    
    /**
     * keterangan : update nama by Id
     * create by: devin
     * tgl: 2013-11-21
     * @param ConfigRewardAndPunishment
     * @return 
     */
  

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstAdjusmentWorkingDay.fieldNames[PstAdjusmentWorkingDay.FLD_ADJUSMENT_WORKING_DAY_ID] 
                    + ") FROM " + TBL_HR_ADJUSMENT_WORKING_DAY;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstEmployee.fieldNames[PstEmployee.FLD_JENIS_ITEM_ID] 
            }
            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    /**
     * keterangan: limit
     * @param oid : ini merupakan oid jenis Item
     * @param recordToGet
     * @param whereClause
     * @param orderClause
     * @return 
     */
    public static int findLimitStart(long oid, int recordToGet
            , String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    AdjusmentWorkingDay jenisItems = (AdjusmentWorkingDay) list.get(ls);
                    if (oid == jenisItems.getOID()) {
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
    /* This method used to find command where current data */

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

