<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>strains</title>
    </head>
    <body class="content">
    <form action="Controller" method="post">
        <h1>strains</h1>
        <p class="toolbar">
            <input type="image" src="images/icons/nav_left_blue_24.png" name="back" value="back" title="back">
        </p> 
        <br>
        <table class="data">
            <tr>
                <th class="data" width="15%"><input type="image" title="sortby MUGEN ID" src="images/version.2.0/icons/sortby_images_01_v.2.png" name="reset" value="Reset"></th>
                <th class="data" width="55%">Designation</th>
                <th class="data" width="15%"><input type="image" title="sortby MGI ID" src="images/version.2.0/icons/sortby_images_05_v.2.png" name="byMGI" value="MGI"></th>
                <th class="data" width="15%"><input type="image" title="sortby EMMA ID" src="images/version.2.0/icons/sortby_images_04_v.2.png" name="byEMMA" value="EMMA"></th>
            </tr>
                <m:iterate-collection collection="strains">
                    <tr class="#?alt#">
                        <td width="15%"><a href="Controller?workflow=ViewModel&expand_all=true&name_begins=model.block&eid=#:getEid#" title="view mouse">M#:getEid#</a></td>
                        <td width="55%"><a href="Controller?workflow=ViewStrain&strainid=#:getStrainId#" title="view strain">#:getDesignation#</a></td>
                        <td width="15%">#:getMgiIdURL#</td>
                        <td width="15%">#:getEmmaidURL#</td>
                    </tr>
                </m:iterate-collection>
        </table> 
    </form>
    </body>
</html>
