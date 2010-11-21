<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>install mp terms</title>
    </head>
    <body class="content">
    <form method=post action="Controller">
        <h1>install mp terms</h1>
        <p class="toolbar">&nbsp;</p>
        <table class="block_data">
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td><%=(String)request.getAttribute("total")%></b> MP Terms need replacement.</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <m:iterate-collection collection="altmps">
                <tr>
                    <th class="data2">model #:getEid# <a class="menu" href="Controller?workflow=removeoldMP&eid=#:getEid#&mp=#:getOldmpath#" title="remove old mp term" onClick="return confirm('remove mp term without replacing?')">[delete]</a></th>
                </tr>
                <tr>
                    <th class="data3">#:getOldmpathtrans#</th>
                </tr>
                <tr class="#?alt#">
                    <td>&nbsp;#:getAltpathstr#</td>
                </tr>
            </m:iterate-collection>              
        </table>
        <br>
        <p class="toolbar">
            <input type="image" src="images/icons/finish.png" name="upload" value="upload" title="upload">
            <%--input type="image" src="images/icons/cancel.png" name="back" value="back" title="back"--%>
        </p>
    </form>
    </body>
</html>
