<%@page contentType="text/html"%>
<%@ page import="java.util.Collection" %>
<%
    com.arexis.mugen.project.ParamDataObject pdo = (com.arexis.mugen.project.ParamDataObject)request.getAttribute("paramdataobject");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>        
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Genotype level update</h1>
        <form action="Controller" method="post">   
            <input type="hidden" name="ignore.level" value="<%=pdo.getValue("g.level_")%>">
            <input type="hidden" name="g.suid" value="<%=pdo.getValue("g.suid")%>">
            <input type="hidden" name="identity" value='<%=pdo.getValue("identity")%>'/>
            <input type="hidden" name="chromosome" value='<%=pdo.getValue("chromosome")%>'/>
            <input type="hidden" name="g.reference" value='<%=pdo.getValue("g.reference")%>'/>
            <input type="hidden" name="marker" value='<%=pdo.getValue("marker")%>'/>
            <input type="hidden" name="allele1" value='<%=pdo.getValue("allele1")%>'/>
            <input type="hidden" name="allele2" value='<%=pdo.getValue("allele2")%>'/>
            <input type="hidden" name="datefrom" value='<%=pdo.getValue("datefrom")%>'/>
            <input type="hidden" name="dateto" value='<%=pdo.getValue("dateto")%>'/>
            <input type="hidden" name="usr" value='<%=pdo.getValue("usr")%>'/>
            <table>             
            <tr>
                <td>Level <br>
                    <m:checkbox collection="levels" idGetter="getLevel" textGetter="getLevel" name="g.level_" selected="<%=pdo.getValue("g.level_")%>"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <input id="button" type="submit" name="back" value="Back">                
                    <input id="button" type="submit" name="collect.getgenotypesaction.display" value="Update">
                </td>  
            </tr>
        </form>
        </table>    
    </body>
</html>
