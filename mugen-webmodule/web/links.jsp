<%
    com.arexis.mugen.project.projectmanager.ProjectDTO project;
    project = (com.arexis.mugen.project.projectmanager.ProjectDTO)session.getAttribute("project.projectdto");
    
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%> 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <META HTTP-EQUIV="Window-target" CONTENT="_top">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store, no-cache, must-revalidate">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="Sat, 6 May 1995 12:00:00 GMT">
    <title>login frame</title>
    <link rel="stylesheet" type="text/css" href="test.css" />
  </head>
  <body class="logit">
  <%--
  <div id="linkcontainer">
    <table border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
    <tr align="left">
        <td height="56" width="56"><a class="linkcont"  href="http://www.informatics.jax.org/imsr/index.jsp" target="_blank"><img src="images/link-logo-imsr.jpg" border="0"></a></td>
        <td height="56" width="56"><a class="linkcont" href="http://strains.emmanet.org/mutant_types.php" target="_blank"><img src="images/link-logo-emma.jpg" border="0"></a></td>
        <td height="56" width="56"><a class="linkcont" href="http://www.informatics.jax.org" target="_blank"><img src="images/link-logo-mgi.jpg" border="0"></a></td>
    </tr>
    </table>
  </div>
  --%>
  <div id="centered2">
        <% if(caller.getUsr().compareTo("public")==0){ %>
        <form action="Controller?workflow=begin" name="login" method="post" target="_top">        
            <table id="logit">
                <tr>
                    <td class="logit">user:</td>
                </tr>
                <tr>
                    <td><input type="text" name="usr" maxlength="19" size="19" class="logit"></td>
                </tr>
                <tr>
                    <td class="logit">pass:</td>
                </tr>
                <tr>
                    <td><input type="password" name="pwd" maxlength="19" size="19" class="logit"></td>
                </tr>
                <tr>
                    <td><input name="submit" type="image" src="images/icons/login.png" align="left"></td>
                </tr>
          </table>
        </form>
        <% }else{ %>
        <form action="Controller?workflow=begin" name="login" method="post" target="_top">        
            <table id="logit">
                <tr>
                    <td class="logit"><b>user <%=caller.getUsr()%></b>
                    <input type="hidden" name="usr" value="public">
                    <input type="hidden" name="pwd" value="notknown">
                    </td>
                </tr>
                <tr>
                    <td><input name="submit" type="image" src="images/icons/logout.png" align="left"></td>
                </tr>
          </table>
        </form>
        <% } %>
    </div>
  </body>
</html>
