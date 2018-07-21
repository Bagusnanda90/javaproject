/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
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
public class PstConfigRewardAndPunishment extends  DBHandler  implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
   
    
    public static final String TBL_HR_CONFIG_REWARD_AND_PUNISHMENT="hr_config_reward_and_punishment";
    public static final int FLD_CONFIG_ID=0;
    public static final int FLD_MAX_DEDUCTION=1;
    public static final int FLD_PERSENTASE_TO_SALES=2;
    public static final int FLD_PERSENTASI_TO_BOD=3;
    public static final int FLD_DAY_NEW_EMPLOYEE_FREE=4;
    
   
    
      
    public static String[] fieldNames = {
        "CONFIG_ID",
        "MAX_DEDUCTION",
        "PRESENTASE_TO_SALES",
        "PRESENTASE_TO_BOD",
        "DAY_NEW_EMPLOYEE_FREE"
       
            
    };
   
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
    };

  
    
    public PstConfigRewardAndPunishment(){
	}

	public PstConfigRewardAndPunishment(int i) throws DBException { 
		super(new PstConfigRewardAndPunishment()); 
	}

    public PstConfigRewardAndPunishment(String sOid) throws DBException {

        super(new PstConfigRewardAndPunishment(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstConfigRewardAndPunishment(long lOid) throws DBException {

        super(new PstConfigRewardAndPunishment(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_CONFIG_REWARD_AND_PUNISHMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
       return fieldTypes;
    }

    public String getPersistentName() {
        return new PstConfigRewardAndPunishment().getClass().getName();
    }

       /**
     * Keterangan: untuk mengambil data dari 
     * database berdasarkan oid oidEmployee dan kemudaian di set objecknya
     * @param oid : oidEmployee
     * @return
     * @throws DBException 
     */
    public static ConfigRewardAndPunishment fetchExc(long oid) throws DBException {

        try {
            ConfigRewardAndPunishment configRewardAndPunishment = new ConfigRewardAndPunishment();
            PstConfigRewardAndPunishment pstConfigRewardAndPunishment = new PstConfigRewardAndPunishment(oid);
            configRewardAndPunishment.setOID(oid);
            configRewardAndPunishment.setMaxDeduction(pstConfigRewardAndPunishment.getdouble(FLD_MAX_DEDUCTION));
            configRewardAndPunishment.setPresentaseToSales(pstConfigRewardAndPunishment.getdouble(FLD_PERSENTASE_TO_SALES));
            configRewardAndPunishment.setPresentaseToBod(pstConfigRewardAndPunishment.getdouble(FLD_PERSENTASI_TO_BOD));
            configRewardAndPunishment.setDayNewEmployeeFree(pstConfigRewardAndPunishment.getInt(FLD_DAY_NEW_EMPLOYEE_FREE));
            
           
            
            return configRewardAndPunishment;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConfigRewardAndPunishment(0), DBException.UNKNOWN);
        }
    }
    public long fetchExc(Entity entity) throws Exception {
        ConfigRewardAndPunishment ConfigRewardAndPunishment = fetchExc(entity.getOID());
        entity = (Entity)ConfigRewardAndPunishment;
        return ConfigRewardAndPunishment.getOID();
    }
    
 /**
  * Keterangan: fungsi untuk update data to database
  * create by  satrya 2013-09-27
  * @param ConfigRewardAndPunishment
  * @return
  * @throws DBException 
  */
    public static synchronized long updateExc(ConfigRewardAndPunishment configRewardAndPunishment) throws DBException {
        try {
            if (configRewardAndPunishment.getOID() != 0) {
                PstConfigRewardAndPunishment pstConfigRewardAndPunishment = new PstConfigRewardAndPunishment(configRewardAndPunishment.getOID());
                pstConfigRewardAndPunishment.setDouble(FLD_MAX_DEDUCTION, configRewardAndPunishment.getMaxDeduction());
                pstConfigRewardAndPunishment.setDouble(FLD_PERSENTASE_TO_SALES, configRewardAndPunishment.getPresentaseToSales());
                pstConfigRewardAndPunishment.setDouble(FLD_PERSENTASI_TO_BOD, configRewardAndPunishment.getPresentaseToBod());
                pstConfigRewardAndPunishment.setInt(FLD_DAY_NEW_EMPLOYEE_FREE, configRewardAndPunishment.getDayNewEmployeeFree());

              
                
                pstConfigRewardAndPunishment.update();

                return configRewardAndPunishment.getOID();
            }
        } 
        catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConfigRewardAndPunishment(0), DBException.UNKNOWN);
        }

        return 0;

    }

    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((ConfigRewardAndPunishment)entity);
    }

    
 /**
 * Keterangan: delete data employee
 * @param oid
 * @return
 * @throws DBException 
 */
    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstConfigRewardAndPunishment pstConfigRewardAndPunishment = new PstConfigRewardAndPunishment(oid) {};

            pstConfigRewardAndPunishment.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstConfigRewardAndPunishment(0) {}, DBException.UNKNOWN);

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
    public static synchronized long insertExc(ConfigRewardAndPunishment configRewardAndPunishment) 
            throws DBException {
    try {

            PstConfigRewardAndPunishment pstConfigRewardAndPunishment = new PstConfigRewardAndPunishment(0);
            
            pstConfigRewardAndPunishment.setDouble(FLD_MAX_DEDUCTION, configRewardAndPunishment.getMaxDeduction());
            pstConfigRewardAndPunishment.setDouble(FLD_PERSENTASE_TO_SALES, configRewardAndPunishment.getPresentaseToSales());
            pstConfigRewardAndPunishment.setDouble(FLD_PERSENTASI_TO_BOD, configRewardAndPunishment.getPresentaseToBod());
            pstConfigRewardAndPunishment.setInt(FLD_DAY_NEW_EMPLOYEE_FREE, configRewardAndPunishment.getDayNewEmployeeFree());
            
            pstConfigRewardAndPunishment.insert();
            
            configRewardAndPunishment.setOID(pstConfigRewardAndPunishment.getlong(FLD_CONFIG_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConfigRewardAndPunishment(0) {}, DBException.UNKNOWN);
        }
        return configRewardAndPunishment.getOID();
    }
    public long insertExc(Entity entity) throws Exception {
         return insertExc((ConfigRewardAndPunishment)entity);
    }
    
     public static void resultToObject(ResultSet rs, ConfigRewardAndPunishment configRewardAndPunishment) {

        try {

configRewardAndPunishment.setOID(rs.getLong(PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_CONFIG_ID]));
configRewardAndPunishment.setMaxDeduction(rs.getDouble(PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_MAX_DEDUCTION]));
configRewardAndPunishment.setPresentaseToSales(rs.getDouble(PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_PERSENTASE_TO_SALES]));
configRewardAndPunishment.setPresentaseToBod(rs.getDouble(PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_PERSENTASI_TO_BOD]));
configRewardAndPunishment.setDayNewEmployeeFree(rs.getInt(PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_DAY_NEW_EMPLOYEE_FREE]));

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

            String sql = "SELECT * FROM " + TBL_HR_CONFIG_REWARD_AND_PUNISHMENT;

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
                ConfigRewardAndPunishment configRewardAndPunishment = new ConfigRewardAndPunishment();
                resultToObject(rs, configRewardAndPunishment);
                lists.add(configRewardAndPunishment);
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
            String sql = "SELECT * FROM " + TBL_HR_CONFIG_REWARD_AND_PUNISHMENT + " WHERE "
                    + PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_CONFIG_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstConfigRewardAndPunishment.fieldNames[PstConfigRewardAndPunishment.FLD_CONFIG_ID] 
                    + ") FROM " + TBL_HR_CONFIG_REWARD_AND_PUNISHMENT;
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
    
    public static int getProsentaseToSales(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT `PRESENTASE_TO_SALES` FROM `hr_config_reward_and_punishment` ORDER BY `CONFIG_ID` DESC LIMIT 0,1";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int persentase = 0;
            while (rs.next()) {
                persentase = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstEmployee.fieldNames[PstEmployee.FLD_JENIS_ITEM_ID] 
            }
            rs.close();
            return persentase;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
     public static int getdayfree(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT `DAY_NEW_EMPLOYEE_FREE` FROM `hr_config_reward_and_punishment` ORDER BY `CONFIG_ID` DESC LIMIT 0,1";
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            dbrs = DBHandler.execQueryResult(sql);//execute query sql
            ResultSet rs = dbrs.getResultSet();
            int dayfree = 0;
            while (rs.next()) {
                dayfree = rs.getInt(1);//ambil isi ResultSet yg 1 atau PstEmployee.fieldNames[PstEmployee.FLD_JENIS_ITEM_ID] 
            }
            rs.close();
            return dayfree;
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
                    ConfigRewardAndPunishment jenisItems = (ConfigRewardAndPunishment) list.get(ls);
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

