<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="gene" scope="request" type="com.arexis.mugen.model.modelmanager.GeneDTO" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title><jsp:getProperty name="gene" property="name"/></title>
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
        <h1>gene/transgene</h1>
        <p class="toolbar">&nbsp;</p>
    
    <form action="DirectView" method="post">
        <input type="hidden" name="gaid" value='<jsp:getProperty name="gene" property="gaid"/>'/>
        <table>
            <tr>
                <td><b>Name:</b>&nbsp;<jsp:getProperty name="gene" property="name"/></td>
            </tr>
            <tr>
                <td><b>Symbol:</b>&nbsp;<jsp:getProperty name="gene" property="genesymbol"/></td>            
            </tr>
            <tr>
                <td><b>Synonym/Old Name:&nbsp;</b><jsp:getProperty name="gene" property="geneexpress"/></td>            
            </tr>
            <tr>
                <td><b>Chromosome:</b>&nbsp;<jsp:getProperty name="gene" property="chromoName"/></td>
            </tr>
            <tr>
                <td><b>MGI ID:&nbsp;</b><jsp:getProperty name="gene" property="mgilink"/></td>
            </tr>
            <tr>
                <td><b>Entrez ID:&nbsp;</b><jsp:getProperty name="gene" property="entrezlink"/></td>            
            </tr>
            <tr>
                <td><b>ENSEMBL ID:&nbsp;</b><jsp:getProperty name="gene" property="ensemblink"/></td>            
            </tr>
            <tr>
                <td><b>ArrayExpress:&nbsp;</b><jsp:getProperty name="gene" property="arrayexpresslink"/></td>            
            </tr>
            <tr>
                <td><b>Eurexpress:&nbsp;</b><jsp:getProperty name="gene" property="eurexpresslink"/></td>            
            </tr>
            <tr>
                <td><b>Comment</b><br><jsp:getProperty name="gene" property="comm"/></td>                
            </tr>
            <tr>
                <td>                
                    <b>Contact:</b>&nbsp;<a href="mailto:<jsp:getProperty name="gene" property="userMail"/>" title="e-mail the contact person"><jsp:getProperty name="gene" property="userFullName"/></a> 
                </td>            
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
                <%--td>#:getComm#</td--%>
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
                <td><a class="menu" href="DirectView?workflow=ViewStrainAlleleDirect&allid=#:getId#&eid=<%=request.getParameter("eid")%>" title="view allele">#:getSymbol#</a></td>
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
