
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    com.arexis.form.FormDataManager pdo = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Marker sets</title>
    </head>
    <body>         
        <h1>Marker sets</h1>    
        <form action="Controller" method="post">                
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox name="v.suid"/>
                </td>
                <td>            
                    Name <br><input type="text" name="v.name" size="15" value='<%=pdo.getValue("v.name")%>'/>
                </td>
                <td>            
                    Comment <br><input type="text" name="v.comm" size="15" value='<%=pdo.getValue("v.comm")%>'/>
                </td>                
                <td>            
                    Member <br><input type="text" name="member" size="15" value='<%=pdo.getValue("member")%>'/>
                </td>                
            </tr>  
            <tr>
                <td colspan="4" align="right">                    
                    <input id="button" type="submit" name="collect.getmarkersetsaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>
        </table>
        <hr id="ruler">
        </form>   
        
        <m:navigation-buttons workflow="ViewMarkerSets" showText='true'/>
        <table class="data">
        <tr>
            <th class="data">Marker set</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
            <m:hide privilege="MRKS_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="markersets">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>            
            <td><a href="Controller?workflow=ViewMarkerSet&msid=#:getMsid#"><m:img name="view"/></a></td>
            <m:hide privilege="MRKS_W">
                <td><a href="Controller?workflow=EditMarkerSet&msid=#:getMsid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewMarkerSets"/>
    </body>
</html>
