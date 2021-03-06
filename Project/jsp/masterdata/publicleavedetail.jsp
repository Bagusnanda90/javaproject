<%--
     Document   : publicleave
    Created on : Mar 3, 2013, 9:05:13 PM
    Author     : Satrya Ramayu
--%>

<%@page import="com.dimata.harisma.form.masterdata.CtrlPublicLeaveDetail"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPublicLeaveDetail"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPublicHolidays"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="java.util.Formatter"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPublicLeave"%>
<%@page import="com.dimata.harisma.entity.masterdata.PublicLeave"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPublicLeave"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPublicLeave"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_PUBLIC_HOLIDAY); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT))); 
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION))); 
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
    

%>
<%!
    public String drawListPublicLeave(int iCommand, int start,FrmPublicLeave frmObject, PublicLeave objEntity, Vector objectClass, long publicLeaveId,Date dtFrom, Date dtTo,long publicHolidayId,long oidSysHoliday,Vector listCategory) { 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No","10%");
        ctrlist.addHeader("Day Work From","10%");
        ctrlist.addHeader("Day Work To","10%");
         //ctrlist.addHeader("Employee", "10%", "2", "0");//6%
        
        //ctrlist.addHeader("From Time","10%");
        //ctrlist.addHeader("To Time","10%");
        ctrlist.addHeader("Emp.Category", "10%");
       // ctrlist.addHeader("Emp.Category","10%","0",""+(listCategory!=null ?listCategory.size():1));
        ctrlist.addHeader("Type","10%");
         ctrlist.addHeader("Taken Schedule","10%");
        //ctrlist.addHeader("Total Days","10%");
       /* if(listCategory!=null && listCategory.size()>0){
            for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                EmpCategory empCategory = (EmpCategory)listCategory.get(idxCat); 
                 ctrlist.addHeader(""+empCategory.getEmpCategory(),"10%", "0","0");
                 //ctrlist.addHeader("Time"+"<br>"+"In", "2%", "0", "0");
                 
                 
            }
        }else{
            ctrlist.addHeader(""); 
        }*/
         
        ctrlist.setLinkRow(0); 
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
        int noCounter = start+1;
        Date  aNewDate = new Date();
        aNewDate.setHours(0);
        aNewDate.setMinutes(0);
        aNewDate.setSeconds(0);
        EmpCategory empCategoryxx = new EmpCategory();
     
     long oidLeave = 1;//jika 0 berati leave. pertama tama di set 0 dahulu nnti ketika prosess baru di set Leave'nya
        Vector vTypeKey = new Vector();
        Vector vTypeValue = new Vector();
        Vector vToolTips = new Vector();
        vTypeKey.add(oidSysHoliday + "");
        vTypeKey.add(oidLeave + "");
        vTypeValue.add("OFF");
        vTypeValue.add("LEAVE");

        vToolTips.add("set To schedule OFF");
        vToolTips.add("set To Leave");
        
                
        Vector vTypeSchKey = new Vector();
        Vector vTypeSchValue = new Vector();
        Vector vToolTipsSch = new Vector();
        vTypeSchKey.add(oidSysHoliday + "");
        vTypeSchKey.add(oidLeave + "");
        vTypeSchValue.add("Awal");
        vTypeSchValue.add("Akhir");

        vToolTipsSch.add("ambil schedule awal");
        vToolTipsSch.add("ambil schedule akhir");
                
        Vector vCatKey = new Vector();
        Vector vCatValue = new Vector();
        Vector rowx = new Vector(1,1);
if(objectClass!=null && objectClass.size()>0){
 for (int i = 0; i < objectClass.size(); i++) {
PublicLeave publicLeave = (PublicLeave) objectClass.get(i);
String empCatx="";
rowx = new Vector();
rowx.add(""+noCounter);noCounter++;
//rowx.add(""+noCounter+ "<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_HOLIDAY_ID] + "\" value=\"" + publicHolidayId + "\" size=\"12\" class=\"elemenForm\">");
if (publicLeaveId == publicLeave.getOID()) {
                index = i;
            }
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                
            
                //if(publicLeaveId == publicLeave.getOID()){
                  //  index = i;
                                        
                    rowx.add(""+ControlDate.drawDateWithStyle(""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_FROM],  publicLeave.getDateLeaveFrom() != null ? publicLeave.getDateLeaveFrom() : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")+ControlDate.drawTime(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_FROM], publicLeave.getDateLeaveFrom() != null ? publicLeave.getDateLeaveFrom() : aNewDate, "elemenForm", 8, 15, 0)+"<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_HOLIDAY_ID] + "\" value=\"" + publicHolidayId + "\" size=\"12\" class=\"elemenForm\">");
                    rowx.add(""+ControlDate.drawDateWithStyle(""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_TO],  publicLeave.getDateLeaveTo() != null ? publicLeave.getDateLeaveTo() : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")+ControlDate.drawTime(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_TO], publicLeave.getDateLeaveTo() != null ? publicLeave.getDateLeaveTo() : aNewDate, "elemenForm", 8, 15, 0)); 
                     /*if(listCategory!=null && listCategory.size()>0){
                         for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                            rowx.add("" +ControlCombo.drawTooltip(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_LEAVE_TYPE], null, publicLeave.getTypeLeave()+"", vTypeKey, vTypeValue, "formElemen",vToolTips)+"<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_LEAVE_TYPE] + "\" value=\"" + publicLeave.getTypeLeave() + "\" size=\"12\" class=\"elemenForm\">"); 
                        }
                       
                    }else{
                        rowx.add(""); 
                    }*/
                    if(listCategory!=null && listCategory.size()>0){
                         for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                             EmpCategory empcategory = (EmpCategory) listCategory.get(idxCat);
                           vCatValue.add(""+empcategory.getEmpCategory());
                            vCatKey.add(""+empcategory.getOID());
                        }
                       
                    }
                    rowx.add("" +ControlCombo.draw(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_TYPE_CATEGORY], null, publicLeave.getEmpCat()+"", vCatKey, vCatValue, "formElemen")); 
                    rowx.add("" +ControlCombo.draw(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_LEAVE_TYPE], null, publicLeave.getTypeLeave()+"", vTypeKey, vTypeValue, "formElemen")); 
                    //lstData.add(rowx);
                    rowx.add("" +ControlCombo.drawTooltip(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_FLAG_SCH], null, publicLeave.getFlagSch()+"", vTypeSchKey, vTypeSchValue, "formElemen",vToolTipsSch)); 
                    lstLinkData.add(String.valueOf(publicLeave.getOID())); 
                                        
                }else{
                     rowx.add(Formater.formatDate(publicLeave.getDateLeaveFrom(), "yyyy-MM-dd HH:mm") + "<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_HOLIDAY_ID] + "\" value=\"" + publicHolidayId + "\" size=\"12\" class=\"elemenForm\">");
                    rowx.add(Formater.formatDate(publicLeave.getDateLeaveTo(), "yyyy-MM-dd HH:mm") );
                    /*if(listCategory!=null && listCategory.size()>0){
                        for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                             if(publicLeave.getTypeLeave()==oidSysHoliday){
                                rowx.add("OFF");
                             }else{
                                rowx.add("LEAVE");
                    }
                        }
                       
                    }else{
                        rowx.add(""); 
                    }*/
                    if(publicLeave.getEmpCat()!=0){
                       try{
                        empCategoryxx = PstEmpCategory.fetchExc(publicLeave.getEmpCat());  
                       }catch(Exception ex){
                           empCatx="";
                       }
                        empCatx = empCategoryxx.getEmpCategory();
                    }
                    rowx.add(empCatx);
                    if(publicLeave.getTypeLeave()==oidSysHoliday){
                                rowx.add("OFF");
                             }else{
                                rowx.add("LEAVE");
                    }
                     rowx.add(""+PstPublicLeave.flagSch[publicLeave.getFlagSch()]);
                    //rowx.add(""+publicLeave.getDateLeaveFrom());
                    lstLinkData.add(String.valueOf(publicLeave.getOID())); 
                }
                 lstData.add(rowx);
                 //noCounter = (noCounter+1); 
            }
        }
             rowx = new Vector(); 
             
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0) || (objectClass.size() < 1)) {
                    rowx.add(""+noCounter);noCounter++; 
                //rowx.add(""+noCounter+ "<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_HOLIDAY_ID] + "\" value=\"" + publicHolidayId + "\" size=\"12\" class=\"elemenForm\">"); 
                    rowx.add(""+ControlDate.drawDateWithStyle(""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_FROM],  dtFrom != null ? dtFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")+ControlDate.drawTime(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_FROM], dtFrom != null ? dtFrom : aNewDate, "elemenForm", 8, 15, 0)+"<input type=\"hidden\" name=\"" + FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_HOLIDAY_ID] + "\" value=\"" + publicHolidayId + "\" size=\"12\" class=\"elemenForm\">");
                    rowx.add(""+ControlDate.drawDateWithStyle(""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_TO],  dtTo != null ? dtTo : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")+ControlDate.drawTime(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_DATE_TO], dtTo != null ? dtTo : aNewDate, "elemenForm", 8, 15, 0));
                    if(listCategory!=null && listCategory.size()>0){
                         for(int idxCat = 0 ; idxCat < listCategory.size(); idxCat++){ 
                             EmpCategory empcategory = (EmpCategory) listCategory.get(idxCat);
                            vCatValue.add(""+empcategory.getEmpCategory());
                            vCatKey.add(""+empcategory.getOID());
                        }
                       
                    }
                    rowx.add("" +ControlCombo.draw(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_TYPE_CATEGORY], null, objEntity.getEmpCat()+"", vCatKey, vCatValue, "formElemen"));  
                    rowx.add("" +ControlCombo.draw(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_LEAVE_TYPE], null, objEntity.getTypeLeave()+"", vTypeKey, vTypeValue, "formElemen"));
                    rowx.add("" +ControlCombo.drawTooltip(FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_FLAG_SCH], null, objEntity.getFlagSch()+"", vTypeSchKey, vTypeSchValue, "formElemen",vToolTipsSch)); 
                    lstLinkData.add(String.valueOf(objEntity.getOID())); 
                    noCounter = (noCounter+1); 
            }
             lstData.add(rowx);
             return ctrlist.draw(); 
	}

