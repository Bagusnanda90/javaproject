/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import com.sun.xml.tree.ParseContext;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.apache.poi.ss.formula.functions.Days360;

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
public class PstRewardAndPunishmentDetail extends  DBHandler  implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {
   
    
    public static final String TBL_HR_REWARD_PUNISMENT_DETAIL="hr_reward_punisment_detail";
    
    public static final int FLD_REWARD_PUNISMENT_DETAIL_ID=0;
    public static final int FLD_REWARD_PUNISMENT_MAIN_ID=1;
    public static final int FLD_EMPLOYEE_ID=2;
    public static final int FLD_KOEFISIEN_POSITION=3;
    public static final int FLD_WORKING_DAYS=4;
    public static final int FLD_TOTAL=5;
    public static final int FLD_BEBAN=6;
    public static final int FLD_TUNAI=7;
    public static final int FLD_LAMA_MASA_CICILAN=8;
    public static final int FLD_ADJUSMENT=9;
   
    
      
    public static String[] fieldNames = {
        "REWARD_PUNISMENT_DETAIL_ID",
        "REWARD_PUNISMENT_MAIN_ID",
        "EMPLOYEE_ID",
        "KOEFISIEN_POSITION_ID",
        "WORKING_DAYS",
        "TOTAL",
        "BEBAN",  
        "TUNAI",
        "LAMA_MASA_CICILAN",
        "ADJUSMENT"
    };
   
    public static int[] fieldTypes = {
       
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT
    };

  
    
    public PstRewardAndPunishmentDetail(){
	}

	public PstRewardAndPunishmentDetail(int i) throws DBException { 
		super(new PstRewardAndPunishmentDetail()); 
	}

    public PstRewardAndPunishmentDetail(String sOid) throws DBException {

        super(new PstRewardAndPunishmentDetail(0));//merupakan induk construktor dari DBHandler lalu membuat new PstEmployee lalu memberi nilai defaoult 0

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstRewardAndPunishmentDetail(long lOid) throws DBException {

        super(new PstRewardAndPunishmentDetail(0));//merupakan induk construktor dari DBHandler, 0 fungsinya sebagai default PSTGEJALA

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
        return TBL_HR_REWARD_PUNISMENT_DETAIL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
       return fieldTypes;
    }

    public String getPersistentName() {
        return new PstRewardAndPunishmentDetail().getClass().getName();
    }

       /**
     * Keterangan: untuk mengambil data dari 
     * database berdasarkan oid oidEmployee dan kemudaian di set objecknya
     * @param oid : oidEmployee
     * @return
     * @throws DBException 
     */
    public static RewardnPunismentDetail fetchExc(long oid) throws DBException {

        try {
            RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
            PstRewardAndPunishmentDetail pstRewardnPunismentDetail = new PstRewardAndPunishmentDetail(oid);
            
            rewardnPunismentDetail.setOID(oid);
            rewardnPunismentDetail.setEmployeeId(pstRewardnPunismentDetail.getlong(FLD_EMPLOYEE_ID));
            rewardnPunismentDetail.setKoefisienId(pstRewardnPunismentDetail.getlong(FLD_KOEFISIEN_POSITION));
            rewardnPunismentDetail.setHariKerja(pstRewardnPunismentDetail.getInt(FLD_WORKING_DAYS));
            rewardnPunismentDetail.setTotal(pstRewardnPunismentDetail.getdouble(FLD_TOTAL));
            rewardnPunismentDetail.setBeban(pstRewardnPunismentDetail.getdouble(FLD_BEBAN));
            rewardnPunismentDetail.setTunai(pstRewardnPunismentDetail.getdouble(FLD_TUNAI));
            rewardnPunismentDetail.setLamamasacicilan(pstRewardnPunismentDetail.getInt(FLD_LAMA_MASA_CICILAN));
            rewardnPunismentDetail.setAdjusment(pstRewardnPunismentDetail.getInt(FLD_ADJUSMENT));
            rewardnPunismentDetail.setRewardnPunismentMainId(pstRewardnPunismentDetail.getlong(FLD_REWARD_PUNISMENT_MAIN_ID));
          
            
           
            
            return rewardnPunismentDetail;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentDetail(0), DBException.UNKNOWN);
        }
    }
    public long fetchExc(Entity entity) throws Exception {
        RewardnPunismentDetail RewardnPunismentDetail = fetchExc(entity.getOID());
        entity = (Entity)RewardnPunismentDetail;
        return RewardnPunismentDetail.getOID();
    }
    
