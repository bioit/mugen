<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Marker set positions</title>
    </head>
    <body>         
        <h1>Marker sets positions</h1>  
        <form action="Controller" method="post">   
            <table>
                <tr>
                    <td>
                        Sampling unit <m:su-combobox onChange="this.form.submit();"/>             
                        <input id="button" type="submit" name="set" value="Set">                        
                    </td>                
                    <td>
                        Marker set <m:checkbox collection="markersets" name="msid" idGetter="getMsid" textGetter="getName"  selected='<%=fdm.getValue("msid")%>' onChange="this.form.submit();"/>             
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
            <m:iterate-collection collection="positions">

            <tr class="#?alt#">
                <td>#:getCname#</td>
                <td><a href="Controller?workflow=ViewMarker&mid=#:getMid#">#:getMname#</a></td>
                <td>#:getDefpos#</td>
                <td>#:getOverpos#</td> 
                <td>#:getPos#</td> 
                <td><a href="Controller?workflow=EditMarkerSetPosition&msid=#:getMsid#&mid=#:getMid#">Set</a></td>
            </tr>

            </m:iterate-collection>
            </table> 
        </form>  
    </body>
</html>
