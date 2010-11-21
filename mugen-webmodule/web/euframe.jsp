<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>linx</title>
    <link rel="stylesheet" type="text/css" href="nav.css" />
    <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
  </head>
  <body class="eu">
  <%
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
  %>
  <table border="0" cellpadding="0" cellspacing="0" width="140">
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.informatics.jax.org/imsr/index.jsp" target="_blank" title="visit imsr website"><img src="images/logos/imsr.jpg" border="0"></a>
        </td>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.informatics.jax.org/" target="_blank" title="visit mgi website"><img src="images/logos/mgi.jpg" border="0"></a>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.ebi.ac.uk/ensembl/" target="_blank" title="visit ensembl website"><img src="images/logos/ensembl.jpg" border="0"></a>
        </td>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.ebi.ac.uk/arrayexpress/" target="_blank" title="visit array express website"><img src="images/logos/arrayexpress.jpg" border="0"></a>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://strains.emmanet.org/mutant_types.php" target="_blank" title="visit emma website"><img src="images/logos/emma.jpg" border="0"></a>
        </td>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.ncbi.nlm.nih.gov/sites/entrez" target="_blank" title="visit ncbi/entrez gene website"><img src="images/logos/ncbi.jpg" border="0"></a>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.casimir.org.uk/" target="_blank" title="visit casimir"><img src="images/logos/casimir.jpg" border="0"></a>
        </td>  
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.eurexpress.org/ee/" target="_blank" title="visit eurexpress"><img src="images/logos/eurexpress.jpg" border="0"></a>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://empress.har.mrc.ac.uk/" target="_blank" title="visit empress"><img src="images/logos/empress.jpg" border="0"></a>
        </td>  
        <td align="center" valign="top">
            <a class="linkcont" href="http://www.europhenome.eu/" target="_blank" title="visit europhenome"><img src="images/logos/europhenome.jpg" border="0"></a>
        </td>
      </tr>
      <tr>
        <td align="center" valign="top">
            <a class="linkcont" href="http://cordis.europa.eu/lifescihealth/genomics/home.htm" target="_blank" title="visit eu fp 6"><img src="images/logos/eufp6.jpg" border="0"></a>
        </td>  
        <td align="center" valign="top">
            <a class="linkcont" href="http://europa.eu/" target="_blank" title="visit eu website"><img src="images/logos/eu.jpg" border="0"></a>
        </td>
      </tr>
  </table>
  </body>
</html>
