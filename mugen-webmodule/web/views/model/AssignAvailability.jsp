<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>assign availability</title>
    </head>
    <body class="content">
        <h1>assign availability</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td><b>Repository&nbsp;</b><a href="Controller?workflow=AddRepositoryWhileAddingAvgenBackInfo&eid=<%=request.getAttribute("eid")%>" class="menu" title="add new repository to the list">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="repositories" name="repositories" idGetter="getRid" textGetter="getReponame"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Available&nbsp;Genetic&nbsp;Background&nbsp;</b><a href="Controller?workflow=AddAvailableGeneticBackgroundWhileAddingAvgenBackInfo&eid=<%=request.getParameter("eid")%>" class="menu" title="add new genetic background to the list">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="avgenbacks" name="avgenbacks" idGetter="getAid" textGetter="getAvbacknamesss"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Strain&nbsp;State</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="states" name="state" idGetter="getId" textGetter="getName" selected="1006"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Strain&nbsp;Type</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="types" name="type" idGetter="getId" textGetter="getName" selected="1016"/>
                        </td>
                    </tr>
                </table>
                <br>
            <p class="toolbar">
                <input type="image" src="images/icons/assign.png" name="create" value="Assign" title="submit changes">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form> 
    </body>
</html>
 