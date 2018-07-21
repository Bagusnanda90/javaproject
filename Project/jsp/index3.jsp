<%-- 
    Document   : index3
    Created on : Jun 30, 2012, 1:02:24 PM
    Author     : ktanjana
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AJAX and Java - veerasundar.com</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>	
</head>
<body>		
    <div id="progressbar"></div>
<script type="text/javascript" language="javascript">
function checkStatus()
{ 
    $.ajax({
          type: 'POST',
          url: 'checkStatusServlet', 
          dataType: 'json',
          success: function( data )
          {
            var statusPercent = data.statusPercent;
            //Update your jQuery progress bar   
            $( "#progressbar" ).progressbar({value: statusPercent });
          }
    });
}

function checkStatusX(){
alert("Ho");
}
</script>
          


</body>
<script language="javascript" type="text/javascript" >
setTimeout('checkStatus()',1000);
setTimeout('checkStatusX()',3000);
</script>
       
</html>