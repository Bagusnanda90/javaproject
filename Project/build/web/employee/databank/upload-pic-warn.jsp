<%-- 
    Document   : upload-pic-warn
    Created on : Mar 21, 2016, 9:15:37 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.util.blob.ImageLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
try
{
        ImageLoader uploader = new ImageLoader();
        int numFiles = uploader.uploadImage(config, request, response);
        String fileName = uploader.getFileName();
        System.out.println("fileName real..." + fileName);
        try {
            // get object of specified location identified by form at previous location (path)
            String fieldFormName = "pict";
            Object obj = uploader.getImage(fieldFormName);
            System.out.println("obj..." + obj);
            // casting object to its 'byte' format and generate file used it at specified location and specified name
            byte[] byteOfObj = (byte[]) obj;
            int intByteOfObjLength = byteOfObj.length;
            if (intByteOfObjLength > 0) {

                // --- start generate record peserta photo ---
                long oidBlobEmpPicture = 0;
                //EmpRelevantDoc objEmpRelevantDoc = PstEmpRelevantDoc.getObjEmpPicture(longOidDoc);
                //objEmpRelevantDoc.setEmployeeId(longOidEmp);
                //get EmployeeNUmber
                // --- start generate photo peserta ---
                //SessEmpRelevantDoc objSessEmpRelevantDoc = new SessEmpRelevantDoc();
                //String pathFileName = objSessEmpRelevantDoc.getAbsoluteFileName(fileName);
                try {
                   /// EmpRelevantDoc empRelevantDoc = new EmpRelevantDoc();
                   /// PstEmpRelevantDoc.updateFileName(fileName, longOidDoc);
                    System.out.println("update sukses.." + fileName);
                } catch (Exception e) {
                    System.out.println("err update.." + e.toString());
                }
                //java.io.ByteArrayInputStream byteIns = new java.io.ByteArrayInputStream(byteOfObj);
                //uploader.writeCache(byteIns, pathFileName, true);
                // --- end generate photo peserta ---
		 			 
            }
        } catch (Exception e) {
            System.out.println("Exc1 when upload image : " + e.toString());
        }
        //}
    } catch (Exception e) {
        System.out.println("Exc2 when upload image : " + e.toString());
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warning Upload</title>
    </head>
    <body>
        <h1>Upload file image warning</h1>
        <form name="frm" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="command" value="">
            <input type="file" name="pict">
            <input type="submit" name="submit" value="Upload" />
        </form>
    </body>
</html>
