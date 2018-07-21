
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.form.leave.FrmLeaveApplication"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,                  
                  com.dimata.util.Command,
                  com.dimata.util.Formater,				  
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.qdep.form.FRMQueryString,
		  com.dimata.gui.jsp.ControlList,				  
		  com.dimata.gui.jsp.ControlLine ,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.LeavePeriod,				  
                  com.dimata.harisma.entity.masterdata.PstLeavePeriod,
		  com.dimata.harisma.entity.masterdata.PstPeriod,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.employee.PstEmployee,
                  com.dimata.harisma.entity.attendance.DpStockManagement,
                  com.dimata.harisma.entity.attendance.PstDpStockManagement,				  
                  com.dimata.harisma.form.attendance.FrmDpStockManagement,
                  com.dimata.harisma.form.attendance.CtrlDpStockManagement,
                  com.dimata.harisma.entity.search.SrcLeaveManagement, 
                  com.dimata.harisma.form.search.FrmSrcLeaveManagement,
                  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.session.attendance.SessLeaveManagement"%>    
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>    
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_DP_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
// untuk menampilkan daftar employee yang memiliki Dp dan editor masing-masing DP dalam periode terpilih
//created by Gadnyana
//edited & documented by Edhy
public String drawListPerEmployee(int offsetStart, Vector listdp, int iCommand, long oidDp,I_Leave leaveConfig) 
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","2%", "0", "0");
	ctrlist.addHeader("Acquisition Date","10%", "0", "0");
        //update by satrya 2013-02-24
        ctrlist.addHeader("Generate/Edit by Stok Management","10%", "0", "0");
        
        ctrlist.addHeader("Expired Date","10%", "0", "0");
	ctrlist.addHeader("Qty","3%", "0", "0");
	ctrlist.addHeader("Taken","3%", "0", "0");
        ctrlist.addHeader("Expired","3%", "0", "0");
	ctrlist.addHeader("Eligible","3%", "0", "0");
        ctrlist.addHeader("Exception","4%", "0", "0");
        ctrlist.addHeader("Exc. Expired Date","7%", "0", "0");
	ctrlist.addHeader("Status","5%", "0", "0");
	ctrlist.addHeader("Notes","10%", "0", "0");
	ctrlist.setLinkRow(-1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector result = new Vector(1,1);
	Vector list = new Vector(1,1);	
        
String readOnly="readonly";
String StyleBg="background-color:#F5F5F5;text-align:right";
boolean cekDpMinus=true;
if(leaveConfig!=null && leaveConfig.getConfigurationDpMinus()==false){
    readOnly=""; 
    StyleBg="text-align:right";
    cekDpMinus = false; 
}        
	for (int i = 0; i < listdp.size(); i++) 
	{
	Vector rowx = new Vector(1,1);
        DpStockManagement dpStockMnt = (DpStockManagement)listdp.get(i);  
		
        if(iCommand==Command.EDIT)   
	{			
            if( oidDp==dpStockMnt.getOID() && dpStockMnt.getiDpStatus()==PstDpStockManagement.DP_STS_AKTIF )
            {
                rowx.add(""+(offsetStart+i+1)+"<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STOCK_ID]+"\" value=\""+dpStockMnt.getOID()+"\">"
                        +"<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_LEAVE_PERIODE_ID]+"\" value=\""+dpStockMnt.getLeavePeriodeId()+"\">");  
		
                rowx.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((dpStockMnt.getDtOwningDate() == null? new Date() : dpStockMnt.getDtOwningDate()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");	
                //update by satrya 2013-02-24
                Vector fgStockValue = new Vector(1,1);
                Vector fgStockKey = new Vector(1,1);
                fgStockValue.add(""+PstDpStockManagement.DP_FLAG_EDIT_NO);
                fgStockKey.add("NO");
                fgStockValue.add(""+PstDpStockManagement.DP_FLAG_EDIT_YES);
                fgStockKey.add("YES");
                rowx.add(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_FLAG_STOCK],null,""+PstDpStockManagement.DP_FLAG_EDIT_YES,fgStockValue,fgStockKey));
                
		rowx.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((dpStockMnt.getDtExpiredDate() == null? new Date() : dpStockMnt.getDtExpiredDate()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");		
                
		String strQtyFieldStyle = "";
                
                float balance_residue = 0;                
                float sumExpired = SessLeaveManagement.getTotalExpiredDP(dpStockMnt.getOID());
                balance_residue = dpStockMnt.getiDpQty() - dpStockMnt.getQtyUsed() - sumExpired;
		rowx.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]+"\" value=\""+dpStockMnt.getiDpQty()+"\" class=\"elemenForm\" "+strQtyFieldStyle+"size=\"3\">");				
		rowx.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]+"\" value=\""+dpStockMnt.getQtyUsed()+"\" class=\"elemenForm\" style=\""+StyleBg +"\""+readOnly+"\" size=\"3\">"); 
                
                rowx.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\"FRM_DP_STOCK_EXPIRED\" value=\""+sumExpired+"\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"3\">");                
		rowx.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_RESIDUE]+"\" value=\""+balance_residue+"\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"3\">");				

                Vector fgValue = new Vector(1,1);
                Vector fgKey = new Vector(1,1);
                fgValue.add("0");
                fgKey.add("NO");
                fgValue.add("1");
                fgKey.add("YES");

                rowx.add(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXCEPTION_FLAG],null,""+dpStockMnt.getiExceptionFlag(),fgValue,fgKey));
                
                rowx.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((dpStockMnt.getDtExpiredDateExc() == null? new Date() : dpStockMnt.getDtExpiredDateExc()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");
                
              
                Vector stValue = new Vector(1,1);
                Vector stKey = new Vector(1,1);
                for(int d=0;d<PstDpStockManagement.fieldStatus.length;d++)
		{
			if(d==PstDpStockManagement.DP_STS_AKTIF)
			{
				stValue.add(""+d);
				stKey.add(PstDpStockManagement.fieldStatus[d]);
			}
                }                
                
                String note = dpStockMnt.getStNote() == null ? "" : dpStockMnt.getStNote();
		rowx.add(PstDpStockManagement.fieldStatus[dpStockMnt.getiDpStatus()]); 
                //rowx.add("<input type=\"text\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]+"\" value=\""+note+"\" class=\"elemenForm\" style=\"background-color:#F5F5F5\"  size=\"20\">");  
                rowx.add("<textarea name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]+"\" cols=\"40\" rows=\"3\" class=\"elemenForm\" >"+note+"</textarea>"); 
                //update by satrya 2013-05-6
                //rowx.add("<input type=\"text\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]+"\" value=\""+note+"\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"20\">"); 
                }
			
			
		else
		{
                rowx.add(""+(offsetStart+i+1));
				if(dpStockMnt.getiDpStatus()==PstDpStockManagement.DP_STS_AKTIF)								
				
				{
                rowx.add("<a href=\"javascript:cmdEdit(\'"+dpStockMnt.getOID()+"')\">"+Formater.formatDate(dpStockMnt.getDtOwningDate(),"MMM dd, yyyy")+"</a>");
				}	
                else
				{
                    rowx.add(Formater.formatDate(dpStockMnt.getDtOwningDate(),"MMM dd, yyyy"));
				}
				
                float sumExpired = SessLeaveManagement.getTotalExpiredDP(dpStockMnt.getOID());
                //update by satrya 2013-02-24
                rowx.add(PstDpStockManagement.fieldFlag[dpStockMnt.getFlagStock()]);
                rowx.add(Formater.formatDate(dpStockMnt.getDtExpiredDate(),"MMM dd, yyyy"));
                  
                rowx.add(""+dpStockMnt.getiDpQty());
                rowx.add(""+dpStockMnt.getQtyUsed());  
                rowx.add(""+sumExpired);  
                rowx.add(""+dpStockMnt.getQtyResidue());
                String str = "YES";
                if(dpStockMnt.getiExceptionFlag()==0)
		{
                        str = "NO";
	                rowx.add(str);
	                rowx.add("-");					
		}	
		else
		{
	                rowx.add(str);
	                rowx.add(Formater.formatDate(dpStockMnt.getDtExpiredDateExc(),"MMM dd, yyyy"));									
		}			
                rowx.add(PstDpStockManagement.fieldStatus[dpStockMnt.getiDpStatus()]);
                rowx.add(dpStockMnt.getStNote()==null ? "" : dpStockMnt.getStNote());
            }
        }		
		
		
		// jika bukan command EDIT
		else
		{
			rowx.add(""+(offsetStart+i+1));
			if(dpStockMnt.getiDpStatus()==PstDpStockManagement.DP_STS_AKTIF)											
			//if( (dpStockMnt.getiDpStatus()==PstDpStockManagement.DP_STS_AKTIF) && (dpStockMnt.getQtyUsed()==0) )  
			{
				rowx.add("<a href=\"javascript:cmdEdit(\'"+dpStockMnt.getOID()+"')\">"+Formater.formatDate(dpStockMnt.getDtOwningDate(),"MMM dd, yyyy")+"</a>");				
			}
			else
			{
				rowx.add(Formater.formatDate(dpStockMnt.getDtOwningDate(),"MMM dd, yyyy"));
			}
                        //update by satrya 2013-02-24
                        rowx.add(PstDpStockManagement.fieldFlag[dpStockMnt.getFlagStock()]);
                      
			rowx.add(Formater.formatDate(dpStockMnt.getDtExpiredDate(),"MMM dd, yyyy"));
                        
                        float sumExpired = SessLeaveManagement.getTotalExpiredDP(dpStockMnt.getOID());
			rowx.add(""+dpStockMnt.getiDpQty());
			rowx.add(""+dpStockMnt.getQtyUsed());
                        rowx.add(""+sumExpired);
                        float balance = 0;
                        balance = dpStockMnt.getiDpQty() - dpStockMnt.getQtyUsed() - sumExpired;
			rowx.add(""+balance);
			String str = "YES";
			if(dpStockMnt.getiExceptionFlag()==0)
			{
				str = "NO";
				rowx.add(str);
				rowx.add("-");					
			}	
			else
			{
				rowx.add(str);
				rowx.add(dpStockMnt.getDtExpiredDateExc()== null ? "-" : Formater.formatDate(dpStockMnt.getDtExpiredDateExc(),"MMM dd, yyyy"));													
			}
			
			rowx.add(PstDpStockManagement.fieldStatus[dpStockMnt.getiDpStatus()]);
			rowx.add(dpStockMnt.getStNote()==null ? "" : dpStockMnt.getStNote());
        }
		
        lstData.add(rowx);		
        lstLinkData.add(String.valueOf(dpStockMnt.getOID()));
	}
		

	// jika command ADD
	if (oidDp == 0)
	{
		Vector rowadd = new Vector(1,1);		
		if(iCommand == Command.ADD)
		{		
			rowadd.add(""+(listdp.size()+1));
                        //rowadd.add(ControlDate.drawDateWithStyle(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE], new Date(), 1, -5,"formElemen", ""));								
                        //rowadd.add(ControlDate.drawDateWithStyle(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE], new Date(), 1, -5,"formElemen", ""));												
			
                        rowadd.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((new Date()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");		
                        //update by satrya 2013-02-24
                Vector fgStockValue = new Vector(1,1);
                Vector fgStockKey = new Vector(1,1);
                fgStockValue.add(""+PstDpStockManagement.DP_FLAG_EDIT_NO);
                fgStockKey.add("NO");
                fgStockValue.add(""+PstDpStockManagement.DP_FLAG_EDIT_YES);
                fgStockKey.add("YES");
                rowadd.add(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_FLAG_STOCK],null,""+PstDpStockManagement.DP_FLAG_EDIT_YES,fgStockValue,fgStockKey));
                
                        rowadd.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((new Date()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");
                       
                        rowadd.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]+"\" value=\"0\" class=\"elemenForm\" size=\"3\">");				
			rowadd.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]+"\" value=\"0\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"3\">");
                        rowadd.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\"FRM_DP_STOCK_EXPIRED\" value=\""+0+"\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"3\">");                
			rowadd.add("<input type=\"text\" onKeyUp=\"javascript:calqty()\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_RESIDUE]+"\" value=\"0\" class=\"elemenForm\" style=\"background-color:#F5F5F5\" readonly size=\"3\">");
			
			Vector fgValue = new Vector(1,1);
			Vector fgKey = new Vector(1,1);
			fgValue.add("0");
			fgKey.add("NO");
			fgValue.add("1");
			fgKey.add("YES");

			rowadd.add(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXCEPTION_FLAG],null,"0",fgValue,fgKey));
                        //rowadd.add(ControlDate.drawDateWithStyle(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC], new Date(), 1, -5,"formElemen", ""));

                        rowadd.add("</a><input onClick=\"ds_sh(this);\" name="+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((new Date()), "yyyy-MM-dd")+"\"/>"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_mn\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_dy\">"
                         + "<input type=\"hidden\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]+"_yr\">"
                         + "<script language=\"JavaScript\" type=\"text/JavaScript\">getThn();</script><a>");
                        
			Vector stValue = new Vector(1,1);
			Vector stKey = new Vector(1,1);
			for(int d=0; d<PstDpStockManagement.fieldStatus.length; d++)
			{
				if(d==PstDpStockManagement.DP_STS_AKTIF)
				{
					stValue.add(""+d);
					stKey.add(PstDpStockManagement.fieldStatus[d]); 
				}
			}
			rowadd.add(ControlCombo.draw(FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_STATUS],null,"",stValue,stKey));
			//rowadd.add("<input type=\"text\" name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]+"\" value=\"\" class=\"elemenForm\" size=\"20\">");  						
                        rowadd.add("<textarea name=\""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_NOTE]+"\" cols=\"40\" rows=\"3\" class=\"elemenForm\" ></textarea>"); 
		}
		lstData.add(rowadd);
	}
	return ctrlist.drawList();
}    
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int startSummary = FRMQueryString.requestInt(request, "start_summary");
long empOid = FRMQueryString.requestLong(request,""+FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]+"");
long oidDp = FRMQueryString.requestLong(request,"dp_id");
// get and set data from / to session
SrcLeaveManagement srcLeaveManagement = new SrcLeaveManagement();

