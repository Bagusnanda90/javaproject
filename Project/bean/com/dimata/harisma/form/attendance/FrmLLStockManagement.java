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
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.LLStockManagement;

public class FrmLLStockManagement extends FRMHandler implements I_FRMInterface, I_FRMType {
    private LLStockManagement llStockManagement;

    public static final String FRM_LL_STOCK_MANAGEMENT = "FRM_LL_STOCK_MANAGEMENT";

    public static final int FRM_FIELD_LEAVE_PERIODE_ID = 0;
    public static final int FRM_FIELD_LL_QTY = 1;
    public static final int FRM_FIELD_OWNING_DATE = 2;
    public static final int FRM_FIELD_LL_STATUS = 3;
    public static final int FRM_FIELD_NOTE = 4;
    public static final int FRM_FIELD_QTY_RESIDUE = 5;
    public static final int FRM_FIELD_QTY_USED = 6;
    public static final int FRM_FIELD_EMPLOYEE_ID = 7;
    public static final int FRM_FIELD_ENTITLED = 8;

    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_PERIODE_ID", 
        "FRM_FIELD_LL_QTY",
        "FRM_FIELD_OWNING_DATE",
        "FRM_FIELD_LL_STATUS",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_QTY_RESIDUE",
        "FRM_FIELD_QTY_USED",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_ENTITLED"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_INT,   
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_INT
    };

    public FrmLLStockManagement() {
    }

    public FrmLLStockManagement(LLStockManagement llStockManagement) {
        this.llStockManagement = llStockManagement;
    }

    public FrmLLStockManagement(HttpServletRequest request, LLStockManagement llStockManagement) {
        super(new FrmLLStockManagement(llStockManagement), request);
        this.llStockManagement = llStockManagement;
    }

    public String getFormName() {
        return FRM_LL_STOCK_MANAGEMENT;
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

    public LLStockManagement getEntityObject() {
        return llStockManagement;
    }
                     
    public void requestEntityObject(LLStockManagement llStockManagement) {
        try {
            this.requestParam();
            llStockManagement.setLeavePeriodeId(getLong(FRM_FIELD_LEAVE_PERIODE_ID));
            llStockManagement.setDtOwningDate(getDate(FRM_FIELD_OWNING_DATE));
            llStockManagement.setLLQty(getInt(FRM_FIELD_LL_QTY));
            llStockManagement.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            llStockManagement.setStNote(getString(FRM_FIELD_NOTE));
            llStockManagement.setQtyResidue(getInt(FRM_FIELD_QTY_RESIDUE));
            llStockManagement.setQtyUsed(getInt(FRM_FIELD_QTY_USED));
            llStockManagement.setLLStatus(getInt(FRM_FIELD_LL_STATUS));
            llStockManagement.setEntitled(getInt(FRM_FIELD_ENTITLED));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
