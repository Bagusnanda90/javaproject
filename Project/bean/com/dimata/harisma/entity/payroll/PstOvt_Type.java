/*
 * PstOvt_Type.java
 *
 * Created on April 11, 2007, 4:28 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.PstLevel;
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
import com.dimata.harisma.entity.payroll.*;


/**
 *
 * @author  emerliana
 */
public class PstOvt_Type extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    
        public static final  String TBL_OVT_TYPE = "pay_ovt_type";//"PAY_OVT_TYPE";
     
        public static final  int FLD_OVT_TYPE_ID = 0;
	public static final  int FLD_OVT_TYPE_CODE = 1;
	public static final  int FLD_TYPE_OF_DAY = 2;
	public static final  int FLD_DESCRIPTION  = 3;
	public static final  int FLD_STD_WORK_HOUR_BEGIN  = 4;
        public static final  int FLD_STD_WORK_HOUR_END  = 5;
        public static final  int FLD_OWRITE_BY_SCHDL  = 6;
        public static final  int FLD_EMP_LEVEL_MIN  = 7;
        public static final  int FLD_EMP_LEVEL_MAX  = 8;
        public static final  int FLD_MASTER_LEVEL_MIN  = 9;
        public static final  int FLD_MASTER_LEVEL_MAX  = 10;
        
         public static final  String[] fieldNames = {
		"OVT_TYPE_ID",
		"OVT_TYPE_CODE",
		"TYPE_OF_DAY",
		"DESCRIPTION",
                "STD_WORK_HOUR_BEGIN",
                "STD_WORK_HOUR_END",
                "OWRITE_BY_SCHDL",
                "EMP_LEVEL_MIN",
                "EMP_LEVEL_MAX",
                "MASTER_LEVEL_ID_MIN",
                "MASTER_LEVEL_ID_MAX",
                
	};
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_INT,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_LONG,
                TYPE_LONG
                
         };
         
          public static final int WORKING_DAY = 1;
          public static final int HOLIDAY = 2;
          public static final int DP_WORKING_DAY = 3;
          public static final int DP_HOLIDAY = 4;
          public static final int END_OF_YEAR = 5;
          public static final int SCHEDULE_OFF = 6;
          public static final int DP_END_OF_YEAR = 7;
          public static final int DP_SCHEDULE_OFF = 8;
          
          public static final int VARIABLE_NILAI_PENGURANG_OT_ASST_MANAGER = 2;
          

          public static final String[]nameOvt = {
                "--select--","Working Day","Holiday","DP on Working Day","DP on Public Holiday", "Overtime End of Year", "Overtime Schedule Off", "DP End of Year", "DP Schedule Off"
          };
          public static final String[]nameOvtIndonesia = {
                "--pilih--","Hari kerja","Libur nasional","DP di hari kerja","DP di libur nasional", "Lembur akir tahun", "Lembur jadwal libur", "DP akhir tahun", "DP Jadwal libur"
          };
          

           public static final int NOT_BY_SCHEDULE = 0;
           public static final int BY_SCHEDULE = 1;

           public static final String[]scheduleIndonesia = {
              "Tidak",
              "Ya"
           };

           public static final String[]schedule = {
              "No",
              "Yes"
           };
           
         
         public PstOvt_Type(){
	}
         
         public PstOvt_Type(int i) throws DBException {
		super(new PstOvt_Type()); 
	}
         
         public PstOvt_Type(String sOid) throws DBException { 
		super(new PstOvt_Type(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}
        
        public PstOvt_Type(long lOid) throws DBException { 
		super(new PstOvt_Type(0)); 
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
        
     public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length; 
    }
    
    public int[] getFieldTypes() {
        return fieldTypes; 
    }
    
    public String getPersistentName() {
        return new PstOvt_Type().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_OVT_TYPE;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Ovt_Type) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Ovt_Type) ent); 
    }
    
     
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
                throw new DBException(this,DBException.RECORD_NOT_FOUND); 
        } 
        return deleteExc(ent.getOID()); 
    }    
    
    public long fetchExc(Entity ent) throws Exception {
        Ovt_Type ovt_Type = fetchExc(ent.getOID());
        ent = (Entity) ovt_Type;
        return ovt_Type.getOID();
    } 
    
    public static Ovt_Type fetchExc(long oid) throws DBException{ 
		try{ 
			Ovt_Type ovt_Type = new Ovt_Type();
			PstOvt_Type pstOvt_Type = new PstOvt_Type(oid); 
			ovt_Type.setOID(oid);

			//schedulesymbol.setScheduleCategoryId(pstScheduleSymbol.getlong(FLD_SCHEDULE_CATEGORY_ID));
                        ovt_Type.setDescription(pstOvt_Type.getString(FLD_DESCRIPTION));
                        ovt_Type.setOvt_Type_Code(pstOvt_Type.getString(FLD_OVT_TYPE_CODE));
                        ovt_Type.setOwrite_by_schdl(pstOvt_Type.getInt(FLD_OWRITE_BY_SCHDL));
                        ovt_Type.setStd_work_hour_begin(pstOvt_Type.getDate(FLD_STD_WORK_HOUR_BEGIN));
                        ovt_Type.setStd_work_hour_end(pstOvt_Type.getDate(FLD_STD_WORK_HOUR_END));
                        ovt_Type.setType_of_day(pstOvt_Type.getInt(FLD_TYPE_OF_DAY));
                        ovt_Type.setEmpLevelMin(pstOvt_Type.getInt(FLD_EMP_LEVEL_MIN));
                        ovt_Type.setEmpLevelMax(pstOvt_Type.getInt(FLD_EMP_LEVEL_MAX));
                        ovt_Type.setMasterLevelMin(pstOvt_Type.getLong(FLD_MASTER_LEVEL_MIN));
                        ovt_Type.setMasterLevelMax(pstOvt_Type.getLong(FLD_MASTER_LEVEL_MAX));
                        
			return ovt_Type; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Type(0),DBException.UNKNOWN); 
		} 
	}
    
    public static long insertExc(Ovt_Type ovt_Type) throws DBException{ 
		try{ 
			PstOvt_Type pstOvt_Type = new PstOvt_Type(0);

			//pstScheduleSymbol.setLong(FLD_SCHEDULE_CATEGORY_ID, schedulesymbol.getScheduleCategoryId());
                        pstOvt_Type.setString(FLD_DESCRIPTION, ovt_Type.getDescription());
                        pstOvt_Type.setString(FLD_OVT_TYPE_CODE, ovt_Type.getOvt_Type_Code());
                        pstOvt_Type.setInt(FLD_OWRITE_BY_SCHDL, ovt_Type.getOwrite_by_schdl());
                        pstOvt_Type.setDate(FLD_STD_WORK_HOUR_BEGIN, ovt_Type.getStd_work_hour_begin());
                        pstOvt_Type.setDate(FLD_STD_WORK_HOUR_END, ovt_Type.getStd_work_hour_end());
                        pstOvt_Type.setInt(FLD_TYPE_OF_DAY, ovt_Type.getType_of_day());
                        if(ovt_Type.getEmpLevelMin()> ovt_Type.getEmpLevelMax()){
                            int temp = ovt_Type.getEmpLevelMin();
                            ovt_Type.setEmpLevelMin(ovt_Type.getEmpLevelMax());
                            ovt_Type.setEmpLevelMax(temp);
                        }
                        pstOvt_Type.setInt(FLD_EMP_LEVEL_MIN, ovt_Type.getEmpLevelMin());
                        pstOvt_Type.setInt(FLD_EMP_LEVEL_MAX, ovt_Type.getEmpLevelMax());
                        pstOvt_Type.setLong(FLD_MASTER_LEVEL_MIN, ovt_Type.getMasterLevelMin());
                        pstOvt_Type.setLong(FLD_MASTER_LEVEL_MAX, ovt_Type.getMasterLevelMax());
			

			pstOvt_Type.insert(); 
			ovt_Type.setOID(pstOvt_Type.getlong(FLD_OVT_TYPE_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Type(0),DBException.UNKNOWN); 
		}
		return ovt_Type.getOID();
	}
    
    
    public static long updateExc(Ovt_Type ovt_Type) throws DBException{ 
		try{ 
			if(ovt_Type.getOID() != 0){ 
				PstOvt_Type pstOvt_Type = new PstOvt_Type(ovt_Type.getOID());

				pstOvt_Type.setString(FLD_DESCRIPTION, ovt_Type.getDescription());
                                pstOvt_Type.setString(FLD_OVT_TYPE_CODE, ovt_Type.getOvt_Type_Code());
                                pstOvt_Type.setInt(FLD_OWRITE_BY_SCHDL, ovt_Type.getOwrite_by_schdl());
                                pstOvt_Type.setDate(FLD_STD_WORK_HOUR_BEGIN, ovt_Type.getStd_work_hour_begin());
                                pstOvt_Type.setDate(FLD_STD_WORK_HOUR_END, ovt_Type.getStd_work_hour_end());
                                pstOvt_Type.setInt(FLD_TYPE_OF_DAY, ovt_Type.getType_of_day());
                                if(ovt_Type.getEmpLevelMin()> ovt_Type.getEmpLevelMax()){
                                    int temp = ovt_Type.getEmpLevelMin();
                                    ovt_Type.setEmpLevelMin(ovt_Type.getEmpLevelMax());
                                    ovt_Type.setEmpLevelMax(temp);
                                }                                
                                pstOvt_Type.setInt(FLD_EMP_LEVEL_MIN, ovt_Type.getEmpLevelMin());
                                pstOvt_Type.setInt(FLD_EMP_LEVEL_MAX, ovt_Type.getEmpLevelMax());
                                pstOvt_Type.setLong(FLD_MASTER_LEVEL_MIN, ovt_Type.getMasterLevelMin());
                                pstOvt_Type.setLong(FLD_MASTER_LEVEL_MAX, ovt_Type.getMasterLevelMax());

				pstOvt_Type.update(); 
				return ovt_Type.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Type(0),DBException.UNKNOWN); 
		}
		return 0;
	}
    
    public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstOvt_Type pstOvt_Type = new PstOvt_Type(oid);
			pstOvt_Type.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstOvt_Type(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_OVT_TYPE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql : " + sql);
			dbrs = DBHandler.execQueryResult(sql); 
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				Ovt_Type ovt_Type = new Ovt_Type();
				resultToObject(rs, ovt_Type);
				lists.add(ovt_Type);
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
    
    public static Vector listWithIndex(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_OVT_TYPE; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
//                        System.out.println("sql : " + sql);
			dbrs = DBHandler.execQueryResult(sql); 
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				Ovt_Type ovt_Type = new Ovt_Type();
				resultToObject(rs, ovt_Type);                                
                                Vector<Ovt_Idx> listIdx = PstOvt_Idx.list(0, 100,
                                        PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+"=\""+ovt_Type.getOvt_Type_Code()+"\"" , 
                                        PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_FROM] );                                
                                ovt_Type.setOvIndex(listIdx);                                
				lists.add(ovt_Type);
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
        
    
    
    private static void resultToObject(ResultSet rs, Ovt_Type ovt_Type){
		try{
			ovt_Type.setOID(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_ID]));
                        ovt_Type.setDescription(rs.getString(PstOvt_Type.fieldNames[PstOvt_Type.FLD_DESCRIPTION]));
                        ovt_Type.setOvt_Type_Code(rs.getString(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]));
                        ovt_Type.setOwrite_by_schdl(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OWRITE_BY_SCHDL]));
                        ovt_Type.setEmpLevelMin(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_EMP_LEVEL_MIN]));
                        ovt_Type.setEmpLevelMax(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_EMP_LEVEL_MAX]));
                        ovt_Type.setMasterLevelMin(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_MASTER_LEVEL_MIN]));
                        ovt_Type.setMasterLevelMax(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_MASTER_LEVEL_MAX]));
                        
                        Date tm_begin = DBHandler.convertDate(rs.getDate(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_BEGIN]),rs.getTime(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_BEGIN]));
                        ovt_Type.setStd_work_hour_begin(tm_begin);
                        
                        Date tm_end = DBHandler.convertDate(rs.getDate(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_END]),rs.getTime(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_END]));
                        ovt_Type.setStd_work_hour_end(tm_end);
                        
                        ovt_Type.setType_of_day(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY]));
			
		}catch(Exception e){ }
	}
    
    
    public static boolean checkOID(long ovt_TypeId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_OVT_TYPE + " WHERE " + 
						PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_ID] + " = " + ovt_TypeId;

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
			String sql = "SELECT COUNT("+ PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_ID] + ") FROM " + TBL_OVT_TYPE;
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
    
    
     public static int findLimitStart( long oid, int recordToGet, String whereClause,String orderClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	  Ovt_Type ovt_Type = (Ovt_Type)list.get(ls);
				   if(oid == ovt_Type.getOID())
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
   
   
   
   /**
    * this method used to get overtime idx
    * @param = dayOfType
    * created by Yunny
    */
   public static Vector getOvertimeType(int dayOfType,String groupBy){
        Vector lists = new Vector(); 
        DBResultSet dbrs = null;
        try {
                 String sql = "SELECT * FROM " + PstOvt_Idx.TBL_OVT_IDX + " as idx " + 
                           " INNER JOIN "+PstOvt_Type.TBL_OVT_TYPE+ " as type"+
                           " ON idx."+PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+
                           " = type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]+
                           " WHERE type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY]+"="+dayOfType;
                 
                 if(groupBy.length() > 0){
                    sql = sql + " GROUP BY "+groupBy;
                 }
                System.out.println("sql getOvertimeType  "+sql);
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                        Vector vect = new Vector();
                        Ovt_Idx ovtIdx = new Ovt_Idx();
                        Ovt_Type ovtType = new Ovt_Type ();
                        
                        ovtIdx.setOID(rs.getLong(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX_ID]));
                        ovtIdx.setHour_from(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_FROM]));
                        ovtIdx.setHour_to(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_HOUR_TO]));
                        ovtIdx.setOvt_idx(rs.getDouble(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_IDX]));
                        ovtIdx.setOvt_type_code(rs.getString(PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]));
                        vect.add(ovtIdx);
                        
                        ovtType.setOID(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_ID]));
                        ovtType.setOvt_Type_Code(rs.getString(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE]));
                        ovtType.setType_of_day(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY]));
                        ovtType.setDescription(rs.getString(PstOvt_Type.fieldNames[PstOvt_Type.FLD_DESCRIPTION]));
                        ovtType.setStd_work_hour_begin(rs.getDate(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_BEGIN]));
                        ovtType.setStd_work_hour_end(rs.getDate(PstOvt_Type.fieldNames[PstOvt_Type.FLD_STD_WORK_HOUR_END]));
                        ovtType.setOwrite_by_schdl(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_OWRITE_BY_SCHDL]));
                        ovtType.setEmpLevelMin(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_EMP_LEVEL_MIN]));
                        ovtType.setEmpLevelMax(rs.getInt(PstOvt_Type.fieldNames[PstOvt_Type.FLD_EMP_LEVEL_MAX]));
                        ovtType.setMasterLevelMin(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_MASTER_LEVEL_MIN]));
                        ovtType.setMasterLevelMax(rs.getLong(PstOvt_Type.fieldNames[PstOvt_Type.FLD_MASTER_LEVEL_MAX]));
                        
                        vect.add(ovtType);
                        
                        lists.add(vect);

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
   
  
   
   public static long getIHour(Date timeOut, Date timeOutSch){
    long hourTimeOut = timeOut.getHours();
    
    System.out.println("hourTimeOut:::::::::::::::::::::::::"+hourTimeOut);
    long hourTimeSch = timeOutSch.getHours();
    System.out.println("hourTimeSch:::::::::::::::::::::::::"+hourTimeSch);
    long totHour = 0;
    if(hourTimeOut < hourTimeSch){
        hourTimeSch = hourTimeSch - 1;
        totHour = hourTimeSch - hourTimeOut;
    }else{
        totHour = hourTimeOut - hourTimeSch;
    }
    
    if(hourTimeOut < hourTimeSch){
        if(totHour > 0)
            totHour = -(totHour);
    }
    
    return totHour;
}

