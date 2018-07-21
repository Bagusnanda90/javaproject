/* 
 * Form Name  	:  FrmAssessmentFormMain.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee.assessment;

/* java package */
import javax.servlet.http.HttpServletRequest;

/* qdep package */
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

/* project package */

import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMainDetail;
import java.util.Vector;

public class FrmAssessmentFormMain extends FRMHandler implements I_FRMInterface, I_FRMType {

    private AssessmentFormMain assessmentFormMain;
    public static final String FRM_ASS_FORM_MAIN = "FRM_ASS_FORM_MAIN";
    private Vector FormAssMens = new Vector();
    private String msgFrm = "";
    public static final int FRM_FIELD_ASS_FORM_MAIN_ID = 0;
    public static final int FRM_FIELD_TITLE = 1;
    public static final int FRM_FIELD_SUBTITLE = 2;
    public static final int FRM_FIELD_TITLE_L2 = 3;
    public static final int FRM_FIELD_SUBTITLE_L2 = 4;
    public static final int FRM_FIELD_MAIN_DATA = 5;
    public static final int FRM_FIELD_NOTE = 6;
    public static final int FRM_FIELD_GROUP_RANK_ID = 7;
    //public static final int FRM_FIELD_ASS_FORM_MAIN_ID_GROUP = 8;
    public static String[] fieldNames = {
        "FRM_ASS_FORM_MAIN_ID",
        "FRM_TITLE",
        "FRM_SUBTITLE",
        "FRM_TITLE_L2",
        "FRM_SUBTITLE_L2",
        "FRM_MAIN_DATA",
        "FRM_NOTE",
        "FRM_GROUP_RANK_ID"
       // "FRM_FIELD_ASS_FORM_MAIN_ID_GROUP"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG + ENTRY_REQUIRED,
       // TYPE_LONG
    };

    public FrmAssessmentFormMain() {
    }

    public FrmAssessmentFormMain(AssessmentFormMain assessmentFormMain) {
        this.assessmentFormMain = assessmentFormMain;
    }

    public FrmAssessmentFormMain(HttpServletRequest request, AssessmentFormMain assessmentFormMain) {
        super(new FrmAssessmentFormMain(assessmentFormMain), request);
        this.assessmentFormMain = assessmentFormMain;
    }

    public String getFormName() {
        return FRM_ASS_FORM_MAIN;
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

    public AssessmentFormMain getEntityObject() {
        return assessmentFormMain;
    }

    public void requestEntityObject(AssessmentFormMain assessmentFormMain) {
        try {
            this.requestParam();
            assessmentFormMain.setTitle(getString(FRM_FIELD_TITLE));
            assessmentFormMain.setTitle_L2(getString(FRM_FIELD_TITLE_L2));
            assessmentFormMain.setSubtitle(getString(FRM_FIELD_SUBTITLE));
            assessmentFormMain.setSubtitle_L2(getString(FRM_FIELD_SUBTITLE_L2));
            assessmentFormMain.setMainData(getString(FRM_FIELD_MAIN_DATA));
            assessmentFormMain.setNote(getString(FRM_FIELD_NOTE));
            assessmentFormMain.setGroupRankId(getLong(FRM_FIELD_GROUP_RANK_ID));
           // assessmentFormMain.setFormMainIdGroup(getLong(FRM_FIELD_ASS_FORM_MAIN_ID_GROUP));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    /**
     * mengambil Id dari Group Rank
     * @param fieldName
     * @return 
     */
    public Vector getVectorLong(){
        try{
            Vector longs = new Vector();
            String sVals[] =  this.getParamsStringValues(fieldNames[FRM_FIELD_GROUP_RANK_ID]);
            if( (sVals==null) || (sVals.length<1))
                return longs;
            
            for(int i=0; i < sVals.length ; i++){
                try{
                    longs.add(new Long(Long.parseLong(sVals[i])));
                }catch (Exception exc){
                    System.out.println("EXC : getVectorLong i="+i+" - "+exc);
                }
            }
            
            return longs;
            
        }catch(Exception e){
        }
        
        return new Vector(1,1);
        
    }
    
    public Vector getGroupRank(long assFormMainId){
        Vector groupRankIds = new Vector(1,1);
        
        Vector rankIdselected = this.getVectorLong();        
        
        if (rankIdselected==null)
            return groupRankIds;
        int max = rankIdselected.size();
        
        for(int i=0; i< max; i++){
            long groupRankId = ( (Long)rankIdselected.get(i)).longValue();
            AssessmentFormMainDetail assessmentFormMainDetail = new AssessmentFormMainDetail();
            assessmentFormMainDetail.setGroupRankId(groupRankId);
            assessmentFormMainDetail.setAssMainDetail(assFormMainId);
            groupRankIds.add(assessmentFormMainDetail);
        }
        return groupRankIds;
    }

    public Vector<AssessmentFormMain> requestEntityObjectMultiple() {
        try {
            this.requestParam();

            String userSelect[] = this.getParamsStringValues(fieldNames[FRM_FIELD_GROUP_RANK_ID]);//request.getParameterValues("userSelect"); 
            if (userSelect != null && userSelect.length > 0) {
                for (int i = 0; i < userSelect.length; i++) {
                    try {
                        long groupRankId = Long.parseLong((userSelect[i]));

                        AssessmentFormMain assessmentFormMain = new AssessmentFormMain();

                        assessmentFormMain.setTitle(getString(FRM_FIELD_TITLE));
                        assessmentFormMain.setTitle_L2(getString(FRM_FIELD_TITLE_L2));
                        assessmentFormMain.setSubtitle(getString(FRM_FIELD_SUBTITLE));
                        assessmentFormMain.setSubtitle_L2(getString(FRM_FIELD_SUBTITLE_L2));
                        assessmentFormMain.setMainData(getString(FRM_FIELD_MAIN_DATA));
                        assessmentFormMain.setNote(getString(FRM_FIELD_NOTE));
                        assessmentFormMain.setGroupRankId(groupRankId);
                       // assessmentFormMain.setFormMainIdGroup(getLong(FRM_FIELD_ASS_FORM_MAIN_ID_GROUP));


                        getFormAssMens().add(assessmentFormMain);
                    } catch (Exception ex) {
                        System.out.println("Exception" + ex);
                    }
                }
            } else {
               this.setMsgFrm("Employee can not selected"); 
            }
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
        return getFormAssMens();
    }

    /**
     * @return the FormAssMens
     */
    public Vector getFormAssMens() {
        return FormAssMens;
    }

    /**
     * @param FormAssMens the FormAssMens to set
     */
    public void setFormAssMens(Vector FormAssMens) {
        this.FormAssMens = FormAssMens;
    }

    /**
     * @return the msgFrm
     */
    public String getMsgFrm() {
        return msgFrm;
    }

    /**
     * @param msgFrm the msgFrm to set
     */
    public void setMsgFrm(String msgFrm) {
        this.msgFrm = msgFrm;
    }
}
