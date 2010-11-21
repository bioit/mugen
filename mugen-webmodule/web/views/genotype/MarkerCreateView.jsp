<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create Marker</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('p1', 'string', 'P1', null, 40);                
                define('p2', 'string', 'P2', null, 40);
                define('alias', 'string', 'Alias', null, 20);
                define('name', 'string', 'Name', 1, 20);
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script>         
    </head>
    <body onLoad="init()">
        <h1>Create Marker</h1>
        <form action="Controller" method="post">
            <p>                                    
                <table>
                    <tr>
                        <td>
                            Sampling unit <br><m:su-combobox onChange="this.form.submit();"/>                            
                            <input id="button" type=submit name="suids" value="Set">
                        </td>   
                    </tr>
                    <tr>                                    
                        <td>Chromosome <br>
                            <m:checkbox collection="chromosomes" idGetter="getCid" textGetter="getName" name="cname" selected="<%=fdm.getValue("chromsome")%>"/>
                        </td>                    
                    </tr>                
                    <tr>                    
                        <td>Name</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" value='<%=fdm.getValue("name")%>'/></td>
                    </tr>
                    <tr>                    
                        <td>Alias</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="alias" size="35" value='<%=fdm.getValue("alias")%>'/></td>
                    </tr>        
                    <tr>                    
                        <td>Position</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="position" size="35" value='<%=fdm.getValue("position")%>'/></td>
                    </tr>           
                     <tr>                    
                        <td>Primer 1</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="p1" size="35" value='<%=fdm.getValue("p1")%>'/></td>
                    </tr>
                    <tr>                    
                        <td>Primer 2</td>
                    </tr>
                    <tr>
                        <td><input type="text" name="p2" size="35" value='<%=fdm.getValue("p2")%>'/></td>
                    </tr>                    
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><%=fdm.getValue("comm")%></textarea></td>
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
