<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean type="com.arexis.mugen.model.modelmanager.StrainAlleleDTO" id="strainallele" scope="request"/>
<%
     com.arexis.mugen.model.modelmanager.StrainAlleleDTO sadto = (com.arexis.mugen.model.modelmanager.StrainAlleleDTO)request.getAttribute("strainallele");
     int geneid = strainallele.getGeneId();
   %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>edit allele & mutation type(s)</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'allele name', 1 , 255);
                define('symbol', 'string', 'allele symbol', 1 , 255);
                define('mgiid', 'num', 'MGI ID', 0, 35);
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>edit allele & mutation type(s)</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
            <table>
                <tr>
                    <td><b>Allele Name</b></td>
                </tr>
                <tr>
                    <td><input type="text" name="name" value="<jsp:getProperty name="strainallele" property="name"/>" maxlength="255" size="35"></td>
                </tr>
                <tr>
                    <td><b>Allele Symbol</b></td> 
                </tr>
                <tr>
                    <td><input type="text" name="symbol" value="<jsp:getProperty name="strainallele" property="symbol"/>" maxlength="255" size="35"></td>
                </tr>
                <tr>
                    <td><b>Allele MGI ID</b></td>
                </tr>
                <tr>
                    <td><input type="text" name="mgiid" value="<jsp:getProperty name="strainallele" property="mgiId"/>" maxlength="35" size="35"></td>
                </tr>
                <tr>
                    <td><b>Relevant Gene</b></td>
                </tr>
                <tr>
                    <td><m:checkbox collection="genes" name="gene" emptyOption="true" idGetter="getGaid" textGetter="getName" selected="<%=geneid%>"/></td>
                </tr>
            </table>
            <br>
            <table width="100%">
                <tr>
                    <td><b>Mutation Types</b><a class="menu" href="Controller?workflow=AddMutationTypeToAllele&strainalleleid=<jsp:getProperty name="strainallele" property="id"/>&isatransgc=<%=sadto.getIsStrainAlleleTransgenic()%>" title="add mutation type">&nbsp;[add new]</a></td>
                </tr>
                <m:iterate-collection collection="mutationtypes"> 
                <tr class="#?alt#">
                    <td>-&nbsp;#:getName#<a class="menu" href="Controller?workflow=RemoveMutationTypeFromAllele&strainalleleid=<jsp:getProperty name="strainallele" property="id"/>&mutationtypeid=#:getId#&isatransgc=<%=sadto.getIsStrainAlleleTransgenic()%>" title="delete mutation type">&nbsp;[del]</a></td>
                </tr>
                </m:iterate-collection>
                <tr>
                    <td><b>Mutation Type Attributes</b></td>
                </tr>
                <tr>
                    <td><m:checkbox collection="attributes" name="attributes" idGetter="toString" textGetter="toString" selected="<%=sadto.getAttributes()%>"/></td>
                </tr>
            </table>
            <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save allele & mutation">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form>
    </body>
</html>
