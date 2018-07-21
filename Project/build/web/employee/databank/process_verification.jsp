<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%!
    String regTemp="";
    String whereClause ="";
    com.dimata.harisma.entity.admin.PstAppUser pstAppUser = new com.dimata.harisma.entity.admin.PstAppUser();
%>

<%
    /*regTemp = request.getParameter("VerPas");
    if (!regTemp.equals("")){
        String [] tempData = regTemp.split("\\;");
        try{
            if(tempData.length>0){
                String user_id	= tempData[0];
                whereClause=" "+pstAppUser.fieldNames[pstAppUser.FLD_EMPLOYEE_ID]+"= "+user_id+"";
                java.util.Vector listAppUser = pstAppUser.listFullObj(0, 0, whereClause, "");
                com.dimata.posbo.entity.admin.AppUser appUser = (com.dimata.posbo.entity.admin.AppUser)listAppUser.get(0);
                
                //update status user ke 1, yang berarti user tersebut sudah login
                long result = pstAppUser.updateUserStatus(appUser.getOID(), 1);
                
                String url = request.getRequestURL().toString();
                String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
                
                out.println("" + baseURL + "close.jsp");
            }
            
        }catch(Exception es){
        }
        
    }*/
    
    long user_id = 0;
    int result = 0;
    
    user_id = FRMQueryString.requestLong(request, "oid");
    result = FRMQueryString.requestInt(request, "result");
    if (user_id!=0){
        if (result==1){
            try{
                String sqlUpdate = "UPDATE "+PstEmployee.TBL_HR_EMPLOYEE+" SET ";
                sqlUpdate += PstEmployee.fieldNames[PstEmployee.FLD_FINGER_CHECK]+"="+result+"";
                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                sqlUpdate += " WHERE "+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+user_id;
                DBHandler.execUpdate(sqlUpdate);
                //update status user ke 1, yang berarti user tersebut sudah login
                //long resultX = pstAppUser.updateUserStatus(appUser.getOID(), 1);

                
            }catch(Exception es){
            
            }
        }
    }
    
%>
