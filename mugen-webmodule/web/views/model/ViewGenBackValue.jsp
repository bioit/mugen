<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>genetic background page</title>
    </head>
    <body class="content">
    <h1>genetic background</h1>
    <p class="toolbar">&nbsp;</p>
    
    <form action="Controller" method="post">
        <table>
            <tr>
                <td><%=request.getAttribute("genback")%></td>
            </tr>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Related MUGEN Mice</b></td>
            </tr>
            <m:iterate-collection collection="models">
            <tr class="#?alt#">
                <td><a class="menu" href="Controller?workflow=ViewModel&eid=#:getEid#" title="view mouse">#:getLineName#</a></td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
    </form>
    </body>
</html>
