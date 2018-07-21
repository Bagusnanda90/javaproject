
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 

/* package java */ 
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
//import com.dimata.harisma.db.DBHandler;
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBLogger;
import com.dimata.harisma.entity.masterdata.*; 
import com.dimata. harisma.entity.employee.*;

public class PstImageAssign extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 

	public static final  String TBL_IMAGE_ASSIGN      =   "hr_emp_image_assign";

	public static final  int FLD_IMG_ASSIGN_OID    = 0;
	public static final  int FLD_EMPLOYEE_OID      = 1;
	public static final  int FLD_PATH              = 2;

	public static final  String[] fieldNames = {
		"IMG_ASSIGN_OID",
		"EMPLOYEE_OID",
		"PATH"
	 }; 

	public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
		TYPE_STRING
	 }; 

	public PstImageAssign(){
	}

	public PstImageAssign(int i) throws DBException { 
		super(new PstImageAssign()); 
	}

	public PstImageAssign(String sOid) throws DBException { 
		super(new PstImageAssign(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}

	public PstImageAssign(long lOid) throws DBException { 
		super(new PstImageAssign(0)); 
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
		return TBL_IMAGE_ASSIGN;
	}

	public String[] getFieldNames(){ 
		return fieldNames; 
	}

	public int[] getFieldTypes(){ 
		return fieldTypes; 
	}

	public String getPersistentName(){ 
		return new PstImageAssign().getClass().getName(); 
	}

	public long fetchExc(Entity ent) throws Exception{ 
		ImageAssign imageAssign = fetchExc(ent.getOID()); 
		ent = (Entity)imageAssign; 
		return imageAssign.getOID(); 
	}

	public long insertExc(Entity ent) throws Exception{ 
		return insertExc((ImageAssign) ent); 
	}

	public long updateExc(Entity ent) throws Exception{ 
		return updateExc((ImageAssign) ent); 
	}

	public long deleteExc(Entity ent) throws Exception{ 
		if(ent==null){ 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		return deleteExc(ent.getOID()); 
	}

	public static ImageAssign fetchExc(long oid) throws DBException{ 
		try{ 
			ImageAssign imageAssign = new ImageAssign();
			PstImageAssign PstImageAssign = new PstImageAssign(oid); 
			imageAssign.setOID(oid);

			imageAssign.setEmployeeOid(PstImageAssign.getlong(FLD_EMPLOYEE_OID));
			imageAssign.setPath(PstImageAssign.getString(FLD_PATH));

			return imageAssign; 
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstImageAssign(0),DBException.UNKNOWN); 
		} 
	}

	public static long insertExc(ImageAssign imageAssign) throws DBException{ 
		try{ 
			PstImageAssign PstImageAssign = new PstImageAssign(0);

			PstImageAssign.setLong(FLD_EMPLOYEE_OID, imageAssign.getEmployeeOid());
			PstImageAssign.setString(FLD_PATH, imageAssign.getPath());

			PstImageAssign.insert(); 
			imageAssign.setOID(PstImageAssign.getlong(FLD_EMPLOYEE_OID));
                        
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstImageAssign(0),DBException.UNKNOWN); 
		}
		return imageAssign.getOID();
	}

	public static long updateExc(ImageAssign imageAssign) throws DBException{ 
		try{ 
			if(imageAssign.getOID() != 0){ 
				PstImageAssign PstImageAssign = new PstImageAssign(imageAssign.getOID());

				PstImageAssign.setLong(FLD_EMPLOYEE_OID, imageAssign.getEmployeeOid());
                                PstImageAssign.setString(FLD_PATH, imageAssign.getPath());


				PstImageAssign.update(); 
				return imageAssign.getOID();

			}
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstImageAssign(0),DBException.UNKNOWN); 
		}
		return 0;
	}

	public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstImageAssign PstImageAssign = new PstImageAssign(oid);
			PstImageAssign.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstImageAssign(0),DBException.UNKNOWN); 
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
			String sql = "SELECT * FROM " + TBL_IMAGE_ASSIGN; 
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
				ImageAssign imageAssign = new ImageAssign();
				resultToObject(rs, imageAssign);
				lists.add(imageAssign);
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

	private static void resultToObject(ResultSet rs, ImageAssign imageAssign){
		try{
			imageAssign.setOID(rs.getLong(PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID]));
			imageAssign.setEmployeeOid(rs.getLong(PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID]));
			imageAssign.setPath(rs.getString(PstImageAssign.fieldNames[PstImageAssign.FLD_PATH]));

		}catch(Exception e){ }
	}

	public static boolean checkOID(long imageAssignId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_IMAGE_ASSIGN + " WHERE " + 
						PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID] + " = " + imageAssignId;

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
			String sql = "SELECT COUNT("+ PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID] + ") FROM " + TBL_IMAGE_ASSIGN;
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
	public static int findLimitStart( long oid, int recordToGet, String whereClause,String order){
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, order); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   ImageAssign imageAssign = (ImageAssign)list.get(ls);
				   if(oid == imageAssign.getOID())
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
    
    public static ImageAssign getImageAssignByEmp(long empOid){
        ImageAssign img = new ImageAssign();
        String whereClause = PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID]+" = "+empOid;
        Vector vData = list(0, 0, whereClause, "");
        if(vData!=null){
            if(vData.size()>0){
                img = (ImageAssign)vData.get(0);
            }
        }
        return img;
    }
    
    public static Vector getList(int limitStart,int recordToGet, String whereClause, String order){
        Vector vList = new Vector(1,1);
        DBResultSet dbrs = null;
        try{
                String sql = "SELECT IMG."+PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID]
                        +" ,IMG."+PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID]+" AS EMP_OID"
                        +" ,IMG."+PstImageAssign.fieldNames[PstImageAssign.FLD_PATH]
                        +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                        +" ,EMP."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                        +" ,POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION]
                        +" ,SEC."+PstSection.fieldNames[PstSection.FLD_SECTION]
                        +" ,DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]
                        +" FROM " + TBL_IMAGE_ASSIGN +" AS IMG"
                        +" INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE +" AS EMP "
                        +" ON IMG." + PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID] 
                        +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        +" INNER JOIN " + PstPosition.TBL_HR_POSITION +" AS POS "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] 
                        +" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                        +" INNER JOIN " + PstSection.TBL_HR_SECTION +" AS SEC "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] 
                        +" = SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        +" INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT +" AS DEP "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] 
                        +" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                
                if(whereClause != null && whereClause.length() > 0)
                        sql = sql + " WHERE " + whereClause;
                if(order != null && order.length() > 0)
                        sql = sql + " ORDER BY " + order;
                if(limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                else
                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
                System.out.println("--->"+sql);
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {
                    Vector vTemp = new Vector(1,1);
                    //Employee
                    Employee emp = new Employee();
                    emp.setOID(rs.getLong(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                    emp.setEmployeeNum(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                    emp.setFullName(rs.getString(PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                    vTemp.add(emp);
                    //Postion
                    Position pos = new Position();
                    pos.setPosition(rs.getString(PstPosition.fieldNames[PstPosition.FLD_POSITION]));
                    vTemp.add(pos);
                    
                    //Section
                    Section sec = new Section();
                    sec.setSection(rs.getString(PstSection.fieldNames[PstSection.FLD_SECTION]));
                    vTemp.add(sec);
                    
                    //department
                    Department dep = new Department();
                    dep.setDepartment(rs.getString(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]));
                    vTemp.add(dep);
                    
                    //image assign
                    ImageAssign img = new ImageAssign();
                    img.setOID(rs.getLong(PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID]));
                    img.setEmployeeOid(rs.getLong("EMP_OID"));
                    img.setPath(rs.getString(PstImageAssign.fieldNames[PstImageAssign.FLD_PATH]));
                    vTemp.add(img);
                    
                    vList.add(vTemp);
                }
                rs.close();
        }catch(Exception e){
                System.out.println("err : "+e.toString());
        }finally{
                DBResultSet.close(dbrs);
                return vList;
        }
    }
    
    public static int getCountMoreParam(String whereClause){
        Vector vList = new Vector(1,1);
        DBResultSet dbrs = null;
        int count =0;
        try{
                String sql = "SELECT COUNT(IMG."+PstImageAssign.fieldNames[PstImageAssign.FLD_IMG_ASSIGN_OID]
                        +") AS NR_IMG "
                        +" FROM " + TBL_IMAGE_ASSIGN +" AS IMG"
                        +" INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE +" AS EMP "
                        +" ON IMG." + PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID] 
                        +" = EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                        +" INNER JOIN " + PstPosition.TBL_HR_POSITION +" AS POS "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] 
                        +" = POS."+PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                        +" INNER JOIN " + PstSection.TBL_HR_SECTION +" AS SEC "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] 
                        +" = SEC."+PstSection.fieldNames[PstSection.FLD_SECTION_ID]
                        +" INNER JOIN " + PstDepartment.TBL_HR_DEPARTMENT +" AS DEP "
                        +" ON EMP." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] 
                        +" = DEP."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
                
                if(whereClause != null && whereClause.length() > 0)
                        sql = sql + " WHERE " + whereClause;

                System.out.println("--->"+sql);
                
                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                
                while(rs.next()) {
                    count=rs.getInt(1);
                }
                rs.close();
        }catch(Exception e){
                System.out.println("err : "+e.toString());
        }finally{
                DBResultSet.close(dbrs);
                return count;
        }
    }
    
}
