<%@page contentType="text/html"%>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>

<%!
public String drawList(Vector objectClass)
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","2%");
	ctrlist.addHeader("Owning Date","6%");
    ctrlist.addHeader("Expired Date","6%");
	ctrlist.addHeader("Qty","3%");
	//ctrlist.addHeader("Used","3%");
	//ctrlist.addHeader("Residue","3%");
	ctrlist.addHeader("Notes","13%");	
		
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.reset();

	String strOwningDate = "";
	for (int i = 0; i < objectClass.size(); i++) 
	{
		DpStockManagement objDpStockManagement = (DpStockManagement) objectClass.get(i);		
		
		strOwningDate = Formater.formatDate(objDpStockManagement.getDtOwningDate(),"MMM dd, yyyy");
		
		Vector rowx = new Vector();
		rowx.add(String.valueOf(i+1));
		rowx.add(strOwningDate);
		rowx.add(Formater.formatDate(objDpStockManagement.getDtExpiredDate(),"MMM dd, yyyy"));
		rowx.add(String.valueOf(objDpStockManagement.getiDpQty()));
		//rowx.add(String.valueOf(objDpStockManagement.getQtyUsed()));
		//rowx.add(String.valueOf(objDpStockManagement.getQtyResidue()));
		rowx.add(objDpStockManagement.getStNote());

		lstData.add(rowx);
		lstLinkData.add(String.valueOf(objDpStockManagement.getOID()) + "','" + strOwningDate + "','" + objDpStockManagement.getStNote());
	}

	ctrlist.setLinkSufix("')");
	return ctrlist.draw();
}
%>

<% 
long emp_oid = Long.parseLong(request.getParameter("emp_oid"));
int takenDateYr = FRMQueryString.requestInt(request, "taken_yr");
int takenDateMn = FRMQueryString.requestInt(request, "taken_mn");
int takenDateDy = FRMQueryString.requestInt(request, "taken_dy");
Date takenDate = new Date(takenDateYr-1900, takenDateMn-1, takenDateDy);
System.out.println("takenDate : " + takenDate);

// mencari stock DP terakhir yang diambil oleh dokumen DP Application
long oidDpStockOnLastDpAppDoc = 0;
int usedQtyOfThisDPStock = 0;
int dpQtyReserved = 0;
String whLastDpStockUsed = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + emp_oid + 
						   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID] + " <> 0" + 
						   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + " = " + PstDpApplication.FLD_DOC_STATUS_VALID;
String ordLastDpStockUsed = PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + " DESC";
Vector vectDpAppLast = PstDpApplication.list(0, 0, whLastDpStockUsed, ordLastDpStockUsed);
if(vectDpAppLast!=null && vectDpAppLast.size()>0)
{
	DpApplication objDpApplication = (DpApplication) vectDpAppLast.get(0);
	oidDpStockOnLastDpAppDoc = objDpApplication.getDpId();
	
	String whCls = PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID] + "=" + oidDpStockOnLastDpAppDoc + 
				   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + " = " + PstDpApplication.FLD_DOC_STATUS_VALID;
	if(oidDpStockOnLastDpAppDoc!=0)
	{
		usedQtyOfThisDPStock = PstDpApplication.getCount(whCls);
	}	
	
	String whClsNull = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + " = " + emp_oid +
					   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DP_ID] + "=0" + 
				       " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_DOC_STATUS] + " = " + PstDpApplication.FLD_DOC_STATUS_INCOMPLATE + 
					   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + " > \"" + Formater.formatDate(objDpApplication.getTakenDate(),"yyyy-MM-dd") + "\"" + 
					   " AND " + PstDpApplication.fieldNames[PstDpApplication.FLD_TAKEN_DATE] + " < \"" + Formater.formatDate(takenDate,"yyyy-MM-dd") + "\""; 					   
	dpQtyReserved = PstDpApplication.getCount(whClsNull);				   
}


