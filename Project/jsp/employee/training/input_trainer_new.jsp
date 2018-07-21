<%-- 
    Document   : input_trainer_new
    Created on : Dec 13, 2015, 11:42:41 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.employee.FrmTrainingActivityPlan"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.employee.PstTrainingActivityPlan"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.form.employee.CtrlTrainingHistory"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String drawList(Vector objectClass) {        
        String str = "";

        for(int i = 0; i < objectClass.size(); i++) {
            Vector rowx = new Vector();
            String name = (String)objectClass.get(i);
            str += "<div id=\"trainer\" onclick=\"javascript:cmdAdd('"+name+"')\">"+name+"</div>";
        }

        return str;
    } 
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int vectSize = FRMQueryString.requestInt(request, "vect_size");
    String trainer = FRMQueryString.requestString(request, "trainer");
    String search = FRMQueryString.requestString(request, "search");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
 
    String whereClause = "";
    String orderClause = "";    
    int recordToGet = 0;  
    
    CtrlTrainingHistory ctrl = new CtrlTrainingHistory(request);
    Vector listTrainer = new Vector(1,1);
        
    if (iCommand == Command.SEARCH){
        listTrainer = PstTrainingActivityPlan.getSearchTrainer(search);
    } else {
        listTrainer = PstTrainingActivityPlan.getTrainer(start, recordToGet); 
    }

    
    
    System.out.println("List Trainer Size = " + listTrainer.size());
   
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Browse or Add Trainer</title>
        <style type="text/css">
            body {
                background-color: #F5F5F5;
                margin: 0;
                padding: 0;
            }
            .content {
                padding: 21px;
            }
            #header {
                color: #575757;
                font-size: 18px;
                font-weight: bold;
                text-align: center;
                padding: 21px 11px;
                background-color: #EEE;
                border-bottom: 1px solid #DDD;
            }
            #trainer {
                color: #474747;
                padding: 3px;
                background-color: #EEE;
                border: 1px solid #DDD;
                margin: 2px 3px;
                border-radius: 3px;
                cursor: pointer;
            }
            #trainer:hover {
                background-color: #CCC;
                border: 1px solid #B7B7B7;
                color: #FFF;
            }
            input {
                padding: 5px 7px;
                border: 1px solid #DDD;
                border-radius: 3px;
            }
            h1 {
                border-bottom: 1px solid #DDD;
            }
            a {
                text-decoration: none;
                padding: 3px 5px;
                background-color: #00A3CC;
                color: #FFF;
            }
        </style>
<script language="JavaScript">
    
    <% if(iCommand == Command.SAVE) { %> 

        cmdAdd("<%=trainer%>");
        
    <% } %>
    
    function cmdSearch(){
        document.frm_trainer.command.value="<%= Command.SEARCH %>";
        document.frm_trainer.submit();
    }
    function cmdAdd(name){
        self.opener.document.frm_trainingplan.<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINER]%>.value=name;
        self.close();
    }
    
    function cmdAddNew() {
        document.frm_trainer.command.value="<%= Command.SAVE %>";
        document.frm_trainer.submit();
    }    
</script>
    </head>
    <body>
        <div id="header">Searching or Add Trainer</div>
        <form name="frm_trainer" method ="post" action="">
            <input type="hidden" name="command" value="<%= iCommand %>">                           
            <input type="hidden" name="start" value="<%= start %>">     
            <input type="hidden" name="vect_size" value="<%= vectSize %>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <div class="content">
            <div id="caption">Trainer Name</div>
            <div id="divinput">
                <input type="text" name="trainer" size="50" value="<%= trainer %>">
                <a href="javascript:cmdAddNew()">Add</a>
            </div>
                <div id="caption">Browse</div>
            <div id="divinput">
                <input type="text" name="search" size="50" value="" placeholder="search...">
                <a href="javascript:cmdSearch()">Search</a>
            </div>
                
            <% if (listTrainer.size()>0) { %>
                        <%= drawList(listTrainer)%> 

            <%  } else {  %>
                        <p>&nbsp;&nbsp; No trainer data found!</p> 
            <%  } %>   
            </div>
        </form>
    </body>
</html>
