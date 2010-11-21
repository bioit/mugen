<jsp:useBean type="com.arexis.mugen.model.modelmanager.StrainAlleleDTO" id="allele" scope="request"/>
<%
     com.arexis.mugen.model.modelmanager.StrainAlleleDTO sadto = (com.arexis.mugen.model.modelmanager.StrainAlleleDTO)request.getAttribute("allele");
     int geneid = allele.getGeneId();
   %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>allele</title>
    </head>
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
        <h1>allele</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="DirectView" method="post">
            <table>
                <tr>
                    <td><b>Name:&nbsp;</b><jsp:getProperty name="allele" property="name"/></td>
                </tr>
                <tr>
                    <td><b>Symbol:&nbsp;</b><jsp:getProperty name="allele" property="symbol"/></td> 
                </tr>
                <tr>
                    <td><b>MGI ID:&nbsp;</b><jsp:getProperty name="allele" property="mgilink"/></td>
                </tr>
                <tr>
                    <td><b>Gene Involved:&nbsp;</b><jsp:getProperty name="allele" property="geneName"/></td>
                </tr>
                <tr>
                    <td><b>Mutation(s):&nbsp;</b><jsp:getProperty name="allele" property="mutations"/></td>
                </tr>
                <tr>
                    <td><b>Attribute:&nbsp;</b><jsp:getProperty name="allele" property="attributes"/></td>
                </tr>
            </table>
            <br>
            <table width="100%">
                <tr>
                    <td><b>Related MUGEN Mice</b></td>
                </tr>
                <m:iterate-collection collection="models">
                <tr class="#?alt#">
                    <td><a class="menu" href="DirectView?workflow=ViewModelDirect&eid=#:getEid#" title="view mouse">#:getLineName#</a></td>
                </tr>
                </m:iterate-collection>
            </table>
            <br>
            <p class="toolbar">
            <% if(request.getParameter("eid")!=null){ %>
                <a href="DirectView?workflow=ViewModelDirect&eid=<%=request.getParameter("eid")%>"><m:img title="go back" name="back2models"/></a>&nbsp
            <% } %>    
            </p>
        </form>
    </body>
</html>
