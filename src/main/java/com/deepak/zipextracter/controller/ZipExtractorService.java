package com.deepak.zipextracter.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ZipExtractorService {

    private static final String OUTPUT_DIR = "D:\\ZipExtractor";

    public void extractZip(MultipartFile file) throws IOException {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File tempFile = File.createTempFile("temp-", ".zip");
        file.transferTo(tempFile);

        extractAllZipFiles(tempFile, outputDir);
    }

    private void extractAllZipFiles(File zipFile, File outputDir) throws IOException {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File file = new File(outputDir, entry.getName());

                if (entry.isDirectory()) {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } else {
                    extractFile(zip, entry, file);
                    if (file.getName().endsWith(".zip")) {
                        // Recursively extract nested zip files
                        extractAllZipFiles(file, file.getParentFile());
                    }
                }
            }
        }
    }

    private void extractFile(ZipFile zip, ZipEntry entry, File outputFile) throws IOException {
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (InputStream is = zip.getInputStream(entry);
             OutputStream os = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }
    }
}
