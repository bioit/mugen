<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>add link</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 35);
                define('url', 'string', 'URL', 8, 255);
                define('comm', 'string', 'Comment', null, 255);
            }     
        </script>         
    </head>
    <body onLoad="init()" class="content">
        <h1>add link</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
        <input type="hidden" name="id" value='<%=(String)request.getParameter("id")%>'/>
        <input type="hidden" name="catId" value='<%=(String)request.getParameter("catId")%>'/>
            <table>
                    <tr>                                  
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" maxlength="255"/></td>
                    </tr>
                    <tr>
                        <td><b>URL</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="url" size="35" maxlength="255" value="http://"/></td>
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
               <input type="image" src="images/icons/add.png" name="create" value="Save" onClick="validate();return returnVal;" title="add link">
               <input type="image" src="images/icons/cancel.png" name="back" value="Back" title="cancel">
            </p>
        </form> 
    </body>
</html>