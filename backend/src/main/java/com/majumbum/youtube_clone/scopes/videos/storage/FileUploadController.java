package com.majumbum.youtube_clone.scopes.videos.storage;

import com.majumbum.youtube_clone.scopes.auth.payload.response.MessageResponse;
import com.majumbum.youtube_clone.scopes.videos.storage.exceptions.StorageFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/")
    public ResponseEntity<?> handleFileUpload(@RequestBody MultipartFile file) {
        if(file == null)
            return ResponseEntity.ok(new MessageResponse("The file should not be null"));
        storageService.store(file);
        return ResponseEntity.ok(new MessageResponse("File successfully uploaded"));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}