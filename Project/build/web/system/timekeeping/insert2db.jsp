<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_BARCODE, AppObjInfo.OBJ_INSERT_TO_DB); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%!
    public String drawList(Vector objectClass){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","8%");
        ctrlist.addHeader("Payroll","8%");
        ctrlist.addHeader("Full Name","15%");
        ctrlist.addHeader("Position","10%");
        ctrlist.addHeader("Barcode Number","10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        Hashtable position = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        position.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            position.put(String.valueOf(pos.getOID()), pos.getPosition());
        }
        Hashtable hDept = new Hashtable();
        Vector listDept = PstDepartment.listAll();
        hDept.put("0", "-");
        for (int ls = 0; ls < listDept.size(); ls++) {
            Department dept = (Department) listDept.get(ls);
            hDept.put(String.valueOf(dept.getOID()), dept.getDepartment());
        }

        for (int i = 0; i < objectClass.size(); i++) {
            Employee employee = (Employee) objectClass.get(i);
            Vector rowx = new Vector();
			rowx.add(""+(i+1));
            rowx.add(employee.getEmployeeNum());
            rowx.add(employee.getFullName());
            rowx.add(position.get(String.valueOf(employee.getPositionId())));
            String barcode = "";
            try {
                barcode = employee.getBarcodeNumber();
            }
            catch(Exception e) {
            }

            if ((barcode != null) && (barcode.length() > 0)) {
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\"><input type=\"hidden\" class=\"elementForm\" name=\"old_barcode_number\" value=\"" + employee.getBarcodeNumber() + "\"><input type=\"textbox\" class=\"elementForm\" name=\"barcode_number\" value=\"" + employee.getBarcodeNumber() + "\">");
            }
            else {
                rowx.add("<input type=\"hidden\" name=\"employee_id\" value=\"" + employee.getOID() + "\"><input type=\"hidden\" class=\"elementForm\" name=\"old_barcode_number\" value=\"\"><input type=\"textbox\" class=\"elementForm\" name=\"barcode_number\" value=\"\">");
            }
            lstData.add(rowx);
            //lstLinkData.add();
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    String gotoEmpName = FRMQueryString.requestString(request, "hidden_goto_empname");
    String gotoEmpPayroll = FRMQueryString.requestString(request, "hidden_goto_emppayroll");
    long gotoEmpCat = FRMQueryString.requestLong(request, "hidden_goto_empcat");
    long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
    long gotoEmpSec = FRMQueryString.requestLong(request, "hidden_goto_empsec");					
    long gotoEmpPos = FRMQueryString.requestLong(request, "hidden_goto_emppos");
	
    String s_barcode_number = null;
    String s_old_barcode_number = null;
    String sOwnError = "";


	// Jika command adalah SAVE, maka update nomor barcodenya
    if (iCommand == Command.SAVE) {
		// Inisialisasi variable yang akan meng-handle nilai2 berikut
        String[] employee_id = null;
        String[] barcode_number = null;		
        String[] old_barcode_number = null;

		// Ngambil nilai dari array form berikut
        try {
            employee_id = request.getParameterValues("employee_id");
            barcode_number = request.getParameterValues("barcode_number");
            old_barcode_number = request.getParameterValues("old_barcode_number");
        }
        catch (Exception e) 		
		{
		 	System.out.println("Err : "+e.toString());
		}
		
		// Jika array barcode_number tidak kosong (ada/minimal satu barcode employee diisi)
        if ((barcode_number != null) && (barcode_number.length > 0)) {
            for (int i = 0; i < barcode_number.length; i++) {
                if ((barcode_number[i].length() > 0)) {
                    try 
					{
                       s_barcode_number = String.valueOf(barcode_number[i]);
                    } catch (Exception e) 
					{
					}
                }
                else 
				{
                    s_barcode_number = null;
                }

                if ((old_barcode_number[i].length() > 0)) {
                    try 
					{
                        s_old_barcode_number = String.valueOf(old_barcode_number[i]);
                    } catch (Exception e) 
					{
					}
                }
                else 
				{
                    s_old_barcode_number = null;
                }                
				
				// Jika nilai curr barcode beda dengan old barcode (diganti) maka nilai barcode diganti
				if(!s_barcode_number.equals(s_old_barcode_number)){					
					System.out.println("... updating HR_EMPLOYEE ...");	
					PstEmployee.updateBarcode(Long.parseLong(employee_id[i]), s_barcode_number);
				}
				
            }
        }		
    }
	
	

    int start = 0;
    int recordToGet = 0;
	
    String whereClause = "";
	if(gotoEmpName!=null && gotoEmpName.length()>0){
		   whereClause = PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + gotoEmpName.trim() + "%'";
	}		   

	if(gotoEmpPayroll!=null && gotoEmpPayroll.length()>0){	
		if(whereClause!=null && whereClause.length()>0){	   	
		   whereClause += " AND "  + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + gotoEmpPayroll.trim() + "%'";
		}else{
		   whereClause += PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " LIKE '%" + gotoEmpPayroll.trim() + "%'";		
		}	 
	}  
	
	if(gotoEmpCat>0){
		if(whereClause!=null && whereClause.length()>0){	   				
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + String.valueOf(gotoEmpCat);
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + " = " + String.valueOf(gotoEmpCat);		
		}   
	}
		   
	if(gotoEmpSec>0){
		if(whereClause!=null && whereClause.length()>0){	   					
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + String.valueOf(gotoEmpSec);
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + String.valueOf(gotoEmpSec);		
		}   
	}	   
	
	if(gotoEmpPos>0){
		if(whereClause!=null && whereClause.length()>0){	   					
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + String.valueOf(gotoEmpPos);		   		   		   		   
		}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID] + " = " + String.valueOf(gotoEmpPos);		   		   		   		   		
		}
	}
		
	if(whereClause!=null && whereClause.length()>0){	   							
	       whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   
           whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";
	}else{
	       whereClause += PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + String.valueOf(gotoDept);		   
           whereClause += " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0 ";	
	}	   
    
	String orderClause = "" + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
	
    Vector listEmp = new Vector(1,1);
    listEmp = PstEmployee.list(start, recordToGet, whereClause, orderClause);
    //System.out.println("listEmp.size() = " + listEmp.size());
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Barcode Management</title>
<script language="JavaScript">

    function cmdSave() {
        document.frmbarcode.command.value = "<%=Command.SAVE%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.action = "insert2db.jsp";
        document.frmbarcode.submit();
    }

    function deptChange() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.action = "insert2db.jsp";
        document.frmbarcode.submit();
    }

    function cmdSearch() {
        document.frmbarcode.command.value = "<%=Command.GOTO%>";
        document.frmbarcode.hidden_goto_empname.value = document.frmbarcode.EMP_NAME.value;
        document.frmbarcode.hidden_goto_emppayroll.value = document.frmbarcode.EMP_PAYROLL_NUM.value;
        document.frmbarcode.hidden_goto_empcat.value = document.frmbarcode.CATEGORY_ID.value;
        document.frmbarcode.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
        document.frmbarcode.hidden_goto_empsec.value = document.frmbarcode.SECTION_ID.value;
        document.frmbarcode.hidden_goto_emppos.value = document.frmbarcode.POSITION_ID.value;										
        document.frmbarcode.action = "insert2db.jsp";
        document.frmbarcode.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Timekeeping &gt; Insert Barcode to Database<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                <% if (privStart) { %>																
                                    <form name="frmbarcode" method="post" action="">
                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                    <input type="hidden" name="hidden_goto_empname" value="<%=gotoEmpName%>">
                                    <input type="hidden" name="hidden_goto_emppayroll" value="<%=gotoEmpPayroll%>">
                                    <input type="hidden" name="hidden_goto_empcat" value="<%=gotoEmpCat%>">
                                    <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
                                    <input type="hidden" name="hidden_goto_empsec" value="<%=gotoEmpSec%>">
                                    <input type="hidden" name="hidden_goto_emppos" value="<%=gotoEmpPos%>">																																													
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>INSERT</b> 
                                                  barcode to database only. <br>
                                                  <br>
                                                  If you want to INSERT barcode 
                                                  to database and UPLOAD it to 
                                                  timekeeping machine instead, 
                                                  please use System &gt; Barcode 
                                                  &gt; Insert &amp; Upload Barcode<br>
                                                  If you want to UPLOAD barcode 
                                                  to timekeeping machine only 
                                                  and not to INSERT it to database 
                                                  instead, please use System &gt; 
                                                  Barcode &gt; Upload Barcode 
                                                  to Machine 
                                                  <hr>
                                                </td>
                                              </tr>
											  
											  
                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
												  <input type="text" name="EMP_NAME"  value="<%=gotoEmpName%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
												  <input type="text" name="EMP_PAYROLL_NUM"  value="<%=gotoEmpPayroll%>" class="elemenForm">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
														Vector cat_value = new Vector(1,1);
														Vector cat_key = new Vector(1,1);        
														cat_value.add("0");
														cat_key.add("select ...");                                                          
														Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
														for (int i = 0; i < listCat.size(); i++) {
																EmpCategory cat = (EmpCategory) listCat.get(i);
																cat_key.add(cat.getEmpCategory());
																cat_value.add(String.valueOf(cat.getOID()));
														}
													%>
												  <%= ControlCombo.draw("CATEGORY_ID","formElemen",null, ""+ gotoEmpCat, cat_value, cat_key, "") %>
												  </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        //dept_key.add("select...");
                                                        //dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%//= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %>
												  <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "") %>												  
												  </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
														Vector sec_value = new Vector(1,1);
														Vector sec_key = new Vector(1,1); 
														sec_value.add("0");
														sec_key.add("select ...");
														Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
														for (int i = 0; i < listSec.size(); i++) {
																Section sec = (Section) listSec.get(i);
																sec_key.add(sec.getSection());
																sec_value.add(String.valueOf(sec.getOID()));
														}
													%>
												  <%= ControlCombo.draw("SECTION_ID","formElemen",null, ""+ gotoEmpSec, sec_value, sec_key, "") %>
												  </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
														Vector pos_value = new Vector(1,1);
														Vector pos_key = new Vector(1,1); 
														pos_value.add("0");
														pos_key.add("select ...");                                                       
														Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
														for (int i = 0; i < listPos.size(); i++) {
																Position pos = (Position) listPos.get(i);
																pos_key.add(pos.getPosition());
																pos_value.add(String.valueOf(pos.getOID()));
														}
													%>
												  <%= ControlCombo.draw("POSITION_ID","formElemen",null, ""+ gotoEmpPos, pos_value, pos_key, "") %>
												  </td>
                                              </tr>
                                              <tr>
                                                <td width="11%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%">
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">&nbsp; </td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%">&nbsp; </td>
                                              </tr>
											  											  
											  
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                          <% if (listEmp.size() > 0) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                    <%=drawList(listEmp)%>
                                                </td>
                                              </tr>
                                              <% if (sOwnError.length() > 0) { %>
                                              <tr>
                                                    <td><br>Duplicate barcode found : <br><%=sOwnError%><br></td>
                                              </tr>
                                              <% } %>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="50" class="command" nowrap><a href="javascript:cmdSave()">Save</a></td>
                                                    </tr> 
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
                                    <!-- #EndEditable -->
                            </td>
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
      </table>
		  </td> 
        </tr>
      </table>
    </td> 
  </tr>
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
