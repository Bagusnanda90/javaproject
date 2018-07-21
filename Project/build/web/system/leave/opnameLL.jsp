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
<%@ page import = "com.dimata.harisma.form.search.FrmSrcLLUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.FrmLLUpload" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcLLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.LLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.PstLLUpload" %>
<%@ page import = "com.dimata.harisma.session.leave.SessLLUpload" %>
<%@ page import = "com.dimata.harisma.entity.leave.I_Leave" %>
<%@ page import = "com.dimata.system.entity.system.PstSystemProperty" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_LL_OPNAME); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>
<%!
    public String drawList(Vector objEmpLLData, Date opnameDate, int start){
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No","5%","2","0");
        ctrlist.addHeader("Payroll","6%","2","0");
        ctrlist.addHeader("Name","24%","2","0");
        ctrlist.addHeader("Commencing Date","14%","2","0");
        ctrlist.addHeader("Entitle","7%","2","0");
        ctrlist.addHeader("Taken ","35%","0","5");
	for(int i=0;i<5;i++){
	  ctrlist.addHeader("<center>"+String.valueOf(i+1)+"</center>","6%","0","0");
	}
        ctrlist.addHeader("Status","7%","2","0");
        ctrlist.addHeader("Process/<br>Reprocess","7%","2","0");
        //ctrlist.addHeader("PIN","10%");
        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.reset();

        /*Hashtable position = new Hashtable();
        Vector listPosition = PstPosition.listAll();
        position.put("0", "-");
        for (int ls = 0; ls < listPosition.size(); ls++) {
            Position pos = (Position) listPosition.get(ls);
            position.put(String.valueOf(pos.getOID()), pos.getPosition());
        }
        */
        
        System.out.println("Membuat table dalam proses...");
        for (int i = 0; i < objEmpLLData.size(); i++){
            //System.out.println(i);
            Vector tempData = new Vector();
            tempData = (Vector)objEmpLLData.get(i);
            Employee objEmployee = new Employee();
            LLUpload objLLUpload = new LLUpload();
            //SrcLLUpload srcLLUpload = new SrcLLUpload();
            
            objEmployee = (Employee)tempData.get(0);
            objLLUpload = (LLUpload)tempData.get(1);
            //srcLLUpload = (SrcLLUpload)tempData.get(2);
            
            Vector rowx = new Vector();
            rowx.add(String.valueOf(i+1+start));
            
            //System.out.println(objEmployee.getFullName());
            //System.out.println(objLLUpload.getOID());
            rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_EMPLOYEE_ID]+"\" value=\""+objEmployee.getOID()+ "\">"
                    +"<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_UPLOAD_ID]+"\" value=\""+objLLUpload.getOID()+ "\">"
                    +objEmployee.getEmployeeNum());
            rowx.add("<input type=\"hidden\" name=\"data_emp_name\" value=\""+objEmployee.getFullName()+ "\">"+objEmployee.getFullName());
            
            if(objEmployee.getCommencingDate()!=null){
                //System.out.println("MASUK ADA");
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\""+objEmployee.getCommencingDate()+ "\">"+String.valueOf(objEmployee.getCommencingDate()));
                Date dateCurrPerStart = new Date();
                Date dateLastPerEnd = new Date();
                //==============================================INPUT DATA
                
                I_Leave leaveConfig = null;           
                try {
                    leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
                }
                catch(Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }
                
                //Entitle
                int iEntitled_Intv5 = SessLLUpload.getLLEntitled(objEmployee.getOID(), opnameDate, leaveConfig.getIntervalLL()[I_Leave.INTERVAL_LL_5_YEAR]);
                int iEntitled_Intv8 = SessLLUpload.getLLEntitled(objEmployee.getOID(), opnameDate, leaveConfig.getIntervalLL()[I_Leave.INTERVAL_LL_8_YEAR]);
                String strEntitle = "";
                if(iEntitled_Intv8>0){
                    strEntitle = String.valueOf(iEntitled_Intv5)+"+"+String.valueOf(iEntitled_Intv8);
                }else{
                    strEntitle = String.valueOf(iEntitled_Intv5);
                }
                rowx.add("<input type=\"hidden\" name=\"data_ll_entitled\" value=\"\">"+strEntitle);
                
                //Taken
                if(iEntitled_Intv5<=0){
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear1()>0?objLLUpload.getLlTakenYear1():0)+"\" disabled=\"true\">");
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR2]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear2()>0?objLLUpload.getLlTakenYear2():0)+"\" disabled=\"true\">");
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR3]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear3()>0?objLLUpload.getLlTakenYear3():0)+"\" disabled=\"true\">");
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR4]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear4()>0?objLLUpload.getLlTakenYear4():0)+"\" disabled=\"true\">");
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR5]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear5()>0?objLLUpload.getLlTakenYear5():0)+"\" disabled=\"true\">");
                }else{
                    Date commDateAtYear = SessLLUpload.getStartPeriodDate(objEmployee.getOID(), opnameDate, 5*12);//start date dari durasi 5 tahun
                    Date commDateAtYearInt8 = SessLLUpload.getStartPeriodDate(objEmployee.getOID(), opnameDate, 8*12);//start date dari durasi 8 tahun
                    rowx.add("<input type=\"text\" size=\"10\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]+"\"  class=\""+(commDateAtYearInt8.getYear()==commDateAtYear.getYear()?"styleInt8":"elemenForm")+"\" value=\""+(objLLUpload.getLlTakenYear1()>0?objLLUpload.getLlTakenYear1():0)+"\" >");
                    
                    //System.out.println("Opname Date :::: "+opnameDate+" Near CommDate ::::"+commDateAtYear);
                    commDateAtYear.setYear(commDateAtYear.getYear()+1);
                    //System.out.println("Data tanggal :::::: Commencing Date ::: "+commDateAtYear);
                    if(opnameDate.getTime()>=commDateAtYear.getTime()){
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR2]+"\"  class=\""+(commDateAtYearInt8.getYear()==commDateAtYear.getYear()?"styleInt8":"elemenForm")+"\" value=\""+(objLLUpload.getLlTakenYear2()>0?objLLUpload.getLlTakenYear2():0)+"\">");
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR2]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear2()>0?objLLUpload.getLlTakenYear2():0)+"\" disabled=\"true\">");
                    }
                    commDateAtYear.setYear(commDateAtYear.getYear()+1);
                    //System.out.println("Data tanggal :::::: Commencing Date ::: "+commDateAtYear);
                    if(opnameDate.getTime()>=commDateAtYear.getTime()){
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR3]+"\"  class=\""+(commDateAtYearInt8.getYear()==commDateAtYear.getYear()?"styleInt8":"elemenForm")+"\" value=\""+(objLLUpload.getLlTakenYear3()>0?objLLUpload.getLlTakenYear3():0)+"\">");
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR3]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear3()>0?objLLUpload.getLlTakenYear3():0)+"\" disabled=\"true\">");
                    }
                    commDateAtYear.setYear(commDateAtYear.getYear()+1);
                    if(opnameDate.getTime()>=commDateAtYear.getTime()){
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR4]+"\"  class=\""+(commDateAtYearInt8.getYear()==commDateAtYear.getYear()?"styleInt8":"elemenForm")+"\" value=\""+(objLLUpload.getLlTakenYear4()>0?objLLUpload.getLlTakenYear4():0)+"\">");
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR4]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear4()>0?objLLUpload.getLlTakenYear4():0)+"\" disabled=\"true\">");
                    }
                    commDateAtYear.setYear(commDateAtYear.getYear()+1);
                    if(opnameDate.getTime()>=commDateAtYear.getTime()){
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR5]+"\"  class=\""+(commDateAtYearInt8.getYear()==commDateAtYear.getYear()?"styleInt8":"elemenForm")+"\" value=\""+(objLLUpload.getLlTakenYear5()>0?objLLUpload.getLlTakenYear5():0)+"\">");
                    }else{
                        rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR5]+"\" value=\"0\"><input type=\"text\" size=\"10\" name=\"\"  class=\"elemenForm\" value=\""+(objLLUpload.getLlTakenYear5()>0?objLLUpload.getLlTakenYear5():0)+"\" disabled=\"true\">");
                    }
                }
                //==============================================/INPUT DATA
                if(objLLUpload.getDataStatus()==PstLLUpload.FLD_DOC_STATUS_PROCESS){
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_PROCESS+"\">"+PstLLUpload.fieldStatusNames[PstLLUpload.FLD_DOC_STATUS_PROCESS]);
                    //rowx.add(""+objAlUpload.getDataStatus());
                }else{
                    rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                }
                
                if(objEmployee.getCommencingDate().getTime()<=opnameDate.getTime() && iEntitled_Intv5>0){
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"1\" checked=\"true\"></center>");
                }else{
                    rowx.add("<center><input type=\"checkbox\" name=\"data_is_process"+i+"\" value=\"0\" disabled=\"true\"></center>");
                }
                
            }else{
                //System.out.println("MASUK TIDAK ADA");
                rowx.add("<input type=\"hidden\" name=\"data_emp_comm_date\" value=\"\">");
                //==============================================INPUT DATA
                rowx.add("<input type=\"hidden\" name=\"data_ll_entitled\">0");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]+"\" value=\"0\"><input type\"text\" size=\"10\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR2]+"\" value=\"0\"><input type\"text\" size=\"10\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR3]+"\" value=\"0\"><input type\"text\" size=\"10\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR4]+"\" value=\"0\"><input type\"text\" size=\"10\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR5]+"\" value=\"0\"><input type\"text\" size=\"10\" class=\"elemenForm\" value=\"0\" disabled=\"true\">");
                //==============================================/INPUT DATA
                rowx.add("<input type=\"hidden\" name=\""+FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]+"\" value=\""+PstLLUpload.FLD_DOC_STATUS_NOT_PROCESS+"\">");
                rowx.add("<input type=\"hidden\" name=\"data_is_process"+i+"\" value=\"0\"><center><input type=\"checkbox\" name=\"\" value=\"false\" disabled=\"true\"></center>");
            }
            lstData.add(rowx);            
        }

        ctrlist.setLinkSufix("')");
        return ctrlist.drawList();
    }
