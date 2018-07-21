<% 
/* 
 * Page Name  		:  savepict_pictcatcomp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ include file = "../../main/javainit.jsp" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.util.blob.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import="com.dimata.qdep.form.*" %>


 <% 
	String oidEmployee ="";
	int iCommand =  FRMQueryString.requestInt(request,"command");
	
	Vector vectPic = new Vector(1,1);
 try{
	 vectPic = ((Vector)session.getValue("SELECTED_PHOTO_SESSION"));
 }catch(Exception e) {
 }
 oidEmployee = (String)vectPic.get(0);
 
 long longOidEmp = 0;
 //long oidBlobEmpPicture = 0;
 try{
  longOidEmp = Long.parseLong(oidEmployee);
 }catch(Exception ex){}


Vector vectResult = new Vector(1,1);
try
{
	 ImageLoader uploader = new ImageLoader();
	 	try
		{
				 // --- end generate record peserta photo ---			 			 
				 //get EmployeeNUmber
				 String empNum = "";
				 if(longOidEmp != 0){
					Employee employee = new Employee();
					try{
						 employee = PstEmployee.fetchExc(longOidEmp);
						 empNum = employee.getEmployeeNum();
					}catch(Exception exc){
						 employee = new Employee();
					}
				}
				 // --- start generate photo peserta ---
				 SessEmployeePicture objSessEmployeePicture = new SessEmployeePicture();			 			 				 
				 String pathFileName = objSessEmployeePicture.getAbsoluteFileName(empNum);
				 uploader.deleteChace(pathFileName);		 
				 vectResult.add(""+longOidEmp);
				 // --- end proses simpan hasil tulis gambar ke vector			 			 
			 }
		
		catch(Exception e)
		{
			System.out.println("Exc1 when delete image : " + e.toString());
		}
	 //}
}
catch(Exception e) 
{
	System.out.println("Exc2 when upload image : " + e.toString());
}

 %>
	
 <% 
 		
 
 		response.sendRedirect("picture.jsp?employee_oid="+oidEmployee);
		System.out.println("iCommand....setelah save"+iCommand);
	    // response.sendRedirect("picture.jsp?command="+Command.NONE+"&employeeId="+oidEmployee);
 %>
