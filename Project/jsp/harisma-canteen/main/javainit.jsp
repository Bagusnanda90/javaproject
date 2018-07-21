<%@ page import="java.util.*" %>
<%@ page import="com.dimata.qdep.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.harisma.entity.admin.*" %>
<%@ page import="com.dimata.harisma.entity.employee.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>

<%!
public static final int LIST_NEXT  	= 34;
public static final int LIST_PREV  	= 33;
public static final int LIST_FIRST  = 36;
public static final int LIST_LAST  	= 35; 
public static final Date installationDate = new Date(105, 0, 1, 0, 0, 0);
public static final String canteenWindowName = "canteenWindow";
static int SESS_LANGUAGE = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;  
%>

<% 
response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");

//-- ini di pakai untuk get di system property oid department HRD
String OID_HRD_DEPARTMENT = "OID_HRD_DEPARTMENT";

String menu = FRMQueryString.requestString(request, "menu");
String approot ="";
String printroot ="";
//if(menu.equals("1")){
	approot="/harisma-canteen"; 
	printroot="/harisma-canteen/servlet/com.dimata.harisma";
//}

boolean viewMinimum = false;
if(session.getValue("VIEW_MINIMUM")!=null){
 	viewMinimum = ((Boolean)session.getValue("VIEW_MINIMUM")).booleanValue();
}

boolean NonViewBirthday = false;
if(session.getValue("VIEW_BIRTHDAY")!=null){
 	NonViewBirthday = ((Boolean)session.getValue("VIEW_BIRTHDAY")).booleanValue();
}

//out.println("viewMinimum : "+viewMinimum+", NonViewBirthday : "+NonViewBirthday);

boolean isLoggedIn =false   ; // indicate if the user is loggen in or not
SessUserSession userSession=(SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME); // instant of object user session

//out.println("userSession : "+userSession);

try{
    if(userSession==null){
        userSession= new SessUserSession();
        //System.out.println("userSession 1 ----------------->null");
        }
     else{
        //System.out.println("userSession 1 ----------------->OK");
        //userSession.printAppUser();
        if(userSession.isLoggedIn()==true)
            isLoggedIn  = true;
        }

   } catch (Exception exc){
      System.out.println(" ==> Exception during check login");
    }

// get data from userSession
//Section sectx = userSession.getSection();
Employee emplx = userSession.getEmployee();
Department departmentOfLoginUser = userSession.getDepartment();
Position positionOfLoginUser = userSession.getPosition();
String userIsLogin = "USER : "+(emplx.getFullName().length()>0 ? emplx.getFullName() : "-");

int positionType = positionOfLoginUser.getPositionLevel();
long departmentOid = departmentOfLoginUser.getOID();

// interval today from installation data
int installInterval = installationDate.getYear() - (new Date()).getYear();


//---------------------------- loading system property ---------------

	//long hrdDepartId = 504404189914245609L;
	//Long.parseLong(PstSystemProperty.getValueByName("HRD_ID"));
%>
