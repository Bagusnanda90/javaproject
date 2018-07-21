<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Employee_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.Section"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Object"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.harisma.entity.masterdata.Competitor"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetitor"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Department_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Section_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Section_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Department_Target"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Division_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Division_Target"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKPI_Employee_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_ListAllDataEmp"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Employee_Target"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_List"%>
<%@page import="com.dimata.harisma.entity.masterdata.Company"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompany"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_List"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.DocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDocType"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstKPI_Company_Target"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlKPI_Company_Target"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.KPI_Company_Target"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmKPI_Company_Target"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  kpi_company_target.jsp
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
<%!    public String drawList(int iCommand, Vector objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId) {
            ControlList ctrlist = new ControlList();
        
        KPI_List kPI_List = new KPI_List();
        
        try {
            kPI_List = PstKPI_List.fetchExc(kpiListId);
        }catch (Exception e ){
            
        }
        Date startDateList  = kPI_List.getValid_from() ;
        Date endDateList    = kPI_List.getValid_to() ;
        
        ctrlist.setAreaWidth("150%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("NO","10%");
        int month1 =  startDateList.getMonth()+1;
        int month2 =  endDateList.getMonth()+1;
        int jumlahBulan = (month2-month1)+1;
        for (int kl = month1; kl < (month2+1); kl++ ){
            String monthString = "";
            if (kl == 1){
                monthString = "Januari";
            } else if (kl == 2){
                monthString = "Februari";
            } else if (kl == 3){
                monthString = "Maret";
            } else if (kl == 4){
                monthString = "April";
            } else if (kl == 5){
                monthString = "Mei";
            } else if (kl == 6){
                monthString = "Juni";
            } else if (kl == 7){
                monthString = "Juli";
            } else if (kl == 8){
                monthString = "Agustus";
            } else if (kl == 9){
                monthString = "September";
            } else if (kl == 10){
                monthString = "Oktober";
            } else if (kl == 11){
                monthString = "November";
            } else if (kl == 12){
                monthString = "Desember";
            }
            ctrlist.addHeader(""+monthString,"15%");
        }
            
        ctrlist.addHeader("TOTAL","20%");   
        ctrlist.addHeader("COMPETITOR","20%");   
        ctrlist.addHeader("COMPETITOR VALUE","20%");   
        
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

            Vector rowx = new Vector();
            double total = 0;
            
            Vector comp_value = new Vector(1, 1);
                Vector comp_key = new Vector(1, 1);
                Vector listcomp = PstCompetitor.list(0, 0, "", "");
                comp_value.add(""+0);
                comp_key.add("select");
                for (int i = 0; i < listcomp.size(); i++) {
                    Competitor comp = (Competitor) listcomp.get(i);
                    comp_key.add(comp.getCompanyName());
                    comp_value.add(String.valueOf(comp.getOID()));
                }
            
             if((iCommand == Command.EDIT || iCommand == Command.ASK)){
                 
                    rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(tahun)+"')\"> Edit </a>");
                
                    if (objectClass.size() >0 ){
                        int ix = 0;
                    for (int k = month1; k < (month2+1) ; k++){
                        KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) objectClass.get(ix);
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_TARGET] +k+"\" value=\""+kPI_Company_Target.getTarget()+"\" class=\"elemenForm\"> * ");
                        total = total + kPI_Company_Target.getTarget();
                        ix++;
                    }
                        KPI_Company_Target kPI_Company_Target12 = (KPI_Company_Target) objectClass.get(0);
                        rowx.add(""+total);
                        rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_COMPETITOR_ID], "formElemen", null, String.valueOf(kPI_Company_Target12.getCompetitorId()), comp_value, comp_key, ""));
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_COMPETITOR_VALUE] +"\" value=\""+kPI_Company_Target12.getCompetitorValue()+"\" class=\"elemenForm\"> * ");

                                       }else {
                    for (int k = month1; k < (month2+1) ; k++){
                        //KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) objectClass.get(k);
                        rowx.add("<input type=\"text\" size=\"10\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_TARGET] +k+"\" value=\"\" class=\"elemenForm\"> * ");
                        total = total ;

                    }    
                 
                       rowx.add(""+total);
                       rowx.add(ControlCombo.draw(frmObject.fieldNames[frmObject.FRM_FIELD_COMPETITOR_ID], "formElemen", null, "", comp_value, comp_key, ""));
                       rowx.add("<input type=\"text\" size=\"10\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_COMPETITOR_VALUE] +"\" value=\"\" class=\"elemenForm\"> * ");

                                       }
                    
            
                } else {
                
                if (objectClass.size() >0){
                    
                        rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(tahun)+"')\"> Edit </a>");
                    int ix =0 ;
                    for (int k = month1; k < (month2+1) ; k++){
                        KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) objectClass.get(ix);
                        rowx.add(""+kPI_Company_Target.getTarget());
                        total = total + kPI_Company_Target.getTarget();
                        ix ++ ;

                    }  
                    KPI_Company_Target kPI_Company_Target22 = (KPI_Company_Target) objectClass.get(0);
                    rowx.add(""+total);
                    rowx.add(""+kPI_Company_Target22.getCompetitorId());
                    rowx.add(""+kPI_Company_Target22.getCompetitorValue());
             
                                        } else{
                     rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(tahun)+"')\"> Edit </a>");
                    for (int k = month1; k < (month2+1) ; k++){
                        rowx.add("-");
                        //total = total + kPI_Company_Target.getTarget();
                    }
                     rowx.add("-");
                     rowx.add("-");
                     rowx.add("-");
                                        }
                }    
              //  for (int k = month1; k < (month2+1) ; k++){
               //     KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) objectClass.get(k);
               //     rowx.add("<input type=\"text\" size=\"10\" name=\""+frmObject.fieldNames[frmObject.FRM_FIELD_TARGET] +k+"\" value=\""+kPI_Company_Target.getTarget()+"\" class=\"elemenForm\"> * ");
	//	    total = total + kPI_Company_Target.getTarget();
         //           
           //     }    
             
            
            
            
            lstData.add(rowx);
           // lstLinkData.add(String.valueOf(kpi_company_target.getOID()));
        
        return ctrlist.draw(index);
    }

    

