<%@page import="com.dimata.harisma.form.payroll.CtrlPaySlipNote"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPaySlipNote"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%


    int iCommand = FRMQueryString.requestCommand(request);
    long oidEmployee = FRMQueryString.requestLong(request, FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_EMPLOYEE_ID]);
//int noteType = FRMQueryString.requestInt(request, "note_type");
    //String note = FRMQueryString.requestString(request, FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE]);
    long periodId = FRMQueryString.requestLong(request, FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_PERIODE]);
    long paySlipId = FRMQueryString.requestLong(request, FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_ID]);
    long dtAdjusment = FRMQueryString.requestLong(request, FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_DATE]);
    Date dtAdjumentDate = new Date(dtAdjusment);
//out.println("iCommand : "+iCommand+", oid : "+oid+", type : "+noteType);
//out.println("note : "+note);



    String whereClause = "";
    if (iCommand == Command.SAVE) {
        if (oidEmployee != 0 && periodId != 0 && paySlipId != 0 && dtAdjusment != 0) {
            Date newDtAdjusment = new Date(dtAdjusment);
            whereClause = PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_EMPLOYEE_ID] + "=" + oidEmployee
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_PERIODE] + "=" + periodId
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_DATE] + "=\"" + Formater.formatDate(newDtAdjusment, "yyyy-MM-dd 00:00") + "\""
                    + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_ID] + "=" + paySlipId;
        }

    }


    Employee emp = new Employee();
    int iErrCode = CtrlPaySlipNote.RSLT_OK;
    CtrlPaySlipNote ctrlPaySlipNote = new CtrlPaySlipNote(request);
    if (oidEmployee != 0) {
        try {
            emp = PstEmployee.fetchExc(oidEmployee);
            iErrCode = ctrlPaySlipNote.action(iCommand, whereClause);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    if (oidEmployee != 0 && periodId != 0 && paySlipId != 0) {
        whereClause = PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_EMPLOYEE_ID] + "=" + oidEmployee
                + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_PERIODE] + "=" + periodId
                + " AND " + PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_ID] + "=" + paySlipId;
    }
    String order = PstPaySlipNote.fieldNames[PstPaySlipNote.FLD_PAY_SLIP_NOTE_DATE] + " DESC ";
    Vector listPaySlipNote = new Vector();
    if (whereClause != null && whereClause.length() > 0) {
        listPaySlipNote = PstPaySlipNote.list(0, 0, whereClause, order);
    }