%>


<%!
    public Vector drawListEmployee(JspWriter outObj,int start,Vector objectClass,boolean isHRDLogin,long oidSysHoliday ,TblCekErrorPublicLeave tblCekErrorPublicLeave,int iCommand) {   
        ControlList ctrlist = new ControlList();
        Vector result = new Vector(1,1);
        ctrlist.setAreaWidth("100%");        
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");   
        ctrlist.setHeaderStyle("listheaderJs");
        ctrlist.addHeader("No","5%");
         ctrlist.addHeader("<a href=\"Javascript:SetAllCheckBoxes('FRM_NAME_PUBLIC_LEAVE_DETAIL','empoid', true)\">Select All</a> | <a href=\"Javascript:SetAllCheckBoxes('FRM_NAME_PUBLIC_LEAVE_DETAIL','empoid', false)\">Deselect All</a> ","10%");      
        ctrlist.addHeader("Payroll Num","10%");
        ctrlist.addHeader("Nama Employee","25%");
        ctrlist.addHeader("Division","10%");
        ctrlist.addHeader("Departement","20%");
        ctrlist.addHeader("Section","10%");
        ctrlist.addHeader("Emp.Category","10%");
        ctrlist.setLinkRow(-1); 
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.setLinkSufix("')");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        //Vector rowx = new Vector(1, 1);
        ctrlist.reset();
        int index = -1;
        int noCounter = start+1;
        Vector rowx = new Vector(1,1);
             long oidLeave = 1;//jika 0 berati leave. pertama tama di set 0 dahulu nnti ketika prosess baru di set Leave'nya
        Vector vTypeKey = new Vector();
        Vector vTypeValue = new Vector();
        Vector vToolTips = new Vector();
        vTypeKey.add(oidSysHoliday + "");
        vTypeKey.add(oidLeave + "");
        vTypeValue.add("OFF");
        vTypeValue.add("LEAVE");

        vToolTips.add("set To schedule OFF");
        vToolTips.add("set To Leave");
        
if(objectClass!=null && objectClass.size()>0){
    ctrlist.drawListHeaderWithJs(outObj);//header
    for (int i = 0; i < objectClass.size(); i++) {
        
        PublicLeaveDetail publicLeaveDetail = (PublicLeaveDetail) objectClass.get(i); 
        long data =Long.parseLong(publicLeaveDetail.getEmployeeNum()) ;
        
        
        
        rowx = new Vector();
            rowx.add(""+noCounter);noCounter++; 
            if(isHRDLogin){
                    rowx.add("<input type=\"hidden\" name=\"executed"+i+"\"><center><input type=\"checkbox\" name=\"empoid\" value=\""+publicLeaveDetail.getEmployeeId()+"\" > <input type=\"hidden\" name=\"empcat\" value=\""+publicLeaveDetail.getEmpCategoryId()+"\" ></center>");
               }else{
                    rowx.add("");
               }
            //update by devin 2104-02-17
            if(iCommand != Command.SAVE){
            rowx.add(publicLeaveDetail.getEmployeeNum());  
            }else{
                
             //update by devin 2104-02-17
                String Error1="";
                String Error2="";
                String Error3="";
                   Error1 =tblCekErrorPublicLeave!=null ? tblCekErrorPublicLeave.getCekErrorCategoryEmp(publicLeaveDetail.getEmployeeId()):"";
                   Error2 =tblCekErrorPublicLeave!=null ? tblCekErrorPublicLeave.getCekErrorGagalTersimpan(publicLeaveDetail.getEmployeeId()):"";
                   Error3 =tblCekErrorPublicLeave!=null ? tblCekErrorPublicLeave.getCekErrorSdhAdaDetail(publicLeaveDetail.getEmployeeId()):"";
                
                   
                    rowx.add(publicLeaveDetail.getEmployeeNum() + Error1+Error2+Error3 ); 
                  
                   //rowx.add(publicLeaveDetail.getEmployeeNum() +"   "+ "<p style=\"color:#FF0000\"><b>" +Error +"</b></p>" );
            }
            rowx.add(publicLeaveDetail.getFullName()!=null && publicLeaveDetail.getFullName().length()>0? publicLeaveDetail.getFullName():"-");
            rowx.add(publicLeaveDetail.getDivision()!=null && publicLeaveDetail.getDivision().length()>0? publicLeaveDetail.getDivision():"-");
            rowx.add(publicLeaveDetail.getDepartement()!=null && publicLeaveDetail.getDepartement().length()>0? publicLeaveDetail.getDepartement():"-");
            rowx.add(publicLeaveDetail.getSection()!=null && publicLeaveDetail.getSection().length()>0? publicLeaveDetail.getSection():"-");
            rowx.add(publicLeaveDetail.getEmpCategory()!=null && publicLeaveDetail.getEmpCategory().length()>0? publicLeaveDetail.getEmpCategory():"-");
           // rowx.add("" +ControlCombo.drawTooltip(FrmPublicLeaveDetail.fieldNames[FrmPublicLeaveDetail.FRM_FIELD_TYPE_LEAVE], null, publicLeaveDetail.getTypeLeaveId()+"", vTypeKey, vTypeValue, "formElemen",vToolTips)); 
            //rowx.add(publicLeaveDetail.getNote()!=null && publicLeaveDetail.getNote().length()>0?publicLeaveDetail.getNote():"");
            //rowx.add(""); 
           // rowx.add(""); 
            lstLinkData.add(String.valueOf(publicLeaveDetail.getEmployeeId())); 
                 //lstData.add(rowx);
                  ctrlist.drawListRowJs(outObj, 0, rowx, i);
 }
}
            ctrlist.drawListEndTableJs(outObj);
             //return ctrlist.draw(); 
            return result;
	}

