<% 
    boolean trans = true;
    if(request.getParameter("transgc")==null || request.getParameter("transgc").compareTo("1")!=0){
        trans = false; 
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>add gene/transgene</title>
    </head>
    <body class="content">
        <% if(trans){%>
        <h1>add transgene</h1>
        <% } else { %>
        <h1>add gene</h1>
        <% } %>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td><b>Name</b><a class="menu" href="Controller?workflow=CreateGeneFromModel&eid=<%=request.getParameter("eid")%>" title="add new">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="genes" name="gene" idGetter="getGaid" textGetter="getName"/>
                        </td>
                    </tr>
                </table>
                <p class="toolbar">
                    <input type="image" src="images/icons/add.png" name="save" value="Save" onClick="validate();return returnVal;" title="add to mouse">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>
 