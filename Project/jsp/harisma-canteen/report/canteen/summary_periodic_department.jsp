
<%-- 
    Document   : summary_periodic_department
    Created on : Nov 29, 2010, 8:54:04 AM
    Author     : Roy Andika
--%>

<%@ page language="java" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ include file = "../../../main/javainit.jsp" %>


<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CANTEEN, AppObjInfo.G2_REPORT_SUMMARY, AppObjInfo.OBJ_SUMMARY_MONTHLY); %>
<%@ include file = "../../../main/checkuser.jsp" %>

<%
    int countSchedule = 0;

    try{
        countSchedule = PstCanteenSchedule.getCount(null);
    }catch(Exception E){
        System.out.println("excption "+E.toString());
    }

    Vector lstCantSch = new Vector();
    String orderCnt = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]+" ASC ";

    try{
        lstCantSch = PstCanteenSchedule.list(0, 0, "", orderCnt);
    }catch(Exception E){
        System.out.println("excption "+E.toString());
    }

    long oidSchCanteen = FRMQueryString.requestLong(request, "SCH");
    int iCommand = FRMQueryString.requestCommand(request);
    Date date = FRMQueryString.requestDate(request, "cmbDate");
    Date dateto = FRMQueryString.requestDate(request, "cmbDateto");
    double valNominal = FRMQueryString.requestDouble(request, "vl_nominal");
    long oidDepartment = FRMQueryString.requestLong(request, "DEPARTMENT_ID");
    long oidSection = FRMQueryString.requestLong(request, "SECTION_ID");
    String departmentName = FRMQueryString.requestString(request, "hidden_dept_name");
    String secName = FRMQueryString.requestString(request, "hidden_sec_name");
    int rpType = FRMQueryString.requestInt(request, "MORNING_AFTERNOON_NIGHT_ID");

    String[] scheduleId = null;

    scheduleId = new String[countSchedule+1];
    double val[] = new double[countSchedule+1];

    int max1 = 0;
    for(int j = 0 ; j < countSchedule ; j++){

        CanteenSchedule canteenSch = new CanteenSchedule();
        canteenSch = (CanteenSchedule)lstCantSch.get(j);
        String name = "SCH_"+canteenSch.getOID();
        String inpTxt = "TXT_"+canteenSch.getOID();
        scheduleId[j] = FRMQueryString.requestString(request,name);
        val[j] = FRMQueryString.requestDouble(request,inpTxt);
        max1++;
    }

    scheduleId[max1] = FRMQueryString.requestString(request,"SCH_"+0);
    val[max1] = FRMQueryString.requestDouble(request,"TXT_"+0);

%>


