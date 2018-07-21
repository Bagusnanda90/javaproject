/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentDetail;
import com.dimata.harisma.entity.configrewardnpunisment.RewardnPunismentDetail;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class FrmRewardPunismentDetail extends FRMHandler implements I_FRMInterface,
        I_FRMType {

    private RewardnPunismentDetail rewardnPunismentDetail;
    public static final String FRM_REWARD_PUNISMENT_DETAIL = "FRM_REWARD_PUNISMENT_DETAIL";
    public static final int FRM_FLD_EMPLOYEE_ID = 0;
    public static final int FRM_FLD_KOEFISIEN_POSITION = 1;
    public static final int FRM_FLD_HARI_KERJA = 2;
    public static final int FRM_FLD_TOTAL = 3;
    public static final int FRM_FLD_BEBAN = 4;
    public static final int FRM_FLD_REWARD_PUNISMENT_MAIN_ID = 5;
    public static final int FRM_FLD_REWARD_PUNISMENT_DETAIL_ID = 6;
    public static final int FRM_FLD_ADJUSMENT = 7;
    
    public static String[] fieldNames = {
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_KOEFISIEN_POSITION",
        "FRM_FLD_HARI_KERJA",
        "FRM_FLD_TOTAL",
        "FRM_FLD_BEBAN",
        "FRM_FLD_REWARD_PUNISMENT_MAIN_ID",
        "FRM_FLD_REWARD_PUNISMENT_DETAIL_ID",
        "FRM_FLD_ADJUSMENT",
       };
    public static int[] fieldTypes = {
        TYPE_LONG,//"FRM_FLD_EMPLOYEE_ID",
        TYPE_LONG,//"FRM_FLD_KOEFISIEN_POSITION",
        TYPE_INT,//"FRM_FLD_HARI_KERJA",
        TYPE_FLOAT,//"FRM_FLD_TOTAL",
        TYPE_FLOAT,//"FRM_FLD_BEBAN",
        TYPE_LONG,//"FRM_FLD_REWARD_PUNISMENT_MAIN_ID",
        TYPE_LONG,//"FRM_FLD_REWARD_PUNISMENT_DETAIL_ID",
        TYPE_INT,
    };

    public FrmRewardPunismentDetail() {
    }

    public FrmRewardPunismentDetail(RewardnPunismentDetail rewardnPunismentDetail) {
        this.rewardnPunismentDetail = rewardnPunismentDetail;
    }

    public FrmRewardPunismentDetail(HttpServletRequest request, RewardnPunismentDetail rewardnPunismentDetail) {
        super(new FrmRewardPunismentDetail(rewardnPunismentDetail), request);// super ini 
        //berfungsi memanggil induk construktor dari FRMHandler
        this.rewardnPunismentDetail = rewardnPunismentDetail;
    }

    public String getFormName() {
        return FRM_REWARD_PUNISMENT_DETAIL;
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

    public RewardnPunismentDetail getEntityObject() {
        return rewardnPunismentDetail;
    }

    public void requestEntityObject(RewardnPunismentDetail rewardnPunismentDetail) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            rewardnPunismentDetail.setRewardnPunismentMainId(getLong(FRM_FLD_REWARD_PUNISMENT_MAIN_ID));
            rewardnPunismentDetail.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            rewardnPunismentDetail.setKoefisienId(getInt(FRM_FLD_KOEFISIEN_POSITION));
            rewardnPunismentDetail.setHariKerja(getInt(FRM_FLD_HARI_KERJA));
            rewardnPunismentDetail.setTotal(getDouble(FRM_FLD_TOTAL));
            rewardnPunismentDetail.setBeban(getDouble(FRM_FLD_BEBAN));
            rewardnPunismentDetail.setAdjusment(getInt(FRM_FLD_ADJUSMENT));
             
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
