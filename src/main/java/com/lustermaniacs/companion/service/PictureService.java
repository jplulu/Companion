package com.lustermaniacs.companion.service;

import com.lustermaniacs.companion.FileStorageProperties;
import com.lustermaniacs.companion.exception.FileFormatException;
import com.lustermaniacs.companion.exception.FileStorageException;
import com.lustermaniacs.companion.models.Picture;
import com.lustermaniacs.companion.models.User;
import com.lustermaniacs.companion.repository.PictureRepository;
import com.lustermaniacs.companion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class PictureService {
    private final Path fileStorageLocation;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    public PictureService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    public Picture storeFile(String username, MultipartFile file) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User not found");
        String type = file.getContentType();
        if(!type.equals("image/jpeg") && !type.equals("image/png"))
            throw new FileFormatException("File must be an image");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains(".."))
                throw new FileStorageException("Sorry! Filename contains invalid path sequence" + fileName);

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        Picture picture = new Picture(fileName, type);
        picture.setProfile(user.getProfile());
        return pictureRepository.save(picture);
    }

    public Resource getFileAsResource(Integer fileId) {
        Optional<Picture> picture = pictureRepository.findById(fileId);
        if(picture.isEmpty())
            throw new EntityNotFoundException("File not found");
        String fileName = picture.get().getName();
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists())
                return resource;
            else
                throw new EntityNotFoundException("File not found");
        } catch (MalformedURLException ex) {
            throw new EntityNotFoundException("File not found");
        }
    }

    @Transactional
    public void deleteFile(String username, Integer fileId) throws EntityNotFoundException, IOException {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User not found");
        Optional<Picture> picture = pictureRepository.findById(fileId);
        if(picture.isEmpty())
            throw new EntityNotFoundException("File not found");
        String fileName = picture.get().getName();
        pictureRepository.deleteByNameAndProfile(fileName, user.getProfile());
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Files.delete(filePath);
    }
}