<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="com.dimata.harisma.entity.masterdata.Level"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstLevel"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMaster"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMaster"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocMasterFlow"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDocMasterFlow"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocMasterFlow"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDocMasterFlow"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  DocMasterFlow.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: priska
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_PERFORMANCE_APPRAISAL, AppObjInfo.OBJ_GROUP_RANK); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!

	public String drawList(int iCommand,FrmDocMasterFlow frmObject, DocMasterFlow objEntity, Vector objectClass,  long docMasterFlowId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
                 ctrlist.addHeader("Doc Master","30%");
		ctrlist.addHeader("Flow Title","30%");
		ctrlist.addHeader("Flow Index","30%");
		ctrlist.addHeader("Company","30%");
		ctrlist.addHeader("Division","30%");
		ctrlist.addHeader("Department","30%");
		ctrlist.addHeader("Level","30%");
		ctrlist.addHeader("Position","30%");
                ctrlist.addHeader("Employee","30%");
                
                ctrlist.addHeader("Employee","30%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                
                
                Vector docmaster_value = new Vector(1, 1);
                Vector docmaster_key = new Vector(1, 1);
                Vector listdocmaster = PstDocMaster.list(0, 0, "", "");
                docmaster_value.add(""+0);
                docmaster_key.add("select");
                for (int i = 0; i < listdocmaster.size(); i++) {
                    DocMaster docMaster = (DocMaster) listdocmaster.get(i);
                    docmaster_key.add(docMaster.getDoc_title());
                    docmaster_value.add(String.valueOf(docMaster.getOID()));
                }
                
                    
                  
                    
                    Vector position_value = new Vector(1, 1);
                    Vector position_key = new Vector(1, 1);
                    position_key.add("- select -");
                    position_value.add("0");
                    Vector listposition = PstPosition.list(0, 0, null, null);
                    for (int s = 0; s< listposition.size(); s++) {
                        Position position = (Position) listposition.get(s);
                        position_key.add(position.getPosition());
                        position_value.add(String.valueOf(position.getOID()));
                    }
                    
                    Vector level_value = new Vector(1, 1);
                    Vector level_key = new Vector(1, 1);
                    level_key.add("- select -");
                    level_value.add("0");
                    Vector listlevel = PstLevel.list(0, 0, null, null);
                    for (int s = 0; s< listlevel.size(); s++) {
                        Level level = (Level) listlevel.get(s);
                        level_key.add(level.getLevel());
                        level_value.add(String.valueOf(level.getOID()));
                    }
                
		for (int i = 0; i < objectClass.size(); i++) {
			 DocMasterFlow docMasterFlow = (DocMasterFlow)objectClass.get(i);
			 rowx = new Vector();
			 if(docMasterFlowId == docMasterFlow.getOID())
				 index = i; 
                    Employee employee = new Employee();
                    try{
                      employee = PstEmployee.fetchExc( docMasterFlow.getEmployee_id());
                    } catch (Exception e){
                        
                    }
                      
			 if(index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)){
                             
                                 //company
                    Vector comp_value = new Vector(1, 1);
                    Vector comp_key = new Vector(1, 1);
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                    comp_value.add(""+0);
                    comp_key.add("select");
                    for (int c = 0; c < listComp.size(); c++) {
                        Company div = (Company) listComp.get(c);
                        comp_key.add(div.getCompany());
                        comp_value.add(String.valueOf(div.getOID()));
                    }
                
                  //division
                    Vector div_value = new Vector(1, 1);
                    Vector div_key = new Vector(1, 1);
                    //update priska 2015-01-29
                       div_key.add("-select-");
                        div_value.add("0");
                        String strWhere="";
                        if(objEntity.getDoc_master_flow_id()>0){
                        strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + objEntity.getCompany_id() ;//oidCompany; 
                        } else {
                        strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + docMasterFlow.getCompany_id() ;//oidCompany; 
                        }	
                        Vector listDiv = PstDivision.list(0, 0, strWhere, " DIVISION ");
                    
                        for (int c = 0; c < listDiv.size(); c++) {
                        Division div = (Division) listDiv.get(c);
                        div_key.add(div.getDivision());
                        div_value.add(String.valueOf(div.getOID()));
                        
                    }  
                
                  
                    Vector dept_value = new Vector(1, 1);
                    Vector dept_key = new Vector(1, 1);
                    dept_key.add("-select-");
                    dept_value.add("0");
                    String strWhereDept="";
                    if(objEntity.getDoc_master_flow_id()>0){	
                    strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + objEntity.getDivision_id(); //oidDivision;
                    } else {
                    strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + docMasterFlow.getDivision_id(); //oidDivision;
                    }
                    Vector listDept = PstDepartment.list(0, 0, strWhereDept, " DEPARTMENT ");
                    boolean adaDept=false;
                    for (int d = 0; d < listDept.size(); d++) {
                    Department dept = (Department) listDept.get(d);
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    } 
                  
                              
                                      
                             if(objEntity.getDoc_master_flow_id()>0){			
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, "disable"));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE] +"\" value=\""+objEntity.getFlow_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"> ");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX], "formElemen", null, String.valueOf(objEntity.getFlow_index()), frmObject.getindexValue(), frmObject.getindexKey(), ""));
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID], "formElemen", null, String.valueOf(objEntity.getCompany_id()), comp_value, comp_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(objEntity.getDivision_id()), div_value, div_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, String.valueOf(objEntity.getDepartment_id()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID], "formElemen", null, String.valueOf(objEntity.getLevel_id()), level_value, level_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(objEntity.getPosition_id()), position_value, position_key, ""));
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\""+employee.getFullName()+"\" class=\"elemenForm\"> <input type=\"hidden\" name=\""+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]+"\" value=\"\" class=\"formElemen\" >");
                                rowx.add("<a href=\"javascript:cmdSearchEmp()\">add employee</a>");
                             } else{
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(docMasterFlow.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE] +"\" value=\""+docMasterFlow.getFlow_title()+"\" class=\"elemenForm\"> * <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"> ");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX], "formElemen", null, String.valueOf(docMasterFlow.getFlow_index()), frmObject.getindexValue(), frmObject.getindexKey(), ""));
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID], "formElemen", null, String.valueOf(docMasterFlow.getCompany_id()), comp_value, comp_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(docMasterFlow.getDivision_id()), div_value, div_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, String.valueOf(docMasterFlow.getDepartment_id()), dept_value, dept_key, "onChange=\"javascript:cmdUpdateCDDS(1)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID], "formElemen", null, String.valueOf(docMasterFlow.getLevel_id()), level_value, level_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(docMasterFlow.getPosition_id()), position_value, position_key, ""));
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\""+employee.getFullName()+"\" class=\"elemenForm\"> <input type=\"hidden\" name=\""+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]+"\" value=\"\" class=\"formElemen\" >");
                                rowx.add("<a href=\"javascript:cmdSearchEmp()\">add employee</a>");
                            }
			}else{
                             
                                 //company
                    Vector comp_value = new Vector(1, 1);
                    Vector comp_key = new Vector(1, 1);
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                    comp_value.add(""+0);
                    comp_key.add("select");
                    for (int c = 0; c < listComp.size(); c++) {
                        Company div = (Company) listComp.get(c);
                        comp_key.add(div.getCompany());
                        comp_value.add(String.valueOf(div.getOID()));
                    }
                
                  //division
                    Vector div_value = new Vector(1, 1);
                    Vector div_key = new Vector(1, 1);
                    //update priska 2015-01-29
                       div_key.add("-select-");
                        div_value.add("0");
                        String strWhere="";
                      
                        strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + docMasterFlow.getCompany_id() ;//oidCompany; 
                       Vector listDiv = PstDivision.list(0, 0, strWhere, " DIVISION ");
                    
                        for (int c = 0; c < listDiv.size(); c++) {
                        Division div = (Division) listDiv.get(c);
                        div_key.add(div.getDivision());
                        div_value.add(String.valueOf(div.getOID()));
                        
                    }  
                
                  
                    Vector dept_value = new Vector(1, 1);
                    Vector dept_key = new Vector(1, 1);
                    dept_key.add("-select-");
                    dept_value.add("0");
                    String strWhereDept="";
              
                    strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + docMasterFlow.getDivision_id(); //oidDivision;
                    Vector listDept = PstDepartment.list(0, 0, strWhereDept, " DEPARTMENT ");
                    boolean adaDept=false;
                    for (int d = 0; d < listDept.size(); d++) {
                    Department dept = (Department) listDept.get(d);
                    dept_key.add(dept.getDepartment());
                    dept_value.add(String.valueOf(dept.getOID()));
                    } 
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(docMasterFlow.getOID())+"')\">"+docMasterFlow.getFlow_title()+"</a> <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"> ");
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX], "formElemen", null, String.valueOf(docMasterFlow.getFlow_index()), frmObject.getindexValue(), frmObject.getindexKey(), "disabled"));
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID], "formElemen", null, String.valueOf(docMasterFlow.getCompany_id()), comp_value, comp_key, "disabled"));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(docMasterFlow.getDivision_id()), div_value, div_key, "disabled"));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, String.valueOf(docMasterFlow.getDepartment_id()), dept_value, dept_key, "disabled"));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID], "formElemen", null, String.valueOf(docMasterFlow.getLevel_id()), level_value, level_key, "disabled"));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(docMasterFlow.getPosition_id()), position_value, position_key, "disabled"));
                                rowx.add(""+employee.getFullName());
				rowx.add("<a href=\"javascript:cmdSearchEmp()\">add employee</a>");
			} 

			lstData.add(rowx);
		}

		 rowx = new Vector();
 
                 
                  //company
                    Vector comp_value1 = new Vector(1, 1);
                    Vector comp_key1 = new Vector(1, 1);
                    Vector listComp1 = PstCompany.list(0, 0, "", " COMPANY ");
                    comp_value1.add(""+0);
                    comp_key1.add("select");
                    for (int c = 0; c < listComp1.size(); c++) {
                        Company comp1 = (Company) listComp1.get(c);
                        comp_key1.add(comp1.getCompany());
                        comp_value1.add(String.valueOf(comp1.getOID()));
                    }
                  //division
                    Vector div_value1 = new Vector(1, 1);
                    Vector div_key1 = new Vector(1, 1);
                    //update priska 2015-01-29
                       div_key1.add("-select-");
                        div_value1.add("0");
                        String strWhere1 = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + (objEntity.getCompany_id()) ;//oidCompany; 
                        Vector listDiv1 = PstDivision.list(0, 0, strWhere1, " DIVISION ");
                    
                        for (int c = 0; c < listDiv1.size(); c++) {
                        Division div1 = (Division) listDiv1.get(c);
                        div_key1.add(div1.getDivision());
                        div_value1.add(String.valueOf(div1.getOID()));
                        
                    }  
                
                  
                    Vector dept_value1 = new Vector(1, 1);
                    Vector dept_key1 = new Vector(1, 1);
                    dept_key1.add("-select-");
                    dept_value1.add("0");
                    String strWhereDept1 = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + objEntity.getDivision_id(); //oidDivision;
                    Vector listDept1 = PstDepartment.list(0, 0, strWhereDept1, " DEPARTMENT ");
                    for (int d = 0; d < listDept1.size(); d++) {
                    Department dept1 = (Department) listDept1.get(d);
                    dept_key1.add(dept1.getDepartment());
                    dept_value1.add(String.valueOf(dept1.getOID()));
                    } 
                    
		if(iCommand == Command.ADD || (iCommand == Command.SAVE && frmObject.errorSize() > 0)){ 
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID], "formElemen", null, String.valueOf(objEntity.getDoc_master_id()), docmaster_value, docmaster_key, "disabled"));
                                rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE] +"\" value=\""+objEntity.getFlow_title()+"\" class=\"elemenForm\"> * </a> <input type=\"hidden\" name=\""+frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID] +"\" value=\""+objEntity.getDoc_master_id()+"\" class=\"elemenForm\"> ");
				rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX], "formElemen", null, String.valueOf(objEntity.getFlow_index()), frmObject.getindexValue(), frmObject.getindexKey(), ""));
                                
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID], "formElemen", null, String.valueOf(objEntity.getCompany_id()), comp_value1, comp_key1, "onChange=\"javascript:cmdUpdateCDDS(0)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID], "formElemen", null, String.valueOf(objEntity.getDivision_id()), div_value1, div_key1, "onChange=\"javascript:cmdUpdateCDDS(0)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, String.valueOf(objEntity.getDepartment_id()), dept_value1, dept_key1, "onChange=\"javascript:cmdUpdateCDDS(0)\""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID], "formElemen", null, String.valueOf(objEntity.getLevel_id()), level_value, level_key, ""));
                                rowx.add(ControlCombo.draw(frmObject.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID], "formElemen", null, String.valueOf(objEntity.getPosition_id()), position_value, position_key, ""));
                                rowx.add("<input type=\"text\" name=\"EMP_FULLNAME\" value=\"\" class=\"elemenForm\"> <input type=\"hidden\" name=\""+FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]+"\" value=\"\" class=\"formElemen\" >");
                                rowx.add("<a href=\"javascript:cmdSearchEmp()\">add employee</a>");
		}

		lstData.add(rowx);

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidDocMasterFlow = FRMQueryString.requestLong(request, "doc_master_flow_oid");
long oidDocMaster = FRMQueryString.requestLong(request, "DocMasterId");

