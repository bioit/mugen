<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="modeldto" scope="request" type="com.arexis.mugen.model.modelmanager.ExpModelDTO" />
<jsp:useBean id="genbackdto" scope="request" type="com.arexis.mugen.model.modelmanager.GeneticBackgroundDTO" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit genetic background</title>
    </head>
    <body class="content">
        <h1>edit genetic background</h1>
        <p class="toolbar">
        <a href="Controller?workflow=EditGeneticBackground&eid=<%=modeldto.getEid()%>&model.info=true"><m:img name="info_24" title="help & info"/></a>
        </p>
    <m:hide-block name="model.info">
        <table class="info">
            <td>
                This page displays all genetic background information for a mutant. You can edit information related to
                <b>DNA origin</b>, <b>targeted background</b>, <b>host background</b> and even <b>backcrossing strain</b> and <b>number of backcrosses</b>.
                You can choose the appropriate strain/number of backcrosses from the drop down menus, while you can see the previously entered data on the row above. 
            </td>
        </table>
    </m:hide-block>  
    <form action="Controller" method="post">
        <input type="hidden" name="eid" value='<jsp:getProperty name="modeldto" property="eid"/>'/>
              <table class="block_data">
                   <tr>
                        <td><b>DNA Origin</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileEditGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new dna origin value">&nbsp;[add new]</a></td>
                   </tr>
                   <tr>
                        <td>
                            <m:checkbox collection="genBacks" selected='<%=genbackdto.getDna_origin()%>' name="dna_origin" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                   </tr>
                   <tr>
                        <td><b>Targeted Background</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileEditGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new targeted background value">&nbsp;[add new]</a></td>
                   </tr>
                   <tr>
                        <td>
                            <m:checkbox collection="genBacks" selected='<%=genbackdto.getTargeted_back()%>' name="targeted_back" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                   </tr>
                   <tr>
                        <td><b>Host Background</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileEditGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new host background value">&nbsp;[add new]</a></td>
                   </tr>
                   <tr>
                        <td>
                            <m:checkbox collection="genBacks" selected='<%=genbackdto.getHost_back()%>' name="host_back" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                   </tr>
                   <tr>
                        <td><b>Backcrossing Strain</b><a class="menu" href="Controller?workflow=CreateGeneticBackgroundValueWhileEditGeneticBackgroundInfo&eid=<%=request.getAttribute("eid")%>&bid=null" title="add new backcrossing strain value">&nbsp;[add new]</a></td>
                   </tr>
                   <tr>
                        <td>
                            <m:checkbox collection="genBacks" selected='<%=genbackdto.getBackcrossing_strain()%>' name="backcrossing_strain" idGetter="getBid" textGetter="getBackname"/>
                        </td>
                   </tr>
                   <tr>
                        <td><b>Backcrosses</b></td>
                   </tr>
                   <tr>
                        <td>
                            <m:checkbox collection="backcrosseCollection" selected='<%=genbackdto.getBackcrosses()%>' name="backcrosses" idGetter="toString" textGetter="toString"/>
                        </td>
                   </tr>
        </table>
        <br>
        <p class="toolbar">
            <input type="image" src="images/icons/save.png" name="create" value="Save" title="submit changes">
            <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
        </p>
    </form>
    </body>
</html>
