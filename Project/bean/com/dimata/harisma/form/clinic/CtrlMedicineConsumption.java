/* 
 * Ctrl Name  		:  CtrlMedicineConsumption.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.clinic;

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
import com.dimata.harisma.entity.clinic.*;

public class CtrlMedicineConsumption extends Control implements I_Language 
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
    private Vector listMedConsumpt = new Vector(1,1);
    private boolean isConsumption ;
	private MedicineConsumption medicineConsumption;
	private PstMedicineConsumption pstMedicineConsumption;
	private FrmMedicineConsumption frmMedicineConsumption;
	int language = LANGUAGE_DEFAULT;

	public CtrlMedicineConsumption(HttpServletRequest request){
		msgString = "";
		medicineConsumption = new MedicineConsumption();
		try{
			pstMedicineConsumption = new PstMedicineConsumption(0);
		}catch(Exception e){;}
		frmMedicineConsumption = new FrmMedicineConsumption(request, medicineConsumption);
	}

	public String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmMedicineConsumption.addError(frmMedicineConsumption.FRM_FIELD_MEDICINE_CONSUMPTION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
				return resultText[language][RSLT_EST_CODE_EXIST];
			default:
				return resultText[language][RSLT_UNKNOWN_ERROR]; 
		}
	}

	public int getControlMsgId(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				return RSLT_EST_CODE_EXIST;
			default:
				return RSLT_UNKNOWN_ERROR;
		}
	}

    public Vector getListMedicine(){ return listMedConsumpt; }

	public int getLanguage(){ return language; }

    public boolean isConsumption(){return  isConsumption = isConsumption; }

	public void setLanguage(int language){ this.language = language; }

	public MedicineConsumption getMedicineConsumption() { return medicineConsumption; } 

	public FrmMedicineConsumption getForm() { return frmMedicineConsumption; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , Date dtMonth){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ASK :
				if (dtMonth != null) {
					try {
                        String whereClause = " TO_DAYS("+PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+")"+
                            				 " > TO_DAYS('"+Formater.formatDate(dtMonth,"yyyy-MM-dd")+"')";
                        Vector listAll = PstMedicineConsumption.list(0,0,whereClause,"");
                        if(listAll != null && listAll.size()>0){
                        	msgString = " If you delete these data, Data after "+YearMonth.getLongEngMonthName(dtMonth.getMonth()+1) +", "+(dtMonth.getYear()+1900)+" will delete too. Are you sure to delete ?";
                        }
                        listMedConsumpt = PstMedicineConsumption.listMedConsumpt(dtMonth);
					} catch (Exception exc){
					}
				}
				break;

			case Command.DELETE :
				if (dtMonth != null){
					try{
                       PstMedicineConsumption.deleteMedicineConsum(dtMonth);
                       msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                       Vector listAll = PstMedicineConsumption.listAll();
	                   Date prevMonth = (Date)dtMonth.clone();
		               prevMonth.setMonth(prevMonth.getMonth()-1);
		        	   if(listAll == null || listAll.size()<1){
		                    listMedConsumpt = PstMedicineConsumption.listMedicine(prevMonth,false);
	                        this.isConsumption = false;
	                        if(listMedConsumpt == null || listMedConsumpt.size()<1){
	                        	msgString = "No Medicine available";
	                            listMedConsumpt = new Vector(1,1);
	                        }
	           		   }else{
		               		listMedConsumpt = PstMedicineConsumption.list(0,0,PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+" = '"+
	               													Formater.formatDate(prevMonth,"yyyy-MM-dd")+"'","");
	                        this.isConsumption = false;
	                        System.out.println("size() "+listMedConsumpt.size());
	                        if(listMedConsumpt == null || listMedConsumpt.size()<1){
	                        	 msgString = "Medicine Consumption last month not available ";
	                           	 listMedConsumpt = new Vector(1,1);
	                        }else{
	                            listMedConsumpt = PstMedicineConsumption.listMedicine(prevMonth,true);
	                        }
	           		   }
					}catch(Exception exc){	
						msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
					}
				}
				break;

        	case Command.EDIT:
               listMedConsumpt = PstMedicineConsumption.list(0,0,PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+" = '"+
               													Formater.formatDate(dtMonth,"yyyy-MM-dd")+"'","");

               if(listMedConsumpt != null && listMedConsumpt.size()>0){
               			listMedConsumpt = PstMedicineConsumption.listMedConsumpt(dtMonth);
        	   			this.isConsumption = true;
               }else{
	        	   Vector listAll = PstMedicineConsumption.listAll();
                   Date prevMonth = (Date)dtMonth.clone();
	               prevMonth.setMonth(prevMonth.getMonth()-1);

	        	   if(listAll == null || listAll.size()<1){
	                    listMedConsumpt = PstMedicineConsumption.listMedicine(prevMonth,false);
                        this.isConsumption = false;
                        if(listMedConsumpt == null || listMedConsumpt.size()<1){
                        	msgString = "No Medicine available";
                            listMedConsumpt = new Vector(1,1);
                        }
           		   }else{
	               		listMedConsumpt = PstMedicineConsumption.list(0,0,PstMedicineConsumption.fieldNames[PstMedicineConsumption.FLD_MONTH]+" = '"+
               													Formater.formatDate(prevMonth,"yyyy-MM-dd")+"'","");
                        this.isConsumption = false;
                        System.out.println("size() "+listMedConsumpt.size());
                        if(listMedConsumpt == null || listMedConsumpt.size()<1){
                        	 msgString = "Medicine Consumption last month not available ";
                           	 listMedConsumpt = new Vector(1,1);
                        }else{
                            listMedConsumpt = PstMedicineConsumption.listMedicine(prevMonth,true);
                        }
           		   }
               }
               break;

        	case Command.SAVE:
               listMedConsumpt = PstMedicineConsumption.listMedConsumpt(dtMonth);
               break;

			default :

		}
		return rsCode;
	}
}
