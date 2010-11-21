
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
<jsp:useBean id="umarker" scope="request" type="com.arexis.mugen.genotype.genotypemanager.UMarkerDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Marker</title>
    </head>
    <body>
        <h1>View Unified Marker</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="umid" value='<jsp:getProperty name="umarker" property="umid"/>'/>
            <p>
                Species: <jsp:getProperty name="umarker" property="species"/><br>               
                <table class="data">  
                    <tr>
                        <th colspan="7" class="data" align="center">Current data</th>
                    </tr>
                    <tr>       
                        <th class="data">Name</th>
                        <th class="data">Alias</th>
                        <th class="data">Position</th>
                        <th class="data">Chromosome</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="umarker" property="name"/></td>
                        <td><jsp:getProperty name="umarker" property="alias"/></td>            
                        <td><jsp:getProperty name="umarker" property="position"/></td>
                        <td><jsp:getProperty name="umarker" property="chromosome"/></td>        
                        <td><jsp:getProperty name="umarker" property="comm"/></td>
                        <td><jsp:getProperty name="umarker" property="user"/></td>          
                        <td><jsp:getProperty name="umarker" property="updated"/></td>                         
                    <tr>
                    <tr>
                        <th colspan="7" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">

                        <tr class="#?alt#">
                            <td>#:getName#</td>
                            <td>#:getAlias#</td>
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
