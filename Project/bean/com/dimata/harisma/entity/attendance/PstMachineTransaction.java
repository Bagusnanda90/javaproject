/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.session.attendance.SessMachineTransactionDestop;
import com.dimata.harisma.utility.harisma.machine.transferdataemployee.TabelMachineTransaction;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class PstMachineTransaction extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

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

	public PstMachineTransaction(){
	}

	public PstMachineTransaction(int i) throws DBException { 
		super(new PstMachineTransaction()); 
	}

	public PstMachineTransaction(String sOid) throws DBException { 
		super(new PstMachineTransaction(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstMachineTransaction(long lOid) throws DBException { 
		super(new PstMachineTransaction(0)); 
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
		return new PstMachineTransaction().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		MachineTransaction machineTransaction = fetchExc(ent.getOID()); 
		ent = (Entity)machineTransaction; 
		return machineTransaction.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((MachineTransaction) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((MachineTransaction) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static MachineTransaction fetchExc(long oid) throws DBException{ 
		try{ 
                    MachineTransaction MachineTransaction = new MachineTransaction();
                    PstMachineTransaction PstMachineTransaction = new PstMachineTransaction(oid); 
                    MachineTransaction.setOID(oid);

                    MachineTransaction.setCardId(PstMachineTransaction.getString(FLD_CARD_ID));
                    MachineTransaction.setDateTransaction(PstMachineTransaction.getDate(FLD_DATE_TRANS));
                    MachineTransaction.setMode(PstMachineTransaction.getString(FLD_MODE));
                    MachineTransaction.setStation(PstMachineTransaction.getString(FLD_STATION));
                    MachineTransaction.setPosted(PstMachineTransaction.getInt(FLD_POSTED));
                    MachineTransaction.setVerify(PstMachineTransaction.getInt(FLD_VERIFY));
                    MachineTransaction.setNote(PstMachineTransaction.getString(FLD_NOTE));
                    return MachineTransaction; 
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransaction(0),DBException.UNKNOWN); 
		}
	}

	public static long insertExc(MachineTransaction MachineTransaction) throws DBException{ 
		try{ 
                    PstMachineTransaction PstMachineTransaction = new PstMachineTransaction(0);

                    PstMachineTransaction.setString(FLD_CARD_ID, MachineTransaction.getCardId());
                    
                    PstMachineTransaction.setDate(FLD_DATE_TRANS, MachineTransaction.getDateTransaction());
                    
                    PstMachineTransaction.setString(FLD_MODE, MachineTransaction.getMode());
                    PstMachineTransaction.setString(FLD_STATION, MachineTransaction.getStation());
                    PstMachineTransaction.setInt(FLD_POSTED, MachineTransaction.getPosted());
                    PstMachineTransaction.setInt(FLD_VERIFY, MachineTransaction.getVerify());
                    PstMachineTransaction.setString(FLD_NOTE, MachineTransaction.getNote());
                    PstMachineTransaction.insert(); 
                    MachineTransaction.setOID(PstMachineTransaction.getlong(FLD_MACHINE_TRANS_ID));
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransaction(0),DBException.UNKNOWN); 
		}
		return MachineTransaction.getOID();
	}

	public static long updateExc(MachineTransaction MachineTransaction) throws DBException{ 
		try{ 
                    if(MachineTransaction.getOID() != 0){ 
                        PstMachineTransaction PstMachineTransaction = new PstMachineTransaction(MachineTransaction.getOID());

                        PstMachineTransaction.setString(FLD_CARD_ID, MachineTransaction.getCardId());
                        PstMachineTransaction.setDate(FLD_DATE_TRANS, MachineTransaction.getDateTransaction());
                        PstMachineTransaction.setString(FLD_MODE, MachineTransaction.getMode());
                        PstMachineTransaction.setString(FLD_STATION, MachineTransaction.getStation());
                        PstMachineTransaction.setInt(FLD_POSTED, MachineTransaction.getPosted());
                        PstMachineTransaction.setInt(FLD_VERIFY, MachineTransaction.getVerify());
                        PstMachineTransaction.setString(FLD_NOTE, MachineTransaction.getNote());
                        
                        PstMachineTransaction.update(); 
                        return MachineTransaction.getOID();
                    }
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransaction(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
                    PstMachineTransaction PstMachineTransaction = new PstMachineTransaction(oid);
                    PstMachineTransaction.delete();
		}catch(DBException dbe){ 
                    throw dbe; 
		}catch(Exception e){ 
                    throw new DBException(new PstMachineTransaction(0),DBException.UNKNOWN); 
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
				MachineTransaction machineTransaction = new MachineTransaction();
				resultToObject(rs, machineTransaction);
				lists.add(machineTransaction);
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
        
        /**
         * create by satrya 2014-02-19
         * @param limitStart
         * @param recordToGet
         * @param whereClause
         * @param order
         * @return 
         */
        public static Hashtable hashListMachineTrans(int limitStart,int recordToGet, String whereClause, String order){
		Hashtable lists = new Hashtable(); 
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
                        String cardId="";
                        SessMachineTransactionDestop sessMachineTransactionDestop = new SessMachineTransactionDestop();
			while(rs.next()) {
				String sCardId= rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_CARD_ID]);
                                if(sCardId!=null && sCardId.length()>0
                                    && !sCardId.equalsIgnoreCase(cardId)){
                                    sessMachineTransactionDestop = new SessMachineTransactionDestop();
                                    cardId=sCardId;
                                    lists.put(sCardId, sessMachineTransactionDestop);
                                }
                                sessMachineTransactionDestop.setCardId(sCardId);
                                sessMachineTransactionDestop.addDtTransaction(DBHandler.convertDate(rs.getDate(fieldNames[FLD_DATE_TRANS]), rs.getTime(fieldNames[FLD_DATE_TRANS])));
                                sessMachineTransactionDestop.addMode(rs.getString(fieldNames[FLD_MODE]));
				
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return lists;
	}
        
        
        public static Hashtable GetStationByDate(int limitStart,int recordToGet, String whereClause, String order){
		Hashtable lists = new Hashtable(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_MACHINE_TRANS + " hmt ";
                               sql = sql + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " he ON he." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + " = hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_CARD_ID] ;
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
                        String cardId="";
                       // SessMachineTransactionDestop sessMachineTransactionDestop = new SessMachineTransactionDestop();
			while(rs.next()) {
				String sCardId = rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_CARD_ID]);
                                String sEmpNum = rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]);
                                Date dateTrans = rs.getDate(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                                Time timeTrans = rs.getTime(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                                String Station = rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_STATION]);
                                
                                if(sCardId!=null && sCardId.length()>0
                                   && !sCardId.equalsIgnoreCase(cardId)){
                                   //SessMachineTransactionDestop sessMachineTransactionDestop = new SessMachineTransactionDestop();
                                   
                                   lists.put(sEmpNum +"_"+dateTrans+" "+timeTrans, Station);
                                }
                                
			}
			rs.close();
			return lists;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
			return lists;
	}
        
        
	public static MachineTransaction fetchBy(String cardId, Date transDate, String mode, String station){
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
                        MachineTransaction machineTransaction = new MachineTransaction();
			while(rs.next()) {
                                
				resultToObject(rs, machineTransaction);
				lists.add(machineTransaction);
			}
			rs.close();
			return machineTransaction;

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
		}
            return new MachineTransaction();
	}

        
	private static void resultToObject(ResultSet rs, MachineTransaction MachineTransaction){
		try{
			MachineTransaction.setOID(rs.getLong(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_MACHINE_TRANS_ID]));
			MachineTransaction.setCardId(rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_CARD_ID]));
                        Date date = null;
                        try{
                            Date dateTemp = rs.getDate(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                            Time time = rs.getTime(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                            date = new Date(dateTemp.getYear(), dateTemp.getMonth(), dateTemp.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
                        }catch(Exception ex){
                            
                        }
                        MachineTransaction.setDateTransaction(date);
			MachineTransaction.setMode(rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_MODE]));
			MachineTransaction.setStation(rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_STATION]));
			MachineTransaction.setPosted(rs.getInt(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_POSTED]));
                        MachineTransaction.setVerify(rs.getInt(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_VERIFY]));
                        MachineTransaction.setNote(rs.getString(PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_NOTE]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long MachineTransactionId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
                    String sql = "SELECT * FROM " + TBL_HR_MACHINE_TRANS 
                            + " WHERE " + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_MACHINE_TRANS_ID] 
                            + " = " + MachineTransactionId;

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
                            + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_MACHINE_TRANS_ID] 
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


	public static int getCount(MachineTransaction trans){
		DBResultSet dbrs = null;
		try {
                    String sql = "SELECT COUNT("
                            + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_MACHINE_TRANS_ID] 
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
                           MachineTransaction MachineTransaction = (MachineTransaction)list.get(ls);
                           if(oid == MachineTransaction.getOID())
                              found=true;
                      }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
        public static void main(String[] arg){
            MachineTransaction mcTran = new MachineTransaction();
            mcTran.setCardId("12345");
            mcTran.setDateTransaction(new Date());
            mcTran.setMode("A");
            mcTran.setStation("01");
            mcTran.setPosted(0);
            try{
                insertExc(mcTran);
            }catch(Exception ex){
                System.out.println(":::::::::: PstMachineTransaction.main : "+ex.toString());
            }
        }
        
        
        public static long insertTransfer(TabelMachineTransaction TabelMachineTransaction) throws DBException{ 
		try {
            PstMachineTransaction pstObj = new PstMachineTransaction(0);

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
            throw new DBException(new PstMachineTransaction(0), DBException.UNKNOWN);
        }
        return TabelMachineTransaction.getMachineTransId();
	}

	public static long updateTransfer(TabelMachineTransaction TabelMachineTransaction) throws DBException{ 
		try{ 
                    if(TabelMachineTransaction.getMachineTransId() != 0){ 
                        PstMachineTransaction pstObj = new PstMachineTransaction(TabelMachineTransaction.getMachineTransId());

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
                    throw new DBException(new PstMachineTransaction(0),DBException.UNKNOWN); 
		}
		return 0;
	}
}
