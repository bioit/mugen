
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
        <title>View Variable Sets</title>
    </head>
    <body>         
        <h1>Variable Sets</h1>
        <form action="Controller" method="post">                
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox/>
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
                    <input id="button" type="submit" name="collect.getvariablesetsaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table> 
        <hr id="ruler">
        </form>
        
        <m:navigation-buttons workflow="ViewVariableSets" showText='true'/> 
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Comment</th>
            <th class="data">Variables</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
        </tr>
        <m:iterate-collection collection="variablesetsdto">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td><a href="Controller?workflow=ViewVariables&vs.vsid=#:getVsid#"><m:img name="jump" title="Jump to variables"/></a></td>            
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewVariableSet&vsid=#:getVsid#"><m:img name="view"/></a></td>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewVariableSets"/>
    </body>
</html>
