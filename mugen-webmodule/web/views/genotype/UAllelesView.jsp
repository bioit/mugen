
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Collection" %>
<%
    String umarker = (String)session.getAttribute("umarker");  
    String umarkerid = (String)session.getAttribute("umarkerid");  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Alleles</title>
    </head>
    <body>         
        <h1>Unified Alleles</h1>  
        <p>
            Unified Marker: <%= umarker %>
        </p>
        <form action="Controller" method="post">
        <input type="hidden" name="umid" value='<%= umarkerid %>'/>
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Comment</th>
            <th class="data">Details</th>
            <th class="data">Edit</th>
        </tr>
        <m:iterate-collection collection="ualleles">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td><a href="Controller?workflow=ViewUAllele&uaid=#:getUaid#"><m:img name="view"/></a></td>
            <td><a href="Controller?workflow=EditUAllele&uaid=#:getUaid#"><m:img name="edit"/></a></td>
        </tr>
        
        </m:iterate-collection>
        </table> 
        <p>
        <input id="button" type="submit" name="back" value="Back">         
        <input id="button" type="submit" name="create" value="Create">       
        </p>
        </form>         
    </body>
</html>
