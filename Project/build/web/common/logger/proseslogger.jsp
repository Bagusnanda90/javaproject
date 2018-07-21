<%@ page import="com.dimata.common.entity.logger.PstLogger"%>
<%
    String strLogNotes = "";
    // get index array first to page name
    int g1 = AppObjInfo.getIdxGroup1(appObjCode);
    if(g1<0)
        strLogNotes = "... > ";
    else
        strLogNotes = AppObjInfo.titleG1[g1]+ " > ";

    // get index array second to page name
    int g2 = AppObjInfo.getIdxGroup2(appObjCode);
    if(g2<0)
        strLogNotes = strLogNotes + "... > ";
    else
        strLogNotes = strLogNotes + AppObjInfo.titleG2[g1][g2]+ " > ";
    // get object name page
    int oidx = AppObjInfo.getIdxObject(appObjCode);
    if(oidx<0)
        strLogNotes = strLogNotes + "... > ";
    else
        strLogNotes = strLogNotes + AppObjInfo.objectTitles[g1][g2][oidx];

    AppUser appUserLogger = userSession.getAppUser();
    if(appObjCode>=0 && appUserLogger.getOID()!= 0){
        PstLogger.insertLogger(appUserLogger.getOID(),appUserLogger.getLoginId()+" - ( "+appUserLogger.getFullName()+" )",new Date(),PstLogger.checkCommLogger(strLogNotes,FRMQueryString.requestCommand(request)));
    }
%>