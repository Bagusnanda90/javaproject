<%-- 
    Document   : custom_rpt_generate_new
    Created on : 15-May-2017, 11:07:32
    Author     : Gunadi
--%>

<%@page import="com.dimata.harisma.entity.payroll.PstPayBanks"%>
<%@page import="com.dimata.harisma.entity.payroll.PayBanks"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptDynamic"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptConfig"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file = "../../main/javainit.jsp" %>


<%!
 /* Update 2016-04-15 */
    public String getBankName(String oid){
        String name = "";
        if (oid.equals("0")){
            name = "CASH";
        } else {
            try {
                PayBanks payBank = PstPayBanks.fetchExc(Long.valueOf(oid));
                name = payBank.getBankName();
            } catch(Exception e){
                System.out.println(e.toString());
            }
        }
        
        return name;
    }
%>
<%
    long oidCustom = FRMQueryString.requestLong(request, "oid_custom");
    
        /* menentukan nilai custom report to Export XLS */
    String dataExportExcel = "";
    boolean ketemuData = false;
    Vector listCustToXLS = PstCustomRptConfig.list(0, 0, PstCustomRptConfig.fieldNames[PstCustomRptConfig.FLD_RPT_MAIN_ID]+"="+oidCustom, "");
    if (listCustToXLS != null && listCustToXLS.size()>0){
        for(int x=0; x<listCustToXLS.size(); x++){
            CustomRptConfig cRc = (CustomRptConfig)listCustToXLS.get(x);
            if (cRc.getRptConfigTableGroup().equals("pay_slip")){
                ketemuData = true;
            }
        }
    }
    if (ketemuData == true){
        dataExportExcel = "CustomRptXLS";
    } else {
        dataExportExcel = "CustomRptVersi2XLS";
    }
