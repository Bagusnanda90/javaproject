/**
 * User: wardana
 * Date: Apr 8, 2004
 * Time: 1:00:51 PM
 * Version: 1.0
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.LeaveApplication;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.PstLeavePeriod;
import com.dimata.harisma.entity.masterdata.LeavePeriod;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.system.entity.PstSystemProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.Date;
import java.util.Hashtable;

public class PstDpStockManagement extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
    
    public static final String TBL_DP_STOCK_MANAGEMENT = "hr_dp_stock_management";//HR_DP_STOCK_MANAGEMENT";
    
    public static final int FLD_DP_STOCK_ID = 0;
    public static final int FLD_LEAVE_PERIODE_ID = 1;
    public static final int FLD_DP_QTY = 2;
    public static final int FLD_OWNING_DATE = 3;
    public static final int FLD_EXPIRED_DATE = 4;
    public static final int FLD_EXCEPTION_FLAG = 5;
    public static final int FLD_EXPIRED_DATE_EXC = 6;
    public static final int FLD_DP_STATUS = 7;
    public static final int FLD_NOTE = 8;
    public static final int FLD_QTY_RESIDUE = 9;
    public static final int FLD_QTY_USED = 10;
    public static final int FLD_EMPLOYEE_ID = 11;
    //public static final int FLD_START_DATE = 12;
    public static final int FLD_FLAG_STOCK=12;
    
    public static final String[] fieldNames = {
        "DP_STOCK_ID", //0
        "LEAVE_PERIODE_ID", // digunakan untuk setting sumber perolehan DP, jika dari Overtime ,maka di set = nilai OID overtime detail, jika dari public holiday maka di set = pulick holiday id, jika dari bithday di set dari employee id
        "DP_QTY",
        "OWNING_DATE",
        "EXPIRED_DATE",
        "EXCEPTION_FLAG", //5
        "EXPIRED_DATE_EXC",
        "DP_STATUS",
        "NOTE",
        "QTY_RESIDUE",
        "QTY_USED", //10
        "EMPLOYEE_ID",
        //"START_DATE"
        "FLAG_STOCK"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID, //0
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT, //5
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_FLOAT, //10
        TYPE_LONG,
        //TYPE_DATE
        TYPE_INT
    };
    
    public static int DP_QTY_COUNT = 2;             // JUMLAH DP YANG DI DAPAT JIKA PUBLIC HOLIDAY MASUK
    public static int DP_EXPIRED_COUNT = 4;         // RENTANG BATAS WAKTU DP BERAKHIR
    public static int DP_QTY_COUNT_FROM_OFF = 1;    // JUMLAH DP YANG DI DAPAT BILA HARI MEMANG OFF TAPI MASUK PAS PH.
    public static int DP_QTY_COUNT_ON_EOD = 1;      // JUMLAH DP YANG DI DAPAT BILA MASUK DENGAN SCHEDULE EOD
    
    public static int EXC_STS_NO = 0; // TIDAK ADA EXCEPTION
    public static int EXC_STS_YES = 1; // ADA EXCEPTION
    
    public static int DP_STS_AKTIF = 0; // DP BISA DI AMBIL
    public static int DP_STS_NOT_AKTIF = 1; // DP BELUM BISA DI AMBIL
    public static int DP_STS_TAKEN = 2; // DP SUDAH HABIS
    public static int DP_STS_EXPIRED = 3; // DP MASA BERLAKUNYA SUDAH BERAKHIR
    
    //update by satrya 2013-02-24
    ///logikanya di balik agar tidak berubah semuanya
    public static int DP_FLAG_EDIT_YES=0;// artinya jika user telah menambahkan dp_stock melalui overtime, lalu user merubahnya lagi lewat dp_stock_management, kemudian lagi prosess overtime tsb agar nilainya dp_stock tidak berubah
   public static int DP_FLAG_EDIT_NO=1;//ini gunanya generate by overtrime
   public static final String[] fieldFlag={
       "YES",
       "NO"
   };
    public static final String[] fieldStatus = {
        "ACTIVE",
        "NON ACTIVE",
        "TAKEN",
        "EXPIRED"
    };
   
    
    public PstDpStockManagement() {
    }
    
    public PstDpStockManagement(int i) throws DBException {
        super(new PstDpStockManagement());
    }
    
    public PstDpStockManagement(String sOid) throws DBException {
        super(new PstDpStockManagement(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstDpStockManagement(long lOid) throws DBException {
        super(new PstDpStockManagement(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_DP_STOCK_MANAGEMENT;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstDpStockManagement().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        DpStockManagement objDpStockMgn = fetchExc(ent.getOID());
        return objDpStockMgn.getOID();
    }
    
    public static DpStockManagement fetchExc(long oid) throws DBException {
        try {
            
            DpStockManagement objDpStockMgn = new DpStockManagement();
            PstDpStockManagement objPstDpStockMgn = new PstDpStockManagement(oid);
            objDpStockMgn.setOID(oid);
            
            objDpStockMgn.setLeavePeriodeId(objPstDpStockMgn.getlong(FLD_LEAVE_PERIODE_ID));
            objDpStockMgn.setEmployeeId(objPstDpStockMgn.getlong(FLD_EMPLOYEE_ID));
            objDpStockMgn.setiDpQty(objPstDpStockMgn.getfloat(FLD_DP_QTY));
            objDpStockMgn.setDtOwningDate(objPstDpStockMgn.getDate(FLD_OWNING_DATE));
            objDpStockMgn.setDtExpiredDate(objPstDpStockMgn.getDate(FLD_EXPIRED_DATE));
            objDpStockMgn.setiExceptionFlag(objPstDpStockMgn.getInt(FLD_EXCEPTION_FLAG));
            objDpStockMgn.setDtExpiredDateExc(objPstDpStockMgn.getDate(FLD_EXPIRED_DATE_EXC));
            objDpStockMgn.setiDpStatus(objPstDpStockMgn.getInt(FLD_DP_STATUS));
            objDpStockMgn.setStNote(objPstDpStockMgn.getString(FLD_NOTE));
            objDpStockMgn.setQtyResidue(objPstDpStockMgn.getfloat(FLD_QTY_RESIDUE));
            objDpStockMgn.setQtyUsed(objPstDpStockMgn.getfloat(FLD_QTY_USED));
            //objDpStockMgn.setDtStartDate(objPstDpStockMgn.getDate(FLD_START_DATE));
            objDpStockMgn.setFlagStock(objPstDpStockMgn.getInt(FLD_FLAG_STOCK));
            return objDpStockMgn;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockManagement(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((DpStockManagement) ent);
    }

    public static long insertOrUpdateByPeriodId(DpStockManagement objDpStockMgn) throws DBException {
        if(objDpStockMgn!=null && objDpStockMgn.getLeavePeriodeId()!=0 ){
            String where = " ( "+fieldNames[FLD_LEAVE_PERIODE_ID]+"=\""+objDpStockMgn.getLeavePeriodeId()+"\" " 
                           //update by satrya
                    //di asumsikan Acquisition Date/owning date tidak boleh sama
                   +" OR "+ fieldNames[FLD_OWNING_DATE]+"=\""+Formater.formatDate(objDpStockMgn.getDtOwningDate(), "yyyy-MM-dd")+"\" ) "
                    + "AND "+
                    fieldNames[FLD_EMPLOYEE_ID]+"=\""+objDpStockMgn.getEmployeeId()+"\"  "
             
                   // + " AND "+ fieldNames[FLD_FLAG_STOCK]+"!=\""+DP_FLAG_EDIT+"\"  "
                    ;
            Vector vDp = list(0,2,where,"");
            long oid=0;
            if(vDp!=null && vDp.size()>0){
              DpStockManagement dpStock =(DpStockManagement) vDp.get(0);
              if(dpStock.getFlagStock()==DP_FLAG_EDIT_NO){
                objDpStockMgn.setOID(dpStock.getOID());
                oid=updateExc(objDpStockMgn);
              }
            } else{
              oid=insertExc(objDpStockMgn);
            }
            return oid;
        }
        return 0;
    }
/**
 * Fungsi untuk menghitung DP
 * @param objDpStockMgn
 * @param hashCekAccDate
 * @param hashCekDateTimeRealStart : dari overtime Real Start
 * @return
 * @throws DBException 
 */
