<%--
    Document   : PayrollGroup
    Created on : 16-Jun-2016, 09:20:44
    Author     : Gunadi
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../main/javainit.jsp" %>
<!DOCTYPE html>
<%
    
    long oidTemp = FRMQueryString.requestLong(request, "oidtemp");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Master Data | Dimata Hairisma</title>
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
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker3.css">
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
            
        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Structure Mapping
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Structure Mapping</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <strong style="font-size: 20px">Structure Mapping</strong>
                                            <hr />
                                            <div class="table-responsive">
                                            <div id="listdata">
                                                
                                            </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="row pull-left">
                                        <div class="col-md-12">
                                            <button type="button" class="btn btn-primary btn-sm fa fa-plus addeditdata" data-oid="0" data-oidtemp="<%=oidTemp%>" data-for="ShowStrMapp" data-command="0">Add Structure Mapping</button>
                                          
                                          
                                            <div class="modal fade" id="myModal" data-focus-on="input:first" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                              <div class="modal-dialog">
                                                <div class="modal-content">
                                                  <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                    <h4 class="addeditgeneral-title" id="myModalLabel"></h4>
                                                  </div>
                                                  <form id="form-modal">
                                                    <input type="hidden" name="oid" id="oid">
                                                    <input type="hidden" name="oidtemp" id="oidtemp">
                                                    <input type="hidden" name="datafor" id="datafor">
                                                    <input type="hidden" name="command" id="command">
                                                   <div class="modal-body" id="modalbody">
                                                   </div>
                                                   <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">CANCEL</button>
                                                        <button type="submit" class="btn btn-primary" id="btnsave">SAVE</button>
                                                   </div>
                                                  </form>
                                                </div>
                                              </div>
                                            </div>
                                            
                                            <!-- Modal -->
                                          <div class="modal fade" id="myModal2" data-focus-on="input:first" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                              <div class="modal-dialog">
                                                <div class="modal-content">
                                                  <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                    <h4 class="addeditgeneral-title2" id="myModalLabel2"></h4>
                                                  </div>
                                                  <form id="form-modal2">
                                                    <input type="hidden" name="oid" id="oid2">
                                                    <input type="hidden" name="datafor" id="datafor2">
                                                    <input type="hidden" name="command" id="command2">
                                                   <div class="modal-body" style="height: 500px;overflow-y: auto;">
                                                       <div class="row">
                                                           <div class="col-md-12">
                                                               <input class="form-control" placeholder="search position..." type="text" name="position_name" size="50" id="positionname"/>
                                                           </div>
                                                       </div>
                                                       <div class="row">
                                                           <div class="col-md-12" id="modalbody2">
                                                           </div>
                                                       </div>
                                                   </div>
                                                  </form>
                                                </div>
                                              </div>
                                            </div>
                                          
                                          <!-- Modal -->
                                            
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
        <script src="<%= approot%>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot%>/assets/bootstrap/js/bootstrap.js"></script>
        <!-- Select2 -->
        <script src="<%= approot%>/assets/plugins/select2/select2.full.min.js"></script>
        <!-- FastClick -->
        <script src="<%= approot%>/assets/plugins/fastclick/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="<%= approot%>/assets/dist/js/app.js"></script>
        
        <script src="<%= approot%>/assets/bootstrap/datepicker/bootstrap-datepicker.js"></script>
        <!-- SlimScroll 1.3.0 -->
        <script src="<%= approot%>/assets/plugins/slimScroll/jquery.slimscroll.js"></script>
        <script src="<%= approot %>/assets/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
        <script src="<%= approot %>/assets/plugins/datatables/dataTables.bootstrap.js" type="text/javascript"></script>
        
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
                
                var datePicker = function(contentId, formatDate){
                    $(contentId).datepicker({
                 format : formatDate
                    });
                    $(contentId).on('changeDate', function(ev){
                 $(this).datepicker('hide');
                    });
                };
                
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
                
                
                $(function(){
                    $('#groups').on('change', function(){
                        var val = $(this).val();
                        var sub = $('#sub_groups');
                        $('option', sub).filter(function(){
                            if (
                                 $(this).attr('data-group') === val 
                              || $(this).attr('data-group') === 'SHOW'
                            ) {
                              if ($(this).parent('span').length) {
                                $(this).unwrap();
                              }
                            } else {
                              if (!$(this).parent('span').length) {
                                $(this).wrap( "<span>" ).parent().hide();
                              }
                            }
                        });
                    });
                    $('#groups').trigger('change');
                });
                
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
                       var oidTemp = getUrlParameter("oidtemp");
                       var dataFor = "liststrMapp";
                       var commandlist = $("#commandlist").val();
                       var start = $("#start").val();
                       var dataSend = {
                           "oid" : oid,
                           "oidtemp" : oidTemp,
                           "datafor" : dataFor,
                           "command" : commandlist,
                           "start" : start
                       }
                       var onDone = function(data){
                           $(selector).html(data).fadeIn("medium");
                           $('#liststructure').dataTable( {
                           
                           } );
                           
                           addEdit(".addeditdata");
                           deleteData(".deletedata")
                       }
                       
                       var onSuccess = function(data){
                           
                       };
                       $(selector).html("Please wait..").fadeIn("medium");
                       sendData("<%= approot %>/masterdata/ajax/strMapping_ajax.jsp", dataSend, onDone, onSuccess);
                   
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
                           sendData("<%= approot %>/masterdata/ajax/strMapping_ajax.jsp", dataSend, onDone, onSuccess);
                       }
                        
                   });
                }
                
                
                function addEdit(selector){
                    $(selector).click(function(){
                       var oid = $(this).data("oid");
                       var oidTemp = $(this).data("oidtemp");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid").val(oid);
                       $("#oidtemp").val(oidTemp);
                       $("#datafor").val(dataFor);
                       $("#command").val("4");
                       
                       if(oid != 0){
                        if(dataFor == 'showstrT'){
                        $(".addeditgeneral-title").html("Edit Structure");
                        }

                        }else{
                            if(dataFor == 'showstrT'){
                                $(".addeditgeneral-title").html("Add Structure");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "oidtemp" : oidTemp,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                           addEdit2(".browse");
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal").modal("show");
                       $("#modalbody").html("Please wait...");
                       
                       sendData("<%= approot %>/masterdata/ajax/strMapping_ajax.jsp", dataSend1, onDone1, onSuccess1);
                    });
                    
                    
                }
                function addEdit2(selector){
                    $(selector).click(function(){
                        
                       var oid = $(this).data("oid");
                       var dataFor = $(this).data("for");
                       var command = $(this).data("command");
                       
                       $("#oid2").val(oid);
                       $("#datafor2").val(dataFor);
                       $("#command2").val("4");
                       $("#positionname").val("");
                       if(oid != 0){
                        if(dataFor == 'filterup'){
                        $(".addeditgeneral-title2").html("Up Position");
                        }else{
                            $(".addeditgeneral-title2").html("Down Position");
                        }

                        }else{
                            if(dataFor == 'filterup'){
                                $(".addeditgeneral-title2").html("Up Position");
                            }else{
                                $(".addeditgeneral-title2").html("Down Position");
                            }
                        }
                       
                       var dataSend1 = {
                           "oid" : oid,
                           "datafor" : dataFor,
                           "command" : command
                       }
                       var onDone1 = function(data){
                           $("#modalbody2").html(data).fadeIn("medium");
                           datePicker(".datepicker","yyyy-mm-dd");
                           $(".item").click(function(){
                               var selectedOid = $(this).data("oid");
                               var selectedName = $(this).data("name");
                               var targetOid = $(this).data("targetOid");
                               var targetName = $(this).data("targetName");
                               // isiin di tag a href data-oid='' data-name='' data-target-oid='' data-target-name=''
                               
                               $(targetName).val(selectedName);
                               $(targetOid).val(selectedOid);
                               
                               $("#myModal2").modal("hide");
                           });
                       }
                       
                       var onSuccess1 = function(data){
                           
                       };
                       
                       $("#myModal2").modal("show");
                       $("#modalbody2").html("Please wait...");
                       
                       sendData("<%= approot %>/masterdata/structure_upposition_form.jsp", dataSend1, onDone1, onSuccess1);
                    });
                    
                    
                }
                modalSetting("#myModal", false, false, 'static');
                modalSetting("#myModal2", false, false, 'static');
                loadData("#listdata");
                
                $("form#form-modal").submit(function(){
                    var dataSend = $(this).serialize();
                    var onDone = function(data){
                        $("#myModal").modal("hide");
                        loadData("#listdata");
                        
                    }
                    var onSuccess = function(data){
                    }
                    sendData("<%= approot %>/masterdata/ajax/strMapping_ajax.jsp", dataSend, onDone, onSuccess);
                    return false;
                });
                
                $("#positionname").keyup(function(){
                    
                    var positionName = $(this).val();
                    var dataFor = $("#datafor2").val();
                   var dataSend = {
                       "position_name" : positionName,
                       "datafor" : dataFor
                   };
                   var onDone = function(data){
                       $("#modalbody2").html(data).fadeIn("medium");
                        $(".item").click(function(){
                             var selectedOid = $(this).data("oid");
                             var selectedName = $(this).data("name");
                             var targetOid = $(this).data("targetOid");
                             var targetName = $(this).data("targetName");
                             // isiin di tag a href data-oid='' data-name='' data-target-oid='' data-target-name=''

                             $(targetName).val(selectedName);
                             $(targetOid).val(selectedOid);

                             $("#myModal2").modal("hide");
                         });
                   }
                   
                   var onSuccess = function(data){
                       
                   }
                   
                   sendData("<%= approot %>/masterdata/structure_upposition_form.jsp", dataSend, onDone, onSuccess);
                    
                });
            });
        </script>
       </body>
</html>