try
{		  
	if(session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP) != null)    
	{
		srcLeaveManagement = (SrcLeaveManagement)session.getValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP); 			
		session.putValue(SessLeaveManagement.SESS_MANAGEMENT_LEAVE_DP, srcLeaveManagement);			
	}
}
catch(Exception e)
{ 
	System.out.println("Exc when get/set data from/to session");      
}

// konstanta untuk navigasi ke database
String msgString = "";
int recordToGet = 10;
int vectSize = 0;
int iErrCode = 0;

// for manage data dp
CtrlDpStockManagement ctrlDpStockManagement = new CtrlDpStockManagement(request);	
iErrCode = ctrlDpStockManagement.action(iCommand, oidDp);
msgString =  ctrlDpStockManagement.getMessage();
DpStockManagement objDpStockManagement = ctrlDpStockManagement.getDpStockManagement();

// mencari nilai limitStart
//vectSize = SessLeaveManagement.countDetailDpStock(empOid);
vectSize = SessEmployee.countDetailDpStock(empOid,srcLeaveManagement) ;
if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST)
{
	start = ctrlDpStockManagement.actionList(iCommand, start, vectSize, recordToGet);
}	

//Mencari employeeId dan record DP	
Employee emp = new Employee();
Vector vectListDP = new Vector(0,0);
if(empOid!=0)
{
	try
	{
		emp = PstEmployee.fetchExc(empOid);
	}
	catch(Exception e)
	{
		System.out.println("exc when fetch emp : " + e.toString());
	}	
	vectListDP = SessLeaveManagement.listDetailDpStock(empOid, start, recordToGet, srcLeaveManagement);   		
}