%>

<%
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_DEPT]);
    int iCommand = FRMQueryString.requestCommand(request);
    boolean status = false;
    SrcLLUpload srcLLUpload = new SrcLLUpload();
    FrmSrcLLUpload objFrmSrcLLUpload = new FrmSrcLLUpload(request, srcLLUpload);
    CtrlLLUpload ctrlLLUpload = new CtrlLLUpload(request);
    objFrmSrcLLUpload.requestEntityObject(srcLLUpload);
    ControlLine ctrLine = new ControlLine();
    
    // Proses jika command adalah SAVE	
    if (iCommand == Command.SAVE || iCommand == Command.ACTIVATE) {
	// Inisialisasi variable yang meng-handle nilai2 berikut
        
        String[] emp_id = null;
        String[] llUpload_id = null;
        String[] taken_year_1 = null;
        String[] taken_year_2 = null;
        String[] taken_year_3 = null;
        String[] taken_year_4 = null;
        String[] taken_year_5 = null;
        String[] data_status = null;
        boolean[] is_process = null;
        Date dateOpname;
        
        Vector vLLUpload = new Vector(1,1);
		// Mengambil array nilai2 berikut
        try {
            emp_id = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_EMPLOYEE_ID]);
            llUpload_id = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_UPLOAD_ID]);
            taken_year_1 = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR1]);
            taken_year_2 = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR2]);
            taken_year_3 = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR3]);
            taken_year_4 = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR4]);
            taken_year_5 = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_LL_TAKEN_YEAR5]);
            data_status = request.getParameterValues(FrmLLUpload.fieldNames[FrmLLUpload.FRM_FLD_DATA_STATUS]);
            dateOpname = (Date)srcLLUpload.getOpnameDate().clone();
                        
            is_process = new boolean[data_status.length];//request.getParameterValues("data_is_process");
            for(int i=0; i<emp_id.length; i++){				
                int ix = FRMQueryString.requestInt(request, "data_is_process"+i);
                if(ix==1){
                    is_process[i] = true;
                }else{
                    is_process[i] = false;
                }
            }
            
            vLLUpload.add(emp_id);      //0
            vLLUpload.add(llUpload_id); //1
            vLLUpload.add(taken_year_1);//2
            vLLUpload.add(taken_year_2);//3
            vLLUpload.add(taken_year_3);//4
            vLLUpload.add(taken_year_4);//5
            vLLUpload.add(taken_year_5);//6
            vLLUpload.add(data_status); //7
            vLLUpload.add(is_process);  //8
            vLLUpload.add(dateOpname);  //9
            
            
            
        }
        catch (Exception e) 
        {
            System.out.println("[ERROR] OpnameLL : "+e.toString());
        }
        
        if(iCommand == Command.SAVE){//jika disimpan saja
            System.out.println("Simpan Data............................................");
            Vector vLLUploadId = new Vector(1,1);
            vLLUploadId = com.dimata.harisma.session.leave.SessLLUpload.saveLLUpload(vLLUpload);
            if(vLLUploadId.size()>0){
                status = true;
            }else{
                status = false;
            }
            
            //System.out.println("STATUS DATA :::::::::::::::::::::::::::::::::::::::: "+status);
        }else if(iCommand == Command.ACTIVATE) {//Jika diproses
            System.out.println("Process data..........................................");
            Vector vLLUploadId = new Vector(1,1);
            vLLUploadId = com.dimata.harisma.session.leave.SessLLUpload.saveLLUpload(vLLUpload);
            status = com.dimata.harisma.session.leave.SessLLUpload.opnameLLAllData(vLLUploadId);
        }
    }

    int start = FRMQueryString.requestInt(request,"start");
    final int recordToGet = 20;
    int vectSize = 0;
	    
    Vector vDataLLToUpload = new Vector(1,1);
 //   if(srcLLUpload.getOpnameDate()!=null && (iCommand != Command.SAVE && iCommand != Command.ACTIVATE && iCommand != Command.ADD)){
 //       vDataLLToUpload = com.dimata.harisma.session.leave.SessLLUpload.searchLLData(srcLLUpload, 0, 0);
 //   }
    
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
    {
        Vector vTemp = new Vector(1,1);
        vTemp = com.dimata.harisma.session.leave.SessLLUpload.searchLLData(srcLLUpload, 0, 0);
        vectSize = vTemp.size();
        start = ctrlLLUpload.actionList(iCommand, start, vectSize, recordToGet);
        vDataLLToUpload = com.dimata.harisma.session.leave.SessLLUpload.searchLLData(srcLLUpload, start, recordToGet);
    }else{
        start = 0;
    }
