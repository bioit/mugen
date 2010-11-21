<%
    com.arexis.form.FormDataManager pdo = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Markers</title>
    </head>
    <body>         
        <h1>Markers</h1>  
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>                    
                </td>       
                <td>
                    Chromosome<br><m:checkbox collection="chromosomes" idGetter="getName" textGetter="getName" name="cname" selected="<%=pdo.getValue("cname")%>"/>
                </td>
                <td>
                    Marker <br><input type="text" name="name" size="15" value='<%=pdo.getValue("name")%>'/>
                </td>              
            </tr>
            <tr>            
                <td colspan="3" align="right" valign="bottom">  
                    <input id="button" type="submit" name="collect.getmarkersaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>             
        </table>
        <hr id="ruler">        
        </form>       
        <m:navigation-buttons workflow="ViewMarkers" showText="true"/>
        <table class="data">
        <tr>
            <th class="data">Chromosome</th>
            <th class="data">Marker</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Alleles</th>
            <th class="data">Details</th>
            <m:hide privilege="MRK_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="markers">
	 
        <tr class="#?alt#">
            <td>#:getChromosome#</td>
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewMarkerAlleles&mid=#:getMid#"><m:img name="alleles"/></a></td>
            <td><a href="Controller?workflow=ViewMarker&mid=#:getMid#"><m:img name="view"/></a></td>
            <m:hide privilege="MRK_W">
                <td><a href="Controller?workflow=EditMarker&mid=#:getMid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewMarkers"/>
    </body>
</html>
