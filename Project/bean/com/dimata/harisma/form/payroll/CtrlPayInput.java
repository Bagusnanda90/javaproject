/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.payroll.PayInput;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class CtrlPayInput extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;
        public static int RSLT_FORM_ERROR = 4;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","Tidak Berhasil Di simpan Karena Belum Process Working Day Summary"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete","Cannot Be Saved Because Working Day Summary is not processed yet"}
	};

    
        private int start;
	private String msgString;
	private PayInput payInput;
	private PstPayInput pstPayInput;
	private FrmPayInput frmPayInput;
        
        int language = LANGUAGE_DEFAULT;
        
    /** Creates a new instance of CtrlPayInput */
    public CtrlPayInput(HttpServletRequest request){
		msgString = "";
		payInput = new PayInput();
        //locker = new Locker();
		try{
			pstPayInput = new PstPayInput(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmPayInput = new FrmPayInput(request,payInput);
		//frmLocker = new FrmLocker(request, locker);
	}
    
    
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPayInput.addError(frmPayInput.FRM_FIELD_PAY_INPUT_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public PayInput getPayGeneral() { return payInput; } 
    
    public FrmPayInput getForm() { return frmPayInput; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public String getMessages(){
          String msgEror="";
        if(frmPayInput!=null && frmPayInput.getvListVctErrorMessage()!=null && frmPayInput.getvListVctErrorMessage().size()>0){
          
            for(int msg=0;msg<frmPayInput.getvListVctErrorMessage().size();msg++){
                msgEror = msgEror + (String)frmPayInput.getvListVctErrorMessage().get(msg)+",";
            }
            if(msgEror!=null && msgEror.length()>0){
                msgEror = msgEror.substring(0, msgEror.length()-1);
                msgEror = "Employee Number: " + msgEror +" "+ CtrlPayInput.resultText[CtrlPayInput.LANGUAGE_DEFAULT][CtrlPayInput.RSLT_FORM_ERROR];
            }
        }
        return msgEror; 
    }
    
    
    public int action(int cmd,Vector listReason,Vector listPosition){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				
                            Vector selected = frmPayInput.requestHowManyCheckbox(listReason, listPosition);
                            if(selected!=null && selected.size()==2){
                                        Vector howManyCheckbox = (Vector)selected.get(0);
                                        Vector howManyCheckboxSelected = (Vector)selected.get(1);

                                        if(howManyCheckbox!=null && howManyCheckbox.size()>0 && howManyCheckboxSelected!=null && howManyCheckboxSelected.size()>0){
                                            Hashtable hashEmployee = PstEmployee.hashListEmployee();
                                            for(int idxchkBox=0;idxchkBox<howManyCheckbox.size();idxchkBox++){
                                                String paramRequest = (String)howManyCheckbox.get(idxchkBox);
                                                String chkBox = (String)howManyCheckboxSelected.get(idxchkBox);
                                                frmPayInput.requestEntityObjectMultipleProsess(paramRequest,listReason,listPosition,chkBox,hashEmployee);
                                            }
                                        }
                            }
                
                Vector listEmpOvertime= new Vector();
                frmPayInput.requestEntityObjOvertime(listEmpOvertime);
                if(listEmpOvertime!=null && listEmpOvertime.size()>0){
                    for(int idxc=0;idxc<listEmpOvertime.size();idxc++){
                        PayInput payInput = (PayInput)listEmpOvertime.get(idxc);
                        if(payInput!=null && payInput.getPeriodId()!=0 && payInput.getEmployeeId()!=0){
                            if(payInput.getOtAllowanceMoneyAdjust()!=0){
                                PstPaySlip.updateWorkingDayAllowanceAjusment(payInput);
                            }
                            if(payInput.getOtIdxPaidSalaryAdjust()!=0){
                                PstPaySlip.updateWorkingDayPaiSalarydAjusment(payInput);
                            }
                            /*
                            if(payInput.getNightAllowanceAdjusment()!=0){
                                PstPaySlip.updateWorkingDayPaiSalarydAjusment(payInput);
                            }*/
                            
                        }
                        
                    }
                    
                }
				
				break;
		}
		return rsCode;
	}
    
    

    
    
}
