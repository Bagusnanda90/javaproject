<%-- 
    Document   : coa
    Created on : Jun 18, 2015, 4:41:09 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.aiso.form.masterdata.FrmPerkiraan"%>
<%@page import="com.dimata.aiso.form.masterdata.CtrlPerkiraan"%>
<%@page import="com.dimata.aiso.entity.masterdata.Perkiraan"%>
<%@page import="com.dimata.aiso.entity.masterdata.PstPerkiraan"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCustomFieldMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.CustomFieldMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmCustomFieldMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlCustomFieldMaster"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  0;
try{
 	appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);
 }catch(Exception e){
 	System.out.println("Exception on componseObjCode ::::: "+e.toString());  
 }%>
<%@ include file = "../main/checkuser.jsp" %>

<%

//if of "hasn't access" condition 
if (!privView && !privAdd && !privUpdate && !privDelete && !privPrint) 
{
%>

<script language="javascript">
	window.location="<%=approot%>/nopriv.html";
</script>

<!-- if of "has access" condition -->
<%
}
else 
{
%>
<%! 
// this constant used to list text of listHeader 
public static final String textJspTitle[][] = 
{
	{
		"Induk",//0
		"Nomor",//1
		"Nama",//2
		"Level",//3
		"Saldo Normal",//4
		"Status",//5
		"Departemen",//6 
		"Anggaran", //7
		"Bobot", //8
		"Perkiraan Acuan",//9 
		"Catatan",//10
		"Perusahaan",//11
		"Perkiraan Hutang/Piutang",//12
		"Jenis Perkiraan",//13
		"Jenis Biaya",//14
		"Biaya Tetap / Variabel",//15
                "Mapping"
	},
	{
		"Reference",
		"Code",
		"Name",
		"Level",
		"Normal Sign",
		"Status",
		"Department", 
		"Budget", 
		"Weight", 
		"General Account", 
		"Note",
		"Company",
		"AR/AP Account",
		"Account Type",
		"Expense Type",
		"Fixed/Variable Exp",
                "Mapping"
	}	
};

public String getJspTitle(String textJsp[][], int index, int language, String prefiks, boolean addBody)
{
	String result = "";
	if(addBody)
	{
		if(language==com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT)
		{	
			result = textJsp[language][index] + " " + prefiks;
		}
		else
		{
			result = prefiks + " " + textJsp[language][index];		
		}
	}
	else
	{
		result = textJsp[language][index];
	} 
	return result;
}

public static final String pageTitle[] = 
{
	"Perkiraan","Chart of Account"	
};

public String getDivisionName(long oid){
    String str = "-";
    try {
        Division div = PstDivision.fetchExc(oid);
        str = div.getDivision();
    } catch(Exception ex){
        System.out.println("getDivisionName()=>"+ex.toString());
    }
    return str;
}

public String getDepartmentName(long oid){
    String str = "-";
    try {
        Department depart = PstDepartment.fetchExc(oid);
        str = depart.getDepartment();
    } catch(Exception ex){
        System.out.println("getDepartmentName()=>"+ex.toString());
    }
    return str;
}

public String getSectionName(long oid){
    String str = "-";
    try {
        Section section = PstSection.fetchExc(oid);
        str = section.getSection();
    } catch(Exception ex){
        System.out.println("getSectionName()=>"+ex.toString());
    }
    return str;
}

public String getMapping(long oid){
    String str = "";
    String where = " ID_PERKIRAAN = "+oid;
    Vector listMapping = PstComponentCoaMap.list(0, 0, where, "");
    if (listMapping != null && listMapping.size()>0){
        str +="<table class=\"tblStyle\">";
        str +="<tr>";
        str +="<td class=\"title_tbl\">Division</td>";
        str +="<td class=\"title_tbl\">Department</td>";
        str +="<td class=\"title_tbl\">Section</td>";
        str +="<td class=\"title_tbl\">Formula</td>";
        str +="<td class=\"title_tbl\">&nbsp;</td>";
        str +="</tr>";
        for(int i=0; i<listMapping.size(); i++){
            ComponentCoaMap coaMap = (ComponentCoaMap)listMapping.get(i);
            str += "<tr>";
            str += "<td>"+getDivisionName(coaMap.getDivisionId())+"</td>";
            str += "<td>"+getDepartmentName(coaMap.getDepartmentId())+"</td>";
            str += "<td>"+getSectionName(coaMap.getSectionId())+"</td>";
            str += "<td>"+coaMap.getFormula()+"</td>";
            str += "<td><button id=\"btnX\" onclick=\"cmdAskItem('"+coaMap.getOID()+"')\">&times;</button></td>";
            str += "</tr>";
        }
        str +="</table>";
    }
    return str;
}

public String drawList(Vector objectClass, long oid, int language)
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
	ctrlist.dataFormat(getJspTitle(textJspTitle,1,language,"",false),"","center","left");
	ctrlist.dataFormat(getJspTitle(textJspTitle,2,language,"",false),"","center","left");
	ctrlist.dataFormat(getJspTitle(textJspTitle,6,language,"",false),"","center","left");
	ctrlist.dataFormat(getJspTitle(textJspTitle,3,language,"",false),"","center","center");	
	ctrlist.dataFormat(getJspTitle(textJspTitle,5,language,"",false),"","center","left");
	ctrlist.dataFormat(getJspTitle(textJspTitle,16,language,"",false),"","center","left");

	ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
	
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();						
	
    String psn = ""; 
	int lvl = 0;
	String strLvl = "";
	String link = "";
	String closeLink ="";
	int index = -1;
        
        Hashtable hHeaders = PstPerkiraan.getListCoAByOidHeaderOnly();
        
	try 
	{ 								
		for (int i = 0; i < objectClass.size(); i++) 
		{
			 Perkiraan perkiraan = (Perkiraan)objectClass.get(i);
			 
			 if(oid==perkiraan.getOID())
			 {
			 	index =i;
			 }
			 
			 String strDepartment = "";
			 Department objDepartment = new Department();			 
			 
			 if(perkiraan.getDepartmentId() != 0){
			 	try{
					objDepartment = PstDepartment.fetchExc(perkiraan.getDepartmentId());
				}catch(Exception e){
					System.out.println("Exception on fetch objDepartment ==> "+e.toString());
				}
			 } 
			 
			 if(objDepartment != null)
			 	strDepartment = objDepartment.getDepartment();
				
			 psn = PstPerkiraan.arrStrPostable[language][perkiraan.getPostable()];

			 Vector rowx = new Vector();
			 lvl = perkiraan.getLevel();
			 switch(lvl)
			 {
			    case 2 : strLvl = "&nbsp;&nbsp;"; 
						 break;
			    case 3 : strLvl = "&nbsp;&nbsp;&nbsp;&nbsp;";
						 break;
			    case 4 : strLvl = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						 break;
			    case 5 : strLvl = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						 break;
			    case 6 : strLvl = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						 break;
			    default : strLvl = "";
			 }
			 link ="<a href=\"javascript:cmdEdit('"+String.valueOf(perkiraan.getOID())+"')\">";
			 closeLink = "</a>"; 
			 rowx.add(strLvl+link+perkiraan.getNoPerkiraan()+closeLink);  
			 
			 String strAccountName = "";
			 if(language == com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN)
			 {			 
			 	strAccountName = perkiraan.getAccountNameEnglish();
			 }
			 else
			 {
			 	strAccountName = perkiraan.getNama();			 
			 }
                         
                         String noteParent="",noteLevel = "", notePostable="";
                         if(perkiraan.getIdParent()!=0){
                            Perkiraan parent = (Perkiraan) hHeaders.get(""+perkiraan.getIdParent());	
                            if(parent!=null){
                                if(parent.getLevel()!= (perkiraan.getLevel()-1)){
                                    noteLevel="<h3>Level Account and Level Parent Account are not appropriate set,<br> please check this account and the parent account</h3>";
                                }
                                
                            }else{
                                noteParent = "<h3>Parent account not found, <b> please check the account shall be the header account,<br> may be it is postable, set to notpostable</h3>";
                            }
                         }
			 rowx.add(strAccountName+"<br>"+noteParent); 
			 rowx.add(strDepartment);
			 rowx.add(String.valueOf(lvl)+"<br>"+noteLevel); 
			 rowx.add(psn);
                                                                           
                        // get account IJ mapping                          
                        try{
                            String button = "";
                            if (psn.equals("Header")){
                                button = "";
                            } else {
                                button = "<button id=\"btn\" onclick=\"javascript:cmdAddMapping('"+perkiraan.getOID()+"','"+objDepartment.getOID()+"')\">add mapping list</button>";
                                button += "&nbsp;";
                                button += "<button id=\"btn\" onclick=\"javascript:cmdAskMap('"+perkiraan.getOID()+"')\">delete all</button>";
                            }
                            String addMapping = button;
                            addMapping += getMapping(perkiraan.getOID());
                            rowx.add(addMapping);
                        }catch(Exception exc2){
                            rowx.add("");
                        }
			lstData.add(rowx);
		}
	} 
	catch(Exception exc) 
	{
		System.out.println("Exc when drawList : " + exc.toString());
	}
	return ctrlist.drawMe(index); 
}