 /**
  * Keterangan: fungsi untuk update data to database
  * create by  satrya 2013-09-27
  * @param RewardnPunismentDetail
  * @return
  * @throws DBException 
  */
    public static synchronized long updateExc(RewardnPunismentDetail rewardnPunismentDetail) throws DBException {
        try {
            if (rewardnPunismentDetail.getOID() != 0) {
                PstRewardAndPunishmentDetail pstRewardnPunismentDetail = new PstRewardAndPunishmentDetail(rewardnPunismentDetail.getOID());
                pstRewardnPunismentDetail.setLong(FLD_REWARD_PUNISMENT_MAIN_ID, rewardnPunismentDetail.getRewardnPunismentMainId());
                pstRewardnPunismentDetail.setLong(FLD_EMPLOYEE_ID, rewardnPunismentDetail.getEmployeeId());
                pstRewardnPunismentDetail.setLong(FLD_KOEFISIEN_POSITION, rewardnPunismentDetail.getKoefisienId());
                pstRewardnPunismentDetail.setInt(FLD_WORKING_DAYS, rewardnPunismentDetail.getHariKerja());
                pstRewardnPunismentDetail.setDouble(FLD_TOTAL, rewardnPunismentDetail.getTotal());
                pstRewardnPunismentDetail.setDouble(FLD_BEBAN, rewardnPunismentDetail.getBeban());
                pstRewardnPunismentDetail.setDouble(FLD_TUNAI, rewardnPunismentDetail.getTunai());
                pstRewardnPunismentDetail.setDouble(FLD_LAMA_MASA_CICILAN, rewardnPunismentDetail.getLamamasacicilan());
                pstRewardnPunismentDetail.setInt(FLD_ADJUSMENT, rewardnPunismentDetail.getAdjusment());
                pstRewardnPunismentDetail.update();

                return rewardnPunismentDetail.getOID();
            }
        } 
        catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentDetail(0), DBException.UNKNOWN);
        }

        return 0;

    }

    
    public long updateExc(Entity entity) throws Exception {
        return updateExc((RewardnPunismentDetail)entity);
    }

    
    
    
 /**
 * Keterangan: delete data employee
 * @param oid
 * @return
 * @throws DBException 
 */
    public static synchronized long deleteExc(long oid) throws DBException {

        try {

            PstRewardAndPunishmentDetail pstRewardnPunismentDetail = new PstRewardAndPunishmentDetail(oid) {};

            pstRewardnPunismentDetail.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstRewardAndPunishmentDetail(0) {}, DBException.UNKNOWN);

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
     * @param RewardnPunismentDetail
     * @return
     * @throws DBException 
     */
    public static synchronized long insertExc(RewardnPunismentDetail rewardnPunismentDetail) 
            throws DBException {
    try {

            PstRewardAndPunishmentDetail pstRewardnPunismentDetail = new PstRewardAndPunishmentDetail(0);
            
                pstRewardnPunismentDetail.setLong(FLD_REWARD_PUNISMENT_MAIN_ID, rewardnPunismentDetail.getRewardnPunismentMainId());
                pstRewardnPunismentDetail.setLong(FLD_EMPLOYEE_ID, rewardnPunismentDetail.getEmployeeId());
                pstRewardnPunismentDetail.setLong(FLD_KOEFISIEN_POSITION, rewardnPunismentDetail.getKoefisienId());
                pstRewardnPunismentDetail.setInt(FLD_WORKING_DAYS, rewardnPunismentDetail.getHariKerja());
                pstRewardnPunismentDetail.setDouble(FLD_TOTAL, rewardnPunismentDetail.getTotal());
                pstRewardnPunismentDetail.setDouble(FLD_BEBAN, rewardnPunismentDetail.getBeban());
                pstRewardnPunismentDetail.setDouble(FLD_TUNAI, rewardnPunismentDetail.getTunai());
                pstRewardnPunismentDetail.setInt(FLD_LAMA_MASA_CICILAN, rewardnPunismentDetail.getLamamasacicilan());
                pstRewardnPunismentDetail.setInt(FLD_ADJUSMENT, rewardnPunismentDetail.getAdjusment());
            pstRewardnPunismentDetail.insert();
            
            rewardnPunismentDetail.setOID(pstRewardnPunismentDetail.getlong(FLD_REWARD_PUNISMENT_DETAIL_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentDetail(0) {}, DBException.UNKNOWN);
        }
        return rewardnPunismentDetail.getOID();
    }
    public long insertExc(Entity entity) throws Exception {
         return insertExc((RewardnPunismentDetail)entity);
    }
    
      public static synchronized long insertExcn(RewardnPunismentDetail rewardnPunismentDetail) 
            throws DBException {
    try {

            PstRewardAndPunishmentDetail pstRewardAndPunishmentDetail = new PstRewardAndPunishmentDetail(0);
            
//    public static final int FLD_REWARD_PUNISMENT_DETAIL_ID=0;
//    public static final int FLD_REWARD_PUNISMENT_MAIN_ID=1;
//    public static final int FLD_EMPLOYEE_ID=2;
//    public static final int FLD_KOEFISIEN_POSITION=3;
//    public static final int FLD_WORKING_DAYS=4;
//    public static final int FLD_TOTAL=5;
//    public static final int FLD_BEBAN=6;
//    public static final int FLD_TUNAI=7;
//    public static final int FLD_LAMAMASACICILAN=8;
            
            pstRewardAndPunishmentDetail.setLong(FLD_REWARD_PUNISMENT_MAIN_ID, rewardnPunismentDetail.getRewardnPunismentMainId());
            pstRewardAndPunishmentDetail.setLong(FLD_EMPLOYEE_ID, rewardnPunismentDetail.getEmployeeId());
            pstRewardAndPunishmentDetail.setLong(FLD_KOEFISIEN_POSITION, rewardnPunismentDetail.getKoefisienId());
            pstRewardAndPunishmentDetail.setInt(FLD_WORKING_DAYS, rewardnPunismentDetail.getHariKerja());
            pstRewardAndPunishmentDetail.setDouble(FLD_TOTAL, rewardnPunismentDetail.getTotal());
            pstRewardAndPunishmentDetail.setDouble(FLD_BEBAN, rewardnPunismentDetail.getBeban());
            pstRewardAndPunishmentDetail.setDouble(FLD_TUNAI, rewardnPunismentDetail.getTunai());
            pstRewardAndPunishmentDetail.setInt(FLD_LAMA_MASA_CICILAN, rewardnPunismentDetail.getLamamasacicilan());
            pstRewardAndPunishmentDetail.setInt(FLD_ADJUSMENT, rewardnPunismentDetail.getAdjusment());
            pstRewardAndPunishmentDetail.insert();
            
            rewardnPunismentDetail.setOID(pstRewardAndPunishmentDetail.getlong(FLD_REWARD_PUNISMENT_DETAIL_ID));

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstRewardAndPunishmentDetail(0), DBException.UNKNOWN);
        }
        return rewardnPunismentDetail.getOID();
    }
    public long insertExcn(Entity entity) throws Exception {
         return insertExcn((RewardnPunismentDetail)entity);
    }
    
    
     public static void resultToObject(ResultSet rs, RewardnPunismentDetail rewardnPunismentDetail) {

        try {

rewardnPunismentDetail.setOID(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID]));
rewardnPunismentDetail.setRewardnPunismentMainId(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID]));
rewardnPunismentDetail.setEmployeeId(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_EMPLOYEE_ID]));
rewardnPunismentDetail.setKoefisienId(rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_KOEFISIEN_POSITION]));
rewardnPunismentDetail.setHariKerja(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_WORKING_DAYS]));
rewardnPunismentDetail.setTotal(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TOTAL]));
rewardnPunismentDetail.setBeban(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]));
rewardnPunismentDetail.setTunai(rs.getDouble(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_TUNAI]));
rewardnPunismentDetail.setLamamasacicilan(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_LAMA_MASA_CICILAN]));
rewardnPunismentDetail.setAdjusment(rs.getInt(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_ADJUSMENT]));

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

            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_DETAIL;

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
                RewardnPunismentDetail rewardnPunismentDetail = new RewardnPunismentDetail();
                resultToObject(rs, rewardnPunismentDetail);
                lists.add(rewardnPunismentDetail);
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
    
    
    
    
        //by priska 2014-11-28
    public static long getKoefisienPosition(int limitStart, int recordToGet, String whereClause, String order,Date dtperiodfrom, Date dtperiodto) {

        long kpId = 0;

        DBResultSet dbrs = null;

        try {

            String sql = " SELECT DISTINCT "
                    + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]
                    + ",EO. "+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]
                    + ",KP. "+PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID];
            
                    sql= sql + " FROM " 
                    + PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN +" AS RPM "   
                    //INNER JOIN 	`hr_emp_outlet` AS eo ON `rpm`.`LOCATION_ID` = `eo`.`LOCATION_ID`        
                    + " INNER JOIN  " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS EO  ON (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_LOCATION_ID] + " = EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] +")"
                    //INNER JOIN      `hr_koefision_position` AS kp ON `eo`.`POSITION_ID` = `kp`.`POSITION_ID`
                    + " INNER JOIN  " + PstKoefisionPosition.TBL_HR_KOEFISION_POSITION + " AS KP  ON (EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_POSITION_ID] + " = KP."+PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_POSITION_ID] +")";
                            
                    if (whereClause != null && whereClause.length() > 0) {
                    sql = sql + " WHERE " + whereClause;
                    }

         
            if(dtperiodfrom!=null && dtperiodto!=null){
                  sql = sql + " AND ((EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " 23:59:59 \" ) ";
                  sql = sql + " OR ( EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " 23:59:59 \" )) ";
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
                
            kpId = (rs.getLong(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]));
                
            }
            //rs.close();
            return kpId;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
            return 0;
     }
    
    
    
    //by priska 2014-11-28
    public static Vector listRewardPunishmentDetailDistinct(int limitStart, int recordToGet, String whereClause, String order,Date dtperiodfrom, Date dtperiodto) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = " SELECT DISTINCT "
                    + " RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]
                    + ",EO. "+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]
                    + ",KP. "+PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID];
            
                    sql= sql + " FROM " 
                    + PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN +" AS RPM "   
                    //INNER JOIN 	`hr_emp_outlet` AS eo ON `rpm`.`LOCATION_ID` = `eo`.`LOCATION_ID`        
                    + " INNER JOIN  " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS EO  ON (RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_LOCATION_ID] + " = EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] +")"
                    //INNER JOIN      `hr_koefision_position` AS kp ON `eo`.`POSITION_ID` = `kp`.`POSITION_ID`
                    + " INNER JOIN  " + PstKoefisionPosition.TBL_HR_KOEFISION_POSITION + " AS KP  ON (EO."+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_POSITION_ID] + " = KP."+PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_POSITION_ID] +")";
                            
                    if (whereClause != null && whereClause.length() > 0) {
                    sql = sql + " WHERE " + whereClause;
                    }

         
            if(dtperiodfrom!=null && dtperiodto!=null){
                  sql = sql + " AND ((EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " 23:59:59 \" ) ";
                  sql = sql + " OR ( EO."+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " BETWEEN \"" + Formater.formatDate(dtperiodfrom, "yyyy-MM-dd") + " 00:00:01 \" AND \"" + Formater.formatDate(dtperiodto, "yyyy-MM-dd") + " 23:59:59 \" )) ";
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
                
            
                
                
                SessRewardPunishmentDetail sessRewardPunismentDetail = new SessRewardPunishmentDetail();
//                
//                    private long rewardnPunismentMainId;
//    private long employeeId;
//    private long koefisienId;
//    private int hariKerja;
//    private double total;
//    private double beban;
                
                sessRewardPunismentDetail.setRewardnPunismentMainId(rs.getLong(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID]));             
                sessRewardPunismentDetail.setEmployeeId(rs.getLong(PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]));             
                sessRewardPunismentDetail.setKoefisienId(rs.getLong(PstKoefisionPosition.fieldNames[PstKoefisionPosition.FLD_KOEFISION_POSITION_ID]));
                
                
                sessRewardPunismentDetail.setHariKerja(0);
                sessRewardPunismentDetail.setTotal(0);
                sessRewardPunismentDetail.setBeban(0);
                sessRewardPunismentDetail.setTotal(0);
                sessRewardPunismentDetail.setBeban(0);
                sessRewardPunismentDetail.setTunai(0);
                sessRewardPunismentDetail.setLamamasacicilan(0);
                sessRewardPunismentDetail.setAdjusment(0);
                
                lists.add(sessRewardPunismentDetail);
                
            }
            //rs.close();
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
            String sql = "SELECT * FROM " + TBL_HR_REWARD_PUNISMENT_DETAIL + " WHERE "
                    + PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID] + " = " + mSId;
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
    
    
        public static long deletewheremain(long rewarpunismentid) {
        DBResultSet dbrs = null;
        long resulthasil =0;
        try {
            String sql = "DELETE  FROM " + TBL_HR_REWARD_PUNISMENT_DETAIL + " WHERE "
                    + PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID] + " = " + rewarpunismentid;
            
            DBHandler.execSqlInsert(sql);
//            dbrs = DBHandler.execQuery(sql);
//            ResultSet rs = dbrs.getResultSet();
//            while (rs.next()) {
//                resulthasil = 1;
//            }
//            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
            
        } finally {
            DBResultSet.close(dbrs);
            return resulthasil;
        }
    }
    
    /**
     * keterangan : update nama by Id
     * create by: devin
     * tgl: 2013-11-21
     * @param RewardnPunismentDetail
     * @return 
     */
  

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_DETAIL_ID] 
                    + ") FROM " + TBL_HR_REWARD_PUNISMENT_DETAIL;
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
                    RewardnPunismentDetail jenisItems = (RewardnPunismentDetail) list.get(ls);
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
    //by priska 2015-01-25
    public static double gettotalrewardpunishment(Date fromdate, Date todate, long employeeId) {

        double nilai = 0;

        DBResultSet dbrs = null;

        
//SELECT rpd.`BEBAN`,rpm.`STATUS_OPNAME` FROM `hr_reward_punisment_detail` AS rpd 
//INNER JOIN `hr_reward_punisment_main` AS rpm ON 
//rpm.`REWARD_PUNISMENT_MAIN_ID` = rpd.`REWARD_PUNISMENT_MAIN_ID`
//WHERE rpm.`END_DATE_PERIOD` BETWEEN "2014-12-01 00:00:00" AND "2014-12-20 23:59:59" AND rpd.`EMPLOYEE_ID` = 504404562438458100
        
        try {

            String sql = " SELECT "
                    + " RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]
                    + ",RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME];            
              sql = sql + " FROM " 
                    + PstRewardAndPunishmentDetail.TBL_HR_REWARD_PUNISMENT_DETAIL +" AS RPD "   
                    + " INNER JOIN  " + PstRewardAndPunishmentMain.TBL_HR_REWARD_PUNISMENT_MAIN + " AS RPM  ON RPM."+PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_REWARD_PUNISMENT_MAIN_ID] + " = RPD."+PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_REWARD_PUNISMENT_MAIN_ID] ;
              sql = sql + " WHERE "
                    + "RPM." + PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_END_DATE_PERIOD]
                    + " BETWEEN \"" + Formater.formatDate(fromdate, "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(todate, "yyyy-MM-dd  23:59:59") + "\"" 
                    + " AND RPD." + PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_EMPLOYEE_ID] + " = " +employeeId ;       
  
            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
            if ((rs.getString(PstRewardAndPunishmentMain.fieldNames[PstRewardAndPunishmentMain.FLD_STATUS_OPNAME])).equals("PUNISMENT")){
            nilai = nilai - (rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]));
            } else {
            nilai = nilai + (rs.getLong(PstRewardAndPunishmentDetail.fieldNames[PstRewardAndPunishmentDetail.FLD_BEBAN]));
            }
                
            }
            //rs.close();
            return nilai;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
            return 0;
     }
    
}

