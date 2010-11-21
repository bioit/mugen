
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    String marker = (String)session.getAttribute("marker");  
    String markerid = (String)session.getAttribute("markerid");  
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Alleles</title>
    </head>
    <body>         
        <h1>View Alleles</h1>  
        <p>
        Marker: <%= marker %>
        </p>
        <form action="Controller" method="post">
        <input type="hidden" name="mid" value='<%= markerid %>'/>
        <table class="data">
        <tr>
            <th class="data">Name</th>
            <th class="data">Comment</th>
            <th class="data">Details</th>
            <th class="data">Edit</th>
        </tr>
        <m:iterate-collection collection="alleles">
	 
        <tr class="#?alt#">
            <td>#:getName#</td>
            <td>#:getComm#</td>
            <td><a href="Controller?workflow=ViewAllele&aid=#:getAid#"><m:img name="view"/></a></td>
            <td><a href="Controller?workflow=EditAllele&aid=#:getAid#"><m:img name="edit"/></a></td>
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
