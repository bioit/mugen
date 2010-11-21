<%       
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Proteins</title>
    </head>
    <body>         
        <h1>Proteins</h1>    
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Species<br>
                    <m:checkbox collection="species" selected='<%=caller.getSid()%>' name='speciesId' idGetter='getSid' textGetter='getName' onChange="this.form.submit();"/>
                    <input id="button" type="submit" value="Set">
                </td>            
            </tr>
        </table>                    
        <hr id="ruler">  
            <table class="data">
                <tr>
                    <th class="data" width="55%">Name</th>
                    <th class="data" width="10%">Processes</th>
                    <th class="data" width="10%">Pathways</th>
                    <th class="data" width="10%">Updated</th>
                    <th class="data" width="10%">Details</th>
                </tr>
                <m:iterate-collection collection="proteins">
                    <tr class="#?alt#">
                        <td width="55%">#:getName#</td>                        
                        <td width="10%">#:getNumProcesses#</td>
                        <td width="10%">#:getNumPathways#</td>
                        <td width="10%">#:getTs#</td>
                        <td width="10%"><a href="Controller?workflow=ViewProtein&proteinId=#:getProteinId#"><m:img name="view" title="View the details for this protein"/></a></td>
                    </tr>
                </m:iterate-collection>
            </table> 
       </form>
    </body>
</html>
