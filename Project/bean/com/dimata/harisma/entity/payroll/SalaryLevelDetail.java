/*
 * SalaryLevelDetail.java
 *
 * Created on March 31, 2007, 1:44 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  autami
 */

public class SalaryLevelDetail extends Entity{  
    
    public static final String DAY_OFF_SCHEDULE="DAY_OFF_SCHEDULE"; // day off scheduled for employee
    public static final String DAY_PERIOD="DAY_PERIOD"; // total day in a period including non working day
    public static final String WORK_DAY_PERIOD="WORK_DAY_PERIOD"; // working day in period set by company
    public static final String TOTAL_DAY_OFF_OVERTIME= "TOTAL_DAY_OFF_OVERTIME"; // total day when overtime occur on day off schedule 
    public static final String TOTAL_DAY_OVERTIME= "TOTAL_DAY_OVERTIME"; // total day when overtime occur on a day off or present schedule
    public static final String SCH_STS_RSN ="SCH_STS_RSN";// total status dan reason dari schedule emplouee, digunakan dgn : SCH_STS_RS_ABS_NAB  = status absent dan reasonnya lupa absent
    public static final String ABSENT_RSN= "ABSENT_RSN"; // total absen dgn nomor reason tertentu e.g. ABSENT_RSN_16
    public static final String DATE_PRESENT = "DATE_PRESENT";// total present date on pay slip
    public static final String DATE_ABSENT = "DATE_ABSENT";// total absent date on pay slip
    public static final String DATE_LATE = "DATE_LATE";// total Late date on pay slip
    public static final String DAYWORK_LESS_MNT = "DAYWORK_LESS_MNT";// jam kerja kurang dari ... menit
    public static final String  OVTIME_MEAL_ALLOWANCE = " OVTIME_MEAL_ALLOWANCE";// quantity of meals allowance entitled for employee : example  OVTIME_MEAL_ALLOWANCE * 10000  => e.g. 5 * 10000 
    // UPDATE BY HENDRA MCHEN 2014-11-21
    public static final String NIGHT_ALLOWANCE = "NIGHT_ALLOWANCE";
    public static final String TRANSPORT_ALLOWANCE = "TRANSPORT_ALLOWANCE";
    //update by satrya 2014-04-02
    private String compName="";
    
    /**
     * Holds value of property levelCode.
     */
    private String levelCode="";
    
    /**
     * Holds value of property compCode.
     */
    private String compCode="";
    
    /**
     * Holds value of property formula.
     */
    private String formula="";
    
    /**
     * Holds value of property sortIdx.
     */
    private int sortIdx;
    
    /**
     * Holds value of property payPeriod.
     */
    private int payPeriod;
    
    /**
     * Holds value of property takeHomePay.
     */
    private int takeHomePay;
    
    /**
     * Holds value of property copyData.
     */
    private int copyData;
    
    /**
     * Holds value of property levelCompId.
     */
    private long componentId;
    
   
    /** Creates a new instance of SalaryLevelDetail */
    public SalaryLevelDetail() {
    }
    
    /**
     * Getter for property levelCode.
     * @return Value of property levelCode.
     */
    public String getLevelCode() {
       
        return this.levelCode;
    }
    
    /**
     * Setter for property levelCode.
     * @param levelCode New value of property levelCode.
     */
    public void setLevelCode(String levelCode) {
         if (levelCode == null) {
	     levelCode = ""; 
	 } 
        this.levelCode = levelCode;
    }
    
    /**
     * Getter for property compCode.
     * @return Value of property compCode.
     */
    public String getCompCode() {
        return this.compCode;
    }
    
    /**
     * Setter for property compCode.
     * @param compCode New value of property compCode.
     */
    public void setCompCode(String compCode) {
         if (compCode == null) {
	     compCode = ""; 
	 } 
        this.compCode = compCode;
    }
    
    /**
     * Getter for property formula.
     * @return Value of property formula.
     */
    public String getFormula() {
        return this.formula;
    }
    
    /**
     * Setter for property formula.
     * @param formula New value of property formula.
     */
    public void setFormula(String formula) {
          if (formula == null) {
	     formula = ""; 
	 } 
        this.formula = formula;
    }
    
    /**
     * Getter for property sortIdx.
     * @return Value of property sortIdx.
     */
    public int getSortIdx() {
        return this.sortIdx;
    }
    
    /**
     * Setter for property sortIdx.
     * @param sortIdx New value of property sortIdx.
     */
    public void setSortIdx(int sortIdx) {
        this.sortIdx = sortIdx;
    }
    
    /**
     * Getter for property payPeriod.
     * @return Value of property payPeriod.
     */
    public int getPayPeriod() {
        return this.payPeriod;
    }
    
    /**
     * Setter for property payPeriod.
     * @param payPeriod New value of property payPeriod.
     */
    public void setPayPeriod(int payPeriod) {
        this.payPeriod = payPeriod;
    }
    
    /**
     * Getter for property takeHomePay.
     * @return Value of property takeHomePay.
     */
    public int getTakeHomePay() {
        return this.takeHomePay;
    }
    
    /**
     * Setter for property takeHomePay.
     * @param takeHomePay New value of property takeHomePay.
     */
    public void setTakeHomePay(int takeHomePay) {
        this.takeHomePay = takeHomePay;
    }
    
    /**
     * Getter for property copyData.
     * @return Value of property copyData.
     */
    public int getCopyData() {
        return this.copyData;
    }
    
    /**
     * Setter for property copyData.
     * @param copyData New value of property copyData.
     */
    public void setCopyData(int copyData) {
        this.copyData = copyData;
    }
    
    /**
     * Getter for property levelCompId.
     * @return Value of property levelCompId.
     */
    public long getComponentId() {
        return this.componentId;
    }    
    
    /**
     * Setter for property levelCompId.
     * @param levelCompId New value of property levelCompId.
     */
    public void setComponentId(long componentId) {
        this.componentId = componentId;
    }

    /**
     * @return the compName
     */
    public String getCompName() {
        return compName;
    }

    /**
     * @param compName the compName to set
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }
    
    
}
