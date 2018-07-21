<%@ page language="java" %>
<%@ page import = "java.util.*" %>

<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%
privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- JSP Block -->
<%!
public Ovt_Employee cekDatabase(Vector listOvt_Employee, long employee_num, String duration_employee)
{
	Ovt_Employee ovt_Employee = new Ovt_Employee();
	if(listOvt_Employee.size()>0 && listOvt_Employee!=null)
	{
		for(int l=0;l<listOvt_Employee.size();l++)
		{
			ovt_Employee = (Ovt_Employee)listOvt_Employee.get(l);
			long num_Employee = Long.parseLong(ovt_Employee.getEmployee_num());
			String strCek = String.valueOf(employee_num)+"_"+duration_employee;
			String strCekDtbase = String.valueOf(num_Employee)+"_"+String.valueOf(ovt_Employee.getDuration());
			
			if(strCek.equals(strCekDtbase)){
				return ovt_Employee;
			}else{
				ovt_Employee = new Ovt_Employee();
			}
		}
	}
	return ovt_Employee;
}


public String drawList(int offset, int iCommand, FrmOvt_Employee frmObject, Ovt_Employee objEntity, Vector objectClass, long idOvt_Employee, long idPeriod, int fromDate, int toDate, String overtime_Code, HttpServletRequest request){
	String result = "";
	
	//untuk mendapatkan jumlah index di overtime index
	String sWhereOver = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+overtime_Code+"'";
	Vector vListOver = PstOvt_Idx.list(0,0,sWhereOver,"");
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Pay Nr.","5%","2","0","center","left");
 	ctrlist.dataFormat("Name","10%","2","0","center","left");
	ctrlist.dataFormat("Position","5%","2","0","center","left");
	ctrlist.dataFormat("Work Date","10%","2","0","center","left");
	ctrlist.dataFormat("Schedule","3%","2","0","center","left");
	
	ctrlist.dataFormat("OverTime","30%","0","6","center","left");
	if(vListOver!=null && vListOver.size()>0){
	    for(int i =0; i<vListOver.size();i++){
			Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(i);
			String sJudul = "Idx "+String.valueOf(over_Idx.getOvt_idx());
			int k = 1;
			k = k + 1;
			int indexnya = 20 / k;
			String sIdx = String.valueOf(indexnya)+"%";
			ctrlist.dataFormat(sJudul,sIdx,"2","0","center","left");
		}
	}
	ctrlist.dataFormat("Total Idx","7%","2","0","center","left");
	ctrlist.dataFormat("Approve","5%","2","0","center","left");
	
	ctrlist.dataFormat("Start Date","5%","0","0","center","left");
	ctrlist.dataFormat("Time","6%","0","0","center","left");
	ctrlist.dataFormat("End Date","5%","0","0","center","left");
	ctrlist.dataFormat("Time","6%","0","0","center","left");
	ctrlist.dataFormat("Duration","5%","0","0","center","left");
	ctrlist.dataFormat("Doc Nr.","5%","0","0","center","left");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	
	int l = 0;
	
	if(objectClass!=null && objectClass.size()>0){
		for(int k=0; k<objectClass.size(); k++){
			Vector temp = (Vector)objectClass.get(k);
			
			Employee employee = (Employee)temp.get(0);
			Position position = (Position)temp.get(1);
			Ovt_Employee objOvt_Employee = (Ovt_Employee)temp.get(2);
			double jumlah = 0.0;
			String strChecked = "";
			
			if(idOvt_Employee == objOvt_Employee.getOID()){
			  index = k;
			}
		
			
			if((index==k) && (iCommand==Command.EDIT || iCommand==Command.ASK)){
			    l = l + 1;
				rowx.add(String.valueOf(l));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_EMPLOYEE_NUM]+"\" value=\""+objOvt_Employee.getEmployee_num()+"\" class=\"formElemen\" size=\"10\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_PERIOD_ID] +"\" value=\""+idPeriod+"\" size=\"5\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_CODE] +"\" value=\""+overtime_Code+"\" size=\"5\" class=\"elemenForm\">");
				rowx.add(employee.getFullName());
				rowx.add(position.getPosition());
				//rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE], objOvt_Employee.getWorkDate() != null ? objOvt_Employee.getWorkDate() : new Date(),"formElemen", 1, -5));
				rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objOvt_Employee.getWorkDate() == null) ? new Date() : objOvt_Employee.getWorkDate(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
					"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_yr\">"+
					"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_STD_WORK_SCHDL]+"\" value=\""+objOvt_Employee.getWork_schedule()+"\" class=\"formElemen\" size=\"10\">");
				//rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], objOvt_Employee.getOvt_Start() != null ? objOvt_Employee.getOvt_Start() : new Date(),"formElemen", 1, -5));
			     rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objEntity.getOvt_Start() == null) ? new Date() : objOvt_Employee.getOvt_Start(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
					"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_yr\">"+
					"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
				rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], objOvt_Employee.getOvt_Start() != null ? objOvt_Employee.getOvt_Start() : new Date(),"formElemen", 24, 1, 0));
				//rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], objOvt_Employee.getOvt_End() != null ? objOvt_Employee.getOvt_End() : new Date(),"formElemen", 1, -5));
				rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objEntity.getOvt_End() == null) ? new Date() : objOvt_Employee.getOvt_End(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
					"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_yr\">"+
					"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
				rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], objOvt_Employee.getOvt_End() != null ? objOvt_Employee.getOvt_End() : new Date(),"formElemen", 24, 1, 0));
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_DURATION] +"\" value=\""+objOvt_Employee.getDuration()+"\" size=\"4\" class=\"elemenForm\">");
				rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_DOC_NR] +"\" value=\""+objOvt_Employee.getOvt_doc_nr()+"\" size=\"4\" class=\"elemenForm\">");
				if(vListOver!=null && vListOver.size()>0){
					for(int r =0; r<vListOver.size();r++){
						rowx.add("");
					}
				}
				rowx.add(String.valueOf(jumlah));
				
				if(objOvt_Employee.getStatus()==2)
						strChecked = "checked";
						
				rowx.add("<input type=\"checkbox\""+strChecked+" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_STATUS] +"\" value=\"2\">");
				System.out.println("strChecked:::::::::::::::::::"+strChecked);
								
			}else{
			    l = l + 1;
				String ovt_employee_Start = Formater.formatDate(objOvt_Employee.getOvt_Start(), "HH:mm");
				String ovt_employee_End = Formater.formatDate(objOvt_Employee.getOvt_End(), "HH:mm");

				rowx.add(String.valueOf(l));
				rowx.add("<a href=\"javascript:cmdEdit('"+String.valueOf(objOvt_Employee.getOID())+"')\">"+objOvt_Employee.getEmployee_num()+"</a>");
				rowx.add(employee.getFullName());
				rowx.add(position.getPosition());
				rowx.add(Formater.formatDate(objOvt_Employee.getWorkDate(), "dd-MM-yyyy"));
				rowx.add(objOvt_Employee.getWork_schedule());
				rowx.add(Formater.formatDate(objOvt_Employee.getOvt_Start(), "dd-MM-yyyy"));
				rowx.add(ovt_employee_Start);
				rowx.add(Formater.formatDate(objOvt_Employee.getOvt_End(), "dd-MM-yyyy"));
				rowx.add(ovt_employee_End);
				rowx.add(String.valueOf(objOvt_Employee.getDuration()));
				rowx.add(objOvt_Employee.getOvt_doc_nr());
				
			
				/***perhitungan untuk duration index*/
			if(vListOver!=null && vListOver.size()>0){
					String durationStr = String.valueOf(objOvt_Employee.getDuration());
					double durationDbl = Double.parseDouble(durationStr);
					double total_idx = 0.0;
					double tot_Idx = 0.0;
					
					String durationStrHourX = durationStr.substring(0,durationStr.indexOf("."));
					String durationStrMinX = "";
					String index_pay = "";
					durationStrMinX = durationStr.substring(durationStr.indexOf("."),durationStr.length());
					
					double iDurationMinDbl = Double.parseDouble(String.valueOf(0)+durationStrMinX);
					String striDurationMin = String.valueOf(iDurationMinDbl);
					double iDurationDbl = iDurationMinDbl * 100;
					String iDurationDblStrX = String.valueOf(iDurationDbl);
					String iDurationDblStr = iDurationDblStrX.substring(0,iDurationDblStrX.indexOf("."));
					
					long iDurationMin = Long.parseLong(iDurationDblStr);
					long iDurationHour = Long.parseLong(durationStrHourX);

						for(int r =0; r<vListOver.size();r++){
						    Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(r);
							double index_ov = over_Idx.getOvt_idx();
							double hourTo = over_Idx.getHour_to();
							String HourStrX = String.valueOf(hourTo);
							String strHourMax = HourStrX.substring(0,HourStrX.indexOf("."));
							long hourMaxIdx = Long.parseLong(strHourMax);
							
							double hourFrom = over_Idx.getHour_from();
							double pay_index = 0.0;
							
							System.out.println("durationDbl:::::::::::::::::::::::::::::::::::::::::;;"+durationDbl);
							if((durationDbl>0 && iDurationMin>0)  || (durationDbl>0 && iDurationMin==0)) 
									{
									
										if((iDurationHour>=hourFrom) && (iDurationHour<hourTo))
										{
											index_pay = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
											pay_index = Double.parseDouble(index_pay);
											//total_idx = pay_index * index_ov;
											if(pay_index > 0)
												total_idx = pay_index * index_ov;
											else
												total_idx = 0.0 * index_ov;
											jumlah = jumlah + total_idx;
											durationDbl = durationDbl - iDurationHour;
											iDurationHour = iDurationHour - hourMaxIdx;
											//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
											iDurationMin = 0;
											if(pay_index > 0)
												rowx.add(index_pay);
											else
												rowx.add("0.0");
										}
										else
										{
											if(iDurationHour<hourTo)
											{
												index_pay = iDurationHour+"."+iDurationMin;
												//Setelah durationDbl sudah sama dengan 0, iDurationMin diset = 0
												iDurationMin = 0;
												//iDurationHour=0;
												//System.out.println("salah nich yach::::::::::::::::::::::::;;");
											}
											else if(iDurationHour==0)
											{
												index_pay = "0."+iDurationMin;
											}
											else{
												index_pay = String.valueOf(hourTo);
												System.out.println("salah nich yach::::::::::::::::::::::::;;");
											}
											durationDbl = durationDbl - hourTo;
											String strDuration = String.valueOf(durationDbl);
											long durationLong = Long.parseLong(strDuration.substring(0,strDuration.indexOf(".")));
											iDurationHour = durationLong;
											pay_index = Double.parseDouble(index_pay);
											if(pay_index > 0)
												total_idx = pay_index * index_ov;
											else
												total_idx = 0.0 * index_ov;
												
											jumlah = jumlah + total_idx;
											System.out.println("total_idx:::::::::::::::::;"+total_idx);
											if(pay_index > 0)
												rowx.add(index_pay);
											else
												rowx.add("0.0");
											System.out.println("index_pay:::::::::::::::::;"+index_pay);
										}
									}
									else
									{
										//untuk mengecek setelah pengurangan Hournya = 0, jika telah habis dikurangi maka melakukan pengecekan terhadap iDurationHournya  
										if(iDurationMin>0)
										{
											  index_pay = "0."+iDurationMin;
											  pay_index = Double.parseDouble(index_pay);
											  //total_idx = pay_index * index_ov;
											  if(pay_index > 0)
												total_idx = pay_index * index_ov;
											  else
												total_idx = 0.0 * index_ov;
											  jumlah = jumlah + total_idx;
											  System.out.println("total_idx:::::::::::::::::;"+total_idx);
											  //jika masih lebih besar dari nol maka di set iDurationMin = 0
											  iDurationMin = 0;
										}
										else
										{
											index_pay = String.valueOf(0.0);
											pay_index = Double.parseDouble(index_pay);
											//total_idx = pay_index * index_ov;
											if(pay_index > 0)
												total_idx = pay_index * index_ov;
											else
												total_idx = 0.0 * index_ov;
											jumlah = jumlah + total_idx;
											System.out.println("total_idx:::::::::::::::::;"+total_idx);
										}
										if(pay_index > 0)
											rowx.add(index_pay);
										else
											rowx.add("0.0");
									}
							}
				}//tutup perhitungan
				
			
				if(iCommand==Command.LIST || iCommand == Command.SAVE || iCommand==Command.ADD || iCommand==Command.EDIT)
				{
					 if(objOvt_Employee.getStatus()==2)
						strChecked = "checked";
				}
						
				rowx.add(String.valueOf(jumlah));
				rowx.add("<input type=\"checkbox\""+strChecked+" name=\"employee_oid_\" value=\"\">");
			}
			
			System.out.println("jumlah::::::::::::::::::::::::::::::::::::::::::::::::"+jumlah);
			lstData.add(rowx);
			
			if(k==objectClass.size()-1)
			{
				break;
			}else{
				rowx = new Vector();
		 	}
		}
		rowx = new Vector();
		
		Ovt_Employee objOvtEmployee = new Ovt_Employee();
		Ovt_Employee objOvtEmployeeX = new Ovt_Employee();
		double jumlahRecord = 0.0;
		double durationRecord = 0;
		if(idOvt_Employee!=0)
			{
				try{
					objOvtEmployee = PstOvt_Employee.fetchExc(idOvt_Employee);
				}catch(Exception e){;}
			}
			durationRecord = objOvtEmployee.getDuration();
			jumlahRecord = PstOvt_Employee.hitTotDuration(vListOver, durationRecord);
		
		int p = objectClass.size();
		String employee_num = "";
		long oidEmployeeX = 0;
		long position_name = 0;
		String strChecked = "";
		if(iCommand==Command.LIST || iCommand==Command.ADD)
		{
		    p = p + 1;
			rowx.add(String.valueOf(p));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_EMPLOYEE_NUM] +"\" value=\""+employee_num+"\" size=\"5\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_PERIOD_ID] +"\" value=\""+idPeriod+"\" size=\"5\" class=\"elemenForm\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_CODE] +"\" value=\""+overtime_Code+"\" size=\"5\" class=\"elemenForm\"><a href=\"javascript:chkEmployee()\" class=\"command\"><img border=\"0\" src=\"../../images/BtnSearch.jpg\" width=\"24\" height=\"24\"></a> ");
			rowx.add("<a id=\"nama\"></a>");
			rowx.add("<a id=\"position\"></a>");
			rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objEntity.getWorkDate() == null) ? new Date() : objEntity.getWorkDate(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
				"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_WORK_DATE] + "_yr\">"+
				"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_STD_WORK_SCHDL] +"\" value=\""+objEntity.getWork_schedule()+"\" size=\"5\" class=\"elemenForm\">");
			//rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], objEntity.getOvt_Start() != null ? objEntity.getOvt_Start() : new Date(),"formElemen", 1, -5));
			rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objEntity.getOvt_Start() == null) ? new Date() : objEntity.getOvt_Start(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
				"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_yr\">"+
				"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
			
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], objEntity.getOvt_Start() != null ? objEntity.getOvt_Start() : new Date(),"formElemen", 24, 1, 0));
			//rowx.add(ControlDate.drawDate(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], objEntity.getOvt_End() != null ? objEntity.getOvt_End() : new Date(),"formElemen", 1, -5));
			rowx.add("<input onClick=\"ds_sh(this);\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END]+"\" readonly=\"readonly\" style=\"cursor: text\" value=\""+Formater.formatDate((objEntity.getOvt_End() == null) ? new Date() : objEntity.getOvt_End(), "dd-MM-yyyy")+"\" class=\"formElemen\" size=\"10\">"+
				"<input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_mn\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_dy\"><input type=\"hidden\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END] + "_yr\">"+
				"<script language=\"JavaScript\" type=\"text/JavaScript\">getThn1();</script>");
			rowx.add(ControlDate.drawTime(frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], objEntity.getOvt_End() != null ? objEntity.getOvt_End() : new Date(),"formElemen", 24, 1, 0));
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_DURATION] +"\" value=\""+objEntity.getDuration()+"\" size=\"4\" class=\"elemenForm\">");
			rowx.add("<input type=\"text\" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_DOC_NR] +"\" value=\""+objEntity.getOvt_doc_nr()+"\" size=\"4\" class=\"elemenForm\">");
			if(vListOver!=null && vListOver.size()>0){
					for(int r =0; r<vListOver.size();r++){
						rowx.add("");
					}
			}
			rowx.add("");
			rowx.add("<input type=\"checkbox\""+strChecked+" name=\""+frmObject.fieldNames[FrmOvt_Employee.FRM_FIELD_STATUS] +"\" value=\"2\">");
		}
		
		if(iCommand==Command.SAVE)
		{
		int statusX = 1;
		if(objOvtEmployee.getStatus()==0)
			objOvtEmployee.setStatus(statusX);
			
			try{
				objOvtEmployeeX.setOID(idOvt_Employee);
				objOvtEmployeeX.setPeriodId(objOvtEmployee.getPeriodId());
				objOvtEmployeeX.setEmployee_num(objOvtEmployee.getEmployee_num());
				objOvtEmployeeX.setWork_schedule(objOvtEmployee.getWork_schedule());
				objOvtEmployeeX.setWorkDate(objOvtEmployee.getWorkDate());
				objOvtEmployeeX.setOvt_Start(objOvtEmployee.getOvt_Start());
				objOvtEmployeeX.setOvt_End(objOvtEmployee.getOvt_End());
				objOvtEmployeeX.setDuration(objOvtEmployee.getDuration());
				objOvtEmployeeX.setOvt_doc_nr(objOvtEmployee.getOvt_doc_nr());
				objOvtEmployeeX.setStatus(objOvtEmployee.getStatus());
				objOvtEmployeeX.setPay_slip_id(objOvtEmployee.getPay_slip_id());
				objOvtEmployeeX.setOvt_code(objOvtEmployee.getOvt_code());
				objOvtEmployeeX.setTot_Idx(jumlahRecord);
				
				PstOvt_Employee.updateExc(objOvtEmployeeX);
			}catch(Exception e){;}
		}
		
		lstData.add(rowx);
		result = ctrlist.drawMeList();
	}else{
		 result = "<i>Belum ada data dalam sistem ...</i>";
		
	}
	return result;
}

