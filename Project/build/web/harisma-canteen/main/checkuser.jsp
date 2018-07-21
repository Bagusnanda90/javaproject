<%@ page import="com.dimata.qdep.system.*" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.harisma.entity.admin.*" %>

<% 
/**
* jsp include this jsp hs to declare variable named :
* int appObjCode
* and initilize it it with the object code (not with the command)
* e.g. 
* int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_ADMIN, AppObjInfo.G2_ADMIN_USER, AppObjInfo.OBJ_ADMIN_USER_USER); 
*
* !!! VIEW privilege access will be checked as the basic privilege !!!
*/    


/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd =userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
boolean privStart = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
boolean privStop = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_STOP));
boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
boolean privSubmit = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));


try{
    if(isLoggedIn == false){
%>
		<script language="javascript">
				window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.NOT_LOGIN%>";
		</script>			
<%
    }else if(privView==false){	
            if((privAdd==false)&&(privUpdate==false)&&(privDelete==false)&&(privStart==false)&&(privStop==false)&&(privPrint==false)&&(privSubmit==false)){
%>
		<script language="javascript">
				window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
		</script>             
<%
            }
    }
} 
catch (Exception exc)
{
    System.out.println("Exc when check user : " + exc.toString());
}
%>
