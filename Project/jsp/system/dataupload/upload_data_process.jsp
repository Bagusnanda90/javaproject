    <%@page import="com.dimata.system.entity.dataupload.PstDataUploadMain"%>
<%@page import="com.dimata.system.entity.dataupload.DataUploadMain"%>
<%@page import="com.dimata.system.session.dataupload.SessDataUpload"%>
<%@page import="com.dimata.system.entity.dataupload.PstDataUploadDetail"%>
<%@page import="com.dimata.system.entity.dataupload.DataUploadDetail"%>
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
	String oidDetail ="";
	int iCommand =  FRMQueryString.requestInt(request,"command");
	iCommand = Command.SAVE;
	Vector vectPic = new Vector(1,1);
        Vector vectDoc = new Vector(1,1);
        
 try{
	 vectPic = ((Vector)session.getValue("SELECTED_PHOTO_SESSION"));
 }catch(Exception e) {
 }
 oidDetail = (String)vectPic.get(0);
 
 long longOidDetail = 0;
 //long oidBlobEmpPicture = 0;
 try{
    longOidDetail = Long.parseLong(oidDetail);
 }catch(Exception ex){}
 
 DataUploadDetail fetchDetail = new DataUploadDetail();
 Employee fetchEmployee = new Employee();
 try{
 	fetchDetail = PstDataUploadDetail.fetchExc(longOidDetail);
 }catch(Exception e){
 	System.out.println("err. fect Data Detail"+e.toString());
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
				 DataUploadDetail dataUploadDetail = PstDataUploadDetail.getObjDocPicture(longOidDetail);
				 //objEmpRelevantDoc.setEmployeeId(longOidEmp);
				 //get EmployeeNUmber
				 // --- start generate photo peserta ---
				 SessDataUpload objSessDataUpload = new SessDataUpload();			 			 				 
				 String pathFileName = objSessDataUpload.getAbsoluteFileName(fileName);
				 try{
				 	//DataUploadDetail dataUploadDetail2 = new DataUploadDetail();
					PstDataUploadDetail.updateFileName(fileName,longOidDetail);
					System.out.println("update sukses.."+fileName);
				 }catch(Exception e){
				 	System.out.println("err update.."+e.toString())	;
				 }
				 java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);		 
				 uploader.writeCache(byteIns, pathFileName, true);		 
				 // --- end generate photo peserta ---
				 
				 // --- start proses simpan hasil tulis gambar ke vector
				 vectResult.add(""+longOidDetail);
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