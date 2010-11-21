<jsp:useBean type="com.arexis.mugen.model.modelmanager.StrainAlleleDTO" id="allele" scope="request"/>
<%
     com.arexis.mugen.model.modelmanager.StrainAlleleDTO sadto = (com.arexis.mugen.model.modelmanager.StrainAlleleDTO)request.getAttribute("allele");
     int geneid = allele.getGeneId();
   %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>allele</title>
    </head>
    <body class="content">
        <h1>allele</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
            <table>
                <tr>
                    <td><b>Name:&nbsp;</b><jsp:getProperty name="allele" property="name"/></td>
                </tr>
                <tr>
                    <td><b>Symbol:&nbsp;</b><jsp:getProperty name="allele" property="symbol"/></td> 
                </tr>
                <tr>
                    <td><b>MGI ID:&nbsp;</b><jsp:getProperty name="allele" property="mgilink"/></td>
                </tr>
                <tr>
                    <td><b>Gene Involved:&nbsp;</b><a href="Controller?workflow=ViewGene&gaid=<jsp:getProperty name="allele" property="geneId"/>" title="view gene"><jsp:getProperty name="allele" property="geneSymbol"/></a></td>
                </tr>
                <tr>
                    <td><b>Mutation(s):&nbsp;</b><jsp:getProperty name="allele" property="mutations"/></td>
                </tr>
                <tr>
                    <td><b>Attribute:&nbsp;</b><jsp:getProperty name="allele" property="attributes"/></td>
                </tr>
            </table>
            <br>
            <table width="100%">
                <tr>
                    <td><b>Related MUGEN Mice</b></td>
                </tr>
                <m:iterate-collection collection="models">
                <tr class="#?alt#">
                    <td><a class="menu" href="Controller?workflow=ViewModel&eid=#:getEid#" title="view mouse">#:getLineName#</a></td>
                </tr>
                </m:iterate-collection>
            </table>
            <br>
            <table width="100%">
                <tr>
                    <td><b>Related MP Terms</b></td>
                </tr>
                <m:iterate-collection collection="mpts">
                <tr class="#?alt#">
                    <td>
                        <b>MP Term:&nbsp;</b>#:getMpnameshortlink#
                        <hr id="rulernew">
                        <b>PATO Logical Definition:&nbsp;</b>#:getPld#
                    </td>
                </tr>
                <tr height="15px"><td>&nbsp;</td></tr>
                </m:iterate-collection>
            </table>
            <br>
            <p class="toolbar">
               <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            </p>
        </form>
    </body>
</html>
