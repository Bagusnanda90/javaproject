<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Section"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String approot = request.getContextPath();
    String inEmpId = FRMQueryString.requestString(request, "inEmpId");
//    SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
//    FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
//    frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);    
//    Date birthFrom = searchSpecialQuery.getDtBirthFrom();
//    Date birthTo = searchSpecialQuery.getDtBirthTo();
//    
//    String DATE_FORMAT_NOW = "yyyy-MM-dd";
//    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//    
//    String strBirthFrom = sdf.format(birthFrom);
//    String strBirthTo = sdf.format(birthTo);
    String whereClause = "";
    long companyId = 0;
    Vector companyList = PstCompany.list(0, 0, "", "");
    whereClause = PstDivision.fieldNames[PstDivision.FLD_VALID_STATUS]+"="+PstDivision.VALID_ACTIVE;
    Vector divisionList = PstDivision.list(0, 0, whereClause, "");
    Vector departList = PstDepartment.list(0, 0, "", "");
    Vector sectionList = PstSection.list(0, 0, "", "");
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Employee List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <%@ include file="../../template/css.jsp" %>
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            #customfilter .select2{
                margin-top: 20px;
            }
        </style>
        <style type="text/css">
            @media screen and (max-width: 768px) {
                #listdata{
                    overflow-x: auto;
                }