I_Leave leaveConfig=null; 
try{
leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());             
}catch(Exception except){
System.out.println("Exception"+except);
}

%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - DP Management</title>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<script language="JavaScript">
<!--
function cmdBackSummary(){
	document.frdp.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frdp.start.value="<%=String.valueOf(startSummary)%>";	
	document.frdp.action="dp.jsp";
	document.frdp.submit();
}

function calqty()
{
    var alqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_DP_QTY]%>.value;
    var usedqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]%>.value;
    var exp = document.frdp.FRM_DP_STOCK_EXPIRED.value;
    if(isNaN(alqty) || alqty=="")
	{
        alqty = 0;
	}
	
    if(isNaN(usedqty) || usedqty=="")
	{
        usedqty = 0;
	}

    
    if(isNaN(exp) || exp=="")
	{
        exp = 0;
	}
        
    if(parseFloat(alqty) - parseFloat(usedqty) < 0)
	{
		//alert('Used quantity should be less or equal with Entitled quantity !!!');
		//usedqty = 0;
		//document.frdp.<%//=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_USED]%>//.value = 0;
	}	
	
    var resqty = document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_QTY_RESIDUE]%>.value = parseFloat(alqty) - parseFloat(usedqty) - parseFloat(exp);
}

function cmdEdit(oid)
{
    document.frdp.command.value="<%=String.valueOf(Command.EDIT)%>";
    document.frdp.dp_id.value=oid;
    document.frdp.action="dp_detail.jsp";
    document.frdp.submit();
}

