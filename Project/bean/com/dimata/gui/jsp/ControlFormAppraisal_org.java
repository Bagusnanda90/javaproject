/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.gui.jsp;

import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.harisma.entity.masterdata.Evaluation;
import com.dimata.harisma.entity.masterdata.PstEvaluation;
import com.dimata.harisma.form.employee.appraisal.FrmAppraisal;
import com.dimata.harisma.session.employee.appraisal.SessAppraisal;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormItem;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormSection;
import com.dimata.qdep.db.I_DBType;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class ControlFormAppraisal_org{
    public static String createFormMain(AssessmentFormMain assessmentFormMain, Vector vAppData){
        String strForm = "";
        strForm = createMainHeader(assessmentFormMain)+"<br>"+createMainData(assessmentFormMain, vAppData)
                +"<br>"+createMainDesc()+"<br>"+createMainRating();
        return strForm;
    }
    public static String createFormSection(AssessmentFormSection assessmentFormSection){
        String strForm = "";
        switch(assessmentFormSection.getType()){
            case PstAssessmentFormSection.TYPE_TEXT_ONLY: 
                strForm = createSectionTypeA(assessmentFormSection);
                break;
            case PstAssessmentFormSection.TYPE_WITH_BACKGROUND: 
                strForm = createSectionTypeB(assessmentFormSection);
                break;
            default: 
                strForm = createSectionTypeA(assessmentFormSection);
                break;
        }
        return strForm;
    }
    public static String createFormItem(AssessmentFormItem assessmentFormItem){
        String strForm = "";
        
        return strForm;
    }
    
    //Main
    private static String createMainHeader(AssessmentFormMain assessmentFormMain){
        String strItem="";
        strItem += "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"
                +"<tr><td align='center' valign='bottom'><font size='4px'><b>"
                +assessmentFormMain.getTitle()
                +"</b></font></td></tr>";
        if(assessmentFormMain.getTitle_L2().length()>0){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                +assessmentFormMain.getTitle_L2()
                +"</font></td></tr>";
        }
        if(assessmentFormMain.getSubtitle().length()>0){
        strItem += "<tr><td align='center' valign='bottom'><font size='4px'><b><i>"
                +assessmentFormMain.getSubtitle()
                +"</i></b></font></td></tr>";
        }
        if(assessmentFormMain.getSubtitle_L2().length()>0){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                +assessmentFormMain.getSubtitle_L2()
                +"</i></font></td></tr>";
        }
        strItem +="</table>";
        return strItem;
    }
    
    private static String createMainData(AssessmentFormMain assessmentFormMain, Vector vAppData){
        String strItem = "";
        String strMainData = assessmentFormMain.getMainData();
        boolean isDataAvailable = false;
        if(PstAssessmentFormMain.fieldFormTypes!=null && PstAssessmentFormMain.fieldFormTypes.length>0){
            for(int i=0; i<PstAssessmentFormMain.fieldFormTypes.length; i++){
                   boolean isUsed = PstAssessmentFormMain.cekFormUsed(strMainData, i);
                  // System.out.println(i+"=="+isUsed);
                   String strVal = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_FIRST];
                   String strValL2 = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_OTHER];
                   		
                if(isUsed){
                    isDataAvailable = true;
                    strItem += "  <tr>";
                    strItem += "    <td width='35%' height='20' align='left' valign='top'><strong>"+strVal+"</strong>";
                    strItem += "        <br>"+strValL2+"</td>";
                    strItem += "    <td width='2%' height='20' align='center' valign='top'><strong>:</strong></td>";
                    strItem += "        <td width='63%' height='20' align='left' valign='top'>";
                    if(vAppData.size()>0){
                        try{
                            String strData = (String)vAppData.get(i);
                            strItem += "<b>"+strData+"</b>";
                        }catch(Exception ex){}
                    }
                    strItem += "        </td>";
                    strItem += "  </tr>";
                }
            }
            if(isDataAvailable){
                strItem = "<table width='100%' bgcolor='000000' border='0' cellspacing='1' cellpadding='1'><tr><td><table width='100%' border='0' cellspacing='1' cellpadding='1' bgcolor='bab4b4'>"
                        +strItem;
                strItem += "</table></td></tr></table>";
            }
        }
        
        return strItem;
    }
    
    private static String createMainDesc(){
        String strItem = "";
        strItem += "<p><strong><u>Preparation<em><br>"
                +"(Persiapan)</em></u></strong></p>"
                +"<ul><li type='disc'> Use this form only for those positions indentified in the assessment guide."
                +"<br /> <i> (Gunakan Formulir ini hanya untuk jabatan yang tercantum dalam petunjuk penilain) </i>"
                +"</li>"
                +"<li type='disc'>Carefully read Human Resources P&P-66 - prior to assessment."
                +"<br /> <i> (Bacalah dengan cermat peraturan dan prosedur Human Resources P&P-66- sebelum penilaian dilakukan) </i> </li></ul>"
                +"<p> <b> <u> Assessor: <br>"
                +"  <i>(Penilain/Penguji)</i></u></b></p> "
                +" <p>Minimum <b><i>two weeks</i></b> before the scheduled review, you should:"
                +"<br /> <i> Minimal 2 minggu sebelum penilaian dijadwalkan, anda harus: </i> </p>"
                +" <ul>"
                +"   <li>Explain the performance assessment process to the Band Member assessed."
                +"     <br /> "
                +"     <i> (Terangkan proses penilaian kepada Band Member yang akan dinilai) </i>     </li>"
                +"   <li>Provide a performance assessment from to the Band Member and shedule date &amp; time for the interview."
                +"   	<br />"
                +"    <em>(Sediakan formulir penilaian kepada band member &amp; Membuat jadwal untuk penilaian) </em></li>"
                +"   <li>After receiving the completed form, the assessor considers the information received and objectively completes "
                +"   <br />"
                +"   the assessor's column. The assessor collects all supporting data required to carry put the assessment."
                +"   <br />"
                +"   <em>(Setelah menerima formulir yang telah diisi secara lengkap, penilai mempertimbangkan informasi yang "
                +"   	<br />"
                +"   	diterima dan melengkapi kolom penguji secara objectif. Penilai mengumpulkan seluruh data yang diperlukan"
                +"	<br /> untuk melakukan penilaian.)</em></li>"
                +" </ul>"
                +" <blockquote>"
                +"   <p><strong>At interview:</strong><br />"
                +"   	<em>(Saat Wawancara)</em></p>"
                +" </blockquote>"
                +" "
                +" <ul>"
                +"   <li>Comments and ratings are agreed and recorded on Assessor's Form. Agree on an Action Plan together with the"
                +"   <br />Band Member. Forward this form duly completed in ink, with appropriate signatures to the Human Resources"
                +"   <br /> office."
                +"   <em><br /> "
                +"   (Komentar dan tingkat penilaian diakui dan dicatat dalam formulir penilaian. Rencana kegiatan disetujui juga "
                +"   <br /> "
                +"   oleh band memeber. Formulir diisi dengan <u>Pulpen</u> dan dengan tanda tangan yang jelas untuk dikumpulkan ke"
                +"   <br /> "
                +"   Human Resources Departemen)</em></li>"
                +"   <li>If an agreement is not reached, the supervisor's rating prevails with an explanation as appropriate."
                +"     <em><br /> "
                +"   (Apabila tidak tercapai kesepakatan, rangking ditentukan oleh supervisor dengan penjelasan yang beralasan)</em></li>"
                +"   <li>Based on Band Member's and Assessor's comments, a constructive discussion should take place resulting in an"
                +"   <br /> individual training and development plan. "
                +"   <br /> "
                +"   <em>(Berdasarkan komentar dari band member dan penilai, akan diambil langkah-langkah untuk pengembangan"
                +"   <br /> "
                +"   dan training individu yang diperlukan)</em></li>"
                +" </ul>";
        return strItem;
    }
    
    private static String createMainRating(){
        String strItem = "";
        Vector vRating = new Vector();
        vRating = PstEvaluation.list(0, 0, "", PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_PERCENTAGE]+" DESC");
        if(vRating.size()>0){
            strItem += "<p><strong><u>Assessment Ratings<em><br>"
                    +"(Tingkat Penilaian)</em></u></strong></p>";
            strItem += "<table width='100%' bgcolor='000000' border='0' cellspacing='0' cellpadding='0'><tr><td><table width='100%' border='0' cellspacing='1' cellpadding='2'>";
            int colSize = 100/vRating.size();
            double maxValPrev = 0;
            double maxValCurr = 0;
            strItem += "  <tr bgcolor='ffffff'>";
            for(int i=0;i<vRating.size();i++){
                Evaluation evCurr = (Evaluation)vRating.get(i);
                Evaluation evNext = new Evaluation();
                if(i+1<vRating.size()){
                    evNext = (Evaluation)vRating.get(i+1);
                }
                maxValCurr = evCurr.getMaxPercentage();
                maxValPrev = evNext.getMaxPercentage();
                String strTitle = evCurr.getName();
                int posTitleDiv = strTitle.indexOf("(");
                strItem += "    <td width='"+colSize+"%' align='center' valign='top'>";
                strItem += "                <b>";
                if(posTitleDiv>-1){
                    strItem += "                    "+strTitle.substring(0,posTitleDiv);
                    strItem += "                    <br><i>"+strTitle.substring(posTitleDiv,strTitle.length())+"</i>";
                }else{
                    strItem += "                    "+strTitle+"<br>";
                }
                if(maxValPrev>0){
                    strItem += "                <br><br><i>"+(maxValPrev+1)+"% - "+maxValCurr+"%</i>";
                }else{
                    strItem += "                <br><br><i>"+(maxValCurr+1)+"% & below</i>";
                }
                strItem += "                </b>";
                strItem += "    </td>";
            }
            strItem += "  </tr>";
            strItem += "  <tr bgcolor='ffffff'>";
            for(int ij=0;ij<vRating.size();ij++){
                Evaluation evCurr = (Evaluation)vRating.get(ij);
                Evaluation evNext = new Evaluation();
                if(ij+1<vRating.size()){
                    evNext = (Evaluation)vRating.get(ij+1);
                }
                maxValCurr = evCurr.getMaxPercentage();
                maxValPrev = evNext.getMaxPercentage();
                String strDesc = evCurr.getDesription();
                int posDescDiv = strDesc.indexOf("(");
                strItem += "    <td width='"+colSize+"%' align='justify' valign='top'>";
                if(posDescDiv>-1){
                    strItem += "                    "+strDesc.substring(0,posDescDiv);
                    strItem += "                    <br><br><i>"+strDesc.substring(posDescDiv,strDesc.length())+"</i>";
                }else{
                    strItem += "                    "+strDesc+"<br><br>";
                }
                strItem += "    </td>";
            }
            strItem += "  </tr>";
            strItem += "</table></td></tr></table>";
        }
        return strItem;
    }
    
    //Section
    private static String createSectionTypeA(AssessmentFormSection assessmentFormSection){
        String strItem="";
        strItem += "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"
                +"<tr><td align='center' valign='bottom'><font size='3px'><b><u>"
                +assessmentFormSection.getSection()
                +"</u></b></font></td></tr>";
        if(assessmentFormSection.getSection_L2()!=null){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                +assessmentFormSection.getSection_L2()
                +"</font></td></tr>";
        }
        if(assessmentFormSection.getDescription()!=null){
        strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><i>"
                +assessmentFormSection.getDescription()
                +"</i></b></font></td></tr>";
        }
        if(assessmentFormSection.getDescription_L2()!=null){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                +assessmentFormSection.getDescription_L2()
                +"</i></font></td></tr>";
        }
        strItem +="</table>";
        return strItem;
    }
    
     private static String createSectionTypeB(AssessmentFormSection assessmentFormSection){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
              strItem+="<tr>";
              strItem+="  <td width='100%' height='100%'>";
              strItem+="<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'>";
        
        strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><u>"
                +assessmentFormSection.getSection()
                +"</u></b></font></td></tr>";
        if(assessmentFormSection.getSection_L2().length()>0){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                +assessmentFormSection.getSection_L2()
                +"</font></td></tr>";
        }
        if(assessmentFormSection.getDescription().length()>0){
        strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><i>"
                +assessmentFormSection.getDescription()
                +"</i></b></font></td></tr>";
        }
        if(assessmentFormSection.getDescription_L2().length()>0){
        strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                +assessmentFormSection.getDescription_L2()
                +"</i></font></td></tr>";
        }
        
        
        strItem += "</table></td></tr></table>";
        return strItem;
    }
    
    
    //Item
    private static String createItemTypeSpace(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='1' cellpadding='1'>";
        for(int i=0;i<assessmentFormItem.getHeight();i++){      
            strItem+="<tr><td width='100%'>"+" "+"</td></tr>";
        }
        strItem+="</table>";
        return strItem;
    }
    
      private static String createItemTypeText(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr><td>";
          strItem+="<div align='justify'>";
              if(assessmentFormItem.getTitle()!=null){
                  strItem+="              <p><strong>"+assessmentFormItem.getTitle()+"";
              }
              if(assessmentFormItem.getTitle_L2()!=null){
                  strItem+="          <em>"+assessmentFormItem.getTitle_L2()+"</em></strong></p>";
              }else if(assessmentFormItem.getTitle()!=null){
                  strItem+="          </strong></p>";
              }
              strItem+="</div></td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
             
             // System.out.println(strItem);
        return strItem;
    }
   
    
     private static String createItemType2ColHeader(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
              strItem+="<tr>";
              strItem+="  <td width='50%' height='100%'>";
              strItem+="<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
              if(assessmentFormItem.getTitle()!=null){
                  strItem+="              <p><strong>"+assessmentFormItem.getTitle()+"<br/>";
              }
              if(assessmentFormItem.getTitle_L2()!=null){
                  strItem+="          <em>"+assessmentFormItem.getTitle_L2()+"</em></strong></p>";
              }else if(assessmentFormItem.getTitle()!=null){
                  strItem+="          </strong></p>";
              }
              strItem+="</center></td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
              strItem+="  </td>";
              strItem+="  <td width='50%'>";
              strItem+="<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
               if(assessmentFormItem.getItemPoin1()!=null){
                  strItem+="              <p><strong>"+assessmentFormItem.getItemPoin1()+"<br/>";
              }
              if(assessmentFormItem.getItemPoin2()!=null){
                  strItem+="          <em>"+assessmentFormItem.getItemPoin2()+"</em></strong></p>";
              }else if(assessmentFormItem.getItemPoin1()!=null){
                  strItem+="          </strong></p>";
              }
              strItem+="</center></td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
              strItem+="  </td>";
              strItem+="</tr>";
              strItem+="</table>";
             // System.out.println(strItem);
        return strItem;
    }
    
    private static String createItemType2ColNoText(AssessmentFormItem assessmentFormItem, Appraisal appraisal){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
              strItem+="<tr>";
              //untuk item yang disembunyikan 
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID]+"' type='hidden' value='"+appraisal.getOID()+"'>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID]+"' type='hidden' value='"+assessmentFormItem.getOID()+"'>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APP_MAIN_ID]+"' type='hidden' value='"+appraisal.getAppMainId()+"'>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' type='hidden' value=''>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' type='hidden' value=''>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+"' type='hidden' value=''>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+"' type='hidden' value=''>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+"' type='hidden' value=''>";
              
              strItem+="  <td width='50%' height='100%'>";
              strItem+="      <table width='100%' height='100%' class='pageForm'>";
              strItem+="    <tr>";
              strItem+="      <td width='9%' height='100%' align='left' valign='top'><strong>"+assessmentFormItem.getNumber()+".</strong></td>";
              strItem+="      <td width='91%' height='100%' align='left' valign='top'>";
              if(assessmentFormItem.getTitle().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getTitle()+"<br/>";
              }
              if(assessmentFormItem.getTitle_L2().length()>0){
                  strItem+="          <em>"+assessmentFormItem.getTitle_L2()+"</em></strong></p>";
              }else if(assessmentFormItem.getTitle().length()>0){
                  strItem+="          </strong></p>";
              }
              
              if(assessmentFormItem.getItemPoin1().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getItemPoin1()+"<br/>";
              }
              if(assessmentFormItem.getItemPoin2().length()>0){
                  strItem+="          <em>"+assessmentFormItem.getItemPoin2()+"</em></strong></p>";
              }else if(assessmentFormItem.getItemPoin1().length()>0){
                  strItem+="          </strong></p>";
              }
              int hInput = 6;
              if(assessmentFormItem.getItemPoin3().length()>0){
                  hInput = 3;
                  if(assessmentFormItem.getItemPoin5().length()>0){
                      hInput = 2;
                  }
              }
              strItem+="           <textarea name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+"' cols='50' rows='"+hInput+"' class='formElemen'>"+((appraisal.getAnswer_1()!=null)?appraisal.getAnswer_1():"")+"</textarea>";
              
              if(assessmentFormItem.getItemPoin3().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getItemPoin3()+"<br/>";
              }
              if(assessmentFormItem.getItemPoin4().length()>0){
                  strItem+="          <em>"+assessmentFormItem.getItemPoin4()+"</em></strong></p>";
              }else if(assessmentFormItem.getItemPoin3().length()>0){
                  strItem+="          </strong></p>";
              }
              
              if(assessmentFormItem.getItemPoin3().length()>0){
                  strItem+="           <textarea name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+"' cols='50' rows='"+hInput+"' class='formElemen'>"+((appraisal.getAnswer_1()!=null)?appraisal.getAnswer_2():"")+"</textarea>";
              }
              
              if(assessmentFormItem.getItemPoin5().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getItemPoin5()+"<br/>";
              }
              if(assessmentFormItem.getItemPoin6().length()>0){
                  strItem+="          <em>"+assessmentFormItem.getItemPoin6()+"</em></strong></p>";
              }else if(assessmentFormItem.getItemPoin5().length()>0){
                  strItem+="          </strong></p>";
              }
              
              if(assessmentFormItem.getItemPoin5().length()>0){
                  strItem+="           <textarea name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+"' cols='50' rows='"+hInput+"' class='formElemen'>"+((appraisal.getAnswer_1()!=null)?appraisal.getAnswer_3():"")+"</textarea>";
              }
              
              strItem+="               </td>";
              strItem+="    </tr>";
              
              strItem+="  </table></td>";
              strItem+="  <td width='50%' height='100%' align='right' valign='top'>";
              strItem+="      <table width='100%' height='100%' class='pageForm'>";
              strItem+="        <tr>";
              strItem+="              <td width='82%' height='100%'>&nbsp;</td>";
              strItem+="              <td width='18%' align='right' valign='top'>";
              strItem+="              <table width='58' height='42' class='pageInput'>";
              strItem+="                <tr>";
              strItem+="                    <td width='70'>";
              strItem+=" <input name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+"' size=7 type='text' value='"+appraisal.getRating()+"'>";
              strItem+="                    </td>";
              strItem+="                </tr>";
              strItem+="              </table>";
              strItem+="              </td>";
              strItem+="        </tr>";
              strItem+="        <tr>";
              strItem+="            <td>";
              strItem+="           <textarea name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' cols='50' rows='6' class='formElemen'>"+((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")+"</textarea>";
              strItem+="            </td>";
              strItem+="        </tr>";
              strItem+="    </table>";
              strItem+="  </td>";
              strItem+=" </tr>";
              strItem+="</table>";
             // System.out.println(strItem);
        return strItem;
    }
    
    private static String createItemTypeSelecHeader(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr><td align='left' width='50%'>";
              if(assessmentFormItem.getTitle().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getTitle()+"<br>";
              }
              if(assessmentFormItem.getTitle_L2().length()>0){
                  strItem+="          <em>("+assessmentFormItem.getTitle_L2()+")</em></strong></p>";
              }else if(assessmentFormItem.getTitle().length()>0){
                  strItem+="          </strong></p>";
              }
              strItem+="</td><td align='right' width='50%'>";
              if(assessmentFormItem.getItemPoin1().length()>0){
                  strItem+="              <p><strong>"+assessmentFormItem.getItemPoin1()+"<br>";
              }
              if(assessmentFormItem.getItemPoin2().length()>0){
                  strItem+="          <em>("+assessmentFormItem.getItemPoin2()+")</em></strong></p>";
              }else if(assessmentFormItem.getItemPoin1().length()>0){
                  strItem+="          </strong></p>";
              }
              strItem+="</td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
             
             // System.out.println(strItem);
        return strItem;
    }
    
    private static String createItemTypeSelectA(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0'> " +
                "<tr><td align='left' width='10%'>";
              strItem+="[  ]";
              strItem+="</td><td align='left' width='80%'>";
              if(assessmentFormItem.getTitle().length()>0){
                  strItem+="              <p>"+assessmentFormItem.getTitle()+"<br>";
              }
              if(assessmentFormItem.getTitle_L2().length()>0){
                  strItem+="          <em>"+assessmentFormItem.getTitle_L2()+"</em></p>";
              }else if(assessmentFormItem.getTitle().length()>0){
                  strItem+="          </p>";
              }
              
              strItem+="</td><td align='right' width='10%'>";
              strItem+="[  ]";
              strItem+="</td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
             
             // System.out.println(strItem);
        return strItem;
    }
    
     private static String createItemTypeSelectB(AssessmentFormItem assessmentFormItem){
        String strItem="<table width='100%' border='0' cellspacing='0' cellpadding='0'> " +
                "<tr><td align='left' width='10%'>";
              strItem+="[  ]";
              strItem+="</td><td align='left' width='60%'>";
              if(assessmentFormItem.getTitle().length()>0){
                  strItem+="              <p><b>"+assessmentFormItem.getTitle()+"<b><br>";
              }
              if(assessmentFormItem.getTitle_L2().length()>0){
                  strItem+="          <i>"+assessmentFormItem.getTitle_L2()+"</i></p>";
              }else if(assessmentFormItem.getTitle().length()>0){
                  strItem+="          </p>";
              }
              
              strItem+="</td><td align='left' width='20%'>";
              if(assessmentFormItem.getItemPoin1().length()>0){
                  strItem+="              <p><b>"+assessmentFormItem.getItemPoin1()+"<b></p>";
              }              
              strItem+="</td><td align='right' width='10%'>";
              strItem+="[  ]";
              strItem+="</td></tr>";
              for(int i=1;i<assessmentFormItem.getHeight();i++){
                  strItem+=" <tr>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                      strItem+=" <td >";
                      strItem+="<br/>";
                      strItem+=" </td>";
                  strItem+=" </tr>";
              }
              strItem+="</table>";
             
             // System.out.println(strItem);
        return strItem;
    }
    
    public static String createPage(long mainOid,int page,long appMainOid){
        System.out.println("::::::::::: CREATE PAGE "+page);
        Vector vSection = new Vector(1,1);
        Vector vItem = new Vector(1,1);
        vSection=SessAssessmentFormSection.getSections(page, mainOid);
        
        Hashtable hAppraisal = new Hashtable();
        hAppraisal = SessAppraisal.listAppraisal(appMainOid);
        
        String strPage = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        if(vSection.size()>0){
            for(int k=0;k<vSection.size();k++){
                AssessmentFormSection assSection = new AssessmentFormSection();
                assSection = (AssessmentFormSection)vSection.get(k);
                if(assSection.getPage()==page){
                    strPage+="<tr><td width='100%'>";
                    strPage+=createFormSection(assSection);
                    strPage+="</td>";
                }
                Vector vItems = new Vector(1,1);
                vItems = SessAssessmentFormItem.listItem(assSection.getOID(), page);
                for(int l=0;l<vItems.size();l++){
                    AssessmentFormItem assItem = new AssessmentFormItem();
                    assItem = (AssessmentFormItem)vItems.get(l);
                    if(assItem.getPage()==page){
                        Appraisal appraisal = new Appraisal();
                   //     appraisal = (Appraisal)vAppraisal.get(l);
                        strPage+="<tr><td width='100%'>";
                       // System.out.println("KE- "+i);;
                        switch(assItem.getType()){
                            case PstAssessmentFormItem.ITEM_TYPE_SPACE : 
                                strPage+=createItemTypeSpace(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT : 
                                strPage+=createItemType2ColNoText(assItem,appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER : 
                                strPage+=createItemType2ColHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_TEXT : 
                                strPage+=createItemTypeText(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER : 
                                strPage+=createItemTypeSelecHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE : 
                                strPage+=createItemTypeSelectA(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE : 
                                strPage+=createItemTypeSelectB(assItem);
                                break;
                        }
                        strPage+="</td>";
                    }
                }
            }
        }
        
        strPage+="</table>";
        //System.out.println(strPage);
        if(vSection.size()>0||vItem.size()>0){
            return strPage;
        }
        return "";
    }
}
