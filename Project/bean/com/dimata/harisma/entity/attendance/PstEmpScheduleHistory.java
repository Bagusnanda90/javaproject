/*
 * PstEmpScheduleHistory.java
 *
 * Created on October 4, 2004, 2:28 PM
 */

package com.dimata.harisma.entity.attendance;

// package java 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

// package qdep 
import com.dimata.util.lang.I_Language;
import com.dimata.util.Formater;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

// package harisma 
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;   
import com.dimata.harisma.entity.attendance.*; 

/**
 *
 * @author  gedhy
 */
public class PstEmpScheduleHistory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 
    
	public static final  String TBL_HR_EMP_SCHEDULE_HISTORY = "hr_emp_schedule_history";//"HR_EMP_SCHEDULE_HISTORY";      

        public static final  int FLD_EMP_SCHEDULE_HISTORY_ID = 0;
	public static final  int FLD_EMP_SCHEDULE_ORG_ID = 1;
	public static final  int FLD_PERIOD_ID = 2;
	public static final  int FLD_EMPLOYEE_ID = 3;     
	public static final  int FLD_D1 = 4;
	public static final  int FLD_D2 = 5;
	public static final  int FLD_D3 = 6;
	public static final  int FLD_D4 = 7;
	public static final  int FLD_D5 = 8;
	public static final  int FLD_D6 = 9;
	public static final  int FLD_D7 = 10;
	public static final  int FLD_D8 = 11;
	public static final  int FLD_D9 = 12;
	public static final  int FLD_D10 = 13;
	public static final  int FLD_D11 = 14;
	public static final  int FLD_D12 = 15;
	public static final  int FLD_D13 = 16;
	public static final  int FLD_D14 = 17;
	public static final  int FLD_D15 = 18;
	public static final  int FLD_D16 = 19;
	public static final  int FLD_D17 = 20;
	public static final  int FLD_D18 = 21;
	public static final  int FLD_D19 = 22;
	public static final  int FLD_D20 = 23;
	public static final  int FLD_D21 = 24;
	public static final  int FLD_D22 = 25;
	public static final  int FLD_D23 = 26;
	public static final  int FLD_D24 = 27;
	public static final  int FLD_D25 = 28;
	public static final  int FLD_D26 = 29;
	public static final  int FLD_D27 = 30;
	public static final  int FLD_D28 = 31;
	public static final  int FLD_D29 = 32;
	public static final  int FLD_D30 = 33;
	public static final  int FLD_D31 = 34;
	public static final  int FLD_D2ND1 = 35;
	public static final  int FLD_D2ND2 = 36;
	public static final  int FLD_D2ND3 = 37;
	public static final  int FLD_D2ND4 = 38;
	public static final  int FLD_D2ND5 = 39;
	public static final  int FLD_D2ND6 = 40;
	public static final  int FLD_D2ND7 = 41;
	public static final  int FLD_D2ND8 = 42;
	public static final  int FLD_D2ND9 = 43;
	public static final  int FLD_D2ND10 = 44;
	public static final  int FLD_D2ND11 = 45;
	public static final  int FLD_D2ND12 = 46;
	public static final  int FLD_D2ND13 = 47;
	public static final  int FLD_D2ND14 = 48;
	public static final  int FLD_D2ND15 = 49;
	public static final  int FLD_D2ND16 = 50;
	public static final  int FLD_D2ND17 = 51;
	public static final  int FLD_D2ND18 = 52;
	public static final  int FLD_D2ND19 = 53;
	public static final  int FLD_D2ND20 = 54;
	public static final  int FLD_D2ND21 = 55;
	public static final  int FLD_D2ND22 = 56;
	public static final  int FLD_D2ND23 = 57;
	public static final  int FLD_D2ND24 = 58;
	public static final  int FLD_D2ND25 = 59;
	public static final  int FLD_D2ND26 = 60;
	public static final  int FLD_D2ND27 = 61;
	public static final  int FLD_D2ND28 = 62;
	public static final  int FLD_D2ND29 = 63;
	public static final  int FLD_D2ND30 = 64;
	public static final  int FLD_D2ND31 = 65;

	public static final  String[] fieldNames = {
		"EMP_SCHEDULE_HISTORY_ID",	
                "EMP_SCHEDULE_ORG_ID",
                "PERIOD_ID",
                "EMPLOYEE_ID",
		"D1",
                "D2",
                "D3",
                "D4",
                "D5",
                "D6",
                "D7",
                "D8",
                "D9",
                "D10",
		"D11",
                "D12",
                "D13",
                "D14",
                "D15",
                "D16",
                "D17",
                "D18",
                "D19",
                "D20",
		"D21",
                "D22",
                "D23",
                "D24",
                "D25",
                "D26",
                "D27",
                "D28",
                "D29",
                "D30",
                "D31",
		"D2ND1",
                "D2ND2",
                "D2ND3",
                "D2ND4",
                "D2ND5",
                "D2ND6",
                "D2ND7",
                "D2ND8",
                "D2ND9",
                "D2ND10",
		"D2ND11",
                "D2ND12",
                "D2ND13",
                "D2ND14",
                "D2ND15",
                "D2ND16",
                "D2ND17",
                "D2ND18",
                "D2ND19",
                "D2ND20",
		"D2ND21",
                "D2ND22",
                "D2ND23",
                "D2ND24",
                "D2ND25",
                "D2ND26",
                "D2ND27",
                "D2ND28",
                "D2ND29",
                "D2ND30",
                "D2ND31"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,                
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
		TYPE_LONG,                
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
		TYPE_LONG
	 };
        
        public PstEmpScheduleHistory(){
	}

	public PstEmpScheduleHistory(int i) throws DBException { 
		super(new PstEmpScheduleHistory()); 
	}

	public PstEmpScheduleHistory(String sOid) throws DBException { 
		super(new PstEmpScheduleHistory(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstEmpScheduleHistory(long lOid) throws DBException { 
		super(new PstEmpScheduleHistory(0)); 
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
		return TBL_HR_EMP_SCHEDULE_HISTORY;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstEmpScheduleHistory().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		EmpScheduleHistory empschedule = fetchExc(ent.getOID()); 
		ent = (Entity)empschedule; 
		return empschedule.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((EmpScheduleHistory) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((EmpScheduleHistory) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static EmpScheduleHistory fetchExc(long oid) throws DBException{ 
		try{ 
			EmpScheduleHistory empschedule = new EmpScheduleHistory();
			PstEmpScheduleHistory pstEmpSchedule = new PstEmpScheduleHistory(oid); 
			empschedule.setOID(oid);

                        empschedule.setEmpScheduleOrgId(pstEmpSchedule.getlong(FLD_EMP_SCHEDULE_ORG_ID));
			empschedule.setPeriodId(pstEmpSchedule.getlong(FLD_PERIOD_ID));
			empschedule.setEmployeeId(pstEmpSchedule.getlong(FLD_EMPLOYEE_ID));
			empschedule.setD1(pstEmpSchedule.getlong(FLD_D1));
			empschedule.setD2(pstEmpSchedule.getlong(FLD_D2));
			empschedule.setD3(pstEmpSchedule.getlong(FLD_D3));
			empschedule.setD4(pstEmpSchedule.getlong(FLD_D4));
			empschedule.setD5(pstEmpSchedule.getlong(FLD_D5));
			empschedule.setD6(pstEmpSchedule.getlong(FLD_D6));
			empschedule.setD7(pstEmpSchedule.getlong(FLD_D7));
			empschedule.setD8(pstEmpSchedule.getlong(FLD_D8));
			empschedule.setD9(pstEmpSchedule.getlong(FLD_D9));
			empschedule.setD10(pstEmpSchedule.getlong(FLD_D10));
			empschedule.setD11(pstEmpSchedule.getlong(FLD_D11));
			empschedule.setD12(pstEmpSchedule.getlong(FLD_D12));
			empschedule.setD13(pstEmpSchedule.getlong(FLD_D13));
			empschedule.setD14(pstEmpSchedule.getlong(FLD_D14));
			empschedule.setD15(pstEmpSchedule.getlong(FLD_D15));
			empschedule.setD16(pstEmpSchedule.getlong(FLD_D16));
			empschedule.setD17(pstEmpSchedule.getlong(FLD_D17));
			empschedule.setD18(pstEmpSchedule.getlong(FLD_D18));
			empschedule.setD19(pstEmpSchedule.getlong(FLD_D19));
			empschedule.setD20(pstEmpSchedule.getlong(FLD_D20));
			empschedule.setD21(pstEmpSchedule.getlong(FLD_D21));
			empschedule.setD22(pstEmpSchedule.getlong(FLD_D22));
			empschedule.setD23(pstEmpSchedule.getlong(FLD_D23));
			empschedule.setD24(pstEmpSchedule.getlong(FLD_D24));
			empschedule.setD25(pstEmpSchedule.getlong(FLD_D25));
			empschedule.setD26(pstEmpSchedule.getlong(FLD_D26));
			empschedule.setD27(pstEmpSchedule.getlong(FLD_D27));
			empschedule.setD28(pstEmpSchedule.getlong(FLD_D28));
			empschedule.setD29(pstEmpSchedule.getlong(FLD_D29));
			empschedule.setD30(pstEmpSchedule.getlong(FLD_D30));
			empschedule.setD31(pstEmpSchedule.getlong(FLD_D31));
			empschedule.setD2nd1(pstEmpSchedule.getlong(FLD_D2ND1));
			empschedule.setD2nd2(pstEmpSchedule.getlong(FLD_D2ND2));
			empschedule.setD2nd3(pstEmpSchedule.getlong(FLD_D2ND3));
			empschedule.setD2nd4(pstEmpSchedule.getlong(FLD_D2ND4));
			empschedule.setD2nd5(pstEmpSchedule.getlong(FLD_D2ND5));
			empschedule.setD2nd6(pstEmpSchedule.getlong(FLD_D2ND6));
			empschedule.setD2nd7(pstEmpSchedule.getlong(FLD_D2ND7));
			empschedule.setD2nd8(pstEmpSchedule.getlong(FLD_D2ND8));
			empschedule.setD2nd9(pstEmpSchedule.getlong(FLD_D2ND9));
			empschedule.setD2nd10(pstEmpSchedule.getlong(FLD_D2ND10));
			empschedule.setD2nd11(pstEmpSchedule.getlong(FLD_D2ND11));
			empschedule.setD2nd12(pstEmpSchedule.getlong(FLD_D2ND12));
			empschedule.setD2nd13(pstEmpSchedule.getlong(FLD_D2ND13));
			empschedule.setD2nd14(pstEmpSchedule.getlong(FLD_D2ND14));
			empschedule.setD2nd15(pstEmpSchedule.getlong(FLD_D2ND15));
			empschedule.setD2nd16(pstEmpSchedule.getlong(FLD_D2ND16));
			empschedule.setD2nd17(pstEmpSchedule.getlong(FLD_D2ND17));
			empschedule.setD2nd18(pstEmpSchedule.getlong(FLD_D2ND18));
			empschedule.setD2nd19(pstEmpSchedule.getlong(FLD_D2ND19));
			empschedule.setD2nd20(pstEmpSchedule.getlong(FLD_D2ND20));
			empschedule.setD2nd21(pstEmpSchedule.getlong(FLD_D2ND21));
			empschedule.setD2nd22(pstEmpSchedule.getlong(FLD_D2ND22));
			empschedule.setD2nd23(pstEmpSchedule.getlong(FLD_D2ND23));
			empschedule.setD2nd24(pstEmpSchedule.getlong(FLD_D2ND24));
			empschedule.setD2nd25(pstEmpSchedule.getlong(FLD_D2ND25));
			empschedule.setD2nd26(pstEmpSchedule.getlong(FLD_D2ND26));
			empschedule.setD2nd27(pstEmpSchedule.getlong(FLD_D2ND27));
			empschedule.setD2nd28(pstEmpSchedule.getlong(FLD_D2ND28));
			empschedule.setD2nd29(pstEmpSchedule.getlong(FLD_D2ND29));
			empschedule.setD2nd30(pstEmpSchedule.getlong(FLD_D2ND30));
			empschedule.setD2nd31(pstEmpSchedule.getlong(FLD_D2ND31));
                        
			return empschedule; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleHistory(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(EmpScheduleHistory empschedule) throws DBException{ 
		try{ 
			PstEmpScheduleHistory pstEmpSchedule = new PstEmpScheduleHistory(0);

                        pstEmpSchedule.setLong(FLD_EMP_SCHEDULE_ORG_ID, empschedule.getEmpScheduleOrgId());
			pstEmpSchedule.setLong(FLD_PERIOD_ID, empschedule.getPeriodId());
			pstEmpSchedule.setLong(FLD_EMPLOYEE_ID, empschedule.getEmployeeId());
			pstEmpSchedule.setLong(FLD_D1, empschedule.getD1());
			pstEmpSchedule.setLong(FLD_D2, empschedule.getD2());
			pstEmpSchedule.setLong(FLD_D3, empschedule.getD3());
			pstEmpSchedule.setLong(FLD_D4, empschedule.getD4());
			pstEmpSchedule.setLong(FLD_D5, empschedule.getD5());
			pstEmpSchedule.setLong(FLD_D6, empschedule.getD6());
			pstEmpSchedule.setLong(FLD_D7, empschedule.getD7());
			pstEmpSchedule.setLong(FLD_D8, empschedule.getD8());
			pstEmpSchedule.setLong(FLD_D9, empschedule.getD9());
			pstEmpSchedule.setLong(FLD_D10, empschedule.getD10());
			pstEmpSchedule.setLong(FLD_D11, empschedule.getD11());
			pstEmpSchedule.setLong(FLD_D12, empschedule.getD12());
			pstEmpSchedule.setLong(FLD_D13, empschedule.getD13());
			pstEmpSchedule.setLong(FLD_D14, empschedule.getD14());
			pstEmpSchedule.setLong(FLD_D15, empschedule.getD15());
			pstEmpSchedule.setLong(FLD_D16, empschedule.getD16());
			pstEmpSchedule.setLong(FLD_D17, empschedule.getD17());
			pstEmpSchedule.setLong(FLD_D18, empschedule.getD18());
			pstEmpSchedule.setLong(FLD_D19, empschedule.getD19());
			pstEmpSchedule.setLong(FLD_D20, empschedule.getD20());
			pstEmpSchedule.setLong(FLD_D21, empschedule.getD21());
			pstEmpSchedule.setLong(FLD_D22, empschedule.getD22());
			pstEmpSchedule.setLong(FLD_D23, empschedule.getD23());
			pstEmpSchedule.setLong(FLD_D24, empschedule.getD24());
			pstEmpSchedule.setLong(FLD_D25, empschedule.getD25());
			pstEmpSchedule.setLong(FLD_D26, empschedule.getD26());
			pstEmpSchedule.setLong(FLD_D27, empschedule.getD27());
			pstEmpSchedule.setLong(FLD_D28, empschedule.getD28());
			pstEmpSchedule.setLong(FLD_D29, empschedule.getD29());
			pstEmpSchedule.setLong(FLD_D30, empschedule.getD30());
			pstEmpSchedule.setLong(FLD_D31, empschedule.getD31());
			pstEmpSchedule.setLong(FLD_D2ND1, empschedule.getD2nd1());
			pstEmpSchedule.setLong(FLD_D2ND2, empschedule.getD2nd2());
			pstEmpSchedule.setLong(FLD_D2ND3, empschedule.getD2nd3());
			pstEmpSchedule.setLong(FLD_D2ND4, empschedule.getD2nd4());
			pstEmpSchedule.setLong(FLD_D2ND5, empschedule.getD2nd5());
			pstEmpSchedule.setLong(FLD_D2ND6, empschedule.getD2nd6());
			pstEmpSchedule.setLong(FLD_D2ND7, empschedule.getD2nd7());
			pstEmpSchedule.setLong(FLD_D2ND8, empschedule.getD2nd8());
			pstEmpSchedule.setLong(FLD_D2ND9, empschedule.getD2nd9());
			pstEmpSchedule.setLong(FLD_D2ND10, empschedule.getD2nd10());
			pstEmpSchedule.setLong(FLD_D2ND11, empschedule.getD2nd11());
			pstEmpSchedule.setLong(FLD_D2ND12, empschedule.getD2nd12());
			pstEmpSchedule.setLong(FLD_D2ND13, empschedule.getD2nd13());
			pstEmpSchedule.setLong(FLD_D2ND14, empschedule.getD2nd14());
			pstEmpSchedule.setLong(FLD_D2ND15, empschedule.getD2nd15());
			pstEmpSchedule.setLong(FLD_D2ND16, empschedule.getD2nd16());
			pstEmpSchedule.setLong(FLD_D2ND17, empschedule.getD2nd17());
			pstEmpSchedule.setLong(FLD_D2ND18, empschedule.getD2nd18());
			pstEmpSchedule.setLong(FLD_D2ND19, empschedule.getD2nd19());
			pstEmpSchedule.setLong(FLD_D2ND20, empschedule.getD2nd20());
			pstEmpSchedule.setLong(FLD_D2ND21, empschedule.getD2nd21());
			pstEmpSchedule.setLong(FLD_D2ND22, empschedule.getD2nd22());
			pstEmpSchedule.setLong(FLD_D2ND23, empschedule.getD2nd23());
			pstEmpSchedule.setLong(FLD_D2ND24, empschedule.getD2nd24());
			pstEmpSchedule.setLong(FLD_D2ND25, empschedule.getD2nd25());
			pstEmpSchedule.setLong(FLD_D2ND26, empschedule.getD2nd26());
			pstEmpSchedule.setLong(FLD_D2ND27, empschedule.getD2nd27());
			pstEmpSchedule.setLong(FLD_D2ND28, empschedule.getD2nd28());
			pstEmpSchedule.setLong(FLD_D2ND29, empschedule.getD2nd29());
			pstEmpSchedule.setLong(FLD_D2ND30, empschedule.getD2nd30());
			pstEmpSchedule.setLong(FLD_D2ND31, empschedule.getD2nd31());
                        
                        pstEmpSchedule.insert(); 
			empschedule.setOID(pstEmpSchedule.getlong(FLD_EMP_SCHEDULE_HISTORY_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleHistory(0),DBException.UNKNOWN); 
		}
		return empschedule.getOID();
	}

	public static long updateExc(EmpScheduleHistory empschedule) throws DBException{ 
		try{ 
			if(empschedule.getOID() != 0){ 
				PstEmpScheduleHistory pstEmpSchedule = new PstEmpScheduleHistory(empschedule.getOID());

                                pstEmpSchedule.setLong(FLD_EMP_SCHEDULE_ORG_ID, empschedule.getEmpScheduleOrgId());
				pstEmpSchedule.setLong(FLD_PERIOD_ID, empschedule.getPeriodId());
				pstEmpSchedule.setLong(FLD_EMPLOYEE_ID, empschedule.getEmployeeId());
				pstEmpSchedule.setLong(FLD_D1, empschedule.getD1());
				pstEmpSchedule.setLong(FLD_D2, empschedule.getD2());
				pstEmpSchedule.setLong(FLD_D3, empschedule.getD3());
				pstEmpSchedule.setLong(FLD_D4, empschedule.getD4());
				pstEmpSchedule.setLong(FLD_D5, empschedule.getD5());
				pstEmpSchedule.setLong(FLD_D6, empschedule.getD6());
				pstEmpSchedule.setLong(FLD_D7, empschedule.getD7());
				pstEmpSchedule.setLong(FLD_D8, empschedule.getD8());
				pstEmpSchedule.setLong(FLD_D9, empschedule.getD9());
				pstEmpSchedule.setLong(FLD_D10, empschedule.getD10());
				pstEmpSchedule.setLong(FLD_D11, empschedule.getD11());
				pstEmpSchedule.setLong(FLD_D12, empschedule.getD12());
				pstEmpSchedule.setLong(FLD_D13, empschedule.getD13());
				pstEmpSchedule.setLong(FLD_D14, empschedule.getD14());
				pstEmpSchedule.setLong(FLD_D15, empschedule.getD15());
				pstEmpSchedule.setLong(FLD_D16, empschedule.getD16());
				pstEmpSchedule.setLong(FLD_D17, empschedule.getD17());
				pstEmpSchedule.setLong(FLD_D18, empschedule.getD18());
				pstEmpSchedule.setLong(FLD_D19, empschedule.getD19());
				pstEmpSchedule.setLong(FLD_D20, empschedule.getD20());
				pstEmpSchedule.setLong(FLD_D21, empschedule.getD21());
				pstEmpSchedule.setLong(FLD_D22, empschedule.getD22());
				pstEmpSchedule.setLong(FLD_D23, empschedule.getD23());
				pstEmpSchedule.setLong(FLD_D24, empschedule.getD24());
				pstEmpSchedule.setLong(FLD_D25, empschedule.getD25());
				pstEmpSchedule.setLong(FLD_D26, empschedule.getD26());
				pstEmpSchedule.setLong(FLD_D27, empschedule.getD27());
				pstEmpSchedule.setLong(FLD_D28, empschedule.getD28());
				pstEmpSchedule.setLong(FLD_D29, empschedule.getD29());
				pstEmpSchedule.setLong(FLD_D30, empschedule.getD30());
				pstEmpSchedule.setLong(FLD_D31, empschedule.getD31());
                                pstEmpSchedule.setLong(FLD_D2ND1, empschedule.getD2nd1());
                                pstEmpSchedule.setLong(FLD_D2ND2, empschedule.getD2nd2());
                                pstEmpSchedule.setLong(FLD_D2ND3, empschedule.getD2nd3());
                                pstEmpSchedule.setLong(FLD_D2ND4, empschedule.getD2nd4());
                                pstEmpSchedule.setLong(FLD_D2ND5, empschedule.getD2nd5());
                                pstEmpSchedule.setLong(FLD_D2ND6, empschedule.getD2nd6());
                                pstEmpSchedule.setLong(FLD_D2ND7, empschedule.getD2nd7());
                                pstEmpSchedule.setLong(FLD_D2ND8, empschedule.getD2nd8());
                                pstEmpSchedule.setLong(FLD_D2ND9, empschedule.getD2nd9());
                                pstEmpSchedule.setLong(FLD_D2ND10, empschedule.getD2nd10());
                                pstEmpSchedule.setLong(FLD_D2ND11, empschedule.getD2nd11());
                                pstEmpSchedule.setLong(FLD_D2ND12, empschedule.getD2nd12());
                                pstEmpSchedule.setLong(FLD_D2ND13, empschedule.getD2nd13());
                                pstEmpSchedule.setLong(FLD_D2ND14, empschedule.getD2nd14());
                                pstEmpSchedule.setLong(FLD_D2ND15, empschedule.getD2nd15());
                                pstEmpSchedule.setLong(FLD_D2ND16, empschedule.getD2nd16());
                                pstEmpSchedule.setLong(FLD_D2ND17, empschedule.getD2nd17());
                                pstEmpSchedule.setLong(FLD_D2ND18, empschedule.getD2nd18());
                                pstEmpSchedule.setLong(FLD_D2ND19, empschedule.getD2nd19());
                                pstEmpSchedule.setLong(FLD_D2ND20, empschedule.getD2nd20());
                                pstEmpSchedule.setLong(FLD_D2ND21, empschedule.getD2nd21());
                                pstEmpSchedule.setLong(FLD_D2ND22, empschedule.getD2nd22());
                                pstEmpSchedule.setLong(FLD_D2ND23, empschedule.getD2nd23());
                                pstEmpSchedule.setLong(FLD_D2ND24, empschedule.getD2nd24());
                                pstEmpSchedule.setLong(FLD_D2ND25, empschedule.getD2nd25());
                                pstEmpSchedule.setLong(FLD_D2ND26, empschedule.getD2nd26());
                                pstEmpSchedule.setLong(FLD_D2ND27, empschedule.getD2nd27());
                                pstEmpSchedule.setLong(FLD_D2ND28, empschedule.getD2nd28());
                                pstEmpSchedule.setLong(FLD_D2ND29, empschedule.getD2nd29());
                                pstEmpSchedule.setLong(FLD_D2ND30, empschedule.getD2nd30());
                                pstEmpSchedule.setLong(FLD_D2ND31, empschedule.getD2nd31());
                                
                                pstEmpSchedule.update(); 
				return empschedule.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleHistory(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstEmpScheduleHistory pstEmpSchedule = new PstEmpScheduleHistory(oid);
			pstEmpSchedule.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstEmpScheduleHistory(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE_HISTORY; 
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
				EmpScheduleHistory empschedule = new EmpScheduleHistory();
				resultToObject(rs, empschedule);                                
				lists.add(empschedule);
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

	private static void resultToObject(ResultSet rs, EmpScheduleHistory empschedule){
		try{
			empschedule.setOID(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_HISTORY_ID]));
                        empschedule.setEmpScheduleOrgId(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_ORG_ID]));
			empschedule.setPeriodId(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_PERIOD_ID]));
			empschedule.setEmployeeId(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMPLOYEE_ID]));
			empschedule.setD1(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D1]));
			empschedule.setD2(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2]));
			empschedule.setD3(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D3]));
			empschedule.setD4(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D4]));
			empschedule.setD5(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D5]));
			empschedule.setD6(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D6]));
			empschedule.setD7(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D7]));
			empschedule.setD8(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D8]));
			empschedule.setD9(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D9]));
			empschedule.setD10(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D10]));
			empschedule.setD11(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D11]));
			empschedule.setD12(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D12]));
			empschedule.setD13(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D13]));
			empschedule.setD14(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D14]));
			empschedule.setD15(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D15]));
			empschedule.setD16(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D16]));
			empschedule.setD17(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D17]));
			empschedule.setD18(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D18]));
			empschedule.setD19(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D19]));
			empschedule.setD20(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D20]));
			empschedule.setD21(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D21]));
			empschedule.setD22(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D22]));
			empschedule.setD23(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D23]));
			empschedule.setD24(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D24]));
			empschedule.setD25(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D25]));
			empschedule.setD26(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D26]));
			empschedule.setD27(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D27]));
			empschedule.setD28(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D28]));
			empschedule.setD29(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D29]));
			empschedule.setD30(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D30]));
			empschedule.setD31(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D31]));
			empschedule.setD2nd1(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND1]));
			empschedule.setD2nd2(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND2]));
			empschedule.setD2nd3(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND3]));
			empschedule.setD2nd4(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND4]));
			empschedule.setD2nd5(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND5]));
			empschedule.setD2nd6(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND6]));
			empschedule.setD2nd7(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND7]));
			empschedule.setD2nd8(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND8]));
			empschedule.setD2nd9(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND9]));
			empschedule.setD2nd10(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND10]));
			empschedule.setD2nd11(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND11]));
			empschedule.setD2nd12(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND12]));
			empschedule.setD2nd13(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND13]));
			empschedule.setD2nd14(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND14]));
			empschedule.setD2nd15(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND15]));
			empschedule.setD2nd16(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND16]));
			empschedule.setD2nd17(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND17]));
			empschedule.setD2nd18(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND18]));
			empschedule.setD2nd19(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND19]));
			empschedule.setD2nd20(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND20]));
			empschedule.setD2nd21(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND21]));
			empschedule.setD2nd22(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND22]));
			empschedule.setD2nd23(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND23]));
			empschedule.setD2nd24(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND24]));
			empschedule.setD2nd25(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND25]));
			empschedule.setD2nd26(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND26]));
			empschedule.setD2nd27(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND27]));
			empschedule.setD2nd28(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND28]));
			empschedule.setD2nd29(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND29]));
			empschedule.setD2nd30(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND30]));
			empschedule.setD2nd31(rs.getLong(PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_D2ND31]));
                        
                }catch(Exception e){ }
	}

	public static boolean checkOID(long empScheduleId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE_HISTORY + " WHERE " + 
						PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_HISTORY_ID] + " = '" + empScheduleId +"'";

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
			String sql = "SELECT COUNT("+ PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_HISTORY_ID] + ") FROM " + TBL_HR_EMP_SCHEDULE_HISTORY;
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
        
    public static long deleteByEmployee(long emplOID) 
    {
    	try{
            String sql = " DELETE FROM "+PstEmpScheduleHistory.TBL_HR_EMP_SCHEDULE_HISTORY +
                 " WHERE " + PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMPLOYEE_ID] +
                 " = " + emplOID;
            int status = DBHandler.execUpdate(sql);
    	}
        catch(Exception exc){
        	System.out.println("error delete empschedule by employee "+exc.toString());
    	}
    	return emplOID;
    }

    public static boolean checkPeriode(long periodeId){
            DBResultSet dbrs = null;
            boolean result = false;
            try{
                    String sql = "SELECT * FROM " + TBL_HR_EMP_SCHEDULE_HISTORY + " WHERE " + 
                                            PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_PERIOD_ID] + " = '" + periodeId +"'";

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
    
    
    public static long storeEmpScheduleTemporary(EmpSchedule objEmpSchedule, long oidEmpScheduleCurr)
    {    
        long oidEmpSchedule = 0;  

        try
        {
            EmpScheduleHistory objEmpScheduleHistory = new EmpScheduleHistory(); 
            
            objEmpScheduleHistory.setEmpScheduleOrgId(oidEmpScheduleCurr);
            objEmpScheduleHistory.setPeriodId(objEmpSchedule.getPeriodId());
            objEmpScheduleHistory.setEmployeeId(objEmpSchedule.getEmployeeId());
            objEmpScheduleHistory.setD1(objEmpSchedule.getD1());
            objEmpScheduleHistory.setD2(objEmpSchedule.getD2());
            objEmpScheduleHistory.setD3(objEmpSchedule.getD3());
            objEmpScheduleHistory.setD4(objEmpSchedule.getD4());
            objEmpScheduleHistory.setD5(objEmpSchedule.getD5());
            objEmpScheduleHistory.setD6(objEmpSchedule.getD6());
            objEmpScheduleHistory.setD7(objEmpSchedule.getD7());
            objEmpScheduleHistory.setD8(objEmpSchedule.getD8());
            objEmpScheduleHistory.setD9(objEmpSchedule.getD9());
            objEmpScheduleHistory.setD10(objEmpSchedule.getD10());
            objEmpScheduleHistory.setD11(objEmpSchedule.getD11());
            objEmpScheduleHistory.setD12(objEmpSchedule.getD12());
            objEmpScheduleHistory.setD13(objEmpSchedule.getD13());
            objEmpScheduleHistory.setD14(objEmpSchedule.getD14());
            objEmpScheduleHistory.setD15(objEmpSchedule.getD15());
            objEmpScheduleHistory.setD16(objEmpSchedule.getD16());
            objEmpScheduleHistory.setD17(objEmpSchedule.getD17());
            objEmpScheduleHistory.setD18(objEmpSchedule.getD18());
            objEmpScheduleHistory.setD19(objEmpSchedule.getD19());
            objEmpScheduleHistory.setD20(objEmpSchedule.getD20());
            objEmpScheduleHistory.setD21(objEmpSchedule.getD21());
            objEmpScheduleHistory.setD22(objEmpSchedule.getD22());
            objEmpScheduleHistory.setD23(objEmpSchedule.getD23());
            objEmpScheduleHistory.setD24(objEmpSchedule.getD24());
            objEmpScheduleHistory.setD25(objEmpSchedule.getD25());
            objEmpScheduleHistory.setD26(objEmpSchedule.getD26());
            objEmpScheduleHistory.setD27(objEmpSchedule.getD27());
            objEmpScheduleHistory.setD28(objEmpSchedule.getD28());
            objEmpScheduleHistory.setD29(objEmpSchedule.getD29());
            objEmpScheduleHistory.setD30(objEmpSchedule.getD30());
            objEmpScheduleHistory.setD31(objEmpSchedule.getD31());
            objEmpScheduleHistory.setD2nd1(objEmpSchedule.getD2nd1());
            objEmpScheduleHistory.setD2nd2(objEmpSchedule.getD2nd2());
            objEmpScheduleHistory.setD2nd3(objEmpSchedule.getD2nd3());
            objEmpScheduleHistory.setD2nd4(objEmpSchedule.getD2nd4());
            objEmpScheduleHistory.setD2nd5(objEmpSchedule.getD2nd5());
            objEmpScheduleHistory.setD2nd6(objEmpSchedule.getD2nd6());
            objEmpScheduleHistory.setD2nd7(objEmpSchedule.getD2nd7());
            objEmpScheduleHistory.setD2nd8(objEmpSchedule.getD2nd8());
            objEmpScheduleHistory.setD2nd9(objEmpSchedule.getD2nd9());
            objEmpScheduleHistory.setD2nd10(objEmpSchedule.getD2nd10());
            objEmpScheduleHistory.setD2nd11(objEmpSchedule.getD2nd11());
            objEmpScheduleHistory.setD2nd12(objEmpSchedule.getD2nd12());
            objEmpScheduleHistory.setD2nd13(objEmpSchedule.getD2nd13());
            objEmpScheduleHistory.setD2nd14(objEmpSchedule.getD2nd14());
            objEmpScheduleHistory.setD2nd15(objEmpSchedule.getD2nd15());
            objEmpScheduleHistory.setD2nd16(objEmpSchedule.getD2nd16());
            objEmpScheduleHistory.setD2nd17(objEmpSchedule.getD2nd17());
            objEmpScheduleHistory.setD2nd18(objEmpSchedule.getD2nd18());
            objEmpScheduleHistory.setD2nd19(objEmpSchedule.getD2nd19());
            objEmpScheduleHistory.setD2nd20(objEmpSchedule.getD2nd20());
            objEmpScheduleHistory.setD2nd21(objEmpSchedule.getD2nd21());
            objEmpScheduleHistory.setD2nd22(objEmpSchedule.getD2nd22());
            objEmpScheduleHistory.setD2nd23(objEmpSchedule.getD2nd23());
            objEmpScheduleHistory.setD2nd24(objEmpSchedule.getD2nd24());
            objEmpScheduleHistory.setD2nd25(objEmpSchedule.getD2nd25());
            objEmpScheduleHistory.setD2nd26(objEmpSchedule.getD2nd26());
            objEmpScheduleHistory.setD2nd27(objEmpSchedule.getD2nd27());
            objEmpScheduleHistory.setD2nd28(objEmpSchedule.getD2nd28());
            objEmpScheduleHistory.setD2nd29(objEmpSchedule.getD2nd29());
            objEmpScheduleHistory.setD2nd30(objEmpSchedule.getD2nd30());
            objEmpScheduleHistory.setD2nd31(objEmpSchedule.getD2nd31());

            oidEmpSchedule = insertExc(objEmpScheduleHistory);
        }
        catch(Exception e)  
        {
            System.out.println("Exc when storeEmpScheduleTemporary : " + e.toString());
        }
        
        return oidEmpSchedule;
    }

    
    
    public static Vector getListEmpScheduleHistory(long lDepartmentOid, long lSectionOid, String sEmployeeName, String sPayrolNum)
            //update by satrya 2012-08-01
            //public static Vector getListEmpScheduleHistory(long lDepartmentOid, long lSectionOid, String sEmployeeName)
    {
        DBResultSet dbrs = null;
        Vector vResult = new Vector(1,1);

        try
        {
            String sql = "SELECT SCH.* FROM " + PstEmpScheduleHistory.TBL_HR_EMP_SCHEDULE_HISTORY + " AS SCH ";                                   
                         
                         String sDepartmentClause = "";
                         if(lDepartmentOid != 0)
                         {
                             sDepartmentClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] +                               
                                                 " = " + lDepartmentOid;
                         }                

                         String sSectionClause = "";
                         if(lSectionOid != 0)
                         {
                             sSectionClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] +                               
                                              " = " + lSectionOid;
                         }

                         String sEmployeeNameClause = "";
                         if(sEmployeeName!=null && sEmployeeName.length() > 0)
                         {
                             sEmployeeNameClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] +                               
                                                   " LIKE \"%" + sEmployeeName + "%\"";
                         }                             
                         //update by satrya 2012-08-01
                         String sEmployeePayrolNumClause = "";
                         if(sPayrolNum!=null && sPayrolNum.length() > 0)
                         {
                             sEmployeePayrolNumClause = " EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +                               
                                                   " LIKE \"%" + sPayrolNum + "%\"";
                         }
            
                         String sWhereClause = "";
                         if(sDepartmentClause!=null && sDepartmentClause.length()>0)
                         {
                             sWhereClause = sDepartmentClause;
                         }
                         
                         if(sSectionClause!=null && sSectionClause.length()>0)
                         {
                             if(sWhereClause!=null && sWhereClause.length()>0)
                             {
                                sWhereClause = sWhereClause + " AND " + sSectionClause;
                             }
                             else
                             {
                                 sWhereClause = sSectionClause;
                             }
                         }

                         if(sEmployeeNameClause!=null && sEmployeeNameClause.length()>0)
                         {
                             if(sWhereClause!=null && sWhereClause.length()>0)
                             {
                                sWhereClause = sWhereClause + " AND " + sEmployeeNameClause;
                             }
                             else
                             {
                                 sWhereClause = sEmployeeNameClause;
                             }
                         }
                         //update by satrya 2012-08-01
                         if(sEmployeePayrolNumClause!=null && sEmployeePayrolNumClause.length()>0)
                         {
                             if(sWhereClause!=null && sWhereClause.length()>0)
                             {
                                sWhereClause = sWhereClause + " AND " + sEmployeePayrolNumClause;
                             }
                             else
                             {
                                 sWhereClause = sEmployeePayrolNumClause;
                             }
                         }
                         //end

                         if(sWhereClause!=null && sWhereClause.length()>0)
                         {
                             sql = sql +  " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMP " + 
                                 " ON SCH." + PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMPLOYEE_ID] +
                                 " = EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] +
                                 " WHERE " + sWhereClause;
                         }            
                         
                         sql = sql + " ORDER BY SCH." + PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_HISTORY_ID];
                         
           // System.out.println(new PstEmpScheduleHistory().getClass().getName()+".getListEmpScheduleHistory() sql : " + sql);             
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while(rs.next()) 
            { 
                EmpScheduleHistory objEmpScheduleHistory = new EmpScheduleHistory();
                resultToObject(rs, objEmpScheduleHistory);                                
                vResult.add(objEmpScheduleHistory);
            }
            rs.close();
        }
        catch(Exception e)
        {
            System.out.println("err : "+e.toString());
        }
        finally
        {
            DBResultSet.close(dbrs);
            return vResult;
        }
    }
    
    
    public static void main(String args[])     
    {
//        System.out.println(".:: importDataFromPresence start");  
//        importDataFromPresence();  
//        System.out.println(".:: importDataFromPresence finish");   
    }
    
}
