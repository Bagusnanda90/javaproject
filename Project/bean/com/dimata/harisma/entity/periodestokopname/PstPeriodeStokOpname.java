/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.periodestokopname;

import com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname;
import com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Devin
 */
public class PstPeriodeStokOpname  extends  DBHandler  implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
     public static final String TBL_HR_PERIOD_STOK_OPNAME="hr_period_stock_opname";
    public static final int FLD_PERIOD_OPNAME_ID=0;
    public static final int FLD_NAME_PERIOD=1;
   public static final int FLD_DATE_FROM=2;
   public static final int FLD_DATE_TO=3;
    
   
    
      
    public static String[] fieldNames = {
        "PERIOD_OPNAME_ID",
        "NAME_PERIOD",
        "DATE_FROM",
        "DATE_TO"
        
      
       
            
    };
   
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE
        
       
        
       
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
   
    public PstPeriodeStokOpname() {
    }

    public PstPeriodeStokOpname(int i) throws DBException {

        super(new PstPeriodeStokOpname());  


    }

    public PstPeriodeStokOpname(String sOid) throws DBException {

        super(new PstPeriodeStokOpname(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstPeriodeStokOpname(long lOid) throws DBException {

        super(new PstPeriodeStokOpname(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_PERIOD_STOK_OPNAME;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
       return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPeriodeStokOpname().getClass().getName();
    }

       /**
     * Keterangan: untuk mengambil data dari 
     * database berdasarkan oid oidEmployee dan kemudaian di set objecknya
     * @param oid : oidEmployee
     * @return
     * @throws DBException 
     */
    public static PeriodeStokOpname fetchExc(long oid) throws DBException {

        try {
            PeriodeStokOpname periodeStokOpname = new PeriodeStokOpname();
            PstPeriodeStokOpname pstPeriodeStokOpname = new PstPeriodeStokOpname(oid);
            periodeStokOpname.setOID(oid);
            periodeStokOpname.setNamePeriod(pstPeriodeStokOpname.getString(FLD_NAME_PERIOD));
            periodeStokOpname.setStartDate(pstPeriodeStokOpname.getDate(FLD_DATE_FROM));
            periodeStokOpname.setEndDate(pstPeriodeStokOpname.getDate(FLD_DATE_TO));
            
           
            
            return periodeStokOpname;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriodeStokOpname(0), DBException.UNKNOWN);
        }
    }
    public long fetchExc(Entity entity) throws Exception {
         PeriodeStokOpname periodeStokOpname  = fetchExc(entity.getOID());
        entity = (Entity)periodeStokOpname;
        return periodeStokOpname.getOID();
    }
    
 /**
  * Keterangan: fungsi untuk update data to database
  * create by  satrya 2013-09-27
  * @param ConfigRewardAndPunishment
  * @return
  * @throws DBException 
  */
    public static synchronized long updateExc(PeriodeStokOpname periodeStokOpname) throws DBException {
        try {
            if (periodeStokOpname.getOID() != 0) {
                PstPeriodeStokOpname pstPeriodeStokOpname = new PstPeriodeStokOpname(periodeStokOpname.getOID()) {};
                pstPeriodeStokOpname.setString(FLD_NAME_PERIOD, periodeStokOpname.getNamePeriod());
                pstPeriodeStokOpname.setDate(FLD_DATE_FROM, periodeStokOpname.getStartDate());
                pstPeriodeStokOpname.setDate(FLD_DATE_TO, periodeStokOpname.getEndDate());
               
                
                  
               

              
                
                pstPeriodeStokOpname.update();

                return periodeStokOpname.getOID();
            }
        } 
        catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriodeStokOpname(0), DBException.UNKNOWN);
        }

        return 0;
        
    }

    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((PeriodeStokOpname)entity);
    }

    

    
    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstPeriodeStokOpname pstPeriodeStokOpname = new PstPeriodeStokOpname(oid) {};

            pstPeriodeStokOpname.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPeriodeStokOpname(0) {}, DBException.UNKNOWN);

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
    public static synchronized long insertExc(PeriodeStokOpname periodeStokOpname) 
            throws DBException {
    try {
       
        
            PstPeriodeStokOpname pstPeriodeStokOpname = new PstPeriodeStokOpname(0);
            
            pstPeriodeStokOpname.setString(FLD_NAME_PERIOD, periodeStokOpname.getNamePeriod());
            pstPeriodeStokOpname.setDate(FLD_DATE_FROM, periodeStokOpname.getStartDate());
             pstPeriodeStokOpname.setDate(FLD_DATE_TO, periodeStokOpname.getEndDate());
           
            
            pstPeriodeStokOpname.insert();
            
            periodeStokOpname.setOID(pstPeriodeStokOpname.getlong(FLD_PERIOD_OPNAME_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPeriodeStokOpname(0) {}, DBException.UNKNOWN);
        }
        return periodeStokOpname.getOID();
    }
    public long insertExc(Entity entity) throws Exception {
         return insertExc((PeriodeStokOpname)entity);
    }
    
     public static void resultToObject(ResultSet rs, PeriodeStokOpname periodeStokOpname) {

        try {

periodeStokOpname.setOID(rs.getLong(PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_PERIOD_OPNAME_ID]));
periodeStokOpname.setNamePeriod(rs.getString(PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_NAME_PERIOD]));
periodeStokOpname.setStartDate(rs.getDate(PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_DATE_FROM]));
periodeStokOpname.setEndDate(rs.getDate(PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_DATE_TO]));



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

            String sql = "SELECT * FROM " + TBL_HR_PERIOD_STOK_OPNAME;

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
                PeriodeStokOpname periodeStokOpname = new PeriodeStokOpname();
                resultToObject(rs, periodeStokOpname);
                lists.add(periodeStokOpname);
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
            String sql = "SELECT * FROM " + TBL_HR_PERIOD_STOK_OPNAME + " WHERE "
                    + PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_PERIOD_OPNAME_ID] + " = " + mSId;
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
            String sql = "SELECT COUNT(" + PstPeriodeStokOpname.fieldNames[PstPeriodeStokOpname.FLD_PERIOD_OPNAME_ID] 
                    + ") FROM " + TBL_HR_PERIOD_STOK_OPNAME;
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
                    PeriodeStokOpname jenisItems = (PeriodeStokOpname) list.get(ls);
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
