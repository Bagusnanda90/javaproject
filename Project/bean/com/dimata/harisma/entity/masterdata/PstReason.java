/*
 * PstReason.java
 *
 * Created on June 20, 2007, 4:27 PM
 */

package com.dimata.harisma.entity.masterdata;

/* package java */ 

import java.sql.*;
import java.util.*;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.system.entity.system.PstSystemProperty;

/**
 *
 * @author  yunny
 */
public class PstReason extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 
    
    public static final  String TBL_HR_REASON = "hr_reason";//"HR_REASON";
    
    public static final  int FLD_REASON_ID      = 0;
    public static final  int FLD_NO             = 1;
    public static final  int FLD_REASON         = 2;
    public static final  int FLD_DESCRIPTION    = 3;
    public static final  int FLD_SCHEDULE_ID    = 4;
    //update by satrya 2012-10-19
    public static final  int FLD_REASON_CODE    = 5; 
    //UPDATE BY SATRYA 20130203
    public static final  int FLD_REASON_TIME    = 6; 
    //update by satrya 2014-04-21
    public static final  int FLD_FLAG_IN_PAY_INPUT    = 7; 
    public static final  int FLD_NUMBER_OF_SHOW    = 8; 
    public static final  int FLD_COUNT_ABLE    = 9; 
    
    public static final  String[] fieldNames = {
		"REASON_ID",
                "NO",
		"REASON",
                "DESCRIPTION",
                "SCHEDULE_ID",
                //update by satrya 2012-10-19
                "REASON_CODE",
                //update by satrya 2013-02-03
                "REASON_TIME",
                "FLAG_IN_PAY_INPUT",
                "NUMBER_OF_SHOW",
                "COUNT_ABLE"
	 }; 
         
    public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_INT,
		TYPE_STRING,
                TYPE_STRING,
                TYPE_LONG,
               //update by satrya 2012-10-19
                TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT
                
	 };
         
      //montly or yearly
    public static final int BLANK 		= 0;   
    public static final int MONTLY 		= 1;
    public static final int YEARLY 		= 2;

    public static final String[] periodKey = {"","Monthly","Yearly"};
    public static final int[] periodValue = {0,1,2};

    
    //update by satrya 20130203
    public static final int REASON_TIME_NO = 0;
    public static final int REASON_TIME_YES = 1;
    public static final String[] timeReasonKey = {"no","yes"};
    public static final int[] timeReasonValue = {REASON_TIME_NO,REASON_TIME_YES};
    //update by priska 20150213
    public static final int YES = 0;
    public static final int NO = 1;
    public static final String[] countAbleKey = {"yes","no"};
    public static final int[] countAbleValue = {YES,NO};
    //update by satrya 20140421
    public static final int SHOW_REASON_IN_PAY_INPUT_NO = 0;
    public static final int SHOW_REASON_IN_PAY_INPUT_YES = 1;
    public static final String[] showReasonPayInputKey = {"no","yes"};
    public static final int[] showReasonPayInputValue = {SHOW_REASON_IN_PAY_INPUT_NO,SHOW_REASON_IN_PAY_INPUT_YES};

    /**
     * getTimeReasonKey: "yes","no";
     * @return 
     */
    public static Vector getTimeReasonKey(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < timeReasonKey.length ; i++){
            vct.add(timeReasonKey[i]);
        }
        return vct;
    }
    /**
     * getTimeReasonValue: ini untuk melihat 0,1
     * @return 
     */    
    public static Vector getTimeReasonValue(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < timeReasonValue.length ; i++){
            vct.add(Integer.toString(i));
        }
        return vct;
    }
    
     /**
     * getTimeReasonKey: "yes","no";
     * @return 
     */
    public static Vector getCountAbleKey(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < countAbleKey.length ; i++){
            vct.add(countAbleKey[i]);
        }
        return vct;
    }
    /**
     * getTimeReasonValue: ini untuk melihat 0,1
     * @return 
     */    
    public static Vector getCountAbleValue(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < countAbleValue.length ; i++){
            vct.add(Integer.toString(i));
        }
        return vct;
    }
    
    /**
     * getShowPayInputReasonKey: "yes","no";
     * @return 
     */
    public static Vector getShowInReasonKey(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < showReasonPayInputKey.length ; i++){
            vct.add(showReasonPayInputKey[i]);
        }
        return vct;
    }
    /**
     * getShowPayInputReasonValue: ini untuk melihat 0,1
     * @return 
     */    
    public static Vector getShowInReasonValue(){
        Vector vct = new Vector(1,1);
        for(int i =0 ; i < showReasonPayInputValue.length ; i++){
            vct.add(Integer.toString(i));
        }
        return vct;
    }
    /** Creates a new instance of PstReason */
    public PstReason() {
    }
    
    public PstReason(int i) throws DBException { 
		super(new PstReason());
    }
    
    public PstReason(String sOid) throws DBException { 
		super(new PstReason(0));
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
    }
   
    public PstReason(long lOid) throws DBException { 
		super(new PstReason(0));
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
    
    
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
    }
    
    public long fetchExc(Entity ent) throws Exception {
        Reason reason= fetchExc(ent.getOID());
	ent = (Entity)reason;
	return reason.getOID();
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
        return new PstReason().getClass().getName();
    }
    
    public String getTableName() {
        return TBL_HR_REASON;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((Reason) ent);
    }
    
    public long updateExc(Entity ent) throws Exception {
        return updateExc((Reason) ent);
    }
    
    public static Reason fetchExc(long oid) throws DBException{
		try{ 
			Reason reason = new Reason();
			PstReason pstReason = new PstReason(oid);
			reason.setOID(oid);

			reason.setNo(pstReason.getInt(FLD_NO));
                        reason.setReason(pstReason.getString(FLD_REASON));
                        reason.setDescription(pstReason.getString(FLD_DESCRIPTION));
                        reason.setScheduleId(pstReason.getlong(FLD_SCHEDULE_ID));
                        ///update by satrya 2012-10-19
                        reason.setKodeReason(pstReason.getString(FLD_REASON_CODE));
			//update by satrya 20130203
                        reason.setTimeReason(pstReason.getInt(FLD_REASON_TIME));
                        
                        //update by satrya 2014-04-21
                        reason.setFlagShowInPayInput(pstReason.getInt(FLD_FLAG_IN_PAY_INPUT));
                        //add by priska 201501113
                        reason.setCountAble(pstReason.getInt(FLD_COUNT_ABLE));
			return reason;
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstReason(0),DBException.UNKNOWN);
		} 
	}
    
    public static long insertExc(Reason reason) throws DBException{
		try{ 
			PstReason pstReason = new PstReason(0);

                        pstReason.setInt(FLD_NO, reason.getNo());
			pstReason.setString(FLD_REASON, reason.getReason());
                        pstReason.setString(FLD_DESCRIPTION, reason.getDescription());
                        pstReason.setLong(FLD_SCHEDULE_ID, reason.getScheduleId());
			//update by satrya 2012-10-19
                        pstReason.setString(FLD_REASON_CODE, reason.getKodeReason());
                        //update by satrya 20130203
                        pstReason.setInt((FLD_REASON_TIME), reason.getTimeReason());
                        //update by satrya 2014-04-21
                        pstReason.setInt((FLD_FLAG_IN_PAY_INPUT), reason.getFlagShowInPayInput());
                        //update by priska 2015-11-12
                        pstReason.setInt((FLD_NUMBER_OF_SHOW), reason.getNumberOfShow());
                        pstReason.setInt((FLD_COUNT_ABLE), reason.getCountAble());
			pstReason.insert();
			reason.setOID(pstReason.getlong(FLD_REASON_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstDivision(0),DBException.UNKNOWN);
		}
		return reason.getOID();
	}
    
    
    public static long updateExc(Reason reason) throws DBException{
		try{ 
			if(reason.getOID() != 0){
				PstReason pstReason = new PstReason(reason.getOID());

				pstReason.setInt(FLD_NO, reason.getNo());
                                pstReason.setString(FLD_REASON, reason.getReason());
                                pstReason.setString(FLD_DESCRIPTION, reason.getDescription());
                                pstReason.setLong(FLD_SCHEDULE_ID, reason.getScheduleId());
                                //update by satrya 2012-10-19
                                pstReason.setString(FLD_REASON_CODE, reason.getKodeReason());
                                //update by satrya 20130203
                                pstReason.setInt((FLD_REASON_TIME), reason.getTimeReason());
                                //update by satrya 2014-04-21
                                pstReason.setInt((FLD_FLAG_IN_PAY_INPUT), reason.getFlagShowInPayInput());
                                pstReason.setInt((FLD_NUMBER_OF_SHOW), reason.getNumberOfShow());
                                pstReason.setInt((FLD_COUNT_ABLE), reason.getCountAble());
				pstReason.update();
				return reason.getOID();
			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstReason(0),DBException.UNKNOWN);
		}
		return 0;
	}
    
    public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstReason pstReason = new PstReason(oid);
			pstReason.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstReason(0),DBException.UNKNOWN);
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
			String sql = "SELECT * FROM " + TBL_HR_REASON;
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
				Reason reason = new Reason();
				resultToObject(rs, reason);
				lists.add(reason);
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
     * create by satrya 2013-04-27
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
    public static Vector listReason(int limitStart,int recordToGet, String whereClause, String order){
		
		DBResultSet dbrs = null;
                Vector list = new Vector();
		try {
			String sql = "SELECT * FROM " + TBL_HR_REASON;
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
				Reason reason = new Reason();
                                resultToObject(rs, reason);
                                list.add(reason);
			}
			rs.close();
			return list;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Vector();
	}
    
    public static void resultToObject(ResultSet rs, Reason reason){
		try{
			reason.setOID(rs.getLong(PstReason.fieldNames[PstReason.FLD_REASON_ID]));
                        reason.setNo(rs.getInt(PstReason.fieldNames[PstReason.FLD_NO]));
			reason.setReason(rs.getString(PstReason.fieldNames[PstReason.FLD_REASON]));
			reason.setDescription(rs.getString(PstReason.fieldNames[PstReason.FLD_DESCRIPTION]));
                        reason.setScheduleId(rs.getLong(PstReason.fieldNames[PstReason.FLD_SCHEDULE_ID]));
			//update by satrya 2012-10-19
                        reason.setKodeReason(rs.getString(PstReason.fieldNames[PstReason.FLD_REASON_CODE]));
                        //update by satrya 2013-02-03
                        reason.setTimeReason(rs.getInt(PstReason.fieldNames[PstReason.FLD_REASON_TIME]));
                        //update by priska 2015-11-12
                        reason.setNumberOfShow(rs.getInt(PstReason.fieldNames[PstReason.FLD_NUMBER_OF_SHOW]));
                        
                        //update by satrya 2014-04-21
                        reason.setFlagShowInPayInput(rs.getInt(PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT]));
                        reason.setCountAble(rs.getInt(PstReason.fieldNames[PstReason.FLD_COUNT_ABLE]));
		}catch(Exception e){ 
                    System.out.println("Exception resultToObject"+e);
                }
	}
    
    public static boolean checkOID(long reasonId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_REASON + " WHERE " +
						PstReason.fieldNames[PstReason.FLD_REASON_ID] + " = " + reasonId;

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
			String sql = "SELECT COUNT("+ PstReason.fieldNames[PstReason.FLD_REASON_ID] + ") FROM " + TBL_HR_REASON;
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
    
    public static int getMaxNo(String whereClause){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT MAX("+ PstReason.fieldNames[PstReason.FLD_NO] + ") FROM " + TBL_HR_REASON;
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
    
    
    /**
    * this method used to get the absence reason
    * created by Yunny
    */
    public static String getStrReason(int intReason){
        DBResultSet dbrs = null;
        String result = "";
        try{
	       String sql = "SELECT REASON FROM " + TBL_HR_REASON + " WHERE " + 
                             PstReason.fieldNames[PstReason.FLD_NO] + " = " + intReason;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("getStrReason"+sql);
                    while(rs.next()) {
                        result = rs.getString(1); 
                    }
                    rs.close();
                    
        }catch(Exception e){
            System.out.println("Exception Error getStrReason: "+ e.toString());
        }
        return result;
	}
    /**
     * Keterangan : melihat resaon code
     * @param intReason
     * @return 
     */
        public static String getStrReasonCode(int intReason){
        DBResultSet dbrs = null;
        String result = "";
        try{
	       String sql = "SELECT REASON_CODE FROM " + TBL_HR_REASON + " WHERE " + 
                             PstReason.fieldNames[PstReason.FLD_NO] + " = " + intReason;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("getStrReason"+sql);
                    while(rs.next()) {
                        result = rs.getString(1); 
                    }
                    rs.close();
                    
        }catch(Exception e){
            System.out.println("Exception Error getStrReasonCode: "+ e.toString());
        }
        return result;
	}
    
         /**
     * Keterangan : melihat resaon code
     * @param intReason
     * @return 
     */
        public static int getReasonByNo(int no){
        DBResultSet dbrs = null;
        if (no == 0){
            return 0;
        }
        int result = 0;
        try{
	       String sql = "SELECT "+PstReason.fieldNames[PstReason.FLD_COUNT_ABLE]+" FROM " + TBL_HR_REASON + " WHERE " + 
                             PstReason.fieldNames[PstReason.FLD_NO] + " = " + no;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("getStrReason"+sql);
                    while(rs.next()) {
                        result = rs.getInt(1); 
                    }
                    rs.close();
                    
        }catch(Exception e){
            System.out.println("Exception Error getStrReasonCode: "+ e.toString());
        }
        return result;
	}
        
         public static int getReasonCountAbleByNo(int no){
        DBResultSet dbrs = null;
        if (no == 0){
            return 0;
        }
        int result = 0;
        try{
	       String sql = "SELECT "+PstReason.fieldNames[PstReason.FLD_COUNT_ABLE]+" FROM " + TBL_HR_REASON + " WHERE " + 
                             PstReason.fieldNames[PstReason.FLD_NO] + " = " + no;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("getStrReason"+sql);
                    while(rs.next()) {
                        result = rs.getInt(1); 
                    }
                    rs.close();
                    
        }catch(Exception e){
            System.out.println("Exception Error getStrReasonCode: "+ e.toString());
        }
        return result;
	}
     /**
    * this method used to get the absence reason to report
    * created by Yunny
    * this method created expectialy to Intimas
    */
    public static String getStrReasonReport(int intReason,long empPosition,long satpamCategory){
        DBResultSet dbrs = null;
        String result = "";
        
        try{
	       String sql = "SELECT REASON FROM " + TBL_HR_REASON + " WHERE " + 
                             PstReason.fieldNames[PstReason.FLD_NO] + " = " + intReason;

                    dbrs = DBHandler.execQueryResult(sql);
                    ResultSet rs = dbrs.getResultSet();
                    //System.out.println("getStrReason"+sql);
                    while(rs.next()) { result = rs.getString(1);
                    //libur jadwal
                    if(intReason==16){
                     if(satpamCategory==empPosition){
                        result = "V";
                     }else{
                        result = "L";
                     }
                    }
                    //sakit
                    else if(intReason==1){
                        result = "S";
                    }
                    //karywan baru
                    else if(intReason==8){
                        result = "-";
                    }
                    //alpa
                    else if(intReason==0){
                        result = "A";
                    }
                    else if(intReason==3 || intReason==4 || intReason==5 || intReason==12){
                        result = "C";
                    }
                    else if(intReason==9 || intReason==14 ||intReason==6){
                        result = "V";
                    }
                    else if(intReason==2 || intReason==15){
                        result = "I";
                    }
                    else if(intReason==17){
                        result = "HR";
                    }
                    else {
                        result = "X";
                    }
                    }
                    rs.close();
        }catch(Exception e){
            System.out.println("Error");
        }
        return result;
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
			  	   Reason reason = (Reason)list.get(ls);
				   if(oid == reason.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
    public static boolean checkMaster(long oid)
    {
        if(PstEmployee.checkPosition(oid))
            return true;
        else{
            if(PstCareerPath.checkPosition(oid))
            	return true;
            else
                return false;

        }
    }
    
    public static boolean isNoUsed(int no, long oid){
        boolean isUsed = false;
        String whereClause = PstReason.fieldNames[PstReason.FLD_NO]
                +" = "+no;
        Vector vList = new Vector(1,1);
        vList = list(0, 0, whereClause, fieldNames[FLD_NO]);
        if(vList.size()>0){
            Reason reason = new Reason();
            reason = (Reason)vList.get(0);
            if(oid!=reason.getOID()){
                return true;
            }
        }
        return isUsed;
    }
     // update by satrya 2012-08-21
    /**
     * 
     * @param limitStart
     * @param recordToGet
     * @param whereClause
     * @param order
     * @return 
     */
        public static Hashtable<String, String> getReason(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = list(limitStart,recordToGet,whereClause,order);
		Hashtable<String, String> reasonMap= new Hashtable();
		try {
			if(lists !=null && lists.size() > 0){
                           for(int i=0; i<lists.size(); i++){
                               Reason reason = (Reason) lists.get(i);
                               reasonMap.put(""+reason.getNo(), reason.getReason());
                            }
                        }
		}catch(Exception e) {
                        System.out.println(e);
		}
	   return reasonMap;
	}
        
        public static Hashtable<String, String> getKodeReason(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = list(limitStart,recordToGet,whereClause,order);
		Hashtable<String, String> reasonMap= new Hashtable();
		try {
			if(lists !=null && lists.size() > 0){
                           for(int i=0; i<lists.size(); i++){
                               Reason reason = (Reason) lists.get(i);
                               reasonMap.put(""+reason.getNo(), reason.getKodeReason());
                            }
                        }
		}catch(Exception e) {
                        System.out.println(e);
		}
	   return reasonMap;
	}
  
        
        
        //create by ramayu
        public static Hashtable<String, Integer> getCountReason(){
		 DBResultSet dbrs = null;
        
       Hashtable<String, Integer> reason= new Hashtable();
                int idx=-1;
        	try {
			 String sql = "SELECT HR."+PstReason.fieldNames[PstReason.FLD_NO]
                        //+ " , HR."+PstReason.fieldNames[PstReason.FLD_REASON]
                        +" FROM " + TBL_HR_REASON + " AS HR "
                                 ///update by satrya 2013-04-28
                        + " WHERE HR."+PstReason.fieldNames[PstReason.FLD_REASON_TIME]+"="+PstReason.REASON_TIME_YES
                        + " ORDER BY HR."+PstReason.fieldNames[PstReason.FLD_REASON];
                         
			dbrs = DBHandler.execQueryResult(sql); 
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
                            
                         //idx= idx + 1;
                         reason.put(""+rs.getInt(PstReason.fieldNames[PstReason.FLD_NO]), rs.getInt(PstReason.fieldNames[PstReason.FLD_NO]));
                            
                        }
                     
			rs.close();
			return reason;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return new Hashtable();
    	}
   
}


