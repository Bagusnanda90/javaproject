<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>

<%@ include file = "../../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_TIMEKEEPING_CHECK_MACHINE); %>
<%@ include file = "../../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");  

Vector machineStatus = new Vector(1,1);
int numOfTransaction = 0 ;

String machineNumber = "01";
String[] machineNumbers;

Vector v1 = new Vector(1,1);
Vector v2 = new Vector(1,1);

if (privStart) 
{
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("Canteen MACHINE :::::::::: "+machineNumbers[count]);
        
            //Test Machine
            I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[count]);
            boolean isOk = i_Machine.processCheckMachine();
            if(isOk){
                machineStatus.add("Check machine NO : "+machineNumbers[count]+" :::: OK<br>");
            }else{
                machineStatus.add("<span class='errfont'><i>Unable to connect to machine NO : "+machineNumbers[count]+"</i></span>");
            }
            count ++;
        }
    
}
%>
<html> <!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Test Machine</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
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
                  &gt; Timekeeping &gt; Check Machine<!-- #EndEditable --> </strong></font> 
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                <% 
								if (privStart) 
								{								 
								%>
                                    <form name="frmReset" method="post" action="download.jsp">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <div align="left"><br>
                                            <%//=tma01Status%><!--<br><br>-->
                                            <%for(int i = 0;i<machineStatus.size();i++){%>
                                            <%=machineStatus.get(i)%><br>
                                            <%}%>
                                            </div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <div align="center"> </div>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                <% 
								} 
                                else
                                {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% 
								} 
								%>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
