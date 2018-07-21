<%-- 
    Document   : mutation
    Created on : 18-Jul-2016, 09:32:34
    Author     : Acer
--%>

<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <label class="col-sm-3">Mutation Type</label>
            <div class="col-sm-9">
                <select name="type" id="type" class="form-control">
                    <option value="0">Promotion</option>
                    <option value="1">Rotation</option>
                    <option value="2">Demotion</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-6">
                <label>Old Division</label>
                <select name="old_div" id="old_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
            <div class="col-sm-6">
                <label>New Division</label>
                <select name="new_div" id="new_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-6">
                <label>Old Department</label>
                <select name="old_div" id="old_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
            <div class="col-sm-6">
                <label>New Department</label>
                <select name="new_div" id="new_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-6">
                <label>Old Section</label>
                <select name="old_div" id="old_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
            <div class="col-sm-6">
                <label>New Section</label>
                <select name="new_div" id="new_div" class="form-control">
                    <option value="0">-Select-</option>
                </select>
            </div>
        </div>
    </div>
</div>
    
