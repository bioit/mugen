<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>mugen database environment</title>
    <link rel="stylesheet" type="text/css" href="test.css" />
    <script defer language="JavaScript" src="javascripts/framebreak.js"></script>
    <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
  </head>
  <body class="toptable">
  <%
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
  %>
  
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
    
    <table class="toptable">
        <tr>
            <td width="140px">&nbsp;</td>
            <td align="right"><a href="http://www.fleming.gr/mrb" target="_blank" title="visit MRB"><img src="images/logos/mrb.png" alt="mrb | mouse resource browser"></a></td>
        </tr>
    </table>
  </body>
</html>
