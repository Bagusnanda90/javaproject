/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee.appraisal;

import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisal;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.harisma.session.employee.assessment.SessAssessmentMain;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessAppraisal {
    
    //List appraisal sesuai dengan appraisal main nya
    public static Hashtable listAppraisal(long appMainOid){
        Hashtable hashtableApp = new Hashtable();
        String whereClause = PstAppraisal.fieldNames[PstAppraisal.FLD_APP_MAIN_ID]
                +"="+appMainOid;
        Vector vTemp = new Vector(1,1);
        vTemp = PstAppraisal.list(0, 0, whereClause, PstAppraisal.fieldNames[PstAppraisal.FLD_ASS_FORM_ITEM_ID]);

        for(int i=0;i<vTemp.size();i++){
            Appraisal app = new Appraisal();
            app = (Appraisal)vTemp.get(i);
            hashtableApp.put(String.valueOf(app.getAssFormItemId()), app);
        }
        return hashtableApp;
    }
    
    //Mengupdate nilai dari rating pada appraisal main dari appraisal
    public static void updateRating(long appraisalMainOid){
        AssessmentFormMain assMain = SessAssessmentMain.getAssessment(appraisalMainOid);
        int maxPoint = countItemRating(assMain.getOID());
        double maxRating = countRatingVal(assMain.getOID(),appraisalMainOid);
        double average = maxRating/maxPoint;
        AppraisalMain appMain = new AppraisalMain();
        try{
            appMain = PstAppraisalMain.fetchExc(appraisalMainOid);
            appMain.setTotalScore(maxRating);
            appMain.setTotalAssessment(maxPoint);
            appMain.setScoreAverage(average);
            PstAppraisalMain.updateExc(appMain);
        }catch(Exception ex){
            System.out.println("[ERROR] com.dimata.harisma.session.employee.appraisal.SessAppraisal.updateRating :::: "+ex.toString());
        }
    }
    
    private static int countItemRating(long assMainOid){
        String query = "SELECT COUNT(item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_ITEM_ID]
                +") FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM+" AS item "
                +" INNER JOIN "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION+" AS sect "
                +"ON item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                +" = sect."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                +" WHERE sect."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                +" = "+assMainOid
                +" AND (item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]
                +" = "+PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT
                +" OR item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]
                +" = "+PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT+")";
        int count = 0;
        DBResultSet dbrs = null;
        try {
                dbrs = DBHandler.execQueryResult(query);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                       count = rs.getInt(1);
                }
                rs.close();
                return count;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
        return count;
    }
    
    private static double countRatingVal(long assMainOid, long appMainOid){
        String query = "SELECT SUM(app."+PstAppraisal.fieldNames[PstAppraisal.FLD_RATING]
                +") FROM "+PstAppraisal.TBL_HR_APPRAISAL+" AS app "
                +" INNER JOIN "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM+" AS item "
                +" ON app."+PstAppraisal.fieldNames[PstAppraisal.FLD_ASS_FORM_ITEM_ID]
                +" = item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_ITEM_ID]
                +" INNER JOIN "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION+" AS sect "
                +" ON item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                +" = sect."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                +" WHERE sect."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                +" = "+assMainOid
                +" AND app."+PstAppraisal.fieldNames[PstAppraisal.FLD_APP_MAIN_ID]
                +" = "+appMainOid
                +" AND (item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]
                +" = "+PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT
                +" OR item."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_TYPE]
                +" = "+PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT+")";
        double ratingTotal = 0;
        DBResultSet dbrs = null;
        try {
                dbrs = DBHandler.execQueryResult(query);
                ResultSet rs = dbrs.getResultSet();
                while(rs.next()) {
                       ratingTotal = rs.getDouble(1);
                }
                rs.close();
                return ratingTotal;

        }catch(Exception e) {
                System.out.println(e);
        }finally {
                DBResultSet.close(dbrs);
        }
        return ratingTotal;
    }
}
