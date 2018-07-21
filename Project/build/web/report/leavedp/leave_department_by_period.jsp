
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%-- 
    Document   : leave_department_by_period
    Created on : Sep 2, 2010, 2:22:29 PM
    Author     : roy andika
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.session.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_REPORT, AppObjInfo.OBJ_LEAVE_DP_SUMMARY); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%! 
static final int SEARCH_ALL = 1;
static final int SEARCH     = 2;
        
public String drawList(Vector objectClass,Vector listCompany,Date dateStart,Date dateEnd,int radioButton,long periodId)
{    
        String result = "";
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
        
        ctrlist.addHeader("NO","2%", "2", "0");
        ctrlist.addHeader("DEPT.","16%", "2", "0");
        ctrlist.addHeader("#EMP.","4%", "2", "0");
        ctrlist.addHeader("DP","35%", "0", "7");
        ctrlist.addHeader("AL","25%", "0", "6");
        ctrlist.addHeader("LL","35%", "0", "7");
        
        /* DP */
        ctrlist.addHeader("PREV.","5%","0","0");
        ctrlist.addHeader("TKN PREV.","5%","0","0");
        ctrlist.addHeader("EXP PREV.","5%","0","0");
        ctrlist.addHeader("QTY.","5%","0","0");
        ctrlist.addHeader("TAKEN","5%","0","0");
        ctrlist.addHeader("EXP","5%","0","0");
        ctrlist.addHeader("BAL.","5%","0","0");        
        
        /* AL */
        ctrlist.addHeader("PREV.","5%","0","0");
        ctrlist.addHeader("TAKEN PREV.","5%","0","0");        
        ctrlist.addHeader("QTY.","5%","0","0");
        ctrlist.addHeader("TAKEN","5%","0","0");
        //UPDATE BY DEVIN 2014-04-03
        ctrlist.addHeader("TO BE TAKEN","5%","0","0");
        ctrlist.addHeader("BAL.","5%","0","0");
        
        /* LL */
        ctrlist.addHeader("PREV.","5%","0","0");
        ctrlist.addHeader("TAKEN PREV.","5%","0","0");
        ctrlist.addHeader("EXP PREV.","5%","0","0");
        ctrlist.addHeader("QTY","5%","0","0");
        ctrlist.addHeader("TAKEN","5%","0","0");        
        ctrlist.addHeader("EXP","5%","0","0");
        ctrlist.addHeader("BAL.","5%","0","0");        
        
	ctrlist.setLinkRow(-1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
        int no = 1 ; 
        
        /*DP*/
        int sumEmp = 0;
        int cekDepartment=20;///???
        float dpQtyBeforeStartPeriod = 0;
        float dpTknBeforeStartPeriod = 0;
        float dpTknExpBeforeStartPeriod = 0;
        float dpQtyCurrentPeriod = 0;
        float dpTknCurrentPeriod = 0;
        float dpTknExpiredCurrentPeriod = 0;  
        float db_balance = 0;

        /*AL*/
        float alQtyBeforeStartPeriod = 0;
        float alTknBeforeStartPeriod = 0;
        float alQtyCurrentPeriod = 0;
        float alTknCurrentPeriod = 0;
        //UPDATE BY DEVIN 2014-04-03
          float alToBeTaken = 0;
        float al_balance = 0;

        /*LL*/
        float llQtyBeforeStartPeriod = 0;
        float llTknBeforeStartPeriod = 0;
        float llTknExpBeforeStartPeriod = 0;
        float llQtyCurrentPeriod = 0;
        float llTknCurrentPeriod = 0;
        float llTknExpiredCurrentPeriod = 0;  
        float ll_balance = 0;
        
        if(objectClass != null && objectClass.size() > 0){
           if(listCompany!=null && listCompany.size()>0){
            for(int vCom=0;vCom<listCompany.size();vCom++){  
                 int sumEmpCom = 0;
        
        float dpQtyBeforeStartPeriodCom = 0;
        float dpTknBeforeStartPeriodCom = 0;
        float dpTknExpBeforeStartPeriodCom = 0;
        float dpQtyCurrentPeriodCom = 0;
        float dpTknCurrentPeriodCom = 0;
        float dpTknExpiredCurrentPeriodCom = 0;   
        float db_balanceCom = 0;

        /*AL*/ 
        float alQtyBeforeStartPeriodCom = 0;
        float alTknBeforeStartPeriodCom = 0;
        float alQtyCurrentPeriodCom = 0;
        float alTknCurrentPeriodCom = 0;
        //UPDATE BY DEVIN 2014-04-03
          float alToBeTakenCom = 0; 
        float al_balanceCom = 0;

        /*LL*/
        float llQtyBeforeStartPeriodCom = 0;
        float llTknBeforeStartPeriodCom = 0;
        float llTknExpBeforeStartPeriodCom = 0;
        float llQtyCurrentPeriodCom = 0;
        float llTknCurrentPeriodCom = 0;
        float llTknExpiredCurrentPeriodCom = 0;  
        float ll_balanceCom = 0;
                PayGeneral payGeneral = (PayGeneral)listCompany.get(vCom); 
                 Vector rowx1 = new Vector();
                 rowx1.add(""); 
                rowx1.add("<b>"+payGeneral.getCompanyName()+"</b>");
                //??
                for(int cmpName=0;cmpName<21;cmpName++){
                    rowx1.add("");
                }
                 lstData.add(rowx1); 
                lstLinkData.add("0");  
               Vector listDivision = PstDivision.list(0, 0, PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+payGeneral.getOID(), PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
            if(listDivision!=null && listDivision.size()>0){ 
                 for(int div=0;div<listDivision.size();div++){ 
            
                 Division division =(Division)listDivision.get(div); 
                  Vector rowx2 = new Vector();
                     rowx2.add(""+no);  
                    rowx2.add("&nbsp;&nbsp;<b>-"+division.getDivision()+"</b>");
                    //??
                     for(int vDiv=0;vDiv<21;vDiv++){
                    rowx2.add("");
                }
                     lstData.add(rowx2); 
                lstLinkData.add("0"); 
                 Vector listDep = PstDepartment.list(0, 0, PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+division.getOID(),PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
            for (int i=0; i<listDep.size(); i++) 
            {
                //??
                cekDepartment=20; 
                
                Department department = (Department)listDep.get(i);
              
                if(cekDepartment==20){
                    float bal_dp = 0;
                    float bal_al = 0;
                    float bal_ll = 0;
                    int xx=0;
           
               
                  
                       Vector rowx = new Vector();
                    Vector resultt = new Vector();
                    SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
                
                  
                
                        rowx.add(""); 
                rowx.add("&nbsp;&nbsp;&nbsp;&nbsp;<b>"+department.getDepartment()+"</b>");   
                
                     resultt=SessLeaveApplication.sumLeave_DepartmentGetSection(department.getOID(),objSrcLeaveAppAlClosing,dateStart,dateEnd,radioButton,periodId);  
                
               
                for(int f=0;f<resultt.size();f++){
                     RepLevDepartment repLevDepartment = new RepLevDepartment();
                
                repLevDepartment = (RepLevDepartment)resultt.get(f);  
                rowx.add(""+repLevDepartment.getCountEmployee());
                
                sumEmp = sumEmp + repLevDepartment.getCountEmployee();
                   sumEmpCom = sumEmpCom + repLevDepartment.getCountEmployee(); 
                /*DP*/
                rowx.add(""+repLevDepartment.getDpQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpiredCurrentPeriod());
                
                dpQtyBeforeStartPeriod = dpQtyBeforeStartPeriod + repLevDepartment.getDpQtyBeforeStartPeriod();
                dpQtyBeforeStartPeriodCom = dpQtyBeforeStartPeriodCom + repLevDepartment.getDpQtyBeforeStartPeriod();
                dpTknBeforeStartPeriod = dpTknBeforeStartPeriod + repLevDepartment.getDpTknBeforeStartPeriod();
                dpTknBeforeStartPeriodCom = dpTknBeforeStartPeriodCom + repLevDepartment.getDpTknBeforeStartPeriod();
                dpTknExpBeforeStartPeriod = dpTknExpBeforeStartPeriod + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpTknExpBeforeStartPeriodCom = dpTknExpBeforeStartPeriodCom + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpQtyCurrentPeriod = dpQtyCurrentPeriod + repLevDepartment.getDpQtyCurrentPeriod();
                dpQtyCurrentPeriodCom = dpQtyCurrentPeriodCom + repLevDepartment.getDpQtyCurrentPeriod();
                dpTknCurrentPeriod = dpTknCurrentPeriod + repLevDepartment.getDpTknCurrentPeriod();
                dpTknCurrentPeriodCom = dpTknCurrentPeriodCom + repLevDepartment.getDpTknCurrentPeriod();
                dpTknExpiredCurrentPeriod = dpTknExpiredCurrentPeriod + repLevDepartment.getDpTknExpiredCurrentPeriod();
                dpTknExpiredCurrentPeriodCom = dpTknExpiredCurrentPeriodCom + repLevDepartment.getDpTknExpiredCurrentPeriod(); 
                                        
                bal_dp =  repLevDepartment.getDpQtyBeforeStartPeriod() - 
                          repLevDepartment.getDpTknBeforeStartPeriod() -
                          repLevDepartment.getDpTknExpBeforeStartPeriod() +
                          repLevDepartment.getDpQtyCurrentPeriod() - 
                          repLevDepartment.getDpTknCurrentPeriod() - 
                          repLevDepartment.getDpTknExpiredCurrentPeriod();
                
                
                rowx.add(""+bal_dp);
                db_balance = db_balance + bal_dp;                
                db_balanceCom = db_balanceCom + bal_dp; 

                /*AL*/
                rowx.add(""+repLevDepartment.getAlQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getAlTknCurrentPeriod());
                //UPDATE BY DEVIN 2014-04-03
                  rowx.add(""+repLevDepartment.getAlToBeTaken());
                bal_al = repLevDepartment.getAlQtyBeforeStartPeriod() -
                         repLevDepartment.getAlTknBeforeStartPeriod() +
                         repLevDepartment.getAlQtyCurrentPeriod() - 
                         repLevDepartment.getAlTknCurrentPeriod()-repLevDepartment.getAlToBeTaken();
                rowx.add(""+bal_al);
                
                alQtyBeforeStartPeriod = alQtyBeforeStartPeriod + repLevDepartment.getAlQtyBeforeStartPeriod();
                alQtyBeforeStartPeriodCom = alQtyBeforeStartPeriodCom + repLevDepartment.getAlQtyBeforeStartPeriod();
                alTknBeforeStartPeriod = alTknBeforeStartPeriod + repLevDepartment.getAlTknBeforeStartPeriod();
                alTknBeforeStartPeriodCom = alTknBeforeStartPeriodCom + repLevDepartment.getAlTknBeforeStartPeriod();
                alQtyCurrentPeriod = alQtyCurrentPeriod + repLevDepartment.getAlQtyCurrentPeriod();
                alQtyCurrentPeriodCom = alQtyCurrentPeriodCom + repLevDepartment.getAlQtyCurrentPeriod();
                alTknCurrentPeriod = alTknCurrentPeriod + repLevDepartment.getAlTknCurrentPeriod();
                alTknCurrentPeriodCom = alTknCurrentPeriodCom + repLevDepartment.getAlTknCurrentPeriod();
                 //UPDATE BY DEVIN 2014-04-03
                alToBeTaken= alToBeTaken + repLevDepartment.getAlToBeTaken(); 
                 alToBeTakenCom= alToBeTakenCom + repLevDepartment.getAlToBeTaken(); 
                al_balance = al_balance + bal_al;                
                  al_balanceCom = al_balanceCom + bal_al;    
                /*LL*/
                rowx.add(""+repLevDepartment.getLLQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getLLTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getLLTknExpiredCurrentPeriod());
                
                bal_ll =  repLevDepartment.getLLQtyBeforeStartPeriod() - 
                          repLevDepartment.getLLTknBeforeStartPeriod() -
                          repLevDepartment.getLLTknExpBeforeStartPeriod() +
                          repLevDepartment.getLLQtyCurrentPeriod() - 
                          repLevDepartment.getLLTknCurrentPeriod() - 
                          repLevDepartment.getLLTknExpiredCurrentPeriod();
                rowx.add(""+bal_ll);
                
                llQtyBeforeStartPeriod = llQtyBeforeStartPeriod + repLevDepartment.getLLQtyBeforeStartPeriod();
                llQtyBeforeStartPeriodCom = llQtyBeforeStartPeriodCom + repLevDepartment.getLLQtyBeforeStartPeriod();
                llTknBeforeStartPeriod = llTknBeforeStartPeriod + repLevDepartment.getLLTknBeforeStartPeriod();
                llTknBeforeStartPeriodCom = llTknBeforeStartPeriodCom + repLevDepartment.getLLTknBeforeStartPeriod();
                llTknExpBeforeStartPeriod = llTknExpBeforeStartPeriod + repLevDepartment.getLLTknExpBeforeStartPeriod();
                llTknExpBeforeStartPeriodCom = llTknExpBeforeStartPeriodCom + repLevDepartment.getLLTknExpBeforeStartPeriod();
                llQtyCurrentPeriod = llQtyCurrentPeriod + repLevDepartment.getLLQtyCurrentPeriod();
                llQtyCurrentPeriodCom = llQtyCurrentPeriodCom + repLevDepartment.getLLQtyCurrentPeriod();
                llTknCurrentPeriod = llTknCurrentPeriod + repLevDepartment.getLLTknCurrentPeriod();
                llTknCurrentPeriodCom = llTknCurrentPeriodCom + repLevDepartment.getLLTknCurrentPeriod();
                llTknExpiredCurrentPeriod = llTknExpiredCurrentPeriod + repLevDepartment.getLLTknExpiredCurrentPeriod();
                llTknExpiredCurrentPeriodCom = llTknExpiredCurrentPeriodCom + repLevDepartment.getLLTknExpiredCurrentPeriod();
                
                ll_balance = ll_balance + bal_ll;
                 ll_balanceCom = ll_balanceCom + bal_ll;
                
               
		lstData.add(rowx);
                lstLinkData.add("0");
                }
                   
                }else{
                
                 Vector rowx3 = new Vector();
                     rowx3.add("");  
                    rowx3.add("&nbsp;&nbsp;&nbsp;&nbsp;<b>"+department.getDepartment()+"</b>");
                     for(int vDept=0;vDept<21;vDept++){
                    rowx3.add("");
                }
                     lstData.add(rowx3); 
                lstLinkData.add("0"); 
                               }
                Vector listSec=PstSection.list(0, 0, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+department.getOID(), PstSection.fieldNames[PstSection.FLD_SECTION]);
                 /*boolean loopOnlyDep=false;
                        if(listSec==null || listSec.size()<1){
                          loopOnlyDep = true;  
                        }*/
                
               
                //for(int sec=0;sec<listSec.size()|| loopOnlyDep==true;sec++){
                for(int sec=0;sec<listSec.size();sec++){
                float bal_dp = 0;
                float bal_al = 0;
                float bal_ll = 0;
                int xx=0;
               /*if(!loopOnlyDep){//tulis untuk di departement
                   cekDepartment=20; 
               }else{
                    cekDepartment=10; 
               }*/
               
                   if(listSec!=null && listSec.size()>0){ 
                Vector rowx = new Vector();
                    Vector resultt = new Vector();
                    SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();
                
                   Section section=(Section)listSec.get(sec);
                
                        rowx.add(""); 
                rowx.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+section.getSection());   
                
                     resultt=SessLeaveApplication.sumLeave_Department2(section.getOID(),objSrcLeaveAppAlClosing,dateStart,dateEnd,radioButton,periodId);  
                
               
                for(int f=0;f<resultt.size();f++){
                     RepLevDepartment repLevDepartment = new RepLevDepartment();
                
                repLevDepartment = (RepLevDepartment)resultt.get(f);  
                rowx.add(""+repLevDepartment.getCountEmployee());
                
                sumEmp = sumEmp + repLevDepartment.getCountEmployee();
                sumEmpCom = sumEmpCom + repLevDepartment.getCountEmployee();

                /*DP*/
                rowx.add(""+repLevDepartment.getDpQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getDpQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getDpTknExpiredCurrentPeriod());
                
                dpQtyBeforeStartPeriod = dpQtyBeforeStartPeriod + repLevDepartment.getDpQtyBeforeStartPeriod();
                dpQtyBeforeStartPeriodCom = dpQtyBeforeStartPeriodCom + repLevDepartment.getDpQtyBeforeStartPeriod();
                dpTknBeforeStartPeriod = dpTknBeforeStartPeriod + repLevDepartment.getDpTknBeforeStartPeriod();
                dpTknBeforeStartPeriodCom = dpTknBeforeStartPeriodCom + repLevDepartment.getDpTknBeforeStartPeriod();
                dpTknExpBeforeStartPeriod = dpTknExpBeforeStartPeriod + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpTknExpBeforeStartPeriodCom = dpTknExpBeforeStartPeriodCom + repLevDepartment.getDpTknExpBeforeStartPeriod();
                dpQtyCurrentPeriod = dpQtyCurrentPeriod + repLevDepartment.getDpQtyCurrentPeriod();
                dpQtyCurrentPeriodCom = dpQtyCurrentPeriodCom + repLevDepartment.getDpQtyCurrentPeriod();
                dpTknCurrentPeriod = dpTknCurrentPeriod + repLevDepartment.getDpTknCurrentPeriod();
                dpTknCurrentPeriodCom = dpTknCurrentPeriodCom + repLevDepartment.getDpTknCurrentPeriod();
                dpTknExpiredCurrentPeriod = dpTknExpiredCurrentPeriod + repLevDepartment.getDpTknExpiredCurrentPeriod();
                dpTknExpiredCurrentPeriodCom = dpTknExpiredCurrentPeriodCom + repLevDepartment.getDpTknExpiredCurrentPeriod();
                                        
                bal_dp =  repLevDepartment.getDpQtyBeforeStartPeriod() - 
                          repLevDepartment.getDpTknBeforeStartPeriod() -
                          repLevDepartment.getDpTknExpBeforeStartPeriod() +
                          repLevDepartment.getDpQtyCurrentPeriod() - 
                          repLevDepartment.getDpTknCurrentPeriod() - 
                          repLevDepartment.getDpTknExpiredCurrentPeriod();
                
                rowx.add(""+bal_dp);
                db_balance = db_balance + bal_dp;                
                db_balanceCom = db_balanceCom + bal_dp;                            

                /*AL*/
                rowx.add(""+repLevDepartment.getAlQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getAlQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getAlTknCurrentPeriod());
                //UPDATE BY DEVIN 2014-04-03
                  rowx.add(""+repLevDepartment.getAlToBeTaken());
                bal_al = repLevDepartment.getAlQtyBeforeStartPeriod() -
                         repLevDepartment.getAlTknBeforeStartPeriod() +
                         repLevDepartment.getAlQtyCurrentPeriod() - 
                         repLevDepartment.getAlTknCurrentPeriod()-repLevDepartment.getAlToBeTaken();
                rowx.add(""+bal_al);
                
                alQtyBeforeStartPeriod = alQtyBeforeStartPeriod + repLevDepartment.getAlQtyBeforeStartPeriod();
                alQtyBeforeStartPeriodCom = alQtyBeforeStartPeriodCom + repLevDepartment.getAlQtyBeforeStartPeriod();
                alTknBeforeStartPeriod = alTknBeforeStartPeriod + repLevDepartment.getAlTknBeforeStartPeriod();
                alTknBeforeStartPeriodCom = alTknBeforeStartPeriodCom + repLevDepartment.getAlTknBeforeStartPeriod();
                alQtyCurrentPeriod = alQtyCurrentPeriod + repLevDepartment.getAlQtyCurrentPeriod();
                alQtyCurrentPeriodCom = alQtyCurrentPeriodCom + repLevDepartment.getAlQtyCurrentPeriod();
                alTknCurrentPeriod = alTknCurrentPeriod + repLevDepartment.getAlTknCurrentPeriod();
                alTknCurrentPeriodCom = alTknCurrentPeriodCom + repLevDepartment.getAlTknCurrentPeriod();
                 //UPDATE BY DEVIN 2014-04-03
                alToBeTaken= alToBeTaken + repLevDepartment.getAlToBeTaken(); 
                alToBeTakenCom= alToBeTakenCom + repLevDepartment.getAlToBeTaken(); 
                al_balance = al_balance + bal_al;                
                 al_balanceCom = al_balanceCom + bal_al; 

                /*LL*/
                rowx.add(""+repLevDepartment.getLLQtyBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLTknBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLTknExpBeforeStartPeriod());
                rowx.add(""+repLevDepartment.getLLQtyCurrentPeriod());
                rowx.add(""+repLevDepartment.getLLTknCurrentPeriod());
                rowx.add(""+repLevDepartment.getLLTknExpiredCurrentPeriod());
                
                bal_ll =  repLevDepartment.getLLQtyBeforeStartPeriod() - 
                          repLevDepartment.getLLTknBeforeStartPeriod() -
                          repLevDepartment.getLLTknExpBeforeStartPeriod() +
                          repLevDepartment.getLLQtyCurrentPeriod() - 
                          repLevDepartment.getLLTknCurrentPeriod() - 
                          repLevDepartment.getLLTknExpiredCurrentPeriod();
                rowx.add(""+bal_ll);
                
                llQtyBeforeStartPeriod = llQtyBeforeStartPeriod + repLevDepartment.getLLQtyBeforeStartPeriod();
                 llQtyBeforeStartPeriodCom = llQtyBeforeStartPeriodCom + repLevDepartment.getLLQtyBeforeStartPeriod();
                llTknBeforeStartPeriod = llTknBeforeStartPeriod + repLevDepartment.getLLTknBeforeStartPeriod();
                llTknBeforeStartPeriodCom = llTknBeforeStartPeriodCom + repLevDepartment.getLLTknBeforeStartPeriod();
                llTknExpBeforeStartPeriod = llTknExpBeforeStartPeriod + repLevDepartment.getLLTknExpBeforeStartPeriod();
                llTknExpBeforeStartPeriodCom = llTknExpBeforeStartPeriodCom + repLevDepartment.getLLTknExpBeforeStartPeriod();
                llQtyCurrentPeriod = llQtyCurrentPeriod + repLevDepartment.getLLQtyCurrentPeriod();
                llQtyCurrentPeriodCom = llQtyCurrentPeriodCom + repLevDepartment.getLLQtyCurrentPeriod();
                llTknCurrentPeriod = llTknCurrentPeriod + repLevDepartment.getLLTknCurrentPeriod();
                llTknCurrentPeriodCom = llTknCurrentPeriodCom + repLevDepartment.getLLTknCurrentPeriod();
                llTknExpiredCurrentPeriod = llTknExpiredCurrentPeriod + repLevDepartment.getLLTknExpiredCurrentPeriod();
                llTknExpiredCurrentPeriodCom = llTknExpiredCurrentPeriodCom + repLevDepartment.getLLTknExpiredCurrentPeriod();
                
                ll_balance = ll_balance + bal_ll;
                ll_balanceCom = ll_balance + bal_ll;
                
               
		lstData.add(rowx);
                lstLinkData.add("0");
                }

                   
                   }   
                 
                
                 
                
                    
                    
                    
                    
                    
                    
            }
            
                
                                 

            }
             no++;
            
                       }
            Vector rowx = new Vector(1,1);  
            rowx.add("");
            rowx.add("TOTAL");
            rowx.add(""+sumEmpCom);
            
            rowx.add(""+dpQtyBeforeStartPeriodCom);
            rowx.add(""+dpTknBeforeStartPeriodCom);            
            rowx.add(""+dpTknExpBeforeStartPeriodCom);
            rowx.add(""+dpQtyCurrentPeriodCom);
            rowx.add(""+dpTknCurrentPeriodCom); 
            rowx.add(""+dpTknExpiredCurrentPeriodCom);
            rowx.add(""+db_balanceCom);
            
            rowx.add(""+alQtyBeforeStartPeriodCom);
            rowx.add(""+alTknBeforeStartPeriodCom);
            rowx.add(""+alQtyCurrentPeriodCom);
            rowx.add(""+alTknCurrentPeriodCom);
            //UPDATE BY DEVIN 2014-04-03
             rowx.add(""+alToBeTakenCom);
            rowx.add(""+al_balanceCom);
            
            rowx.add(""+llQtyBeforeStartPeriodCom);
            rowx.add(""+llTknBeforeStartPeriodCom);
            rowx.add(""+llTknExpBeforeStartPeriodCom);
            rowx.add(""+llQtyCurrentPeriodCom);
            rowx.add(""+llTknCurrentPeriodCom);
            rowx.add(""+llTknExpiredCurrentPeriodCom);
            rowx.add(""+ll_balanceCom);
            
            lstData.add(rowx);
            lstLinkData.add("0");
            
            
                 no=1;
                
                       }
                   }
            Vector rowxCom = new Vector(1,1);  
            rowxCom.add("");
            rowxCom.add("TOTAL ALL COMPANY");
            rowxCom.add(""+sumEmp);
        
            rowxCom.add(""+dpQtyBeforeStartPeriod);
            rowxCom.add(""+dpTknBeforeStartPeriod);            
            rowxCom.add(""+dpTknExpBeforeStartPeriod);
            rowxCom.add(""+dpQtyCurrentPeriod);
            rowxCom.add(""+dpTknCurrentPeriod);
            rowxCom.add(""+dpTknExpiredCurrentPeriod);
            rowxCom.add(""+db_balance);
            
            rowxCom.add(""+alQtyBeforeStartPeriod);
            rowxCom.add(""+alTknBeforeStartPeriod);
            rowxCom.add(""+alQtyCurrentPeriod);
            rowxCom.add(""+alTknCurrentPeriod);
            //UPDATE BY DEVIN 2014-04-03
             rowxCom.add(""+alToBeTaken);
            rowxCom.add(""+al_balance);
            
            rowxCom.add(""+llQtyBeforeStartPeriod);
            rowxCom.add(""+llTknBeforeStartPeriod);
            rowxCom.add(""+llTknExpBeforeStartPeriod);
            rowxCom.add(""+llQtyCurrentPeriod);
            rowxCom.add(""+llTknCurrentPeriod);
            rowxCom.add(""+llTknExpiredCurrentPeriod);
            rowxCom.add(""+ll_balance);
            
            lstData.add(rowxCom);
            lstLinkData.add("0");
            
           
            result = ctrlist.drawList();
         }
        }else{					
            result += "<div class=\"msginfo\">&nbsp;&nbsp;Leave and Dp Stock Data Found found ...</div>";																
        }       
        return result;
}
%>

<%

privAdd=false;

int iCommand = FRMQueryString.requestCommand(request);
int Type = FRMQueryString.requestInt(request, "Type");
Date dateStart = FRMQueryString.requestDate(request, FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START]);
Date dateEnd = FRMQueryString.requestDate(request, FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END]); 
Date Period = FRMQueryString.requestDate(request, FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_PERIOD]); 
SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();

