
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya Ramayu
 * @version  	: 01
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

/* package java */
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstMachineTransferDesktop extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final  String TBL_HR_MACHINE_TRANS = "hr_machine_transaction";//"";

        public static final int POSTED_STATUS_OPEN = 0;
        public static final int POSTED_STATUS_PROCESSED = 1;
        public static final int POSTED_STATUS_INVALID_DATA = -1;
        
	public static final  int FLD_MACHINE_TRANS_ID = 0;
	public static final  int FLD_CARD_ID = 1;
	public static final  int FLD_DATE_TRANS = 2;
	public static final  int FLD_MODE = 3;
	public static final  int FLD_STATION = 4;
	public static final  int FLD_POSTED = 5;
        public static final  int FLD_VERIFY = 6;
        public static final  int FLD_NOTE = 7;

	public static final  String[] fieldNames = {
            "TRANSACTION_ID",
            "CARD_ID",
            "DATE_TRANSACTION",
            "MODE",
            "STATION",
            "POSTED",
            "VERIFY",
            "NOTE"
	 }; 

	public static final  int[] fieldTypes = {
            TYPE_LONG + TYPE_PK + TYPE_ID,
            TYPE_STRING,
            TYPE_DATE,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_INT,
            TYPE_INT,
            TYPE_STRING
	 }; 

	public PstMachineTransferDesktop(){
	}

	public PstMachineTransferDesktop(int i) throws DBException { 
		super(new PstMachineTransferDesktop()); 
	}

	public PstMachineTransferDesktop(String sOid) throws DBException { 
		super(new PstMachineTransferDesktop(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMachineTransferDesktop(long lOid) throws DBException { 
		super(new PstMachineTransferDesktop(0)); 
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
		return TBL_HR_MACHINE_TRANS;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstMachineTransferDesktop().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		return 0; 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return 0; 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return 0; 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static long insertExc(TabelMachineTransaction TabelMachineTransaction) throws DBException{ 
		try {
            PstMachineTransferDesktop pstObj = new PstMachineTransferDesktop(0);

            pstObj.setString(FLD_CARD_ID, TabelMachineTransaction.getCardId());
            pstObj.setDate(FLD_DATE_TRANS, TabelMachineTransaction.getDateTransaction());
            pstObj.setString(FLD_MODE, TabelMachineTransaction.getMode());
            pstObj.setString(FLD_STATION, TabelMachineTransaction.getStation());
            pstObj.setInt(FLD_POSTED, TabelMachineTransaction.getPosted());
            pstObj.setInt(FLD_VERIFY, TabelMachineTransaction.getVerify());
            pstObj.setString(FLD_NOTE, TabelMachineTransaction.getNote());
            
            pstObj.insert(TabelMachineTransaction.getMachineTransId());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstScheduleDestopTransfer(0), DBException.UNKNOWN);
        }
        return TabelMachineTransaction.getMachineTransId();
	}

	public static long updateExc(TabelMachineTransaction TabelMachineTransaction) throws DBException{ 
		try{ 
                    if(TabelMachineTransaction.getMachineTransId() != 0){ 
                        PstMachineTransferDesktop pstObj = new PstMachineTransferDesktop(TabelMachineTransaction.getMachineTransId());

                        pstObj.setString(FLD_CARD_ID, TabelMachineTransaction.getCardId());
            pstObj.setDate(FLD_DATE_TRANS, TabelMachineTransaction.getDateTransaction());
            pstObj.setString(FLD_MODE, TabelMachineTransaction.getMode());
            pstObj.setString(FLD_STATION, TabelMachineTransaction.getStation());
            pstObj.setInt(FLD_POSTED, TabelMachineTransaction.getPosted());
            pstObj.setInt(FLD_VERIFY, TabelMachineTransaction.getVerify());
            pstObj.setString(FLD_NOTE, TabelMachineTransaction.getNote());
                        
                        pstObj.update(); 
                        return TabelMachineTransaction.getMachineTransId();
                    }
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransferDesktop(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
                    PstMachineTransferDesktop PstMachineTransferDesktop = new PstMachineTransferDesktop(oid);
                    PstMachineTransferDesktop.delete();
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransferDesktop(0),DBException.UNKNOWN); 
		}
		return oid;
	}

	public static Vector listAll(){ 
		return list(0, 0, "",""); 
	}

	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_MACHINE_TRANS; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			//System.out.println("SQL List : "+sql);
                        dbrs = DBHandler.execQueryResult(sql);
                        
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				TabelMachineTransaction tabelMachineTransaction = new TabelMachineTransaction();
				resultToObject(rs, tabelMachineTransaction);
				lists.add(tabelMachineTransaction);
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
        
        //update by satrya 2012-06-29
        
        public static int updateStatusPosted(String oidMachine){
		int success=0;
		DBResultSet dbrs = null;
		try {
			String sql = "UPDATE "+TBL_HR_MACHINE_TRANS+" SET "+fieldNames[FLD_POSTED]+"="+POSTED_STATUS_PROCESSED+" WHERE "+fieldNames[FLD_MACHINE_TRANS_ID]+" IN("+oidMachine+")"; 
                        success  = DBHandler.execUpdate(sql);
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
                        return success;
		}
		
	}
        
	public static TabelMachineTransaction fetchBy(String cardId, Date transDate, String mode, String station){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_MACHINE_TRANS + 			
				" WHERE " + fieldNames[FLD_CARD_ID]+"=\""+cardId+"\"" +
                                " AND " + fieldNames[FLD_DATE_TRANS]+"=\""+Formater.formatDate(transDate, "yyyy-MM-dd HH:mm:ss") +"\"" +
                                " AND " + fieldNames[FLD_MODE]+"=\""+mode+"\"" +
                                " AND " + fieldNames[FLD_STATION]+"=\""+station+"\"" +
				" LIMIT 0,1";		
                        //System.out.println(sql + " "+ transDate);
                        dbrs = DBHandler.execQueryResult(sql);                        
			ResultSet rs = dbrs.getResultSet();
                        TabelMachineTransaction tabelMachineTransaction = new TabelMachineTransaction();
			while(rs.next()) {
                                
				resultToObject(rs, tabelMachineTransaction);
				lists.add(tabelMachineTransaction);
			}
			rs.close();
			return tabelMachineTransaction;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
            return new TabelMachineTransaction();
	}

        
	private static void resultToObject(ResultSet rs, TabelMachineTransaction TabelMachineTransaction){
		try{
			TabelMachineTransaction.setMachineTransId(rs.getLong(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID]));
			TabelMachineTransaction.setCardId(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_CARD_ID]));
                        Date date = null;
                        try{
                            Date dateTemp = rs.getDate(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_DATE_TRANS]);
                            Time time = rs.getTime(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_DATE_TRANS]);
                            date = new Date(dateTemp.getYear(), dateTemp.getMonth(), dateTemp.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
                        }catch(Exception ex){
                            
                        }
                        TabelMachineTransaction.setDateTransaction(date);
			TabelMachineTransaction.setMode(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MODE]));
			TabelMachineTransaction.setStation(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_STATION]));
			TabelMachineTransaction.setPosted(rs.getInt(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_POSTED]));
                        TabelMachineTransaction.setVerify(rs.getInt(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_VERIFY]));
                        TabelMachineTransaction.setNote(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_NOTE]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long TabelMachineTransactionId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
                    String sql = "SELECT * FROM " + TBL_HR_MACHINE_TRANS 
                            + " WHERE " + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID] 
                            + " = " + TabelMachineTransactionId;

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
                    String sql = "SELECT COUNT("
                            + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID] 
                            + ") FROM " + TBL_HR_MACHINE_TRANS;
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


	public static int getCount(TabelMachineTransaction trans){
		DBResultSet dbrs = null;
		try {
                    String sql = "SELECT COUNT("
                            + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID] 
                            + ") FROM " + TBL_HR_MACHINE_TRANS;
                    
                    if(trans != null && trans.getCardId()!=null && trans.getDateTransaction()!=null && 
                            trans.getMode()!=null && trans.getStation()!=null ){
                            sql = sql + " WHERE " + fieldNames[FLD_CARD_ID]+"=\""+trans.getCardId() + "\" AND " +
                                    fieldNames[FLD_DATE_TRANS]+"=\""+Formater.formatDate(trans.getDateTransaction(),"yyyy-MM-dd HH:mm:ss")+ "\" AND " +
                                    fieldNames[FLD_MODE]+"=\""+trans.getMode() + "\" AND "+fieldNames[FLD_STATION]+"=\""+trans.getStation()+"\"";
                    } else {
                        return 0;
                    }

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
                           TabelMachineTransaction TabelMachineTransaction = (TabelMachineTransaction)list.get(ls);
                           if(oid == TabelMachineTransaction.getMachineTransId())
                              found=true;
                      }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
        public static void main(String[] arg){
            TabelMachineTransaction mcTran = new TabelMachineTransaction();
            mcTran.setCardId("12345");
            mcTran.setDateTransaction(new Date());
            mcTran.setMode("A");
            mcTran.setStation("01");
            mcTran.setPosted(0);
            try{
                insertExc(mcTran);
            }catch(Exception ex){
                System.out.println(":::::::::: PstMachineTransferDesktop.main : "+ex.toString());
            }
        }
}
