<%@page import="java.io.File"%>
<%
// --- manage object response supaya yang tampil adalah object terbaru s
/*response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
//response.setHeader("Cache-Control", "nocache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires",-1); 
ini di buat dengan tujuan karena tampilan fixed header jadi hancur karena pengaruh ini ada di javainit
 * <SCRIPT type="text/javascript">
	window.history.forward();
	function noBack() { window.history.forward(); }
</SCRIPT>
*/
%>
<%@page import="java.util.*" %>
<%@page import="com.dimata.qdep.system.*" %>
<%@page import="com.dimata.qdep.form.*" %>
<%@page import="com.dimata.harisma.session.admin.*" %>
<%@page import="com.dimata.harisma.entity.admin.*" %>
<%@page import="com.dimata.harisma.entity.employee.*" %>
<%@page import="com.dimata.harisma.entity.masterdata.*" %>
<%@page import="com.dimata.system.entity.system.*" %>
<%@page import="com.dimata.util.CalendarCalc"%>

<%@page import="com.dimata.aplikasi.entity.uploadpicture.PstPictureBackground"%>
<%@page import="com.dimata.aplikasi.entity.uploadpicture.PictureBackground"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.TempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.mastertemplate.PstTempDinamis"%>
<%@page import="com.dimata.aplikasi.entity.picturecompany.PictureCompany"%>
<%@page import="com.dimata.aplikasi.entity.picturecompany.PstPictureCompany"%>
<%!
public static final int LIST_NEXT  	= 34;
public static final int LIST_PREV  	= 33;
public static final int LIST_FIRST      = 36;
public static final int LIST_LAST  	= 35; 
public static final Date installationDate = new Date(104, 0, 1, 0, 0, 0);
public static final String canteenWindowName = "canteenWindow";
static int SESS_LANGUAGE = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;  
%>
<% 
long startTime = System.currentTimeMillis();
String strInformation = "";
try{
    strInformation = PstSystemProperty.getValueByName("INFORMATION");
}catch(Exception ex){
    System.out.println("[exception] "+ex.toString());
}

// -- manage application name (depend on client)
String approot=request.getContextPath();
String scheme = request.getScheme();
String serverName = request.getServerName();
int serverPort = request.getServerPort();
String urlWeb = scheme + "://" + serverName + ":" + serverPort + approot;
//System.out.println("ini linknya bro"+url); 
String reportroot=approot+"/servlet";
String imagesroot=approot+"/images";
String printroot=approot+"/servlet/com.dimata.harisma";

String approotCanteen=approot+"/harisma-canteen";   
String printrootCanteen=approot+"/servlet/com.dimata.harisma";
String approotFile=approot+"/imgdoc";

// -- manage view of selected type from user trigger on login form
boolean viewMinimum = false;
if(session.getValue("VIEW_MINIMUM")!=null)
{
 	viewMinimum = ((Boolean)session.getValue("VIEW_MINIMUM")).booleanValue();
}

boolean NonViewBirthday = false;
if(session.getValue("VIEW_BIRTHDAY")!=null)
{
 	NonViewBirthday = ((Boolean)session.getValue("VIEW_BIRTHDAY")).booleanValue();
}

boolean isLoggedIn = false; // indicate if the user is loggen in or not
SessUserSession userSession = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME); // instant of object user session

try
{
    if(userSession==null)
	{
        userSession= new SessUserSession();
    }
    else
	{
        if(userSession.isLoggedIn()==true)
		{
            isLoggedIn  = true;
		}
	}
}
catch (Exception exc)
{
	System.out.println(" >>> Exception during check login");
}

//setup company profile
String COMPANY_NAME = "Company - please set Javainit";
String COMPANY_ADDRESS = "Address - please set Javainit";
CompanyProfile compProfile = new CompanyProfile();
compProfile.setCompName(COMPANY_NAME);
compProfile.setCompAddress(COMPANY_ADDRESS);
compProfile.setUseHeaderImage(false);
session.putValue("COMPANY_PROFILE", compProfile);
//==================================

// -- manage system property for oid department HRD
String OID_HRD_DEPARTMENT = "OID_HRD_DEPARTMENT";
String OID_EDP_SECTION = "EDP_SECTION_OID";



// -- get data from userSession
Employee emplx = userSession.getEmployee();
Department departmentOfLoginUser = userSession.getDepartment();
Position positionOfLoginUser = userSession.getPosition();
Section sectionOfLoginUser = userSession.getSection();
String userIsLogin = "USER : "+(emplx.getFullName().length()>0 ? emplx.getFullName() : "-");
int positionType = positionOfLoginUser.getPositionLevel();
long departmentOid = departmentOfLoginUser.getOID();
long positionOID = positionOfLoginUser.getOID();

//update by satrya 2013-10-21
//update by satrya 2013-10-28
 long oidHRDirs =0;
try {
   oidHRDirs = Long.parseLong(PstSystemProperty.getValueByName("HR_DIR_POS_ID"));
} catch (Exception E) {
}
boolean isDirector = positionOID == oidHRDirs ? true : false;

//update by satrya 2013-09-13
long joinHodDepartmentOid = departmentOfLoginUser==null?0:departmentOfLoginUser.getJoinToDepartmentId();

// interval today from installation data
int installInterval = installationDate.getYear() - (new Date()).getYear();

// -- manage schedule editor depend on department of operator's department or not ???
boolean processDependOnUserDept = true;

