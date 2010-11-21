<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>genetic backgrounds</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
            <h1>genetic backgrounds</h1>
            <p class="toolbar">
                <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            </p> 
            <br>
            <table class="data">
                <tr>                    
                    <th class="data" width="10%">ID</th>
                    <th class="data" width="5%">Mice</th>
                    <th class="data" width="75%">Background</th>
                    <th class="data" width="10%">&nbsp;</th>
                </tr>
                <m:iterate-collection collection="genBacks">
                    <tr class="#?alt#">                        
                        <td>#:getBid#</td>
                        <td><a href="Controller?workflow=ViewGeneticBackgroundValue&bid=#:getBid#" title="view mice">#:getModelcount#</a></td>
                        <td><a href="Controller?workflow=EditGeneticBackgroundValue&bid=#:getBid#" title="edit background">#:getSsbackname#</a></td>
                        <td><a class="menu" href="Controller?workflow=RemoveGeneticBackgroundValue&bid=#:getBid#" title="delete background" onClick="return confirm('delete background?')">[delete]</a></td>
                    </tr>
                </m:iterate-collection>
            </table>
    </form>
    </body>
</html>
