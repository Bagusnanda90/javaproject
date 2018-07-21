<%@page import="com.dimata.harisma.util.Sms"%>
<%@page import="com.dimata.harisma.form.service.notification.FrmNotification"%>
<%@page import="com.dimata.harisma.utility.machine.NotificationService"%>
<%@page import="com.dimata.util.Command"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<%
    String approot = request.getContextPath();
    String strStatusManualAttd = "";
    String icon = "";
    NotificationService notificationService = NotificationService.getInstance(true);
    if (notificationService.getStatus()) {
        strStatusManualAttd = "Stop";
        icon = "fa-stop";
    } else {
        strStatusManualAttd = "Run";
        icon = "fa-play-circle-o";
    }
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Dimata Hairisma | Notification</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <%@ include file="../../template/css.jsp" %>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    <style>
        table{
            margin: 0 auto;
            width: 100%;
            clear: both;
            border-collapse: collapse;
            table-layout: fixed; 
            word-wrap:break-word; 
          }
    </style>    
    </head>
    <!--
    BODY TAG OPTIONS:
    =================
    Apply one or more of the following classes to get the
    desired effect
    |---------------------------------------------------------|
    | SKINS         | skin-blue                               |
    |               | skin-black                              |
    |               | skin-purple                             |
    |               | skin-yellow                             |
    |               | skin-red                                |
    |               | skin-green                              |
    |---------------------------------------------------------|
    |LAYOUT OPTIONS | fixed                                   |
    |               | layout-boxed                            |
    |               | layout-top-nav                          |
    |               | sidebar-collapse                        |
    |               | sidebar-mini                            |
    |---------------------------------------------------------|
    -->
    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">

            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>         

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Notification
<!--                        <small>Optional description</small>-->
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> System</a></li>
                        <li class="active">Notification</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box-body">
                                <div class="callout callout-info">
                                    <button class="btn btn-default btn-run-notif" data-for="notif">
                                        <i id="run" class="fa <%=icon%>">&nbsp<%=strStatusManualAttd%></i>
                                    </button>
                                    Click this button to <b><%=strStatusManualAttd%></b> the Notification Service
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class='box box-primary'>
                                <div class='box-header'>
                                    Notification List
                                </div>
                                <div class="box-body">
                                    <div id="notif-element">
                                        <table class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th style="width: 2%"></th>
                                                    <th style="width: 3%">No</th>
                                                    <th style="width: 8%">Modul Name</th>
                                                    <th style="width: 5%">Type</th>
                                                    <th style="width: 5%">Time Distance</th>
                                                    <th style="width: 5%">Time Send</th>
                                                    <th style="width: 7%">Subject</th>
                                                    <th style="width: 10%">Text</th>
                                                    <th style="width: 5%">Recipient</th>
                                                    <th style="width: 5%">Number</th>
                                                    <th style="width: 5%">cc</th>
                                                    <th style="width: 5%">bcc</th>
                                                    <th style="width: 5%">All Employee</th>
                                                    <th style="width: 10%">Action</th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
                                <div class='box-footer'>
                                    <button class="btn btn-primary btn-add-general" data-oid="0" data-for="show_notif_form">
                                        <i class="fa fa-plus"></i> Add Notification
                                    </button>
                                    <button class="btn btn-danger btn-delete-notif" data-for="notif">
                                        <i class="fa fa-trash"></i> Delete
                                    </button>
                                </div>
                            </div>
                        </div><!-- ./col -->
                      </div><!-- /.row -->
                </section><!-- /.content -->
            </div><!-- /.content-wrapper -->

            <!-- Main Footer -->
            <%@include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
            
            <script type="text/javascript">
                $(document).ready(function(){
                    //SET ACTIVE MENU
                   /* var activeMenu = function(parentId, childId){
                        $(parentId).addClass("active").find(".treeview-menu").slideDown();
                        $(childId).addClass("active");
                    }*/

                   // activeMenu("#masterdata", "#customer");
                    
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
                    var addEditGeneral = null;
                    var custForm = null;
                    var runGeneral = null;
                    var deleteGeneral = null;

                    //MODAL SETTING
                    var modalSetting = function(elementId, backdrop, keyboard, show){
                        $(elementId).modal({
                            backdrop	: backdrop,
                            keyboard	: keyboard,
                            show	: show
                        });
                    };

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
                    addEditGeneral = function(elementId){
                        $(elementId).click(function(){
                            $("#add-edit-general").modal("show");
                            $(".modal-dialog").css("width", "85%");
                            command = $("#command").val();
                            oid = $(this).data('oid');
                            dataFor = $(this).data('for');
                            $("#general-data-for").val(dataFor);
                            $("#oid").val(oid);

                            //SET TITLE MODAL
                            if(oid != 0){
                                if(dataFor == 'show_notif_form'){
                                    $(".add-edit-general-title").html("Edit Notification");
                                }

                            }else{
                                if(dataFor == 'show_notif_form'){
                                    $(".add-edit-general-title").html("Add Notification");
                                }
                            }


                            dataSend = {
                                "FRM_FIELD_DATA_FOR"	: dataFor,
                                "FRM_FIELD_OID"		 : oid,
                                "command"		 : command
                            }
                            onDone = function(data){
                                    $('#checkAllEmployee').change(function(){
                                        if ($('#checkAllEmployee').is(':checked') == true){
                                            $('#email').val('').prop('disabled', true);
                                        } else {
                                            $('#email').val('').prop('disabled', false);
                                       }
                                    });
                                    $("#FRM_FIELD_TEXT").wysihtml5();
                                    $('#timepicker').datetimepicker({
                                        format: 'HH:mm'
                                    });
                                    
                            };
                            onSuccess = function(data){

                            };
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", ".add-edit-general-body", false, "json");
        
                        });
                    };

                    runGeneral = function(elementId){
                        $(elementId).click(function(){
                            dataFor = $(this).data("for");
                            command = <%=Command.UPDATE%>;
                            $("#general-data-for").val(dataFor);
                            var run = $("#run").html();
                            var hasil =run.split(";");
                            
                            dataSend = {
                                "FRM_FIELD_DATA_FOR"	: dataFor,
                                "FRM_FIELD_STATUS"      : hasil[1],
                                "command"		: command
                            }
                            onDone = function(data){
                                
                            };
                            onSuccess = function(data){

                            };
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", ".btn-run-notif", false, "json");
                        });
                    };
                    
                    var onOff = function(elementId){
                        $(elementId).click(function(){
                            dataFor = $(this).data("for");
                            oid = $(this).data('oid');
                            command = <%=Command.UPDATE%>;
                            $("#general-data-for").val(dataFor);
                            
                            dataSend = {
                                "FRM_FIELD_DATA_FOR"	: dataFor,
                                "FRM_FIELD_OID"		 : oid,
                                "command"		: command
                            }
                            onDone = function(data){
                                runDataTables();
                            };
                            onSuccess = function(data){

                            };
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", null, false, "json");
                        });
                    };
                    
                    //DELETE GENERAL
                    deleteGeneral = function(elementId, checkboxelement){

                        $(elementId).unbind().click(function(){
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
                                    onDone = function(data){
                                        runDataTables();
                                    };
                                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", null, true, "json");
                                    $(this).removeAttr("disabled").html(currentHtml);
                                }else{
                                    $(this).removeAttr("disabled").html(currentHtml);
                                }
                            }

                        });
                    };

                    //DELETE SINGLE
                    var deletesingle = function (elementId) {

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


                                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", null, true, "json");
                                $(this).removeAttr("disabled").html(currentHtml);
                            } else {
                                    $(this).removeAttr("disabled").html(currentHtml);
                                }
                        });
                    };

                    //FUNCTION FOR DATA TABLES
                    callBackDataTables = function(){
                        addEditGeneral(".btn-edit-general");
                        deletesingle(".btndeletesingle");
                        onOff(".btnonoff")
                        iCheckBox();
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
                        var table = $('#'+elementId).DataTable();
                        table.destroy();
                        var datafilter = $("#datafilter").val();
                        var privUpdate = $("#privUpdate").val();
                        $(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
                        $("#"+elementId).dataTable({"bDestroy": true,
                            "iDisplayLength": 10,
                            "bProcessing" : true,
                            "scrollX" : true,
                            "oLanguage" : {
                                "sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
                            },
                            "bServerSide" : true,
                            "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER="+datafilter+"&FRM_FIELD_DATA_FOR="+dataFor+"&privUpdate="+privUpdate,
                            aoColumnDefs: [
                                {
                                   bSortable: false,
                                   aTargets: [ -1, -2 ]
                                },
                                { "width": "2%", "targets": 0 },
                                { "width": "3%", "targets": 1 },
                                { "width": "8%", "targets": 2 },
                                { "width": "5%", "targets": 3 },
                                { "width": "5%", "targets": 4 },
                                { "width": "5%", "targets": 5 },
                                { "width": "7%", "targets": 6 },
                                { "width": "10%", "targets": 7 },
                                { "width": "5%", "targets": 8 },
                                { "width": "5%", "targets": 9 },
                                { "width": "5%", "targets": 10 },
                                { "width": "5%", "targets": 11 },
                                { "width": "5%", "targets": 12 },
                                { "width": "10%", "targets": 13 }
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
                        dataTablesOptions("#notif-element", "tableNotifElement", "AjaxNotification", "list_notif", callBackDataTables);
                    }

                    modalSetting("#add-edit-general", "static", false, false);
                    addEditGeneral(".btn-add-general");
                    deleteGeneral(".btn-delete-notif",  ".<%= FrmNotification.fieldNames[FrmNotification.FRM_FIELD_NOTIFICATION_ID]%>");
                    runGeneral(".btn-run-notif");
                    //deleteGeneral(".btn-delete-notif", ".<!--%= FrmCustomer.fieldNames[FrmCustomer.FRM_FIELD_PROJ_CUSTOMER_ID]%-->");

                    runDataTables();

                    //FORM SUBMIT
                    $("form#general-form").submit(function(){
                        var currentBtnHtml = $("#btn-general-form").html();
                        $("#btn-general-form").html("Saving...").attr({"disabled":"true"});
                        var generaldatafor = $("#general-data-for").val();
                            
                        onDone = function(data){
                            runDataTables();
                        };
                        

                        if($(this).find(".has-error").length == 0){
                            onSuccess = function(data){
                                $("#btn-general-form").removeAttr("disabled").html(currentBtnHtml);
                                $("#add-edit-general").modal("hide");
                            };

                            dataSend = $(this).serialize();
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxNotification", null, true, "json");
                        }else{
                            $("#btn-general-form").removeAttr("disabled").html(currentBtnHtml);
                        }

                        return false;
                    });
                })
              </script>
              <div id="add-edit-general" class="modal fade nonprint" tabindex="-1">
                <div class="modal-dialog nonprint">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="add-edit-general-title"></h4>
                        </div>
                        <form id="general-form" enctype="multipart/form-data">
                            <input type="hidden" name="FRM_FIELD_DATA_FOR" id="general-data-for">
                            <input type="hidden" name="command" value="<%= Command.SAVE %>">
                            <input type="hidden" name="FRM_FIELD_OID" id="oid">
                            <div class="modal-body ">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="box-body add-edit-general-body">
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" id="btn-general-form"><i class="fa fa-check"></i> Save</button>
                                <button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>            
        </div><!-- ./wrapper -->
    </body>
</html>
