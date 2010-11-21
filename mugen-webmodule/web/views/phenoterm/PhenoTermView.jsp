<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Existing MP Terms</title>
    </head>
    <body>         
        <h1>Existing MP Term Descriptors</h1>
         
        <form action="Controller" method="post">
        
        <hr id="ruler">  
            <table class="data">
                <tr>                    
                    <th class="data" width="100%">MP Term Descriptors</th>
                </tr>
                <m:iterate-collection collection="mptermsonly">
                    <tr class="#?alt#">
                        <td>#:getPhenoTerm#</td>
                    </tr>
                </m:iterate-collection>
            </table>
       </form>
    </body>
</html>
