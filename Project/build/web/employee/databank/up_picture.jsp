<%-- 
    Document   : up_picture
    Created on : 11-Jul-2016, 11:14:35
    Author     : Acer
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.util.blob.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->

<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<!DOCTYPE html>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
String pictName =  FRMQueryString.requestString(request, "pict");

    String approot = request.getContextPath();

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstEmpPicture.fieldNames[PstEmpPicture.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;

CtrlEmpPicture ctrlEmpPicture = new CtrlEmpPicture(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpPicture = new Vector(1,1);

SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
String pictPath = "";
try{
        Employee employee = new Employee();
        employee= PstEmployee.fetchExc(oidEmployee);
	pictPath = sessEmployeePicture.fetchImageEmployee(employee.getEmployeeNum());// fetchImagePeserta(oidEmployee);

}catch(Exception e){
	System.out.println("err."+e.toString());
}
	System.out.println("pictPath sebelum..............."+pictPath);

/*switch statement */
iErrCode = ctrlEmpPicture.action(iCommand , oidEmpPicture, oidEmployee);
/* end switch*/
FrmEmpPicture frmEmpPicture = ctrlEmpPicture.getForm();

EmpPicture empPicture = ctrlEmpPicture.getEmpPicture();
msgString =  ctrlEmpPicture.getMessage();


/*count list All CareerPath*/
int vectSize = PstEmpPicture.getCount(whereClause);

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlEmpPicture.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/
/* get record to display */
listEmpPicture = PstEmpPicture.list(start,recordToGet, whereClause , "");


long oidDepartment = 0;
if(oidEmployee != 0){
	Employee employee = new Employee();
	try{
		 employee = PstEmployee.fetchExc(oidEmployee);
		 oidDepartment = employee.getDepartmentId();
	}catch(Exception exc){
		 employee = new Employee();
	}
}
//listSection = PstSection.list(0,500,PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+ " = "+oidDepartment,"SECTION");
Vector listSection = PstSection.list(0,0,"","SECTION");
/*handle condition if size of record to display = 0 and start > 0 	after delete*/


Vector vectPict = new Vector(1,1);
 vectPict.add(""+oidEmployee);

session.putValue("SELECTED_PHOTO_SESSION", vectPict);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cam Capture</title>
        <style type="text/css">
            body { font-family: Helvetica, sans-serif; }
            h2, h3 { margin-top:0; }
            form { margin-top: 15px; }
            form > input { margin-right: 15px; }
            #results { float:top; margin:10px; padding:10px;   }
            #my_camera { float:top; margin:10px; padding:10px; }
        </style>
        <style type="text/css">
            .tblStyle {border-collapse: collapse;}
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; font-weight: bold; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}

            #btn {
                background: #3498db;
                border: 1px solid #0066CC;
                border-radius: 3px;
                font-family: Arial;
                color: #ffffff;
                font-size: 12px;
                padding: 3px 9px 3px 9px;
            }

            #btn:hover {
                background: #3cb0fd;
                border: 1px solid #3498db;
            }

            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}

            body {background-color: #EEE;}
            .header {

            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }

            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 15px 7px 15px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }

            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}

            .btn-small-e {
                font-family: sans-serif;
                text-decoration: none;
                padding: 3px 5px; 
                background-color: #92C8E8; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-e:hover { background-color: #659FC2; color: #FFF;}

            .btn-small-x {
                font-family: sans-serif;
                text-decoration: none;
                padding: 3px 5px; 
                background-color: #EB9898; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-x:hover { background-color: #D14D4D; color: #FFF;}

            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}

            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }

            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }

            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }

            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                font-size: 12px;
                padding: 7px 0px 8px 15px;
                background-color: #FF6666;
                color: #FFF;
                visibility: hidden;
            }
            #btn-confirm-y {
                padding: 7px 15px 8px 15px;
                background-color: #F25757; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
            #btn-confirm-n {
                padding: 7px 15px 8px 15px;
                background-color: #E34949; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            .caption {font-weight: bold; padding-bottom: 3px;}
            .divinput {margin-bottom: 7px;}
            #payroll_num {
                background-color: #DEDEDE;
                border-radius: 3px;
                font-family: Arial;
                font-weight: bold;
                color: #474747;
                font-size: 12px;
                padding: 5px 11px 5px 11px;
                cursor: pointer;
            }
        </style>
        <script lang="JavaScript">
        function cmdSave(){
	document.frmcareerpath.command.value="<%=Command.SAVE%>";
	document.frmcareerpath.prev_command.value="<%=prevCommand%>";
	document.frmcareerpath.action="up_pict_process.jsp";
	document.frmcareerpath.submit();
	}
        </script>


    </head>
<body>
<div class="content-main">
           <form name="frmcareerpath" method ="post"  enctype="multipart/form-data" action="up_pict_process.jsp">
                <div style="padding: 17px; background-color: #FFF;">
                    <div id="menu_utama">
                        Upload Picture</div>
                    <div>&nbsp;
                        <table width="100%">
                            <tr>
                                
                                <input type="hidden" name="command" value="<%=iCommand%>">
                                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="emp_picture_oid" value="<%=oidEmpPicture%>">
                                <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
                                <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                <input type="hidden" name="<%=FrmCareerPath.fieldNames[FrmCareerPath.FRM_FIELD_DEPARTMENT_ID]%>" value="<%=oidDepartment%>">
                                
                                            <td valign="top">
                                                <table width="48%" height="158" border="0">
                                                    <tr> 

                                                      <td height="154" valign="top">  <%
                                                          if(pictPath!=null && pictPath.length()>0)
                                                          {
                                                                out.println("<img width=\"250\"  src=\""+approot+"/"+pictPath+"\">");
                                                          }
                                                          else{
                                                                 %>
                                                                        <img width="250"  src="<%=approot%>/imgcache/no_photo.JPEG"></image>
                                                                 <%

                                                          }	%>  </td>
</tr>
                                                         </table>
                                    <div class="caption">
                                        <div class="form-group">
                                            <label>( Click browse... 
                                                  if you want to add/edit 
                                                  picture )</label><br/><br/>
                                            <input type="file" name="pict" size="60" height="100">
                                        </div>
                                    </td>
                                    
                                    </div>
                                   
                            </tr>
                            <tr>
                                <td colspan="2"><a class="btn" id="save" style="width: 50px;" style="color:#FFF" href="javascript:cmdSave()">Save</a></td>

                            </tr>
                        </table>
                    </div>
                </div>       

            </form>
        </div>
</body>
</html>
    
    