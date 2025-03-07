package com.company.fileSharingManagement.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.company.fileSharingManagement.service.FileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequestMapping("/files")
@Tag(name = "File Management", description = "APIs for managing file uploads, downloads, and sharing")
public class fileController {

    @Autowired
    private FileService fileService;

    @GetMapping()
    public String login() {
        return "home";
    }

    @GetMapping("/home")
    @Operation(summary = "List all uploaded files", description = "Retrieves a list of all files uploaded by users")
    public String listFiles(Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        return "list-files";
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a file", description = "Uploads a file and saves it to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file upload request")
    })
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("uploadedBy") String uploadedBy) throws IOException {
        fileService.uploadFile(file, uploadedBy);
        return "redirect:/files/home";
    }

    @GetMapping("/share/{id}")
    @Operation(summary = "Generate a shareable link for a file", description = "Returns a shareable link for a specific file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shareable link generated"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    public String shareFile(@PathVariable("id") int id, Model model) {
        ResponseEntity<?> fileModel = fileService.shareFile(id);
        if (fileModel.hasBody()) {
            String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            model.addAttribute("shareUrl", currentUrl);
            model.addAttribute("file", fileModel.getBody());
            return "share-file";
        } else {
            return "redirect:/files";
        }
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download a file", description = "Allows users to download a specific file by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    public ResponseEntity<?> downloadFile(@PathVariable("id") int id) {
        return fileService.getFile(id);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete a file", description = "Deletes a file by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    public String deleteFile(@PathVariable int id) {
        ResponseEntity<?> file = fileService.deleteFile(id);
        if (file.hasBody()) {
            return "redirect:/files/home";
        } else {
            return "redirect:/files";
        }
    }
}