function cmdSave()
{
    document.frdp.command.value="<%=String.valueOf(Command.SAVE)%>";
    document.frdp.action="dp_detail.jsp";
    document.frdp.submit();
}

function cmdAdd()
{
	document.frdp.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frdp.action="dp_detail.jsp";
        document.frdp.dp_id.value=0;
	document.frdp.submit();
}

function cmdAsk(oidDp)
{
	document.frdp.dp_id.value=oidDp;
	document.frdp.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdConfirmDelete(oidDp)
{
	document.frdp.dp_id.value=oidDp;
	document.frdp.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdCancel(oidDp){
	document.frdp.dp_id.value=oidDp;
	document.frdp.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdBack(){
	document.frdp.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdListFirst(){
	document.frdp.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdListPrev(){
	document.frdp.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdListNext(){
	document.frdp.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}

function cmdListLast(){
	document.frdp.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frdp.action="dp_detail.jsp";
	document.frdp.submit();
}
        function openDPOverlap(oidLeave)
{
     var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?command=<%=(Command.EDIT)%>&<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>="+oidLeave; 
     var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
     newWin.focus();
}
//--------------------------------------------------for calender
 function getThn(){
            var date1 = ""+document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]%>.value;
            var thn = date1.substring(0,4);
            var bln = date1.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date1.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]%>_mn.value=bln;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]%>_dy.value=hri;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_OWNING_DATE]%>_yr.value=thn;
                
            var date2 = ""+document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]%>.value;
            var thn = date2.substring(0,4);
            var bln = date2.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date2.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]%>_mn.value=bln;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]%>_dy.value=hri;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE]%>_yr.value=thn;

            var date3 = ""+document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]%>.value;
            var thn = date3.substring(0,4);
            var bln = date3.substring(5,7);	
            if(bln.charAt(0)=="0"){
                    bln = ""+bln.charAt(1);
            }

            var hri = date3.substring(8,10);
            if(hri.charAt(0)=="0"){
                    hri = ""+hri.charAt(1);
            }

            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]%>_mn.value=bln;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]%>_dy.value=hri;
            document.frdp.<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EXPIRED_DATE_EXC]%>_yr.value=thn;

}


    function hideObjectForDate(){
    }

    function showObjectForDate(){
    } 
