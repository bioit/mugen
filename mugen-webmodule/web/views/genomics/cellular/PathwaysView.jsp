<%       
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Pathways</title>
    </head>
    <body>         
        <h1>Pathways</h1>    
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Species<br>
                    <m:checkbox collection="species" selected='<%=caller.getSid()%>' name='speciesId' idGetter='getSid' textGetter='getName' onChange="this.form.submit();"/>
                    <input id="button" type="submit" value="Set">
                </td>            
                <td>                       
                    Process <br><m:checkbox collection="processes" idGetter="getProcessId" textGetter="getName" wildcardOption='true' name="processId" selected='<%=(String)request.getAttribute("processId")%>' onChange="this.form.submit();"/>
                    <input id="button" type=submit name="display" value="Display">
                <td>
            </tr>
        </table>                    
        <hr id="ruler">  
            <table class="data">
                <tr>
                    <th class="data" width="50%">Pathway</th>
                    <th class="data" width="10%">Processes</th>
                    <th class="data" width="10%">Proteins</th>
                    <th class="data" width="15%">Updated</th>
                    <th class="data" width="15%">Details</th>
                </tr>
                <m:iterate-collection collection="pathways">
                    <tr class="#?alt#">
                        <td width="50%">#:getName#</td>
                        <td width="10%"><a href="Controller?workflow=ViewProcesses&pathwayId=#:getPathwayId#" title="Go to processes">#:getNumProcesses#</a></td>
                        <td width="10%">#:getNumProteins#</td>
                        <td width="15%">#:getTs#</td>
                        <td width="15%"><a href="Controller?workflow=ViewPathway&pathwayId=#:getPathwayId#"><m:img name="view" title="View the details for this pathway"/></a></td>
                    </tr>
                </m:iterate-collection>
            </table> 
       </form>
    </body>
</html>
