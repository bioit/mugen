<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Marker sets</title>
    </head>
    <body>         
        <h1>Unified Marker sets</h1>       
        <form action="Controller" method="post">                
        <table>
            <tr>
                <td>Species <br>
                    <m:checkbox collection="species" selected='<%=fdm.getValue("v.sid")%>' name="v.sid" idGetter="getSid" textGetter="getName"/>
                </td>
                <td>            
                    Name <br><input type="text" name="v.name" size="15" value='<%=fdm.getValue("v.name")%>'/>
                </td>
                <td>            
                    Comment <br><input type="text" name="v.comm" size="15" value='<%=fdm.getValue("v.comm")%>'/>
                </td>                
                <td>            
                    Member <br><input type="text" name="member" size="15" value='<%=fdm.getValue("member")%>'/>
                </td>                
            </tr>  
            <tr>
                <td colspan="4" align="right">                    
                    <input id="button" type="submit" name="collect.getumarkersetsaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table> 
        <hr id="ruler">  
        </form>          
        <m:navigation-buttons workflow="ViewUMarkerSets" showText='true'/>
        <table class="data">
        <tr>
            <th class="data">Unified Marker set</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
            <m:hide privilege="UMRKS_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="umarkersets">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>            
            <td><a href="Controller?workflow=ViewUMarkerSet&umsid=#:getUmsid#"><m:img name="view"/></a></td>
            <m:hide privilege="UMRKS_W">
                <td><a href="Controller?workflow=EditUMarkerSet&umsid=#:getUmsid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewUMarkerSets"/>
    </body>
</html>
