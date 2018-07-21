
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

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.entity.*;

//Gede_7Feb2012 {
import com.dimata.harisma.utility.harisma.machine.db.DBException;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.machine.db.I_DBInterface;
import com.dimata.harisma.utility.harisma.machine.db.I_DBType;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Ari_20111002
 * Menambah Company, Division, Level dan EmpCategory
 * @author Wiweka
 */
public class PstInformationHrdDesktop extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HRD_INFORMATION = "hr_hrd_information";//"HR_EMP_CATEGORY";
    public static final int FLD_INFORMATION_HRD_ID = 0;
    public static final int FLD_NAMA_INFORMATION = 1;
    public static final int FLD_DATE_START = 2;
    public static final int FLD_DATE_END = 3;
    public static final String[] fieldNames = {
        "INFORMATION_HRD_ID",
        "NAMA_INFORMATION",
        "DATE_START",
        "DATE_END"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,};
    
    

    public PstInformationHrdDesktop() {
    }

    public PstInformationHrdDesktop(int i) throws DBException {
        super(new PstInformationHrdDesktop());
    }

    public PstInformationHrdDesktop(String sOid) throws DBException {
        super(new PstInformationHrdDesktop(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstInformationHrdDesktop(long lOid) throws DBException {
        super(new PstInformationHrdDesktop(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_HRD_INFORMATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstInformationHrdDesktop().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
       return 0;
    }

    public long insertExc(Entity ent) throws Exception {
        return 0;
    }

    public long updateExc(Entity ent) throws Exception {
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static long insertExc(TabelDataInformationTransfer tabelDataInformationTransfer) throws DBException {
        try {
            PstInformationHrdDesktop pstPositionDestopTransfer = new PstInformationHrdDesktop(0);

           pstPositionDestopTransfer.setString(FLD_NAMA_INFORMATION, tabelDataInformationTransfer.getNamaInformation());
            pstPositionDestopTransfer.setDate(FLD_DATE_START, tabelDataInformationTransfer.getDateStart());
            pstPositionDestopTransfer.setDate(FLD_DATE_END, tabelDataInformationTransfer.getDateEnd());
            
            pstPositionDestopTransfer.insert(tabelDataInformationTransfer.getInformationId());
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrdDesktop(0), DBException.UNKNOWN);
        }
        return tabelDataInformationTransfer.getInformationId();
    }

    public static long updateExc(TabelDataInformationTransfer tabelDataInformationTransfer) throws DBException {
        try {
            if (tabelDataInformationTransfer.getInformationId() != 0) {
                PstInformationHrdDesktop pstPositionDestopTransfer = new PstInformationHrdDesktop(tabelDataInformationTransfer.getInformationId());

               
              pstPositionDestopTransfer.setString(FLD_NAMA_INFORMATION, tabelDataInformationTransfer.getNamaInformation());
            pstPositionDestopTransfer.setDate(FLD_DATE_START, tabelDataInformationTransfer.getDateStart());
            pstPositionDestopTransfer.setDate(FLD_DATE_END, tabelDataInformationTransfer.getDateEnd());
            

                pstPositionDestopTransfer.update();
                return tabelDataInformationTransfer.getInformationId();

            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrdDesktop(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstInformationHrdDesktop pstCareerPath = new PstInformationHrdDesktop(oid);
            pstCareerPath.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstInformationHrdDesktop(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static boolean checkOID(long positionId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HRD_INFORMATION + " WHERE "
                    + PstInformationHrdDesktop.fieldNames[PstInformationHrdDesktop.FLD_INFORMATION_HRD_ID] + " = " + positionId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }
    
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HRD_INFORMATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                TabelDataInformationTransfer tabelDataInformationTransfer = new TabelDataInformationTransfer();
                tabelDataInformationTransfer.setDateStart(DBHandler.convertDate(rs.getDate(fieldNames[FLD_DATE_START]), rs.getTime(fieldNames[FLD_DATE_START])));
                tabelDataInformationTransfer.setDateEnd(DBHandler.convertDate(rs.getDate(fieldNames[FLD_DATE_END]), rs.getTime(fieldNames[FLD_DATE_END])));
                tabelDataInformationTransfer.setInformationId(rs.getLong(fieldNames[FLD_INFORMATION_HRD_ID]));
                tabelDataInformationTransfer.setNamaInformation(rs.getString(fieldNames[FLD_NAMA_INFORMATION]));
                lists.add(tabelDataInformationTransfer);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
             return lists;
        }
    }
    public static Hashtable<String,Boolean> hashHrdInfoAda(int limitStart, int recordToGet, String whereClause, String order) {
        Hashtable hashHrdInfoAda= new Hashtable();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "+fieldNames[FLD_INFORMATION_HRD_ID]+" FROM " + TBL_HRD_INFORMATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hashHrdInfoAda.put(""+rs.getLong(fieldNames[FLD_INFORMATION_HRD_ID]), true);
            }
            rs.close();
            

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return hashHrdInfoAda;
        }
        
    }
}
