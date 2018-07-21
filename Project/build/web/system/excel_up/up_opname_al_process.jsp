<% 
/* 
 * Page Name  	:  
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: artha
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language ="java"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.System"%>
<%@ page import="java.io.ByteArrayInputStream"%>  
<%@ page import="java.io.IOException"%>

<%@ page import="com.dimata.qdep.form.*"%>
<%@ page import="com.dimata.gui.jsp.*"%>
<%@ page import="com.dimata.util.Command" %>
<%@ page import="com.dimata.util.Excel"%>
<%@ page import="com.dimata.util.Formater"%>
<%@ page import="com.dimata.util.blob.TextLoader"%>
<%@ page import="org.apache.poi.hssf.usermodel.*"%>
<%@ page import="com.dimata.system.entity.system.*"%>
<%@ page import="com.dimata.harisma.session.leave.SessOpnameAL"%>
<%@ page import = "com.dimata.harisma.entity.leave.I_Leave" %>
<%@ page import = "com.dimata.system.entity.system.PstSystemProperty" %>

<!--package hris -->


<%@ include file = "../../main/javainit.jsp"%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_EMP_LEAVE_OPNAME, AppObjInfo.OBJ_EMP_LEAVE_AL_OPNAME); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
//boolean privSumbit=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_SUBMIT));
%>

<!-- Jsp Block -->
<%
   boolean isValidData = true;
   FRMHandler fRMHandler = new FRMHandler();
   int iCommand = FRMQueryString.requestCommand(request);
   int iDate = FRMQueryString.requestInt(request,"opnamedate_dy");
   int iMon = FRMQueryString.requestInt(request,"opnamedate_mn");
   int iYear = FRMQueryString.requestInt(request,"opnamedate_yr");
   
   Date opnameDate = new Date();
   System.out.println("Command : "+iCommand +" VS "+Command.RESET);
   if(iCommand == Command.RESET){
       Date dTemp = new Date(iYear-1900, iMon-1, iDate);
	opnameDate = dTemp;
   }

   final int numberOfCol = 7;
   
%>

<%!

    public Vector drawData(Vector vOpnameAl,Date opnameDate,boolean status){
        //Dibutuhkan dalam proses perhutungan entiled AL dan earned Al YTD
        Vector vListData = new Vector();
        String strHtml = "";
        
        
        int ERR_NAME_NOT_MATCH = 0;
        int ERR_PAYROLL_NUMBER_NOT_VALID = 1;
        String[] errStatus = {
            "Name not match",
            "Payroll number not valid"
        };

   //     int configAL = com.dimata.harisma.entity.attendance.PstAlStockManagement.AL_PERIOD_COMMENCING;
       // SPLeaveConfig objSPLeaveConfig = SPLeaveConfig.getInstant();
        
            I_Leave leaveConfig = null;           
            try {
                leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
            }
            catch(Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
        
        int configAL = leaveConfig.getALEntitleBy();
   /*      try{
           configAL = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_ANUAL_ENTITLE_PERIOD"));
        } catch(Exception exc){

        }

       int defALEntitle = 12; // default biasa 12
        try{
           defALEntitle = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_ANUAL_ENTITLE_NUMBER"));
        } catch(Exception exc){

        }
     */   
        								
        strHtml += "<table class=\"listgen\" cellpadding=\"1\" cellspacing=\"1\"><tr>";

        // Jika ukuran vector yang menyimpan data dari excel lebih dari nol, maka proses
        if(vOpnameAl!=null && vOpnameAl.size()>0)
        {
            int maxV = vOpnameAl.size();
            
            // create header/title
	     Vector vTitle = (Vector)vOpnameAl.get(0);
            for(int numTitle=0; numTitle<vTitle.size(); numTitle++) 
            {											
                    strHtml += "<td class=\"listgentitle\">"+vTitle.elementAt(numTitle)+"</td>";
            }
            strHtml += "<td align=\"center\" class=\"listgentitle\">STATUS</td>";
            strHtml += "</tr>";																			


            // iterasi dilakukan mulai indeks ke (numcol) karena baris pertama dari schedule excel adalah JUDUL/TITLE								
            int count = 1;
            //Ini untuk menampung no payroll employee
            String empPayroll="";
            Employee objEmployee = new Employee();
            Date dCommencingDate= new Date();
            int entitleAL=0;
            int earnedAL=0;
            int errCode = -1;
            for(int i=1; i<maxV; i++) 
            {		
                errCode = -1;
                Vector vData = new Vector();
                vData = (Vector)vOpnameAl.get(i);					
                for(int j=0; j<vTitle.size();j++){
		      switch (j) 
		      {
		      case 0 : // kalo sisanya 0 ==> pada kolom I (data number)
				strHtml += "<tr>";
				strHtml += "<td class=\"listgensell\">"+count+"</td>";
				count ++;
				break;

		      case 1 : // kalo sisanya 1 ==> pada kolom II (employee payroll)
				empPayroll=(String)vData.elementAt(j);
				strHtml += "<td class=\"listgensell\"><input type=\"hidden\" name=\"empnumber\" value=\"" + vData.elementAt(j) + "\">"+vData.elementAt(j)+"</td>";												
				break;

		      case 2 : // kalo sisanya 2 ==> pada kolom III (employee name)
			   String strEmployeeName = "";
			   strEmployeeName = (String)vData.elementAt(j);
			   objEmployee = PstEmployee.getEmployeeByNum(empPayroll.trim());
			   if( objEmployee.getFullName()!=null && !objEmployee.getFullName().equals("")){
				if(!strEmployeeName.equals(objEmployee.getFullName())){
				    strEmployeeName = "<font color=\"#04ae88\">"+objEmployee.getFullName()+"</font>";
                                    errCode = ERR_NAME_NOT_MATCH;
                                }
			   }else{

				//Status data menjadi tidak valid ketika
				//terdapat data yang tidak sesuai
                                errCode = ERR_PAYROLL_NUMBER_NOT_VALID;
				status = false;
				strEmployeeName = "<font color=\"#FF0000\"> "+strEmployeeName+"</font>";
			   }
			   strHtml += "<td class=\"listgensell\"><input type=\"hidden\" name=\"empid\" value=\"" + String.valueOf(objEmployee.getOID()) + "\">"+strEmployeeName+"</td>";
			   break;

		      case 3 : // kalo sisanya 3 ==> pada kolom IV (last year quantity)
				strHtml += "<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"lastyearqty\" value=\"" + vData.elementAt(j) + "\">"+vData.elementAt(j)+"</td>";
				break;

		      case 4 : // kalo sisanya 4 ==> pada kolom V (entitled quantity)
				//strHtml += "<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"entitledqty\" value=\"" + vData.elementAt(j) + "\">"+vData.elementAt(j)+"</td>";
                                
				try{
				    entitleAL=Integer.parseInt((String) vData.elementAt(j));

				    if(configAL==leaveConfig.AL_ENTITLE_BY_COMMENCING){
					 objEmployee = PstEmployee.getEmployeeByNum(empPayroll.trim());
					 if( objEmployee.getCommencingDate()!=null)  {
					     dCommencingDate = new Date(objEmployee.getCommencingDate().getTime());
					     dCommencingDate.setYear(opnameDate.getYear());
					     // Jika waktu commencing date lebih kecil dari waktu terkini
					     // atau dengan kata lain, earned ytd akan muncul jika waktu commencing
					     // pegawai telah lewat dari waktu sekarang
                                             Level objLevel = new Level();
                                             objLevel = PstLevel.fetchExc(objEmployee.getLevelId());
                                             EmpCategory objEmpCategory = new EmpCategory();
                                             objEmpCategory = PstEmpCategory.fetchExc(objEmployee.getEmpCategoryId());
					     if(dCommencingDate.getTime()< opnameDate.getTime()){
						  entitleAL=leaveConfig.getALEntitleAnualLeave(objLevel.getLevel(),objEmpCategory.getEmpCategory());
					     }
					 }
				    }else{
                                    
                                    }
				    strHtml += "<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"entitledqty\" value=\"" +entitleAL+ "\">"+entitleAL+"</td>";
				} catch(Exception exc){
				    System.out.println("webPage.system.up_opname_al_process :: ERROR ::::::: "+exc.toString());
				}	
                                break;

		      case 5 : // kalo sisanya 5 ==> pada kolom VI (earned ytd qty)

				
				try{
				    earnedAL=Integer.parseInt((String) vData.elementAt(j));

				    if(configAL==leaveConfig.AL_ENTITLE_BY_COMMENCING){
					 earnedAL = entitleAL;
				    }else{
                                        if(entitleAL>0){
                                            earnedAL = opnameDate.getMonth()+1;
                                            if(opnameDate.getDate()>dCommencingDate.getDate()){
                                                earnedAL += 1;
                                            }
                                         }
                                    }
				    strHtml += "<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"earnedytd\" value=\"" +earnedAL+ "\">"+earnedAL+"</td>";
				} catch(Exception exc){
				    System.out.println("webPage.system.up_opname_al_process :: ERROR ::::::: "+exc.toString());
				}	

				break;

		      case 6 : // kalo sisanya 6 ==> pada kolom VI (al stock)
				String cellVal="0";
				try{ 
				    cellVal=(String)vData.elementAt(j);
				    } catch ( Exception exc){

				    }
				//System.out.println("::::::::: BERHASIL DITAMPILKAN :::::::::::::::XXXXXXXXXXXXXXXXXXXXXX");
				strHtml += "<td align=\"right\" class=\"listgensell\"><input type=\"hidden\" name=\"takenytd\" value=\"" + cellVal + "\" >"+cellVal+"</td>";
                                if(errCode>-1){
                                    strHtml += "<td align=\"center\" class=\"listgensell\"><input type=\"hidden\" name=\"err\" value=\"\" > <font color=\"#FF0000\"> "+errStatus[errCode]+"</font></td>";
                                }else{
                                    strHtml += "<td align=\"center\" class=\"listgensell\"><input type=\"hidden\" name=\"err\" value=\"\" > <font color=\"#00bb61\">OK</font></td>";
				}
                                strHtml += "</tr>";													
				break;

		      default :
				cellVal="0";
				try{ 
				    cellVal=(String)vData.elementAt(j);
				    } catch ( Exception exc){

				    }
				strHtml += "<td class=\"listgensell\">" + cellVal+ "</td>";
				break;
		      }
                }
            }										
        }	
        strHtml += "</table>";
	 vListData.add(strHtml);
	 vListData.add(String.valueOf(status));
        
        return vListData;
    }

