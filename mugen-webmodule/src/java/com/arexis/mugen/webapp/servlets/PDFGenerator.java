package com.arexis.mugen.webapp.servlets;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.model.expmodel.*;
import com.arexis.mugen.model.availability.*;
import com.arexis.mugen.model.geneticbackground.*;
import com.arexis.mugen.model.strain.allele.*;
import com.arexis.mugen.species.gene.*;
import com.arexis.mugen.phenotype.ontology.*;
import com.arexis.mugen.model.modelmanager.*;
import com.arexis.mugen.MugenCaller;

import java.util.Collection;
import java.util.Iterator;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class PDFGenerator extends HttpServlet {

    protected ServiceLocator locator;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try{
            HttpSession se = request.getSession();
            MugenCaller caller = (MugenCaller)se.getAttribute("caller");
            
            if(request.getParameter("eid")!=null && request.getParameter("eid").length()>0){
                
                int eid = Integer.parseInt(request.getParameter("eid")); 
                locator = ServiceLocator.getInstance();
                ExpModelRemoteHome modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
                AvailabilityRemoteHome avHome = (AvailabilityRemoteHome)locator.getHome(ServiceLocator.Services.AVAILABILITY);
                GeneticBackgroundRemoteHome genbackHome = (GeneticBackgroundRemoteHome)locator.getHome(ServiceLocator.Services.GENETIC_BACKGROUND);
                StrainAlleleRemoteHome saHome = (StrainAlleleRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_ALLELE);
                GeneRemoteHome geneHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
                PhenotypeOntologyPathRemoteHome pathHome = (PhenotypeOntologyPathRemoteHome)locator.getHome(ServiceLocator.Services.PHENOTYPE_ONTOLOGY_PATH);
                
                ExpModelRemote model = modelHome.findByPrimaryKey(new Integer(eid));
                Collection avs = avHome.findByModel(model.getEid());
                Collection genbacks = genbackHome.findByGeneticBackgroundModel(model.getEid());
                Collection sas = saHome.findByStrain(model.getStrain().getStrainid(), caller);
                Collection genes = geneHome.findByModel(model.getEid());
                Collection paths = pathHome.findByModel(model.getEid());
                
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                response.setHeader("Pragma", "no-cache");
                
                response.setContentType("application/pdf");
                
                response.setHeader("Content-disposition","inline; filename="+"MUGEN_M"+model.getEid()+".pdf" );
                
                Document document = new Document(PageSize.A4, 25, 25, 25, 25);
                
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                PdfWriter docWriter = PdfWriter.getInstance(document, buffer);
                
                document.addAuthor("MUGEN NoE");
                document.addCreationDate();
                document.addTitle("MUGEN Mutant M"+model.getEid());
                document.addCreator("MUGEN NoE");
                HeaderFooter foot = new HeaderFooter(new Phrase("© 2007 MUGEN NoE", new Font(Font.HELVETICA, 8, Font.NORMAL, new Color(0,0,0))),true);
                foot.setBorderColor(new Color(255,255,255));
                document.setFooter(foot);
                document.open();
                
                //page title
                //document.add(new Paragraph(model.getAlias(), new Font(Font.HELVETICA, 18, Font.BOLD, new Color(204,102,51))));
                document.add(getSuperScript(model.getAlias(), 18, 1, new Color(204,102,51)));
                document.add(new Paragraph(" "));
                
                //create general info table
                PdfPTable general_table = new PdfPTable(3);
                PdfPCell cell = new PdfPCell(new Paragraph("General Info", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(3);
                cell.setBorderColorBottom(new Color(0,0,0));
                general_table.addCell(cell);
                
                //common line cell
                cell = new PdfPCell(new Paragraph("Common Line Name:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setBorderColorTop(new Color(0, 0, 0));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                //cell = new PdfPCell(new Paragraph(model.getAlias(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell = new PdfPCell(getSuperScript(model.getAlias(), 10, 0, new Color(0,0,0)));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                //official strain designation cell
                cell = new PdfPCell(new Paragraph("Official Strain Designation:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                //cell = new PdfPCell(new Paragraph(model.getStrain().getDesignation(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell = new PdfPCell(getSuperScript(model.getStrain().getDesignation().replaceAll("\\{", "").replaceAll("}",""), 10, 0, new Color(0,0,0)));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                //mugen id cell
                cell = new PdfPCell(new Paragraph("MUGEN ID:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph("M"+model.getEid(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                //research application cell
                cell = new PdfPCell(new Paragraph("Research Application Type:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(model.getResearchApplication().getName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                general_table.addCell(cell);
                
                if(!model.getResearchApplicationText().equals("") || !model.getResearchApplicationText().equals(" ")) {
                    
                    //research application comment cell
                    cell = new PdfPCell(new Paragraph("Research Application Comment:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    general_table.addCell(cell);
                
                    //cell = new PdfPCell(new Paragraph(model.getResearchApplicationText(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                    cell = new PdfPCell(getSuperScript(model.getResearchApplicationText(), 10, 0, new Color(0,0,0)));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(2);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    general_table.addCell(cell);
                
                }
                
                if(!model.getComm().equals("") || !model.getComm().equals(" ")) {
                    
                    //authors comment cell
                    cell = new PdfPCell(new Paragraph("Author's Comment:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    general_table.addCell(cell);
                
                    //cell = new PdfPCell(new Paragraph(model.getComm(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                    cell = new PdfPCell(getSuperScript(model.getComm(), 10, 0, new Color(0,0,0)));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(2);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    general_table.addCell(cell);
                    
                }
                
                //general info table align + width
                general_table.setWidthPercentage(100);
                general_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                document.add(general_table);
                document.add(new Paragraph(" "));
                
                //create contact info table
                PdfPTable contact_table = new PdfPTable(3);
                cell = new PdfPCell(new Paragraph("Contact Information", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(3);
                cell.setBorderColorBottom(new Color(0,0,0));
                contact_table.addCell(cell);
                
                //contact name + email
                cell = new PdfPCell(new Paragraph("Researcher:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setBorderColorTop(new Color(0, 0, 0));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                contact_table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(model.getContact().getName()+" ["+model.getContact().getEmail()+"]", new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                contact_table.addCell(cell);
                
                //institution
                cell = new PdfPCell(new Paragraph("Institution:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                contact_table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(model.getContact().getGroupName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                cell.setBorderColor(new Color(255, 255, 255));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                contact_table.addCell(cell);
                
                //contact info table align + width
                contact_table.setWidthPercentage(100);
                contact_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                document.add(contact_table);
                document.add(new Paragraph(" "));
                
                //availability table
                if(!avs.isEmpty()){
                    PdfPTable av_table = new PdfPTable(3);
                    cell = new PdfPCell(new Paragraph("Availability Information", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(3);
                    cell.setBorderColorBottom(new Color(0,0,0));
                    av_table.addCell(cell);
                
                    Iterator i = avs.iterator();
                    while(i.hasNext()){
                        AvailabilityRemote av = (AvailabilityRemote)i.next();
                        //repository
                        cell = new PdfPCell(new Paragraph("Repository:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setBorderColorTop(new Color(0, 0, 0));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(av.getRepositoryName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        //background
                        cell = new PdfPCell(new Paragraph("Background:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(av.getAvailableGeneticBackgroundName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        //state
                        cell = new PdfPCell(new Paragraph("State:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(av.getStateName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        //type
                        cell = new PdfPCell(new Paragraph("Type:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(av.getTypeName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        av_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(" "));
                        cell.setColspan(3);
                        cell.setBorderColor(new Color(255, 255, 255));
                        av_table.addCell(cell);
                    }
                    
                    //availability table align + width
                    av_table.setWidthPercentage(100);
                    av_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                    document.add(av_table);
                }
                
                //genetic background table
                if(!avs.isEmpty()){
                    PdfPTable gb_table = new PdfPTable(3);
                    cell = new PdfPCell(new Paragraph("Genetic Background", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(3);
                    cell.setBorderColorBottom(new Color(0,0,0));
                    gb_table.addCell(cell);
                
                    Iterator j = genbacks.iterator();
                    while(j.hasNext()){
                        GeneticBackgroundRemote gb = (GeneticBackgroundRemote)j.next();
                        //dna
                        cell = new PdfPCell(new Paragraph("DNA Origin:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setBorderColorTop(new Color(0, 0, 0));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gb.getBackNameFromBackId(gb.getDna_origin()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gb.getBackNameFromBackId(gb.getDna_origin()), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //targeted
                        cell = new PdfPCell(new Paragraph("Targeted Background:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gb.getBackNameFromBackId(gb.getTargeted_back()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gb.getBackNameFromBackId(gb.getTargeted_back()), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //host
                        cell = new PdfPCell(new Paragraph("Host Background:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gb.getBackNameFromBackId(gb.getHost_back()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gb.getBackNameFromBackId(gb.getHost_back()), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //backcrossing
                        cell = new PdfPCell(new Paragraph("Backcrossing Strain:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gb.getBackNameFromBackId(gb.getBackcrossing_strain()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gb.getBackNameFromBackId(gb.getBackcrossing_strain()), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        //backcrosses
                        cell = new PdfPCell(new Paragraph("Backcrosses:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(gb.getBackcrosses(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gb_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(" "));
                        cell.setColspan(3);
                        cell.setBorderColor(new Color(255, 255, 255));
                        gb_table.addCell(cell);
                    }
                    
                    //genetic background table
                    gb_table.setWidthPercentage(100);
                    gb_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                    document.add(gb_table);
                }
                
                //allele(s) & mutations
                if(!sas.isEmpty()){
                    PdfPTable am_table = new PdfPTable(3);
                    cell = new PdfPCell(new Paragraph("Allele & Mutations", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(3);
                    cell.setBorderColorBottom(new Color(0,0,0));
                    am_table.addCell(cell);
                
                    Iterator k = sas.iterator();
                    while(k.hasNext()){
                        StrainAlleleRemote sa = (StrainAlleleRemote)k.next();
                        StrainAlleleDTO tmp = new StrainAlleleDTO(sa);
                        //allele symbol
                        cell = new PdfPCell(new Paragraph("Allele Symbol:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setBorderColorTop(new Color(0, 0, 0));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(sa.getSymbol(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(sa.getSymbol(), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //allele name
                        cell = new PdfPCell(new Paragraph("Allele Name:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(sa.getName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(sa.getName(), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //mgiid
                        cell = new PdfPCell(new Paragraph("Allele MGI ID:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph("MGI:"+sa.getMgiId(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //mutation types
                        cell = new PdfPCell(new Paragraph("Mutation Type(s):", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(tmp.getMutations(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        //attributes
                        cell = new PdfPCell(new Paragraph("Attributes:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(sa.getAttributes(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        am_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(" "));
                        cell.setColspan(3);
                        cell.setBorderColor(new Color(255, 255, 255));
                        am_table.addCell(cell);
                    }
                    
                    //allele+mutations table align + width
                    am_table.setWidthPercentage(100);
                    am_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                    document.add(am_table);
                }
                
                //genes table
                if(!genes.isEmpty()){
                    PdfPTable gene_table = new PdfPTable(3);
                    cell = new PdfPCell(new Paragraph("Genes", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(3);
                    cell.setBorderColorBottom(new Color(0,0,0));
                    gene_table.addCell(cell);
                
                    Iterator l = genes.iterator();
                    while(l.hasNext()){
                        GeneRemote gene = (GeneRemote)l.next();
                        //gene symbol
                        cell = new PdfPCell(new Paragraph("Gene Symbol:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setBorderColorTop(new Color(0, 0, 0));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gene.getGenesymbol(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gene.getGenesymbol(), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //gene name
                        cell = new PdfPCell(new Paragraph("Gene Name:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //cell = new PdfPCell(new Paragraph(gene.getName(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell = new PdfPCell(getSuperScript(gene.getName(), 10, 0, new Color(0,0,0)));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //chromosome
                        cell = new PdfPCell(new Paragraph("Chromosome(s):", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(gene.getChromosome().getAbbr(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //mgiid
                        cell = new PdfPCell(new Paragraph("Gene MGI ID:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph("MGI:"+gene.getMgiid(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        //ensembl
                        cell = new PdfPCell(new Paragraph("ENSEMBL ID:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(gene.getIdensembl(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        gene_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(" "));
                        cell.setColspan(3);
                        cell.setBorderColor(new Color(255, 255, 255));
                        gene_table.addCell(cell);
                    }
                    
                    //contact info table align + width
                    gene_table.setWidthPercentage(100);
                    gene_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                    document.add(gene_table);
                }
                
                //phenopaths table
                if(!paths.isEmpty()){
                    PdfPTable path_table = new PdfPTable(3);
                    cell = new PdfPCell(new Paragraph("Mammalian Phenotype Ontologies", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
                    cell.setBorderColor(new Color(255, 255, 255));
                    cell.setColspan(3);
                    cell.setBorderColorBottom(new Color(0,0,0));
                    path_table.addCell(cell);
                
                    Iterator m = paths.iterator();
                    while(m.hasNext()){
                        PhenotypeOntologyPathRemote path = (PhenotypeOntologyPathRemote)m.next();
                        pathsMP tmp = new pathsMP(path);
                        cell = new PdfPCell(new Paragraph("MP Term:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setBorderColorTop(new Color(0, 0, 0));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        path_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(tmp.getMpnameshort(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
                        cell.setBorderColor(new Color(255, 255, 255));
                        cell.setColspan(2);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setVerticalAlignment(Element.ALIGN_TOP);
                        path_table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(" "));
                        cell.setColspan(3);
                        cell.setBorderColor(new Color(255, 255, 255));
                        path_table.addCell(cell);
                    }
                    
                    //paths table align + width
                    path_table.setWidthPercentage(100);
                    path_table.setHorizontalAlignment(Element.ALIGN_LEFT);
                
                    document.add(path_table);
                }
                
                
                document.close();
                docWriter.close();
                
                response.setContentLength(buffer.size());
                
                ServletOutputStream sos = response.getOutputStream ();
                buffer.writeTo(sos);
                sos.flush();
                
            }
        } catch (Exception e) {}
    }
    
    protected Paragraph getSuperScript(String line, int fontsize, int fontstyle, Color color){
        Paragraph p = new Paragraph("");
        
        int [] fonts = {Font.NORMAL, Font.BOLD};
        
        int [] fonts2 = {Font.ITALIC, Font.BOLDITALIC};
        
        Cell cell = new Cell("");
        
        //Chunk c = new Chunk(line, new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0)));
        Chunk c = new Chunk(line, new Font(Font.HELVETICA, fontsize, fonts[fontstyle], color));
        StringTokenizer spr = new StringTokenizer(line, "<");
        
        while(spr.hasMoreTokens()){
            String tmp = spr.nextToken();
            StringTokenizer spr_ = new StringTokenizer(tmp, ">");
            int i = 0;
            while(spr_.hasMoreTokens()){
                if(i==0 && tmp.contains(">")){
                    //c = new Chunk(spr_.nextToken(), new Font(Font.HELVETICA, 8, Font.ITALIC, new Color(0,0,0)));
                    c = new Chunk(spr_.nextToken(), new Font(Font.HELVETICA, fontsize-2, fonts2[fontstyle], color));
                    c.setTextRise(06.0f);
                } else {
                    //c = new Chunk(spr_.nextToken(), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0)));
                    c = new Chunk(spr_.nextToken(), new Font(Font.HELVETICA, fontsize, fonts[fontstyle], color));
                }
                i++;
                p.add(c);
            }
        }
        
        return p;
    }
    
    // <editor-fold defaultstate="collapsed">
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
