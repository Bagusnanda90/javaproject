<% 
/* 
 * Page Name  		:  pay_slip_level_val.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: autami
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_LEVEL);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=false;
%>

<%!
	 public String drawList(int iCommand, Vector<PayComponentValue> allDetailPay, FrmSalaryLevelDetail frmObject, SalaryLevelDetail objEntity, Vector objectClass,  long salaryLevelDetailId,String comp_code, int status) 
	 {
	 	ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("<strong>No</strong>","5%");
		ctrlist.addHeader("<strong>Code</strong>","10%");
		ctrlist.addHeader("<strong>Name</strong>","20%");
		ctrlist.addHeader("<strong>Formula(*) NOTE: Whitespaces have to be set among formula components e.g. = BS * 30000 * DATE_PRESENT  </strong>","35%");
		ctrlist.addHeader("<strong>Value</strong>","10%");
		ctrlist.addHeader("<strong>Continue</strong>","10%");
		ctrlist.addHeader("<strong>Non Continue</strong>","10%");
		ctrlist.addHeader("<strong>Take Home Pay</strong>","5%");
		ctrlist.addHeader("<strong> Group</strong>","5%");
                
                Hashtable<String, PaySlipGroup> hPaySlipGroup = PstPaySlipGroup.listInHashtable(0,0,"","");
                Hashtable<String, PayComponent> hPayComponen = PstPayComponent.listHashtableKeyCompCode(0,0,"","");
                
		//ini untuk Periode Pembayaran
		Vector val_period = new Vector();
		Vector key_period = new Vector();
		for (int j = 0; j < PstSalaryLevelDetail.periodKey.length;j++){
			val_period.add(""+j);
			key_period.add(PstSalaryLevelDetail.periodKey[j]);
		}
		
		//ini untuk Take Home Pay
		Vector val_take = new Vector();
		Vector key_take = new Vector();
		for (int m = 0; m < PstSalaryLevelDetail.takeKey.length;m++){
			val_take.add(""+m);
			key_take.add(PstSalaryLevelDetail.takeKey[m]);
		}
		
		//ini untuk SAME NEXT MONTH
		Vector val_copy = new Vector();
		Vector key_copy = new Vector();
		for (int n = 0; n < PstSalaryLevelDetail.copyKey.length;n++){
			val_copy.add(""+n);
			key_copy.add(PstSalaryLevelDetail.copyKey[n]);
		}
		
		Vector val_code = new Vector();    
		Vector key_code = new Vector();
		String wherePayComp = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+ "=" + PstPayComponent.TYPE_BENEFIT;
		Vector temp = PstPayComponent.list(0,0, wherePayComp , "");
		key_code.add("");
		val_code.add("");
		for (int j = 0; j < temp.size();j++){
			PayComponent payComponent= (PayComponent)temp.get(j);
			val_code.add(""+payComponent.getOID());
			key_code.add(payComponent.getCompName());
		}
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		for (int i = 0; i < objectClass.size(); i++) {		
			 SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)objectClass.get(i);			 
			 rowx = new Vector();
			 if(salaryLevelDetailId == salaryLevelDetail.getOID())
				 index = i;
                         PayComponentValue  payVal= null;
                         if(allDetailPay!=null && allDetailPay.size()>0){
                             for(int iv=0;iv< allDetailPay.size();iv++ ){
                                 payVal = allDetailPay.get(iv);
                                 // jika code sama, maka hapus vector allDetailPay
                                 if(payVal.getCompCode()!=null && payVal.getCompCode().equalsIgnoreCase(salaryLevelDetail.getCompCode())){
                                     allDetailPay.remove(iv); 
                                     break;
                                 }
                                 if( iv == (allDetailPay.size()-1) && !payVal.getCompCode().equalsIgnoreCase(salaryLevelDetail.getCompCode())){
                                     payVal = null; 
                                     break;
                                 }
                             }
                         }
				 
			 {				
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(salaryLevelDetail.getOID())+"')\">"+salaryLevelDetail.getSortIdx()+"</a>");
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(salaryLevelDetail.getOID())+"')\">"+salaryLevelDetail.getCompCode()+"</a>");
				// mengambil nama berdasarkan component code
				long compId=0;
				compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
                                //update by satrya 2014-04-02
                                if(compId==0){
                                    compId = PstPayComponent.getIdComonenName(salaryLevelDetail.getCompName());
                                }
				PayComponent payComp =   new PayComponent();
				String compName ="";
				int defPeriod = 0;
						try{
							payComp = hPayComponen.get(salaryLevelDetail.getCompCode());//  PstPayComponent.fetchExc(compId);
							compName = payComp.getCompName();
							defPeriod = payComp.getPayPeriod();
						}
						catch(Exception e){
					}			
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(salaryLevelDetail.getOID())+"')\">"+compName+"</a>");
				rowx.add(salaryLevelDetail.getFormula());	
                                double checkValue = 0.0;
                                int taxItem = 0;
                                if(payVal != null){
                                    checkValue = payVal.getCheckValue();
                                    taxItem = payVal.getTaxItem();
                                }		
				rowx.add(String.valueOf(Formater.formatNumber(checkValue,"###,###.##" )));				
				if(taxItem== PstPayComponent.GAJI) {
					rowx.add(""+Formater.formatNumber(checkValue,"###,###.##" ));
				}else{
                                    rowx.add("");
                                }
                                
                                if(taxItem== PstPayComponent.TUNJANGAN) {
					rowx.add(""+Formater.formatNumber(checkValue,"###,###.##" ));
				}else{
                                    rowx.add("");
                                }
				if(salaryLevelDetail.getTakeHomePay()==PstSalaryLevelDetail.YES_TAKE) {
					rowx.add(""+Formater.formatNumber(checkValue,"###,###.##" ));
				}else{
                                    rowx.add("");
                                }	
                                 
                                PaySlipGroup payGroup = hPaySlipGroup.get(""+payComp.getOID());
				rowx.add(payGroup!=null? payGroup.getGroupName() : "?");
				}

			lstData.add(rowx);
		}

	return ctrlist.draw();
		
	 }
%>
<%!
	 public String drawListDeduction (int iCommand,FrmSalaryLevelDetail frmObject, SalaryLevelDetail objEntity, Vector objectClass,  long salaryLevelDetailId,String comp_code,int status) 
	 {
	 	ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("<strong>No Urut</strong>","5%");
		ctrlist.addHeader("<strong>Kode</strong>","10%");
		ctrlist.addHeader("<strong>Nama</strong>","20%");
		ctrlist.addHeader("<strong>Formula(*)</strong>","35%");
		ctrlist.addHeader("<strong>Periode Pembayaran</strong>","10%");
		ctrlist.addHeader("<strong>Periode Akumulasi</strong>","10%");
		ctrlist.addHeader("<strong>Take Home Pay</strong>","5%");
		ctrlist.addHeader("<strong>Same Next Month</strong>","5%");
	
		//ini untuk Periode Pembayaran
		Vector val_period = new Vector();
		Vector key_period = new Vector();
		for (int j = 0; j < PstSalaryLevelDetail.periodKey.length;j++){
			val_period.add(""+j);
			key_period.add(PstSalaryLevelDetail.periodKey[j]);
		}
		
		//ini untuk Take Home Pay
		Vector val_take = new Vector();
		Vector key_take = new Vector();
		for (int m = 0; m < PstSalaryLevelDetail.takeKey.length;m++){
			val_take.add(""+m);
			key_take.add(PstSalaryLevelDetail.takeKey[m]);
		}
		
		//ini untuk SAME NEXT MONTH
		Vector val_copy = new Vector();
		Vector key_copy = new Vector();
		for (int n = 0; n < PstSalaryLevelDetail.copyKey.length;n++){
			val_copy.add(""+n);
			key_copy.add(PstSalaryLevelDetail.copyKey[n]);
		}
		
		Vector val_code = new Vector();    
		Vector key_code = new Vector();
		String wherePayComD = PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+ "=" + PstPayComponent.TYPE_DEDUCTION;
		Vector temp = PstPayComponent.list(0,0,wherePayComD,"");
		key_code.add("");
		val_code.add("");
		for (int j = 0; j < temp.size();j++){
			PayComponent payComponent= (PayComponent)temp.get(j);
			val_code.add(""+payComponent.getOID());
			key_code.add(payComponent.getCompName());
		}
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		for (int i = 0; i < objectClass.size(); i++) {		
			 SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)objectClass.get(i);			 
			 rowx = new Vector();
			 if(salaryLevelDetailId == salaryLevelDetail.getOID())
				 index = i; 
				 
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
			 	// mengambil nama berdasarkan component code
				long compId=0;
				compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
				PayComponent payComp = new PayComponent();
				String compName ="";
				String periodAkumulasi = "";
				int defPeriod = 0;
						try{
							payComp = PstPayComponent.fetchExc(compId);
							compName = payComp.getCompName();
							defPeriod = payComp.getPayPeriod();
							comp_code = payComp.getCompCode();
						}
						catch(Exception e){
					}
				if(defPeriod==0) {
					periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.MINGGUAN];
				}
				if(defPeriod==1) {
					periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.BULANAN];
				}
				if(defPeriod==2) {
					periodAkumulasi = PstPayComponent.defPeriod[PstPayComponent.TAHUNAN];
				}
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX]+"\" value=\""+salaryLevelDetail.getSortIdx()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\""+comp_code+"\" class=\"formElement\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE]+"\" value=\""+comp_code+"\" class=\"formElement\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, String.valueOf(salaryLevelDetail.getComponentId()), val_code, key_code, "onchange=\"javascript:cmdload("+salaryLevelDetail.getComponentId()+")\"", "formElement"));
				//rowx.add(""+ControlCombo.draw(FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID],"formElemen",null, ""+compName, val_code, key_code) );
				rowx.add("<textarea  cols =\"30\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] +"\" class=\"formElement\">"+salaryLevelDetail.getFormula()+"</textarea>");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(salaryLevelDetail.getPayPeriod()), val_period, key_period, "", "formElement"));			
				rowx.add("<input type=\"text\" name=\"akumulasi\" value=\""+periodAkumulasi+"\" class=\"formElement\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(salaryLevelDetail.getTakeHomePay()), val_take, key_take, "", "formElement"));			
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(salaryLevelDetail.getCopyData()), val_copy, key_copy, "", "formElement"));			
				
			}else{	
				rowx.add(""+salaryLevelDetail.getSortIdx());
				rowx.add(salaryLevelDetail.getCompCode());
				// mengambil nama berdasarkan component code
				long compId=0;
				compId = PstPayComponent.getIdName(salaryLevelDetail.getCompCode());
				PayComponent payComp = new PayComponent();
				String compName ="";
				int defPeriod = 0;
						try{
							payComp = PstPayComponent.fetchExc(compId);
							compName = payComp.getCompName();
							defPeriod = payComp.getPayPeriod();
						}
						catch(Exception e){
					}
							
                                String strCompName = (compName==null || compName.length()<1 ) ? "???": compName;
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(salaryLevelDetail.getOID())+"')\">"+strCompName+"</a>");
				rowx.add(salaryLevelDetail.getFormula());				
				rowx.add(String.valueOf(PstSalaryLevelDetail.periodKey[salaryLevelDetail.getPayPeriod()]));				
				if(defPeriod==0) {
					rowx.add(""+PstPayComponent.defPeriod[PstPayComponent.MINGGUAN]);
				}
				if(defPeriod==1) {
					rowx.add(""+PstPayComponent.defPeriod[PstPayComponent.BULANAN]);
				}
				if(defPeriod==2) {
					rowx.add(""+PstPayComponent.defPeriod[PstPayComponent.TAHUNAN]);
				}				
				rowx.add(String.valueOf(PstSalaryLevelDetail.takeKey[salaryLevelDetail.getTakeHomePay()]));
				rowx.add(String.valueOf(PstSalaryLevelDetail.copyKey[salaryLevelDetail.getCopyData()]));
				}

			lstData.add(rowx);
		}
		rowx = new Vector(); 
	if(status==2|| status==0){
    	if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_SORT_IDX]+"\" value=\""+objEntity.getSortIdx()+"\" class=\"formElement\">");
				rowx.add("<input type=\"text\" disabled name=\"levelcode\" value=\"\" class=\"formElement\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE]+"\" value=\""+comp_code+"\" class=\"formElement\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID], null, String.valueOf(objEntity.getComponentId()), val_code, key_code, "onchange=\"javascript:cmdload("+objEntity.getComponentId()+")\"", "formElement")); 
				rowx.add("<textarea  cols =\"30\" type=\"text\" name=\""+frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_FORMULA] +"\" value=\""+objEntity.getFormula()+"\" class=\"formElement\"></textarea>");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_PAY_PERIOD], null, String.valueOf(objEntity.getPayPeriod()), val_period, key_period, "", "formElement"));			
				rowx.add("<input type=\"text\" name=\"akumulasi\" value=\"\" class=\"formElement\">");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_TAKE_HOME_PAY], null, String.valueOf(objEntity.getTakeHomePay()), val_take, key_take, "", "formElement"));			
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COPY_DATA], null, String.valueOf(objEntity.getCopyData()), val_copy, key_copy, "", "formElement"));			
			
		}
	}
		lstData.add(rowx);
	return ctrlist.draw();
		
	 }
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int startDeduction = FRMQueryString.requestInt(request,"startDeduction");
int prevCommand = FRMQueryString.requestInt(request, "prev_command"); 
long oidEmployee = FRMQueryString.requestLong(request, "oidEmployee");
long oidPeriod = FRMQueryString.requestLong(request, "oidPeriod");
 PayPeriod payPeriod = null;
 PayEmpLevel payEmpLevel = null;
  Vector<PayComponentValue> allDetailPay = null; 
if(oidEmployee!=0 && oidPeriod!=0 ){
   
    try{
        payPeriod=PstPayPeriod.fetchExc(oidPeriod);
    }catch(Exception exc){
        out.println("Period not found for OID="+oidPeriod); 
    }
    if(payPeriod!=null){ 
         payEmpLevel = PstPayEmpLevel.getActiveLevelByEmployeeOid(oidEmployee,payPeriod.getStartDate(), payPeriod.getEndDate(),"");
    }
  allDetailPay= PstPaySlip.getAllDetailSalary(payPeriod.getOID(), oidEmployee, null,null) ;   
}

	//menangkap variabel-variabel yang dikirim oleh halaman sebelumnya
	String salary_level = payEmpLevel==null ? "": payEmpLevel.getLevelCode();  //request.getParameter("salary_level");
         String cur = request.getParameter("cur");
	String code_cur ="" ;//payEmpLevel.getCur_Code(); //request.getParameter("code_cur");
	String comp_code = "";//request.getParameter("comp_code");
	


long oidSalaryLevelDetail = 0;
// untuk status benefit=1 dan deduction = 2
int status = FRMQueryString.requestInt(request,"status");


/*variable declaration*/
int recordToGet = 100;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
whereClause=PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" = '"+salary_level+"'";
String whereClauseDeduction = PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_LEVEL_CODE]+" = '"+salary_level+"'";
String orderClause = "";

