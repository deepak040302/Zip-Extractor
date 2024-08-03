package com.deepak.zipextracter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ZipExtractorController {

    @Autowired
    private ZipExtractorService zipExtractorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "index";
        }

        System.out.println(file);
        try {
            zipExtractorService.extractZip(file);
            model.addAttribute("message", "File extracted successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to extract file: " + e.getMessage());
            e.printStackTrace();
        }

        return "index";
    }
}
