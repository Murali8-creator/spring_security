package com.moveflix.MovieAPI.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class FileServiceImpl implements FileService{

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //get name of the file
        String fileName = file.getOriginalFilename();
        logger.info("in service layer");
        logger.info("File name : "+fileName);

        //to get the file path
        String filePath = path + File.separator + fileName;

        //create the file
        File newFile = new File(filePath);

        if(!newFile.exists()){
            newFile.mkdir();
        }

        //copy the file or upload file
        Files.copy(file.getInputStream() , Paths.get(filePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String name) throws FileNotFoundException {
        String filePath = path + File.separator + name;

        return new FileInputStream(filePath);
    }
}
