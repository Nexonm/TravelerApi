package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.exception.storage.StorageException;
import com.example.SpringBootNetTry.service.StorageService;
import jdk.internal.loader.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;


    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> image(@RequestParam(name = "uid") long id) throws IOException {
        File file = storageService.getUserPhoto(id);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @GetMapping("/get-one")
    public ResponseEntity<InputStreamResource> getUserPhoto(@RequestParam(name = "uid") long id) {
        try {
            File file = storageService.getUserPhoto(id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("some", "some");
            System.out.println("===HERE_WE_GO===");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (FileNotFoundException e) {

        } catch (StorageException e) {
            return null;
        }
        return null;
    }

    @GetMapping("/get-one-two")
    public ResponseEntity get(@RequestParam(name = "uid") long id) {
        try {

            return ResponseEntity.ok(storageService.getUserPhotoTwo(id));
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!!!" + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public String listAllFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/download/")
                                .path(path.getFileName().toString())
                                .toUriString())
                .collect(Collectors.toList()));

        return "listFiles";
    }

//    @GetMapping("/download/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
//
//        Resource resource = storageService.loadAsResource(filename);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }

    @PostMapping("/upload-file")
    @ResponseBody
    public ResponseEntity uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "uid") long id
    ) {
        try {
            String name = storageService.storeUserPhoto(file, id);

//        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/download/")
//                .path(name)
//                .toUriString();
            return ResponseEntity.ok("Файл был сохранён");
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/upload-multiple-files")
//    @ResponseBody
//    public List<StorageEntity> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.stream(files)
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }
}