CtrlSalaryLevelDetail ctrlSalaryLevelDetail = new CtrlSalaryLevelDetail(request);
ControlLine ctrLine = new ControlLine();
Vector listSalaryLevelDetail = new Vector(1,1);
Vector listDeduction= new Vector(1,1);


/*switch statement */
iErrCode = ctrlSalaryLevelDetail.action(iCommand , oidSalaryLevelDetail);
/* end switch*/
FrmSalaryLevelDetail frmSalaryLevelDetail = ctrlSalaryLevelDetail.getForm();

/*count list All kategori*/
int vectSize = PstSalaryLevelDetail.getCountListComponent(salary_level,PstPayComponent.TYPE_BENEFIT);
int vectSizeDeduction = PstSalaryLevelDetail.getCountListComponent(salary_level,PstPayComponent.TYPE_DEDUCTION);

/*switch list kategori*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlSalaryLevelDetail.actionList(iCommand, start, vectSize, recordToGet);
		startDeduction = ctrlSalaryLevelDetail.actionList(iCommand, startDeduction, vectSizeDeduction, recordToGet);

 } 
/* end switch list*/

SalaryLevelDetail salaryLevelDetail = ctrlSalaryLevelDetail.getSalaryLevelDetail();
msgString =  ctrlSalaryLevelDetail.getMessage();

