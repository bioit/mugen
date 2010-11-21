<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");
%>  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>strain types</title>
    </head>
    <body class="content">
        <form action="Controller" method="post">
        <h1>strain types</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
        <br>
            <table class="data">
                <tr>
                    <th class="data" width="20%">Name</th>
                    <th class="data" width="20%">Abbreviation</th>
                </tr>
                <m:iterate-collection collection="straintypes">
                    <tr class="#?alt#">
                        <td width="20%">#:getName#</td>
                        <td width="20%">#:getAbbreviation#</td>
                    </tr>
                </m:iterate-collection>
            </table> 
       </form>
    </body>
</html>
