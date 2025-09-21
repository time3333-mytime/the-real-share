package com.WHY.lease.web.app.controller.FileUploadController;

import com.WHY.lease.common.result.Result;
import com.WHY.lease.web.app.service.FileService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "文件管理")
@RequestMapping("/app/file")
@RestController
public class FileUploadController {
    @Autowired
    FileService service;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
//    @CrossOrigin(origins = "http://localhost:5173")
    public Result<String> upload(@RequestParam MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url=service.upload(file);
        return Result.ok(url);
    }
}