/* get record to display */
//untuk benefit
listSalaryLevelDetail = PstSalaryLevelDetail.listComponent(start,recordToGet,salary_level,PstPayComponent.TYPE_BENEFIT);
//untuk deduction
listDeduction= PstSalaryLevelDetail.listComponent(startDeduction,recordToGet,salary_level,PstPayComponent.TYPE_DEDUCTION);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listSalaryLevelDetail.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
  listSalaryLevelDetail = PstSalaryLevelDetail.listComponent(start,recordToGet,salary_level,PstPayComponent.TYPE_BENEFIT);
}

if (listDeduction.size() < 1 && startDeduction > 0)
    {
             if (vectSizeDeduction - recordToGet > recordToGet)
                            startDeduction = startDeduction - recordToGet;   //go to Command.PREV
             else{
                     startDeduction = 0 ;
                     iCommand = Command.FIRST;
                     prevCommand = Command.FIRST; //go to Command.FIRST
             }
				listDeduction= PstSalaryLevelDetail.listComponent(startDeduction,recordToGet,salary_level,PstPayComponent.TYPE_DEDUCTION);
			   //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
    }
%>
<!-- JSP Block -->
<!-- End of JSP Block -->
<html>

<head>
<title>HARISMA - </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<SCRIPT language=JavaScript>

