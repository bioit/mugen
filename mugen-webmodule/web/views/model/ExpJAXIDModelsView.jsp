<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>mugen mice with jax id</title>
    </head>
    <body class="content">
        <form action="Controller" method="post">
        <h1>mugen mice with jax id</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
        <table class="data">
            <tr>
                <th class="data" width="13%">MUGEN ID</th>
                <th class="data" width="29%">Common Line Name</th>
                <th class="data" width="14%">Mutation(s)</th>
                <th class="data" width="30%">Research Application</th>
                <th class="data" width="14%">Updated</th>
            </tr>
            <m:iterate-collection collection="modelsdto">
            <tr class="#?alt#">
                <td width="13%">#:getAccNr#</td>
                <td width="29%"><a href="Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid=#:getEid#">#:getLineName#</a></td>
                <td width="14%">#:getMutations#</td>
                <td width="30%">#:getResearchAppType#</td>
                <td width="14%">#:getTs#</td>
            </tr>
            </m:iterate-collection>
        </table>
       </form>
    </body>
</html>
