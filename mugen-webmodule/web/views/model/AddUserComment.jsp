<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>post comment</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('usercomm', 'string', 'Posted Comment',1 , 255);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>post comment</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td>
                            <% if(request.getParameter("eid")!=null){%>
                            <input type="hidden" name="eid" value='<%=request.getParameter("eid")%>'/>
                            <% } %>
                            <textarea type="text" cols="35" rows="6" maxlength="255" name="usercomm"></textarea>
                        </td>
                    </tr>
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="create" value="Create" onClick="validate();return returnVal;" title="save comment">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>