public static long insertOrUpdateByPeriodIdVer2(DpStockManagement objDpStockMgn,Hashtable hashCekAccDate) throws DBException {
        if(objDpStockMgn!=null && objDpStockMgn.getLeavePeriodeId()!=0 ){
            String where = " ( "+fieldNames[FLD_LEAVE_PERIODE_ID]+"=\""+objDpStockMgn.getLeavePeriodeId()+"\" " 
                           //update by satrya
                    //di asumsikan Acquisition Date/owning date tidak boleh sama
                   +" OR "+ fieldNames[FLD_OWNING_DATE]+"=\""+Formater.formatDate(objDpStockMgn.getDtOwningDate(), "yyyy-MM-dd")+"\" ) "
                    + "AND "+
                    fieldNames[FLD_EMPLOYEE_ID]+"=\""+objDpStockMgn.getEmployeeId()+"\"  ";
                    //update by satrya 2013-11-06
                    // ini berfungsi untuk accosision date yg sama tapi overtime detailnya berbeda
                 if(objDpStockMgn.getLeavePeriodeId()!=0){
                    where = where + "AND "+
                    fieldNames[FLD_LEAVE_PERIODE_ID]+"=\""+objDpStockMgn.getLeavePeriodeId()+"\"  ";
                 }
                   // + " AND "+ fieldNames[FLD_FLAG_STOCK]+"!=\""+DP_FLAG_EDIT+"\"  "
                    
            Vector vDp = list(0,2,where,"");
            long oid=0;
            if(vDp!=null && vDp.size()>0){
                //update by satrya 2013-11-06 ini smtara di hidden if(vDp!=null && vDp.size()>0){//membandingkan dengan objDpStockMgn.getLeavePeriodeId() yg artinya sama dengan overtime detail
              DpStockManagement dpStock =(DpStockManagement) vDp.get(0);
              if(objDpStockMgn.getLeavePeriodeId() == dpStock.getLeavePeriodeId()){
                        if(dpStock.getFlagStock()==DP_FLAG_EDIT_NO){
                      //update by satrya 2013-05-06
                      if(dpStock.getQtyUsed()==0){
                        objDpStockMgn.setOID(dpStock.getOID());
                        //update by satrya 2013-11-06
                        //if(hashCekAccDate.size()==0 || hashCekAccDate.containsKey(objDpStockMgn.getOID())){
                        objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() /* + (hashCekAccDate!=null && hashCekAccDate.size()>0 ?(Float)hashCekAccDate.get(objDpStockMgn.getOID()):0)*/);
                        //hashCekAccDate.put(objDpStockMgn.getOID(), objDpStockMgn.getiDpQty());
                        oid=updateExc(objDpStockMgn);
                          //hashCekAccDate.put(oid,objDpStockMgn.getLeavePeriodeId());
                        //}
                      }
                    }
              }
              /*update by satrya 2013-11-14 else{
                  if(dpStock.getFlagStock()==DP_FLAG_EDIT_NO){
                         oid=insertExc(objDpStockMgn);
                         //hashCekAccDate.put(oid,objDpStockMgn.getLeavePeriodeId());
                  }
              }*/
              
            } else{
              oid=insertExc(objDpStockMgn);
                hashCekAccDate.put(oid,objDpStockMgn.getLeavePeriodeId());
            }
            return oid;
        }
        return 0;
    }