%>


<%
CtrlPublicLeave ctrlPublicLeave = new CtrlPublicLeave(request);
long oidPublicHoliday = FRMQueryString.requestLong(request, ""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]); 
long oidPublicLeave = FRMQueryString.requestLong(request, ""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_LEAVE_ID]);
long oidPublicLeaveDetail = FRMQueryString.requestLong(request, ""+FrmPublicLeaveDetail.fieldNames[FrmPublicLeaveDetail.FRM_FIELD_PUBLIC_LEAVE_DETAIL_ID]);
long oidEntilement = FRMQueryString.requestLong(request, ""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS]);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
String empNum = FRMQueryString.requestString(request, "emp_number");
String fullName = FRMQueryString.requestString(request, "full_name");
//update by devin 2014-02-17
long oidDepartment = FRMQueryString.requestLong(request, "department");
long oidReligion = FRMQueryString.requestLong(request, "religion");
long oidCompany =FRMQueryString.requestLong(request, "hidden_companyId");
long oidDivision =FRMQueryString.requestLong(request, "hidden_divisionId");
long oidSection = FRMQueryString.requestLong(request, "section");
long oidEmpCategory = FRMQueryString.requestLong(request, "empCategory");
long typePublicLeave = FRMQueryString.requestLong(request, "typeLeave");
String titlePublicHoliday = FRMQueryString.requestString(request, "titlePublicHoliday");

int iErrCode = FRMMessage.ERR_NONE;
PublicLeave publicLeave1 = new PublicLeave();

