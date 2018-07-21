
<%@page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.form.search.FrmSrcDPUpload" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcDPUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.DPUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstDPUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessDPUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_DP_OPNAME); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
    public String drawList(Vector objEmpDPData, Date opnameDate,int start){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Payroll","6%");
        ctrlist.addHeader("Name","24%");
        ctrlist.addHeader("Commencing Date","14%");
        ctrlist.addHeader("Acquisition Date","14%");
        ctrlist.addHeader("DP","10%");
        ctrlist.addHeader("New Entry","11%");
        ctrlist.addHeader("Status","9%");
        ctrlist.addHeader("Process/<br>Reprocess","7%");
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();
        System.out.println("Membuat table dalam proses...");
        for (int i = 0; i < objEmpDPData.size(); i++){
            //System.out.println(i);
            Vector tempData = new Vector();
            tempData = (Vector)objEmpDPData.get(i);
            Employee objEmployee = new Employee();
            DPUpload objDPUpload = new DPUpload();
            int selected = 1;
            //SrcAlUpload srcDPUpload = new SrcAlUpload();
            
            objEmployee = (Employee)tempData.get(0);
            objDPUpload = (DPUpload)tempData.get(1);
            if(tempData.size()>2){
                String strSelected = (String)tempData.get(2);
                selected = Integer.parseInt(strSelected);
            }
            //srcDPUpload = (SrcAlUpload)tempData.get(2);
           
            Vector rowx = new Vector();
            rowx.add(String.valueOf(i+1+start));
            
            //System.out.println(objEmployee.getFullName());
            //System.out.println(objDPUpload.getOID());
            rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objEmployee.getOID()+ "\">"
                    +"<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]+"\" value=\""+objDPUpload.getOID()+ "\">"
                    +objEmployee.getEmployeeNum());
            rowx.add("<input type=\"hidden\" name=\"emp_name\" value=\""+objEmployee.getFullName()+"\">"+objEmployee.getFullName());
            
            if(objEmployee.getCommencingDate()!=null){
                //System.out.println("MASUK ADA");
                rowx.add("<input type=\"hidden\" name=\"emp_com_date\" value=\""+objEmployee.getCommencingDate()+ "\">"+String.valueOf(objEmployee.getCommencingDate()));
                //////////////////////////////////////////////////////////////////////
                
                rowx.add("<input onClick=\"ds_sh(this);\" size=\"\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objDPUpload.getAcquisitionDate() == null? opnameDate : objDPUpload.getAcquisitionDate()), "yyyy-MM-dd")+"\"/>");
                rowx.add("<input type=\"text\" size=\"14%\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\""+objDPUpload.getDPNumber()+"\">");
                rowx.add("<center><input type=\"button\" name=\"btn\" onclick=\"cmdAddCol("+i+")\" value=\"add\"></center>");
                
                /////////////////////////////////////////////////////////////////////
                
                if(objDPUpload.getDataStatus()==PstDPUpload.FLD_DOC_STATUS_PROCESS){
                    rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_PROCESS+"\">"+PstDPUpload.fieldStatusNames[PstDPUpload.FLD_DOC_STATUS_PROCESS]);
                    //rowx.add(""+objDPUpload.getDataStatus());
                }else{
                    rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                }
                
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime()){
                    if(selected==1){
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\" checked=\"true\" ></center>");
                    }else{
                        rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\" ></center>");
                    }
                }else{
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"0\" disabled=\"true\" ></center>");
                } 
            }else{
                //System.out.println("MASUK TIDAK ADA");
               
                rowx.add("<input type=\"hidden\" name=\"emp_com_date\" value=\"\">");
                //////////////////////////////////////////////////////////////////////
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]+"\" value=\"0000-00-00\"><input type=\"text\" size=\"\" name=\"\"  class=\"elemenForm\" value=\"\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]+"\" value=\"0\"><input type=\"text\"  size=\"14%\" name=\"\"  class=\"elemenForm\" value=\"\" disabled=\"true\">");
                rowx.add("<center><input type=\"button\" name=\"btn\" onclick=\"\" value=\"add\" disabled=\"true\"></center>");
                //////////////////////////////////////////////////////////////////////
                rowx.add("<input type=\"hidden\" name=\""+FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstDPUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
            }
    
            lstData.add(rowx);         
        }
        ctrlist.setLinkSufix("')");
        return ctrlist.draw();
    }
