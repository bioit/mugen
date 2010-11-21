<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="gene" scope="request" type="com.arexis.mugen.model.modelmanager.GeneDTO" />
<% 
    boolean trans = true;
    int transgc = 1;
    if(request.getParameter("transgc")==null || request.getParameter("transgc").compareTo("1")!=0){
        trans = false;
        transgc = 0;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit gene/transgene</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {
                define('name', 'string', 'Name', 1, 255);
                define('genesymbol', 'string', 'Symbol', 1, 255);
                define('geneexpress', 'string', 'Synonym/Old Name', 1, 255);
                define('comm', 'string', 'Comments', null, 255);
                define('mgiid', 'num', 'MGI ID');
                define('idgene', 'num', 'Entrez Gene ID');
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <% if(trans){ %>
        <h1>edit transgene</h1>
        <% } else { %>
        <h1>edit gene</h1>
        <% } %>
        <p class="toolbar">
            <a href="Controller?workflow=EditGene&transgc=<%=transgc%>&genedit.help=true&gaid=<jsp:getProperty name="gene" property="gaid"/>"><m:img name="info_24" title="help & info"/></a>
        </p>
        <%--info table--%>
        <m:hide-block name="genedit.help">    
            <table class="info">
                <td>
                    <b>Help & Info on this page:</b><br><br>
                    <b>Gene Name:</b> If you would like to update the name of this gene simply type the new name of the Gene. You can use any special character, uppercase and lowercase.<br>
                    <b>MGI Gene ID:</b> Type the new or correct MGI id of the Gene. You can use numbers and characters.<br>
                    <b>Gene Symbol:</b> Edit the symbol of the Gene. You are free to use all special characters, uppercase, lowercase and numbers.<br>
                    <b>Gene Expression:</b> Edit the existing Gene's expression. You are free to use all special characters, uppercase, lowercase and numbers.<br>
                    <b>Comment:</b> Add, remove or update comments that are relevant to the gene on this text field.<br>
                    <b>GENE Database ID:</b> Retype or simply enter the GENE database id of the Gene. This id will be automatically converted to a GENE database link, so please make sure the id you are typing is the correct one. All characters are allowed.<br>
                    <b>ENSEMBL database ID:</b> Retype or simply enter the id of the ENSEMBL database that corresponds to the Gene. Again the link to the ENSEMBL database will be automatically generated so be sure to enter the right one. Of course all characters are allowed.<br>
                    <b>Saving your data: </b> Once you're done with editing the data you can click on the save icon (the blue diskette icon) to store the updated gene data in the MUGEN database. If you are having second thoughts you can always click on the cancel icon (the blue x icon) to return to the previously visited web-page without storing data in the database.<br>
                </td>
            </table>                     
        </m:hide-block>
        <form action="Controller" method="post">
                <input type="hidden" name="gaid" value="<jsp:getProperty name='gene' property='gaid'/>">
                <%--input type="hidden" name="geneexpress" value='<jsp:getProperty name="gene" property="geneexpress"/>'/--%>
                <table>
                    <tr>
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="name" size="35" maxlength="255" value='<jsp:getProperty name="gene" property="name"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Symbol</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="genesymbol" size="35" maxlength="255" value='<jsp:getProperty name="gene" property="genesymbol"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Synonym/Old Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="geneexpress" size="35" maxlength="255" value='<jsp:getProperty name="gene" property="geneexpress"/>'/></td>
                    </tr>
                    <tr>
                        <td><b>Chromosome</b></td>
                    </tr>
                    <tr>
                        <td><m:checkbox collection="chromosomes" name="cid" idGetter="getCid" textGetter="getName" selected='<%=gene.getCid()%>'/></td>
                    </tr>
                    <tr>
                        <td><b>MGI ID</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="mgiid" size="35" maxlength="35" value='<jsp:getProperty name="gene" property="mgiid"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Comment</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="gene" property="comm"/></textarea></td>                        
                    </tr>
                    <tr>
                        <td><b>Entrez ID</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="idgene" size="35" maxlength="35" value='<jsp:getProperty name="gene" property="idgene"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>ENSEMBL ID</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="idensembl" size="35" maxlength="35" value='<jsp:getProperty name="gene" property="idensembl"/>'/>
                        </td>
                    </tr>                    
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="save gene/transgene">
                    <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
                </p>
        </form> 
    </body>
</html>

