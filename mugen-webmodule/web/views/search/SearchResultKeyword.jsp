<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>search results</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
        <h1>search results [<%=request.getAttribute("hits")%>&nbsp;hits]</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
        <br>
        <table class="data">
                <tr>
                    <th class="data">name</th>
                    <th class="data">type</th>
                    <th class="data">comment</th>
                </tr>
                <m:iterate-collection collection="results">
                <tr class="#?alt#">
                    <td width="20%"><a href="#:getWorkflow#">#:getName#</a></td>
                    <td width="10%">#:getType#</td>
                    <td width="70%">#:getComment#</td>
                </tr>
                </m:iterate-collection>
        </table>
    </form>
    </body>
</html>