%>

<%
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_DEPT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    SrcDPUpload srcDPUpload = new SrcDPUpload();
    FrmSrcDPUpload objFrmSrcDPUpload = new FrmSrcDPUpload(request, srcDPUpload);
    objFrmSrcDPUpload.requestEntityObject(srcDPUpload);
    CtrlDPUpload ctrlDPUpload = new CtrlDPUpload(request);
    
    //System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::"+srcDPUpload.getOpnameDate());
    
    ControlLine ctrLine = new ControlLine();
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	//System.out.println("MASUK KESINI........");
	                                                                                                                                                                                                                                                                                                                    // Inisialisasi variable yang meng-handle nilai2 berikut
        String[] emp_id = null;		
        String[] dpUpload_id = null;
        String[] dp_aq_date = null;
        String[] dp_number = null;				
        String[] data_status = null;				
        boolean[] is_process = null;				
        Vector vDPUpload = new Vector();
		// Mengambil array nilai2 berikut
        try {
            emp_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]);
            dpUpload_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]);
            dp_aq_date = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]);
            dp_number = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]);
            data_status = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]);
            is_process = new boolean[data_status.length];//request.getParameterValues("data_is_process");
            for(int i=0; i<emp_id.length; i++){				
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    is_process[i] = true;
                }else{
                    is_process[i] = false;
                }
            }
            
            vDPUpload.add(emp_id);//0
            vDPUpload.add(dpUpload_id);//1
            vDPUpload.add(dp_aq_date);//2
            vDPUpload.add(dp_number);//3
            vDPUpload.add(data_status);//4
            vDPUpload.add(is_process);//5
            vDPUpload.add(srcDPUpload.getOpnameDate());//6
        }
        catch (Exception e) 
        {
            System.out.println("[ERROR] opnameDP : "+e.toString());
        }
        
        if(iCommand == Command.SAVE){//jika disimpan saja
            System.out.println("Simpan Data............................................");
            Vector vDPUploadId = new Vector(1,1);
            vDPUploadId = com.dimata.harisma.session.leave.SessDPUpload.saveDPUpload(vDPUpload);
            if(vDPUploadId.size()>0){
                status = true;
            }else{
                status = false;
            }
            
            //System.out.println("STATUS DATA :::::::::::::::::::::::::::::::::::::::: "+status);
        }else if(iCommand == Command.ACTIVATE) {//Jika diproses
            System.out.println("Process data..........................................");
            Vector vAlUploadId = new Vector(1,1);
            vAlUploadId = com.dimata.harisma.session.leave.SessDPUpload.saveDPUpload(vDPUpload);
          //  try{
            status = com.dimata.harisma.session.leave.SessDPUpload.opnameAllDp(vAlUploadId);
           // }catch(Exception ex){}
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 20;
    int vectSize = 0;
    
    Vector vDataDpToUpload = new Vector(1,1);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        Vector vTemp = new Vector(1,1);
        vTemp = com.dimata.harisma.session.leave.SessDPUpload.searchDpData(srcDPUpload, 0, 0);
        vectSize = vTemp.size();
        start = ctrlDPUpload.actionList(iCommand, start, vectSize, recordToGet);
        vDataDpToUpload = com.dimata.harisma.session.leave.SessDPUpload.searchDpData(srcDPUpload, start, recordToGet);
    }else{
        if(iCommand==Command.ADD){
            int addIndex = FRMQueryString.requestInt(request,"addIndex");
            String[] emp_id = null;		
            String[] dpUpload_id = null;
            String[] dp_aq_date = null;
            String[] dp_number = null;				
            String[] data_status = null;				
            int[] is_process = null;				
            Vector vDPUpload = new Vector();
            // Mengambil array nilai2 berikut
            try {
                emp_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_EMPLOYEE_ID]);
                dpUpload_id = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_UPLOAD_ID]);
                dp_aq_date = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_ACQUISITION_DATE]);
                dp_number = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DP_ACQUIRED]);
                data_status = request.getParameterValues(FrmDPUpload.fieldNames[FrmDPUpload.FRM_FLD_DATA_STATUS]);
                
                is_process = new int[data_status.length];//request.getParameterValues("data_is_process");
                for(int i=0; i<emp_id.length; i++){				
                    is_process[i] = FRMQueryString.requestInt(request, "data_is_process"+i);
                }
                
              //  System.out.println(emp_id.length);
              //  System.out.println(dpUpload_id.length);
              //  System.out.println(emp_name.length);
              //  System.out.println(emp_comm_date.length);
              //  System.out.println(dp_aq_date.length);
              //  System.out.println(dp_number.length);
              //  System.out.println(data_status.length);
                
                //Looping to create employee and DPUpload object
                for(int i=0;i<emp_id.length;i++){
                    Employee objEmployee = new Employee();
                    DPUpload objDPUpload = new DPUpload();
                    objEmployee = PstEmployee.fetchExc(Long.parseLong(emp_id[i]));
                    
                    if(objEmployee.getCommencingDate()!=null){
                        objDPUpload.setOID(Long.parseLong(dpUpload_id[i]));
                        objDPUpload.setAcquisitionDate(SessDPUpload.parseStringToDate(dp_aq_date[i],SessDPUpload.DATE_FORMAT_OTHER));
                        objDPUpload.setDPNumber(Integer.parseInt(dp_number[i]));
                        objDPUpload.setDataStatus(Integer.parseInt(data_status[i]));
                    }
                    Vector vTemp = new Vector(1,1);
                    vTemp.add(objEmployee);
                    vTemp.add(objDPUpload);
                    vTemp.add(String.valueOf(is_process[i]));
                    vDataDpToUpload.add(vTemp);
                    if(i==addIndex){
                        Employee objEmployee2 = new Employee();
                        objEmployee2 = PstEmployee.fetchExc(Long.parseLong(emp_id[i]));
                        Vector vTemp2 = new Vector(1,1);
                        String name = "<font color=ff0000 >"+objEmployee.getFullName()+"</font>";
                        objEmployee2.setFullName(name);
                        vTemp2.add(objEmployee2);
                        vTemp2.add(new DPUpload());
                        vDataDpToUpload.add(vTemp2);
                    }
                }
             //   srcDPUpload.setOpnameDate(FRMQueryString.requestDate(request,FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]+"_temp"));
             //   iCommand=Command.LIST;
            }catch(Exception ex){
                System.out.println("[ERROR] system.opnameDP.jsp :::::::::: "+ex.toString());
            }
        }else{ 
            start = 0;
        }
    }