%>
<!DOCTYPE html>
<%
// get request from hidden text
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start"); 
long iAccountOID = FRMQueryString.requestLong(request,"accountchart_id"); 
int iAccountGroup = FRMQueryString.requestInt(request,"accountchart_group");
int iAccountType = FRMQueryString.requestInt(request,"accountchart_type");
long oidPerkiraan = FRMQueryString.requestLong(request, "id_perkiraan");
int commandCustom = FRMQueryString.requestInt(request, "command_custom");
long oidMap = FRMQueryString.requestLong(request, "id_map");
//int arapAccount = FRMQueryString.requestInt(request,"arapAccount");
//long departmentOid = FRMQueryString.requestLong(request, "departmentOid");
long companyId = FRMQueryString.requestLong(request, "companyId");
long lGeneralAccOid = FRMQueryString.requestLong(request, "generalAccOid");
if(iAccountGroup == 0) iAccountGroup = PstPerkiraan.ACC_GROUP_LIQUID_ASSETS;	
int listCommand = FRMQueryString.requestInt(request, "list_command");
if(listCommand==Command.NONE) listCommand = Command.LIST; 

//AisoBudgeting objAisoBudg = SessAisoBudgeting.getCurrentAisoBudgeting(iAccountOID);
//double dBudget = objAisoBudg.getBudget();
// variable declaration 
int recordToGet = 20;

if(iCommand == Command.EDIT || iCommand == Command.ADD){
	recordToGet = 5;
}

// Setup controlLine and Commands caption
ControlLine ctrLine = new ControlLine();
ctrLine.setLanguage(SESS_LANGUAGE);
String currPageTitle = pageTitle[SESS_LANGUAGE];
String strAddAcc = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ADD,true);
String strUploadAcc = "Upload "+ currPageTitle;
String strListAcc = (SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? "List " : "Daftar ")+currPageTitle;
String strSaveAcc = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_SAVE,true);
String strAskAcc = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_ASK,true);
String strBackAcc = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_BACK,true)+(SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? " List" : "");
String strDeleteAcc = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_DELETE,true);
String strCancel = ctrLine.getCommand(SESS_LANGUAGE,currPageTitle,ctrLine.CMD_CANCEL,false);
String delConfirm = (SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? "Are You Sure to Delete " : "Anda Yakin Menghapus ")+currPageTitle+" ?";
String strHeader = (SESS_LANGUAGE==com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN ? "LIST " : "DAFTAR ")+(PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][iAccountGroup]).toUpperCase();
String strCmbOption[] = {"- Semua Departemen -", "- All Departments -"};
String strCmbFirstSelection = strCmbOption[SESS_LANGUAGE];
String strNoAccSelected = (SESS_LANGUAGE == com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN) ? "- No Reference Account -" : "- Tidak ada referensi perkiraan -";
String strAccGroup = (SESS_LANGUAGE == com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN) ? "Group Account" : "Kelompok Perkiraan";
String strAccPostable = (SESS_LANGUAGE == com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN) ? "The Account is set to level 1 and postable account. Account at Level 1 has to be a header !" : "Code Perkiraan di set Level 1 dan postable. Kode perkiraan di Level 1 harus header ! ?";
String strNote = (SESS_LANGUAGE == 1) ? "Entry required" : "Harus diisi";

// Manage controlList and Action process
Control controlList = new Control();
CtrlPerkiraan ctrlPerkiraan = new CtrlPerkiraan(request);  
ctrlPerkiraan.setLanguage(SESS_LANGUAGE);
int ctrlErr = ctrlPerkiraan.action(iCommand, iAccountOID); 
FrmPerkiraan frmPerkiraan = ctrlPerkiraan.getForm();
//frmPerkiraan.setDecimalSeparator(sUserDecimalSymbol);
//frmPerkiraan.setDigitSeparator(sUserDigitGroup);
Perkiraan perkiraan = ctrlPerkiraan.getPerkiraan(); 

if(iCommand == Command.ADD){
	perkiraan = new Perkiraan();
	//dBudget = 0;
}

departmentOid = perkiraan.getDepartmentId();
companyId = perkiraan.getCompanyId();
//arapAccount = perkiraan.getArapAccount();

// get list of department object
String strWhere = "";
String strOrder = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
Vector vectDept = PstDepartment.list(0, 0, strWhere, strOrder);


// Update value of application variable id command is SUBMIT
if(iCommand==Command.SUBMIT)
{
	if(vectDept!=null && vectDept.size()>0)
	{
		//AppValue.updateAppValueHashtable(vectDept);
	}
}