/*                .btn{
                    padding:3px 6px;
                    font-size:80%;
                    line-height: 1;
                    border-radius:3px;
                  }*/
            }
            p.serif {
                font-family: "Times New Roman", Times, serif;
            }

            p.sansserif {
                font-family: Arial, Helvetica, sans-serif;
            }
            .btn-cs {
                padding: 7px 12px;
                font-size: 12px;
                border-radius: 10px;
                }
            .navbar {
                border-left: medium none;
                border-radius: 0px;
                border-right: medium none;
                background-color:rgb(34,45,49);
            }
            .btn-primary-outline {
                background-color: transparent;
                border-color: #000;
              }
              
        </style>
    </head>
    
    <script language="JavaScript">

	function cmdPrint(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListPdf");
	}
        
        function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS");
	}
        
        function cmdCheckData(){
		window.open("<%=approot%>/employee/databank/export_excel/data_check.jsp");
	}
        
        function cmdSendEmail(){
                  //window.open("editPenerima.jsp","height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
		  window.open("editPenerima.jsp", "Search",  "height=550,width=1000, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");
	}
    </script>
    
    <body class="hold-transition skin-blue sidebar-mini fixed sidebar-collapse">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
                <input type="hidden" name="approot" id="approot" value="<%= approot %>">
                <section class="content-header">
                    <h1>
                        <button onclick="goBack()" class="btn btn-primary-outline" type="button" id="btnBack">
                            <i class="fa fa-arrow-left"></i>  Back
                        </button>    
                        Employee List
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Employee List</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                                    <div class="box box-primary collapsed-box">
                                
                                        <div class="box-header with-border">
                                            <h3 class="box-title">Advance Search</h3>
                                            <div class="box-tools pull-right">
                                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
                                            </div><!-- /.box-tools -->
                                        </div><!-- /.box-header -->
                                        <form id="customSearch" method="get" action="">
                                        <div class="box-body">
                                            
                                                <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
                                                <div class="row">
                                                    <div class="col-md-8 col-sm-10">
                                                        <div class="form-group">
                                                            <label class="col-md-1 col-sm-2 control-label">Filter</label>
                                                            <div class="col-md-5 col-sm-4">
                                                                <select name="filter" id="filter" class="form-control comboFilter" style="width: 100%;">
                                                                    <option value="all">All</option>
                                                                    <option value="emp_num">Employee Number</option>
                                                                    <option value="emp_name">Employee Name</option>
                                                                    <option value="company">Company</option>
                                                                    <option value="division">Division</option>
                                                                    <option value="department">Department</option>
                                                                    <option value="section">Section</option>
                                                                    <option value="position">Position</option>
                                                                    <option value="level">Level</option>
                                                                    <option value="emp_cat">Employee Category</option>
                                                                    <option value="resign">Resign Status</option>
                                                                    <option value="marital">Marital Status</option>
                                                                    <option value="race">Race</option>
                                                                    <option value="birthday">Birthday</option>
                                                                    <option value="religion">Religion</option>
                                                                </select>
                                                            </div>
                                                            <div class="col-md-1 col-sm-2">
                                                                <button id="add" class="btn btn-primary" type="button"><i class="fa fa-check"></i> Add</button>
                                                            </div>    
                                                            <div class="col-md-1 col-sm-2">
                                                                <button id="clear" class="btn btn-warning" type="button" style="display:none;"><i class="fa fa-eraser"></i> Clear</button>
                                                            </div>  
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group"  id="customfilter"></div>
                                                    </div>
                                                    
                                                </div>
                                                <div class="row">
                                                </div>
                                       
                                </div><!-- /.box-body -->
                                <div class="box-footer">
                                    <button id="search" class="btn btn-primary" type="submit"><i class='fa fa-filter'></i> Filter</button>
                                                        </div>
                                 </form>
                                                    </div>
                                                </div>
                    </div>
                                    <div class="row">                                    
                                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Employee List</h3>
                                </div>
                                <div class="box-body">
                                    <div class="row">                                    
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <button  type="button" onclick="location.href='../databank/employee_edit.jsp?command=2'" class="btn btn-success "><i class="fa fa-plus"></i>&nbsp;&nbsp;Add New Employee</button>
                                            <button type="button" id="printPDF" class="btn btn-primary"><i class="fa fa-file"></i>&nbsp;&nbsp;Print Employee</button>
                                            <button type="button" id="printXLS" class="btn btn-primary"><i class="fa fa-file"></i>&nbsp;&nbsp;Export to Excel</button>
                                            <button type="button" id="checkData" class="btn btn-primary"><i class="fa fa-file"></i>&nbsp;&nbsp;Check Data</button>
                                            <button type="button" id="sendEmail" data-for="showSendEmailForm" class="btn btn-primary"><i class="fa fa-envelope"></i>&nbsp;&nbsp;Send List to Email</button>
                                            <div>&nbsp;</div>
                                            <div>
                                            <div id="listdata">
                                                <table class="table table-striped table-bordered">
                                                    <thead>
                                                    <th>No</th>
                                                    <th>Payroll Number</th>
                                                    <th>Full Name</th>
                                                    <th>Division</th>
                                                    <th>Position</th>
                                                    <th>Gender</th>
                                                    <th>Phone</th>
                                                    <th>Join Date</th>
                                                    <th>Date of Birth</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                    </thead>
                                                </table>
                                            </div>
                                            </div>    
                                        </div>
                                    </div>
                                </div>
                                <!--div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
                                            <strong><a href="<%= approot %>/employee/databank/employee_edit.jsp?command=2"><i class="fa fa-plus"></i> Add New Employee</a></strong>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="#"><i class="fa fa-file"></i> Export to Excel</a></strong>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="#"><i class="fa fa-file"></i> Export to PDF</a></strong> 
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <strong><a href="#"><i class="fa fa-envelope"></i> Send List to Email</a></strong> 
                                        </div>
                                    </div>
                                </div-->
                            </div>
                        </div>   
                    </div>
                                            
                </section>
        </div><!-- /.content-wrapper -->
            <%@ include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <script>
            $(function () {
//                $(".comboDivision").select2({
//                    placeholder: "Division"
//                });
//                $(".comboCompany").select2({
//                    placeholder: "Company"
//                });
//                $(".comboDepartment").select2({
//                    placeholder: "Department"
//                });
//                $(".comboSection").select2({
//                    placeholder: "Section"
//                });
//                $(".comboPosition").select2({
//                    placeholder: "Position"
//                });
                $(".comboFilter").select2({
                    placeholder: "Filter"
                });
            });
            function goBack() {
                window.history.back();
            }
        </script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#databank").addClass("treeview active");
                $("#EmpData").addClass("treeview active");
                
                var approot = $("#approot").val();
                var command = $("#command").val();
                var dataSend = null;

                var oid = null;
                var dataFor = null;

                //FUNCTION VARIABLE
                var onDone = null;
                var onSuccess = null;
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
                
                var runDefault = function(){
                    dataTablesOptions("#listdata", "tablelistdata", "AjaxEmployee", "employee_list", null);
                    
                }
                runDefault();
                
                $(document).on('keyup', function(e) {
                    if (e.keyCode === 13) {
                        $('#search').click();
                    }
                });
                
                $("form#customSearch").submit(
                    function(){
                    runDefault();
                    deleteData(".deletedata");
                    return false;
                });
                
                //DATA TABLES SETTING
                
                function dataTablesOptions(elementIdParent, elementId, servletName, dataFor, callBackDataTables){
                    var empNum = $("#employee_number").val();
                    if (typeof empNum === "undefined"){
                        empNum = "";
                    }
                    var empName = $("#employee_name").val();
                    if (typeof empName === "undefined"){
                        empName = "";
                    }
                    var companyId = $("#company_id").val();
                    if (typeof companyId === "undefined"){
                        companyId = 0;
                    }
                    var divisionId = $("#division_id").val();
                    if (typeof divisionId === "undefined"){
                        divisionId = 0;
                    }
                    var departmentId = $("#department_id").val();
                    if (typeof departmentId === "undefined"){
                        departmentId = 0;
                    }
                    var sectionId = $("#section_id").val();
                    if (typeof sectionId === "undefined"){
                        sectionId = 0;
                    }
                    var positionId = $("#position_id").val();
                    if (typeof positionId === "undefined"){
                        positionId = 0;
                    }
                    var levelId = $("#level_id").val();
                    if (typeof levelId === "undefined"){
                        levelId = 0;
                    }
                    var empCatId = $("#category_id").val();
                    if (typeof empCatId === "undefined"){
                        empCatId = 0;
                    }
                    var maritalId = $("#marital_id").val();
                    if (typeof maritalId === "undefined"){
                        maritalId = 0;
                    }
                    var raceId = $("#race_id").val();
                    if (typeof raceId === "undefined"){
                        raceId = 0;
                    }
                    var religionId = $("#religion_id").val();
                    if (typeof religionId === "undefined"){
                        religionId = 0;
                    }
                    var birthMonth = $("#birthday_id").val();
                    if (typeof birthMonth === "undefined"){
                        birthMonth = 0;
                    }
                    var resign = null;
                    $(".resign").each(function (i) {
                        if ($(this).is(":checked")) {
                            resign = $(".resign:checked").val();
                        }
                    });
                    var inEmpId = "<%=inEmpId%>";
                    var dataAjaxSource = "&employee_num="+empNum+"&employee_name="+empName;
                    dataAjaxSource = dataAjaxSource + "&company_id="+companyId+"&division_id="+divisionId;
                    dataAjaxSource = dataAjaxSource + "&department_id="+departmentId+"&section_id="+sectionId;
                    dataAjaxSource = dataAjaxSource + "&position_id="+positionId+"&resign="+resign+"&inEmpId="+inEmpId;
                    dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&emp_category_id="+empCatId+"&level_id="+levelId;
                    dataAjaxSource = dataAjaxSource + "&marital_id="+maritalId+"&religion_id="+religionId+"&race_id="+raceId+"&birth_month="+birthMonth;
                    $(elementIdParent).find('table').addClass('table-bordered table-striped table-hover').attr({'id':elementId});
                    $("#"+elementId).dataTable({"bDestroy": true,
                        "iDisplayLength": 10,
                        "bFilter" : true,
                        "bProcessing" : true,
                        "oLanguage" : {
                            "sProcessing" : "<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>"
                        },
                        "bServerSide" : true,
                        "sAjaxSource" : "<%= approot %>/"+servletName+"?command=<%= Command.LIST%>&FRM_FIELD_DATA_FOR="+dataFor+dataAjaxSource,
                        aoColumnDefs: [
                            {
                               bSortable: false
                            }
                          ],
                        "initComplete": function(settings, json) {

                        },
                        "fnDrawCallback": function( oSettings ) {

                        },
                        "fnPageChange" : function(oSettings){

                        }
                    });

                    $(elementIdParent).find("#"+elementId+"_filter").find("input").addClass("form-control");
                    $(elementIdParent).find("#"+elementId+"_length").find("select").addClass("form-control");
                    $("#"+elementId).css("width","100%");
                }
                
                function modalSetting(selector, keyboard, show, backdrop){
                    $(selector).modal({
                        show : keyboard,
                        backdrop : backdrop,
                        keyboard : keyboard
                    });
                }
                
                var datePicker = function(contentId, formatDate){
                    $(contentId).datepicker({
                 format : formatDate
                    });
                    $(contentId).on('changeDate', function(ev){
                 $(this).datepicker('hide');
                    });
                };
                
                
                function resign(selector){
                    $(selector).click(function(){     
                       var resign = $("input[name=resign]:checked").val();
                       $("#resign").val(resign); 
                       runDefault();
                    })
                }
                
                var getUrlParameter = function getUrlParameter(sParam) {
                    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                        sURLVariables = sPageURL.split('&'),
                        sParameterName,
                        i;

                    for (i = 0; i < sURLVariables.length; i++) {
                        sParameterName = sURLVariables[i].split('=');

                        if (sParameterName[0] === sParam) {
                            return sParameterName[1] === undefined ? true : sParameterName[1];
                        }
                    }
                };

                      
                function sendData(url, datasend, onDone, onSuccess){
                   //alert("WORK");
                    $.ajax({
                        type    : "GET",
                        data    : datasend,
                        url     : url,
                        success : function(data){
                            onSuccess(data);
                        },
                        error: function(xhr,err){
                            alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
                            alert("responseText: "+xhr.responseText);
                        }
                    }).done(function(data){
                        onDone(data);
                    });
                    
                }
                
                function paging(selector){
                    $(selector).click(function(){
                        var start = $(this).data("start");
                        var commandlist = $(this).data("command");
                        $("#start").val(start);
                        $("#commandlist").val(commandlist);
                        runDefault();
                    })
                }
                                
                function deleteData(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       var confirmText = "Are you sure to delete these data?"
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone = function(data){
                           runDefault();
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       //$(selector).html("Please wait..").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/employee/databank/ajax/emplist_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEdit(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showposform'){
			    $(".addeditgeneral-title").html("Edit Position");
			}

                        }else{
                            if(dataFor == 'showposform'){
                                $(".addeditgeneral-title").html("Add Position");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("Please wait...");
                       
                       sendData("<%= approot %>/employee/databank/ajax/emplist_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                modalSetting("#myModal", false, false, 'static');
                modalSetting("#sendEmailModal", false, false, 'static');
                
                $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModal").modal("hide");
                        runDefault();
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/emplist_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                    
                });
                
                var scrwidth = window.screen.availWidth;
                if (scrwidth <= 360 )
                
                {
                    $("#btncustom").find(".customclass").addClass("col-xs-6").attr("style","margin-bottom:5px;");
                    $("#btncustom").find("button").addClass("form-control");
                    $("#btncustom").removeClass("col-md-12");
                }else{
                    $("#btncustom").find(".customclass").addClass("pull-left").attr("style","margin-right:5px;");
                }
                
                $("#printPDF").click(function(){
                    var empNum = $("#employee_number").val();
                    if (typeof empNum === "undefined"){
                        empNum = "";
                    }
                    var empName = $("#employee_name").val();
                    if (typeof empName === "undefined"){
                        empName = "";
                    }
                    var companyId = $("#company_id").val();
                    if (typeof companyId === "undefined"){
                        companyId = 0;
                    }
                    var divisionId = $("#division_id").val();
                    if (typeof divisionId === "undefined"){
                        divisionId = 0;
                    }
                    var departmentId = $("#department_id").val();
                    if (typeof departmentId === "undefined"){
                        departmentId = 0;
                    }
                    var sectionId = $("#section_id").val();
                    if (typeof sectionId === "undefined"){
                        sectionId = 0;
                    }
                    var positionId = $("#position_id").val();
                    if (typeof positionId === "undefined"){
                        positionId = 0;
                    }
                    var levelId = $("#level_id").val();
                    if (typeof levelId === "undefined"){
                        levelId = 0;
                    }
                    var empCatId = $("#category_id").val();
                    if (typeof empCatId === "undefined"){
                        empCatId = 0;
                    }
                    var maritalId = $("#marital_id").val();
                    if (typeof maritalId === "undefined"){
                        maritalId = 0;
                    }
                    var raceId = $("#race_id").val();
                    if (typeof raceId === "undefined"){
                        raceId = 0;
                    }
                    var religionId = $("#religion_id").val();
                    if (typeof religionId === "undefined"){
                        religionId = 0;
                    }
                    var birthMonth = $("#birthday_id").val();
                    if (typeof birthMonth === "undefined"){
                        birthMonth = 0;
                    }
                    var resign = null;
                    $(".resign").each(function (i) {
                        if ($(this).is(":checked")) {
                            resign = $(".resign:checked").val();
                        }
                    });
                    var inEmpId = "<%=inEmpId%>";
                    var dataAjaxSource = "&employee_num="+empNum+"&employee_name="+empName;
                    dataAjaxSource = dataAjaxSource + "&company_id="+companyId+"&division_id="+divisionId;
                    dataAjaxSource = dataAjaxSource + "&department_id="+departmentId+"&section_id="+sectionId;
                    dataAjaxSource = dataAjaxSource + "&position_id="+positionId+"&resign="+resign+"&inEmpId="+inEmpId;
                    dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&emp_category_id="+empCatId+"&level_id="+levelId;
                    dataAjaxSource = dataAjaxSource + "&marital_id="+maritalId+"&religion_id="+religionId+"&race_id="+raceId+"&birth_month="+birthMonth;
                    window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListPdf?command=1"+dataAjaxSource);
                });
                
                $("#checkData").click(function(){
                    var empNum = $("#employee_number").val();
                    if (typeof empNum === "undefined"){
                        empNum = "";
                    }
                    var empName = $("#employee_name").val();
                    if (typeof empName === "undefined"){
                        empName = "";
                    }
                    var companyId = $("#company_id").val();
                    if (typeof companyId === "undefined"){
                        companyId = 0;
                    }
                    var divisionId = $("#division_id").val();
                    if (typeof divisionId === "undefined"){
                        divisionId = 0;
                    }
                    var departmentId = $("#department_id").val();
                    if (typeof departmentId === "undefined"){
                        departmentId = 0;
                    }
                    var sectionId = $("#section_id").val();
                    if (typeof sectionId === "undefined"){
                        sectionId = 0;
                    }
                    var positionId = $("#position_id").val();
                    if (typeof positionId === "undefined"){
                        positionId = 0;
                    }
                    var levelId = $("#level_id").val();
                    if (typeof levelId === "undefined"){
                        levelId = 0;
                    }
                    var empCatId = $("#category_id").val();
                    if (typeof empCatId === "undefined"){
                        empCatId = 0;
                    }
                    var maritalId = $("#marital_id").val();
                    if (typeof maritalId === "undefined"){
                        maritalId = 0;
                    }
                    var raceId = $("#race_id").val();
                    if (typeof raceId === "undefined"){
                        raceId = 0;
                    }
                    var religionId = $("#religion_id").val();
                    if (typeof religionId === "undefined"){
                        religionId = 0;
                    }
                    var birthMonth = $("#birthday_id").val();
                    if (typeof birthMonth === "undefined"){
                        birthMonth = 0;
                    }
                    var resign = null;
                    $(".resign").each(function (i) {
                        if ($(this).is(":checked")) {
                            resign = $(".resign:checked").val();
                        }
                    });
                    var inEmpId = "<%=inEmpId%>";
                    var dataAjaxSource = "&employee_num="+empNum+"&employee_name="+empName;
                    dataAjaxSource = dataAjaxSource + "&company_id="+companyId+"&division_id="+divisionId;
                    dataAjaxSource = dataAjaxSource + "&department_id="+departmentId+"&section_id="+sectionId;
                    dataAjaxSource = dataAjaxSource + "&position_id="+positionId+"&resign="+resign+"&inEmpId="+inEmpId;
                    dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&emp_category_id="+empCatId+"&level_id="+levelId;
                    dataAjaxSource = dataAjaxSource + "&marital_id="+maritalId+"&religion_id="+religionId+"&race_id="+raceId+"&birth_month="+birthMonth;
                    window.open("<%=approot%>/employee/databank/export_excel/data_check.jsp?command=1"+dataAjaxSource);
                });
                
                $("#printXLS").click(function(){
                    var empNum = $("#employee_number").val();
                    if (typeof empNum === "undefined"){
                        empNum = "";
                    }
                    var empName = $("#employee_name").val();
                    if (typeof empName === "undefined"){
                        empName = "";
                    }
                    var companyId = $("#company_id").val();
                    if (typeof companyId === "undefined"){
                        companyId = 0;
                    }
                    var divisionId = $("#division_id").val();
                    if (typeof divisionId === "undefined"){
                        divisionId = 0;
                    }
                    var departmentId = $("#department_id").val();
                    if (typeof departmentId === "undefined"){
                        departmentId = 0;
                    }
                    var sectionId = $("#section_id").val();
                    if (typeof sectionId === "undefined"){
                        sectionId = 0;
                    }
                    var positionId = $("#position_id").val();
                    if (typeof positionId === "undefined"){
                        positionId = 0;
                    }
                    var levelId = $("#level_id").val();
                    if (typeof levelId === "undefined"){
                        levelId = 0;
                    }
                    var empCatId = $("#category_id").val();
                    if (typeof empCatId === "undefined"){
                        empCatId = 0;
                    }
                    var maritalId = $("#marital_id").val();
                    if (typeof maritalId === "undefined"){
                        maritalId = 0;
                    }
                    var raceId = $("#race_id").val();
                    if (typeof raceId === "undefined"){
                        raceId = 0;
                    }
                    var religionId = $("#religion_id").val();
                    if (typeof religionId === "undefined"){
                        religionId = 0;
                    }
                    var birthMonth = $("#birthday_id").val();
                    if (typeof birthMonth === "undefined"){
                        birthMonth = 0;
                    }
                    var resign = null;
                    $(".resign").each(function (i) {
                        if ($(this).is(":checked")) {
                            resign = $(".resign:checked").val();
                        }
                    });
                    var inEmpId = "<%=inEmpId%>";
                    var dataAjaxSource = "&employee_num="+empNum+"&employee_name="+empName;
                    dataAjaxSource = dataAjaxSource + "&company_id="+companyId+"&division_id="+divisionId;
                    dataAjaxSource = dataAjaxSource + "&department_id="+departmentId+"&section_id="+sectionId;
                    dataAjaxSource = dataAjaxSource + "&position_id="+positionId+"&resign="+resign+"&inEmpId="+inEmpId;
                    dataAjaxSource = dataAjaxSource + "&level_id="+levelId+"&emp_category_id="+empCatId+"&level_id="+levelId;
                    dataAjaxSource = dataAjaxSource + "&marital_id="+maritalId+"&religion_id="+religionId+"&race_id="+raceId+"&birth_month="+birthMonth;
                    window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS?command=1"+dataAjaxSource);
                });
                
                $("#sendEmail").click(function(){
                    $("#sendEmailModal").modal("show");
                    if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    var empNum = $("#employee_number").val();
                    if (typeof empNum === "undefined"){
                        empNum = "";
                    }
                    var empName = $("#employee_name").val();
                    if (typeof empName === "undefined"){
                        empName = "";
                    }
                    var companyId = $("#company_id").val();
                    if (typeof companyId === "undefined"){
                        companyId = 0;
                    }
                    var divisionId = $("#division_id").val();
                    if (typeof divisionId === "undefined"){
                        divisionId = 0;
                    }
                    var departmentId = $("#department_id").val();
                    if (typeof departmentId === "undefined"){
                        departmentId = 0;
                    }
                    var sectionId = $("#section_id").val();
                    if (typeof sectionId === "undefined"){
                        sectionId = 0;
                    }
                    var positionId = $("#position_id").val();
                    if (typeof positionId === "undefined"){
                        positionId = 0;
                    }
                    var levelId = $("#level_id").val();
                    if (typeof levelId === "undefined"){
                        levelId = 0;
                    }
                    var empCatId = $("#category_id").val();
                    if (typeof empCatId === "undefined"){
                        empCatId = 0;
                    }
                    var maritalId = $("#marital_id").val();
                    if (typeof maritalId === "undefined"){
                        maritalId = 0;
                    }
                    var raceId = $("#race_id").val();
                    if (typeof raceId === "undefined"){
                        raceId = 0;
                    }
                    var religionId = $("#religion_id").val();
                    if (typeof religionId === "undefined"){
                        religionId = 0;
                    }
                    var birthMonth = $("#birthday_id").val();
                    if (typeof birthMonth === "undefined"){
                        birthMonth = 0;
                    }
                    var resign = null;
                    $(".resign").each(function (i) {
                        if ($(this).is(":checked")) {
                            resign = $(".resign:checked").val();
                        }
                    });
                    var inEmpId = "<%=inEmpId%>";
                    $("#empNum").val(empNum);
                    $("#empName").val(empName);
                    $("#companyId").val(companyId);
                    $("#divisionId").val(divisionId);
                    $("#departmentId").val(departmentId);
                    $("#sectionId").val(sectionId);
                    $("#positionId").val(positionId);
                    $("#resign").val(resign);
                    $("#levelId").val(levelId);
                    $("#empCatId").val(empCatId);
                    $("#maritalId").val(maritalId);
                    $("#religionId").val(religionId);
                    $("#raceId").val(raceId);
                    $("#birthMonth").val(birthMonth);
                    
                    dataFor = $(this).data("for");
                    
                    dataSend = {
                        "FRM_FIELD_DATA_FOR": dataFor
                    }
                    onDone = function (data) {
                        $("#message").wysihtml5();
                        $('#cc').tagit({
                            fieldName: "ccEmail"
                        });
                        $('#bcc').tagit({
                            fieldName: "bccEmail"
                        }); 
                    };
                    onSuccess = function (data) {
                    };
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxEmployee", ".sendemail-body", false, "json");
                });
                
                $("#clear").click(function(){
                    $('#customfilter').empty();
                    $('#clear').hide();
                });
                
                $("#add").click(function(){
                    $('#clear').show();
                    var filter = $("#filter").val();
                    dataFor = "addfilter";
                    
                    dataSend = {
                        "FRM_FIELD_FILTER" : filter,
                        "FRM_FIELD_DATA_FOR" : dataFor
                    };
                    var onDones = function(data){
                        if(filter == "all"){
                            $("#customfilter").html(data.FRM_FIELD_HTML);
                        }else{
                            if($("#customfilter").find("#"+filter).length == 0){
                                 $("#customfilter").append(data.FRM_FIELD_HTML);
                            }                           
                        }
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
                        $(".comboLevel").select2({
                            placeholder: "Level"
                        });
                        $(".comboCategory").select2({
                            placeholder: "Emp Category"
                        });
                        $(".comboMarital").select2({
                            placeholder: "Marital Status"
                        });
                        $(".comboRace").select2({
                            placeholder: "Race"
                        });
                        $(".comboReligion").select2({
                            placeholder: "Religion"
                        });
                        $(".comboBirthday").select2({
                            placeholder: "Birthday"
                        });
                        
//                        $('#employee_number').on('keyup', function(e) {
//                            if (e.keyCode === 13) {
//                                $('#search').click();
//                            }
//                        });
//
//                        $('#employee_name').on('keyup', function(e) {
//                            if (e.keyCode === 13) {
//                                $('#search').click();
//                            }
//                        });
                        
                    };
                    var onSuccesses = function(data){};
                    getDataFunction(onDones, onSuccesses, approot, command, dataSend, "AjaxEmployee", null, false, "json");
                });
                
                //FORM SUBMIT
                    $("form#generalform").submit(function () {
                        var currentBtnHtml = $("#btngeneralform").html();
                        $("#btngeneralform").html("Sending...").attr({"disabled": "true"});
                        var generaldatafor = $("#generaldatafor").val();
                        onDone = function (data) {
                           runDefault();
                           $("#notifications").notify({
                                message: { html : "Email Sent" },
                                type    : "info",
                                transition : "fade"
                            }).show();
                        };
                        
                        if ($(this).find(".has-error").length == 0) {
                            onSuccess = function (data) {
                                $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                                $("#sendEmailModal").modal("hide");
                            };

                            dataSend = $(this).serialize();
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "servlet/com.dimata.harisma.report.EmployeeListEmail", null, false, null);
                        } else {
                            $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                        }

                        return false;
                    });
                
                
            });
        </script>
        <div id="sendEmailModal" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="title">Send Email</h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
		    <input type="hidden" name="command" value="<%= Command.LIST %>">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="employee_num" id="empNum">
                    <input type="hidden" name="employee_name" id="empName">
                    <input type="hidden" name="company_id" id="companyId">
                    <input type="hidden" name="division_id" id="divisionId">
                    <input type="hidden" name="department_id" id="departmentId">
                    <input type="hidden" name="section_id" id="sectionId">
                    <input type="hidden" name="position_id" id="positionId">
                    <input type="hidden" name="resign" id="resign">
                    <input type="hidden" name="level_id" id="levelId">
                    <input type="hidden" name="emp_category_id" id="empCatId">
                    <input type="hidden" name="marital_id" id="maritalId">
                    <input type="hidden" name="religion_id" id="religionId">
                    <input type="hidden" name="race_id" id="raceId">
                    <input type="hidden" name="birth_month" id="birthMonth">
                    <div class="modal-body ">
			<div class="row">
			    <div class="col-md-12">
				<div class="box-body sendemail-body">

				</div>
			    </div>
			</div>
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btngeneralform"><i class="fa fa-check"></i> Send</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div>
    </body>
</html>
