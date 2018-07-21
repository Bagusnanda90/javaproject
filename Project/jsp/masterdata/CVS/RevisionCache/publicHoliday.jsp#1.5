<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.masterdata.Religion"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.harisma.entity.masterdata.PublicHolidays"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlPublicHolidays"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPublicHolidays"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPublicHolidays"%>
<%@page import="com.dimata.util.Command"%>
<%
/*
 * Page Name  		:  publicHoliday.jsp
 * Created on 		:  [date] [time] AM/PM
 *
 * @author  		:  [authorName]
 * @version  		:  [version]
 */

    /*******************************************************************
     * Page Description 	: [project description ... ]
     * Imput Parameters 	: [input parameter ...]
     * Output 			: [output ...]
     *******************************************************************/
%>

<%@ page import="java.util.Vector,
                 com.dimata.gui.jsp.ControlList,
                 com.dimata.harisma.entity.masterdata.PstReligion,
                 com.dimata.harisma.entity.masterdata.Religion,
                 com.dimata.harisma.entity.masterdata.PublicHolidays,
                 com.dimata.util.Formater,
                 com.dimata.harisma.entity.masterdata.PstPublicHolidays,
                 com.dimata.qdep.form.FRMQueryString,
                 java.util.Date,
                 com.dimata.qdep.form.FRMMessage,
                 com.dimata.harisma.form.masterdata.CtrlPublicHolidays,
                 com.dimata.gui.jsp.ControlLine,
                 com.dimata.harisma.form.masterdata.FrmPublicHolidays,
                 com.dimata.util.Command,
                 com.dimata.gui.jsp.ControlCombo,
                 com.dimata.gui.jsp.ControlDate,
                 java.util.Calendar"%>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_PUBLIC_HOLIDAY); %>
<%@ include file = "../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%! public String drawList(Vector objectClass ,  long lHolidayId, Vector religions, I_Dictionary dictionaryD)

{
	String[] stDays = {
		"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	};
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Date", "10%", "2", "0");
	ctrlist.addHeader("Day","10%", "2", "0");
	ctrlist.addHeader(dictionaryD.getWord("PUBLIC_HOLIDAY"), "40%", "2", "0");
        
        if(religions!=null){
        	ctrlist.addHeader("Entitlement", "30%", "0", ""+(3+religions.size()));        
            
        } else{
        	ctrlist.addHeader("Entitlement", "30%", "0", "1");        
        }
        ctrlist.addHeader("National", "5%", "0", "0");
        ctrlist.addHeader("Black Day", "5%", "0", "0");
        ctrlist.addHeader("Yelow Day", "5%", "0", "0");
        if(religions!=null){
                for(int r=0;r<religions.size();r++){
                    Religion rel= (Religion) religions.get(r);
                    ctrlist.addHeader(""+rel.getReligion(), "5%", "0", "0");
                }
            
        }
        
        ctrlist.addHeader("<a href=\"Javascript:SetAllCheckBoxesX('frmholiday','holiday_', true,'10')\">Select All</a> || <a href=\"Javascript:SetAllCheckBoxesX('frmholiday','holiday_', false,'10')\">Deselect All</a>", "15%", "2", "0");
        
	//ctrlist.addHeader("Hindu", "15%", "0", "0");
	//ctrlist.addHeader("Non Hindu", "15%", "0", "0");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	int index = -1;

	for (int i = 0; i < objectClass.size(); i++) {
            	PublicHolidays objPublicHolidays = (PublicHolidays)objectClass.get(i);
		 Vector rowx = new Vector();
		 if(lHolidayId == objPublicHolidays.getOID())
			 index = i;
                if(objPublicHolidays.getDtHolidayDateTo()==null){
                    objPublicHolidays.setDtHolidayDateTo(objPublicHolidays.getDtHolidayDate());
                }

		Calendar objCal = Calendar.getInstance();
		objCal.setTime(objPublicHolidays.getDtHolidayDate());
                Calendar objCalTo = Calendar.getInstance();
		objCalTo.setTime(objPublicHolidays.getDtHolidayDateTo());
               
		rowx.add((objPublicHolidays.getDtHolidayDate()!=null?Formater.formatDate(objPublicHolidays.getDtHolidayDate(), "dd-MM-yyyy"):"")+" to "+
                        ( objPublicHolidays.getDtHolidayDateTo()!=null? Formater.formatDate(objPublicHolidays.getDtHolidayDateTo(), "dd-MM-yyyy"):"-")
                        );
		rowx.add(stDays[objCal.get(Calendar.DAY_OF_WEEK)-1]+" to "+ stDays[objCalTo.get(Calendar.DAY_OF_WEEK)-1]);
		rowx.add(objPublicHolidays.getStDesc());

                if(objPublicHolidays.getiHolidaySts() == PstPublicHolidays.STS_NATIONAL){                        
                            rowx.add(" <div align=\"center\">"+objPublicHolidays.getDays()+"</div>");
                    } else {
                     rowx.add(" ");
                    }
                ///update by satrya 2013-03-5
                if(objPublicHolidays.getiHolidaySts() == PstPublicHolidays.STS_BLACK_DAY){                        
                            rowx.add(" <div align=\"center\">"+objPublicHolidays.getDays()+"</div>");
                    } else {
                     rowx.add(" ");
                   }
               if(objPublicHolidays.getiHolidaySts() == PstPublicHolidays.STS_YELLOW_DAY){                        
                            rowx.add(" <div align=\"center\">"+objPublicHolidays.getDays()+"</div>");
                    } else {
                     rowx.add(" ");
                   }
                               
        if(religions!=null){
                for(int r=0;r<religions.size();r++){
                    Religion rel= (Religion) religions.get(r);
                    if(objPublicHolidays.getiHolidaySts() == rel.getOID()){
                            rowx.add("  <div align=\"center\">"+objPublicHolidays.getDays()+"</div>");
                    }
                    else {
                            rowx.add(" ");
                    }
                   }
            
        }

            /*
		if(objPublicHolidays.getiHolidaySts() == PstPublicHolidays.STS_HINDU){
			rowx.add("1");
			rowx.add("");
		}
		else if(objPublicHolidays.getiHolidaySts() == PstPublicHolidays.STS_NON_HINDU){
			rowx.add("");
			rowx.add("1");
		}
		else{
			rowx.add("1");
			rowx.add("1");
		}
            */
                rowx.add("<input name=\"holiday_"+objPublicHolidays.getOID()+"\" id=\"holiday_"+(i)+"\" type=\"checkbox\" value=\"1\" >");
                //rowx.add("<input name=\"holiday_"+objPublicHolidays.getOID()+"\" id=\"holiday_"+objPublicHolidays.getOID()+"\" type=\"checkbox\" value=\"1\" >");
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(objPublicHolidays.getOID()));
	}

	return ctrlist.drawList(index);
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long lOidHolidayId = FRMQueryString.requestLong(request, ""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]);
long lOidEntitle = FRMQueryString.requestLong(request, ""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS]);
String stCurrYear = FRMQueryString.requestString(request, "curr_year");
String stFromYear = FRMQueryString.requestString(request, "from_year");

