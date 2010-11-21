<%@page contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>add resource file</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'Name', 1, 255); 
                define('comm', 'string', 'Comment', null, 255); 
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>add resource file</h1>
        <p class="toolbar">&nbsp;</p>
        <form method=post enctype="multipart/form-data" action="Controller">
            <input type="hidden" name="id" value='<%=(String)request.getParameter("id")%>'/>
            <input type="hidden" name="catId" value='<%=(String)request.getParameter("catId")%>'/>
            <table>
                <tr>
                    <td><b>File</b></td>
                </tr>
                <tr>
                    <td><input type="file" name="file"></td>
                </tr>
                <tr>                                  
                    <td><b>Name</b></td>
                </tr>
                <tr>
                    <td><input type="text" size="35" maxlength="255" name="name"></td>
                </tr> 
                <tr>
                    <td><b>Category</b></td>
                </tr>
                <tr>
                    <td><m:checkbox name="catid" collection="categories" idGetter="getCatId" textGetter="getCatName"/></td>
                </tr>
                <tr>
                    <td><b>Comment</b></td>
                </tr>
                <tr>
                    <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/add.png" name="upload" value="Upload" onClick="validate();return returnVal;" title="add resource file">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
                        <%--td align="right">
                            <input id="button" type="submit" name="upload" value="Upload" onClick="validate();return returnVal;">
                            
                            <input id="button" type="submit" name="back" value="Cancel">  
                        </td--%>
        </form>
    </body>
</html>