%>


<script language="JavaScript">
        
function cmdUpdateDep(){
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.ADD)%>";
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp"; 
	document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdSave() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.SAVE)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "opnameLL.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdProccess() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.ACTIVATE)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "opnameLL.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function deptChange() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.GOTO)%>";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.hidden_goto_dept.value = document.frmbarcode.DEPARTMENT_ID.value;
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "opnameLL.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdSearch() {
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value = "<%=String.valueOf(Command.LIST)%>";
    getThn();										
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action = "opnameLL.jsp";
    document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
}

function cmdViewList(){
    window.open("ll_opname_list.jsp", null, "height=500,width=400, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
}


function cmdCek(index){
    var val1;
    var val2;
    switch(index){
    <%
        for(int k=0;k<vDataLLToUpload.size();k++){
    %>
            case <%=""+k%>:
            val1 = parseInt(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="data_emp_to_clear_last_per"+k%>.value);
            val2 = parseInt(document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%="data_emp_taken_curr_per"+k%>.value);
            if(val1>0 && val2>0){
                alert("Data not valid...");
            }
            break;
        
    <%}%>
    }
    
    
}




//---------------------------------------------------
    function getThn(){
            var date1 = ""+document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_mn.value=bln;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_dy.value=hri;
            document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_yr.value=thn;
    }

    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 

    function setChecked(val) {
	dml=document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;
	}
    }