public String drawListTransfer(int offset, int iCommand, FrmOvt_Employee frmObject, Ovt_Employee objEntity, Hashtable has, Vector objectClass, long idOvt_Employee, Date dtPeriodStart, Date dtPeriodEnd, String overtime_Code, Hashtable hasCb, long idPeriod){
    
	//untuk mendapatkan jumlah index di overtime index
	String sWhereOver = PstOvt_Idx.fieldNames[PstOvt_Idx.FLD_OVT_TYPE_CODE]+" = '"+overtime_Code+"'";
	Vector vListOver = PstOvt_Idx.list(0,0,sWhereOver,"");
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.dataFormat("No.","3%","2","0","center","left");
	ctrlist.dataFormat("Pay Nr.","5%","2","0","center","left");
 	ctrlist.dataFormat("Name","20%","2","0","center","left");
	ctrlist.dataFormat("Position","10%","2","0","center","left");
	ctrlist.dataFormat("Work Date","10%","2","0","center","left");
	ctrlist.dataFormat("Schedule","5%","2","0","center","left");
	ctrlist.dataFormat("OverTime","42%","0","6","center","left");
	if(vListOver!=null && vListOver.size()>0){
	    for(int i =0; i<vListOver.size();i++){
			Ovt_Idx over_Idx = (Ovt_Idx)vListOver.get(i);
			String sJudul = "Idx "+String.valueOf(over_Idx.getOvt_idx());
			int k = 1;
			k = k + 1;
			int indexnya = 20 / k;
			String sIdx = String.valueOf(indexnya)+"%";
			ctrlist.dataFormat(sJudul,sIdx,"2","0","center","left");
		}
	}
	ctrlist.dataFormat("Total Index","5%","2","0","center","left");
	ctrlist.dataFormat("Approve","5%","2","0","center","left");

	ctrlist.dataFormat("Start Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("End Date","7%","0","0","center","left");
	ctrlist.dataFormat("Time","7%","0","0","center","left");
	ctrlist.dataFormat("Duration","7%","0","0","center","left");
	ctrlist.dataFormat("OV-Form-Nr","7%","0","0","center","left");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	Vector rowx1 = new Vector(1,1);
	int index = -1;
	
	int yearStart = dtPeriodStart.getYear() + 1900;
	int monthStart = dtPeriodStart.getMonth() + 1;
	int dateStart = dtPeriodStart.getDate();
	int monthEnd = dtPeriodEnd.getMonth() + 1;
	GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart-1, dateStart);
	int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	Vector list = new Vector();
	Vector listCb = new Vector();
	String payNum = "";
	String actualTime = "";
	int l = 0;
	if(objectClass!=null)
	{
		for(int k=0;k<objectClass.size();k++)
		{
			rowx = new Vector(1,1);
			Vector temp = (Vector)objectClass.get(k);
			Employee employee = (Employee)temp.get(0);
			int durasi = nDayOfMonthStart;
			
			for(int w =0; w<durasi; w++){
				try{
						list = (Vector)has.get(""+employee.getOID()+"_"+w);
					}catch(Exception e){
						System.out.println("err >>>> : "+e.toString());
					}
					
					String strChecked = "";
					String overtime_nr = "";
					Ovt_Employee objOvt_EmployeeX = new Ovt_Employee();
					if(list!=null){
					    l = l + 1;
						payNum = (String)list.get(0);
						String docNr = (String)list.get(1);
						String nameEmployee = (String)list.get(2);
						String positition = (String)list.get(3);
						String work_date = (String)list.get(4);
						String scheduleSymbol = (String)list.get(5);
						String dateTime = (String)list.get(6);
						String timeOut1 = (String)list.get(7);
						String endTime = (String)list.get(8);
						actualTime = (String)list.get(9);
						long iDurationHour = Long.parseLong((String)list.get(10));
						long iDurationMin = Long.parseLong((String)list.get(11));
						double jumlah_idx = Double.parseDouble((String)list.get(12));
						
						String sWhereClause = PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_PERIOD_ID]+" = "+idPeriod+" AND "+
											  PstOvt_Employee.fieldNames[PstOvt_Employee.FLD_OVT_CODE]+" = '"+overtime_Code+"'";
						Vector vListCekOvt_Import = PstOvt_Employee.list(0,0,sWhereClause,"");
						
						String strDuration = String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin);
						objOvt_EmployeeX = cekDatabase(vListCekOvt_Import,employee.getOID(),strDuration);
						
						/* System.out.println("objOvt_EmployeeX.getOID():::::::::::::::::::::::::::::::::::;;;"+objOvt_EmployeeX.getOID());
						 Ovt_Employee objOvt_EmployeeXDel = new Ovt_Employee();
						 objOvt_EmployeeX = cekDatabaseHps(vListCekOvt_Import,objOvt_EmployeeX.getOID());*/
						if(iCommand==Command.SUBMIT)
						{
							 if(objOvt_EmployeeX.getStatus()==2)
							 	strChecked = "checked";
						}
						 Ovt_Employee ovt_Employee = new Ovt_Employee();
						 
						if(iCommand==Command.APPROVE)
						{
							//menampilkan oidOvtEmployee yang sudah dipilih
							objOvt_EmployeeX = new Ovt_Employee();
							objOvt_EmployeeX = cekDatabase(vListCekOvt_Import,employee.getOID(),strDuration);
							
							try{
									listCb = (Vector)hasCb.get(""+employee.getOID()+"_"+w);
								}catch(Exception e){
								System.out.println("err >>>> : "+e.toString());
								}
									
									if(listCb!=null)
									{
									    String payNumCb = (String)listCb.get(0);
										String docNrCb = (String)listCb.get(1);
										int statusCb = Integer.parseInt((String)listCb.get(2));
										String work_dateCb = (String)listCb.get(3);
										String date_TimeCb = (String)listCb.get(4);
										String time_outCb = (String)listCb.get(5);
										String endTimeCb = (String)listCb.get(6);
										String actualTimeCb = (String)listCb.get(7);
										String symbolCb = (String)listCb.get(8);
										long iDurationHourCb = Long.parseLong((String)listCb.get(9));
										long iDurationMinCb = Long.parseLong((String)listCb.get(10));
										
										String ovt_start = date_TimeCb+" "+time_outCb;
										String ovt_end = endTimeCb+" "+actualTimeCb;
										Date dateOvt_start = Formater.formatDate(ovt_start, "yyyy-MM-dd HH:mm");
										Date dateOvt_end = Formater.formatDate(ovt_end, "yyyy-MM-dd HH:mm");
										Date work_DateCb = Formater.formatDate(work_dateCb, "yyyy-MM-dd");
										String strDurationAll = String.valueOf(iDurationHourCb)+"."+String.valueOf(iDurationMinCb);
										double durationAll = Double.parseDouble(strDurationAll);
										double tot_Idx = Double.parseDouble((String)listCb.get(11));
										
										if(statusCb==2)
											strChecked = "checked";
										String cekEmployeeDtBase = objOvt_EmployeeX.getEmployee_num()+"_"+String.valueOf(objOvt_EmployeeX.getDuration());
										String cekDatabase = payNumCb+"_"+strDurationAll;
										
												if(cekEmployeeDtBase.equals(cekDatabase))
												{
													ovt_Employee.setOID(objOvt_EmployeeX.getOID());
													ovt_Employee.setPeriodId(idPeriod);
													ovt_Employee.setEmployee_num(payNumCb);
													ovt_Employee.setWorkDate(work_DateCb);
													ovt_Employee.setWork_schedule(symbolCb);
													ovt_Employee.setOvt_Start(dateOvt_start);
													ovt_Employee.setOvt_End(dateOvt_end);
													ovt_Employee.setDuration(durationAll);
													ovt_Employee.setOvt_doc_nr(docNrCb);
													ovt_Employee.setStatus(statusCb);
													ovt_Employee.setOvt_code(overtime_Code);
													ovt_Employee.setTot_Idx(tot_Idx);
													try{
														PstOvt_Employee.updateExc(ovt_Employee);
													}catch(Exception e){System.out.println("ERR"+e.toString());}
												}
												else
												{
													ovt_Employee.setPeriodId(idPeriod);
													ovt_Employee.setEmployee_num(payNumCb);
													ovt_Employee.setWorkDate(work_DateCb);
													ovt_Employee.setWork_schedule(symbolCb);
													ovt_Employee.setOvt_Start(dateOvt_start);
													ovt_Employee.setOvt_End(dateOvt_end);
													ovt_Employee.setDuration(durationAll);
													ovt_Employee.setOvt_doc_nr(docNrCb);
													ovt_Employee.setStatus(statusCb);
													ovt_Employee.setOvt_code(overtime_Code);
													ovt_Employee.setTot_Idx(tot_Idx);
													try{
														PstOvt_Employee.insertExc(ovt_Employee);
													}catch(Exception e){System.out.println("ERR"+e.toString());}
												}
									}
						}						
						
						rowx.add(String.valueOf(l));
						rowx.add(payNum+"<input type=\"hidden\" name=\"employee_num_"+k+""+w+"\" value=\""+employee.getEmployeeNum()+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(nameEmployee);
						rowx.add(positition);
						rowx.add(work_date+"<input type=\"hidden\" name=\"work_date_"+k+""+w+"\" value=\""+work_date+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(scheduleSymbol+"<input type=\"hidden\" name=\"simbol_"+k+""+w+"\" value=\""+scheduleSymbol+"\" size=\"25\" class=\"elemenForm\">");
						
						rowx.add(dateTime+"<input type=\"hidden\" name=\"dateTime_"+k+""+w+"\" value=\""+dateTime+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(timeOut1+"<input type=\"hidden\" name=\"timeOut_"+k+""+w+"\" value=\""+timeOut1+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(endTime+"<input type=\"hidden\" name=\"dateTime_1_"+k+""+w+"\" value=\""+dateTime+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(actualTime+"<input type=\"hidden\" name=\"actualTime_"+k+""+w+"\" value=\""+actualTime+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(String.valueOf(iDurationHour)+"."+String.valueOf(iDurationMin)+"<input type=\"hidden\" name=\"iDurationHour_"+k+""+w+"\" value=\""+iDurationHour+"\" size=\"25\" class=\"elemenForm\"><input type=\"hidden\" name=\"iDurationMin_"+k+""+w+"\" value=\""+iDurationMin+"\" size=\"25\" class=\"elemenForm\">");
						rowx.add(docNr+"<input type=\"hidden\" name=\"overtime_nr_"+k+""+w+"\" value=\""+docNr+"\" class=\"formElemen\">");
						if(vListOver!=null && vListOver.size()>0)
						{
						  for(int x =13; x<=vListOver.size()+12;x++)
						  {
							String index_pay = (String)list.get(x);
							rowx.add("<input type=\"text\" name=\"indexPay_"+k+""+w+""+x+"\" value=\""+index_pay+"\" class=\"formElemen\" size=\"2\" readonly=\"true\">");
						  }
						}
						
						
						rowx.add("<input type=\"text\" name=\"jumlahIdx_"+k+""+w+"\" value=\""+jumlah_idx+"\" class=\"formElemen\" size=\"2\" readonly=\"true\">");
						rowx.add("<input type=\"checkbox\""+strChecked+" name=\"employee_oid_"+k+""+w+"\" value=\""+employee.getOID()+"\">");
						lstData.add(rowx);
						System.out.println("durasi:::::::::::::::::::::::::::;"+durasi);	
						if(w==durasi-1)
						{
							break;
						}else{
							rowx = new Vector();
					 }
				 }//tutupnya if list!=null
				
			}
		}
	}
	//result = ctrlist.drawMeList();
	//return result;
	return ctrlist.drawMeList();
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidOvt_Employee = FRMQueryString.requestLong(request, "hidden_ovt_Employee_id");
long oidDivision = FRMQueryString.requestLong(request,"division");
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
String cariName = FRMQueryString.requestString(request,"cariName");
String payrollNum = FRMQueryString.requestString(request,"payrollNum");
int ovtStatus = FRMQueryString.requestInt(request,"ovtStatus");
int ovtStatus1 = FRMQueryString.requestInt(request,"ovtStatus1");
int ovtStatus2 = FRMQueryString.requestInt(request,"ovtStatus2");
long oidPeriod = FRMQueryString.requestLong(request,"period");
int dateFrom = FRMQueryString.requestInt(request, "dateFrom");
int dateTo = FRMQueryString.requestInt(request, "dateTo");
String code_overtime = FRMQueryString.requestString(request,"ovt_type");
long oidEmployeeX = FRMQueryString.requestLong(request, "hidden_id_employee");
String dateMontStart = FRMQueryString.requestString(request, ""+FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_mn_start");
String dateDayStart = FRMQueryString.requestString(request, ""+FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_dy_start");
String dateYearStart = FRMQueryString.requestString(request, ""+FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START] + "_yr_start");

Date dateStartX = FRMQueryString.requestDate(request, ""+FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START]+"");



//long oidEmployeeX = FRMQueryString.requestLong(request, "hidden_id_employee");
/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;


CtrlOvt_Employee ctrlOvt_Employee = new CtrlOvt_Employee(request);
Vector listOvt_EmployeeInpt = new Vector(1,1);

/*switch statement */
iErrCode = ctrlOvt_Employee.action(iCommand , oidOvt_Employee, request);
/* end switch*/
FrmOvt_Employee frmOvt_Employee = ctrlOvt_Employee.getForm();
Ovt_Employee ovt_Employee = ctrlOvt_Employee.getOvt_Employee();
msgString =  ctrlOvt_Employee.getMessage();

if(iCommand == Command.SAVE)
{
	oidOvt_Employee = ovt_Employee.getOID();
	
}

/* get record to display */
if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD)
{
	if(ovtStatus!=0 || ovtStatus1!=0 || ovtStatus2!=0)
	{
		listOvt_EmployeeInpt = SessEmployee.listOvt_Empolyee(oidDepartment,oidDivision,oidSection,cariName,payrollNum,code_overtime,oidPeriod, dateFrom, dateTo, ovtStatus, ovtStatus1, ovtStatus2);
	}
}


Hashtable has = new Hashtable();
HttpSession sessOvertime = request.getSession(true);  
Vector temp = new Vector();
Vector listEmployeeCb = new Vector();
HttpSession sessListEmployee = request.getSession(true); 
Date datePeriodStart = new Date();
Date datePeriodEnd = new Date();
String code_Overtime = "";
String strOidPeriode = "";
long oidPeriode = 0;

Hashtable hasCb = new Hashtable();

if(iCommand==Command.SUBMIT || iCommand==Command.APPROVE)
{
	has = (Hashtable)sessOvertime.getValue("employee_transfer");
	temp = (Vector)sessListEmployee.getValue("list_employee_import");
	
	datePeriodStart = (Date)temp.get(0);
	datePeriodEnd = (Date)temp.get(1);
	listEmployeeCb = (Vector)temp.get(2);
	code_Overtime = (String)temp.get(3);
	strOidPeriode = (String)temp.get(4);
	oidPeriode = Long.parseLong(strOidPeriode);
}

int yearStart = datePeriodStart.getYear() + 1900;
int monthStart = datePeriodStart.getMonth() + 1;
int dateStart = datePeriodStart.getDate();
int monthEnd = datePeriodEnd.getMonth() + 1;
GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart-1, dateStart);
int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

String strChecked = "";

if(iCommand==Command.APPROVE)
{
	if(listEmployeeCb!=null && listEmployeeCb.size()>0)	
	{
			Vector list = new Vector();
			Vector listCb = new Vector();
			for(int k=0;k<listEmployeeCb.size();k++)
			{
				Vector tempX = (Vector)listEmployeeCb.get(k);
				Employee employee = (Employee)tempX.get(0);
				int durasi = nDayOfMonthStart;
				for(int r=0;r<durasi;r++)
				{
				
					try{
						list = (Vector)has.get(""+employee.getOID()+"_"+r);
					}catch(Exception e){
						System.out.println("err >>>> : "+e.toString());
					}
					
					if(list!=null)
					{
						 long oidEmployee = FRMQueryString.requestLong(request, "employee_oid_"+k+""+r+""); 
						 
						 Vector listSave = new Vector();
						 String overtime_Nr = "";
						 String employee_num = "";
						 int status = 0;
						 if(oidEmployee!=0)
						 {
						 	status = 2;	
						 }
						 else
						 {
						 	status = 1;
						 }
						 
							employee_num = FRMQueryString.requestString(request, "employee_num_"+k+""+r+"");
							listSave.add(employee_num);
						
							overtime_Nr = FRMQueryString.requestString(request, "overtime_nr_"+k+""+r+""); 
							listSave.add(overtime_Nr);
							
							listSave.add(String.valueOf(status));
							
							String work_Date = FRMQueryString.requestString(request, "work_date_"+k+""+r+""); 
							listSave.add(work_Date);
							
							String dateTime = FRMQueryString.requestString(request, "dateTime_"+k+""+r+""); 
							listSave.add(dateTime);
							
							String timeOut1 = FRMQueryString.requestString(request, "timeOut_"+k+""+r+""); 
							listSave.add(timeOut1);
							
							String endTime = FRMQueryString.requestString(request, "dateTime_1_"+k+""+r+""); 
							listSave.add(endTime);
							
							String actualTime = FRMQueryString.requestString(request, "actualTime_"+k+""+r+""); 
							listSave.add(actualTime);
							
							String symbol = FRMQueryString.requestString(request, "simbol_"+k+""+r+""); 
							listSave.add(symbol);
							
							long iDurationHour1 = FRMQueryString.requestLong(request, "iDurationHour_"+k+""+r+"");
							listSave.add(String.valueOf(iDurationHour1));
					
							long iDurationMin = FRMQueryString.requestLong(request, "iDurationMin_"+k+""+r+"");
							listSave.add(String.valueOf(iDurationMin));
							
							double tot_Idx = FRMQueryString.requestDouble(request, "jumlahIdx_"+k+""+r+"");
							listSave.add(String.valueOf(tot_Idx));
							
							hasCb.put(String.valueOf(employee.getOID()+"_"+r),listSave);
						
						System.out.println("Has1111111111111111111111111 =>>>>>>>> : "+hasCb);
					}
				}
			}
		}
}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/calendar.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>

function getThn1()
{

	var date  = ""+document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_WORK_DATE]%>.value;
	var date1 = ""+document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_START]%>.value;
	var date2 = ""+document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_END]%>.value;
	
	var thn = date.substring(6,10);
	var bln = date.substring(3,5);	
	if(bln.charAt(0)=="0"){
		bln = ""+bln.charAt(1);
	}
	
	var hri = date.substring(0,2);
	if(hri.charAt(0)=="0"){
		hri = ""+hri.charAt(1);
	}
	
	var thn1 = date1.substring(6,10);
	var bln1 = date1.substring(3,5);	
	if(bln1.charAt(0)=="0"){
		bln1 = ""+bln1.charAt(1);
	}
	
	var hri1 = date1.substring(0,2);
	if(hri1.charAt(0)=="0"){
		hri1 = ""+hri1.charAt(1);
	}
	
	var thn2 = date2.substring(6,10);
	var bln2 = date2.substring(3,5);	
	if(bln2.charAt(0)=="0"){
		bln2 = ""+bln2.charAt(1);
	}
	
	var hri2 = date2.substring(0,2);
	if(hri2.charAt(0)=="0"){
		hri2 = ""+hri2.charAt(1);
	}
	
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_WORK_DATE] + "_mn"%>.value=bln;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_WORK_DATE] + "_dy"%>.value=hri;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_WORK_DATE] + "_yr"%>.value=thn;
	
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_START] + "_mn"%>.value=bln1;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_START] + "_dy"%>.value=hri1;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_START] + "_yr"%>.value=thn1;
	
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_END] + "_mn"%>.value=bln2;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_END] + "_dy"%>.value=hri2;
	document.frm_ovt_input.<%=frmOvt_Employee.fieldNames[frmOvt_Employee.FRM_FIELD_OVT_END] + "_yr"%>.value=thn2;		
		
				
}


