<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.search.*,
                  com.dimata.harisma.form.search.*,
                  com.dimata.harisma.entity.employee.*,
                  com.dimata.harisma.entity.masterdata.*,
                  com.dimata.harisma.session.employee.*,               
                  com.dimata.harisma.entity.masterdata.*" %>
                  
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>

<!-- JSP Block -->

<%-- YANG INI BELUM DIEDIT --%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!   
public String drawList(Vector objectClass, SrcEmpRace srcEmpRace)
{
	Vector vListCategory = PstEmpCategory.listAll();
	ControlList ctrlist = new ControlList();
        
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
        
        // creating header
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Race","22%","2","0","center","left");	
        
	if(vListCategory!=null && vListCategory.size()>0)
	{
		int numCategory = 0;
                int width = 0;                
                boolean counted = false;
            
                for(int i=0;i<vListCategory.size();i++)
		{
			EmpCategory objCateg = (EmpCategory)vListCategory.get(i);
			String category = objCateg.getEmpCategory();
			
                        if(counted == false) {
                            numCategory = PstEmpCategory.getCount("");
                            width = 65 / numCategory;
                            counted = true;
                        }
                        
			ctrlist.dataFormat(category,String.valueOf(width)+"%","0","2","center","right");
		}
	
                ctrlist.dataFormat("Total","10%","2","0","center","right");	
	
		for(int i=0;i<vListCategory.size();i++)
		{
			ctrlist.dataFormat("M",String.valueOf(width/2)+"%","0","0","center","right");
			ctrlist.dataFormat("F",String.valueOf(width/2)+"%","0","0","center","right");
		}
	}
		
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
        
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.reset();
        
	Vector rowx = new Vector(1,1);
	int index = -1;
	int no = 0;
        
        // fill with data
	if(objectClass!=null && objectClass.size()>0)
        {
            for(int k=0; k<=objectClass.size(); k++)
            {
                rowx = new Vector();
                int total = 0;
                
                // summary row  
                if(k==objectClass.size()){      
                    rowx.add("");
                    rowx.add("<b>Total</b>");

                    // get race count for each category 
                    if(vListCategory!=null && vListCategory.size()>0)
                    {
                            for(int i=0;i<vListCategory.size();i++)
                            {
                                // jumlah total male dan female
                                EmpCategory objEmpCategory = (EmpCategory)vListCategory.get(i);
                                
                                int totalMale = SessEmployee.listEmployeeCategMale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(), objEmpCategory.getOID(),0);
                                int totalFemale = SessEmployee.listEmployeeCategFemale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),0);
                                total += totalMale + totalFemale;
                                
                                rowx.add("<b>"+totalMale+"</b>");
                                rowx.add("<b>"+totalFemale+"</b>");
                            }
                    }
                    rowx.add("<b>"+total+"</b>");
                    lstData.add(rowx);
                }
                // data rows
                else { 
                    Race objRace = (Race)objectClass.get(k);

                    no++;
                    rowx.add(String.valueOf(no));
                    rowx.add(objRace.getRaceName());
                    
                    // get race count for each category 
                    if(vListCategory!=null && vListCategory.size()>0)
                    {
                            for(int i=0;i<vListCategory.size();i++)
                            {
                                    EmpCategory objEmpCategory = (EmpCategory)vListCategory.get(i);
                                    
                                    int countMale = SessEmployee.listEmployeeCategMale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),objRace.getOID());
                                    int countFemale = SessEmployee.listEmployeeCategFemale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),objRace.getOID());
                                    total += countMale + countFemale;
                                    
                                    rowx.add(String.valueOf(countMale));
                                    rowx.add(String.valueOf(countFemale));
                            }
                    }
                    rowx.add(String.valueOf(total));
                    lstData.add(rowx);		
                    total = 0;
                }
            }
	}
	
	return ctrlist.drawMeList();	
}

