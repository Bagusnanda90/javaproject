<%-- 
    Document   : upload-pic-rep
    Created on : Mar 21, 2016, 9:16:27 AM
    Author     : Dimata 007
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reprimand Upload</title>
    </head>
    <body>
        <h1>Upload file image reprimand</h1>
        <form name="frm" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="command" value="">
            <input type="file" name="pict">
            <input type="submit" name="submit" value="Upload" />
        </form>
    </body>
</html>