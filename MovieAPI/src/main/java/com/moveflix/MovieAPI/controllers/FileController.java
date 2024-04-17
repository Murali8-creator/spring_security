package com.moveflix.MovieAPI.controllers;

import com.moveflix.MovieAPI.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${project.poster}")
    private String path;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String > uploadFileHandler(@RequestPart MultipartFile multipartFile) throws IOException {
        logger.info("File upload request received");
        String uploadedFileName = fileService.uploadFile(path, multipartFile);
        logger.info("File uploaded successfully");
        return ResponseEntity.ok("Uploaded File Name : "+uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}