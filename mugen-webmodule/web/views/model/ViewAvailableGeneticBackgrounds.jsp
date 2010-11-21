<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>available backgrounds</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
            <h1>available backgrounds</h1>
            <p class="toolbar">
                <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            </p>
            <br>
            <table class="data">
                <tr>                    
                    <th class="data" width="25%">ID</th>
                    <th class="data" width="55%">Background</th>
                    <th class="data" width="20%">&nbsp;</th>
                </tr>
                <m:iterate-collection collection="avgenbacks">
                    <tr class="#?alt#">                        
                        <td>#:getAid#</td>
                        <td><a href="Controller?workflow=EditAvailableGeneticBackground&aid=#:getAid#" title="edit background">#:getAvbacknamess#</a></td>
                        <td><a class="menu" href="Controller?workflow=RemoveAvailableGeneticBackground&aid=#:getAid#" title="remove background" onClick="return confirm('remove background?')">[remove]</a></td>
                    </tr>
                </m:iterate-collection>
            </table>
    </form>
    </body>
</html>