public String drawListNew(Vector objectClass, Vector objectRpt, SrcEmpRace srcEmpRace)
{
	Vector vListCategory = PstEmpCategory.listAll();
	ControlList ctrlist = new ControlList();
        Vector listEmpCategory = new Vector();
        Vector listEmpCount = new Vector();
        
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
        
        // creating header
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Race","22%","2","0","center","left");	
        
	if(vListCategory!=null && vListCategory.size()>0)
	{
		int numCategory = 0;
                int width = 0;                
                boolean counted = false;
            
                for(int i=0;i<vListCategory.size();i++)
		{
			EmpCategory objCateg = (EmpCategory)vListCategory.get(i);
			String category = objCateg.getEmpCategory();
			listEmpCategory.add(category);
                        
                        if(counted == false) {
                            numCategory = PstEmpCategory.getCount("");
                            width = 65 / numCategory;
                            counted = true;
                        }
                        
			ctrlist.dataFormat(category,String.valueOf(width)+"%","0","2","center","right");
		}
	
                ctrlist.dataFormat("Total","10%","2","0","center","right");	
	
		for(int i=0;i<vListCategory.size();i++)
		{
			ctrlist.dataFormat("M",String.valueOf(width/2)+"%","0","0","center","right");
			ctrlist.dataFormat("F",String.valueOf(width/2)+"%","0","0","center","right");
		}
	}
		
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
        
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.reset();
        
	Vector rowx = new Vector(1,1);
	int index = -1;
	int no = 0;
        
        // fill with data
	if(objectClass!=null && objectClass.size()>0)
        {
            for(int k=0; k<=objectClass.size(); k++)
            {
                rowx = new Vector();
                int total = 0;
                
                // summary row  
                if(k==objectClass.size()){      
                    rowx.add("");
                    rowx.add("<b>Total</b>");

                    // get race count for each category 
                    if(vListCategory!=null && vListCategory.size()>0)
                    {
                            for(int i=0;i<vListCategory.size();i++)
                            {
                                // jumlah total male dan female
                                EmpCategory objEmpCategory = (EmpCategory)vListCategory.get(i);
                                
                                int totalMale = SessEmployee.listEmployeeCategMale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(), objEmpCategory.getOID(),0);
                                int totalFemale = SessEmployee.listEmployeeCategFemale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),0);
                                total += totalMale + totalFemale;
                                
                                rowx.add("<b>"+totalMale+"</b>");
                                rowx.add("<b>"+totalFemale+"</b>");                                
                            }
                    }
                    rowx.add("<b>"+total+"</b>");
                    
                    listEmpCount.add(rowx);
                    lstData.add(rowx);
                }
                // data rows
                else { 
                    Race objRace = (Race)objectClass.get(k);

                    no++;
                    rowx.add(String.valueOf(no));
                    rowx.add(objRace.getRaceName());
                    
                    // get race count for each category 
                    if(vListCategory!=null && vListCategory.size()>0)
                    {
                            for(int i=0;i<vListCategory.size();i++)
                            {
                                    EmpCategory objEmpCategory = (EmpCategory)vListCategory.get(i);
                                    
                                    int countMale = SessEmployee.listEmployeeCategMale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),objRace.getOID());
                                    int countFemale = SessEmployee.listEmployeeCategFemale(srcEmpRace.getDepartmentId(), srcEmpRace.getSectionId(),objEmpCategory.getOID(),objRace.getOID());
                                    total += countMale + countFemale;
                                    
                                    rowx.add(String.valueOf(countMale));
                                    rowx.add(String.valueOf(countFemale));
                            }
                    }
                    rowx.add(String.valueOf(total));
                    
                    listEmpCount.add(rowx);
                    lstData.add(rowx);		
                    total = 0;
                }
            }
	}
        
        objectRpt.add(listEmpCategory);
        objectRpt.add(listEmpCount);
	
	return ctrlist.drawMeList();	
}
  
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    
    SrcEmpRace srcEmpRace = new SrcEmpRace();
    FrmSrcEmpRace frmSrcEmpRace = new FrmSrcEmpRace(request, srcEmpRace);
    frmSrcEmpRace.requestEntityObject(srcEmpRace);
  
    Vector listRace = new Vector(); 
    
    if(iCommand == Command.LIST) {
        listRace = PstRace.listAll();  
    }
   
