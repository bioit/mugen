
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit group</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 255);                
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Create Grouping of family</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Sampling unit <br>
                            <m:su-combobox onChange="this.form.submit()"/>
                            <input id="button" type="submit" name="set" value="Set">
                        </td>
                    </tr>
                    <tr>
                        <td>Father<br>
                        <m:checkbox collection="males" name='father' idGetter='getIid' textGetter='getName' selected='<%=fdm.getValue("father")%>'/></td>
                    </tr>                    
                    <tr>
                        <td>Mother<br>
                        <m:checkbox collection="females" name='mother' idGetter='getIid' textGetter='getName' selected='<%=fdm.getValue("mother")%>'/></td>
                    </tr>                      
                    <tr>                                  
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value="<%= fdm.getValue("name")%>"/></td>
                    </tr>
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><%= fdm.getValue("comm")%></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="create" cancelName="back" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>