%>
<html>
    <head>
        <title>Note Edit</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script language="JavaScript">
            function cmdSave(){
                document.frmnote.command.value="<%=Command.SAVE%>";
                document.frmnote.action="note_edit.jsp";
                document.frmnote.submit();
            }


            function cmdBack(){
                document.frmnote.command.value="<%=Command.BACK%>";
                document.frmnote.action="srcleavestock.jsp";
                document.frmnote.submit();
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
    </head>

    <body bgcolor="#FFFFFF" text="#000000">
        <form name="frmnote" method="post" action="">
            <input type="hidden" name="command" value="">
            <input type="hidden" name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
            <input type="hidden" name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_PERIODE]%>" value="<%=periodId%>">
            <input type="hidden" name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_ID]%>" value="<%=paySlipId%>">
            <input type="hidden" name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_DATE]%>" value="<%=dtAdjusment%>">


            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td colspan="3"><b>PAYROLL INPUT NOTE</b></td>
                </tr>
                <tr> 
                    <td width="13%">&nbsp;</td>
                    <td width="10%">&nbsp;</td>
                    <td width="77%">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="13%"><b>Employee</b></td>
                    <td width="10%"><b>:</b></td>
                    <td width="77%"><%=emp.getFullName()%></td>
                </tr>
                <tr> 
                    <td width="13%" nowrap><b>Payroll Number</b></td>
                    <td width="10%"><b>:</b></td>
                    <td width="77%"><%=emp.getEmployeeNum()%></td>
                </tr>
                <tr> 
                    <td width="13%"><b></b></td>
                    <td width="10%"><b></b></td>
                    <td width="77%">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="13%" nowrap="nowrap"><b>Note  
                        </b></td>
                    <td colspan="2">

                        <table width="100%">

                            <%
                                boolean belumAdaAdd = false;
                                if (listPaySlipNote != null && listPaySlipNote.size() > 0) {

                                    boolean prevAdaAdd=false;
                                    for (int idxc = 0; idxc < listPaySlipNote.size(); idxc++) {
                                        PaySlipNote paySlipNote = (PaySlipNote) listPaySlipNote.get(idxc);

                                        
                                        if (paySlipNote.getDtPaySlipNote() != null && paySlipNote.getDtPaySlipNote().getTime() == dtAdjumentDate.getTime()) {
                                            prevAdaAdd=true;
                            %>

                            <tr>
                                <td width="20%" nowrap="nowrap">: <%=Formater.formatDate(paySlipNote.getDtPaySlipNote() == null ? new Date() : paySlipNote.getDtPaySlipNote(), "dd MMM yyyy")%> <%=ControlDatePopup.writeDateDisabled(FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_DATE], paySlipNote.getDtPaySlipNote() != null ? paySlipNote.getDtPaySlipNote() : new Date())%> :</td>
                                <td width="76%"><textarea name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE]%>" rows="3" cols="35"><%=paySlipNote.getNote()%></textarea></td> 
                            </tr>
                            <%} else {
                            %>
                            <tr>
                                <td width="20%" nowrap="nowrap">: <%=paySlipNote.getDtPaySlipNote() == null ? "" : Formater.formatDate(paySlipNote.getDtPaySlipNote(), "dd MMM yyyy")%> :</td>
                                <td width="76%"><%=paySlipNote.getNote()%></td> 
                            </tr>

                            <%
                                        if (idxc == listPaySlipNote.size() - 1 && prevAdaAdd==false) { 
                                            belumAdaAdd = true;
                                        }
                                    }

                                }

                            } else {%>
                            <tr>
                                <td width="20%" nowrap="nowrap">: <%=Formater.formatDate(dtAdjumentDate == null ? new Date() : dtAdjumentDate, "dd MMM yyyy")%> <%=ControlDatePopup.writeDateDisabled(FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_DATE], dtAdjumentDate != null ? dtAdjumentDate : new Date())%> :</td>
                                <td width="76%"><textarea name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE]%>" rows="3" cols="35"></textarea></td> 
                            </tr>
                            <%}
                                if (belumAdaAdd) {%>
                            <tr>
                                <td width="20%" nowrap="nowrap">: <%=Formater.formatDate(dtAdjumentDate == null ? new Date() : dtAdjumentDate, "dd MMM yyyy")%> <%=ControlDatePopup.writeDateDisabled(FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE_DATE], dtAdjumentDate != null ? dtAdjumentDate : new Date())%> :</td>
                                <td width="76%"><textarea name="<%=FrmPaySlipNote.fieldNames[FrmPaySlipNote.FRM_PAY_SLIP_NOTE]%>" rows="3" cols="35"></textarea></td> 
                            </tr>
                            <%}

                            %>
                        </table>
                    </td>
                </tr>
                <tr> 
                    <td width="13%">&nbsp;</td>
                    <td width="10%">&nbsp;</td>
                    <td width="77%">&nbsp;</td>
                </tr>
                <tr> 
                    <td width="13%">&nbsp;</td>
                    <td width="10%">&nbsp;</td>
                    <td width="77%"> 
                        <table width="50%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td width="6%" class="command" nowrap><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="save data"></a></td>
                                <td width="2%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                <td width="92%" class="command" nowrap><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch">Save 
                                        Note </a></td>
                            </tr>
                        </table>      </td>
                </tr>
                <tr> 
                    <td width="13%">&nbsp;</td>
                    <td width="10%">&nbsp;</td>
                    <td width="77%">&nbsp;</td>
                </tr>
            </table>
        </form>
    </body>
</html>
