<%@ page language="java" %>
<%@ include file = "../../../main/javainit.jsp" %>
<!-- package java -->
<%@ page import = "java.util.*"%>
<!-- package qdep -->
<%@ page import = "com.dimata.util.*"%>
<%@ page import = "com.dimata.qdep.form.*"%>
<%@ page import = "com.dimata.gui.jsp.*"%>
<!-- package prochain -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.clinic.*" %>
<!-- JSP Block -->
<!-- End of JSP Block -->
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_WEEKLY_REPORT); %>
<%
	Date sdate = new Date();
		sdate.setMonth(sdate.getMonth()-1);
		sdate.setDate(15);
	Date edate = new Date();
		edate.setDate(16);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Summary Receipt Report</title>
<SCRIPT language=JavaScript>
function cmdPrint(){	
	var medTypeName = "";
	for(i=0; i<document.frmSumReceipt.med_type.length; i++) {
		if(document.frmSumReceipt.med_type.options[i].selected){
			medTypeName = document.frmSumReceipt.med_type.options[i].text;
		}
	}		

	var deptName = "";
	for(i=0; i<document.frmSumReceipt.dept_id.length; i++) {
		if(document.frmSumReceipt.dept_id.options[i].selected){
			deptName = document.frmSumReceipt.dept_id.options[i].text;
		}
	}		
	
	var dt   =  document.frmSumReceipt.date_periode_dy.value;		
	var month   =  document.frmSumReceipt.date_periode_mn.value;		
	var year    =  document.frmSumReceipt.date_periode_yr.value;

	var dtend   =  document.frmSumReceipt.date_periode_end_dy.value;		
	var monthend   =  document.frmSumReceipt.date_periode_end_mn.value;		
	var yearend    =  document.frmSumReceipt.date_periode_end_yr.value;
	
	var linkPage   = "summ_receipt_buffer.jsp?date_periode_yr=" + year + "&date_periode_mn=" + month +"&date_periode_dy="+dt+
		"&date_periode_end_yr=" + yearend + "&date_periode_end_mn=" + monthend +"&date_periode_end_dy="+dtend+
		"&med_type=" + document.frmSumReceipt.med_type.value + 			 	
		"&dept_id=" + document.frmSumReceipt.dept_id.value + 
		"&med_type_name=" + medTypeName + 			 	
		"&dept_name=" + deptName; 			 						 					 

	/*var linkPage   = "summ_receipt_buffer.jsp?" +
					 "periode_mn=" + document.frmSumReceipt.periode_mn.value + 
					 "&periode_yr=" + document.frmSumReceipt.periode_yr.value + 
					 	 	
					 "&med_type=" + document.frmSumReceipt.med_type.value + 			 	
					 "&dept_id=" + document.frmSumReceipt.dept_id.value + 
					 "&periode_name=" + periodName + 			 	
					 "&med_type_name=" + medTypeName + 			 	
					 "&dept_name=" + deptName; */ 						 					 
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no,scrollbars=no");  					
}  
</SCRIPT>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
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

function showObjectForMenu(){
}
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../../main/mnmain.jsp" %>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Clinic &gt; Summary Receipt<!-- #EndEditable --> </strong></font> 
                </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
								  <form name="frmSumReceipt" method="post">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                      <tr> 
                                        <td width="8%">&nbsp;</td>
                                        <td width="1%">&nbsp;</td>
                                        <td width="91%">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td width="8%">Period</td>
                                          <td width="1%">:</td>
                                          <td width="91%"><%=ControlDate.drawDateWithStyle("date_periode", sdate,5, -10,"formElemen", "")%> to <%=ControlDate.drawDateWithStyle("date_periode_end", edate, 5, -10,"formElemen", "")%><!--<%=ControlDate.drawDateMY("periode", new Date(),"MMMM","","","",-5,5)%>--></td>
                                      </tr>
                                      <tr> 
                                        <%
										    Vector vectMedicalType = PstMedicalType.listAll();
											Vector medicaltypeid_value = new Vector(1,1);
											Vector medicaltypeid_key = new Vector(1,1);
											if(vectMedicalType!=null && vectMedicalType.size()>0){
												for(int i=0; i<vectMedicalType.size(); i++){
													MedicalType medicalType = (MedicalType)vectMedicalType.get(i);
													medicaltypeid_value.add(""+medicalType.getOID());
													medicaltypeid_key.add(medicalType.getTypeCode()+" "+medicalType.getTypeName()); 
												}
											}													
											%>
                                        <td width="8%">Medical Type</td>
                                        <td width="1%">:</td>
                                        <td width="91%"><%=ControlCombo.draw("med_type",null, null, medicaltypeid_value , medicaltypeid_key, "formElemen", "")%></td>
                                      </tr>
                                      <tr> 
                                        <td width="8%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                        <td width="1%">:</td>
                                        <td width="91%"> 
                                          <%
											Vector dept_value = new Vector(1,1);
											Vector dept_key = new Vector(1,1);
											Vector listDept = PstDepartment.list(0,0,""," DEPARTMENT ");
											//dept_key.add("All Department");
											//dept_value.add("0");
											for (int i = 0; i < listDept.size(); i++) {
												Department dept = (Department) listDept.get(i);
												dept_key.add(dept.getDepartment());
												dept_value.add(String.valueOf(dept.getOID()));
											}
											%>
                                          <%= ControlCombo.draw("dept_id","formElemen",null,"",dept_value,dept_key) %></td>
                                      </tr>
                                      <tr> 
                                        <td width="8%">&nbsp;</td>
                                        <td width="1%">&nbsp;</td>
                                        <td width="91%">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td colspan="3"> 
                                            <table width="24%" border="0" cellspacing="1" cellpadding="1">
                                              <tr> 
                                              <td width="13%"><a href="javascript:cmdPrint()"><img src="../../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="87%"><b><a href="javascript:cmdPrint()" class="buttonlink">Print 
                                                  Summary Receipt Report (B)</a></b> 
                                                </td>
                                            </tr>
                                          </table>
                                        </td>
                                      </tr>
                                    </table>                                    
                                    </form>
                                    <!-- #EndEditable --> </td>
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
      <%@ include file = "../../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
