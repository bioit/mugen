    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create Marker set</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Create Marker set</h1>
        <form action="Controller" method="post">
            <p>                                  
                <table> 
                    <tr>
                        <td>
                            Sampling unit <br><m:su-combobox/>                            
                        </td>   
                    </tr>             
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
                <input id="button" type="submit" name="create" value="Save" onClick="validate();return returnVal;">                
                <input id="button" type="submit" name="back" value="Back">
                <input id="button" type="submit" name="back" value="Cancel">
            </p>
        </form> 
    </body>
</html>
