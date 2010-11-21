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
   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        
        <% if (request.getAttribute("working").equals("TRUE")) { %>
            <meta http-equiv="refresh" content="5;URL=Controller?workflow=ViewImportJobs">
        <% } %>
        
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Import jobs for user</h1>
    <a href="Controller?workflow=RemoveAllImportJobs"><m:img name="delete_24" title="Remove all your import jobs for this project"/></a>
    <table>
    <tr><th>JobId</th><th>Status</th><th>File name</th><th>Operations</th></tr>
    <m:iterate-collection collection="jobs">
        <tr>
            <td>#:getJobId#</td>
            <td>#:getStatus# #:getProgress#</td>
            <td>#:getFileName#</td>
            <td>
                <a href="Controller?workflow=ViewJob&jobid=#:getJobId#"><m:img name="view"/></a>
                <a href="Controller?workflow=RunImportJob&jobid=#:getJobId#"><m:img name="import" title="Import job to database"/></a>
                <a href="Controller?workflow=ViewFile&fileid=#:getJobId#"><m:img name="document_view" title="View File"/></a>
                <a href="Controller?workflow=ViewImportJobReport&jobid=#:getJobId#"<m:img name="document_view" title="View Report"/>
            </td>
        </tr>
    </m:iterate-collection>
    </table>
    
    </body>
</html>
