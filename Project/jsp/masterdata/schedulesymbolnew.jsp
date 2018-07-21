<%-- 
    Document   : periodnew
    Created on : Jun 04, 2017, 10:34:12 AM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmScheduleSymbol"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmScheduleCategory"%>
<%@page import="com.dimata.util.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../main/javainit.jsp" %>
<%
int iCommand = FRMQueryString.requestCommand(request);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Schedule Symbol | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
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
        </style>
       <%@ include file="../template/css.jsp" %>
       
       <script type="text/javascript">

        function yesnoCheck() {
            if (document.getElementById('yesCheck').checked) {
                document.getElementById('ifYes').style.display = 'block';
                
            }
            else {
            document.getElementById('ifYes').style.display = 'none';
            document.getElementById('FRM_FIELD_TIME_IN_STRING').value = "00:00";
            document.getElementById('FRM_FIELD_TIME_OUT_STRING').value = "00:00";
            document.getElementById('FRM_FIELD_BREAK_OUT_STRING').value = "00:00";
            document.getElementById('FRM_FIELD_BREAK_IN_STRING').value = "00:00";
            }
        }
        
       
        </script>
    </head>
    
    <body class="hold-transition skin-blue fixed sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../template/header.jsp" %>
            <%@ include file="../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Master Data
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Master Data</li>
                        <li class="active">Schedule Symbol</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Schedule Symbol
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showsymbolform">
                                    <i class="fa fa-plus"></i> Add Symbol
                                </button>
                                <button class="btn btn-danger btndeletedivision" data-for="division">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                            </div>
                            <div class="box-body">
                                <div id="SymbolElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Symbol</th>
                                                <th>Schedule Category</th>					
                                                <th>Schedule Name</th>
                                                <th>Time IN</th>
                                                <th>Break OUT</th>
                                                <th>Break IN</th>
                                                <th>Time OUT</th>
                                                <th>Note</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            
                            </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section><!-- /.content -->

                        </div>
           
            <%@include file="../template/plugin.jsp" %>
            <%@ include file="../template/footer.jsp" %>
        
        
        
        <script type="text/javascript">
	$(document).ready(function(){
	    //SET ACTIVE MENU
            $("#Master_data").addClass("treeview active");
            $("#Schedule").addClass("treeview active");
            $("#schSymbol").addClass("treeview active");
	    var activeMenu = function(parentId, childId){
		$(parentId).addClass("active").find(".treeview-menu").slideDown();
		$(childId).addClass("active");
	    }

	    activeMenu("#masterdata", "#division");
	    
	    
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
	    var areaTypeForm = null;
	    var deletegeneral = null;
	    
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
		    $("#generaldatafor").val(dataFor);
		    $("#oid").val(oid);

		    //SET TITLE MODAL
		    if(oid != 0){
			if(dataFor == 'showsymbolform'){
			    $(".addeditgeneral-title").html("Edit Symbol");
			}

		    }else{
			if(dataFor == 'showsymbolform'){
			    $(".addeditgeneral-title").html("Add Symbol");
			}
		    }


		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
                        $('.datetimepicker').datetimepicker({
                        format : 'LT',
                        locale: 'ru'
                        });
			$(".colorpicker").colorpicker();
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxScheduleSymbol", ".addeditgeneral-body", false, "json");
		});
	    };
	    
	    //DELETE GENERAL
	    deletegeneral = function(elementId, checkboxelement){

		$(elementId).click(function(){
		    dataFor = $(this).data("for");
		    var checkBoxes = (checkboxelement);
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
			    if(dataFor == "division"){
				onDone = function(data){
				    runDataTables();
				};
			    }
			    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxScheduleSymbol", null, true, "json");
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
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxScheduleSymbol", null, true, "json");
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
	    divisionForm = function(){
		validateOptions("#<%= FrmScheduleCategory.fieldNames[FrmScheduleCategory.FRM_FIELD_CATEGORY] %>", 'text', 'has-error', 1, null);
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
		$(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate,
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
	    
	    function runDataTables(){
		dataTablesOptions("#SymbolElement", "tableSymbolElement", "AjaxScheduleSymbol", "listschsymbol", callBackDataTables);
	    }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeletedivision", ".<%= FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_SCHEDULE_ID]%>");
		    
	    runDataTables();

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
		if(generaldatafor == "showsymbolform"){
		    divisionForm();
		    onDone = function(data){
			runDataTables();
		    };
		}


		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxScheduleSymbol", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
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