public static long getiMin(Date timeOut, Date timeOutSch){
    long minTimeOut = timeOut.getMinutes();
    long minTimeSch = timeOutSch.getMinutes();
    long hourTimeOut = timeOut.getHours();
    long hourTimeSch = timeOutSch.getHours();
    long totMin = 0;
    if(hourTimeOut < hourTimeSch){
        minTimeSch = minTimeSch + 60;
        totMin = minTimeSch - minTimeOut;
    }else{
        totMin = minTimeOut - minTimeSch;
    }
    
    if(hourTimeOut < hourTimeSch){
        System.out.println("masuk ke yang lebih kurang nich");
        if(totMin > 0)
           totMin = -(totMin);
    }
    
    return totMin;
}

 /**
    * this method used to get start time of holiday ovt
    * created by Yunny
    */
    public static String getStartTimeHL(){
        DBResultSet dbrs = null;
        String result = "";
        try{
	      String sql = "SELECT STD_WORK_HOUR_BEGIN FROM " + TBL_OVT_TYPE + " WHERE " + 
                            PstOvt_Type.fieldNames[PstOvt_Type.FLD_TYPE_OF_DAY] + " = "+PstOvt_Type.HOLIDAY;
               
              dbrs = DBHandler.execQueryResult(sql);
              ResultSet rs = dbrs.getResultSet();
               //System.out.println("sql getComponentName  "+sql);
              while(rs.next()) { 
                  result = rs.getString(1);}
              rs.close();
        }catch(Exception e){
            System.out.println("Error");
        }
        return result;
	}
   
   
  public static void main(String args[]) {
        //long result = getPeriodIdBySelectedDate(new Date(104,5,31));
        //long result = getPeriodIdJustBefore(504404240112808778L);
        //System.out.println("result : " + result);
       /*Date objDate = new Date();
        Ovt_Type ovt_type = new Ovt_Type();
        ovt_type.setDescription("waktu cepat berlalu");
        ovt_type.setOvt_Type_Code("WD1");
        ovt_type.setStd_work_hour_begin(objDate);
        ovt_type.setStd_work_hour_end(objDate);
        
        try{
            PstOvt_Type.insertExc(ovt_type);
        }catch(Exception e){
            System.out.println("Err"+e.toString());
        }*/
         
        String dtTimeOut = "21:10";
        String dtTimeOutSch = "24:00";
        Date dtTimeOut1 = Formater.formatDate(dtTimeOut, "HH:mm");
        Date dtTimeOutSch1 = Formater.formatDate(dtTimeOutSch, "HH:mm");
        
        long iHour = getIHour(dtTimeOut1, dtTimeOutSch1);
        long iMint = getiMin(dtTimeOut1, dtTimeOutSch1);
        
        System.out.println("iHou11122222r::::::::::::::::::::::::::::::::::::::::::::::"+iHour);
        System.out.println("iMinute1111::::::::::::::::::::::::::::::::::::::::::::::"+iMint);
        
    }
    
    
  /**
   * Get employee level of position ( staff to GM ) map to type of index which contains overtime index detail
   *     Level => Type of Overtime
   *                  -> Overtime Index 0
   *                  -> Overtime Index 1
   * @param level
   * @param typeOfDay
   * @return 
   */ 
  public static Hashtable<String, Ovt_Type> getLevelOvIdxMap(int typeOfDay){
       Hashtable<String, Ovt_Type> levelToOvType = new Hashtable();
       String where = ""+fieldNames[FLD_TYPE_OF_DAY]+"="+typeOfDay;
       Vector listOv = listWithIndex(0,500,where, "");
       if(listOv!=null && listOv.size()>0){
           for(int i=0;i< listOv.size();i++){
               Ovt_Type ovType = (Ovt_Type) listOv.get(i);
               if(ovType.getEmpLevelMin()>ovType.getEmpLevelMax()){
                   int temp=ovType.getEmpLevelMin();
                   ovType.setEmpLevelMin(ovType.getEmpLevelMax());
                   ovType.setEmpLevelMax(temp);
               }
               for(int x=ovType.getEmpLevelMin(); x <= ovType.getEmpLevelMax();x++){
                    levelToOvType.put(""+x, ovType); 
               }               
           }
       }
       return levelToOvType;
   }
  
    public static Hashtable<String, Ovt_Type> getLevelOvMapLevelOID(int typeOfDay){
       Hashtable<String, Ovt_Type> levelToOvType = new Hashtable();
       String where = ""+fieldNames[FLD_TYPE_OF_DAY]+"="+typeOfDay;
       Vector listOv = listWithIndex(0,500,where, "");
       Vector listMasterLevel= PstLevel.list(0, 0, "", PstLevel.fieldNames[PstLevel.FLD_LEVEL_RANK]);
       Hashtable<String, Level>  hLevel = PstLevel.toHashtable(listMasterLevel);
       if(listOv!=null && listOv.size()>0){
           for(int i=0;i< listOv.size();i++){
             try{  Ovt_Type ovType = (Ovt_Type) listOv.get(i);
               Level levelMin = hLevel.get(""+ovType.getMasterLevelMin());
               Level levelMax = hLevel.get(""+ovType.getMasterLevelMax());
               if(levelMin==null || levelMax==null){ continue;}
               if(levelMin.getLevelRank() > levelMax.getLevelRank()){
                   long temp=ovType.getEmpLevelMin();
                   ovType.setMasterLevelMin(levelMax.getOID());
                   ovType.setMasterLevelMax(levelMin.getOID());
                   levelMin = hLevel.get(""+ovType.getMasterLevelMin());
                   levelMax = hLevel.get(""+ovType.getMasterLevelMax());
               }
                       
               boolean startPut=false;
               boolean endPut = false;
               for(int x=0; (x <listMasterLevel.size() ) && !endPut ;x++){
                    Level level = (Level) listMasterLevel.get(x);
                    if(level==null)
                        continue;
                    if(startPut && level.getOID() == levelMax.getOID()  ){
                        endPut = true;
                    }
                    if(level.getOID()==levelMin.getOID()){
                        startPut= true;
                    }
                    if(startPut){
                         levelToOvType.put(""+level.getOID(), ovType); 
                    }
               }               
           }
             catch(Exception exc){
                 System.out.println(exc);
             }
       }
       }
       return levelToOvType;
   }
    
}
