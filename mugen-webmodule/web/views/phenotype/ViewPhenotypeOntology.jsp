<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="pot" scope="request" type="com.arexis.mugen.model.modelmanager.PhenoOntologyDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>mammalian phenotype term</title>
    </head>
    <body class="content">
    <h1>mammalian phenotype term</h1>
    <p class="toolbar">&nbsp;</p>
    
    <form action="Controller" method="post">
        <table width="100%">
            <tr>
                <td><b>MGI ID:&nbsp;</b><a class="menu" href="http://www.informatics.jax.org/searches/Phat.cgi?id=<jsp:getProperty name='pot' property='mpid'/>" title="find mp term at mgi" target="_blank"><jsp:getProperty name="pot" property="mpid"/></a></td>            
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td><b>Name:&nbsp;</b><jsp:getProperty name="pot" property="phenoName"/></td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td><b>Definition:&nbsp;</b>"<jsp:getProperty name="pot" property="def"/>"&nbsp;[<jsp:getProperty name="pot" property="defref"/>]</td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td><b>Comment:&nbsp;</b><jsp:getProperty name="pot" property="comm"/></td>                
            </tr>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Alt_id(s):</b></td>
            </tr>
            <m:iterate-collection collection="alts">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b>MP:#:getAltId#</td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Synonym(s):</b></td>
            </tr>
            <m:iterate-collection collection="syns">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b>"#:getSynonym#"&nbsp;#:getAttribute#</td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Is_a(s):</b></td>
            </tr>
            <m:iterate-collection collection="isas">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b>#:getIsaline#</td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Path(s):</b></td>
            </tr>
            <m:iterate-collection collection="mpz">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b>#:getMpnameshort#</td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>PATO Logical Definition:</b></td>
            </tr>
            <tr>
                <td><b>+&nbsp;</b><jsp:getProperty name="pot" property="pldfull"/></td>
            </tr>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Related Mutant(s):</b></td>
            </tr>
            <m:iterate-collection collection="models">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b><a class="menu" href="Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid=#:getEid#" title="view mouse">#:getLineName#</a></td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <table width="100%">
            <tr>
                <td><b>Related Gene(s):</b></td>
            </tr>
            <m:iterate-collection collection="genz">
            <tr class="#?alt#">
                <td><b>+&nbsp;</b><a class="menu" href="Controller?workflow=ViewGene&gaid=#:getGaid#" title="view gene">#:getGenesymbol#</a></td>
            </tr>
            </m:iterate-collection>
        </table>
        <br>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p>
    </form>
    </body>
</html>
