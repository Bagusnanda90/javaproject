<%-- 
    Document   : hutang kariawan
    Created on : April 17, 2015, 3:56:51 PM
    Author     : Priska
--%>
<%@page import="com.dimata.harisma.entity.arap.*"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApMain"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApPayment"%>
<%@page import="com.dimata.harisma.entity.arap.ArApPayment"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.payroll.CurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.arap.ArApPayment"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApPayment"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApPayment"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.arap.CtrlArApPayment"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  arApMain.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Priska_20150417
             * @version  		: -
             */

            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 			: [output ...]
             *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!    public String drawList(Vector objectClass) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        ctrlist.addHeader("Pay Amount", "20%");
        
        ctrlist.addHeader("Left To Pay", "10%");
        ctrlist.addHeader("Due Date", "10%");
        ctrlist.addHeader("Description", "50%");
        ctrlist.addHeader("Status", "10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
                        double totalangsuran = 0;
                        double totallefttopay = 0;
        for (int i = 0; i < objectClass.size(); i++) {
            ArApItem arApItem = (ArApItem) objectClass.get(i);
            Vector rowx = new Vector();
            
            rowx.add(""+(i+1));
            rowx.add(arApItem.getAngsuran()!=0?"Rp. " + df.format(arApItem.getAngsuran()) : "-");
            rowx.add(arApItem.getLeftToPay()!=0?"Rp. " + df.format(arApItem.getLeftToPay()) : "-");
            rowx.add(""+arApItem.getDueDate());
            rowx.add(""+arApItem.getDescription());
       
            rowx.add(""+(arApItem.getArApItemStatus() == 0 ? "Open" :"Close"));
            
            totalangsuran = totalangsuran + arApItem.getAngsuran();
            totallefttopay = totallefttopay + arApItem.getLeftToPay();
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(arApItem.getOID()));
        }
                        
         Vector rowx = new Vector();
            
            rowx.add("TOTAL ");
            rowx.add(totalangsuran!=0?"Rp. " + df.format(totalangsuran) : "-");
            rowx.add(totallefttopay!=0?"Rp. " + df.format(totallefttopay) : "-");
            rowx.add("");
            rowx.add("");
            
            rowx.add("");
            
            lstData.add(rowx);               
                        
        return ctrlist.draw(index);
    }

