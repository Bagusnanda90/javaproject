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
<%@ page import = "com.dimata.harisma.session.employee.SessEmployee" %>
<%@ page import = "com.dimata.harisma.utility.service.tma.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_BARCODE, AppObjInfo.OBJ_INSERT_AND_UPLOAD_BARCODE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    int iCommand = FRMQueryString.requestCommand(request);    
        
    Vector mchStatus = new Vector(1,1);
    Vector ctnStatus = new Vector(1,1);
    
    Vector mchAction = new Vector(1,1);
    Vector ctnAction = new Vector(1,1);
    
    String s_barcode_number = null;
    String s_old_barcode_number = null;
    String s_emp_pin = null;
    String machineNumber = "01";
    String machineNumberCanteen = "01";
    String[] machineNumbers;
    String[] machineNumbersCanteen;
    Vector vMsg = new Vector(1,1);
    Vector vMsgCanteen = new Vector(1,1);
  //  SessEmployee.setAllEmpFingerPrintNumber();
    int iUseFingerPrint = 0;
    machineNumber = String.valueOf(PstSystemProperty.getValueByName("ABSEN_TMA_NO"));
    machineNumberCanteen = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
    iUseFingerPrint = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("FINGER_PRINT_NUMBER")));
    StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
    StringTokenizer strTokenizerCanteen = new StringTokenizer(machineNumberCanteen,",");
    machineNumbers = new String[strTokenizer.countTokens()];
    machineNumbersCanteen = new String[strTokenizerCanteen.countTokens()];

    int count = 0;
    int countCanteen = 0;
    while(strTokenizer.hasMoreTokens()){
        machineNumbers[count] = strTokenizer.nextToken();
        System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
        count ++;
    }
    while(strTokenizerCanteen.hasMoreTokens()){
        machineNumbersCanteen[countCanteen] = strTokenizerCanteen.nextToken();
        System.out.println("CANTEEN MACHINE :::::::::: "+machineNumbersCanteen[countCanteen]);
        countCanteen ++;
    }
    
    System.out.println(" CEK ABSEN MACHINE :::::::::: "+machineNumbers.length);
    System.out.println(" CEK CANTEEN MACHINE :::::::::: "+machineNumbersCanteen.length);
	// Proses jika command adalah ASVE	
    if (iCommand == Command.SAVE) {
	// Jika array barcode_number tidak kosong (ada/minimal satu barcode employee diisi)	
        
            //PROSES PENGIRIMAN PESAN
            
                System.out.println("-----------------------------------------------");
                System.out.println("----------- START SEND MESSAGE ----------------");
                System.out.println("-----------------------------------------------");
                //PESAN AKAN DIKIRIM PADA SATU MESIN TERLEBIH DAHULU
                Vector vList = new Vector(1,1);
                vList = SessEmpMessage.listDataEmpMessageReady(new Date());
                //KIRIMKAN PESAN KE MESIN ABSENSI
                for(int icount=0;icount<machineNumbers.length;icount++){
                    //iUseFingerPrint untuk menghitung banyak sidik jari yang dipergunakan
                    String isOn = FRMQueryString.requestString(request,"MACHINE"+icount);
                    I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[icount]);
                    mchAction.add(isOn);
                    if(isOn.equals("1")){
                            //System.out.println("- PESAN : "+empMessage.getEmployeeId()+" = "+empMessage.getMessage());
                        boolean status = i_Machine.processOpenComm(i_Machine.COMM_STATUS_OPEN);
                        if(status){
                            mchStatus.add("- <b>OK</b>");                        
                        }else{
                            mchStatus.add("- <b><font color=red>FAILED</font></b> (Check the machine)");
                        }
                    }else if(isOn.equals("2")){
                        boolean status = i_Machine.processOpenComm(i_Machine.COMM_STATUS_CLOSE);
                        if(status){
                            mchStatus.add("- <b>OK</b>");                        
                        }else{
                            mchStatus.add("- <b><font color=red>FAILED</font></b> (Check the machine)");
                        }
                    }else{
                        mchStatus.add("");
                    }
                }
                //KIRIMKAN PESAN KE MESIN CANTEEN
                for(int icount=0;icount<machineNumbersCanteen.length;icount++){
                    //iUseFingerPrint untuk menghitung banyak sidik jari yang dipergunakan
                    String isOn = FRMQueryString.requestString(request,"MACHINE_CANTEEN"+icount);
                    I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbersCanteen[icount]);
                    ctnAction.add(isOn);
                    if(isOn.equals("1")){
                            //System.out.println("- PESAN : "+empMessage.getEmployeeId()+" = "+empMessage.getMessage());
                        boolean status = i_Machine.processOpenComm(i_Machine.COMM_STATUS_OPEN);
                        if(status){
                            ctnStatus.add("- <b>OK</b>");                        
                        }else{
                            ctnStatus.add("- <b><font color=red>FAILED</font></b> (Check the machine)");
                        }
                    }else if(isOn.equals("2")){
                        boolean status = i_Machine.processOpenComm(i_Machine.COMM_STATUS_CLOSE);
                        if(status){
                            ctnStatus.add("- <b>OK</b>");                        
                        }else{
                            ctnStatus.add("- <b><font color=red>FAILED</font></b> (Check the machine)");
                        }
                    }else{
                        ctnStatus.add("");
                    }
                }
        }
    
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Comm Management</title>
<script language="JavaScript">
function cmdSave() {
	document.frmCommMachine.command.value = "<%=String.valueOf(Command.SAVE)%>";
	document.frmCommMachine.action = "close_comm.jsp";
	document.frmCommMachine.submit();
}


