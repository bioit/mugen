
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Marker set positions</title>
    </head>
    <body>         
        <h1>Unified Marker sets positions</h1>  
        <form action="Controller" method="post"> 
            <table>
                <tr>
                    <td>
                        Species <br><m:checkbox collection="species" name="sid" idGetter="getSid" textGetter="getName" selected='<%=fdm.getValue("sid")%>' onChange="this.form.submit();"/>              
                        <input id="button" type="submit" name="set" value="Set">
                    </td>                
                    <td>
                        Unified Marker set <br><m:checkbox collection="umarkersets" name="umsid" idGetter="getUmsid" textGetter="getName" selected='<%=fdm.getValue("umsid")%>' onChange="this.form.submit();"/>              
                        <input id="button" type="submit" name="display" value="Display">
                    </td>
                </tr>
            </table>
            <br>
            <table class="data">
            <tr>
                <th class="data">Chromosome</th>
                <th class="data">Marker</th>
                <th class="data">Def. position</th>
                <th class="data">Over. position</th>
                <th class="data">Position</th>
                <th class="data">New position</th>
            </tr>
            <m:iterate-collection collection="upositions">

            <tr class="#?alt#">
                <td>#:getCname#</td>
                <td><a href="Controller?workflow=ViewUMarker&umid=#:getUmid#">#:getUmname#</a></td>
                <td>#:getDefpos#</td>
                <td>#:getOverpos#</td> 
                <td>#:getPos#</td> 
                <td><a href="Controller?workflow=EditUMarkerSetPosition&umsid=#:getUmsid#&umid=#:getUmid#">Set</a></td>
            </tr>

            </m:iterate-collection>
            </table> 
        </form>  
    </body>
</html>
