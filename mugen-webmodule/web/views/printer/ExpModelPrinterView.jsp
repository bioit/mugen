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
        <title><jsp:getProperty name="modeldto" property="lineName"/></title>
    </head>
    <body>
    <form>
        <h1>
            <jsp:getProperty name="modeldto" property="lineName"/>
        </h1>
        <table><tr><td><p id="small"><a href="javascript:window.print()">[print]</a></p></td></tr></table>
                <!--general info table-->
                <table class="block_data">
                    <tr>
                        <td class="pftitle">General information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <tr>
                        <td class="block">
                            <b>Official Strain Designation:</b> <jsp:getProperty name="straindto" property="designation"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="block">
                            <b>Common Line Name:</b> <jsp:getProperty name="modeldto" property="lineName"/>
                        </td>
                    </tr>
                    <tr><td>
                    <table><tr>
                        <td class="block">
                            <b>MUGEN ID:</b> <jsp:getProperty name="modeldto" property="accNr"/>
                        </td>
                        <% if (!sdto.getMgiId().equals("0")){ %>
                        <td><b>JAX ID:</b> <jsp:getProperty name="straindto" property="mgiId"/></td>
                        <% } %>
                        <% if (!sdto.getEmmaid().equals("0")){ %>
                        <td><b>EMMA ID:</b> EM:<jsp:getProperty name="straindto" property="emmaid"/></td>
                        <% } %>
                    </tr></table>
                    </td></tr>
                    <tr>
                        <td class="block">
                            <b>Research Applications Type:</b> <jsp:getProperty name="modeldto" property="researchAppType"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="block">
                            <b>Research Applications Comments:</b> <jsp:getProperty name="modeldto" property="researchAppText"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="block">
                            <b>Other Comments:</b> <jsp:getProperty name="modeldto" property="comm"/>
                        </td>
                    </tr>
                </table>
                <!--end general info table-->
                <br>
		<!--contact table-->
		<table class="block_data">
                    <tr>
                        <td class="pftitle">Contact Information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <tr>
                        <td class="block">
                            <b>Contact:</b> <jsp:getProperty name="modeldto" property="contactName"/> <b>e-mail:</b> <jsp:getProperty name="modeldto" property="contactMail"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="block">
                            <b>Institution:</b> <jsp:getProperty name="modeldto" property="participant"/>
                        </td>
                    </tr>
		</table>
                <!--end of contact table-->
                <br>
                <!--availability info table-->
                <table class="block_data">
                    <tr>
                         <td class="pftitle">Availability Information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="availability">
                    <tr><td><b>Repository:</b> #:getReponame#</td></tr>
                    <tr><td><b>Available Genetic Background:</b> #:getAvbacknamess#</td></tr>
                    <tr><td><b>Strain State:</b> #:getStatename#</td></tr>
                    <tr><td><b>Strain Type:</b> #:getTypename#</td></tr>
                    <tr><td><hr id="ruler"></td></tr>
                    </m:iterate-collection>              
                </table>   
                <!--end of availability info table-->
                <br>
                <!--genetic background info table-->
                <table class="block_data">
                    <tr>
                        <td class="pftitle">Genetic Background Information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="geneticBackground">
                        <tr><td><b>DNA Origin:</b> #:getDna_origin_namess#</td></tr>
                        <tr><td><b>Targeted Background:</b> #:getTargeted_back_namess#</td></tr>
                        <tr><td><b>Host Background:</b> #:getHost_back_namess#</td></tr>
                        <tr><td><b>Backcrossing Strain:</b> #:getBackcrossing_strain_namess#</td></tr>
                        <tr><td><b>Number Of Backcrosses:</b> #:getBackcrosses#</td></tr>
                        </tr>
                    </m:iterate-collection>              
                </table>
                <!--end of genetic background info table-->
                <br>
                <!--allele & mutations info table-->
                <table class="block_data">
                    <tr>
                        <td class="pftitle">Allele & Mutations Information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="strainalleles">
                    <tr><td><b>Allele Symbol:</b> #:getSymbol#</td></tr>
                    <tr><td><b>Allele Name:</b> #:getName#</td></tr>
                    <tr><td><b>Allele MGI ID:</b> #:getMgilink#</td></tr>
                    <tr><td><b>Mutation Type(s):</b> #:getMutations#</td></tr>
                    <tr><td><b>Attributes:</b> #:getAttributes#</td></tr>
                    <tr><td><hr id="ruler"></td></tr>
                    </m:iterate-collection>
                </table>
                <!--end of allele & mutations info table-->   
                <br>
                <!--affected genes info table-->
                <% if (isTransgenic==0){ %>
                <table class="block_data">
                    <tr>
                        <td class="pftitle">Affected Genes</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="genesAffected">
                        <tr><td><b>Gene Symbol:</b> #:getGenesymbol#</a></td></tr>
                        <tr><td><b>Gene Name:</b>#:getName#</td></tr>
                        <tr><td><b>Chromosome: </b>#:getChromoName#</td></tr>
                        <tr><td><b>MGI ID: </b>MGI:#:getMgiid#</td></tr>
                        <tr><td><b>ENSEMBL ID: </b>#:getIdensembl#</td></tr>
                        <tr><td><hr id="ruler"></td></tr>
                    </m:iterate-collection>              
                </table>    
                <% }else{ %>
                <table class="block_data">
                    <tr>
                        <td class="pftitle">Transgene Information</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="genesAffected">
                        <tr><td><b>Gene Symbol:</b> #:getGenesymbol#</a></td></tr>
                        <tr><td><b>Gene Name:</b>#:getName#</td></tr>
                        <tr><td><b>Chromosome: </b>#:getChromoName#</td></tr>
                        <tr><td><b>MGI ID: </b>MGI:#:getMgiid#</td></tr>
                        <tr><td><b>ENSEMBL ID: </b>#:getIdensembl#</td></tr>
                        <tr><td><hr id="ruler"></td></tr>
                    </m:iterate-collection>              
                </table>    
                <% } %>
                <!--end of affected genes info table-->
                <br>
                <!--mammalian phenotype ontologies table-->
                <table class="block_data">
                    <tr>
                        <td class="pftitle">Mammalian Phenotype Ontologies</td>
                    </tr>
                    <tr><td><hr id="ruler"></td></tr>
                    <m:iterate-collection collection="mpz">
                    <tr><td>#:getMpnameshort#</td></tr>
                    </m:iterate-collection>
                </table>
                <!--end of mammalian phenotype ontologies table-->
                <!--footer table-->
                <table>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td><p id="small">&copy; 2007 MUGEN NoE</p></td></tr>
                </table>
    </form>
    </body>
</html>
