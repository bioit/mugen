<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Genes</title>
    </head>
    <body>         
        <h1>Stand Alone Genes</h1>
         
        <form action="Controller" method="post">
       
        <hr id="ruler">
            <table class="data">
                <tr>                    
                    <th class="data" width="15%">Gene Symbol</th>
                    <th class="data" width="45%">Gene Name</th>
                    <th class="data" width="10%">User</th>
                    <th class="data" width="10%">Updated</th>
                    <th class="data" width="10%">Details</th>
                    <th class="data" width="10%">Kill</th>
                </tr>
                <m:iterate-collection collection="genes">
                    <tr class="#?alt#">
                        <td>#:getGenesymbol#</td>
                        <td>#:getName#</td>
                        <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUserName#</a></td>
                        <td>#:getTs#</td>
                        <td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#"><m:img name="view" title="View the details for this gene"/></a></td>
                        <td><a href="Controller?workflow=SimplyRemoveGene&gaid=#:getGaid#" title="kill gene" onClick="return confirm('kill gene?')"><m:img name="delete"/></a></td>
                    </tr>
                </m:iterate-collection>
            </table>
       </form>
    </body>
</html>
