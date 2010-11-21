<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Contact</title>
    </head>
    <body>
    
    <%
       com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
    %>

    <h1>Contact</h1>
    <table>
        <tr>
            <td>Do you need help using the MUGEN Mutant Mice Database?</td>
        </tr>
        <tr>
            <td>Have you come across a bug that you would like to report?</td>
        </tr>
		<tr>
            <td>Would you like to report a dead link?</td>
        </tr>
        <tr>
            <td>Do you have comments, suggestions or another reason to contact the MMMDb development team?</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Contact the MMMDb administrator by  clicking <a href="mailto:mmmdb@mugen-noe.org"><b>here</b></a></td>
        </tr>
    </table>
    </body>
</html>
