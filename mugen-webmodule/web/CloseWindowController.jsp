<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Control Close Window Frame</title>
    </head>
    <body class="winctrl">
    <form method="post" action="Controller?workflow=SearchKeywordFast" target="page">
    <table class="winctrl" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left" valign="bottom">
                &nbsp;<a href="Welcome" target="page" class="winctrl">home</a>
                |&nbsp;<a href="views/help/help.jsp" target="page" class="winctrl">help</a>
                <%--|&nbsp;<a href="Contact" target="page" class="winctrl">contact</a>--%>
                |&nbsp;<a href="Controller?workflow=Contact" target="page" class="winctrl">contact</a>
                |&nbsp;<a href="About" target="page" class="winctrl">about</a>
                |&nbsp;search:&nbsp;<input type="text" name="fast_search_key">
                mutants<input type="checkbox" class="check" name="filter" value="mutants">
                genes<input type="checkbox" class="check" name="filter" value="genes">
                alleles<input type="checkbox" class="check" name="filter" value="alleles">
                mp terms<input type="checkbox" class="check" name="filter" value="mps">
            </td>
            <td align="right">v.2.1.0&nbsp;&nbsp;<input type="hidden" name="ctrl" value="ctrl"></td>
        </tr>
    </table>
    </form>
    </body>
</html>
