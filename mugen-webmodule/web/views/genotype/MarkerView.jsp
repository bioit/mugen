
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
<jsp:useBean id="marker" scope="request" type="com.arexis.mugen.genotype.genotypemanager.MarkerDTO" />
<%@ page import="java.util.Collection" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Marker</title>
    </head>
    <body>
        <h1>View Marker</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="mid" value='<jsp:getProperty name="marker" property="mid"/>'/>
            <p>
                Species: <jsp:getProperty name="marker" property="species"/><br>
                Sampling Unit: <jsp:getProperty name="marker" property="samplingunit"/><br>                
                <table class="data">  
                    <tr>
                        <th colspan="9" class="data" align="center">Current data</th>
                    </tr>
                    <tr>       
                        <th class="data">Name</th>
                        <th class="data">Alias</th>
                        <th class="data">Primer 1</th>
                        <th class="data">Primer 2</th>
                        <th class="data">Position</th>
                        <th class="data">Chromosome</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="marker" property="name"/></td>
                        <td><jsp:getProperty name="marker" property="alias"/></td>        
                        <td><jsp:getProperty name="marker" property="p1"/></td>
                        <td><jsp:getProperty name="marker" property="p2"/></td>      
                        <td><jsp:getProperty name="marker" property="position"/></td>
                        <td><jsp:getProperty name="marker" property="chromosome"/></td>        
                        <td><jsp:getProperty name="marker" property="comm"/></td>
                        <td><jsp:getProperty name="marker" property="user"/></td>          
                        <td><jsp:getProperty name="marker" property="updated"/></td>                         
                    <tr>
                    <tr>
                        <th colspan="9" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">

                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getAlias#</td>
                            <td>#:getP1#</td>
                            <td>#:getP2#</td>
                            <td>#:getPosition#</td>
                            <td>#:getChromosome#</td>
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
