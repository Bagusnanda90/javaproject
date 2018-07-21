/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.gui.jsp;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisal;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Evaluation;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstEvaluation;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.session.employee.appraisal.SessAppraisal;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormItem;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormSection;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.harisma.form.employee.appraisal.FrmAppraisal;
import com.dimata.harisma.form.employee.assessment.FrmAssessmentFormItem;
import com.dimata.util.Formater;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class ControlFormAppraisal {

    private static int SESS_LANGUAGE = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;
    public static String HTML_TD_ITEM_1TD = "<td width='30px'>&nbsp;</td>";
    public static String HTML_TD_ITEM_2TD = "<td width='30'>&nbsp;</td><td width='100'>&nbsp;</td>";
    public static String[][] SYSTEM_VOCAB = {{"Rangkuman", "Paragraf", "Bobot", "Total","Nilai"}, {"Summary", "Section", "Weight", "Total","Point"}};
    public static final int VOCAB_SUMMARY = 0;
    public static final int VOCAB_SECTION = 1;
    public static final int VOCAB_WEIGHT = 2;
    public static final int VOCAB_TOTAL = 3;
    public static final int VOCAB_POINT = 4;
    private static String mainDescription = "";

    public static String createFormMain(AssessmentFormMain assessmentFormMain, AppraisalMain appraisalMain) {
        String strForm = "";
        strForm = createMainHeader(assessmentFormMain) + "<br>" + createMainData(assessmentFormMain, appraisalMain)
                + "<br>" + createMainDesc() + "<br>"/*+createMainRating()*/;
        return strForm;
    }

    public static String createFormSection(AssessmentFormSection assessmentFormSection) {
        String strForm = "";
        switch (assessmentFormSection.getType()) {
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

    public static String createFormItem(AssessmentFormItem assessmentFormItem) {
        String strForm = "";

        return strForm;
    }

    //Main
    private static String createMainHeader(AssessmentFormMain assessmentFormMain) {
        String strItem = "";
        String approot = "";
        try {
            approot = PstSystemProperty.getValueByName("IMG_ROOT");
        } catch (Exception ex) {
        }
        if (assessmentFormMain == null) {
            return "assessmentFormMain is null";
        }
        strItem += "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"
                //     +"<tr><td align='center' valign='top'><img src='"+approot+"/logo.jpg'></td></tr>"
                + "<tr><td align='center' valign='bottom'><font size='4px'><b>"
                + assessmentFormMain.getTitle()
                + "</b></font></td></tr>";
        if (assessmentFormMain.getTitle_L2() != null && assessmentFormMain.getTitle_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                    + assessmentFormMain.getTitle_L2()
                    + "</font></td></tr>";
        }
        if (assessmentFormMain.getSubtitle() != null && assessmentFormMain.getSubtitle().length() > 0) {
            strItem += "<tr><td align='center' valign='bottom'><font size='4px'><b><i>"
                    + assessmentFormMain.getSubtitle()
                    + "</i></b></font></td></tr>";
        }
        if (assessmentFormMain.getSubtitle_L2() != null && assessmentFormMain.getSubtitle_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                    + assessmentFormMain.getSubtitle_L2()
                    + "</i></font></td></tr>";
        }
        strItem += "</table>";
        return strItem;
    }

    private static String createMainData(AssessmentFormMain assessmentFormMain, AppraisalMain objAppMain) {
        String strItem = "";
        String strMainData = assessmentFormMain.getMainData();
        boolean isDataAvailable = false;

        Employee employee = new Employee();
        Employee assessor = new Employee();
        Department empDep = new Department();
        Department assDep = new Department();
        Position empPos = new Position();
        Position assPos = new Position();
        Level empLevel = new Level();
        Level assLevel = new Level();

        try {
            employee = PstEmployee.fetchExc(objAppMain.getEmployeeId());
            assessor = PstEmployee.fetchExc(objAppMain.getAssesorId());
            empDep = PstDepartment.fetchExc(employee.getDepartmentId());
            //     assDep = PstDepartment.fetchExc(assessor.getDepartmentId());
            empPos = PstPosition.fetchExc(employee.getPositionId());
            assPos = PstPosition.fetchExc(assessor.getPositionId());
            empLevel = PstLevel.fetchExc(employee.getLevelId());
            // assLevel = PstLevel.fetchExc(assessor.getLevelId()); 

        } catch (Exception ex) {
        }

        Vector vAppData = new Vector(1, 1);

        vAppData.add(employee.getFullName());
        vAppData.add(empPos.getPosition());
        vAppData.add(empDep.getDepartment());
        vAppData.add(Formater.formatDate(objAppMain.getDateAssumedPosition(), "dd MMMM yyyy"));
        vAppData.add(Formater.formatDate(objAppMain.getDateJoinedHotel(), "dd MMMM yyyy"));
        vAppData.add(Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"));
        vAppData.add(assessor.getFullName());
        vAppData.add(assPos.getPosition());
        vAppData.add(Formater.formatDate(objAppMain.getDateOfAssessment(), "dd MMMM yyyy"));
        vAppData.add(Formater.formatDate(objAppMain.getDateOfLastAssessment(), "dd MMMM yyyy"));
        vAppData.add(Formater.formatDate(objAppMain.getDateOfNextAssessment(), "dd MMMM yyyy"));

        if (PstAssessmentFormMain.fieldFormTypes != null && PstAssessmentFormMain.fieldFormTypes.length > 0) {
            for (int i = 0; i < PstAssessmentFormMain.fieldFormTypes.length; i++) {
                boolean isUsed = PstAssessmentFormMain.cekFormUsed(strMainData, i);
                // System.out.println(i+"=="+isUsed);
                String strVal = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_FIRST];
                String strValL2 = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_OTHER];

                if (isUsed) {
                    String strValue = (String) vAppData.get(i);
                    isDataAvailable = true;
                    strItem += "  <tr>";
                    strItem += "    <td width='35%' height='20' align='left' valign='top'><strong>" + strVal + "</strong>";
                    strItem += "        <br>" + strValL2 + "</td>";
                    strItem += "    <td width='2%' height='20' align='center' valign='top'><strong>:</strong></td>";
                    strItem += "        <td width='63%' height='20' align='left' valign='top'><strong>" + strValue + "</strong></td>";
                    strItem += "  </tr>";
                }
            }
            if (isDataAvailable) {
                strItem = "<table width='100%' bgcolor='000000' border='0' cellspacing='1' cellpadding='1'><tr><td><table width='100%' border='0' cellspacing='1' cellpadding='1' bgcolor='bab4b4'>"
                        + strItem;
                strItem += "</table></td></tr></table>";
            }
        }

        return strItem;
    }

    private static String createMainDesc() {
        String strItem = "";
        if (mainDescription == null || mainDescription.length() < 1) {
            strItem += "<p><strong><u>Preparation<em><br>"
                    + "(Persiapan)</em></u></strong></p>"
                    + "<ul><li type='disc'> Use this form only for those positions indentified in the assessment guide."
                    + "<br /> <i> (Gunakan Formulir ini hanya untuk jabatan yang tercantum dalam petunjuk penilain) </i>"
                    + "</li>"
                    + "<li type='disc'>Carefully read Human Resources P&P-66 - prior to assessment."
                    + "<br /> <i> (Bacalah dengan cermat peraturan dan prosedur Human Resources P&P-66- sebelum penilaian dilakukan) </i> </li></ul>"
                    + "<p> <b> <u> Assessor: <br>"
                    + "  <i>(Penilain/Penguji)</i></u></b></p> "
                    + " <p>Minimum <b><i>two weeks</i></b> before the scheduled review, you should:"
                    + "<br /> <i> Minimal 2 minggu sebelum penilaian dijadwalkan, anda harus: </i> </p>"
                    + " <ul>"
                    + "   <li>Explain the performance assessment process to the Employee assessed."
                    + "     <br /> "
                    + "     <i> (Terangkan proses penilaian kepada Employee yang akan dinilai) </i>     </li>"
                    + "   <li>Provide a performance assessment from to the Employee and shedule date &amp; time for the interview."
                    + "   	<br />"
                    + "    <em>(Sediakan formulir penilaian kepada Employee &amp; Membuat jadwal untuk penilaian) </em></li>"
                    + "   <li>After receiving the completed form, the assessor considers the information received and objectively completes "
                    + "   <br />"
                    + "   the assessor's column. The assessor collects all supporting data required to carry put the assessment."
                    + "   <br />"
                    + "   <em>(Setelah menerima formulir yang telah diisi secara lengkap, penilai mempertimbangkan informasi yang "
                    + "   	<br />"
                    + "   	diterima dan melengkapi kolom penguji secara objectif. Penilai mengumpulkan seluruh data yang diperlukan"
                    + "	<br /> untuk melakukan penilaian.)</em></li>"
                    + " </ul>"
                    + " <blockquote>"
                    + "   <p><strong>At interview:</strong><br />"
                    + "   	<em>(Saat Wawancara)</em></p>"
                    + " </blockquote>"
                    + " "
                    + " <ul>"
                    + "   <li>Comments and ratings are agreed and recorded on Assessor's Form. Agree on an Action Plan together with the"
                    + "   <br />Employee. Forward this form duly completed in ink, with appropriate signatures to the Human Resources"
                    + "   <br /> office."
                    + "   <em><br /> "
                    + "   (Komentar dan tingkat penilaian diakui dan dicatat dalam formulir penilaian. Rencana kegiatan disetujui juga "
                    + "   <br /> "
                    + "   oleh band memeber. Formulir diisi dengan <u>Pulpen</u> dan dengan tanda tangan yang jelas untuk dikumpulkan ke"
                    + "   <br /> "
                    + "   Human Resources Departemen)</em></li>"
                    + "   <li>If an agreement is not reached, the supervisor's rating prevails with an explanation as appropriate."
                    + "     <em><br /> "
                    + "   (Apabila tidak tercapai kesepakatan, rangking ditentukan oleh supervisor dengan penjelasan yang beralasan)</em></li>"
                    + "   <li>Based on Employee's and Assessor's comments, a constructive discussion should take place resulting in an"
                    + "   <br /> individual training and development plan. "
                    + "   <br /> "
                    + "   <em>(Berdasarkan komentar dari Employee dan penilai, akan diambil langkah-langkah untuk pengembangan"
                    + "   <br /> "
                    + "   dan training individu yang diperlukan)</em></li>"
                    + " </ul>";
        } else {
            strItem = mainDescription;
        }
        return strItem;
    }

    private static String createMainRating() {
        return createMainRating(0);
        /*   String strItem = "";
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
         return strItem;*/
    }

    private static String createMainRating(long evalTypeIdx) {
        if (evalTypeIdx < Evaluation.EVAL_TYPE_PERFORMANCE_RANGE && evalTypeIdx > Evaluation.EVAL_TYPE_PREDICAT) {
            return "";
        }
        String strItem = "";
        Vector vRating = new Vector();
        String sWhere = evalTypeIdx == 0 ? "" : PstEvaluation.fieldNames[PstEvaluation.FLD_EVAL_TYPE] + "=" + evalTypeIdx;
        vRating = PstEvaluation.list(0, 0, sWhere, PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_POINT] + "," + PstEvaluation.fieldNames[PstEvaluation.FLD_MAX_PERCENTAGE] + " DESC");
        if (vRating.size() > 0) {
            //*strItem += "<p><strong><u>Assessment Ratings<em><br>"
            //        +"(Tingkat Penilaian)</em></u></strong></p>";
            strItem += "<table width='100%' class='pagelink2' border='0' cellspacing='0' cellpadding='0'><tr><td><p ><strong><u>" + Evaluation.EVAL_TYPE[getSESS_LANGUAGE()][(int) evalTypeIdx] + "</u></strong></p></td></tr><tr><td><table width='100%' border='0' cellspacing='1' cellpadding='2'>";
            int colSize = 100 / vRating.size();

            double maxValPrev = 0;
            double maxValCurr = 0;
            strItem += "  <tr bgcolor='ffffff'>";
            for (int i = 0; i < vRating.size(); i++) {
                Evaluation evCurr = (Evaluation) vRating.get(i);
                Evaluation evPrevious = new Evaluation();
                if (i - 1 >= 0 /*i+1<vRating.size()*/) {
                    evPrevious = (Evaluation) vRating.get(i - 1);
                }
                maxValCurr = evCurr.getMaxPoint();
                maxValPrev = evPrevious.getMaxPoint();
                strItem += "    <td width='" + colSize + "%' align='center' valign='top'>";
                strItem += "                <b>";
                if (maxValCurr > 0 && ((maxValPrev + 1) < maxValCurr)) {
                    if (maxValPrev > 0) {
                        strItem += "                <br><br><i>" + (maxValPrev + 1) + " - " + maxValCurr + "</i>";
                    } else {
                        strItem += "                <br><br><i> < " + (maxValCurr + 1) + "</i>";
                    }
                } else {
                    strItem += "                <br><br><i>" + maxValCurr + " </i>";
                }
                strItem += "                </b>";
                strItem += "    </td>";
            }
            strItem += "  </tr>";

            strItem += "  <tr bgcolor='ffffff'>";
            for (int i = 0; i < vRating.size(); i++) {
                Evaluation evCurr = (Evaluation) vRating.get(i);
                Evaluation evPrevious = new Evaluation();
                if (i - 1 >= 0 /*i+1<vRating.size()*/) {
                    evPrevious = (Evaluation) vRating.get(i - 1);
                }
                maxValCurr = evCurr.getMaxPercentage();
                maxValPrev = evPrevious.getMaxPercentage();
                String strTitle = evCurr.getName();
                int posTitleDiv = strTitle.indexOf("(");
                strItem += "    <td width='" + colSize + "%' align='center' valign='top'>";
                strItem += "                <b>";
                if (posTitleDiv > -1) {
                    strItem += "                    " + strTitle.substring(0, posTitleDiv);
                    strItem += "                    <br><i>" + strTitle.substring(posTitleDiv, strTitle.length()) + "</i>";
                } else {
                    strItem += "                    " + strTitle + "<br>";
                }
                if (maxValCurr > 0) {
                    if (maxValPrev > 0) {
                        strItem += "                <br><br><i>" + (maxValPrev + 1) + "% - " + maxValCurr + "%</i>";
                    } else {
                        strItem += "                <br><br><i> < " + (maxValCurr + 1) + "%</i>";
                    }
                }
                strItem += "                </b>";
                strItem += "    </td>";
            }
            strItem += "  </tr>";
            strItem += "  <tr bgcolor='ffffff'>";
            for (int ij = 0; ij < vRating.size(); ij++) {
                Evaluation evCurr = (Evaluation) vRating.get(ij);
                Evaluation evPrevious = new Evaluation();
                if (ij - 1 >= 0 /*+1<vRating.size()*/) {
                    evPrevious = (Evaluation) vRating.get(ij - 1);
                }
                maxValCurr = evCurr.getMaxPercentage();
                maxValPrev = evPrevious.getMaxPercentage();
                String strDesc = evCurr.getDesription();
                int posDescDiv = strDesc.indexOf("(");
                strItem += "    <td width='" + colSize + "%' align='justify' valign='top'>";
                if (posDescDiv > -1) {
                    strItem += "                    " + strDesc.substring(0, posDescDiv);
                    strItem += "                    <br><br><i>" + strDesc.substring(posDescDiv, strDesc.length()) + "</i>";
                } else {
                    strItem += "                    " + strDesc + "<br><br>";
                }
                strItem += "    </td>";
            }
            strItem += "  </tr>";
            strItem += "</table></td></tr></table>";
        }
        return strItem;
    }

    /**
     * Create Summary rating from sections with point weight by Kartika 20150223
     *
     * @return
     */
    private static String createSummaryRating(AppraisalMain appMain,  AssessmentFormSection assessmentFormSection) {
        if (assessmentFormSection == null || assessmentFormSection.getAssFormMainId() == 0) {
            return "";
        }
        String strItem = "";
        Vector vSections = new Vector();
        String sWhere = PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ASS_FORM_MAIN_ID] + "=" + assessmentFormSection.getAssFormMainId();
        vSections = PstAssessmentFormSection.list(0, 0, sWhere, PstAssessmentFormSection.fieldNames[PstAssessmentFormSection.FLD_ORDER_NUMBER] + " ASC ");
        if (vSections.size() > 0) {

            strItem += "<table width='100%' class='pagelink2' border='0' cellspacing='0' cellpadding='0'><tr><td><p ><strong><u>"
                    + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_SUMMARY] + "</u></strong></p></td></tr><tr><td><table width='100%' border='0' cellspacing='1' cellpadding='2'>";
            strItem += "  <tr bgcolor='ffffff'>";
            strItem += "    <td width='" + 55 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_SECTION] + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_WEIGHT] + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_POINT] + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_TOTAL] + "</b></td>";
            strItem += "  </tr>";
            float totalWeight = 0;
            float totalRating = 0;
            for (int i = 0; i < vSections.size(); i++) {
                AssessmentFormSection section = (AssessmentFormSection) vSections.get(i);
                if (section.getWeightPoint() > 0.0) {
                    String where = PstAppraisal.fieldNames[PstAppraisal.FLD_APP_MAIN_ID] +"="+appMain.getOID()+" AND "
                    + PstAssessmentFormItem.fieldNames[PstAssessmentFormItem.FLD_ASS_FORM_SECTION_ID]+"="+ section.getOID(); 
                     float totalRatingSection = PstAppraisal.getSummaryRating(where);
                     totalRating = totalRating + Math.round((section.getWeightPoint() * totalRatingSection )/100);
                    strItem += "  <tr bgcolor='ffffff'>";
                    strItem += "    <td width='" + 55 + "%' align='center' valign='top'>" + section.getSection() + "<br>" + section.getDescription() + "</td>";
                    strItem += "    <td width='" + 15 + "%' align='center' valign='top'>" + section.getWeightPoint() + "</td>";
                    strItem += "    <td width='" + 15 + "%' align='center' valign='top'>" + totalRatingSection + "</td>";
                    strItem += "    <td width='" + 15 + "%' align='center' valign='top'>" + Math.round((section.getWeightPoint() * totalRatingSection )/100) + "</td>";
                    strItem += "  </tr>";
                    totalWeight = totalWeight + section.getWeightPoint();
                }
            }
            strItem += "  <tr bgcolor='ffffff'>";
            strItem += "    <td width='" + 55 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_TOTAL] + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + totalWeight + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + "" + "</b></td>";
            strItem += "    <td width='" + 15 + "%' align='center' valign='top'><b>" + totalRating + "</b></td>";
            strItem += "  </tr>";
            
            Evaluation eval=PstEvaluation.getByMaxPoint(totalRating,  Evaluation.EVAL_TYPE_PREDICAT );
            
            strItem += "</table></td></tr>";
            strItem += "<tr><td align='right'><font size='4'><b> PREDICATE </b></font>  : <font size='4'><b>" + (eval!=null ? eval.getName(): "NO PREDICATE FOUND, PLEASE CHECK EVALUATION CRETERIA ") + "</b></font></td></tr>";
            strItem +="</table>";
        }
        return strItem;
    }

    //Section
    private static String createSectionTypeA(AssessmentFormSection assessmentFormSection) {
        String strItem = "";
        strItem += "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"
                + "<tr><td align='center' valign='bottom'><font size='3px'><b><u>"
                + assessmentFormSection.getSection()
                + "</u></b></font></td></tr>";
        if (assessmentFormSection.getSection_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                    + assessmentFormSection.getSection_L2()
                    + "</font></td></tr>";
        }
        if (assessmentFormSection.getDescription().length() > 0) {
            strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><i>"
                    + assessmentFormSection.getDescription()
                    + "</i></b></font></td></tr>";
        }
        if (assessmentFormSection.getDescription_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                    + assessmentFormSection.getDescription_L2()
                    + "</i></font></td></tr>";
        }
        strItem += "</table>";
        return strItem;
    }

    private static String createSectionTypeB(AssessmentFormSection assessmentFormSection) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='100%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'>";
        strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><u>"
                + assessmentFormSection.getSection()
                + "</u></b></font></td></tr>";
        if (assessmentFormSection.getSection_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                    + assessmentFormSection.getSection_L2()
                    + "</font></td></tr>";
        }
        if (assessmentFormSection.getDescription().length() > 0) {
            strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><i>"
                    + assessmentFormSection.getDescription()
                    + "</i></b></font></td></tr>";
        }
        if (assessmentFormSection.getDescription_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                    + assessmentFormSection.getDescription_L2()
                    + "</i></font></td></tr>";
        }
        strItem += "</table></td></tr></table>";
        return strItem;
    }

    //Item
    private static String createItemTypeSpace(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='1' cellpadding='1'>";
        for (int i = 0; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<tr><td width='100%'>" + " " + "</td></tr>";
        }
        strItem += "</table>";
        return strItem;
    }

    private static String createItemTypeText(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr><td>";
        strItem += "<div align='justify'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p>" + assessmentFormItem.getTitle() + "";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </p>";
        }
        strItem += "</div></td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeTextBold(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr><td>";
        strItem += "<div align='justify'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</div></td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColHeader(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "<br/>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";
        strItem += "  </td>";
        strItem += "  <td width='50%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin1() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin2() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColNoText(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        int maxText = 120;
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "      <table width='100%' height='100%' class='pageForm'>";
        strItem += "    <tr>";
        strItem += "      <td width='9%' height='100%' align='left' valign='top'><strong>" + assessmentFormItem.getNumber() + ".</strong></td>";
        strItem += "      <td width='91%' height='100%' align='left' valign='top'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "<br/>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }

        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin1() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin2() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "          </strong></p>";
        }

        //Input val 1
        int hInputEmp = 6;
        int hInputAss = 6;
        int pengali = 1;
        if (assessmentFormItem.getItemPoin3().length() > 0) {
            hInputEmp = 4;
            hInputAss = 12;
            pengali = 2;
            if (assessmentFormItem.getItemPoin5().length() > 0) {
                pengali = 3;
                hInputEmp = 2;
                hInputAss = 18;
            }
        }
        strItem += "<textarea onchange='cmdSetNeedSaving()' onKeyPress=check_length(this," + maxText + "); onKeyDown=check_length(this," + maxText + "); name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='" + hInputEmp + "' class='formElemen'>" + ((appraisal.getEmpComment() != null) ? appraisal.getEmpComment() : "") + "</textarea>";

        if (assessmentFormItem.getItemPoin3().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin3() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin4().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin4() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin3().length() > 0) {
            strItem += "          </strong></p>";
        }
        if (assessmentFormItem.getItemPoin3().length() > 0) {
            strItem += "<textarea onchange='cmdSetNeedSaving()' onKeyPress=check_length(this," + maxText + "); onKeyDown=check_length(this," + maxText + ");  name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1] + assessmentFormItem.getOID() + "' cols='50' rows='" + hInputEmp + "' class='formElemen'>" + ((appraisal.getAnswer_1() != null) ? appraisal.getAnswer_1() : "") + "</textarea>";
        }


        if (assessmentFormItem.getItemPoin5().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin5() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin6().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin6() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin5().length() > 0) {
            strItem += "          </strong></p>";
        }
        if (assessmentFormItem.getItemPoin5().length() > 0) {
            strItem += "<textarea onchange='cmdSetNeedSaving()' onKeyPress=check_length(this," + maxText + "); onKeyDown=check_length(this," + maxText + ");  name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2] + assessmentFormItem.getOID() + "' cols='50' rows='" + hInputEmp + "' class='formElemen'>" + ((appraisal.getAnswer_2() != null) ? appraisal.getAnswer_2() : "") + "</textarea>";
        }

        strItem += "               </td>";
        strItem += "    </tr>";

        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "  </table></td>";
        strItem += "  <td width='50%' height='100%' align='right' valign='top'>";
        strItem += "      <table width='100%' height='100%' class='pageForm'>";
        strItem += "        <tr>";
        strItem += "              <td width='82%' height='100%' align='left' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' onKeyPress=check_length(this," + (pengali * maxText) + "); onKeyDown=check_length(this," + (pengali * maxText) + ");  name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='" + hInputAss + "' class='formElemen'>" + ((appraisal.getAssComment() != null) ? appraisal.getAssComment() : "") + "</textarea>";
        strItem += "              </td>";
        strItem += "              <td width='18%' align='right' valign='top'>";
        strItem += "              <table width='58' height='42' class='pageInput'>";
        strItem += "                <tr>";
        strItem += "                    <td width='70'>";
        strItem += "<input onchange='cmdSetNeedSaving()' type='text' size='5' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getRating() > 0) ? appraisal.getRating() : 0) + "'>";;
        strItem += "                    </td>";
        strItem += "                </tr>";
        strItem += "              </table>";
        strItem += "              </td>";
        strItem += "        </tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "    </table>";
        strItem += "  </td>";
        strItem += " </tr>";
        strItem += "</table>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + "' value='" + ((appraisal.getEmpComment() != null) ? appraisal.getEmpComment() : "") + "'>";
        //     strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+"' value=''>";
        //     strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+"' value=''>";
        //     strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+"' value=''>";
        int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
        if (false && maxNumber == assessmentFormItem.getNumber()) {
            AppraisalMain appMainTemp = new AppraisalMain();
            try {
                appMainTemp = PstAppraisalMain.fetchExc(appraisal.getAppMainId());
            } catch (Exception ex) {
            }
            strItem += "<br><table width='100%' border='0' cellspacing='0' cellpadding='0' >";
            strItem += "<tr>";
            strItem += "<td alight='left'>";
            strItem += "<font size='4'><b>PERFORMANCE OF " + "" + " </b></font> <font size='4'><b>" + appMainTemp.getTotalScore() + " / " + appMainTemp.getTotalAssessment() + " = </b></font>";
            strItem += "</td>";
            strItem += "<td alight='right'>";
            strItem += "              <table width='70' height='42' class='pageInput'>";
            strItem += "                <tr>";
            strItem += "                    <td width='70' alight='right'>";
            strItem += "<font size='4'>" + appMainTemp.getScoreAverage() + " %</font>";
            strItem += "                    </td>";
            strItem += "                </tr>";
            strItem += "              </table>";
            strItem += "</td>";
            strItem += "<td>";
            strItem += "(average %)";
            strItem += "</td>";
            strItem += "</tr>";
            strItem += "</table>";
        }
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeSelecHeader(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr><td align='left' width='50%'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "<br>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</td><td align='right' width='50%'>";
        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin1() + "<br>";
        }
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin2() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeSelectA(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> "
                + "<tr><td align='left' valign='top' width='10%'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='checkbox' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' value='1' " + ((appraisal.getEmpComment() != null) ? (appraisal.getEmpComment().equals("1") ? "checked" : "") : "") + "/>]";
        strItem += "</td><td align='left' valign='top' width='80%'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p>" + assessmentFormItem.getTitle() + "<br>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </p>";
        }

        strItem += "</td><td align='right' valign='top' width='10%'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='checkbox' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' value='1' " + ((appraisal.getAssComment() != null) ? (appraisal.getAssComment().equals("1") ? "checked" : "") : "") + " />]";
        strItem += "</td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' value='"+((appraisal.getEmpComment()!=null)?appraisal.getEmpComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value='"+((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")+"'>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //  strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeSelectB(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> "
                + "<tr><td align='left' valign='top' width='10%'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='checkbox' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' value='1' " + ((appraisal.getEmpComment() != null) ? (appraisal.getEmpComment().equals("1") ? "checked" : "") : "") + "/>]";
        strItem += "</td><td align='left' valign='top' width='60%'>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><b>" + assessmentFormItem.getTitle() + "<b><br>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <i>" + assessmentFormItem.getTitle_L2() + "</i></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </p>";
        }

        strItem += "</td><td align='left' width='20%'>";
        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><b>" + assessmentFormItem.getItemPoin1() + "<b></p>";
        }
        strItem += "</td><td align='right' valign='top' width='10%'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='checkbox' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' value='1' " + ((appraisal.getAssComment() != null) ? (appraisal.getAssComment().equals("1") ? "checked" : "") : "") + " />]";
        strItem += "</td></tr>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' value='"+((appraisal.getEmpComment()!=null)?appraisal.getEmpComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value='"+((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColCommentsEmpAss(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "<br/>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";

        strItem += "</table>";
        strItem += "  </td>";
        strItem += "  <td width='50%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin1() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin2() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";
        strItem += "</table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td  align='right' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='12' class='formElemen'>" + ((appraisal.getEmpComment() != null) ? appraisal.getEmpComment() : "") + "</textarea>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br/>";
        }
        strItem += "</td></tr></table>";
        strItem += "  </td>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td  align='right' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='12' class='formElemen'>" + ((appraisal.getAssComment() != null) ? appraisal.getAssComment() : "") + "</textarea>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br/>";
        }
        strItem += "</td></tr></table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' value='"+((appraisal.getEmpComment()!=null)?appraisal.getEmpComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value='"+((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //  strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColCommentsAss(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getTitle() + "<br/>";
        }
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getTitle_L2() + "</em></strong></p>";
        } else if (assessmentFormItem.getTitle().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";
        strItem += "</table>";
        strItem += "  </td>";
        strItem += "  <td width='50%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'><tr><td><center>";
        if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin1() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin2() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin1().length() > 0) {
            strItem += "          </strong></p>";
        }
        strItem += "</center></td></tr>";

        strItem += "</table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "<tr>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td align='right' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='12' class='formElemen'>" + ((appraisal.getEmpComment() != null) ? appraisal.getEmpComment() : "") + "</textarea>";

        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br/>";
        }
        strItem += "</td></tr></table>";
        strItem += "  </td>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td  align='right' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' cols='50' rows='12' class='formElemen'>" + ((appraisal.getAssComment() != null) ? appraisal.getAssComment() : "") + "</textarea>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br/>";
        }
        strItem += "</td></tr></table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' value='"+((appraisal.getEmpComment()!=null)?appraisal.getEmpComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value='"+((appraisal.getAssComment()!=null)?appraisal.getAssComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColCommentsEmp(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "<tr>";
        strItem += "<td>";
        strItem += "<b>" + assessmentFormItem.getTitle() + "</b>";
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "<br><b><i>" + assessmentFormItem.getTitle_L2() + "</i></b>";
        }
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "<tr>";
        strItem += "<td   align='left' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT] + assessmentFormItem.getOID() + "' cols='100' rows='12' class='formElemen'>" + ((appraisal.getEmpComment() != null) ? appraisal.getEmpComment() : "") + "</textarea>";

        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+"' value='"+((appraisal.getEmpComment()!=null)?appraisal.getEmpComment():"")+"'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColCommentsAssessor(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "<tr>";
        strItem += "<td>";
        strItem += "<b>" + assessmentFormItem.getTitle() + "</b>";
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "<br><b><i>" + assessmentFormItem.getTitle_L2() + "</i></b>";
        }
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "<tr>";
        strItem += "<td  align='left' valign='top'>";
        strItem += "<textarea onchange='cmdSetNeedSaving()' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' cols='100' rows='12' class='formElemen'>" + ((appraisal.getAssComment() != null) ? appraisal.getAssComment() : "") + "</textarea>";

        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //  strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeInput(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "<tr>";
        strItem += "<td width='5%' align='left' valign='top'>";
        strItem += "&#164;";
        strItem += "</td>";
        strItem += "<td width='30%' align='left' valign='top'>";
        strItem += assessmentFormItem.getTitle();
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "<br><i>" + assessmentFormItem.getTitle_L2() + "</i>";
        }
        strItem += "</td>";
        strItem += "<td width='65%' align='left' valign='top'>";
        strItem += "<input onchange='cmdSetNeedSaving()' type='text' size='50' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getAssComment() != null) ? appraisal.getAssComment() : "") + "'>";
        //   strItem+="___________________________________________";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //    strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeInputCheckHeader(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "<tr>";
        strItem += "<td width='80%' align='left' valign='top'>";
        for (int i = 0; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br>";
        }
        strItem += "</td>";
        strItem += "<td width='10%' align='center' valign='top'>";
        strItem += "<b>" + assessmentFormItem.getTitle() + "</b>";
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "<br><i>" + assessmentFormItem.getTitle_L2() + "</i>";
        }
        strItem += "</td>";
        strItem += "<td width='10%' align='center' valign='top'>";
        strItem += "<b>" + assessmentFormItem.getItemPoin1() + "</b>";
        if (assessmentFormItem.getItemPoin2().length() > 0) {
            strItem += "<br><i>" + assessmentFormItem.getItemPoin2() + "</i>";
        }
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeInputCheck(AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "<tr>";
        strItem += "<td width='80%' align='left' valign='top'>";
        strItem += assessmentFormItem.getTitle();
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "<br><i>" + assessmentFormItem.getTitle_L2() + "</i>";
        }
        for (int i = 0; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br>";
        }

        strItem += "</td>";
        strItem += "<td width='10%' align='center' valign='top'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='radio' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' value='1' " + (appraisal.getAssComment() != null ? (appraisal.getAssComment().equals("1") ? "checked" : "") : "") + " />]";
        strItem += "</td>";
        strItem += "<td width='10%' align='center' valign='top'>";
        strItem += "[<input onClick='cmdSetNeedSaving()' type='radio' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT] + assessmentFormItem.getOID() + "' value='0' " + (appraisal.getAssComment() != null ? (appraisal.getAssComment().equals("1") ? "" : "checked") : "checked") + "/>]";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING]+assessmentFormItem.getOID()+"' value=''>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColWithText(AppraisalMain appMain, AssessmentFormSection assSection, AssessmentFormItem assessmentFormItem, Appraisal appraisal) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='90%' height='100%'>";
        strItem += "    <table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        strItem += "        <tr>";
        strItem += "            <td width='5%' height='100%' align='left' valign='top'>";
        strItem += "            <b>" + assessmentFormItem.getNumber() + ".</b>";
        strItem += "            </td>";
        strItem += "            <td width='95%' height='100%' align='left' valign='top'>";
        strItem += "            <b>" + assessmentFormItem.getTitle() + "</b>";
        if (assessmentFormItem.getTitle_L2().length() > 0) {
            strItem += "            <br><b><i>" + assessmentFormItem.getTitle_L2() + "</i></b>";
        }
        strItem += "            <br></td>";
        strItem += assessmentFormItem.getKpiTarget() != 0.0 ? "<td  width='30' colspan='2' class='pageCover'> Target:<input type='text' readonly size='10'  value='" + assessmentFormItem.getKpiTarget() + "'> <input type='text' readonly size='15'  value='" + assessmentFormItem.getKpiUnit() + "'" + "&nbsp;&nbsp;</td><td  width='50' colspan='2' class='pageCover'>&nbsp;&nbsp;Note:<input type='text' readonly size='40'  value='" + assessmentFormItem.getKpiNote() + "'></td>" : HTML_TD_ITEM_2TD;
        strItem += assessmentFormItem.getWeightPoint() != 0.0 ? "<td width='30' colspan='2' class='pageCover'>Weight:<input type='text' readonly size='5' name='" + FrmAssessmentFormItem.fieldNames[FrmAssessmentFormItem.FRM_FIELD_WEIGHT_POINT] + assessmentFormItem.getOID() + "' value='" + assessmentFormItem.getWeightPoint() + "'></td>" : HTML_TD_ITEM_1TD;

        strItem += "        </tr>";
        int countNumber = 1;
        for (int i = 0; i < 6; i++) {
            String strPoin_1 = "";
            String strPoin_2 = "";

            String strTempPoin = "";
            switch (i) {
                case 0:
                    strTempPoin = assessmentFormItem.getItemPoin1();
                    break;
                case 1:
                    strTempPoin = assessmentFormItem.getItemPoin2();
                    break;
                case 2:
                    strTempPoin = assessmentFormItem.getItemPoin3();
                    break;
                case 3:
                    strTempPoin = assessmentFormItem.getItemPoin4();
                    break;
                case 4:
                    strTempPoin = assessmentFormItem.getItemPoin5();
                    break;
                case 5:
                    strTempPoin = assessmentFormItem.getItemPoin6();
                    break;
            }
            int splitPos = strTempPoin.indexOf("(");
            if (splitPos > -1) {
                strPoin_1 = strTempPoin.substring(0, splitPos);
                strPoin_2 = strTempPoin.substring(splitPos, strTempPoin.length());
            } else {
                strPoin_1 = strTempPoin;
            }
            if (strPoin_1.length() > 0 || !(strPoin_1.equals(""))) {
                strItem += "        <tr>";
                strItem += "            <td width='5%' height='100%' align='left' valign='top'>";
                strItem += "            " + countNumber + ".";
                countNumber = countNumber + 1;
                strItem += "            </td>";
                strItem += "            <td width='95%' height='100%' align='left' valign='top'>";
                strItem += "            " + strPoin_1;
                if (strPoin_2.length() > 0) {
                    strItem += "            <br><i>" + strPoin_2 + "</i>";
                }
                strItem += "            <br><br>";
                strItem += "            </td>";
                strItem += "        </tr>";
            }
        }
        strItem += "    </table>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += "<br/>";
        }
        strItem += "  </td>";
        strItem += "  <td width='10%' height='100%' align='right' valign='top'>";
        strItem += "              <table width='58' height='42' class='pageInput'>";
        strItem += "                <tr>";
        strItem += "                    <td width='70'>";
        strItem += "Realization:<input onchange='cmdSetNeedSaving()' type='text' size='5' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_REALIZATION] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getRealization() > 0) ? appraisal.getRealization() : 0) + "'>" + assessmentFormItem.getEntryUnit();
        strItem += "                    </td>";
        strItem += "                    <td width='70'>";
        strItem += "Evidence:<input onchange='cmdSetNeedSaving()' type='text'  size='50' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EVIDENCE] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getEvidence() != null) ? appraisal.getEvidence() : "") + "'>";;
        strItem += "                    </td>";
        strItem += "                    <td width='70'>";
        strItem += "Point:<input readonly onchange='cmdSetNeedSaving()' type='text' size='5' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_POINT] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getPoint() > 0) ? appraisal.getPoint() : 0) + "'>";;
        strItem += "                    </td>";
        strItem += "                    <td width='70'>";
        strItem += "Point x Weight:<input  readonly onchange='cmdSetNeedSaving()' type='text' size='5' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_RATING] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getRating() > 0) ? appraisal.getRating() : 0) + "'>";;
        strItem += "                    </td>";
        strItem += "                </tr>";
        strItem += "              </table>";
        strItem += "  </td>";
        strItem += " </tr>";
        strItem += "</table>";

        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_APPRAISAL_ID] + assessmentFormItem.getOID() + "' value='" + ((appraisal.getOID() > 0) ? appraisal.getOID() : 0) + "'>";
        strItem += "<input type='hidden' name='" + FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_FORM_ITEM_ID] + assessmentFormItem.getOID() + "' value='" + ((assessmentFormItem.getOID() > 0) ? assessmentFormItem.getOID() : 0) + "'>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_EMP_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ASS_COMMENT]+assessmentFormItem.getOID()+"' value=''>";
        //  strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_1]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_2]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_3]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_4]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_5]+assessmentFormItem.getOID()+"' value=''>";
        //   strItem+="<input type='hidden' name='"+FrmAppraisal.fieldNames[FrmAppraisal.FRM_FIELD_ANSWER_6]+assessmentFormItem.getOID()+"' value=''>";
        int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
        if (false && maxNumber == assessmentFormItem.getNumber()) {

            strItem += "<br><table width='100%' border='0' cellspacing='0' cellpadding='0' >";
            strItem += "<tr>";
            strItem += "<td alight='left'>";
            strItem += "<font size='4'><b> PERFORMANCE %</b></font> (add ratings) <font size='4'><b>" + appMain.getTotalScore() + " / " + appMain.getTotalAssessment() + " = </b></font>";
            strItem += "</td>";
            strItem += "<td alight='right'>";
            strItem += "              <table width='70' height='42' class='pageInput'>";
            strItem += "                <tr>";
            strItem += "                    <td width='70' alight='right'>";
            strItem += "<font size='4'>" + appMain.getScoreAverage() + " %</font>";
            strItem += "                    </td>";
            strItem += "                </tr>";
            strItem += "              </table>";
            strItem += "</td>";
            strItem += "<td>";
            strItem += "(average %)";
            strItem += "</td>";
            strItem += "</tr>";
            strItem += "</table>";
        }
        // System.out.println(strItem);
        return strItem;
    }

    public static String createPage(long mainOid, int page, long appraisalMainOid) {
        System.out.println("::::::::::: CREATE PAGE APPRAISAL " + page);
        Vector vSection = new Vector(1, 1);
        //  Vector vItem = new Vector(1,1);
        vSection = SessAssessmentFormSection.getSections(page, mainOid);
        //   vItem=PstAssessmentFormItem.listItem(mainOid, page);

        AppraisalMain appMain = new AppraisalMain();
        try {
            appMain = PstAppraisalMain.fetchExc(appraisalMainOid);
        } catch (Exception ex) {
        }
        Hashtable hAppraisal = new Hashtable();
        hAppraisal = SessAppraisal.listAppraisal(appraisalMainOid);

        String strPage = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        if (vSection.size() > 0) {
            for (int k = 0; k < vSection.size(); k++) {
                float totalRating =0.0f;
                AssessmentFormSection assSection = new AssessmentFormSection();
                assSection = (AssessmentFormSection) vSection.get(k);
                if (assSection.getPage() == page) {
                    strPage += "<tr><td width='100%'>";
                    strPage += createFormSection(assSection);
                    strPage += "</td>";
                    strPage += "</tr>";

                    if (assSection.getPointEvaluationId() != 0) {
                        strPage += "<tr><td width='94%'>";
                        strPage += createMainRating(assSection.getPointEvaluationId());
                        strPage += "</td>";
                        strPage += "<td width='6%' valign='top'>&nbsp;";
                        strPage += "</td></tr>";
                    }

                    if (assSection.getType() == PstAssessmentFormSection.TYPE_TEXT_SUMMARY) {
                        strPage += "<tr><td width='94%'>";
                        strPage += createSummaryRating(appMain, assSection);
                        strPage += "</td>";
                        strPage += "<td width='6%' valign='top'>&nbsp;";
                        strPage += "</td></tr>";
                    }

                }

                Vector vItems = new Vector(1, 1);
                vItems = SessAssessmentFormItem.listItem(assSection.getOID(), page);

                for (int l = 0; l < vItems.size(); l++) {
                    AssessmentFormItem assItem = new AssessmentFormItem();
                    assItem = (AssessmentFormItem) vItems.get(l);
                    
                    if (assItem.getPage() == page) {
                        Appraisal appraisal = new Appraisal();
                        if (assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM
                                || assItem.getType() == PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM) {
                            if (hAppraisal.get(String.valueOf(assItem.getOID())) != null) {
                                appraisal = (Appraisal) hAppraisal.get(String.valueOf(assItem.getOID()));
                                totalRating = totalRating + (float) appraisal.getRating();
                            }
                        }
                        strPage += "<tr><td width='100%'>";
                        if (assSection.getPointEvaluationId() == Evaluation.EVAL_TYPE_PERFORMANCE_RANGE) {
                            assItem.setEntryUnit("%");
                        }
                        switch (assItem.getType()) {
                            case PstAssessmentFormItem.ITEM_TYPE_SPACE:
                                strPage += createItemTypeSpace(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT:
                                strPage += createItemType2ColNoText(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER:
                                strPage += createItemType2ColHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT:
                                strPage += createItemType1ColWithText(appMain, assSection, assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_TEXT:
                                strPage += createItemTypeText(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_TEXT_BOLD:
                                strPage += createItemTypeTextBold(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_HEADER:
                                strPage += createItemTypeSelecHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_2_WITHOUT_RANGE:
                                strPage += createItemTypeSelectA(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE:
                                strPage += createItemTypeSelectB(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT:
                                strPage += createItemTypeInput(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK:
                                strPage += createItemTypeInputCheck(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER:
                                strPage += createItemTypeInputCheckHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM:
                                strPage += createItemType2ColCommentsAss(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM:
                                strPage += createItemType2ColCommentsEmpAss(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM:
                                strPage += createItemType1ColCommentsEmp(assItem, appraisal);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM:
                                strPage += createItemType1ColCommentsAssessor(assItem, appraisal);
                                break;
                        }
                        strPage += "</td>";
                        strPage += "</tr>";
                    }
                }
                if (assSection.getPage() == page && assSection.getPredicateEvaluationId() != 0) {
                    Evaluation eval=PstEvaluation.getByMaxPoint(totalRating,  assSection.getPredicateEvaluationId());
                    strPage += "<tr><td width='94%'><br><table width='100%' border='0' cellspacing='0' cellpadding='0' >";
                    strPage += "<tr><td alight='left'>";
                    strPage += "<font size='4'><b> TOTAL PERFORMANCE  "+assSection.getSection() +"</b></font> </td><td><font size='4'><b>" +  totalRating + "</b></font></td>";
                    strPage += "<tr><td alight='left'>";
                    strPage += "<font size='4'><b> PREDICATE </b></font> </td><td><font size='4'><b>" + (eval!=null? eval.getName():"NO PREDICATE FOUND, PLEASE CHECK EVALUATION CRETERIA ") + "</b></font></td>";
                    strPage += "</table></td></tr>";
                    strPage += "<tr><td width='94%'></td></tr>";
                    strPage += "<tr><td width='94%'>";
                    strPage += createMainRating(assSection.getPredicateEvaluationId());
                    strPage += "</td>";
                    strPage += "<td width='6%' valign='top'>&nbsp;";
                    strPage += "</td></tr>";
                }

            }
        }

        strPage += "</table>";
        //System.out.println(strPage);
        if (vSection.size() > 0) {
            return strPage;
        }
        return "";
    }

    /**
     * @return the SESS_LANGUAGE
     */
    public static int getSESS_LANGUAGE() {
        return SESS_LANGUAGE;
    }

    /**
     * @param aSESS_LANGUAGE the SESS_LANGUAGE to set
     */
    public static void setSESS_LANGUAGE(int aSESS_LANGUAGE) {
        SESS_LANGUAGE = aSESS_LANGUAGE;
    }

    /**
     * @return the mainDescription
     */
    public static String getMainDescription() {
        return mainDescription;
    }

    /**
     * @param aMainDescription the mainDescription to set
     */
    public static void setMainDescription(String aMainDescription) {
        mainDescription = aMainDescription;
    }
    
    
}
