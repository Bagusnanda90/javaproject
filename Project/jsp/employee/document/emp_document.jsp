<%-- 
    Document   : emp_document
    Created on : 29-Jul-2016, 13:34:36
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmEmpDoc"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Employee Document List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .item {
                padding: 3px;
                border:1px solid #CCC;
                border-radius: 3px;
                background-color: #EEE;
                margin: 3px;
                cursor: pointer;
            }
            
        </style>
         <%@ include file="../../template/css.jsp" %>
         <script src="../../styles/ckeditor/ckeditor.js"></script>
    </head>
    
    <body class="hold-transition skin-blue fixed sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <input type="hidden" id="editor_hidden">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Employee Document
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Employee</li>
                        <li class="active">Employee Document</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                                Employee Document
                            </div>
                            <div class='box-footer'>
                                <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showEmpDocForm">
                                    <i class="fa fa-plus"></i> Add Document
                                </button>
                                <button class="btn btn-danger btndeletedoc" data-for="empDoc">
                                    <i class="fa fa-trash"></i> Delete
                                </button>
                            </div>
                            <div class="box-body">
                                <div id="empDocElement">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>No.</th>
                                                <th>Master Document</th>
                                                <th>Document Title</th>					
                                                <th>Document Number</th>
                                                <th>Document Date</th>
                                                <th>Valid Date</th>
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
                        </div>
           
            <%@include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
        
        
        <script>
            $(function () {
                $(".comboDivision").select2({
                    placeholder: "Division"
                });
                $(".comboCompany").select2({
                    placeholder: "Company"
                });
                $(".comboDepartment").select2({
                    placeholder: "Department"
                });
                $(".comboSection").select2({
                    placeholder: "Section"
                });
                $(".comboPosition").select2({
                    placeholder: "Position"
                });
                
            });
        </script>
        <script type="text/javascript">
	$(document).ready(function(){
            $("#Aset_Inventory").addClass("treeview active");
            $("#Aset_Inventory").addClass("treeview active");
            
	    //SET ACTIVE MENU
	    var activeMenu = function(parentId, childId){
		$(parentId).addClass("active").find(".treeview-menu").slideDown();
		$(childId).addClass("active");
	    }

	    activeMenu("#masterdata", "#division");
	    
            
//	    CKEDITOR.replace('editor_hidden');
//            CKEDITOR.on("instanceReady", function(event)
//            {
//                 $('#cke_editor_hidden').hide();
//            });
            
            
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
            var addempsingle = null;
            var addtext = null;
	    var areaTypeForm = null;
	    var deletegeneral = null;
            var deletesingle = null;
            var editormodal = null;
            var approve = null;
            var editor = "";
	    
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
                    todayHighlight: true,
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
                    var template = $(this).data('template1');
		    $("#generalform #generaldatafor").val(dataFor);
		    $("#generalform #oid").val(oid);
                    $("#addeditgeneral .modal-dialog").css("width", "100%");
		    //SET TITLE MODAL
                    $(".addeditgeneral-title").html("Add Employee Document");
		

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "datachange"               : template
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        loadDocNumber(".datachange");
                        //loadTemplate(".datachange");
//                        CKEDITOR.replace('FRM_FIELD_DETAILS');
//                        $("#FRM_FIELD_DETAILS").val($("#FRM_FIELD_DETAILS").val());
                        
                        $('#btnClose').click(function(){
                            $('#addempsingle').modal('toggle');
                        });
                        addempsingle("a#addEmpSingle");
                        addtext("a#addText");
                        addrecipient("a#addRecipient");
                        addempdata("a#addEmpAward");
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addeditgeneral-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT EMPLOYEE DATA
	    var addempdata = function(elementId){
		$(elementId).click(function(){
		    $("#addempdata").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
                    var template = $(this).data('template1');
                    var target2 = $(this).data("target2");
                    var object = $(this).data("object");
                    var empDataId = $(this).data("empdataid");
		    $("#empdatadatafor").val(dataFor);
		    $("#empdataform #oid").val(oid);
                    $("#empdataform #object").val(object);
                    $("#empdataform #datachange").val(template);
                    $("#empdataform #target").val(target2);
                    $("#empdataform #empdataid").val(empDataId);                    
                    
                    //SET TITLE MODAL
                    $(".addempdata-title").html("Add Employee Document");
		

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
                        "FRM_FIELD_EMP_DATA_ID" : empDataId,
			"command"		 : command,
                        "datachange"               : template,
                        "FRM_FIELD_OID"          : oid,
                        "object"                 : object
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			loadDocNumber(".datachange");
                        //loadTemplate(".datachange");
//                        CKEDITOR.replace('FRM_FIELD_DETAILS');
//                        $("#FRM_FIELD_DETAILS").val($("#FRM_FIELD_DETAILS").val());
                        
                        $('#btnClose').click(function(){
                            $('#addempsingle').modal('toggle');
                        });
                        addempsingle("a#addEmpSingle");
                        addtext("a#addText");
                        addrecipient("a#addRecipient");
                        addempdata("a#addEmpAward");
                        $('#internal').click(function()
                        {
                          $('.internal').removeAttr("disabled");
                          $('.external').attr("disabled","disabled")
                        });
                        $('#external').click(function()
                        {
                          $('.external').removeAttr("disabled");
                          $('.internal').attr("disabled","disabled")
                        });
                        $(".filteremp").change(function(){
                            var empId = $(this).val();
                            dataFor = $(this).data("for");
                            var dataSends1 = {
                                "empId" : empId,
                                "FRM_FIELD_DATA_FOR" : dataFor
                            }
                            var onDones1 = function(data){
                                $("#emp_container").empty();
                                $("#emp_container").html(data.FRM_FIELD_HTML);
                            };
                            var onSuccesses1 = function(data){

                            };
                            getDataFunction(onDones1, onSuccesses1, approot, command, dataSends1, "AjaxEmpDoc", null, false, "json");
                        });
                        $(".getCareer").change(function(){
                            var empId = $(this).val();
                            dataFor = $(this).data("for");
                            var dataSends2 = {
                                "empId" : empId,
                                "FRM_FIELD_DATA_FOR" : dataFor,
                                "object"                 : object
                            }
                            var onDones2 = function(data){
                                $("#careerContainer").empty();
                                $("#careerContainer").html(data.FRM_FIELD_HTML);
                                datePicker(".datepicker", "yyyy-mm-dd");
                                $(".company_struct").change(function(){
                                var companyId = $(this).val();
                                var mutationId = $(this).val();
                                dataFor = $(this).data("for");
                                var target = $(this).data("replacement");
                                var empId = $(this).data("empid")
                                var dataSends3 = {
                                    "FRM_FIELD_DATA_FOR": dataFor,
                                    "FRM_FIELD_OID": oid,
                                    "command": command,
                                    "company_id" : companyId,
                                    "mutation_id" : mutationId,
                                    "empId" : empId,
                                    "object"                 : object
                                }
                                var onDones3 = function(data){

                                };
                                var onSuccesses3 = function(data){

                                };
                                getDataFunction(onDones3, onSuccesses3, approot, command, dataSends3, "AjaxEmpDoc", target, false, "json");
                            });
                            };
                            var onSuccesses2 = function(data){

                            };
                            getDataFunction(onDones2, onSuccesses2, approot, command, dataSends2, "AjaxEmpDoc", null, false, "json");
                        });
                        $(".company_struct").change(function(){
                            var companyId = $(this).val();
                            dataFor = $(this).data("for");
                            var target = $(this).data("replacement");
                            var dataSends = {
                                "FRM_FIELD_DATA_FOR": dataFor,
                                "FRM_FIELD_OID": oid,
                                "command": command,
                                "company_id" : companyId
                            }
                            var onDones = function(data){

                            };
                            var onSuccesses = function(data){

                            };
                            getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmpDoc", target, false, "json");
                        });
                        
                        //FORM SUBMIT
                        $("form#empdataform").unbind().submit(function(){
                           var currentBtnHtml = $("#btnaddempdata").html();
                           $("#btnaddempdata").html("Submitting...").attr({"disabled":"true"});
                           var generaldatafor = $("#empdatadatafor").val();
                           onDone = function(data){
                               //alert(target2);
                               $(target2).html(data.FRM_FIELD_HTML_2);
                               addtext("a#addText");
                               addempsingle("a#addEmpSingle");
                               addrecipient("a#addRecipient");
                               addempdata("a#addEmpAward");
                           };
                           if($(this).find(".has-error").length == 0){
                               onSuccess = function(data){
                                   $("#btnaddempdata").removeAttr("disabled").html(currentBtnHtml);
                                   $("#addempdata").modal("hide");
                               };

                               dataSend = $(this).serialize();
                               getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                           }else{
                               $("#btnaddempdata").removeAttr("disabled").html(currentBtnHtml);
                           }

                           return false;
                       });
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addempdata-body", false, "json");
		});
	    };
            
            //SHOW EDITOR FORM
	    editormodal = function(elementId){
		$(elementId).click(function(){
		    $("#editor").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
                    var template = $(this).data('template1');
		    $("#editorform #generaldatafor").val(dataFor);
		    $("#editorform #oid").val(oid);
                    $("#editor .modal-dialog").css("width", "100%");
		    //SET TITLE MODAL
                    $(".editor-title").html("Editor");
		

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command,
                        "datachange"               : template
		    }
		    onDone = function(data){
                        editor = CKEDITOR.instances['FRM_FIELD_DETAILS'];
                        if (editor) { editor.destroy(true); }
                        CKEDITOR.replace('FRM_FIELD_DETAILS');
//                        if (CKEDITOR.instances.FRM_FIELD_DETAILS){
//                          CKEDITOR.instances.FRM_FIELD_DETAILS.destroy();  
//                        } 
//                        editor = CKEDITOR.replace('FRM_FIELD_DETAILS');
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        loadDocNumber(".datachange");
                        //loadTemplate(".datachange");
//                        CKEDITOR.replace('FRM_FIELD_DETAILS');
                        $("#FRM_FIELD_DETAILS").val($("#editor_text").val());
                        
                        $('#btnClose').click(function(){
                            $('#addempsingle').modal('toggle');
                        });
                        addempsingle("a#addEmpSingle");
                        addtext("a#addText");
                        addrecipient("a#addRecipient");
                        addempdata("a#addEmpAward");
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".editor-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT FORM
	    addempsingle = function(elementId){
		$(elementId).bind('click', function () {
		    $("#addempsingle").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    var object = $(this).data('object');
                    dataFor = $(this).data('for');
                    var template = $(this).data('template1');
		    $("#empsingleform #singledatafor").val(dataFor);
		    $("#empsingleform #oid").val(oid);
                    $("#empsingleform #object").val(object);
                    $("#addempsingle .modal-dialog").css("width", "50%");
		    //SET TITLE MODAL
                    $(".addempsingle-title").html("Add Employee");
		    

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
                        "object"                 : object,
                        "template"               : template,
			"command"		 : command
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        loadDocNumber(".datachange");
                        addtext("a#addText");
                        addempdata("a#addEmpAward");
                        addempsingle("a#addEmpSingle");
                        addrecipient("a#addRecipient");
                        //loadTemplate(".datachange");
                        //CKEDITOR.replace('editor1');
                        $(".item").click(function(){
                            var object = $(this).data("object");
                            var empId = $(this).data("empid");
                            var oid = $("#empsingleform #oid").val();
                            var template = $(this).data("template1");
                            var dataFor = $(this).data("for");
                            var target2 = $(this).data("target2");
                            command = '<%=Command.SAVE%>';
                            
                            
                            dataSend = {
                                "object" : object,
                                "datachange" : template,
                                "empId" : empId,
                                "command" : command,
                                "FRM_FIELD_DATA_FOR" : dataFor,
                                "FRM_FIELD_OID" : oid
                            }

                            onSuccess = function(data){

                            };

                            onDone = function(data){
                                //alert(target2);
                                $(target2).html(data.FRM_FIELD_HTML_2);
                                addtext("a#addText");
                                addempsingle("a#addEmpSingle");
                                addrecipient("a#addRecipient");
                                addempdata("a#addEmpAward");
                                $("#empId").val(empId);
                            };


                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                            $("#addempsingle").modal("hide");
                        });
                        
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addempsingle-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT TEXT FORM
	    addtext = function(elementId){
		$(elementId).bind('click', function () {
		    $("#addtext").modal("show");
		    oid = $(this).data('oid');
		    var object = $(this).data('object');
                    var objectType = $(this).data('objecttype');
                    var objectClass = $(this).data('objectclass');
                    var objectStatusField = $(this).data('objectstatusfield');
                    dataFor = $(this).data('for');
                    var template = $(this).data('template1');
                    var target2 = $(this).data("target2");
		    $("#textform #textdatafor").val(dataFor);
		    $("#textform #oid").val(oid);
                    $("#textform #objectField").val(object);
                    $("#textform #objectType").val(objectType);
                    $("#textform #objectClass").val(objectClass);
                    $("#textform #objectStatusField").val(objectStatusField);
                    $("#addtext #datachange").val(template);
                    
                    $("#addtext .modal-dialog").css("width", "50%");
                    $("#addtext .modal-dialog").css("height", "50%");
		    //SET TITLE MODAL
                    $(".addtext-title").html("Set Field");
		    if (objectStatusField == 'DATE'){
                        $("#FRM_FIELD_VALUE").addClass("datepicker");
                    }                  

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
                        "object"                 : object,
                        "objectType"             : objectType,
                        "objectClass"            : objectClass,
                        "objectStatusField"      : objectStatusField,
                        "template"               : template
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
                        //CKEDITOR.replace('FRM_FIELD_VALUE');
                        $("#FRM_FIELD_VALUE").wysihtml5();
			$(".colorpicker").colorpicker();
                        loadDocNumber(".datachange");
                        //loadTemplate(".datachange");
                        //FORM SUBMIT
                        $("form#textform").unbind().submit(function(){
                            var currentBtnHtml = $("#btnaddtext").html();
                            $("#btnaddtext").html("Submitting...").attr({"disabled":"true"});
                            var generaldatafor = $("#generaldatafor").val();
                            onDone = function(data){
                                //alert(target2);
                                $(target2).html(data.FRM_FIELD_HTML_2);
                                addtext("a#addText");
                                addempsingle("a#addEmpSingle");
                                addrecipient("a#addRecipient");
                                addempdata("a#addEmpAward");
                            };
                            if($(this).find(".has-error").length == 0){
                                onSuccess = function(data){
                                    $("#btnaddtext").removeAttr("disabled").html(currentBtnHtml);
                                    $("#addtext").modal("hide");
                                };

                                dataSend = $(this).serialize();
                                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                            }else{
                                $("#btnaddtext").removeAttr("disabled").html(currentBtnHtml);
                            }

                            return false;
                        });
                        
		    };
		    onSuccess = function(data){
			
		    };
//                    onDone = function(data){
//                        //alert(target2);
//                        $(target2).html(data.FRM_FIELD_HTML_2);
//                        addtext("a#addText");
//                    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addtext-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT FORM
	    var addrecipient = function(elementId){
		$(elementId).bind('click', function () {
		    $("#addrecipient").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    var object = $(this).data('object');
                    dataFor = $(this).data('for');
                    var template = $(this).data('template1');
                    var target2 = $(this).data("target2");
                    var issueDate = $(this).data("issuedate");
		    $("#recipientform #recipientdatafor").val(dataFor);
		    $("#recipientform #oid").val(oid);
                    $("#recipientform #object").val(object);
                    $("#recipientform #datachange").val(template);
                    $("#recipientform #issueDate").val(issueDate);
                    $("#addrecipient .modal-dialog").css("width", "50%");
		    //SET TITLE MODAL
                    $(".addrecipient-title").html("Add Recipient");
		    

		    dataSend = {
			"FRM_FIELD_DATA_FOR"	: dataFor,
			"FRM_FIELD_OID"		 : oid,
                        "object"                 : object,
                        "datachange"               : template,
			"command"		 : command
		    }
		    onDone = function(data){
                        datePicker(".datepicker", "yyyy-mm-dd");
			$(".colorpicker").colorpicker();
                        loadDocNumber(".datachange");
                        addtext("a#addText");
                        addempsingle("a#addEmpSingle");
                        addrecipient("a#addRecipient");
                        addempdata("a#addEmpAward");
                        $(".sendTo").change(function(){
                            var filter = $("#sendTo").val();
                            dataFor = "getRecipient";

                            dataSend = {
                                "FRM_FIELD_FILTER" : filter,
                                "FRM_FIELD_DATA_FOR" : dataFor
                            };
                            onDone = function(data){
                                $('#customSelection').empty();
                                $("#customSelection").html(data.FRM_FIELD_HTML);  
                            $(".company_struct").change(function(){
                                var companyId = $(this).val();
                                dataFor = $(this).data("for");
                                var target = $(this).data("replacement");
                                var dataSends = {
                                    "FRM_FIELD_DATA_FOR": dataFor,
                                    "FRM_FIELD_OID": oid,
                                    "command": command,
                                    "company_id" : companyId
                                    
                                }
                                var onDones = function(data){
                                    
                                };
                                var onSuccesses = function(data){
                                    
                                };
                                getDataFunction(onDones, onSuccesses, approot, command, dataSends, "AjaxEmpDoc", target, false, "json");
                            });
                            };
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, false, "json");
                        });
                        
                        $("form#recipientform").unbind().submit(function(){
                            var currentBtnHtml = $("#btnrecipient").html();
                            $("#btnrecipient").html("Submitting...").attr({"disabled":"true"});
                            var generaldatafor = $("#recipientdatafor").val();
                            onDone = function(data){
                                //alert(target2);
                                $(target2).html(data.FRM_FIELD_HTML_2);
                                addtext("a#addText");
                                addempsingle("a#addEmpSingle");
                                addrecipient("a#addRecipient");
                                addempdata("a#addEmpAward");
                            };
                            if($(this).find(".has-error").length == 0){
                                onSuccess = function(data){
                                    $("#btnrecipient").removeAttr("disabled").html(currentBtnHtml);
                                    $("#addrecipient").modal("hide");
                                };

                                dataSend = $(this).serialize();
                                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                            }else{
                                $("#btnrecipient").removeAttr("disabled").html(currentBtnHtml);
                            }

                            return false;
                        });
                        //loadTemplate(".datachange");
                        //CKEDITOR.replace('editor1');
                        
                        
		    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addrecipient-body", false, "json");
		});
	    };
            
            //SHOW ADD OR EDIT FORM
	    approve = function(elementId){
		$(elementId).unbind().click(function(){
                    var confirmTextSingleApprove = "Are you want to approve these document?";
                    if (confirm(confirmTextSingleApprove)) {
                        $("#approval").modal("show");
                        command = $("#command").val();
                        oid = $(this).data('oid');
                        dataFor = $(this).data('for');
                        var template = $(this).data('template1');
                        $("#approvaldatafor").val(dataFor);
                        $("#approvalform #oid").val(oid);
                        //SET TITLE MODAL
                        $(".approval-title").html("Approval");


                        dataSend = {
                            "FRM_FIELD_DATA_FOR"	: dataFor,
                            "FRM_FIELD_OID"		 : oid,
                            "command"		 : command,
                            "template"               : template
                        }
                        onDone = function(data){
                            doApproval(".doapprove");
                        };
                        onSuccess = function(data){

                        };
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".approval-body", false, "json");
                    } else {
                        var confirmTextSingleReview = "Do you want to Review the Document?";
                        if (confirm(confirmTextSingleReview)) {
                            $("#review").modal("show");
                            command = $("#command").val();
                            oid = $(this).data('oid');
                            dataFor = 'review';
                            $("#reviewdatafor").val(dataFor);
                            $("#reviewform #oid").val(oid);
                            //SET TITLE MODAL
                            $(".review-title").html("Review Document");
                            $("#review .modal-dialog").css("width", "80%");


                            dataSend = {
                                "FRM_FIELD_DATA_FOR"	: dataFor,
                                "FRM_FIELD_OID"		 : oid,
                                "command"               : command
                            }
                            onDone = function(data){
                                $("#reviewform #note").wysihtml5();
                            };
                            onSuccess = function(data){

                            };
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".review-body", false, "json");
                        }
                    }
		});
	    };
            //SHOW ADD OR EDIT FORM
	    var approved = function(elementId){
		$(elementId).unbind().click(function(){
                        $("#approval").modal("show");
                        command = $("#command").val();
                        oid = $(this).data('oid');
                        dataFor = $(this).data('for');
                        var template = $(this).data('template1');
                        $("#approvaldatafor").val(dataFor);
                        $("#approvalform #oid").val(oid);
                        //SET TITLE MODAL
                        $(".approval-title").html("Approval");


                        dataSend = {
                            "FRM_FIELD_DATA_FOR"	: dataFor,
                            "FRM_FIELD_OID"		 : oid,
                            "command"		 : command,
                            "template"               : template
                        }
                        onDone = function(data){
                            
                        };
                        onSuccess = function(data){

                        };
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".approval-body", false, "json");
                    
		});
	    };
	    
            //SHOW ADD OR EDIT FORM
	    var viewreview = function(elementId){
		$(elementId).unbind().click(function(){
                        $("#viewreview").modal("show");
                        command = $("#command").val();
                        oid = $(this).data('oid');
                        dataFor = $(this).data('for');
                        var template = $(this).data('template1');
                        $("#viewreviewdatafor").val(dataFor);
                        $("#viewreviewform #oid").val(oid);
                        //SET TITLE MODAL
                        $(".viewreview-title").html("Note");
                        $("#viewreview .modal-dialog").css("width", "80%");


                        dataSend = {
                            "FRM_FIELD_DATA_FOR"	: dataFor,
                            "FRM_FIELD_OID"		 : oid,
                            "command"		 : command
                        }
                        onDone = function(data){
                           
                            
                        };
                        onSuccess = function(data){

                        };
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".viewreview-body", false, "json");
                    
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
			    if(dataFor == "empDoc"){
				onDone = function(data){
				    runDataTables();
				};
			    }
			    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
			    $(this).removeAttr("disabled").html(currentHtml);
			}else{
			    $(this).removeAttr("disabled").html(currentHtml);
			}
		    }

		});
	    };
            
            //DELETE SINGLE
            deletesingle = function (elementId) {

                $(elementId).unbind().click(function () {
                    dataFor = $(this).data("for");
                    var vals = $(this).data("oid");
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
                        onDone = function (data) {
                            runDataTables();
                        };


                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                            $(this).removeAttr("disabled").html(currentHtml);
                        }
                });
            };
            
            var loadDocNumber = function(selector){
                $(selector).change(function(){
                   var datachange = $(this).val();
                   dataFor = $(this).data("for");
                   command = $("#command").val();
                   var issue_date = $("#FRM_FIELD_DATE_OF_ISSUE_STRING").val();
                   var target = $(this).data("target");
                   var target2 = $(this).data("target2");
                   $("#template_id").val(datachange);
                   dataSend = {
                       "datachange" : datachange,
                       "FRM_FIELD_DATA_FOR" : dataFor,
                       "command" : command,
                       "FRM_FIELD_DATE_OF_ISSUE_STRING" : issue_date
                   }

                   onSuccess = function(data){

                   };

                   onDone = function(data){
                      $(target).val(data.FRM_FIELD_HTML);
                      $(target2).html(data.FRM_FIELD_HTML_2);
//                      CKEDITOR.instances[target2].setData(data.FRM_FIELD_HTML_2);
                        addempsingle("a#addEmpSingle");
                        addtext("a#addText");
                        addrecipient("a#addRecipient");
                        addempdata("a#addEmpAward");
//                        $('.addEmpSingle').click(function(){
//                            alert('.');
//                            $("#addempsingle").modal("show");
//                        });
                   };

                   
                   getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, false, "json");

                });

            };
            
            var doApproval = function(selector){
                $(selector).change(function(){
                   $("#login").modal("show"); 
                   var approver = $(this).val();
                   dataFor = $(this).data("for");
                   command = $("#command").val();
                   oid = $(this).data("oid");
                   var title = $(this).data("title");
                   var flowindex = $(this).data("flowindex");
                   var index = $(this).data("index");
                   $("#logindatafor").val(dataFor);
                   dataSend = {
                       "FRM_FIELD_DATA_FOR" : dataFor,
                       "FRM_FIELD_OID" : oid,
                       "command" : command,
                       "oidApprover" : approver,
                       "flowTitle" : title,
                       "flowIndex" : flowindex,
                       "flowIndex" : index
                   }

                   onSuccess = function(data){

                   };

                   onDone = function(data){
                   };

                   
                   getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".login-body", false, "json");

                });

            };
            
            $("#empname").keyup(function(){
                var keyword = $(this).val();
                var dataFor = $("#singledatafor").val();
                var object = $("#object").val();
                var template = $("#template_id").val();
                var empId = $("#empId").val();
                var oid = $("#oid").val();
                dataSend = {
                    "keyword" : keyword,
                    "FRM_FIELD_DATA_FOR" : dataFor,
                    "object" : object,
                    "template" : template,
                    "empId" : empId,
                    "command" : command
                }

                onSuccess = function(data){

                };

                onDone = function(data){
                  $(".item").click(function(){
                            var object = $(this).data("object");
                            var empId = $(this).data("oid");
                            var template = $(this).data("template1");
                            var dataFor = $(this).data("for");
                            var target2 = $(this).data("target2");
                            command = '<%=Command.SAVE%>';
                            
                            
                            dataSend = {
                                "object" : object,
                                "datachange" : template,
                                "empId" : empId,
                                "command" : command,
                                "FRM_FIELD_DATA_FOR" : dataFor
                            }

                            onSuccess = function(data){

                            };

                            onDone = function(data){
                                //alert(target2);
                                $(target2).html(data.FRM_FIELD_HTML_2);
                                addempsingle("a#addEmpSingle");
                                $("#empId").val(empId);
                            };


                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
                            $("#addempsingle").modal("hide");
                        });
                };


                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", ".addempsingle-body", false, "json");
            });
	    
	    //FUNCTION FOR DATA TABLES
	    callBackDataTables = function(){
		addeditgeneral(".btneditgeneral");
                editormodal(".btneditor");
                deletesingle(".btndeletesingle");
                approve(".btnapprove");
                approved(".btnapproved");
                viewreview(".btnviewnote");
                docAcknowledge(".btnacknowledgestatus");
		iCheckBox();
	    }

	    //FORM HANDLER
            empdocForm = function () {
            validateOptions("#<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_MASTER_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_TITLE]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DOC_NUMBER]%>", 'text', 'has-error', 1, null);
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
                var oidDoc = $("#docacknowledgeform #oid").val();
                $(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
		$("#"+elementId).dataTable({"bDestroy": true,
		    "iDisplayLength": 10,
		    "bProcessing" : true,
		    "oLanguage" : {
			"sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
		    },
		    "bServerSide" : true,
		    "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate+"&FRM_FIELD_OID="+oidDoc,
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
                if (dataFor === "showAcknowledgeStatus"){
                    dataTablesOptions("#docAcknowledgeElement", "tableEmpDocAcknowledge", "AjaxDocumentAcknowledge", "listEmpGeneralDoc", callBackDataTables);
                } else {
                    dataTablesOptions("#empDocElement", "tableEmpDocElement", "AjaxEmpDoc", "listEmpDoc", callBackDataTables);
                }
	    }
	    
	    modalSetting("#addeditgeneral", "static", false, false);
	    addeditgeneral(".btnaddgeneral");
	    deletegeneral(".btndeletedoc", ".<%= FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_EMP_DOC_ID]%>");
		    
	    runDataTables();
            CKEDITOR.replace('FRM_FIELD_DETAILS');
            loginForm = function () {
                validateOptions("#pass_wd", 'text', 'has-error', 1, null);
            }

	    //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
                if(generaldatafor == "showEmpDocForm"){
		    empdocForm();
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
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#editorform").submit(function(){
		var currentBtnHtml = $("#btneditor").html();
		$("#btneditor").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
                var value = editor.getData();
                $("#editorform #FRM_FIELD_DETAILS").val(value);
		if(generaldatafor == "showEmpDocForm"){
		    //divisionForm();
		    onDone = function(data){
			runDataTables();
		    };
		}
                if(generaldatafor == "editor"){
		    //divisionForm();
		    onDone = function(data){
			runDataTables();
		    };
		}


		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btneditor").removeAttr("disabled").html(currentBtnHtml);
			$("#editor").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
		}else{
		    $("#btneditor").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#loginform").submit(function(){
		var currentBtnHtml = $("#btnlogin").html();
		$("#btnlogin").html("Login...").attr({"disabled":"true"});
		var generaldatafor = $("#logindatafor").val();
                loginForm();
                    onDone = function(data){
			if(data.FRM_FIELD_HTML == 1){
                            alert("Login Success!");
                        }else{
                            alert("Login Failed Please Input Your Password Correctly!");
                        }
                        runDataTables();
		    };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btnlogin").removeAttr("disabled").html(currentBtnHtml);
			$("#login").modal("hide");
                        $("#approval").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, false, "json");
		}else{
		    $("#btnlogin").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#reviewform").submit(function(){
		var currentBtnHtml = $("#btnreview").html();
		$("#btnreview").html("Submitting...").attr({"disabled":"true"});
		var generaldatafor = $("#reviewdatafor").val();
                    onDone = function(data){
	                runDataTables();
		    };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btnreview").removeAttr("disabled").html(currentBtnHtml);
			$("#review").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
		}else{
		    $("#btnreview").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            $("form#viewreviewform").submit(function(){
		var currentBtnHtml = $("#btnreview").html();
		$("#btnviewreview").html("Confirming...").attr({"disabled":"true"});
		var generaldatafor = $("#viewreviewdatafor").val();
                    onDone = function(data){
	                runDataTables();
		    };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btnviewreview").removeAttr("disabled").html(currentBtnHtml);
			$("#viewreview").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpDoc", null, true, "json");
		}else{
		    $("#btnviewreview").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            var docAcknowledge = function(elementId){
		$(elementId).unbind().click(function(){
		    $("#docacknowledge").modal("show");
		    command = $("#command").val();
		    oid = $(this).data('oid');
		    dataFor = $(this).data('for');
		    $("#docacknowledgeform #generaldatafor").val(dataFor);
		    $("#docacknowledgeform #oid").val(oid);
                    $("#docacknowledge .modal-dialog").css("width", "70%");
		    //SET TITLE MODAL
                    $(".docacknowledge-title").html("Acknowledge List");
		
		    dataSend = {
			"FRM_FIELD_DATA_FOR"	 : dataFor,
			"FRM_FIELD_OID"		 : oid,
			"command"		 : command
		    }
		    onDone = function(data){
                        runDataTables("showAcknowledgeStatus"); 
                    };
		    onSuccess = function(data){
			
		    };
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxDocumentAcknowledge", ".docacknowledge-body", false, "json");
		});
	    };
            
            
            
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
      <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
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
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
<!--                                <textarea id="editor2"></textarea>-->
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
    <div id="editor" class="modal fade nonprint" tabindex="-1" style="overflow-y: auto">
          <div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="editor-title"></h4>
		</div>
                <form id="editorform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="empId" id="empId">
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <textarea name='<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS]%>' id='<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DETAILS]%>' style='visibility: hidden; display: none;'></textarea>
				<div class="box-body editor-body">
                                    
				</div>
                                
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btneditor"><i class="fa fa-check"></i> Save</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="addempsingle" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addempsingle-title"></h4>
		</div>
                <form id="empsingleform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="singledatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="template_id" id="template_id">
                    <input type="hidden" name="object" id="object">
                    
		    <div class="modal-body " style="height: 500px;overflow-y: auto;">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body">
                                    <input class="form-control" placeholder="search employee..." type="text" name="emp_key" size="50" id="empname"/>
                                </div>
                                <div class="box-body addempsingle-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnaddsingle"><i class="fa fa-check"></i> Submit</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="addtext" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addtext-title"></h4>
		</div>
                <form id="textform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="textdatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="object" id="objectField">
                    <input type="hidden" name="objectType" id="objectType">
                    <input type="hidden" name="objectClass" id="objectClass">
                    <input type="hidden" name="objectStatusField" id="objectStatusField">
                    <input type="hidden" name="target" id="target">
                    
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body addtext-body">
<!--                                    <input type="text" name="FRM_FIELD_VALUE" value="" id="FRM_FIELD_VALUE" class="form-control"/>-->
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnaddtext"><i class="fa fa-check"></i> Submit</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="addempdata" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addempdata-title"></h4>
		</div>
                <form id="empdataform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="empdatadatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="FRM_FIELD_EMP_DATA_ID" id="empdataid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="target" id="target">
                    <input type="hidden" name="object" id="object">
                    
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body addempdata-body">
<!--                                    <input type="text" name="FRM_FIELD_VALUE" value="" id="FRM_FIELD_VALUE" class="form-control"/>-->
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnaddempdata"><i class="fa fa-check"></i> Submit</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>                
    <div id="addrecipient" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addrecipient-title"></h4>
		</div>
                <form id="recipientform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="recipientdatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="datachange" id="datachange">
                    <input type="hidden" name="object" id="object">
                    <input type="hidden" name="<%=FrmEmpDoc.fieldNames[FrmEmpDoc.FRM_FIELD_DATE_OF_ISSUE_STRING]%>" id="issueDate">
                    
		    <div class="modal-body " style="height: 500px;overflow-y: auto;">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body addrecipient-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnaddrecipient"><i class="fa fa-check"></i> Add</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>                
    <div id="approval" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="approval-title"></h4>
		</div>
                <form id="approvalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="approvaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body approval-body">
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="login" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="login-title"></h4>
		</div>
                <form id="loginform" enctype="multipart/form-data" class="form-horizontal">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="logindatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body login-body">
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnlogin"><i class="fa fa-check"></i> Login</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    <div id="review" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="review-title"></h4>
		</div>
                <form id="reviewform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="reviewdatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="FRM_FIELD_OID" id="oid">
		    
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body review-body">
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btnreview"><i class="fa fa-check"></i> Submit</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>  
    <div id="viewreview" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="viewreview-title"></h4>
		</div>
                <form id="viewreviewform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="viewreviewdatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="FRM_FIELD_OID" id="oid">
		    
                    
		    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
                                <div class="box-body viewreview-body">
                                </div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-success" id="btnviewreview"><i class="fa fa-check"></i> Confirm</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger" id="btnClose"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div> 
    <div id="docacknowledge" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="docacknowledge-title"></h4>
		</div>
                <form id="docacknowledgeform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <div class="modal-body ">
			
				<div class="box-body docacknowledge-body">
                                    
				</div>
			    
			
		    </div>
		    <div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>                    
   </div><!-- ./wrapper -->
    </body>
</html>