int iCurrYear = Calendar.getInstance().get(Calendar.YEAR);

Vector religions = com.dimata.harisma.entity.masterdata.PstReligion.list(0, 10, "", " "+com.dimata.harisma.entity.masterdata.PstReligion.fieldNames[com.dimata.harisma.entity.masterdata.PstReligion.FLD_RELIGION]);


if(stCurrYear.trim().length() == 0){
	stCurrYear = iCurrYear+"";
}

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "Year("+PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]+") = '"+ stCurrYear +"'";
String orderClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE];

CtrlPublicHolidays objCtrlPublicHolidays = new CtrlPublicHolidays(request);
ControlLine ctrLine = new ControlLine();
Vector listHoliday = new Vector(1,1);

/*switch statement */
iErrCode = objCtrlPublicHolidays.action(iCommand , lOidHolidayId,stCurrYear,stFromYear);
/* end switch*/
FrmPublicHolidays objFrmPublicHolidays = objCtrlPublicHolidays.getForm();

/*count list All holiday*/
int vectSize = PstPublicHolidays.getCount(whereClause);

PublicHolidays objPublicHolidays = objCtrlPublicHolidays.getPublicHolidays();
msgString =  objCtrlPublicHolidays.getMessage();

/*switch list holiday*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (lOidHolidayId == 0))
		start = PstPublicHolidays.findLimitStart(objPublicHolidays.getOID(),recordToGet, whereClause, orderClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
				start = objCtrlPublicHolidays.actionList(iCommand, start, vectSize, recordToGet);
 }
/* end switch list*/