// Proses list account chart ...
Vector vectAccountList = PstPerkiraan.getAllAccount(iAccountGroup, 0);
Vector vectAccountList_2 = null;//( perkiraan!=null && perkiraan.getAccountGroup() != iAccountGroup ) ? PstPerkiraan.getAllAccount(perkiraan.getAccountGroup(), 0): null;
if(vectAccountList_2!=null){
    vectAccountList.addAll(vectAccountList_2);
}
int vectSize = vectAccountList.size(); 
if(iAccountType == iAccountGroup)
{
	start = controlList.actionList(listCommand, start, vectSize, recordToGet); 
}
else
{
	start = 0;
	iAccountType = iAccountGroup;
}

Vector vCompany = com.dimata.aiso.entity.masterdata.PstCompany.list(0, 0, "", com.dimata.aiso.entity.masterdata.PstCompany.fieldNames[com.dimata.aiso.entity.masterdata.PstCompany.FLD_COMPANY_NAME]); 
System.out.println(frmPerkiraan.getErrors());

    if (commandCustom == 2){
        String wMap = " ID_PERKIRAAN="+oidPerkiraan+" ";
        Vector listMap = PstComponentCoaMap.list(0, 0, wMap, "");
        if (listMap != null && listMap.size()>0){
            for (int i=0; i<listMap.size(); i++){
                ComponentCoaMap coaMap = (ComponentCoaMap)listMap.get(i);
                
                try {
                    long oidMaps = PstComponentCoaMap.deleteExc(coaMap.getOID());
                } catch (Exception e){
                    System.out.println("delete coa mapping=>"+e.toString());
                }
            }
        }
    }

    if (commandCustom == 4){
        try {
            long oidMapItem = PstComponentCoaMap.deleteExc(oidMap);
        } catch (Exception e){
            System.out.println("delete coa item=>"+e.toString());
        }
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accounting Information System Online</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
              margin: 3px 0px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
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
            
            #btn2 {
              background: #7cc5e3;
              border: 1px solid #488eab;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn2:hover {
              background: #488eab;
              border: 1px solid #316a82;
            }
            #btnX {border:1px solid #CCC;color:#575757;background-color:#EEE;padding:3px;}
            #btnX:hover {border:1px solid #B7B7B7;background-color: #CCC;color:#FFF;}
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #confirm2 {background-color: #7cc5e3;border: 1px solid #488eab; color: #FFF; padding: 14px 21px;border-radius: 5px;}
            #desc_field_type{padding:7px 12px; background-color: #F3F3F3; border:1px solid #FFF; margin:3px 0px;}
            #text_desc {background-color: #FFF;color:#575757; padding:3px; font-size: 9px;}
            #data_list{padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px;}
            #data_list_close {padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px; cursor: pointer;}
            #data_list_close:hover {padding:3px 5px; color:#FFF; background-color: #0099FF; margin:2px 1px 2px 0px; border-radius: 3px;}
        </style>
        <script language="JavaScript">
<%
if((privAdd)&&(privUpdate)&&(privDelete))
{
%>
function addNew(){
	document.frmaccountchart.accountchart_id.value="0";
	document.frmaccountchart.command.value="<%=Command.ADD%>";
	document.frmaccountchart.departmentOid.value = "0";
	document.frmaccountchart.companyId.value = "0";
	//document.frmaccountchart.<%//=frmPerkiraan.fieldNames[frmPerkiraan.FRM_ARAP_ACCOUNT]%>.value = "0";
	document.frmaccountchart.generalAccOid.value = "0";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdUpload(){
	document.frmaccountchart.command.value="<%=Command.SUBMIT%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdSave()
{
	var level = document.frmaccountchart.LEVEL.value;
	var posted = document.frmaccountchart.POSTABLE.value;
	if(level==1 && posted==1)
	{		
            alert("<%=strAccPostable%>");              
                document.frmaccountchart.LEVEL.focus();
               
           
	}
	else
	{	
		document.frmaccountchart.departmentOid.value = document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_DEPARTMENT_ID]%>.value;
		document.frmaccountchart.companyId.value = document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_COMPANY_ID]%>.value;
		//document.frmaccountchart.generalAccOid.value = document.frmaccountchart.<%//=frmPerkiraan.fieldNames[frmPerkiraan.FRM_GENERAL_ACCOUNT_LINK]%>.value;
		document.frmaccountchart.command.value="<%=Command.SAVE%>";
		document.frmaccountchart.action = "account_chart.jsp#down";
		document.frmaccountchart.submit();		
	}
}

        function cmdAskMap(oid){
            document.frmaccountchart.command_custom.value="1";
            document.frmaccountchart.id_perkiraan.value = oid;
            document.frmaccountchart.action = "account_chart.jsp";
            document.frmaccountchart.submit();
        }
        function cmdDeleteAllMap(oid){
            document.frmaccountchart.command_custom.value = "2";
            document.frmaccountchart.id_perkiraan.value = oid;
            document.frmaccountchart.action = "account_chart.jsp";
            document.frmaccountchart.submit();
        }
        
        function cmdBatal(){
            document.frmaccountchart.command_custom.value="0";
            document.frmaccountchart.action = "account_chart.jsp";
            document.frmaccountchart.submit();
        }
        
        function cmdAskItem(oid){
            document.frmaccountchart.command_custom.value = "3";
            document.frmaccountchart.id_map.value = oid;
            document.frmaccountchart.action = "account_chart.jsp";
            document.frmaccountchart.submit();
        }
        
        function cmdDeleteMapItem(oid){
            document.frmaccountchart.command_custom.value = "4";
            document.frmaccountchart.id_map.value = oid;
            document.frmaccountchart.action = "account_chart.jsp";
            document.frmaccountchart.submit();
        }

function cmdAsk(oid){
	document.frmaccountchart.accountchart_id.value=oid;
	document.frmaccountchart.command.value="<%=Command.ASK%>";
	document.frmaccountchart.action="account_chart.jsp#down";
	document.frmaccountchart.submit();
}

function cmdDelete(oid){
	document.frmaccountchart.accountchart_id.value=oid;
	document.frmaccountchart.command.value="<%=Command.DELETE%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdEditMap(mapType, mapOid, boSys ){
    var msg= mapType + "," + mapOid;    
    if( mapType == "<%//=I_IJGeneral.MAP_TYPE_PAYMENT%>" ){
      //alert("Type"+msg);
      cmdEditPayMap(mapOid, boSys);
    } else if ( mapType == "<% //=I_IJGeneral.MAP_TYPE_ACCOUNT %>" ){
      cmdEditAccMap(mapOid, boSys);
    } else if ( mapType == "<% //=I_IJGeneral.MAP_TYPE_LOCATION %>" ){
      cmdEditLocationMap(mapOid, boSys);
    }
}

function cmdEditPayMap(oidIjPaymentMapping, boSys){
     var strUrl = "../ij/mapping/ijpaymentmapping.jsp?hidden_ij_map_payment_id="+
         oidIjPaymentMapping+"&command=<%=Command.EDIT%>"+"&prev_command=<%=Command.LIST%>"+
         "&<% //=FrmIjPaymentMapping.fieldNames[FrmIjPaymentMapping.FRM_FIELD_BO_SYSTEM]%>="+ boSys;
       //alert(""+strUrl);
    var awindow = window.open(strUrl, "editpaymap", "");
    awindow.focus();
}

function cmdEditAccMap(oidIjAccMapping, boSys){
     var strUrl = "../ij/mapping/ijaccountmapping.jsp?hidden_ij_map_account_id="+
         oidIjAccMapping+"&command=<%=Command.EDIT%>"+"&prev_command=<%=Command.LIST%>"+
         "&<% //=FrmIjAccountMapping.fieldNames[FrmIjAccountMapping.FRM_FIELD_BO_SYSTEM]%>="+ boSys;
       //alert(""+strUrl);
    var awindow = window.open(strUrl, "editaccmap", "");
    awindow.focus();
}

function cmdEditLocationMap(oidIjLocMapping, boSys){
     var strUrl = "../ij/mapping/ijlocationmapping.jsp?hidden_ij_map_location_id="+
         oidIjLocMapping+"&command=<%=Command.EDIT%>"+"&prev_command=<%=Command.LIST%>"+
         "&<% //=FrmIjLocationMapping.fieldNames[FrmIjLocationMapping.FRM_FIELD_BO_SYSTEM]%>="+ boSys;
       //alert(""+strUrl);
    var awindow = window.open(strUrl, "editlocmap", "");
    awindow.focus();
}

function cmdAddLocMap(oidCoA){
     var strUrl = "../ij/mapping/ijlocationmapping.jsp?<% //=FrmIjLocationMapping.fieldNames[FrmIjLocationMapping.FRM_FIELD_ACCOUNT]%>preset="+
         oidCoA+"&command=<%=Command.ADD %>"+"&prev_command=<%=Command.LIST%>";
       //alert(""+strUrl);
    var awindow = window.open(strUrl, "addlocmap", "");
    awindow.focus();
}

function cmdAddAccMap(oidCoA){
     var strUrl = "../ij/mapping/ijaccountmapping.jsp?<% //=FrmIjLocationMapping.fieldNames[FrmIjLocationMapping.FRM_FIELD_ACCOUNT]%>preset="+
         oidCoA+"&command=<%=Command.ADD %>"+"&prev_command=<%=Command.LIST%>";
       //alert(""+strUrl);
    var awindow = window.open(strUrl, "addlocmap", "");
    awindow.focus();
}

<%
}
%>
function cmdAddMapping(val,oidDepart)
{
	var linkPage = "coa_map.jsp?oid_coa="+val+"&oid_department="+oidDepart;
	var newWindow =window.open(linkPage,null,"height=480,width=640,status=no,toolbar=no,menubar=yes,location=no,scrollbars=yes");
        newWindow.focus();
}

