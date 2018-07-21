 
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.*" %>
<%@ page import="com.dimata.system.session.*" %>
<%@ include file="../main/javainit.jsp"%>
<%!
public String drawList(int iCommand, FrmSystemProperty frmSystemProperty, Vector objectClass, String groupName, long lOid){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgensell");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
	ctrlist.setTitle(groupName + " Properties");	
	ctrlist.dataFormat("Name","20%","center","center");
	ctrlist.dataFormat("Value","30%","left","left");
	ctrlist.dataFormat("Value Type","10%","left","left");	
	ctrlist.dataFormat("Description","40%","left","left");	

	String editValPre = "<input type=\"text\" name=\"" + frmSystemProperty.fieldNames[frmSystemProperty.FRM_VALUE] +"\" size=\"20\" value=\"";
	String editValSup = "\"> * "+ frmSystemProperty.getErrorMsg(frmSystemProperty.FRM_NAME);

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData 		= ctrlist.getData();
	Vector lstLinkData 	= ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	try{
		if((objectClass!=null) && (objectClass.size()>0)){  
			for(int i=0; i<objectClass.size(); i++){
				 Vector rowx = new Vector();
				 SystemProperty sysProp2 = (SystemProperty)objectClass.get(i);
				 rowx.add(sysProp2.getName());

				 if(iCommand==Command.ASSIGN && lOid==sysProp2.getOID()){
					rowx.add(editValPre + sysProp2.getValue() + editValSup);
				 }else{
					rowx.add("<a href=\"javascript:cmdAssign('"+sysProp2.getOID()+"')\">"+sysProp2.getValue()+"</a>");
				 }

				 rowx.add(sysProp2.getValueType());
				 rowx.add(sysProp2.getNote());

				 lstData.add(rowx); 
				 lstLinkData.add(String.valueOf(sysProp2.getOID()));
			}
		}
	}catch(Exception e){
		System.out.println("Exc : " + e.toString());
	} 
	return ctrlist.draw();
}
%>
<%   
int iCommand = FRMQueryString.requestCommand(request);
long lOid = FRMQueryString.requestLong(request, "oid");
CtrlSystemProperty ctrlSystemProperty = new CtrlSystemProperty(request);
ctrlSystemProperty.action(iCommand, lOid, request);

SystemProperty sysProp = ctrlSystemProperty.getSystemProperty();
FrmSystemProperty frmSystemProperty = ctrlSystemProperty.getForm();
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>System Property</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Property<!-- #EndEditable --> </strong></font> </td>
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
                                    <script language=javascript>
function cmdList() {
  document.frmData.command.value="<%= Command.LIST %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdLoad() {
  document.frmData.command.value="<%= Command.LOAD %>";          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdNew() {
  document.frmData.command.value="<%= Command.ADD %>";          
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdEdit(oid) {
  document.frmData.command.value="<%= Command.EDIT %>";                    
  document.frmData.oid.value = oid;
  document.frmData.action = "syspropnew.jsp";
  document.frmData.submit();
}

function cmdAssign(oid) {
  document.frmData.command.value="<%= Command.ASSIGN %>";       
  document.frmData.oid.value= oid;          
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
}

function cmdUpdate(oid) {
  document.frmData.command.value="<%= Command.UPDATE %>";          
  document.frmData.oid.value = oid;
  document.frmData.action = "sysprop.jsp";
  document.frmData.submit();
    }
</script>
                                    <form name="frmData" method="post" action="">
                                      <input type="hidden" name="command" value="0">
                                      <input type="hidden" name="oid" value="<%=lOid%>">
                                      <table width="100%" border="0" cellspacing="6" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <%
					String cbxName = FrmSystemProperty.fieldNames[FrmSystemProperty.FRM_GROUP];
		
					String groupName = FRMQueryString.requestString(request, cbxName);
					if(groupName == null || groupName.equals("")) groupName = "";
		
					Vector grs = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "> ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - ", true);
					Vector val = Validator.toVector(SessSystemProperty.groups, SessSystemProperty.subGroups, "", "", false);
					
					String strChange = "onChange=\"javascript:cmdList()\"";
					out.println("&nbsp;"+ControlCombo.draw(cbxName, "formElemen", null, groupName, val, grs, strChange));
					Vector vctData = PstSystemProperty.listByGroup(groupName);							
					%>
                                            <!--<a href='javascript:cmdList()'>List</a> </td>-->
                                        </tr>
                                        <tr> 
                                          <td><%=drawList(iCommand,frmSystemProperty,vctData,groupName,lOid)%></td>
                                        </tr>
                                        <tr> 
                                          <td align="right"><%="<i>"+ctrlSystemProperty.getMessage()+"</i>"%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="right"> 
                                            <% 
					if(iCommand==Command.ASSIGN){
						out.println("<a href=\"javascript:cmdUpdate('"+lOid+"')\">Update Value</a> | <a href='javascript:cmdList()'>Cancel</a> | ");
					}
					out.println("<a href=\"javascript:cmdNew()\">New System Property</a> | ");
					out.println("<a href=\"javascript:cmdLoad()\">Load New value</a>&nbsp;");
					%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td align="left"> N O T E : <br>
                                            - Use "\\" character when you want 
                                            to input "\" character in value field.<br>
                                            - Click "Load new value" link when 
                                            property it's updated. </td>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
