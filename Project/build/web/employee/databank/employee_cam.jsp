<%-- 
    Document   : employee_cam
    Created on : 04-Jul-2016, 10:39:43
    Author     : GUSWIK
--%>

<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
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
             
            function cmdUpload(){
                document.frm.command.value="<%=Command.SAVE%>";
                document.frm.action="upload_pict_cam.jsp";
                document.frm.submit();
            }
            
            function cmdTry(){
                document.getElementById("my_camera").style.display="block";  
                document.getElementById("Take").style.display="block";
                document.getElementById("results").style.display="none";   
            }
        </script>


    </head>
    <body>


        <div class="content-main">
            <form name="frm" method ="post" action="">
                <div style="padding: 17px; background-color: #FFF;">
                    <div id="menu_utama">
                        Upload</div>
                    <div>&nbsp;
                        <table width="100%">
                            <tr>
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="emp_id" value="<%=oidEmployee%>">
                                <input type="hidden" name="datauri" id="results2" >

                                <td valign="top">
                                    
                                    <script type="text/javascript" src="../../styles/webcam/webcam.js"></script>   
                                    <!-- Configure a few settings and attach camera -->
                                    <script language="JavaScript">
                                        Webcam.set({
                                           width: 320,
                                            height: 240,

                                            // device capture size
                                            dest_width: 320,
                                            dest_height: 240,

                                            // final cropped size
                                            crop_width: 300,
                                            crop_height: 240,

                                            // format and quality
                                            image_format: 'jpeg',
                                            jpeg_quality: 90,
                                            force_flash: true
                                        });
		
                                        Webcam.attach( '#my_camera' );
                                    </script>
                                    <div class="divinput" id="results"></div></td>
                            </tr>
                            <tr>
                                <td colspan="2"><a class="btn" id="Take" style="width: 50px;" style="color:#FFF" href="javascript:take_snapshot()">Take</a></td>

                            </tr>
                        </table>
                    </div>
                </div>       

            </form>
        </div>
        <script language="JavaScript">
            function take_snapshot() {
                // take snapshot and get image data
                Webcam.snap( function(data_uri) {
                    // display results in page
                    document.getElementById('results').innerHTML = 
                        '<img src="'+data_uri+'"/> <br><br> <input type=button value="Try" class="btn" id="Try" onClick="cmdTry()"><input type=button value="Save" class="btn" onClick="cmdUpload()">';
                                     
                    document.getElementById('results2').value = 
                        data_uri;
                                
                    document.getElementById("my_camera").style.display="none";  
                    document.getElementById("Take").style.display="none";   
                    document.getElementById("results").style.display="block"; 
                    // Webcam.upload( data_uri, 'upload_pict_cam.jsp', function(code, text) {
                    //window.open("upload_pict_cam.jsp?datauri="+data_uri, "Up Pict","height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
                    //alert("resu");
                } );
            }
        </script>
    </body>
</html>
