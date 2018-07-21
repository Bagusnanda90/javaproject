/*
 * PayEmpLevel.java
 *
 * Created on April 5, 2007, 10:48 AM
 */

package com.dimata.harisma.entity.clinic;


import java.util.Vector;
import com.dimata.qdep.entity.*;


/**
 *
 * @author  Kartika ,  21 feb 2009
 */
public class MedicalBudget extends Entity{
	private long medicalLevelId=0;
	private long medicalCaseId=0;
        private double budget=0.0;
        private int usePeriod=0;
        private int usePax=0;
        
    public static final int PERIOD_NONE =0;
    public static final int PERIOD_DAY =1;
    public static final int PERIOD_MONTH =2;
    public static final int PERIOD_YEAR =3;
    public static final int PERIOD_IN_TOTAL =4;
    public static String[] PeriodTitle = {"-", "Day", "Month", "Year","In Total"};
    
    public static int USE_PAX_NONE = 0;
    public static int USE_PAX_PER_PAX=1;
    public static String[] PaxTitle = {"-", "Pax"};
    
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

    public static Vector getPaxTitle(){
        Vector title= new Vector();
        
        for(int i= 0;i<PaxTitle.length;i++){
            title.add(PaxTitle[i]);
        }        
        return title;
    }
    
    public static Vector getPaxIndexString(){
        Vector index= new Vector();
        
        for(int i= 0;i<PaxTitle.length;i++){
            index.add(new String(""+i));
        }        
        return index;
    }
    
    
    
    public long getMedicalLevelId() {
        return medicalLevelId;
    }

    public void setMedicalLevelId(long medicalLevelId) {
        this.medicalLevelId = medicalLevelId;
    }

    public long getMedicalCaseId() {
        return medicalCaseId;
    }

    public void setMedicalCaseId(long medicalCaseId) {
        this.medicalCaseId = medicalCaseId;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getUsePeriod() {
        return usePeriod;
    }

    public void setUsePeriod(int usePeriod) {
        this.usePeriod = usePeriod;
    }

    public int getUsePax() {
        return usePax;
    }

    public void setUsePax(int usePax) {
        this.usePax = usePax;
    }
    

    
}