%>

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
                        Custom Report Generate
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li>Custom Report</li>
                        <li class="active">Custom Report Generate</li>
                    </ol>
                </section>
                <section class="content">
                  <!-- Small boxes (Stat box) -->
                  <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                                
                                        <div class="box-header with-border">
                                            <h4 class="box-title">Operator Description</h4>
                                            <div class="box-tools pull-right">
                                                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                            </div><!-- /.box-tools -->
                                        </div>
                                        <div class="box-body">
                                            <p>
                                                =, !=, >, <, >=, <= <br/>
                                                This operator is used for only one of data, either number or letters. Example : name = 'John' or Salary = 1000. But in this field, just type value of data. Example : John or 219. 
                                            </p>
                                            <p>
                                                <b>IN</b><br/>
                                                It is used for some of data which it have same type. Example: IN(123, 456, 78) or IN('abc', 'def'). Just type test, text, etc. 
                                            </p>
                                            <p>
                                                <b>BETWEEN</b><br/>
                                                The BETWEEN operator is used when you want to get data by range. E.g: 2014-12-01 To 2015-01-01. So you just type 2014-12-01 2015-01-01. ([date_from][space][date_to]) Note: the between operator is used if the value is date. 
                                            </p>
                                            <p>
                                                <b>LIKE</b><br/>
                                                You can type not complete value. E.g: You want to find some of data that has letter 'a', so you must type: %a%
                                            </p>
                                        </div>
                                </div>
                        <div class='box box-primary'>
                            <div class='box-header'>
                               Custom Report Generate
                            </div>
                            <div class="box-body">
                                
                                
                                <div class="col-md-10" id="filter">
                                    <h4>Selection Default</h4>
                                    <form role="form" class="form-horizontal" id="formselection">
                                        <input type="hidden" name="oid_custom" value="<%=oidCustom%>" />
                                        <input type="hidden" name="generate" value="" />
                                        <input type="hidden" name="query_result" value="" />
                                        <input type="hidden" name="selection_choose" value="" />
                                        <input type="hidden" name="FRM_FIELD_DATA_FOR" id="generaldatafor"/>
                                        <input type="hidden" name="command" id="command" value="1">
                                    <%
                                    if (oidCustom != 0){
                                        /* mengambil where yang ada di config report */
                                        Vector listWhere = PstCustomRptConfig.listWhere(oidCustom);
                                        if (listWhere != null && listWhere.size()>0){
                                            for(int i=0; i<listWhere.size(); i++){
                                                CustomRptConfig selection = (CustomRptConfig)listWhere.get(i);
                                                %>
                                                <div class="form-group">
                                                    <div class="col-sm-2">
                                                        <b><%=selection.getRptConfigFieldHeader()%></b>
                                                    </div>
                                                    <div class="col-sm-1">
                                                        <input type="hidden" name="where_field" value="<%=selection.getRptConfigTableName()+"."+selection.getRptConfigFieldName()%>" />
                                                        <select name="operator" id="operator">
                                                            <option value="=">=</option>
                                                            <option value="!=">!=</option>
                                                            <option value=">">></option>
                                                            <option value="<"><</option>
                                                            <option value=">=">>=</option>
                                                            <option value="<="><=</option>
                                                            <option value="BETWEEN">BETWEEN</option>
                                                            <option value="LIKE">LIKE</option>
                                                            <option value="IN">IN</option>
                                                        </select>
                                                    </div>
                                                        <%
                                                        String fieldName = selection.getRptConfigFieldHeader();
                                                        String[] dataFields = fieldName.split(" ");
                                                        boolean isDate = false;
                                                        if (dataFields != null && dataFields.length>0){
                                                            for(int df=0; df<dataFields.length; df++){
                                                                String hurufkecil = dataFields[df].toLowerCase();
                                                                if (hurufkecil.equals("date")){
                                                                    isDate = true;
                                                                }
                                                            }
                                                        }
                                                        if (isDate == true){
                                                            %>
                                                            <div class="col-sm-3">
                                                                <input type="text" name="where_value" value="" size="70" />
                                                            </div>
                                                            <%
                                                        } else {
                                                            %>
                                                            <div class="col-sm-3">
                                                                <select name="where_value" class="chosen-select">
                                                                    <option value="">-SELECT-</option>
                                                                    <%
                                                                    String sqlData = "SELECT DISTINCT "+selection.getRptConfigFieldName()+" FROM "+selection.getRptConfigTableName();
                                                                    Vector listSelectData = PstCustomRptConfig.listSelectData(sqlData, selection.getRptConfigFieldName());
                                                                    if (listSelectData != null && listSelectData.size()>0){
                                                                        if (selection.getRptConfigFieldName().equals("BANK_ID") || selection.getRptConfigFieldName().equals("STATUS_DATA")){
                                                                            if (selection.getRptConfigFieldName().equals("BANK_ID")){

                                                                                for(int sd=0; sd<listSelectData.size(); sd++){
                                                                                    CustomRptDynamic dycData = (CustomRptDynamic)listSelectData.get(sd);
                                                                                    %>
                                                                                    <option value="<%=dycData.getField(selection.getRptConfigFieldName())%>"><%= getBankName(dycData.getField(selection.getRptConfigFieldName())) %></option>
                                                                                    <%
                                                                                }        
                                                                            }
                                                                            if (selection.getRptConfigFieldName().equals("STATUS_DATA")){
                                                                                %>
                                                                                <option value="0">Active</option>
                                                                                <option value="1">History</option>
                                                                                <%
                                                                            }
                                                                        } else {
                                                                            for(int sd=0; sd<listSelectData.size(); sd++){
                                                                                CustomRptDynamic dycData = (CustomRptDynamic)listSelectData.get(sd);
                                                                                %>
                                                                                <option value="<%=dycData.getField(selection.getRptConfigFieldName())%>"><%= dycData.getField(selection.getRptConfigFieldName()) %></option>
                                                                                <%
                                                                            }
                                                                        }
                                                                    }
                                                                    %>

                                                                </select>
                                                            <%
                                                        }
                                                        %>

                                                        <input type="hidden" name="where_type" value="<%=selection.getRptConfigFieldType()%>" />
                                                        </div>                                                   
                                                </div>
                                                <%
                                            }
                                        }
                                    }
                                    %>
                                    <div class="form-group">
                                        <button type="button" class="btn btn-primary btngenerate"><i class="fa fa-check"></i> Generate</button>
                                        <button type="button" id="printXLS" class="btn btn-primary"><i class="fa fa-file"></i>&nbsp;&nbsp;Export to Excel</button>
                                        <button type="button" id="sendEmail" data-for="showSendEmailForm" class="btn btn-primary"><i class="fa fa-envelope"></i>&nbsp;&nbsp;Send List to Email</button>
                                    </div>
                                    </form>
                                </div>
                                    <div id="tableResult" class="col-md-12">
                                        
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
	    };
            
            //SHOW ADD OR EDIT FORM
            var generate = function (elementId) {
                $(elementId).click(function () {
                    command = <%= Command.LIST%>;
                    approot = "<%= approot %>";
                    $("#generaldatafor").val("listGenerate");
                    $("#command").val("<%= Command.LIST%>");
                    dataSend = $("form#formselection").serialize();
                    onDone = function (data) {
                    };
                    onSuccess = function (data) {

                    };
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "AjaxCustomRptGenerate", "#tableResult", false, "json");
                });
            };
            
            $("#printXLS").click(function(){
                dataSend = $("form#formselection").serialize();
                var servletName = "<%=dataExportExcel%>";
                window.open("<%=printroot%>.report.payroll."+servletName+"?"+dataSend);
            });
            
            $("#sendEmail").click(function(){
                    $("#sendEmailModal").modal("show");
                    if ($(window).width() > 480) {
                            $(".modal-dialog").css("width", "85%");
                        } else {
                            $(".modal-dialog").css("width", "100%");
                        }
                    
                    var dataFor = $(this).data("for");
                    
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

                    dataSend = $("form#formselection").serialize()+"&"+$(this).serialize();
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "servlet/com.dimata.harisma.report.payroll.CustomRptXLSEmail", null, false, null);
                } else {
                    $("#btngeneralform").removeAttr("disabled").html(currentBtnHtml);
                }

                return false;
            });
            
            generate(".btngenerate");
            
            $("[data-widget='collapse']").click(function() {
                //Find the box parent........
                var box = $(this).parents(".box").first();
                //Find the body and the footer
                var bf = box.find(".box-body, .box-footer");
                if (!$(this).children().hasClass("fa-plus")) {
                    $(this).children(".fa-minus").removeClass("fa-minus").addClass("fa-plus");
                    bf.slideUp();
                } else {
                    //Convert plus into minus
                    $(this).children(".fa-plus").removeClass("fa-plus").addClass("fa-minus");
                    bf.slideDown();
                }
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
   </div><!-- ./wrapper -->
    </body>
</html>

