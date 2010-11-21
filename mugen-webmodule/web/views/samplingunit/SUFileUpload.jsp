<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <h1>File Upload</h1>    
        <form method=post enctype="multipart/form-data" action="Controller">
            <p>
                <table>       
                    <tr>                                  
                        <td>File</td>
                    </tr>
                    <tr>
                        <td><input type="file" name="file"></td>
                    </tr>                            
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>
                    </tr>                     
                    <tr>
                        <td>&nbsp</td>
                    </tr>
                    <tr>
                        <td align="right">
                            <input id="button" type="submit" name="back" value="Back">  
                            <input id="button" type="submit" name="upload" value="Upload">
                        </td>
                    </tr>                  
                </table>  
            </p>
        </form>
    </body>
</html>