public static long insertOrUpdateByPeriodIdVer3(DpStockManagement objDpStockMgn,Hashtable hashCekAccDate) throws DBException {
        if(objDpStockMgn!=null && objDpStockMgn.getLeavePeriodeId()!=0 ){
            String where = " ( "+fieldNames[FLD_LEAVE_PERIODE_ID]+"=\""+objDpStockMgn.getLeavePeriodeId()+"\" " 
                           //update by satrya
                    //di asumsikan Acquisition Date/owning date tidak boleh sama
                   +" OR "+ fieldNames[FLD_OWNING_DATE]+"=\""+Formater.formatDate(objDpStockMgn.getDtOwningDate(), "yyyy-MM-dd")+"\" ) "
                    + "AND "+
                    fieldNames[FLD_EMPLOYEE_ID]+"=\""+objDpStockMgn.getEmployeeId()+"\"  "
             
                   // + " AND "+ fieldNames[FLD_FLAG_STOCK]+"!=\""+DP_FLAG_EDIT+"\"  "
                    ;
            Vector vDp = list(0,2,where,"");
            long oid=0;
            if(vDp!=null && vDp.size()>0){
              DpStockManagement dpStock =(DpStockManagement) vDp.get(0);
              if(dpStock.getFlagStock()==DP_FLAG_EDIT_NO){
                //update by satrya 2013-05-06
              if(dpStock.getQtyUsed()==0){
                objDpStockMgn.setOID(dpStock.getOID());
                if(hashCekAccDate.size()==0 || hashCekAccDate.containsKey(objDpStockMgn.getOID())){
                objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() + (hashCekAccDate!=null && hashCekAccDate.size()>0 ?(Float)hashCekAccDate.get(objDpStockMgn.getOID()):0));
                hashCekAccDate.put(objDpStockMgn.getOID(), objDpStockMgn.getiDpQty());
                oid=updateExc(objDpStockMgn);
                
                }
              }
              }
            } else{
              oid=insertExc(objDpStockMgn);
            }
            return oid;
        }
        return 0;
    }
    public static long deleteByPeriodId(DpStockManagement objDpStockMgn) throws DBException {
        if(objDpStockMgn!=null && objDpStockMgn.getLeavePeriodeId()!=0 ){
            String where = fieldNames[FLD_LEAVE_PERIODE_ID]+"=\""+objDpStockMgn.getLeavePeriodeId()+"\" AND "+
                    fieldNames[FLD_EMPLOYEE_ID]+"=\""+objDpStockMgn.getEmployeeId()+"\"  ";
            Vector vDp = list(0,2,where,"");
            long oid=0;
            if(vDp!=null && vDp.size()>0){
              DpStockManagement dpStock =(DpStockManagement) vDp.get(0);
                //jika ada DP yg di gunakan maka empTime Tidak di set
                //update by satrya 2012-12-20
                if(PstDpStockTaken.isNotDpUse(dpStock.getOID())){
                    oid=deleteExc(dpStock.getOID());
                }else{
                    oid = -1;// jika dp sedang di gunakan maka oid di set -1
                }
            } 
            return oid;
        }
        return 0;
    }
    /**
     * Create by satrya 2013-01-03
     * Keterangan Untuk melakukan Update Qty Residue dan Qty Use, guna melakukan balancing
     * @param dpStockManagement 
     */
  public synchronized static long updateQtyUsedResidue( DpStockManagement dpStockManagement) {

        DBResultSet dbrs = null;
        try {
            String sql = " UPDATE  "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT
                    + " SET "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]+"="+dpStockManagement.getQtyUsed()+ ", "
                    + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]+"="+dpStockManagement.getQtyResidue();
                    if(dpStockManagement.getQtyResidue()==0){
                        sql = sql + ","+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"="+PstDpStockManagement.DP_STS_TAKEN;
                    }
                    sql = sql +" WHERE "
                    + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+"="+dpStockManagement.getOID();
                   

            //System.out.println("SQL update doc status " + sql);

            int i = DBHandler.execUpdate(sql);

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return 0;
        }
        return dpStockManagement.getOID();

    }  
    public static long updateExc(DpStockManagement objDpStockMgn) throws DBException {
        try {
            if (objDpStockMgn.getOID() != 0) {
                PstDpStockManagement objPstDpStockMgn = new PstDpStockManagement(objDpStockMgn.getOID());
                
                objPstDpStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objDpStockMgn.getLeavePeriodeId());
                objPstDpStockMgn.setLong(FLD_EMPLOYEE_ID, objDpStockMgn.getEmployeeId());
                objPstDpStockMgn.setFloat(FLD_DP_QTY, objDpStockMgn.getiDpQty());
                objPstDpStockMgn.setDate(FLD_OWNING_DATE, objDpStockMgn.getDtOwningDate());
                objPstDpStockMgn.setDate(FLD_EXPIRED_DATE, objDpStockMgn.getDtExpiredDate());
                objPstDpStockMgn.setInt(FLD_EXCEPTION_FLAG, objDpStockMgn.getiExceptionFlag());
                objPstDpStockMgn.setDate(FLD_EXPIRED_DATE_EXC, objDpStockMgn.getDtExpiredDateExc());
                objPstDpStockMgn.setInt(FLD_DP_STATUS, objDpStockMgn.getiDpStatus());
                objPstDpStockMgn.setString(FLD_NOTE, objDpStockMgn.getStNote());
                // new
                objPstDpStockMgn.setFloat(FLD_QTY_RESIDUE, objDpStockMgn.getQtyResidue());
                objPstDpStockMgn.setFloat(FLD_QTY_USED, objDpStockMgn.getQtyUsed());
                //objPstDpStockMgn.setDate(FLD_START_DATE, objDpStockMgn.getDtStartDate());
                //update by satrya 2013-02-24
                objPstDpStockMgn.setInt(FLD_FLAG_STOCK, objDpStockMgn.getFlagStock());
                objPstDpStockMgn.update();
                return objDpStockMgn.getOID();
                
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockManagement(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstDpStockManagement objPstDpStockMgn = new PstDpStockManagement(oid);
            objPstDpStockMgn.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockManagement(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((DpStockManagement)ent);
    }
    
    public static long insertExc(DpStockManagement objDpStockMgn) throws DBException {
        try {
            PstDpStockManagement objPstDpStockMgn = new PstDpStockManagement(0);
            
            objPstDpStockMgn.setLong(FLD_LEAVE_PERIODE_ID, objDpStockMgn.getLeavePeriodeId());
            objPstDpStockMgn.setLong(FLD_EMPLOYEE_ID, objDpStockMgn.getEmployeeId());
            objPstDpStockMgn.setFloat(FLD_DP_QTY, objDpStockMgn.getiDpQty());
            objPstDpStockMgn.setDate(FLD_OWNING_DATE, objDpStockMgn.getDtOwningDate());
            objPstDpStockMgn.setDate(FLD_EXPIRED_DATE, objDpStockMgn.getDtExpiredDate());
            objPstDpStockMgn.setInt(FLD_EXCEPTION_FLAG, objDpStockMgn.getiExceptionFlag());
            objPstDpStockMgn.setDate(FLD_EXPIRED_DATE_EXC, objDpStockMgn.getDtExpiredDateExc());
            objPstDpStockMgn.setInt(FLD_DP_STATUS, objDpStockMgn.getiDpStatus());
            objPstDpStockMgn.setString(FLD_NOTE, objDpStockMgn.getStNote());
            objPstDpStockMgn.setFloat(FLD_QTY_RESIDUE, objDpStockMgn.getQtyResidue());
            objPstDpStockMgn.setFloat(FLD_QTY_USED, objDpStockMgn.getQtyUsed());
            //update by satrya 2013-02-24
            objPstDpStockMgn.setInt(FLD_FLAG_STOCK, objDpStockMgn.getFlagStock());
            objPstDpStockMgn.insert();
            objDpStockMgn.setOID(objPstDpStockMgn.getlong(FLD_DP_STOCK_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstDpStockManagement(0), DBException.UNKNOWN);
        }
        return objDpStockMgn.getOID();
    }
    
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param oidPeriod
     * @param oidDepatment
     * @param groupOrder
     * @return
     * @created by gadnyana
     * @documented by edhy
     */    
    public static Vector list(int limitStart, int recordToGet, long oidPeriod, long oidDepatment, String groupOrder) 
    {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try 
        {
           String sql = "SELECT DP." +fieldNames[FLD_DP_STOCK_ID]+
                        ", DP."+fieldNames[FLD_EMPLOYEE_ID]+
                        ", DP."+fieldNames[FLD_LEAVE_PERIODE_ID]+
                        ", DP."+fieldNames[FLD_OWNING_DATE]+
                        ", DP."+fieldNames[FLD_DP_QTY]+
                        ", DP."+fieldNames[FLD_QTY_RESIDUE]+
                        ", DP."+fieldNames[FLD_QTY_USED]+
                        ", DP."+fieldNames[FLD_DP_STATUS]+
                        ", DP."+fieldNames[FLD_NOTE]+
                        ", DP."+fieldNames[FLD_EXPIRED_DATE]+
                        ", DP."+fieldNames[FLD_EXPIRED_DATE_EXC]+
                        ", DP."+fieldNames[FLD_EXCEPTION_FLAG]+
                        ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+
                        ", EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+
                        ", LP."+PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]+
                        " FROM " + TBL_DP_STOCK_MANAGEMENT+ " AS DP " +
                        " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " +
                        " ON DP." + fieldNames[FLD_EMPLOYEE_ID] + 
                        " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                        " INNER JOIN " + PstLeavePeriod.TBL_HR_LEAVE_PERIOD + " AS LP " + 
                        " ON DP." + fieldNames[FLD_LEAVE_PERIODE_ID] + 
                        " = LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] +
                        " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                        " = " + oidDepatment +
                        " AND LP." + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] + 
                        " = " + oidPeriod;
           
           if(groupOrder!=null && groupOrder.length()>0)
           {
                sql = sql + groupOrder;
           }
            
            if (recordToGet != 0)
            {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            
            System.out.println("sQl = "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                Vector ls = new Vector(1,1);
                DpStockManagement dpStockManagement = new DpStockManagement();
                Employee emp = new Employee();
                LeavePeriod lp = new LeavePeriod();
                resultToObject(rs, dpStockManagement);
                ls.add(dpStockManagement);
                
                emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                ls.add(emp);
                
                lp.setStartDate(rs.getDate(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]));
                ls.add(lp);
                
                lists.add(ls);
            }
            rs.close();
            return lists;
            
        }
        catch (Exception e) 
        {
            System.out.println(e);
        } 
        finally 
        {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */    
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DP_STOCK_MANAGEMENT;
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
                DpStockManagement dpStockManagement = new DpStockManagement();
                resultToObject(rs, dpStockManagement);
                lists.add(dpStockManagement);
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
     * Keterangan : di gunakan untuk cek Ada Stock atau tidak
     * create by satrya 2013-01-13
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listDPStock(int limitStart,int recordToGet, String whereClause, String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_DP_STOCK_MANAGEMENT + " SM ";
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
                DpStockManagement dpStockManagement = new DpStockManagement();
                resultToObject(rs, dpStockManagement);
                lists.add(dpStockManagement);
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
     *  //create by satrya 2013-01-03
     * @param whereClause
     * @return 
     */
     public static Vector listByEmpNumb(String whereClause,String order){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = " SELECT "
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]+","
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]+","
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+","
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+","
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]+","
            + " DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]
            +" from "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT + " AS DSM "
            + " inner join "+PstEmployee.TBL_HR_EMPLOYEE 
            + " as EMP on (DSM."+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]
            + " =EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+")";
               
            
            if(whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;
            if(order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;
            
            dbrs = DBHandler.execQueryResult(sql);
            //System.out.println("querynya"+sql);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpStockManagement dpStockManagement = new DpStockManagement();
                //resultToObject(rs, dpStockManagement);
                dpStockManagement.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
                dpStockManagement.setiDpQty(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
                dpStockManagement.setQtyResidue(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
                dpStockManagement.setQtyUsed(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
                dpStockManagement.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
                dpStockManagement.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
                lists.add(dpStockManagement);
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
    
    private static void resultToObject(ResultSet rs, DpStockManagement objDpStockMgn) {
        try {
            objDpStockMgn.setOID(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID]));
            objDpStockMgn.setLeavePeriodeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID]));
            objDpStockMgn.setEmployeeId(rs.getLong(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]));
            objDpStockMgn.setiDpQty(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY]));
            objDpStockMgn.setDtOwningDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]));
            objDpStockMgn.setDtExpiredDate(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]));
            objDpStockMgn.setiExceptionFlag(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG]));
            objDpStockMgn.setDtExpiredDateExc(rs.getDate(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]));
            objDpStockMgn.setiDpStatus(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]));
            objDpStockMgn.setStNote(rs.getString(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE]));
            // new
            objDpStockMgn.setQtyResidue(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE]));
            objDpStockMgn.setQtyUsed(rs.getFloat(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED]));
            //update by satrya 2013-02-24
            objDpStockMgn.setFlagStock(rs.getInt(PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_FLAG_STOCK]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return
     */    
    public static Vector listDpStockPerEmp(long employeeId, Date selectedDate)
    {
        Vector result = new Vector(1,1);
        
        String whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                             " = " + employeeId + 
                             " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                             " = \"" + Formater.formatDate(selectedDate,"yyyy-MM-dd 00:00:00") + "\"";
        String orderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector listDpStock = PstDpStockManagement.list(0, 0, whereClause, orderBy);
        if(listDpStock!=null && listDpStock.size()>0)
        {
            int maxDpStockList = listDpStock.size();
            for(int i=0; i<maxDpStockList; i++)
            {
                DpStockManagement dpStockManagement = (DpStockManagement) listDpStock.get(i);
                
                if(dpStockManagement.getiDpQty()>1)
                {
                    float maxDpStock = dpStockManagement.getiDpQty();
                    for(int j=0; j<maxDpStock; j++)
                    {
                        dpStockManagement.setiDpQty(1);
                        dpStockManagement.setQtyUsed(0);
                        dpStockManagement.setQtyResidue(1);
                        result.add(dpStockManagement);
                    }
                }
                else
                {
                    dpStockManagement.setiDpQty(1);
                    dpStockManagement.setQtyUsed(0);
                    dpStockManagement.setQtyResidue(1);
                    result.add(dpStockManagement);                    
                }
            }
        }        
        
        return result;   
    }

    
    public static Vector listDpByExpDate(Date dtExpDate){
        Vector vList = new Vector();
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_DP_STOCK_MANAGEMENT +
        " WHERE ("+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE]+ " = '"+Formater.formatDate(dtExpDate, "yyyy-MM-dd") + "' " +
        " OR "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC]+ " = '"+Formater.formatDate(dtExpDate, "yyyy-MM-dd") + "') " +
        " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + " = " +PstDpStockManagement.DP_STS_AKTIF;
        try{
            System.out.println("Sql DP Expired = " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            DpStockManagement objDpStockMgn = new DpStockManagement();
            while(rs.next()){
                objDpStockMgn = new DpStockManagement();
                resultToObject(rs, objDpStockMgn);
                vList.add(objDpStockMgn);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return vList;
    }
    
    /** gadnyana
     * untuk mengambil data stock dp semua employee
     * dengan parameter periode id
     * @param oidEmployee
     * @return
     */
    public static Vector listDpStock(long oidEmployee){
        Vector vList = new Vector();
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_DP_STOCK_MANAGEMENT +
        " WHERE "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
        try{
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            DpStockManagement objDpStockMgn = new DpStockManagement();
            while(rs.next()){
                objDpStockMgn = new DpStockManagement();
                resultToObject(rs, objDpStockMgn);
                vList.add(objDpStockMgn);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return vList;
    }
    

    public static DpStockManagement getDpStockManagement(long oidLeavePeriode, long iOidEmployee){
        DpStockManagement objDpStockMgn = new DpStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT * FROM "+ TBL_DP_STOCK_MANAGEMENT +
        " WHERE "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID]+ " = "+oidLeavePeriode+
        " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+ " = "+iOidEmployee;
        try{
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                objDpStockMgn = new DpStockManagement();
                resultToObject(rs, objDpStockMgn);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return objDpStockMgn;
    }
    
    /**
     * @param owningDate
     * @param iOidEmployee
     * @return
     * @created by Edhy
     */    
    public static DpStockManagement getDpStockManagement(Date owningDate, long iOidEmployee)
    {
        DpStockManagement objDpStockMgn = new DpStockManagement();
        DBResultSet dbrs;
        String strOwningDate = "\"" + Formater.formatDate(owningDate, "yyyy-MM-dd") + "\"";        
        String stSQL = " SELECT * FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+ 
                       " = "+strOwningDate+
                       " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+ 
                       " = "+iOidEmployee;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next())
            {
                objDpStockMgn = new DpStockManagement();
                resultToObject(rs, objDpStockMgn);
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return objDpStockMgn;
    }

    public static DpStockManagement getDpStockManagement(long oidLeavePeriode, long iOidEmployee, Date dt){
        DpStockManagement objDpStockMgn = new DpStockManagement();
        DBResultSet dbrs;   
        String stSQL = " SELECT * FROM "+ TBL_DP_STOCK_MANAGEMENT +
//        " WHERE "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID]+ " = "+oidLeavePeriode+
        " WHERE "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+ " = "+iOidEmployee+
        " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+ " = '"+Formater.formatDate(dt,"yyyy-MM-dd")+"'";
        try{
//            System.out.println("getDpStockManagement sql : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()){
                objDpStockMgn = new DpStockManagement();
                resultToObject(rs, objDpStockMgn);
            }
        }
        catch(DBException dbe){
            dbe.printStackTrace();
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return objDpStockMgn;
    }
    
    
    /**
     * get first record of DpStockManagement with sort ascending by OWNING_DATE with active status
     * @return
     * @created by Edhy
     */    
    public static DpStockManagement getDpStockPerEmpFirst()
    {
        DpStockManagement dpStockManagement = new DpStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] +                       
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       " = " + DP_STS_AKTIF +
                       " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        try
        {
            System.out.println("stSQL : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                dpStockManagement.setOID(rs.getLong(1));
                dpStockManagement.setLeavePeriodeId(rs.getLong(2));
                dpStockManagement.setEmployeeId(rs.getLong(3));
                dpStockManagement.setDtOwningDate(rs.getDate(4));
                dpStockManagement.setDtExpiredDate(rs.getDate(5));
                dpStockManagement.setiDpQty(rs.getFloat(6));   
                dpStockManagement.setQtyUsed(rs.getFloat(7));
                dpStockManagement.setQtyResidue(rs.getFloat(8));
                dpStockManagement.setiDpStatus(rs.getInt(9));
                dpStockManagement.setStNote(rs.getString(10));
                dpStockManagement.setiExceptionFlag(rs.getInt(11));
                dpStockManagement.setDtExpiredDateExc(rs.getDate(12));
                
                break;                
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return dpStockManagement;
    }


    /**
     * get first record of DpStockManagement with sort ascending by OWNING_DATE with active status
     * @return
     * @created by Edhy
     */    
    public static DpStockManagement getDpStockPerEmpFirst(long empOid)
    {
        DpStockManagement dpStockManagement = new DpStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] +                       
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE " +  PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + empOid + 
                       " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       " = " + DP_STS_AKTIF +
                       " ORDER BY " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        try
        {
//            System.out.println("stSQL : " + stSQL);
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                dpStockManagement.setOID(rs.getLong(1));
                dpStockManagement.setLeavePeriodeId(rs.getLong(2));
                dpStockManagement.setEmployeeId(rs.getLong(3));
                dpStockManagement.setDtOwningDate(rs.getDate(4));
                dpStockManagement.setDtExpiredDate(rs.getDate(5));
                dpStockManagement.setiDpQty(rs.getFloat(6));   
                dpStockManagement.setQtyUsed(rs.getFloat(7));
                dpStockManagement.setQtyResidue(rs.getFloat(8));
                dpStockManagement.setiDpStatus(rs.getInt(9));
                dpStockManagement.setStNote(rs.getString(10));
                dpStockManagement.setiExceptionFlag(rs.getInt(11));
                dpStockManagement.setDtExpiredDateExc(rs.getDate(12));                
                break;                
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return dpStockManagement;
    }    
    
    /**
     * @param leavePeriodeId
     * @param employeeId
     * @param presenceDate
     * @return
     * @created by Edhy
     */    
    public static DpStockManagement getDpStockPerEmployee(long leavePeriodeId, long employeeId, Date presenceDate)
    {
        DpStockManagement dpStockManagement = new DpStockManagement();
        DBResultSet dbrs;
        String stSQL = " SELECT " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_QTY] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_NOTE] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXCEPTION_FLAG] +
                       ", " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EXPIRED_DATE_EXC] +                       
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_LEAVE_PERIODE_ID] +
                       " = " + leavePeriodeId +
                       " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +
                       " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] +
                       " = \"" + Formater.formatDate(presenceDate, "yyyy-MM-dd") + "\"";
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                dpStockManagement.setOID(rs.getLong(0));
                dpStockManagement.setLeavePeriodeId(rs.getLong(1));
                dpStockManagement.setEmployeeId(rs.getLong(2));
                dpStockManagement.setDtOwningDate(rs.getDate(3));
                dpStockManagement.setDtExpiredDate(rs.getDate(4));
                dpStockManagement.setiDpQty(rs.getFloat(5));   
                dpStockManagement.setQtyUsed(rs.getFloat(6));
                dpStockManagement.setQtyResidue(rs.getFloat(7));
                dpStockManagement.setiDpStatus(rs.getInt(8));
                dpStockManagement.setStNote(rs.getString(9));
                dpStockManagement.setiExceptionFlag(rs.getInt(10));
                dpStockManagement.setDtExpiredDateExc(rs.getDate(11));               
            }
        }
        catch(DBException dbe)
        {
            dbe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return dpStockManagement;
    }    
    

    /**
     * @param presenceDate
     * @param employeeId
     */
    public void updateDpStockManagementByPresence(Date presenceDate, long employeeId) 
    {   
        // menghitung jumlah stock DP yang terpakai dan sisanya
        // jika Dp itu sudah terpakai semua
        DpStockManagement dpStockManagement = getDpStockPerEmpFirst(employeeId);
        float intDpUsed  = dpStockManagement.getQtyUsed() + 1;  
        float intResidue = dpStockManagement.getiDpQty() - intDpUsed;  
        dpStockManagement.setQtyUsed(intDpUsed);
        dpStockManagement.setQtyResidue(intResidue);          
        if(intResidue == 0) 
        {
            dpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
        }

        try   
        {
            // jika pada saat pengambilan DP, masih ada stock DP
            boolean dpTakenExist = PstDpStockTaken.existDpStockTaken(employeeId, presenceDate);            
            if( (dpStockManagement.getOID()!=0) && (!dpTakenExist) ) 
            {
                // update data dp stock management utk kolom USED dan RESIDUE
                PstDpStockManagement.updateExc(dpStockManagement);         
            }
            
            
            // insert data di Dp stock taken              
            if(!dpTakenExist)
            {                
                PstDpStockTaken objPstDpStockTaken = new PstDpStockTaken();
                DpStockTaken objDpStockTaken = new DpStockTaken();
                objDpStockTaken.setDpStockId(dpStockManagement.getOID());
                objDpStockTaken.setEmployeeId(employeeId);
                objDpStockTaken.setTakenDate(presenceDate);  
                objDpStockTaken.setTakenQty(1);                        
                objPstDpStockTaken.insertExc(objDpStockTaken);    
            }   
        }  
        catch(Exception e)   
        {
            System.out.println("Exc when update Dp stock on PstDpStockManagement : " + e.toString());
        }
    }

    
    /**
     * @param leavePeriodeId
     * @param presenceDate  
     * @param employeeId
     * @created by Edhy
     */
    public void deleteDpStockManagement(long employeeId, Date presenceDate) 
    {        
        long leavePeriodeId = PstPeriod.getPeriodeIdBetween(presenceDate);                
        DpStockManagement dpStockManagement = getDpStockPerEmployee(leavePeriodeId, employeeId, presenceDate);
        try 
        {
            PstDpStockManagement.deleteExc(dpStockManagement.getOID()); 
        }
        catch(Exception e)  
        {
            System.out.println("Exc when delete DP stock on PstDpStockManagement : " + e.toString());
        }
    }
    
    
    /**
     * @param selectedDate
     * @param employeeId
     * @return
     * @created by Edhy
     */    
    public int getDpStockAmountPerEmployee(long employeeId)
    {
        int result = 0;
        DBResultSet dbrs = null;
        String stSQL = " SELECT SUM(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +                       
                       " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       " = " + PstDpStockManagement.DP_STS_AKTIF;
        try
        {
//            System.out.println(getClass().getName() + ".getDpStockAmountPerEmployee() stSQL : " + stSQL);            
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = rs.getInt(1);
                break;
            }
        }
        catch(Exception e)
        {
            System.out.println(getClass().getName() + ".getDpStockAmountPerEmployee() exc : " + e.toString());            
        }
        finally
        {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
    
     /**
     * @param selectedDate
     * @param employeeId
     * @return
     * @created by priska
     */    
    public static int getDpStockAmountPerEmployeestatic(long employeeId)
    {
        int result = 0;
        DBResultSet dbrs = null;
        String stSQL = " SELECT SUM(" + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_RESIDUE] + ")" +
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT +
                       " WHERE " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " = " + employeeId +                       
                       " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                       " = " + PstDpStockManagement.DP_STS_AKTIF;
        try
        {
//            System.out.println(getClass().getName() + ".getDpStockAmountPerEmployee() stSQL : " + stSQL);            
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                result = rs.getInt(1);
                break;
            }
        }
        catch(Exception e)
        {
            //System.out.println(getClass().getName() + ".getDpStockAmountPerEmployee() exc : " + e.toString());            
        }
        finally
        {
            DBResultSet.close(dbrs);
        }
        return result;
    }
    
