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
    
    java.util.Collection geneticBackground = (java.util.Collection)request.getAttribute("geneticBackground");
    int edithis = 0;
    
    int EmmaID = new Integer(sdto.getEmmaid()).intValue();
    
    String whoNow = (String)request.getAttribute("curruser");
    
    if (modeldto.getContactName().compareTo(whoNow)==0 || whoNow.compareTo("MUGEN Admin")==0){
        edithis = 13;
    }
    
    String whatDVLevel = (String)request.getAttribute("DirectViewLevel");

    String addHandling = "";
    String removeHandling = "Remove";

    String addGenotyping = "";
    String removeGenotyping = "Remove";

    if(genotypingId == 0){
        addGenotyping = "Add";
        removeGenotyping = "";
    }
    
    if(handlingId == 0){
        addHandling = "Add";
        removeHandling = "";
    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title><jsp:getProperty name="modeldto" property="lineName"/></title>
    </head>
    <% if (whatDVLevel.compareTo("Public")==0){ %>
    <body class="contentdirect">
    
    <!-- Start of StatCounter Code -->
    <script type="text/javascript" language="javascript">
    var sc_project=1988167; 
    var sc_invisible=1; 
    var sc_partition=18; 
    var sc_security="54dcab52"; 
    </script>
    <script type="text/javascript" language="javascript" src="http://www.statcounter.com/counter/frames.js">    
    </script>
    <noscript>
    <a href="http://www.statcounter.com/" target="_blank">
    <img src="http://c19.statcounter.com/counter.php?sc_project=1988167&java=0&security=54dcab52&invisible=1" alt="web counter" border="0">
    </a>
    </noscript>
    <!-- End of StatCounter Code -->
    
        <table class="toptabledirect">
            <tr><td>&nbsp;</td></tr>
        </table>
        <table class="winctrldirect">
            <tr><td><a href="EntryMugenViewingModels.jsp" class="winctrl">all MUGEN mice</a></td></tr>
        </table>
        <h1>
            <jsp:getProperty name="modeldto" property="lineName"/>
        </h1>
        <p class="toolbar">
            <a href="DirectView?workflow=ViewModelDirect&eid=<%=modeldto.getEid()%>&expand_all=true&name_begins=model.block"><m:img name="nav_close_24" title="expand all tabs"/></a>
            <a href="DirectView?workflow=ViewModelDirect&eid=<%=modeldto.getEid()%>&collapse_all=true&name_begins=model.block"><m:img name="nav_open_24" title="collapse all tabs"/></a>
            <%--a href="DirectView?workflow=ViewModelPrinter&eid=<%=modeldto.getEid()%>" target="_blank"><m:img name="print24" title="open printer friendly page"/></a--%>
            <a href="PDFGenerator?eid=<%=modeldto.getEid()%>" target="_blank" title="View PDF Version"><img src="images/icons/pdf.png"></a>
        </p>
        
        <form action="DirectView" method="post">
            <input type="hidden" name="eid" value='<jsp:getProperty name="modeldto" property="eid"/>'/>
            
            <br>
            <!--table with basic info-->
            <m:windowdirect title="General Information" name="model.main" workflow="ViewModelDirect" state="expanded">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <!--line with accession nr + line name-->
                        <tr class="block">
                            <td class="block">
                                <b>MUGEN ID</b><br><jsp:getProperty name="modeldto" property="accNr"/>
                            </td>
                            <td class="block">
                                <b>Common Line Name</b><br><jsp:getProperty name="modeldto" property="lineName"/>
                            </td>
                            <td class="block">
                                <b>Research Applications Type</b><br><jsp:getProperty name="modeldto" property="researchAppType"/>
                            </td>
                        </tr>
                        <!--line with contact link + authors comment-->
                        <tr class="block">
                            <td class="block">
                                <b>Contact</b><br><a href="mailto:<jsp:getProperty name='modeldto' property='contactMail'/>"><jsp:getProperty name='modeldto' property='contactName'/></a>
                            </td>
                            <td class="block">
                                <b>Institution</b><br><jsp:getProperty name="modeldto" property="participant"/>
                            </td>
                            <td>
                                <b>Research Applications Comments</b><br><jsp:getProperty name="modeldto" property="researchAppText"/>
                            </td>
                        </tr>
                    </table>
                </body>
            </m:windowdirect>
  
            <br>
            <!--table for availability-->
            <m:windowdirect title="Availability" name="model.block.availability" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="20%">Repository</th>
                            <th class="data2" width="40%">Available Background</th>
                            <th class="data2" width="20%">State</th>
                            <th class="data2" width="20%">Type</th>
                        </tr>
                        <m:iterate-collection collection="availability">
                            <tr class="#?alt#">
                                <td>#:getReponame#</td>
                                <td>#:getAvbacknamess#</td>
                                <td>#:getStatename#</td>
                                <td>#:getTypename#</td>
                            </tr>
                        </m:iterate-collection>              
                    </table>
                </body>
            </m:windowdirect>   
  
            <br>
            <!--table for genetic background info-->
            <m:windowdirect title="Genetic Background" name="model.block.genback" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>                        
                            <th class="data2" width="20%">DNA Origin</th>
                            <th class="data2" width="20%">Targeted Background</th>
                            <th class="data2" width="20%">Host Background</th>
                            <th class="data2" width="20%">Backcrossing Strain</th>
                            <th class="data2" width="20%">Backcrosses</th>
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
            </m:windowdirect>
                    
            <br>
            <!--table for strain info (designation, mgi id, state, type)-->
            <m:windowdirect title="Strain Information" name="model.block.strain" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="33%">Designation</th>
                            <% if (!sdto.getMgiId().equals("0")){ %>
                            <th class="data2" width="33%">JAX ID</th>
                            <% } %>
                            <% if (!sdto.getEmmaid().equals("0")){ %>
                            <th class="data2" width="33%">EMMA ID</th>
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
            </m:windowdirect>
            
            <br>
            <!--table for allele + mutation info -->
            <m:windowdirect title="Allele & Mutations" name="model.block.allele" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="50%">Name</th>
                            <th class="data2" width="30%">Symbol</th>
                            <%--th class="data2" width="10%">MGI ID</th>
                            <th class="data2" width="20%">Gene</th--%>
                            <th class="data2" width="20%">Mutation(s)</th>
                            <%--th class="data2" width="20%">Attributes</th--%>
                        </tr>
                        <m:iterate-collection collection="strainalleles"> 
                        <tr class="#?alt#">
                            <td><a href="DirectView?workflow=ViewStrainAlleleDirect&allid=#:getId#&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="view allele">#:getName#</a></td>
                            <td>#:getSymbol#</td>
                            <%--td>#:getMgiId#</td>
                            <td>#:getGeneName#</td--%>
                            <td>#:getMutations#</td>
                            <%--td>#:getAttributes#</td--%>
                        </tr>
                        </m:iterate-collection>
                    </table>
                </body>
            </m:windowdirect>
          
            <br>
            <!--table for genes affected-->
            <m:windowdirect title="Gene(s)" name="model.block.geneaffected" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>                        
                            <th class="data2" width="50%">Name</th>
                            <th class="data2" width="30%">Comment</th>
                            <th class="data2" width="20%">Chromosome</th>
                        </tr>
                        <m:iterate-collection collection="genesAffected">
                            <tr class="#?alt#">
                                <td><a href="DirectView?workflow=ViewGeneDirect&gaid=#:getGaid#&eid=<jsp:getProperty name="modeldto" property="eid"/>" title="view gene">#:getName#</a></td>
                                <td>#:getComm#</td>
                                <td>#:getChromoName#</td>
                            </tr>
                        </m:iterate-collection>              
                    </table>    
                </body>
            </m:windowdirect>
            
            <br>
            <!--handling + genotyping-->
            <m:windowdirect title="Handling & Genotyping Instructions" name="model.block.res" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <m:resource-simple-list resourceTreeCollection="resourceTree"/>
                </body>
            </m:windowdirect>
          
            <%--
            <br>
            <!--table for phenotype terms info -->
            <m:window title="Phenotype(s)" name="model.block.pheno" workflow="ViewModel">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="100%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="phenoPaths"> 
                        <tr class="#?alt#">
                            <td>#:getPhenoPath#</td>
                        </tr>
                        </m:iterate-collection>
                    </table>
                </body>
            </m:window>
            --%>
            
            
            <br>
            <!--table for mp paths -->
            <m:windowdirect title="Mammalian Phenotype Ontologies" name="model.block.mps" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                        <tr>
                            <th class="data2" width="100%">&nbsp;</th>
                        </tr>
                        <m:iterate-collection collection="mpz"> 
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
                </body>
            </m:windowdirect>
            
            <br>
            <!--table for references-->
            <m:windowdirect title="References" name="model.block.ref" workflow="ViewModelDirect">
                <menu></menu>
                <body>
                    <table class="block_data">
                    <tr>                        
                        <th class="data2" width="20%">Name</th>
                        <th class="data2" width="10%">Type</th>
                        <th class="data2" width="30%">Comment</th>
                    </tr>
                    <m:iterate-collection collection="references">
                        <tr class="#?alt#">
                            <td><a href="#:getResource#" target="_blank" title="view file/visit link">#:getName#</a></td>
                            <td>#:getType#</td>
                            <td>#:getComm#</td>
                        </tr>
                    </m:iterate-collection>              
                </table>
                </body>
            </m:windowdirect>
  
            <br>
            <!--table for author's comments-->          
            <m:windowdirect title="Author's Comments" name="model.block.authcomm" workflow="ViewModelDirect">
                <menu></menu>
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
            </m:windowdirect>
            
            <br>
        </form>
    </body>
    <% } else { %>
    
  <body class="contentdirect">
        <table class="toptabledirect">
            <tr><td>&nbsp;</td></tr>
        </table>
        <table class="winctrldirect">
            <tr><td>&nbsp;</td></tr>
        </table>
        <h1>no admission to MUGEN resource</h1>
        <p class="toolbar">&nbsp;</p>
        <br>
        <table>
            <tr>
                <td>the resource you requested is not available</td>
                <td>visit the MUGEN database <a href="Controller?workflow=begin" target="_top"><b>here</b></a></td>
            </tr>
        </table>
  </body>
    <% } %>
</html>
