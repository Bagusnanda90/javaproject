/*
 * FrmSrcPosition.java
 *
 * Created on January 5, 2005, 1:42 PM
 */
package com.dimata.harisma.form.search;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Date;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.search.*;

/**
 *
 * @author gedhy
 */
public class FrmSrcPosition extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SrcPosition srcPosition;
    public static final String FRM_NAME_SRC_POSITION = "FRM_NAME_SRC_POSITION";
    public static final int FRM_FIELD_LEVEL = 0;
    public static final int FRM_FIELD_NAME = 1;
    public static final int FRM_FIELD_START_DATE = 2;
    public static final int FRM_FIELD_END_DATE = 3;
    public static final int FRM_FIELD_RADIO_BTN = 4;
    public static final int FRM_FIELD_LEVEL_RANK_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_LEVEL",
        "FRM_FIELD_NAME",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_RADIO_BTN",
        "FRM_FIELD_LEVEL_RANK_ID"
    };
    public static int[] fieldTypes = {
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmSrcPosition() {
    }

    public FrmSrcPosition(SrcPosition srcPosition) {
        this.srcPosition = srcPosition;
    }

    public FrmSrcPosition(HttpServletRequest request, SrcPosition srcPosition) {
        super(new FrmSrcPosition(srcPosition), request);
        this.srcPosition = srcPosition;
    }

    public String getFormName() {
        return FRM_NAME_SRC_POSITION;
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

    public SrcPosition getEntityObject() {
        return srcPosition;
    }

    public void requestEntityObject(SrcPosition srcPosition) {
        try {
            this.requestParam();
            srcPosition.setPosLevel(getInt(FRM_FIELD_LEVEL));
            srcPosition.setPosName(getString(FRM_FIELD_NAME));
            srcPosition.setStartDate(getString(FRM_FIELD_START_DATE));
            srcPosition.setEndDate(getString(FRM_FIELD_END_DATE));
            srcPosition.setRadioButton(getInt(FRM_FIELD_RADIO_BTN));
            srcPosition.setLevelRankID(getLong(FRM_FIELD_LEVEL_RANK_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
