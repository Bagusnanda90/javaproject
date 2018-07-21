/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.report.lkpbu;

import com.dimata.harisma.entity.report.lkpbu.Lkpbu805;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmLkpbu805 extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Lkpbu805 entLkpbu805;
    public static final String FRM_NAME_LKPBU_805 = "FRM_NAME_LKPBU_805";
    public static final int FRM_FIELD_LKPBU_805_ID = 0;
    public static final int FRM_FIELD_LEVEL_ID = 1;
    public static final int FRM_FIELD_EDUCATION_ID = 2;
    public static final int FRM_FIELD_EMP_CATEGORY_ID = 3;
    public static final int FRM_FIELD_LKPBU_805_YEAR_REALISASI = 4;
    public static final int FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_1 = 5;
    public static final int FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_2 = 6;
    public static final int FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_3 = 7;
    public static final int FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_4 = 8;
    public static final int FRM_FIELD_LKPBU_805_START_DATE = 9;
    public static String[] fieldNames = {
        "FRM_FIELD_LKPBU_805_ID",
        "FRM_FIELD_LEVEL_ID",
        "FRM_FIELD_EDUCATION_ID",
        "FRM_FIELD_EMP_CATEGORY_ID",
        "FRM_FIELD_LKPBU_805_YEAR_REALISASI",
        "FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_1",
        "FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_2",
        "FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_3",
        "FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_4",
        "FRM_FIELD_LKPBU_805_START_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_DATE
    };

    public FrmLkpbu805() {
    }

    public FrmLkpbu805(Lkpbu805 entLkpbu805) {
        this.entLkpbu805 = entLkpbu805;
    }

    public FrmLkpbu805(HttpServletRequest request, Lkpbu805 entLkpbu805) {
        super(new FrmLkpbu805(entLkpbu805), request);
        this.entLkpbu805 = entLkpbu805;
    }

    public String getFormName() {
        return FRM_NAME_LKPBU_805;
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

    public Lkpbu805 getEntityObject() {
        return entLkpbu805;
    }

    public void requestEntityObject(Lkpbu805 entLkpbu805) {
        try {
            this.requestParam();
            entLkpbu805.setLevelId(getLong(FRM_FIELD_LEVEL_ID));
            entLkpbu805.setEducationId(getLong(FRM_FIELD_EDUCATION_ID));
            entLkpbu805.setEmpCategoryId(getLong(FRM_FIELD_EMP_CATEGORY_ID));
            entLkpbu805.setLkpbu805YearRealisasi(getFloat(FRM_FIELD_LKPBU_805_YEAR_REALISASI));
            entLkpbu805.setLkpbu805YearPrediksi1(getFloat(FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_1));
            entLkpbu805.setLkpbu805YearPrediksi2(getFloat(FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_2));
            entLkpbu805.setLkpbu805YearPrediksi3(getFloat(FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_3));
            entLkpbu805.setLkpbu805YearPrediksi4(getFloat(FRM_FIELD_LKPBU_805_YEAR_PREDIKSI_4));
           // entLkpbu805.setLkpbu805StartDate(getDate(FRM_FIELD_LKPBU_805_START_DATE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}