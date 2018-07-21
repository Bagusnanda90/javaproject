/* 
 * Form Name  	:  FrmCareerPath.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
import com.dimata.harisma.entity.employee.*;
import com.dimata.qdep.form.*;
import java.sql.Date;
import javax.servlet.http.*;

public class FrmCareerPath extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CareerPath careerPath;
    public static final String FRM_NAME_CAREERPATH = "FRM_NAME_CAREERPATH";
    public static final int FRM_FIELD_WORK_HISTORY_NOW_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_COMPANY_ID = 2;
    public static final int FRM_FIELD_DEPARTMENT_ID = 3;
    public static final int FRM_FIELD_POSITION_ID = 4;
    public static final int FRM_FIELD_SECTION_ID = 5;
    public static final int FRM_FIELD_DIVISION_ID = 6;
    public static final int FRM_FIELD_LEVEL_ID = 7;
    public static final int FRM_FIELD_WORK_FROM = 8;
    public static final int FRM_FIELD_WORK_TO = 9;
    public static final int FRM_FIELD_DESCRIPTION = 10;
    public static final int FRM_FIELD_SALARY = 11;
    public static final int FRM_FIELD_EMP_CATEGORY_ID = 12;
    //add priska 2014-11-02
    public static final int FRM_FIELD_LOCATION_ID = 13;
    public static final int FRM_FIELD_NOTE = 14;
    public static final int FRM_FIELD_PROVIDER_ID = 15; // kartika 2019-09-16
    /* Update by Hendra Putu - 20151009 */
    public static final int FRM_FIELD_HISTORY_TYPE = 16;
    public static final int FRM_FIELD_NOMOR_SK = 17;
    public static final int FRM_FIELD_TANGGAL_SK = 18;
    public static final int FRM_FIELD_EMP_DOC_ID = 19;
    public static final int FRM_FIELD_HISTORY_GROUP = 20;
    public static final int FRM_FIELD_GRADE_LEVEL_ID = 21;
    public static final int FRM_FIELD_CONTRACT_FROM = 22;
    public static final int FRM_FIELD_CONTRACT_TO = 23;
    public static final int FRM_FIELD_CONTRACT_TYPE = 24;
    /* alternative date */
    public static final int FRM_FIELD_WORK_FROM_STR = 25;
    public static final int FRM_FIELD_WORK_TO_STR = 26;
    /* add by hendra | 2016-10-24 */
    public static final int FRM_FIELD_MUTATION_TYPE = 27;
    public static final int FRM_FIELD_DOCUMENT = 28;
    public static final int FRM_FIELD_ACKNOWLEDGE_STATUS = 29;
    
    public static String[] fieldNames = {
        "FRM_FIELD_WORK_HISTORY_NOW_ID", 
        "FRM_FIELD_EMPLOYEE_ID", 
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DEPARTMENT_ID", 
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_SECTION_ID", 
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_LEVEL_ID", 
        "FRM_FIELD_WORK_FROM",
        "FRM_FIELD_WORK_TO", 
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_SALARY", 
        "FRM_FIELD_EMP_CATEGORY_ID",
        //priska 2014-11-03
        "FRM_FIELD_LOCATION_ID", 
        "FRM_FIELD_NOTE",
        "FRM_FIELD_PROVIDER_ID",
        "FRM_FIELD_HISTORY_TYPE",
        "FRM_FIELD_NOMOR_SK",
        "FRM_FIELD_TANGGAL_SK",
        "FRM_FIELD_EMP_DOC_ID",
        "FRM_FIELD_HISTORY_GROUP",
        "FRM_FIELD_GRADE_LEVEL_ID",
        "FRM_FIELD_CONTRACT_FROM",
        "FRM_FIELD_CONTRACT_TO",
        "FRM_FIELD_CONTRACT_TYPE",
        "FRM_FIELD_WORK_FROM_STR",
        "FRM_FIELD_WORK_TO_STR",
        "FRM_FIELD_MUTATION_TYPE",
        "FRM_FIELD_DOCUMENT",
        "FRM_FIELD_ACKNOWLEDGE_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED, 
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG, 
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG, 
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED, 
        TYPE_STRING,
        TYPE_FLOAT, 
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_STRING, 
        TYPE_LONG,
        TYPE_INT, 
        TYPE_STRING, 
        TYPE_STRING, 
        TYPE_LONG, 
        TYPE_INT, 
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING, 
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT
    /*update by devin 2014-02-17 TYPE_LONG,  TYPE_LONG,  TYPE_LONG,
     TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
     TYPE_LONG,  TYPE_LONG,
     TYPE_LONG,  TYPE_DATE + ENTRY_REQUIRED,
     TYPE_DATE + ENTRY_REQUIRED,  TYPE_STRING, 
     TYPE_FLOAT, TYPE_LONG*/
    };

    public FrmCareerPath() {
    }

    public FrmCareerPath(CareerPath careerPath) {
        this.careerPath = careerPath;
    }

    public FrmCareerPath(HttpServletRequest request, CareerPath careerPath) {
        super(new FrmCareerPath(careerPath), request);
        this.careerPath = careerPath;
    }

    public String getFormName() {
        return FRM_NAME_CAREERPATH;
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

    public CareerPath getEntityObject() {
        return careerPath;
    }

    public void requestEntityObject(CareerPath careerPath) {
        try {
            this.requestParam();
            careerPath.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            careerPath.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            careerPath.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            careerPath.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            careerPath.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            careerPath.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            careerPath.setLevelId(getLong(FRM_FIELD_LEVEL_ID));
            
            careerPath.setDescription(getString(FRM_FIELD_DESCRIPTION));
            careerPath.setSalary(getFloat(FRM_FIELD_SALARY));
            careerPath.setEmpCategoryId(getLong(FRM_FIELD_EMP_CATEGORY_ID));
            careerPath.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
            careerPath.setProviderID(getLong(FRM_FIELD_PROVIDER_ID));
            careerPath.setNote(getString(FRM_FIELD_NOTE));
            careerPath.setHistoryType(getInt(FRM_FIELD_HISTORY_TYPE));
            careerPath.setNomorSk(getString(FRM_FIELD_NOMOR_SK));
            try{
                if(getString(FRM_FIELD_WORK_FROM_STR).length()>0){
                    careerPath.setWorkFrom(Date.valueOf(getString(FRM_FIELD_WORK_FROM_STR)));
                } else {
                    careerPath.setWorkFrom(getDate(FRM_FIELD_WORK_FROM));
                }
                if (getString(FRM_FIELD_WORK_TO_STR).length()>0){
                    careerPath.setWorkTo(Date.valueOf(getString(FRM_FIELD_WORK_TO_STR)));
                } else {
                    careerPath.setWorkTo(getDate(FRM_FIELD_WORK_TO));
                }
                if (getString(FRM_FIELD_TANGGAL_SK).length()>0){
            careerPath.setTanggalSk(Date.valueOf(getString(FRM_FIELD_TANGGAL_SK)));
                }
                if (getString(FRM_FIELD_CONTRACT_FROM).length()>0){
                    careerPath.setContractFrom(Date.valueOf(getString(FRM_FIELD_CONTRACT_FROM)));
                }
                if (getString(FRM_FIELD_CONTRACT_TO).length()>0){
                    careerPath.setContractTo(Date.valueOf(getString(FRM_FIELD_CONTRACT_TO)));
                }                
            } catch (Exception e){
                System.out.println("=>"+e.toString());
            }
            careerPath.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            careerPath.setHistoryGroup(getInt(FRM_FIELD_HISTORY_GROUP));
            careerPath.setGradeLevelId(getLong(FRM_FIELD_GRADE_LEVEL_ID));
            careerPath.setContractType(getInt(FRM_FIELD_CONTRACT_TYPE));
            careerPath.setMutationType(getInt(FRM_FIELD_MUTATION_TYPE));
            careerPath.setDocument(getString(FRM_FIELD_DOCUMENT));
            careerPath.setAcknowledgeStatus(getInt(FRM_FIELD_ACKNOWLEDGE_STATUS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
