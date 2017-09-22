package com.maxpaint.fileinfo.upload.flat.endpoint;

import com.maxpaint.fileinfo.upload.flat.service.parser.FileInfo;
import com.maxpaint.fileinfo.upload.flat.service.parser.TxtFileParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("upload/flat/file")
@Slf4j
public class UploadFileEndpoint {


    @Autowired
    private TxtFileParser txtFileParser;

    private List<FileInfo> fileInfos;

    @PostMapping()
    @SneakyThrows
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadfile) {
        log.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }

        try {
            fileInfos = txtFileParser.parse(uploadfile.getBytes());

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping
    private List<FileInfo> getFileInfos(){
        return fileInfos;
    }
}