if ((oidDocMaster !=0) && (oidDocMaster>0)  ) {
    session.putValue("SELECTED_DOC_MASTER_ID", oidDocMaster);
} else {
     try{
      oidDocMaster = ((Long)session.getValue("SELECTED_DOC_MASTER_ID"));
     }catch(Exception e) {
     }
}

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
long sdocmasterId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID]);
String whereClause ="";
if (oidDocMaster > 0){
    whereClause = PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] +" = " + oidDocMaster;
} else {
    whereClause = PstDocMasterFlow.fieldNames[PstDocMasterFlow.FLD_DOC_MASTER_ID] +" = " + sdocmasterId;
}

String orderClause = "";

CtrlDocMasterFlow ctrlDocMasterFlow = new CtrlDocMasterFlow(request);
ControlLine ctrLine = new ControlLine();
Vector listDocMasterFlow = new Vector(1,1);

iErrCode = ctrlDocMasterFlow.action(iCommand , oidDocMasterFlow);
/* end switch*/
FrmDocMasterFlow frmDocMasterFlow = ctrlDocMasterFlow.getForm();

/*count list All GroupRank*/
int vectSize = PstDocMasterFlow.getCount(whereClause);

/*switch list GroupRank*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlDocMasterFlow.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

DocMasterFlow docMasterFlow = ctrlDocMasterFlow.getdDocMasterFlow();
msgString =  ctrlDocMasterFlow.getMessage();

/* get record to display */