/*function untuk mengubah judul buku dan harga sesuai dengan barang yang dipilih dalam combo box*/
	function cmdload(oid){
             
               // alert(oid);
		var oidbk = document.frmSalaryLevelDetail.<%=FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMPONENT_ID]%>.value; 
		//alert(oidbk);
                
        if(oid==null)
            return;
             if(oidbk!=null){
                 <% 
                 Vector temp = PstPayComponent.listAll(); 
                 if(temp!=null){ 
                     for(int i=0;i < temp.size(); i++ ){
                    PayComponent paycomponent= (PayComponent)temp.get(i);
                    %>
                 if(oidbk=="<%=paycomponent.getOID()%>"){
                    document.frmSalaryLevelDetail.levelcode.value="<%=paycomponent.getCompCode()%>";
                    document.frmSalaryLevelDetail.comp_code.value="<%=paycomponent.getCompCode()%>"; 
                    document.frmSalaryLevelDetail.akumulasi.value="<%=PstPayComponent.defPeriod[paycomponent.getPayPeriod()]%>";
                    document.frmSalaryLevelDetail.<%=FrmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_COMP_CODE]%>.value  = "<%=paycomponent.getCompCode()%>";
                 }
               
                 <%}
                    }%>
                    
             }
		
        /// document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
        //document.frmSalaryLevelDetail.target = "";
        //document.frmSalaryLevelDetail.submit();
      } 

	function cmdAdd(){
		document.frmSalaryLevelDetail.hidden_salary_detail_id.value="0"; 
		document.frmSalaryLevelDetail.status.value="1";
		document.frmSalaryLevelDetail.command.value="<%=Command.ADD%>";
		document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	}
	function cmdState(oid){
		document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oid; 
		document.frmSalaryLevelDetail.command.value="<%=Command.NONE%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	} 

	function cmdAsk(oidSalaryLevelDetail){
		document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
		document.frmSalaryLevelDetail.command.value="<%=Command.ASK%>";
		document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	} 

	function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmSalaryLevelDetail.command.value="<%=Command.DELETE%>";
			document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
			document.frmSalaryLevelDetail.submit();
		}
	}
	
	function cmdSave(){
		document.frmSalaryLevelDetail.command.value="<%=Command.SAVE%>"; 
		document.frmSalaryLevelDetail.status.value="3";
		document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	}
	
	function cmdEdit(oidSalaryLevelDetail){
		document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
		document.frmSalaryLevelDetail.status.value="0";
		document.frmSalaryLevelDetail.command.value="<%=Command.EDIT%>";
		document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	}
	
	function cmdCancel(oidSalaryLevelDetail){
		document.frmSalaryLevelDetail.hidden_salary_detail_id.value=oidSalaryLevelDetail; 
		document.frmSalaryLevelDetail.command.value="<%=Command.EDIT%>";
		document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
		document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
		document.frmSalaryLevelDetail.submit();
	}
	
	function cmdBack(){
		document.frmSalaryLevelDetail.command.value="<%=Command.BACK%>";
		document.frmSalaryLevelDetail.action="self";
		document.frmSalaryLevelDetail.submit();
	}
	
	function cmdBackSal(){
		document.frmSalaryLevelDetail.command.value="<%=Command.BACK%>";
		document.frmSalaryLevelDetail.action="self";
		document.frmSalaryLevelDetail.submit();
	}
	
	
	function cmdAddD(){
	document.frmSalaryLevelDetail.hidden_salary_detail_id.value="0";
	document.frmSalaryLevelDetail.status.value="2";
	document.frmSalaryLevelDetail.command.value="<%=Command.ADD%>";
	document.frmSalaryLevelDetail.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevelDetail.action="pay_slip_level_val.jsp";
	document.frmSalaryLevelDetail.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
		switch(event.keyCode) {
			case <%=LIST_PREV%>:
				cmdListPrev();
				break;
			case <%=LIST_NEXT%>:
				cmdListNext();
				break;
			case <%=LIST_FIRST%>:
				cmdListFirst();
				break;
			case <%=LIST_LAST%>:
				cmdListLast();
				break;
			default:
				break;
		}
	}    
	
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
	
    function hideObjectForEmployee(){
        
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
	function showObjectForMenu(){
        
    }
</SCRIPT>
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
   
  <tr> 
    <td width="88%" valign="top" align="left"> <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> Salary Component per Salary Level </strong></font> </td>
              </tr>
              <tr> 
                <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; " > 
                          <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top">
                                <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> <form name="frmSalaryLevelDetail" method ="post" action=""> 
                                    <input type="hidden" name="command" value="<%=iCommand%>"> 
                                    <input type="hidden" name="vectSize" value="<%=vectSize%>"> 
                                    <input type="hidden" name="start" value="<%=start%>"> 
                                    <input type="hidden" name="startDeduction" value="<%=startDeduction%>"> 
                                    <input type="hidden" name="<%=frmSalaryLevelDetail.fieldNames[FrmSalaryLevelDetail.FRM_FIELD_LEVEL_CODE] %>" value="<%=salary_level%>"> 
                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>"> 
                                    <input type="hidden" name="salary_level" value="<%=salary_level%>"> 
                                    <input type="hidden" name="cur" value="<%=cur%>"> 
                                    <input type="hidden" name="comp_code" value="<%=comp_code%>"> 
                                 
                                    <input type="hidden" name="code_cur" value="<%=code_cur%>"> 
                                    <input type="hidden" name="hidden_salary_detail_id" value="<%=oidSalaryLevelDetail%>"> 
									<input type="hidden" name="status" value="<%=status%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td class="listtitle">&nbsp;</td>
                                        <td class="listtitle"> Level : <%=salary_level %> - in <%=code_cur %> ( <%=cur%> )</td>
                                      </tr>
                                      <tr> 
                                        <td height="13">&nbsp;</td>
                                        <td height="13">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td class="listtitle">&nbsp;</td>
                                        <td class="listtitle">Benefit List</td>
                                      </tr>
                                      <tr> 
                                        <td>&nbsp;</td>
                                        <td> <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgensell">
                                            <%
											 
										 			try{                                              
                                                %>
                                            <tr> 
                                              <td nowrap align="left"><%= drawList(iCommand,allDetailPay, frmSalaryLevelDetail, salaryLevelDetail,listSalaryLevelDetail,oidSalaryLevelDetail,comp_code,status)%></td>
                                            </tr>
                                            <% 
											  	}catch(Exception exc){
												 System.out.println("exc nieh"+exc);
											  		}
											  %>
                                          </table>
                                      
                                      </tr>
                                      <tr> 
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td class="listtitle">&nbsp; </td>
                                        <td  align="left" height="14" valign="middle" colspan="2" class="listtitle">&nbsp;Deduction 
                                          List </td>
                                      </tr>
                                      <%
                                              try{
											 // out.println("listBenefit"+listBenefit.size());
                                              if((listDeduction == null || listDeduction.size()<1)&&(iCommand == Command.NONE))
                                                   iCommand = Command.ADD;  
                                         %>
                                      <tr> 
                                        <td>&nbsp;</td>
                                        <td> <%= drawListDeduction(iCommand,frmSalaryLevelDetail, salaryLevelDetail,listDeduction,oidSalaryLevelDetail,comp_code,status)%> </td>
                                        <td> </td>
                                        <% 
                                              }catch(Exception exc){ 
                                              }
											   %>
                                      </tr>
                              		 </table>
					<table width="100%" border="0">
                                        <tr>
                                            <td> * Konstanta </td></tr>
                                            <tr>
											
                                          <td> DATE_PRESENT ; DATE_ABSENT ; DATE_LATE ; EARLY_HOME; LATE_EARLY ; DATE_ONLY_IN; DATE_ONLY_OUT; GET_VALUE_MAPPING; 
                                            <br> get schedule symbol (ex: S1,S2,S3)
                                            <br> <%=PstPaySlip.UNPAID_LEAVE %> (<%=PstPaySlip.UNPAID_LEAVE_DESCRIPTION %>) 
                                            <br> <%=PstPaySlip.PAY_COMP_OVERTIME_IDX %> (<%=PstPaySlip.PAY_COMP_DESCRIPTION_OVERTIME_IDX %>) 
                                            <br> <%=PstPaySlip.PAY_COMP_SALARY_TAX %>(<%=PstPaySlip.PAY_COMP_DESCRIPTION_SALARY_TAX %>)
                                            <br> <%=PstPaySlip.PAY_COMP_SALARY_TAX_CODE %>(<%=PstPaySlip.PAY_COMP_DESCRIPTION_SALARY_TAX_CODE %>)
                                                
                                            <br> <%=PstPaySlip.PAY_COMP_REASON_IDX %> (<%=PstPaySlip.PAY_COMP_DESCRIPTION_REASON_IDX %>)
                                            <br> <%=PstPaySlip.PAY_COMP_REASON_TIME %> (<%=PstPaySlip.PAY_COMP_DESCRIPTION_REASON_TIME %>) 
                                            
                                            <br> WIFE; CHILD1; CHILD2; CHILD3 ; SYS_PPH21 
                                            ; <%=SalaryLevelDetail.DAY_PERIOD%>, <%=SalaryLevelDetail.WORK_DAY_PERIOD%>, 
                                            <%=SalaryLevelDetail.DAY_OFF_SCHEDULE%>, <%=SalaryLevelDetail.TOTAL_DAY_OFF_OVERTIME%> <%//,=SalaryLevelDetail.ABSENT_RSN _##%> <br>
                                            , <%=SalaryLevelDetail.DAYWORK_LESS_MNT%>_### ( Day(s) with present OK but, total work hour less than ### minutes </td>
                                        </tr>
											<tr>
											<td>&nbsp;  </td></tr>
											<tr>
											<td> * Input manual : IN_$$$$$ </td></tr>
											</table>
								
							  </form>
                              <!-- #EndEditable --> </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table></td>
</tr> 
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
{script} 
</html>
