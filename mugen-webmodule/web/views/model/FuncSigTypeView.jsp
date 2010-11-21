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
        <!--
        <p>
            <a href="Controller?workflow=ViewModels" title="Go to the listing over all models">Models</a>&nbsp>>
            <a href="Controller?workflow=ViewModel" title="Return to the current model">Model</a>&nbsp>>
            Functional Significance   
        </p>
        -->
        <table class="block">
            <th class="block" colspan="2">
                <m:hide privilege="MODEL_W" pid="<%=funcsigtype.getPid()%>">
                    <a href="Controller?workflow=EditFuncSigType&fstid=<jsp:getProperty name="funcsigtype" property="fstid"/>"><m:img name="edit" title="Edit this functional significance type"/></a>
                    <a href="Controller?workflow=RemoveFuncSigType&fstid=<jsp:getProperty name="funcsigtype" property="fstid"/>" onClick="return confirm('Remove functional signifiance type?')"><m:img name="delete" title="Remove this functional signifiance type"/></a>
                </m:hide>
                &nbsp;
            </th>
            <tr class="block">
                <td class="block">
                    <b>Functional significance type name</b><br> <jsp:getProperty name="funcsigtype" property="name"/>
                </td>
            </tr>
            <tr class="block">
                <td class="block">
                    <b>Comment</b><br> <jsp:getProperty name="funcsigtype" property="comm"/>
                </td>            
            </tr>  
            
            <tr class="block">
                <td class="block">                
                    <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="funcsigtype" property="userId"/>" title="View the details for this user"><jsp:getProperty name="funcsigtype" property="userName"/></a> 
                </td>            
            </tr> 
            <tr class="block">
                <td class="block">
                    <b>Last updated</b><br> <jsp:getProperty name="funcsigtype" property="ts"/>
                </td>            
            </tr>               
        </table>
    </form>
    
    </body>
</html>
