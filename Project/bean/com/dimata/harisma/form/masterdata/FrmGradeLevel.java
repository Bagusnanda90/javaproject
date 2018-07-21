/* 
 * Form Name  	:  FrmLevel.java 
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
package com.dimata.harisma.form.masterdata;

/* java package */
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmGradeLevel extends FRMHandler implements I_FRMInterface, I_FRMType {

    private GradeLevel gradeLevel;
    public static final String FRM_NAME_LEVEL = "FRM_NAME_GRADE_LEVEL";
    public static final int FRM_FIELD_GRADE_LEVEL_ID = 0;
    public static final int FRM_FIELD_GRADE_CODE = 1;
    public static String[] fieldNames = {
        "FRM_FIELD_GRADE_LEVEL_ID",
        "FRM_FIELD_GRADE_CODE",};
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
    };

    public FrmGradeLevel() {
    }

    public FrmGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public FrmGradeLevel(HttpServletRequest request, GradeLevel gradeLevel) {
        super(new FrmGradeLevel(gradeLevel), request);
        this.gradeLevel = gradeLevel;
    }

    public String getFormName() {
        return FRM_NAME_LEVEL;
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

    public GradeLevel getEntityObject() {
        return gradeLevel;
    }

    public void requestEntityObject(GradeLevel gradeLevel) {
        try {
            this.requestParam();

            gradeLevel.setCodeLevel(getString(FRM_FIELD_GRADE_CODE));
           
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
