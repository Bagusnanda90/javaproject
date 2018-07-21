/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;

public class PstPositionInCompany extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_POSITION_IN_COMPANY = "Position_in_company";
    public static final int FLD_POS_IN_COMPANY_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_COMPANY_ID = 2;
    public static final int FLD_DIVISION_ID = 3;
    public static final int FLD_DEPARTMENT_ID = 4;
    public static final int FLD_SECTION_ID = 5;
    public static final int FLD_LEVEL_ID = 6;
    public static String[] fieldNames = {
        "POS_IN_COMPANY_ID",
        "POSITION_ID",
        "COMPANY_ID",
        "DIVISION_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "LEVEL_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstPositionInCompany() {
    }

    public PstPositionInCompany(int i) throws DBException {
        super(new PstPositionInCompany());
    }

    public PstPositionInCompany(String sOid) throws DBException {
        super(new PstPositionInCompany(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstPositionInCompany(long lOid) throws DBException {
        super(new PstPositionInCompany(0));
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
        return TBL_POSITION_IN_COMPANY;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstPositionInCompany().getClass().getName();
    }

    public static PositionInCompany fetchExc(long oid) throws DBException {
        try {
            PositionInCompany entPositionInCompany = new PositionInCompany();
            PstPositionInCompany pstPositionInCompany = new PstPositionInCompany(oid);
            entPositionInCompany.setOID(oid);
            entPositionInCompany.setPositionId(pstPositionInCompany.getLong(FLD_POSITION_ID));
            entPositionInCompany.setCompanyId(pstPositionInCompany.getLong(FLD_COMPANY_ID));
            entPositionInCompany.setDivisionId(pstPositionInCompany.getLong(FLD_DIVISION_ID));
            entPositionInCompany.setDepartmentId(pstPositionInCompany.getLong(FLD_DEPARTMENT_ID));
            entPositionInCompany.setSectionId(pstPositionInCompany.getLong(FLD_SECTION_ID));
            entPositionInCompany.setLevelId(pstPositionInCompany.getLong(FLD_LEVEL_ID));
            return entPositionInCompany;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionInCompany(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        PositionInCompany entPositionInCompany = fetchExc(entity.getOID());
        entity = (Entity) entPositionInCompany;
        return entPositionInCompany.getOID();
    }

    public static synchronized long updateExc(PositionInCompany entPositionInCompany) throws DBException {
        try {
            if (entPositionInCompany.getOID() != 0) {
                PstPositionInCompany pstPositionInCompany = new PstPositionInCompany(entPositionInCompany.getOID());
                pstPositionInCompany.setLong(FLD_POSITION_ID, entPositionInCompany.getPositionId());
                pstPositionInCompany.setLong(FLD_COMPANY_ID, entPositionInCompany.getCompanyId());
                pstPositionInCompany.setLong(FLD_DIVISION_ID, entPositionInCompany.getDivisionId());
                pstPositionInCompany.setLong(FLD_DEPARTMENT_ID, entPositionInCompany.getDepartmentId());
                pstPositionInCompany.setLong(FLD_SECTION_ID, entPositionInCompany.getSectionId());
                pstPositionInCompany.setLong(FLD_LEVEL_ID, entPositionInCompany.getLevelId());
                pstPositionInCompany.update();
                return entPositionInCompany.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionInCompany(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((PositionInCompany) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstPositionInCompany pstPositionInCompany = new PstPositionInCompany(oid);
            pstPositionInCompany.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionInCompany(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(PositionInCompany entPositionInCompany) throws DBException {
        try {
            PstPositionInCompany pstPositionInCompany = new PstPositionInCompany(0);
            pstPositionInCompany.setLong(FLD_POS_IN_COMPANY_ID, entPositionInCompany.getPosInCompanyId());
            pstPositionInCompany.setLong(FLD_POSITION_ID, entPositionInCompany.getPositionId());
            pstPositionInCompany.setLong(FLD_COMPANY_ID, entPositionInCompany.getCompanyId());
            pstPositionInCompany.setLong(FLD_DIVISION_ID, entPositionInCompany.getDivisionId());
            pstPositionInCompany.setLong(FLD_DEPARTMENT_ID, entPositionInCompany.getDepartmentId());
            pstPositionInCompany.setLong(FLD_SECTION_ID, entPositionInCompany.getSectionId());
            pstPositionInCompany.setLong(FLD_LEVEL_ID, entPositionInCompany.getLevelId());
            pstPositionInCompany.insert();
            entPositionInCompany.setOID(pstPositionInCompany.getLong(FLD_POS_IN_COMPANY_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstPositionInCompany(0), DBException.UNKNOWN);
        }
        return entPositionInCompany.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((PositionInCompany) entity);
    }

    public static void resultToObject(ResultSet rs, PositionInCompany entPositionInCompany) {
        try {
            entPositionInCompany.setPosInCompanyId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_POS_IN_COMPANY_ID]));
            entPositionInCompany.setPositionId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_POSITION_ID]));
            entPositionInCompany.setCompanyId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_COMPANY_ID]));
            entPositionInCompany.setDivisionId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_DIVISION_ID]));
            entPositionInCompany.setDepartmentId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_DEPARTMENT_ID]));
            entPositionInCompany.setSectionId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_SECTION_ID]));
            entPositionInCompany.setLevelId(rs.getLong(PstPositionInCompany.fieldNames[PstPositionInCompany.FLD_LEVEL_ID]));
        } catch (Exception e) {
        }
    }
}
