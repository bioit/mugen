<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit group</title>   
    </head>
    <body>

    <h1>Genetic modification</h1>
    <table class="block">
        <th class="block" colspan="2">
            Properties <a href="Controller?workflow=EditGenMod"><m:img name="edit"/></a>
        </th>
        <tr>
            <td>
                <b>Modification name</b><br> XC78A
            </td>
        </tr>
        <tr>
            <td>
                <b>Comment</b><br> No comment
            </td>            
        </tr>              
    </table>
    <br>
    <table class="block">
        <th class="block">
            + Files
        </th>    
        <tr>
            <td>    
        
            </td>
        </tr>        
    </table>
    <br>
    <table class="block">
        <th class="block">
            - Gene ontology
        </th>
        <tr>
            <td>    
                
                <p><input id="button" type="submit" name="back" value="Add"></p>
                <table class="block_data">
                <tr>
                    <th class="data">GO nr.</th> 
                    <th class="data">Comment</th> 
                    <th class="data">Edit</th>
                    <th class="data">Remove</th>
                </tr>

                <tr class="alternatingOne">
                    <td><a href="http://www.godatabase.org?view=details&query=GO:0044419" target="_blank">GO:0044419</a></td>
                    <td>No comment</a></td>
                    <td><a href="Controller?workflow=EditModelGO"><m:img name="edit"/></a></td>            
                    <td><a href="Controller?workflow=DeleteModelGO" onClick="confirm('Delete GO?');"><m:img name="delete"/></a></td>            
                </tr>
                <tr class="alternatingTwo">
                    <td><a href="http://www.godatabase.org?view=details&query=GO:0044420" target="_blank">GO:0044420</a></td>
                    <td>No comment</a></td>
                    <td><a href="Controller?workflow=EditModelGO"><m:img name="edit"/></a></td>            
                    <td><a href="Controller?workflow=DeleteModelGO" onClick="confirm('Delete GO?');"><m:img name="delete"/></a></td>            
                </tr>
                <tr class="alternatingOne">
                    <td><a href="http://www.godatabase.org?view=details&query=GO:0044421" target="_blank">GO:0044421</a></td>
                    <td>No comment</a></td>
                    <td><a href="Controller?workflow=EditModelGO"><m:img name="edit"/></a></td>            
                    <td><a href="Controller?workflow=DeleteModelGO" onClick="confirm('Delete GO?');"><m:img name="delete"/></a></td>            
                </tr>                
                </table>  
            </td>
        </tr>   
    </table>  
    <form action="Controller" method="post">
        <p>
            <input id="button" type="submit" name="back" value="Back">
        </p>
    </form>
    </body>
</html>
