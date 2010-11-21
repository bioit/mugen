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
<%
    com.arexis.arxframe.WebFormDataManager fdm = (com.arexis.arxframe.WebFormDataManager)request.getAttribute("formdata");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create Genotype</title>
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
        <h1>Create Genotype</h1>
        <form action="Controller" method="post">
            <p>
                <table>
                    <tr>
                        <td>Sampling unit <br>
                            <m:su-combobox onChange="this.form.submit()"/>
                        </td>                    
                        <td>Chromosome <br>
                            <m:checkbox collection="chromosomes"  idGetter="getCid" textGetter="getName" name="cid" selected='<%=fdm.getValue("cid")%>' onChange="this.form.submit()"/>
                        </td>
                    </tr>  
                    <tr>
                        <td>Marker <br>
                            <m:checkbox collection="markers"  idGetter="getMid" textGetter="getName" name="mid" selected='<%=fdm.getValue("mid")%>'/>
                        </td>
                        <td>Identity <br>
                            <m:checkbox collection="individuals"  idGetter="getIid" textGetter="getIdentity" name="iid" selected='<%=fdm.getValue("iid")%>'/>
                        </td>
                    </tr>                      
                    <tr>
                        <td>Allele 1 <br>
                            <m:checkbox collection="alleles"  idGetter="getAid" textGetter="getName" name="aid1" selected='<%=fdm.getValue("aid1")%>'/>
                        </td>
                        <td>Allele 2 <br>
                            <m:checkbox collection="alleles"  idGetter="getAid" textGetter="getName" name="aid2" selected='<%=fdm.getValue("aid2")%>'/>
                        </td>
                    </tr>    
                    <tr>
                        <td colspan="2">Raw 1</td>
                    </tr>           
                    <tr>
                        <td colspan="2"><input type="text" name="raw1" size="35" value='<%=fdm.getValue("raw1")%>'/></td>
                    </tr>
                    <tr>
                        <td colspan="2">Raw 2</td>
                    </tr>           
                    <tr>
                        <td colspan="2"><input type="text" name="raw2" size="35" value='<%=fdm.getValue("raw2")%>'/></td>
                    </tr>                    
                    <tr>
                        <td colspan="2">Level <br>
                            <m:checkbox collection="levels"  idGetter="getLevel" textGetter="getLevel" name="level" selected='<%=fdm.getValue("level")%>'/>
                        </td>
                    </tr>   
                    <tr>
                        <td colspan="2">Reference</td>
                    </tr>               
                    <tr>
                        <td colspan="2"><input type="text" name="reference" size="35" value='<%=fdm.getValue("reference")%>'/></td>
                    </tr>                
                    <tr>
                        <td colspan="2">Comment</td>
                    </tr>
                    <tr>
                        <td colspan="2"><textarea type="text" cols="35" rows="6" name="comm"><%=fdm.getValue("comm")%></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <input id="button" type="submit" name="new" value="Save" onClick="validate();return returnVal;">                
                <input id="button" type="submit" name="back" value="Back">
                <input id="button" type="submit" name="back" value="Cancel">
                <input id="button" type="submit" name="reset" value="Reset">
            </p>
        </form> 
    </body>
</html>