%>

<!-- end of Jsp Block-->

<!-- Java Script-->
        <script language="JavaScript">
            function cmdSave(status){
		  document.frmupload.command=<%=String.valueOf(Command.SAVE)%>;
                if(status == true){
                    document.frmupload.action="up_opname_al_save.jsp";
                    document.frmupload.submit();
                }else{
                    var msg = "Has invalid data...!\n"
                             +"are sure to continue this process?";
                    if(confirm(msg)){
                       document.frmupload.action="up_opname_al_save.jsp";
                       document.frmupload.submit(); 
                    }
                }                
            }
	     
            function cmdReset(){
		  document.frmupload.command.value="<%=String.valueOf(Command.RESET)%>";
                document.frmupload.action="up_opname_al_process.jsp";
                document.frmupload.submit();        
            }
            
        </script>
<!-- End of java script-->

<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
	 <!-- #BeginEditable "doctitle" --> 
	 <title>HARISMA - Upload AL Stock</title>
	 
	 <!-- #EndEditable --> 
	 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
	 <!-- #BeginEditable "styles" --> 
	 <link rel="stylesheet" href="../../styles/main.css" type="text/css">
	 <!-- #EndEditable -->
	  <!-- #BeginEditable "stylestab" --> 
	 <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
	 <!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
	  <!-- #EndEditable -->
    </head> 
    
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
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
					 <td height="20">
					 <font color="#FF6600" face="Arial"><strong>
					     <!-- #BeginEditable "contenttitle" --> 
					     Uploader &gt; Leave Management &gt; AL Stock
					     <!-- #EndEditable --> 
					 </strong></font>
					 </td>
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

			   <form name="frmupload" method="post" action="">
			   &nbsp;&nbsp;<b>List employee's AL Stock</b>
				
				<input name="command" type="hidden" value="<%=String.valueOf(iCommand)%>">
				<table class="listarea"><tr><td>
				     <%
                                // upload file sesuai dengan yang dipilih oleh "browse"
                                //String a = request.getParameter("file"); 
					 Vector vOpnameData = new Vector();
				    if(iCommand != Command.RESET){
                                     TextLoader uploader = new TextLoader();
					 Vector v = new Vector();
                                    uploader.uploadText(config, request, response);
                                    Object obj = uploader.getTextFile("file");
					 vOpnameData = SessOpnameAL.readObjToVecor(obj, numberOfCol);
                                }else{
					 vOpnameData = (Vector)session.getValue("vData");
                                }
                                    String strList = "";
                                    Vector vTemp = new Vector();
                                    vTemp = drawData(vOpnameData, opnameDate, isValidData);
                                    strList = (String)vTemp.get(0);
                                    isValidData = Boolean.parseBoolean((String)vTemp.get(1));
                                    out.println(strList);
                                    try{
					     session.putValue("vData", vOpnameData);    
                                    }catch(Exception ex){}
				   %>
				   
				</td></tr></table>
				 <% if(privSubmit){
					 out.println("<br>&nbsp;&nbsp;<b>Opname Date : </b>");																		
					 out.println("<table><tr><td>");
					 out.println("&nbsp;"+ControlDate.drawDate("opnamedate", opnameDate, "formElemen", 1, -2));
					 //strHtml += "<input type=\"submit\" value=\"Upload AL\">";									
					 out.println("<a href=\"javascript:cmdReset()\" class=\"command\" style=\"text-decoration:none\">Reload Data</a>");
					 out.println("<br><a href=\"javascript:cmdSave("+String.valueOf(isValidData)+")\" class=\"command\" style=\"text-decoration:none\">Save AL Data</a>");
                                    out.println("</td></tr></table>");
				    } %>
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
					 <tr> 
<td colspan="2" height="20" <%=bgFooterLama%>> 
<!-- #BeginEditable "footer" --> 
<%@ include file = "../../main/footer.jsp" %>
<!-- #EndEditable --> </td>
</tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