%>

<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Down Payment Management</title>
<script language="JavaScript">
    
function cmdUpdateDep(){
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.OK)%>";
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="opnameDP.jsp"; 
	document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdSave() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "opnameDP.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "opnameDP.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "opnameDP.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "opnameDP.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

function cmdViewList(){
    window.open("dp_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}

function cmdAddCol(index){
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value = "<%=String.valueOf(Command.ADD)%>";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.addIndex.value = index;
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action = "opnameDP.jsp";
    document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
}

//---------------------------------------------------
    function getThn(){
            var date1 = ""+document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }


    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 
    
    function setChecked(val) {
	dml=document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;
	}
    }
    
    function checkEnableAll(){
        if(document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.isSelect.value == 1){
            setChecked(1);
        }else{
            setChecked(0);
        }
    }
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="opnameDP.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="opnameDP.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="opnameDP.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.action="opnameDP.jsp";
		document.<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>.submit();
	}


        function MM_swapImgRestore() 
        { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
        }

        function MM_preloadImages() 
        { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
        }

        function MM_findObj(n, d) 
        { //v4.0
                var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                if(!x && document.getElementById) x=document.getElementById(n); return x;
        }

        function MM_swapImage() 
        { //v3.0
                var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
        }
</script>


<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
    
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display:none">
    <tr><td id="ds_calclass">
    </td></tr>