function cmdListAccount()
{
	var linkPage = "account_list.jsp?accountchart_group=<%=iAccountGroup%>&str_header=<%=strHeader%>";
	var newWindow =window.open(linkPage,null,"height=480,width=640,status=no,toolbar=no,menubar=yes,location=no,scrollbars=yes");
        newWindow.focus();
}
function cmdListBudget()
{
	var linkPageBudget = "account_list_budget.jsp?accountchart_group=<%=iAccountGroup%>&str_header=<%=strHeader%>";
	var newWindow = window.open(linkPageBudget,null,"height=680,width=1000,status=no,toolbar=no,menubar=yes,location=no,scrollbars=yes");
        newWindow.focus();
}



<%
String refreshParent[] = {"Set Ulang Perkiraan Induk","Reset Parent Account"};
if(privUpdate) 
{
%>
function cmdRefreshParent(){        
	var linkPage = "account_list.jsp?accountchart_group=<%=iAccountGroup%>&str_header=<%=strHeader%>&command=<%=Command.REFRESH %>";
        
        if(confirm("<%=refreshParent[SESS_LANGUAGE>1 || SESS_LANGUAGE < 0? 0:SESS_LANGUAGE] %> ? "))
        
	window.open(linkPage,null,"height=480,width=640,status=no,toolbar=no,menubar=yes,location=no,scrollbars=yes");
}
<%
}
%>


function cmdCancel(){
	document.frmaccountchart.command.value="<%=Command.EDIT%>";
	document.frmaccountchart.action="account_chart.jsp#down";
	document.frmaccountchart.submit();
}

