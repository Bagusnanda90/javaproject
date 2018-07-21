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

public class FrmAlStockManagement extends FRMHandler implements I_FRMInterface, I_FRMType {
    private AlStockManagement alStockManagement;

    public static final String FRM_AL_STOCK_MANAGEMENT = "FRM_AL_STOCK_MANAGEMENT";

    public static final int FRM_FIELD_LEAVE_PERIODE_ID = 0;
    public static final int FRM_FIELD_AL_QTY = 1;
    public static final int FRM_FIELD_OWNING_DATE = 2;
    public static final int FRM_FIELD_AL_STATUS = 3;
    public static final int FRM_FIELD_NOTE = 4;
    public static final int FRM_FIELD_QTY_RESIDUE = 5;
    public static final int FRM_FIELD_QTY_USED = 6;
    public static final int FRM_FIELD_EMPLOYEE_ID = 7;
    public static final int FRM_FIELD_ENTITLED = 8;
    public static final int FRM_FIELD_EXPIRED_DATE = 8;
    
    public static String[] fieldNames = {
        "FRM_FIELD_LEAVE_PERIODE_ID", 
        "FRM_FIELD_AL_QTY",
        "FRM_FIELD_OWNING_DATE",
        "FRM_FIELD_AL_STATUS",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_QTY_RESIDUE",
        "FRM_FIELD_QTY_USED",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_ENTITLED",
        "FRM_FIELD_EXPIRED_DATE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_FLOAT,   
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_FLOAT,
        TYPE_DATE
    };

    public FrmAlStockManagement() {
    }

    public FrmAlStockManagement(AlStockManagement alStockManagement) {
        this.alStockManagement = alStockManagement;
    }

    public FrmAlStockManagement(HttpServletRequest request, AlStockManagement alStockManagement) {
        super(new FrmAlStockManagement(alStockManagement), request);
        this.alStockManagement = alStockManagement;
    }

    public String getFormName() {
        return FRM_AL_STOCK_MANAGEMENT;
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

    public AlStockManagement getEntityObject() {
        return alStockManagement;
    }

    public void requestEntityObject(AlStockManagement alStockManagement) {
        try {
            this.requestParam();
            alStockManagement.setLeavePeriodeId(getLong(FRM_FIELD_LEAVE_PERIODE_ID));
            alStockManagement.setDtOwningDate(getDate(FRM_FIELD_OWNING_DATE));
            alStockManagement.setAlQty(getFloat(FRM_FIELD_AL_QTY));
            alStockManagement.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            alStockManagement.setStNote(getString(FRM_FIELD_NOTE));
            alStockManagement.setQtyResidue(getFloat(FRM_FIELD_QTY_RESIDUE));
            alStockManagement.setQtyUsed(getFloat(FRM_FIELD_QTY_USED));
            alStockManagement.setAlStatus(getInt(FRM_FIELD_AL_STATUS));
            alStockManagement.setEntitled(getFloat(FRM_FIELD_ENTITLED));
            alStockManagement.setExpiredDate(getDate(FRM_FIELD_EXPIRED_DATE));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
