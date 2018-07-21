
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
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;


public class PstAssessmentFormSection extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_HR_ASS_FORM_SECTION = "hr_ass_form_section";//
        
	public static final  int FLD_ASS_FORM_SECTION_ID   = 0;
	public static final  int FLD_ASS_FORM_MAIN_ID      = 1;
	public static final  int FLD_SECTION               = 2;
	public static final  int FLD_DESCRIPTION           = 3;
	public static final  int FLD_SECTION_L2            = 4;
	public static final  int FLD_DESCRIPTION_L2        = 5;
	public static final  int FLD_ORDER_NUMBER          = 6;
	public static final  int FLD_PAGE                  = 7;
	public static final  int FLD_TYPE                  = 8;
        public static final  int FLD_POINT_EVALUATION_ID   = 9;
        public static final  int FLD_PREDICATE_EVALUATION_ID= 10;
        public static final  int FLD_WEIGHT_POINT= 11;
        
	public static final  String[] fieldNames = {
		"ASS_FORM_SECTION_ID",
		"ASS_FORM_MAIN_ID",
		"SECTION",
		"DESCRIPTION",
		"SECTION_L2",
		"DESCRIPTION_L2",
		"ORDER_NUMBER",
		"PAGE",
		"TYPE_SECTION",
                "POINT_EVALUATION_ID",
                "PREDICATE_EVALUATION_ID",
                "WEIGHT_POINT"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_INT,
		TYPE_INT,
		TYPE_INT,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_FLOAT
	 }; 

	public static final  int TYPE_TEXT_ONLY         = 0;
        public static final  int TYPE_WITH_BACKGROUND   = 1;
        public static final  int TYPE_TEXT_SUMMARY      = 2;
        
	public static final  String[] fieldTypeNames = {
		"TEXT ONLY",
		"WITH BACKGROUND",
                "TEXT SUMMARY"
	 }; 
        
	public PstAssessmentFormSection(){
	}

	public PstAssessmentFormSection(int i) throws DBException { 
		super(new PstAssessmentFormSection()); 
	}

	public PstAssessmentFormSection(String sOid) throws DBException { 
		super(new PstAssessmentFormSection(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstAssessmentFormSection(long lOid) throws DBException { 
		super(new PstAssessmentFormSection(0)); 
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
		return TBL_HR_ASS_FORM_SECTION;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstAssessmentFormSection().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		AssessmentFormSection assessmentFormSection = fetchExc(ent.getOID()); 
		ent = (Entity)assessmentFormSection; 
		return assessmentFormSection.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((AssessmentFormSection) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((AssessmentFormSection) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static AssessmentFormSection fetchExc(long oid) throws DBException{ 
		try{ 
			AssessmentFormSection assessmentFormSection = new AssessmentFormSection();
			PstAssessmentFormSection pstAssessmentFormSection = new PstAssessmentFormSection(oid); 
			assessmentFormSection.setOID(oid);

			assessmentFormSection.setAssFormMainId(pstAssessmentFormSection.getlong(FLD_ASS_FORM_MAIN_ID));
			assessmentFormSection.setSection(pstAssessmentFormSection.getString(FLD_SECTION));
			assessmentFormSection.setDescription(pstAssessmentFormSection.getString(FLD_DESCRIPTION));
			assessmentFormSection.setSection_L2(pstAssessmentFormSection.getString(FLD_SECTION_L2));
			assessmentFormSection.setDescription_L2(pstAssessmentFormSection.getString(FLD_DESCRIPTION_L2));
			assessmentFormSection.setOrderNumber(pstAssessmentFormSection.getInt(FLD_ORDER_NUMBER));
			assessmentFormSection.setPage(pstAssessmentFormSection.getInt(FLD_PAGE));
			assessmentFormSection.setType(pstAssessmentFormSection.getInt(FLD_TYPE));
                        assessmentFormSection.setPointEvaluationId(pstAssessmentFormSection.getlong(FLD_POINT_EVALUATION_ID));
                        assessmentFormSection.setPredicateEvaluationId(pstAssessmentFormSection.getlong(FLD_PREDICATE_EVALUATION_ID));
                        assessmentFormSection.setWeightPoint(pstAssessmentFormSection.getfloat(FLD_WEIGHT_POINT));
			return assessmentFormSection; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormSection(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(AssessmentFormSection assessmentFormSection) throws DBException{ 
		try{ 
			PstAssessmentFormSection pstAssessmentFormSection = new PstAssessmentFormSection(0);

			pstAssessmentFormSection.setLong(FLD_ASS_FORM_MAIN_ID, assessmentFormSection.getAssFormMainId()); 
			pstAssessmentFormSection.setString(FLD_SECTION, assessmentFormSection.getSection());
			pstAssessmentFormSection.setString(FLD_DESCRIPTION, assessmentFormSection.getDescription());
			pstAssessmentFormSection.setString(FLD_SECTION_L2, assessmentFormSection.getSection_L2());
			pstAssessmentFormSection.setString(FLD_DESCRIPTION_L2, assessmentFormSection.getDescription_L2());
			pstAssessmentFormSection.setInt(FLD_ORDER_NUMBER, assessmentFormSection.getOrderNumber());
			pstAssessmentFormSection.setInt(FLD_PAGE, assessmentFormSection.getPage());
			pstAssessmentFormSection.setInt(FLD_TYPE, assessmentFormSection.getType());
			pstAssessmentFormSection.setLong(FLD_POINT_EVALUATION_ID, assessmentFormSection.getPointEvaluationId());
                        pstAssessmentFormSection.setLong(FLD_PREDICATE_EVALUATION_ID, assessmentFormSection.getPredicateEvaluationId());
                        pstAssessmentFormSection.setFloat(FLD_WEIGHT_POINT, assessmentFormSection.getWeightPoint());
                        pstAssessmentFormSection.insert(); 
			assessmentFormSection.setOID(pstAssessmentFormSection.getlong(FLD_ASS_FORM_MAIN_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormSection(0),DBException.UNKNOWN); 
		}
		return assessmentFormSection.getOID();
	}

	public static long updateExc(AssessmentFormSection assessmentFormSection) throws DBException{ 
		try{ 
                    if(assessmentFormSection.getOID() != 0){ 
                        PstAssessmentFormSection pstAssessmentFormSection = new PstAssessmentFormSection(assessmentFormSection.getOID());

                        pstAssessmentFormSection.setLong(FLD_ASS_FORM_MAIN_ID, assessmentFormSection.getAssFormMainId()); 
			pstAssessmentFormSection.setString(FLD_SECTION, assessmentFormSection.getSection());
			pstAssessmentFormSection.setString(FLD_DESCRIPTION, assessmentFormSection.getDescription());
			pstAssessmentFormSection.setString(FLD_SECTION_L2, assessmentFormSection.getSection_L2());
			pstAssessmentFormSection.setString(FLD_DESCRIPTION_L2, assessmentFormSection.getDescription_L2());
			pstAssessmentFormSection.setInt(FLD_ORDER_NUMBER, assessmentFormSection.getOrderNumber());
			pstAssessmentFormSection.setInt(FLD_PAGE, assessmentFormSection.getPage());
			pstAssessmentFormSection.setInt(FLD_TYPE, assessmentFormSection.getType());
                        pstAssessmentFormSection.setLong(FLD_POINT_EVALUATION_ID, assessmentFormSection.getPointEvaluationId());
                        pstAssessmentFormSection.setLong(FLD_PREDICATE_EVALUATION_ID, assessmentFormSection.getPredicateEvaluationId());
                        pstAssessmentFormSection.setFloat(FLD_WEIGHT_POINT, assessmentFormSection.getWeightPoint());
                        pstAssessmentFormSection.update(); 
                        return assessmentFormSection.getOID();
                    }
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormSection(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstAssessmentFormSection pstAssessmentFormSection = new PstAssessmentFormSection(oid);
			pstAssessmentFormSection.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstAssessmentFormSection(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_SECTION; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                  //      System.out.println("=============>"+sql);
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				AssessmentFormSection assessmentFormSection = new AssessmentFormSection();
				resultToObject(rs, assessmentFormSection);
				lists.add(assessmentFormSection);
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

	public static void resultToObject(ResultSet rs, AssessmentFormSection assessmentFormSection){
		try{
                    assessmentFormSection.setOID(rs.getLong(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]));
                    assessmentFormSection.setAssFormMainId(rs.getLong(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]));
                    assessmentFormSection.setSection(rs.getString(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_SECTION]));
                    assessmentFormSection.setDescription(rs.getString(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_DESCRIPTION]));
                    assessmentFormSection.setSection_L2(rs.getString(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_SECTION_L2]));
                    assessmentFormSection.setDescription_L2(rs.getString(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_DESCRIPTION_L2]));
                    assessmentFormSection.setOrderNumber(rs.getInt(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]));
                    assessmentFormSection.setPage(rs.getInt(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]));
                    assessmentFormSection.setType(rs.getInt(PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_TYPE]));
                    assessmentFormSection.setPointEvaluationId(rs.getLong(PstAssessmentFormSection.fieldNames[FLD_POINT_EVALUATION_ID]));
                    assessmentFormSection.setPredicateEvaluationId(rs.getLong(PstAssessmentFormSection.fieldNames[FLD_PREDICATE_EVALUATION_ID]));
                    assessmentFormSection.setWeightPoint(rs.getFloat(fieldNames[FLD_WEIGHT_POINT]));
		}catch(Exception e){ }
	}

	public static boolean checkOID(long assessmentFormSectionId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_ASS_FORM_SECTION + " WHERE " + 
                                PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID] 
                                + " = " + assessmentFormSectionId;

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
			String sql = "SELECT COUNT("+ PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID] 
                                + ") FROM " + TBL_HR_ASS_FORM_SECTION;
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
			  	   AssessmentFormSection assessmentFormSection = (AssessmentFormSection)list.get(ls);
				   if(oid == assessmentFormSection.getOID())
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
