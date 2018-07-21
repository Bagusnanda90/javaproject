/*
 * Form Name  	:  FrmPosition.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.attendance;

/* java package */

import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.attendance.DpStockManagement;

public class FrmDpStockManagement extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private DpStockManagement dpStockManagement;

    public static final String FRM_DP_STOCK_MANAGEMENT = "FRM_DP_STOCK_MANAGEMENT";  

    public static final int FRM_FLD_DP_STOCK_ID         = 0;
    public static final int FRM_FLD_LEAVE_PERIODE_ID    = 1;
    public static final int FRM_FLD_EMPLOYEE_ID         = 2;
    public static final int FRM_FLD_DP_QTY              = 3;
    public static final int FRM_FLD_OWNING_DATE         = 4;
    public static final int FRM_FLD_EXPIRED_DATE        = 5;
    public static final int FRM_FLD_EXCEPTION_FLAG      = 6;
    public static final int FRM_FLD_EXPIRED_DATE_EXC    = 7;
    public static final int FRM_FLD_DP_STATUS           = 8;
    public static final int FRM_FLD_NOTE                = 9;
    public static final int FRM_FLD_QTY_RESIDUE         = 10;
    public static final int FRM_FLD_QTY_USED            = 11;
    //update by satrya 2013-02-24
    public static final int FRM_FLD_FLAG_STOCK = 12;
    public static String[] fieldNames = {
        "FRM_FLD_DP_STOCK_ID",
        "FRM_FLD_LEAVE_PERIODE_ID", 
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_DP_QTY",
        "FRM_FLD_OWNING_DATE",
        "FRM_FLD_EXPIRED_DATE",
        "FRM_FLD_EXCEPTION_FLAG",
        "FRM_FLD_EXPIRED_DATE_EXC",
        "FRM_FLD_DP_STATUS",
        "FRM_FLD_NOTE",
        "FRM_FLD_QTY_RESIDUE",
        "FRM_FLD_QTY_USED",
        "FRM_FLD_FLAG_STOCK"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,  
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_FLOAT  /*update by satrya 2013-10-03 dikarenakan konfigurasi memperbolehkan ada nilai 0 contoh melia + ENTRY_REQUIRED*/,
        TYPE_DATE  + ENTRY_REQUIRED,
        TYPE_DATE  + ENTRY_REQUIRED,
        TYPE_INT ,
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT
    };

    public FrmDpStockManagement() {
    }

    public FrmDpStockManagement(DpStockManagement dpStockManagement) {
        this.dpStockManagement = dpStockManagement;
    }

    public FrmDpStockManagement(HttpServletRequest request, DpStockManagement dpStockManagement) {
        super(new FrmDpStockManagement(dpStockManagement), request);
        this.dpStockManagement = dpStockManagement;
    }

    public String getFormName() {
        return FRM_DP_STOCK_MANAGEMENT;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public DpStockManagement getEntityObject() {
        return dpStockManagement;
    }  

    public void requestEntityObject(DpStockManagement dpStockManagement) {
        try {
            
            this.requestParam();
            dpStockManagement.setOID(getLong(FRM_FLD_DP_STOCK_ID));
            dpStockManagement.setLeavePeriodeId(getLong(FRM_FLD_LEAVE_PERIODE_ID));
            dpStockManagement.setDtOwningDate(getDate(FRM_FLD_OWNING_DATE));  
            dpStockManagement.setiDpQty(getFloat(FRM_FLD_DP_QTY));
            dpStockManagement.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            dpStockManagement.setStNote(getString(FRM_FLD_NOTE));
            dpStockManagement.setQtyResidue(getFloat(FRM_FLD_QTY_RESIDUE));
            dpStockManagement.setQtyUsed(getFloat(FRM_FLD_QTY_USED));
            dpStockManagement.setiDpStatus(getInt(FRM_FLD_DP_STATUS));
            dpStockManagement.setiExceptionFlag(getInt(FRM_FLD_EXCEPTION_FLAG));
            dpStockManagement.setDtExpiredDateExc(getDate(FRM_FLD_EXPIRED_DATE_EXC));   
            dpStockManagement.setDtExpiredDate(getDate(FRM_FLD_EXPIRED_DATE));
            //update by satrya 2013-02-24
            dpStockManagement.setFlagStock(getInt(FRM_FLD_FLAG_STOCK));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
