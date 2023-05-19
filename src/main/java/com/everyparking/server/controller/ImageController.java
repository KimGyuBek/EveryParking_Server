package com.everyparking.server.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

//    @PostMapping("/api/parking_breaker/lp_img")
//    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
//        try {
//            // 이미지 저장 경로 설정
//            String uploadDir = "/home/ubuntu/history";
//            File uploadDirFile = new File(uploadDir);
//
//            // 폴더 생성
//            if (!uploadDirFile.exists()) {
//                uploadDirFile.mkdirs();
//            }
//
//            // 이미지 파일 경로 설정
//            File dest = new File(uploadDir, Objects.requireNonNull(file.getOriginalFilename()));
//
//            // 이미지 저장
//            file.transferTo(dest);
//
//            return ResponseEntity.ok("File uploaded successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Failed to upload file.");
//        }
//    }

    @Value("${file.dir}")
    private String fileDir;

    @ResponseBody
//    @GetMapping("/images/{fileName}")
    @RequestMapping(method = RequestMethod.GET,
        value = "/images/{fileName}",
        produces = "image/jpeg")
    public Resource downloadImage(@PathVariable("fileName") String fileName,
        HttpServletResponse response)
        throws MalformedURLException {

        response.setContentType("image/jpg");

        return new UrlResource("file:" + fileDir + fileName);
    }


}
