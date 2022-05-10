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
import org.springframework.http.client.MultipartBodyBuilder;
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


    @GetMapping(value = "/image-user", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> imageUser(@RequestParam(name = "uid") long id) throws IOException {
        File file = storageService.getUserPhoto(id);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @GetMapping(value = "/image-card", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> imageCard(@RequestParam(name = "cid") long id) throws IOException {
        File file = storageService.getCardPhoto(id);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @GetMapping(value = "/image-post", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> imagePost(@RequestParam(name = "pid") long id) throws IOException {
        File file = storageService.getPostPhoto(id);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

    @Deprecated
    @GetMapping("/get-one-user")
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
            return null;
        } catch (StorageException e) {
            return null;
        }
    }

    @Deprecated
    @GetMapping("/get-one-card")
    public ResponseEntity<InputStreamResource> getCardPhoto(@RequestParam(name = "cid") long id) {
        try {
            File file = storageService.getCardPhoto(id);
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
            return null;
        } catch (StorageException e) {
            return null;
        }
    }

    @GetMapping("/get-user-path")
    public ResponseEntity getPathToUsrPhoto(@RequestParam(name = "uid") long id) {
        try {

            return ResponseEntity.ok(storageService.getUserPhotoTwo(id));
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!!!" + e.getMessage());
        }
    }

    @GetMapping("/get-card-path")
    public ResponseEntity getPathToCardPhoto(@RequestParam(name = "cid") long id) {
        try {
            return ResponseEntity.ok(storageService.getCardPhotoTwo(id));
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!!!" + e.getMessage());
        }
    }

    @GetMapping("/get-post-path")
    public ResponseEntity getPathToPostPhoto(@RequestParam(name = "pid") long id) {
        try {
            return ResponseEntity.ok(storageService.getPostPhotoTwo(id));
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

    @PostMapping(path = "/upload-file-user")
    public ResponseEntity uploadFileUser(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "uid") String id
    ) {
        try {
            String name = storageService.storeUserPhoto(file, getNum(id));
            System.out.println("FILE name for uid:" + id + " is " + name);
//        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/download/")
//                .path(name)
//                .toUriString();
            return ResponseEntity.ok("Файл был сохранён");
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/upload-file-card")
    public ResponseEntity uploadFileCard(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "cid") String id
    ) {
        try {

            String name = storageService.storeCardsPhoto(file, getNum(id));
            System.out.println("FILE name for cid:" + id + " is " + name);
            return ResponseEntity.ok("Файл был сохранён");
        } catch (StorageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private long getNum(String str) {
        return Long.parseLong(str);
    }

    @PostMapping(path = "/upload-file-post")
    public ResponseEntity uploadFilePost(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "pid") String id
    ) {
        try {

            String name = storageService.storePostPhoto(file, getNum(id));
            System.out.println("FILE name for pid:" + id + " is " + name);
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
