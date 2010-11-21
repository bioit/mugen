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
        <h1>Protein complexes</h1>    
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
                    <th class="data" width="30%">Name</th>
                    <th class="data" width="10%">Subunits</th>
                    <th class="data" width="40%">Function</th>
                    <th class="data" width="10%">Updated</th>
                    <th class="data" width="10%">Details</th>
                </tr>
                <m:iterate-collection collection="complexes">
                    <tr class="#?alt#">
                        <td width="30%">#:getName#</td>                        
                        <td width="10%">#:getNumSubunits#</td>
                        <td width="40%">#:getFunction#</td>
                        <td width="10%">#:getTs#</td>
                        <td width="10%"><a href="Controller?workflow=ViewComplex&complexId=#:getComplexId#"><m:img name="view" title="View the details for this complex"/></a></td>
                    </tr>
                </m:iterate-collection>
            </table> 
       </form>
    </body>
</html>
