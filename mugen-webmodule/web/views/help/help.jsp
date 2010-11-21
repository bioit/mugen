<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)session.getAttribute("caller");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="../../test.css"/>
        <script defer language="JavaScript" src="../../javascripts/framebreak.js"></script>
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Help</title>
    </head>
    <body class="content">
    <h1>help</h1>
    <p class="toolbar">&nbsp;</p>
    <br>
    <%--view mutant mice strain details--%>
    <table class="block">
        <th class="block"><a href="help.jsp?help.viewstrain_display=true" title="Expand/Collapse this section">View MUGEN Mutant Mouse Details</a></th>
        <tr><td></td></tr>
        <m:hide-block name="help.viewstrain_display">
        <tr>
            <td><ul><li><b>Straight from the list of MUGEN Mutant Mice</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "MUGEN Mutant Mice" button on the menu on your left.</li>
                    <li>Click on the "View M.M.M." button that appears underneath the "MUGEN Mutant Mice" button.</li>
                    <li>From the list of all MUGEN Mutant Mice you can select the one that is of interest to you. To view detailed information about this model you can click on the model's "Common Line name". You will be then redirected to the "Detailed information" page.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Via a specific gene</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Genes" button on the menu on your left.</li>
                    <li>Click on the "View Genes" button that appears underneath the "Genes".</li>
                    <li>From the list of genes find the one that is relevant to the desired MUGEN Mutant Mouse. Click on the arrow button on the right gene's row.</li>
                    <li>From the list of MUGEN Mutant Mice that appears that are relevant to the gene you selected select the MUGEN Mutant Mouse's "Common Line name". You will be redirected to its "Detailed information" page.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Using the MUGEN search engine</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Search" button on the menu on your left.</li>
                    <li>Click on the "Keyword" button to search using a specific keyword.</li>
                    <li>Type the keyword in the text field and click on the "search" button underneath the text field.</li>
                    <li>When the search is completed a list with all results matching your keyword will be displayed. In the list the MUGEN Mutant Mouse should appear. By clicking on its name you will be redirected to its "Detailed Information" page.</li>
                </ol>
            </td>
        </tr>
        </m:hide-block>
    </table>
    
    <br>
    <%--view gene details--%>
    <table class="block">
        <th class="block"><a href="help.jsp?help.viewgene_display=true" title="Expand/Collapse this section">View Gene Details</a></th>
        <tr><td></td></tr>
        <m:hide-block name="help.viewgene_display">
        <tr>
            <td><ul><li><b>Straight from the list of genes</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Genes" button on the menu on your left.</li>
                    <li>Click on the "View Genes" button that appears underneath the blue "Genes" button.</li>
                    <li>From the list of all genes you can select the one that is of interest to you. To view detailed information about this gene you can click on the lens icon on the details column.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Via a specific mutant strain</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "MUGEN Mutant Mice" button on the menu on your left.</li>
                    <li>Click on the "View M.M.M." button that appears underneath the "MUGEN Mutant Mice" button.</li>
                    <li>From the list of all MUGEN Mutant Mice you can select the one that is of interest to you. To view detailed information about this model you can click on the model's "Common Line name". You will be then redirected to the "Detailed information" page.</li>
                    <li>On the "Detailed Information" page of the mutant strain you must click on the "Genes affected" tab.</li>
                    <li>Once revealed this tab will display all genes that are connected to this mutant strain. Should the gene you are looking for be amongst them, you can click on the lens icon on the "View" column to access all details relevant to this model.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Using the MUGEN search engine</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Search" button on the menu on your left.</li>
                    <li>Click on the "Keyword" button to search using a specific keyword.</li>
                    <li>Type the keyword in the text field and click on the "search" button underneath the text field.</li>
                    <li>When the search is completed a list with all results matching your keyword will be displayed. In the list the gene should appear. By clicking on its name you will be redirected to its "Gene" page.</li>
                </ol>
            </td>
        </tr>
        </m:hide-block>
    </table>
    
    <% if(caller.hasPrivilege("MODEL_MUGEN")){ %>
    <br>
    <%--create mutant mice strain instructions--%>
    <table class="block">
        <th class="block"><a href="help.jsp?help.createstrain_display=true" title="Expand/Collapse this section">Create a MUGEN Mutant Mouse</a></th>
        <tr><td></td></tr>
        <m:hide-block name="help.createstrain_display">
        <tr>
            <td><ul><li><b>Navigation</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "MUGEN Mutant Mice" button on the menu on your left.</li>
                    <li>Click on the "Create M.M.M." link that appears underneath the blue "MUGEN Mutant Mice" button.</li>
                    <li>The "Create MUGEN Mutant Mouse" page should appear.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Adding basic & general information</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>To add basic & general data you need to fill most of the fields on the "Create Mutant Mouse Strain" page</li>
                    <li><b>Line name: </b>Type the name of the mutant mouse strain (line). You can use letters, numbes and most special characters.</li>
                    <li><b>Research application type: </b>Select the appropriate research application type for the mutant mouse strain you wish to submit. You can choose amongst "MODEL OF HUMAN DISEASE", "MODEL OF IMMUNE PROCESSES" and "TRANSGENIC TOOL". If you would like to have more available options please contact the MUGEN database administrator.</li>
                    <li><b>Research applications Comments: </b>Type comments relevant to the research applications of the mutant mouse strain you wish to submit. (Optional)</li>
                    <li><b>Contact: </b>Select (from the drop-down list) the name of the contact person for the strain you wish to submit.</li>
                    <li><b>Authors comments: </b>Type comments that you would like to add to the strain you wish to submit. (Optional)</li>
                    <li><b>Save your strain: </b>Once you have completed all fields you must click on the diskette icon to save the information in the MUGEN database.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Adding detailed data</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Once you have created a strain (in other words once you have clicked on the diskette icon), you are presented with a list of all MUGEN Mutant Mice that exist in the MUGEN database.
                    Amongst them you will notice the new mutant mouse strain you have just created. To add more detailed information about the strain you have to select the newly created strain from the list
                    by clicking on its "Common Line name". On this page tabs are used to group information and make access and submission easier.
                    </li>
                    <br>
                    <li><b>Availability: </b>
                    <br>
                    <b>- Check Availability </b>
                    To make sure that you are not entering information twice, check the availability of a strain before you add availability information. To check the availability of a strain you just need to click on the Availability tab's title.
                    <br>
                    <b>- Add Availability</b>
                    Click on the green plus sign next to the tab's title. You will be redirected to the "Add Availability Information" page. At this page you can select a repository, an available genetic background, the state and the type of the strain from the drop-down menus. 
                    If the list of repositories does not include the repository you would like to select you have an option to create a new repository by clicking on the appropriately named link. The same goes for the available genetic background list.
                    To save the combination of information you have selected click on the diskette ("save") icon. You will then be redirected to the strain's detailed information page. 
                    To add even more information on availability click on the green plus sign again and repeat the forementioned steps.
                    </li>
                    <br>
                    <li><b>Genetic Background: </b>
                    <br>
                    <b>- Check Genetic Background Information</b>
                    To check the genetic background of a strain you just need to click on the "Genetic Background" tab's title.
                    <br>
                    <b>- Add Genetic Background Information</b>
                    Click on the green plus sign next to the tab's title. You will be redirected to the "Add Information Relevant to the Mutant's Background Information" page. 
                    At this page you can select the strain the dna derived from (dna origin), the targeted background, the host background, the backcrossing strain and the number of backcrosses.
                    All information is provided in the form of easy to use drop-down menus. If however you would like to add a strain so that you can select it from the drop-down list in any field you can click on 
                    the "Create a new background" link. You will be redirected to the "Create genetic background" page where you can freely type the strain you would to add in the forementioned drop-down lists. 
                    Once you save the new background strain you return to the "Add Information Relevant to the Mutant's Background Information" page. When you are done with the submission of data you will have to click on the diskette icon to store the data in the MUGEN database. 
                    Please note that only one set of data relevant to genetic background information can be associated with a mutant mouse strain. Once you store them you can only edit them.
                    <br>
                    <b>- Edit Genetic Background Information</b>
                    When genetic background information for a mutant mouse strain is stored you can edit it by clicking on the edit icon that appears next to the "Genetic Background Information" tab's title. You will be then redirected to the "Edit Genetic Background Info" page 
                    where you can freely alter all stored information about the mutant mouse strain's genetic background information.
                    </li>
                    
                    <br>
                    <li><b>Strain Information: </b>
                    <br>
                    <b>- Check Strain Information</b>
                    To check the strain information of a strain you just need to click on the "Strain Information" tab's title.
                    <br>
                    <b>- Add/Edit Strain Information</b>
                    Click on the edit icon. You will be redirected to the "Strain" page where you can edit/add the strain's designation (in accordance with MGNC's nomenclature rules available <a href="http://www.informatics.jax.org/mgihome/nomen/" target="_blank">here</a>) 
                    and the strain's MGI id if applicable. Clicking on the diskette icon the information you have submitted will be stored in the MUGEN database.
                    </li>
                    
                    <br>
                    <li><b>Allele and mutations: </b>
                    <br>
                    <b>- Check Allele and mutations Information</b>
                    To check the Allele and mutations information of a strain you just need to click on the "Allele and mutations" tab's title.
                    <br>
                    <b>- Add/Edit Allele and mutations Information</b>
                    Click on the green plus sign. On the "Create new Strain Allele" web-page type the allele symbol and its name in the text fields. Then click on the diskette icon. 
                    You will be then redirected back to the detailed information page of the mutant mouse strain. In the "Allele and mutations" tab the allele name and symbol you have typed will appear. 
                    To add even more detailed information click on the edit icon on the "Details" column of the "Allele and mutations" tab. A new web-page opens. 
                    In this page you can edit the allele name and symbol, select the gene that is associated with the allele from the drop-down list and type the MGI id of the allele if known. 
                    You can also select mutation attributes from the drop-down menu. Most importantly you can add mutation types to the mutant mouse strain. To do that 
                    you will have to click on the green plus sign. On the "Add Mutation types" page you can select the mutation type from the drop-down list. By clicking on the diskette icon the mutation type you selected 
                    is temporarily associated with the mutant mouse strain and you are redirected to the "Edit Strain Allele" page. You can add even more mutation types by clicking the green plus sign again and repeating the previous steps. 
                    Once you have finished entering allele and mutation information you can click on the diskette icon to store this data permanenlty in the MUGEN database. You will return to the detailed information page of the mutant mouse strain automatically.
                    </li>
                    
                    <br>
                    <li><b>Genes affected: </b>
                    <br>
                    <b>- Check Genes affected</b>
                    To check the Genes affected of a strain you just need to click on the "Genes affected" tab's title.
                    <br>
                    <b>- Add/Edit Genes affected</b>
                    Click on the green plus sign. You will be redirected to the "Assign a gene to model" web-page. On this page you can select the gene that is affected and the relevant chromosome from the two drop-down menus. 
                    If the selection of genes does not satisfy you and if you want to add a new gene that is not included in the MUGEN database or the drop-down list of genes you can click on "Create a new gene" link on the "Assign a gene to model" web-page.
                    The "Create gene" page will open and you can add information relevant to the gene you wish to add. When you are finished you can click on the diskette icon and you will be redirected to the "Assign a gene to model" page again. 
                    Back on the "Assign a gene to model" page you can review your selections and click on the diskette icon to relate the information to the mutant mouse strain and store it in the database. The next page you will be automatically redirected is the detailed information page of the mutant mouse strain you have currently created or are editing.
                    </li>
                    
                    <br>
                    <li><b>Handling & Genotyping Instructions: </b>
                    <br>
                    <b>- Check Handling & Genotyping Instructions</b>
                    To check the Handling & Genotyping Instructions of a strain you just need to click on the "Handling & Genotyping Instructions" tab's title.
                    <br>
                    <b>- Add/Edit Handling & Genotyping Instructions</b>
                    To add Handling & Genotyping Instructions you have two options. One is to upload a file (or files) containing those instructions or alternatively you can add a link to another web-page or a document that is available on-line (on another database perhaps).
                    <br>
                       <b>+Adding a file: </b>To add a file you need to click on the green plus sign next to the Handling & Genotyping Instructions tab's main title. A new page entitled "File Upload" should open. Clicking on the "Browse..." button allows you to browse your computer for the file you wish to upload. 
                       In the new window navigate to the desired file, select it and click the "Open" button. The name of the file you have just selected will appear in the "File" text area. Underneath the "File" text square you will find the "Comment" text field. There you can type any further comments. 
                       To upload and save the given data you will have to click on the "Upload" button. Once stored you will be redirected to the detailed information page of the mutant mouse strain. From there you can freely edit the data you have submitted by clicking on the edit icon on the "Edit" column of the specific tab. 
                       You can even delete/remove the data you have submitted by clicking on the remove ("rex x") icon on the remove column.
                    <br>
                        <b>+Adding a link: </b>To add a link you will have to click on the link icon, which is located on the right side of the green plus sign on the tab title's row. On the "Create link" page that will open you can type a name for the link you are going to submit. On the "URL" text field 
                        you can type the web address of the link. The "Comment" text field is there for you to add any further comments. Click on the blue diskette icon to store the information you have entered. Again you will return to the detailed information page of the mutant mouse strain. Of course you can edit or even delete the data you have submitted as mentioned above.
                    </li>
                    
                    <br>
                    <li><b>References: </b>
                    <br>
                    The process of viewing and adding/editing references data is identical to the process that needs to be followed to view, add or edit Handling & Genotyping Instructions; so please read the relevant help section.
                    </li>
                    
                    <br>
                    <li><b>Author's Comments: </b>
                    <br>
                    <b>- Check Author's Comments</b>
                    On the "Adding basic & general information" help section, the way to add the author's comments was described. To view these comments  you will have to click on the tab's title.
                    <br>
                    <b>- Add/Edit Author's Comments</b>
                    To see how one can add author's comments visit the "Adding basic & general information" section.
                    <br>
                    To edit the author's comments you need to click on the edit icon right next to the "Author's Comments" tab title. You will be redirected to the "Edit MUGEN Mutant Mouse" web-page where you can edit all basic & general information. For the changes to take effect you'll have to click on the save button.
                    <br>
                    </li>
                </ol>
            </td>
        </tr>
        </m:hide-block>
    </table>
    <% } %>
    
    <% if(caller.hasPrivilege("MODEL_MUGEN")){ %>
    <br>
    <%--create new gene instructions--%>
    <table class="block">
        <th class="block"><a href="help.jsp?help.creategene_display=true" title="Expand/Collapse this section">Create a Gene</a></th>
        <tr><td></td></tr>
        <m:hide-block name="help.creategene_display">
        <tr>
            <td><ul><li><b>Navigating to the "Create Gene" page from the main menu</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Genes" button on the menu on your left.</li>
                    <li>Click on the "Create Gene" link that appears underneath the blue "Genes" button.</li>
                    <li>The "Create gene" page should appear. On this page you can enter data.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Creating a Gene while entering Mutant Strain data</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Assuming that you have created a new mutant strain by adding basic & general information (refer to the "Create a MUGEN Mutant Mouse" instructions on this help section for details), click on the green plus sign next to the "Genes Affected" tab on the "Detailed Information" page of the new strain.</li>
                    <li>While on the "Assign a gene to model" page click on the "Create a new gene" link.</li>
                    <li>You will be redirected to the "Create gene" page to add all data for the new gene.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Adding Gene data</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li><b>Gene Name: </b>
                    <br>
                    Simply type the name of the Gene. You can use any special character, uppercase and lowercase.
                    </li>
                    <br>
                    <li><b>MGI Gene ID: </b>
                    <br>
                    Type the MGI id of the Gene if known. You can use numbers and characters.
                    </li>
                    <br>
                    <li><b>Gene Symbol: </b>
                    <br>
                    Type the symbol of the Gene. You are free to use all special characters, uppercase, lowercase and numbers.
                    </li>
                    <br>
                    <li><b>Gene Expression: </b>
                    <br>
                    Type the Gene's expression. You are free to use all special characters, uppercase, lowercase and numbers.
                    </li>
                    <br>
                    <li><b>GENE Database Link: </b>
                    <br>
                    Type the GENE database id of the Gene. This id will be automatically converted to a GENE database link. All characters are allowed.
                    </li>
                    <br>
                    <li><b>ENSEMBL Database Link: </b>
                    <br>
                    Type the id of the ENSEMBL database that corresponds to the Gene. Again the link to the ENSEMBL database will be automatically generated. Of course all characters are allowed.
                    </li>
                    <br>
                    <li><b>Comment: </b>
                    <br>
                    Add a few words relevant to the Gene.
                    </li>
                    <br>
                    <li>To save the information you have provided you must click on the blue diskette icon.</li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Editing Gene data</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>From a Gene's main page you can click on the edit icon which is located under the title ("Gene") of the page.</li>
                    <li>On the "Edit gene" that opens next you can see all data that has been previously stored. You can freely edit or not this information. 
                        Fields that while entering gene data for the first time were left blank appear on the "Edit gene" page as "not provided yet". Of course you can edit them adding correct information or leave them as they are if you have nothing to add.
                    </li>
                </ol>
            </td>
        </tr>
        <tr>
            <td><ul><li><b>Deleting a Gene</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>From a Gene's main page you can click on the delete icon (the red x icon) which is located right next to the edit icon.</li>
                    <li>On the "Edit gene" that opens next you can see all data that has been previously stored. You can freely edit or not this information. 
                        Fields that while entering gene data for the first time were left blank appear on the "Edit gene" page as "not provided yet". Of course you can edit them adding correct information or leave them as they are if you have nothing to add.
                    </li>
                </ol>
            </td>
        </tr>
        </m:hide-block>
    </table>
    <% } %>
    
    <br>
    <%--searching the database instructions--%>
    <table class="block">
        <th class="block"><a href="help.jsp?help.search_display=true" title="Expand/Collapse this section">Search the MUGEN database</a></th>
        <tr><td></td></tr>
        <m:hide-block name="help.search_display">
        <tr>
            <td><ul><li><b>Searching the MUGEN database for strains and genes</b></li></ul></td>
        </tr>
        <tr>
            <td>
                <ol type="i">
                    <li>Click on the "Search" button on the menu on your left.</li>
                    <li>Click on the "Keywords" link that appears underneath the blue "Search" button.</li>
                    <li>The "Search keyword" page should appear. Type a keyword and press the "Search" button.</li>
                    <li>Once the search returns you should see the "Search results" page. The list contains all genes and MUGEN Mutant Mice that are somehow relevant to the keyword you have typed.
                        Whether it is the name of the gene or strain or another piece of information associated with a gene or strain (e.g. comments, references etc.).
                    </li>
                </ol>
            </td>
        </tr>
        </m:hide-block>
    </table>
    </body>
</html>