// mencari list DP Stock yang bisa diambil
// hanya satu DP Stock yang akan ditampilkan karena memakai metode FIFO
String whereClause = "";
String orderClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE];
if (emp_oid != 0) 
{
	whereClause = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID] + 
				  " = " + emp_oid + 
				  " AND " + PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_DP_STATUS] + 
				  " IN (" + PstDpStockManagement.DP_STS_AKTIF + 
				  "," + PstDpStockManagement.DP_STS_TAKEN + 
				  "," + PstDpStockManagement.DP_STS_EXPIRED + ")";
}
Vector vectTemp = PstDpStockManagement.list(0, 0, whereClause, orderClause);
Vector listDpTemp = new Vector(1,1);
if(vectTemp!=null && vectTemp.size()>0)
{
	// masukkan masing-masing object DpStockManagement ke vector dengan ketentuan, jika QtyDP lebih dari 1, 
	// maka akan dibuatkan object yang sejenis sejumlah QtyDP tersebut 
	int maxTemp = vectTemp.size();
	boolean sameDpOidHasFound = false;
	for(int i=0; i<maxTemp; i++)
	{
		DpStockManagement objDpStockManagement = (DpStockManagement) vectTemp.get(i);		
		if( (oidDpStockOnLastDpAppDoc == 0) || (oidDpStockOnLastDpAppDoc == objDpStockManagement.getOID()) || sameDpOidHasFound)
		{
			if(objDpStockManagement.getiDpQty() > 0)
			{			
				sameDpOidHasFound = true;
				int dpQty = objDpStockManagement.getiDpQty();
				for(int j=0; j<dpQty; j++)
				{
					objDpStockManagement.setiDpQty(1);
					listDpTemp.add(objDpStockManagement);
				}
			}
		}			
	}
	
	// perhitungan utk mendapatkan DP Stock yang seharusnya harus diambil (FIFO)	
	if(listDpTemp!=null && listDpTemp.size()>0)
	{
		int lstDpSize = listDpTemp.size();
		int maxRemoveTop = ((usedQtyOfThisDPStock + dpQtyReserved) > lstDpSize) ? lstDpSize : (usedQtyOfThisDPStock + dpQtyReserved);		
		for(int it=0; it<maxRemoveTop; it++)
		{
			listDpTemp.remove(0);
		}		

		if(listDpTemp!=null && listDpTemp.size()>1)
		{
			int maxRemoveBottom = listDpTemp.size() - 1;
			for(int itr=0; itr<maxRemoveBottom; itr++)
			{
				int iRem = listDpTemp.size()-1;
				listDpTemp.remove(iRem);
			}			
		}
	}
}
%>
<html>
<head><title>Search Employee</title>
<script language="javascript">
function cmdEdit(oid, dpowningdate, dpnotes) 
{
	self.opener.document.frm_dp_application.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_DP_ID]%>.value = oid;
	self.opener.document.frm_dp_application.DP_OWNING_DATE.value = dpowningdate;	
	self.opener.document.frm_dp_application.DP_NOTES.value = dpnotes;
	self.close();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FCFDEC" >
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong>DP 
                  Stock List </strong></font></td>
              </tr>
              <tr> 
                <td> 
				  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
					<td valign="top"> 
					  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
					  	<%
					    if(listDpTemp!=null && listDpTemp.size()>0)
						{
						%>
						<tr> 
						  <td colspan="3" valign="top"><%=drawList(listDpTemp)%></td>
						</tr>
						<%
						}
						else
						{
						%>
						<tr> 
						  <td colspan="3" valign="top">&nbsp;</td>
						</tr>						
						<tr> 
						  <td valign="top">&nbsp;</td>
						  <td valign="top"><div class=msginfo>No DP Stock data found ...</div></td>						  
						  <td valign="top">&nbsp;</td>
						</tr>
						<tr> 
						  <td colspan="3" valign="top">&nbsp;</td>
						</tr>												
						<%
						}
						%>
					  </table>
					</td>
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
</table>
</body>
</html>
