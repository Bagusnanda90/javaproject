
<%@page import="com.dimata.harisma.form.employee.FrmExperience"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmpLanguage"%>
<%@page import="com.dimata.harisma.form.employee.FrmFamilyMember"%>
<script type="text/javascript">
    $(document).ready(function () {
        //SET ACTIVE MENU
        var activeMenu = function (parentId, childId) {
            $(parentId).addClass("active").find(".treeview-menu").slideDown();
            $(childId).addClass("active");
        }

        activeMenu("#employee", "#employee_edit");


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
        var servletName = null;

        //MODAL SETTING
        var modalSetting = function (elementId, backdrop, keyboard, show) {
            $(elementId).modal({
                backdrop: backdrop,
                keyboard: keyboard,
                show: show
            });
        };

        function datePicker(contentId, formatDate) {
            $(contentId).datepicker({
                format: formatDate
            });
            $(contentId).on('changeDate', function (ev) {
                $(this).datepicker('hide');
            });
        }

        var getDataFunction = function (onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType) {
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
                onDone: function (data) {
                    onDone(data);
                },
                onSuccess: function (data) {
                    onSuccess(data);
                },
                approot: approot,
                dataSend: dataSend,
                servletName: servletName,
                dataAppendTo: dataAppendTo,
                notification: notification,
                ajaxDataType: dataType
            });
        }

        //SHOW ADD OR EDIT FORM
        addeditgeneral = function (elementId) {
            $(elementId).click(function () {
                $("#addeditgeneral").modal("show");
                command = $("#command").val();
                oid = $(this).data('oid');
                dataFor = $(this).data('for');
                $("#generaldatafor").val(dataFor);
                $("#oid").val(oid);

                //SET TITLE MODAL
                if (oid != 0) {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Edit Employee Competency");
                    }
                    if (dataFor == 'showempfamilyform') {
                        $(".addeditgeneral-title").html("Edit Family Relationship");
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Edit Employee Language");
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Edit Employee Experience");
                    }
                    

                } else {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Add Employee Competency");
                    }
                    if (dataFor == 'showempfamilyform') {
                        $(".addeditgeneral-title").html("Add Family Relationship");
                    }
                    if (dataFor == 'showemplanguageform') {
                        $(".addeditgeneral-title").html("Add Employee Language");
                    }
                    if (dataFor == 'showempexperienceform') {
                        $(".addeditgeneral-title").html("Add Employee Experience");
                    }
                }
                
                //Set Servlet Name
                if (dataFor == 'showempcompetencyform'){
                    servletName = "AjaxEmpCompetency";
                }
                if (dataFor == 'showempfamilyform'){
                    servletName = "AjaxEmpFamily";
                }
                if (dataFor == 'showemplanguageform'){
                    servletName = "AjaxEmpLanguage";
                }
                if (dataFor == 'showempexperienceform'){
                    servletName = "AjaxEmpExperience";
                }


                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "command": command,
                    "empName": <%=emplx.getFullName()%>,
                    "userId" : <%=appUserIdSess%>,
                    "empId"  : <%=employee.getOID%>
                    
                }
                onDone = function (data) {
                    datePicker(".datepicker", "yyyy-mm-dd");
                    $(".colorpicker").colorpicker();
                };
                onSuccess = function (data) {

                };
                
                getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, ".addeditgeneral-body", false, "json");
            });
        };

        //DELETE GENERAL
        deletegeneral = function (elementId, checkboxelement) {

            $(elementId).click(function () {
                dataFor = $(this).data("for");
                var checkBoxes = (checkboxelement);
                var vals = "";
                $(checkboxelement).each(function (i) {

                    if ($(this).is(":checked")) {
                        if (vals.length == 0) {
                            vals += "" + $(this).val();
                        } else {
                            vals += "," + $(this).val();
                        }
                    }
                });

                var confirmText = "Are you sure want to delete these data?";
                if (vals.length == 0) {
                    alert("Please select the data");
                } else {
                    command = <%= Command.DELETEALL%>;
                    var currentHtml = $(this).html();
                    $(this).html("Deleting...").attr({"disabled": true});
                    if (confirm(confirmText)) {
                        dataSend = {
                            "FRM_FIELD_DATA_FOR": dataFor,
                            "FRM_FIELD_OID_DELETE": vals,
                            "command": command
                        };
                        onSuccess = function (data) {

                        };
                        if (dataFor == "empcompetency") {
                            onDone = function (data) {
                                runDataTables();
                            };
                        }
                        
                        //Set Servlet Name
                        if (dataFor == 'showempcompetencyform'){
                            servletName = "AjaxEmpCompetency";
                        }
                        if (dataFor == 'showempfamilyform'){
                            servletName = "AjaxEmpFamily";
                        }
                        if (dataFor == 'showemplanguageform'){
                            servletName = "AjaxEmpLanguage";
                        }
                        if (dataFor == 'showempexperienceform'){
                            servletName = "AjaxEmpExperience";
                        }
                        
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                        $(this).removeAttr("disabled").html(currentHtml);
                    }
                }

            });
        };

        //FUNCTION FOR DATA TABLES
        callBackDataTables = function () {
            addeditgeneral(".btneditgeneral");
            iCheckBox();
        }

        //FORM HANDLER
        empCompetencyForm = function () {
            validateOptions("#<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_COMPETENCY_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_ID]%>", 'text', 'has-error', 1, null);
        }
        empFamilyForm = function () {
            validateOptions("#<%= FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]%>", 'text', 'has-error', 1, null);
        }
        empLanguageForm = function () {
            validateOptions("#<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN]%>", 'text', 'has-error', 1, null);
        }
        empExperienceForm = function () {
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_COMPANY_NAME]%>", 'text', 'has-error', 1, null);
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_START_DATE]%>", 'text', 'has-error', 1, null);        
            validateOptions("#<%= FrmExperience.fieldNames[FrmExperience.FRM_FIELD_END_DATE]%>", 'text', 'has-error', 1, null);
        }

        //VALIDATE FORM
        function validateOptions(elementId, checkType, classError, minLength, matchValue) {

            /* OPTIONS
             * minLength    : INT VALUE,
             * matchValue   : STRING OR INT VALUE,
             * classError   : STRING VALUE,
             * checkType    : STRING VALUE ('text' for default, 'email' for email check
             */

            $(elementId).validate({
                minLength: minLength,
                matchValue: matchValue,
                classError: classError,
                checkType: checkType
            });
        }

        //iCheck
        iCheckBox = function () {
            $("input[type='checkbox'], input[type='radio']").iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });


        }


        //DATA TABLES SETTING
        function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables) {
            var datafilter = $("#datafilter").val();
            var privUpdate = $("#privUpdate").val();
            //Set Servlet Name
            if (dataFor == 'showempcompetencyform'){
                servletName = "AjaxEmpCompetency";
            }
            if (dataFor == 'showempfamilyform'){
                servletName = "AjaxEmpFamily";
            }
            if (dataFor == 'showemplanguageform'){
                servletName = "AjaxEmpLanguage";
            }
            if (dataFor == 'showempexperienceform'){
                servletName = "AjaxEmpExperience";
            }
            $(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id': elementId});
            $("#" + elementId).dataTable({"bDestroy": true,
                "iDisplayLength": 10,
                "bProcessing": true,
                "oLanguage": {
                    "sProcessing": "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
                },
                "bServerSide": true,
                "sAjaxSource": "<%= approot%>/" + servletName + "?command=<%= Command.LIST%>&FRM_FIELD_DATA_FILTER=" + datafilter + "&FRM_FIELD_DATA_FOR=" + dataFor + "&privUpdate=" + privUpdate,
                aoColumnDefs: [
                    {
                        bSortable: false,
                        aTargets: [-1, -2]
                    }
                ],
                "initComplete": function (settings, json) {
                    if (callBackDataTables != null) {
                        callBackDataTables();
                    }
                },
                "fnDrawCallback": function (oSettings) {
                    if (callBackDataTables != null) {
                        callBackDataTables();
                    }
                },
                "fnPageChange": function (oSettings) {

                }
            });

            $(elementIdParent).find("#" + elementId + "_filter").find("input").addClass("form-control");
            $(elementIdParent).find("#" + elementId + "_length").find("select").addClass("form-control");
            $("#" + elementId).css("width", "100%");
        }

        function runDataTables(dataFor) {
            var listData = "";
            if (dataFor == 'showempcompetencyform'){
                servletName = "AjaxEmpCompetency";
                listData = "listempcompetency";
            }
            if (dataFor == 'showempfamilyform'){
                servletName = "AjaxEmpFamily";
                listData = "listempfamily";
            }
            if (dataFor == 'showemplanguageform'){
                servletName = "AjaxEmpLanguage";
                listData = "listemplanguage";
            }
            if (dataFor == 'showempexperienceform'){
                servletName = "AjaxEmpExperience";
                listData = "listempexperience";
            }
            dataTablesOptions("#element", "tableElement", servletName, listData, callBackDataTables);
        }

        modalSetting("#addeditgeneral", "static", false, false);
        addeditgeneral(".btnaddgeneral");
        deletegeneral(".btndeleteempcompetency", ".<%= FrmEmployeeCompetency.fieldNames[FrmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID]%>");

        runDataTables();

        //FORM SUBMIT
        $("form#generalform").submit(function () {
            var currentBtnHtml = $("#btngeneralform").html();
            $("#btngeneralform").html("Saving...").attr({"disabled": "true"});
            var generaldatafor = $("#generaldatafor").val();
            if (generaldatafor == "showempcompetencyform") {
                empCompetencyForm();
                onDone = function (data) {
                    runDataTables(generaldatafor);
                };
            }
            //Set Servlet Name
            if (generaldatafor == 'showempcompetencyform'){
                servletName = "AjaxEmpCompetency";
            }
            if (generaldatafor == 'showempfamilyform'){
                servletName = "AjaxEmpFamily";
            }
            if (generaldatafor == 'showemplanguageform'){
                servletName = "AjaxEmpLanguage";
            }
            if (generaldatafor == 'showempexperienceform'){
                servletName = "AjaxEmpExperience";
            }


            if ($(this).find(".has-error").length == 0) {
                onSuccess = function (data) {
                    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                    $("#addeditgeneral").modal("hide");
                };

                dataSend = $(this).serialize();
                getDataFunction(onDone, onSuccess, approot, command, dataSend, servletName, null, true, "json");
            } else {
                $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
            }

            return false;
        });

    })
</script>
