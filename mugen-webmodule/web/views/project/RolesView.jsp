<%--
    Mugen JSP View.
    
--%>

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
<%--
Example of a jsp:useBean:
<jsp:useBean id="ind" scope="request" type="com.arexis.mugen.samplingunit.individualmanager.IndividualDO"/>

--%>
<jsp:useBean id="roles" scope="request" type="java.util.Collection"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>View roles</h1>
    
    
   <table class=data>
   <tr>
        <th class=data>Role</th><th class=data>Comment</th><th class=data>Edit</th>
   </tr>
   <m:iterate-collection collection="<%=roles%>">
   <tr class="data #?alt#">
        <td>#:getName#</td>
        <td>#:getComm#</td>
        <td><a href="Controller?workflow=EditRole&rid=#:getRid#"><m:img name="edit" title="Edit role"/></a></td>
   </tr>
   </m:iterate-collection>
   </table>
    
    <%--
        Example to get a property
        <jsp:getProperty name="ind" property="iid"/>
    --%>
    
    </body>
</html>