function chkEmployee()
{
	emp_division = document.frm_ovt_input.division.value;
	emp_department = document.frm_ovt_input.department.value;
	emp_section = document.frm_ovt_input.section.value;
	emp_name = document.frm_ovt_input.cariName.value;
	emp_number = document.frm_ovt_input.payrollNum.value;
    
    window.open("empsearch.jsp?emp_division=" + emp_division + "&emp_department=" + emp_department + "&emp_section=" + emp_section + "&emp_name=" + emp_name +"&emp_number=" + emp_number);
}

function cmdSearch(){
	document.frm_ovt_input.command.value="<%=Command.LIST%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}

function cmdSave(){
	document.frm_ovt_input.command.value="<%=Command.SAVE%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}

function cmdEdit(oidOvt_Employee){
	document.frm_ovt_input.hidden_ovt_Employee_id.value=oidOvt_Employee;
	document.frm_ovt_input.command.value="<%=Command.EDIT%>";
	document.frm_ovt_input.prev_command.value="<%=prevCommand%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}

function cmdBack(){
	document.frm_ovt_input.command.value="<%=Command.BACK%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}

function cmdAdd(){
	document.frm_ovt_input.hidden_ovt_Employee_id.value="0";
	document.frm_ovt_input.command.value="<%=Command.ADD%>";
	document.frm_ovt_input.prev_command.value="<%=prevCommand%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}

function cmdApprove(){
	document.frm_ovt_input.command.value="<%=Command.APPROVE%>";
	document.frm_ovt_input.action="ov-input.jsp";
	document.frm_ovt_input.submit();
}


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
	
	function showObjectForMenu(){
        
    }
	
	function hideObjectForDate(){
  	}
	
	function showObjectForDate(){
	}
</SCRIPT>


<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
		 
      <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
        <tr>
          <td id="ds_calclass"> </td>
        </tr>
      </table>
		<script language=JavaScript src="<%=approot%>/main/calendar.js"></script>
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Input 
                  Overtime<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" -->
								   
									
                                    <form name="frm_ovt_input" method="post" action="">
									    <input type="hidden" name="command" value="">
										<input type="hidden" name="start" value="<%=start%>">
										<input type="hidden" name="hidden_ovt_Employee_id" value="<%=oidOvt_Employee%>">
										 <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td height="13" colspan="6"> <table width="100%" border="0">
                                              <tr> 
                                                <td height="24" width="10%" align="left">Division 
                                                  :</td>
                                                <td height="24" width="29%"> <%
													  Vector listDivision = PstDivision.list(0, 0, "", "DIVISION");										  
													  Vector divValue = new Vector(1,1);
													  Vector divKey = new Vector(1,1);
													  divValue.add("0");
                                                      divKey.add("select ..."); 
													  for(int d=0;d<listDivision.size();d++)
													  {
														Division division = (Division)listDivision.get(d);
														divValue.add(""+division.getOID());
														divKey.add(division.getDivision());										  
													  }
													  out.println(ControlCombo.draw("division",null,""+oidDivision,divValue,divKey));
                                                        %> <%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID],"formElemen",null, ""+srcEmployee.getDivisionId(), divValue, divKey, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                </td>
                                                <td height="24" width="4%" nowrap align="left">Department 
                                                  : </td>
                                                <td height="24" width="21%" nowrap> 
                                                  <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("0");
                                                            dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
														out.println(ControlCombo.draw("department",null,""+oidDepartment,dept_value,dept_key));

                                                        %> <%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmployee.getDepartment(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                </td>
                                                <td height="24" width="16%" nowrap align="left">Section 
                                                  : </td>
                                                <td height="24" width="7%" nowrap> 
                                                  <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1); 
															sec_value.add("0");
                                                            sec_key.add("select ...");
                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
															Vector listSec = PstSection.list(0, 0, "", " SECTION ");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
															out.println(ControlCombo.draw("section",null,""+oidSection,sec_value,sec_key));

															%> <%//= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null, "" + srcEmployee.getSection(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                </td>
                                                <td width="13%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="24" align="left">Employee 
                                                  Name:</td>
                                                <td height="24"><input type="text" name="cariName"  value="<%=cariName%>" class="elemenForm" size="30" ></td>
                                                <td height="24" nowrap align="left">Payroll 
                                                  Nr. : </td>
                                                <td height="24" nowrap> <input type="text" name="payrollNum"  value="<%=payrollNum%>" class="elemenForm" onkeydown="javascript:fnTrapKD()"></td>
                                                <td height="24" nowrap align="left">&nbsp;</td>
                                                <td height="24" nowrap>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="24" align="left">Overtime 
                                                  status :</td>
                                                <td height="24"> 
												 <input type="checkbox" name="ovtStatus" value="1" checked>
                                                  New/Draft 
                                                  <input type="checkbox" name="ovtStatus1" value="2" checked>
                                                  Approve 
                                                  <input type="checkbox" name="ovtStatus2" value="3" checked>
                                                  Posted </td>
                                                <td height="24" nowrap align="left">&nbsp;</td>
                                                <td height="24" nowrap>&nbsp;</td>
                                                <td height="24" nowrap align="left">&nbsp;</td>
                                                <td height="24" nowrap>&nbsp;</td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td height="24" align="left">Period 
                                                  :</td>
                                                <td height="24"> <%
													  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");										  
													  Vector periodValue = new Vector(1,1);
													  Vector periodKey = new Vector(1,1);
													 
													  for(int d=0;d<listPeriod.size();d++)
													  {
														Period period = (Period)listPeriod.get(d);
														periodValue.add(""+period.getOID());
														periodKey.add(""+period.getPeriod());										  
													  }
													  out.println(ControlCombo.draw("period",null,""+oidPeriod,periodValue,periodKey));
                                               %> 
                                                </td>
                                                <td height="24" nowrap align="left">Date 
                                                  from : </td>
                                                <td height="24" nowrap><input type="text" name="dateFrom" value = <%=dateFrom%> size="12">
                                                  to : 
                                                  <input type="text" name="dateTo" value =<%=dateTo%> size="12"> 
                                                </td>
                                                <td height="24" nowrap align="left">Overtime 
                                                  Code :</td>
                                                <td height="24" nowrap> <% 
														Vector ovt_type_value = new Vector(1,1);
														Vector ovt_type_key = new Vector(1,1); 
														Vector listOvt_Type = PstOvt_Type.list(0, 0, "", "");
														for (int i = 0; i < listOvt_Type.size(); i++) {
																Ovt_Type ovt = (Ovt_Type)listOvt_Type.get(i);
																ovt_type_key.add(ovt.getOvt_Type_Code());
																ovt_type_value.add(ovt.getOvt_Type_Code());
														}
														out.println(ControlCombo.draw("ovt_type",null,""+code_overtime,ovt_type_value,ovt_type_key));

														%> </td>
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td width="2%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a> 
                                            <img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                          <td colspan="5" valign="top"> <a href="javascript:cmdSearch()">Search 
                                            for Employee</a> </td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="4">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="6" class="listtitle"> <table width="100%" border="0">
                                              <%if(iCommand==Command.LIST || iCommand==Command.SAVE || iCommand == Command.ADD || iCommand==Command.EDIT){
											  %>
											  <tr> 
                                                <td   width="100%" colspan="6" height="8"><%=drawList(start, iCommand,frmOvt_Employee, ovt_Employee, listOvt_EmployeeInpt, oidOvt_Employee, oidPeriod, dateFrom, dateTo, code_overtime, request)%></td>
                                              </tr>
                                              <%}
										  else
										  {
										     if(iCommand==Command.SUBMIT || iCommand==Command.APPROVE)
											 {
										  %>
                                              <tr> 
                                                <td height="8" width="17%" class="comment"><%=drawListTransfer(start,iCommand,frmOvt_Employee,ovt_Employee,has,listEmployeeCb,oidOvt_Employee, datePeriodStart, datePeriodEnd, code_Overtime, hasCb, oidPeriode)%> </td>
                                              </tr>
                                              <%
										 	 }
										 }%>
                                            </table></td>
                                        </tr>
                                        <%
										if(iCommand == Command.APPROVE || iCommand == Command.SUBMIT)
										{
										%>
                                        <tr> 
                                          <td width="2%"> <a href="javascript:cmdApprove()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                          <td colspan="5"><a href="javascript:cmdApprove()" class="command">Save 
                                            All</a></td>
                                        </tr>
                                        <%
										}
										%>
                                        <%
										if(iCommand == Command.LIST || iCommand == Command.ADD || iCommand==Command.EDIT)
										{
											if(listOvt_EmployeeInpt.size()>0)
											{
										%>
                                        <tr> 
                                          <td width="2%"> <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a></td>
                                          <td colspan="5"><a href="javascript:cmdSave()" class="command">Save 
                                            All</a></td>
                                        </tr>
                                        <%
											}
										}
										%>
                                        <%
										if(iCommand==Command.SAVE)
										{
										%>
                                        <tr> 
                                          <td width="2%" height="27"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                          <td colspan="5"><a href="javascript:cmdAdd()" class="command">Add 
                                            New </a> </td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
