<%-- 
    Document   : do_resign
    Created on : 01-Jul-2016, 11:35:45
    Author     : Acer
--%>

<%@page import="com.dimata.common.entity.contact.ContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.PstContactClassAssign"%>
<%@page import="com.dimata.common.entity.contact.ContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.harisma.entity.masterdata.*"%>
<%@page import="com.dimata.harisma.form.masterdata.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.harisma.form.employee.*"%>
<%@page import="com.dimata.harisma.entity.employee.*"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ include file = "../../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_EMPLOYEE_MENU, AppObjInfo.OBJ_EMPLOYEE_AND_FAMILY);%>
<!--%@ include file = "../../main/checkuser.jsp" %-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    long employeeid = FRMQueryString.requestLong(request, "empId");
    int iCommand = FRMQueryString.requestInt(request, "command");
    int start = FRMQueryString.requestInt(request, "start");
    String datafor = FRMQueryString.requestString(request, "datafor");
    long oid = FRMQueryString.requestLong(request, "oid");
    
    if(datafor.equals("doresignform")){

        if(iCommand == Command.SAVE){
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            ctrlEmployee.action(iCommand, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else if(iCommand == Command.DELETE){
            CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
            ctrlEmployee.action(iCommand, employeeid, request, emplx.getFullName(), appUserIdSess);
        }else{
            Employee employee = new Employee();
            if(employeeid != 0){
                try{
                    employee = PstEmployee.fetchExc(employeeid);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_ID]%>"  value="<%=employee.getOID()%>" class="form-control"  >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]%>"  value="<%=employee.getEmployeeNum()%>" class="form-control"  >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]%>" value="<%=employee.getFullName()%>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS]%>" size="50" value="<%=employee.getAddress()%>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_COUNTRY_ID]%>" value="<%="" + employee.getAddrCountryId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PROVINCE_ID]%>" value="<%="" + employee.getAddrProvinceId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_REGENCY_ID]%>" value="<%="" + employee.getAddrRegencyId()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrSubRegencyId()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_PERMANENT]%>" size="50" value="<%=employee.getAddressPermanent() %>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_COUNTRY_ID]%>" value="<%="" + employee.getAddrPmntCountryId()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_PROVINCE_ID]%>" value="<%="" + employee.getAddrPmntProvinceId()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_REGENCY_ID]%>" value="<%="" + employee.getAddrPmntRegencyId()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDR_PMNT_SUBREGENCY_ID]%>" value="<%="" + employee.getAddrPmntSubRegencyId()%>" >   
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSTAL_CODE]%>" value="<%=employee.getPostalCode() %>" class="form-control" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_HANDPHONE]%>" value="<%=employee.getHandphone()%>" class="form-control" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NAME_EMG]%>" value="<%=employee.getNameEmg()%>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ADDRESS_EMG]%>" value="<%=employee.getAddressEmg()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_PLACE]%>" value="<%=employee.getBirthPlace()%>" class="form-control" >
            <%
            String DATE_FORMAT_NOW = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            Date birthDate = employee.getBirthDate()== null ? new Date() : employee.getBirthDate();
            String strBirthDate = sdf.format(birthDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BIRTH_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strBirthDate %>">
            <input type="hidden" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SHIO]%>" value="<%=employee.getShio()%>" class="form-control"> 
            <input type="hidden" disabled="disable" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ELEMEN]%>" value="<%=employee.getElemen()%>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RELIGION_ID]%>" value="<%=employee.getReligionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MARITAL_ID]%>" value="<%=employee.getMaritalId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_TAX_MARITAL_ID]%>" value="<%=employee.getTaxMaritalId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BLOOD_TYPE]%>" value="<%=employee.getBloodType()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RACE]%>" value="<%=employee.getRace()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BARCODE_NUMBER]%>" value="<%=employee.getBarcodeNumber()%>" class="form-control">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ID_CARD_TYPE]%>" value="<%=employee.getIdcardtype()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_NR]%>" value="<%=employee.getIndentCardNr()%>" class="form-control">
            <%
            Date idDate = employee.getIndentCardValidTo()== null ? new Date() : employee.getIndentCardValidTo();
            String strIdDate = sdf.format(idDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_INDENT_CARD_VALID_TO_STRING]%>" value="<%=strIdDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_TYPE]%>" value="<%=employee.getSecondaryIdType()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_NO]%>" value="<%=employee.getSecondaryIdNo()%>">
            <%
            Date secIdDate = employee.getSecondaryIDValid()== null ? new Date() : employee.getSecondaryIDValid();
            String strSecIdDate = sdf.format(secIdDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECONDARY_ID_DATE_STRING]%>" value="<%=strSecIdDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMAIL_ADDRESS]%>" value="<%=employee.getEmailAddress()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PAYROLL_GROUP]%>" value="<%=employee.getPayrollGroup()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_IQ]%>" value="<%=employee.getIq()%>" >
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EQ]%>" value="<%=employee.getEq()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NO_REKENING]%>" value="<%=employee.getNoRekening()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_NPWP]%>" value="<%=employee.getNpwp()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMPANY_ID]%>" value="<%=employee.getCompanyId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DIVISION_ID]%>" value="<%=employee.getDivisionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DEPARTMENT_ID]%>" value="<%=employee.getDepartmentId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_SECTION_ID]%>" value="<%=employee.getSectionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_CATEGORY_ID]%>" value="<%=employee.getEmpCategoryId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_LEVEL_ID]%>" value="<%=employee.getLevelId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_GRADE_LEVEL_ID]%>" value="<%=employee.getGradeLevelId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_POSITION_ID]%>" value="<%=employee.getPositionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_COMPANY_ID]%>" value="<%=employee.getWorkassigncompanyId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DIVISION_ID]%>" value="<%=employee.getWorkassigndivisionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_DEPARTMENT_ID]%>" value="<%=employee.getWorkassigndepartmentId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_SECTION_ID]%>" value="<%=employee.getWorkassignsectionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WORK_ASSIGN_POSITION_ID]%>" value="<%=employee.getWorkassignpositionId()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROVIDER_ID]%>" value="<%=employee.getWorkassignpositionId()%>">
            <%
            Date waFromDate = employee.getWaFrom()== null ? new Date() : employee.getWaFrom();
            Date waToDate = employee.getWaTo()== null ? new Date() : employee.getWaTo();
            String strWaFromDate = sdf.format(waFromDate);
            String strWaToDate = sdf.format(waToDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WA_FROM_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strWaFromDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_WA_TO_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%=strWaToDate %>">
            <%
            Date commDate = employee.getCommencingDate()== null ? new Date() : employee.getCommencingDate();
            String strCommDate = sdf.format(commDate);
            %>
            <input type="hidden" name="<%= FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_COMMENCING_DATE_STRING] %>" class="form-control pull-right datepicker" id="datepicker" value="<%= strCommDate%>">
            <%
            Date probDate = employee.getProbationEndDate()== null ? new Date() : employee.getProbationEndDate();
            String strProbDate = sdf.format(probDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_PROBATION_END_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strProbDate %>">
            <%
            Date endContractDate = employee.getEnd_contract()== null ? new Date() : employee.getEnd_contract();
            String strContractDate = sdf.format(endContractDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_END_CONTRACT_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strContractDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_NUM]%>" value="<%=employee.getAstekNum()%>" class="form-control">
            <%
            Date astekDate = employee.getAstekDate()== null ? new Date() : employee.getAstekDate();
            String strAstekDate = sdf.format(astekDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_ASTEK_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strAstekDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_NO]%>" value="<%=employee.getBpjs_no()%>" class="form-control">
            <%
            Date bpjsDate = employee.getBpjs_date()== null ? new Date() : employee.getBpjs_date();
            String strBpjsDate = sdf.format(bpjsDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_BPJS_DATE_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strBpjsDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MEMBER_OF_BPJS_KESEHATAN]%>" value="<%=employee.getMemOfBpjsKesahatan()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_MEMBER_OF_BPJS_KETENAGAKERJAAN]%>" value="<%=employee.getMemOfBpjsKetenagaKerjaan()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_STATUS_PENSIUN_PROGRAM]%>" value="<%=employee.getStatusPensiunProgram()%>">
            <%
            Date pensiunDate = employee.getStartDatePensiun()== null ? new Date() : employee.getStartDatePensiun();
            String strPensiunDate = sdf.format(pensiunDate);
            %>
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_START_DATE_PENSIUN_STRING]%>" class="form-control pull-right datepicker" id="datepicker" value="<%= strPensiunDate %>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_DANA_PENDIDIKAN]%>" value="<%=employee.getDanaPendidikan()%>">
            <input type="hidden" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMP_PIN]%>" value="<%=employee.getEmpPin()%>">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-sm-3">Resigned Status</label>
                        <div class="col-sm-9">
                            <% for (int i = 0; i < PstEmployee.resignValue.length; i++) {
                                String strRes = "";
                                if (employee.getResigned() == PstEmployee.resignValue[i]) {
                                    strRes = "checked";
                                }
                            %> <input type="radio" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED]%>" value="<%="" + PstEmployee.resignValue[i]%>" <%=strRes%> style="border:'none'">
                            <%=PstEmployee.resignKey[i]%> <%}%> 
                        
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3">Resigned Date</label>
                        <div class="col-sm-9">
                            <%
                            Date resignDate = employee.getResignedDate()== null ? new Date() : employee.getResignedDate();
                            String strResignDate = sdf.format(resignDate);
                            %>
                            <input type="text" name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DATE_STRING]%>" value="<%=strResignDate%>" class="form-control datepicker" id="datepicker">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3">Resigned Reason</label>
                        <div class="col-sm-9">
                            <select name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_REASON_ID]%>" class="form-control">
                                <%
                                    Vector listRes = PstResignedReason.list(0, 0, "", "RESIGNED_REASON");
                                    for (int i = 0; i < listRes.size(); i++) {
                                    ResignedReason resignedReason = (ResignedReason) listRes.get(i);
                                    if(employee.getResignedReasonId() == resignedReason.getOID()){
                                        %><option selected="selected" value="<%=resignedReason.getOID()%>"><%=resignedReason.getResignedReason()%></option><%
                                    } else {
                                        %><option value="<%=resignedReason.getOID()%>"><%=resignedReason.getResignedReason()%></option><%
                                    }
                                }   
                                %>    
                            </select>    
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3">Resigned Description</label>
                        <div class="col-sm-9">
                            <textarea name="<%=FrmEmployee.fieldNames[FrmEmployee.FRM_FIELD_RESIGNED_DESC]%>" class="form-control" rows="2" cols="30"><%=employee.getResignedDesc()%></textarea>
                        </div>
                    </div>
                </div>
            </div>
            
            
            
        <%
    }
}
    
%>    
    