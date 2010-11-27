<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="modeldto" scope="request" type="com.arexis.mugen.model.modelmanager.ExpModelDTO" />
<jsp:useBean id="straindto" scope="request" type="com.arexis.mugen.model.modelmanager.StrainDTO" />

<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");

    com.arexis.mugen.model.modelmanager.ExpModelDTO dto = (com.arexis.mugen.model.modelmanager.ExpModelDTO)request.getAttribute("modeldto");
    
    com.arexis.mugen.model.modelmanager.StrainDTO sdto = (com.arexis.mugen.model.modelmanager.StrainDTO)request.getAttribute("straindto");
    
    int genotypingId = dto.getGenotypingId();
    int handlingId = dto.getHandlingId();
    int isTransgenic = dto.getDistParam();
    
    int EmmaID = new Integer(sdto.getEmmaid()).intValue();
    
    java.util.Collection geneticBackground = (java.util.Collection)request.getAttribute("geneticBackground");
    int edithis = 0;
    String whoNow = (String)request.getAttribute("curruser");
    
    if (modeldto.getContactName().compareTo(whoNow)==0 || whoNow.compareTo("MUGEN Admin")==0 || whoNow.compareTo("[devdude]")==0){
        edithis = 13;
    }
    
    String addHandling = "";
    String removeHandling = "Remove";

    String addGenotyping = "";
    String removeGenotyping = "Remove";

    if(genotypingId == 0)
    {
        addGenotyping = "Add";
        removeGenotyping = "";
    }
    if(handlingId == 0)
    {
        addHandling = "Add";
        removeHandling = "";
    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>strain detailed information </title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
        <h1>
            <jsp:getProperty name="modeldto" property="lineName"/>
        </h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            <a href="Controller?workflow=ViewModel&eid=<%=modeldto.getEid()%>&modelinfo.info=true" class="icon">
            <m:img name="info_24"/>
            </a>
            <a href="Controller?workflow=ViewModel&eid=<%=modeldto.getEid()%>&expand_all=true&name_begins=model.block" class="icon">
            <m:img title="Expand all boxes" name="nav_close_24"/>
            </a>
            <a href="Controller?workflow=ViewModel&eid=<%=modeldto.getEid()%>&collapse_all=true&name_begins=model.block" class="icon">
            <m:img title="Collapse all boxes" name="nav_open_24"/>
            </a>
            <%--<a href="Controller?workflow=ViewModelPrinter&eid=<%=modeldto.getEid()%>" target="_blank" title="View Printer Friendly Version"><m:img name="print24"/></a>--%>
            <a href="PDFGenerator?eid=<%=modeldto.getEid()%>" target="_blank" title="View PDF Version"><img src="images/icons/pdf.png"></a>
        </p>
        <br>
        <!--info block - appears when clicking i button -->
        <m:hide-block name="modelinfo.info">
            <table class="info">
                <td>
                    <b>Help & Info on this page:</b><br><br>
                    This is the detailed page for a MUGEN mutant mouse. All information is grouped into 9 categories which are represented with 9 different tabs on this page. To reveal information of a tab you just need to click on the tab's title. To unfold all tabs click on the unfold icon right next to the blue Help & Info exclamation mark.<br>
                    <b>General Information:</b> Withholds basic information such as the common line name, the unique identification number assigned to the MUGEN Mutant Mouse by the MUGEN database, its research application type, the name of the contact person (which also works as a contact link), the institution in which the strain was created. Other information concern the date the strain data have been last updated/created and the user of the MUGEN database that performed this update.<br>
                    The blue exclamation mark icon on this tab, once hit, will reveal the research application comments the user might have added to this mutant strain.<br>
                    <b>Availability:</b> Contains information on the mutant strains availability like in which repository it is available from, the available genetic background, the state and the type of the strain.<br>
                    <b>Genetic Background:</b> This tab contains information about the background of the MUGEN mutant mouse. The background the DNA derived from, the hosting & targeted background, the backcrossing strain and the number of backcrosses.<br>
                    <b>Strain Information:</b> In this category the official -according to MGNC's nomenclature rules- strain designation is provided and the MGI id of the strain if it exists.<br>
                    <b>Allele & mutations:</b> As the title suggests this tab contains information about the strains allele(s) and mutation. You can see the allele name and its symbol, the MGI id of the allele, the relevant gene the type of mutation of the mutant strain and an extra attribute that informs you if the mutation type is conditional, inducible, conditional+inducible or other.<br>
                    <b>Genes Affected:</b> This section withholds the name of the gene(s) affected for this mutant strain, comments relevant to the specific gene as added by a MUGEN database user, the chromosome on which the gene is located and the user that performed the latest update on the gene and the date this update took place. Clicking on the lens (a.k.a. "detailed info") icon you are redirected to a new page with even more information about a specific gene. Note also that the gene's name on this tab works as a link to the GENE database, so clicking on it will open a new window containing the information that is stored in the GENE database about this gene.<br>
                    <b>Handling & Genotyping Instructions:</b> Here you will find handling and genotyping instructions in the form of documents that are stored in the MUGEN database or in the form of links to web-pages or other on-line documents. The name is the actual name of the instruction file and serves as a link to the document whether it is available from a different server or the MUGEN database server. The type attribute informs you of the type of the instruction file, the comment column simply contains comments provided by the user who submitted this instruction file, the user and date columns carry the name of the user who edited/provided the resource and the date he/she actualized this edit/submission.<br>
                    <b>References:</b> This tab contains all references for this mutant strain in the form of documents that are stored in the MUGEN database or links to web-pages or other on-line documents. The name is the actual name of the reference title and serves as a link to the document whether it is available from a different server or from the MUGEN database server. The type attribute informs you of the type of the reference file, the comment column simply contains comments provided by the user who submitted this reference, the user and date columns carry the name of the user who edited/provided the reference and the date this edit/submission was actualized.<br>
                    <b>Author's Comments:</b> Here, as the title suggests, you will find comments about this mutant strain.<br>
                </td>
            </table>
        </m:hide-block>
        
        <input type="hidden" name="eid" value='<jsp:getProperty name="modeldto" property="eid"/>'/>
            
            <!--table with basic info-->
            <m:window title="General Information" name="model.main" workflow="ViewModel" state="expanded">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                        <% if (edithis==13){ %>
                        <a href="Controller?workflow=EditModel&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="edit this mouse">&nbsp;[edit]<%--m:img name="edit" title="Edit this mutant"/--%></a>
                        <a href="Controller?workflow=RemoveModel&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="delete this mouse" onClick="return confirm('delete mutant & all resources related to it?')">&nbsp;[del]<%--m:img name="delete"/--%></a>
                        <% } %>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <!--line with accession nr + line name-->
                        <tr class="block">
                            <td class="block">
                                <b>MUGEN ID</b><br> <jsp:getProperty name="modeldto" property="accNr"/>
                            </td>
                            <td class="block">
                                <b>Common Line name</b><br> <jsp:getProperty name="modeldto" property="lineName"/>
                            </td>
                            <td class="block">
                                <b>Research applications type</b><br><jsp:getProperty name="modeldto" property="researchAppType"/>
                            </td>
                        </tr>
                        <!--line with contact link + authors comment-->
                        <tr class="block">
                            <td class="block">
                                <b>Contact</b><br><a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="modeldto" property="contactId"/>" title="View the details for this user"><jsp:getProperty name="modeldto" property="contactName"/></a>
                            </td>
                            <td class="block">
                                <b>Institution</b><br>
                                <a href="<%=modeldto.getInsturl()%>" target="_blank">
                                <jsp:getProperty name="modeldto" property="participant"/>
                                </a>
                            </td>
                            <td>
                                <b>Research applications comments</b><br><jsp:getProperty name="modeldto" property="researchAppText"/>
                            </td>
                        </tr>        
                        <m:hide privilege="MODEL_ADM" suid="<%=modeldto.getSuid()%>">
                        <!--line with phenotypes-->
                        <tr class="block">
                            <td>
                                <b>Phenotypes</b><br><a href="Controller?workflow=ViewPhenotypes&_identity=<jsp:getProperty name="modeldto" property="accNr"/>" title="View the phenotypes for this model"><jsp:getProperty name="modeldto" property="phenotypes"/></a>
                            </td>
                        </tr>
                        <!--line with user-->
                        <tr class="block">
                            <tr class="block">
                                <td class="block">
                                    <b>last updated on: </b><jsp:getProperty name="modeldto" property="ts"/>
                                </td>
                                <td class="block">
                                    <b>by user: </b><a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="modeldto" property="userId"/>" title="View the details for this user"><jsp:getProperty name="modeldto" property="user"/></a> 
                                </td>
                                <td class="block">
                                    <b>dissemination level </b><jsp:getProperty name="modeldto" property="level"/>
                                </td>
                            </tr>
                        </tr>
                        </m:hide>
                    </table>
                </body>
            </m:window>
  
            <br>
            <!--table for availability-->
            <m:window title="Availability" name="model.block.availability" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=AssignAvailability&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add availability">&nbsp;[add]<%--m:img name="add" title="Add availability information for this model"/--%></a>
                    <% } %>    
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="15%">Repository</th>
                            <th class="data2" width="30%">Available Genetic Background</th>
                            <th class="data2" width="20%">Strain State</th>
                            <th class="data2" width="20%">Strain Type</th>
                            <th class="data2" width="10%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="availability">
                            <tr class="#?alt#">
                                <td><a href="#:getRepourl#" target="_blank">#:getReponame#</a></td>
                                <td>#:getAvbacknamess#</td>
                                <td>#:getStatename#</td>
                                <td>#:getTypename#</td>    
                                <td>
                                <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                                <% if (edithis==13){ %>
                                <a class="menu" href="Controller?workflow=UnAssignAvailability&eid=<jsp:getProperty name="modeldto" property="eid"/>&rid=#:getRid#&aid=#:getAid#&stateid=#:getStateid#&typeid=#:getTypeid#" title="remove availability" onClick="return confirm('remove availability?')">[remove]<%--m:img name="delete" title="Remove this availability information line"/--%></a>
                                <% } %>
                                </m:hide>
                                </td>
                            </tr>
                        </m:iterate-collection>              
                    </table>
                </body>
            </m:window>   
  
            <br>
            <!--table for genetic background info-->
            <m:window title="Genetic Background" name="model.block.genback" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                    <%  if (geneticBackground.isEmpty()== true){ %>
                        <a href="Controller?workflow=CreateGeneticBackground&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add genetic background">&nbsp;[add]<%--m:img name="add" title="Add genetic background information to this model"/--%></a>
                    <% } else {%>
                        <a href="Controller?workflow=EditGeneticBackground&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="edit genetic background">&nbsp;[edit]<%--m:img name="edit" title="Edit genetic background information"/--%></a>
                    <% } %>
                    <% } %>
                     </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>                        
                            <th class="data2" width="10%">DNA Origin</th>
                            <th class="data2" width="10%">Targeted Background</th>
                            <th class="data2" width="10%">Host Background</th>
                            <th class="data2" width="10%">Backcrossing Strain</th>
                            <th class="data2" width="10%">Backcrosses</th>
                        </tr>
                        <m:iterate-collection collection="geneticBackground">
                            <tr class="#?alt#">
                                <td>#:getDna_origin_namess#</td>
                                <td>#:getTargeted_back_namess#</td>
                                <td>#:getHost_back_namess#</td>
                                <td>#:getBackcrossing_strain_namess#</td>
                                <td>#:getBackcrosses#</td>
                            </tr>
                        </m:iterate-collection>              
                    </table>
                </body>
            </m:window>
  
            <br>
            <!--table for strain info (designation, mgi id, state, type)-->
            <m:window title="Strain Information" name="model.block.strain" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=EditStrain&strainid=<jsp:getProperty name="straindto" property="strainId"/>" title="edit strain">&nbsp;[edit]</a>
                    <% } %>
                    </m:hide>  
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="30%">Designation</th>
                            <% if (!sdto.getMgiId().equals("0")){ %>
                            <th class="data2" width="30%">JAX ID</th>
                            <% } %>
                            <% if (!sdto.getEmmaid().equals("0")){ %>
                            <th class="data2" width="30%">EMMA ID</th>
                            <% } %>
                        </tr>
                        <tr class="#?alt#">
                            <td><jsp:getProperty name="straindto" property="designationdisplay"/></td>
                            <% if (!sdto.getMgiId().equals("0")){ %>
                            <td><a href="http://jaxmice.jax.org/strain/<jsp:getProperty name="straindto" property="mgiId"/>.html" target="_blank"><jsp:getProperty name="straindto" property="mgiId"/></a></td>
                            <% } %>
                            <% if (!sdto.getEmmaid().equals("0")){ %>
                            <td><a href="http://strains.emmanet.org/strains/strain_<%=EmmaID%>.utf8.php" target="_blank">EM:<jsp:getProperty name="straindto" property="emmaid"/></a></td>
                            <% } %>
                        </tr>
                    </table>
                </body>
            </m:window>
            
            <br>
            <!--table for allele + mutation info -->
            <m:window title="Allele & Mutations" name="model.block.allele" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=CreateStrainAllele&strainid=<jsp:getProperty name="straindto" property="strainId"/>" title="add allele">&nbsp;[add]<%--m:img name="add" title="Add allele"/--%></a>
                    <% } %>    
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="20%">Symbol</th>
                            <th class="data2" width="40%">Name</th>
                            <%--<th class="data2" width="8%">MGI ID</th>
                            <th class="data2" width="20%">Gene Name</th>--%>
                            <th class="data2" width="30%">Mutation Types</th>
                            <%--th class="data2" width="10%">Attributes</th--%>
                            <th class="data2" width="5%">&nbsp;</th>
                            <th class="data2" width="5%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="strainalleles"> 
                        <tr class="#?alt#">
                            <td><a href="Controller?workflow=ViewStrainAllele&allid=#:getId#" title="view allele">#:getSymbol#</a></td>
                            <td>#:getName#</td>
                            <%--td>#:getMgilink#</td>
                            <td>#:getGeneName#</td--%>
                            <td>#:getMutations#</td>
                            <%--td>#:getAttributes#</td--%>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                            <% if (edithis==13){ %>
                            <a class="menu"  href="Controller?workflow=EditStrainAllele&strainalleleid=#:getId#&transgc=<%=modeldto.getDistParam()%>&isatransgc=#:getIsStrainAlleleTransgenic#&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="edit allele">&nbsp;[edit]<%--m:img name="edit"/--%></a>
                            <% } %>
                            </m:hide>
                            </td>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                            <% if (edithis==13){ %>
                            <a class="menu"  href="Controller?workflow=RemoveStrainAllele&strainalleleid=#:getId#&transgc=<%=modeldto.getDistParam()%>&isatransgc=#:getIsStrainAlleleTransgenic#&eid=<jsp:getProperty name="modeldto" property="eid"/>" onClick="return confirm('remove allele?')" title="delete allele">&nbsp;[del]<%--m:img name="delete"/--%></a>
                            <% } %>
                            </m:hide>
                            </td>
                        </tr>
                        </m:iterate-collection>
                    </table>
                </body>
            </m:window>
            
            <br>
            <!--table for genes affected-->
            <% if (isTransgenic==0){ %>
            <m:window title="Genes Affected" name="model.block.geneaffected" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=AssignGeneModel&eid=<jsp:getProperty name="modeldto" property="eid"/>&transgc=<%=modeldto.getDistParam()%>&strainid=<jsp:getProperty name="straindto" property="strainId"/>" title="add gene">&nbsp;[add]<%--m:img name="add" title="Add an affected gene to this model"/--%></a>
                    <% } %>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="20%">Symbol</th>
                            <th class="data2" width="40%">Name</th>
                            <th class="data2" width="10%">Chromosome</th>
                            <th class="data2" width="10%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="genesAffected">
                            <tr class="#?alt#">
                                <td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#&transgc=<%=dto.getDistParam()%>" title="view gene">#:getGenesymbol#</a></td>
                                <td>#:getName#</td>
                                <td>#:getChromoName#</td>
                                <td>
                                <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                                <% if (edithis==13){ %>
                                    <a  class="menu" href="Controller?workflow=UnAssignGeneModel&gaid=#:getGaid#&eid=<jsp:getProperty name="modeldto" property="eid"/>&istrans=<%=dto.getDistParam()%>" title="unassign gene" onClick="return confirm('unassign gene?')">&nbsp;[unassign]<%--m:img name="unassign" title="Remove the entry"/--%></a>
                                <% } %>
                                </m:hide>
                                </td>
                            </tr>
                        </m:iterate-collection>              
                    </table>    
                </body>
            </m:window>
            
            <% }else{ %>
            
            <m:window title="Transgene Information (Expressed Genes)" name="model.block.geneaffected" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=AssignGeneModel&eid=<jsp:getProperty name="modeldto" property="eid"/>&transgc=<%=modeldto.getDistParam()%>&strainid=<jsp:getProperty name="straindto" property="strainId"/>" title="add transgene">&nbsp;[add]<%--m:img name="add" title="Add an affected gene to this model"/--%></a>
                    <% } %>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>                        
                            <th class="data2" width="20%">Symbol</th>
                            <th class="data2" width="40%">Name</th>
                            <th class="data2" width="10%">Chromosome</th>
                            <th class="data2" width="10%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="genesAffected">
                            <tr class="#?alt#">
                                <td><a href="Controller?workflow=ViewGene&gaid=#:getGaid#&transgc=<%=dto.getDistParam()%>" title="view transgene">#:getGenesymbol#</a></td>
                                <td>#:getName#</td>
                                <td>#:getChromoName#</td>
                                <td>
                                <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                                <% if (edithis==13){ %>
                                    <a class="menu"  href="Controller?workflow=UnAssignGeneModel&gaid=#:getGaid#&eid=<jsp:getProperty name="modeldto" property="eid"/>&istrans=<%=dto.getDistParam()%>" title="unassign transgene" onClick="return confirm('unassign transgene?')">&nbsp;[unassign]<%--m:img name="unassign" title="Remove the entry"/--%></a>
                                <% } %>
                                </m:hide>
                                </td>
                            </tr>
                        </m:iterate-collection>              
                    </table>    
                </body>
            </m:window>
            
            <% } %>
  
            <br>
            <!--table for handling & genotyping-->
            <m:window title="Handling & Genotyping Instructions" name="model.block.res" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=CreateModelFileResource&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add file">&nbsp;[add file]<%--m:img name="add" title="Add Handling & Genotyping Instructions file resource to this model"/--%></a>
                        <a href="Controller?workflow=CreateModelLinkResource&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add link">&nbsp;[add link]<%--m:img name="bookmark_add" title="Add Handling & Genotyping Instructions link resource to this model"/--%></a>    
                    <% } %>
                    </m:hide>
                </menu>
                <body>
                    <m:resource-simple-list resourceTreeCollection="resourceTree"/>
                </body>
            </m:window>
            
            <%--
            <br>
            <!--table for phenotype terms info -->
            <m:window title="Phenotype(s)" name="model.block.pheno" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=PhenoAssign&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="assign phenotype">&nbsp;[assign]</a>
                    <% } %>    
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="95%">&nbsp;</th>
                            <th class="data2" width="5%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="phenoPaths"> 
                        <tr class="#?alt#">
                            <td>#:getPhenoPath#</td>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                            <% if (edithis==13){ %>
                            <a class="menu"  href="Controller?workflow=PhenoUnAssign&eid=<jsp:getProperty name="modeldto" property="eid"/>&mp01=#:getPhenoId00#&mp02=#:getPhenoId01#&mp03=#:getPhenoId02#&mp04=#:getPhenoId03#&mp05=#:getPhenoId04#&mp06=#:getPhenoId05#&mp07=#:getPhenoId06#&mp08=#:getPhenoId07#&mp09=#:getPhenoId08#" onClick="return confirm('unassign phenotype?')" title="unassign phenotype">&nbsp;[unassign]</a>
                            <% } %>
                            </m:hide>
                            </td>
                        </tr>
                        </m:iterate-collection>
                    </table>
                </body>
            </m:window>
            --%>
            
            <br>
            <!--table for mp paths -->
            <m:window title="Mammalian Phenotype Ontologies" name="model.block.mps" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=PhenoPathAssign&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="assign phenotype">&nbsp;[assign]</a>
                    <% } %>    
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="99%">&nbsp;</th>
                            <th class="data2" width="1%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="mpz"> 
                        <tr class="#?alt#">
                            <td>
                                <b>MP Term:&nbsp;</b>#:getMpnameshortlink#
                                <hr id="rulernew">
                                <b>PATO Logical Definition:&nbsp;</b>#:getPld#
                            </td>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                                <% if (edithis==13){ %>
                                <a class="menu" href="Controller?workflow=RemovePhenoPathDirect&eid=<jsp:getProperty name="modeldto" property="eid"/>&ppid=#:getPathid#" onClick="return confirm('unassign mp term?')" title="unassign mp term">&nbsp;[-]</a>
                                <% } %>
                            </m:hide>
                            </td>
                        </tr>
                        <tr height="15px"><td colspan="2">&nbsp;</td></tr>
                        </m:iterate-collection>
                    </table>
                </body>
            </m:window>
          
            <br>
            <!--table for references-->
            <m:window title="References" name="model.block.ref" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=CreateModelFileReference&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add file">&nbsp;[add file]<%--m:img name="add" title="Add file reference to this model"/--%></a>
                        <a href="Controller?workflow=CreateModelLinkReference&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="add link">&nbsp;[add link]<%--m:img name="bookmark_add" title="Add link to this model"/--%></a>
                    <% } %>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                    <tr>                        
                        <th class="data2" width="20%">Name</th>
                        <th class="data2" width="10%">Type</th>
                        <th class="data2" width="30%">Comment</th>
                        <th class="data2" width="10%">&nbsp;</th>
                        <th class="data2" width="10%">&nbsp;</th>
                    </tr>
                    <m:iterate-collection collection="references">
                        <tr class="#?alt#">
                            <td><a href="#:getResource#" target="_blank" title="View file/Visit link">#:getName#</a></td>
                            <td>#:getType#</td>
                            <td>#:getComm#</td>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                            <% if (edithis==13){ %>
                                <a class="menu"  href="#:getEdit#" title="edit reference">&nbsp;[edit]<%--m:img name="edit" title="Remove the entry"/--%></a>
                            <% } %>
                            </m:hide>
                            </td>
                            <td>
                            <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                            <% if (edithis==13){ %>
                                <a class="menu"  href="Controller?workflow=RemoveModelReferenceSimple&refid=#:getRefid#" title="remove reference" onClick="return confirm('remove reference?')">&nbsp;[remove]<%--m:img name="delete" title="Remove the entry"/--%></a>
                            <% } %>
                            </m:hide>
                            </td>
                        </tr>
                    </m:iterate-collection>              
                </table>
                </body>
            </m:window>
  
            <br>
            <!--table for author's comments-->
            <m:window title="Author's Comments" name="model.block.authcomm" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_W" suid="<%=modeldto.getSuid()%>">
                    <% if (edithis==13){ %>
                        <a href="Controller?workflow=EditModel&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="edit comments">&nbsp;[edit]<%--m:img name="edit" title="Edit Author's Comments"/--%></a>
                    <% } %>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="100%">&nbsp;</th>
                        </tr>
                        <tr class="#?alt#">
                            <td><jsp:getProperty name="modeldto" property="comm"/></td>
                        </tr>
                    </table>    
                </body>
            </m:window>
            
            <br>
            <!--table for availability-->
            <m:window title="User's Comments" name="model.block.usercomments" workflow="ViewModel">
                <menu>
                    <m:hide privilege="MODEL_ADM" suid="<%=modeldto.getSuid()%>">
                    <a href="Controller?workflow=AddUserComment&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="post comment">&nbsp;[post comment]</a>
                    </m:hide>
                </menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="10%">&nbsp;</th>
                            <th class="data2" width="70%">&nbsp;</th>
                            <th class="data2" width="10%">&nbsp;</th>
                            <m:hide privilege="MODEL_ADM" suid="<%=modeldto.getSuid()%>">
                                <th class="data2" width="10%">&nbsp;</th>
                            </m:hide>
                        </tr>
                        <m:iterate-collection collection="usercomms">
                            <tr class="#?alt#">
                                <td>#:getTs#</td>
                                <td>#:getComm#</td>
                                <td>#:getUsername#</td>
                                <m:hide privilege="MODEL_ADM" suid="<%=modeldto.getSuid()%>">
                                <td>
                                <a class="menu" href="Controller?workflow=RemoveUserComment&commid=#:getCommid#" title="remove user comment" onClick="return confirm('remove user comment?')">[remove]</a>
                                </td>
                                </m:hide>
                            </tr>
                        </m:iterate-collection>              
                    </table>
                </body>
            </m:window>
            
            <br>
            <p class="toolbar">
              <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
            </p>
        </form>
    </body>
</html>
