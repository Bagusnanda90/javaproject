<%-- 
    Document   : recrapplication
    Created on : 09-Oct-2017, 09:08:53
    Author     : Gunadi
--%>

<%@page import="com.dimata.util.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<%
    String url= request.getParameter("menu");
    String urlC = request.getRequestURL().toString();
        String baseURL = urlC.substring(0, urlC.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Employment Application | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-lg{
	    max-width: 1200px;
	    width: 100%;
            }
            .modal-body {
            max-height: calc(100vh -212px);
            overflow-y: auto;
            }
            .errormessage{
                color: #d9534f;
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
        <%@ include file="../../template/css.jsp" %>
        
        
    </head>
    <body class="hold-transition skin-blue sidebar-collapse sidebar-mini">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Staff Requisition
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Staff Requisition</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Staff Requisition
                            </div>
                            <div class="box-body">
                                <div id="staffRequisitionElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Position</th>
                                                <th>Other Position</th>
                                                <th>Date Available</th>
                                                <th>Full Name</th>
                                                <th>Sex</th>
                                                <th>Birth Place</th>
                                                <th>Birth Date</th>
                                                <th>Religion</th>
                                                <th>Marital Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmploymentForm">
                                    <i class="fa fa-plus"></i> Add Form
                                </button>
                                <button class="btn btn-danger btndeletegeneral" data-for="employment">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
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
	    //SET ACTIVE MENU
	    var activeMenu = function(parentId, childId){
		$(parentId).addClass("active").find(".treeview-menu").slideDown();
		$(childId).addClass("active");
	    }
            
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

	    activeMenu("#employee", "#staffrequisition");
	    
	    
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
	    
	    //SHOW ADD OR EDIT FORM
	    addeditgeneral = function(elementId){
		$(elementId).click(function(){
		    $("#addeditgeneral").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
                    $("#generalform #generaldatafor").val(dataFor);
		    $("#generalform #oid").val(oid);
                    //SET TITLE MODAL
		    if(oid != 0){
                        $(".addeditgeneral-title").html("Edit Staff Requisition");
		    }else{
                        $(".addeditgeneral-title").html("Add Staff Requisition");
		    }                    

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "user"                  : <%=emplx.getOID()%>
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
                        $("select").chosen();
			$(".colorpicker").colorpicker();
                        approvalModal(".doApprove","1");
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxStaffRequisition", ".addeditgeneral-body", false, "json");
		});
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
                                runDataTables();
                            };
			    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxStaffRequisition", null, true, "json");
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
                            runDataTables();
                        };
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxStaffRequisition", null, true, "json");
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
                iCheckBox();
	    }

	    //FORM HANDLER
	    var staffRequisitionForm = function(){
		validateOptions("#department", 'text', 'has-error', 1, null);
                validateOptions("#section", 'text', 'has-error', 1, null);
                validateOptions("#position", 'text', 'has-error', 1, null);
                validateOptions("#category", 'text', 'has-error', 1, null);
	    }

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
	    function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables){
		var datafilter = $("#datafilter").val();
		var privUpdate = $("#privUpdate").val();
                var oidDoc = $("#oidDoc").val();
		$(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate+"&oidDocMaster="+oidDoc,
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
                dataTablesOptions("#staffRequisitionElement", "tableStaffRequisitionElement", "AjaxStaffRequisition", "listStaffRequisition", callBackDataTables);
            }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeletegeneral", ".<%= FrmStaffRequisition.fieldNames[FrmStaffRequisition.FRM_FIELD_STAFF_REQUISITION_ID]%>");
		   
	    runDataTables();

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generalform #generaldatafor").val();
                staffRequisitionForm();
                onDone = function(data){
                    runDataTables();
                };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxStaffRequisition", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
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
                        $("#modal-title-approve-finger").html("Finger List");
                        $("#modalApproveFinger .modal-dialog").css("width", "50%");
                    }
                    loadFingerPatern(oid,dataFor);
                    closeFingerModal("#closeFinger");
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
                $("#modalApproveFinger").modal("hide");
            };

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxStaffRequisition", null, false, "json");

        };
        
        var closeFingerModal = function (elementId) {
                $(elementId).unbind().click(function () {
                    var target = $("#target").val();
                    $(target).prop('selectedIndex',0).trigger("chosen:updated");
                    $("#modalApproveFinger").modal("hide");
                });
            };
	})
      </script>
    <div id="addeditgeneral" class="modal fade nonprint">
	<div class="modal-dialog modal-lg nonprint">
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
			
				<div class="box-body addeditgeneral-body">
                                    
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
    <div id="modalApproveFinger" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modal-title-approve-finger" value="Select Finger"></h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <input type="hidden" name="oid" id="docid">
                        <input type="hidden" name="FRM_FIELD_EMP_ID" id="empid">
                        <input type="hidden" name="FRM_FIELD_DATA_FOR" id="fingerdatafor">
                        <input type="hidden" name="FRM_FIELD_DATA_TARGET" id="target">
                        <input type="hidden" name="FRM_FIELD_DATA_TARGET2" id="target2">
                        <div class="col-md-12" id="dynamicFingerPatern">

                        </div>
                    </div>
                </div>
                <div class="modal-footer">                              
                    <button type="button" data-dismiss="modal" id="closeFinger" class="btn btn-danger">Close</button>
                </div>
            </div>
        </div>
    </div>                    
   </div><!-- ./wrapper -->
    </body>
</html>
