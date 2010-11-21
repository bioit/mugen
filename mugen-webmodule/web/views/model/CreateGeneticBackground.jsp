<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>create genetic background</title>
    </head>
    <body class="content">
        <h1>create genetic background</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td><b>DNA Origin</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileAddingGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new dna origin value">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="genBacks" name="dna_origin" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Targeted Background</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileAddingGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new targeted background value">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="genBacks" name="targeted_back" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Host Background</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileAddingGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new host background value">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="genBacks" name="host_back" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Backcrossing Strain</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileAddingGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new backcrossing strain value">&nbsp;[add new]</a></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="genBacks" name="backcrossing_strain" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Backcrosses</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="backcrosseCollection" name="backcrosses" idGetter="toString" textGetter="toString"/>
                        </td>
                    </tr>
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="create" value="Save" title="save genetic background">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>
 