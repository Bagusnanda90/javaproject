/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee.assessment;

import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessAssessmentFormSection {
    
    /**
     * Mengambil nomor urut terbesar
     */
    public static int getMaxOrderNumber(long formMainOid, int page){
        DBResultSet dbrs = null;
        int count = 0;
        try{
            String query = "SELECT MAX("+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]
                    +") FROM "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    +" AS S INNER JOIN "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN
                    +" AS M ON S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    +" = M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" WHERE M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" = "+formMainOid
                    +" AND "+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]
                    +" = "+page;
                    dbrs = DBHandler.execQueryResult(query);
                    ResultSet rs = dbrs.getResultSet();
                    while(rs.next()) { count = rs.getInt(1); }
                    rs.close();
                    return count;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs);
        }
    }
    
    public static void changeOrderNumber(int orderNumber,int iPage){
        String whereClause = PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]
                +">="+orderNumber
                +" AND "+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]
                +" = "+iPage;
        Vector vSection = new Vector(1,1);
        vSection = PstAssessmentFormSection.list(0, 0, whereClause, PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]+" ASC");
        int renumber = orderNumber+1;
        for(int i=0;i<vSection.size();i++){
            AssessmentFormSection section = (AssessmentFormSection)vSection.get(i);
            section.setOrderNumber(renumber);
            //System.out.println(">>>>>>>>>>>>>> ORDER NUMBER "+renumber);
            try{
                PstAssessmentFormSection.updateExc(section);
            }catch(Exception ex){}
            renumber +=1;
        }
    }
    
    public static Vector getSections(int page, long formMainOid){
        Vector vSection = new Vector(1,1);
        String strWhere = "";
        strWhere = PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]
                +" BETWEEN "+(1)+" AND "+(page)
                +" AND "+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                +" = "+formMainOid;
        vSection = PstAssessmentFormSection.list(0, 0, strWhere, PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]+","+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]);
        return vSection;
    }
    
    public static Vector getItems(int page, long formSectionOid){
        Vector vItem = new Vector(1,1);
        String whereClause = PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                +"="+formSectionOid
                +" AND "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                +"="+page;
        vItem = PstAssessmentFormItem.list(0, 0, whereClause, PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]);
        return vItem;
    }
}
