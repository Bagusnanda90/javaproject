<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ page import="java.awt.image.*,java.io.*,javax.imageio.*,sun.misc.*" %>

         <% 
            String st = FRMQueryString.requestStringWithoutInjection(request, "datauri");
            long oidEmpPicture = FRMQueryString.requestLong(request, "emp_picture_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "emp_id");
            String pictName =  FRMQueryString.requestString(request, "pict");
            
            Employee employee = new Employee();
            try{
                employee = PstEmployee.fetchExc(oidEmployee);
            }catch(Exception e){ }
            
            String base64Image = st.split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            String loca = getServletContext().getRealPath("/")+"/imgcache/";
            String paynum = ""+employee.getEmployeeNum();
            if (img != null)
                ImageIO.write(img, "jpg", new File(loca+paynum+".jpg"));
            //out.println("value=" + st); // here it going to displaying base64 chars
            //System.out.println("value=" + st); //but here it is going to displaying document.writeln(dat)  
        %>
        <script type="text/javascript">
            window.close();
self.close();
web_window.close();
         my_window.close ();
        </script>