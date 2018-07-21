
package com.dimata.harisma.entity.clinic;

import com.dimata.qdep.entity.Entity;

import java.util.Vector;
/**
 *
 * @author bayu
 */

public class MedicalCase extends Entity {

    private int sortNumber=0;
    private String caseGroup = "";
    private String caseName = "";
    private int maxUse=0;
    private int maxUsePeriod=0;
    private int minTakenBy=0;
    private int minTakenByPeriod=0;
    private String caseLink = "";
    private String formula = "";    

    public static final int QTY_UNIT_NO_LIMIT =0;
    public static final int QTY_UNIT_DAY =1;
    public static final int QTY_UNIT_TIMES =2;
    public static final int QTY_UNIT_IN_LIVE =3;
    public static String[]   qtyUnitTitle = {"No Limit", "Day(s)", "Time(s)"};
    
    
    public static final int PERIOD_FOLLOW_HR_PERIOD =0;
    public static final int PERIOD_DAY =1;
    public static final int PERIOD_MONTH =2;
    public static final int PERIOD_YEAR =3;
    public static final int PERIOD_TIMES =4;
    public static String[] PeriodTitle = {"Follow HR Period", "Day(s)", "Month(s)", "Year(s)","Time(s)"};
    
       
    public static Vector getPeriodTitle(){
        Vector title= new Vector();
        
        for(int i= 0;i<PeriodTitle.length;i++){
            title.add(PeriodTitle[i]);
        }        
        return title;
    }   
    public static Vector getPeriodIndexString(){
        Vector index= new Vector();
        
        for(int i= 0;i<PeriodTitle.length;i++){
            index.add(new String(""+i));
        }        
        return index;
    }
    
       
    public static Vector getQtyUnitTitle(){
        Vector title= new Vector();
        
        for(int i= 0;i<qtyUnitTitle.length;i++){
            title.add(qtyUnitTitle[i]);
        }        
        return title;
    }   
    public static Vector getQtyUnitIndexString(){
        Vector index= new Vector();
        
        for(int i= 0;i<qtyUnitTitle.length;i++){
            index.add(new String(""+i));
        }        
        return index;
    }
    
    


    public int getSortNumber(){
        return this.sortNumber;
    }
    
    public void setSortNumber(int sortNumber){
        this.sortNumber=sortNumber;
    }
    
    public String getCaseGroup() {
        return caseGroup;
    }

    public void setCaseGroup(String caseGroup) {
        this.caseGroup = caseGroup;
    }
    
        
    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(int maxUse) {
        this.maxUse = maxUse;
    }

    public int getMaxUsePeriod() {
        return maxUsePeriod;
    }

    public void setMaxUsePeriod(int maxUsePeriod) {
        this.maxUsePeriod = maxUsePeriod;
    }

    public int getMinTakenBy() {
        return minTakenBy;
    }

    /**
     * 
     * @param minTakenBy
     */
    public void setMinTakenBy(int minTakenBy) {
        this.minTakenBy = minTakenBy;
    }
    
    /**
     * 
     * @return  integer :  PERIOD_FOLLOW_HR_PERIOD minimum taken by no limit means in one period of medical record
     */
    public int getMinTakenByPeriod() {
        return minTakenByPeriod;
    }

    public void setMinTakenByPeriod(int minTakenByPeriod) {
        this.minTakenByPeriod = minTakenByPeriod;
    }

    public String getCaseLink() {
        return caseLink;
    }

    public void setCaseLink(String caseLink) {
        this.caseLink = caseLink;
        if(this.caseLink==null)
            this.caseLink="";
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
    
    
}