// -- manage type of calendar : INDONESIAN (week start on Monday) or ENGLISH (week start on Sunday)
int iCalendarType = CalendarCalc.INDONESIAN;
// -- untuk stlye menu header template harisma 2013-12-23 update by devin
File jsp = new File(request.getSession().getServletContext().getRealPath(""));
File dir = jsp.getParentFile();
String pathImage =dir.getPath().replace("\\build", "\\jsp")+"\\"+approot.replace("/", "")+"\\imgupload"; // hidden by satrya 2014-02-17"D:/share/Share Ramayu/SATRYA/MEMBUAT PROGRAM/HARISMA/new_template/hr_new_tmplate_20131227/jsp/imgupload";//untuk baground
String pathImg = dir.getPath().replace("\\build", "\\jsp")+"\\"+approot.replace("/", "")+"\\imgcompany";//hidden by satrya 2014-02-17 "D:/share/Share Ramayu/SATRYA/MEMBUAT PROGRAM/HARISMA/new_template/hr_new_tmplate_20131227/jsp/imgcompany";//untuk company client
    String whereClauses = PstPictureBackground.fieldNames[PstPictureBackground.FLD_LOGIN_ID] + "=" + 0;
    Vector listPictureBackground = new Vector();
           try{
            listPictureBackground =PstPictureBackground.list(0, 0, whereClauses, "");
           }catch(Exception exc){
               System.out.println("Exc"+exc);
           }
    PictureBackground pictureBackground = new PictureBackground();
Vector listPictureCompany =  new Vector();
    try{
        listPictureCompany =PstPictureCompany.list(0, 1, "", "");
    }catch(Exception exc){
        System.out.println("Exc"+exc);
    }
    PictureCompany pictureCompany = new PictureCompany();
    if(listPictureCompany!=null && listPictureCompany.size()>0){
        pictureCompany = (PictureCompany)listPictureCompany.get(0);
    }
    
    Vector listTempDinamis =  new Vector();
        try{
            listTempDinamis = PstTempDinamis.list(0, 1, "", "");
        }catch(Exception exc){
            System.out.println("Exc"+exc);
        }
    TempDinamis tempDinamis = new TempDinamis();
    if(listTempDinamis!=null && listTempDinamis.size()>0){
        tempDinamis = (TempDinamis)listTempDinamis.get(0);
    }
  
    String navigation = tempDinamis!=null && tempDinamis.getNavigation()!=null ? tempDinamis.getNavigation():"menu_i";
    boolean headerStyle = true?true:false;
    String bgColorBody = tempDinamis!=null && tempDinamis.getTempColor()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getTempColor()):"white"; 
    String verTemplate = tempDinamis!=null && tempDinamis.getTempVersionNo()!=null && tempDinamis.getTempVersionNo().length()>0 ?tempDinamis.getTempVersionNo():"0";
 
    String bgColorHeader = tempDinamis!=null && tempDinamis.getHeaderColor()!=null ? "bgcolor=\""+tempDinamis.getHeaderColor()+"\"":"bgcolor=\"#42b6e8\"";    
    ///String bgColorHeader = tempDinamis!=null && tempDinamis.getHeaderColor()!=null ? "#"+tempDinamis.getHeaderColor():"white";
    String bgColorContent = tempDinamis!=null && tempDinamis.getContentColor()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getContentColor()):"white";
    
    String bgMenu = tempDinamis!=null && tempDinamis.getBgMenu()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"#42B6E8":"#"+tempDinamis.getBgMenu()):"#42B6E8";
    String fontMenu = tempDinamis!=null && tempDinamis.getFontMenu()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getFontMenu()):"white";
    String hoverMenu = tempDinamis!=null && tempDinamis.getHoverMenu()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getHoverMenu()):"#C1E4EC";
    String menu_1 =  "#42b6e8";
    String menu_1_bgFont = "#42b6e8";
    String header1 = tempDinamis!=null && tempDinamis.getHeaderColor()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"#42B6E8":"#"+tempDinamis.getHeaderColor()):"#42B6E8";
    String header2 = tempDinamis!=null && tempDinamis.getTempColorHeader()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"#42B6E8":"#"+tempDinamis.getTempColorHeader()):"#42B6E8";
    String garis1 = tempDinamis!=null && tempDinamis.getGarisHeader1()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getGarisHeader1()):"white";
    String garis2 = tempDinamis!=null && tempDinamis.getGarisHeader2()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getGarisHeader2()):"white";
  String footerBg = tempDinamis!=null && tempDinamis.getFooterBackground()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"#42B6E8":"#"+tempDinamis.getFooterBackground()):"#42B6E8";
  String garisFooter = tempDinamis!=null && tempDinamis.getFooterGaris()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getFooterGaris()):"white";
  String warnaFont = tempDinamis!=null && tempDinamis.getFontMenu()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"white":"#"+tempDinamis.getFontMenu()):"white";
   String garisContent = tempDinamis!=null && tempDinamis.getGarisContent()!=null ? (tempDinamis.getTempVersionNo().equalsIgnoreCase("0")?"blue":"#"+tempDinamis.getGarisContent()):"blue";
   String noBack = "onload=\"noBack();\" onpageshow=\"if (event.persisted) noBack();\" onunload=\""; /*"oncontextmenu=\"return false;\" onload=\"noBack();\" onpageshow=\"if (event.persisted) noBack();\" onunload=\"\""*/;    
String bgFooterLama ="";//"bgcolor=\"#9BC1FF\"";

%>
