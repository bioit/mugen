<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Variables</title>
    </head>
    <body>         
        <h1>Variables</h1>
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox name="v.suid" onChange="this.form.submit()"/>
                    <input id="button" type=submit name="set" value="Set">
                </td>
                <td>                       
                    Variable set <br><m:checkbox collection="variablesets" wildcardOption='true' idGetter="getVsid" textGetter="getName" name="vs.vsid" selected='<%=fdm.getValue("vs.vsid")%>'/>                    
                <td>                
                <td>            
                    Name <br><input type="text" name="v.name" size="15" value='<%=fdm.getValue("v.name")%>'/>
                </td>
                <td>
                    Type <br><m:type name="v.type" value='<%=fdm.getValue("v.type")%>' wildcard='true'/>
                </td>             
                <td>            
                    Unit <br><input type="text" name="v.unit" size="15" value='<%=fdm.getValue("v.unit")%>'/>
                </td>
            </tr>  
            <tr>
                <td colspan="6" align="right">                    
                    <input id="button" type="submit" name="collect.getvariablesaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>   
        <hr id="ruler">
        </form>
        
        <m:navigation-buttons workflow="ViewVariables"showText='true'/>   
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Type</th>
            <th class="data">Unit</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
        </tr>
        <m:iterate-collection collection="variablesdto">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getType#</td>
            <td>#:getUnit#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewVariable&vid=#:getVid#"><m:img name="view"/></a></td>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewVariables"/>
    </body>
</html>