</table>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

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
                  &gt; Employee &gt; Leave Opname Aplication &gt; Down Payment<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                <% if (privAdd) { %>
                                    <form name="<%=FrmSrcDPUpload.FRM_DP_UPLOAD%>" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <input type="hidden" name="addIndex">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>OPNAME</b> 
                                                  down payment (DP) to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcDPUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcDPUpload.getEmployeePayroll())%>" class="elemenForm">
                                                </td>
                                              </tr>
                                             
                                              <tr> 
                                                <td width="11%">Category</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
							Vector cat_value = new Vector(1,1);
							Vector cat_key = new Vector(1,1);        
							cat_value.add("0");
							cat_key.add("all category ...");                                                          
							Vector listCat = PstEmpCategory.list(0, 0, "", "EMP_CATEGORY");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                        dept_key.add("all department...");
                                                        dept_value.add("0");
                                                        //String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                  <%//= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %>
                                                  <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                <% 
							Vector sec_value = new Vector(1,1);
							Vector sec_key = new Vector(1,1); 
							sec_value.add("0");
							sec_key.add("all section ...");
                                                        String strWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
							Vector listSec = PstSection.list(0, 0, strWhere, " DEPARTMENT_ID, SECTION ");
							for (int i = 0; i < listSec.size(); i++) {
								Section sec = (Section) listSec.get(i);
								sec_key.add(sec.getSection());
								sec_value.add(String.valueOf(sec.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcDPUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Position</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                              <% 
							Vector pos_value = new Vector(1,1);
							Vector pos_key = new Vector(1,1); 
							pos_value.add("0");
							pos_key.add("all position ...");                                                       
							Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                        for (int i = 0; i < listPos.size(); i++) {
								Position pos = (Position) listPos.get(i);
								pos_key.add(pos.getPosition());
								pos_value.add(String.valueOf(pos.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcDPUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcDPUpload.getOpnameDate() == null? new Date() : srcDPUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                     <% //ControlDate.drawDate(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_OPNAME_DATE],srcDPUpload.getOpnameDate(),+5,-10) %>
                                                 
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">
                                              <%/*
                                              <tr> 
                                                <td width="11%">Status</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <% 
                                                        Vector vStatus_Value = new Vector(1,1);
                                                        Vector vStatus_Key = new Vector(1,1);
                                                        vStatus_Key.add("all status...");
                                                        vStatus_Key.add("Process");
                                                        vStatus_Key.add("Not Process");
                                                        vStatus_Value.add("-1");
                                                        vStatus_Value.add("0");
                                                        vStatus_Value.add("1");
                                                    ControlCombo.draw(FrmSrcDPUpload.fieldNames[FrmSrcDPUpload.FRM_FIELD_DATA_STATUS],"formElemen",null,String.valueOf(srcDPUpload.getDataStatus()), vStatus_Value, vStatus_Key, "")  </td>
                                              </tr>
                                              */%>
                                              <tr> 
                                                <td width="11%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="88%"> 
                                                  <input type="submit" name="Submit" value="Search Employee" onClick="javascript:cmdSearch()">
                                                </td>
                                              </tr>
                                              
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
                                            <% if(iCommand == Command.SAVE && status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="fffff9">
                                                <tr>
                                                    <td>
                                                        <center>SAVE DATA SUCCESS</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.SAVE && !status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="red">
                                                <tr>
                                                    <td>
                                                        <center>SAVE DATA FAILED</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.ACTIVATE && status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="fffff9">
                                                <tr>
                                                    <td>
                                                        <center>PROCESS DATA SUCCESS</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                            <% if(iCommand == Command.ACTIVATE && !status){%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="red">
                                                <tr>
                                                    <td>
                                                        <center>PROCESS DATA FAILED</center>
                                                    </td>
                                                </tr>
                                                </table>
                                            <%}%>
                                          <% if (vDataDpToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST)||(iCommand==Command.ADD))) { %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td align="right" valign="top">
                                                    [<a onclick="javascript:setChecked(1)"><b>Check All</b></a>]
                                                    |[<a onclick="javascript:setChecked(0)"><b>Uncheck All</b></a>]
                                                    <img src="<%=imagesroot%>/arrow_ltr.png">
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                    <%=drawList(vDataDpToUpload, srcDPUpload.getOpnameDate(), start)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %>
                                                <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      
                                                      <td width="50" class="command" nowrap>
                                                          <br />
                                                          <a href="javascript:cmdSave()">Save</a>
                                                      ||<a href="javascript:cmdProccess()">Proccess</a></td>
                                                    </tr> 
                                                  </table>                      
                                                </td>
                                              </tr>
                                            </table>
                                          <% } %>
                                          </td>
                                          </tr>
                                        </table>
                                    </form>
                                <% } 
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% } %>
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
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

