<%-- 
    Document   : med_budget
    Created on : Feb 21, 2009, 1:59:13 PM
    Author     : Kartika(2009-02-21)   
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_MEDICAL, AppObjInfo.OBJ_MEDICAL_GROUP); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidMedicalCase = FRMQueryString.requestLong(request, "hidden_medical_level_id");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";

CtrlMedicalCase ctrlMedLevel = new CtrlMedicalCase(request);
ControlLine ctrLine = new ControlLine();
Vector listMedicalCase = new Vector(1,1);




/*switch statement */
//iErrCode = ctrlMedLevel.action(iCommand , oidMedicalCase);
/* end switch*/

FrmMedicalCase frmMedLevel = ctrlMedLevel.getForm();
MedicalCase medicalCase = ctrlMedLevel.getMedicalCase();
msgString =  ctrlMedLevel.getMessage();

/*count list All MedicalCase*/
int vectSize = PstMedicalCase.getCount(whereClause);

/*switch list MedicalCase*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlMedLevel.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
orderClause =  PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];
listMedicalCase = PstMedicalCase.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listMedicalCase.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
                 start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listMedicalCase = PstMedicalCase.list(start,recordToGet, whereClause , orderClause);
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Medical Budget</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmmedicallevel.hidden_medical_level_id.value="0";
	document.frmmedicallevel.command.value="<%=Command.ADD%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdAsk(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.ASK%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdConfirmDelete(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.DELETE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdSave(){
	document.frmmedicallevel.command.value="<%=Command.SAVE%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdEdit(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdCancel(oidMedicalCase){
	document.frmmedicallevel.hidden_medical_level_id.value=oidMedicalCase;
	document.frmmedicallevel.command.value="<%=Command.EDIT%>";
	document.frmmedicallevel.prev_command.value="<%=prevCommand%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdBack(){
	document.frmmedicallevel.command.value="<%=Command.BACK%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdListFirst(){
	document.frmmedicallevel.command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.FIRST%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdListPrev(){
	document.frmmedicallevel.command.value="<%=Command.PREV%>";
	document.frmmedicallevel.prev_command.value="<%=Command.PREV%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdListNext(){
	document.frmmedicallevel.command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.prev_command.value="<%=Command.NEXT%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

function cmdListLast(){
	document.frmmedicallevel.command.value="<%=Command.LAST%>";
	document.frmmedicallevel.prev_command.value="<%=Command.LAST%>";
	document.frmmedicallevel.action="med_budget.jsp";
	document.frmmedicallevel.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidMedicalCase){
	document.frmimage.hidden_medical_level_id.value=oidMedicalCase;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="med_budget.jsp";
	document.frmimage.submit();
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
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
                  Clinic &gt; Medical Budget <!-- #EndEditable --> 
            </strong></font>	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmmedicallevel" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_medical_level_id" value="<%=oidMedicalCase%>">
                                      
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">&nbsp;  
                                                </td>
                                              </tr>
											  <% if((listMedicalCase == null || listMedicalCase.size()<1)&& (iCommand == Command.NONE)){%>
											  <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td> 
                                              </tr>
											  <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="comment"> 
                                                  &nbsp;&nbsp;No Medical Case available </td>
f                                              </tr>
                                              <% }else{%>
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp; HOSPITAL SERVICE AND OTHER ENTITLEMENT
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                <% // drawList(iCommand,frmMedLevel, medicalCase,listMedicalCase,oidMedicalCase)%>
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="listarea">
                                                  <tr>
                                                    <td class="listgenedit">
                                                    <%
                                                       //String orderBy = ""+ PstMedicalCase.fieldNames[PstMedicalCase.FLD_CASE_GROUP]+ ", "+PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];                                                       
                                                       String orderBy = " "+PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];                                                       
                                                       Vector  medCaseList = PstMedicalCase.list(0,200, "", orderBy);                                                       
                                                       orderBy = ""+ PstMedicalCase.fieldNames[PstMedicalCase.FLD_SORT_NUMBER];
                                                       Vector medLevelList = PstMedicalLevel.list(0,20, "", orderBy);
                                                       int iMedLevel =0;
                                                       if(medLevelList!=null){
                                                           iMedLevel = medLevelList.size();
                                                       }
                                                       
                                                       int iMedCase = 0;
                                                       if(medCaseList!=null){
                                                           iMedCase = medCaseList.size();
                                                       }
                                                       
                                                       
                                                    %>    
                                                    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="listgen">
                                                          
                                                      <tr>
                                                        <td width="18%" rowspan="2" class="listgentitle"><div align="center">CASE </div>
                                                        <div align="center">TREATMENT</div></td>
                                                        <td colspan="<%=(iMedLevel>0?iMedLevel:1)%>" class="listgentitle">
                                                        <div align="center">LEVELS &amp; ENTITLEMENT </div>                                                          </td>
                                                        </tr>
                                                      <tr>
                                                        <%if(medLevelList!=null){
                                                            
                                                            for(int iL = 0; iL<iMedLevel;iL++){
                                                                MedicalLevel medLev = (MedicalLevel) medLevelList.get(iL);
                                                            %>  
                                                                <td width="15%" class="listgentitle"><div align="center"><%=medLev.getLevelName()%><br>
                                                                  <%=medLev.getLevelClass()%> </div></td>
                                                          <%   }
                                                            } else {%>
                                                          <td width="42%" class="listgentitle"><div align="center"> Please Input Medical Case/Treatment<br></div></td>                                                          
                                                           <%}%>

                                                      </tr>
                                                      <%
                                                         String sLastMedCaseGroup="";
                                                         int medBudgetIdx=0;
                                                         for(int iC=0;iC<iMedCase;iC++){
                                                              MedicalCase medCase= (MedicalCase) medCaseList.get(iC);
                                                              if(medCase!=null){ 
                                                                  if(sLastMedCaseGroup.compareTo(""+medCase.getCaseGroup())!=0){
                                                                  sLastMedCaseGroup=medCase.getCaseGroup();
                                                                  %> 
                                                                    <tr>
                                                                        <td class="listgen"><strong><%=sLastMedCaseGroup%></strong></td>
                                                                        <td class="listgen"><div align="right"></div></td>
                                                                        <td class="listgen"><div align="right"></div></td>
                                                                        <td class="listgen"><div align="right"></div></td>
                                                                    </tr>
                                                                    <%} %>
                                                                    <tr>
                                                                        <td class="listgen"><%=medCase.getCaseName()%> 
                                                                                <% if(medCase.getMaxUse()>0 || medCase.getMinTakenBy()>0){
                                                                                      out.print("(");
                                                                                      if(medCase.getMaxUse()>0 && medCase.getMaxUsePeriod()>0){
                                                                                          out.print("Max. "+medCase.getMaxUse()+" "+MedicalCase.qtyUnitTitle[medCase.getMaxUsePeriod()]);
                                                                                      }
                                                                                      if(medCase.getMinTakenBy()>0 && medCase.getMinTakenByPeriod()>0){
                                                                                          out.print(""+((medCase.getMaxUse()>0 && medCase.getMaxUsePeriod()>0)?" & ":"")+" Min. taken "+medCase.getMinTakenBy()+" "+MedicalCase.PeriodTitle[medCase.getMinTakenByPeriod()]);
                                                                                          ;
                                                                                      }
                                                                                        
                                                                                      out.print(")");
                                                                                    }                                                                                     
                                                                                %> </td>

                                                                        <%if(medLevelList!=null){

                                                                            for(int iL = 0; iL<iMedLevel;iL++){
                                                                                MedicalLevel medLev = (MedicalLevel) medLevelList.get(iL);
                                                                                MedicalBudget medBudget = null;
                                                                                Vector vctMedBudget = new Vector(1,1);
                                                                                boolean medBudgetExists=true;
                                                                                try{
                                                                                   medBudget = PstMedicalBudget.fetchExc(medLev.getOID(), medCase.getOID());
                                                                                   }catch(Exception exc){                                                                                    
                                                                                  }
                                                                                if(medBudget==null || medBudget.getMedicalLevelId()==0 || medBudget.getMedicalCaseId()==0){
                                                                                    medBudgetExists=false;
                                                                                    medBudget = new MedicalBudget();
                                                                                    medBudget.setMedicalLevelId(medLev.getOID());
                                                                                    medBudget.setMedicalCaseId(medCase.getOID());
                                                                                }
                                                                                
                                                                                if(iCommand==Command.SAVE){ 
                                                                                    double budget = FRMQueryString.requestDouble(request, FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_MEDICAL_BUDGET]+"_"+medBudgetIdx);
                                                                                    int usePeriod = FRMQueryString.requestInt(request, FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_USE_PERIOD]+"_"+medBudgetIdx);
                                                                                    int usePax =  FRMQueryString.requestInt(request, FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_USE_PAX]+"_"+medBudgetIdx);
                                                                                    medBudget.setBudget(budget);
                                                                                    medBudget.setUsePeriod(usePeriod);
                                                                                    medBudget.setUsePax(usePax);
                                                                                    try{
                                                                                    if(medBudgetExists){
                                                                                        PstMedicalBudget.updateExc(medBudget);
                                                                                      } else{
                                                                                        PstMedicalBudget.insertExc(medBudget);
                                                                                      }
                                                                                    } catch(Exception exc){}
                                                                                            
                                                                                }                                                                                                                                                                
                                                                                  %>  <td class="listgen" nowrap>
                                                                                       <div align="right">
                                                                                       <% String inputName = FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_MEDICAL_BUDGET]+"_"+medBudgetIdx; %>   
                                                                                        <input name="<%=inputName%>" value="<%=(""+ Formater.formatNumber(medBudget.getBudget(),"###,###.##"))%>" type="text" size="9" maxlength="15">/
                                                                                        <% inputName = FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_USE_PERIOD]+"_"+medBudgetIdx; %>
                                                                                        <%=com.dimata.gui.jsp.ControlCombo.drawWithStyle(inputName,"",""+medBudget.getUsePeriod(),MedicalBudget.getPeriodIndexString(),MedicalBudget.getPeriodTitle(),"combo","combo")%>                                                                                        
                                                                                        <% inputName = FrmMedicalBudget.fieldNames[FrmMedicalBudget.FRM_FIELD_USE_PAX]+"_"+medBudgetIdx; %>
                                                                                        <%=com.dimata.gui.jsp.ControlCombo.drawWithStyle(inputName,"",""+medBudget.getUsePax(),MedicalBudget.getPaxIndexString(),MedicalBudget.getPaxTitle(),"combo","combo")%>                                                                                        
                                                                                       </div>
                                                                                      </td>                                                                                
                                                                                <% 
                                                                                medBudgetIdx=medBudgetIdx+1;
                                                                               }
                                                                            } else {%>
                                                                              <td class="listgen"><div align="right"> &nbsp;</div></td>                                                          
                                                                           <%}%>
                                                                    </tr>                                                                    
                                                                  <%
                                                              }
                                                            %>
                                                      <%
                                                          }
                                                      %>
                                                      
                                                    </table></td>
                                                  </tr>
                                                </table></td>
                                              </tr>
                                              <% 
											  	}catch(Exception exc){ 
                                                                                                    System.out.println(exc);
											  	}
											 }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images/ctr_line");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											 
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp; </td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									
									
									
									

									
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
                                                                                ctrLine.setAddCaption("");
                                                                                ctrLine.setBackCaption("");
									

									if( (privAdd == true  || privUpdate == true)){
                                                                            ctrLine.setCommandStyle("buttonlink");								
                                                                            ctrLine.setSaveCaption("Save Data");
                                                                        }else{
										ctrLine.setSaveCaption("");
                                                                                ctrLine.setSaveCommand("");
									}
                                                                        iCommand= Command.EDIT;   
									%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>  
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->&nbsp;{script}<!-- #EndEditable -->
<!-- #EndTemplate --></html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>