/**
 * FUngsi untuk pengecekan apakah DP tersebut jika kelebihan ambil di cek kembali
 * create by satrya 2013-03-19
 * @return 
 */
public static String checkPaidDPPayable(){
       String strWhere = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS e"
                    + " WHERE " + strWhere;
                    ///+ " LIMIT 0,10000";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                String where= PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])
                                + " AND "+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"="+PstDpStockManagement.DP_STS_AKTIF;
                Vector vtDpStock = PstDpStockManagement.listDPStock(0,0,where,PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+ " ASC " );                        
                    if (vtDpStock != null && vtDpStock.size() > 0) 
                    {
                        for (int i=0; i<vtDpStock.size(); i++)  
                        {
                            DpStockManagement dpStockMng = (DpStockManagement) vtDpStock.get(i);
                              try {
                                   // pembayaran hutang DP jika ada                                                                                                                                     
                                   Vector vectOidLeavePaid = PstDpStockManagement.paidDpPayable(dpStockMng.getEmployeeId(), dpStockMng);																												                                            
                               }catch (Exception e)     
                                        {
                                            System.out.println("Exc when update dpStockManagement on processStockDp : " + e.toString());
                                        } 
                        }
                    }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return "";
    }

/**
 * create by satrya 2013-07-01
 * melakukan pengecekan jika ada pembayaran dp 
 * @param employeeId
 * @return 
 */
