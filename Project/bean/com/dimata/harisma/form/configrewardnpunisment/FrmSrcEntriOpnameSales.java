/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.SrcEntriOpnameSales;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmSrcEntriOpnameSales extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private SrcEntriOpnameSales srcEntriOpnameSales;
    public static final String FRM_ENTRI_OPNAME_SALES = "FRM_ENTRI_OPNAME_SALES";
    public static final int FRM_FLD_ENTRI_OPNAME_SALES_ID = 0;
    public static final int FRM_FLD_JENIS_SO_ID = 1;
    public static final int FRM_FLD_LOCATION_ID = 2;
    public static final int FRM_FLD_TYPE_OF_TOLERANCE = 3;
    public static final int FRM_FLD_NET_SALES_PERIOD = 4;
    public static final int FRM_FLD_PROSENTASE_TOLERANCE = 5;
    public static final int FRM_FLD_BARANG_HILANG = 6;
    public static final int FRM_FLD_CREATE_FORM_LOCATION_OPNAME = 7;
    public static final int FRM_FLD_PLUS_MINUS = 8;
    public static final int FRM_FLD_STATUS_OPNAME = 9;
    public static final int FRM_FLD_DATE_START_PERIOD = 10;
    public static final int FRM_FLD_DATE_END_PERIOD = 11;
    
    public static String[] fieldNames = {
        "FRM_FLD_ENTRI_OPNAME_SALES_ID",
        "FRM_FLD_JENIS_SO_ID",
        "FRM_FLD_LOCATION_ID",
        "FRM_FLD_TYPE_OF_TOLERANCE",
        "FRM_FLD_NET_SALES_PERIOD",
        "FRM_FLD_PROSENTASE_TOLERANCE",
        "FRM_FLD_BARANG_HILANG",
        "FRM_FLD_CREATE_FORM_LOCATION_OPNAME",
        "FRM_FLD_PLUS_MINUS",
        "FRM_FLD_STATUS_OPNAME",
        "FRM_FLD_DATE_START_PERIOD",
        "FRM_FLD_DATE_END_PERIOD"
    };
    
    public static int[] fieldTypes = {
        TYPE_LONG,//"FRM_FLD_ENTRI_OPNAME_SALES_ID",
        TYPE_LONG + ENTRY_REQUIRED,//"FRM_FLD_JENIS_SO_ID",
        TYPE_LONG + ENTRY_REQUIRED,//"FRM_FLD_LOCATION_ID",
        TYPE_INT,//"FRM_FLD_TYPE_OF_TOLERANCE",
        TYPE_FLOAT,//"FRM_FLD_NET_SALES_PERIOD",
        TYPE_FLOAT,//"FRM_FLD_PROSENTASE_TOLERANCE",
        TYPE_FLOAT,//"FRM_FLD_BARANG_HILANG",
        TYPE_STRING + ENTRY_REQUIRED,//"FRM_FLD_CREATE_FORM_LOCATION_OPNAME",
        TYPE_FLOAT,//"FRM_FLD_PLUS_MINUS",
        TYPE_STRING,//"FRM_FLD_STATUS_OPNAME"
        TYPE_DATE + ENTRY_REQUIRED, //"FRM_FLD_DATE_START_PERIOD",
        TYPE_DATE + ENTRY_REQUIRED,//"FRM_FLD_DATE_END_PERIOD"
    };

    public FrmSrcEntriOpnameSales() {
    }

    public FrmSrcEntriOpnameSales(SrcEntriOpnameSales srcEntriOpnameSales) {
        this.srcEntriOpnameSales = srcEntriOpnameSales;
    }

    public FrmSrcEntriOpnameSales(HttpServletRequest request, SrcEntriOpnameSales srcEntriOpnameSales) {
        super(new FrmSrcEntriOpnameSales(srcEntriOpnameSales), request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.srcEntriOpnameSales = srcEntriOpnameSales;
    }

    public String getFormName() {
        return FRM_ENTRI_OPNAME_SALES;
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

    public SrcEntriOpnameSales getEntityObject() {
        return srcEntriOpnameSales;
    }

    public void requestEntityObject(SrcEntriOpnameSales srcEntriOpnameSales) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            srcEntriOpnameSales.setLocationId(getLong(FRM_FLD_LOCATION_ID));
            srcEntriOpnameSales.setJenisSoId(getLong(FRM_FLD_JENIS_SO_ID));
            srcEntriOpnameSales.setTypeTolerance(getInt(FRM_FLD_TYPE_OF_TOLERANCE));
            srcEntriOpnameSales.setNetSalesPeriod(getDouble(FRM_FLD_NET_SALES_PERIOD));
            srcEntriOpnameSales.setProsentaseTolerance(getDouble(FRM_FLD_PROSENTASE_TOLERANCE));
            srcEntriOpnameSales.setBarangHilang(getDouble(FRM_FLD_BARANG_HILANG));
            srcEntriOpnameSales.setPlusMinus(getDouble(FRM_FLD_PLUS_MINUS));
            srcEntriOpnameSales.setCreateLocationName(getString(FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
            srcEntriOpnameSales.setDtFromPeriod(getDate(FRM_FLD_DATE_START_PERIOD));
            srcEntriOpnameSales.setDtToPeriod(getDate(FRM_FLD_DATE_END_PERIOD));
           
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
