<%-- 
    Document   : outSourceEvaluation
    Created on : Sep 30, 2011, 3:56:51 PM
    Author     : Wiweka
--%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.outsource.OutSourceEvaluation"%>
<%@page import="com.dimata.harisma.entity.outsource.PstOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.form.outsource.FrmOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.form.outsource.CtrlOutSourceEvaluation"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  outsource_evaluation.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Ari_20110930
             * @version  		: -
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
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_OUTSOURCE, AppObjInfo.G2_OUTSOURCE_DATA_ENTRY, AppObjInfo.OBJ_OUTSOURCE_FORM_EVALUASI  );%>

<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%!

	public String drawList(int iCommand,FrmOutSourceEvaluation frmObject, OutSourceEvaluation outSourceEvaluationObj, Vector objectClass ,  long docOutSourceEvaluation)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("No","10%");
		ctrlist.addHeader("Lokasi","20%");
		ctrlist.addHeader("Periode","20%");
                ctrlist.addHeader("Tgl Pembuatan","20%");
                ctrlist.addHeader("Oleh","20%");
                ctrlist.addHeader("Status","20%");
                ctrlist.addHeader("Disetujui Oleh","20%");
                ctrlist.addHeader("Tanggal Persetujuan","20%");
                ctrlist.addHeader("Note","20%");
                ctrlist.addHeader("Detail","20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
	

                Vector div_value = new Vector(1, 1);
                Vector div_key = new Vector(1, 1);
                Vector listdiv = PstDivision.list(0, 0, "", "DIVISION");
                div_value.add(""+0);
                div_key.add("select");
                for (int i = 0; i < listdiv.size(); i++) {
                    Division div = (Division) listdiv.get(i);
                    div_key.add(div.getDivision());
                    div_value.add(String.valueOf(div.getOID()));
                }
                
                Vector period_value = new Vector(1, 1);
                Vector period_key = new Vector(1, 1);
                Vector listperiod = PstPayPeriod.list(0, 0, "", "");
                period_value.add(""+0);
                period_key.add("select");
                for (int i = 0; i < listperiod.size(); i++) {
                    PayPeriod period = (PayPeriod) listperiod.get(i);
                    period_key.add(period.getPeriod());
                    period_value.add(String.valueOf(period.getOID()));
                }
                
                Hashtable Hlistlistperiod = PstPayPeriod.hashlistTblPeriodName(0, 0, "", "");
                Hashtable HlistlistDivision = PstDivision.listMapDivisionName(0, 0, "", "");
                
             Vector val_status = new Vector(1,1);
             Vector key_status = new Vector(1,1);
             val_status.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT) ;
             key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT] );
             val_status.add(""+I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
             key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED] );
             val_status.add(""+I_DocStatus.DOCUMENT_STATUS_FINAL);
             key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);

             Hashtable hashstatus = new Hashtable();
             hashstatus.put(I_DocStatus.DOCUMENT_STATUS_DRAFT, I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);
             hashstatus.put(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED, I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
             hashstatus.put(I_DocStatus.DOCUMENT_STATUS_FINAL, I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                
                
		for (int i = 0; i < objectClass.size(); i++) {
			 OutSourceEvaluation outSourceEvaluation = (OutSourceEvaluation)objectClass.get(i);
                    Employee employee = new Employee();
                    try{
                      employee = PstEmployee.fetchExc(outSourceEvaluation.getCreatedById());
                    } catch (Exception e){
                        
                    }
                    Employee employeeApproved = new Employee();
                    try{
                      employeeApproved = PstEmployee.fetchExc(outSourceEvaluation.getApprovedById());
                    } catch (Exception e){
                        
                    }
                    
			 rowx = new Vector();
                         //cek apakah ada doc master template
                      
                         rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(outSourceEvaluation.getOID())+"')\">"+(i+1)+"</a>");
				        
			 if((iCommand == Command.EDIT || iCommand == Command.ASK) && (outSourceEvaluation.getOID() == docOutSourceEvaluation)){				
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(outSourceEvaluation.getDivisionId()), div_value, div_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_PERIOD_ID], "formElemen", null, String.valueOf(outSourceEvaluation.getPeriodId()), period_value, period_key, ""));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_DATE], outSourceEvaluation.getCreatedDate(), 5, -40, "formElemen") +" "+ frmObject.getErrorMsg(FrmOutSourceEvaluation.FRM_FIELD_CREATED_DATE));
                                //rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID] +"\" value=\""+outSourceEvaluation.getCreatedById()+"\" class=\"elemenForm\">");
				
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\""+employee.getFullName()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_DEPARTMENT\" value=\""+employee.getDepartmentId()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_NUMBER\" value=\""+employee.getEmployeeNum()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]+"\" value=\""+outSourceEvaluation.getCreatedById()+"\" class=\"formElemen\" ><a href=\"javascript:cmdSearchEmpCreated()\">employee</a>");
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_STATUS_DOC], "formElemen", null, String.valueOf(outSourceEvaluation.getStatusDoc()), val_status, key_status, ""));
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME_APPROVED\" value=\""+employeeApproved.getFullName()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_DEPARTMENT_APPROVED\" value=\""+employee.getDepartmentId()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_NUMBER_APPROVED\" value=\""+employeeApproved.getEmployeeNum()+"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_BY_ID]+"\" value=\""+outSourceEvaluation.getApprovedById()+"\" class=\"formElemen\" ><a href=\"javascript:cmdSearchEmpApproved()\">employee</a>");
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_DATE], outSourceEvaluation.getApprovedDate(), 5, -40, "formElemen") +" "+ frmObject.getErrorMsg(FrmOutSourceEvaluation.FRM_FIELD_APPROVED_DATE));
                                
                                rowx.add("<textarea name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_NOTE] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+outSourceEvaluation.getNote()+"</textarea>");
                                
                                rowx.add("<a href=\"javascript:cmdDetail('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Detail</a>");
                                //rowx.add("<a href=\"javascript:cmdExpense('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Expense || </a><a href=\"javascript:cmdTemplate('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Template || </a><a href=\"javascript:cmdFlow('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Flow</a> || </a><a href=\"javascript:cmdAction('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Action</a>");
                                
                         }else{
                                rowx.add(""+HlistlistDivision.get(outSourceEvaluation.getDivisionId()));
                                rowx.add(""+Hlistlistperiod.get(outSourceEvaluation.getPeriodId()));
                                rowx.add(""+ outSourceEvaluation.getCreatedDate());
                                rowx.add(""+employee.getFullName());
				rowx.add(""+hashstatus.get(outSourceEvaluation.getStatusDoc()));
                                rowx.add(""+employeeApproved.getFullName());
				rowx.add(""+outSourceEvaluation.getApprovedDate());
                                
                                rowx.add(""+outSourceEvaluation.getNote());
                                rowx.add("<a href=\"javascript:cmdDetail('"+String.valueOf(outSourceEvaluation.getOID())+"')\">Detail</a>");
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();

		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add("-");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(outSourceEvaluationObj.getDivisionId()), div_value, div_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_PERIOD_ID], "formElemen", null, String.valueOf(outSourceEvaluationObj.getPeriodId()), period_value, period_key, ""));
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_DATE], outSourceEvaluationObj.getCreatedDate(), 5, -40, "formElemen") +" "+ frmObject.getErrorMsg(FrmOutSourceEvaluation.FRM_FIELD_CREATED_DATE));
                                //rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID] +"\" value=\""+outSourceEvaluationObj.getCreatedById()+"\" class=\"elemenForm\">");
				//rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]+"\" value=\"\" class=\"formElemen\" ><a href=\"javascript:cmdSearchEmp()\">add employee</a>");
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_DEPARTMENT\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_NUMBER\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]+"\" value=\""+outSourceEvaluationObj.getCreatedById()+"\" class=\"formElemen\" ><a href=\"javascript:cmdSearchEmpCreated()\">employee</a>");
                                
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_STATUS_DOC], "formElemen", null, String.valueOf(outSourceEvaluationObj.getStatusDoc()), val_status, key_status, ""));
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME_APPROVED\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_DEPARTMENT_APPROVED\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\"EMP_NUMBER_APPROVED\" value=\"\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_BY_ID]+"\" value=\""+outSourceEvaluationObj.getApprovedById()+"\" class=\"formElemen\" ><a href=\"javascript:cmdSearchEmpApproved()\">employee</a>");
                                rowx.add(ControlDate.drawDateWithStyle(frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_DATE], outSourceEvaluationObj.getDateOfEval(), 5, -40, "formElemen") +" "+ frmObject.getErrorMsg(FrmOutSourceEvaluation.FRM_FIELD_APPROVED_DATE));
                                
                                rowx.add("<textarea name=\""+frmObject.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_NOTE] +"\" class=\"elemenForm\" rows=\"3\" cols=\"35\">"+outSourceEvaluationObj.getNote()+"</textarea>");
                                rowx.add("-");
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidOutSourceEvaluation = FRMQueryString.requestLong(request, "hidden_outSourceEvaluation_id");

            
            long companyId = FRMQueryString.requestLong(request, "companyId");
            long divisionId = FRMQueryString.requestLong(request, "divisionId");
            long startPeriodId = FRMQueryString.requestLong(request, "startPeriodId");
            

            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            if (divisionId > 0 && startPeriodId==0){
                whereClause = ""+PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DIVISION_ID]+ " = " + divisionId ;
            } else if (divisionId == 0 && startPeriodId>0){
                whereClause = ""+PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_PERIOD_ID]+ " = " + startPeriodId ;
            } else if (divisionId > 0 && startPeriodId>0){
                whereClause = ""+PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_PERIOD_ID]+ " = " + startPeriodId + " AND " +PstOutSourceEvaluation.fieldNames[PstOutSourceEvaluation.FLD_DIVISION_ID]+ " = " + divisionId ;
            }             
            
            CtrlOutSourceEvaluation ctrlOutSourceEvaluation = new CtrlOutSourceEvaluation(request);
            ControlLine ctrLine = new ControlLine();
            Vector listOutSourceEvaluation = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlOutSourceEvaluation.action(iCommand, oidOutSourceEvaluation);
            /* end switch*/
            FrmOutSourceEvaluation frmOutSourceEvaluation = ctrlOutSourceEvaluation.getForm();

            /*count list All Position*/
            int vectSize = PstOutSourceEvaluation.getCount(whereClause);

            OutSourceEvaluation outSourceEvaluation = ctrlOutSourceEvaluation.getOutSourceEvaluation();
            msgString = ctrlOutSourceEvaluation.getMessage();

            /*switch list OutSourceEvaluation*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstOutSourceEvaluation.findLimitStart(outSourceEvaluation.getOID(),recordToGet, whereClause);
                oidOutSourceEvaluation = outSourceEvaluation.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlOutSourceEvaluation.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listOutSourceEvaluation = PstOutSourceEvaluation.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listOutSourceEvaluation.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listOutSourceEvaluation = PstOutSourceEvaluation.list(start, recordToGet, whereClause, orderClause);
            }

                

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>DIMATA HARISMA - Outsource Evaluation</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value="0";
                document.frmoutSourceEvaluation.command.value="<%=Command.ADD%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdAsk(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.ASK%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdConfirmDelete(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.DELETE%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }
            function cmdSave(){
                document.frmoutSourceEvaluation.command.value="<%=Command.SAVE%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdEdit(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdCancel(oidOutSourceEvaluation){
                document.frmoutSourceEvaluation.hidden_outSourceEvaluation_id.value=oidOutSourceEvaluation;
                document.frmoutSourceEvaluation.command.value="<%=Command.EDIT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdBack(){
                document.frmoutSourceEvaluation.command.value="<%=Command.BACK%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListFirst(){
                document.frmoutSourceEvaluation.command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.FIRST%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListPrev(){
                document.frmoutSourceEvaluation.command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.PREV%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListNext(){
                document.frmoutSourceEvaluation.command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.NEXT%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdListLast(){
                document.frmoutSourceEvaluation.command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=Command.LAST%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }
            
            function cmdUpdateDiv(){
                document.frmoutSourceEvaluation.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp";
                document.frmoutSourceEvaluation.submit();
            }

            function cmdSearch(nilai){
                document.frmoutSourceEvaluation.command.value="<%=Command.SEARCH%>";
                document.frmoutSourceEvaluation.prev_command.value="<%=prevCommand%>";
                document.frmoutSourceEvaluation.action="outsource_evaluation.jsp?type="+nilai;
                document.frmoutSourceEvaluation.submit();
            }
            
        //function cmdSearchEmp(){
        //window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmOutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        //}
        
        function cmdSearchEmpCreated(){
        window.open("<%=approot%>/employee/search/search.jsp?formName=frmoutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_CREATED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        
        function cmdSearchEmpApproved(){
        window.open("<%=approot%>/employee/search/searchApproved.jsp?formName=frmoutSourceEvaluation&empPathId=<%=frmOutSourceEvaluation.fieldNames[FrmOutSourceEvaluation.FRM_FIELD_APPROVED_BY_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        
        function setEmployee(){
        window.open("<%=approot%>/employee/search/search.jsp?formName=frmoutSourceEvaluation&empPathId=EMPID", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        function cmdDetail(oidOutSourceEvaluation){
	window.open("<%=approot%>/outsource/outsource_evaluation_provider.jsp?hidden_outSourceEvaluation_id="+oidOutSourceEvaluation+"", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
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

                //-------------- script control line -------------------
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

        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
                    <%@ include file = "../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="10" valign="middle">

                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            <tr>
                <td width="88%" valign="top" align="left">
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                    <!-- #BeginEditable "contenttitle" -->
                                                    Master Data &gt; <%=dictionaryD.getWord(I_Dictionary.COMPANY)%><!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmoutSourceEvaluation" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_outSourceEvaluation_id" value="<%=oidOutSourceEvaluation%>">
                                                                                    <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                      <tr> 
                                                                                                          <td  nowrap="nowrap">Perusahaan    : </td><td>   
                                                                                                            <%
                                                                                                            Vector comp_value = new Vector(1, 1);
                                                                                                            Vector comp_key = new Vector(1, 1);
                                                                                                            Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                            comp_value.add(""+0);
                                                                                                            comp_key.add("select");
                                                                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                                                                Company div = (Company) listComp.get(i);
                                                                                                                comp_key.add(div.getCompany());
                                                                                                                comp_value.add(String.valueOf(div.getOID()));
                                                                                                            }

                                                                                                            %>
                                                                                                            <%= ControlCombo.draw("companyId", "formElemen", null, "" + companyId, comp_value, comp_key,"onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                                                                                        </td>

                                                                                                        </tr>
                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">Lokasi   :   </td><td>  
                                                                                                            <%
                                                                                                                Vector div_value = new Vector(1, 1);
                                                                                                                Vector div_key = new Vector(1, 1);
                                                                                                                //update by satrya 2013-09-07
                                                                                                                   div_key.add("-select-");
                                                                                                                    div_value.add("0");
                                                                                                                    String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  companyId ;//oidCompany; 
                                                                                                                    Vector listDiv = PstDivision.list(0, 0, strWhere, " DIVISION ");
                                                                                                                for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                    Division div = (Division) listDiv.get(i);
                                                                                                                    div_key.add(div.getDivision());
                                                                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                                                                    
                                                                                                                }

                                                                                                            %>
                                                                                                            <%= ControlCombo.draw("divisionId", "formElemen", null, "" + divisionId, div_value, div_key,"")%> 
                                                                                                       
                                                                                                        </td>

                                                                                                        </tr>

                                                                                                        
                                                                                                        <tr> 

                                                                                                        <td  nowrap="nowrap">Start Periode   :  </td>
                                                                                                        <td>   
                                                                                                            
                                                                                                            <%
                                                                                                            Vector perValue = new Vector(1, 1);
                                                                                                            Vector perKey = new Vector(1, 1);
                                                                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                            for (int r = 0; r < listPeriod.size(); r++) {
                                                                                                                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                                                                perValue.add("" + payPeriod.getOID());
                                                                                                                perKey.add(payPeriod.getPeriod());
                                                                                                            }
                                                                                                             %> 
                                                                                                            <%=ControlCombo.draw("startPeriodId", null, "" + startPeriodId, perValue, perKey, "")%>

                                                                                                        </td>
                                                                                                       
                                                                                                        </tr>
                                                                                                      
                                                                                                         <tr align="left" valign="top">
                                                                                                        <td>&nbsp;</td>
                                                                                                    </tr>
                                                                                                      <tr>
                                                                                                            <td width="39%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search "></a>
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                            <a href="javascript:cmdSearch()">Search </a></td>  
                                                                                                           
                                                                                                      </tr>
                                                                                                      </table>
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8"  colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;OutSourceEvaluation List </td>
                                                                                                    </tr>
                                                                                                
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="22" valign="middle" colspan="3">
                                                                                                            
                                                                                                                                                                                
                                                                                                            <%=drawList(iCommand ,frmOutSourceEvaluation ,outSourceEvaluation,listOutSourceEvaluation, oidOutSourceEvaluation)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                            
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td height="8" align="left" colspan="3" class="command">
                                                                                                            <span class="command">
                                                                                                                <%
                                                                                                                            int cmd = 0;
                                                                                                                            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                                                                                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                                                                                cmd = iCommand;
                                                                                                                            } else {
                                                                                                                                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                                                                                    cmd = Command.FIRST;
                                                                                                                                } else {
                                                                                                                                    cmd = prevCommand;
                                                                                                                                }
                                                                                                                            }
                                                                                                                %>
                                                                                                                <% ctrLine.setLocationImg(approot + "/images");
                                                                                                                            ctrLine.initDefault();
                                                                                                                %>
                                                                                                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%>
                                                                                                            </span> </td>
                                                                                                    </tr>
                                                                                                    
                                                                                                    
                                                                                                       <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmOutSourceEvaluation.errorSize()<1)){
                                                                                                    if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmOutSourceEvaluation.errorSize() < 1)) {
                                                                                                        if (privAdd) {%>
                                                                                                    <tr align="left" valign="top">
                                                                                                        <td>
                                                                                                            <table cellpadding="0" cellspacing="0" border="0">
                                                                                                                <tr>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                                                                                    <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                                                                                    <td height="22" valign="middle" colspan="3" width="951">
                                                                                                                        <a href="javascript:cmdAdd()" class="command">Add
                                                                                                                            New </a> </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}
                                                                                                      }%>
                                                                                                </table>
                                                                                                <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmOutSourceEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    
                                                                                                    
                                                                                                    <tr>
                                                                                                        <td colspan="2">
                                                                                                            <%
                                                                                                                ctrLine.setLocationImg(approot + "/images");
                                                                                                                ctrLine.initDefault();
                                                                                                                ctrLine.setTableWidth("80%");
                                                                                                                String scomDel = "javascript:cmdAsk('" + oidOutSourceEvaluation + "')";
                                                                                                                String sconDelCom = "javascript:cmdConfirmDelete('" + oidOutSourceEvaluation + "')";
                                                                                                                String scancel = "javascript:cmdEdit('" + oidOutSourceEvaluation + "')";
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setCommandStyle("buttonlink");
                                                                                                                ctrLine.setBackCaption("Back to List");
                                                                                                                ctrLine.setSaveCaption("Save");
                                                                                                                ctrLine.setConfirmDelCaption("Yes Delete");
                                                                                                                ctrLine.setDeleteCaption("Delete");

                                                                                                                if (privDelete) {
                                                                                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                                                                                    ctrLine.setDeleteCommand(scomDel);
                                                                                                                    ctrLine.setEditCommand(scancel);
                                                                                                                } else {
                                                                                                                    ctrLine.setConfirmDelCaption("");
                                                                                                                    ctrLine.setDeleteCaption("");
                                                                                                                    ctrLine.setEditCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false && privUpdate == false) {
                                                                                                                    ctrLine.setSaveCaption("");
                                                                                                                }

                                                                                                                if (privAdd == false) {
                                                                                                                    ctrLine.setAddCaption("");
                                                                                                                }

                                                                                                                if (iCommand == Command.ASK) {
                                                                                                                    ctrLine.setDeleteQuestion(msgString);
                                                                                                                }
                                                                                                            %>
                                                                                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                <%}%>
                                                                                            </td>
                                                                                        </tr>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        
                                                                                    </table>
                                                                                </form>
                                                                                <!-- #EndEditable -->
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp; </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

