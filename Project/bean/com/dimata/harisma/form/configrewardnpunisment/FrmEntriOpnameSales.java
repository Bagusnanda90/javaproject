/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.EntriOpnameSales;
import com.dimata.harisma.entity.configrewardnpunisment.PstEntriOpnameSales;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmEntriOpnameSales extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private EntriOpnameSales entriOpnameSales;
    private Vector vlistEntriOpname = new Vector();
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
        TYPE_DATE + ENTRY_REQUIRED,//"FRM_FLD_DATE_START_PERIOD",
        TYPE_DATE + ENTRY_REQUIRED,//"FRM_FLD_DATE_END_PERIOD"
    };

    public FrmEntriOpnameSales() {
    }

    public FrmEntriOpnameSales(EntriOpnameSales entriOpnameSales) {
        this.entriOpnameSales = entriOpnameSales;
    }

    public FrmEntriOpnameSales(HttpServletRequest request, EntriOpnameSales entriOpnameSales) {
        super(new FrmEntriOpnameSales(entriOpnameSales), request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.entriOpnameSales = entriOpnameSales;
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

    public EntriOpnameSales getEntityObject() {
        return entriOpnameSales;
    }

    public void requestEntityObject(long oidEntriOpname, EntriOpnameSales entriOpnameSales, Vector listEntriOpnameSales) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParamMultipleSelected(listEntriOpnameSales,"userSelect");
            entriOpnameSales.setLocationId(getLong(FRM_FLD_LOCATION_ID));
            entriOpnameSales.setJenisSoId(getLong(FRM_FLD_JENIS_SO_ID));
            entriOpnameSales.setTypeTolerance(getInt(FRM_FLD_TYPE_OF_TOLERANCE));
            entriOpnameSales.setNetSalesPeriod(getDouble(FRM_FLD_NET_SALES_PERIOD));
            entriOpnameSales.setProsentaseTolerance(getDouble(FRM_FLD_PROSENTASE_TOLERANCE));
            entriOpnameSales.setBarangHilang(getDouble(FRM_FLD_BARANG_HILANG));
            entriOpnameSales.setPlusMinus(getDouble(FRM_FLD_PLUS_MINUS));
            entriOpnameSales.setCreateLocationName(getString(FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
            entriOpnameSales.setDtFromPeriod(getDate(FRM_FLD_DATE_START_PERIOD));
            entriOpnameSales.setDtToPeriod(getDate(FRM_FLD_DATE_END_PERIOD));
            
            //get oid
            entriOpnameSales.setOID(getLong(FRM_FLD_ENTRI_OPNAME_SALES_ID));
            
            
            //sudah di singkat di requestParamMultipleSelected
//            boolean sudahSesuaiData = false;
//            if (listEntriOpnameSales != null && listEntriOpnameSales.size() > 0) {
//                for (int x = 0; x < listEntriOpnameSales.size(); x++) {
//                    long selected = this.getParamLong("isSelected"+x);
//                    if (selected == oidEntriOpname) {
//                        sudahSesuaiData = true;
//                        entriOpnameSales.setLocationId(this.getParamLong(fieldNames[FRM_FLD_LOCATION_ID]+x));
//                        entriOpnameSales.setJenisSoId(this.getParamLong(fieldNames[FRM_FLD_JENIS_SO_ID]+x));
//                        entriOpnameSales.setTypeTolerance(this.getParamInt(fieldNames[FRM_FLD_TYPE_OF_TOLERANCE]+x));
//                        entriOpnameSales.setNetSalesPeriod(this.getParamDouble(fieldNames[FRM_FLD_NET_SALES_PERIOD]+x));
//                        entriOpnameSales.setProsentaseTolerance(this.getParamDouble(fieldNames[FRM_FLD_PROSENTASE_TOLERANCE]+x));
//                        entriOpnameSales.setBarangHilang(this.getParamDouble(fieldNames[FRM_FLD_BARANG_HILANG]+x));
//                        entriOpnameSales.setPlusMinus(this.getParamDouble(fieldNames[FRM_FLD_PLUS_MINUS]+x));
//                        entriOpnameSales.setCreateLocationName(this.getParamString(fieldNames[FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+x));
//                    }
//                }
//            }
//            if (sudahSesuaiData == false) {
//                int tot = listEntriOpnameSales != null ? listEntriOpnameSales.size() + 1 : 1;
//                entriOpnameSales.setLocationId(this.getParamLong(fieldNames[FRM_FLD_LOCATION_ID]+tot));
//                entriOpnameSales.setJenisSoId(this.getParamLong(fieldNames[FRM_FLD_JENIS_SO_ID]+tot));
//                entriOpnameSales.setTypeTolerance(this.getParamInt(fieldNames[FRM_FLD_TYPE_OF_TOLERANCE]+tot));
//                entriOpnameSales.setNetSalesPeriod(this.getParamDouble(fieldNames[FRM_FLD_NET_SALES_PERIOD]+tot));
//                entriOpnameSales.setProsentaseTolerance(this.getParamDouble(fieldNames[FRM_FLD_PROSENTASE_TOLERANCE]+tot));
//                entriOpnameSales.setBarangHilang(this.getParamDouble(fieldNames[FRM_FLD_BARANG_HILANG]+tot));
//                entriOpnameSales.setPlusMinus(this.getParamDouble(fieldNames[FRM_FLD_PLUS_MINUS]+tot));
//                entriOpnameSales.setCreateLocationName(this.getParamString(fieldNames[FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+tot));
//            }


            //set Nama_Employee
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /**
     * prosess pengambilan nilai
     *
     * @param oidEntriOpname
     * @param entriOpnameSales
     * @param listEntriOpnameSales
     */
    public void requestEntityMultipleObject() { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            String[] selectedUser = this.getParamsStringValues("userSelect");
            if (selectedUser != null && selectedUser.length > 0) {
                for (int x = 0; x < selectedUser.length; x++) {
                    EntriOpnameSales entriOpnameSales = new EntriOpnameSales();
                    entriOpnameSales.setOID(this.getParamLong(fieldNames[FRM_FLD_ENTRI_OPNAME_SALES_ID]+x));
                    entriOpnameSales.setBarangHilang(this.getParamDouble(fieldNames[FRM_FLD_BARANG_HILANG]+x));
                    entriOpnameSales.setCreateLocationName(this.getParamString(fieldNames[FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+x));
                    entriOpnameSales.setJenisSoId(this.getParamLong(fieldNames[FRM_FLD_JENIS_SO_ID]+x));
                    entriOpnameSales.setLocationId(this.getParamLong(fieldNames[FRM_FLD_LOCATION_ID]+x));
                    entriOpnameSales.setNetSalesPeriod(this.getParamDouble(fieldNames[FRM_FLD_NET_SALES_PERIOD]+x));
                    entriOpnameSales.setPlusMinus(this.getParamDouble(fieldNames[FRM_FLD_PLUS_MINUS]+x));
                    entriOpnameSales.setProsentaseTolerance(this.getParamDouble(fieldNames[FRM_FLD_PROSENTASE_TOLERANCE]+x));
                    entriOpnameSales.setStatusOpname(this.getParamString(fieldNames[FRM_FLD_STATUS_OPNAME]+x));
                    entriOpnameSales.setTypeTolerance(this.getParamInt(fieldNames[FRM_FLD_TYPE_OF_TOLERANCE]+x));
                    entriOpnameSales.setDtFromPeriod(this.getParamDateVer2(fieldNames[FRM_FLD_DATE_START_PERIOD]+x));
                    entriOpnameSales.setDtToPeriod(this.getParamDateVer2(fieldNames[FRM_FLD_DATE_END_PERIOD]+x));

                    vlistEntriOpname.add(entriOpnameSales);
                }
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    
    /**
     * update opie-eyek 20140312
     */
    public void requestEntityMultipleObject(Vector listEntriOpnameSales) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
           // String[] selectedUser = this.getParamsStringValues("userSelect");
            
            if(listEntriOpnameSales.size()>0){
                    for (int x = 0; x < listEntriOpnameSales.size(); x++) {
                        String selectedUserx = this.getParamString("userSelect_"+x);
                        if(selectedUserx.equals("1")){
                            long oid = 0;
                            oid= this.getParamLong(fieldNames[FRM_FLD_ENTRI_OPNAME_SALES_ID]+x);
                            EntriOpnameSales entriOpnameSales = new EntriOpnameSales();
                            if(oid!=0){
                                entriOpnameSales=PstEntriOpnameSales.fetchExc(oid);
                            }
                            
                            
                            //entriOpnameSales.setOID(this.getParamLong(fieldNames[FRM_FLD_ENTRI_OPNAME_SALES_ID]+x));
//                            entriOpnameSales.setBarangHilang(this.getParamDouble(fieldNames[FRM_FLD_BARANG_HILANG]+x));
//                            entriOpnameSales.setCreateLocationName(this.getParamString(fieldNames[FRM_FLD_CREATE_FORM_LOCATION_OPNAME]+x));
//                            entriOpnameSales.setJenisSoId(this.getParamLong(fieldNames[FRM_FLD_JENIS_SO_ID]+x));
//                            entriOpnameSales.setLocationId(this.getParamLong(fieldNames[FRM_FLD_LOCATION_ID]+x));
//                            entriOpnameSales.setNetSalesPeriod(this.getParamDouble(fieldNames[FRM_FLD_NET_SALES_PERIOD]+x));
//                            entriOpnameSales.setPlusMinus(this.getParamDouble(fieldNames[FRM_FLD_PLUS_MINUS]+x));
//                            entriOpnameSales.setProsentaseTolerance(this.getParamDouble(fieldNames[FRM_FLD_PROSENTASE_TOLERANCE]+x));
//                            entriOpnameSales.setStatusOpname(this.getParamString(fieldNames[FRM_FLD_STATUS_OPNAME]+x));
//                            entriOpnameSales.setTypeTolerance(this.getParamInt(fieldNames[FRM_FLD_TYPE_OF_TOLERANCE]+x));
                            //vlistEntriOpname.add(entriOpnameSales);
                            vlistEntriOpname.add(entriOpnameSales);
                        }
                    }
            }
            
            

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    

    public void requestEntityObject(EntriOpnameSales entriOpnameSales) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            entriOpnameSales.setLocationId(getLong(FRM_FLD_LOCATION_ID));
            entriOpnameSales.setJenisSoId(getLong(FRM_FLD_JENIS_SO_ID));
            entriOpnameSales.setTypeTolerance(getInt(FRM_FLD_TYPE_OF_TOLERANCE));
            entriOpnameSales.setNetSalesPeriod(getDouble(FRM_FLD_NET_SALES_PERIOD));
            entriOpnameSales.setProsentaseTolerance(getDouble(FRM_FLD_PROSENTASE_TOLERANCE));
            entriOpnameSales.setBarangHilang(getDouble(FRM_FLD_BARANG_HILANG));
            entriOpnameSales.setPlusMinus(getDouble(FRM_FLD_PLUS_MINUS));
            entriOpnameSales.setCreateLocationName(getString(FRM_FLD_CREATE_FORM_LOCATION_OPNAME));
            entriOpnameSales.setDtFromPeriod(getDate(FRM_FLD_DATE_START_PERIOD));
            entriOpnameSales.setDtToPeriod(getDate(FRM_FLD_DATE_END_PERIOD));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistEntriOpname() {
        return vlistEntriOpname;
    }
}