//-------------- script control line -------------------
        function cmdListFirst(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListPrev(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.PREV)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListNext(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
	}

	function cmdListLast(){
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.command.value="<%=String.valueOf(Command.LAST)%>";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.action="opnameLL.jsp";
		document.<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>.submit();
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
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<style type="text/css">
<!--
.styleInt8 {
	background-color: #fff7b8;
}
-->
</style>

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Long Leave Management</title>




<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!--<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css"> -->



</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
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
                  &gt; Employee &gt; Leave Opname Aplication &gt; Long Leave<!-- #EndEditable --> 
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
                                    <form name="<%=FrmSrcLLUpload.FRM_LL_UPLOAD%>" method="post" action="">
                                    <%if(iCommand == Command.SAVE || iCommand == Command.ACTIVATE){ %>
                                        <input type="hidden" name="command" value="<%=String.valueOf(Command.LIST)%>">
                                    <%}else{%>
                                        <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <%}%>
				    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3"><b><u><font color="#FF0000">ATTENTION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>OPNAME</b> 
                                                  long leave to database<br>                                               
                                                  <hr>
                                                </td>
                                              </tr>                                              

                                              <tr> 
                                                <td width="11%">Name</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_NAME]%>"  value="<%=String.valueOf(srcLLUpload.getEmployeeName())%>" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="11%">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="88%"> 
                                                  <input type="text" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_PAYROLL]%>"  value="<%=String.valueOf(srcLLUpload.getEmployeePayroll())%>" class="elemenForm">
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
							Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
							for (int i = 0; i < listCat.size(); i++) {
								EmpCategory cat = (EmpCategory) listCat.get(i);
								cat_key.add(cat.getEmpCategory());
								cat_value.add(String.valueOf(cat.getOID()));
							}
						%>
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_CAT],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeCategory()), cat_value, cat_key, "") %> </td>
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
                                                  <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_DEPT],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeDepartement()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_SEC],"formElemen",null,String.valueOf(srcLLUpload.getEmployeeSection()), sec_value, sec_key, "") %> </td>
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
                                                <%= ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_EMP_POS],"formElemen",null,String.valueOf(srcLLUpload.getEmployeePosition()), pos_value, pos_key, "") %> </td>
                                              </tr>
					      <tr> 
                                          	<td width="13%">Opname Date</td>
                                          	<td width="2%">:</td>
                                          	<td width="85%">
                                                    <input onClick="ds_sh(this);" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>" readonly="readonly" style="cursor: text" value="<%=Formater.formatDate((srcLLUpload.getOpnameDate() == null? new Date() : srcLLUpload.getOpnameDate()), "yyyy-MM-dd")%>"/>
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_mn">
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_dy">
                                                    <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE]%>_yr">
                                                    
                                                    <a href="javascript:cmdViewList()" class="buttonlink"><img src="../../images/icon/folderopen.gif" border="0" alt="null"></a>
                                                    <script language="JavaScript" type="text/JavaScript">getThn();</script>
                                                     <% //ControlDate.drawDate(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_OPNAME_DATE],srcLLUpload.getOpnameDate(),+5,-10) %>
                                                 
                                                </td>
                                              </tr>
                                              <input type="hidden" name="<%=FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_DATA_STATUS]%>" value="-1">
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
                                                    ControlCombo.draw(FrmSrcLLUpload.fieldNames[FrmSrcLLUpload.FRM_FIELD_DATA_STATUS],"formElemen",null,String.valueOf(srcLLUpload.getDataStatus()), vStatus_Value, vStatus_Key, "")  </td>
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
                                          <% //
                                          if (vDataLLToUpload.size() > 0 && ((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { %>
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
                                                    <%=drawList(vDataLLToUpload, srcLLUpload.getOpnameDate(),start)%>
                                                </td>
                                              </tr>
					      <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
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

