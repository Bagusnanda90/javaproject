/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;


import com.dimata.harisma.entity.configrewardnpunisment.SrcRewardPunishment;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PRISKA
 */
public class FrmSrcRewardPunishment extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private SrcRewardPunishment srcRewardPunishment;
    public static final String FRM_SRC_REWARD_PUNISHMENT = "FRM_SRC_REWARD_PUNISHMENT";
    public static final int FRM_FLD_FULL_NAME_EMPLOYEE = 0;
    public static final int FRM_FLD_EMPLOYEE_NUMBER = 1;
    public static final int FRM_FLD_COMPANY = 2;
    public static final int FRM_FLD_DIVISION = 3;
    public static final int FRM_FLD_DEPARTMENT = 4;
    public static final int FRM_FLD_SECTION = 5;
    public static final int FRM_FLD_LOCATION = 6;
    public static final int FRM_FLD_JENIS_SO = 7;
    public static final int FRM_FLD_PERIODEFROM = 8;
    public static final int FRM_FLD_PERIODETO = 9;
    public static final int FRM_FLD_STATUS_SO = 10;
    public static final int FRM_FLD_ORDER_BY = 11;
    public static final int FRM_FLD_GROUP_BY = 12;
    public static final int FRM_FLD_RADIO_PERIODE = 13;
    public static final int FRM_FLD_REWARD_PUNISMENT_MAIN_ID = 14;
    
    
    
    public static final int ORDER_EMPLOYEE_NAME = 0;
    public static final int ORDER_EMPLOYEE_NUM = 1;
    public static final int ORDER_TOTAL = 2;
    public static final int ORDER_BEBAN = 3;
    public static final int ORDER_TUNAI = 4;

    public static final int[] orderValue = {0, 1, 2, 3, 4};
    
    public static final String[] orderKey = {"Name", "Employee Number", "Total","Beban","Tunai"};

    public static Vector getOrderValue() {
        Vector order = new Vector();
        for (int i = 0; i < orderValue.length; i++) {
            order.add(String.valueOf(orderValue[i]));
        }
        return order;
    }

    public static Vector getOrderKey() {
        Vector order = new Vector();
        for (int i = 0; i < orderKey.length; i++) {
            order.add(orderKey[i]);
        }
        return order;
    }
    
    
    public static String[] fieldNames = {
        "FRM_FLD_FULL_NAME_EMPLOYEE",
        "FRM_FLD_EMPLOYEE_NUMBER",
        "FRM_FLD_COMPANY",
        "FRM_FLD_DIVISION",
        "FRM_FLD_DEPARTMENT",
        "FRM_FLD_SECTION",
        "FRM_FLD_LOCATION",
        "FRM_FLD_JENIS_SO",
        "FRM_FLD_PERIODEFROM",
        "FRM_FLD_PERIODETO",
        "FRM_FLD_STATUS_SO",
        "FRM_FLD_ORDER_BY",
        "FRM_FLD_GROUP_BY",
        "FRM_FLD_RADIO_PERIODE"
    };
    public static int[] fieldTypes = {
        TYPE_STRING, //"FRM_FLD_FULL_NAME_EMPLOYEE",
        TYPE_STRING, //"FRM_FLD_EMPLOYEE_NUMBER",
        TYPE_LONG, //"FRM_FLD_COMPANY",
        TYPE_LONG, //"FRM_FLD_DIVISION",
        TYPE_LONG, //"FRM_FLD_DEPARTMENT",
        TYPE_LONG, //"FRM_FLD_SECTION",
        TYPE_LONG, //"FRM_FLD_LOCATION",
        TYPE_LONG, //"FRM_FLD_JENIS_SO",
        TYPE_DATE, //"FRM_FLD_PERIODE",
        TYPE_DATE, //"FRM_FLD_PERIODE",
        TYPE_LONG, //"FRM_FLD_STATUS_SO",
        TYPE_LONG, //"FRM_FLD_ORDER_BY",
        TYPE_LONG, //"FRM_FLD_GROUP_BY"       
        TYPE_STRING, //"FRM_FLD_RADIO_PERIODE"
    };

    public FrmSrcRewardPunishment() {
    }

    public FrmSrcRewardPunishment(SrcRewardPunishment srcRewardPunishment) {
        this.srcRewardPunishment = srcRewardPunishment;
    }

    public FrmSrcRewardPunishment(HttpServletRequest request, SrcRewardPunishment srcRewardPunishment) {
        super(new FrmSrcRewardPunishment(srcRewardPunishment), request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.srcRewardPunishment = srcRewardPunishment;
    }
    
    public String getFormName() {
        return FRM_SRC_REWARD_PUNISHMENT;
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

    public SrcRewardPunishment getEntityObject() {
        return srcRewardPunishment;
    }

    public void requestEntityObject(SrcRewardPunishment srcRewardPunishment) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            srcRewardPunishment.setFullNameEmp(getString(FRM_FLD_FULL_NAME_EMPLOYEE));
            srcRewardPunishment.setEmpnumber(getString(FRM_FLD_EMPLOYEE_NUMBER));
            srcRewardPunishment.addArrCompany(this.getParamsStringValues(fieldNames[FRM_FLD_COMPANY]));
            srcRewardPunishment.addArrDivision(this.getParamsStringValues(fieldNames[FRM_FLD_DIVISION]));
            srcRewardPunishment.addArrDepartmentId(this.getParamsStringValues(fieldNames[FRM_FLD_DEPARTMENT]));
            srcRewardPunishment.addArrSectionId(this.getParamsStringValues(fieldNames[FRM_FLD_SECTION]));   
            srcRewardPunishment.addArrLocationId(this.getParamsStringValues(fieldNames[FRM_FLD_LOCATION]));
            srcRewardPunishment.addArrJenisSo(this.getParamsStringValues(fieldNames[FRM_FLD_JENIS_SO])); 
            srcRewardPunishment.setStatusOpname(getInt(FRM_FLD_STATUS_SO)); 
            srcRewardPunishment.addDtPeriodFrom(getDate(FRM_FLD_PERIODEFROM));
            srcRewardPunishment.addDtPeriodTo(getDate(FRM_FLD_PERIODETO));
            srcRewardPunishment.addSort(""+getInt(FRM_FLD_ORDER_BY));
            srcRewardPunishment.addGroupBy(""+getInt(FRM_FLD_GROUP_BY));
            srcRewardPunishment.setRewardPunismentMainId(getString(FRM_FLD_REWARD_PUNISMENT_MAIN_ID));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
