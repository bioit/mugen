<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>research application types</title>
    </head>
    <body class="content">
        <form action="Controller" method="post">
        <h1>research application types</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p> 
        <br>
        <table class="data">
            <tr>
                <th class="data" width="30%">Name</th>
                <th class="data" width="30%">Comment</th>
                <th class="data" width="10%">Mutants</th>
                <th class="data" width="10%">User</th>
                <th class="data" width="10%">Updated</th>
                <th class="data" width="10%">Details</th>
            </tr>
            <m:iterate-collection collection="resapptypes">
                <tr class="#?alt#">
                    <td>#:getName#</td>
                    <td>#:getComm#</td>
                    <td><a href="Controller?workflow=ViewModels&_raid=#:getRaid#"><m:img name="jump" title="Find mutants from research application"/></a></td>
                    <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUserName#</a></td>
                    <td>#:getTs#</td>
                    <td><a href="Controller?workflow=ViewResearchApp&raid=#:getRaid#" title="View the details for this research application type"><m:img name="view"/></a></td>
                </tr>
            </m:iterate-collection>  
        </table>
       </form>
    </body>
</html>
