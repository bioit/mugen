<html>
    <head>
        <meta http-equiv="Content-Type">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Groupings</title>
    </head>
    <body>         
        <h1>Groupings</h1>
        <form action="Controller" method="post">
        <table>
            <tr>
                <td>Sampling unit <br>
                    <m:su-combobox onChange="this.form.submit()"/>
                    <input id="button" type="submit" name="collect.getgroupingsaction.display" value="Display">
                </td>
            </tr>
        </table>     
        <hr id="ruler">
        <m:navigation-buttons workflow="ViewGroupings" showText='true'/>
        <table class="data">
            <tr>
                <th class="data">Name</th>
                <th class="data">Comment</th>
                <th class="data">Groups</th>
                <th class="data">User</th>
                <th class="data">Updated</th>
                <th class="data">Details</th>
            </tr>
            <m:iterate-collection collection="mugen.collection.groupingdto">

            <tr class="#?alt#">
                <td>#:getName#</td>
                <td>#:getComm#</td>
                <td><a href="Controller?workflow=ViewGroups&gsid=#:getGsid#">#:getGroups#</a></td>
                <td>#:getUser#</td>
                <td>#:getUpdated#</td>
                <td><a href="Controller?workflow=ViewGrouping&gsid=#:getGsid#"><m:img name="view"/></a></td>
            </tr>

            </m:iterate-collection>
        </table> 
        </form>        
       <m:navigation-buttons workflow="ViewGroupings"/>
    </body>
</html>
