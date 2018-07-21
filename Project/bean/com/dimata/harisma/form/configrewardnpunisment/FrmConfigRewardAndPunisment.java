/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmConfigRewardAndPunisment extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private ConfigRewardAndPunishment configRewardAndPunishment;
    public static final String FRM_CONFIG_REWARD_AND_PUNISHMENT = "FRM_CONFIG_REWARD_AND_PUNISHMENT";
    public static final int FRM_FLD_CONFIG_ID = 0;
    public static final int FRM_FLD_MAX_DEDUCTION = 1;
    public static final int FRM_FLD_IPRESENTASE_TO_SALES = 2;
    public static final int FRM_FLD_IPRESENTASE_TO_BOD = 3;
    public static final int FRM_FLD_DAY_NEW_EMPLOYEE_FREE = 4;
    public static String[] fieldNames = {
        "FRM_FLD_CONFIG_ID",
        "FRM_FLD_MAX_DEDUCTION",
        "FRM_FLD_IPRESENTASE_TO_SALES",
        "FRM_FLD_IPRESENTASE_TO_BOD",
        "FRM_FLD_DAY_NEW_EMPLOYEE_FREE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT
    };

    public FrmConfigRewardAndPunisment() {
    }

    public FrmConfigRewardAndPunisment(ConfigRewardAndPunishment configRewardAndPunishment) {
        this.configRewardAndPunishment = configRewardAndPunishment;
    }

    public FrmConfigRewardAndPunisment(HttpServletRequest request, ConfigRewardAndPunishment configRewardAndPunishment) {
        super(new FrmConfigRewardAndPunisment(configRewardAndPunishment), request);
        this.configRewardAndPunishment = configRewardAndPunishment;
    }

    public String getFormName() {
        return FRM_CONFIG_REWARD_AND_PUNISHMENT;
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

    public ConfigRewardAndPunishment getEntityObject() {
        return configRewardAndPunishment;
    }

    public void requestEntityObject(ConfigRewardAndPunishment configRewardAndPunishment) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            configRewardAndPunishment.setMaxDeduction(getDouble(FRM_FLD_MAX_DEDUCTION));
            configRewardAndPunishment.setPresentaseToSales(getDouble(FRM_FLD_IPRESENTASE_TO_SALES));
            configRewardAndPunishment.setPresentaseToBod(getDouble(FRM_FLD_IPRESENTASE_TO_BOD));
            configRewardAndPunishment.setDayNewEmployeeFree(getInt(FRM_FLD_DAY_NEW_EMPLOYEE_FREE));


            //set Nama_Employee

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
