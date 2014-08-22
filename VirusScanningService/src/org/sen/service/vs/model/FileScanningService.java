package org.sen.service.vs.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class FileScanningService {
   
       
    private static String tempDirectory = null;
        
    private static final String SYSTEM_TEM_DIRECTORY = System.getProperty("java.io.tmpdir");
        
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    private final static String CLIENT_PROP_FILE_PATH ="C:\\Temp\\adf";

    public FileScanningService() {
        super();
    }
    
    public static String getTempDirectory(){
            if(tempDirectory == null){
                tempDirectory = CLIENT_PROP_FILE_PATH;
                System.out.println("From Properties " + tempDirectory);
                if (tempDirectory == null) {
                    tempDirectory = SYSTEM_TEM_DIRECTORY;
                    System.out.println("tempDirectory is null in application scope using system temp directory "  + tempDirectory);
                }            
            }
            System.out.println("tempDirectory----->" + tempDirectory);
            return tempDirectory;
        }
    
    public static  boolean validateAttachmentFile(String fileName) {
        
        System.out.println("Attachment File Scan called for file name : " + fileName);
        String tempdir = getTempDirectory();
        if (fileName == null || fileName.equals("")){
            return false;
        }
        System.out.println("OS name : "+OS);
        if(OS.indexOf("win") != -1){
            return true;
        }
        String virusSCANCMD = "/usr/local/bin/uvscan_secure @ --REPORT @.viruscan1.log";
        System.out.println("CMD:" + virusSCANCMD + ":");
        if (virusSCANCMD == null || StringUtils.isBlank(virusSCANCMD))
            return false;
        
        File scanFile = new File(tempdir + "/" + fileName);
        
        if(!scanFile.exists())return false;
        if (scanFile.exists()) {
            String cmdFileName = fileName.replace('\\', '/');
            if (File.separatorChar != '/')
                cmdFileName =
                        java.util.regex.Pattern.compile("/").matcher((CharSequence)cmdFileName).replaceAll("\\\\\\\\");
            System.out.println("CMD:FILE AFTER REPLACE :" + cmdFileName + ":");

            String reportFileName = null;

            String command = "";
            command = StringUtils.replace(virusSCANCMD, "@", tempdir+ "/" + fileName);
            String cmdArr[] = StringUtils.split(command);     
            System.out.println("CMD: array " + Arrays.toString(cmdArr));
            
            
            Process proc = null;
            
            try {
                System.out.println("Executing Virus Command " + Arrays.toString(cmdArr));
                
                proc = Runtime.getRuntime().exec(cmdArr);
                System.out.println("Scanning of Attachment file is complete " + fileName);               
                
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error executing the command " + Arrays.toString(cmdArr));
            }
            String line = null;
            StringBuffer sb = new StringBuffer();

            BufferedReader es =
                new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            BufferedReader is =
                new BufferedReader(new InputStreamReader(proc.getInputStream()));
            try {
                while (true) {
                    boolean isErrLine = false;
                    line = is.readLine();

                    if (line == null) {
                        isErrLine = true;
                        line = es.readLine();
                    }

                    if (line == null)
                        break;
                    sb.append("\r\n" +
                            line);
                }
                int exitValWait = proc.waitFor();
                int exitVal = proc.exitValue();
                System.out.println("FileUploader: VirusScan:DONE:" +
                                   exitValWait + ":" + exitVal);
                scanFile.delete();
                File scanReportFile = new File(tempdir+ "/" + fileName+".viruscan.log");
                scanReportFile.delete();
                if(exitVal == 13){ 
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error Reading command input/error stream virus scanning");
            }
        }
        System.out.println("file scan is done : the result is : true !");
        return true;
    }
    
    public static  boolean validateAttachmentFile_win(String fileName) {
        
        System.out.println("Attachment File Scan called for file name : " + fileName);
        String tempdir = "C:\\Temp\\adf";
        if (fileName == null || fileName.equals("")){
            return false;
        }
        System.out.println("OS name : "+OS);
    //        if(OS.indexOf("win") != -1){
    //            return true;
    //        }        
        String virusSCANCMD = "C:\\JDeveloper\\adf\\adf\\scan\\scan.bat";
        System.out.println("CMD:" + virusSCANCMD + ":");
        if (virusSCANCMD == null || StringUtils.isBlank(virusSCANCMD))
            return false;
        
        File scanFile = new File(tempdir + "\\" + fileName);
        
        if(!scanFile.exists()) {
            System.out.println("the file does not exist. "+scanFile);
            return false;
        }
        if (scanFile.exists()) {
            String reportFileName = null;
            Process proc = null;
            try {
                proc = Runtime.getRuntime().exec(virusSCANCMD);
                System.out.println("Scanning of Attachment file is complete " + scanFile);               
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error executing the command " + virusSCANCMD);
            }
            String line = null;
            StringBuffer sb = new StringBuffer();

            BufferedReader es = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            try {
                while (true) {
                    boolean isErrLine = false;
                    line = is.readLine();

                    if (line == null) {
                        isErrLine = true;
                        line = es.readLine();
                    }

                    if (line == null)
                        break;
                    sb.append("\r\n" + line);
                }
                System.out.println("## line : "+line);
                int exitValWait = proc.waitFor();
                int exitVal = proc.exitValue();
                System.out.println("FileUploader: VirusScan:DONE:" + exitValWait + ":" + exitVal);
                scanFile.delete();
                if (exitVal == 13) {  
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error Reading command input/error stream virus scanning");
            }
        }
        System.out.println("file scan is done : the result is : true !");
        return true;
    }
    
    }
