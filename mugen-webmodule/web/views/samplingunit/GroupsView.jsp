<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View groups</title>
    </head>
    <body>                          
        <h1>Groups</h1>

        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit();"/>
                    <input id="button" type="submit" value="Set">
                </td>
                <td>Grouping<br>
                    <m:checkbox collection="mugen.collection.groupingdto" selected='<%=fdm.getValue("gsid")%>' idGetter="getGsid" textGetter="getName" name="gsid" onChange="this.form.submit();"/>
                    <input id="button" type="submit" id="clickableWidget" value="Display">
                </td>
            </tr>
        </table>
        <hr id="ruler">
        </form> 
        <m:navigation-buttons workflow="ViewGroups" showText='true'/>
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Comment</th>
            <th class="data">Individuals</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
        </tr>
        <m:iterate-collection collection="mugen.collection.groupdto">
        
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td><a href="Controller?workflow=ViewIndividuals&gid=#:getGid#&gsid=<%=fdm.getValue("gsid")%>">#:getIndividuals#</a></td>
            <td>#:getUser#</td>
            <td>#:getDate#</td>
            <td><a href="Controller?workflow=ViewGroup&gid=#:getGid#"><m:img name="view"/></a></td> 
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewGroups"/>
    </body>
</html>