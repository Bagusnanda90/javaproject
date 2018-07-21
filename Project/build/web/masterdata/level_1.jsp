<%-- 
    Document   : level
    Created on : 14-Jun-2016, 15:52:54
    Author     : Gunadi
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
        <title>Master Level | Dimata Hairisma</title>
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
                        Master Level
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Level</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <strong style="font-size: 20px">Level List</strong>
                                            <hr />
                                            <div class="table-responsive">
                                            <table id="example1" class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Level</th>
                                                        <th>Group</th>
                                                        <th>Employee Medical Level</th>
                                                        <th>Family Medical Level</th>
                                                        <th>Description</th>
                                                        <th>Level Point</th>
                                                        <th>Code</th>
                                                        <th>Level Rank</th>
                                                        <th>Max Level Approval</th>
                                                        <th>HR Approval</th>
                                                        <th>Action</th>
                                                        </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>Executive</td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td></td>
                                                        <td>Yes</td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Senior Management</td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td></td>
                                                        <td>No</td>
                                                        <td><a href="#"><i class="fa fa-pencil"></i> Edit </a>| <a href="#"><i class="fa fa-times"></i> Delete</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>Middle Management</td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td></td>
                                                        <td>Yes</td>
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
                                            <button type="button" class="btn btn-primary btn-lg btn-sm" data-toggle="modal" data-target="#addModal">
                                                <strong><i class="fa fa-plus"></i> Add New Level</strong>
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
                    <h4 class="modal-title" id="myModalLabel">Add New Level</h4>
                  </div>
                  <div class="modal-body">
                    <label>Group Rank</label>
                    <select class="form-control" id="group_rank">
                        <option>Staff</option>
                        <option>Supervisor</option>
                        <option>Manager</option>
                    </select>
                    
                    <label>Level</label>
                    <input type="text" name="point" class="form-control" placeholder="Type Name Level...">
                    
                    <label>Employee Medical Level</label>
                    <div class="input-group">
                        <select class="form-control" id="emp_med_level">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>
                        <span class="input-group-btn">
                            <button id="filter" class="btn btn-primary btn-block" onclick="add();">
                                <span class="glyphicon glyphicon-plus"></span>
                            </button>
                        </span>
                    </div>
                    
                    <label>Family Member Medical Level</label>
                    <select class="form-control" id="fam_med_level">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                    </select>
                    
                    <label>Level Point</label>
                    <input type="text" name="point" class="form-control" placeholder="Type Level Point...">
                    
                    <label>Description</label>
                    <textarea class="form-control" rows="5" id="desc"></textarea>
                    
                    <label>Code</label>
                    <input type="text" name="code" class="form-control" placeholder="Type Code Value...">
                    
                    <label>Level Rank</label>
                    <input type="text" name="level_rank" class="form-control" placeholder="Type Code Value...">
                    
                    <label>Max Level</label>
                    <select class="form-control" id="max_level">
                        <option>Executive</option>
                        <option>Supervisor</option>
                        <option>Directur</option>
                    </select>
                    
                    <label>HR Approval</label>
                    <select class="form-control" id="hr_approv">
                        <option>Yes</option>
                        <option>No</option>
                    </select>
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