//-------------- script control line -------------------
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

function getThn(){
            <%=ControlDatePopup.writeDateCaller("frmCommMachine","start_date")%>
            <%=ControlDatePopup.writeDateCaller("frmCommMachine","end_date")%>
            
    }


    function hideObjectForDate(index){
        if(index==1){
            <%//=ControlDatePopup.writeDateHideObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
        }
    }

    function showObjectForDate(){
        <%//=ControlDatePopup.writeDateShowObj("frdp", FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS])%>
    }

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
    
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --><!-- #EndEditable -->    </td>
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
                  &gt; Timekeeping &gt; Comm Management<!-- #EndEditable --> 
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
                                <% if (privStart) { %>
                                    <form name="frmCommMachine" method="post" action="">
                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">																																													

                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                            <td>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="4"><b><u><font color="#FF0000">INFORMATION</font></u></b>: 
                                                  <br>
                                                  Use this form to <b>CLOSE/OPEN COMM</b>
                                                  timekeeping machine.
                                                  Please make sure that all of 
                                                  timekeeping machines are switched 
                                                  on.
                                                  
                                                  <br>                                                  
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="100%" colspan="4"><b>Setting to Machine</b></td>
                                              </tr>
                                            <% 
                                            for(int i=0;i<machineNumbers.length;i++){
                                            %><tr> 
                                                <td width="15%">ABSEN MACHINE <%=""+(machineNumbers[i])%></td>
                                                <td width="1%">:</td>
                                                <td width="12%"> 
                                                  <% 
                                                            Vector machine_value = new Vector(1,1);
                                                            Vector machine_key = new Vector(1,1); 
                                                            machine_value.add("0");
                                                            machine_key.add("...");
                                                            machine_value.add("1");
                                                            machine_key.add("OPEN COMM");
                                                            machine_value.add("2");
                                                            machine_key.add("CLOSE COMM");
                                                            String valSelect = "0";
                                                            if(mchAction.size()>0){
                                                                valSelect = (String)mchAction.get(i);
                                                            }                                                    %>
                                                  <%= ControlCombo.draw("MACHINE"+i,"formElemen",null, valSelect, machine_value, machine_key, "") %> 
                                                  </td>
                                                  <td width="72%">
                                                  <%
                                                    if(mchStatus.size()>0){
                                                        out.println(mchStatus.get(i));
                                                    }
                                                  %>      
                                                </td>
                                              </tr>
                                           <%
                                                }
                                            %>
                                            <% 
                                                    for(int i=0;i<machineNumbersCanteen.length;i++){
                                            %>                                                            
                                            <tr> 
                                                <td width="15%">CANTEEN MACHINE <%=""+(machineNumbersCanteen[i])%></td>
                                                <td width="1%">:</td>
                                                <td width="12%"> 
                                                  <% 
                                                            Vector machine_value = new Vector(1,1);
                                                            Vector machine_key = new Vector(1,1); 
                                                            machine_value.add("0");
                                                            machine_key.add("...");
                                                            machine_value.add("1");
                                                            machine_key.add("OPEN COMM");
                                                            machine_value.add("2");
                                                            machine_key.add("CLOSE COMM");
                                                            String valSelect = "0";
                                                            if(ctnAction.size()>0){
                                                                valSelect = (String)ctnAction.get(i);
                                                            }     
                                                    %>
                                                  <%= ControlCombo.draw("MACHINE_CANTEEN"+i,"formElemen",null, valSelect, machine_value, machine_key, "") 
                                                  %>
                                                  </td>
                                                  <td width="72%">
                                                  <%
                                                    if(ctnStatus.size()>0){
                                                        out.println(ctnStatus.get(i));
                                                    }
                                                  %> 
                                                   </td>
                                              </tr>
                                              <%
                                                }
                                            %>
                                              <tr> 
                                                <td width="15%">&nbsp; </td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="20%">&nbsp; </td>
                                                <td width="64%">&nbsp; </td>
                                              </tr>
                                            </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td colspan="4">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                  <table border="0" cellpadding="0" cellspacing="0" width="100">
                                                    <tr> 
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                                      <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="100" class="command" nowrap><a href="javascript:cmdSave()">Set Machine</a></td>
                                                    </tr> 
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
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
<!-- #EndEditable --> 
      </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

