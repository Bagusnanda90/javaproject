<%-- 
    Class Name   : template.jsp
    Author       : Dian Putra
    Created      : Apr 17, 2016 10:59:14 PM
    Function     : This is template
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String approot = request.getContextPath();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Employee List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="<%= approot%>/assets/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <link rel="stylesheet" href="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.css">
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
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Databank
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Databank</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header">
                                    <div class="row pull-left">
                                        <div class="col-md-12">
                                            <button type="button" onclick="location.href='../databank/employee_edit.jsp?command=2'" class="btn btn-primary"><i class="fa fa-plus"></i> Add New Employee</button>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <button type="button" onclick="location.href='#'" class="btn btn-primary"><i class="fa fa-file"></i> Export to Excel</button>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <button type="button" onclick="location.href='#'" class="btn btn-primary"><i class="fa fa-file"></i> Export to PDF</button>
                                            &nbsp;
                                            &nbsp;
                                            &nbsp;
                                            <button type="button" onclick="location.href='#'" class="btn btn-primary"><i class="fa fa-envelope"></i> Send List to Email</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-body">                                    
                                    <div class="box box-primary collapsed-box">
                                        <div class="box-header with-border">
                                            <h3 class="box-title">Advance Search</h3>
                                            <div class="box-tools pull-right">
                                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
                                            </div><!-- /.box-tools -->
                                        </div><!-- /.box-header -->
                                        <div class="box-body">
                                            <form method="get" action="">
                                                <div class="row">
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Employee Number</label>
                                                            <input type="text" name="employee_num" class="form-control" placeholder="E.g : 13102009">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Full Name</label>
                                                            <input type="text" name="full_name" class="form-control" placeholder="E.g : I Putu Dian Pramana Putra">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Company</label>
                                                            <select class="form-control comboCompany" style="width: 100%;" name="comboCompany" multiple="multiple">
                                                                <option>Queen Tandoor</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Division</label>
                                                            <select class="form-control comboDivision" style="width: 100%;" name="comboDivision" multiple="multiple">
                                                                <optgroup label="Queen Tandoor">
                                                                    <option>Finance</option>
                                                                    <option>HRD</option>
                                                                    <option>F&B</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Department</label>
                                                            <select class="form-control comboDepartment" style="width: 100%;" name="comboDepartment" multiple="multiple">
                                                                <optgroup label="HRD">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="Finance">
                                                                    <option>AR</option>
                                                                    <option>AP</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Section</label>
                                                            <select class="form-control comboSection" style="width: 100%;" name="comboSection" multiple="multiple">
                                                                <optgroup label="HRD">
                                                                <optgroup label="-- Operational">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="-- Payroll">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                                <optgroup label="-- Training">
                                                                    <option>Operational</option>
                                                                    <option>Payroll</option>
                                                                    <option>Training</option>
                                                                </optgroup>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3" style="border-right: 1px solid #f4f4f4">
                                                        <div class="form-group">
                                                            <label>Position</label>
                                                            <select class="form-control comboPosition" style="width: 100%;" name="comboPosition" multiple="multiple">
                                                                <option>HR Staff</option>
                                                                <option>Payroll Staff</option>
                                                                <option>Training Staff</option>
                                                                <option>AR Staff</option>
                                                                <option>Chief Accounting</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label>&nbsp;</label>
                                                            <button id="submit" class="btn btn-block btn-primary btn-flat">Search</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div><!-- /.box-body -->
                                    </div><!-- /.box -->
                                    <div class="row">                                    
                                        <div class="col-md-12">
                                            <div class="table-responsive">
                                            <div id="listdata">
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
                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="<%= approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= approot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        <script src="<%= approot %>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
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
                       loadData("#listdata");
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
                        loadData("#listdata");
                    })
                }
                
                function loadData(selector){
                       var oid = $(this).data("oid");
                       var dataFor = "listEmployee";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var resigned = getUrlParameter('resign');
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start,
                           "resign" : resigned
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           $('#listEmployee').DataTable( {
                                } );
                           addEdit(".addeditdata");
                           deleteData(".deletedata");
                           resign("#submit1");
                           
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/employee/databank/ajax/emplist_ajax.jsp", dataSend, onDone, onSuccess);
                   
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
                           //$(selector).html(data).fadeIn("medium");
                           loadData("#listdata")
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
                loadData("#listdata");
                
                $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModal").modal("hide");
                        loadData("#listdata");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/employee/databank/ajax/emplist_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                });
            });
        </script>
    </body>
</html>
