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
 
 /*if(longOidEmp==0){
  // insert new 
  EmpPicture empPicture = new EmpPicture();
  empPicture.setEmployeeId(longOidEmp);
  oidBlobEmpPicture=PstEmpPicture.insertExc(empPicture);
 }
 
// SessPictCatCompPict sessPictCatCompPict = new SessPictCatCompPict();
 SessEmployeePicture objSessEmployeePicture = new SessEmployeePicture();
 ImageLoader uploader = new ImageLoader();

 try{
	 uploader.uploadImage(config, request, response); 
	 Object obj = uploader.getImage("pict");
	 System.out.println("obj");
	 if(obj != null) { 
		 //objSessEmployeePicture.updateImage(obj, Long.parseLong(pictCatCompOid));
		 System.out.println("nilaiObj................"+obj);
	 }else{ 
		 System.out.println("OBJ NULL................"); 
	 } 
 }catch(Exception e) {
 }
 
 if(iCommand==Command.POST){
 //	SessPictCatCompPict.deleteImage(lnPictOid);
 }
 else{
	 Thread th = new Thread();
	 th.sleep(2000);
 }*/

Vector vectResult = new Vector(1,1);
try
{
	 ImageLoader uploader = new ImageLoader();
	 int numFiles = uploader.uploadImage(config, request, response); 
	 	try
		{
			 // get object of specified location identified by form at previous location (path)
			 String fieldFormName = "pict";
			 Object obj = uploader.getImage(fieldFormName);
			 System.out.println("obj..."+obj);
			 // casting object to its 'byte' format and generate file used it at specified location and specified name
			 byte[] byteOfObj = (byte[]) obj;
			 int intByteOfObjLength = byteOfObj.length;
			 if(intByteOfObjLength > 0)
			 { 
		
				 // --- start generate record peserta photo ---
				 // ngambil data pesertaJkj
				 //Employee objEmployee = (Employee) vectPeserta.get(i);
				 
				 // insert / update data peserta jkj photo
				 long oidBlobEmpPicture = 0;			 
				 EmpPicture objEmpPicture = PstEmpPicture.getObjEmpPicture(longOidEmp);
				 objEmpPicture.setEmployeeId(longOidEmp);
				 			 
				 if(objEmpPicture.getOID() > 0)
				 {
				 	System.out.println("masuk update...")	 ;
					oidBlobEmpPicture = PstEmpPicture.updateExc(objEmpPicture);
				 }
				 else
				 {
				 	System.out.println("masuk insert...")	 ;	
					oidBlobEmpPicture = PstEmpPicture.insertExc(objEmpPicture);    		 
				 }
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
				 java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
				 uploader.writeCache(byteIns, pathFileName, true);		 
				 // --- end generate photo peserta ---
				 // --- start proses simpan hasil tulis gambar ke vector
				 vectResult.add(""+longOidEmp);
				 // --- end proses simpan hasil tulis gambar ke vector			 			 
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exc1 when upload image : " + e.toString());
		}
	 //}
}
catch(Exception e) 
{
	System.out.println("Exc2 when upload image : " + e.toString());
}

 %>
<%
    	response.sendRedirect("picture.jsp?command="+Command.EDIT+"&employee_oid="+oidEmployee);
%>
