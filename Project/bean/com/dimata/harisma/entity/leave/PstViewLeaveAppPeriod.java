/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;
import java.sql.ResultSet;


import java.util.Vector;
import java.util.Date;

import com.dimata.util.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.attendance.EmpSchedule;


import com.dimata.system.entity.system.PstSystemProperty;

/**
 *
 * @author Tu Roy
 */


public class PstViewLeaveAppPeriod extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language{

    public static final String TBL_VIEW_LEAVE_APP_PERIOD = "hr_view_leave_app_period";
    
    public static final int FLD_EMPLOYEE_ID     = 0;    
    public static final int FLD_AL_START_DATE   = 1;
    public static final int FLD_AL_END_DATE     = 2;
    public static final int FLD_LL_START_DATE   = 3;
    public static final int FLD_LL_END_DATE     = 4;
    public static final int FLD_DP_START_DATE   = 5;
    public static final int FLD_DP_END_DATE     = 6;
    public static final int FLD_SP_START_DATE   = 7;
    public static final int FLD_SP_END_DATE     = 8;
    public static final int FLD_LEAVE_APPLICATION_ID = 9;
    
    public static final String[] fieldNames = {
        "EMPLOYEE_ID",       
        "AL_START_DATE",
        "AL_END_DATE",       
        "LL_START_DATE",       
        "LL_END_DATE",       
        "DP_START_DATE",
        "DP_END_DATE",
        "SP_START_DATE",
        "SP_END_DATE",
        "LEAVE_APPLICATION_ID"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG
    };

    
    public PstViewLeaveAppPeriod() {
    }
    
    public PstViewLeaveAppPeriod(int i) throws DBException {
        super(new PstViewLeaveAppPeriod());
    }
    
    public PstViewLeaveAppPeriod(String sOid) throws DBException {
        super(new PstViewLeaveAppPeriod(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public PstViewLeaveAppPeriod(long lOid) throws DBException {
        super(new PstViewLeaveAppPeriod(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public String getTableName() {
        return TBL_VIEW_LEAVE_APP_PERIOD;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getPersistentName() {
        return new PstViewLeaveAppPeriod().getClass().getName();
    }
    
    public long fetchExc(Entity ent) throws Exception {
        ViewLeaveAppPeriod objViewLeaveAppPeriod = fetchExc(ent.getOID());
        return objViewLeaveAppPeriod.getOID();
    }
    
    public static ViewLeaveAppPeriod fetchExc(long oid) throws DBException 
    {
        try 
        {
            ViewLeaveAppPeriod objViewLeaveAppPeriod = new ViewLeaveAppPeriod();
            PstViewLeaveAppPeriod objPstViewLeaveAppPeriod = new PstViewLeaveAppPeriod(oid);

            objViewLeaveAppPeriod.setOID(oid);            
            objViewLeaveAppPeriod.setAl_start_date(objPstViewLeaveAppPeriod.getDate(FLD_AL_START_DATE));
            objViewLeaveAppPeriod.setAl_end_date(objPstViewLeaveAppPeriod.getDate(FLD_AL_END_DATE));
            objViewLeaveAppPeriod.setLl_start_date(objPstViewLeaveAppPeriod.getDate(FLD_LL_START_DATE));
            objViewLeaveAppPeriod.setLl_end_date(objPstViewLeaveAppPeriod.getDate(FLD_LL_END_DATE));
            objViewLeaveAppPeriod.setDp_start_date(objPstViewLeaveAppPeriod.getDate(FLD_DP_START_DATE));
            objViewLeaveAppPeriod.setDp_end_date(objPstViewLeaveAppPeriod.getDate(FLD_DP_END_DATE));
              
            return objViewLeaveAppPeriod;
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
    }
    
    public long updateExc(Entity ent) throws Exception 
    {
        return updateExc((ViewLeaveAppPeriod) ent);
    }
    
    public static long updateExc(ViewLeaveAppPeriod objViewLeaveAppPeriod) throws DBException 
    {
        try 
        {
            if (objViewLeaveAppPeriod.getOID() != 0) 
            {
                PstViewLeaveAppPeriod objPstViewLeaveAppPeriod = new PstViewLeaveAppPeriod(objViewLeaveAppPeriod.getOID());                
                
                objPstViewLeaveAppPeriod.setDate(FLD_AL_START_DATE, objViewLeaveAppPeriod.getAl_start_date());
                objPstViewLeaveAppPeriod.setDate(FLD_AL_END_DATE  , objViewLeaveAppPeriod.getAl_end_date());
                objPstViewLeaveAppPeriod.setDate(FLD_DP_START_DATE  , objViewLeaveAppPeriod.getDp_end_date());
                objPstViewLeaveAppPeriod.setDate(FLD_DP_END_DATE, objViewLeaveAppPeriod.getDp_end_date());
                objPstViewLeaveAppPeriod.setDate(FLD_LL_START_DATE, objViewLeaveAppPeriod.getLl_start_date());
                objPstViewLeaveAppPeriod.setDate(FLD_LL_END_DATE, objViewLeaveAppPeriod.getLl_end_date());                
                
                objPstViewLeaveAppPeriod.update();
                return objViewLeaveAppPeriod.getOID();                
            }
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstViewLeaveAppPeriod(0), DBException.UNKNOWN);
        }
        return 0;
    }
    
    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }
    
    public static long deleteExc(long oid) throws DBException {
        try {
            PstViewLeaveAppPeriod objPstViewLeaveAppPeriod = new PstViewLeaveAppPeriod(oid);
            objPstViewLeaveAppPeriod.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstViewLeaveAppPeriod(0), DBException.UNKNOWN);
        }
        return oid;
    }
    
    public long insertExc(Entity ent) throws Exception {
        return insertExc((ViewLeaveAppPeriod)ent);
    }
    
    public static long insertExc(ViewLeaveAppPeriod objViewLeaveAppPeriod) throws DBException 
    {
        try 
        {
            PstViewLeaveAppPeriod objPstViewLeaveAppPeriod = new PstViewLeaveAppPeriod(0);
            objPstViewLeaveAppPeriod.setLong(FLD_EMPLOYEE_ID, objViewLeaveAppPeriod.getEmployee_id());
            objPstViewLeaveAppPeriod.setDate(FLD_AL_START_DATE, objViewLeaveAppPeriod.getAl_start_date());
            objPstViewLeaveAppPeriod.setDate(FLD_AL_END_DATE, objViewLeaveAppPeriod.getAl_end_date());
            objPstViewLeaveAppPeriod.setDate(FLD_LL_START_DATE, objViewLeaveAppPeriod.getLl_start_date());
            objPstViewLeaveAppPeriod.setDate(FLD_LL_START_DATE, objViewLeaveAppPeriod.getLl_end_date());
            objPstViewLeaveAppPeriod.setDate(FLD_DP_START_DATE, objViewLeaveAppPeriod.getDp_start_date());
            objPstViewLeaveAppPeriod.setDate(FLD_DP_END_DATE, objViewLeaveAppPeriod.getDp_end_date());
            
            objPstViewLeaveAppPeriod.insert();
            objViewLeaveAppPeriod.setEmployee_id(objPstViewLeaveAppPeriod.getlong(FLD_EMPLOYEE_ID));
        }
        catch (DBException dbe) 
        {
            throw dbe;
        }
        catch (Exception e) 
        {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
        return objViewLeaveAppPeriod.getOID();
    }
}
