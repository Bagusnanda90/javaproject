
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

package com.dimata.harisma.entity.employee.assessment; 

/* package java */ 
import com.dimata.harisma.entity.employee.*;
import java.io.*
;
import java.sql.*
;import java.util.*
;import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.util.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

/* package harisma */
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.masterdata.PstGroupRank;
import com.dimata.harisma.form.employee.appraisal.FrmAppraisalMain;

public class PstAssessmentFormMain extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_ASS_FORM_MAIN = "hr_ass_form_main";//

	public static final  int FLD_ASS_FORM_MAIN_ID   = 0;
	public static final  int FLD_TITLE              = 1;
	public static final  int FLD_SUBTITLE           = 2;
	public static final  int FLD_TITLE_L2           = 3;
	public static final  int FLD_SUBTITLE_L2        = 4;
	public static final  int FLD_MAIN_DATA          = 5;
	public static final  int FLD_NOTE               = 6;
	//public static final  int FLD_GROUP_RANK_ID      = 7;
        ///public static final  int FLD_ASS_FORM_MAIN_ID_GROUP = 8;

	public static final  String[] fieldNames = {
		"ASS_FORM_MAIN_ID",
		"TITLE",
		"SUBTITLE",
		"TITLE_L2",
		"SUBTITLE_L2",
		"MAIN_DATA",
		"NOTE",
		//"GROUP_RANK_ID"
               // "ASS_FORM_MAIN_ID_GROUP"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		//TYPE_LONG,
                //TYPE_LONG
	 };
        
        public static final  int FLD_FORM_EMP_NAME                  = 0;
        public static final  int FLD_FORM_EMP_POSITION              = 1;
        public static final  int FLD_FORM_EMP_DEPARTMENT            = 2;
        public static final  int FLD_FORM_EMP_ASSUMED_POSITION      = 3;
        public static final  int FLD_FORM_EMP_DATE_JOINED_HOTEL     = 4;
        public static final  int FLD_FORM_EMP_DATE_JOINED_COMPANY   = 5;
        public static final  int FLD_FORM_ASSESSOR_NAME             = 6;
        public static final  int FLD_FORM_ASSESSOR_POSITION         = 7;
        public static final  int FLD_FORM_DATE_OF_ASSESSMENT        = 8;
        public static final  int FLD_FORM_DATE_OF_LAST_ASSESSMENT   = 9;
        public static final  int FLD_FORM_DATE_OF_NEXT_ASSESSMENT   = 10;
        
        
        
        public static final String[][] fieldFormValue = {
            {"NAME OF EMPLOYEE","Nama Karyawan"},
            {"JOB TITLE","Jabatan"},
            {"DEPARTMENT","Departemen"},
            {"DATE ASSUMED POSITION","Tanggal dimulainya jabatan sekarang"},
            {"DATE JOINED HOTEL","Tanggal mulai kerja di hotel"},
            {"DATE JOINED COMPANY","Tanggal mulai kerja di company"},
            {"NAME OF ASSESSOR","Nama Penilai/Penguji"},
            {"JOB TITLE","Jabatan"},
            {"DATE OF ASSESSMENT","Tanggal Penilaian"},
            {"DATE OF LAST ASSESSMENT","Tanggal Penilaian Terakhir"},
            {"DATE OF NEXT ASSESSMENT","Tanggal Penilaian Berikutnya"}
        };
        
        public static final String[]fieldFormTempName = {
            "FRM_FLD_TEMP_EMP_NAME",
            "FRM_FLD_TEMP_EMP_JOB",
            "FRM_FLD_TEMP_EMP_DEPT",
            "FRM_FLD_TEMP_EMP_ASS_POS",
            "FRM_FLD_TEMP_EMP_JOINED",
            "FRM_FLD_TEMP_DATE_JOINED_COMP",
            "FRM_FLD_TEMP_ASS_NAME",
            "FRM_FLD_TEMP_ASS_JOB",
            "FRM_FLD_TEMP_ASS",
            "FRM_FLD_TEMP_LAST",
            "FRM_FLD_TEMP_NEXT"
        };
        
        public static final  int[] fieldFormTypes = {
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_DATE,
		TYPE_DATE,
		TYPE_DATE,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_DATE,
		TYPE_DATE,
		TYPE_DATE
	 };
        //Belum digunakan
        public static final  int[] fieldFormReadonly = {
            //0 = read only
            //1 = input
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0
	 };
        
        public static final  int LANGUAGE_FIRST   = 0;
        public static final  int LANGUAGE_OTHER   = 1;
        
        public static final  int FORM_NOT_USE   = 0;
        public static final  int FORM_USE       = 1;
        public static final String[] fieldFormUsed = {
            "0",
            "1"
        };
        
        
        public static boolean cekFormUsed(String strCode,int formPos){
            boolean isUsed = false;
            try{
                    isUsed = String.valueOf(strCode.charAt(formPos)).equals(fieldFormUsed[FORM_USE]);
            }catch(Exception ex){}            
            return isUsed;
        }
        
        public static boolean[] cekFormUseds(String strCode){
            boolean[] isUseds = new boolean[strCode.length()];
            //inisialisasi
            for(int i=0;i<strCode.length();i++){
                isUseds[i] = false;
            }
            for(int j=0;j<strCode.length();j++){
                isUseds[j] = cekFormUsed(strCode, j);
            }
            return isUseds;
        }

	public PstAssessmentFormMain(){
	}

	public PstAssessmentFormMain(int i) throws DBException { 
		super(new PstAssessmentFormMain()); 
	}

	public PstAssessmentFormMain(String sOid) throws DBException { 
		super(new PstAssessmentFormMain(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstAssessmentFormMain(long lOid) throws DBException { 
		super(new PstAssessmentFormMain(0)); 
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
		return TBL_HR_ASS_FORM_MAIN;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstAssessmentFormMain().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		AssessmentFormMain assessmentFormMain = fetchExc(ent.getOID()); 
		ent = (Entity)assessmentFormMain; 
		return assessmentFormMain.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((AssessmentFormMain) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((AssessmentFormMain) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static AssessmentFormMain fetchExc(long oid) throws DBException{ 
		try{ 
			AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
			PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(oid); 
			assessmentFormMain.setOID(oid);

			assessmentFormMain.setTitle(pstAssessmentFormMain.getString(FLD_TITLE));
			assessmentFormMain.setSubtitle(pstAssessmentFormMain.getString(FLD_SUBTITLE));
			assessmentFormMain.setTitle_L2(pstAssessmentFormMain.getString(FLD_TITLE_L2));
			assessmentFormMain.setSubtitle_L2(pstAssessmentFormMain.getString(FLD_SUBTITLE_L2));
			assessmentFormMain.setMainData(pstAssessmentFormMain.getString(FLD_MAIN_DATA));
			assessmentFormMain.setNote(pstAssessmentFormMain.getString(FLD_NOTE));
			//assessmentFormMain.setGroupRankId(pstAssessmentFormMain.getlong(FLD_GROUP_RANK_ID));
                       // assessmentFormMain.setFormMainIdGroup(pstAssessmentFormMain.getlong(FLD_ASS_FORM_MAIN_ID_GROUP));
			return assessmentFormMain; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
		} 
	}

        public static AssessmentFormMain fetch(long oid) throws DBException{ 
		try{ 
                    
			AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
			PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(oid); 
			assessmentFormMain.setOID(oid);

			assessmentFormMain.setTitle(pstAssessmentFormMain.getString(FLD_TITLE));
			assessmentFormMain.setSubtitle(pstAssessmentFormMain.getString(FLD_SUBTITLE));
			assessmentFormMain.setTitle_L2(pstAssessmentFormMain.getString(FLD_TITLE_L2));
			assessmentFormMain.setSubtitle_L2(pstAssessmentFormMain.getString(FLD_SUBTITLE_L2));
			assessmentFormMain.setMainData(pstAssessmentFormMain.getString(FLD_MAIN_DATA));
			assessmentFormMain.setNote(pstAssessmentFormMain.getString(FLD_NOTE));
			//assessmentFormMain.setGroupRankId(pstAssessmentFormMain.getlong(FLD_GROUP_RANK_ID));
                       // assessmentFormMain.setFormMainIdGroup(pstAssessmentFormMain.getlong(FLD_ASS_FORM_MAIN_ID_GROUP));
                       // String groupRankId =PstAssessmentFormMain.listGroupRankId(0, 0, PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID_GROUP]+"="+oid, "");
                       // assessmentFormMain.setsGroupRankId(groupRankId.split("_")); 
			return assessmentFormMain; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
		} 
	}
	public static long insertExc(AssessmentFormMain assessmentFormMain) throws DBException{ 
		try{ 
			PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(0);

			pstAssessmentFormMain.setString(FLD_TITLE, assessmentFormMain.getTitle()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE, assessmentFormMain.getSubtitle()); 
			pstAssessmentFormMain.setString(FLD_TITLE_L2, assessmentFormMain.getTitle_L2()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE_L2, assessmentFormMain.getSubtitle_L2()); 
			pstAssessmentFormMain.setString(FLD_MAIN_DATA, assessmentFormMain.getMainData()); 
			pstAssessmentFormMain.setString(FLD_NOTE, assessmentFormMain.getNote()); 
			//pstAssessmentFormMain.setLong(FLD_GROUP_RANK_ID, assessmentFormMain.getGroupRankId()); 
                        //pstAssessmentFormMain.setLong(FLD_ASS_FORM_MAIN_ID_GROUP, assessmentFormMain.getFormMainIdGroup()); 

			pstAssessmentFormMain.insert(); 
			assessmentFormMain.setOID(pstAssessmentFormMain.getlong(FLD_ASS_FORM_MAIN_ID));
                        
                        //pstAssessmentFormMain.updateMainGroup(0, 1, PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]+"="+assessmentFormMain.getOID(), assessmentFormMain.getOID());
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
		}
		return assessmentFormMain.getOID();
	}
        
        public static long insert(AssessmentFormMain assessmentFormMain) throws DBException{ 
		try{ 
			PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(0);

			pstAssessmentFormMain.setString(FLD_TITLE, assessmentFormMain.getTitle()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE, assessmentFormMain.getSubtitle()); 
			pstAssessmentFormMain.setString(FLD_TITLE_L2, assessmentFormMain.getTitle_L2()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE_L2, assessmentFormMain.getSubtitle_L2()); 
			pstAssessmentFormMain.setString(FLD_MAIN_DATA, assessmentFormMain.getMainData()); 
			pstAssessmentFormMain.setString(FLD_NOTE, assessmentFormMain.getNote()); 
			//pstAssessmentFormMain.setLong(FLD_GROUP_RANK_ID, assessmentFormMain.getGroupRankId()); 
                        //pstAssessmentFormMain.setLong(FLD_ASS_FORM_MAIN_ID_GROUP, assessmentFormMain.getFormMainIdGroup()); 

			pstAssessmentFormMain.insert();
			assessmentFormMain.setOID(pstAssessmentFormMain.getlong(FLD_ASS_FORM_MAIN_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
		}
		return assessmentFormMain.getOID();
	}

	public static long updateExc(AssessmentFormMain assessmentFormMain) throws DBException{ 
		try{ 
                    if(assessmentFormMain.getOID() != 0){ 
                        PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(assessmentFormMain.getOID());

                        pstAssessmentFormMain.setString(FLD_TITLE, assessmentFormMain.getTitle()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE, assessmentFormMain.getSubtitle()); 
			pstAssessmentFormMain.setString(FLD_TITLE_L2, assessmentFormMain.getTitle_L2()); 
			pstAssessmentFormMain.setString(FLD_SUBTITLE_L2, assessmentFormMain.getSubtitle_L2()); 
			pstAssessmentFormMain.setString(FLD_MAIN_DATA, assessmentFormMain.getMainData()); 
			pstAssessmentFormMain.setString(FLD_NOTE, assessmentFormMain.getNote()); 
			//pstAssessmentFormMain.setLong(FLD_GROUP_RANK_ID, assessmentFormMain.getGroupRankId()); 
                        //pstAssessmentFormMain.setLong(FLD_ASS_FORM_MAIN_ID_GROUP, assessmentFormMain.getFormMainIdGroup()); 
                        pstAssessmentFormMain.update(); 
                        return assessmentFormMain.getOID();
                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstAssessmentFormMain pstAssessmentFormMain = new PstAssessmentFormMain(oid);
			pstAssessmentFormMain.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormMain(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_MAIN; 
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
				AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
				resultToObject(rs, assessmentFormMain);
				lists.add(assessmentFormMain);
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
         * Keterangan: mencari list detail
         * @param limitStart
         * @param recordToGet
         * @param whereClause
         * @param order
         * @return 
         */
         public static Vector listJoinDetail(int limitStart,int recordToGet, String whereClause, String order){
                Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = " SELECT afm.* "
                                + " FROM "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN + " AS afm "
                                + " INNER JOIN "+ PstAssessmentFormMainDetail.TBL_HR_ASS_FORM_MAIN_DETAIL + " AS afmd ON afm."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID] + "=afmd."+PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_ASS_FORM_MAIN_ID]; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
                        if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0){
				sql = sql + "";
                        }else{
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        }
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
				resultToObject(rs, assessmentFormMain);
                                assessmentFormMain.setGroupRankId(rs.getLong(PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_GROUP_RANK_ID])); 
				lists.add(assessmentFormMain);
			}

			rs.close();
			
		}catch(Exception e) {
			return lists;
		}finally {
			DBResultSet.close(dbrs);
                        return lists;
		}
	}
        public static int getCountDetail(int limitStart,int recordToGet, String whereClause, String order){
		DBResultSet dbrs = null;
		try {
			String sql = " SELECT gr."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_NAME]+",afm.* "
                                + " FROM "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN + " AS afm "
                                + " INNER JOIN "+ PstAssessmentFormMainDetail.TBL_HR_ASS_FORM_MAIN_DETAIL + " AS afmd ON afm."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID] + "=afmd."+PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_ASS_FORM_MAIN_ID]
                                + " INNER JOIN "+ PstGroupRank.TBL_HR_GROUP_RANK + " AS gr ON gr."+PstGroupRank.fieldNames[PstGroupRank.FLD_GROUP_RANK_ID] + "=afmd."+PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_GROUP_RANK_ID]; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
                        if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0){
				sql = sql + "";
                        }else{
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                        }
                         sql = sql + " GROUP BY afm."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID];
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
     

	public static void resultToObject(ResultSet rs, AssessmentFormMain assessmentFormMain){
		try{
                    assessmentFormMain.setOID(rs.getLong(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]));
                    assessmentFormMain.setTitle(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_TITLE]));
                    assessmentFormMain.setSubtitle(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_SUBTITLE]));
                    assessmentFormMain.setTitle_L2(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_TITLE_L2]));
                    assessmentFormMain.setSubtitle_L2(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_SUBTITLE_L2]));
                    assessmentFormMain.setMainData(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_MAIN_DATA]));
                    assessmentFormMain.setNote(rs.getString(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_NOTE]));
                    //assessmentFormMain.setGroupRankId(rs.getLong(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_GROUP_RANK_ID]));
                    //assessmentFormMain.setFormMainIdGroup(rs.getLong(PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID_GROUP]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long assessmentFormMainId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_MAIN + " WHERE " + 
                                PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID] 
                                + " = " + assessmentFormMainId;

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
			String sql = "SELECT COUNT("+ PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID] 
                                + ") FROM " + TBL_HR_ASS_FORM_MAIN;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause, String orderClause){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   AssessmentFormMain assessmentFormMain = (AssessmentFormMain)list.get(ls);
				   if(oid == assessmentFormMain.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
	/* This method used to find command where current data */
	public static int findLimitCommand(int start, int recordToGet, int vectSize){
		 int cmd = Command.LIST;
		 int mdl = vectSize % recordToGet;
		 vectSize = vectSize + (recordToGet - mdl);
		 if(start == 0)
			 cmd =  Command.FIRST;
		 else{
			 if(start == (vectSize-recordToGet))
				 cmd = Command.LAST;
			 else{
				 start = start + recordToGet;
				 if(start <= (vectSize - recordToGet)){
					 cmd = Command.NEXT;
				 }else{
					 start = start - recordToGet;
					 if(start > 0){
						 cmd = Command.PREV;  
					 } 
				 }
			 } 
		 }
 
		 return cmd;
	}
        

        
        
}
