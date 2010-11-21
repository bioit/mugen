<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>logs</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
        <h1>logs</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            <input type="image" src="images/icons/reset.png" name="reset" value="reset" title="reset">
        </p>
        <table>
            <tr valign="bottom">
                <td>:action:</td>
                <td>:user:</td>
            </tr>
            <tr valign="top">
                <td><m:checkbox collection="mgnactions" onChange="this.form.submit()" name="mgnaction" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("mgnaction")%>" wildcardOption="true"/></td>
                <td><m:checkbox collection="mgnusers" onChange="this.form.submit()" name="user" idGetter="toString" textGetter="toString" selected="<%=fdm.getValue("user")%>" wildcardOption="true"/></td>
            </tr>
        </table>
       
        <hr id="ruler">  
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewSimpleLogs" showText='true'/>  
                    </td>
                </tr>
            </table>
            <table class="data">
                <tr>
                    <th class="data" width="15%">Action Class</th>
                    <th class="data" width="15%">User</th>
                    <th class="data" width="55%">What</th>
                    <th class="data" width="15%">Date</th>
                </tr>
                <m:iterate-collection collection="logsdto">
                    <tr class="#?alt#">
                        <td width="15%">#:getMgnaction#</td>
                        <td width="15%">#:getMgnuser#</td>
                        <td width="55%">#:getTxt#</td>
                        <td width="15%">#:getTs#</td>
                    </tr>
                </m:iterate-collection>
            </table>
            <table>
                <tr>
                    <td>
                      <m:navigation-buttons workflow="ViewSimpleLogs"/>  
                    </td>
                </tr>
            </table>
       </form>
    </body>
</html>
