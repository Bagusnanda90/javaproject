<%-- 
    Document   : custom_rpt_config_new.jsp
    Created on : Nov 28, 2016, 10:26:20 AM
    Author     : Acer
--%>

<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Custom Report Config | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
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
            overflow-y: auto;
            }
        </style>
        <%@ include file="../../template/css.jsp" %>
        
        
    </head>
    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE %>">
        <input type="hidden" name="approot" id="approot" value="<%= approot %>">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Custom Report Config
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li>Custom Report</li>
                        <li class="active">Custom Report Config</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class='box box-primary'>
                            <div class='box-header'>
                               Custom Report Configuration
                            </div>
                            <div class="box-body">
                                <div id="form">
                                    
                                </div>
                            </div>
                        </div>
                    </div><!-- ./col -->
                  </div><!-- /.row -->

                </section>
            </div><!-- /.content-wrapper -->
            <%@ include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
       
       
        <script type="text/javascript">
	$(document).ready(function(){
            var onDone;
            var onSuccess;
            var approot;
            var command;
            var dataSend;
            var datafor;
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
            
            function pageLoaded(){
                approot = "<%= approot %>";
                command = <%= Command.NONE %>;
                datafor = "showCustomRptConfig";
                var oid = getUrlParameter('oid_custom');
                onDone = function(data){
                    addeditgeneral('.btnadd');
                    deletesingle('.btndelete');
                };
                onSuccess = function(data){
                };
                dataSend = {
                    command : command,
                    FRM_FIELD_APPROOT : approot,
                    FRM_FIELD_DATA_FOR : datafor,
                    FRM_FIELD_OID : oid
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptConfig", "#form", false, "json");
            }
            
            var combine = function(selector){
                $(selector).click(function(){
                   /*var dataCombine = $('input[name="combine_component"]:checked').map(function(_, el) {
                                        return $(el).val();
                                    }).get();
                    alert(dataCombine);   */     
                    var dataCombine = "";
                    $("input[name='combine_component']").each(function(){
                        if($(this).is(":checked")){
                            if(dataCombine.length > 0){
                                dataCombine += ","+$(this).val();
                            }else{
                                dataCombine += $(this).val();
                            }
                        }
                    });
                   var dataFor = $(this).data("for");
                   command = <%= Command.NONE %>;
                   var target = $(this).data("target");

                   dataSend = {
                       "component" : dataCombine,
                       "FRM_FIELD_DATA_FOR" : dataFor,
                       "command" : command
                   }

                   onSuccess = function(data){

                   };

                   onDone = function(data){
                      $(target).val(data.FRM_FIELD_HTML);
                   };

                   
                   getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptConfig", null, false, "json");

                });

            };
            
            //SHOW ADD OR EDIT FORM
            addeditgeneral = function (elementId) {
                $(elementId).click(function () {
                    $("#addeditgeneral").modal("show");
                    command = $("#command").val();
                    var oid = $(this).data('oid');
                    var dataFor = $(this).data('for');
                    var idx = $(this).data('idx');
                    var dataSave = $(this).data('save');
                    $("#generaldatafor").val(dataFor);
                    $("#datasave").val(dataSave);
                    $("#oid").val(oid);
                    $("#tbl_idx").val(idx);
                    
                    dataSend = {
                        "FRM_FIELD_DATA_FOR": dataFor,
                        "FRM_FIELD_OID": oid,
                        "command": command,
                        "tbl_idx" : idx,
                        "FRM_FIELD_DATA_SAVE" : dataSave
                    }
                    onDone = function (data) {
                        combine('.btncombine');
                        
                    };
                    onSuccess = function (data) {

                    };
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptConfig", ".addeditgeneral-body", false, "json");
                });
            };
            
            deletesingle = function (elementId) {
                $(elementId).unbind().click(function () {
                    var dataFor = $(this).data("for");
                    var vals = $(this).data("oid");
                    var confirmTextSingle = "Are you sure want to delete these data?";

                    command = <%= Command.DELETE%>;
                    var currentHtml = $(this).html();
                    $(this).html("Deleting...").attr({"disabled": true});
                    if (confirm(confirmTextSingle)) {
                        dataSend = {
                            "FRM_FIELD_DATA_FOR": dataFor,
                            "FRM_FIELD_OID": vals,
                            "command": command
                        };
                        onSuccess = function (data) {

                        };
                        onDone = function (data) {
                            pageLoaded();
                        };
                        

                        getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptConfig", null, true, "json");
                        $(this).removeAttr("disabled").html(currentHtml);
                    } else {
                            $(this).removeAttr("disabled").html(currentHtml);
                        }
                });
            };
            
            //FORM SUBMIT
	    $("form#generalform").submit(function(){
		var currentBtnHtml = $("#btngeneralform").html();
		$("#btngeneralform").html("Saving...").attr({"disabled":"true"});
		var generaldatafor = $("#generaldatafor").val();
		
                    onDone = function(data){
			pageLoaded();
		    };
		

		if($(this).find(".has-error").length == 0){
		    onSuccess = function(data){
			$("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
			$("#addeditgeneral").modal("hide");
		    };

		    dataSend = $(this).serialize();
		    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptConfig", null, true, "json");
		}else{
		    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
		}

		return false;
	    });
            
            pageLoaded();
	    
	});
      </script>
      <div id="addeditgeneral" class="modal fade nonprint" tabindex="-1">
	<div class="modal-dialog nonprint">
	    <div class="modal-content">
		<div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h4 class="addeditgeneral-title"></h4>
		</div>
                <form id="generalform" enctype="multipart/form-data">
		    <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor">
                    <input type="hidden" name="FRM_FIELD_DATA_SAVE" id="datasave">
		    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <input type="hidden" name="component" id="component">
		    <input type="hidden" name="FRM_FIELD_OID" id="oid">
                    <input type="hidden" name="tbl_idx" id="tbl_idx">
		    <div class="modal-body ">
			
				<div class="box-body addeditgeneral-body">
                                    
				</div>
			    
			
		    </div>
		    <div class="modal-footer">
			<button type="submit" class="btn btn-primary" id="btngeneralform"><i class="fa fa-check"></i> Add</button>
			<button type="button" data-dismiss="modal" class="btn btn-danger"><i class="fa fa-ban"></i> Close</button>
		    </div>
		</form>
	    </div>
	</div>
    </div> 
   </div><!-- ./wrapper -->
    </body>
</html>
