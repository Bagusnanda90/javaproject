<%@ page import="com.dimata.qdep.system.*" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.harisma.entity.admin.*" %>

<%!
    public final int PRIV_NONE = 0;
    public final int PRIV_GENERAL = 1;
    public final int PRIV_DEPT = 2;
   
    // check privilege status & training type
    private int checkPrivileges(int appObjCode, int trainType, SessUserSession userSession) {
        boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
        boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
        boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
        boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
        boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
        
        // if one of those is assigned, return train type        
        if(privView || privAdd || privUpdate || privDelete || privPrint) {  
            return trainType;
        }
        
        return PRIV_NONE;
    }
    
    // check for training type
    public int checkTrainingType(int appObjCodeGen, int appObjCodeDept, SessUserSession userSession) {
        
        // check for general training access first
        int type = checkPrivileges(appObjCodeGen, PRIV_GENERAL, userSession);
      
        // if type is not general training, check for departmental training access
        if(type == PRIV_NONE) {
            type =  checkPrivileges(appObjCodeDept, PRIV_DEPT, userSession);
            return type;
        }
        else {
            return type;
        }
        
    }
%>

<%
try {
    
    /* if not login, forward request */
    if(!isLoggedIn) {
%>
        <script language="javascript">
                window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.NOT_LOGIN%>";
        </script>			
<%
    } 
    /* if privilege access type is still 'none', forward request */
    else if(trainType == PRIV_NONE) {    
%>
        <script language="javascript">
                window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
        </script>		
<%
    }    
    else if(!privView) {
        
            /* if none of the privileges is assigned, forward request */
            if((!privAdd) && (!privUpdate) && (!privDelete) && (!privPrint)) {
%>
		<script language="javascript">
                        window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
		</script>             
<%
            } else {
                privView = true;
            }
    }
    
}
catch (Exception exc) {
    System.out.println("Exc when check user : " + exc.toString());
}
%>