Vector listEmployee = new Vector();
String msgString = "";
ControlLine ctrLine = new ControlLine();
//iErrCode = ctrlPublicLeave.action(iCommand, oidPublicLeave);
msgString = ctrlPublicLeave.getMessage();
FrmPublicLeave frmPublicLeave = ctrlPublicLeave.getForm();
PublicLeave publicLeave = ctrlPublicLeave.getPublicLeave(); 


/*variable declaration*/
int recordToGet = 5;
String whereClause = PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_HOLIDAY_ID] + "=" + oidPublicHoliday;

String orderClause = PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_FROM];
Vector listPublicLeave = new Vector(1, 1);


/* end switch*/
Vector listCategory = PstEmpCategory.list(0, 500, "", "");  
/*count list All Position*/
int vectSize = PstPublicLeave.getCount(whereClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPublicLeave.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listPublicLeave = PstPublicLeave.list(start, recordToGet, whereClause, orderClause); 

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPublicLeave.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listPublicLeave = PstPublicLeave.list(start,recordToGet, whereClause , orderClause);
}
PublicHolidays publicHolidays = new PublicHolidays();
if(oidPublicHoliday!=0){
    publicHolidays = PstPublicHolidays.fetchExc(oidPublicHoliday);  
}
long oidSysHoliday = 0;         
try{
    oidSysHoliday = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
}catch(Exception ex){
    System.out.println("Execption OID_PUBLIC_HOLIDAY: " + ex.toString());
}
Religion religion = new Religion();
if(publicHolidays.getiHolidaySts()!=0){
    try{
        religion = PstReligion.fetchExc(publicHolidays.getiHolidaySts());
        publicHolidays.setEntitlement(religion.getReligion());
    }catch(Exception exc){
        religion = new Religion();
    }
}
if(publicHolidays.getEntitlement().length()<1){
    publicHolidays.setEntitlement(PstPublicHolidays.stHolidaySts[Integer.parseInt(""+publicHolidays.getiHolidaySts())]);   
}

String orderEmp = "EMP."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+" ASC ";
int status=0;
listEmployee = PstPublicLeaveDetail.getEmployeeidWithEmpCategory (0, 0, orderEmp, empNum, fullName, oidDepartment, oidSection, oidEmpCategory,status,oidReligion,oidCompany,oidDivision);   

//update by devin 2014-02-08
 int errorCode =0;
if(listEmployee == null || listEmployee.size()   == 0){
  errorCode =1; 
}
 
String whereClauseLeave =  PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID]+"="+ oidPublicHoliday; 
String orderLeave = PstPublicLeave.fieldNames[PstPublicLeave.FLD_PUBLIC_DATE_FROM]+ " ASC "; 
Vector listdayLeave = PstPublicLeave.list(0, 0, whereClauseLeave, orderLeave); 
// untuk public leave detail
CtrlPublicLeaveDetail ctrlPublicLeaveDetail = new CtrlPublicLeaveDetail(request); 
int iErrCodeDetail = ctrlPublicLeaveDetail.action(iCommand, oidPublicLeaveDetail,listdayLeave,oidSysHoliday,oidPublicHoliday); 
msgString = ctrlPublicLeave.getMessage();
 //update by devin 2104-02-17
TblCekErrorPublicLeave tblCekErrorPublicLeave= ctrlPublicLeaveDetail.tblCekErrorPublicLeave();
FrmPublicLeaveDetail frmPublicLeaveDetail = ctrlPublicLeaveDetail.getForm();
PublicLeaveDetail publicLeaveDetail = ctrlPublicLeaveDetail.getPublicLeaveDetail();  
%>

