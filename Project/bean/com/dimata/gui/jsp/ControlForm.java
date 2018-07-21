/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.gui.jsp;

import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;
import com.dimata.harisma.entity.masterdata.Evaluation;
import com.dimata.harisma.entity.masterdata.PstEvaluation;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormItem;
import com.dimata.harisma.session.employee.assessment.SessAssessmentFormSection;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class ControlForm {

    private static int SESS_LANGUAGE = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;
    public static String HTML_TD_ITEM_1TD = "<td width='30px'>&nbsp;</td>";
    public static String HTML_TD_ITEM_2TD = "<td width='30'>&nbsp;</td><td width='100'>&nbsp;</td>";
    public static String[][] SYSTEM_VOCAB = {{"Rangkuman", "Paragraf", "Bobot", "Total"}, {"Summary", "Section", "Weight", "Total"}};
    public static final int VOCAB_SUMMARY = 0;
    public static final int VOCAB_SECTION = 1;
    public static final int VOCAB_WEIGHT = 2;
    public static final int VOCAB_TOTAL = 3;

    public static String createFormMain(AssessmentFormMain assessmentFormMain) {
        String strForm = "";
        strForm = createMainHeader(assessmentFormMain) + "<br>" + createMainData(assessmentFormMain)
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
            case PstAssessmentFormSection.TYPE_TEXT_SUMMARY:
                strForm = createSectionTypeC(assessmentFormSection);
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

        strItem += "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"
                //     +"<tr><td align='center' valign='top'><img src='"+approot+"/logo.jpg'></td></tr>"
                + "<tr><td align='center' valign='bottom'><font size='4px'><b>"
                + assessmentFormMain.getTitle()
                + "</b></font></td></tr>";
        if (assessmentFormMain.getTitle_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'>"
                    + assessmentFormMain.getTitle_L2()
                    + "</font></td></tr>";
        }
        if (assessmentFormMain.getSubtitle().length() > 0) {
            strItem += "<tr><td align='center' valign='bottom'><font size='4px'><b><i>"
                    + assessmentFormMain.getSubtitle()
                    + "</i></b></font></td></tr>";
        }
        if (assessmentFormMain.getSubtitle_L2().length() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'><i>"
                    + assessmentFormMain.getSubtitle_L2()
                    + "</i></font></td></tr>";
        }
        strItem += "</table>";
        return strItem;
    }

    private static String createMainData(AssessmentFormMain assessmentFormMain) {
        String strItem = "";
        String strMainData = assessmentFormMain.getMainData();
        boolean isDataAvailable = false;
        if (PstAssessmentFormMain.fieldFormTypes != null && PstAssessmentFormMain.fieldFormTypes.length > 0) {
            for (int i = 0; i < PstAssessmentFormMain.fieldFormTypes.length; i++) {
                boolean isUsed = PstAssessmentFormMain.cekFormUsed(strMainData, i);
                // System.out.println(i+"=="+isUsed);
                String strVal = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_FIRST];
                String strValL2 = PstAssessmentFormMain.fieldFormValue[i][PstAssessmentFormMain.LANGUAGE_OTHER];

                if (isUsed) {
                    isDataAvailable = true;
                    strItem += "  <tr>";
                    strItem += "    <td width='35%' height='20' align='left' valign='top'><strong>" + strVal + "</strong>";
                    strItem += "        <br>" + strValL2 + "</td>";
                    strItem += "    <td width='2%' height='20' align='center' valign='top'><strong>:</strong></td>";
                    strItem += "        <td width='63%' height='20' align='left' valign='top'>_________________________________________________</td>";
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
        
        strItem += "<p><strong><u>Preparation<em><br>"
                + "(Persiapan)</em></u></strong></p>"
                + "<ul><li type='disc'> Use this form only for those positions indentified in the assessment guide."
                + "<br /> <i> (Gunakan Formulir ini hanya untuk jabatan yang tercantum dalam petunjuk penilain) </i>"
                + "</li>"
                //update by satrya 2014-06-2 +"<li type='disc'>Carefully read Human Resources P&P-66 - prior to assessment."
                + "<li type='disc'>Carefully read Human Resourcess P&P - prior to assessment."
                + "<br /> <i> (Hati-hati membaca Human Resourcess P & P - sebelum penilaian) </i> </li></ul>"
                //update by satrya 2014-06-02 +"<br /> <i> ( Bacalah dengan cermat peraturan dan prosedur Human Resources P&P-66- sebelum penilaian dilakukan) </i> </li></ul>"
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
        return strItem;
    }

    private static String createMainRating() {
        return createMainRating(0);
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
     * Create Summary rating from sections with point weight
     * by Kartika 20150223
     * @return
     */
    private static String createSummaryRating(AssessmentFormSection assessmentFormSection) {
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
            strItem += "    <td width='" + 80 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_SECTION] + "</b></td>";
            strItem += "    <td width='" + 20 + "%' align='center' valign='top'><b>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_WEIGHT] + "</b></td>";
            strItem += "  </tr>";
            float totalWeight = 0;
            for (int i = 0; i < vSections.size(); i++) {
                AssessmentFormSection section = (AssessmentFormSection) vSections.get(i);
                if(section.getWeightPoint()> 0.0){
                    strItem += "  <tr bgcolor='ffffff'>";
                    strItem += "    <td width='" + 80 + "%' align='center' valign='top'>" + section.getSection() + "<br>" + section.getDescription() + "</td>";
                    strItem += "    <td width='" + 20 + "%' align='center' valign='top'>" + section.getWeightPoint() + "</td>";
                    strItem += "  </tr>";
                    totalWeight = totalWeight + section.getWeightPoint();
                }
            }
            strItem += "  <tr bgcolor='ffffff'>";
            strItem += "    <td width='" + 80 + "%' align='left' valign='top'><br>" + SYSTEM_VOCAB[getSESS_LANGUAGE()][VOCAB_TOTAL] + "</br></td>";
            strItem += "    <td width='" + 20 + "%' align='center' valign='top'>" + totalWeight + "</td>";
            strItem += "  </tr>";

            strItem += "</table></td></tr></table>";
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
        if (assessmentFormSection.getWeightPoint() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'> Point Weight : "
                    + assessmentFormSection.getWeightPoint()
                    + "</font></td></tr>";
        }
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

        if (assessmentFormSection.getWeightPoint() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'> Point Weight : "
                    + assessmentFormSection.getWeightPoint()
                    + "</font></td></tr>";
        }

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

    /**
     * @created by : Kartika, 2015-02-23
     * @param assessmentFormSection
     * @return
     */
    private static String createSectionTypeC(AssessmentFormSection assessmentFormSection) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='pageCover'>";
        strItem += "<tr>";
        strItem += "  <td width='100%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageCoverHeader'>";
        strItem += "<tr><td align='center' valign='bottom'><font size='3px'><b><u>"
                + assessmentFormSection.getSection()
                + "</u></b></font></td></tr>";

        if (assessmentFormSection.getWeightPoint() > 0) {
            strItem += "<tr><td height='18' align='center' valign='top'><font size='2px'> Point Weight : "
                    + assessmentFormSection.getWeightPoint()
                    + "</font></td></tr>";
        }

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

    private static String createItemType2ColNoText(AssessmentFormItem assessmentFormItem) {
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
        if (assessmentFormItem.getItemPoin3().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin3() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin4().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin4() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin3().length() > 0) {
            strItem += "          </strong></p>";
        }
        if (assessmentFormItem.getItemPoin5().length() > 0) {
            strItem += "              <p><strong>" + assessmentFormItem.getItemPoin5() + "<br/>";
        }
        if (assessmentFormItem.getItemPoin6().length() > 0) {
            strItem += "          <em>" + assessmentFormItem.getItemPoin6() + "</em></strong></p>";
        } else if (assessmentFormItem.getItemPoin5().length() > 0) {
            strItem += "          </strong></p>";
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
        strItem += "              <td width='82%' height='100%'>&nbsp;</td>";
        strItem += "              <td width='18%' align='right' valign='top'>";
        strItem += "              <table width='58' height='42' class='pageInput'>";
        strItem += "                <tr>";
        strItem += "                    <td width='70'></td>";
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

        int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
        if (false && maxNumber == assessmentFormItem.getNumber()) {
            strItem += "<br><table width='100%' border='0' cellspacing='0' cellpadding='0' >";
            strItem += "<tr>";
            strItem += "<td alight='left'>";
            strItem += "<font size='4'><b>PERFORMANCE %</b></font> (add ratings) <font size='4'><b>____________________ / ____ = </b></font>";
            strItem += "</td>";
            strItem += "<td alight='right'>";
            strItem += "              <table width='58' height='42' class='pageInput'>";
            strItem += "                <tr>";
            strItem += "                    <td width='70' alight='right'>";
            strItem += "<font size='4'> %</font>";
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

    private static String createItemTypeSelectA(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> "
                + "<tr><td align='left' valign='top' width='10%'>";
        strItem += "[<input type='checkbox' name='' value='' />]";
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
        strItem += "[<input type='checkbox' name='' value='' />]";
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

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeSelectB(AssessmentFormItem assessmentFormItem) {
        String strItem = "<table width='100%' border='0' cellspacing='0' cellpadding='0'> "
                + "<tr><td align='left' valign='top' width='10%'>";
        strItem += "[<input type='checkbox' name='' value='' />]";
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
        strItem += "[<input type='checkbox' name='' value='' />]";
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

        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColCommentsEmpAss(AssessmentFormItem assessmentFormItem) {
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
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td><center>";
        strItem += "  <br><br><br><br><br>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</center></td></tr></table>";
        strItem += "  </td>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td><center>";
        strItem += "  <br><br><br><br><br>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</center></td></tr></table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType2ColCommentsAss(AssessmentFormItem assessmentFormItem) {
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
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td><center>";
        strItem += "  <br><br><br><br><br>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</center></td></tr></table>";
        strItem += "  </td>";
        strItem += "  <td width='50%' height='100%'>";
        strItem += "<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' class='pageForm'><tr><td><center>";
        strItem += "  <br><br><br><br><br>";
        for (int i = 1; i < assessmentFormItem.getHeight(); i++) {
            strItem += " <tr>";
            strItem += " <td >";
            strItem += "<br/>";
            strItem += " </td>";
            strItem += " </tr>";
        }
        strItem += "</center></td></tr></table>";
        strItem += "  </td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColCommentsEmp(AssessmentFormItem assessmentFormItem) {
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
        strItem += "<td>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColCommentsAssessor(AssessmentFormItem assessmentFormItem) {
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
        strItem += "<td>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "___________________________________________________<br>";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemTypeInput(AssessmentFormItem assessmentFormItem) {
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
        strItem += "___________________________________________";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
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

    private static String createItemTypeInputCheck(AssessmentFormItem assessmentFormItem) {
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
        strItem += "[<input type='radio' name='" + assessmentFormItem.getOID() + "' value='' />]";
        strItem += "</td>";
        strItem += "<td width='10%' align='center' valign='top'>";
        strItem += "[<input type='radio' name='" + assessmentFormItem.getOID() + "' value='' />]";
        strItem += "</td>";
        strItem += "</tr>";
        strItem += "</table>";
        // System.out.println(strItem);
        return strItem;
    }

    private static String createItemType1ColWithText(AssessmentFormItem assessmentFormItem) {
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
        strItem += assessmentFormItem.getKpiTarget() != 0.0 ? "<td  width='30' colspan='2' class='pageCover'> Target:<input type='text' readonly size='10'  value='" + assessmentFormItem.getKpiTarget() + "'> <input type='text' readonly size='15'  value='" + assessmentFormItem.getKpiUnit() + "'" + "&nbsp;&nbsp;</td><td  width='100' colspan='2' class='pageCover'>&nbsp;&nbsp;Note:<input type='text' readonly size='64'  value='" + assessmentFormItem.getKpiNote() + "'></td>" : HTML_TD_ITEM_2TD;
        strItem += assessmentFormItem.getWeightPoint() != 0.0 ? "<td width='30' colspan='2' class='pageCover'>Weight:<input type='text' readonly size='10'  value='" + assessmentFormItem.getWeightPoint() +"'></td>" : HTML_TD_ITEM_1TD;

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
        strItem += "                    <td width='70'></td>";
        strItem += "                </tr>";
        strItem += "              </table>";
        strItem += "  </td>";
        strItem += " </tr>";
        strItem += "</table>";

        int maxNumber = SessAssessmentFormItem.getMaxNumber(assessmentFormItem.getAssFormSection());
        if ( false && maxNumber == assessmentFormItem.getNumber()) {
            strItem += "<br><table width='100%' border='0' cellspacing='0' cellpadding='0' >";
            strItem += "<tr>";
            strItem += "<td alight='left'>";
            strItem += "<font size='4'><b>PERFORMANCE %</b></font> (add ratings) <font size='4'><b>____________________ / ____ = </b></font>";
            strItem += "</td>";
            strItem += "<td alight='right'>";
            strItem += "              <table width='58' height='42' class='pageInput'>";
            strItem += "                <tr>";
            strItem += "                    <td width='70' alight='right'>";
            strItem += "<font size='4'> %</font>";
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

    public static String createPage(long mainOid, int page) {
        System.out.println("::::::::::: CREATE PAGE " + page);
        Vector vSection = new Vector(1, 1);
        Vector vItem = new Vector(1, 1);
        vSection = SessAssessmentFormSection.getSections(page, mainOid);
        vItem = PstAssessmentFormItem.listItem(mainOid, page);
        String strPage = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        if (vSection.size() > 0) {
            for (int k = 0; k < vSection.size(); k++) {
                AssessmentFormSection assSection = new AssessmentFormSection();
                assSection = (AssessmentFormSection) vSection.get(k);
                if (assSection.getPage() == page) {
                    strPage += "<tr><td width='94%'>";
                    strPage += createFormSection(assSection);
                    strPage += "</td>";
                    strPage += "<td width='6%' valign='top'>";
                    strPage += createSpliter(assSection.getOID());
                    strPage += "</td></tr>";

                    if (assSection.getPointEvaluationId() != 0) {
                        strPage += "<tr><td width='94%'>";
                        strPage += createMainRating(assSection.getPointEvaluationId());
                        strPage += "</td>";
                        strPage += "<td width='6%' valign='top'>&nbsp;";
                        strPage += "</td>";
                    }

                    if (assSection.getType() == PstAssessmentFormSection.TYPE_TEXT_SUMMARY) {
                        strPage += "<tr><td width='94%'>";
                        strPage += createSummaryRating(assSection);
                        strPage += "</td>";
                        strPage += "<td width='6%' valign='top'>&nbsp;";
                        strPage += "</td>";
                    }
                }
                Vector vItems = new Vector(1, 1);
                vItems = SessAssessmentFormItem.listItem(assSection.getOID(), page);
                for (int l = 0; l < vItems.size(); l++) {
                    AssessmentFormItem assItem = new AssessmentFormItem();
                    assItem = (AssessmentFormItem) vItems.get(l);
                    if (assItem.getPage() == page) {
                        strPage += "<tr><td width='94%'>";
                        // System.out.println("KE- "+i);
                        switch (assItem.getType()) {
                            case PstAssessmentFormItem.ITEM_TYPE_SPACE:
                                strPage += createItemTypeSpace(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_WITHOUT_TEXT:
                                strPage += createItemType2ColNoText(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_2_HEADER:
                                strPage += createItemType2ColHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_COL_1_WITH_TEXT:
                                strPage += createItemType1ColWithText(assItem);
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
                                strPage += createItemTypeSelectA(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_SELECT_1_WITH_RANGE:
                                strPage += createItemTypeSelectB(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_WITH_DOT:
                                strPage += createItemTypeInput(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK:
                                strPage += createItemTypeInputCheck(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_CHECK_HEADER:
                                strPage += createItemTypeInputCheckHeader(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_2_COL_ASS_COMM:
                                strPage += createItemType2ColCommentsAss(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_2_COL_OVERALL_COMM:
                                strPage += createItemType2ColCommentsEmpAss(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_EMP_COMM:
                                strPage += createItemType1ColCommentsEmp(assItem);
                                break;
                            case PstAssessmentFormItem.ITEM_TYPE_INPUT_ASS_COMM:
                                strPage += createItemType1ColCommentsAssessor(assItem);
                                break;
                        }
                        strPage += "</td>";
                        strPage += "<td width='6%' valign='top'>";
                        strPage += createSpliterItem(assItem.getOID());
                        strPage += "</td></tr>"; 
                    }
                }
                if (assSection.getPage() == page && assSection.getPredicateEvaluationId() != 0) {
                            strPage += "<tr><td width='94%'>";
                            strPage += createMainRating(assSection.getPredicateEvaluationId());
                            strPage += "</td>";
                            strPage += "<td width='6%' valign='top'>&nbsp;";
                            strPage += "</td>";
                        }
            }
        }

        strPage += "</table>";
        //System.out.println(strPage);
        if (vSection.size() > 0 || vItem.size() > 0) {
            return strPage;
        }
        return "";
    }

    private static String createSpliter(long sectionOid) {
        String strSpliter = "";
        strSpliter += "<table width='100%' height='25' border='0' cellspacing='1' cellpadding='1' bgcolor='#FFFFFF'><tr><td>";
        strSpliter += "<table width='100%' height='100%' border='0' cellspacing='1' cellpadding='1' bgcolor='#CCCCCC'><tr><td width='100%' align='right'>";
        strSpliter += "<a href=javascript:cmdEditSection('" + sectionOid + "')>[Edit Section]</a><br>";
        strSpliter += "<a href=javascript:cmdNewItem('" + sectionOid + "')>[New Item]</a>";
        strSpliter += "</td></tr></table>";
        strSpliter += "</td></tr></table>";
        return strSpliter;
    }

    private static String createSpliterItem(long itemOid) {
        String strSpliter = "";
        strSpliter += "<table width='100%' height='25' border='0' cellspacing='1' cellpadding='1' bgcolor='#CCCCCC'><tr><td>";
        strSpliter += "<table width='100%' height='100%' border='0' cellspacing='1' cellpadding='1' bgcolor='#FFFFFF'><tr><td width='100%' align='right'>";
        strSpliter += "<a href=javascript:cmdEditItem('" + itemOid + "')>[Edit Item]</a>";
        strSpliter += "</td></tr></table>";
        strSpliter += "</td></tr></table>";
        return strSpliter;
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
}
