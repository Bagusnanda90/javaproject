<%-- 
    Document   : PositionGroup
    Created on : 16-Jun-2016, 09:20:44
    Author     : Gunadi
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmPositionGroup"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../main/javainit.jsp" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Master Position Group | Dimata Hairisma</title>
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
        </style>
        <script src="../styles/ckeditor/ckeditor.js"></script>
        <%@ include file="../template/css.jsp" %>
        
        
    </head>
    <body class="hold-transition skin-blue sidebar-collapse sidebar-mini">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../template/header.jsp" %>
            <%@ include file="../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Master Position Group
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Position Group</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Master Position Group
                            </div>
                            <div class="box-body">
                                <div id="positionGroupElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Position Group</th>
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showPositionGroupForm">
                                    <i class="fa fa-plus"></i> Add Position
                                </button>
                                <button class="btn btn-danger btndeletegeneral" data-for="positiongroup">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                            </div>
                            
                        </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../template/plugin.jsp" %>
            <%@ include file="../template/footer.jsp" %>
       
       
        <script type="text/javascript">
	$(document).ready(function(){
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
                    var servletName;
		    //SET TITLE MODAL
		    if(oid != 0){
                        $(".addeditgeneral-title").html("Edit Position Group");
		    }else{
                        $(".addeditgeneral-title").html("Add Position Group");
		    }                    

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPositionGroup", ".addeditgeneral-body", false, "json");
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
			    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPositionGroup", null, true, "json");
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
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPositionGroup", null, true, "json");
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
	    var positionGroupForm = function(){
		validateOptions("#<%= FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_NAME] %>", 'text', 'has-error', 1, null);
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
                dataTablesOptions("#positionGroupElement", "tablePosiitonGroupElement", "AjaxPositionGroup", "listPositionGroup", callBackDataTables);
            }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeletegeneral", ".<%= FrmPositionGroup.fieldNames[FrmPositionGroup.FRM_FIELD_POSITION_GROUP_ID]%>");
		   
	    runDataTables();

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generalform #generaldatafor").val();
                positionGroupForm();
                onDone = function(data){
                    runDataTables();
                };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxPositionGroup", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
	    
	})
      </script>
    <div id="addeditgeneral" class="modal fade nonprint">
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
   </div><!-- ./wrapper -->
    </body>
</html>