<!-- JSP Block -->
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>Harisma - Public Leave</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
        <link rel="stylesheet" href="<%=approot%>/styles/form.css" type="text/css">
         <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" />
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidPublicLeave){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.<%=FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_LEAVE_ID]%>.value=oidPublicLeave;
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.EDIT%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdAdd(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.<%=FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_LEAVE_ID]%>.value=0; 
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.ADD%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdSave(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.SAVE%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=prevCommand%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdBack(){
               
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.BACK%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            function editMain(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.BACK%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            function cmdBackListHoliday(){
                 //document.frmholiday.<%//=FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value=oidHolidays;
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.GOTO%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.start.value=0;
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicHoliday.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdListFirst(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=Command.FIRST%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdListPrev(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.PREV%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=Command.PREV%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            function cmdListNext(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=Command.NEXT%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
            function cmdListLast(){
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.LAST%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=Command.LAST%>";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleave.jsp";
                document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
            }
            
function SetAllCheckBoxes(FormName, FieldName, CheckValue)
{
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
		for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
}
function cmdExcLeave(){
    document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.command.value="<%=Command.SAVE%>";
    document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.prev_command.value="<%=prevCommand%>";
    document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.action="publicleavedetail.jsp";
    document.<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>.submit();
    
}         
function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=String.valueOf(LIST_PREV)%>:
			cmdListPrev();
			break;
		case <%=String.valueOf(LIST_NEXT)%>:
			cmdListNext();
			break;
		case <%=String.valueOf(LIST_FIRST)%>:
			cmdListFirst();
			break;
		case <%=String.valueOf(LIST_LAST)%>:
			cmdListLast();
			break;
		default:
			break;
	}
}   
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
        </SCRIPT>
        <!-- #EndEditable -->
    </head> 
    
 <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<style>
/*.fakeContainer { /* The parent container */
/*    margin: 0px;
    padding: 0px;
    border: none;
    width: 1310px; /* Required to set */
/*    height: 400px; /* Required to set */
/*   overflow: hidden; /* Required to set */
/*}*/
/*.skinCon {
    float: left;
    margin: 0px;
    border: none;
    width: 1310px;
}*/
.fakeContainer { /* The parent container */
    margin: 1px;
    padding: 1px;
    border: none;
    width: 100%; /* Required to set */
    height: 400px; /* Required to set */
    overflow: auto; /* Required to set */
}
.skinCon {
    float: inside;
    margin: 0px;
    border: none;
    width: 1310px;
}

</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --><!-- #EndEditable -->  <%@ include file = "../main/mnmain.jsp" %> </td> 
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Master Data &gt; Public Leave<!-- #EndEditable -->
            </strong></font>
	      </td>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
                                    <!--- FORM PERTAMA-->
                                    
                                    <!--- FORM KEDUA-->
                                   <form name="<%=FrmPublicLeaveDetail.FRM_NAME_PUBLIC_LEAVE_DETAIL%>" method="post" action="">
                                    <input type="hidden" name="command" value="">
                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="<%=""+FrmPublicLeave.fieldNames[FrmPublicLeave.FRM_FIELD_PUBLIC_LEAVE_ID]%>" value="<%=oidPublicLeave%>">
                                    <input type="hidden" name="<%=""+FrmPublicLeaveDetail.fieldNames[FrmPublicLeaveDetail.FRM_FIELD_PUBLIC_LEAVE_DETAIL_ID]%>" value="<%=oidPublicLeaveDetail%>">
                                     <input type="hidden" name="<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>" value="<%=oidPublicHoliday%>">
                                      <input type="hidden" name="<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS]%>" value="<%=oidEntilement%>">
                                      <input type="hidden" name="department" value="<%=oidDepartment%>">
                                      <input type="hidden" name="religion" value="<%=oidReligion%>">
                                       <input type="hidden" name="hidden_companyId" value="<%=oidCompany%>">
                                        <input type="hidden" name="hidden_divisionId" value="<%=oidDivision%>">
                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
													<td width="5">&nbsp;</td>
                                                    <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                    <%
                                                        String title = "<font size='4px'>"+publicHolidays.getStDesc() +" </font> <br> <font size='4px'>"+ Formater.formatDate(publicHolidays.getDtHolidayDate(), "yyyy-MM-dd") + " to " + Formater.formatDate(publicHolidays.getDtHolidayDateTo(), "yyyy-MM-dd")+"</font>";
                                                    %>
                                                    <td colspan="2"><div align="center"><%=title%>													  </div>
                                                <hr></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="18" valign="middle" colspan="3" class="listtitle">
                                                    <%
                                                    String stsEntliment = publicHolidays.getEntitlement() +" List ";
                                                    
                                                        if(iCommand== Command.ADD || iCommand == Command.EDIT){
                                                            stsEntliment = " Set Date Time " + publicHolidays.getEntitlement();
                                                        }
                                                    %>
                                                    &nbsp;<%=stsEntliment%> </td>
                                              </tr>
                                              <%
                                            try{
                                            //if (listPublicLeave!=null && listPublicLeave.size()>0){
                                            %>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                   <%=drawListPublicLeave(iCommand, start,frmPublicLeave, publicLeave, listPublicLeave, oidPublicLeave,publicHolidays.getDtHolidayDate(),publicHolidays.getDtHolidayDateTo(),oidPublicHoliday,oidSysHoliday,listCategory)%></td> 
                                              </tr>
                                              <% // } 
						  }catch(Exception exc){ 
                                                      System.out.println(exc);
						  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
                                                   int cmd = 0;
                                                           if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                                                (iCommand == Command.NEXT || iCommand == Command.LAST))
                                                                        cmd =iCommand; 
                                                   else{
                                                          if(iCommand == Command.NONE || prevCommand == Command.NONE)
                                                                cmd = Command.FIRST;
                                                          else 
                                                                cmd =prevCommand; 
                                                   } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%>                                                  </span> </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(msgString!=null && msgString.length()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>

                                              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="2"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidPublicLeave+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidPublicLeave+"')";
									String scancel = "javascript:cmdEdit('"+oidPublicLeave+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setBackCaption("Back to List Public Leave");
									ctrLine.setSaveCaption("Save Public Leave");
									ctrLine.setConfirmDelCaption("Yes Delete Publioc Leave");
									ctrLine.setDeleteCaption("Delete Public Leave");
                                                                        ctrLine.setAddCaption("Add Public Leave");
									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									
									if(iCommand == Command.ASK){
										ctrLine.setDeleteQuestion(msgString);
                                                                        }
                                                                        if(iCommand == Command.SAVE){
										ctrLine.setSaveCaption("");
                                                                                ctrLine.setDeleteCaption("");
                                                                                ctrLine.setBackCaption("");
                                                                        }
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                   <a href="javascript:cmdBackListHoliday()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                        <a href="javascript:cmdBackListHoliday()" class="command">Back to List Holidays </a>
                                                </td>
                                                
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
                                        <%if(iCommand==Command.BACK || (iCommand == Command.FIRST || iCommand == Command.PREV )|| 
                                                                (iCommand == Command.NEXT || iCommand == Command.LAST)|| iCommand == Command.DELETE){%>
                                        <tr>
                                            <td>
                                                <table width="100%">
					
                                                    <tr>
                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"> 
                                                        <a href="javascript:cmdAdd()" class="command">Add New Public Leave</a> 
                                                         <a href="javascript:cmdBackListHoliday()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                        <a href="javascript:cmdBackListHoliday()" class="command">Back to List Holidays </a>                                                      </td>
                                                    </tr>
                                                </table>
                                            </td>
                                             
                                        </tr>
                                        <%}%>
                                      </table>
                                      <table width="100%">
                                                                    
                          <tr>
                              <td class="listtitle"><hr>List Employee  | <b><a href="javascript:editMain()" nowrap>Edit Main</a></b></td>
                              
                          </tr>
                          <tr>
                              <td>
                                  <table width="100%">
                                      <tr>
                            <td width="6%" nowrap="nowrap"> <div align="left">Payrol Num </div></td>
                              <td width="30%" nowrap="nowrap">:
                            <input type="text" size="40" name="emp_number"  value="<%= empNum!=null && empNum.length()>0?empNum:"" %>"  class="elemenForm" onKeyDown="javascript:fnTrapKD()" disabled="true"> </td>
                              <td width="5%" nowrap="nowrap"> Full Name </td>
                              <td width="59%" nowrap="nowrap">:<input type="text" size="50" name="full_name"  value="<%= fullName!=null && fullName.length()>0?fullName:""%>"  class="elemenForm" onKeyDown="javascript:fnTrapKD()" disabled="true"> </td>
                          </tr>
                           <!-- update by devin 2104-02-17 -->
                          <tr> 
              <td width="6%" nowrap="nowrap"><div align="left">Company </div></td>
              <td width="30%" nowrap="nowrap">:
                <%
					Vector comp_value = new Vector(1, 1);
					Vector comp_key = new Vector(1, 1);  
String whereComp="";   
/*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
    whereComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcOvertime.getCompanyId();
}*/   
    Vector div_value = new Vector(1, 1);
    Vector div_key = new Vector(1, 1);      
    
    Vector dept_value = new Vector(1, 1);
    Vector dept_key = new Vector(1, 1);                                      
 if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                    div_value.add("0");
                   div_key.add("select ...");
                   
                    dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                    // comp_value.add("0");
                    // comp_key.add("select ...");
                     
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+  whereDiv +")": whereDiv;
                    
                } else {

                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                    try {
                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                        int grpIdx = -1;
                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                        int countIdx = 0;
                        int MAX_LOOP = 10;
                        int curr_loop = 0;
                        do { // find group department belonging to curretn user base in departmentOid
                            curr_loop++;
                            String[] grp = (String[]) depGroup.get(countIdx);
                            for (int g = 0; g < grp.length; g++) {
                                String comp = grp[g];
                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                    grpIdx = countIdx;   // A ha .. found here 
                                }
                            }
                            countIdx++;
                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                        // compose where clause
                        if (grpIdx >= 0) {
                            String[] grp = (String[]) depGroup.get(grpIdx);
                            for (int g = 0; g < grp.length; g++) {
                                String comp = grp[g];
                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                            }
                        }
                    } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);

                    }
                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                    
                     whereComp = whereComp!=null && whereComp.length()>0 ? whereComp + " AND ("+ whereClsDep +")":whereClsDep;
                     
                }
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
     div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }               
    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    String prevCompany="";
    String prevDivision="";
    


long prevCompanyTmp=0;        
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                //}
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
            String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
             //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
             ////update
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                if(oidCompany!=0){ 
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
                }
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                    if(oidCompany!=0){
                       div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision();             
                   }
                    //update by satrya 2013-09-19
                    else if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){
                        div_key.add(dept.getDivision());
                        div_value.add(""+dept.getDivisionId()); 
                        
                        //update by satrya 2013-09-19
                        dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                   }
                   
                }else{
                    
                     div_key.add(dept.getDivision());
                       div_value.add(""+dept.getDivisionId()); 

                       dept_key.add(dept.getDepartment());
                       dept_value.add(String.valueOf(dept.getOID()));
                       prevCompany = dept.getCompany();
                       prevDivision = dept.getDivision(); 
                }
            }
        }
 }else{ 
       if(oidCompany!=0){  
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 

                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    prevCompany = dept.getCompany();
                    prevDivision = dept.getDivision();             
       }
 }
            
            }
           else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }
           
        }
    }  
			%>
                <%= ControlCombo.draw("hidden_companyId", "formElemen", null, ""+oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"disabled=true")%> </td>
              <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
              <td width="59%" nowrap="nowrap">:
                <%
					
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(oidCompany==0){ 
                                      oidDivision=0; 
                                   }

