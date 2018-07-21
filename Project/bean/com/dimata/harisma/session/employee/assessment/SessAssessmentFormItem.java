/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.employee.assessment;

import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
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
public class SessAssessmentFormItem {
    
    /**
     * Mengambil halaman terbesar pada oid form main
     */
    public static int getMaxPage(long formMainOid){
        int maxPageItem = getMaxPageItem(formMainOid);
        int maxPageSection = getMaxPageSection(formMainOid);
        int max = maxPageItem;
        if(max<maxPageSection){
            max = maxPageSection;
        }
        return max;
    }
    
    private static int getMaxPageItem(long formMainOid){
        DBResultSet dbrs = null;
        try{
            String query = "SELECT MAX(I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    +") FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" AS I INNER JOIN "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    +" AS S ON I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                    +" INNER JOIN "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN
                    +" AS M ON S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    +" = M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" WHERE M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" = "+formMainOid;
           // System.out.println("SQL 1 : "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            int count = 0;
            while(rs.next()) { count = rs.getInt(1); }
            rs.close();
            return count;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs);
        }
    }
    
    private static int getMaxPageSection(long formMainOid){
        DBResultSet dbrs2 = null;
        try{
            String querySec = "SELECT MAX(S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]
                    +") FROM "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    +" AS S INNER JOIN "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN
                    +" AS M ON S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    +" = M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" WHERE M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" = "+formMainOid;
         //   System.out.println("SQL 1 : "+querySec);
            dbrs2 = DBHandler.execQueryResult(querySec);
            ResultSet rs2 = dbrs2.getResultSet();
            int countSec = 0;
            while(rs2.next()) { countSec = rs2.getInt(1); }
            rs2.close();
            return countSec;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs2);
        }
    }
    
    /**
     * Mengambil seluruh halaman
     */
    public static Vector getPage(int start, int recordToGet, long formMainOid){
        DBResultSet dbrs = null;
        Vector vPage = new Vector(1,1);
        try{
            String query = "SELECT DISTINCT I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    +" FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" AS I INNER JOIN "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    +" AS S ON I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                    +" INNER JOIN "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN
                    +" AS M ON S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    +" = M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" WHERE M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" = "+formMainOid;
            
            query = query + " GROUP BY I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE];
            
            if(start!=0 && recordToGet!=0){
                query = query + " LIMIT " + start + "," + recordToGet;
            }
        //    System.out.println("::::::::::::::: GET PAGE :: "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();;
            while(rs.next()) { vPage.add(rs.getString(1)); }
            rs.close();
            return vPage;
        }catch(Exception e) {
                return new Vector(1,1);
        }finally {
                DBResultSet.close(dbrs);
        }
    }
    
    public static void changeOrderNumber(int orderNumber,int iPage){
        String whereClause = PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER]
                +">="+orderNumber
                +" AND "+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_PAGE]
                +" = "+iPage;
        Vector vItem = new Vector(1,1);
        vItem = PstAssessmentFormItem.list(0, 0, whereClause, PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]+" ASC");
        int renumber = orderNumber+1;
        for(int i=0;i<vItem.size();i++){
            AssessmentFormItem item = (AssessmentFormItem)vItem.get(i);
            item.setOrderNumber(renumber);
            try{
                PstAssessmentFormItem.updateExc(item);
            }catch(Exception ex){}
            renumber +=1;
        }
    }
    
    /**
     * Mengambil section pada page tertentu
     */
 /*   public static Vector getSectionAtPage(int page, long formMainOid){
        DBResultSet dbrs = null;
        Vector vSection = new Vector(1,1);
        try{
            String query = "SELECT DISTINCT I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" AS I INNER JOIN "+PstAssessmentFormSection.TBL_HR_ASS_FORM_SECTION
                    +" AS S ON I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_SECTION_ID]
                    +" INNER JOIN "+PstAssessmentFormMain.TBL_HR_ASS_FORM_MAIN
                    +" AS M ON S."+PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID]
                    +" = M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" WHERE M."+PstAssessmentFormMain.fieldNames[PstAssessmentFormMain.FLD_ASS_FORM_MAIN_ID]
                    +" = "+formMainOid
                    +" AND I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    +" = "+page;
            
            query = query + " GROUP BY I."+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID];
            
            System.out.println("::::::::::::::: 123 :: "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();;
            while(rs.next()) { vSection.add(rs.getString(1)); }
            rs.close();
            return vSection;
        }catch(Exception e) {
                return new Vector(1,1);
        }finally {
                DBResultSet.close(dbrs);
        }
    }
  */  
    
    public static int getMaxOrderNumber(long sectionOid,int page){
        DBResultSet dbrs2 = null;
        try{
            String querySec = "SELECT MAX("+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]
                    +") FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" WHERE "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = "+sectionOid
                    +" AND "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                    +" = "+page;
    //        System.out.println("SQL getMaxOrderNumber : "+querySec);
            dbrs2 = DBHandler.execQueryResult(querySec);
            ResultSet rs2 = dbrs2.getResultSet();
            int countSec = 0;
            while(rs2.next()) { countSec = rs2.getInt(1); }
            rs2.close();
            return countSec;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs2);
        }
    }
    
    public static int getMaxOrderNumber(long sectionOid){
        DBResultSet dbrs2 = null;
        try{
            String querySec = "SELECT MAX("+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]
                    +") FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" WHERE "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = "+sectionOid;
    //        System.out.println("SQL getMaxOrderNumber : "+querySec);
            dbrs2 = DBHandler.execQueryResult(querySec);
            ResultSet rs2 = dbrs2.getResultSet();
            int countSec = 0;
            while(rs2.next()) { countSec = rs2.getInt(1); }
            rs2.close();
            return countSec;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs2);
        }
    }
    
    public static int getMaxNumber(long sectionOid){
        DBResultSet dbrs2 = null;
        try{
            String querySec = "SELECT MAX("+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_NUMBER]
                    +") FROM "+PstAssessmentFormItem.TBL_HR_ASS_FORM_ITEM
                    +" WHERE "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                    +" = "+sectionOid;
    //        System.out.println("SQL getMaxNumber : "+querySec);
            dbrs2 = DBHandler.execQueryResult(querySec);
            ResultSet rs2 = dbrs2.getResultSet();
            int countSec = 0;
            while(rs2.next()) { countSec = rs2.getInt(1); }
            rs2.close();
            return countSec;
        }catch(Exception e) {
                return 0;
        }finally {
                DBResultSet.close(dbrs2);
        }
    }
    
    public static Vector listItem(long sectionOid, int page){
        Vector vItem = new Vector(1,1);
        String whereClause = PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]
                +"="+sectionOid
                +" AND "+PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_PAGE]
                +" BETWEEN "+1+" AND "+page;
        vItem = PstAssessmentFormItem.list(0, 0, whereClause, PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ORDER_NUMBER]);
        return vItem;
    }
    
}
