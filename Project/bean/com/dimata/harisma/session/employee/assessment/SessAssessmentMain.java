/* 
 * Session Name  	:  SessAppraisal.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.session.employee.assessment;/* java package */ 
import com.dimata.harisma.session.employee.*;
import java.io.*; 
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.util.*;
/* project package */
//import com.dimata.harisma.db.DBException;
//import com.dimata.harisma.db.DBHandler;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMain;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormMainDetail;
import com.dimata.harisma.entity.masterdata.*;

public class SessAssessmentMain
{
	public static final String SESS_SRC_APPRAISAL = "SESSION_SRC_APPRAISAL";
        
        public static AssessmentFormMain getAssessment(long appraisalMainOid){
            AssessmentFormMain assessmentFormMain = new AssessmentFormMain();
            AppraisalMain appMain = new AppraisalMain();
            try{
                appMain = PstAppraisalMain.fetchExc(appraisalMainOid);
                long levelOid = appMain.getEmpLevelId();
                Level level = new Level();
                level = PstLevel.fetchExc(levelOid);
                String strWhere = "afmd."+PstAssessmentFormMainDetail.fieldNames[PstAssessmentFormMainDetail.FLD_GROUP_RANK_ID]
                        +"="+level.getGroupRankId();
                //update by satrya 2014-06-09 Vector vAssMain = PstAssessmentFormMain.list(0, 0, strWhere, "");
                Vector vAssMain = PstAssessmentFormMain.listJoinDetail(0, 0, strWhere, "");
                assessmentFormMain = (AssessmentFormMain)vAssMain.get(0);
                return assessmentFormMain;
            }catch(Exception ex){}
            return assessmentFormMain;
        }

}