%>
<%!

	public String drawListn(int iCommand,FrmArApPayment frmArApPayment, ArApPayment objEntity, Vector objectClass,  long arapPaymentId, long oidarapmain)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("listgentitle");
                ctrlist.addHeader("No", "10%");
                //ctrlist.addHeader("Payment No", "20%");
                ctrlist.addHeader("Pay Amount", "20%");
                ctrlist.addHeader("Date Of Payment", "20%");
                //ctrlist.addHeader("Description", "50%");
                ctrlist.addHeader("Type Of Payment", "20%");
                ctrlist.addHeader("Received By", "30%");
                ctrlist.addHeader("Status", "10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;

                
                        //set rupiah
                        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
                        
		for (int i = 0; i < objectClass.size(); i++) {
			 ArApPayment arApPayment = (ArApPayment)objectClass.get(i);
			 rowx = new Vector();
			 if(arapPaymentId == arApPayment.getOID())
				 index = i; 

			 rowx.add(""+(i+1));
//          rowx.add(""+arApPayment.getPaymentNo()); 
            rowx.add(arApPayment.getAmount()!=0?"Rp. " + df.format(arApPayment.getAmount()) : "-");
            rowx.add(""+arApPayment.getPaymentDate());
            //rowx.add(""+arApPayment.get);
            rowx.add(""+(arApPayment.getPaymentType() != 0 ? "Deduction Of Payroll" : "Cash"));
            String nama = PstEmployee.getEmployeeName(arApPayment.getEmployeeId());                    
            rowx.add(""+nama);
            rowx.add(""+(arApPayment.getPaymentStatus() == 0 ? "Open" :"Close"));
			

			lstData.add(rowx);
		}

		 rowx = new Vector();

		
                                
                                
                                 rowx.add("-");  
                                rowx.add("<input type=\"text\" name=\"" + frmArApPayment.fieldNames[frmArApPayment.FRM_AMOUNT]+ "\"  value=\"\" size=\"10\" class=\"elemenForm\"><input type=\"hidden\" name=\"" + FrmArApPayment.fieldNames[FrmArApPayment.FRM_ARAP_MAIN_ID]+ "\"  value=\""+oidarapmain+"\" size=\"10\" class=\"elemenForm\">");
                                rowx.add(ControlDate.drawDateWithStyle(frmArApPayment.fieldNames[frmArApPayment.FRM_PAYMENT_DATE], new Date(), 0, -40, "formElemen") );
                                rowx.add(ControlCombo.draw(frmArApPayment.fieldNames[frmArApPayment.FRM_PAYMENT_TYPE], "formElemen", null, "0", FrmArApMain.getTypePayValue(), FrmArApMain.getTypePayKey(), "") );
                                //rowx.add("-"); 
                                //rowx.add("-"); 
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\"\" class=\"elemenForm\"> <input type=\"hidden\" name=\""+frmArApPayment.fieldNames[frmArApPayment.FRM_FIELD_EMPLOYEE_ID]+"\" value=\"\" class=\"formElemen\" > <a href=\"javascript:cmdSearchEmpPay()\">add employee</a>");
                                //rowx.add("<input type=\"text\" name=\"" + FrmArApPayment.fieldNames[FrmArApPayment.FRM_PAYMENT_STATUS]+ "\"  value=\"\" size=\"10\" class=\"elemenForm\">");
                                rowx.add("-");
		
		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%!    public String drawListPayment(Vector objectClass, long oidarapmain, FrmArApPayment frmArApPayment ) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        //ctrlist.addHeader("Payment No", "20%");
        ctrlist.addHeader("Pay Amount", "20%");
        ctrlist.addHeader("Date Of Payment", "20%");
        //ctrlist.addHeader("Description", "50%");
        ctrlist.addHeader("Type Of Payment", "20%");
        ctrlist.addHeader("Received By", "30%");
        ctrlist.addHeader("Status", "10%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

         //set rupiah
         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
       
        for (int i = 0; i < objectClass.size(); i++) {
            ArApPayment arApPayment  = (ArApPayment) objectClass.get(i);
            Vector rowx = new Vector();
            
            rowx.add(""+(i+1));
//          rowx.add(""+arApPayment.getPaymentNo()); 
            rowx.add(arApPayment.getAmount()!=0?"Rp. " + df.format(arApPayment.getAmount()) : "-");
            rowx.add(""+arApPayment.getPaymentDate());
            //rowx.add(""+arApPayment.get);
            rowx.add(""+(arApPayment.getPaymentType() != 0 ? "Deduction Of Payroll" : "Cash"));
            String nama = PstEmployee.getEmployeeName(arApPayment.getEmployeeId());                    
            rowx.add(""+nama);
            rowx.add(""+(arApPayment.getPaymentStatus() == 0 ? "Open" :"Close"));
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(arApPayment.getOID()));
        }
            ArApMain arApMain = new ArApMain();
        try{
            arApMain = PstArApMain.fetchExc(oidarapmain);                
        } catch (Exception e){
            
        }   
        if (arApMain.getArApMainStatus() == 0){
        Vector rowx = new Vector();     
                                rowx.add("-");  
                                rowx.add("<input type=\"text\" name=\"" + frmArApPayment.fieldNames[frmArApPayment.FRM_AMOUNT]+ "\"  value=\"\" size=\"10\" class=\"elemenForm\"><input type=\"hidden\" name=\"" + FrmArApPayment.fieldNames[FrmArApPayment.FRM_ARAP_MAIN_ID]+ "\"  value=\""+oidarapmain+"\" size=\"10\" class=\"elemenForm\">");
                                rowx.add(ControlDate.drawDateWithStyle(frmArApPayment.fieldNames[frmArApPayment.FRM_PAYMENT_DATE], new Date(), 0, -40, "formElemen") );
                                rowx.add(ControlCombo.draw(frmArApPayment.fieldNames[frmArApPayment.FRM_PAYMENT_TYPE], "formElemen", null, "0", FrmArApMain.getTypePayValue(), FrmArApMain.getTypePayKey(), "") );
                                //rowx.add("-"); 
                                //rowx.add("-"); 
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\"\" class=\"elemenForm\"> <input type=\"hidden\" name=\""+frmArApPayment.fieldNames[frmArApPayment.FRM_FIELD_EMPLOYEE_ID]+"\" value=\"\" class=\"formElemen\" > <a href=\"javascript:cmdSearchEmpPay()\">add employee</a>");
                                //rowx.add("<input type=\"text\" name=\"" + FrmArApPayment.fieldNames[FrmArApPayment.FRM_PAYMENT_STATUS]+ "\"  value=\"\" size=\"10\" class=\"elemenForm\">");
                                rowx.add("-");
		lstData.add(rowx); 
        }                              
        return ctrlist.draw(index);
    }