/* get record to display */
listHoliday = PstPublicHolidays.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listHoliday.size() < 1 && start > 0)
{
	if (vectSize - recordToGet > recordToGet)
		start = start - recordToGet;   //go to Command.PREV
	else {
		start = 0 ;
		iCommand = Command.FIRST;
		prevCommand = Command.FIRST; //go to Command.FIRST
	}
	listHoliday = PstPublicHolidays.list(start,recordToGet, whereClause , orderClause);
}

    

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Master Data <%=dictionaryD.getWord("PUBLIC_HOLIDAY")%></title>
<style type="text/css">
            #btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            #tdForm {
                padding: 5px;
            }
            
            #info {
                width: 500px;
                padding: 21px; color: #797979; background-color: #F7F7F7;
                border:1px solid #DDD;
                font-size: 12px;
            }
            
        </style>
<script language="JavaScript">

function refreshList(){
    document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value="0";
	document.frmholiday.command.value="<%=Command.NONE%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdAdd(){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value="0";
	document.frmholiday.command.value="<%=Command.ADD%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdGenerate(){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value="0";
	document.frmholiday.command.value="<%=Command.LOAD%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}


function cmdAsk(oidHoliday){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value=oidHoliday;
	document.frmholiday.command.value="<%=Command.ASK%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdConfirmDelete(oidHoliday){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value=oidHoliday;
	document.frmholiday.command.value="<%=Command.DELETE%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}
function cmdSave(){
	document.frmholiday.command.value="<%=Command.SAVE%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}
function cmdGenerateDP(){
	document.frmholiday.command.value="<%=Command.ASSIGN%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}        
function cmdSaveGenerate(){
	document.frmholiday.command.value="<%=Command.POST%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}        

function cmdEdit(oidHoliday){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value=oidHoliday;
	document.frmholiday.command.value="<%=Command.EDIT%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}
        
function cmdEditPublicLeave(){
        document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value="<%=lOidHolidayId%>";
        document.frmholiday.<%=FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS]%>.value="<%=lOidEntitle%>";       
        //document.frmholiday.xxx.value="<%//=source%>";
	document.frmholiday.command.value="<%=Command.ADD%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicleave.jsp";
	document.frmholiday.submit();
    
}
function cmdCancel(oidHoliday){
	document.frmholiday.<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%>.value=oidHoliday;
	document.frmholiday.command.value="<%=Command.EDIT%>";
	document.frmholiday.prev_command.value="<%=prevCommand%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdBack(){
	document.frmholiday.command.value="<%=Command.BACK%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}

function cmdListFirst(){
	document.frmholiday.command.value="<%=Command.FIRST%>";
	document.frmholiday.prev_command.value="<%=Command.FIRST%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdListPrev(){
	document.frmholiday.command.value="<%=Command.PREV%>";
	document.frmholiday.prev_command.value="<%=Command.PREV%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
	}

function cmdListNext(){
	document.frmholiday.command.value="<%=Command.NEXT%>";
	document.frmholiday.prev_command.value="<%=Command.NEXT%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}

function cmdListLast(){
	document.frmholiday.command.value="<%=Command.LAST%>";
	document.frmholiday.prev_command.value="<%=Command.LAST%>";
	document.frmholiday.action="publicHoliday.jsp";
	document.frmholiday.submit();
}


function SetAllCheckBoxes(FormName, FieldName, CheckValue)
            {
                    if(!document.forms[FormName])
                            return;
                        
                    <%
                Vector vPublicHolidays = new Vector();

                try{
                    String orderLev = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE]+" ASC ";
                    vPublicHolidays = PstPublicHolidays.list(0, 0, "", orderLev);
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                 
                for(int iPublicH = 0 ; iPublicH < vPublicHolidays.size() ; iPublicH++){
                    PublicHolidays publicHolidays = new PublicHolidays();
                    publicHolidays = (PublicHolidays)vPublicHolidays.get(iPublicH);
                    long publicHId = publicHolidays.getOID();
                %>    
                    var objCheckBoxes = document.forms[FormName].elements[FieldName<%=publicHId%>];
                    if(!objCheckBoxes)
                            return;
                    var countCheckBoxes = objCheckBoxes.length;
                    if(!countCheckBoxes)
                            objCheckBoxes.checked = CheckValue;
                    else
                            // set the check value for all check boxes
                                    for(var i = 0; i < countCheckBoxes; i++)
                                    objCheckBoxes[i].checked = CheckValue;
                <% } %>
            }

       function SetAllCheckBoxesX(FormName, FieldName, CheckValue, nilai)
            {
                for(var i = 0; i < nilai; i++)
                document.getElementById("holiday_"+i).checked = CheckValue;
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
  <%}%>
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                  Master Data &gt; <%=dictionaryD.getWord(I_Dictionary.COMPANY)%> &gt; <%=dictionaryD.getWord("PUBLIC_HOLIDAY")%><!-- #EndEditable -->
                  </strong></font> </td>
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
                                    <form name="frmholiday" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name=<%=""+FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_ID]%> value="<%=lOidHolidayId%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top">
                                          <td height="8"  colspan="3">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td> 
                                                  <%

                                                    Vector vYear = new Vector();
                                                    for(int i=iCurrYear+installInterval; i<iCurrYear+3; i++){
                                                        vYear.add(i+"");
                                                    }

                                                %>
                                                  &nbsp;Year : <%=ControlCombo.draw("curr_year", null, stCurrYear, vYear, vYear, "onChange=\"refreshList()\"", "formElemen")%>
                                                 <button id="btn1" onclick="cmdGenerateDP()"  >GENERATE DP</button></td>
                                              </tr>
                                              <%
                                try{
                                    if (listHoliday.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listHoliday, lOidHolidayId, religions, dictionaryD)%> </td>
                                              </tr>
                                              <%  } else { %>
                                            
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdGenerate()" class="command">Copy data from year</a> 
                                                      </td>
                                                      
                                                         <!-- Priska 20151021 -->
                                                        <%if(iCommand == Command.LOAD){ %>
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                          <tr> 
                                                            <td colspan="3" class="listtitle">Generate From this Year</td>
                                                          </tr>

                                                          <tr align="left" valign="top"> 
                                                            <td> 
                                                              <%

                                                                Vector vYearFrom = new Vector();
                                                                vYearFrom = PstPublicHolidays.SelectYearNotEmpty();
                                                              //  for(int i=iCurrYear+installInterval; i<iCurrYear+3; i++){
                                                              //      vYearFrom.add(i+"");
                                                              //  }

                                                            %>
                                                              &nbsp;From Year : <%=ControlCombo.draw("from_year", null, stFromYear, vYearFrom, vYearFrom, "", "formElemen")%></td>
                                                          </tr>
                                                          <tr align="left" valign="top" > 
                                                            <td colspan="4"> 
                                                              <a href="javascript:cmdSaveGenerate()" class="command">Generate</a>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                        <% } %>

                                              
                                             <% }
                              }catch(Exception exc){
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
                                          {
                                              if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (lOidHolidayId == 0))
                                                  cmd = PstPublicHolidays.findLimitCommand(start,recordToGet,vectSize);
                                              else
                                                  cmd = prevCommand;
                                          }
                                       }
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                       ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand ==Command.BACK || iCommand ==Command.SAVE)&& (frmEmpCategory.errorSize()<1)){
                                                   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (objFrmPublicHolidays.errorSize()<1)){
                                                    if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New <%=dictionaryD.getWord("PUBLIC_HOLIDAY")%></a> 
                                                      </td>
                                                    
                                                    </tr>
                                                    
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}
                                                  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top">
                                          <td height="8" valign="middle" colspan="3">
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(objFrmPublicHolidays.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){ %>
                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                              <tr> 
                                                <td colspan="3" class="listtitle">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="1%">&nbsp;</td>
                                                <td height="21" valign="middle" colspan="3" class="listtitle"><%=lOidHolidayId==0?"Add":"Edit"%> Holiday</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="1%">&nbsp;</td>
                                                <td height="21" valign="middle" width="7%">&nbsp;</td>
                                                <td height="21" colspan="2" width="92%" class="comment">*)= 
                                                  required</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="1%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%">Date</td>
                                                <td height="21" colspan="2" width="92%"> 
                                                 from <%=ControlDate.drawDateWithStyle(FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_DATE], (objPublicHolidays.getDtHolidayDate()==null) ? new Date() : objPublicHolidays.getDtHolidayDate(), +2, -2, "formElemen", "")%> * <%= objFrmPublicHolidays.getErrorMsg(FrmPublicHolidays.FRM_FIELD_HOLIDAY_DATE) %>
                                                 to   <%=ControlDate.drawDateWithStyle(FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_DATE_TO], (objPublicHolidays.getDtHolidayDateTo()==null) ? new Date() : objPublicHolidays.getDtHolidayDateTo(), +2, -2, "formElemen", "")%> * <%= objFrmPublicHolidays.getErrorMsg(FrmPublicHolidays.FRM_FIELD_HOLIDAY_DATE_TO) %>
                                                </td>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="1%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%"><%=dictionaryD.getWord("DESCRIPTION")%></td>
                                                <td height="21" colspan="2" width="92%"> 
                                                  <input type="text" size="50" width="100" name="<%=objFrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_DESC] %>"  value="<%= objPublicHolidays.getStDesc() %>" class="formElemen">
                                                  * <%= objFrmPublicHolidays.getErrorMsg(FrmPublicHolidays.FRM_FIELD_HOLIDAY_DESC) %></td>
                                              <tr align="left" valign="top"> 
                                                <td height="21" valign="top" width="1%">&nbsp;</td>
                                                <td height="21" valign="top" width="7%">Entitlement</td>
                                                <td height="21" colspan="2" width="92%"> 
                                                  <%
                                                    Vector vKey = new Vector();
                                                    Vector vValue = new Vector();
                                                    Vector vPublicHol_tooltip = new Vector(1, 1);                                                    
                                                    vKey.add(""+PstPublicHolidays.STS_NATIONAL+"");
                                                    vValue.add(""+PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_NATIONAL]);
                                                    vPublicHol_tooltip.add("National Holiday that all employee celebrated");
                                                    vKey.add(""+PstPublicHolidays.STS_BLACK_DAY+"");
                                                    vValue.add(""+PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_BLACK_DAY]);
                                                    vPublicHol_tooltip.add("Working Day that all employee must leave");
                                                    vKey.add(""+PstPublicHolidays.STS_YELLOW_DAY+"");
                                                    vValue.add(""+PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_YELLOW_DAY]);
                                                    vPublicHol_tooltip.add("Holiday that the leave is according to the employee");
                                                    if(religions!=null){
                                                        for(int r=0;r<religions.size();r++){
                                                            Religion rel= (Religion) religions.get(r);
                                                            vPublicHol_tooltip.add("Holiday that only "+rel.getReligion()+" employee can leave");
                                                            vValue.add(""+rel.getReligion());
                                                            vKey.add(""+rel.getOID());
                                                        }
                                                    }
                                                    /*
                                                    vKey.add(PstPublicHolidays.STS_BOTH+"");
                                                    vKey.add(PstPublicHolidays.STS_HINDU+"");
                                                    vKey.add(PstPublicHolidays.STS_NON_HINDU+"");

                                                    vValue.add(PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_BOTH]);
                                                    vValue.add(PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_HINDU]);
                                                    vValue.add(PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_NON_HINDU]);
                                                     */ 
                                                %>
                                                  <%=ControlCombo.drawTooltip(FrmPublicHolidays.fieldNames[FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS], null, objPublicHolidays.getiHolidaySts()+"", vKey, vValue, "formElemen",vPublicHol_tooltip)%> * <%= objFrmPublicHolidays.getErrorMsg(FrmPublicHolidays.FRM_FIELD_HOLIDAY_STS) %> 
                                              <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="1%">&nbsp;</td>
                                                <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                <td height="8" colspan="2" width="92%">&nbsp; 
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="4" class="command"> 
                                                  <%
                                        ctrLine.setLocationImg(approot+"/images");
                                        ctrLine.initDefault();
                                        ctrLine.setTableWidth("80");
                                        String scomDel = "javascript:cmdAsk('"+lOidHolidayId+"')";
                                        String sconDelCom = "javascript:cmdConfirmDelete('"+lOidHolidayId+"')";
                                        String scancel = "javascript:cmdEdit('"+lOidHolidayId+"')";
                                        ctrLine.setBackCaption("Back to List");
                                        ctrLine.setCommandStyle("buttonlink");
                                            ctrLine.setDeleteCaption("Delete");
                                            ctrLine.setSaveCaption("Save");
                                            ctrLine.setAddCaption("");

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
                                                if(iCommand == Command.ASK)
                                                        ctrLine.setDeleteQuestion(msgString);

                                                if(Integer.parseInt(stCurrYear) >= iCurrYear || iCommand == Command.ADD){
                                                    out.print(ctrLine.drawImage(iCommand, iErrCode, msgString));
                                                }
                                                else{
									%>

                                                  <%
                                                }
                                                %>
                                                </td>
                                              </tr>
										                                                  <table width="80">
                                                    <tr> 
                                                      <td valign="top" width="20%"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                          <tr> 
                                                           
                                                            
					<td width="24"><a href="javascript:cmdEditPublicLeave" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnEdit.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Edit Public Leave"></a></td>                                  
                                  <td height="22" valign="middle" colspan="3" width="100" nowrap="nowrap"> 
                                    <a href="javascript:cmdEditPublicLeave()" class="command">Add Public Leave</a> </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                      <td valign="top" width="40%">&nbsp;</td>
                                                    </tr>
                                                  </table>
												
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="7%">&nbsp;</td>
                                                <td width="92%">&nbsp;</td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="4"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                            
                                            
                                            
                                            
                                         
                                            
                                            
                                            
                                            
                                            
                                          </td>
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
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