//-------------- script control line -------------------
function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
    
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css"> 
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> <!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','/harisma_proj/images/BtnDelOn.jpg')">

<!-- Untuk Calender-->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
    <tr><td id="ds_calclass">
    </td></tr>
</table>
<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
<!-- End Calender-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Leave Application &gt; DP Detail<!-- #EndEditable --> 
                  </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frdp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="start_summary" value="<%=String.valueOf(startSummary)%>">									  
                                      <input type="hidden" name="dp_id" value="<%=String.valueOf(oidDp)%>">
                                      <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_EMPLOYEE_ID]%>" value="<%=String.valueOf(empOid)%>">
                                      <input type="hidden" name="<%=FrmDpStockManagement.fieldNames[FrmDpStockManagement.FRM_FLD_LEAVE_PERIODE_ID]%>" value="0">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <%
										//--- start untuk menampilkan nomor dan nama employee yang sedang di-edit
										if(empOid!=0)
										{
										%>
                                        <tr> 
                                          <td>&nbsp;&nbsp;<b>Payroll &nbsp;: <%=emp.getEmployeeNum().toUpperCase()%></b></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;&nbsp;<b>Name &nbsp; &nbsp;: 
                                            <%=emp.getFullName().toUpperCase()%></b></td>
                                        </tr>
                                        <%
										}
										//--- end untuk menampilkan nomor dan nama employee yang sedang di-edit
										%>
                                        <%
										//--- start untuk menampilkan list data result, baik summary maupun detail per employee 
										String drawList = "";
										if(vectListDP.size()>0  )
										{
											drawList = drawListPerEmployee(start, vectListDP, iCommand, oidDp,leaveConfig);
										}
										
										String strDpData = drawList.trim();
										if(strDpData!=null && strDpData.length()>0)
										{
										%>
                                        <tr> 
                                          <td><%=strDpData%></td>
                                        </tr>
                                        <%
										}
										else if(iCommand == Command.LIST)
										{
										%>
                                        <tr> 
                                          <td><%="<div class=\"msginfo\">&nbsp;&nbsp;No day off payment data found ...</div>"%></td>
                                        </tr>

                                        <%	
										}
										//--- end untuk menampilkan list data result, baik summary maupun detail per employee 
										%>
