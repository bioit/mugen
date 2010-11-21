<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Genes</title>
    </head>
    <body>         
        <h1>genes with ensembl id</h1>
         
        <form action="Controller" method="post">
       
        <hr id="ruler">
            <table class="data">
                <tr>                    
                    <th class="data" width="15%">Gene Symbol</th>
                    <th class="data" width="45%">Gene Name</th>
                    <th class="data" width="30%">ENS ID</th>
                    <th class="data" width="10%">User</th>
                </tr>
                <m:iterate-collection collection="genes">
                    <tr class="#?alt#">
                        <td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#" title="View the details for this gene">#:getGenesymbol#</a></td>
                        <td>#:getName#</td>
                        <td>#:getEnsemblink#</td>
                        <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUserName#</a></td>
                    </tr>
                </m:iterate-collection>
            </table>
       </form>
    </body>
</html>
