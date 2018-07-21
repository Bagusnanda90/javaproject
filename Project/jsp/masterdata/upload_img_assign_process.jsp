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
<%@ include file = "../main/javainit.jsp" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.util.blob.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import="com.dimata.qdep.form.*" %>


 <% 
	String strOidImageAssign ="";
	int iCommand =  FRMQueryString.requestInt(request,"command");
	
	Vector vectPic = new Vector(1,1);
 try{
	 vectPic = ((Vector)session.getValue("SELECTED_IMAGE_ASSIGN_SESSION"));
 }catch(Exception e) {
 }
 strOidImageAssign = (String)vectPic.get(0);
 
 long oidImageAssign = 0;
 //long oidBlobImageAssign = 0;
 try{
  oidImageAssign = Long.parseLong(strOidImageAssign);
 }catch(Exception ex){}
 
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
				 long oidBlobImageAssign = 0;			 
				 ImageAssign objImageAssign = PstImageAssign.fetchExc(oidImageAssign);
				 objImageAssign.setOID(oidImageAssign);
                                 
                                 // --- end generate record peserta photo ---			 			 
				 //get EmployeeNUmber
				 String empNum = "";
				 if(oidImageAssign != 0){
					Employee employee = new Employee();
					try{
						 employee = PstEmployee.fetchExc(objImageAssign.getEmployeeOid());
						 empNum = employee.getEmployeeNum();
						 //empNum = ""+employee.getOID();
					}catch(Exception exc){
						 employee = new Employee();
					}
				}
                                 
                                 // --- start generate photo peserta ---
				 SessImageAssign objSessImageAssign = new SessImageAssign();			 			 				 
				 String pathFileName = objSessImageAssign.getAbsoluteFileName(empNum);
				 String pathFileNameOnly = objSessImageAssign.getRealFileName(empNum);
				 java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
				 uploader.writeCache(byteIns, pathFileName, true);		 
				 // --- end generate photo peserta ---
				 	
                                 objImageAssign.setPath(pathFileNameOnly); 
				 if(objImageAssign.getOID() > 0)
				 {
				 	System.out.println("masuk update...")	 ;
					oidBlobImageAssign = PstImageAssign.updateExc(objImageAssign);
				 }
				 else
				 {
				 	System.out.println("masuk insert...")	 ;	
					oidBlobImageAssign = PstImageAssign.insertExc(objImageAssign);    		 
				 }
				 
			
				 
				 // --- start proses simpan hasil tulis gambar ke vector
				 vectResult.add(""+oidImageAssign);
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
    	response.sendRedirect("image_assign.jsp?command="+Command.EDIT+"&"+FrmImageAssign.fieldNames[FrmImageAssign.FRM_FIELD_IMG_ASSIGN_ID]+"="+strOidImageAssign);
%>
