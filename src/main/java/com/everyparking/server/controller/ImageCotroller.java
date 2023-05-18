package com.everyparking.server.controller;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ImageCotroller {

    @PostMapping("/api/parking_breaker/lp_img")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        try {
            // 이미지 저장 경로 설정
            String uploadDir = "/home/ubuntu/history";
            File uploadDirFile = new File(uploadDir);

            // 폴더 생성
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // 이미지 파일 경로 설정
            File dest = new File(uploadDir, Objects.requireNonNull(file.getOriginalFilename()));

            // 이미지 저장
            file.transferTo(dest);

            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

}
