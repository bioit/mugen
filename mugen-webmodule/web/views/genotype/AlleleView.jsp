
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
<jsp:useBean id="allele" scope="request" type="com.arexis.mugen.genotype.genotypemanager.AlleleDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Allele</title>
    </head>
    <body>
        <h1>View Allele</h1>
        <form action="Controller" method="post">
            <input type="hidden" name="mid" value='<jsp:getProperty name="allele" property="mid"/>'/>
            <p>
                Species: <jsp:getProperty name="allele" property="species"/><br>
                Chromosome: <jsp:getProperty name="allele" property="chromosome"/><br>
                Sampling Unit: <jsp:getProperty name="allele" property="samplingUnit"/><br>                
                Marker: <jsp:getProperty name="allele" property="marker"/><br>
                <table class="data">  
                    <tr>
                        <th colspan="4" class="data" align="center">Current data</th>
                    </tr>
                    <tr>         
                        <th class="data">Name</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="allele" property="name"/></td>
                        <td><jsp:getProperty name="allele" property="comm"/></td>        
                        <td><jsp:getProperty name="allele" property="user"/></td>
                        <td><jsp:getProperty name="allele" property="updated"/></td>               
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
                <a href="Controller?workflow=ViewMarkerAlleles&mid=<jsp:getProperty name="allele" property="mid"/>"><input id="button" type="button" name="back" value="Back"></a>
            </p>
        </form> 
    </body>
</html>
