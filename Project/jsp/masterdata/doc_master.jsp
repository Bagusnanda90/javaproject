<%-- 
    Document   : PayrollGroup
    Created on : 16-Jun-2016, 09:20:44
    Author     : Gunadi
--%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocMaster"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocMasterFlow"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../main/javainit.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Master Document | Dimata Hairisma</title>
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
                        Master Document
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Doc Master</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Document Master
                            </div>
                            <div class="box-body">
                                <div id="docMasterElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Document Type</th>
                                                <th>Title</th>					
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showDocMasterForm">
                                    <i class="fa fa-plus"></i> Add Document Master
                                </button>
                                <button class="btn btn-danger btndeletedivision" data-for="division">
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
		    $("#generalform #oidDoc").val(oid);
                    var servletName;
		    //SET TITLE MODAL
		    if(oid != 0){
			if(dataFor == 'showDocMasterForm'){
			    $(".addeditgeneral-title").html("Edit Document Master");
			}
                        if(dataFor == 'showDocFlowForm'){
                            $(".addeditgeneral-title").html("Edit Document Flow");
                        }

		    }else{
			if(dataFor == 'showDocMasterForm'){
			    $(".addeditgeneral-title").html("Add Document Master");
			}
                        if(dataFor == 'showDocFlowForm'){
                            $(".addeditgeneral-title").html("Add Document Flow");
                        }
		    }
                    
                    if (dataFor == 'showDocMasterForm'){
                        servletName = "AjaxDocMaster";
                    } else if (dataFor == 'showDocFlowForm'){
                        servletName = "AjaxDocMasterFlow";
                    }


		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "oidDocMaster"              : oidMaster
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, ".addeditgeneral-body", false, "json");
		});
	    };
            
            docFlow = function(elementId){
		$(elementId).click(function(){
		    $("#docflow").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
		    $("#docflowform #generaldatafor").val(dataFor);
		    $("#docflowform #oid").val(oid);
                    $("#docflowform #oidDoc").val(oid);
                    $("#docflow .modal-dialog").css("width", "70%");
		    //SET TITLE MODAL
                    $(".docflow-title").html("Document Approval");
		
		    dataSend = {
			"FRM_FIELD_DATA_FOR"	 : dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        runDataTables("showflow"); 
                        addeditgeneral(".btnaddflow");
                        deletegeneral(".btndeleteflow", ".<%= FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_FLOW_ID]%>");
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxDocMaster", ".docflow-body", false, "json");
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
			    if(dataFor == "docMaster"){
                                servletName = "AjaxDocMaster";
				onDone = function(data){
				    runDataTables();
				};
			    }
                            if(dataFor == "flow"){
                                servletName = "AjaxDocMasterFlow";
				onDone = function(data){
				    runDataTables("showdocmaster");
				};
			    }
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
                        
                        if(dataFor == "docMaster"){
                            servletName = "AjaxDocMaster";
                            onDone = function(data){
                                runDataTables();
                            };
                        }
                        if(dataFor == "deleteDocFlowSingle"){
                            servletName = "AjaxDocMasterFlow";
                            onDone = function(data){
                                runDataTables("showflow");
                            };
                        }

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
                docFlow(".btnflow");
		iCheckBox();
	    }

	    //FORM HANDLER
	    divisionForm = function(){
		validateOptions("#<%= FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_TITLE] %>", 'text', 'has-error', 1, null);
	    }
            flowForm = function () {
                validateOptions("#<%= FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE] %>", 'text', 'has-error', 1, null);
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
                var elementIdParent = null;
                var elementId = null;
                var servletName = null;
                var listData = null;
                if (dataFor == "showdocmaster"){
                    elementIdParent = "#docMasterElement";
                    servletName = "AjaxDocMaster";
                    listData = "listDocMaster";
                    elementId = "tableDocMasterElement";
                }
                if (dataFor == "showflow" ){
                    elementIdParent = "#docFlowElement";
                    servletName = "AjaxDocMasterFlow";
                    listData = "listDocMasterFlow";
                    elementId = "tableDocFlowElement";
                }
		dataTablesOptions(elementIdParent, elementId, servletName, listData, callBackDataTables);
            }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeleteDocMaster", ".<%= FrmDocMaster.fieldNames[FrmDocMaster.FRM_FIELD_DOC_MASTER_ID]%>");
		   
	    runDataTables("showdocmaster");

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generalform #generaldatafor").val();
                var servletName;
		if(generaldatafor == "showDocFlowForm"){
                    flowForm();
		    servletName = "AjaxDocMasterFlow";
		    onDone = function(data){
			runDataTables("showflow");
		    };
		} if (generaldatafor == "showDocMasterForm"){
                    servletName = "AjaxDocMaster";
                    onDone = function(data){
			runDataTables("showflow");
		    };
                }


		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            //FancyBox
            $('.fancybox').fancybox({
                    helpers:{
                        overlay : {closeClick:false}
                    }
                });

                /*
                 *  Different effects
                 */

                // Change title type, overlay closing speed
                $(".fancybox-effects-a").fancybox({
                    helpers: {
                        title : {
                            type : 'outside'
                        },
                        overlay : {
                            speedOut : 0
                        }
                    }
                });

                // Disable opening and closing animations, change title type
                $(".fancybox-effects-b").fancybox({
                    openEffect  : 'none',
                    closeEffect	: 'none',

                    helpers : {
                        title : {
                            type : 'over'
                        }
                    }
                });

                // Set custom style, close if clicked, change title type and overlay color
                $(".fancybox-effects-c").fancybox({
                    wrapCSS    : 'fancybox-custom',
                    closeClick : true,

                    openEffect : 'none',

                    helpers : {
                        title : {
                            type : 'inside'
                        },
                        overlay : {
                            css : {
                                'background' : 'rgba(238,238,238,0.85)'
                            }
                        }
                    }
                });

                // Remove padding, set opening and closing animations, close if clicked and disable overlay
                $(".fancybox-effects-d").fancybox({
                    padding: 0,

                    openEffect : 'elastic',
                    openSpeed  : 150,

                    closeEffect : 'elastic',
                    closeSpeed  : 150,

                    closeClick : true,

                    helpers : {
                        overlay : null
                    }
                });

                /*
                 *  Button helper. Disable animations, hide close button, change title type and content
                 */

                $('.fancybox-buttons').fancybox({
                    openEffect  : 'none',
                    closeEffect : 'none',

                    prevEffect : 'none',
                    nextEffect : 'none',

                    closeBtn  : false,

                    helpers : {
                        title : {
                            type : 'inside'
                        },
                        buttons	: {}
                    },

                    afterLoad : function() {
                        this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
                    }
                });


                /*
                 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
                 */

                $('.fancybox-thumbs').fancybox({
                    prevEffect : 'none',
                    nextEffect : 'none',

                    closeBtn  : false,
                    arrows    : false,
                    nextClick : true,

                    helpers : {
                        thumbs : {
                            width  : 50,
                            height : 50
                        }
                    }
                });

                /*
                 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
                 */
                $('.fancybox-media')
                .attr('rel', 'media-gallery')
                .fancybox({
                    openEffect : 'none',
                    closeEffect : 'none',
                    prevEffect : 'none',
                    nextEffect : 'none',

                    arrows : false,
                    helpers : {
                        media : {},
                        buttons : {}
                    }
                });

                /*
                 *  Open manually
                 */

                $("#fancybox-manual-a").click(function() {
                    $.fancybox.open('1_b.jpg');
                });

                $("#fancybox-manual-b").click(function() {
                    $.fancybox.open({
                        href : 'member.jsp',
                        type : 'iframe',
                        padding : 5
                    });
                });

                $("#fancybox-manual-c").click(function() {
                    $.fancybox.open([
                        {
                            href : '1_b.jpg',
                            title : 'My title'
                        }, {
                            href : '2_b.jpg',
                            title : '2nd title'
                        }, {
                            href : '3_b.jpg'
                        }
                    ], {
                        helpers : {
                            thumbs : {
                                width: 175,
                                height: 150
                            }
                        }
                    });
               


            });
	    
	})
      </script>
    <div id="docflow" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="docflow-title"></h4>
		</div>
                <form id="docflowform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="oidDocMaster" id="oidDoc">
		    <div class="modal-body ">
			
				<div class="box-body docflow-body">
                                    
				</div>
			    
			
		    </div>
		    <div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>    
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
