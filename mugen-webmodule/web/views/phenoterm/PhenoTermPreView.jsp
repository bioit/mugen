<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>Check MP Term Descriptors</title>
    </head>
    <body>
        <h1>Check MP Term Descriptors</h1>    
        <form method=post enctype="multipart/form-data" action="Controller">
            <p>
                <table>
                    <tr>
                        <td align="right">
                            <input id="button" type="submit" name="upload" value="Upload">                        
                            <input id="button" type="submit" name="back" value="Cancel">  
                        </td>
                    </tr>                  
                </table>  
            </p>
        </form>
    </body>
</html>
