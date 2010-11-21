<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>update mp terms</title>
    </head>
    <body class="content">
    <%--form method=post enctype="multipart/form-data" action="Controller"--%>
    <form method=post action="Controller">
        <h1>load mp terms</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/upload.png" name="upload" value="upload" title="upload">
            <input type="image" src="images/icons/cancel.png" name="back" value="back" title="back">
        </p>
            <%--table>
                    <tr>
                        <td align="right">
                            <input id="button" type="submit" name="upload" value="Upload">                        
                            <input id="button" type="submit" name="back" value="Cancel">  
                        </td>
                    </tr>                  
                </table--%>
    </form>
    </body>
</html>
