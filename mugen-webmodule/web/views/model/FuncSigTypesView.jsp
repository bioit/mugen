<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>functional significance types</title>
    </head>
    <body class="content">
        <form action="Controller" method="post"> 
        <h1>functional significance types</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p> 
        <br>
        <table class="data">
            <tr>
                <th class="data" width="20%">Name</th>
                <th class="data" width="30%">Comment</th>
                <th class="data" width="10%">Functional sig.</th>
                <th class="data" width="10%">Models</th>
                <th class="data" width="10%">User</th>
                <th class="data" width="10%">Updated</th>
                <th class="data" width="10%">Details</th>                
            </tr>
            <m:iterate-collection collection="funcsigtypes">
                <tr class="#?alt#">
                    <td>#:getName#</td>
                    <td>#:getComm#</td>
                    <td><a href="Controller?workflow=ViewFuncSigs&_type=#:getFstid#" title="View functional significances for this type"><m:img name="jump"/></a></td>
                    <td><a href="Controller?workflow=ViewModels&_fstid=#:getFstid#" title="View models for this functional significance type"><m:img name="jump"/></a></td>
                    <td><a href="Controller?workflow=ViewUser&id=#:getUserId#" title="View the details for this user">#:getUserName#</a></td>
                    <td>#:getTs#</td>
                    <td><a href="Controller?workflow=ViewFuncSigType&fstid=#:getFstid#" title="View the details for this funtional significance type"><m:img name="view"/></a></td>
                </tr>
            </m:iterate-collection>
        </table>
       </form>
    </body>
</html>