public static String checkPaidDPPayable(long employeeId){
       String strWhere = PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN + " AND "
               + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+employeeId;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                    + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS e"
                    + " WHERE " + strWhere;
                    ///+ " LIMIT 0,10000";
        //hanya untuk test
        long testempId=(Long)504404463276544146l;
        if(employeeId==testempId){
            boolean test=true;
        }  
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                String where= PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID])
                                + " AND "+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS]+"="+PstDpStockManagement.DP_STS_AKTIF
                                + " AND "+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+"="+employeeId;
                Vector vtDpStock = PstDpStockManagement.listDPStock(0,0,where,PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+ " ASC " );                        
                    if (vtDpStock != null && vtDpStock.size() > 0) 
                    {
                        for (int i=0; i<vtDpStock.size(); i++)  
                        {
                            DpStockManagement dpStockMng = (DpStockManagement) vtDpStock.get(i);
                              try {
                                   // pembayaran hutang DP jika ada   
                  
                                   Vector vectOidLeavePaid = PstDpStockManagement.paidDpPayable(dpStockMng.getEmployeeId(), dpStockMng);																												                                            
                               }catch (Exception e)     
                                        {
                                            System.out.println("Exc when update dpStockManagement on processStockDp : " + e.toString());
                                        } 
                        }
                    }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return "";
    }


