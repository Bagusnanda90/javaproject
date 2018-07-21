<%@ taglib uri="/WEB-INF/tlds/pd4ml.tld" prefix="pd4ml" %><%@page
contentType="text/html; charset=ISO8859_1"%><pd4ml:transform
      screenWidth="400"
      pageFormat="A5"
      pageOrientation="landscape"
      pageInsets="100,100,100,100,points">
 
  <html>
      <head>
            <title>pd4ml test</title>
            <style type="text/css">
                  body {
                        color: red;
                        background-color: #FFFFFF;
                        font-family: Tahoma, "Sans-Serif";
                        font-size: 10pt;
                  }
            </style>
      </head>
      <body>
         <img src="images/logos.gif" width="125" height="74">
            <p>
            Hello, World!
<pd4ml:page.break/>
            <table width="100%" style="background-color: #f4f4f4; color: #000000">
            <tr>
            <td>
                  Hello, New Page!
            </td>
            </tr>
            </table>
      </body>
  </html>
 </pd4ml:transform>