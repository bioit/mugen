<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<jsp:useBean id="umarker" scope="request" type="com.arexis.mugen.genotype.genotypemanager.UMarkerDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Unified Marker mappings</title>
    </head>
    <body>         
        <h1>Unified Marker mappings</h1>  
        <form action="Controller" method="post">      
            <input type="hidden" name="umid" value='<%=fdm.getValue("umid")%>'>
            Mapping for the unified marker: <jsp:getProperty name="umarker" property="name"/><br>
            <table class="data">
            <tr>	  	  	 
                <th class="data">Sampling unit</th>
                <th class="data">Marker</th>
                <th class="data">Delete</th>
                <th class="data">Allele</th>
            </tr>
            <m:iterate-collection collection="umapping">

            <tr class="#?alt#">
                <td>#:getSamplingunit#</td>
                <td>#:getMarker#</td>      
                <td><a href="Controller?workflow=DeleteUMarkerMapping&umid=#:getUmid#&mid=#:getMid#" onClick="confirm('Delete mapping?');"><m:img name="delete"/></a></td>
                <td><a href="Controller?workflow=ViewUMarkerMappingAlleles&umid=#:getUmid#&mid=#:getMid#">Allele</a></td>
            </tr>

            </m:iterate-collection>
            </table> 
            <p>                                                
                <input id="button" type="submit" name="back" value="Back">
                <input id="button" type="submit" name="create" value="Create">
            </p>   
        </form>
    </body>
</html>
