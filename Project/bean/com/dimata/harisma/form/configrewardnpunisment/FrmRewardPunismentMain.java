/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentMain;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmRewardPunismentMain extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private RewardnPunismentMain rewardnPunismentMain;
    public static final String FRM_REWARD_PUNISMENT_MAIN = "FRM_REWARD_PUNISMENT_MAIN";
    public static final int FRM_FLD_REWARD_PUNIMENT_MAIN_ID = 0;
    public static final int FRM_FLD_DETAIL_NBH_NO = 1;
    public static final int FRM_FLD_JENIS_SO_ID = 2;
    public static final int FRM_FLD_LOCATION_ID = 3;
    public static final int FRM_FLD_STATUS_DOC = 4;
    public static final int FRM_FLD_TGL_CREATE_DOC = 5;
    public static final int FRM_FLD_APPROVALL_ONE = 6;
    public static final int FRM_FLD_APPROVALL_TWO = 7;
    public static final int FRM_FLD_APPROVALL_THREE = 8;
    public static final int FRM_FLD_START_DATE_PERIOD = 9;
    public static final int FRM_FLD_END_DATE_PERIOD = 10;
    public static String[] fieldNames = {
        "FRM_FLD_REWARD_PUNIMENT_MAIN_ID",
        "FRM_FLD_DETAIL_NBH_NO",
        "FRM_FLD_JENIS_SO_ID",
        "FRM_FLD_LOCATION_ID",
        "FRM_FLD_STATUS_DOC",
        "FRM_FLD_TGL_CREATE_DOC",
        "FRM_FLD_APPROVALL_ONE",
        "FRM_FLD_APPROVALL_TWO",
        "FRM_FLD_APPROVALL_THREE",
        "FRM_FLD_START_DATE_PERIOD",
        "FRM_FLD_END_DATE_PERIOD"
    };
    public static int[] fieldTypes = {
         TYPE_LONG,//"FRM_FLD_REWARD_PUNIMENT_MAIN_ID",
        TYPE_STRING,//"FRM_FLD_DETAIL_NBH_NO",
        TYPE_LONG,//"FRM_FLD_JENIS_SO_ID",
        TYPE_LONG,//"FRM_FLD_LOCATION_ID",
        TYPE_INT,//"FRM_FLD_STATUS_DOC",
        TYPE_DATE,//"FRM_FLD_TGL_CREATE_DOC",
        TYPE_LONG,//"FRM_FLD_APPROVALL_ONE",
        TYPE_LONG,//"FRM_FLD_APPROVALL_TWO",
        TYPE_LONG,//"FRM_FLD_APPROVALL_THREE",
        TYPE_DATE,//"FRM_FLD_START_DATE_PERIOD",
        TYPE_DATE,//"FRM_FLD_END_DATE_PERIOD"
    };

    public FrmRewardPunismentMain() {
    }

    public FrmRewardPunismentMain(RewardnPunismentMain rewardnPunismentMain) {
        this.rewardnPunismentMain = rewardnPunismentMain;
    }

    public FrmRewardPunismentMain(HttpServletRequest request, RewardnPunismentMain rewardnPunismentMain) {
        super(new FrmRewardPunismentMain(rewardnPunismentMain), request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.rewardnPunismentMain = rewardnPunismentMain;
    }

    public String getFormName() {
        return FRM_REWARD_PUNISMENT_MAIN;
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

    public RewardnPunismentMain getEntityObject() {
        return rewardnPunismentMain;
    }

    public void requestEntityObject(RewardnPunismentMain rewardnPunismentMain) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            rewardnPunismentMain.setDetailNbhNo(getString(FRM_FLD_DETAIL_NBH_NO));
            
            rewardnPunismentMain.setJenisSoId(getLong(FRM_FLD_JENIS_SO_ID));
            rewardnPunismentMain.setLocationId(getLong(FRM_FLD_LOCATION_ID));
            rewardnPunismentMain.setStatusDoc(getInt(FRM_FLD_STATUS_DOC));
            rewardnPunismentMain.setDtCreateDocument(getDate(FRM_FLD_TGL_CREATE_DOC));
            rewardnPunismentMain.setApprovallOne(getLong(FRM_FLD_APPROVALL_ONE));
            rewardnPunismentMain.setApprovallTwo(getLong(FRM_FLD_APPROVALL_TWO));
            rewardnPunismentMain.setApprovallThree(getLong(FRM_FLD_APPROVALL_THREE));
            rewardnPunismentMain.setDtFromPeriod(getDate(FRM_FLD_START_DATE_PERIOD));
            rewardnPunismentMain.setDtToPeriod(getDate(FRM_FLD_END_DATE_PERIOD));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
