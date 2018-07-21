<%-- 
    Document   : asset_inventory
    Created on : 03-Feb-2017, 15:31:45
    Author     : Gunadi
--%>

<%@page import="com.dimata.harisma.entity.attendance.Presence"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcPresence"%>
<%@page import="com.dimata.harisma.form.attendance.FrmPresence"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpAssetInventoryItem"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpAssetInventory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>
<%
    String url= request.getParameter("menu");
    String urlC = request.getRequestURL().toString();
        String baseURL = urlC.substring(0, urlC.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Presence List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .finger{
                width:20%; 
                height:90px;
                padding : 2%;
                float:left;
             }
            .finger_spot{
                width:90%;
                height: 80px;
                background-color :#e5e5e5;
                border : thin solid #c5c5c5;
                font-size: 14px;
                font-family:calibri;
                text-align:center;
                color :#FFF;
                border-radius: 3px;
            }

            .green{
               background-color : #5CB85C;
               border : thin solid #4CAE4C;
            }
        </style>
        <script src="../../styles/ckeditor/ckeditor.js"></script>
        <%@ include file="../../template/css.jsp" %>
        
        
    </head>
    <body class="hold-transition skin-blue fixed sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
         <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Presence List
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Presence List</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary collapsed-box">
                                <div class="box-header with-border">
                                    <h4 class="box-title">Filter</h4>
                                    <div class="box-tools pull-right">
                                        <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
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
                                                  <input type="text" class="form-control" id="payroll" name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_EMPNUMBER] %>" placeholder="Type Employee Number..">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="name" class="col-sm-2 control-label">Full Name</label>
                                                <div class="col-sm-6">
                                                  <input type="text" class="form-control" id="name" name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_FULLNAME] %>" placeholder="Type Employee Name..">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="company" class="col-sm-2 control-label">Department</label>
                                                <div class="col-sm-6">
                                                    <select name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_DEPARTMENT]%>" id="department" class="form-control chosen-select" data-placeholder="Choose Department..." data-replacement="#section" data-for="getSection">
                                                        <option value="">Chose Department...</option>
                                                        <%
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DIVISION_ID "); 
                                                            long divisionId = 0;
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                Department dept = (Department) listDept.get(i);
                                                                Division div = new Division();
                                                                Company comp = new Company();
                                                                try {
                                                                    div = PstDivision.fetchExc(dept.getDivisionId());
                                                                } catch (Exception exc){
                                                                    System.out.println(exc.toString());
                                                                }
                                                                
                                                                try {
                                                                    comp = PstCompany.fetchExc(div.getCompanyId());
                                                                } catch (Exception exc){
                                                                    System.out.println(exc.toString());
                                                                }
                                                                
                                                                if (divisionId != dept.getDivisionId()){
                                                                    if (i!=0){
                                                                        %> </optgroup> <%
                                                                    }
                                                                    %> <optgroup label="<%=comp.getCompany()%> - <%=div.getDivision()%>"> <%
                                                                    divisionId = dept.getDivisionId();
                                                                }
                                                        %>
                                                                
                                                                <option value="<%=dept.getOID()%>"><%=dept.getDepartment()%></option>
                                                        <%
                                                            }
                                                        %>       
                                                        </optgroup>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="section" class="col-sm-2 control-label">Section</label>
                                                <div class="col-sm-6">
                                                    <select name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_SECTION]%>" id="section" class="form-control chosen-select" data-placeholder="Choose Section...">

                                                    </select>
                                                </div>
                                            </div>        
                                            <div class="form-group">
                                                <label for="section" class="col-sm-2 control-label">Position</label>
                                                <div class="col-sm-6">
                                                    <%
                                                            Vector position_value = new Vector(1,1);
                                                            Vector position_key = new Vector(1,1);
                                                            position_value.add("0");
                                                            position_key.add("select ...");                                                       
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    position_key.add(pos.getPosition());
                                                                    position_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_POSITION],"form-control chosen-select", null, "", position_value, position_key,"") %>
                                                </div>
                                            </div>         
                                            <div class="form-group">
                                                <label for="section" class="col-sm-2 control-label">Status Data</label>
                                                <%
                                                    Vector status_value = Presence.getStatusIndexString();
                                                    Vector status_key = Presence.getStatusAttString();                                                            
                                                    ControlCheckBox  ctrlChkbox= new ControlCheckBox();
                                                %>
                                                <div class="col-sm-10">
                                                    <%= ctrlChkbox.draw( FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_PERIOD_STATUS],status_value, status_key, new Vector() ) %> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="section" class="col-sm-2 control-label">Periode</label>
                                                <div class="col-sm-6">
                                                    <input type="checkbox" name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_PERIOD_CHECKED]%>"  checked value="1"><i><font color="#FF0000">select 
                                                        all period</font></i> 
                                                </div>
                                            </div>  
                                            <div class="form-group">
                                                <label for="section" class="col-sm-2 control-label"></label>
                                                <div class="col-sm-6">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">From</span>
                                                        <input type="text" id="from" name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_DATEFROM_STR]%>" class="form-control datepicker" value="<%=Formater.formatDate(new Date(), "yyyy-MM-dd")%>"> 
                                                        <span class="input-group-addon">To</span>
                                                        <input type="text" id="to" name="<%=FrmSrcPresence.fieldNames[FrmSrcPresence.FRM_FIELD_DATETO_STR]%>" class="form-control datepicker" value="<%=Formater.formatDate(new Date(), "yyyy-MM-dd")%>">
                                                    </div>
                                                </div>
                                            </div>   
                                        </div>
                                        <div class="col-md-12">
                                            <div class="box-footer">
                                                <button id="search" class="btn btn-primary" type="submit"><i class='fa fa-filter'></i> Filter</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                        </div>
                        <div class='box box-primary'>
                            <div class='box-header'>
                                <div class="col-md-11" align="left">
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showPresenceListForm">
                                    <i class="fa fa-plus"></i> Add Presence Date Time
                                </button>
                                <button class="btn btn-success btnaddgeneral" data-oid="0" data-for="showMultipleAttendance">
                                    <i class="fa fa-plus"></i> Multiple Set Attendance Status and Reason
                                </button>
                                <button class="btn btn-warning btnaddgeneral" data-oid="0" data-for="showGenerateSchPh">
                                    <i class="fa fa-edit"></i> Re Generate Schedule PH
                                </button>
                                </div>
                                <div class="col-md-1" align="right">
                                <button class="btn btn-danger btndeleteEmpAsset" data-for="empAsset">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                            </div>
                                
                            </div>
                            <div class="box-body">
                                <div id="handOverAssetElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Payroll Number</th>
                                                <th>Full Name</th>
                                                <th>Presence Date Time</th>
                                                <th>Status</th>
                                                <th>Analyzed</th>
                                                <th>Manual</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class='box-footer'>
                                
                            </div>
                            
                        </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
       
       
        <script type="text/javascript">
	$(document).ready(function(){
            
             $("#attendancemenu").addClass("treeview active");
              $("#manual_registration").addClass("treeview active");
            
            $(".chosen-select").chosen();
            
	    //SET ACTIVE MENU
	    var activeMenu = function(parentId, childId){
		$(parentId).addClass("active").find(".treeview-menu").slideDown();
		$(childId).addClass("active");
	    }

	    activeMenu("#masterdata", "#docmaster");
	    
	    
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
            
            datePicker(".datepicker", "yyyy-mm-dd");
	    
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
	    
	    //SHOW ADD OR EDIT FORM
	    addeditgeneral = function(elementId){
		$(elementId).click(function(){
		    $("#addeditgeneral").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
                    var oidMaster = $(this).data('oidmaster');
		    $("#generalform #generaldatafor").val(dataFor);
                    $("#generalform #oid").val(oid);
		    $("#generalform #oidAssetInventory").val(oid);
                    var servletName;
		    //SET TITLE MODAL
                    if(dataFor=="showPresenceListForm"){
                            if(oid != 0){
                                $(".addeditgeneral-title").html("Edit Presence");
                                if ($(window).width() > 480) {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                }
                            }else{
                                $(".addeditgeneral-title").html("Add Presence");
                                if ($(window).width() > 480) {
                                    $("#addeditgeneral .modal-dialog").css("width", "30%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "30%");
                                }
                            }
                    }
                    if(dataFor=="showMultipleAttendance"){
                            if(oid != 0){
                                $(".addeditgeneral-title").html("Edit Presence");
                                if ($(window).width() > 480) {
                                    $("#addeditgeneral .modal-dialog").css("width", "40%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "40%");
                                }
                            }else{
                                $(".addeditgeneral-title").html("Multiple Set Attendance Status and Reason");
                                if ($(window).width() > 480) {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                }
                            }
                    }
                     if(dataFor=="showGenerateSchPh"){
                            if(oid != 0){
                                $(".addeditgeneral-title").html("Edit Presence");
                                if ($(window).width() > 480) {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "50%");
                                }
                            }else{
                                $(".addeditgeneral-title").html("Generate Schedule PH");
                                if ($(window).width() > 250) {
                                    $("#addeditgeneral .modal-dialog").css("width", "20%");
                                } else {
                                    $("#addeditgeneral .modal-dialog").css("width", "20%");
                                }
                            }
                    }
                    
		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "oidDocMaster"              : oidMaster
		    }
		    onDone = function(data){
                        approvalModal(".doApprove","1");
                        runDataTables("showassetitem"); 
                        additem(".btnadditem");
                        deletegeneral(".btndeleteitem", ".<%= FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]%>");
                        datePicker(".datepicker", "yyyy-mm-dd");
                        $( ".datetimepicker2" ).datetimepicker({
                            widgetPositioning:{
                                horizontal: 'auto',
                                vertical: 'bottom'
                            },
                            format: "YYYY-MM-DD HH:mm"
                        });
                        $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
			$(".colorpicker").colorpicker();
                        $("select").chosen();
                        $("#employee").change(function(){
                            var employeeId = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "empId" : employeeId
                            }
                            var onDones = function(data){
                                $(target).val(data.FRM_FIELD_HTML);
                                $("#empReceived").chosen("destroy");
                                $("#empReceived").html(data.FRM_FIELD_HTML2).chosen();
                                $(".btnadditem").attr("data-empid",employeeId);
                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxPresenceList", target, false, "json");
                        });
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPresenceList", ".addeditgeneral-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT FORM ITEM
	    var additem = function(elementId){
		$(elementId).click(function(){
		    $("#additem").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
                    var oidAsset = $(this).data('oidasset');
                    var empid = $(this).data('empid');
                    var doctype = $(this).data('doctype');
		    dataFor = $(this).data('for');
                    var oidMaster = $(this).data('oidmaster');
		    $("#additemform #generaldatafor").val(dataFor);
		    $("#additemform #oidAssetInventory").val(oidAsset);
                    $("#additemform #oid").val(oid);
                    var servletName;
		    //SET TITLE MODAL
		    if(oid != 0){
                        $(".additem-title").html("Edit Item");
			if ($(window).width() > 480) {
                            $("#additem .modal-dialog").css("width", "50%");
                        } else {
                            $("#additem .modal-dialog").css("width", "85%");
                        }
		    }else{
                        $(".additem-title").html("Add Item");
                        if ($(window).width() > 480) {
                            $("#additem .modal-dialog").css("width", "50%");
                        } else {
                            $("#additem .modal-dialog").css("width", "85%");
                        }
		    }

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "oidAssetInventory"              : oidAsset,
                        "empId" : empid,
                        "docType" : doctype
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        $("select").chosen();
                        $(".getmaterial").change(function(){
                            var locationId = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "locationId" : locationId,
                                "empId" : empid
                            }
                            var onDones = function(data){
                                $(target).chosen("destroy");
                                $(target).html(data.FRM_FIELD_HTML).chosen();
                                
                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxPresenceListItem", null, false, "json");
                        });
                        $(".getStok").keyup(function(){
                            var stok = $("#FRM_FIELD_MATERIAL_ID option:selected").data("stok");
                            $("#additemform #stok").val(stok);
                            var qty = $(this).val();
                            if(stok < qty){
                                alert("Not Enough Stock");
                                $(this).val("").focus();
                            }
                        });
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPresenceListItem", ".additem-body", false, "json");
		});
	    };
            
            $("form#customSearch").submit(
                    function(){
                var dataSends = $(this).serialize();
                dataTablesOptions("#handOverAssetElement", "tableHandOverAsset", "AjaxPresenceList", "listPresenceFilter", callBackDataTables,dataSends);
                return false;
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
                getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxPresenceList", target, false, "json");
            });
            
            var approvalModal = function(elementId,showModal){
                $(elementId).change(function(){
                    var target = $(this).data('target');
                    var target2 = $(this).data('target2');
                    var approver = $(this).val();
                    oid = $(this).data('oid');
                    var docId = $(this).data('docid');
                    dataFor = $(this).data('for');
                    $("#fingerdatafor").val(dataFor);
                    $("#docid").val(oid);
                    $("#empid").val(approver);
                    $("#target").val(target);
                    $("#target2").val(target2);
                    if (showModal==="1"){
                        $("#modalApproveFinger").modal("show");
                        $("#modalApproveFinger .modal-dialog").css("width", "50%");
                    }
                    loadFingerPatern(oid,dataFor);
                });
            };
            
            var loadFingerPatern = function(oid,dataFor){
            oid = oid;
            dataFor = "showAcknowledgeForm";
            var empId = $("#empid").val();
            var url = "<%=baseURL%>";
                dataSend = {
                    "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                    "FRM_FIELD_OID"            : oid, 
                    "empId"                    : empId,
                    "FRM_FIELD_APPROOT"        : url
                };

                onSuccess = function(data){

                };

                onDone = function(data){
                   fingerClick(".loginFinger");
                };

                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "#dynamicFingerPatern", false, "json");
            };

        var fingerClick = function (elementId){
            //alert('test');
            $(elementId).unbind().click(function(){
                var empId = $("#empid").val();
                interval2 =  setInterval(function() {
                    checkStatusUser(empId);
                },5000);

            });
        };

        var checkStatusUser = function(empId){
            var dataFor = "checkStatusUser";                                  
            command = "<%= Command.NONE%>";
            dataSend = {
                "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                "FRM_FIELD_LOGIN_ID"       : empId,                                   
                "command"                  : command
            };

            onSuccess = function(data){

            };

            onDone = function(data){
                if (data.FRM_FIELD_HTML=="1"){
                    clearInterval(interval2);                                      
                    alert('Verification Success...');  
                    //PROSES DILANJUTKAN
                    processingApproveFinger();
                }
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", "", false, "json");

        };

        var processingApproveFinger= function(){
            var oidDetail = $("#docid").val();
            var empId = $("#empid").val();
            command = "<%= Command.NONE%>";
            var dataFor = "approveFinger";
            var generaldatafor = $("#fingerdatafor").val();
            var target = $("#target").val();
            var target2 = $("#target2").val();
            dataSend = {
                "FRM_FIELD_DATA_FOR"       : dataFor,                                       
                "FRM_FIELD_OID_DETAIL"     : oidDetail,
                "FRM_FIELD_EMP_ID"         : empId,
                "command"                  : command,
                "GENERAL_DATA_FOR"         : generaldatafor
            };

            onSuccess = function(data){

            };

            onDone = function(data){
                $(target).chosen("destroy");
                $(target).html(data.FRM_FIELD_HTML).chosen();
                $(target).prop('disabled',true).trigger("chosen:updated");
                $(target2).val(empId);
                $("#modalApproveFinger").modal("hide");
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPresenceList", null, false, "json");

        };
            
            
            //DELETE GENERAL
	    deletegeneral = function(elementId, checkboxelement){

		$(elementId).click(function(){
		    dataFor = $(this).data("for");
		    var checkBoxes = (checkboxelement);
                    var servletName = "";
		    var vals = "";
		    $(checkboxelement).each(function(i){

			if($(this).is(":checked")){
			    if(vals.length == 0){
				vals += ""+$(this).val();
			    }else{
				vals += ","+$(this).val();
			    }
			}
		    });
                    if (dataFor === 'empAsset'){
                        servletName = 'AjaxPresenceList';
                    }
		    var confirmText = "Are you sure want to delete these data?";
		    if(vals.length == 0){
			alert("Please select the data");
		    }else{
			command = <%= Command.DELETEALL %>;
			var currentHtml = $(this).html();
			$(this).html("Deleting...").attr({"disabled":true});
			if(confirm(confirmText)){
			    dataSend = {
				"FRM_FIELD_DATA_FOR"	    : dataFor,
				"FRM_FIELD_OID_DELETE"	    : vals,
				"command"		    : command
			    };
			    onSuccess = function(data){

			    };
                            
                            onDone = function(data){
                                if (dataFor === 'empAsset'){
                                runDataTables('showPresenceListForm');
                                
                                } else {
                                    runDataTables('showassetitem');
                                }
                            };
			    			    getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
			    $(this).removeAttr("disabled").html(currentHtml);
			}else{
			    $(this).removeAttr("disabled").html(currentHtml);
			}
		    }

		});
	    };
            
            var deletesingle = function (elementId) {

                $(elementId).unbind().click(function () {
                    dataFor = $(this).data("for");
                    var vals = $(this).data("oid");
                    var empid = $(this).data("empid");
                    var delResign = $(this).data("resign");
                    var servletName = "";
                    var confirmTextSingle = "Are you sure want to delete these data?";
                    if (dataFor === 'deleteEmpAssetInventoryForm'){
                        servletName = 'AjaxPresenceList';
                    } else {
                        servletName = 'AjaxPresenceList';
                    }
                    command = <%= Command.DELETE%>;
                    var currentHtml = $(this).html();
                    $(this).html("Deleting...").attr({"disabled": true});
                    if (confirm(confirmTextSingle)) {
                        dataSend = {
                            "FRM_FIELD_DATA_FOR": dataFor,
                            "FRM_FIELD_OID": vals,
                            "command": command
                        };
                        onSuccess = function (data) {

                        };
                        
                        onDone = function(data){
                           
                                runDataTables('showPresenceListForm');
                        };

                        getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                            $(this).removeAttr("disabled").html(currentHtml);
                        }
                });
            };
	    
	    //FUNCTION FOR DATA TABLES
	    callBackDataTables = function(){
		addeditgeneral(".btneditgeneral");
                deletesingle(".btndeletesingle");
                additem(".btnedititem");
                iCheckBox();
	    }

	    //FORM HANDLER

	    //VALIDATE FORM
	    function validateOptions (elementId, checkType, classError, minLength, matchValue){

		/* OPTIONS
		 * minLength    : INT VALUE,
		 * matchValue   : STRING OR INT VALUE,
		 * classError   : STRING VALUE,
		 * checkType    : STRING VALUE ('text' for default, 'email' for email check
		 */

		$(elementId).validate({
		    minLength   : minLength,
		    matchValue  : matchValue,
		    classError  : classError,
		    checkType   : checkType
		});
	    }

	    //iCheck
	    iCheckBox = function(){
		$("input[type='checkbox'], input[type='radio']").iCheck({
		    checkboxClass: 'icheckbox_minimal-blue',
		    radioClass: 'iradio_minimal-blue'
		});
	    }


	    //DATA TABLES SETTING
	    function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables, parameter){
		var datafilter = $("#datafilter").val();
		var privUpdate = $("#privUpdate").val();
                var oidAssetInventory = $("#oidAssetInventory").val();
                if(parameter != "" ){
                    parameter="&"+parameter;
                }
		$(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate+"&oidAssetInventory="+oidAssetInventory+parameter,
		    aoColumnDefs: [
			{
			   bSortable: false,
			   aTargets: [ -1, -2 ]
			}
		      ],
		    "initComplete": function(settings, json) {
			if(callBackDataTables != null){
			    callBackDataTables();
			}
		    },
		    "fnDrawCallback": function( oSettings ) {
			if(callBackDataTables != null){
			    callBackDataTables();
			}
		    },
		    "fnPageChange" : function(oSettings){

		    }
		});

		$(elementIdParent).find("#"+elementId+"_filter").find("input").addClass("form-control");
		$(elementIdParent).find("#"+elementId+"_length").find("select").addClass("form-control");
		$("#"+elementId).css("width","100%");
	    }
            
            
            function runDataTables(dataFor){
                var elementIdParent = null;
                var elementId = null;
                var servletName = null;
                var listData = null;
                if (dataFor == "showPresenceListForm"){
                    elementIdParent = "#handOverAssetElement";
                    servletName = "AjaxPresenceList";
                    listData = "listPresence";
                    elementId = "tableHandOverAsset";
                }
                
		dataTablesOptions(elementIdParent, elementId, servletName, listData, callBackDataTables,"");
            }
	    
	    
	    modalSetting("#addeditgeneral", "static", false, false);
            modalSetting("#additem", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeleteEmpAsset", ".<%= FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]%>");
		   
	    runDataTables("showPresenceListForm");

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generalform #generaldatafor").val();
                var servletName;
                onDone = function(data){
                    runDataTables('showPresenceListForm');
                };

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };
		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPresenceList", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#additemform").submit(function(){
		var currentBtnHtml = $("#btnadditemform").html();
		$("#btnadditemform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#additemform #generaldatafor").val();
                var servletName;
                onDone = function(data){
                    runDataTables("showassetitem");
                };

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btnadditemform").removeAttr("disabled").html(currentBtnHtml);
			$("#additem").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPresenceListItem", null, true, "json");
		}else{
		    $("#btnadditemform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
	    
	})
      </script>
    <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title"></h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body addeditgeneral-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btngeneralform"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>               
   </div><!-- ./wrapper -->
    </body>
</html>
