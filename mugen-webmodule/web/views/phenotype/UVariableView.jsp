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
<jsp:useBean id="uvariabledto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.UVariableDTO" />
<%@ page import="java.util.Collection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Variable</title>
    </head>
    <body>
        <h1>View Unified Variable</h1>
        <form action="Controller" method="post">
            <p>
                Species: <jsp:getProperty name="uvariabledto" property="speciesname"/>
                <table class="data">  
                    <tr>
                        <th colspan="6" class="data" align="center">Current data</th>
                    </tr>
                    <tr>                  
                        <th class="data">Name</th>
                        <th class="data">Type</th>
                        <th class="data">Unit</th>
                        <th class="data">Comment</th>                        
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="uvariabledto" property="name"/></td>
                        <td><jsp:getProperty name="uvariabledto" property="type"/></td>
                        <td><jsp:getProperty name="uvariabledto" property="unit"/></td>
                        <td><jsp:getProperty name="uvariabledto" property="comm"/></td>
                        <td><jsp:getProperty name="uvariabledto" property="user"/></td>
                        <td><jsp:getProperty name="uvariabledto" property="updated"/></td>                     
                    <tr>
                    <tr>
                        <th colspan="6" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">
                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getType#</td>
                            <td>#:getUnit#</td>
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
