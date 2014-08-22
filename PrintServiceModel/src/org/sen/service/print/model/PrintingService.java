package org.sen.service.print.model;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 *  sfphpx585-003 / SFPHPX585-003
 *  Model :HP Universal Printing PCL 6 (v5.2)
 *
 *  Found attribute: printer-name with value: \\SFOFPS005\SFPCN5250-012
 *
 * @see
 */
public class PrintingService {

    public PrintingService() {
        super();
    }
           
    public static void main(String[] args) throws Exception {
        // Locate the default print service for this environment.
        PrintService  service = PrintServiceLookup.lookupDefaultPrintService();
        Attribute[] attrs = service.getAttributes().toArray();
        for (int j=0; j<attrs.length; j++) {
            // Get the name of the category of which this attribute value is an instance.
            String attrName = attrs[j].getName();
            // get the attribute value
            String attrValue = attrs[j].toString();
            System.out.println("Found attribute: " + attrName + " with value: " + attrValue);
        }
        
        PrintingService.doprint();
    }
    
    /**
     *  Printing a pdf file from C:/Temp/adf location.
     *  DocFlavor is AUTOSENSE.
     *  The file is PDF.
     *  This method takes the Default printer setup in your local environment.
     *  
     * @see
     */
    private static void doprint() {
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;        
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        
        System.out.println("PrintService name : " + service.getName());            
        DocPrintJob pj = service.createPrintJob();
        try {               
           FileInputStream fis = new FileInputStream("C:\\Temp\\adf\\ar-11.pdf");
           Doc doc = new SimpleDoc(fis, flavor, null);
           pj.print(doc, aset);
            System.out.println("print is success!");
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();; 
        } catch (PrintException e) {
            e.printStackTrace();
        }            
    }
    
    private static void doprints() {
        DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
        pservices[0] = service;
        if (pservices.length > 0) {            
            System.out.println("PrintService name : " + pservices[0].getName());            
            DocPrintJob pj = pservices[0].createPrintJob();
            try {   
               FileInputStream fis = new FileInputStream("C:\\Temp\\adf\\sample.txt");
               Doc doc = new SimpleDoc(fis, flavor, null);
               pj.print(doc, aset);
                System.out.println("print is success!");
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();; 
            } catch (PrintException e) {
                e.printStackTrace();
            }            
        }
    }
    
    public static void printFile(String filename,String printerindx){
            FileInputStream psStream=null;
            int Printerinx=Integer.parseInt(printerindx);
            try {
               psStream = new FileInputStream(filename);
            } catch (FileNotFoundException ffne) {
                ffne.printStackTrace();
            } 
            if (psStream == null) {
                return;
            }   
            DocFlavor psInFormat = null;

            int index=filename.lastIndexOf(".");
            String extension=filename.substring(index+1);

            if(extension.equals("txt"))//||extension.equals("log")||extension.equals("xml")||extension.equals("htm")||extension.equals("html"))
            psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;

            else if(extension.equals("jpg"))
                psInFormat = DocFlavor.INPUT_STREAM.JPEG;

            else if(extension.equals("png"))
                psInFormat = DocFlavor.INPUT_STREAM.PNG;

            else if(extension.equals("gif"))
                psInFormat = DocFlavor.INPUT_STREAM.GIF; 


            Doc myDoc = new SimpleDoc(psStream, psInFormat, null);  
            PrintRequestAttributeSet aset = 
                new HashPrintRequestAttributeSet();
            aset.add(new Copies(1));
            aset.add(MediaSizeName.ISO_A4); 

            //aset.add(Sides.DUPLEX);
            aset.add(OrientationRequested.PORTRAIT);
            PrintService[] services = 
            PrintServiceLookup.lookupPrintServices(psInFormat, null);
            System.out.println("Printer Selected "+services[Printerinx]);   

            //PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

            DocFlavor[] docFalvor = services[Printerinx].getSupportedDocFlavors();
            for (int i = 0; i < docFalvor.length; i++) {
                System.out.println(docFalvor[i].getMimeType());
            }   
            if (services.length > 0) {
               DocPrintJob job = services[Printerinx].createPrintJob();
            try 
               {
                job.print(myDoc, aset);     
                  System.out.print("Printing Doc");    
               } catch (PrintException pe)
               {           
                   System.out.print(pe);          
               }
            }
            }   
//            public static void main(String [] args)
//            {       
//                printFile("D:/testStream.txt","3");
//            }
    
}