listDocMasterFlow = PstDocMasterFlow.list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDocMasterFlow.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDocMasterFlow = PstDocMasterFlow.list(start,recordToGet, whereClause , orderClause);
}

   // long sdocmasterId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DOC_MASTER_ID]);
    String sflowtitle = FRMQueryString.requestString(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_TITLE]);
    int sflowindex = FRMQueryString.requestInt(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_FLOW_INDEX]);
    
    long scompanyId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_COMPANY_ID]);
    long sdivisionId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DIVISION_ID]);
    long sdepartementId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_DEPARTMENT_ID]);
    long slevelId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_LEVEL_ID]);
     
    long spositionId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_POSITION_ID]);
    long semployeeId = FRMQueryString.requestLong(request, FrmDocMasterFlow.fieldNames[FrmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]);
    
    
     if (oidDocMaster > 0){
                              docMasterFlow.setDoc_master_id(oidDocMaster);
                         } else {
                             docMasterFlow.setDoc_master_id(sdocmasterId);
                         }
 
    if(iCommand==Command.GOTO){
        
                         docMasterFlow.setDoc_master_flow_id(oidDocMasterFlow);
                       
                        
                         docMasterFlow.setFlow_title(sflowtitle);
                         docMasterFlow.setFlow_index(sflowindex);
                         docMasterFlow.setCompany_id(scompanyId);
                         docMasterFlow.setDivision_id(sdivisionId);
                         docMasterFlow.setDepartment_id(sdepartementId); 
                         docMasterFlow.setLevel_id(slevelId);//"LEVEL_ID",
                         docMasterFlow.setPosition_id(spositionId);//"MARITAL_ID",
                         docMasterFlow.setEmployee_id(semployeeId);
    
            long n = FRMQueryString.requestLong(request, "n");
            if (n==1){
                iCommand=Command.EDIT;
            } else {
                iCommand=Command.ADD;
            }
    }

     if (listDocMasterFlow.size() ==0 ){
         iCommand = Command.ADD;
     } 
     if (iCommand == Command.DELETE || iCommand == Command.SAVE  ){
         iCommand = Command.BACK;
     } 
    
    
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Doc Master Flow</title>
<script language="JavaScript">
function cmdUpdateCDDS(n){
                document.frmDocMasterFlow.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmDocMasterFlow.action="DocMasterFlow.jsp?n="+n+"";
                document.frmDocMasterFlow.submit();
            }



