<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="linkdto" scope="request" class="com.arexis.mugen.resource.resourcemanager.LinkDTO" />
<jsp:useBean id="resource" scope="request" class="com.arexis.mugen.resource.resourcemanager.ResourceDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit link</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'Name', 1, 255);
                define('url', 'string', 'URL', 8, 255);
                define('comm', 'string', 'Comment', null, 255);
            }    
        </script>         
    </head>
    <body onLoad="init()" class="content">
        <h1>edit link</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
            <input type="hidden" name="resourceId" value='<jsp:getProperty name="resource" property="resourceId"/>'/>
            <input type="hidden" name="id" value='<%=(String)request.getAttribute("id")%>'/>
                <table>
                    <tr>                                      
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" maxlength="255" value='<jsp:getProperty name="resource" property="resourceName"/>'/></td>
                    </tr>
                    <tr>
                        <td><b>URL</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="url" size="35" maxlength="255" value='<jsp:getProperty name="resource" property="resourceLink"/>'/></td>
                    </tr>
                    <tr>
                        <td><b>Category</b></td>
                    </tr>
                    <tr>
                        <td><m:checkbox name="catid" collection="categories" idGetter="getCatId" textGetter="getCatName" selected="<%=resource.getCategoryId()%>"/></td>
                    </tr>
                    <tr>
                        <td><b>Comment</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="resource" property="resourceComment"/></textarea></td>
                    </tr>                    
                </table>
                <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" title="save changes" onClick="validate();return returnVal;">
                <input type="image" src="images/icons/cancel.png" name="back" value="Back" title="cancel">
            </p>
        </form> 
    </body>
</html>