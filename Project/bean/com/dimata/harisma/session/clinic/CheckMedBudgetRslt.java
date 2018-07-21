/*
 * copyrights : PT. Dimata Sora Jayate
 * Denpasar Bali Indonesia - Earth
 * 
 */

package com.dimata.harisma.session.clinic;

/**
 *
 * @author Ketut Kartika T
 */

import com.dimata.harisma.entity.clinic.*;
public class CheckMedBudgetRslt {
    public static final int RESULT_OK=0;
    public static final int RESULT_CASE_OVER_QTY=1;
    public static final int RESULT_OVER_BUDGET=2;
    public static final int RESULT_OVER_QTY_N_BUDGET=3;
    public static final int RESULT_PARAMETER_NOT_COMPLETE=4;
    
    public static final String[] resultStr={"OK", "Case over quantity","Total Amount Over Budget","Parameter Not Complete"};
    
    private MedicalCase medCase=new MedicalCase();
    private MedicalLevel medLevel = new MedicalLevel();
    private MedicalBudget medBudget = new MedicalBudget();
    private String message="";
    private int errCode=0;

    public MedicalCase getMedCase() {
        return medCase;
    }

    public void setMedCase(MedicalCase medCase) {
        this.medCase = medCase;
    }

    public MedicalLevel getMedLevel() {
        return medLevel;
    }

    public void setMedLevel(MedicalLevel medLevel) {
        this.medLevel = medLevel;
    }

    public MedicalBudget getMedBudget() {
        return medBudget;
    }

    public void setMedBudget(MedicalBudget medBudget) {
        this.medBudget = medBudget;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
