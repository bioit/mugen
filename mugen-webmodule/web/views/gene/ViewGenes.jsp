<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>genes</title>
    </head>
    <body class="content">
        <form action="Controller" method="post">
        <h1>genes</h1>
        <p class="toolbar">
        <%--<a href="Controller?workflow=ViewGenes&genes.help=true"><m:img name="info_24" title="help & info"/></a>--%>
        <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
        <m:hide-block name="genes.help">
                <table>    
                    <tr>
                        <td>
                            <table class="info">
                                <td>
                                    <b>Help & Info on this page:</b><br><br>
                                    <b>Gene Name:</b> It is the name of the gene as it was typed by the user who submitted this gene.<br>
                                    <b>Comment:</b> Contains the comments that are relevant to the gene as typed by the user who submitted the gene.<br>
                                    <b>Mutants:</b> This blue arrow icon will redirect you to the list of mutant strains that are connected to a specific gene. It is a very quick and convenient way to sort mutant strains by an affected gene.<br>
                                    <b>User:</b> Represents the user who last edited or first submitted a gene. It is link-functional, meaning that clicking on it will transfer you to the contact details page of this person.<br>
                                    <b>Updated:</b> Displays the date the last update on this gene took place.<br>
                                    <b>Details:</b> Clicking on the lens icon will redirect you to the detailed information page for a gene. All information that is stored in the database and is relevant to this gene will be displayed on this page.<br>
                                </td>
                            </table>
                        </td>
                    </tr>     
                </table>     
       </m:hide-block>
        <hr id="ruler">  
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewGenes" showText='true'/>
                    </td>
                </tr>
            </table>
            <table class="data">
                <tr>                    
                    <th class="data" width="15%">Symbol</th>
                    <th class="data" width="55%">Name</th>
                    <th class="data" width="10%">M|A</th>
                    <%--th class="data" width="10%">Alleles</th--%>
                    <th class="data" width="10%">User</th>
                    <th class="data" width="10%">Updated</th>
                </tr>
                <m:iterate-collection collection="genes">
                    <tr class="#?alt#">
                        <td>#:getGenesymbol#</td>
                        <td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#" title="view gene/transgene details">#:getName#</a></td>
                        <td><a class="menu" href="Controller?workflow=ViewGene&gaid=#:getGaid#" title="view related mice | alleles">#:getModelsnum# | #:getAllelesnum#</a></td>
                        <%--td><a class="menu" href="Controller?workflow=ViewModels&_gaid=#:getGaid#" title="view related mice">#:getModelsnum#</a></td--%>
                        <%--td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#" title="view related alleles">#:getAllelesnum#</a></td--%>
                        <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="view user details">#:getUserName#</a></td>
                        <td>#:getTs#</td>
                    </tr>
                </m:iterate-collection>
            </table>
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewGenes"/>
                    </td>
                </tr>
            </table>
       </form>
    </body>
</html>
