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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>gene/transegene page</title>
    </head>
    <body class="content">
    <% if(trans){ %>
    <h1>transgene</h1>
    <p class="toolbar">
        <m:hide privilege="MODEL_W" pid="<%=gene.getPid()%>">
            <a href="Controller?workflow=EditGene&transgc=<%=transgc%>&gaid=<jsp:getProperty name="gene" property="gaid"/>" title="edit transgene"><img src="images/icons/editdark.png"></a>
            <a href="Controller?workflow=RemoveGeneSimple&gaid=<jsp:getProperty name="gene" property="gaid"/>" title="delete transgene" onClick="return confirm('delte transgene?')"><img src="images/icons/delete.png"></a>
        </m:hide>
        <a href="Controller?workflow=ViewGene&gaid=<%=gene.getGaid()%>&gene.info=true&transgc=<%=transgc%>"><m:img name="info_24" title="help & info"/></a>
    </p>
    <% } else { %>
    <h1>gene</h1>
    <p class="toolbar">
        <m:hide privilege="MODEL_W" pid="<%=gene.getPid()%>">
            <a href="Controller?workflow=EditGene&gaid=<jsp:getProperty name="gene" property="gaid"/>" title="edit gene"><img src="images/icons/editdark.png"></a>
            <a href="Controller?workflow=RemoveGeneSimple&gaid=<jsp:getProperty name="gene" property="gaid"/>" title="delete gene" onClick="return confirm('delte gene?')"><img src="images/icons/delete.png"></a>
        </m:hide>
        <a href="Controller?workflow=ViewGene&gaid=<%=gene.getGaid()%>&gene.info=true&transgc=<%=transgc%>"><m:img name="info_24" title="help & info"/></a>
    </p>
    <% } %>
    
    <m:hide-block name="gene.info">
        <table class="info">
            <td>
                <b>Help & Info on this page:</b><br><br>
                <b>Name:</b> Stands for the name of the gene.<br>
                <b>MGI ID:</b> The ID of the gene as assigned by the MGI.<br>
                <b>Symbol:</b> The symbol used to represent the gene. <br>
                <b>Comments:</b> Comments relevant to the gene as typed by the user who submitted this gene. <br>
                <b>Entrez Database Link:</b> Shows the GENE database id and is functional as a link to the GENE database, hence clicking on it will open a new window with all the information that is stored in the GENE database and is relevant to the gene.<br>
                <b>ENSEMBL Database Link:</b> Shows the ENSEMBL database id of the gene and works as link to it as well.<br>
                <b>Connected MUGEN Mutant Mice: </b> To unfold this tab click on its title. Once unfolded it displays the list of mutant strains that are connected to this gene. Each strain's name and the comments that accompany it are listed. Clicking on the lens icon will redirect you to the detailed information page for the specific strain.<br><br>
                <m:hide privilege="MODEL_W" pid="<%=gene.getPid()%>">
                    <b>Editing this gene:</b> To edit this gene click on the edit icon on the gene information tab. The "Edit Gene" will open. There you can freely edit this gene.<br>
                    <b>Deleting this gene:</b> To remove this gene from the database you must click on the remove icon (the red x icon on the gene information tab). You will be asked to confirm your intention to delete the gene and if you click ok it will be deleted. Otherwise the gene will remain stored in the database. NOTE!!! You do not have to delete a gene to unassigned it from a mutant strain. To do that you have to visit the detailed information page of the strain and unassign the gene from there. If you delete a gene from this page it will be permanently deleted from the database!!!.<br>
                </m:hide>
            </td>
        </table>
        <br>
    </m:hide-block>
    
    <form action="Controller" method="post">
        <input type="hidden" name="gaid" value='<jsp:getProperty name="gene" property="gaid"/>'/>
        <table>
            <tr>
                <td><b>Name:&nbsp;</b><jsp:getProperty name="gene" property="name"/></td>
            </tr>
            <tr>
                <td><b>Symbol:&nbsp;</b><jsp:getProperty name="gene" property="genesymbol"/></td>            
            </tr>
            <tr>
                <td><b>Synonym/Old Name:&nbsp;</b><jsp:getProperty name="gene" property="geneexpress"/></td>            
            </tr>
            <tr>
                <td><b>Chromosome:&nbsp;</b><jsp:getProperty name="gene" property="chromoName"/></td>
            </tr>
            <tr>
                <td><b>Comment</b></td>                
            </tr>
            <tr>
                <td><jsp:getProperty name="gene" property="comm"/></td>
            </tr>
            <m:hide privilege="MODEL_ADM" pid="<%=gene.getPid()%>">
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>
                    <b>updated:&nbsp;</b><jsp:getProperty name="gene" property="ts"/>
                    <b>&nbsp;user:&nbsp;</b><a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="gene" property="userId"/>" title="View the details for this user"><jsp:getProperty name="gene" property="userName"/></a> 
                </td>            
            </tr>
            </m:hide>
        </table>
        <br>
        <table width="100%">
            <tr><td valign="top">
                <table width="100%">
                    <tr class="alternatingTwo"><td><b>External Links</b></td></tr>
                    <tr>
                        <td><img src="images/logos/mgilink.jpg" alt="mgi" class="link">&nbsp;<b>MGI ID:&nbsp;</b><jsp:getProperty name="gene" property="mgilink"/></td>
                    </tr>
                    <tr>
                        <td><img src="images/logos/entrez.jpg" alt="entrez" class="link">&nbsp;<b>Entrez ID:&nbsp;</b><jsp:getProperty name="gene" property="entrezlink"/></td>            
                    </tr>
                    <tr>
                        <td><img src="images/logos/ensemblelink.jpg" alt="ensembl" class="link">&nbsp;<b>ENSEMBL ID:&nbsp;</b><jsp:getProperty name="gene" property="ensemblink"/></td>            
                    </tr>
                </table>
                </td>
                <td valign="top">
                <table width="100%">
                    <tr class="alternatingTwo"><td><b>Expression Profiles</b></td></tr>
                    <tr>
                        <td><img src="images/logos/arrayexpresslink.jpg" alt="array express" class="link">&nbsp;<b>ArrayExpress:&nbsp;</b><jsp:getProperty name="gene" property="arrayexpresslink"/></td>            
                    </tr>
                    <tr>
                        <td><img src="images/logos/eurexpresslink.png" alt="eurexpress" class="link">&nbsp;<b>Eurexpress:&nbsp;</b><jsp:getProperty name="gene" property="eurexpresslink"/></td>            
                    </tr>
                </table>
                </td>
                <jsp:getProperty name="gene" property="invitrogen"/>
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
                <td><b>Related Alleles</b></td>
            </tr>
            <m:iterate-collection collection="alleles">
            <tr class="#?alt#">
                <td><a class="menu" href="Controller?workflow=ViewStrainAllele&allid=#:getId#" title="view allele">#:getSymbol#</a></td>
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
