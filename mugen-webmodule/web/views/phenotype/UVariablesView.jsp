<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Collection" %>
<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller"); 
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");             
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Variables</title>
    </head>
    <body>         
        <h1>Unified Variables</h1>
        <form action="Controller" method="post">
        <input type="hidden" name="pid" value='<%=caller.getPid()%>'/>
        <table>
            <tr>
                <td>Species <br>
                    <m:checkbox collection="species" selected='<%=caller.getSid()%>' name="sid" idGetter="getSid" textGetter="getName"/>
                </td>
                <td>            
                    Name <br><input type="text" name="name" size="15" value='<%=fdm.getValue("name")%>'/>
                </td>
                <td>
                    Type <br><m:type value='<%=fdm.getValue("type")%>' wildcard='true'/>
                </td>             
                <td>            
                    Unit <br><input type="text" name="unit" size="15" value='<%=fdm.getValue("unit")%>'/>
                </td>
            </tr>  
            <tr>
                <td colspan=4 align="right">                    
                    <input id="button" type="submit" name="collect.getuvariablesaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>   
        <hr ir="ruler">
        </form>
        
        <m:navigation-buttons workflow="ViewUVariables" showText='true'/> 
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Type</th>
            <th class="data">Unit</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Mappings</th>
            <th class="data">Details</th>
            <m:hide privilege="UVAR_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="uvariablesdto">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getType#</td>
            <td>#:getUnit#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewUVariableMapping&uvid=#:getUvid#">Mapping</a></td>            
            <td><a href="Controller?workflow=ViewUVariable&uvid=#:getUvid#"><m:img name="view"/></a></td>
            <m:hide privilege="UVAR_W">
                <td><a href="Controller?workflow=EditUVariable&uvid=#:getUvid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewUVariables"/>
    </body>
</html>
