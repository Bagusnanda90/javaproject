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
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import="com.dimata.qdep.form.*" %>

<% 
	String oidTrainingMaterial ="";
	String oidTrainingFile = "";
	int iCommand =  FRMQueryString.requestInt(request,"command");
	iCommand = Command.SAVE;
	Vector vectPic = new Vector(1,1);
 try{
	 vectPic = ((Vector)session.getValue("SELECTED_PHOTO_SESSION"));
 }catch(Exception e) {
 }
 oidTrainingMaterial = (String)vectPic.get(0);
 oidTrainingFile = (String)vectPic.get(1);
 
 long longOidDoc = 0;
 long longOidTrainingFile = 0;
 try{
  longOidDoc = Long.parseLong(oidTrainingMaterial);
  longOidTrainingFile= Long.parseLong(oidTrainingFile);
  
 }catch(Exception ex){}
 TrainingFile training = new TrainingFile();
 if(longOidTrainingFile == 0){
	  //save data ke tabel training material
	 try{
		PstTrainingFile.insertExc(training);	
	 }catch(Exception e){
		System.out.println("err insert trainig material.."+e.toString());
	 }
	 longOidTrainingFile  = training.getOID();
 }

Vector vectResult = new Vector(1,1);
try
{
	 ImageLoader uploader = new ImageLoader();
	 int numFiles = uploader.uploadImage(config, request, response); 
	  // ambil nama file
		String fileName = uploader.getFileName();
	 //-------------------------------------------------	
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
				 // --- start generate photo peserta ---
				 SessTrainingMaterial objSessTrainingMaterial = new SessTrainingMaterial();			 			 				 
				 String pathFileName = objSessTrainingMaterial.getAbsoluteFileName(fileName);
				 try{
				 	training.setTrainingId(longOidDoc);
				 	training.setOID(longOidTrainingFile);
				 	training.setFileName(fileName);
					PstTrainingFile.updateExc(training);
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
		response.sendRedirect("training.jsp?command="+Command.EDIT+"&hidden_training_id="+longOidDoc);
 %>
 		
<script language="JavaScript">
	<%if(iCommand==Command.SAVE){%>
		window.close();
	<%}%>
</script>