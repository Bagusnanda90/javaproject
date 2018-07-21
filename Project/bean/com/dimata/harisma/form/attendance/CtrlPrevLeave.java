/* 
 * Ctrl Name  		:  CtrlPrevLeave.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.attendance;

/* java package */ 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.attendance.*;

public class CtrlPrevLeave extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private PrevLeave prevLeave;
	private PstPrevLeave pstPrevLeave;
	private FrmPrevLeave frmPrevLeave;
	int language = LANGUAGE_DEFAULT;

	public CtrlPrevLeave(HttpServletRequest request){
		msgString = "";
		prevLeave = new PrevLeave();
		try{
			pstPrevLeave = new PstPrevLeave(0);
		}catch(Exception e){;}
		frmPrevLeave = new FrmPrevLeave(request, prevLeave);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPrevLeave.addError(frmPrevLeave.FRM_FIELD_PREV_LEAVE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
				return resultText[language][RSLT_EST_CODE_EXIST];
			default:
				return resultText[language][RSLT_UNKNOWN_ERROR]; 
		}
	}

	private int getControlMsgId(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				return RSLT_EST_CODE_EXIST;
			default:
				return RSLT_UNKNOWN_ERROR;
		}
	}

	public int getLanguage(){ return language; }

	public void setLanguage(int language){ this.language = language; }

	public PrevLeave getPrevLeave() { return prevLeave; } 

	public FrmPrevLeave getForm() { return frmPrevLeave; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.PRINT :

                long oid = 0;
                try{
                    Date dt = new Date();
                    int month = dt.getMonth();
                    String where = PstPrevLeave.fieldNames[PstPrevLeave.FLD_MONTH]+"="+month;
	                Vector vct = PstPrevLeave.list(0,0,where, null);

	            	if(vct!=null && vct.size()>0){
	                	PrevLeave prev = (PrevLeave)vct.get(0);
	                    frmPrevLeave.requestEntityObject(prev);
                        prev.setMonth(month);
	                    oid = PstPrevLeave.updateExc(prev);
	
	            	}
	            	else{
	                	PrevLeave prev = new PrevLeave();
	                    frmPrevLeave.requestEntityObject(prev);
	                    prev.setMonth(month);
	                    oid = PstPrevLeave.insertExc(prev);
	            	}
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }


				break;


			default :

		}
		return rsCode;
	}
}
