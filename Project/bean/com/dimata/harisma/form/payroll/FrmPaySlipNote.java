/* 
 * Form Name  	:  FrmPayPeriod.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya 
 * @version  	: 01 
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlipNote;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmPaySlipNote extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PaySlipNote paySlipNote;
    public static final String FRM_NAME_PAY_SLIP_NOTE = "FRM_NAME_PAY_SLIP_NOTE";
    public static final int FRM_PAY_SLIP_NOTE_ID = 0;
    public static final int FRM_PAY_SLIP_NOTE_DATE = 1;
    public static final int FRM_PAY_SLIP_NOTE_PERIODE = 2;
    public static final int FRM_PAY_SLIP_ID = 3;
    public static final int FRM_PAY_SLIP_EMPLOYEE_ID = 4;
    public static final int FRM_PAY_SLIP_NOTE = 5;
    public static String[] fieldNames = {
      "FRM_PAY_SLIP_NOTE_ID",
      "FRM_PAY_SLIP_NOTE_DATE",
      "FRM_PAY_SLIP_NOTE_PERIODE",
      "FRM_PAY_SLIP_ID",
      "FRM_PAY_SLIP_EMPLOYEE_ID",
      "FRM_PAY_SLIP_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE ,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED
    };

    public FrmPaySlipNote() {
    }

    public FrmPaySlipNote(PaySlipNote paySlipNote) {
        this.paySlipNote = paySlipNote;
    }

    public FrmPaySlipNote(HttpServletRequest request, PaySlipNote paySlipNote) {
        super(new FrmPaySlipNote(paySlipNote), request);
        this.paySlipNote = paySlipNote;
    }

    public String getFormName() {
        return FRM_NAME_PAY_SLIP_NOTE;
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

    public PaySlipNote getEntityObject() {
        return paySlipNote;
    }

    public void requestEntityObject(PaySlipNote objPaySlipNote) {
        try {
            this.requestParam();
            long dtAdj = this.getParamLong(fieldNames[FRM_PAY_SLIP_NOTE_DATE]);
            
            objPaySlipNote.setDtPaySlipNote(new Date(dtAdj));
            objPaySlipNote.setPeriodId(getLong(FRM_PAY_SLIP_NOTE_PERIODE));
            
            objPaySlipNote.setPaySlipId(getLong(FRM_PAY_SLIP_ID));
            objPaySlipNote.setEmployeeId(getLong(FRM_PAY_SLIP_EMPLOYEE_ID));
            objPaySlipNote.setNote(getString(FRM_PAY_SLIP_NOTE));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
