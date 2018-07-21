/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.log;

import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public class DictionaryField {
    /*Employee Education*/
    public final static String EMP_EDUCATION_ID = "EMP_EDUCATION_ID" ;
    public final static String EDUCATION_ID = "EDUCATION_ID" ;
    public final static String EMPLOYEE_ID = "EMPLOYEE_ID" ;
    public final static String START_DATE = "START_DATE" ;
    public final static String END_DATE = "END_DATE" ;
    public final static String GRADUATION = "GRADUATION" ;
    public final static String EDUCATION_DESC = "EDUCATION_DESC" ;
    public final static String POINT = "POINT" ;
    public final static String INSTITUTION_ID = "INSTITUTION_ID" ;
    
    /*Company*/
    public final static String COMPANY_ID = "COMPANY_ID";
    public final static String DIVISION_ID = "DIVISION_ID";
    public final static String DEPARTMENT_ID = "DEPARTMENT_ID";
    public final static String SECTION_ID = "SECTION_ID";
    public final static String POSITION_ID = "POSITION_ID";
    public final static String LEVEL_ID = "LEVEL_ID";
    public final static String EMP_CATEGORY_ID = "EMP_CATEGORY_ID";
    
    /*Employee Competency*/
    public final static String EMP_COMP_ID = "EMP_COMP_ID";
    public final static String COMPETENCY_ID = "COMPETENCY_ID";
    public final static String LEVEL_VALUE = "LEVEL_VALUE";
    public final static String SPECIAL_ACHIEVEMENT = "SPECIAL_ACHIEVEMENT";
    public final static String DATE_OF_ACHVMT = "DATE_OF_ACHVMT";
    public final static String HISTORY = "HISTORY";
    public final static String PROVIDER_ID = "PROVIDER_ID";
    
    /*Employee Reprimand*/
    public final static String REPRIMAND_ID = "REPRIMAND_ID";
    public final static String NUMBER = "NUMBER";
    public final static String CHAPTER = "CHAPTER";
    public final static String ARTICLE = "ARTICLE";
    public final static String PAGE = "PAGE";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String REP_DATE = "REPRIMAND_DATE";
    public final static String VALIDITY = "VALID_UNTIL";
    public final static String REPRIMAND_LEVEL_ID = "REPRIMAND_LEVEL_ID";
    public final static String VERSE = "VERSE";
    
    /*Employee Warning*/
    public final static String WARNING_ID = "WARNING_ID";
    public final static String BREAK_FACT = "BREAK_FACT";
    public final static String BREAK_DATE = "BREAK_DATE";
    public final static String WARN_BY = "WARN_BY";
    public final static String WARN_DATE = "WARN_DATE";
    public final static String WARN_LEVEL_ID = "WARN_LEVEL_ID";
    
    /*Employee Training*/
    public final static String TRAINING_HISTORY_ID = "TRAINING_HISTORY_ID";
    public final static String TRAINING_PROGRAM = "TRAINING_PROGRAM";
    public final static String TRAINER = "TRAINER";
    public final static String REMARK = "REMARK";
    public final static String TRAINING_ID = "TRAINING_ID";
    public final static String DURATION = "DURATION";
    public final static String PRESENCE = "PRESENCE";
    public final static String START_TIME = "START_TIME";
    public final static String END_TIME = "END_TIME";
    public final static String TRAINING_ACTIVITY_PLAN_ID = "TRAINING_ACTIVITY_PLAN_ID";
    public final static String TRAINING_ACTIVITY_ACTUAL_ID = "TRAINING_ACTIVITY_ACTUAL_ID";
    public final static String NOMOR_SK = "NOMOR_SK";
    public final static String TANGGAL_SK = "TANGGAL_SK";
    public final static String EMP_DOC_ID  = "EMP_DOC_ID";
    public final static String TRAINING_TITLE = "TRAINING_TITLE";
    
    static Hashtable <String,String> dictionary = null;
    
    public String getWord(String key) {
        return dictionary.get(key);
    }

    public void loadWord() {
        if (dictionary == null ){
            dictionary =  new Hashtable<String, String>();
            
        }
        /*Employee Education*/
        dictionary.put(EMP_EDUCATION_ID,"Emp Edu ID");
        dictionary.put(EDUCATION_ID,"Pendidikan");
        dictionary.put(EMPLOYEE_ID,"Karyawan");
        dictionary.put(START_DATE,"Tgl Mulai");
        dictionary.put(END_DATE,"Tgl Berakhir");
        dictionary.put(GRADUATION,"Angkatan");
        dictionary.put(EDUCATION_DESC,"Keterangan Pendidikan");
        dictionary.put(POINT,"Poin");
        dictionary.put(INSTITUTION_ID,"Institusi");
        /*Company*/
        dictionary.put(COMPANY_ID, "Company");
        dictionary.put(DIVISION_ID, "Division");
        dictionary.put(DEPARTMENT_ID, "Department");
        dictionary.put(SECTION_ID, "Section");
        dictionary.put(POSITION_ID, "Position");
        dictionary.put(LEVEL_ID, "Level");
        dictionary.put(EMP_CATEGORY_ID, "Employee Category");
        /*Employee Competency*/
        dictionary.put(EMP_COMP_ID, "Emp Comp ID");
        dictionary.put(COMPETENCY_ID, "Kompetensi");
        dictionary.put(LEVEL_VALUE,"Level Value");
        dictionary.put(SPECIAL_ACHIEVEMENT, "Special Achievement");
        dictionary.put(DATE_OF_ACHVMT, "Date of Achievement");
        dictionary.put(HISTORY, "History");
        dictionary.put(PROVIDER_ID, "Provider");
        /*Employee Reprimand*/
        dictionary.put(REPRIMAND_ID,"Reprimand ID");
        dictionary.put(NUMBER, "Number");
        dictionary.put(CHAPTER,"Chapter/Bab");
        dictionary.put(ARTICLE,"Article/Pasal");
        dictionary.put(PAGE,"Page/Halaman");
        dictionary.put(DESCRIPTION, "Deskripsi/Uraian");
        dictionary.put(REP_DATE, "Date/Tanggal");
        dictionary.put(VALIDITY, "Valid Until");
        dictionary.put(REPRIMAND_LEVEL_ID, "Reprimand Level (Point)");
        dictionary.put(VERSE ,"Verse/Ayat");
        /*Employee Warning*/
        dictionary.put(WARNING_ID,"Warning ID");
        dictionary.put(BREAK_FACT, "Break Fact");
        dictionary.put(BREAK_DATE, "Break Date");
        dictionary.put(WARN_BY, "Warning By");
        dictionary.put(WARN_DATE, "Warning Date");
        dictionary.put(WARN_LEVEL_ID, "Warning Level (Point)");
        /*Employee Training*/
        dictionary.put(TRAINING_HISTORY_ID, "Training ID");
        dictionary.put(TRAINING_PROGRAM, "Training Program");
        dictionary.put(TRAINER, "Trainer");
        dictionary.put(REMARK,"Remark");
        dictionary.put(TRAINING_ID, "Training Program ID");
        dictionary.put(DURATION, "Duration");
        dictionary.put(PRESENCE, "Presence");
        dictionary.put(START_TIME, "Start Time");
        dictionary.put(END_TIME, "End Time");
        dictionary.put(TRAINING_ACTIVITY_PLAN_ID, "Training Activity Plan ID");
        dictionary.put(TRAINING_ACTIVITY_ACTUAL_ID, "Training Activity Actual ID");
        dictionary.put(NOMOR_SK, "Nomor SK");
        dictionary.put(TANGGAL_SK, "Tanggal SK");
        dictionary.put(EMP_DOC_ID,  "ID Dokumen");
        dictionary.put(TRAINING_TITLE, "Training Title");
        
    }

}
