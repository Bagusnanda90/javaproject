<%-- 
    Document   : presence_report_daily_n
    Created on : Oct 19, 2017, 2:36:00 PM
    Author     : IPAG
--%>

<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>
<%!    
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
    
    I_Atendance attdConfig = null;
               try {
                   attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
               } catch (Exception e) {
                   System.out.println("Exception : " + e.getMessage());
                   System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
               }
    
%>
<%@ include file = "../../main/checkuser.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Report Presence | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<%@ include file="../../template/css.jsp" %>
        <style>
            #rekapitulasi th {
                background-color: rgb(80, 183, 220);
                
            }
              
            
        </style>
    </head>
    
    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Daily Presence
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Reports</li>
                        <li class="active">Daily Presence</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                                
                            <div class="box-header with-border">
                                <h4 class="box-title">Search</h4>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                </div><!-- /.box-tools -->
                            </div>
                            <div class="box-body">
                                <form id="customSearch" class="form-horizontal">
                                    <input type="hidden" name="command" value="<%=Command.LIST%>">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="payroll" class="col-sm-2 control-label">Payroll</label>
                                            <div class="col-sm-6">
                                                <input type="hidden" name="FRM_FIELD_DATA_FOR" value="listData">
                                              <input type="text" class="form-control" id="payroll" name="emp_number" placeholder="Type Employee Number..">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="company" class="col-sm-2 control-label">Company</label>
                                            <div class="col-sm-6">
                                                <select name="company_id" id="company" class="form-control" data-placeholder="Choose Company..." data-replacement="#division" data-for="get_division">
                                                    <option value="">Chose Company...</option>
                                                    <%
                                                        Vector listCom = PstCompany.list(0, 0, "", "");
                                                        for (int i = 0; i < listCom.size(); i++) {
                                                            Company company = (Company) listCom.get(i);
                                                    %>
                                                            <option value="<%=company.getOID()%>"><%=company.getCompany()%></option>
                                                    <%
                                                        }
                                                    %>                                                                                                          
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="department" class="col-sm-2 control-label">Department</label>
                                            <div class="col-sm-6">
                                                <select name="department" id="department" class="form-control" multiple="multiple" data-placeholder="Choose Department..." data-replacement="#section" data-for="get_section">
                                                      
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="department" class="col-sm-2 control-label">Sub Section</label>
                                            <div class="col-sm-6">
                                                <select name="department" id="department" class="form-control" multiple="multiple" data-placeholder="Choose Department..." data-replacement="#section" data-for="get_section">
                                                      
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="date" class="col-sm-2 control-label">Date</label>
                                            <div class="col-sm-6">
                                                <div class='input-group'>
                                                    <input type='text' name='check_date_start'  id='dtfrom' class='form-control datepicker' >
                                                    <div class='input-group-addon'>To</div>
                                                    <input type='text' name='check_date_finish'  id='dtto' class='form-control datepicker' >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="view" class="col-sm-2 control-label">Schedule</label>
                                            <div class="col-sm-8">
                                                <label class="radio-inline">
                                                    <input type="radio" name="viewschedule" id="inlineRadio1" value="0" checked="checked"> Normal Schedule
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="viewschedule" id="inlineRadio2" value="1"> With Status
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="viewschedule" id="inlineRadio3" value="2"> With Reason
                                                </label>
                                            </div>
                                        </div>
                                       
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="name" class="col-sm-2 control-label">Full Name</label>
                                            <div class="col-sm-6">
                                              <input type="text" class="form-control" id="name" name="full_name">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="division" class="col-sm-2 control-label">Division</label>
                                            <div class="col-sm-6">
                                                <select name="division_id" id="division" class="form-control" multiple="multiple" data-placeholder="Choose Division..." data-replacement="#department" data-for="get_department">
                                                <option value="" disabled>Select Division</option>
                                                      
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="section" class="col-sm-2 control-label">Section</label>
                                            <div class="col-sm-6">
                                                <select name="section" id="section" class="form-control" multiple="multiple" data-placeholder="Choose Section...">
                                                      
                                                </select>
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label for="view" class="col-sm-2 control-label">Emp.Category</label>
                                            <div class="col-sm-8">
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio1" value="option1">  Kontrak 1 Tahun 
                                                </label>
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio2" value="option2">  Tetap
                                                </label>
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio3" value="option3">  Probation
                                                </label>
                                                <br/>
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio3" value="option3">  Kontrak 2 Tahun
                                                </label>
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio3" value="option3">  Kontrak 3 Tahun
                                                </label>
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="inlineRadioOptions" id="inlineRadio3" value="option3">  Kontrak
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="view" class="col-sm-2 control-label">Resigned</label>
                                            <div class="col-sm-8">
                                                <label class="radio-inline">
                                                    <input type="radio" name="statusResign" id="inlineRadio1" value="0" checked="checked"> No
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="statusResign" id="inlineRadio2" value="1"> Yes
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="statusResign" id="inlineRadio3" value="2"> All
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="view" class="col-sm-2 control-label">Only DW</label>
                                            <div class="col-sm-8">
                                                <label class="radio-inline">
                                                    <input type="radio" name="OnlyDw" id="0" value="0" checked="checked"> No
                                                </label>
                                                <label class="radio-inline">
                                                    <input type="radio" name="OnlyDw" id="1" value="1"> Yes
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <button id="search" class="btn btn-primary" type="submit"><i class='fa fa-filter'></i> Filter</button>
                                    </div>
                                </form>
                            </div>
                    </div>
                        <div class='box box-primary'>
                            <div class='box-header'>
                                
                            </div>
                            <div class="box-body">
                                <div id="table">
                                    
                                </div>
                            </div>
                            <div class='box-footer'>
                                
                            </div>
                        </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section><!-- /.content -->
                        </div>
           
            <%@include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
            
        <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
        <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
        <script type="text/javascript">
        $(document).ready(function(){
            $("[data-widget='collapse']").click(function() {
                //Find the box parent........
                var box = $(this).parents(".box").first();
                //Find the body and the footer
                var bf = box.find(".box-body, .box-footer");
                if (!$(this).children().hasClass("fa-plus")) {
                    $(this).children(".fa-minus").removeClass("fa-minus").addClass("fa-plus");
                    bf.slideUp();
                } else {
                    //Convert plus into minus
                    $(this).children(".fa-plus").removeClass("fa-plus").addClass("fa-minus");
                    bf.slideDown();
                }
            });
            
            var approot = $("#approot").val();
	    var command = $("#command").val();
	    var dataSend = null;
	    
	    var oid = null;
	    var dataFor = null;

	    //FUNCTION VARIABLE
	    var onDone = null;
	    var onSuccess = null;
	    var callBackDataTables = null;
	    var iCheckBox = null;
	    var addeditgeneral = null;
            var docFlow = null;
	    var areaTypeForm = null;
	    var deletegeneral = null;
            var addeditflow = null;
	    
	    //MODAL SETTING
	    var modalSetting = function(elementId, backdrop, keyboard, show){
		$(elementId).modal({
		    backdrop	: backdrop,
		    keyboard	: keyboard,
		    show	: show
		});
	    };
            
            function datePicker(contentId, formatDate){
		$(contentId).datepicker({
                    todayHighlight : true,
		    format : formatDate
                    
		});
		$(contentId).on('changeDate', function(ev){
		    $(this).datepicker('hide');
		});
	    }
	    
	    var getDataFunction = function(onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType){
		/*
		 * getDataFor	: # FOR PROCCESS FILTER
		 * onDone	: # ON DONE FUNCTION,
		 * onSuccess	: # ON ON SUCCESS FUNCTION,
		 * approot	: # APPLICATION ROOT,
		 * dataSend	: # DATA TO SEND TO THE SERVLET,
		 * servletName  : # SERVLET'S NAME,
		 * dataType	: # Data Type (JSON, HTML, TEXT)
		 */
		$(this).getData({
		   onDone	: function(data){
		       onDone(data);
		   },
		   onSuccess	: function(data){
			onSuccess(data);
		   },
		   approot	: approot,
		   dataSend	: dataSend,
		   servletName	: servletName,
		   dataAppendTo	: dataAppendTo,
		   notification : notification,
		   ajaxDataType	: dataType
		});
	    }
	    
            
            
            $('.datepicker').datepicker({
                todayHighlight: true,
                format: 'yyyy-mm-dd'
            });
            
            var config = {
                '.chosen-select'           : {},
                '.chosen-select-deselect'  : {allow_single_deselect:true},
                '.chosen-select-no-single' : {disable_search_threshold:10},
                '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
                '.chosen-select-width'     : {width:"100%"}
            }
            for (var selector in config) {
                $(selector).chosen(config[selector]);
            }
            $("select").chosen();
            
            $("#company").change(function(){
                var oid = $(this).val();
                dataFor = $(this).data("for");
                var target = $(this).data("replacement");
                var dataSends = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "company": oid,
                    "command": command
                }
                var onDones = function(data){
                    $(target).chosen("destroy");
                    $(target).html(data.FRM_FIELD_HTML).chosen();
                };
                var onSuccesses = function(data){

                };
                getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxRekapitulasiAbsensi", target, false, "json");
            });
            
            $("#division").change(function(){
                var oid = $(this).val();
                dataFor = $(this).data("for");
                var target = $(this).data("replacement");
                var dataSends = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "division": oid,
                    "command": command
                }
                var onDones = function(data){
                    $(target).chosen("destroy");
                    $(target).html(data.FRM_FIELD_HTML).chosen();
                };
                var onSuccesses = function(data){

                };
                getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxRekapitulasiAbsensi", target, false, "json");
            });
            
            $("#department").change(function(){
                var oid = $(this).val();
                dataFor = $(this).data("for");
                var target = $(this).data("replacement");
                var dataSends = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "department": oid,
                    "command": command
                }
                var onDones = function(data){
                    $(target).chosen("destroy");
                    $(target).html(data.FRM_FIELD_HTML).chosen();
                };
                var onSuccesses = function(data){

                };
                getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxRekapitulasiAbsensi", target, false, "json");
            });
            
            
            
            function sendData(url, datasend, onDone, onSuccess){
                //alert("WORK");
                 $.ajax({
                     type    : "GET",
                     data    : datasend,
                     url     : url,
                     success : function(data){
                         onSuccess(data);
                     },
                     error: function(xhr,err){
                         alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
                         alert("responseText: "+xhr.responseText);
                     }
                 }).done(function(data){
                     onDone(data);
                 });

             }
            
            $("form#customSearch").submit(
                    function(){
                
                var onDones = function(data){
                    $("#table").empty();
                    $("#table").html(data).fadeIn("medium");
                    gridviewScroll();
                };
                var onSuccesses = function(data){

                };
                var dataSends = $(this).serialize();
                $("#table").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                sendData("<%= approot %>/report/presence/ajax_presence_daily.jsp", dataSends, onDones, onSuccesses);
                return false;
            });
            
            
            
            <%
              int freesize = 7;
            %>
	    function gridviewScroll() {
                    var gridWidth = $(window).width()-100; 
                    var gridHeight = $(window).height(); 
                    gridView1 = $('#GridView1').gridviewScroll({
                        width: gridWidth,
                        height: 500,
                        railcolor: "##33AAFF",
                        barcolor: "#CDCDCD",
                        barhovercolor: "#606060",
                        bgcolor: "##33AAFF",
                        freezesize: <%=freesize%>,
                        arrowsize: 30,
                        varrowtopimg: "<%=approot%>/images/arrowvt.png",
                        varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                        harrowleftimg: "<%=approot%>/images/arrowhl.png",
                        harrowrightimg: "<%=approot%>/images/arrowhr.png",
                        headerrowcount: 2,
                        railsize: 16,
                        barsize: 15
                    });
                }
            
	});
      </script>
    </body>
</html>