//update by priska 20150930

public static String checkGetDP(long oidScheduleEmp,long empOid, long oidSymbolBefore, long periodId, long oidSymbolNew, int dateSelect){
    
                       long oidph = PstSystemProperty.getPropertyLongbyName("OID_PUBLIC_HOLIDAY");
                       //long oidstatusbefore = PstEmpSchedule.getStatusValue(oidScheduleEmp, dateSelect);
                       
                       Date nd = new Date();
                       Date newDatePlusOneYear = new Date();
                       newDatePlusOneYear.setYear(newDatePlusOneYear.getYear()+1);
                        if (oidSymbolBefore == oidph){
                            DpStockManagement dpStockManagement = new DpStockManagement() ;
                            dpStockManagement.setQtyResidue(1);
                            
                            dpStockManagement.setiDpQty(1);
                            dpStockManagement.setDtOwningDate(nd);
                            dpStockManagement.setDtExpiredDate(newDatePlusOneYear);
                            dpStockManagement.setiDpStatus(0);
                            dpStockManagement.setEmployeeId(empOid);
                            dpStockManagement.setStNote(" DP Generate " + nd );
                            try {
                                long oid = PstDpStockManagement.insertExc(dpStockManagement);
                            } catch (Exception e){
                                System.out.print(e);
                            }
                           
                       }
                      
                        
                       String sday = Formater.formatDate(nd, "yyyy-MM-dd");
                       String whereclausedpstock = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]  + " = \"" + sday + " \" ";
                       Vector DpStockM = PstDpStockManagement.list(0, 0, whereclausedpstock, null);
                       if (oidSymbolNew == oidph){
                           for (int k=0 ; k< DpStockM.size(); k++){
                               DpStockManagement dpStockManagement = (DpStockManagement) DpStockM.get(k);
                                try {
                                    long oid = PstDpStockManagement.deleteExc(dpStockManagement.getOID());
                                } catch (Exception e){
                                    System.out.print(e);
                                }
                           }
                           
                       }

    
    
        return "";
    }

/**
 * Create by satrya 2013-07-02
 * @param startDate
 * @param endDate
 * @return 
 */
