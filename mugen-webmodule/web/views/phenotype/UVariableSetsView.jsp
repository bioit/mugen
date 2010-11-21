<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Collection" %>
<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");             
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller"); 
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Variable Sets</title>
    </head>
    <body>         
        <h1>Unified Variable Sets</h1>
        <form action="Controller" method="post">
        <input type="hidden" name="pid" value='<%=caller.getPid()%>'/>
        <table>
            <tr>
                <td>Species <br>
                    <m:checkbox collection="species" selected='<%=caller.getSid()%>' name="sid" idGetter="getSid" textGetter="getName"/>
                </td>
                <td>            
                    Name <br><input type="text" name="vsname" size="15" value='<%=fdm.getValue("vsname")%>'/>
                </td>
                <td>            
                    Member <br><input type="text" name="vname" size="15" value='<%=fdm.getValue("vname")%>'/>
                </td>                
            </tr>  
            <tr>
                <td colspan=3 align="right">                    
                    <input id="button" type="submit" name="collect.getuvariablesetsaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>   
        <hr id="ruler">
        </form>
        
        <m:navigation-buttons workflow="ViewUVariableSets" showText='true'/>                         
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
            <m:hide privilege="UVARS_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="uvariablesetsdto">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewUVariableSet&uvsid=#:getUvsid#"><m:img name="view"/></a></td>
            <m:hide privilege="UVARS_W">
                <td><a href="Controller?workflow=EditUVariableSet&uvsid=#:getUvsid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewUVariableSets"/>
    </body>
</html>