FrmSrcLeaveAppAlClosing objFrmSrcLeaveAppAlClosing = new FrmSrcLeaveAppAlClosing(request, objSrcLeaveAppAlClosing);

Vector result = new Vector();

//update by devin 2014-04-03
Vector listCompany = new Vector(); 
//Vector listDivision = new Vector();
/* Jika kondisi adalah view */
if(iCommand == Command.VIEW){
    //update by devin 2014-04-03
    listCompany = PstPayGeneral.listAll();
   // listDivision = PstDivision.listAll(); 
    
        objFrmSrcLeaveAppAlClosing.requestEntityObject(objSrcLeaveAppAlClosing);
        
        result = SessLeaveApplication.sumLeave_Department(objSrcLeaveAppAlClosing);
    
        try{
		session.removeValue("LEAVE_DEPARTMENT_PERIOD_REPORT");
	}
	catch(Exception e)
	{
		System.out.println("Exc when remove from session(\"LEAVE_DEPARTMENT_PERIOD_REPORT\") : " + e.toString());	
	}
	
	try{
		session.putValue("LEAVE_DEPARTMENT_PERIOD_REPORT",result);
	}catch(Exception e){
		System.out.println("Exc when put to session(\"LEAVE_DEPARTMENT_PERIOD_REPORT\") : " + e.toString());		
	}    
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report Leave Department By Period</title>
<script language="JavaScript">
<!--
function cmdPrintXLS(){
        pathUrl = "<%=approot%>/servlet/com.dimata.harisma.report.leave.LeaveDepartmentSumPeriod";
        window.open(pathUrl);
}    

function cmdView(){
        getCommancingDateStart();
        getCommancingDateEnd();
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.command.value ="<%=String.valueOf(Command.VIEW)%>";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.action ="leave_department_by_period.jsp";
        document.<%=FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>.submit();
}

function getThn(){
}

function hideObjectForDate(index){
}

function showObjectForDate(){
} 

function getCommancingDateStart(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START])%>
}