%>


<%!

	public String drawListAll(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId, long divId, Hashtable HDepartment,Vector VDepartment,long depId, Hashtable HSection,Vector VSection,long secId, Hashtable HEmployee,Vector VEmployee)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Division ","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                Vector VDivision = PstDivision.listwithTypeDivision(0, 0, ""," HDT.GROUP_TYPE ");
                for (int i = 0; i < VDivision.size(); i++) {
			 Division division = (Division)VDivision.get(i);
                         KPI_Division_Target kPI_Division_Target = (KPI_Division_Target) objectClass.get(division.getOID());
                         if (kPI_Division_Target == null){
                             kPI_Division_Target = new KPI_Division_Target();
                         }
			 rowx = new Vector();
                        if (kPI_Division_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdDep('"+division.getOID()+"')\">"+division.getDivision()+"</a> ");
			 }else{
                            rowx.add(""+division.getDivision() );
                             
                        }		
                         
                         //rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(kpiListId)+"')\">"+division.getDivision()+"</a> ");
				
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                          if (divId ==0){
                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DIVISION]+division.getOID() +"\" value=\""+Formater.formatNumber(((total/100)*kPI_Division_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> ");
                         } else{
                             rowx.add(""+Formater.formatNumber(((total/100)*kPI_Division_Target.getTarget()), "#,###.##")+"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DIVISION]+division.getOID() +"\" value=\""+Formater.formatNumber(((total/100)*kPI_Division_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> ");
                         }
                         rowx.add(""+Formater.formatNumber((kPI_Division_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Division_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                         if(iCommand == Command.EDIT ){
                            lstData.add(rowx);
                         } else {
                              if (kPI_Division_Target.getTarget() > 0){
                                  lstData.add(rowx);
                              } 
                         }
                            if (divId >0 && divId == division.getOID()){
                                     for (int id = 0; id < VDepartment.size(); id++) {
                                     Department department = (Department)VDepartment.get(id);
                                     KPI_Department_Target kPI_Department_Target = (KPI_Department_Target) HDepartment.get(department.getOID());
                                     if (kPI_Department_Target == null){
                                         kPI_Department_Target = new KPI_Department_Target();
                                     }
                                     rowx = new Vector();
                                    // rowx.add(""+department.getDepartment() );
                                     if (kPI_Department_Target.getTarget() > 0 && iCommand ==  Command.EDIT){
                                        rowx.add("<a href=\"javascript:cmdSec('"+department.getOID()+"')\">-----"+department.getDepartment()+"</a> ");
                                     }else{
                                        rowx.add("------"+department.getDepartment() );

                                    }
                                     //rowx.add("Department ");
                                     //rowx.add("Section ");
                                     //rowx.add("Name ");
                                    if (depId ==0){
                                       rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Department_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> ");
                                    } else {
                                       rowx.add(""+Formater.formatNumber((total/100*kPI_Department_Target.getTarget()), "#,###.##")+"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Department_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> ");
                                   
                                    }
                                       
                                     
                                     rowx.add(""+Formater.formatNumber((kPI_Department_Target.getTarget()), "#,###.##") );
                                     rowx.add(""+Formater.formatNumber(((kPI_Department_Target.getTarget()/vtotal.size())), "#,###.##"));
                                    // rowx.add("Achievement"); 
                                        lstData.add(rowx);
                                                                                   
                                        if (depId > 0 && depId == department.getOID()){     
                                            for (int is = 0; is < VSection.size(); is++) {
                                                         Section section = (Section)VSection.get(is);
                                                         KPI_Section_Target kPI_Section_Target = (KPI_Section_Target) HSection.get(section.getOID());
                                                         if (kPI_Section_Target == null){
                                                             kPI_Section_Target = new KPI_Section_Target();
                                                         }
                                                         rowx = new Vector();
                                                        // rowx.add(""+department.getDepartment() );
                                                         if (kPI_Section_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                                                            rowx.add("----------<a href=\"javascript:cmdEmp('"+section.getOID()+"')\">"+section.getSection()+"</a> ");
                                                         }else{
                                                            rowx.add("----------"+section.getSection() );

                                                        }
                                                         //rowx.add("Department ");
                                                         //rowx.add("Section ");
                                                         //rowx.add("Name ");
                                                         if (secId ==0){
                                                            rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_SECTION]+section.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Section_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                                                         } else {
                                                            rowx.add(""+Formater.formatNumber((total/100*kPI_Section_Target.getTarget()), "#,###.##") +"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_SECTION]+section.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Section_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                                                         }
                                                         
                                                         rowx.add(""+Formater.formatNumber((kPI_Section_Target.getTarget()), "#,###.##") );
                                                         rowx.add(""+Formater.formatNumber(((kPI_Section_Target.getTarget()/vtotal.size())), "#,###.##"));
                                                        // rowx.add("Achievement"); 
                                                            lstData.add(rowx);
                                                            
                                                            
                                                           if (secId > 0 && secId == section.getOID()){                                                  
                                                            for (int ie = 0; ie < VEmployee.size(); ie++) {
                                                                             Employee employee = (Employee)VEmployee.get(ie);
                                                                             KPI_Employee_Target kPI_Employee_Target = (KPI_Employee_Target) HEmployee.get(employee.getOID());
                                                                             if (kPI_Employee_Target == null){
                                                                                 kPI_Employee_Target = new KPI_Employee_Target();
                                                                             }
                                                                             rowx = new Vector();
                                                                            // rowx.add(""+department.getDepartment() );
                                                                             if (kPI_Employee_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                                                                                rowx.add("---------------<a href=\"javascript:cmdSec('"+employee.getSectionId()+"','"+employee.getOID()+"')\">"+employee.getFullName()+"</a> ");
                                                                             }else{
                                                                                rowx.add("---------------"+employee.getFullName() );

                                                                            }
                                                                             //rowx.add("Department ");
                                                                             //rowx.add("Section ");
                                                                             //rowx.add("Name ");
                                                                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_E]+employee.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Employee_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");

                                                                             rowx.add(""+Formater.formatNumber((kPI_Employee_Target.getTarget()), "#,###.##") );
                                                                             rowx.add(""+Formater.formatNumber(((kPI_Employee_Target.getTarget()/vtotal.size())), "#,###.##"));
                                                                            // rowx.add("Achievement"); 
                                                                                lstData.add(rowx);

                                                                    }
                                                             }
                                                } 
                                               }
                            }
                          }
                }

		

		return ctrlist.draw();
	}
%>
<%!

	public String drawListAllEdit(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId, long divId, Hashtable HDepartment,Vector VDepartment,long depId, Hashtable HSection,Vector VSection,long secId, Hashtable HEmployee,Vector VEmployee)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Division ","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                Vector VDivision = PstDivision.listwithTypeDivision(0, 0, ""," HDT.GROUP_TYPE ");
                for (int i = 0; i < VDivision.size(); i++) {
			 Division division = (Division)VDivision.get(i);
                         KPI_Division_Target kPI_Division_Target = (KPI_Division_Target) objectClass.get(division.getOID());
                         if (kPI_Division_Target == null){
                             kPI_Division_Target = new KPI_Division_Target();
                         }
			 rowx = new Vector();
                        if (kPI_Division_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdDep('"+division.getOID()+"')\">"+division.getDivision()+ "</a> ");
			 }else{
                            rowx.add(""+division.getDivision() );
                             
                        }		
                         
                         //rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(kpiListId)+"')\">"+division.getDivision()+"</a> ");
				
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                          if (divId ==0){
                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DIVISION]+division.getOID() +"\" value=\""+Formater.formatNumber((kPI_Division_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> ");
                         } else{
                             rowx.add(""+Formater.formatNumber((kPI_Division_Target.getTarget()/total*100), "#,###.##")+"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DIVISION]+division.getOID() +"\" value=\""+Formater.formatNumber((kPI_Division_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> ");
                         }
                         rowx.add(""+Formater.formatNumber((kPI_Division_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Division_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                         if(iCommand == Command.EDIT ){
                            lstData.add(rowx);
                         } else {
                              if (kPI_Division_Target.getTarget() > 0){
                                  lstData.add(rowx);
                              } 
                         }
                            if (divId >0 && divId == division.getOID()){
                                     for (int id = 0; id < VDepartment.size(); id++) {
                                     Department department = (Department)VDepartment.get(id);
                                     KPI_Department_Target kPI_Department_Target = (KPI_Department_Target) HDepartment.get(department.getOID());
                                     if (kPI_Department_Target == null){
                                         kPI_Department_Target = new KPI_Department_Target();
                                     }
                                     rowx = new Vector();
                                    // rowx.add(""+department.getDepartment() );
                                     if (kPI_Department_Target.getTarget() > 0 && iCommand ==  Command.EDIT){
                                        rowx.add("<a href=\"javascript:cmdSec('"+department.getOID()+"')\">-----"+department.getDepartment()+"</a> ");
                                     }else{
                                        rowx.add("------"+department.getDepartment() );

                                    }
                                     //rowx.add("Department ");
                                     //rowx.add("Section ");
                                     //rowx.add("Name ");
                                    if (depId ==0){
                                       rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID() +"\" value=\""+Formater.formatNumber((kPI_Department_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> ");
                                    } else {
                                       rowx.add(""+Formater.formatNumber((kPI_Department_Target.getTarget()/total*100), "#,###.##")+"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID() +"\" value=\""+Formater.formatNumber((kPI_Department_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> ");
                                   
                                    }
                                       
                                     
                                     rowx.add(""+Formater.formatNumber((kPI_Department_Target.getTarget()), "#,###.##") );
                                     rowx.add(""+Formater.formatNumber(((kPI_Department_Target.getTarget()/vtotal.size())), "#,###.##"));
                                    // rowx.add("Achievement"); 
                                        lstData.add(rowx);
                                                                                   
                                        if (depId > 0 && depId == department.getOID()){     
                                            for (int is = 0; is < VSection.size(); is++) {
                                                         Section section = (Section)VSection.get(is);
                                                         KPI_Section_Target kPI_Section_Target = (KPI_Section_Target) HSection.get(section.getOID());
                                                         if (kPI_Section_Target == null){
                                                             kPI_Section_Target = new KPI_Section_Target();
                                                         }
                                                         rowx = new Vector();
                                                        // rowx.add(""+department.getDepartment() );
                                                         if (kPI_Section_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                                                            rowx.add("----------<a href=\"javascript:cmdEmp('"+section.getOID()+"')\">"+section.getSection()+"</a> ");
                                                         }else{
                                                            rowx.add("----------"+section.getSection() );

                                                        }
                                                         //rowx.add("Department ");
                                                         //rowx.add("Section ");
                                                         //rowx.add("Name ");
                                                         if (secId ==0){
                                                            rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_SECTION]+section.getOID() +"\" value=\""+Formater.formatNumber((kPI_Section_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> * ");
                                                         } else {
                                                            rowx.add(""+Formater.formatNumber((kPI_Section_Target.getTarget()/total*100), "#,###.##") +"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_SECTION]+section.getOID() +"\" value=\""+Formater.formatNumber((kPI_Section_Target.getTarget()/total*100), "#,###.##")+"\" class=\"elemenForm\"> * ");
                                                         }
                                                         
                                                         rowx.add(""+Formater.formatNumber((kPI_Section_Target.getTarget()), "#,###.##") );
                                                         rowx.add(""+Formater.formatNumber(((kPI_Section_Target.getTarget()/vtotal.size())), "#,###.##"));
                                                        // rowx.add("Achievement"); 
                                                            lstData.add(rowx);
                                                            
                                                            
                                                           if (secId > 0 && secId == section.getOID()){                                                  
                                                            for (int ie = 0; ie < VEmployee.size(); ie++) {
                                                                             Employee employee = (Employee)VEmployee.get(ie);
                                                                             KPI_Employee_Target kPI_Employee_Target = (KPI_Employee_Target) HEmployee.get(employee.getOID());
                                                                             if (kPI_Employee_Target == null){
                                                                                 kPI_Employee_Target = new KPI_Employee_Target();
                                                                             }
                                                                             rowx = new Vector();
                                                                            // rowx.add(""+department.getDepartment() );
                                                                             if (kPI_Employee_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                                                                                rowx.add("---------------<a href=\"javascript:cmdSec('"+employee.getSectionId()+"','"+employee.getOID()+"')\">"+employee.getFullName()+"</a> ");
                                                                             }else{
                                                                                rowx.add("---------------"+employee.getFullName() );

                                                                            }
                                                                             //rowx.add("Department ");
                                                                             //rowx.add("Section ");
                                                                             //rowx.add("Name ");
                                                                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_E]+employee.getOID() +"\" value=\""+Formater.formatNumber((kPI_Employee_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> * ");

                                                                             rowx.add(""+Formater.formatNumber((kPI_Employee_Target.getTarget()), "#,###.##") );
                                                                             rowx.add(""+Formater.formatNumber(((kPI_Employee_Target.getTarget()/vtotal.size())), "#,###.##"));
                                                                            // rowx.add("Achievement"); 
                                                                                lstData.add(rowx);

                                                                    }
                                                             }
                                                }
                                            
                                          
                                                    
                                            
                                            
                                                
                                                
                                            
                                            
                                              //menampilkan 
                                            Hashtable HlistKPI_EmployeeDepart_Target = new Hashtable(1, 1);
                                            Vector VemployeDepart = new Vector();
                                             String whereClauseKpiList = " KPI_LIST_ID = " + kpiListId ;
                                            whereClauseKpiList = whereClauseKpiList + " AND STARTDATE LIKE \"%" + tahun + "%\" ";
                                            whereClauseKpiList = whereClauseKpiList + " AND ENDDATE LIKE \"%" + tahun + "%\" ";
                                            HlistKPI_EmployeeDepart_Target= PstKPI_Employee_Target.Hlist(0, 0, whereClauseKpiList, "");
                                            VemployeDepart = PstEmployee.list(0, 0, " DEPARTMENT_ID = "+department.getOID()+" AND SECTION_ID = "+0,"");
                                                                                                  
                                                for (int ie = 0; ie < VemployeDepart.size(); ie++) {
                                                                             Employee employee = (Employee)VemployeDepart.get(ie);
                                                                             KPI_Employee_Target kPI_Employee_Target = (KPI_Employee_Target) HlistKPI_EmployeeDepart_Target.get(employee.getOID());
                                                                             if (kPI_Employee_Target == null){
                                                                                 kPI_Employee_Target = new KPI_Employee_Target();
                                                                             }
                                                                             rowx = new Vector();
                                                                            // rowx.add(""+department.getDepartment() );
                                                                             if (kPI_Employee_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                                                                                rowx.add("----------<a href=\"javascript:cmdSec('"+employee.getSectionId()+"','"+employee.getOID()+"')\">"+employee.getFullName()+"</a> ");
                                                                             }else{
                                                                                rowx.add("----------"+employee.getFullName() );

                                                                            }
                                                                             //rowx.add("Department ");
                                                                             //rowx.add("Section ");
                                                                             //rowx.add("Name ");
                                                                             if (secId ==0){
                                                                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_E]+employee.getOID() +"\" value=\""+Formater.formatNumber((kPI_Employee_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\">  ");
                                                                             } else {
                                                                                rowx.add(""+Formater.formatNumber((kPI_Employee_Target.getTarget()/total*100), "#,###.##") +"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_E]+employee.getOID() +"\" value=\""+Formater.formatNumber((kPI_Employee_Target.getTarget()/total*100), "#,###.##") +"\" class=\"elemenForm\"> ");
                                                                             }
                                                                             rowx.add(""+Formater.formatNumber((kPI_Employee_Target.getTarget()), "#,###.##") );
                                                                             rowx.add(""+Formater.formatNumber(((kPI_Employee_Target.getTarget()/vtotal.size())), "#,###.##"));
                                                                            // rowx.add("Achievement"); 
                                                                                lstData.add(rowx);

                                                                    }
                                            
                                               }
                            }
                          }
                }

		

		return ctrlist.draw();
	}
%>
<%!

	public String drawListDiv(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Division ","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                Vector VDivision = PstDivision.listwithTypeDivision(0, 0, ""," HDT.GROUP_TYPE ");
                for (int i = 0; i < VDivision.size(); i++) {
			 Division division = (Division)VDivision.get(i);
                         KPI_Division_Target kPI_Division_Target = (KPI_Division_Target) objectClass.get(division.getOID());
                         if (kPI_Division_Target == null){
                             kPI_Division_Target = new KPI_Division_Target();
                         }
			 rowx = new Vector();
                        if (kPI_Division_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdDep('"+division.getOID()+"')\">"+division.getDivision()+"</a> ");
			 }else{
                            rowx.add(""+division.getDivision() );
                             
                        }		
                         
                         //rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(kpiListId)+"')\">"+division.getDivision()+"</a> ");
				
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                         if(iCommand == Command.EDIT ){
                             rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DIVISION]+division.getOID() +"\" value=\""+Formater.formatNumber(((total/100)*kPI_Division_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                         } else{
                             rowx.add(""+Formater.formatNumber(((total/100)*kPI_Division_Target.getTarget()), "#,###.##"));
                         }
                         rowx.add(""+Formater.formatNumber((kPI_Division_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Division_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                         if(iCommand == Command.EDIT ){
                            lstData.add(rowx);
                         } else {
                              if (kPI_Division_Target.getTarget() > 0){
                                  lstData.add(rowx);
                              } 
                         }
                }

		

		return ctrlist.draw();
	}
%>

<%!

	public String drawListDep(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId, Vector VDepartment)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Department","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                
                for (int i = 0; i < VDepartment.size(); i++) {
			 Department department = (Department)VDepartment.get(i);
                         KPI_Department_Target kPI_Department_Target = (KPI_Department_Target) objectClass.get(department.getOID());
                         if (kPI_Department_Target == null){
                             kPI_Department_Target = new KPI_Department_Target();
                         }
			 rowx = new Vector();
			// rowx.add(""+department.getDepartment() );
                         if (kPI_Department_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdSec('"+department.getOID()+"')\">"+department.getDepartment()+"</a> ");
			 }else{
                            rowx.add(""+department.getDepartment() );
                             
                        }
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                         rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Department_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                         
                         rowx.add(""+Formater.formatNumber((kPI_Department_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Department_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                            lstData.add(rowx);
                         
                }

		

		return ctrlist.draw();
	}
%>


<%!

	public String drawListSec(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId, Vector VSection)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Section","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                
                for (int i = 0; i < VSection.size(); i++) {
			 Section section = (Section)VSection.get(i);
                         KPI_Section_Target kPI_Section_Target = (KPI_Section_Target) objectClass.get(section.getOID());
                         if (kPI_Section_Target == null){
                             kPI_Section_Target = new KPI_Section_Target();
                         }
			 rowx = new Vector();
			// rowx.add(""+department.getDepartment() );
                         if (kPI_Section_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdEmp('"+section.getOID()+"')\">"+section.getSection()+"</a> ");
			 }else{
                            rowx.add(""+section.getSection() );
                             
                        }
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                         rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_SECTION]+section.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Section_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                         
                         rowx.add(""+Formater.formatNumber((kPI_Section_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Section_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                            lstData.add(rowx);
                         
                }

		

		return ctrlist.draw();
	}
%>
<%!

	public String drawListEmp(JspWriter outJsp,int iCommand, Hashtable objectClass, long kpi_company_targetId, FrmKPI_Company_Target frmObject, int tahun, long kpiListId, long companyId, Vector VEmp)
{
		ControlList ctrlist = new ControlList();
                ctrlist.setAreaWidth("150%");
                ctrlist.setListStyle("listgen");
                ctrlist.setTitleStyle("listgentitle");
                ctrlist.setCellStyle("listgensell");
                //ctrlist.setCellStyles("listgensellstyles");
                ctrlist.setRowSelectedStyles("rowselectedstyles");
                ctrlist.setHeaderStyle("listheaderJs");
        
                //ctrlist.addHeader("No","5%");
                ctrlist.addHeader("Employee","10%");
                //ctrlist.addHeader("Department ","10%");
                //ctrlist.addHeader("Section ","10%");
                
		//ctrlist.addHeader("Name ","30%");
		ctrlist.addHeader("Target (%)","10%");
		ctrlist.addHeader("Target (Value)","10%");
                ctrlist.addHeader("Avr/month","10%");
                //ctrlist.addHeader("Achievement","10%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
                
                Vector vtotal = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, companyId);
                double total = 0;
                
                for (int ik=0; ik<vtotal.size();ik++){
                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) vtotal.get(ik); 
                   total = total + ( kPI_Company_Target.getTarget() > 0 ? ((Double)kPI_Company_Target.getTarget()).doubleValue() : 0 );
                }

                
                for (int i = 0; i < VEmp.size(); i++) {
			 Employee employee = (Employee)VEmp.get(i);
                         KPI_Employee_Target kPI_Employee_Target = (KPI_Employee_Target) objectClass.get(employee.getOID());
                         if (kPI_Employee_Target == null){
                             kPI_Employee_Target = new KPI_Employee_Target();
                         }
			 rowx = new Vector();
			// rowx.add(""+department.getDepartment() );
                         if (kPI_Employee_Target.getTarget()>0 && iCommand ==  Command.EDIT){
                            rowx.add("<a href=\"javascript:cmdSec('"+employee.getSectionId()+"','"+employee.getOID()+"')\">"+employee.getFullName()+"</a> ");
			 }else{
                            rowx.add(""+employee.getFullName() );
                             
                        }
                         //rowx.add("Department ");
                         //rowx.add("Section ");
                         //rowx.add("Name ");
                         rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmKPI_Company_Target.FRM_FIELD_PERCENT_E]+employee.getOID() +"\" value=\""+Formater.formatNumber((total/100*kPI_Employee_Target.getTarget()), "#,###.##") +"\" class=\"elemenForm\"> * ");
                         
                         rowx.add(""+Formater.formatNumber((kPI_Employee_Target.getTarget()), "#,###.##") );
                         rowx.add(""+Formater.formatNumber(((kPI_Employee_Target.getTarget()/vtotal.size())), "#,###.##"));
                        // rowx.add("Achievement"); 
                            lstData.add(rowx);
                         
                }

		

		return ctrlist.draw();
	}
%>
<%
            long type = FRMQueryString.requestLong(request, "type");
            long divId = FRMQueryString.requestLong(request, "divId");
            long depId = FRMQueryString.requestLong(request, "depId");
            long secId = FRMQueryString.requestLong(request, "secId");
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidKPI_Company_Target = FRMQueryString.requestLong(request, "hidden_kpi_company_target_id");
            //long divisiOid = FRMQueryString.requestLong(request, "divisiOid");
            
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlKPI_Company_Target ctrlKPI_Company_Target = new CtrlKPI_Company_Target(request);
            ControlLine ctrLine = new ControlLine();
            Vector listKPI_Company_Target = new Vector(1, 1);
            Vector listKPI_Employee_Target = new Vector(1, 1);
            
            
            
            long CompanyId = FRMQueryString.requestLong(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_COMPANY_ID]);
            long kpiListId = FRMQueryString.requestLong(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_KPI_LIST_ID]);
            int  tahun = FRMQueryString.requestInt(request, FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_YEAR]);
            
            
            //long divisiOid = 2006;
            //int  type = FRMQueryString.requestInt(request, "type");
            
           // long viewDirect = FRMQueryString.requestLong(request, "viewDirect");
           // KPI_Object kPI_Object = new KPI_Object();
           // if(session.getValue("KPI_OBJECT")!=null){
           //     kPI_Object = (KPI_Object)session.getValue("KPI_OBJECT"); 
            //}
            
           // if (CompanyId == 0){
           //     CompanyId = kPI_Object.getCompanyId();
           // }
           // if (kpiListId == 0){
           //     kpiListId = kPI_Object.getKPI_ListId();
           // }
          //  if (tahun == 0){
            //    tahun = kPI_Object.getTahun();
          //  }
           // if (DivisionId == 0){
           //     DivisionId = kPI_Object.getDivisionId();
           // }       
           Date newdate = new Date();
           long tahungsekarang = newdate.getYear();
            if (tahun == 0){
                tahun = 2015;
            }
            
           iErrCode = ctrlKPI_Company_Target.action(iCommand, oidKPI_Company_Target, kpiListId, tahun, CompanyId,type,divId,depId,secId,0);
          
     
           //wehere clause kpilist
           String whereClauseKpiList = " KPI_LIST_ID = " + kpiListId ;
           whereClauseKpiList = whereClauseKpiList + " AND STARTDATE LIKE \"%" + tahun + "%\" ";
           whereClauseKpiList = whereClauseKpiList + " AND ENDDATE LIKE \"%" + tahun + "%\" ";
           //hastabel untuk divisi  yang sudah diset nilainya 
           Hashtable HlistKPI_Division_Target = new Hashtable(1, 1);         
           HlistKPI_Division_Target = PstKPI_Division_Target.Hlist(0, 0, whereClauseKpiList, "");
           
           //department
           //hastabel untuk divisi  yang sudah diset nilainya 
           Hashtable HlistKPI_Department_Target = new Hashtable(1, 1);
           Vector VDepartment = new Vector();
           if (divId>0){
           HlistKPI_Department_Target= PstKPI_Department_Target.Hlist(0, 0, whereClauseKpiList, "");
           VDepartment = PstDepartment.list(0, 0, " hr_department.DIVISION_ID="+divId,"");
                     }
           
           //section
           //hastabel untuk divisi  yang sudah diset nilainya 
           Hashtable HlistKPI_Section_Target = new Hashtable(1, 1);
           Vector VSection = new Vector();
           Vector VEmployeeDepart = new Vector();
           if (depId>0){
           HlistKPI_Section_Target= PstKPI_Section_Target.Hlist(0, 0, whereClauseKpiList, "");
           VSection = PstSection.list(0, 0, " DEPARTMENT_ID = "+depId,"");
           VEmployeeDepart = PstEmployee.list(0, 0, " DEPARTMENT_ID = "+department.getOID()+" AND SECTION_ID = "+0,"");
                     }
           
           //employee
           Hashtable HlistKPI_Employee_Target = new Hashtable(1, 1);
           Vector VEmployee = new Vector();
           if (secId>0){
           HlistKPI_Employee_Target= PstKPI_Employee_Target.Hlist(0, 0, whereClauseKpiList, "");
           VEmployee = PstEmployee.list(0, 0, " SECTION_ID = "+secId,"");
                     }
            /*switch statement */
           
            /* end switch*/
            FrmKPI_Company_Target frmKPI_Company_Target = ctrlKPI_Company_Target.getForm();
            FrmKPI_Employee_Target frmKPI_Employee_Target = new FrmKPI_Employee_Target();
            /*count list All Position*/
            int vectSize = PstKPI_Company_Target.getCount(whereClause);

            KPI_Company_Target kpi_company_target = ctrlKPI_Company_Target.getdKPI_Company_Target();
            msgString = ctrlKPI_Company_Target.getMessage();

             
            /* get record to display */
          //  listKPI_Company_Target = PstKPI_Company_Target.list(start, recordToGet, whereClause, orderClause);
            listKPI_Company_Target = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, CompanyId);
            if( iCommand == Command.EDIT){
                listKPI_Employee_Target = PstKPI_Company_Target.listAlldataEmployee(tahun, kpiListId, CompanyId);
            } else {
                listKPI_Employee_Target = PstKPI_Company_Target.listAlldataEmployeeView(tahun, kpiListId, CompanyId);
            }
           
           
           //cek dulu sudah ada atau tidak kpi company targetnya
           
           
            
            /*switch list KPI_Company_Target*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstKPI_Company_Target.findLimitStart(kpi_company_target.getOID(),recordToGet, whereClause);
                oidKPI_Company_Target = kpi_company_target.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlKPI_Company_Target.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            
  
            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listKPI_Company_Target.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                //listKPI_Company_Target = PstKPI_Company_Target.list(start, recordToGet, whereClause, orderClause);
                listKPI_Company_Target = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, CompanyId);
            }

           if (iCommand == Command.GOTO){
               listKPI_Company_Target = PstKPI_Company_Target.listAlldataNew(tahun, kpiListId, CompanyId);
           }
          //session.putValue("KPI_OBJECT", kPI_Object);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>KPI TYPE</title>

        <%@ include file = "../main/konfigurasi_jquery.jsp" %>    
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../javascripts/jquery.min.js"></script>
        <script type="text/javascript" src="../javascripts/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../javascripts/gridviewScroll.min.js"></script>
        <link href="../stylesheets/GridviewScroll.css" rel="stylesheet" />
        
        <script language="JavaScript">

function cmdAdd(){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value="0";
	document.frmKPI_Company_Target.command.value="<%=Command.ADD%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdAsk(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.ASK%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}


function cmdConfirmDelete(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.DELETE%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdSave(){
	document.frmKPI_Company_Target.command.value="<%=Command.SAVE%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdSaveModif(type){
	document.frmKPI_Company_Target.command.value="<%=Command.SAVE%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
        document.frmKPI_Company_Target.type.value=type;
	document.frmKPI_Company_Target.action="kpi_company_target.jsp?type = "+type;
	document.frmKPI_Company_Target.submit();
}

function cmdDep(divId){
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
        document.frmKPI_Company_Target.divId.value=divId;
        document.frmKPI_Company_Target.depId.value=0;
        document.frmKPI_Company_Target.secId.value=0;
	document.frmKPI_Company_Target.action="kpi_company_target.jsp?divId = "+divId;
	document.frmKPI_Company_Target.submit();
}

function cmdSec(depId){
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
  
        document.frmKPI_Company_Target.depId.value=depId;
        document.frmKPI_Company_Target.secId.value=0;
	document.frmKPI_Company_Target.action="kpi_company_target.jsp?depId = "+depId;
	document.frmKPI_Company_Target.submit();
}
function cmdEmp(secId){
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
  
        document.frmKPI_Company_Target.secId.value=secId;
	document.frmKPI_Company_Target.action="kpi_company_target.jsp?secId = "+secId;
	document.frmKPI_Company_Target.submit();
}
function cmdEditDep(divisiId,tahun,kpilistId,companyId){
        //document.frmKPI_Company_Target.KPI_Company_Target_oid.value=kpilistId;
	document.frmKPI_Company_Target.divisiOid.value=divisiId;
        alert(divisiId);
        document.frmKPI_Company_Target.FRM_FIELD_YEAR.value=tahun;
	document.frmKPI_Company_Target.FRM_FIELD_KPI_LIST_ID.value=kpilistId;
        document.frmKPI_Company_Target.FRM_FIELD_COMPANY_ID.value=companyId;
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp?divisiOid= "+divisiId+"";
	document.frmKPI_Company_Target.submit();
}

function cmdEdit(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
        document.frmKPI_Company_Target.divId.value=0;
        document.frmKPI_Company_Target.depId.value=0;
        document.frmKPI_Company_Target.secId.value=0;
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdCancel(oidKPI_Company_Target){
	document.frmKPI_Company_Target.KPI_Company_Target_oid.value=oidKPI_Company_Target;
	document.frmKPI_Company_Target.command.value="<%=Command.EDIT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=prevCommand%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdBack(){
	document.frmKPI_Company_Target.command.value="<%=Command.BACK%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListFirst(){
	document.frmKPI_Company_Target.command.value="<%=Command.FIRST%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.FIRST%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListPrev(){
	document.frmKPI_Company_Target.command.value="<%=Command.PREV%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.PREV%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListNext(){
	document.frmKPI_Company_Target.command.value="<%=Command.NEXT%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.NEXT%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdListLast(){
	document.frmKPI_Company_Target.command.value="<%=Command.LAST%>";
	document.frmKPI_Company_Target.prev_command.value="<%=Command.LAST%>";
	document.frmKPI_Company_Target.action="kpi_company_target.jsp";
	document.frmKPI_Company_Target.submit();
}

function cmdUpdateSec(){
                document.frmKPI_Company_Target.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmKPI_Company_Target.action="kpi_company_target.jsp";
                document.frmKPI_Company_Target.submit();
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
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 

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
                  &gt; KPI_Company_Target<!-- #EndEditable --> 
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
                                    <form name="frmKPI_Company_Target" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="type" value="<%=type%>">
                                      
                                      <input type="hidden" name="divId" value="<%=divId%>">
                                      <input type="hidden" name="depId" value="<%=depId%>">
                                      <input type="hidden" name="secId" value="<%=secId%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="KPI_Company_Target_oid" value="<%=oidKPI_Company_Target%>">
                                    
                                      
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">                                              
                                              <tr align="left" valign="top"> 
                                                <td colspan="3" >
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left">Tahun</div></td>
                                                      <td width="83%"> <%= ControlCombo.draw(frmKPI_Company_Target.fieldNames[frmKPI_Company_Target.FRM_FIELD_YEAR],"formElemen",null, ""+tahun, frmKPI_Company_Target.getYearValue(), frmKPI_Company_Target.getYearKey(), "onChange=\"javascript:cmdUpdateSec()\"") %></td> 
                                                    </tr>
                                                    
                                                    <tr align="left" valign="top">
                                                        <td valign="top" width="17%">
                                                            KPI_List</td>
                                                        <td width="83%">
                                                            <%
                                                                        Vector kpilist_value1 = new Vector(1, 1);
                                                                        Vector kpilist_key1 = new Vector(1, 1);
                                                                        Vector listkpilist1 = PstKPI_List.list(0, 0, "", "");
                                                                        kpilist_value1.add(""+0);
                                                                        kpilist_key1.add("select");
                                                                        for (int i = 0; i < listkpilist1.size(); i++) {
                                                                            KPI_List kPI_List = (KPI_List) listkpilist1.get(i);
                                                                            kpilist_key1.add(kPI_List.getKpi_title());
                                                                            kpilist_value1.add(String.valueOf(kPI_List.getOID()));
                                                                        }

                                                                        %>
                                                           <%= ControlCombo.draw(FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_KPI_LIST_ID], "formElemen", null, "" + (kpiListId), kpilist_value1, kpilist_key1,"onChange=\"javascript:cmdUpdateSec()\"")%> 

                                                        </td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                        <td valign="top" width="17%">
                                                            Company</td>
                                                        <td width="83%">
                                                        <%
                                                                        Vector comp_value2 = new Vector(1, 1);
                                                                        Vector comp_key2 = new Vector(1, 1);
                                                                        Vector listComp2 = PstCompany.list(0, 0, "", " COMPANY ");
                                                                        comp_value2.add(""+0);
                                                                        comp_key2.add("select");
                                                                        for (int i = 0; i < listComp2.size(); i++) {
                                                                            Company div2 = (Company) listComp2.get(i);
                                                                            comp_key2.add(div2.getCompany());
                                                                            comp_value2.add(String.valueOf(div2.getOID()));
                                                                        }

                                                                        %>
                                                           <%= ControlCombo.draw(FrmKPI_Company_Target.fieldNames[FrmKPI_Company_Target.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + (CompanyId), comp_value2, comp_key2,"onChange=\"javascript:cmdUpdateSec()\"")%>     
                                                        </td>
                                                    </tr>
                                                    
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                      <td class="listtitle" width="37%">&nbsp;</td>
                                                      <td width="63%" class="comment">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td class="listtitle" width="37%">Doc  List</td>
                                                      <td width="63%" class="comment">*)entry 
                                                        required </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <%  if(iCommand == Command.SAVE || iCommand == Command.GOTO || iCommand == Command.ASK || iCommand == Command.EDIT) {
												try{
                                                                                                 if (CompanyId > 0 && kpiListId > 0 && tahun > 0 ){    
												%>
                                              <tr align="left" valign="top"> 
                                                
                                                   
                                                   <%= drawList(iCommand, listKPI_Company_Target, oidKPI_Company_Target, frmKPI_Company_Target, tahun, kpiListId)%>
                                                   
                                                  <%if(iCommand == Command.EDIT){%>
                                                   <a href="javascript:cmdSaveModif(1)" class="command">Save Company Target</a>
                                                  
                                                   <% } %>
                                                  
                                                  
                                                   <%if (listKPI_Company_Target.size() > 0 && iCommand == Command.EDIT){%>
                                                   <%= drawListAllEdit(out,iCommand, HlistKPI_Division_Target, oidKPI_Company_Target, frmKPI_Company_Target, tahun, kpiListId, CompanyId, divId,HlistKPI_Department_Target, VDepartment,depId, HlistKPI_Section_Target, VSection,secId, HlistKPI_Employee_Target, VEmployee)%>
                                                  
                                            
                                                   <%if ((divId> 0) && (depId> 0)&& (secId> 0) &&  (iCommand == Command.EDIT) && VEmployee.size() > 0 ) {%>
                                                   <a href="javascript:cmdSaveModif(5)" class="command">Save Employee Target</a>
                                                   <% } else  if ((divId> 0) && (depId> 0) &&  (iCommand == Command.EDIT) && (VSection.size() > 0 || VEmployeeDepart.size() > 0) ) {%>
                                                   <a href="javascript:cmdSaveModif(4)" class="command">Save Section Target</a>
                                                   <% } else if ((divId> 0) && (iCommand == Command.EDIT) && VDepartment.size() > 0 ) {%>
                                                   <a href="javascript:cmdSaveModif(3)" class="command">Save Departemen Target</a>
                                                   <% } else {%>
                                                   <a href="javascript:cmdSaveModif(2)" class="command">Save Divisi Target</a>
                                                   <% } %>
                                                   <% } else {%>
                                                   <%//= drawListAll(out,iCommand, HlistKPI_Division_Target, oidKPI_Company_Target, frmKPI_Company_Target, tahun, kpiListId, CompanyId, divId,HlistKPI_Department_Target, VDepartment,depId, HlistKPI_Section_Target, VSection,secId, HlistKPI_Employee_Target, VEmployee)%>
                                                  
                                                   <%}%>
                                                   
                                              </tr>
                                              <% 
                                                                                           }										 
                                              }catch(Exception exc){ 
											  }
                                                        }
                                                  %>
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
                                              <%if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmKPI_Company_Target.errorSize()<1)){
											  	if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                    <!--  <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td> 
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Doc Type</a> </td> -->
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
									//String scomDel = "javascript:cmdAsk('"+oidKPI_Company_Target+"')";
									//String sconDelCom = "javascript:cmdConfirmDelete('"+oidKPI_Company_Target+"')";
									String scancel = "javascript:cmdEdit('"+oidKPI_Company_Target+"')";
									ctrLine.setBackCaption("");
									ctrLine.setCommandStyle("buttonlink");
									ctrLine.setAddCaption("");
									ctrLine.setSaveCaption("Save KPI_Company_Target");
									//ctrLine.setDeleteCaption("Delete KPI_Company_Target");
									ctrLine.setConfirmDelCaption("");
                                                                        
									ctrLine.setDeleteCaption("");

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										//ctrLine.setAddCaption("");
									}
									
									if((listKPI_Company_Target.size()<1)&&(iCommand == Command.NONE))
										 //iCommand = Command.ADD;  
										 
									if(iCommand == Command.ASK)
										//ctrLine.setDeleteQuestion(msgString);										 
									%>
                                            <% //ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
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
