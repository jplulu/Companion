package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.exception.FileFormatException;
import com.lustermaniacs.companion.exception.FileStorageException;
import com.lustermaniacs.companion.models.Picture;
import com.lustermaniacs.companion.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@RequestMapping("/picture")
@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @PostMapping("/{username}")
    public ResponseEntity<?> uploadPicture(@PathVariable("username") String username, @RequestParam("file") MultipartFile file) throws EntityNotFoundException, FileFormatException, FileStorageException {
        Picture picture = pictureService.storeFile(username, file);
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Resource> getPicture(@RequestParam("fileId") Integer id, HttpServletRequest request) throws IOException {
        Resource resource = pictureService.getFileAsResource(id);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deletePicture(@PathVariable("username") String username, @RequestParam("fileId") Integer id) throws IOException {
        pictureService.deleteFile(username, id);
        return new ResponseEntity<>("Picture deleted", HttpStatus.OK);
    }
}
