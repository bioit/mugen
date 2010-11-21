<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="uvariablesetdto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.UVariableSetDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Variable Set</title>
    </head>
    <body>
        <h1>View Unified Variable Set</h1>
        <form action="Controller" method="post">
            <p>
                Species: <jsp:getProperty name="uvariablesetdto" property="speciesname"/><br>
                <table class="data">  
                    <tr>
                        <th colspan="4" class="data" align="center">Current data</th>
                    </tr>
                    <tr>                  
                        <th class="data">Value</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="uvariablesetdto" property="name"/></td>
                        <td><jsp:getProperty name="uvariablesetdto" property="comm"/></td>
                        <td><jsp:getProperty name="uvariablesetdto" property="user"/></td>
                        <td><jsp:getProperty name="uvariablesetdto" property="updated"/></td>                     
                    <tr>
                    <tr>
                        <th colspan="4" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">
                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getComm#</td>
                            <td>#:getUser#</td>
                            <td>#:getUpdated#</td>
                        </tr>

                    </m:iterate-collection>                    
                </table>
            </p>
            <p>                                
                <input id="button" type="submit" name="back" value="Back">
            </p>
        </form> 
    </body>
</html>