function getCommancingDateEnd(){    
     <%=ControlDatePopup.writeDateCaller(FrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP,FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END])%>
}

function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	cmdSearch();
   }
}

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
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">

<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->


<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../../main/mnmain.jsp" %>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave &gt; Leave & Dp summary period<!-- #EndEditable --> 
                  </strong></font> </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table  width="100%">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
                                    <!-- #BeginEditable "content" --> 
                                    <form name="<%=objFrmSrcLeaveAppAlClosing.FRM_NAME_SRCLEAVE_APP%>" method="post" action="">
                                      <!--<input type="hidden" name="command" value="">-->
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="Type" value="<%=String.valueOf(Type)%>">     
                                      <table border="0" cellspacing="2" cellpadding="2" width="100%" > 
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Period</td>
                                          <td width="2%">:</td>
                                          <td width="85%" class="command" nowrap>  
                                          <%                                        
                                            String selectValuePeriod = ""+objSrcLeaveAppAlClosing.getPeriodId();
                                            
                                            Vector period_value = new Vector(1, 1);
                                            Vector period_key = new Vector(1, 1);
            
                                            period_value.add("0");
                                            period_key.add("select...");
                                            Vector listPeriod = new Vector(1, 1);
           
                                            listPeriod = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" DESC");
                                            for (int i = 0; i < listPeriod.size(); i++) {
                                                Period period = (Period) listPeriod.get(i);
                                                period_key.add(period.getPeriod());
                                                period_value.add(String.valueOf(period.getOID()));
                                            }
                                           %>
                                           <% 
                                           if(objSrcLeaveAppAlClosing.getRadioBtn()==0){
                                           %>
                                           <input type="radio" name="<%=FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_RADIO_BTN]%>" value="0" checked>                                             
                                           <% 
                                           }else{
                                           %>
                                           <input type="radio" name="<%=FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_RADIO_BTN]%>" value="0">                                             
                                           <%}%>
                                           <%= ControlCombo.draw(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_PERIOD], null, selectValuePeriod, period_value, period_key, " onkeydown=\"javascript:fnTrapKD()\"") %>                                            
                                           &nbsp;OR&nbsp;  
                                           <%
                                           if(objSrcLeaveAppAlClosing.getRadioBtn()==1){
                                           %>
                                           <input type="radio" name="<%=FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_RADIO_BTN]%>" value="1" checked>                                             
                                           <% 
                                           }else{
                                           %>
                                           <input type="radio" name="<%=FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_RADIO_BTN]%>" value="1">                                             
                                           <%}%>
                                           <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_START],objSrcLeaveAppAlClosing.getEmpCommancingDateStart()==null ? new Date() : objSrcLeaveAppAlClosing.getEmpCommancingDateStart(), "getCommancingDateStart()")%> To
                                           <%=ControlDatePopup.writeDate(FrmSrcLeaveAppAlClosing.fieldNames[FrmSrcLeaveAppAlClosing.FRM_FIELD_COMMANCING_END],objSrcLeaveAppAlClosing.getEmpCommancingDateEnd()==null ? new Date() : objSrcLeaveAppAlClosing.getEmpCommancingDateEnd(), "getCommancingDateEnd()")%>                                                                                    
                                           
                                        </td>
                                        </tr>                                        
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
                                                <td width="25px" ></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap align="left"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search closing employee" ></a></td>  
                                                <td width="100px"><a href="javascript:cmdView()">View Report</a></td>
                                                <td width="30px"></td>  
                                                <td width="100px"></td>                                                
                                                <td width="25px"></td>
                                                <td width="15px"></td>
                                                <td width="100px"></td>                                                                                                     
                                              </tr>                                              
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                         <tr>
                                        <td colspan="3">
                                        <% 
                                        if(result != null && result.size() > 0) { %>
                                          
                                               <%=drawList(result,listCompany,dateStart,dateEnd,objSrcLeaveAppAlClosing.getRadioBtn(),objSrcLeaveAppAlClosing.getPeriodId())%> 
                                           <% 
                                        %>        
                                                
                                        </td>
                                        </tr> 
                                         <tr>
                                        <td colspan="3">
                                        &nbsp;
                                        </td>
                                        </tr> 
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="225">
                                              <tr> 
                                                <td width="25px" ><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Close Period" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdPrintXLS()">Print XLS</a></td>                                                                                                                                                      
                                                <td width="15px"></td>
                                                <td width="30px" ></td>
                                                <td width="10px"></td>
                                                <td width="10px"></td>                                                                                                     
                                              </tr>                                                   
                                            </table>
                                          </td>
                                        </tr>
                                        <%
                                        }
                                        %>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
</html>