<tr>
    <td>
        <table>
            <tr>
                <td> <img src="<%=approot%>/images/attention-icon.png" width="25" height="25"></td>
                <td><span class="errfont"><font size="4">Attention : </font></span></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                 <td><span class="errfont"><font size="2"><blink><ul><li>this DP is generated by overtime Form, Editting has to be done with care</li></ul></blink></font></span></td>
            </tr>
        </table>
    </td>
</tr>
                                        <tr> 
                                          <td> 
                                            <% 
										    ControlLine ctrLine = new ControlLine();												
											ctrLine.setLanguage(SESS_LANGUAGE);											
											ctrLine.setLocationImg(approot+"/images");
											ctrLine.initDefault();		
											int listCommand = iCommand;											
											if(iCommand==Command.EDIT && empOid!=0)
											{
												listCommand = Command.LIST;
											}
											out.println(ctrLine.drawImageListLimit(listCommand, vectSize, start, recordToGet));
											%>
                                          </td>
                                       
                                        </tr>
                                                                                 
                                        <%
										if(iCommand==Command.LIST || iCommand==Command.FIRST || iCommand==Command.PREV 
										|| iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK 
										|| (iCommand==Command.SAVE && iErrCode==0) || (iCommand==Command.DELETE && iErrCode==0))
										{										
										%>

                                        <tr> 
                                          <td> 
                                            <table width="30%" border="0" cellpadding="0" cellspacing="0">
                                              <tr> 
											  <%if(privAdd){%>
                                                <td width="29"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image35" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24"></a></td>
                                                <td width="83" valign="top" nowrap class="button">&nbsp; 
                                                  <a href="javascript:cmdAdd()" class="buttonlink">Add 
                                                  New DP</a></td>
												  <%}%>
												<td width="10">&nbsp;</td>   
                                                <td width="29"><a href="javascript:cmdBackSummary()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image36" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24"></a></td>
                                                <td width="143" valign="top" nowrap class="button">&nbsp; 
                                                  <a href="javascript:cmdBackSummary()" class="buttonlink">Back 
                                                  To Summary DP</a></td>												  
                                              </tr>
                                            </table>   
                                          </td>
                                        </tr>

                                        <%
										}
										else
										{
										%>
                                        <tr> 
                                          <td> 
                                            <%
											if(empOid!=0)
											{
												ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												ctrLine.setTableWidth("60%");
												String scomDel = "javascript:cmdAsk('"+oidDp+"')";
												String sconDelCom = "javascript:cmdConfirmDelete('"+oidDp+"')";
												String scancel = "javascript:cmdEdit('"+oidDp+"')";
												ctrLine.setBackCaption("Back to List");
												ctrLine.setCommandStyle("buttonlink");
												ctrLine.setBackCaption("Back to List DP");
												ctrLine.setSaveCaption("Save DP");
												ctrLine.setConfirmDelCaption("Yes Delete DP");
												ctrLine.setDeleteCaption("Delete DP");
												ctrLine.setAddCaption("");
			
												if(privDelete)
												{
													ctrLine.setConfirmDelCommand(sconDelCom);
													ctrLine.setDeleteCommand(scomDel);
													ctrLine.setEditCommand(scancel);
												}
												else
												{ 
													ctrLine.setConfirmDelCaption("");
													ctrLine.setDeleteCaption("");
													ctrLine.setEditCaption("");
												}
			
												if(privAdd==false && privUpdate==false)
												{
													ctrLine.setSaveCaption("");
												}
			
												if(iCommand==Command.ASK)
												{
													ctrLine.setDeleteQuestion("Are you sure to delete this DP ?");//msgString);
												}
												
												// jika record DP sudah ada yg di-taken, maka tdk boleh dihapus
												if(objDpStockManagement.getQtyUsed() > 0)
												{
													ctrLine.setDeleteCaption("");
												}
												
												out.println(ctrLine.drawImage(iCommand, iErrCode, msgString));	
											}
											%>
                                          </td>
                                        </tr>
										<%
										}
										%>
										
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
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
      <SCRIPT>
function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("blink")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT>
</body>

<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