<%!

        int DATA_NULL = 0;
        int DATA_PRINT = 1;
        
	public String drawListCanteenVisitation(Date dateStrt, Date dateEnd, Vector dataOfReport,Vector dataOfVisitsAccess,String schIdx[],double nominal[])
        {

            String frmCurrency = "#,###";
            ControlList ctrlist = new ControlList();

            ctrlist.setAreaWidth("80%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            
            ctrlist.addHeader("DIVISION","20%","2","0");
            ctrlist.addHeader("DEPARTMENT","20%","2","0");
            ctrlist.addHeader("SCHEDULE","10%","2","0");
            ctrlist.addHeader("NOMINAL","10%","2","0");

            Vector canteenSchedule = new Vector();
            String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]+" ASC";
            canteenSchedule = PstCanteenSchedule.list(0, 0, "", order);
            int dateDiff = PstPresence.DATEDIFF(dateEnd, dateStrt);
            int countDtMax = 0;

            Date dtProces = dateStrt;
            int lengthDate = dateDiff + 1;

            /* looping sebanyak tanggal yang diinginkan */
            ctrlist.addHeader("<div align=\"center\">Date</div>","20%","0",""+lengthDate);
            
            while(countDtMax <= dateDiff){

                int DtCurent = dtProces.getDate();

                ctrlist.addHeader("<div align=\"center\">" + String.valueOf(DtCurent) + "</div>","5%","0","0");
                System.out.println("date "+DtCurent);

                Date nextProcess = (Date)dtProces.clone();
                nextProcess.setDate(nextProcess.getDate()+1);
                
                dtProces = nextProcess;                    
                countDtMax++;

            }
            
            ctrlist.addHeader("<div align=\"center\">COVER</div>","10%","2","0");
            ctrlist.addHeader("<div align=\"center\">AMOUNT</div>","10%","2","0");            

            ctrlist.setLinkRow(-1);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            Vector lstLinkData = ctrlist.getLinkData();
            ctrlist.setLinkPrefix("javascript:cmdEdit('");
            ctrlist.reset();

            Vector listDepat = new Vector();

            String orderDpt = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];

            try{
                listDepat = PstDepartment.list(0,0,"",orderDpt);
            }catch(Exception E){
                System.out.println("[exception] " +E.toString());
            }

            int countMxData = 0;
            long divId = 0;
            long depId = 0;

            int sumTotal[] = new int[lengthDate];
            double sumTotalAll = 0;
            /* looping sebanyak department */
            for(int iDept = 0 ; iDept < listDepat.size() ; iDept++){

                boolean dept = false;
                Department department = new Department();
                department = (Department)listDepat.get(iDept);

                int sumDt[] = new int[lengthDate];
                double sumAmount = 0;

                int max = 0;
                /* looping sebanyak canteen schedule */                
                for(int idxCnt = 0; idxCnt < canteenSchedule.size() ; idxCnt++){

                    if (schIdx[idxCnt].compareTo("1") == 0){

                        dept = true;
                        Vector rowx = new Vector();

                        if(divId == 0 || divId != department.getDivisionId()){

                            Division division = new Division();

                            try{

                                division = (Division)PstDivision.fetchExc(department.getDivisionId());
                                rowx.add(""+division.getDivision());
                            }catch(Exception E){
                                System.out.println("[exception] "+E.toString());
                                rowx.add("");
                            }

                        }else{

                            rowx.add("");

                        }

                        if(depId == 0 || depId != department.getOID()){
                            rowx.add(""+department.getDepartment());
                        }else{
                            rowx.add("");
                        }

                        double nominl = nominal[idxCnt];

                        int sumVisitation = 0;
                    
                        CanteenSchedule cntSch = (CanteenSchedule)canteenSchedule.get(idxCnt);

                        rowx.add(""+cntSch.getSName());
                        rowx.add(""+nominl);

                        Vector visitation = (Vector)dataOfReport.get(idxCnt);

                        Date ProcsDt = dateStrt;

                        countMxData = 0;

                        while(countMxData <= dateDiff){

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;
                            boolean DepFound = false;
                            int iDt = ProcsDt.getDate(); 

                            for(int idSum = 0 ; idSum < visitation.size() ; idSum++){

                                sumReportDepartment = (SumReportDepartment)visitation.get(idSum);

                                if(department.getOID()==sumReportDepartment.getDepartmentId()){
                                    DepFound = true;
                                    if(iDt == sumReportDepartment.getCanteenVisitation().getDate()){
                                        visitation.remove(idSum);
                                        found = true;
                                        break;
                                    }
                                }else{

                                    if(DepFound){
                                        break;
                                    }
                                }

                            }

                            if(found){
                                rowx.add(""+Formater.formatNumber(sumReportDepartment.getSummary(),frmCurrency));
                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary(); 
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                            }else{
                                rowx.add("0");
                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }
                            
                            countMxData++;
                            Date nextProcess = (Date)ProcsDt.clone();nextProcess.setDate(nextProcess.getDate()+1);
                            ProcsDt = nextProcess;
                        }

                        rowx.add(""+sumVisitation);
                        double sumTot = Double.parseDouble(""+sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;
                        rowx.add(""+Formater.formatNumber(sumTot,frmCurrency));
                     
                        lstData.add(rowx);
                     }

                    if(dept){
                        divId = department.getDivisionId();
                        depId = department.getOID();
                    }
                    max++;

                   }




                   //Kondisi untuk yang di luar schedule
                
                   if (schIdx[max].compareTo("1") == 0) {

                        Vector rowx = new Vector();

                        if(divId == 0 || divId != department.getDivisionId()){

                            Division division = new Division();

                            try{

                                division = (Division)PstDivision.fetchExc(department.getDivisionId());
                                rowx.add(""+division.getDivision());
                            }catch(Exception E){
                                System.out.println("[exception] "+E.toString());
                                rowx.add("");
                            }

                        }else{

                            rowx.add("");

                        }

                        if(depId == 0 || depId != department.getOID()){
                            rowx.add(""+department.getDepartment());
                        }else{
                            rowx.add("");
                        }

                        double nominl = nominal[max];

                        int sumVisitation = 0;

                        rowx.add("SPLIT");
                        rowx.add(""+nominl);

                        Vector visitation = (Vector)dataOfReport.get(max);

                        Date ProcsDt = dateStrt;

                        countMxData = 0;

                        while(countMxData <= dateDiff){

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;
                            boolean DepFound = false;
                            int iDt = ProcsDt.getDate();

                            for(int idSum = 0 ; idSum < visitation.size() ; idSum++){

                                sumReportDepartment = (SumReportDepartment)visitation.get(idSum);

                                if(department.getOID()==sumReportDepartment.getDepartmentId()){
                                    DepFound = true;
                                    if(iDt == sumReportDepartment.getCanteenVisitation().getDate()){
                                        visitation.remove(idSum);
                                        found = true;
                                        break;
                                    }
                                }else{

                                    if(DepFound){
                                        break;
                                    }
                                }
                            }

                            if(found){
                                rowx.add(""+Formater.formatNumber(sumReportDepartment.getSummary(),frmCurrency));
                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                            }else{
                                rowx.add("0");
                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }

                            countMxData++;
                            Date nextProcess = (Date)ProcsDt.clone();nextProcess.setDate(nextProcess.getDate()+1);
                            ProcsDt = nextProcess;
                        }

                        rowx.add(""+sumVisitation);
                        double sumTot = Double.parseDouble(""+sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;
                        rowx.add(""+Formater.formatNumber(sumTot,frmCurrency));

                        lstData.add(rowx);
                   }


                    Vector rowx = new Vector();
                    rowx.add("");
                    rowx.add("<B>TOTAL</B>");
                    rowx.add("");
                    rowx.add("");
                    int tot = 0;
                    for(int iDt = 0 ; iDt < lengthDate ; iDt++){

                        rowx.add("<B>"+sumDt[iDt]+"</B>");
                        tot = tot + sumDt[iDt];

                    }

                    rowx.add("<B>"+Formater.formatNumber(tot,frmCurrency)+"</B>");
                    rowx.add("<B>"+Formater.formatNumber(sumAmount,frmCurrency)+"</B>");
                    lstData.add(rowx);

                    rowx = new Vector();
                    rowx.add("&nbsp;");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");

                    for(int iDtx = 0 ; iDtx < lengthDate ; iDtx++){
                        rowx.add("");
                    }

                    rowx.add("");
                    rowx.add("");
                    lstData.add(rowx);
                    divId = department.getDivisionId();
                    depId = department.getOID();
                    
                }

                int idxDiv = 0;
                int idxDep = 0;

                if(canteenSchedule != null && canteenSchedule.size() > 0){

                  int sumDt[] = new int[lengthDate];
                  double sumAmount = 0;

                  int maxAccess = 0;
                  for(int idxCntX = 0; idxCntX < canteenSchedule.size() ; idxCntX++){                   

                    if (schIdx[idxCntX].compareTo("1") == 0){

                        Vector rowx = new Vector();

                        if(idxDiv == 0){
                            rowx.add("Unassigned");
                        }else{
                            rowx.add("");
                        }

                        if(idxDep==0){
                            rowx.add("Unassigned");
                        }else{
                            rowx.add("");
                        }
                        
                        double nominl = nominal[idxCntX];

                        int sumVisitation = 0;

                        CanteenSchedule cntSch = (CanteenSchedule)canteenSchedule.get(idxCntX);

                        rowx.add(""+cntSch.getSName());
                        rowx.add(""+nominl);

                        Vector visitation = (Vector)dataOfVisitsAccess.get(idxCntX);

                        Date ProcsDt = dateStrt;

                        countMxData = 0;

                        while(countMxData <= dateDiff){

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;                            
                            int iDt = ProcsDt.getDate();

                            for(int idSum = 0 ; idSum < visitation.size() ; idSum++){

                                sumReportDepartment = (SumReportDepartment)visitation.get(idSum);
                               
                                if(iDt == sumReportDepartment.getCanteenVisitation().getDate()){
                                    visitation.remove(idSum);
                                    found = true;
                                    break;
                                }
                            }

                            if(found){
                                rowx.add(""+Formater.formatNumber(sumReportDepartment.getSummary(),frmCurrency));
                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                            }else{
                                rowx.add("0");
                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }

                            countMxData++;
                            Date nextProcess = (Date)ProcsDt.clone();nextProcess.setDate(nextProcess.getDate()+1);
                            ProcsDt = nextProcess;
                        }

                        rowx.add(""+sumVisitation);
                        double sumTot = Double.parseDouble(""+sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;
                        rowx.add(""+Formater.formatNumber(sumTot,frmCurrency));

                        lstData.add(rowx);

                        idxDiv++;
                        idxDep++;

                     }
                     
                     maxAccess++;
                  }

                  
                  //Kondisi untuk yang di luar schedule
                   if (schIdx[maxAccess].compareTo("1") == 0) {

                        Vector rowx = new Vector();

                        if(idxDiv == 0){
                            rowx.add("Unasigned");
                        }else{
                            rowx.add("");
                        }

                        if(idxDep==0){
                            rowx.add("Unasigned");
                        }else{
                            rowx.add("");
                        }
                        
                        double nominl = nominal[maxAccess];

                        int sumVisitation = 0;

                        rowx.add("SPLIT");
                        rowx.add(""+nominl);

                        Vector visitation = (Vector)dataOfVisitsAccess.get(maxAccess);

                        Date ProcsDt = dateStrt;

                        countMxData = 0;

                        while(countMxData <= dateDiff){

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;                           
                            int iDt = ProcsDt.getDate();

                            for(int idSum = 0 ; idSum < visitation.size() ; idSum++){

                                sumReportDepartment = (SumReportDepartment)visitation.get(idSum);

                                if(iDt == sumReportDepartment.getCanteenVisitation().getDate()){
                                    visitation.remove(idSum);
                                    found = true;
                                    break;
                                }
                            }

                            if(found){
                                rowx.add(""+Formater.formatNumber(sumReportDepartment.getSummary(),frmCurrency));
                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                            }else{
                                rowx.add("0");
                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }

                            countMxData++;
                            Date nextProcess = (Date)ProcsDt.clone();nextProcess.setDate(nextProcess.getDate()+1);
                            ProcsDt = nextProcess;
                        }

                        rowx.add(""+sumVisitation);
                        double sumTot = Double.parseDouble(""+sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;
                        rowx.add(""+Formater.formatNumber(sumTot,frmCurrency));

                        lstData.add(rowx);
                   }

                    Vector rowx = new Vector();
                    rowx.add("");
                    rowx.add("<B>TOTAL</B>");
                    rowx.add("");
                    rowx.add("");
                    int tot = 0;
                    for(int iDt = 0 ; iDt < lengthDate ; iDt++){

                        rowx.add("<B>"+sumDt[iDt]+"</B>");
                        tot = tot + sumDt[iDt];

                    }

                    rowx.add("<B>"+Formater.formatNumber(tot,frmCurrency)+"</B>");
                    rowx.add("<B>"+Formater.formatNumber(sumAmount,frmCurrency)+"</B>");
                    lstData.add(rowx);

                    rowx = new Vector();
                    rowx.add("&nbsp;");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");

                    for(int iDtx = 0 ; iDtx < lengthDate ; iDtx++){
                        rowx.add("");
                    }

                    rowx.add("");
                    rowx.add("");
                    lstData.add(rowx);
                    
                }
                
                    Vector rowx = new Vector();
                    rowx.add("<B>TOTAL ALL</B>");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    int totAll = 0;
                    
                    for(int iDtAll = 0 ; iDtAll < lengthDate ; iDtAll++){

                        rowx.add("<B>"+Formater.formatNumber(sumTotal[iDtAll],frmCurrency)+"</B>");
                        totAll = totAll + sumTotal[iDtAll];

                    }

                    rowx.add("<B>"+Formater.formatNumber(totAll,frmCurrency)+"</B>");
                    rowx.add("<B>"+Formater.formatNumber(sumTotalAll,frmCurrency)+"</B>");
                    lstData.add(rowx);

                ctrlist.setLinkSufix("')");
                return ctrlist.drawList();

	}

%>



<%
    
    Vector dataOfVisits = new Vector();
    Vector dataOfVisitsAccess = new Vector();
    String pageWidth = "100%";
    SumReportParameter sumReportParameter = new SumReportParameter();

    if (iCommand == Command.LIST) {
        
        SessCanteenVisitation sessCanteenVisitation = new SessCanteenVisitation();
        
        dataOfVisits =  sessCanteenVisitation.getSummaryReportDepartment(date, dateto, scheduleId);
        dataOfVisitsAccess =  sessCanteenVisitation.getSummaryReportDepartmentAccess(date, dateto, scheduleId);

        sumReportParameter.setStartDt(date);
        sumReportParameter.setEndDt(dateto);
        sumReportParameter.setNominal(val);
        sumReportParameter.setSchIdx(scheduleId);
        
        if (dataOfVisits.size() > 0){
            pageWidth = "100%";
        }

        try{
            session.removeValue("SUMMARY_CANTEEN_DEPARTMEN");
            session.removeValue("SUMMARY_CANTEEN_DEPARTMENT_PARAMETER");
	}catch(Exception e){
            System.out.println("Exc when remove from session(\"SUMMARY_CANTEEN_DEPARTMENT\") : " + e.toString());
	}

        try{
            session.putValue("SUMMARY_CANTEEN_DEPARTMEN",dataOfVisits);
            session.putValue("SUMMARY_CANTEEN_DEPARTMENT_PARAMETER",sumReportParameter);
	}catch(Exception e){
            System.out.println("Exc when put to session(\"SUMMARY_CANTEEN_DEPARTMEN\") : " + e.toString());
	}

    }
  
%>

<html>
<script language="JavaScript" type="text/JavaScript">
<!--
    function deptChange() {
        document.frmMonthlyReport.command.value = "<%=Command.GOTO%>";
        document.frmMonthlyReport.action = "summary_periodic_department.jsp";
        document.frmMonthlyReport.submit();
    }

    function cekDate(){
        var mth1 = document.frmMonthlyReport.cmbDate_mn.value
        var mth2 = document.frmMonthlyReport.cmbDateto_mn.value
        if(mth1==mth2){
            var yr1 = document.frmMonthlyReport.cmbDate_yr.value
            var yr2 = document.frmMonthlyReport.cmbDateto_yr.value
            if(yr1!=yr2){
                alert("please select same year.");
                return false;
            }
        }else{
            alert("please select same month.");
            return false;
        }
        return true;
    }

    function cmdView() {     
        document.frmMonthlyReport.command.value = "<%=Command.LIST%>";
        document.frmMonthlyReport.action = "summary_periodic_department.jsp";
        document.frmMonthlyReport.submit();       
    }

    function cmdPrintXLS(){
        pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.canteen.CanteenSumReport";
        window.open(pathUrl);
    }

    function reportPdf() {
        var linkPage = "<%=printroot%>.report.canteen.MonthlySummaryVisitation";
        handle = window.open("", "<%=canteenWindowName%>");
        handle.close();
        handle = window.open(linkPage, "<%=canteenWindowName%>");
        handle.focus();
    }

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #BeginTemplate "/Templates/main.dwt" --><!-- DW6 -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - </title>
<!-- #EndEditable -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" -->
<link rel="stylesheet" href="../../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" -->
<link rel="stylesheet" href="../../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" -->
<SCRIPT language=JavaScript>
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
</SCRIPT>
<!-- #EndEditable -->
</head>

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="<%=pageWidth%>%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
      <!-- #BeginEditable "header" -->
      <%@ include file = "../../../main/header.jsp" %>
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
  <%}%>
  <tr>
    <td width="88%" valign="top" align="left" >
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report
                  &gt;&gt; Summary &gt;&gt; Periodic Meal Record<!-- #EndEditable -->
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
                        <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr>
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->
							  <form name="frmMonthlyReport" method="post" action="">
                              <input type="hidden" name="command" value="<%=iCommand%>">
                              <input type="hidden" name="hidden_sec_name" value="<%=secName%>">
                              <input type="hidden" name="hidden_dept_name" value="<%=departmentName%>">
                              <table width="100%"  border="0" cellspacing="0" cellpadding="1">
                                <tr>
                                    <td><table border="0" width="100">
                                    <tr>
                                      <td>Period</td>
                                      <td>:</td>
                                      <td>
                                        <%
                                        Date currentDate = new Date();
                                        int currentYear = currentDate.getYear();
                                        int installationYear = installationDate.getYear();
                                        int diffYear = installationYear - currentYear;
                                       %>
                                        <%=ControlDate.drawDate("cmbDate", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, diffYear)%> to <%=ControlDate.drawDate("cmbDateto", dateto == null || iCommand == Command.NONE ? new Date() : dateto, "formElemen", 0, diffYear)%></td>
                                    </tr>                                    
                                    <tr>
                                            <td valign="top">Time</td>
                                            <td valign="top">:</td>
                                            <td valign="top">
                                            <%
                                            Vector listCntSch = new Vector();

                                            String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE]+" ASC ";
                                            listCntSch = PstCanteenSchedule.list(0, 0, "" , order);

                                            int count = 0;

                                            FRMHandler objFRMHandler = new FRMHandler();
                                            objFRMHandler.setDecimalSeparator(",");
                                            objFRMHandler.setDigitSeparator(".");

                                            for(int i = 0 ; i < listCntSch.size() ; i++){

                                                CanteenSchedule canteenSchedule = new CanteenSchedule();
                                                canteenSchedule = (CanteenSchedule)listCntSch.get(i);

                                                String nameInp = "SCH_"+canteenSchedule.getOID();
                                                String nameTxtInp = "TXT_"+canteenSchedule.getOID();

                                                if(scheduleId[i].compareTo("1")==0){
                                            %>
                                                    <input name=<%=nameInp%> type="checkbox" checked value=1 > <%=canteenSchedule.getSName()%>&nbsp;&nbsp;&nbsp;&nbsp;-> &nbsp;&nbsp;nominal:&nbsp;&nbsp;
                                                    <input name=<%=nameTxtInp%> type="text" class="numberright" value="<%=objFRMHandler.userFormatStringDecimal(val[i])%>">
                                                    <BR><BR>
                                            <%

                                                }else{
                                            %>
                                                    <input name=<%=nameInp%> type="checkbox" value=1 > <%=canteenSchedule.getSName()%>&nbsp;&nbsp;&nbsp;&nbsp;-> &nbsp;&nbsp;nominal:&nbsp;&nbsp;
                                                    <input name=<%=nameTxtInp%> type="text" class="numberright" value="<%=objFRMHandler.userFormatStringDecimal(val[i])%>">
                                                    <BR><BR>
                                            <%
                                                }

                                                 count ++;
                                            }

                                            if(scheduleId[count].compareTo("1")==0){
                                            %>
                                                <input name="SCH_0" type="checkbox" value=1 checked > SPLIT &nbsp;&nbsp;&nbsp;&nbsp;-> &nbsp;&nbsp;nominal:&nbsp;&nbsp;
                                                <input name="TXT_0" type="text" class="numberright" value="<%=objFRMHandler.userFormatStringDecimal(val[count])%>" >
                                                <BR><BR>
                                            <%
                                            }else{
                                            %>
                                                <input name="SCH_0" type="checkbox" value=1 > SPLIT &nbsp;&nbsp;&nbsp;&nbsp;-> &nbsp;&nbsp;nominal:&nbsp;&nbsp;
                                                <input name="TXT_0" type="text" class="numberright" value="<%=objFRMHandler.userFormatStringDecimal(val[count])%>" >
                                                <BR><BR>
                                            <%
                                            }
                                            %>
                                            </td>
                                      </tr>
                                    <tr>
                                      <td></td>
                                      <td></td>
                                      <td><table width="447">
                                          <tr>
                                            <td width="24"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Visitation"></a></td>
                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                            <td width="397" class="command" nowrap><a href="javascript:cmdView()">View Visitation</a></td>
                                          </tr>
                                      </table></td>
                                    </tr>
                                    </table>
                                    </td>
                                    </tr>
                                    <tr>
                                        <td valign="top">
                                            <% if (iCommand == Command.LIST) { %>

                                                <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                <tr>
                                                    <td><%=drawListCanteenVisitation(date,dateto,dataOfVisits,dataOfVisitsAccess,scheduleId,val)%></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td align="left" class="command" nowrap>
                                                        <a href="javascript:cmdPrintXLS()">Print XLS</a>
                                                    </td>
                                                </tr>
                                                </table>

                                            <% } %>
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
                                <%@include file="../../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
