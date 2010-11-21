
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
<jsp:useBean id="genotypedto" scope="request" type="com.arexis.mugen.genotype.genotypemanager.GenotypeDTO" />
<%@ page import="java.util.Collection" %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Genotype</title>
    </head>
    <body>
        <h1>View Genotype</h1>
        <form action="Controller" method="post">
            <p>
                Species: <jsp:getProperty name="genotypedto" property="species"/><br>
                Chromosome: <jsp:getProperty name="genotypedto" property="chromosome"/><br>
                Marker: <jsp:getProperty name="genotypedto" property="markerName"/><br>
                Sampling Unit: <jsp:getProperty name="genotypedto" property="suname"/><br>                
                Identity: <jsp:getProperty name="genotypedto" property="identity"/><br>
                <table class="data">  
                    <tr>
                        <th colspan="9" class="data" align="center">Current data</th>
                    </tr>
                    <tr>         
                        <th class="data">Allele 1</th>
                        <th class="data">Allele 2</th>
                        <th class="data">Raw1</th>
                        <th class="data">Raw2</th>
                        <th class="data">L</th>
                        <th class="data">Comment</th>
                        <th class="data">Reference</th>
                        <th class="data">User</th>
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="genotypedto" property="allele1"/></td>
                        <td><jsp:getProperty name="genotypedto" property="allele2"/></td>
                        <td><jsp:getProperty name="genotypedto" property="raw1"/></td>
                        <td><jsp:getProperty name="genotypedto" property="raw2"/></td>
                        <td><jsp:getProperty name="genotypedto" property="level"/></td>
                        <td><jsp:getProperty name="genotypedto" property="comm"/></td>        
                        <td><jsp:getProperty name="genotypedto" property="reference"/></td>
                        <td><jsp:getProperty name="genotypedto" property="user"/></td>
                        <td><jsp:getProperty name="genotypedto" property="updated"/></td>               
                    <tr>
                    <tr>
                        <th colspan="9" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">

                        <tr class="#?alt#">
                            <td>#:getAid1#</td>
                            <td>#:getAid2#</td>
                            <td>#:getRaw1#</td>
                            <td>#:getRaw2#</td>
                            <td>#:getLevel#</td>
                            <td>#:getComm#</td>
                            <td>#:getReference#</td>
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