%>


<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidArapPayment = FRMQueryString.requestLong(request, "hidden_arapPayment_id");
            long oidArapMain = FRMQueryString.requestLong(request, "hidden_arapMain_id");
            oidArapPayment = 0;
       
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = " arap_main_id = " + oidArapMain;
            String orderClause = "";

            CtrlArApPayment ctrlArApPayment = new CtrlArApPayment(request);
            ControlLine ctrLine = new ControlLine();
            Vector listArApPayment = new Vector(1, 1);
            Vector listArApItem = new Vector(1, 1);
            /*switch statement */
            iErrCode = ctrlArApPayment.action(iCommand, oidArapPayment);
            /* end switch*/
            FrmArApPayment frmArApPayment = ctrlArApPayment.getForm();
            /*count list All Position*/
            int vectSize = PstArApPayment.getCount(whereClause);

            ArApPayment arApPayment = ctrlArApPayment.getArApPayment();
            msgString = ctrlArApPayment.getMessage();

            /*switch list ArApPayment*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstArApPayment.findLimitStart(arApMain.getOID(),recordToGet, whereClause);
                oidArapPayment = arApPayment.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlArApPayment.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listArApPayment = PstArApPayment.list(start, recordToGet, whereClause, orderClause);
            listArApItem = PstArApItem.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
          //  if (listArApPayment.size() < 1 && start > 0) {
          //      if (vectSize - recordToGet > recordToGet) {
          //          start = start - recordToGet;   //go to Command.PREV
          //      } else {
          //          start = 0;
          //          iCommand = Command.FIRST;
          //          prevCommand = Command.FIRST; //go to Command.FIRST
          //      }
          //      listArApPayment = PstArApPayment.list(start, recordToGet, whereClause, orderClause);
          //  }
            
            if (listArApPayment.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listArApItem = PstArApItem.list(start, recordToGet, whereClause, orderClause);
                listArApPayment = PstArApPayment.list(start, recordToGet, whereClause, orderClause);
            }


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - ArApPayment</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmArApPayment.hidden_arapPayment_id.value="0";
                document.frmArApPayment.command.value="<%=Command.ADD%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdAsk(oidArapPayment){
                document.frmArApPayment.hidden_arapPayment_id.value=oidArapPayment;
                document.frmArApPayment.command.value="<%=Command.ASK%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdConfirmDelete(oidArapPayment){
                document.frmArApPayment.hidden_arapPayment_id.value=oidArapPayment;
                document.frmArApPayment.command.value="<%=Command.DELETE%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_main_list.jsp";
                document.frmArApPayment.submit();
            }
            function cmdSave(){
                document.frmArApPayment.command.value="<%=Command.SAVE%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }
            function cmdPost(){
                document.frmArApPayment.command.value="<%=Command.POST%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdEdit(oidArapPayment){
                document.frmArApPayment.hidden_arapPayment_id.value=oidArapPayment;
                document.frmArApPayment.command.value="<%=Command.EDIT%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdCancel(oidArapPayment){
                document.frmArApPayment.hidden_arapPayment_id.value=oidArapPayment;
                document.frmArApPayment.command.value="<%=Command.EDIT%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdBack(){
                document.frmArApPayment.command.value="<%=Command.BACK%>";
                document.frmArApPayment.action="arap_main_list.jsp";
                document.frmArApPayment.submit();
            }

            function cmdListFirst(){
                document.frmArApPayment.command.value="<%=Command.FIRST%>";
                document.frmArApPayment.prev_command.value="<%=Command.FIRST%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdListPrev(){
                document.frmArApPayment.command.value="<%=Command.PREV%>";
                document.frmArApPayment.prev_command.value="<%=Command.PREV%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdListNext(){
                document.frmArApPayment.command.value="<%=Command.NEXT%>";
                document.frmArApPayment.prev_command.value="<%=Command.NEXT%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function cmdListLast(){
                document.frmArApPayment.command.value="<%=Command.LAST%>";
                document.frmArApPayment.prev_command.value="<%=Command.LAST%>";
                document.frmArApPayment.action="arap_payment.jsp";
                document.frmArApPayment.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
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
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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

                    function cmdSearchEmp(){

                            window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmArApPayment&empPathId=<%=frmArApPayment.fieldNames[frmArApPayment.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
                   // cmdSearchEmpPay()
                    function cmdSearchEmpPay(){

                            window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmArApPayment&empPathId=<%=frmArApPayment.fieldNames[frmArApPayment.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
                                                    Employee Deduction &gt; ArApPayment<!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmArApPayment" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_arapPayment_id" value="<%=oidArapPayment%>">
                                                                                    <input type="hidden" name="hidden_arapMain_id" value="<%=oidArapMain%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                       
                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                   
                                                                                                    
                                                                                                    <% if (listArApItem.size() > 0) { %>
                                                                                                           
                                                                                                            <tr>
                                                                                                            <span class="bullettitle1">
                                                                                                                  <li>Menghapus Maupun Mengedit Employee Deduction akan otomatis menghapus data item dan Payment realyzation </li>
                                                                                                            </span>
                                                                                                            </tr>
                                                                                                        <td>Payment Plan</td>
 
                                                                                                        <tr align="left" valign="top">
                                                                                                            <td height="22" valign="middle" colspan="3">
                                                                                                                <%= drawList(listArApItem)%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <!--<td>Payment Realization</td>
                                                                                                        <tr align="left" valign="top">
                                                                                                            <td height="22" valign="middle" colspan="3">
                                                                                                                <!--%= drawListn(iCommand,frmArApPayment, arApPayment, listArApPayment,oidArapPayment,oidArapMain)%-->
                                                                                                               <!--
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        -->
                                                                                                        <% } %>
                                                                                                      
                                                                                                        <!--<td colspan="2">
                                                                                                            </tr>
                                                                                                               <tr>
                                                                                                                    <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<!--%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<!--%=approot%>/images/BtnNew.jpg" width="24" height="24" ></a> <a href="javascript:cmdSave()" class="command" style="text-decoration:none">SAVE</a></td>
                                                                                                               </tr>-->
                                                                                                               <tr>
                                                                                                                    <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" ></a> <a href="javascript:cmdBack()" class="command" style="text-decoration:none">BACK</a></td>
                                                                                                               </tr>
                                                                                                            
                                                                                                        <tr>
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
                                <%@include file="../../footer.jsp" %>
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
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

