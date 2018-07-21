

<%@page import="com.dimata.system.session.dataupload.SessDataUpload"%>
<%@page import="com.dimata.util.blob.ImageLoader"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.util.Command"%>
<!DOCTYPE html>
<%@include file="../../main/javainit.jsp" %>

<%
    int iCommand = FRMQueryString.requestInt(request,"command");
    long oid = FRMQueryString.requestLong(request, "FRM_FIELD_OID");
    String modul = FRMQueryString.requestString(request, "modul");
    long empId = FRMQueryString.requestLong(request, "empId");
    
    String tabName = "";
    if(modul.equals("award")){
        tabName = "tab_award";
    } else if (modul.equals("reprimand")){
        tabName = "tab_reprimand";
    } else if (modul.equals("reldoc")){
        tabName = "tab_relevant";
    } else if (modul.equals("career")){
        tabName = "tab_cpath";
    }
    
    if(iCommand == 0){
      
            try {
            ImageLoader uploader = new ImageLoader();
            int numFiles = uploader.uploadImage(config, request, response);

            //System.out.println("oid di proses upload image : "+oidmaterial);
            String fieldFormName = "FRM_DOC";
            Object obj = uploader.getImage(fieldFormName);

            String fileName = uploader.getFileName();

            byte[] byteOfObj = (byte[]) obj;
            int intByteOfObjLength = byteOfObj.length;
            SessDataUpload objSessDataUpload = new SessDataUpload();	
            if (intByteOfObjLength > 0) {
                String pathFileName = objSessDataUpload.getAbsoluteFileName(fileName);
                try{
                        if(modul.equals("award")){
                            PstEmpAward.updateFileName(fileName,oid);
                        } else if(modul.equals("reprimand")){
                            PstEmpReprimand.updateFileName(fileName,oid);
                        } else if (modul.equals("reldoc")){
                            PstEmpRelevantDoc.updateFileName(fileName, oid);
                        } else if (modul.equals("career")){
                            PstCareerPath.updateFileName(fileName, oid);;
                        }
                        
                        System.out.println("update sukses.."+fileName);
                 }catch(Exception e){
                        System.out.println("err update.."+e.toString())	;
                 }
                java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
                uploader.writeCache(byteIns, pathFileName, true);
                try {
                    //PROSES UPDATE

                } catch (Exception eY) {
                    System.out.print("");
                }

            }
            
        } catch(Exception ex){
        
        }
        }  
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uploading</title>
    </head>
    <body>
        <script>
            window.location="<%=approot%>/employee/databank/employee_edit.jsp?oid=<%=empId%>#<%=tabName%>";
        </script>
    </body>
</html>
