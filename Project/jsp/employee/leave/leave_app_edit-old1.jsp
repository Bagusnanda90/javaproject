<%-- 
    Document   : famRelation
    Created on : 14-Jun-2016, 14:56:07
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
        <title>Leave Application | Dimata Hairisma</title>
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
            .modal-lg{
	    max-width: 1200px;
	    width: 100%;
            }
            .modal-body {
            max-height: calc(100vh -212px);
            overflow-y: scroll;
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
                        Leave Application Form
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Leave Application</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div align="center" class="">
                                                <select  name="" class="" id="">
                                                    <option> <strong style="font-size: 20px">LEAVE APPLICATION</strong> </option>
                                                    <option> <strong style="font-size: 20px">EXCUSE APPLICATION</strong> </option>
                                                </select>
                                            </div>
                                            <hr />
                                            <div class="table-responsive">
                                                <table width="100%"  align="center">
                                                        <tr>
                                                          <td width="12%" rowspan="6" valign="top">
                                                              <table>
                                                                  <tr>
                                                                      <td>
                                                                          <img width="140" height="150" src="<%=approot%>/imgcache/no_photo.JPEG"></image>
                                                                      </td>
                                                                  </tr>
                                                              </table>
                                                          </td>
                                                          <td width="4%" height="34">&nbsp;</td>
                                                          <td width="8%">Payroll</td>
                                                          <td width="1%">:</td>
                                                          <td width="26%"><input type="text" name="EMP_NUMBER"  value="" class="elemenForm" size="25" disabled></br></td>
                                                          <td width="3%">&nbsp;</td>
                                                          <td colspan="3">
                                                              <table cellpadding="0" cellspacing="0" border="0">
                                                                <tr>
                                                                 <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                 <td width="15"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                 <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                 <td class="command" nowrap width="99"> 
                                                                 <div align="left"><a href="#" data-target="#myModal" data-toggle="modal">Search Employee</a></div>
                                                                 </td>
                                                                 <td width="15"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                                                 <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                 <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                 <td class="command" nowrap width="99">
                                                                 <div align="left "><a href="javascript:cmdClearSearchEmp()">Clear Search</a></div>
                                                                 </td>
                                                                </tr>
                                                            </table>
                                                          </td>
                                                        </tr>
                                                        
                                                        <tr>
                                                          <td height="35">&nbsp;</td>
                                                          <td>Name</td>
                                                          <td>:</td>
                                                          <td><strong><input type="text" name="EMP_FULLNAME"  value="" class="elemenForm" size="30"  disabled></strong></td>
                                                          <td>&nbsp;</td>
                                                          <td width="21%">Position</td>
                                                          <td width="2%">:</td>
                                                          <td width="23%"><strong><input type="text" name="EMP_POSITION"  value="" class="elemenForm" size="35"  disabled></strong></td>
                                                        </tr>
                                                        <tr>
                                                          <td height="33">&nbsp;</td>
                                                          <td>Division</td>
                                                          <td>:</td>
                                                          <td><strong><input type="text" name="EMP_DIVISION"  value="" class="elemenForm" size="43"  disabled> </strong></td>
                                                          <td>&nbsp;</td>
                                                          <td>Commencing Date</td>
                                                          <td>:</td>
                                                          <td><input type="text" name="EMP_COMENCING"  value="" class="elemenForm" size="25"  disabled></td>
                                                        </tr>
                                                        <tr>
                                                          <td height="36">&nbsp;</td>
                                                          <td>Department</td>
                                                          <td>:</td>
                                                          <td><strong><input type="text" name="EMP_DEPARTMENT"  value="" class="elemenForm" size="30"  disabled> </strong></td>
                                                          <td>&nbsp;</td>
                                                          <td>Date of Request</td>
                                                          <td>:</td>
                                                          <td>
                                                              <strong>                                                              </strong>
                                                              <input type="text" name="EMP_COMENCING2"  value="" class="elemenForm" size="25"  disabled></td>
                                                        </tr>
                                                        <tr>
                                                          <td>&nbsp;</td>
                                                          <td>Doc Status</td>
                                                          <td>:</td>
                                                          <td><strong>
                                                            <B>Draft</B>
                                                          </strong>
                                                          </td>
                                                          <td>&nbsp;</td>

                                                         
                                                          <td>
                                                              <input type="checkbox" name="AL_Allowance"  id="userSelectAL_Allowance" value="">Ent. For AL Allowance
                                                          </td>
                                                          <td>:</td>
                                                          <td>
                                                              <input type="checkbox" name="LL_Allowance"  id="userSelectLL_Allowance" value="">Ent. For LL Allowance
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                          <td>&nbsp;</td>
                                                        </tr>
                                                      </table>                  
                                                        <table width="99%" align="center">                    
                                                           <tr>
                                                               <td>

                                                               </td>
                                                           </tr> 
                                                           <tr>
                                                               <td class="msgerror">
                                                                   <font color = "FF0000"></font>                            
                                                             </td>
                                                           </tr> 
                                                           <tr>
                                                               <td>&nbsp;&nbsp;           
                                                               </td>
                                                           </tr>

                                                           <tr>
                                                               <td>
                                                               </td>
                                                           </tr>

                                                           <tr>
                                                               <td class="msgerror">
                                                                   <font color = "FF0000"></font>                            
                                                             </td>
                                                           </tr> 
                                                           <tr>
                                                               <td>&nbsp;&nbsp;           
                                                               </td>
                                                           </tr>
                                                           <tr>
                                                               <td>
                                                               </td>
                                                           </tr> 
                                                           <tr>
                                                               <td class="msgerror">
                                                                   <font color = "FF0000"></font>                            
                                                             </td>
                                                           </tr>
                                                           <tr>
                                                               <td>
                                                               </td>
                                                           </tr>
                                                           <tr>
                                                               <td>
                                                               <table width="100%" bgcolor="#E0EDF0">
                                                               <tr>
                                                                   <td colspan="3">&nbsp;</td>
                                                               </tr>
                                                               <tr>
                                                                   <td colspan="3"><b>Leave Reason</b><td>
                                                               </tr>
                                                               <tr>
                                                                   <td width="20%">Reason</td>
                                                                   <td width="5%">:</td>
                                                                   <td width="75%">
                                                                       <textarea type="text" class="form-control col-md-2" name="" value="" size="10"></textarea>
                                                                   </td>
                                                               </tr>                           
                                                               <tr>
                                                                   <td colspan="3">&nbsp;</td>
                                                               </tr>
                                                               </table>
                                                               </td>
                                                           </tr>
                                                           <tr> 
                                                               <td valign="top"> 
                                                               <blink>To view approval form, please set document status to TO BE APPROVED</<blink>

                                                                 <table width="100%" border="0"  cellpadding="1" cellspacing="1" bgcolor="#E0EDF0">
                                                                   <tr> 
                                                                     <td valign="top"> 
                                                                       <table width="100%" border="0" bgcolor="#E0EDF0">
                                                                           <!--Table Approval-->
                                                                       </table>
                                                                     </td>
                                                                   </tr>
                                                                 </table>
                                                                     <table width="100%" border="0"  cellpadding="1" cellspacing="1" bgcolor="#E0EDF0">
                                                                   <tr> 
                                                                     <td valign="top"> 
                                                                       <table width="100%" border="0" bgcolor="#E0EDF0">
                                                                         <!-- Table Approval -->
                                                                       </table>
                                                                     </td>
                                                                   </tr>
                                                                 </table>
                                                               </td>
                                                             </tr>
                                                           <tr> 
                                                               <td>&nbsp; </td>
                                                           </tr>                        
                                                           <tr> 
                                                               <td>
                                                               </td>
                                                           </tr>
                                                           <tr> 
                                                            <td>
                                                            </td>
                                                           </tr>
                                                           </table>
                                                            <table class="table table-bordered table-striped success">
                                                                <thead >
                                                                    <tr class="info">
                                                                    <th>No</th>
                                                                    <th>Type Of Leave</th>
                                                                    <th>Qty</th>
                                                                    <th>Taken</th>
                                                                    <th>Current Qty</th>
                                                                    <th>To Be Taken</th>
                                                                    <th>AL Eligible</th>
                                                                    <th>All Requested</th>
                                                                    <th>Start Date</th>
                                                                    <th>End Date</th>
                                                                    <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                                <tr>
                                                                    <td>1</td>
                                                                    <td>Annual Leave</td>
                                                                    <td>9</td>
                                                                    <td>2</td>
                                                                    <td>9</td>
                                                                    <td>0</td>
                                                                    <td>9 d</td>
                                                                    <td>0</td>
                                                                    <td>
                                                                    <input type="date" name="date">
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>
                                                                    </td>
                                                                    <td>
                                                                    <input type="date" name="date">
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>    
                                                                    </td>
                                                                    <td><button class=" btn btn-info">SAVE</button></td> 
                                                                </tr>
                                                                <tr></tr>
                                                            </table>
                                                             
                                                            <table class="table table-bordered table-striped">
                                                                <tr></tr>
                                                                <thead >
                                                                    <tr class="info">
                                                                    <th>No</th>
                                                                    <th>Type Of Leave</th>
                                                                    <th>Schedule</th>
                                                                    <th>No. of Days Requested</th>
                                                                    <th>Start Date</th>
                                                                    <th>End Date</th>
                                                                    <th>Action</th>
                                                                    </tr>
                                                                    
                                                                </thead>
                                                                <tr>
                                                                    <td>1</td>
                                                                    <td></td>
                                                                    <td>
                                                                    <select>
                                                                        <option>Unpaid Leave</option>
                                                                        <option>Cuti Melahirkan</option>    
                                                                    </select>
                                                                    </td>
                                                                    <td>0</td>
                                                                    <td>
                                                                    <input type="date" name="date">
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>
                                                                    </td>
                                                                    <td>
                                                                    <input type="date" name="date">
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>    
                                                                    </td>
                                                                    <td><button class=" btn btn-info">SAVE</button></td> 
                                                                </tr>
                                                            </table>
                                                                 
                                                            <table class="table table-bordered table-striped success">
                                                                <thead >
                                                                    <tr class="info">
                                                                    <th>No</th>
                                                                    <th>Type Of Leave</th>
                                                                    <th>Qty</th>
                                                                    <th>Taken</th>
                                                                    <th>Current Qty</th>
                                                                    <th>To Be Taken</th>
                                                                    <th>No. of Days Eligible</th>
                                                                    <th>No. of Days Requested</th>
                                                                    <th>Taken Date</th>
                                                                    <th>Unpaid Date</th>
                                                                    <th>Action</th>
                                                                    </tr>
                                                                </thead>
                                                                <tr>
                                                                    <td>1</td>
                                                                    <td>Day off payment</td>
                                                                    <td>1</td>
                                                                    <td>2</td>
                                                                    <td>9</td>
                                                                    <td>0</td>
                                                                    <td>9 d</td>
                                                                    <td>0</td>
                                                                    <td>
                                                                    <input type="date" name="date">
                                                                    <select><br>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>
                                                                    To
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>
                                                                    </td>
                                                                    <td>
                                                                    <input type="date" name="date"><br>
                                                                    <select>
                                                                        <option>08</option>    
                                                                    </select>
                                                                    <select>
                                                                        <option>16</option>    
                                                                    </select>    
                                                                    </td>
                                                                    <td><button class=" btn btn-info">SAVE</button></td> 
                                                                </tr>
                                                                <tr></tr>
                                                            </table>
                                                                 
                                                    <button onfocus="test" type="button" class="btn btn-primary btn-small-x fa fa-save addeditdata" data-oid="0" data-for="showfamRelationform" data-command="0"> Save Leave Application</button> 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                                                 
                                <div id="myModal" class="modal fade" role="dialog">
                                   <div class="modal-dialog">
                                     <!-- Modal content-->
                                     <div class="modal-content">
                                       <div class="modal-header">
                                         <button type="button" class="close" data-dismiss="modal">&times;</button>
                                         <h4 class="modal-title">Employee List</h4>
                                       </div>
                                       <div class="modal-body table-responsive">
                                         <table >
                                              <tr> 
                                                <td width="19%" height="33">Name</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <input type="text" name="" class="elemenForm" size="40">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="30%" height="40">Payroll Number</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <input type="text" name="" class="elemenForm">
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%" height="36">Category</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                    <select>
                                                        <option></option>
                                                    </select>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%" height="30">Company</td>
                                                <td width="1%">:</td>
                                                <td width="80%">
                                                <select>
                                                        <option> Queen Tandoor</option>
                                                </select>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="19%" height="33">Division</td>
                                                <td width="1%">:</td>
                                                <td width="80%">
                                                <select>
                                                        <option> Test</option>
                                                </select>
                                                </td>
                   
                                              </tr>
                                              <tr> 
                                                <td width="19%" height="33">Department</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <select>
                                                        <option>Test</option>
                                                </select>
                                                </td>
                   
                                              </tr>
                                              <tr> 
                                                <td width="30%" height="30">Section</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <select>
                                                        <option>test</option>
                                                </select>
                                                </td>
                
                                              </tr>
                                              <tr> 
                                                <td width="19%" height="35">Position</td>
                                                <td width="1%">:</td>
                                                <td width="80%"> 
                                                <select>
                                                        <option>Opration</option>
                                                </select>  
                                               </td>
                                              </tr>
					      
                                              <input type="hidden" name="" value="0">
                                              <input type="hidden" name="" value="3">
                                              
                                              <tr> 
                                                <td width="19%">&nbsp;</td>
                                                <td width="1%">&nbsp;</td>
                                                <td width="80%"> 
                                                  <input type="submit" name="Submit" value="Search Employee">
                                                </td>
                                              </tr>
                                              
                                            </table>
                                           <table class="table table-bordered table-striped">
                                               <thead>
                                                   <tr>
                                                       <th>No</th>
                                                       <th>Payroll</th>
                                                       <th>Name</th>
                                                       <th>Commencing Date</th>
                                                       <th>Division</th>
                                                       <th>Department</th>
                                                       <th>Position</th>
                                                   </tr>
                                               </thead>
                                               <tr>
                                                   <td>1</td>
                                                   <td>123</td>
                                                   <td>Gundai</td>
                                                   <td>2016-03-23</td>
                                                   <td>QA</td>
                                                   <td>Testing</td>
                                                   <td>QA</td>
                                               </tr>
                                               <tr>
                                                   <td>2</td>
                                                   <td>1234</td>
                                                   <td>GunDai</td>
                                                   <td>2016-03-23</td>
                                                   <td>QA</td>
                                                   <td>Testing</td>
                                                   <td>QA</td>
                                               </tr>
                                               <tr>
                                                   <td>3</td>
                                                   <td>12345</td>
                                                   <td>Dedy</td>
                                                   <td>2016-03-23</td>
                                                   <td>QA</td>
                                                   <td>Testing</td>
                                                   <td>QA</td>
                                               </tr>
                                               
                                           </table>
                                           
                                           
                                           
                                       </div>
                                       <div class="modal-footer">
                                         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                       </div>
                                     </div>

                                   </div>
                                 </div>
                            </div>
                        </div>
                        </div>   
                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/footer.jsp" %>
        </div>
        <!-- jQuery 2.1.4 -->
        <script src="<%=approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%=approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%=approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%=approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%=approot%>/assets/dist/js/app.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%=approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        <script src="<%=approot%>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%=approot%>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
       </body>
       <script>
    
    $(function(){
    $("#tblGrid").dataTable();
    })
     
     $(function () {
     $('input').on('click', function () {
        var Status = $(this).val();
        $.ajax({
            url: '<%= approot %>/employee/leave/famRelation_ajax.jsp',
            dataType : 'json'
        });
    });
    });
       </script>
</html>