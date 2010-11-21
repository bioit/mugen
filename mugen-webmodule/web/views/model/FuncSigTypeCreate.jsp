    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">       
        </script>          
        <title>Create functional significance type</title>
    </head>
    <body onLoad="init()">
        <h1>Create functional significance type</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                   <tr>
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35"/></td>
                    </tr>                  
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="create" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>