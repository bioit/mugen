<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>create gene/transgene</title>
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
    <body class="content" onLoad="init()">
        <h1>create gene/transgene</h1>
        <p class="toolbar">
            <a href="Controller?workflow=CreateGene&gene.help=true"><m:img name="info_24" title="help & info"/></a>
        </p>
        <m:hide-block name="gene.help">
                <table>    
                    <tr>
                        <td>
                            <table class="info">
                                <td>
                                    <b>Help & Info on this page:</b><br><br>
                                    <b>Gene Name:</b> Simply type the name of the Gene. You can use any special character, uppercase and lowercase.<br>
                                    <b>MGI Gene ID:</b> Type the MGI id of the Gene if known. You can use numbers and characters.<br>
                                    <b>Gene Symbol:</b> Type the symbol of the Gene. You are free to use all special characters, uppercase, lowercase and numbers.<br>
                                    <b>Gene Expression:</b> Type the Gene's expression. You are free to use all special characters, uppercase, lowercase and numbers.<br>
                                    <b>Comment:</b> Add some comments that are relevant to the new gene here.<br>
                                    <b>GENE Database ID:</b> Type the GENE database id of the Gene. This id will be automatically converted to a GENE database link, so please make sure the id you are typing is the correct one. All characters are allowed.<br>
                                    <b>ENSEMBL database ID:</b> Type the id of the ENSEMBL database that corresponds to the Gene. Again the link to the ENSEMBL database will be automatically generated so be sure to enter the right one. Of course all characters are allowed.<br>
                                    <b>Saving your data: </b> Once you're done with entering data you can click on the save icon (the blue diskette icon) to store the new gene in the MUGEN database. If you are having second thoughts you can always click on the cancel icon (the blue x icon) to return to the previously visited web-page without storing data in the database.<br>
                                </td>
                            </table>
                        </td>
                    </tr>     
                </table>     
        </m:hide-block>
        <form action="Controller" method="post">
                <input type="hidden" name="eid" value='<%=request.getAttribute("eid")%>'/>
                <%--input type="hidden" name="geneexpress" value="under construction"/--%>
                <table>
                    <tr>
                        <td><b>Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="name" size="35" maxlength="255"/></td>
                    </tr>
                    <tr>
                        <td><b>Symbol</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="genesymbol" size="35" maxlength="255"/></td>
                    </tr>
                    <tr>
                        <td><b>Synonym/Old Name</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="geneexpress" size="35" maxlength="255"/></td>
                    </tr>
                    <tr>
                        <td><b>Chromosome</b></td>
                    </tr>
                    <tr>
                        <td><m:checkbox collection="chromosomes" name="cid" idGetter="getCid" textGetter="getName" selected="24"/></td>
                    </tr>
                    <tr>
                        <td><b>MGI ID</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="mgiid" size="35" maxlength="35"/></td>
                    </tr>
                    <tr>
                        <td><b>Comment</b></td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"></textarea></td>                        
                    </tr>
                    <tr>
                        <td><b>Entrez ID</b></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="idgene" size="35" maxlength="35"/></td>
                    </tr>
                    <tr>
                        <td><b>ENSEMBL ID</b></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="idensembl" size="35" maxlength="35"/>
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

