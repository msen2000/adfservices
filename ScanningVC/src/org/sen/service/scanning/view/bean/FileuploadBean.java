package org.sen.service.scanning.view.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class FileuploadBean {
    public FileuploadBean() {
    }

    public void onUploadValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("onFileuploadValueChangeListener:: has been invoked.");
        
        UploadedFile uplFile = (UploadedFile)valueChangeEvent.getNewValue();
        String fileName = uplFile.getFilename();
        String contType= uplFile.getContentType();
        long length = uplFile.getLength();
        
        System.out.println("fileName - "+fileName);
        System.out.println("contType - "+contType);
        System.out.println("length - "+length);
             
        
        //uploadFileAndScan
        this.uploadFileAndScan(uplFile);
        
        }
        
        private void uploadFileAndScan(UploadedFile file) {
        System.out.println("UploadFileAndScan invoked for the file name = '"+file.getFilename()+"'");
        String fileName = file.getFilename();        

        // Location where files can be uploaded.
        String uploadLoc = "C:\\Temp\\adf";
        File destinationDir = new File(uploadLoc);
        boolean exists = destinationDir.exists();
        // Create upload directory if it does not exists.
        if (!exists) {
            destinationDir.mkdirs();
        }
        
        File destinationFile = new File(uploadLoc, fileName);
        try {
            this.copy(file.getInputStream(), new FileOutputStream(destinationFile));
            System.out.println("copy done....");
            boolean validated = true;//FileScaningService.validateAttachmentFile_win(fileName);
            if (validated == false) {                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File has virus", null)); 
                return ;       
            }
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return;
        }
        
        private void copy(InputStream in, OutputStream out) {
        try {
            int bytes = -1;
            while ((bytes = in.read()) > -1) {
                out.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }

    public void onValuechange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("sdsdsd");
    }
}
