<%-- 
    Document   : custom_field_master
    Created on : 15-Jun-2016, 10:12:13
    Author     : Acer
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
        <title>Master Custom Field | Dimata Hairisma</title>
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
            .modal-body {
            max-height: calc(100vh - 212px);
            overflow-y: auto;
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
                        Master Custom Field
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Custom Field</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <strong style="font-size: 20px">Custom Field List</strong>
                                            <hr />
                                            <div class="table-responsive">
                                            <table id="example1" class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>No</th>
                                                        <th>Field Name</th>
                                                        <th>Field Type</th>
                                                        <th>Required</th>
                                                        <th>Data List</th>
                                                        <th>Input Type</th>
                                                        <th>Show Field</th>
                                                        <th>Note</th>
                                                        <th>Action</th>
                                                        </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>Secondary ID</td>
                                                        <td>Text</td>
                                                        <td>Not-Required</td>
                                                        <td></td>
                                                        <td>Text Field</td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>   
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Validity Date</td>
                                                        <td>Date</td>
                                                        <td>Not-Required</td>
                                                        <td></td>
                                                        <td>Date Picker</td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    
                                                </tbody>
                                            </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addModal">
                                                <strong><i class="fa fa-plus"></i> Add New Custom Field</strong>
                                            </button>
                                            
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>   
                </section>
            </div><!-- /.content-wrapper -->
            
            <!-- Modal -->
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">Add New Custom Field</h4>
                  </div>
                  <div class="modal-body">
                    
                    
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save</button>
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
       </body>
</html>

