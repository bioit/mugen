<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller"); 
    com.arexis.mugen.project.ParamDataObject pdo = (com.arexis.mugen.project.ParamDataObject)request.getAttribute("paramdataobject");    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View samples</title>
    </head>
    <body>         
        <h1>Filters</h1>
        <form action="Controller" method="post">
        <input type="hidden" name="pid" value='<%=caller.getPid()%>'/>
        <table>
            <tr>
                <td>Species <br>
                    <m:checkbox collection="species" selected='<%=caller.getSid()%>' name="sid" idGetter="getSid" textGetter="getName"/>
                </td>
                <td>            
                    Name <br><input type="text" name="name" size="15" value='<%=pdo.getValue("name")%>'/>
                </td>
                <td>            
                    Expression <br><input type="text" name="expression" size="15" value='<%=pdo.getValue("expression")%>'/>
                </td>
            </tr>
            <tr>
                <td colspan=3 align="right">                    
                    <input id="button" type="submit" name="collect.getfiltersaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>   
        <hr id="ruler">
        </form>                       
        <m:navigation-buttons workflow="ViewFilters" showText='true'/>
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Expression</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Edit</th>
        </tr>
        <m:iterate-collection collection="filters">
        
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td width="50%">#:getExpression#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=EditFilter&fid=#:getFid#"><m:img name="edit"/></a></td>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewFilters"/>
    </body>
</html>
