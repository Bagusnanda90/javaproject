
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;
import java.util.Calendar;
import com.dimata.system.entity.system.SystemProperty;

public class PstLeavePeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

	public static final  String TBL_HR_LEAVE_PERIOD = "hr_leave_period";//"HR_LEAVE_PERIOD";

	public static final  int FLD_LEAVE_PERIOD_ID = 0;
	public static final  int FLD_START_DATE = 1;
	public static final  int FLD_END_DATE = 2;
	public static final  int FLD_STATUS = 3;

	public static final  String[] fieldNames = {
		"LEAVE_PERIOD_ID",
		"START_DATE",
		"END_DATE",
		"STATUS"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_DATE,
		TYPE_DATE,
		TYPE_BOOL
	 };


    public static final  int STATUS_HISTORY = 0;
    public static final  int STATUS_VALID = 1;
    public static String[] statusStr   = {"History", "Valid"};

	public PstLeavePeriod(){
	}

	public PstLeavePeriod(int i) throws DBException { 
		super(new PstLeavePeriod()); 
	}

	public PstLeavePeriod(String sOid) throws DBException { 
		super(new PstLeavePeriod(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstLeavePeriod(long lOid) throws DBException { 
		super(new PstLeavePeriod(0)); 
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
		return TBL_HR_LEAVE_PERIOD;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstLeavePeriod().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{
		LeavePeriod leaveperiod = fetchExc(ent.getOID()); 
		ent = (Entity)leaveperiod; 
		return leaveperiod.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((LeavePeriod) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((LeavePeriod) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static LeavePeriod fetchExc(long oid) throws DBException{ 
		try{ 
			LeavePeriod leaveperiod = new LeavePeriod();
			PstLeavePeriod pstLeavePeriod = new PstLeavePeriod(oid); 
			leaveperiod.setOID(oid);

			leaveperiod.setStartDate(pstLeavePeriod.getDate(FLD_START_DATE));
			leaveperiod.setEndDate(pstLeavePeriod.getDate(FLD_END_DATE));
			leaveperiod.setStatus(pstLeavePeriod.getboolean(FLD_STATUS));

			return leaveperiod; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeavePeriod(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(LeavePeriod leaveperiod) throws DBException{ 
		try{ 
			PstLeavePeriod pstLeavePeriod = new PstLeavePeriod(0);

			pstLeavePeriod.setDate(FLD_START_DATE, leaveperiod.getStartDate());
			pstLeavePeriod.setDate(FLD_END_DATE, leaveperiod.getEndDate());
			pstLeavePeriod.setboolean(FLD_STATUS, leaveperiod.getStatus());

			pstLeavePeriod.insert(); 
			leaveperiod.setOID(pstLeavePeriod.getlong(FLD_LEAVE_PERIOD_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeavePeriod(0),DBException.UNKNOWN); 
		}
		return leaveperiod.getOID();
	}

	public static long updateExc(LeavePeriod leaveperiod) throws DBException{ 
		try{ 
			if(leaveperiod.getOID() != 0){ 
				PstLeavePeriod pstLeavePeriod = new PstLeavePeriod(leaveperiod.getOID());

				pstLeavePeriod.setDate(FLD_START_DATE, leaveperiod.getStartDate());
				pstLeavePeriod.setDate(FLD_END_DATE, leaveperiod.getEndDate());
				pstLeavePeriod.setboolean(FLD_STATUS, leaveperiod.getStatus());

				pstLeavePeriod.update(); 
				return leaveperiod.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeavePeriod(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstLeavePeriod pstLeavePeriod = new PstLeavePeriod(oid);
			pstLeavePeriod.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstLeavePeriod(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_LEAVE_PERIOD; 
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
				LeavePeriod leaveperiod = new LeavePeriod();
				resultToObject(rs, leaveperiod);
				lists.add(leaveperiod);
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

	private static void resultToObject(ResultSet rs, LeavePeriod leaveperiod){
		try{
			leaveperiod.setOID(rs.getLong(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID]));
			leaveperiod.setStartDate(rs.getDate(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]));
			leaveperiod.setEndDate(rs.getDate(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_END_DATE]));
			leaveperiod.setStatus(rs.getBoolean(PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_STATUS]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long leavePeriodId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_LEAVE_PERIOD + " WHERE " + 
						PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] + " = " + leavePeriodId;

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
			String sql = "SELECT COUNT("+ PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] + ") FROM " + TBL_HR_LEAVE_PERIOD;
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
			  	   LeavePeriod leaveperiod = (LeavePeriod)list.get(ls);
				   if(oid == leaveperiod.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}

    /**gadnyana
     * cek leave period exist or not
     * @return
     */
    public static LeavePeriod cekAlreadyExistLeavePeriod(Date stDate)
    {
        LeavePeriod leavePeriod = new LeavePeriod();
        try
        {
            String whereClause = "'"+Formater.formatDate(stDate,"yyyy-MM-dd")+"' BETWEEN "+PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE]+" AND "+PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_END_DATE];
            //System.out.println("whereClause : "+whereClause);
            Vector vectLv = PstLeavePeriod.list(0,0,whereClause,"");
            if(vectLv!=null && vectLv.size()>0)
            {
                leavePeriod = (LeavePeriod)vectLv.get(0);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exc on cekAlreadyExistLeavePeriod : " + e.toString());
        }
        return leavePeriod;
    }

    /**
     * get OID of period object that wrap selectedDate
     * @param selectedDate
     * @return
     * @created by Edhy
     */
    public static long getPeriodIdBySelectedDate(Date selectedDate) {
        long result = 0;
        DBResultSet dbrs = null;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] +
                    " FROM " + TBL_HR_LEAVE_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE] +
                    " AND " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_END_DATE];
            //System.out.println("\tgetPeriodIdBySelectedDate : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    

    /**
     * get period object that wrap selectedDate
     * @param selectedDate
     * @return
     * @created by Edhy
     */
    public static LeavePeriod getLeavePeriodBySelectedDate(Date selectedDate) {
        LeavePeriod result = new LeavePeriod();
        DBResultSet dbrs = null;
        String strDate = "\"" + Formater.formatDate(selectedDate, "yyyy-MM-dd") + "\"";
        try {
            String sql = "SELECT " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_LEAVE_PERIOD_ID] +
                    ", " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE] +
                    ", " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_END_DATE] +
                    ", " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_STATUS] +
                    " FROM " + TBL_HR_LEAVE_PERIOD +
                    " WHERE " + strDate +
                    " BETWEEN " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE] +
                    " AND " + PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_END_DATE];
            
            //System.out.println("\tgetLeavePeriodBySelectedDate : "+sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) 
            {
                result.setOID(rs.getLong(1));
                result.setStartDate(rs.getDate(2));
                result.setEndDate(rs.getDate(3));
                result.setStatus(rs.getBoolean(4));
            }
        }
        catch (Exception e) 
        {
            System.out.println("Exc when getLeavePeriodBySelectedDate : " + e.toString());
            return new LeavePeriod();
        } 
        finally 
        {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    
    
    /** 
     * gadnyana
     * untuk pembuatan leave period
     * @param startDt
     * @param endDt
     * @edited by Edhy
     * algoritma : 
     *  1. ngecek apakah sudah benar endDt > startDt (ini harus)
     *  2. ngecek apakah tahun antara start date = end date sama atau berbeda
     *  3. iterasi pengecekan leave period
     *  4. dalam iterasi, melakukan pengecekan apakah leave periode pada "selected date" sudah ada
     *     klo sudah ada maka "break" sebaliknya "insert new one"
     *  5. jika "insert new one", ngecek apakah tipe leave period yang dipakai (FULL atau HALF)
     */
    public static void generateLeavePeriodObj(Date startDtParam, Date endDtParam) 
    {
        try 
        {
            Date startDt = new Date(startDtParam.getYear(), startDtParam.getMonth(), startDtParam.getDate());
            Date endDt = new Date(endDtParam.getYear(), endDtParam.getMonth(), endDtParam.getDate());
            
            int month1st = startDt.getMonth();
            int month2nd = endDt.getMonth();
            //int monthDiff = month2nd - month1st;
      
            // 1. ngecek apakah sudah benar endDt > startDt (ini harus)
            // endDt > startDt
            if(endDt.after(startDt))
            {
//                System.out.println("endDt.after(startDt)");            
                
                // 2. ngecek apakah tahun antara start date and end date sama atau berbeda
                // tahun start date = end date
                if(startDt.getYear() == endDt.getYear())  
                {
//                    System.out.println("startDt.getYear() == endDt.getYear()");

                    // 3. iterasi pengecekan leave period                    
                    // iterasi pengecekan selama "monthDiff"
                    int monthDiff = month2nd - month1st;
                    for (int k=0; k<=monthDiff; k++)                         
                    {                       
//                        System.out.println("k : " + k);
                        
                        // 4. melakukan pengecekan apakah leave periode pada "selected date" sudah ada
                        //    klo sudah ada maka "break" sebaliknya "insert new one"        
                        Date dtStartLv = new Date(startDt.getYear(), startDt.getMonth()+k, startDt.getDate());
                        LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(dtStartLv);

                        // leave period untuk "selected date" belum ada di db, jadi harus "insert new one"
                        if (leavePeriod.getOID() == 0) 
                        {
//                            System.out.println("leavePeriod.getOID() == 0");
                            
                            // 5. ngecek apakah tipe leave period yang dipakai (FULL atau HALF)
                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_FULL
                            if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) 
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_FULL");
                                
                                // membuat start dan end date atribut dari leave period yang akan dibuat
                                // based on "startDt" object                              
                                dtStartLv.setDate(1);

                                Calendar gre = Calendar.getInstance();
                                gre.setTime(dtStartLv);
                                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                
                                Date dtEndLv = new Date(dtStartLv.getYear(), dtStartLv.getMonth(), maxDay);

                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(dtStartLv);
                                leavePeriod.setEndDate(dtEndLv);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                }
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) FULL : " + e.toString());
                                }                                
                            }


                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_HALF
                            // ini sebenarnya masih pending !!!
                            else
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_HALF");
                                
                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(startDt);
                                leavePeriod.setEndDate(endDt);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                } 
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) HALF : " + e.toString());
                                }                                
                            }                            
                        }
                    }
                }


                // endDt > startDt
                else
                {
//                    System.out.println("startDt.getYear() != endDt.getYear()");

                    // 3. iterasi pengecekan leave period 
                    // iterasi pengecekan selama "11-startDt.getMonth()" utk tahun current(pertama)
                    // karena bulan dalam java maximal adalah 11 (dimulai dari 0)
                    int maxMonth = 11 - startDt.getMonth();                               
                    for (int k=0; k<=maxMonth; k++) 
                    {
//                        System.out.println("k : " + k);

                        // 4. melakukan pengecekan apakah leave periode pada "selected date" sudah ada
                        //    klo sudah ada maka "break" sebaliknya "insert new one"        
                        Date dtStartLv = new Date(startDt.getYear(), startDt.getMonth()+k, startDt.getDate());
                        LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(dtStartLv);

                        // leave period untuk "selected date" belum ada di db, jadi harus "insert new one"
                        if (leavePeriod.getOID() == 0) 
                        {
//                            System.out.println("leavePeriod.getOID() == 0");
                            
                            // 5. ngecek apakah tipe leave period yang dipakai (FULL atau HALF)
                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_FULL
                            if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) 
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_FULL");
                                
                                // membuat start dan end date atribut dari leave period yang akan dibuat
                                // based on "startDt" object                              
                                dtStartLv.setDate(1);

                                Calendar gre = Calendar.getInstance();
                                gre.setTime(dtStartLv);
                                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                
                                Date dtEndLv = new Date(dtStartLv.getYear(), dtStartLv.getMonth(), maxDay);

                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(dtStartLv);
                                leavePeriod.setEndDate(dtEndLv);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                }
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) FULL : " + e.toString());
                                }                                
                            }


                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_HALF
                            // ini sebenarnya masih pending !!!
                            else
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_HALF");
                                
                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(startDt);
                                leavePeriod.setEndDate(endDt);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                } 
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) HALF : " + e.toString());
                                }                                
                            }                            
                        }
                    }                


                    // 3. iterasi pengecekan leave period
                    // iterasi pengecekan selama "endDt.getMonth()" utk tahun next(kedua atau seterusnya)                    
                    int intEndDate = endDt.getMonth();
                    for (int k=0; k<=intEndDate; k++) 
                    {                        
//                        System.out.println("k : " + k);                                       

                        // 4. melakukan pengecekan apakah leave periode pada "selected date" sudah ada
                        //    klo sudah ada maka "break" sebaliknya "insert new one"  
                        Date dtStartLv = new Date(endDt.getYear(), k, 1);
                        LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(dtStartLv);

                        // leave period untuk "selected date" belum ada di db, jadi harus "insert new one"
                        if (leavePeriod.getOID() == 0) 
                        {
//                            System.out.println("leavePeriod.getOID() == 0");
                            
                            // 5. ngecek apakah tipe leave period yang dipakai (FULL atau HALF)
                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_FULL
                            if (SystemProperty.SYS_PROP_SCHEDULE_LEAVE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL) 
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_FULL");
                                
                                // membuat start dan end date atribut dari leave period yang akan dibuat
                                // based on "startDt" object                              
                                dtStartLv.setDate(1);

                                Calendar gre = Calendar.getInstance();
                                gre.setTime(dtStartLv);
                                int maxDay = gre.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                
                                Date dtEndLv = new Date(dtStartLv.getYear(), dtStartLv.getMonth(), maxDay);

                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(dtStartLv);
                                leavePeriod.setEndDate(dtEndLv);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                }
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) FULL : " + e.toString());
                                }                                
                            }


                            // tipe leave period == TYPE_SCHEDULE_PERIOD_A_MONTH_HALF
                            // ini sebenarnya masih pending !!!
                            else
                            {
//                                System.out.println("TYPE_SCHEDULE_PERIOD_A_MONTH_HALF");
                                
                                // set nilai start dan end date ke object leave period yang akan diinsert ke db
                                leavePeriod.setStartDate(startDt);
                                leavePeriod.setEndDate(endDt);                                

                                try 
                                {
                                    PstLeavePeriod.insertExc(leavePeriod);
                                } 
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when insertExc(leavePeriod) HALF : " + e.toString());
                                }                                
                            }                            
                        }
                    }
                }
            }            
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when generateLeavePeriod : " + e.toString());   
        }
    }
    
    
    
    /**
     * get period object just after selected period
     * @param selectedPeriod
     * @return
     * @created by Edhy
     */
    public static LeavePeriod getLeavePeriodJustAfter(long leavePeriodId) 
    {
        LeavePeriod result = new LeavePeriod();
        
        if(leavePeriodId != 0)
        {	    
            String orderBy = PstLeavePeriod.fieldNames[PstLeavePeriod.FLD_START_DATE];
            Vector vectLeavePeriod = PstLeavePeriod.list(0,0,"",orderBy);            
            if(vectLeavePeriod!=null && vectLeavePeriod.size()>1)
            {
                for(int i=0; i<vectLeavePeriod.size(); i++)
                {
                    LeavePeriod per = (LeavePeriod)vectLeavePeriod.get(i);                
                    if(leavePeriodId==per.getOID() && i<vectLeavePeriod.size()-1)  
                    {
                        per = (LeavePeriod)vectLeavePeriod.get(i+1);                        
                        return per;
                    }
                }
            }
            
            if(vectLeavePeriod.size()==1)
            {
                LeavePeriod per = (LeavePeriod)vectLeavePeriod.get(0);
                if(per.getOID()!=leavePeriodId)
                {
                    return per;
                }
            }
        }
        return result;        
    }
 
    
    public static void main(String args[])
    {
        //LeavePeriod per = getLeavePeriodJustAfter(504404250284451471L);
        //System.out.println("leave period : " + per.getOID());
        
        /*
        Date startDate = new Date(104,9,1);
        Date endDate = new Date(105,5,1);        
        generateLeavePeriodObj(startDate, endDate);    
        System.out.println("finish");
         */
        
        Date dt = new Date(104, 12, 1);
        System.out.println("dt : " + dt);
    }
    
}
