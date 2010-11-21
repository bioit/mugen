<%--
    Mugen JSP View.
    
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<jsp:useBean id="funcsigtype" scope="request" type="com.arexis.mugen.model.modelmanager.FunctionalSignificanceTypeDTO" />   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Functional Significance type</h1>
    
    <table class="info">
        <td>
            This is the detailed view of a Functional Significance type entry.
        </td>
    </table>
    
    <form action="Controller" method="post">
        
        
    
        <table>
            <tr>
                <td>
                    <b>Functional significance type name</b><br><input type="text" name="name" value="<jsp:getProperty name="funcsigtype" property="name"/>">
                </td>
            </tr>
            <tr>
                <td>
                    <b>Comment</b><br><textarea name="comm" rows="6" cols=40><jsp:getProperty name="funcsigtype" property="comm"/></textarea> 
                </td>            
            </tr>  
            
        </table>
        <m:save saveName="save"/>
    </form>
    
    </body>
</html>