function cmdEdit(oid){
	document.frmaccountchart.accountchart_id.value=oid;
	document.frmaccountchart.departmentOid.value = "0";
	document.frmaccountchart.generalAccOid.value = "0";
	document.frmaccountchart.command.value="<%=Command.EDIT%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function first(){
	document.frmaccountchart.list_command.value="<%=Command.FIRST%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}
function prev(){
	document.frmaccountchart.list_command.value="<%=Command.PREV%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function next(){
	document.frmaccountchart.list_command.value="<%=Command.NEXT%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}
function last(){
	document.frmaccountchart.list_command.value="<%=Command.LAST%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdBackList(){
	document.frmaccountchart.command.value="<%=Command.NONE%>";
	document.frmaccountchart.list_command.value="<%=Command.LIST%>";
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdChange(){
	if(document.frmaccountchart.TANDA_DEBET_KREDIT.value=="1"){
		document.frmaccountchart.KREDIT.value=" +";
	} else {
		document.frmaccountchart.KREDIT.value=" -";
	}
}

function cmdItemData(tipe){
	document.frmaccountchart.command.value="<%=Command.NONE%>";	
	document.frmaccountchart.list_command.value="<%=Command.LIST%>";
	document.frmaccountchart.accountchart_group.value=tipe;
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdClasification(){
	var classType = document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]%>.value;	
	document.frmaccountchart.command.value="<%=Command.NONE%>";	
	document.frmaccountchart.list_command.value="<%=Command.LIST%>";
	document.frmaccountchart.accountchart_group.value=classType;
	document.frmaccountchart.action="account_chart.jsp";
	document.frmaccountchart.submit();
}

function cmdChangeLevel(){
	var level = document.frmaccountchart.LEVEL.value;
	if(level==1){
	   document.frmaccountchart.POSTABLE.options[1].selected=true;
	}
 
}

function cmdEditBudget() 
{
    document.frmaccountchart.action = "edit_budget.jsp";
    document.frmaccountchart.submit();
}

function chgExpenseType()
{
	var expType = document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_EXPENSE_TYPE]%>.value;
	if(expType == 0){
		document.getElementById("fixedvar").style.display="";
	}else{
		document.getElementById("fixedvar").style.display="none";
	}
}
</script>
<!--  -->
<script language="JavaScript" type="text/javascript" >
            var xMLHttpRequest = new XMLHttpRequest();
 function findCoAParent(){
                var value = document.getElementById("<%=( frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]+"_INPUT" )%>").value;            
                xMLHttpRequest.open("Get","<%=approot%>/servlet/com.dimata.aiso.entity.masterdata.CoAServlet?groupid="+value,true );
                xMLHttpRequest.onreadystatechange = processCoA;
                xMLHttpRequest.send(null);
            }
            function processCoA(){
                if( (xMLHttpRequest.readyState == 4) && (xMLHttpRequest.status=200) ){ 
                    var jasonData = '{'+xMLHttpRequest.responseText+'}';
                    var JSONTopicObject  = eval("(function(){return " + jasonData + ";})()");
                    var select= document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%> ;// document.getElementById("<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%>");
                    select.options.length = 0;
                    
                    var CoAs = JSONTopicObject.CoAs;
                    var i=0; 
                    while (i < CoAs.length ){
                        var oCoA = CoAs[i++];                                                
                        select.options[i] = new Option(oCoA.name,oCoA.id);              
                    }
                    
                }
            }            
            
            
            function findCoAParentX(){
               //alert("Group ID"+document.getElementById("<%=( frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]+"_INPUT" )%>").value);
              //  alert("Select: " + document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%>);   
               //alert("Select: " + document.getElementById("<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%>"));   
               
                var value = document.getElementById("<%=( frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]+"_INPUT" )%>").value;
                var select= document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%> ;// document.getElementById("<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%>");
                
                var length = select.options.length;
                for (i = 0; i < length; i++) {
                  select.options[i] = null;
                }
                alert(length);
             
                xMLHttpRequest.open("Get","<%=approot%>/servlet/com.dimata.aiso.entity.masterdata.CoAServlet?groupid="+value,true );
                xMLHttpRequest.onreadystatechange = processCoA;
                xMLHttpRequest.send(null);
                //alert("Send");
            }
            function processCoAX(){
                //alert(" readyState : "+xMLHttpRequest.readyState + " status "+ xMLHttpRequest.status+" xml= " + xMLHttpRequest.responseText);
                if( (xMLHttpRequest.readyState == 4) && (xMLHttpRequest.status=200) ){ 
                    var jasonData = '{'+xMLHttpRequest.responseText+'}';
                    //alert(jasonData);
                    var JSONTopicObject  = eval("(function(){return " + jasonData + ";})()");
                    //alert("JSONTopicObject.result"+JSONTopicObject.result);
                    //alert("JSONTopicObject.count"+JSONTopicObject.count);                    

                    var select= document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%> ;// document.getElementById("<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT]%>");
                    var CoAs = JSONTopicObject.CoAs;
                    var i=0; 
                    //alert("CoAs" + CoAs.length);
                    while (i < CoAs.length ){
                        var oCoA = CoAs[i++];                                                
                        var opt = document.createElement("option");
                        // Add an Option object to Drop Down/List Box
                        select.options.add(opt);
                        // Assign text and value to Option object
                        opt.text = oCoA.name;
                        opt.value = oCoA.id;                        

                    }
                    
                }
            } 
    
            
        </script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <%
                                    PstPerkiraan pstperkiraan = new PstPerkiraan();
                                    pstperkiraan.setIndex(iAccountGroup);
                                    %>
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <%=currPageTitle%> : <font color="#CC3300"><%=pstperkiraan.getAccountGroupName(SESS_LANGUAGE).toUpperCase()%></font> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
              <form name="frmaccountchart" method="post" action="">
                  <input type="hidden" name="command_custom" value="<%=commandCustom%>" />
               <input type="hidden" name="id_perkiraan" value="<%=oidPerkiraan%>">  
               <input type="hidden" name="id_map" value="<%=oidMap%>">
              <input type="hidden" name="accountchart_group" value="<%=iAccountGroup%>">
              <input type="hidden" name="accountchart_type" value="<%=iAccountType%>">			  
              <input type="hidden" name="command" value="<%=iCommand==Command.ADD ? Command.ADD : Command.NONE%>">
              <input type="hidden" name="accountchart_id" value="<%=iAccountOID%>">
              <input type="hidden" name="vectSize" value="<%=vectSize%>">
              <input type="hidden" name="start" value="<%=start%>">
              <input type="hidden" name="list_command" value="<%=Command.LIST%>">
              <input type="hidden" name="departmentOid" value="<%=departmentOid%>">
			  <input type="hidden" name="companyId" value="<%=companyId%>">
              <input type="hidden" name="generalAccOid" value="<%=lGeneralAccOid%>">
              <table>
                        <%
                        if (commandCustom == 1){
                        %>
                        <tr>
                            <td colspan="3">
                                <span id="confirm">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn1" onclick="javascript:cmdDeleteAllMap('<%=oidPerkiraan%>')">Yes</button>
                                    &nbsp;<button id="btn1" onclick="javascript:cmdBatal()">No</button>
                                </span>
                            </td>
                        </tr>
                        <%
                        }
                        if (commandCustom == 3){
                        %>
                        <tr>
                            <td colspan="3">
                                <span id="confirm2">
                                    <strong>Are you sure to delete item ?</strong> &nbsp;
                                    <button id="btn2" onclick="javascript:cmdDeleteMapItem('<%=oidMap%>')">Yes</button>
                                    &nbsp;<button id="btn2" onclick="javascript:cmdBatal()">No</button>
                                </span>
                            </td>
                        </tr>
                        <%    
                        }
                        %>
              </table>
              <table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
                  <td colspan="2">&nbsp; 
                  </td>
                </tr>
              </table>
              <table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
                  <td colspan="2"><font face="Verdana, Arial, Helvetica, sans-serif" size="2"><%=strAccGroup%> :</font>
                  <%
				  String attrClass = "onChange=\"javascript:cmdClasification()\"";				  
				  Vector vectClassVal = new Vector(1,1);				  				  				  
				  Vector vectClassKey = new Vector(1,1);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_LIQUID_ASSETS);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_LIQUID_ASSETS]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_FIXED_ASSETS);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_FIXED_ASSETS]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_OTHER_ASSETS);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_OTHER_ASSETS]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_CURRENCT_LIABILITIES);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_CURRENCT_LIABILITIES]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_LONG_TERM_LIABILITIES);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_LONG_TERM_LIABILITIES]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_EQUITY);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_EQUITY]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_REVENUE);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_REVENUE]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_COST_OF_SALES);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_COST_OF_SALES]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_EXPENSE);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_EXPENSE]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_OTHER_REVENUE);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_OTHER_REVENUE]);
				  
				  vectClassVal.add(""+PstPerkiraan.ACC_GROUP_OTHER_EXPENSE);					
				  vectClassKey.add(""+PstPerkiraan.arrAccountGroupNames[SESS_LANGUAGE][PstPerkiraan.ACC_GROUP_OTHER_EXPENSE]);
				  				  				  				  				  				  				  				  				  				  				  				  				  
				  out.println("&nbsp;"+ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP],null,""+iAccountGroup,vectClassVal,vectClassKey,attrClass));
				  %>
                  </td>
                </tr>
              </table>	
                  
              <table width="100%" cellpadding="0" cellspacing="0" class="listgenactivity">

                  <tr> 
                  <td colspan="2"> 
				    <%
					Vector listPerkiraan = new Vector(1,1);
					for (int item=start; item<(start + recordToGet) && item<vectSize; item++) 
					{
						listPerkiraan.add(vectAccountList.get(item));
					}

                 	if((listPerkiraan!=null)&&(listPerkiraan.size()>0))
					{  
						out.println(drawList(listPerkiraan,perkiraan.getOID(),SESS_LANGUAGE)); 
					}
					else
					{
						out.println("<font size=\"2\" color=\"#FF0000\">&nbsp;No Account Available ...</font>");
					}
			  		%>                     
                    <table width="100%">
                      <tr> 
                        <td colspan="2"> 
						<%
							ctrLine.initDefault(SESS_LANGUAGE,"");
						%>
						<span class="command"><%=ctrLine.drawMeListLimit(listCommand, vectSize, start, recordToGet,"first","prev","next","last","left")%> </span> </td>
                      </tr>
                    </table>
                    <table width="100%" cellpadding="1" cellspacing="1">
                      <% if( (ctrlErr==CtrlPerkiraan.RSLT_OK) && (iCommand!=Command.ADD)&&(iCommand!=Command.ASK)&&(iCommand!=Command.EDIT)&&(frmPerkiraan.errorSize()<1) ) { %>
                      <tr>
                        <td colspan="6" class="command"><hr color="#00CCFF" size="2"></td>
                      </tr>
                      <tr>
                        <td colspan="6" class="command"><%if(privAdd){%>
                          &nbsp;<a id="btn" href="javascript:addNew()"><%=strAddAcc%></a> 
                          <%if(iCommand==Command.SAVE){%>
                          <a id="btn" href="javascript:cmdUpload()"><%=strUploadAcc%></a> 
                          <%}%>
                          <a id="btn" href="javascript:cmdListAccount()"><%=strListAcc%></a>
                          <%}%>
                          <%if(privPrint){%>
                          <a id="btn" href="javascript:cmdListAccount()"><%=strListAcc%></a>  
                          <%}%>
                          <%if(privUpdate){
                              
                            %>
                          <a id="btn" href="javascript:cmdRefreshParent()"><%=refreshParent[SESS_LANGUAGE>1 || SESS_LANGUAGE < 0? 0:SESS_LANGUAGE] %></a>
                          <%}%>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="6" class="command">&nbsp;</td>
                      </tr>
                      <%}%>
                      <% if(((iCommand==Command.SAVE)&& ((frmPerkiraan.errorSize()>0) || (ctrlErr!=CtrlPerkiraan.RSLT_OK)) )||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){ %>
                      <tr>
                        <td width="15%" nowrap height="25">&nbsp;</td>
                        <td width="2%" align="center">&nbsp;</td>
                        <td width="83%"><span class="fielderror">*)&nbsp;=&nbsp;<%=strNote%></span></td>
                      </tr>
                      <tr>
                        <td height="28" nowrap>&nbsp;<%=textJspTitle[SESS_LANGUAGE][11]%></td>
                        <td align="center"><div align="center"><strong>:</strong></div></td>
                        <td colspan="4">
							<%
								Vector vCompanyVal = new Vector();
								Vector vCompanyKey = new Vector();
								String selectedCompany = ""+perkiraan.getCompanyId();
								if(vCompany != null && vCompany.size() > 0)
								{
									for(int t = 0; t < vCompany.size(); t++)
									{
										com.dimata.aiso.entity.masterdata.Company objCompany = (com.dimata.aiso.entity.masterdata.Company) vCompany.get(t);
										vCompanyVal.add(""+objCompany.getOID());
										vCompanyKey.add(objCompany.getCompanyName());
									}
								}
							%>
                            <%=ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_COMPANY_ID], "", null, selectedCompany, vCompanyVal, vCompanyKey)%> </td>
                      </tr>
                      <tr>
                        <td width="15%" height="28" nowrap>&nbsp;<%=getJspTitle(textJspTitle, 6, SESS_LANGUAGE, currPageTitle, false)%></td>
                        <td width="2%" align="<%
							 Vector vectCompKey = new Vector();
							 Vector vectCompName = new Vector();
							 String strSelectedComp = String.valueOf(companyId);							 
							%>center"><b>:</b></td>
                        <td colspan="4" width="83%"><table width="100%">
                            <tr>
                              <td id="down"><!-- ------------------ Update by DWI ------------------- 
				 Digunakan untuk menampilkan departement sesuai dengan wewenang user.
				 Alurnya :
				 - Kenali OID user yang masuk
				 - Cari departement berdasarkan OID user
				 - Masukan department ke combo departement -->
                                  <%
                  Vector vctDept = PstDepartment.list(0, 1000, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT ]);
                  /*Vector vUsrCustomDepartment = PstDataCustom.getDataCustom(userOID, "hrdepartment");
                  if(vUsrCustomDepartment!=null && vUsrCustomDepartment.size()>0){
                  int iDataCustomCount = vUsrCustomDepartment.size();
                    for(int i=0; i<iDataCustomCount; i++){
                        DataCustom objDataCustom = (DataCustom) vUsrCustomDepartment.get(i);
                        Department dept = new Department();
                        try{
                            dept = PstDepartment.fetchExc(Long.parseLong(objDataCustom.getDataValue()));
                        }catch(Exception e){
                            System.out.println("Err >> department : "+e.toString());
                        }
                        vctDept.add(dept);
                    }
                  }*/

                 Vector vectDeptKey = new Vector();
                 Vector vectDeptName = new Vector();
                 vectDeptKey.add("0");
                 vectDeptName.add(strCmbFirstSelection);
                 int iDeptSize = vctDept.size();
                 for (int deptNum = 0; deptNum < iDeptSize; deptNum++){
                     Department dept = (Department) vctDept.get(deptNum);
                     vectDeptKey.add(String.valueOf(dept.getOID()));
                     vectDeptName.add(dept.getDepartment());

                 }
                 String strSelectedDept = String.valueOf(departmentOid);
                 if(departmentOid==0 && iDeptSize==1){
                    strSelectedDept = String.valueOf(vectDeptKey.get(0));
                 }
                %>
                                  <!--  ---------------------------- End Update -------------------------	-->
                                  <%=ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_DEPARTMENT_ID], "", null, strSelectedDept, vectDeptKey, vectDeptName)%> <span class="fielderror">&nbsp;&nbsp;&nbsp;&nbsp;<%=frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_DEPARTMENT_ID)%></span> </td>
                            </tr>
                        </table></td>
                        
                       <tr>
                        <td width="15%" nowrap>&nbsp;<%=strAccGroup%> </td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td>
                                  <%=("&nbsp;"+ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]+"_INPUT",null,""+perkiraan.getAccountGroup() ,vectClassVal,vectClassKey,
                                   "onchange=\"javascript:findCoAParent();\"  id=\""+ frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_GROUP]+"_INPUT" +"\" ")) %></td>
                            </tr>
                        </table></td>
                      </tr> 
                      </tr>
                        <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,0,SESS_LANGUAGE,currPageTitle,true)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td><%
								  Vector accCodeKey = new Vector(1,1);
								  Vector accOptionStyle = new Vector(1,1);
								  Vector accCodeVal = new Vector(1,1);
								  String strSelect = String.valueOf(perkiraan.getIdParent());																	  

								  Vector listCode = new Vector(1,1);
								  for (int item=0; item < vectSize; item++) 
								  {
									 Perkiraan account = (Perkiraan) vectAccountList.get(item);
									 if (account.getPostable() == PstPerkiraan.ACC_NOTPOSTED)									 
									 {		 									 
										 listCode.add(account);
									 }
								  }  								  

								  if(listCode!=null && listCode.size()>0)
								  {
										String space = "";
										String style = "";
										for(int i=0; i<listCode.size(); i++)
										{  
										   Perkiraan perk = (Perkiraan)listCode.get(i); 
										   switch(perk.getLevel())
										   {
											   case 1 : space = ""; style= "Style=\"font-weight:bold; color:#000000\""; break; 
											   case 2 : space = "&nbsp;&nbsp;&nbsp;&nbsp;"; style= "Style=\"font-weight:bold; color:#000000\""; break;
											   case 3 : space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; style= "Style=\"font-weight:bold; color:#000000\""; break;
											   case 4 : space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; style= "Style=\"font-weight:bold; color:#000000\"";break;
											   case 5 : space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; style= "Style=\"font-weight:bold; color:#000000\""; break;
											   case 6 : space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; style= "Style=\"font-weight:bold; color:#000000\""; break;															    															   															   															   															   
										   }
										   accCodeKey.add(""+perk.getOID());
										   accOptionStyle.add(style);
										   accCodeVal.add(space + perk.getNoPerkiraan() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+perk.getNama()) ; 
									 } 
								  }																																				  
								%>
                                  <%=ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_IDPARENT], strNoAccSelected,strSelect,accCodeKey,accCodeVal)%></td>
                            </tr>
                        </table></td>
                      </tr>                      
                      
                      <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,1,SESS_LANGUAGE,currPageTitle,true)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td valign="top"><input type="text" name="<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_NOPERKIRAAN] %>" value="<%=perkiraan.getNoPerkiraan()%>" maxlength="15" size="15">
                                  <span class="fielderror">&nbsp;*)&nbsp;&nbsp;<%=frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_NOPERKIRAAN)%></span></td>
                            </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,2,SESS_LANGUAGE,currPageTitle,true)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td><input type="text" name="<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_NAMA] %>" size="60" value="<%=perkiraan.getNama()%>">
                                  <span class="fielderror">&nbsp;*)&nbsp;&nbsp;(Indonesia)&nbsp;&nbsp;<%=frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_NAMA)%><b></b></span> </td>
                            </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td width="15%"></td>
                        <td width="2%"></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td><input type="text" name="<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_ACCOUNT_NAME_ENGLISH]%>" size="60" value="<%=perkiraan.getAccountNameEnglish()%>">
                                  <span class="fielderror">&nbsp;*)&nbsp;&nbsp;(English)&nbsp;&nbsp;<%=frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_ACCOUNT_NAME_ENGLISH)%></span> </td>
                            </tr>
                        </table></td>
                      </tr>
                      <%if(iCommand != Command.ADD){%>
                      <tr>
                        <td width="15%" height="36" nowrap>&nbsp;<%=getJspTitle(textJspTitle,7,SESS_LANGUAGE,currPageTitle,true)%></td>
                        <td width="2%" height="36"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%" height="36"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td><input type="text" name="budget" size="30" value="<%=frmPerkiraan.userFormatStringDecimal(0)%>" class="cmbDisabledStyle" readonly style="text-align: right">
                                &nbsp;&nbsp;&nbsp;<a href="javascript:cmdEditBudget()"><b>Edit&nbsp;<%=getJspTitle(textJspTitle, 7, SESS_LANGUAGE, "", false)%></b></a> </td>
                            </tr>
                        </table></td>
                      </tr>
                      <%}//if(iCommand != Command.ADD){%>
                      <!-- tr>
                       <td width="11%" nowrap>&nbsp;<%//=getJspTitle(textJspTitle, 8, SESS_LANGUAGE, "", false)%></td>
                       <td width="2%" align="center"><b>:</b></td>
                       <td colspan="4" width="87%">
                        <table border="0" cellspacing="0" cellpadding="0">
                         <tr>
                          <td width="62">
                          <input type="text" size="6" name="<%//=frmPerkiraan.fieldNames[frmPerkiraan.FRM_WEIGHT]%>" value="<%//=Formater.formatNumber(perkiraan.getWeight(), "##,###.##")%>" style="text-align: right;"></td>
                          <td width="138">&nbsp;&nbsp;%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%//=getJspTitle(textJspTitle, 9, SESS_LANGUAGE, "", false)%>&nbsp;&nbsp;</td>
                          <td width="70">
                          <%
                           Vector vectAccKey = new Vector();
                           Vector vectAccName = new Vector();
                           
                           for (int item = 0; item < vectSize; item++) {
                                Perkiraan account = (Perkiraan) vectAccountList.get(item);
                                String strName = "";
                                switch (account.getLevel()) {
                                case 1 : strName = "";
                                break;
                                case 2 : strName = "&nbsp;&nbsp;&nbsp;&nbsp;";
                                break;
                                case 3 : strName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                break;
                                case 4 : strName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                break;
                                case 5 : strName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                break;
                                case 6 : strName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                                break;
                                default : ;
                                }
                                strName += account.getNoPerkiraan();
                                if (account.getPostable()==PstPerkiraan.ACC_POSTED) {
                                    strName += "&nbsp;&nbsp;&nbsp;&nbsp;";
                                    vectAccKey.add(String.valueOf(account.getOID()));
                                } else {
                                    strName += "&nbsp;&nbsp;>>&nbsp;&nbsp;";
                                    vectAccKey.add(String.valueOf(0 - account.getOID()));
                                }    
                                strName += account.getNama();
                                vectAccName.add(strName);
                           }
                          	String strSelectedAcc = String.valueOf(perkiraan.getGeneralAccountLink());
                          %>
                          <%//=ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_GENERAL_ACCOUNT_LINK], strNoAccSelected, strSelectedAcc, vectAccKey, vectAccName)%>
                          </td>
                         </tr>
                        </table>
                       </td>
                      </tr -->
                      <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,3,SESS_LANGUAGE,"",false)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td>
                                  <select name="<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_LEVEL] %>" onChange="javascript:cmdChangeLevel()" style="width: 50">
                                  <% int levelValue = perkiraan.getLevel(); 
								     if(levelValue>0){ 
								    	for(int i=1;i<7;i++) {
							        	   if (levelValue==i) { 
								  %>
                                  <option value="<%=i%>" selected><%=i%></option>
                                  <%       } else { 
			   					  %>
                                  <option value="<%=i%>"><%=i%></option>
                                  <%       } 
					    			} 
								  %>
                                  <% } else {
								  %>
                                  <option value="1" selected>1</option>
                                  <% 	 for(int i=2;i<7;i++) { 
								 %>
                                  <option value="<%=i%>"><%=i%></option>
                                  <%   } 
					   			  } 
								 %>
                                </select>
                                  <span class="fielderror"><%=frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_LEVEL)%></span></td>
                            </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td height="27" nowrap>&nbsp;<%=textJspTitle[SESS_LANGUAGE][13]%></td>
                        <td height="27"><div align="center"><b>:</b></div></td>
                        <td colspan="4" height="27"><input name="<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_ARAP_ACCOUNT]%>" type="checkbox" value="1" <%if(perkiraan.getArapAccount() == 1){%>checked<%}%>>
                          &nbsp;&nbsp;<%=textJspTitle[SESS_LANGUAGE][12]%> </td>
                      </tr> 
					  <%if(iAccountGroup == PstPerkiraan.ACC_GROUP_EXPENSE){%>               
                      <tr>
                        <td nowrap>&nbsp;<%=textJspTitle[SESS_LANGUAGE][14]%></td>
                        <td><b><div align="center">:</div></b></td>
                        <td colspan="4"><%
								out.println(ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_EXPENSE_TYPE], null,""+perkiraan.getExpenseType(),PstPerkiraan.getExpTypeVal(SESS_LANGUAGE),PstPerkiraan.getExpTypeKey(SESS_LANGUAGE),"onChange=\"javascript:chgExpenseType()\""));
							%>                          &nbsp;&nbsp;
                          <%//=textJspTitle[SESS_LANGUAGE][14]%>						</td>
                      </tr>
                      <tr id="fixedvar">
                        <td nowrap>&nbsp;<%=textJspTitle[SESS_LANGUAGE][15]%></td>
                        <td><b><div align="center">:</div></b></td>
                        <td colspan="4">
							<%
								out.println(ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_EXPENSE_FIXED_VARIABLE], null,""+perkiraan.getExpenseFixedVar(),PstPerkiraan.getExpFVVal(SESS_LANGUAGE),PstPerkiraan.getExpFVKey(SESS_LANGUAGE)));
							%>							
                          &nbsp;&nbsp;<%//=textJspTitle[SESS_LANGUAGE][15]%>						</td>
                      </tr>
					  <%}%>
                      <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,4,SESS_LANGUAGE,"",false)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td width="6%"><%
								/*
								public static final int ACC_NOTPOSTED  = 0;
								public static final int ACC_POSTED 	   = 1;
								public static String[][] arrStrPostable = 
								{
									{
										"Header",
										"Postable",        
									},
									{
										"Header",
										"Postable",                    
									}
								};
								*/    
								%>
                                  <%
								  Vector valNormalSign = new Vector(1,1);																									
								  Vector keyNormalSign = new Vector(1,1);
								  
								  valNormalSign.add(""+PstPerkiraan.ACC_DEBETSIGN);					
								  keyNormalSign.add(""+PstPerkiraan.arrStrNormalSign[SESS_LANGUAGE][PstPerkiraan.ACC_DEBETSIGN]);
								  
								  valNormalSign.add(""+PstPerkiraan.ACC_KREDITSIGN);					
								  keyNormalSign.add(""+PstPerkiraan.arrStrNormalSign[SESS_LANGUAGE][PstPerkiraan.ACC_KREDITSIGN]);								  
																																																								  
								  out.println(ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_TANDA_DEBET_KREDIT],null,""+perkiraan.getTandaDebetKredit(),valNormalSign,keyNormalSign,""));
								  out.println("<span class=\"fielderror\">"+frmPerkiraan.getErrorMsg(frmPerkiraan.FRM_TANDA_DEBET_KREDIT)+"</span>");
								%>                              </td>
                            </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td width="15%" nowrap>&nbsp;<%=getJspTitle(textJspTitle,5,SESS_LANGUAGE,"",false)%></td>
                        <td width="2%"><div align="center"><b>:</b></div></td>
                        <td colspan="4" width="83%"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td><%
								  Vector valPostable = new Vector(1,1);																									
								  Vector keyPostable = new Vector(1,1);
								  
								  valPostable.add(""+PstPerkiraan.ACC_NOTPOSTED);					
								  keyPostable.add(""+PstPerkiraan.arrStrPostable[SESS_LANGUAGE][PstPerkiraan.ACC_NOTPOSTED]);
								  
								  valPostable.add(""+PstPerkiraan.ACC_POSTED);					
								  keyPostable.add(""+PstPerkiraan.arrStrPostable[SESS_LANGUAGE][PstPerkiraan.ACC_POSTED]);								  

								  String selectedPostable = ""+perkiraan.getPostable();																																																  
								  out.println(ControlCombo.draw(frmPerkiraan.fieldNames[frmPerkiraan.FRM_POSTABLE],null,selectedPostable,valPostable,keyPostable,""));								  
								%>                              </td>
                            </tr>
                        </table></td>
                      </tr>
                      <%if(iCommand == Command.ASK){%>
                      <tr>
                        <td colspan="6" height="27"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                            <tr>
                              <td width="97%" class="msgquestion"><div align="center"><%=delConfirm%></div></td>
                            </tr>
                        </table></td>
                      </tr>
                      <%}%>
                      <% if ((ctrlPerkiraan.getMessage()).length() > 0) { %>
                      <tr>
                        <td height="15" align="center" colspan="6" id=errMsg class="msgerror" bgcolor="#ebebeb"><%=ctrlPerkiraan.getMessage()%></td>
                      </tr>
                      <% } %>
                      <tr>
                        <td height="10" width="15%"></td>
                      </tr>
                      <tr>
                        <td colspan="6" class="command"><%if((privAdd)&&(privUpdate)&&(privDelete)){%>
                            <% if(iCommand!=Command.ASK){  %>
                            <a href="javascript:cmdSave()"><%=strSaveAcc%></a> |
                          <%   if((iCommand != Command.ADD) && !((iCommand == Command.SAVE) && (iAccountOID < 1)) && PstPerkiraan.isFreeAccount(iAccountOID) ){ %>
                            <a href="javascript:cmdAsk('<%=iAccountOID%>')"><%=strAskAcc%></a> |
                          <%   }
					     	 } else {
						  %>
                            <a href="javascript:cmdCancel()"><%=strCancel%></a>  <% if((ctrlErr==CtrlPerkiraan.RSLT_OK)){ %>| <a href="javascript:cmdDelete('<%=iAccountOID%>')"><%=strDeleteAcc%></a> <% } %>|
                          <% }
						  }
						  %>
                          <a href="javascript:cmdBackList()"><%=strBackAcc%></a> </a></td>
                      </tr>
                      <tr>
                        <td colspan="6" class="command">&nbsp;</td>
                      </tr>
                      <script language="javascript">
							document.frmaccountchart.<%=frmPerkiraan.fieldNames[frmPerkiraan.FRM_DEPARTMENT_ID]%>.focus();
					  </script>
                      <%}%>
                    </table></td>
                </tr>
              </table>
            </form>
                                                        
                                                        
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                               
                                                
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>
                                        
                                        </td>
                                    </tr>
                                </table><!---End Table--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
<%}%>