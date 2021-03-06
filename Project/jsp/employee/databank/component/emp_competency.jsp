
<div class="tab-pane" id="tab_competencies"  >
    <a> <h3>Competency List : </h3></a>

    <hr>
    <div id="empCompetencyElement">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Competency</th>
                    <th>Level Value</th>					
                    <th>Date of Achievment</th>
                    <th>Special Achievment</th>
                    <th>Action</th>
                </tr>
            </thead>
        </table>
    </div>
    <div class='box-footer'>
        <button class="btn btn-primary btnaddgeneral" data-oid="0" data-for="showempcompetencyform">
            <i class="fa fa-plus"></i> Add Competency
        </button>
        <button class="btn btn-danger btndeletedivision" data-for="division">
            <i class="fa fa-trash"></i> Delete
        </button>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        //SET ACTIVE MENU
        var activeMenu = function (parentId, childId) {
            $(parentId).addClass("active").find(".treeview-menu").slideDown();
            $(childId).addClass("active");
        }

        activeMenu("#masterdata", "#empcompetency");


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

                } else {
                    if (dataFor == 'showempcompetencyform') {
                        $(".addeditgeneral-title").html("Add Employee Competency");
                    }
                }


                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "FRM_FIELD_OID": oid,
                    "command": command,
                    "empName": <%=emplx.getFullName()%>,
                    "userId" : <%=appUserIdSess%>
                }
                onDone = function (data) {
                    datePicker(".datepicker", "yyyy-mm-dd");
                    $(".colorpicker").colorpicker();
                };
                onSuccess = function (data) {

                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpCompetency", ".addeditgeneral-body", false, "json");
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
                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpCompetency", null, true, "json");
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

        function runDataTables() {
            dataTablesOptions("#empCompetencyElement", "tableempCompetencyElement", "AjaxEmpCompetency", "listempcompetency", callBackDataTables);
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
                    runDataTables();
                };
            }


            if ($(this).find(".has-error").length == 0) {
                onSuccess = function (data) {
                    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                    $("#addeditgeneral").modal("hide");
                };

                dataSend = $(this).serialize();
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmpCompetency", null, true, "json");
            } else {
                $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
            }

            return false;
        });

    })
</script>
