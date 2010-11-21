<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>MP Terms</title>
    </head>
    <body>         
        <h1>MP Terms</h1>
         
        <form action="Controller" method="post">
        
        <hr id="ruler">  
            <table class="data">
                <tr>                    
                    <th class="data" width="10%">MP ID</th>
                    <th class="data" width="15%">MP Term</th>
                    <th class="data" width="10%">MP Def</th>
                    <th class="data" width="10%">MP Def_Ref</th>
                    <th class="data" width="10%">MP Synonyms</th>
                    <th class="data" width="5%">OBS</th>
                    <th class="data" width="20%">MP ISAS</th>
                    <th class="data" width="10%">Alt IDs</th>
                    <th class="data" width="10%">XREFS</th>
                </tr>
                <m:iterate-collection collection="mpterms">
                    <tr class="#?alt#">
                        <td>#:getPhenoId#</td>
                        <td>#:getPhenoName#</td>
                        <td>#:getPhenoDef#</td>
                        <td>#:getPhenoDefRef#</td>
                        <td>#:getSynonymsString#</td>
                        <td>#:getIsObsolete#</td>
                        <td>#:getIsAsString#</td>
                        <td>#:getAltIdsString#</td>
                        <td>#:getXrefsString#</td>
                    </tr>
                </m:iterate-collection>
            </table>
       </form>
    </body>
</html>
