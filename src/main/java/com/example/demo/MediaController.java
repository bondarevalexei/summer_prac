package com.example.demo;

import com.example.demo.model.MediaFile;
import com.example.demo.repository.MediaFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MediaController {

    @Autowired
    private MediaFileRepository repository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileName(file.getOriginalFilename());
        mediaFile.setFileType(file.getContentType());
        mediaFile.setSize(file.getSize());
        mediaFile.setData(file.getBytes());
        repository.save(mediaFile);
        return "redirect:/files";
    }

    @GetMapping("/files")
    public String listFiles(Model model) {
        List<MediaFile> files = repository.findAll();
        model.addAttribute("files", files);
        return "file-list";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Optional<MediaFile> mediaFileOptional = repository.findById(id);
        if (mediaFileOptional.isPresent()) {
            MediaFile mediaFile = mediaFileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaFile.getFileName() + "\"")
                    .body(mediaFile.getData());
        }
        return ResponseEntity.status(404).body(null);
    }
}