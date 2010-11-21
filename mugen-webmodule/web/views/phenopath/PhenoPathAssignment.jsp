<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");
    com.arexis.form.FormDataManager fdmEID = (com.arexis.form.FormDataManager)request.getAttribute("formdataEID");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>assign mp term</title>
    </head>
    <body class="content">
        <h1>assign mp term</h1>
        <p class="toolbar">
            <a href="Controller?workflow=ViewModel&eid=<%=fdmEID.getValue("eid")%>" title="cancel"><img src="images/icons/nav_left_blue_24.png"></a>
        </p>
        <form action="Controller" method="post">
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                <td valign="top" width="50%">
                <table class="block_data">
                    <tr>
                        <th class="data2" width="95%">Assigned MP Term(s)</th>
                        <th class="data2" width="5%">&nbsp;</th>
                    </tr>
                    <m:iterate-collection collection="phenoPaths"> 
                    <tr class="#?alt#">
                        <td>#:getMpnameshort#</td>
                        <td>
                            <a class="menu" href="Controller?workflow=RemovePhenoPath&eid=<%=fdmEID.getValue("eid")%>&ppid=#:getPathid#" onClick="return confirm('unassign mp term?')" title="unassign mp term">&nbsp;[-]</a>
                        </td>
                    </tr>
                    </m:iterate-collection>
                </table>
                </td>
                <td width="5px">&nbsp;</td>
                <td valign="top" width="50%">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <m:checkbox collection="roots" emptyOption="true" onChange="this.form.submit()" name="LO" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("LO")%>"/>
                        </td>
                    </tr>
                    <m:iterate-collection collection="rootpaths"> 
                    <tr class="#?alt#">
                        <td><a class="menu" href="Controller?workflow=AddPhenoPath&eid=<%=fdmEID.getValue("eid")%>&ppid=#:getPathid#" onClick="return confirm('add mp term?')" title="add mp term">[+]&nbsp;</a>#:getMptreenode#</td>
                    </tr>
                    </m:iterate-collection>
                </table>
                <%--
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/assign.png" name="ass" value="Assign" title="assign mp term">
                    <a href="Controller?workflow=ViewModel&eid=<%=fdmEID.getValue("eid")%>" title="cancel"><img src="images/icons/nav_left_blue_24.png"></a>
                </p>
                --%>
                </td>
                </tr>
            </table>
        </form> 
    </body>
</html>
 