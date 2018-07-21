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
	long longOidDoc =0;
	int iCommand =  FRMQueryString.requestInt(request,"command");
	iCommand = Command.SAVE;
        
 try{
	 longOidDoc = ((Long)session.getValue("SELECTED_PHOTO_SESSION"));
         
 }catch(Exception e) {
 }
 
 EmpDoc fetch = new EmpDoc();
 try{
 	fetch = PstEmpDoc.fetchExc(longOidDoc);	
 }catch(Exception e){
 	System.out.println("err. fect EpDocumnet"+e.toString());
 }
 
Vector vectResult = new Vector(1,1);
try
{
	 ImageLoader uploader = new ImageLoader();
	 int numFiles = uploader.uploadImage(config, request, response); 
	 String fileName = uploader.getFileName();
	 System.out.println("fileName real..."+fileName);
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
				 long oidBlobEmpPicture = 0;
                                 		 
				 EmpDoc objEmpDoc = new EmpDoc();
                                 try{
                                     objEmpDoc = PstEmpDoc.fetchExc(longOidDoc);
                                 } catch (Exception e){} 
				 //objEmpDoc.setEmployeeId(longOidEmp);
				 //get EmployeeNUmber
				 // --- start generate photo peserta ---
				 SessEmpRelevantDoc objSessEmpDoc = new SessEmpRelevantDoc();			 			 				 
				 String pathFileName = objSessEmpDoc.getAbsoluteFileName(fileName);
				 try{
				 	EmpDoc empDoc = new EmpDoc();
					PstEmpDoc.updateFileName(fileName,longOidDoc);
					System.out.println("update sukses.."+fileName);
				 }catch(Exception e){
				 	System.out.println("err update.."+e.toString())	;
				 }
				 java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);		 
				 uploader.writeCache(byteIns, pathFileName, true);		 
				 // --- end generate photo peserta ---
				 
				 // --- start proses simpan hasil tulis gambar ke vector
				 vectResult.add(""+longOidDoc);
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
<script language="JavaScript">
	<%if(iCommand==Command.SAVE){%>
		window.close();
	<%}%>
</script>