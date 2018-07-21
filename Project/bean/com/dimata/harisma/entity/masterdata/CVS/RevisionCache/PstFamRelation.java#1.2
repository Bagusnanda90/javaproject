/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Wiweka
 */
/* package java */
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
import com.dimata.harisma.entity.masterdata.*;

import com.dimata.harisma.entity.employee.*;

public class PstFamRelation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{
    public static final  String TBL_HR_FAM_RELATION = "hr_family_relation";//"HR_EDUCATION";

	public static final  int FLD_FAMILY_RELATION_ID = 0;
	public static final  int FLD_FAMILY_RELATION = 1;
	public static final  int FLD_FAMILY_RELATION_TYPE = 2;

	public static final  String[] fieldNames = {
		"FAMILY_RELATION_ID",
		"FAMILY_RELATION",
		"FAMILY_RELATION_TYPE"
                
	 };

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_STRING,
                TYPE_INT
	 };

    public static final int SPOUSE           =   0;
    public static final int CHILD            =   1;
    //public static final int OTHER            =   2;


    public static String[] levelNames =
    {
        "Spouse",
        "Child",
        //"Other"
    };

    public static Vector getLevelKeys() {
        Vector keys = new Vector();

        for(int i=0; i<levelNames.length; i++) {
            keys.add(levelNames[i]);
        }

        return keys;
    }

    public static Vector getLevelValues() {
        Vector values = new Vector();

        for(int i=0; i<levelNames.length; i++) {
            values.add(String.valueOf(i));
        }

        return values;
    }


	public PstFamRelation(){
	}

	public PstFamRelation(int i) throws DBException {
		super(new PstFamRelation());
	}

	public PstFamRelation(String sOid) throws DBException {
		super(new PstFamRelation(0));
		if(!locate(sOid))
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		else
			return;
	}

	public PstFamRelation(long lOid) throws DBException {
		super(new PstFamRelation(0));
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
		return TBL_HR_FAM_RELATION;
	}

	public String[] getFieldNames(){
		return fieldNames;
	}

	public int[] getFieldTypes(){
		return fieldTypes;
	}

	public String getPersistentName(){
		return new PstFamRelation().getClass().getName();
	}

	public long fetchExc(Entity ent) throws Exception{
		FamRelation famRelation = fetchExc(ent.getOID());
		ent = (Entity)famRelation;
		return famRelation.getOID();
	}

	public long insertExc(Entity ent) throws Exception{
		return insertExc((FamRelation) ent);
	}

	public long updateExc(Entity ent) throws Exception{
		return updateExc((FamRelation) ent);
	}

	public long deleteExc(Entity ent) throws Exception{
		if(ent==null){
			throw new DBException(this,DBException.RECORD_NOT_FOUND);
		}
		return deleteExc(ent.getOID());
	}

	public static FamRelation fetchExc(long oid) throws DBException{
		try{
			FamRelation famRelation = new FamRelation();
			PstFamRelation pstFamRelation = new PstFamRelation(oid);
			famRelation.setOID(oid);

			famRelation.setFamRelation(pstFamRelation.getString(FLD_FAMILY_RELATION));
                        famRelation.setFamRelationType(pstFamRelation.getInt(FLD_FAMILY_RELATION_TYPE));

			return famRelation;
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstFamRelation(0),DBException.UNKNOWN);
		}
	}

	public static long insertExc(FamRelation famRelation) throws DBException{
		try{
			PstFamRelation pstFamRelation = new PstFamRelation(0);

			pstFamRelation.setString(FLD_FAMILY_RELATION, famRelation.getFamRelation());
                        pstFamRelation.setInt(FLD_FAMILY_RELATION_TYPE, famRelation.getFamRelationType());

			pstFamRelation.insert();
			famRelation.setOID(pstFamRelation.getlong(FLD_FAMILY_RELATION_ID));
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstFamRelation(0),DBException.UNKNOWN);
		}
		return famRelation.getOID();
	}

	public static long updateExc(FamRelation famRelation) throws DBException{
		try{
			if(famRelation.getOID() != 0){
				PstFamRelation pstFamRelation = new PstFamRelation(famRelation.getOID());

				pstFamRelation.setString(FLD_FAMILY_RELATION, famRelation.getFamRelation());
                                pstFamRelation.setInt(FLD_FAMILY_RELATION_TYPE, famRelation.getFamRelationType());

				pstFamRelation.update();

				return famRelation.getOID();

			}
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstFamRelation(0),DBException.UNKNOWN);
		}
		return 0;
	}
        

       	public static long deleteExc(long oid) throws DBException{
		try{
			PstFamRelation pstFamRelation = new PstFamRelation(oid);
			pstFamRelation.delete();
		}catch(DBException dbe){
			throw dbe;
		}catch(Exception e){
			throw new DBException(new PstFamRelation(0),DBException.UNKNOWN);
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
			String sql = "SELECT * FROM " + TBL_HR_FAM_RELATION;
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
				FamRelation famRelation = new FamRelation();
				resultToObject(rs, famRelation);
				lists.add(famRelation);
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
        
        public static Vector listRelationName(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector();
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_HR_FAM_RELATION;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE FAMILY_RELATION_ID=" + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				FamRelation famRelation = new FamRelation();
				resultToObject(rs, famRelation);
				lists.add(famRelation);
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

	private static void resultToObject(ResultSet rs, FamRelation famRelation){
		try{
			famRelation.setOID(rs.getLong(PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION_ID]));
			famRelation.setFamRelation(rs.getString(PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION]));
                        famRelation.setFamRelationType(rs.getInt(PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION_TYPE]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long famRelationId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_HR_FAM_RELATION + " WHERE " +
						PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION_ID] + " = " + famRelationId;

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
			String sql = "SELECT COUNT("+ PstFamRelation.fieldNames[PstFamRelation.FLD_FAMILY_RELATION_ID] + ") FROM " + TBL_HR_FAM_RELATION;
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
			  	   FamRelation famRelation = (FamRelation)list.get(ls);
				   if(oid == famRelation.getOID())
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

    public static boolean checkMaster(long oid){
    	if(PstFamilyMember.checkFamRelation(oid))
            return true;
    	else
            return false;
    }

}