%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report Employee Race/Ethnic</title>
<script language="JavaScript">

        function cmdView(){
            document.frmethnic.command.value="<%=Command.LIST%>";
            document.frmethnic.action="list_employee_race.jsp";
            document.frmethnic.submit();
        }

        function reportPdf(){  
            var linkPage ="<%=printroot%>.report.listRequest.ListEmployeeEthnicPdf";
            window.open(linkPage); 
        }

        function deptChange() {
            document.frmethnic.command.value="<%=Command.GOTO%>";
            document.frmethnic.action="list_employee_race.jsp";
            document.frmethnic.submit();
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
      <!-- #BeginEditable "header" -->
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
  <%}%>
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Of Employee Race / Ethnic<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td  style="background-color:<%=bgColorContent%>; ">
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr>
                            <td valign="top">
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr>
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frmethnic" method="post" action="">
                                        <input type="hidden" name="command" value="">
                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                          <table width="86%" border="0" cellspacing="2" cellpadding="2">
                                          <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="86%"> : 
                                            <%
                                                  Vector deptValue = new Vector(1,1);
                                                  Vector deptKey = new Vector(1,1);
                                                  
                                                  deptKey.add(" ALL DEPARTMENTS");
                                                  deptValue.add("0");
                                                  
                                                  Vector listDept = PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                  
                                                  for(int p=0;p<listDept.size();p++){
                                                        Department department = (Department)listDept.get(p);
                                                        deptValue.add(""+department.getOID());
                                                        deptKey.add(department.getDepartment());
                                                  }
                                                  %> 
                                                  <%=ControlCombo.draw(FrmSrcEmpRace.fieldNames[FrmSrcEmpRace.FRM_FIELD_DEPT_ID], null,""+srcEmpRace.getDepartmentId(),deptValue,deptKey,"onChange='javascript:deptChange()'")%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" align="right" nowrap> 
                                          <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                          <td width="86%"> : 
                                            <%
                                                  Vector secValue = new Vector(1,1);
                                                  Vector secKey = new Vector(1,1);
                                                  
                                                  secKey.add(" ALL SECTIONS");
                                                  secValue.add("0");
                                                  
                                                  String filter = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + srcEmpRace.getDepartmentId();
                                                  Vector listSec = PstSection.list(0, 0, filter, PstSection.fieldNames[PstSection.FLD_SECTION]);
                                                  
                                                  for(int p=0;p<listSec.size();p++){
                                                        Section section = (Section)listSec.get(p);
                                                        secValue.add(""+section.getOID());
                                                        secKey.add(section.getSection());
                                                  }
                                                  %> 
                                                  <%=ControlCombo.draw(FrmSrcEmpRace.fieldNames[FrmSrcEmpRace.FRM_FIELD_SECT_ID],null,""+srcEmpRace.getSectionId(),secValue,secKey,"")%>
                                          </td>
                                        </tr>			
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" nowrap> <div align="left"></div></td>
                                          <td width="86%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"></td>
                                                <td width="2"></td>
                                                <td width="94" class="command" nowrap> 
                                                </td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                          <td>&nbsp;</td>
                                          <td nowrap><div align="right"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></div></td>
                                          <td>
                                              <a href="javascript:cmdView()">List Employee Statistics</a>
                                          </td>
                                        </tr>
                                      </table>
                                      <% if(iCommand == Command.LIST) { 
                                          
                                            Vector objReport = new Vector();
                                          
                                          %>
                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                            <tr>    
                                                <td><hr></td>   
                                            </tr>
                                            <tr>
                                                <td><%= drawListNew(listRace, objReport, srcEmpRace) %></td>
                                            </tr>
                                            <tr>
                                              <td>&nbsp;</td>
                                            </tr>  
                                            
                                            <%
                                                // pass additional data for report
                                                Vector vctReport = new Vector();

                                                vctReport.add(""+srcEmpRace.getDepartmentId());
                                                vctReport.add(""+srcEmpRace.getSectionId());
                                                vctReport.add(objReport);

                                                session.putValue("EMPLOYEE_RACE", vctReport);
                                                
                                            %>
                                            
                                            <%if(privPrint && listRace != null && listRace.size() > 0) { %>
                                            <tr>
                                              <td>
                                                  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                  <tr>
                                                     <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                     <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
                                                         Employee Race Statistics</a></b></td>                     
                                                  </tr>
                                                  </table>
                                               </td>
                                            </tr>
                                            <% } %>
                                        </table>
                                      <% } %>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
