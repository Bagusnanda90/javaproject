<%-- 
    Document   : assets
    Created on : 11-Jul-2016, 11:03:23
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
        <title>Master Assets | Dimata Hairisma</title>
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
        </style>
        
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Master Assets
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Assets</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <strong style="font-size: 20px">Assets List</strong>
                                            <hr />
                                            <table id="example1" class="table table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Code</th>
                                                        <th>Name</th>
                                                        <th>Aqc Date</th>
                                                        <th>Action</th>
                                                        </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>HP-01</td>
                                                        <td>Smartphone</td>
                                                        <td>2016-06-07</td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>CR-001</td>
                                                        <td>Mobil</td>
                                                        <td>2016-06-07</td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-lg btn-sm" data-toggle="modal" data-target="#addModal">
                                                <strong><i class="fa fa-plus"></i> Add New Assets</strong>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </section>
                
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/footer.jsp" %>
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">Add New Assets</h4>
                  </div>
                  <div class="modal-body">
                    <form role="form" class="form-horizontal">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-sm-2">Code</label>
                                    <div class="col-sm-10"><input class="form-control" id="code" placeholder="Code" type="text"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2">Name</label>
                                    <div class="col-sm-10"><input class="form-control" id="name" placeholder="Name" type="text"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2">Assets Group</label>
                                    <div class="col-sm-10"><input class="form-control" id="group" placeholder="Assets Group" type="text"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2">Acquisition Date</label>
                                    <div class="col-sm-10"><input class="form-control" id="date" placeholder="Acquisition Date" type="text"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2">Qty</label>
                                    <div class="col-sm-10"><input class="form-control" id="qty" placeholder="Qty" type="text"></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Qty</th>
                                            <th>Action</th>
                                            </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Acc 1</td>
                                            <td>2</td>
                                            <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                        </tr>
                                        <tr>
                                            <td>Acc 2</td>
                                            <td>3</td>
                                            <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="box-footer">
                                    <div class="row pull-right">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-lg btn-sm" data-toggle="modal" data-target="#assetModal">
                                                <strong><i class="fa fa-plus"></i> Add Accessory</strong>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>    
                    
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save</button>
                  </div>
                    </form>
                </div>
              </div>
            </div>
        </div>
            
            <div id="assetModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm">

                    <!-- Modal content-->

                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="addeditgeneral-title"></h4>
                        </div>
                        <div class="modal-body">
                        <form role="form" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2">Name</label>
                                <div class="col-sm-10"><input class="form-control" id="code" placeholder="Name" type="text"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2">Qty</label>
                                                                           <div class="col-sm-10"><input class="form-control" id="code" placeholder="Qty" type="text"></div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
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