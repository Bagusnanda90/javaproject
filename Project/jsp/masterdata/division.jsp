<%-- 
    Document   : division
    Created on : Jun 14, 2016, 10:34:12 AM
    Author     : ARYS
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
        <title>Division List | Dimata Hairisma</title>
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
        <input type="hidden" name="start" id="start">
        <input type="hidden" name="command" id="commandlist">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Master Data
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Master Data</li>
                        <li class="active">Division</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="col-xs-12">
                                                <a> <h3>Division List : </h3></a>
                                                <hr>
                                                <div class="table-responsive">
                                                <div id="listdata">
                                                </div>
                                                </div>    
                                            </div>
                                            <div class="box-footer">
                                    <div class="row pull-left">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdata" data-oid="0" data-for="showdivisionform" data-command="0"> Add Division</button> 
                                            
                                            <!-- Modal -->
                                                <div id="myModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                                                     <div class="modal-dialog">

                                                        <!-- Modal content-->
                                                         
                                                            <div class="modal-content">
                                                            <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="addeditgeneral-title"></h4>
                                                            </div>
                                                            <form id="form-modal">
                                                             <input type="hidden" name="oid" id="oid">
                                                             <input type="hidden" name="datafor" id="datafor">
                                                             <input type="hidden" name="command" id="command">
                                                            <div class="modal-body active-scroll" id="modalbody" >
                                                            </div>
                                                            <div class="modal-footer">
                                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                                 <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                                            </div>
                                                            </form>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        
                                                        <div id="mdl_delete" class="modal fade" role="dialog">
                                                        <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-body">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Delete Division</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                
                                                                Are You Sure ? 
                                                            </div>
                                                            <div class="modal-footer">
                                                            <button type="button" data-dismiss="modal" class="btn btn-primary btn-danger" id="delete">Delete</button>
                                                            <button type="button" data-dismiss="modal" class="btn">Cancel</button>
                                                            </div>
                                                            </div>
                                                        </div>
                                                        </div>
                                            
                                                    </div>
                                                </div>
                                        
                                        
                                        </div>
                                    </div>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
           
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
                
                var height = $(window).height() - 200; //value corresponding to the modal heading + footer
                $(".active-scroll").css({"height":height,"overflow-y":"auto"});
                 
                
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
                       var dataFor = "listdivision";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           deleteData(".deletedata")
                           $('#listDivision').DataTable( {
                                
                            } );
                            addEditTable("#listDivision",".addeditdata");
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend, onDone, onSuccess);
                   
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
                       //$(selector).html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>").fadeIn("medium");
                       
                       if(confirm(confirmText)){
                           sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                function open(selector){
                    $(selector).click(function(){
                        var oid = $(this).data("oid");
                        var command = $(this).data("command");
                        
                        $("#oid").val(oid);
                        $("#command").val("4");
                        
                        var dataSend = {
                           "oid" : oid,
                           "command" : command
                       }
                       var onDone = function(data){
                           
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       
                       window.location.href = "<%= approot %>/employee/databank/employee_edit.jsp";
                       
                       sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend, onDone, onSuccess);
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
			if(dataFor == 'showdivisionform'){
			    $(".addeditgeneral-title").html("Edit Division");
			}

                        }else{
                            if(dataFor == 'showdivisionform'){
                                $(".addeditgeneral-title").html("Add Division");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                }
                
                function addEditTable(selectorTable, selector){
                    $(selectorTable).on('click',selector, function(){
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
			if(dataFor == 'showdivisionform'){
			    $(".addeditgeneral-title").html("Edit Division");
			}

                        }else{
                            if(dataFor == 'showdivisionform'){
                                $(".addeditgeneral-title").html("Add Division");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("<div class='progress progress-striped active'><div class='progress-bar progress-bar-primary' style='width: 100%'><b>Please Wait...</b></div></div>.");
                       
                       sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend1, onDone1, onSuccess1);
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
                    sendData("<%= approot %>/masterdata/ajax/division_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                });
            });
        </script>
        
    </body>
</html>
