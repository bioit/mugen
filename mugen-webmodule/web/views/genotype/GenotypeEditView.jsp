<%@ page import="java.util.Collection" %> 
<%@ page import="com.arexis.mugen.genotype.genotypemanager.GenotypeDTO" %> 
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
<%
    GenotypeDTO dto = (GenotypeDTO)request.getAttribute("genotypedto");
    String aid1 = ""+dto.getAid1();
    String aid2 = ""+dto.getAid2();
    String level = ""+dto.getLevel();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit Genotype</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('raw1', 'string', 'Raw 1', null, 20);                
                define('raw2', 'string', 'Raw 2', null, 20);
                define('reference', 'string', 'Reference', null, 32);
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script> 
    </head>
    <body onLoad="init()">
        <h1>Edit Genotype</h1>
        <form action="Controller" method="post">
            <p>
                <input type="hidden" name="mid" value='<jsp:getProperty name="genotypedto" property="mid"/>'/>
                <input type="hidden" name="iid" value='<jsp:getProperty name="genotypedto" property="iid"/>'/>
                <table>
                    <tr>
                        <td>Species: <jsp:getProperty name="genotypedto" property="species"/></td>
                    <tr>
                    <tr>
                        <td>Chromosome: <jsp:getProperty name="genotypedto" property="chromosome"/></td>
                    <tr>                    
                    <tr>
                        <td>Sampling Unit: <jsp:getProperty name="genotypedto" property="suname"/></td>
                    <tr>                                      
                        <td>Marker: <jsp:getProperty name="genotypedto" property="markerName"/></td>
                    </tr>
                    <tr>                                      
                        <td>Identity: <jsp:getProperty name="genotypedto" property="identity"/></td>
                    </tr>   
                    <tr>                                      
                        <td>Last updated by: <jsp:getProperty name="genotypedto" property="user"/>, <jsp:getProperty name="genotypedto" property="updated"/></td>                        
                    </tr>
                    <tr>
                        <td>Allele 1 <br>
                            <m:checkbox collection="alleles"  idGetter="getAid" textGetter="getName" name="aid1" selected="<%=aid1%>"/>
                        </td>
                    </tr>    
                    <tr>
                        <td>Allele 2 <br>
                            <m:checkbox collection="alleles"  idGetter="getAid" textGetter="getName" name="aid2" selected="<%=aid2%>"/>
                        </td>
                    </tr>    
                    <tr>
                        <td>Raw 1</td>
                    </tr>           
                    <tr>
                        <td><input type="text" name="raw1" size="35" value='<jsp:getProperty name="genotypedto" property="raw1"/>'/></td>
                    </tr>
                    <tr>
                        <td>Raw 2</td>
                    </tr>           
                    <tr>
                        <td><input type="text" name="raw2" size="35" value='<jsp:getProperty name="genotypedto" property="raw2"/>'/></td>
                    </tr>                    
                    <tr>
                        <td>Level <br>
                            <m:checkbox collection="levels"  idGetter="getLevel" textGetter="getLevel" name="level" selected="<%=level%>"/>
                        </td>
                    </tr>   
                    <tr>
                        <td>Reference</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="reference" size="35" value='<jsp:getProperty name="genotypedto" property="reference"/>'/></td>
                    </tr>                
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="genotypedto" property="comm"/></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="save" value="Save" onClick="validate();return returnVal;">                
                <input id="button" type="submit" name="back" value="Back">
                <input id="button" type="submit" name="remove" value="Remove" onClick="return confirm('Delete genotype?');">
            </p>
        </form> 
    </body>
</html>