if(oidCompany!=0){
    whereComp = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+oidCompany;
 listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
    prevCompany="";
    prevDivision="";
    
    div_value = new Vector(1, 1);
    div_key = new Vector(1, 1);      
    
    dept_value = new Vector(1, 1);
    dept_key = new Vector(1, 1); 

    prevCompanyTmp=0;  
long tmpFirstDiv=0;   

if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                   comp_value.add("0");
                   comp_key.add("select ...");
                   
                   div_value.add("0");
                   div_key.add("select ...");
                   
                   dept_value.add("0");
                   dept_key.add("select ...");
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) { 
                       //div_value.add("0");
                       //div_key.add("select ...");
                   
                       dept_value.add("0");
                       dept_key.add("select ...");
                    
                } 
            }
        }
 }else{
    comp_value.add("0");
    comp_key.add("select ...");
    
    div_value.add("0");
    div_key.add("select ...");

    dept_value.add("0");
    dept_key.add("select ...");
 }
   long prevDivTmp=0;
    for (int i = 0; i < listCostDept.size(); i++) {
        Department dept = (Department) listCostDept.get(i);
        if (prevCompany.equals(dept.getCompany())) {
            if (prevDivision.equals(dept.getDivision())) {
                //update
                if(oidDivision!=0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                }
                //lama
                /*
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
                */
                
            } 
            else {
                div_key.add(dept.getDivision());
                div_value.add(""+dept.getDivisionId());
                if(dept_key!=null && dept_key.size()==0){
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID())); 
                }
                prevDivision = dept.getDivision();
            }
        } else {
           String chkAdaDiv="";
            if(div_key!=null && div_key.size()>0){
                chkAdaDiv = (String)div_key.get(0);
            }
            if((div_key!=null && div_key.size()==0 ) || ( chkAdaDiv.equalsIgnoreCase("select ..."))){ 
             //comp_key.add(dept.getCompany());
             //comp_value.add(""+dept.getCompanyId());
             
            
             
             if(prevDivTmp!=dept.getDivisionId()){
                    div_key.add(dept.getDivision());
                    div_value.add(""+dept.getDivisionId()); 
                    prevDivTmp=dept.getDivisionId();
              }
             
                    tmpFirstDiv=dept.getDivisionId(); 

                   // dept_key.add(dept.getDepartment());
                 //   dept_value.add(String.valueOf(dept.getOID()));           
               
            prevCompany = dept.getCompany();
            prevDivision = dept.getDivision();             
            }
           /*else{
              if(prevCompanyTmp!=dept.getCompanyId()){
                    comp_key.add(dept.getCompany());
                    comp_value.add(""+dept.getCompanyId());
                    prevCompanyTmp=dept.getCompanyId();
              }
              
            }*/
          String chkAdaDpt="";
          if(whereComp!=null && whereComp.length()>0){
                chkAdaDpt = "("+(whereComp!=null && whereComp.length()==0?"1=1":whereComp)+ ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] +"="+oidDivision;
          }
            Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
            if((listCheckAdaDept==null || listCheckAdaDept.size()==0)){
                
if (processDependOnUserDept) {
        if (emplx.getOID() > 0) {
            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                
            } else {
                Position position = null;
                try {
                    position = PstPosition.fetchExc(emplx.getPositionId());
                } catch (Exception exc) {
                }
                
                oidDivision=tmpFirstDiv;
              
            }
        }
 }else{
    oidDivision = tmpFirstDiv;
     
 }
               
            }
        }
    }
}
			%>
                <%= ControlCombo.draw("hidden_divisionId", "formElemen", null,""+oidDivision , div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"disabled=true")%> </td>
                                                                                            </td>
                          </tr>
                          <tr> 
                              <td width="6%" align="right" nowrap> 
                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>                                                                                            </td>
                              <td width="30%" nowrap="nowrap"> : 
                                  <%

                                      Vector dept_valuee  = new Vector(1, 1);
                                      Vector dept_keye = new Vector(1, 1);
                                      //Vector listDept = new Vector(1, 1);
                                      DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                      if (processDependOnUserDept) {
                                          if (emplx.getOID() > 0) {
                                              if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                  keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                  //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                              } else {
                                                  Position position = null;
                                                  try {
                                                      position = PstPosition.fetchExc(emplx.getPositionId());
                                                  } catch (Exception exc) {
                                                  }
                                                  if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                      String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                      keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                  } else {

                                                      String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                              + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                      try {
                                                          String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                          Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                          int grpIdx = -1;
                                                          int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                          int countIdx = 0;
                                                          int MAX_LOOP = 10;
                                                          int curr_loop = 0;
                                                          do { // find group department belonging to curretn user base in departmentOid
                                                              curr_loop++;
                                                              String[] grp = (String[]) depGroup.get(countIdx);
                                                              for (int g = 0; g < grp.length; g++) {
                                                                  String comp = grp[g];
                                                                  if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                      grpIdx = countIdx;   // A ha .. found here 
                                                                  }
                                                              }
                                                              countIdx++;
                                                          } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                          // compose where clause
                                                          if (grpIdx >= 0) {
                                                              String[] grp = (String[]) depGroup.get(grpIdx);
                                                              for (int g = 0; g < grp.length; g++) {
                                                                  String comp = grp[g];
                                                                  whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                              }
                                                          }
                                                      } catch (Exception exc) {
                                                              System.out.println(" Parsing Join Dept" + exc);

                                                      }
                                                      keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                      //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                  }
                                              }
                                          } else {
                                              //dept_valuee.add("0");
                                              //dept_key.add("select ...");
                                              keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                              //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                          }
                                      } else {
                                          //dept_valuee.add("0");
                                          //dept_key.add("select ...");
                                          keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                          //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                      }
                                      dept_valuee = keyList.getDepIDs();
                                      dept_keye = keyList.getDepNames();

                                      String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                  %>
                                  <%//=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"")%>

                            <%=ControlCombo.draw("department", "elementForm", null, selectValueDepartment , dept_valuee, dept_keye, " onChange=\"javascript:cmdUpdateDep()\" disabled=true")%>                                                                                            </td>
                             <td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                              <td width="59%" nowrap="nowrap">:
                                  <%
                                      Vector sec_value = new Vector(1, 1);
                                      Vector sec_key = new Vector(1, 1);
                                      sec_value.add("0");
                                      sec_key.add("select ...");

                                      //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                      //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                      String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                      Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                      for (int i = 0; i < listSec.size(); i++) {
                                          Section sec = (Section) listSec.get(i);
                                          sec_key.add(sec.getSection());
                                          sec_value.add(String.valueOf(sec.getOID()));
                                      }
                                  %>
                            <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key, "disabled=true")%>                                                                                            </td>
                          </tr>
                          <!-- emp category -->
                           <tr> 
                              <td width="6%" align="right" nowrap> 
                            <div align="left">Employee Category </div></td>
                              <td width="30%" nowrap="nowrap"> : 
                                 <%		
                                                Vector cat_value = new Vector();
                                                Vector cat_key = new Vector();
                                                String whereClauseEmpCat="";
                                                String empCat  = "";
                                                //String whereClause = "";
                                                try{
                                                    empCat  = PstSystemProperty.getValueByName("OID_EMP_CATEGORY");
                                                    if(empCat!=null && empCat.length()>0){
                                                        whereClauseEmpCat = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID]+" IN ("+empCat+")";
                                                    }
                                                }catch(Exception ex){
                                                    System.out.println("Exception System properties OID_EMP_CATEGORY ca'nt ben set"+ex);
                                                }
                                                Vector listCategoryX = PstEmpCategory.list(0, 500, whereClauseEmpCat, ""); 
                                                cat_value.add("0");
                                                cat_key.add("-selected-");
                                                for(int idxCat = 0 ; idxCat < listCategoryX.size(); idxCat++){ 
                                                    EmpCategory empCategory = (EmpCategory)listCategoryX.get(idxCat); 
                                                    
                                                    cat_value.add(""+empCategory.getOID());
                                                    cat_key.add(""+empCategory.getEmpCategory());
                                                }
                                                
                                                String selectValueSection = ""+oidEmpCategory;
                                                
					    %>

                             <%=ControlCombo.draw("empCategory","elementForm", null, selectValueSection, cat_value, cat_key ," onkeydown=\"javascript:fnTrapKD()\"disabled=true") %>                              </td>     
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                          </tr>
                           <!-- update by devin 2014-02-17 -->
                              <tr>
                                   <td width="5%" align="left" nowrap valign="top">Religion                                                                                            </td>
                              <td width="59%" nowrap="nowrap">:
                                  <%
                                      Vector reg_value = new Vector(1, 1);
                                      Vector reg_key = new Vector(1, 1);
                                    
                                      

                                      //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                      //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                      String ReligionWhere = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID]+ "= " +  oidPublicHoliday;
                                      Vector listReligion = PstPublicHolidays.list(0, 0, ReligionWhere, "");
                                       long oidReligion1=0;
                                      for (int i = 0; i < listReligion.size(); i++) {
                                          PublicHolidays publicHolidays1 = (PublicHolidays) listReligion.get(i);
                                          oidReligion1 = publicHolidays1.getiHolidaySts();
                                          String whereAgama = PstReligion.fieldNames[PstReligion.FLD_RELIGION_ID]+ "= " + oidReligion1;
                                           Vector listReligion1 = PstReligion.list(0, 0, whereAgama, "");
                                           for(int x=0;x<listReligion1.size();x++){ 
                                               Religion religion1 = (Religion)listReligion1.get(x); 
                                               reg_key.add(religion1.getReligion()); 
                                               reg_value.add(String.valueOf(religion1.getOID()));  
                                           }
                                          reg_value.add("0");
                                          reg_key.add("select");
                                          
                                      }
                                  %>
                            <%=ControlCombo.draw("religion", null, "" + (oidReligion==0?oidReligion1:oidReligion), reg_value, reg_key," onkeydown=\"javascript:fnTrapKD()\"disabled=true")%> </td> 
                                  
                              </tr>
						  <!-- set Type OT -->
						  <tr>
						  	<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						 </tr>
						
						  <tr>
						    <td>Note</td>
						    <td>:<input name="<%=FrmPublicLeaveDetail.fieldNames[FrmPublicLeaveDetail.FRM_FIELD_PUBLIC_LEAVE_DETAIL_NOTE]%>" type="text" size="50" maxlength="50" value="<%="Cuti :"+titlePublicHoliday%>"></td>
						    <td>&nbsp;</td>
						    <td>&nbsp;</td>
						    </tr>
						  
                                                   
						  
                          <%if(!(iCommand==Command.LIST)){%>
                          <tr>
                              <td>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                          <tr> 
                                              <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                              <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                              <td width="94" class="command" nowrap><a href="javascript:cmdView()">View 
                                                      Employee</a></td>
                                          </tr>
                                </table>                              </td>
                          </tr>
                          <%}%>
                            
                           <%
                            if(errorCode ==1 ){
                             %>
                          <tr>
                              <td>
                                   <!-- update by devin 2014-02-08 -->
                            <table width="100%">
                              <tr>
                              <td style="background-color:yellow; color: red" ><b>MAAF DATA TIDAK DI TEMUKAN</b></td>
                             </tr>
                               </table>
                              </td>
                          </tr>
                           <%
                                 }
                           if(iErrCodeDetail>0){
                                 %>
                          <tr>
                              <td>
                                   <!-- update by devin 2014-02-08 -->
                                                    <table width="100%">
                                                    <tr>
                                                      <td style="background-color:yellow; color: red" ><b>DATA HAS BEEN SAVED</b></td>                 
                                                     </tr>
                                  </table>
                              </td>
                          </tr>
                           <%
                                    }
                             
                                %>
                                
                                
                                
                                  </table>
                              </td>
                          </tr>
                          <%if(iCommand==Command.LIST ||  iCommand==Command.SAVE){%> 
                          <tr>
                              <td>
                                  <table width="100%">
                                      <tr>
                                          <td>
                                               <%=drawListEmployee(out,start,listEmployee,isHRDLogin,oidSysHoliday,tblCekErrorPublicLeave,iCommand)%> 
                                          </td>
                                      </tr>
                                  </table>
                              </td>
                          </tr>
                          <%}%>
                           <tr>
                                                     <%
        long oidLeave = 1;//jika 0 berati leave. pertama tama di set 0 dahulu nnti ketika prosess baru di set Leave'nya
        Vector vTypeKey = new Vector();
        Vector vTypeValue = new Vector();
        vTypeKey.add(oidSysHoliday + "");
        vTypeKey.add(oidLeave + "");
        vTypeValue.add("OFF");
        vTypeValue.add("LEAVE");
                                                        %>
						  	<td>
							<table>
								<tr>
									<td>
											<!--set selected employee to take : <//%=ControlCombo.draw("typeLeave","elementForm", null, ""+typePublicLeave, vTypeKey,vTypeValue ," onkeydown=\"javascript:fnTrapKD()\"") %>-->
									</td>
									 <td width="16"><a href="javascript:cmdExcLeave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Excecute Leave"></a></td>
                                              <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                              <td width="94" class="command" nowrap><a href="javascript:cmdExcLeave()">Save</a></td>
                                                                    
								</tr>
							</table>
						</td>
						 </tr>

                                      </table>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
<script type="text/javascript" src="<%=approot%>/javascripts/superTables.js"></script>
<script type="text/javascript">
//<![CDATA[

(function () {
    new superTable("demoTable", {
    	cssSkin : "sDefault",
        fixedCols : 0,
		headerRows : 1
    });
})();

//]]>
</script>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