public static Hashtable hashDPStockCek(Date startDate,Date endDate){
        Hashtable hashDpStockCek= new Hashtable();
        DBResultSet dbrs = null;
        try {
        if(startDate!=null && endDate!=null){
            String sql = "SELECT * FROM "+PstDpStockManagement.TBL_DP_STOCK_MANAGEMENT
                    + " WHERE "+ PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE] + " BETWEEN \""+Formater.formatDate(startDate, "yyyy-MM-dd")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd") +"\"";
                    ///+ " LIMIT 0,10000";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                DpStockManagement dpStockManagement = new DpStockManagement();
                resultToObject(rs, dpStockManagement);
               hashDpStockCek.put(""+dpStockManagement.getEmployeeId()+"_"+dpStockManagement.getDtOwningDate(), dpStockManagement);
            }
            rs.close();
        }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hashDpStockCek;
    }
    /** 
     * Proses pembayaran hutang DP, dimana object DpStockManagement tidak null
     * @param objDpStockManagement
     * @param employeeId
     * @return
     * @created by Edhy
     */        
    public static Vector paidDpPayable(long employeeId, DpStockManagement objDpStockManagement)
    {        
       /* Vector result = new Vector(1,1);
        Vector listDpPayable = PstDpStockTaken.getDpPayable(employeeId);
        if(listDpPayable!=null && listDpPayable.size()>0)
        {            
            long dpStockManagementOid = objDpStockManagement.getOID();
            Date dtDpStockActive = objDpStockManagement.getDtOwningDate();
            float maxDpStock = objDpStockManagement.getQtyResidue();
            int maxDpPayable = listDpPayable.size();
            float maxIterate = (maxDpStock >= maxDpPayable) ? maxDpPayable : maxDpStock;                                    
            for(int i=0; i<maxIterate; i++)
            {
                try
                {
                    DpStockTaken objDpStockTaken = (DpStockTaken) listDpPayable.get(i);
                    objDpStockTaken.setDpStockId(dpStockManagementOid);     
                    objDpStockTaken.setPaidDate(dtDpStockActive);     
                                        
                    long updatedOid = PstDpStockTaken.updateExc(objDpStockTaken);
                    result.add(String.valueOf(updatedOid));
                }
                catch(Exception e)
                {
                    System.out.println("Exc when update objDpStockTaken : " + e.toString());
                }
            }            
            
            // update object DpStockManagement karena semua Qty sudah digunakan utk membayar hutang
            try
            {
                if(result!=null && result.size()>0) 
                {
                    float dpUsed = objDpStockManagement.getQtyUsed();                    
                    objDpStockManagement.setQtyUsed(dpUsed + result.size());
                    objDpStockManagement.setQtyResidue(objDpStockManagement.getiDpQty() - objDpStockManagement.getQtyUsed());
                    if(objDpStockManagement.getQtyResidue() == 0)
                    {
                        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
                    }
                    long dpManUpdatedOid = PstDpStockManagement.updateExc(objDpStockManagement);
                }
            }
            catch(Exception e)
            {
                System.out.println("Exc when update object DpStockManagement : " + e.toString());
            }            
        }        
        return result; */
        /**
         * update by satrya 2013-03-19
         */
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
         Vector result = new Vector(1,1);
          LeaveApplication leaveApplication = new LeaveApplication();
        
        //hanya untuk test
        long testempId=(Long)504404463276544146l;
        if(employeeId==testempId){
            boolean test=true;
        }  
        Vector listDpPayable = PstDpStockTaken.getDpPayable(employeeId);
        if(listDpPayable!=null && listDpPayable.size()>0)
        {            
            long dpStockManagementOid = objDpStockManagement.getOID();
            Date dtDpStockActive = objDpStockManagement.getDtOwningDate();
            float maxDpStock = objDpStockManagement.getQtyResidue();
            int maxDpPayable = listDpPayable.size();
            //float maxIterate = (maxDpStock >= maxDpPayable) ? maxDpPayable : maxDpStock;                                    
            for(int i=0; i<maxDpPayable; i++)
            {
                try
                {
                    boolean isUpdate = false;
                    DpStockTaken objDpStockTaken = (DpStockTaken) listDpPayable.get(i);
                    if(objDpStockTaken.getLeaveApplicationId()!=0){
                    leaveApplication = PstLeaveApplication.fetchExc(objDpStockTaken.getLeaveApplicationId());
                    }
                    objDpStockTaken.setDpStockId(dpStockManagementOid);     
                    objDpStockTaken.setPaidDate(dtDpStockActive);     
                 
                     float dpUsed = objDpStockManagement.getQtyUsed();
                     //jika residue'nya masih ada sisa maka akan di kurangi taken yg masih ngutang
                    if(objDpStockManagement.getQtyResidue() >= objDpStockTaken.getTakenQty()){
                          objDpStockManagement.setQtyUsed(dpUsed + objDpStockTaken.getTakenQty());
                          objDpStockManagement.setQtyResidue(objDpStockManagement.getiDpQty() - objDpStockManagement.getQtyUsed());
                          
                          objDpStockManagement.setStNote(objDpStockManagement.getStNote() + " " +  " OverLap Taken: " + objDpStockTaken.getTakenQty()+ " ,Date Off Application : <a href=\"javascript:openDPOverlap(\'" + objDpStockTaken.getLeaveApplicationId() + "\');\">" + Formater.formatDate(leaveApplication.getSubmissionDate(), "yyyy-MM-dd") + "</a> ; ");
                          isUpdate=true;
                          //update by satrya 2013-03-19 jam 18.00
                    }else if(objDpStockManagement.getiDpQty() - objDpStockManagement.getQtyUsed() > 0){
                        float tmpTakenQty = objDpStockTaken.getTakenQty();
                        float tmpQtyResidue = objDpStockManagement.getQtyResidue();
                        String sHitunganSisa = Formater.formatNumber(tmpTakenQty - tmpQtyResidue, "#.#####");
                        float hitunganSisa = Float.parseFloat(sHitunganSisa);
                        objDpStockTaken.setTakenQty(objDpStockManagement.getQtyResidue());
                        long lTmpTaken = Formater.getWorkDayMiliSeconds(objDpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday());
                        objDpStockTaken.setTakenFinnishDate(new Date(objDpStockTaken.getTakenDate().getTime()+lTmpTaken));
                        long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(objDpStockTaken.getEmployeeId(), objDpStockTaken.getTakenDate(), objDpStockTaken.getTakenFinnishDate());
                        if(intersecX!=0){
                            objDpStockTaken.setTakenFinnishDate(new Date(objDpStockTaken.getTakenFinnishDate().getTime()+intersecX));
                        }
                        objDpStockManagement.setQtyUsed(dpUsed + objDpStockTaken.getTakenQty());
                        objDpStockManagement.setQtyResidue(objDpStockManagement.getiDpQty() - objDpStockManagement.getQtyUsed());
                       objDpStockManagement.setStNote(objDpStockManagement.getStNote() + " " +  " OverLap Taken: " + objDpStockTaken.getTakenQty()+ " ,Date Off Application : <a href=\"javascript:openDPOverlap(\'" + objDpStockTaken.getLeaveApplicationId() + "\');\">" + Formater.formatDate(leaveApplication.getSubmissionDate(), "yyyy-MM-dd") + "</a> ; ");
                         isUpdate=true;
                         //di karenakan DP'nya takennya melebihi stok jadi sisanya di insert kembali
                         DpStockTaken dpStockTaken = new DpStockTaken();
                         dpStockTaken.setDpStockId(0);//karena stok abis dan dp belum pengganti belum ada
                         dpStockTaken.setEmployeeId(objDpStockTaken.getEmployeeId());
                         dpStockTaken.setTakenDate(objDpStockTaken.getTakenFinnishDate());//lanjutan dari takenfinisih yg sisa tadi
                         dpStockTaken.setTakenQty(hitunganSisa);
                         dpStockTaken.setPaidDate(null);
                         dpStockTaken.setLeaveApplicationId(objDpStockTaken.getLeaveApplicationId());
                         long lHitungSisa = Formater.getWorkDayMiliSeconds(hitunganSisa, leaveConfig.getHourOneWorkday());
                         dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenDate().getTime()+lHitungSisa));
                            intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                        if(intersecX!=0){
                            dpStockTaken.setTakenFinnishDate(new Date(dpStockTaken.getTakenFinnishDate().getTime()+intersecX));
                        }
                       
                        PstDpStockTaken.insertExc(dpStockTaken);
                    }
                  if(objDpStockManagement.getQtyResidue() == 0)
                    {
                        objDpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
                    }
                  if(isUpdate){
                    long updatedOid = PstDpStockTaken.updateExc(objDpStockTaken);
                    long dpManUpdatedOid = PstDpStockManagement.updateExc(objDpStockManagement);
                  }
                    
                }
                catch(Exception e)
                {
                    System.out.println("Exc when update objDpStockTaken : " + e.toString());
                }
            }            
                        
        }        
        return result; 
    }

    /**
     * Proses generate data DP Stock Taken sesuai dengan data Dp used pada object DP Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateDpTaken()
    {        
        return generateDpTaken(0);
    }
    
    /**
     * Proses generate data DP Stock Taken sesuai dengan data Dp used pada object DP Management
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateDpTaken(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                                  " IN (" + PstDpStockManagement.DP_STS_AKTIF + "," + PstDpStockManagement.DP_STS_TAKEN + ")" +
                                  " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_QTY_USED] +
                                  " > 0";  
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {
            strEmployeeCondition = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
        }
        
        String whereClause = "";
        if(strCommCondition!=null && strCommCondition.length()>0) {
            whereClause = strCommCondition;
        }            

        if(strEmployeeCondition!=null && strEmployeeCondition.length()>0) {
            if(whereClause!=null && whereClause.length()>0) {
                whereClause = whereClause + " AND " + strEmployeeCondition;
            }
            else 
            {
                whereClause = strEmployeeCondition;
            }
        }
        
        String strOrderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector vectOfDpStock = PstDpStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfDpStock!=null && vectOfDpStock.size()>0)
        {
            int maxVectOfDpStock = vectOfDpStock.size();
            for(int i=0; i<maxVectOfDpStock; i++)
            {
                DpStockManagement objDpStockManagement = (DpStockManagement) vectOfDpStock.get(i);
                float maxDpUsed = objDpStockManagement.getQtyUsed();
                for(int j=0; j<maxDpUsed; j++)
                {
                    DpStockTaken objDpStockTaken = new DpStockTaken();
                    objDpStockTaken.setDpStockId(objDpStockManagement.getOID());
                    objDpStockTaken.setEmployeeId(objDpStockManagement.getEmployeeId());
                    objDpStockTaken.setTakenDate(objDpStockManagement.getDtOwningDate());
                    objDpStockTaken.setTakenQty(1);
                    objDpStockTaken.setPaidDate(objDpStockManagement.getDtOwningDate());
                    
                    try
                    {
                        long oid = PstDpStockTaken.insertExc(objDpStockTaken);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateDpTaken : " + e.toString());
                    }
                }
            }                
        }
        
        return result;
    }


    
    
    /**
     * Proses generate data DP Stock Expired sesuai dengan data Dp used pada object DP Management     
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateDpExpired()
    {        
        return generateDpExpired(0);
    }
    
    /**
     * Proses generate data DP Stock Expired sesuai dengan data Dp used pada object DP Management
     * @param employeeId
     *
     * @return
     * @created by Edhy
     */        
    public static Vector generateDpExpired(long employeeId)
    {        
        Vector result = new Vector(1,1);
        String strCommCondition = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] +
                                  " = " + PstDpStockManagement.DP_STS_EXPIRED;
        
        String strEmployeeCondition = "";
        if(employeeId != 0)
        {
            strEmployeeCondition = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + employeeId;            
        }
        
        String whereClause = "";
        if(strCommCondition!=null && strCommCondition.length()>0) 
        {
            whereClause = strCommCondition;
        }            

        if(strEmployeeCondition!=null && strEmployeeCondition.length()>0) 
        {
            if(whereClause!=null && whereClause.length()>0) 
            {
                whereClause = whereClause + " AND " + strEmployeeCondition;
            }
            else 
            {
                whereClause = strEmployeeCondition;
            }
        }
        
        String strOrderBy = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
        Vector vectOfDpStock = PstDpStockManagement.list(0, 0, whereClause, strOrderBy);
        if(vectOfDpStock!=null && vectOfDpStock.size()>0)
        {  
            int maxVectOfDpStock = vectOfDpStock.size();
            for(int i=0; i<maxVectOfDpStock; i++)
            {
                DpStockManagement objDpStockManagement = (DpStockManagement) vectOfDpStock.get(i);
                float maxDpUsed = objDpStockManagement.getQtyUsed();
                for(int j=0; j<maxDpUsed; j++)  
                {
                    DpStockExpired objDpStockExpired = new DpStockExpired();
                    objDpStockExpired.setDpStockId(objDpStockManagement.getOID());                    
                    objDpStockExpired.setExpiredDate(objDpStockManagement.getDtOwningDate());
                    objDpStockExpired.setExpiredQty(1);                    
                    
                    try
                    {
                        long oid = PstDpStockExpired.insertExc(objDpStockExpired);
                        result.add(String.valueOf(oid));
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exc when generateDpExpired : " + e.toString());
                    }
                }
            }                
        }
        
        return result;
    }
    
    
    
    /**
     * @param employeeOid
     * @param submissionDate
     * @return
     */    
    public static Vector getDpStock(long employeeOid, Date submissionDate)
    {
        Vector result = new Vector(1,1);
        
//        DpApplication objDpApplication = new DpApplication();
//        objDpApplication.setEmployeeId(employeeOid);
//        objDpApplication.setSubmissionDate(submissionDate);        
//        objDpApplication.setTakenDate(takenDate);
//        objDpApplication.setDpId(dpStockOid);        
//        objDpApplication.setDocStatus(PstDpApplication.FLD_DOC_STATUS_VALID);               
//        objDpApplication.setApprovalId(0);
        
        try
        {
//            result = PstDpApplication.insertExc(objDpApplication);
        }
        catch(Exception e)
        {
            System.out.println("Exc when getDpStock : " + e.toString());
        }
        
        return result;
    }    

    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteDpStockManagementPerDepartment(long lDepartmentOid) 
    {                
        DBResultSet dbrs = null;
        String stSQL = " SELECT DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STOCK_ID] +                       
                       ", DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " FROM "+ TBL_DP_STOCK_MANAGEMENT + " AS DP" +
                       " INNER JOIN "+ PstEmployee.TBL_HR_EMPLOYEE + " AS EMP" +
                       " ON DP." + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] +
                       " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +                       
                       " WHERE EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + 
                       " = " + lDepartmentOid;
        try
        {
            dbrs = DBHandler.execQueryResult(stSQL);
            ResultSet rs = dbrs.getResultSet();            
            while(rs.next())
            {
                long lDpStockOid = rs.getLong(1);
                long lEmployeeOid = rs.getLong(2);
                
                // delete Dp expired
                PstDpStockExpired.deleteByDpStock(lDpStockOid);
                
                // delete Dp taken
                PstDpStockTaken.deleteByEmployee(lEmployeeOid);
                
                // delete Dp stock
                try
                {
                    long oidDpStock = PstDpStockManagement.deleteExc(lDpStockOid);
                }
                catch(Exception e)
                {
                    System.out.println("Exc : " + e.toString());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Exc : " + e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
        }        
    }
    
    
    /**
     * @param lDepartmentOid
     * @created by Edhy     
     */
    public void deleteDpStockPerDepartment(long lDepartmentOid) 
    {   
        PstDpStockTaken objPstDpStockTaken = new PstDpStockTaken();
        objPstDpStockTaken.deleteDpStockTakenPerDepartment(lDepartmentOid); 
        deleteDpStockManagementPerDepartment(lDepartmentOid);
    }    
    
    
    /**
     * @param args
     */    
    public static void main(String args[])
    {
        /*
        Vector vectResult = generateDpTaken();
        if(vectResult!=null && vectResult.size()>0)
        {
            int maxVectResult = vectResult.size(); 
            for(int i=0; i<maxVectResult; i++)
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        }
        */
        
        System.out.println("Start process"); 
        Vector vectResult = generateDpExpired();  
        if(vectResult!=null && vectResult.size()>0)
        {
            System.out.println("In vectResult > 0");
            int maxVectResult = vectResult.size();
            for(int i=0; i<maxVectResult; i++)
            {
                System.out.println("oid ke " + (i+1) + " = " + String.valueOf(vectResult.get(i)));
            }
        }
        System.out.println("Stop process");  

        /*
        PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();        
        int result = pstDpStockManagement.getDpStockAmountPerEmployee(504404246928985499L);
        System.out.println("result : " + result);
        */
    }
    
}
