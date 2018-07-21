<%-- 
    Document   : contact_company_list_ajax
    Created on : Aug 16, 2016, 8:36:28 AM
    Author     : ARYS
--%>





<%@page import="com.dimata.common.entity.contact.PstContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.ContactClassAssign"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*"%>
<%@ page import = "com.dimata.qdep.form.*"%>
<%@page import="java.util.Vector" %>
<%@page import="com.dimata.common.entity.contact.ContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.form.search.FrmSrcContact" %>
<%@page import="com.dimata.common.form.search.FrmSrcContactList" %>
<%@page import="com.dimata.common.form.contact.CtrlContactClass" %>
<%@page import="com.dimata.common.form.contact.CtrlContactList" %>
<%@page import="com.dimata.common.session.contact.SessContactList"%>
<%@page import="com.dimata.common.entity.search.SrcContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.form.contact.FrmContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@ include file = "../../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
public boolean getStatus(long index, Vector vct){
	if(vct!=null && vct.size()>0){
		for(int i=0; i<vct.size(); i++){
			ContactClassAssign cntAs = (ContactClassAssign)vct.get(i);
			if(index == cntAs.getContactClassId()){
				return true;
			}
		}
	}
	return false;
}
%>


<%
    String conname = FRMQueryString.requestString(request, "contact_name");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    boolean sameContactCode = false;
    String whereClause = "";
    
    
    if(datafor.equals("listcc")){
        String order = "";
        Vector listContCom = new Vector();
        
        CtrlContactList ctrlContactList = new CtrlContactList(request);
        Vector cntClass = PstContactClass.listAll();
        
        
        int recordToGet = 0;
        int vectSize = 0;
        vectSize = PstContactList.getCount("");
        if ((iCommand == Command.FIRST) || (iCommand == Command.NEXT) || (iCommand == Command.PREV)
                || (iCommand == Command.LAST) || (iCommand == Command.LIST)) {
            start = ctrlContactList.actionList(iCommand, start, vectSize, recordToGet);
        }

        if (!(conname.equals(""))){
            whereClause = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+" LIKE '%"+conname+"%'";
            order = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID];
            vectSize = PstContactList.getCount(whereClause);
            listContCom = PstContactList.list(0, 0, whereClause, order);        
        } else {
            vectSize = PstContactList.getCount("");
            order = PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID];
            listContCom = PstContactList.list(start, recordToGet, "", order);
        }

        if (listContCom != null && listContCom.size()>0){
        %>
        <table id="ContCom" class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Code</th>
                    <th>Company Name</th>
                    <th>Telephone</th>
                    <th>Address</th>
                    <th>Town</th>
                    <th>Province</th>
                    <th>Country</th>
                    <th width="60px">action</th>
                    </tr>
            </thead>
            <%
            for (int i = 0; i < listContCom.size(); i++) {
                ContactList contactList = (ContactList) listContCom.get(i);

            %>
            <tr>
                    <td><%=(i + 1)%></td>
                    <td><%=contactList.getContactCode()%></td>
                    <td><%=contactList.getCompName()%></td>
                    <td><%= contactList.getTelpNr() %></td>
                    <td><%= contactList.getBussAddress() %></td>
                    <td><%= contactList.getTown() %></td>
                    <td><%= contactList.getProvince() %></td>
                    <td><%= contactList.getCountry() %></td>
                    <td>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= contactList.getOID() %>" class="addeditdata btn btn-primary" data-command="<%= Command.NONE %>" data-for="showCform"  data-toggle="tooltip" data-placement="top" title="Edit"><i class="fa fa-edit"></i></button>
                    <button type="button" onclick="location.href='javascript:'" data-oid="<%= contactList.getOID() %>" data-for="showCform" data-command="<%= Command.DELETE %>" class="btn btn-danger deletedata"  data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-trash-o"></i></button>
                    </td>
                </tr>
                <%
            }
            %>
        </table>
        <%
        } else { %>
            <a> <h4>No Record </h4></a>
        <% }
        }else if(datafor.equals("showCform")){

        if(iCommand == Command.SAVE){
            Vector cntClass = PstContactClass.listAll();
            Vector classAssign = new Vector(1,1);
            for(int a=0;a<cntClass.size();a++){
                    long oidAssign = FRMQueryString.requestLong(request,"contact_"+a);
                    if(oidAssign!=0){
                            ContactClassAssign cntAssign = new ContactClassAssign();
                            cntAssign.setContactClassId(oidAssign);
                            cntAssign.setContactId(oid);
                            classAssign.add(cntAssign);
                    }
            }
            
           
            
            CtrlContactList ctrlContactList = new CtrlContactList(request);
            ctrlContactList.action(iCommand, oid, classAssign, sameContactCode);
        }else if(iCommand == Command.DELETE){
            Vector classAssign = new Vector(1,1);
            CtrlContactList ctrlContactList = new CtrlContactList(request);
            ctrlContactList.action(iCommand, oid, classAssign, sameContactCode);
        }else{
            ContactList contactList = new ContactList();
            if(oid != 0){
                try{
                     contactList = PstContactList.fetchExc(oid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        
        %> 
        <input type="hidden" name="<%=FrmContactList.fieldNames[FrmContactList.FRM_FIELD_EMPLOYEE_ID]%>" value="0">
       <input type="hidden" name="<%=FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_TYPE]%>" value="<%=PstContactList.EXT_COMPANY%>">
                <div class="form-group">
                    <label class="col-sm-3" >Code :</label>
                    <div class="col-sm-7"><input type="text" class="" id="company" value="<%= contactList.getContactCode() %>" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_CODE] %>"/>
                    <font color="red">*</font></div>
                </div>


                <div class="form-group">
                    <label class="col-sm-3">Contact Group :</label>
                    <div class="col-sm-9">
                        <%
                            Vector listClass = PstContactClass.listAll();
                            Vector classAssign = new Vector(1,1);
                            String whereAssign = " "+PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CONTACT_ID]+" = "+oid;
                            classAssign = PstContactClassAssign.list(0,0,whereAssign,"");
                            
                            if(listClass.size() > 0){
                                for(int i = 0; i < listClass.size(); i++){
                                    ContactClass contactClass = (ContactClass) listClass.get(i);
                                    %>
                                        <div class="col-md-4">
                                            <input type="checkbox"  name="contact_<%= i %>" <%if(getStatus(contactClass.getOID(), classAssign)){%>checked<%}%> value="<%= contactClass.getOID() %>"> <%= contactClass.getClassName() %>
                                            <font color="red">*</font></div>
                                    <%
                                }
                            }
                        %>
                    </div>
                </div>
            
                    
                <div class="form-group">
                    <label class="col-sm-3">Company Name :</label>
                       <div class="col-sm-9"> <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COMP_NAME]%>" value="<%= contactList.getCompName() %>"/>
                      </div>
                </div>
        
                <div class="form-group">
                    <label class="col-sm-3">Person Name :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" value="<%= contactList.getPersonName() %>">
                        </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-sm-3">Person Last Name :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_LASTNAME]%>" value="<%= contactList.getPersonLastname() %>">
                    </div>
                </div>
            
                <div class="form-group">
                    <label class="col-sm-3">Address :</label>
                    <div class="col-sm-9">
                        <textarea type="text" class="" style="width: 860px; height: 77px;" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_BUSS_ADDRESS] %>"><%= contactList.getBussAddress() %></textarea>
                    <font color="red">*</font></div>
                </div>
       
                <div class="form-group">
                    <label class="col-sm-3">Town :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TOWN] %>" value="<%= contactList.getTown() %>">
                    </div>
                </div>
       
                <div class="form-group">
                    <label class="col-sm-3">Province :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PROVINCE]%>" value="<%= contactList.getProvince() %>" />
                    </div>
                </div>
       
                <div class="form-group">
                    <label class="col-sm-3">Country :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COUNTRY] %>" value="<%= contactList.getCountry() %>"> 
                    </div>
                </div>
       
                <div class="form-group">
                    <label class="col-sm-3">Phone :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_NR] %>" value="<%= contactList.getTelpNr() %>"> 
                    </div>
                </div> 
       
                <div class="form-group">
                    <label class="col-sm-3">Handphone :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>" value="<%= contactList.getTelpMobile() %>">
                    </div>
                </div>
      
                <div class="form-group">
                    <label class="col-sm-3">Fax :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_FAX] %>" value="<%= contactList.getFax() %>">
                    </div>
                </div>
            
                 
                <div class="form-group">
                    <label class="col-sm-3">Email :</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_EMAIL] %>" value="<%= contactList.getEmail() %>">
                    </div>
                </div>
                          
        <%
    }
}
    
%>