function cmdSearchEmp(){
        
	//emp_number = document.frmDocMasterFlow.EMP_NUMBER.value;
	window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmDocMasterFlow&empPathId=<%=frmDocMasterFlow.fieldNames[frmDocMasterFlow.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
}

function cmdAdd(masterId){
	document.frmDocMasterFlow.doc_master_flow_oid.value="0";
	document.frmDocMasterFlow.command.value="<%=Command.ADD%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp?DocMasterId="+masterId+"";
	document.frmDocMasterFlow.submit();
}

function cmdAsk(oidDocMasterFlow){
	document.frmDocMasterFlow.doc_master_flow_oid.value=oidDocMasterFlow;
	document.frmDocMasterFlow.command.value="<%=Command.ASK%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdConfirmDelete(oidDocMasterFlow){
	document.frmDocMasterFlow.doc_master_flow_oid.value=oidDocMasterFlow;
	document.frmDocMasterFlow.command.value="<%=Command.DELETE%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}



function cmdSave(){
	document.frmDocMasterFlow.command.value="<%=Command.SAVE%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdEdit(oidDocMasterFlow){
	document.frmDocMasterFlow.doc_master_flow_oid.value=oidDocMasterFlow;
	document.frmDocMasterFlow.command.value="<%=Command.EDIT%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdCancel(oidDocMasterFlow){
	document.frmDocMasterFlow.doc_master_flow_oid.value=oidDocMasterFlow;
	document.frmDocMasterFlow.command.value="<%=Command.EDIT%>";
	document.frmDocMasterFlow.prev_command.value="<%=prevCommand%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdBack(masteroid){
	document.frmDocMasterFlow.command.value="<%=Command.BACK%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp?DocMasterId="+masteroid+"";
	document.frmDocMasterFlow.submit();
}

function cmdListFirst(){
	document.frmDocMasterFlow.command.value="<%=Command.FIRST%>";
	document.frmDocMasterFlow.prev_command.value="<%=Command.FIRST%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdListPrev(){
	document.frmDocMasterFlow.command.value="<%=Command.PREV%>";
	document.frmDocMasterFlow.prev_command.value="<%=Command.PREV%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdListNext(){
	document.frmDocMasterFlow.command.value="<%=Command.NEXT%>";
	document.frmDocMasterFlow.prev_command.value="<%=Command.NEXT%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdListLast(){
	document.frmDocMasterFlow.command.value="<%=Command.LAST%>";
	document.frmDocMasterFlow.prev_command.value="<%=Command.LAST%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmDocMasterFlow.submit();
}

function cmdListCateg(oidDocMasterFlow){
	document.frmDocMasterFlow.doc_master_flow_oid.value=oidDocMasterFlow;
	document.frmDocMasterFlow.command.value="<%=Command.LIST%>";
	document.frmDocMasterFlow.action="DocMasterFlow.jsp";
	document.frmdoctype.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
		case <%=LIST_PREV%>:
			cmdListPrev();
			break;
		case <%=LIST_NEXT%>:
			cmdListNext();
			break;
		case <%=LIST_FIRST%>:
			cmdListFirst();
			break;
		case <%=LIST_LAST%>:
			cmdListLast();
			break;
		default:
			break;
	}
}

//-------------- script control line -------------------
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee(){    
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
}

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
      <!-- #EndEditable --> </td> 
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
		
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
			<td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
			<td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
		  </tr>
		</table>
	</td> 
  </tr>
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Masterdata 
                  &gt; DocMasterFlow<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmDocMasterFlow" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="doc_master_flow_oid" value="<%=oidDocMasterFlow%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listtitle" width="37%">Doc Master Template List</td>
                                                      <td width="63%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%
												try{
												%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(iCommand,frmDocMasterFlow, docMasterFlow, listDocMasterFlow,oidDocMasterFlow)%></td>
                                              </tr>
                                              <% 
											  }catch(Exception exc){ 
											  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmDocMasterFlow.errorSize()<1)){
											  	if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd('<%=oidDocMaster%>')" class="command">Add 
                                                        New Doc Master Flow</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%}
											  }%>
                                            </table>
                                          </td>										  
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command" height="26"> 
                                            <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidDocMasterFlow+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidDocMasterFlow+"')";
									String scancel = "javascript:cmdEdit('"+oidDocMasterFlow+"')";
									ctrLine.setBackCaption("Back to List Doc Master");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("Add Doc Master");
									ctrLine.setSaveCaption("Save Doc Master");
									ctrLine.setDeleteCaption("Delete Doc Master");
									ctrLine.setConfirmDelCaption("Yes Delete Doc Master");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									
									if((listDocMasterFlow.size()<1)&&(iCommand == Command.NONE))
										 iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);										 
									%>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable -->
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td>&nbsp; </td>
              </tr>
            </table>
		  </td> 
        </tr>
      </table>
		  </td> 
        </tr>
      </table>
    </td> 
  </tr>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
