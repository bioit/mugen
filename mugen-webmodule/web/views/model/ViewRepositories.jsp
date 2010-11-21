<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>repositories</title>
    </head>
    <body class="content">
        <form action="Controller" method="post">
            <h1>repositories</h1>
            <p class="toolbar">
                <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            </p>
            <br>
            <table class="data">
                <tr>                    
                    <th class="data" width="25%">ID</th>
                    <th class="data" width="55%">Repository</th>
                    <th class="data" width="20%">&nbsp;</th>
                </tr>
                <m:iterate-collection collection="repositories">
                    <tr class="#?alt#">                        
                        <td>#:getRid#</td>
                        <td><a href="Controller?workflow=EditRepository&rid=#:getRid#" title="edit repository">#:getReponame#</a></td>
                        <td><a class="menu" href="Controller?workflow=RemoveRepository&rid=#:getRid#" title="remove repository" onClick="return confirm('remove repository?')">[remove]</a></td>
                    </tr>
                </m:iterate-collection>
            </table>
       </form>
    </body>
</html>
