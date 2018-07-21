<%-- 
    Document   : company
    Created on : Jun 14, 2016, 9:34:12 AM
    Author     : ARYS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String appRoot = request.getContextPath();
%>
<html>
    <head>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Competency List | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= appRoot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= appRoot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= appRoot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="<%= appRoot%>/assets/plugins/select2/select2.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= appRoot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= appRoot%>/assets/dist/css/skins/skin-blue.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= appRoot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= appRoot%>/assets/respond/1.4.2/respond.min.js"></script>
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
                    Master Data Competency Detail
                </h1>
                <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Master Data</li>
                        <li class="active">Competency Detail</li>
                </ol>
            </section>
            <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body">
                            <div class="row">
                                    <div class="col-xs-12">
                                        
                                        <div class="tab-pane active" id="tab_1">
                                                        <div class="row">
                                                            <form method="get" action="">
                                                                <div class="col-md-5" style="border-right: 1px solid #f4f4f4">
                                                                    <div class="form-group">
                                                                        <input type="text" name="src_div" class="form-control" placeholder="Search...">
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <div class="form-group">
                                                                        <button type="submit" class="btn btn-block btn-primary btn-flat">Search</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                        <br><br>
                                        <strong style="font-size: 20px">Competency Detail List</strong>
                                          <br/>
                                          <div class="col-md-12">
                                            <hr />
                                            <table id="example1" class="table table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>No</th>
                                                        <th>Competency Name</th>
                                                        <th>Description</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>1</td>
                                                        <td>Kejujuran</td>
                                                        <td>Pribadi</td>
                                                        <td><a href="#"<i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>2</td>
                                                        <td>Yashoda</td>
                                                        <td>Retail</td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>3</td>
                                                        <td>test</td>
                                                        <td>Retail</td>
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
                                            <button type="button" class="btn btn-primary btn-sm fa fa-plus" data-toggle="modal" data-target="#myModal">Add Competency Detail</button>
                                        
                                                <!-- Modal -->
                                                <div id="myModal" class="modal fade" role="dialog">
                                                     <div class="modal-dialog">

                                                        <!-- Modal content-->
                                                            <div class="modal-content">
                                                            <div class="modal-body">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Add Competency Detail</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                
                                                                <form role ="form">
                                                                    <div class="form-group">
                                                                        <label for="Company">All Competency :</label>
                                                                        <select type="email" class="form-control" id="company">
                                                                            <option>Group 1</option>
                                                                            <option>Group 2</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="Company">Description :</label>
                                                                        <textarea rows="5" class="form-control" id="cmp_address"></textarea>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                            <div class="modal-footer">
                                                                 <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                                 <button type="button" class="btn btn-primary" data-dismiss="modal">SAVE</button>
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
        <script src="<%= appRoot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= appRoot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= appRoot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= appRoot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= appRoot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= appRoot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
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
        </section>
    </body>
</html>
