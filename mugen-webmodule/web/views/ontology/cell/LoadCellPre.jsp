<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>load cell terms</title>
    </head>
    <body class="content" onLoad="hideFlash()">
    <form method=post action="Controller">
        <h1>load cell terms</h1>
        <p class="toolbar">&nbsp;</p>
        <table>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>Import cell terms.</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
        </table>
        <p class="toolbar">
            <input type="image" src="images/icons/upload.png" name="upload" value="upload" title="upload">
            <input type="image" src="images/icons/cancel.png" name="back" value="back" title="back">
        </p>
    </form>
    </body>
</html>
