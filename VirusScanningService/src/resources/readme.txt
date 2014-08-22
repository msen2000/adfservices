Setup;
-----
1.) create scan.bat
        - has DOS command to run the McAfee Scan command. modify it based on ur local/env scanner installed.
2.) Two mentods available:
        - validateAttachmentFile_win()    - for windows
        - validateAttachmentFile() - for UNIX
3.) create a folder where we can upload files for scanning. 
        - "C:\\Temp\\adf
4.) Create filename(full path) of BAT file, update this in java file
        - C:\JDeveloper\adf\adf\scan\scan.bat

How to Run the Virus scanner.
----------------------------

1.) use the ScanningVC appliation (fileupload.jspx) 
2.) use main() in FileScanningService.java.