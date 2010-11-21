<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Markers</title>
    </head>
    <body>         
        <h1>Unified Markers</h1>  
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>
                    Species <br><m:checkbox collection="species" idGetter="getSid" textGetter="getName" name="sid" selected="<%=fdm.getValue("sid")%>" onChange="this.form.submit()"/>
                    <input id="button" type="submit" value="Set">
                </td>        
                <td>
                    Chromosome <br><m:checkbox collection="chromosomes"  idGetter="getName" textGetter="getName" name="cname" selected="<%=fdm.getValue("cname")%>"/>
                </td>            
            </tr>
            <tr>            
                <td colspan="2" align="right" valign="bottom">  
                    <input id="button" type="submit" name="collect.getumarkersaction.display" value="Display">
                    <input id="button" type="submit" name="reset" value="Reset">
                </td>
            </tr>          
        </table>
        <hr id="ruler">        
        </form>       
        <m:navigation-buttons workflow="ViewUMarkers" showText="true"/>
        <table class="data">
        <tr>	  	  	 
            <th class="data">Chromosome</th>
            <th class="data">U. Marker</th>
            <th class="data">Comment</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Mapping</th>
            <th class="data">Alleles</th>
            <th class="data">Details</th>
            <m:hide privilege="UMRK_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="umarkers">
	 
        <tr class="#?alt#">
            <td>#:getChromosome#</td>
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>            
            <td><a href="Controller?workflow=ViewUMarkerMapping&umid=#:getUmid#">Mapping</a></td>
            <td><a href="Controller?workflow=ViewUMarkerAlleles&umid=#:getUmid#"><m:img name="alleles"/></a></td>
            <td><a href="Controller?workflow=ViewUMarker&umid=#:getUmid#"><m:img name="view"/></a></td>
            <m:hide privilege="UMRK_W">
                <td><a href="Controller?workflow=EditUMarker&umid=#:getUmid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewUMarkers"/>
    </body>
</html>
