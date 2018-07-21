<%-- 
    Document   : asset_inventory
    Created on : 03-Feb-2017, 15:31:45
    Author     : Gunadi
--%>

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
        <title>Asset & Inventory | Dimata Hairisma</title>
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
                        Asset & Inventory
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Asset & Inventory</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Asset & Inventory
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpAssetForm">
                                    <i class="fa fa-plus"></i> Add Employee Asset
                                </button>
                                <button class="btn btn-danger btndeleteEmpAsset" data-for="empAsset">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
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
                                                <th>Position</th>
                                                <th>Assignment Date</th>
                                                <th>Return Date</th>
                                                <th>Note</th>
                                                <th>Type</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
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
            $("#Aset_Inventory").addClass("treeview active");
             $("#aset_Inventory").addClass("treeview active");
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
		    if(oid != 0){
                        $(".addeditgeneral-title").html("Edit Hand Over Asset");
			if ($(window).width() > 480) {
                            $("#addeditgeneral .modal-dialog").css("width", "85%");
                        } else {
                            $("#addeditgeneral .modal-dialog").css("width", "100%");
                        }
		    }else{
                        $(".addeditgeneral-title").html("Add Hand Over Asset");
                        if ($(window).width() > 480) {
                            $("#addeditgeneral .modal-dialog").css("width", "85%");
                        } else {
                            $("#addeditgeneral .modal-dialog").css("width", "100%");
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
                        deletegeneral(".btndeleteitem", ".<%= FrmEmpAssetInventoryItem.fieldNames[FrmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID]%>");
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        $("select").chosen();
                        FRM_FIELD_DOC_TYPE
                        $("#FRM_FIELD_DOC_TYPE").change(function(){
                            $(".btnadditem").attr("data-doctype",$(this).val());
                        });
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
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmpAssetInventory", target, false, "json");
                        });
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpAssetInventory", ".addeditgeneral-body", false, "json");
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
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmpAssetInventoryItem", null, false, "json");
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
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpAssetInventoryItem", ".additem-body", false, "json");
		});
	    };
            
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

            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpAssetInventory", null, false, "json");

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
                        servletName = 'AjaxEmpAssetInventory';
                    } else {
                        servletName = 'AjaxEmpAssetInventoryItem';
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
                                runDataTables('showassetmaster');
                                
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
                        servletName = 'AjaxEmpAssetInventory';
                    } else {
                        servletName = 'AjaxEmpAssetInventoryItem';
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
                            if (dataFor === 'deleteEmpAssetInventoryForm'){
                                runDataTables('showassetmaster');
                                
                            } else {
                                runDataTables('showassetitem');
                            }
                            
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
	    function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables){
		var datafilter = $("#datafilter").val();
		var privUpdate = $("#privUpdate").val();
                var oidAssetInventory = $("#oidAssetInventory").val();
		$(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate+"&oidAssetInventory="+oidAssetInventory,
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
                if (dataFor == "showassetmaster"){
                    elementIdParent = "#handOverAssetElement";
                    servletName = "AjaxEmpAssetInventory";
                    listData = "listEmpAssetInventory";
                    elementId = "tableHandOverAsset";
                }
                if (dataFor == "showassetitem" ){
                    elementIdParent = "#empAssetItemElement";
                    servletName = "AjaxEmpAssetInventoryItem";
                    listData = "listAssetItem";
                    elementId = "tableAssetItem";
                }
		dataTablesOptions(elementIdParent, elementId, servletName, listData, callBackDataTables);
            }
	    
	    
	    modalSetting("#addeditgeneral", "static", false, false);
            modalSetting("#additem", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeleteEmpAsset", ".<%= FrmEmpAssetInventory.fieldNames[FrmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID]%>");
		   
	    runDataTables("showassetmaster");

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generalform #generaldatafor").val();
                var servletName;
                onDone = function(data){
                    runDataTables('showassetmaster');
                };

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpAssetInventory", null, true, "json");
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
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpAssetInventoryItem", null, true, "json");
		}else{
		    $("#btnadditemform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
	    
	})
      </script>
    <div id="addeditgeneral" class="modal fade nonprint" style="overflow-y: auto">
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
                    <input type="hidden" name="oidAssetInventory" id="oidAssetInventory">
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
    <div id="additem" class="modal fade nonprint" style="overflow-y: auto">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="additem-title"></h4>
		</div>
                <form id="additemform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="oidAssetInventory" id="oidAssetInventory">
                    <input type="hidden" name="stok" id="stok">
		    <div class="modal-body ">
			
				<div class="box-body additem-body">
                                    
				</div>
			    
			
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnadditemform"><i class="fa fa-check"></i> Save</button>
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
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
                    <button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
                </div>
            </div>
        </div>
    </div>                
   </div><!-- ./wrapper -->
    </body>
</html>
